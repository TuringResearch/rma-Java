package br.pro.turing.rma.core.service;

import br.pro.turing.rma.core.utils.Constants;
import com.google.gson.*;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * This class provide some common methods to use involving Json language.
 */
public class JsonService {

    /** Instance of this singleton. */
    private static JsonService instance;

    /** Gson instance. */
    private Gson gson;

    /**
     * Constructor.
     */
    private JsonService() {
        this.gson = new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create();
    }

    /**
     * Get the unique instance of this singleton.
     *
     * @return A unique Json service instance.
     */
    public static JsonService getInstance() {
        if (JsonService.instance == null) {
            JsonService.instance = new JsonService();
        }
        return JsonService.instance;
    }

    /**
     * Check if Json is a specific object.
     *
     * @param jsonMessage      Json message.
     * @param desiredClassName Desired class name to check.
     * @return true, if Json message represents the desired Object.
     */
    public boolean jasonIsObject(String jsonMessage, String desiredClassName) {
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject = parser.parse(jsonMessage).getAsJsonObject();
            return validateJsonObject(jsonObject, desiredClassName);
        } catch (IllegalStateException e) {
            JsonArray jsonArray = parser.parse(jsonMessage).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                if (!validateJsonObject(jsonElement.getAsJsonObject(), desiredClassName)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean validateJsonObject(JsonObject jsonObject, String desiredClassName) {
        final Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        return entries.stream().anyMatch(
                stringJsonElementEntry -> stringJsonElementEntry.getKey().equals("className") && stringJsonElementEntry
                        .getValue().getAsString().equals(desiredClassName));
    }

    /**
     * Convert message to object.
     *
     * @param jsonMessage Json message.
     * @param classOfT    Class to convert.
     * @param <T>         Type of object of the conversion.
     * @return Converted Object.
     */
    public <T> T fromJson(String jsonMessage, Class<T> classOfT) {
        return this.gson.fromJson(jsonMessage, classOfT);
    }

    /**
     * Convert message to object.
     *
     * @param jsonReader Json reader.
     * @param classOfT   Class to convert.
     * @param <T>        Type of object of the conversion.
     * @return Converted Object.
     */
    public <T> T fromJson(Reader jsonReader, Class<T> classOfT) {
        return this.gson.fromJson(jsonReader, classOfT);
    }

    /**
     * Convert message to object.
     *
     * @param jsonMessage Json message.
     * @param typeOfT     Type of class to convert.
     * @param <T>         Type of object of the conversion.
     * @return Converted Object.
     */
    public <T> T fromJson(String jsonMessage, Type typeOfT) {
        return this.gson.fromJson(jsonMessage, typeOfT);
    }

    /**
     * Convert Object to Json string.
     *
     * @param src Object.
     * @return Json string.
     */
    public String toJson(Object src) {
        return this.gson.toJson(src);
    }
}
