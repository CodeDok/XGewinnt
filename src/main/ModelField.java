/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Bildet eine Kachel auf dem Spielfeld ab. Mit der ModelPosition als Koordinate und Nummer.
 */
package main;

import constants.MyColor;

public class ModelField {
    private int componentIndex;
    private ModelPosition modelPosition;
    private MyColor myColor;

    /**
     * Initalisiert die Attribute
     *
     * @param componentIndex ModelPosition im Array als Liste
     * @param modelPosition ModelPosition im Array als 2 dimensionales Array
     * @param myColor Farbe der Kachel
     */
    public ModelField(int componentIndex, ModelPosition modelPosition, MyColor myColor) {
        this.componentIndex = componentIndex;
        this.modelPosition = modelPosition;
        this.myColor = myColor;
    }

    /**
     * Standardimplementierung von Equals.
     * Verglichen werden ComponentIndex und die ModelPosition
     *
     * @param o Das zu ueberpruefende Objekt
     * @return Gibt an ob die diese gleich sind
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelField modelField = (ModelField) o;

        if (componentIndex != modelField.componentIndex) return false;
        if (!modelPosition.equals(modelField.modelPosition)) return false;
        return myColor.equals(modelField.myColor);
    }

    /**
     * Standardimplementierung von der Hashcode Methode
     * @return Hashwert des Objekts
     */
    @Override
    public int hashCode() {
        int result = componentIndex;
        result = 31 * result + modelPosition.hashCode();
        result = 31 * result + myColor.hashCode();
        return result;
    }

    /**
     * Standardimplementierung von toString
     * @return Objekt als String
     */
    @Override
    public String toString() {
        return "ModelField{" +
                "componentIndex=" + componentIndex +
                ", modelPosition=" + modelPosition +
                ", color=" + myColor +
                "} \n";
    }

    public String getFieldName() {
        return modelPosition.getRow() + ";" + modelPosition.getColumn();
    }

    public int getComponentIndex() {
        return componentIndex;
    }

    public void setComponentIndex(int componentIndex) {
        this.componentIndex = componentIndex;
    }

    public ModelPosition getPosition() {
        return modelPosition;
    }

    public void setPosition(ModelPosition modelPosition) {
        this.modelPosition = modelPosition;
    }

    public MyColor getMyColor() {
        return myColor;
    }

    public void setMyColor(MyColor myColor) {
        this.myColor = myColor;
    }
}
