package nz.ac.auckland.se206.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import com.opencsv.exceptions.CsvException;



public class ProfileLoaderTest {
  Profile profile;

  @Test
  public void createTest() throws URISyntaxException, IOException, CsvException {
    ProfileLoader s = new ProfileLoader("createTest");
    s.create();
    String expectedOutput =
        "{\"username\":\"createTest\",\"wins\":0,\"losses\":0,\"wordHistory\":[],\"fastestWinTime\":0}";
    assertEquals(s.read().toString(), expectedOutput);
  }

  @Test
  public void readTest() throws URISyntaxException, IOException, CsvException {
    ProfileLoader s = new ProfileLoader("readTest");
    s.create();
    Profile profile = s.read();
    assertEquals(profile.getFastestWinTime(), 0);
    assertEquals(profile.getLosses(), 0);
    assertEquals(profile.getUsername(), "readTest");
    assertEquals(profile.getWins(), 0);

  }

  @Test
  public void updateJSONTest() throws URISyntaxException, IOException, CsvException {
    ProfileLoader s = new ProfileLoader("updateJSONTest");
    s.create();
    Profile prof = s.read();
    prof.addToList("word1");
    prof.addToList("word2");
    s.updateJSON(prof, "updateJSONTest");
    String expectedOutput =
        "{\"username\":\"updateJSONTest\",\"wins\":0,\"losses\":0,\"wordHistory\":[word1, word2],\"fastestWinTime\":0}";
    assertEquals(s.read().toString(), expectedOutput);
  }
}
