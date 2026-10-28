#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// Inspired by http://stackoverflow.com/questions/14458450/what-to-use-instead-of-org-jboss-resteasy-client-clientrequest
public class StreamFlightsIntoKafka {
	static class Task extends TimerTask {
		private Client client;
		Random generator = new Random();
		// We are just going to get a random sampling of flights from a few airlines
		// Getting all flights would be much more expensive!
		String[] airlines = new String[] { "DAL", "AAL", "SWA", "UAL" };

		public EnrouteFlights getEnrouteFlights() {
			Invocation.Builder bldr
			  = client.target("https://aeroapi.flightaware.com/aeroapi/operators/UAL/flights/enroute")
					   .request("application/json")
					   .header("x-apikey", "Your API key here");
			try {
				return  bldr.get(EnrouteFlights.class);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			return null;  // Sometimes the web service fails due to network problems. Just let it try again
		}


		// Adapted from http://hortonworks.com/hadoop-tutorial/simulating-transporting-realtime-events-stream-apache-kafka/
		Properties props = new Properties();
		String TOPIC = "flights";
		KafkaProducer<String, String> producer;
		
		public Task() {
			client = ClientBuilder.newClient();
			// enable POJO mapping using Jackson - see
			// https://jersey.java.net/documentation/latest/user-guide.html${symbol_pound}json.jackson
			client.register(JacksonFeature.class); 
			props.put("bootstrap.servers", bootstrapServers);
			props.put("acks", "all");
			props.put("retries", 0);
			props.put("batch.size", 16384);
			props.put("linger.ms", 1);
			props.put("buffer.memory", 33554432);
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("security.protocol", "SASL_SSL");
			// MSK IAM authentication uses the AWS credentials of the machine we run on (e.g., the EMR instance role)
			props.put("sasl.mechanism", "AWS_MSK_IAM");
			props.put("sasl.jaas.config", "software.amazon.msk.auth.iam.IAMLoginModule required;");
			props.put("sasl.client.callback.handler.class", "software.amazon.msk.auth.iam.IAMClientCallbackHandler");

			producer = new KafkaProducer<>(props);
		}

		@Override
		public void run() {
			EnrouteFlights enrouteFlights = getEnrouteFlights();
			if(enrouteFlights == null)
				return;

			ObjectMapper mapper = new ObjectMapper();
			System.out.println("running");
			for(Enroute enroute : enrouteFlights.getEnroute()) {
				// Skip flights FlightAware has no delay for. An exception here would stop the Timer.
				if(enroute.getDepartureDelay() == null)
					continue;
				ProducerRecord<String, String> data;
				try {
					KafkaFlightRecord kfr = new KafkaFlightRecord(
							enroute.getIdent(),
							enroute.getOrigin().getCodeIata(),
							enroute.getDestination().getCodeIata(),
							enroute.getDepartureDelay()/60);
					System.out.println(kfr.flight);
					data = new ProducerRecord<String, String>
					(TOPIC, 
					 mapper.writeValueAsString(kfr));
					producer.send(data,(recordMetadata, exception) -> {
						if (exception == null) {
							System.out.println("Record written to offset " +
									recordMetadata.offset() + " timestamp " +
									recordMetadata.timestamp());
						} else {
							System.err.println("An error occurred");
							exception.printStackTrace(System.err);
						}
					});
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	static String bootstrapServers = new String("localhost:9092");

	public static void main(String[] args) {
		if(args.length > 0)  // This lets us run on the cluster with a different kafka
			bootstrapServers = args[0];
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), 0, 60*1000);
	}
}

