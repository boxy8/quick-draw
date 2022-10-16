package nz.ac.auckland.se206.badges;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * Provides custom serialization for Badge objects
 *
 * @author mat_boy <https://stackoverflow.com/users/1983997/mat-boy>
 * @copyright 2020 mat_boy
 * @license CC BY-SA 4.0
 * @see {@link https://stackoverflow.com/a/16873051/1248177|Gson and abstract superclasses:
 *     deserialization issue}
 */
public class BadgeTypeAdapter implements JsonSerializer<Badge>, JsonDeserializer<Badge> {
  @Override
  public JsonElement serialize(Badge src, Type typeOfSrc, JsonSerializationContext context) {
    // create new json object to modify
    JsonObject result = new JsonObject();
    result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
    result.add("properties", context.serialize(src, src.getClass()));
    return result;
  }

  @Override
  public Badge deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    // convert to json object
    JsonObject jsonObject = json.getAsJsonObject();
    // convert type to string for use
    String type = jsonObject.get("type").getAsString();
    // convert property to a json element
    JsonElement element = jsonObject.get("properties");
    // get the full name
    try {
      String fullName = typeOfT.getTypeName();
      String packageText = fullName.substring(0, fullName.lastIndexOf(".") + 1);
      return context.deserialize(element, Class.forName(packageText + type));
    } catch (ClassNotFoundException cnfe) {
      throw new JsonParseException("Unknown element type: " + type, cnfe);
    }
  }
}
