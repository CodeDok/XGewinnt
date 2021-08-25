/**
 * @author Khanh An Do
 * @version 1.1
 * @since 02.05.2021
 *
 * Stellt programmspezifische Funktionen zu Listen bereit
 */

package utility;

import java.util.List;

public class UtilityList {

    private UtilityList() {
    }

    /**
     * Gibt die Listeninhalte mit Kommata zurueck
     *
     * @param list List mit den Inhalten
     * @return alle Elemente als String
     */
    public static String GET_AS_STRING(List<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i) + (i < list.size() - 1 ? "\n" : ""));
        }
        return (!stringBuilder.toString().equals("")) ? stringBuilder.toString() : "leer";
    }

    /**
     * Entfernt die letzten x-Elemente einer List
     * @param list Liste
     * @param elements Anzahl der zu loeschenden Elemente
     */
    public static void REMOVE_TRAILING_ELEMENTS(List<?> list, int elements) {
        for (int i = 0; i < elements; i++) {
            list.remove(list.size() - 1);
        }
    }
}
