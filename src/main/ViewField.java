/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Eine Kachel als ein UI Element
 */
package main;

import javax.swing.*;
import java.awt.*;

public class ViewField implements Icon {

    private final ModelField modelField;

    /**
     * Initalisiert eine UI Kachel mit einer Datenkachel
     * @param modelField
     */
    public ViewField(ModelField modelField) {
        this.modelField = modelField;
    }

    /**
     * Zeichnet ein farbiges Viereck.
     * @param c Component, von welchem zusaetzliche Informationen genommen werden kann
     * @param g Grafikkontext
     * @param x x-Koordinate von der linken oberen Ecke des Icons
     * @param y y-Koordinate von der linken oberen Ecke des Icons
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        int breite = c.getWidth();
        int hoehe = c.getHeight();
        Color meineFarbe = modelField.getMyColor().getCOLOR();
        g2.setColor(meineFarbe);
        g2.fillRect(0, 0, breite, hoehe);
    }

    @Override
    public int getIconWidth() {
        return -1;
    }

    @Override
    public int getIconHeight() {
        return -1;
    }

    public ModelField getField() {
        return modelField;
    }
}
