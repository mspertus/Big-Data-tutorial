#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import com.amazonaws.services.lambda.runtime.Context;

import edu.uchicago.WeatherSummary;

public class Transform {
	public Transform(Context context) {
		this.context = context;
	}

	Context context;
	static DatumWriter<WeatherSummary> wsDatumWriter = new SpecificDatumWriter<WeatherSummary>(WeatherSummary.class);

	static class MissingDataException extends Exception {
	    public MissingDataException(String message) {
	        super(message);
	    }

	    public MissingDataException(String message, Throwable throwable) {
	        super(message, throwable);
	    }
	}

	public void transform(TarArchiveInputStream is, File outp) {
		DataFileWriter<WeatherSummary> dataFileWriter = new DataFileWriter<WeatherSummary>(wsDatumWriter);
		try {
			dataFileWriter.create(WeatherSummary.getClassSchema(), outp);

			TarArchiveEntry entry = null;
			while((entry = is.getNextTarEntry()) != null) {
				context.getLogger().log(entry.getName());
				if(!entry.getName().endsWith("gz"))
					continue;
				BufferedReader fileReader 
				  = new BufferedReader(new InputStreamReader(new GZIPInputStream(is)));
				context.getLogger().log("header: " + fileReader.readLine());
				String line = null;
				while ((line = fileReader.readLine()) != null) {
					try {
						dataFileWriter.append(weatherFromLine(line));
					} catch (MissingDataException e) {
						continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				System.out.println("In finally");
				dataFileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static double tryToReadMeasurement(String name, String s, String missing) throws MissingDataException {
		if(s.equals(missing))
			throw new MissingDataException(name + ": " + s);
		return Double.parseDouble(s.trim());

	}
	
	static WeatherSummary weatherFromLine(String line) throws NumberFormatException, MissingDataException {
		System.out.println(line);
		WeatherSummary summary 
			= new WeatherSummary(Integer.parseInt(line.substring(0, 6).trim()),
				                      Integer.parseInt(line.substring(14, 18).trim()),
				                      Integer.parseInt(line.substring(18, 20).trim()),
				                      Integer.parseInt(line.substring(20, 22).trim()),
				                      tryToReadMeasurement("Mean Temperature", line.substring(24, 30), "9999.9"),
				                      tryToReadMeasurement("Mean Visibility", line.substring(68, 73), "999.9"),
				                      tryToReadMeasurement("Mean WindSpeed", line.substring(78, 83), "999.9"),
				                      line.charAt(132) == '1',
				                      line.charAt(133) == '1',
				                      line.charAt(134) == '1',
				                      line.charAt(135) == '1',
				                      line.charAt(136) == '1',
				                      line.charAt(137) == '1');
		return summary;
	}

}
