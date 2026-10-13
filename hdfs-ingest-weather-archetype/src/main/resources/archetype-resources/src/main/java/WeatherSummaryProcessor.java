#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import edu.uchicago.mpcs53013.weatherSummary.WeatherSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

// Reads NOAA Global Surface Summary of the Day (GSOD) yearly archives, such as
// https://www.ncei.noaa.gov/data/global-summary-of-the-day/archive/2024.tar.gz
// Each archive is a gzipped tar file with one CSV file per weather station.
public abstract class WeatherSummaryProcessor {
	static class MissingDataException extends Exception {

	    public MissingDataException(String message) {
	        super(message);
	    }

	    public MissingDataException(String message, Throwable throwable) {
	        super(message, throwable);
	    }

	}

	// The first line of each CSV file names the columns, so we can look up fields by name
	static final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
			.setHeader()
			.setSkipHeaderRecord(true)
			.get();

	static double tryToReadMeasurement(String name, String s, String missing) throws MissingDataException {
		s = s.trim(); // NOAA pads numbers with spaces
		if(s.equals(missing))
			throw new MissingDataException(name + ": " + s);
		return Double.parseDouble(s);
	}

	void processRecord(CSVRecord record) throws IOException {
		try {
			processWeatherSummary(weatherFromRecord(record));
		} catch(MissingDataException e) {
			// Just ignore lines with missing data
		}
	}

	abstract void processWeatherSummary(WeatherSummary summary) throws IOException;

	void processNoaaFile(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		// Don't close the parser when done, as that would also close the tar file we are reading from
		for (CSVRecord record : csvFormat.parse(br)) {
			processRecord(record);
		}
	}

	void processNoaaTarFile(InputStream is) throws IOException {
		TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GZIPInputStream(is));
		TarArchiveEntry entry;
		while ((entry = tarArchiveInputStream.getNextEntry()) != null) {
			if(!entry.getName().endsWith(".csv"))
				continue;
			processNoaaFile(tarArchiveInputStream);
		}
	}

	WeatherSummary weatherFromRecord(CSVRecord record) throws NumberFormatException, MissingDataException {
		// STATION is the 6-digit USAF station number followed by the 5-digit WBAN number.
		// We keep just the USAF number, as the station field always has.
		String station = record.get("STATION").substring(0, 6);
		String date = record.get("DATE"); // yyyy-mm-dd
		String frshtt = record.get("FRSHTT"); // Fog, Rain, Snow, Hail, Thunder, Tornado
		WeatherSummary summary
			= new WeatherSummary(station,
				                      Short.parseShort(date.substring(0, 4)),
				                      Byte.parseByte(date.substring(5, 7)),
				                      Byte.parseByte(date.substring(8, 10)),
				                      tryToReadMeasurement("Mean Temperature", record.get("TEMP"), "9999.9"),
				                      tryToReadMeasurement("Mean Visibility", record.get("VISIB"), "999.9"),
				                      tryToReadMeasurement("Mean WindSpeed", record.get("WDSP"), "999.9"),
				                      frshtt.charAt(0) == '1',
				                      frshtt.charAt(1) == '1',
				                      frshtt.charAt(2) == '1',
				                      frshtt.charAt(3) == '1',
				                      frshtt.charAt(4) == '1',
				                      frshtt.charAt(5) == '1');
		return summary;
	}

}
