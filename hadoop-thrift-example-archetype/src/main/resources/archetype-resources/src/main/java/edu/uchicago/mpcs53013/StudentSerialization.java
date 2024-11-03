package edu.uchicago.mpcs53013;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.hadoop.io.serializer.Serialization;
import org.apache.hadoop.io.serializer.Serializer;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
public class StudentSerialization implements Serialization<Student> {

	class StudentDeserializer implements Deserializer<Student> {
		  private TProtocol protocol;

		@Override
		public void close() throws IOException {
		}

		@Override
		public Student deserialize(Student student) throws IOException {
			if(student == null) {
				student = new Student();
			}
			try {
				student.read(protocol);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return student;
		}

		@Override
		public void open(InputStream is) throws IOException {
			try {
				protocol = new TJSONProtocol(new TIOStreamTransport(is));
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	class StudentSerializer implements Serializer<Student> {
		  private TProtocol protocol;

		@Override
		public void close() throws IOException {
		}

		@Override
		public void open(OutputStream os) throws IOException {
			try {
				protocol = new TJSONProtocol(new TIOStreamTransport(os));
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void serialize(Student student) throws IOException {
			try {
				student.write(protocol);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public boolean accept(Class<?> clazz) {
		return clazz.equals(Student.class);
	}

	@Override
	public Deserializer<Student> getDeserializer(Class<Student> clazz) {
		return new StudentDeserializer();
	}

	@Override
	public Serializer<Student> getSerializer(Class<Student> clazz) {
		return new StudentSerializer();
	}

}
