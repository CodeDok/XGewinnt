/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Eigener Serializer, damit die Ausgabe der gewuenschten Json Ausgabe entspricht
 */
package jsons;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import main.Model;
import main.ModelField;

import java.io.IOException;

public class CustomModelSerializer extends StdSerializer<Model> {

    public CustomModelSerializer() {
        this(null);
    }

    protected CustomModelSerializer(Class<Model> t) {
        super(t);
    }

    /**
     * Serialisiert das Model
     * @param model Model, welches in ein Json umgewandelt werden soll
     * @param jsonGenerator Basisklasse mit den Standardfunktionen fuer die JSON Erstellung
     * @param serializerProvider Stellt Serializer fuer weitere oder  speziell serializierte Objekte zur Verfuegung
     * @throws IOException Fehler beim Einlesen oder Auslesen von Json Elementen
     */
    @Override
    public void serialize(Model model, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ModelField[][] modelFields = model.getFeld();

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("max", Model.getMAX());
        jsonGenerator.writeArrayFieldStart("feld");
        for (int column = 0; column < modelFields.length; column++) {
            jsonGenerator.writeStartArray();
            for (int row = 0; row < modelFields[column].length; row++) {
                jsonGenerator.writeObject(modelFields[column][row].getMyColor());
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeNumberField("spielstand", model.getSpielstand());
        jsonGenerator.writeEndObject();
    }
}
