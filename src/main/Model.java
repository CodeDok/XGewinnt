/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Stellt das Spielfeld und die Spiellogik wieder.
 * Ein Objekt ist observeable.
 */
package main;

import constants.MyColor;
import exceptions.NoMatchException;
import utility.UtilityList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;


public class Model implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final PropertyChangeSupport changeSupport;
    private static final int MAX = 4;
    private static final int MATCH_SIZE = 3;
    private ModelField[][] feld;
    private static final MyColor[] COLORS = MyColor.values();;
    private int spielstand;

    /**
     * Initialisiert ein Model mit einem Feld mit der groesse Max und einem Seed.
     *
     * @param seed Seed fuer die Erstellung eines Feldes
     */
    public Model(int seed) {
        this.spielstand = 0;
        this.feld = new ModelField[MAX][MAX];
        changeSupport = new PropertyChangeSupport(this);
        initFeld(seed);
    }

    /**
     * Initialisiert ein Model mit einem vorhandenen Feld und einem vorhandenen Spielfeld.
     * Enthaltene Matches werden direkt aufgeloest.
     *
     * @param feld vorhandenes Spielfeld
     * @param spielstand aktueller Spielstand
     */
    public Model(ModelField[][] feld, int spielstand) {
        this.spielstand = spielstand;
        this.feld = feld;
        resolveMatches();
        changeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Initialisiert ein Feld ohne Matches mit einem Seed. Bestehend aus ModelField Objekten.
     *
     * @param seed Seed mit dem das Feld initalisiert werden soll
     */
    private void initFeld(int seed) {
        Random random = new Random(seed);
        for (int row = 0; row < feld.length; row++) {
            for (int column = 0; column < feld.length; column++) {
                ModelPosition modelPosition = new ModelPosition(row, column);
                MyColor color = COLORS[random.nextInt(COLORS.length)];
                feld[row][column] = new ModelField(row * feld.length + column, modelPosition, color);
            }
        }
        resolveMatches();
    }

    /**
     * Ueberprueft das ganze Spielfeld auf Matches
     * @return gefundene Matches
     */
    public LinkedList<ModelField> checkWholeMap() {
        LinkedList<ModelField> matches = new LinkedList<>();
        for (int i = 0; i < feld.length; i++) {
            matches.addAll(checkRowAndColumn(i, i));
        }
        return matches;
    }

    /**
     * Ueberprueft eine Zeile und eine Spalte auf Matches
     * @param rowIndex Zeilenindex
     * @param columnIndex Spaltenindex
     * @return gefundene Matches
     */
    public LinkedList<ModelField> checkRowAndColumn(int rowIndex, int columnIndex) {
        LinkedList<ModelField> matches = new LinkedList<>();
        matches.addAll(checkColumn(columnIndex));
        matches.addAll(checkRow(rowIndex));
        return matches.stream().distinct().collect(Collectors.toCollection(LinkedList<ModelField>::new));
    }

    /**
     * Gibt eine Spalte vom Spielfeld zurueck
     * @param columnIndex Spaltenindex
     * @return Spalte
     */
    private ModelField[] getColumn(int columnIndex) {
        return Arrays.stream(feld).map(field -> field[columnIndex])
                .toArray(value -> new ModelField[feld.length]);
    }

    /**
     * Ueberprueft eine Spalte auf Matches
     * @param columnIndex Spaltenindex
     * @return Matches in der Spalte
     */
    private LinkedList<ModelField> checkColumn(int columnIndex) {
        ModelField[] modelFieldColumn = getColumn(columnIndex);
        return checkFields(modelFieldColumn);
    }

    /**
     * Ueberprueft eine Zeile auf Matches
     * @param rowIndex Zeilenindex
     * @return Matches in der Zeile
     */
    private LinkedList<ModelField> checkRow(int rowIndex) {
        ModelField[] modelFieldRow = feld[rowIndex];
        return checkFields(modelFieldRow);
    }

    /**
     * Ueberprueft eine "Linie" auf Matches
     * @param modelFields Linie bzw. Spalte/Zeile
     * @return Matches in der Linie
     */
    private LinkedList<ModelField> checkFields(ModelField[] modelFields) {
        LinkedList<ModelField> matches = new LinkedList<>();
        int sameColorCount = 1;
        for (int i = 0; i < modelFields.length - 1; i++) {
            sameColorCount += 1;
            if (!matches.contains(modelFields[i])) matches.add(modelFields[i]);
            matches.add(modelFields[i + 1]);
            if (modelFields[i].getMyColor() != modelFields[i + 1].getMyColor() || (i == (modelFields.length - 2) && sameColorCount < MATCH_SIZE)) {
                if (sameColorCount > MATCH_SIZE && i == (modelFields.length - 2)) sameColorCount = 1;
                if (sameColorCount <= MATCH_SIZE) UtilityList.REMOVE_TRAILING_ELEMENTS(matches, sameColorCount);
                sameColorCount = 1;
            }
        }
        return matches;
    }

    /**
     * Ein Spielzug in Form von dem Verschieben von Kacheln.
     * @param target Zielkachel
     * @param source Ursprungskachel
     * @throws NoMatchException Wenn der Spielzug kein Match verursacht
     * @throws NullPointerException Wenn die Zielkachen nicht im eins ueber/unter oder
     *                              links/rechts neben Ursprungsfeld liegt
     */
    public void swapFields(ModelField target, ModelField source) throws NoMatchException, NullPointerException {
        if (!isInRange(target, source)) throw new NullPointerException("Invalid Locations");
        ModelPosition targetModelPosition = target.getPosition();
        ModelPosition sourceModelPosition = source.getPosition();
        switchFields(targetModelPosition, sourceModelPosition);
        LinkedList<ModelField> matches = checkWholeMap();
        if (matches.size() == 0) {
            switchFields(targetModelPosition, sourceModelPosition);
            throw new NoMatchException();
        }
        addScore(resolveMatches());
        changeSupport.firePropertyChange("model", null, this);
    }

    /**
     * Entfernt Matches bis keine mehr existieren und gibt die Punkte zurueck
     * @return durch Matches erhaltene Punkte
     */
    private int resolveMatches() {
        LinkedList<ModelField> matches;
        int accumulatedPoints = 0;
        do {
            matches = checkWholeMap();
            if (matches.size() > 0) {
                accumulatedPoints += matches.size();
                removeMatches(matches);
            }
        } while (matches.size() != 0);
        return accumulatedPoints;
    }

    /**
     * Entfernt Matches, und generiert neue Zufallskachel
     * @param matches
     */
    private void removeMatches(LinkedList<ModelField> matches) {
        for (int column = 0; column < feld.length; column++) {
            for (int row = 0; row < feld.length; row++) {
                if (matches.contains(feld[row][column])) {
                    feld[row][column].setMyColor(null);
                }
            }
            dropColumn(column);
        }
    }

    /**
     * Fuellt die leeren Kacheln mit Kacheln oben drueber auf. (siehe Folien zum Spielbetrieb)
     * @param index Spaltenindex
     */
    private void dropColumn(int index) {
        ModelField[] column = getColumn(index);
        for (int i = column.length - 1; i > 0; i--) {
            if (column[i].getMyColor() == null) {
                Integer nextField = null;
                for (int next = i; next >= 0 && nextField == null; next--) {
                    if (column[next].getMyColor() != null) nextField = next;
                }
                if (nextField == null) break;
                column[i].setMyColor(column[nextField].getMyColor());
                column[nextField].setMyColor(null);

            }
        }
        fillGapsInColumn(index);
    }

    /**
     * Fuellt die leeren Kacheln mit zufaelligen Farben auf
     * @param index Spaltenindex
     */
    private void fillGapsInColumn(int index) {
        ModelField[] column = getColumn(index);
        for (ModelField modelField : column) {
            if (modelField.getMyColor() == null) {
                modelField.setMyColor(COLORS[(int) (Math.random() * COLORS.length)]);
            }
        }
    }

    /**
     * Tauscht die Kacheln aus
     * @param target Zielkachel
     * @param source Ursprungskachel
     */
    private void switchFields(ModelPosition target, ModelPosition source) {
        ModelField sourceModelField = feld[source.getRow()][source.getColumn()];
        ModelField targetModelField = feld[target.getRow()][target.getColumn()];
        MyColor tmpSourceColor = sourceModelField.getMyColor();
        sourceModelField.setMyColor(targetModelField.getMyColor());
        targetModelField.setMyColor(tmpSourceColor);
    }

    /**
     * Ueberprueft, ob die Ziel- und Ursprungskachel legal sind.
     * @param target Zielkachel
     * @param source Urpsurngskachel
     * @return ob die Ziel- und Urpsungskachel legal sind
     */
    private boolean isInRange(ModelField target, ModelField source) {
        ModelPosition targetModelPosition = target.getPosition();
        ModelPosition sourceModelPosition = source.getPosition();
        if (targetModelPosition.getColumn() == sourceModelPosition.getColumn()) {
            if (targetModelPosition.getColumn() >= sourceModelPosition.getColumn() - 1) return true;
            if (targetModelPosition.getColumn() <= sourceModelPosition.getColumn() + 1) return true;
        }
        if (targetModelPosition.getRow() == sourceModelPosition.getRow()) {
            if (targetModelPosition.getRow() >= sourceModelPosition.getRow() - 1) return true;
            return targetModelPosition.getRow() <= sourceModelPosition.getRow() + 1;
        }
        return false;
    }

    public ModelField[][] getFeld() {
        return feld;
    }

    public static int getMAX() {
        return MAX;
    }

    public int getSpielstand() {
        return spielstand;
    }

    public void addScore(int points) {
        this.spielstand += points;
    }

    public LinkedList<ModelField> getGameMapAsList() {
        return Arrays.stream(feld).flatMap(Arrays::stream).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Fuegt einen Observer hinzu
     * @param propertyChangeListener Observer
     */
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        changeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Entfernt einen Observer
     * @param propertyChangeListener Observer
     */
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        changeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    /**
     * Standardimplementierung von toString
     * @return Objekt als String
     */
    @Override
    public String toString() {
        return "Model{" +
                "changeSupport=" + changeSupport +
                ", feld=" + Arrays.toString(feld) +
                ", COLORS=" + Arrays.toString(COLORS) +
                ", spielstand=" + spielstand +
                '}';
    }

    public void setFeld(ModelField[][] feld) {
        this.feld = feld;
    }


    public void setSpielstand(int spielstand) {
        this.spielstand = spielstand;
    }
}
