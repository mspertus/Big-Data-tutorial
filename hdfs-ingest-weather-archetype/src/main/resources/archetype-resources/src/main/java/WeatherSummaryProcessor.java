#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import edu.uchicago.mpcs53013.weatherSummary.WeatherSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;


public abstract class WeatherSummaryProcessor {
	static class MissingDataException extends Exception {

	    public MissingDataException(String message) {
	        super(message);
	    }

	    public MissingDataException(String message, Throwable throwable) {
	        super(message, throwable);
	    }

	}
	
	static double tryToReadMeasurement(String name, String s, String missing) throws MissingDataException {
		if(s.equals(missing))
			throw new MissingDataException(name + ": " + s);
		return Double.parseDouble(s.trim());
	}

	void processLine(String line, String archiveEntryName) throws IOException {
		try {
			processWeatherSummary(weatherFromLine(line), archiveEntryName);
		} catch(MissingDataException e) {
			// Just ignore lines with missing data
		}
	}

	abstract void processWeatherSummary(WeatherSummary summary, String fileName) throws IOException;
	BufferedReader getFileReader(InputStream is) throws FileNotFoundException, IOException {
		return new BufferedReader(new InputStreamReader(new GZIPInputStream(is)));
	}
	
	void processNoaaFile(InputStream is, String archiveEntryName) throws IOException {
		BufferedReader br = getFileReader(is);
		br.readLine(); // Discard header
		String line;
		while((line = br.readLine()) != null) {
			processLine(line, archiveEntryName);
		}
	}

	void processNoaaTarFile(InputStream is) throws IOException {
		TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(is);
		TarArchiveEntry entry;
		while ((entry = (TarArchiveEntry)tarArchiveInputStream.getNextEntry()) != null) {
			if(!entry.getName().endsWith(".gz"))
				continue;
			processNoaaFile(tarArchiveInputStream, entry.getName());
		}
	}
	
	WeatherSummary weatherFromLine(String line) throws NumberFormatException, MissingDataException {
		WeatherSummary summary 
			= new WeatherSummary(Integer.parseInt(line.substring(0, 6).trim()),
				                      Short.parseShort(line.substring(14, 18).trim()),
				                      Byte.parseByte(line.substring(18, 20).trim()),
				                      Byte.parseByte(line.substring(20, 22).trim()),
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
