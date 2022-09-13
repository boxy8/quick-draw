package nz.ac.auckland.se206.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

public class ProfileTest {

  @Test
  public void testCSVreader() throws URISyntaxException, IOException, CsvException {
    Profile profile = new Profile("test");
    assertEquals(1, profile.getWins());
    assertEquals(4, profile.getLosses());
    assertEquals("airplane", profile.getWordHistory().get(0));
    assertEquals("giraffe", profile.getWordHistory().get(1));
  }
}
