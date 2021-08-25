/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Initialisiert die View und das Model mit dem gewuenschten Seed.
 */
package main;

public class ControllerSwapGame {

    private static final int SEED = 13;
    private Model model;
    private ViewSwapGame viewSwapGame;

    /**
     * Initalisiert das Model und die View und uebergibt der View das Model.
     * Fuegt die View zu dem PropertyChangeListeners vom Model hinzu.
     */
    public ControllerSwapGame() {
        model = new Model(SEED);
        viewSwapGame = new ViewSwapGame(model);
        model.addPropertyChangeListener(viewSwapGame);
    }

    /**
     * Initialisiert ein Controller
     * @param args Konfigurationen
     */
    public static void main(String[] args) {
        new ControllerSwapGame();
    }
}
