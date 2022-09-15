package nz.ac.auckland.se206.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvException;

public class ProfileTest {
	Profile profile;

	@Test
	public void readDefaultCsvTest() throws URISyntaxException, IOException, CsvException {
		Profile readDefault = new Profile("" + System.currentTimeMillis());
		assertEquals(0, readDefault.getWins());
		assertEquals(0, readDefault.getLosses());
		assertEquals(60, readDefault.getfastestWinTime());
	}

	@Test
	public void incrementWinsTest() throws IOException, CsvException {
		Profile winTest = new Profile("" + System.currentTimeMillis());
		winTest.incrementWins();
		winTest.readCsv();
		assertEquals(1, winTest.getWins());
		winTest.incrementWins();
		winTest.readCsv();
		assertEquals(2, winTest.getWins());
		winTest.incrementWins();
		winTest.readCsv();
		assertEquals(3, winTest.getWins());
	}

	@Test
	public void incrementLossesTest() throws IOException, CsvException {
		Profile lossTest = new Profile("" + System.currentTimeMillis());
		lossTest.incrementLosses();
		lossTest.readCsv();
		assertEquals(1, lossTest.getLosses());
		lossTest.incrementLosses();
		lossTest.readCsv();
		assertEquals(2, lossTest.getLosses());
		lossTest.incrementLosses();
		lossTest.readCsv();
		assertEquals(3, lossTest.getLosses());
	}

	@Test
	public void changeFastestWinTimeTest() throws IOException, CsvException {
		Profile fastestTime = new Profile("" + System.currentTimeMillis());
		fastestTime.setfastestWinTime(50);
		fastestTime.readCsv();
		assertEquals(50, fastestTime.getfastestWinTime());
		fastestTime.setfastestWinTime(25);
		fastestTime.readCsv();
		assertEquals(25, fastestTime.getfastestWinTime());
		fastestTime.setfastestWinTime(20);
		fastestTime.readCsv();
		assertEquals(20, fastestTime.getfastestWinTime());
	}

}
