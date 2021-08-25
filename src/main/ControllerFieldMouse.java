/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Bildet die Aktionen ab, die bei den Mauseaktionen auf eine Kachel stattfinden.
 */
package main;

import exceptions.NoMatchException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControllerFieldMouse extends MouseAdapter {

    private ViewSwapGame viewSwapGame;
    private Model model;
    private ViewField source;
    private ViewField target;

    /**
     * Initialisiert die Variablen
     *
     * @param model Das Model mit den Daten
     * @param viewSwapGame Die View mit den Elementen
     */
    public ControllerFieldMouse(Model model, ViewSwapGame viewSwapGame) {
        this.model = model;
        this.viewSwapGame = viewSwapGame;
    }

    /**
     * Speichert die Kachel ab, auf die geklickt wurde.
     * @param e Objekt mit den Informationen zu der Mausaktion
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Casted die Componente in ein Label, damit man das Icon extrahieren kann,
        // welches dann in ein ViewField gecasted wird, um auf Attribute wie Farben usw zuzugreifen.
        source = (ViewField) (((JLabel) e.getComponent()).getIcon());
    }

    /**
     * Aktion nach dem das geklickte Feld per Drag and Drop irgendwo losgelassen wird.
     * Ueberpruft, ob der Zug legal ist und fuehrt die Aktion aus oder gibt eine Fehlermeldung
     * @param e Objekt mit den Informationen zu der Mausaktion
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Container contentPane = e.getComponent().getParent().getParent();
        try {
            Point mousePosition = e.getComponent().getParent().getMousePosition();
            // damit man auf die Attribute von JLabel zugreifen kann
            JLabel targetComponent = (JLabel) viewSwapGame.mapContainer.findComponentAt(mousePosition);
            // damit man auf die Attribute von ViewField zugreifen kann
            target = ((ViewField) targetComponent.getIcon());
            if (target.equals(source)) throw new NullPointerException();
            model.swapFields(target.getField(), source.getField());
        } catch (NullPointerException nullPointerException) {
            JOptionPane.showMessageDialog(contentPane, "Invalid Drop Location");
        } catch (NoMatchException noMatchException) {
            JOptionPane.showMessageDialog(contentPane, "Not a Match!");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(contentPane,
                    "Ein unbekannter Fehler ist aufgetreten. Was machen wir jetzt? ._.");
        } finally {
            target = source = null;
        }
    }
}
