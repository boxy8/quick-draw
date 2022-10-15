package nz.ac.auckland.se206.dictionary;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DictionaryLookUp {
  private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

  /**
   * Searches for the word in an online dictionary, returns string with definition or null if it is
   * not found
   *
   * @param query the word you wish to search up
   * @return the definition of the word as a string
   * @throws IOException if it was unable to read from the api
   */
  public static String searchWordInfo(String query) throws IOException {
    // JSON reading setup
    Gson gson = new Gson();
    // Connect to server
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(API_URL + query).build();
    Response response = client.newCall(request).execute();
    // get JSON information
    ResponseBody responseBody = response.body();
    // Initialize variables
    String definition = "Not found";
    // save JSON as string
    String jsonString = responseBody.string();
    // read JSON
    JsonElement element = gson.fromJson(jsonString, JsonElement.class);
    // check to see if there is a meaning for this word
    if (element.isJsonArray()) {
      // getter reads specific place where definitions are stored and return an object with that
      // information
      // clean up info and put into a string
      definition = getDefinition(element).toString().replace("\"", "");
    }
    // return null if there is no definition
    if (definition.equals("Not found")) {
      return null;
    } else {
      return definition;
    }
  }

  /**
   * This finds the area of the json with the definition and returns it so that it is easy to use
   *
   * @param element the json element to search inside
   * @return definition of word as a jsonElement
   */
  private static JsonElement getDefinition(JsonElement element) {
    // grab required string which is the definition
    // it finds the first meaning of the word only
    JsonElement jsonObject =
        element
            .getAsJsonArray()
            .get(0)
            .getAsJsonObject()
            .get("meanings")
            .getAsJsonArray()
            .get(0)
            .getAsJsonObject()
            .get("definitions")
            .getAsJsonArray()
            .get(0)
            .getAsJsonObject()
            .get("definition");
    // returns as a json object for easier reworking
    return jsonObject;
  }
}
