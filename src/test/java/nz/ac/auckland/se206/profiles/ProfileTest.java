package nz.ac.auckland.se206.profiles;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

public class ProfileTest {
  @Test
  void newProfileTest() throws URISyntaxException, IOException, CsvException {
    Profile NewProfile = new Profile("test");
  }
}
