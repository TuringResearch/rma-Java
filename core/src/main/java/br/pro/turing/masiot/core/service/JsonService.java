package br.pro.turing.masiot.core.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.Reader;
import java.lang.reflect.Type;

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
        this.gson = new Gson();
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
     * @param jsonMessage  Json message.
     * @param desiredClass Desired class to check.
     * @return true, if Json message represents the desired Object.
     */
    public boolean jasonIsObject(String jsonMessage, Class<?> desiredClass) {
        try {
            return this.gson.fromJson(jsonMessage, desiredClass) != null;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    /**
     * Check if Json is a specific object. This method must be used when you want to check if the Json message is a
     * specific list.
     *
     * @param jsonMessage  Json message.
     * @param desiredClass Desired class type to check.
     * @return true, if Json message represents the desired Object.
     */
    public boolean jasonIsObject(String jsonMessage, Type desiredClass) {
        try {
            return this.gson.fromJson(jsonMessage, desiredClass) != null;
        } catch (JsonSyntaxException e) {
            return false;
        }
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
