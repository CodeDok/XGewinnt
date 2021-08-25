/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Eine Hilfsklasse um von einem Model ein Json zu erhalten oder ein Json in ein Model umzuwandeln
 */
package jsons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import exceptions.ColorNotAvailableException;
import exceptions.FieldSizeException;
import main.Model;

import java.io.File;
import java.io.IOException;

public class ModelJson {

    private ObjectMapper objectMapper;

    /**
     * Initalisiert ein Objectmapper mit dem dazugehoerigen Serializer und Deserializer
     */
    public ModelJson() {
        objectMapper = new ObjectMapper();
        SimpleModule modelSerializer = new SimpleModule("CustomModelSerializer", new Version(1, 0, 0, null, null, null));
        modelSerializer.addSerializer(Model.class, new CustomModelSerializer());
        objectMapper.registerModule(modelSerializer);
        SimpleModule modelDeserializer = new SimpleModule("CustomModelDeserializer", new Version(1, 0, 0, null, null, null));
        modelDeserializer.addDeserializer(Model.class, new CustomModelDeserializer());
        objectMapper.registerModule(modelDeserializer);
    }

    /**
     * Wandelt ein Model in ein Json um
     * @param model das umzuwandelnde Model
     * @return Json vom Model
     * @throws JsonProcessingException Fehler beim Umwandeln in ein Json
     */
    public String toJson(Model model) throws JsonProcessingException {
        return objectMapper.writeValueAsString(model);
    }

    /**
     * Generiert von einem Json ein Model
     * @param json Json als Datei
     * @return Mdoel aus Json
     * @throws IOException Fehler beim Lesen der Json
     * @throws ColorNotAvailableException Fehler, wenn die Farbe nicht im Programm existiert
     * @throws FieldSizeException Fehler, wenn max von der Json nicht dem max vom Model entspricht
     */
    public Model fromJson(File json) throws IOException, ColorNotAvailableException, FieldSizeException {
        return objectMapper.readValue(json, Model.class);
    }
}
