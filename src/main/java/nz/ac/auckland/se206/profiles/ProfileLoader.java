package nz.ac.auckland.se206.profiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.opencsv.exceptions.CsvException;

public class ProfileLoader {

  String username;

  /**
   * constructor for profile loader, Just one is reuqired for all loading reading
   * and writing
   * purposes
   * 
   * @param username
   */
  public ProfileLoader(String username) {
    this.username = username;
  }

  /**
   * This method creates a new json file to be read and sets default values
   * 
   * @throws IOException
   * @throws CsvException
   */
  public void create() throws IOException, CsvException {
    // create New Profile and make it default settings with name as user name
    Profile profile = new Profile();
    String filePath = "src/main/resources/profiles/" + username + ".json";
    profile.setName(username);
    // create json format using gson
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

  /**
   * This creates a new instance of profile and returns the profile so its info
   * can be accessed
   * 
   * @return loaded profile
   */
  public Profile read() {
    // create new gson so it can read
    Gson gson = new Gson();
    try {
      // get file and read it
      String filePath = "src/main/resources/profiles/" + username + ".json";
      BufferedReader buffReader = new BufferedReader(new FileReader(filePath));
      // read the file and make it into a profile
      Profile readProfile = gson.fromJson(buffReader, Profile.class);
      return readProfile;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * This takes a profile and name of file/user profile as a input and updates the
   * profile
   * 
   * @param profile
   * @param name
   * @throws IOException
   * @throws CsvException
   */
  public void updateJSON(Profile profile, String name) throws IOException, CsvException {
    String filePath = "src/main/resources/profiles/" + name + ".json";
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
