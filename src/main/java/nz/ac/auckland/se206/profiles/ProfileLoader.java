package nz.ac.auckland.se206.profiles;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileLoader {

  /**
   * This creates a new instance of profile and returns the profile so its info can be accessed
   *
   * @return loaded profile
   * @throws FileNotFoundException
   */
  public static Profile read(String username) throws FileNotFoundException {
    // create new gson so it can read
    Gson gson = new Gson();
    // get file and read it
    String filePath = "profiles/" + username + ".json";
    BufferedReader buffReader = new BufferedReader(new FileReader(filePath));
    // read the file and make it into a profile
    Profile readProfile = gson.fromJson(buffReader, Profile.class);
    return readProfile;
  }

  /**
   * This takes a profile and name of file/user profile as a input and updates the profile
   *
   * @param profile
   * @param name
   * @throws IOException
   */
  public static void updateJSON(Profile profile) throws IOException {
    String filePath = "profiles/" + profile.getUsername() + ".json";
    // create json format using gjon with passed in profile
    Gson gson = new Gson();
    String json = gson.toJson(profile);
    try {
      // write to a new file at correct path
      FileWriter writer = new FileWriter(filePath);
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
