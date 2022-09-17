package nz.ac.auckland.se206.profiles;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import com.opencsv.exceptions.CsvException;



public class ProfileLoaderTest {
  Profile profile;

  @Test
  public void readasdf() throws URISyntaxException, IOException, CsvException {
    ProfileLoader s = new ProfileLoader("somethin2g");
    s.create();
    System.out.println(s.read());
    s.read();
  }



}
