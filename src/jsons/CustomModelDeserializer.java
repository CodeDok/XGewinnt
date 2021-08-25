/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Eigener Deserializer, damit die gwuenschten Jsons eingelesen werden koennen.
 * Quelle: Github Jackson -> Baeldung Tutorial
 *
 * Github: https://github.com/FasterXML/jackson
 * Tutorial: https://www.baeldung.com/jackson
 */
package jsons;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import constants.MyColor;
import exceptions.ColorNotAvailableException;
import exceptions.FieldSizeException;
import main.*;

import java.io.IOException;

public class CustomModelDeserializer extends StdDeserializer<Model> {

    public CustomModelDeserializer() {
        this(null);
    }

    public CustomModelDeserializer(Class<?> t) {
        super(t);
    }

    /**
     * Deserialisiert ein Model
     * @param jsonParser Basisklasse mit den Standardfunktionen fuer die JSON Auslesung
     * @param deserializationContext Configurationseinstellungen
     * @return Model aus der Json
     * @throws IOException Fehler beim Einlesen oder Auslesen von Json Elementen
     * @throws FieldSizeException Fehler, wenn die max im Json nicht mit dem max im Model uebereinstimmt
     * @throws ColorNotAvailableException Fehler, wenn im Json eine Farbe definiert ist, welche nicht im Spiel existiert.
     */
    @Override
    public Model deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, FieldSizeException, ColorNotAvailableException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode maxNode = node.get("max");
        JsonNode feldNode = node.get("feld");
        JsonNode scoreNode = node.get("spielstand");

        int max = maxNode.asInt();
        if (max != Model.getMAX()) throw new FieldSizeException("Problem mit der Feldgroesse!");

        ModelField[][] modelFields = new ModelField[max][max];
        ObjectMapper objectMapper = new ObjectMapper();

        for (int row = 0; row < max; row++) {
            for (int column = 0; column < max; column++) {
                ModelPosition modelPosition = new ModelPosition(row, column);
                MyColor myColor;
                try {
                    myColor = objectMapper.treeToValue(feldNode.get(row).get(column), MyColor.class);
                } catch (Exception e) {
                    throw new ColorNotAvailableException("Problem mit den Farben!");
                }
                modelFields[row][column] = new ModelField(row * max + column, modelPosition, myColor);
            }
        }
        return new Model(modelFields, scoreNode.asInt());
    }
}
