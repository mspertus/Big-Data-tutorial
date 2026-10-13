#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransportException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

import edu.uchicago.mpcs53013.weatherSummary.WeatherSummary;


public class SerializeWeatherSummary {
	static TProtocol protocol;
	public static void main(String[] args) {
		if(args.length == 0 || args.length > 2) {
			System.err.println("Usage:  yarn jar jarname tarFileLocations hdfsLocationToGenerateThriftFiles");
			System.err.println("");
			System.err.println("Examples:");
			System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary /home/hadoop/spertus/weatherData /inputs/thriftWeather1");
			System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary  hdfs:///inputs/raw_weather /inputs/thriftWeather2");
			System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary  s3://spertus-abc/weather /inputs/thriftWeather3");
			System.exit(-1);
		}
		final String targetDir = args.length > 1 ? args[1] : "/inputs/thriftWeather";
		try {
			Configuration conf = new Configuration();
			final Configuration finalConf = new Configuration(conf);
			final FileSystem fs = FileSystem.get(conf);
			final TSerializer ser = new TSerializer(new TBinaryProtocol.Factory());
			final Map<Integer, SequenceFile.Writer> yearMap = new HashMap<Integer, SequenceFile.Writer>();
			WeatherSummaryProcessor processor = new WeatherSummaryProcessor() {
				Pattern yearPattern = Pattern.compile("^.*${symbol_escape}${symbol_escape}d+-${symbol_escape}${symbol_escape}d+-(${symbol_escape}${symbol_escape}d+)${symbol_escape}${symbol_escape}.op${symbol_escape}${symbol_escape}.gz");
				
				Writer getWriter(String fileName) throws IOException {
					Matcher yearMatcher = yearPattern.matcher(fileName);
					if(!yearMatcher.find())
						throw new IllegalArgumentException("Bad file name. Can't find year: " + fileName);
					int year = Integer.parseInt(yearMatcher.group(1));
					if(!yearMap.containsKey(year)) {
						yearMap.put(year, 
								SequenceFile.createWriter(finalConf,
										SequenceFile.Writer.file(
												new Path(targetDir + "/weather-" + Integer.toString(year))),
										SequenceFile.Writer.keyClass(IntWritable.class),
										SequenceFile.Writer.valueClass(BytesWritable.class),
										SequenceFile.Writer.compression(CompressionType.NONE)));
					}
					return yearMap.get(year);
				}

				@Override
				void processWeatherSummary(WeatherSummary summary, String fileName) throws IOException {
					try {
						getWriter(fileName).append(new IntWritable(1), new BytesWritable(ser.serialize(summary)));;
					} catch (TException e) {
						throw new IOException(e);
					}
				}
			};

			Iterable<InputStream> tarFiles;
			String tarFilesDirectory = args[0];
			if(tarFilesDirectory.startsWith("hdfs://"))
				tarFiles = new InputStreamsForHdfsDirectory(fs, tarFilesDirectory);
			else if (tarFilesDirectory.startsWith("s3://"))
				tarFiles = new InputStreamsForS3Folder(tarFilesDirectory);
			else
				tarFiles = new InputStreamsForLocalDirectory(tarFilesDirectory);

			for (InputStream is : tarFiles) {
				processor.processNoaaTarFile(is);
			}
			for (Writer writer : yearMap.values()) {
				writer.close();
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TTransportException e) {
			throw new RuntimeException(e);
		}
	}
}

// The classes below make it possible to iterate InputStreams for all the tar files in a folder.
// There is one class for each kind of folder that can hold the TarFiles (local filesystem, HDFS, and S3).

// All of these are iterable, so we can use a for loop as above to iterate through the tar files.
// This requires a lot of boilerplate implementing the Iterable and Iterator interfaces (e.g., hasNext()),
// but fortunately, that is basically passing through to the appropriate iterator for the folder type.

class InputStreamsForLocalDirectory implements Iterable<InputStream> {
	InputStreamsForLocalDirectory(String pathName) {
		this.pathName = pathName;
	}
	String pathName;

	@Override
	public Iterator<InputStream> iterator() {
		return new LocalInputStreamsIterator();
	}

	private class LocalInputStreamsIterator implements Iterator<InputStream> {
		Iterator<File> fileIterator
				= FileUtils.iterateFiles(new File(pathName), null, false);

		@Override
		public boolean hasNext() {
			return fileIterator.hasNext();
		}

		@Override
		public InputStream next() {
			try {
				return new FileInputStream(fileIterator.next());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
/** Provides InputStreams for all the tar files in an HDFS directory
 *
 */
class InputStreamsForHdfsDirectory implements Iterable<InputStream> {
	/**
	 *
	 * @param fs The HDFS Filesystem containing the directory
	 * @param pathName The pathName of the directory
	 */
	InputStreamsForHdfsDirectory(FileSystem fs, String pathName) {
		this.fs = fs;
		String hdfsPrefix = "hdfs://";
		String withoutHdfsPrefix
				= pathName.startsWith(hdfsPrefix) ? pathName.substring(hdfsPrefix.length()) : pathName;

		this.path = new Path(withoutHdfsPrefix);
	}
	FileSystem fs;
	Path path;

	@Override
	public Iterator<InputStream> iterator() {
		return new HdfsInputStreamsIterator();
	}

	private class HdfsInputStreamsIterator implements Iterator<InputStream> {
		HdfsInputStreamsIterator() {
			try {
				fileStatusIterator = Arrays.asList(fs.listStatus(path)).iterator();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Iterator<FileStatus> fileStatusIterator;

		@Override
		public boolean hasNext() {
			return fileStatusIterator.hasNext();
		}

		@Override
		public InputStream next() {
			try {
				return fs.open(fileStatusIterator.next().getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
}

class InputStreamsForS3Folder implements Iterable<InputStream> {
	InputStreamsForS3Folder(String folderName) {
		String s3Prefix = "s3://";
		String withoutS3Prefix
				= folderName.startsWith(s3Prefix) ? folderName.substring(s3Prefix.length()) : folderName;
		// Up to the first / is the bucket name
		bucketName = withoutS3Prefix.substring(0, withoutS3Prefix.indexOf('/'));
		// After the bucket name is the object prefix
		objectPrefix = withoutS3Prefix.substring(bucketName.length()+1);
	}
	String bucketName;
	String objectPrefix;
	S3Client s3 = S3Client.builder().region(Region.US_EAST_2).build();
	@Override
	public Iterator<InputStream> iterator() {
		return new S3InputStreamsIterator();
	}

	private class S3InputStreamsIterator implements Iterator<InputStream> {

		@Override
		public boolean hasNext() {
			return objectIterator.hasNext();
		}

		@Override
		public InputStream next() {
			return s3.getObject(GetObjectRequest.builder()
					.bucket(bucketName)
					.key(objectIterator.next().key())
					.build());
		}

		Iterator<S3Object> objectIterator
				= s3.listObjectsV2Paginator(ListObjectsV2Request.builder()
						.bucket(bucketName)
						.prefix(objectPrefix)
						.build())
					.contents().iterator();
	}
}
