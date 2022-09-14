package nz.ac.auckland.se206.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

public class ProfileTest {

  @Test
  public void readDefaultCsvTest() throws URISyntaxException, IOException, CsvException {
    Profile profile = new Profile("test");
    assertEquals(0, profile.getWins());
    assertEquals(0, profile.getLosses());
    assertEquals(60, profile.getfastestWinTime());
  }
}
