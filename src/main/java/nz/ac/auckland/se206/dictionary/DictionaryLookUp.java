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
    return definition;
  }

  /**
   * @param element
   * @return definition of word as a jsonElement
   */
  private static JsonElement getDefinition(JsonElement element) {
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
    return jsonObject;
  }
}
