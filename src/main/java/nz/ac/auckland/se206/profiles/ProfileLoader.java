package nz.ac.auckland.se206.profiles;

import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import nz.ac.auckland.se206.badges.Badge;
import nz.ac.auckland.se206.badges.BadgeTypeAdapter;

public class ProfileLoader {

  /**
   * This creates a new instance of profile and returns the profile so its info can be accessed
   *
   * @return loaded profile
   * @throws FileNotFoundException
   */
  public static Profile read(String username) throws FileNotFoundException {
    // create new gson so it can read
    GsonBuilder gson = new GsonBuilder();
    gson.registerTypeAdapter(Badge.class, new BadgeTypeAdapter());
    // get file and read it
    String filePath = "profiles/" + username + ".json";
    BufferedReader buffReader = new BufferedReader(new FileReader(filePath));
    // read the file and make it into a profile
    Profile readProfile = gson.create().fromJson(buffReader, Profile.class);
    try {
      buffReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return readProfile;
  }

  /**
   * This takes a profile and name of file/user profile as a input and updates the profile
   *
   * @param profile
   * @throws IOException
   */
  public static void updateJson(Profile profile) throws IOException {
    String filePath = "profiles/" + profile.getUsername() + ".json";

    // create json format using gson with passed in profile
    GsonBuilder gson = new GsonBuilder();
    gson.registerTypeAdapter(Badge.class, new BadgeTypeAdapter());
    String json = gson.create().toJson(profile);
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
