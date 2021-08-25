/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Stellt das UI dar mit dem Spielfeld, den Punkten und einem Lade Json Knopf und Beenden Knopf
 */
package main;

import jsons.ModelJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class ViewSwapGame extends JFrame implements PropertyChangeListener {

    Model model;
    Container window, mapContainer, footerContainer;
    JLabel score;

    /**
     * Initialisiert die Komponenten und konfiguriert das JFrame
     * @param model
     */
    public ViewSwapGame(Model model) {
        super("Swap");
        this.model = model;
        int gridSize = Model.getMAX();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(75 * gridSize, 75 * gridSize);

        this.window = this.getContentPane();
        this.window.setLayout(new BorderLayout());

        initScore(model);
        initMap();
        initFooter();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Initalisert die Punktestandanzeige
     * @param model
     */
    private void initScore(Model model) {
        this.score = new JLabel();
        this.score.setText("Punkte: " + model.getSpielstand());
        this.window.add(score, BorderLayout.NORTH);
    }

    /**
     * Initialisiert das Spielfeld
     */
    private void initMap() {
        mapContainer = new Container();
        mapContainer.setName("MAP");
        int gameMapSize = Model.getMAX();
        mapContainer.setLayout(new GridLayout(gameMapSize, gameMapSize, 1, 1));
        for (ModelField modelField : model.getGameMapAsList()) {
            JLabel fieldItem = new JLabel(new ViewField(modelField));
            fieldItem.addMouseListener(new ControllerFieldMouse(model, this));
            mapContainer.add(fieldItem, modelField.getComponentIndex());
        }
        this.window.add(mapContainer, BorderLayout.CENTER);
    }

    /**
     * Initialisiert den Footer mit dem LadeJson und dem Quit Knopf
     */
    private void initFooter() {
        footerContainer = new Container();
        footerContainer.setLayout(new BorderLayout());
        JButton loadJson = new JButton("Lade JSON...");
        JButton exitApplication = new JButton("Beenden");
        exitApplication.addActionListener((ActionEvent e) -> System.exit(0));
        loadJson.addActionListener((ActionEvent e) -> {
            ModelJson modelJson = new ModelJson();
            JFileChooser fc = new JFileChooser();
            int returnValue = fc.showOpenDialog(footerContainer);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fc.getSelectedFile();
                    this.model = modelJson.fromJson(file);
                    model.addPropertyChangeListener(this);
                    refreshGame();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, "Fehler mit der Datei!");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "You cancelled it! (O_o)");
            }
        });
        footerContainer.add(loadJson, BorderLayout.WEST);
        footerContainer.add(exitApplication, BorderLayout.EAST);
        window.add(footerContainer, BorderLayout.SOUTH);
    }

    /**
     * Aktualisiert den Punktestand und die Map
     */
    private void refreshGame() {
        this.score.setText("Punkte: " + model.getSpielstand());
        window.remove(mapContainer);
        this.initMap();
        this.window.revalidate();
    }

    /**
     * Wird ausgefuehrt, wenn das Model es sagt.
     * Aktualisiert dann das Spielfeld.
     * @param evt zusaetzliche Informationen ueber die aenderung.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("model")) {
            refreshGame();
        }
    }
}
