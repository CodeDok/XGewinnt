/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Stellt programmspezifische Funktionen zu Collections bereit
 */

package pack;

import java.util.ArrayList;

public class UtilityArrayList {

    private UtilityArrayList() { }


    /**
     * Gibt die Arraylisteninhalte mit Kommata zurueck
     *
     * @param arrayList ArrayList mit den Inhalten
     * @return alle Elemente als String
     */
    public static String GET_AS_STRING(ArrayList arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < arrayList.size(); i++) {
            stringBuilder.append(arrayList.get(i) + (i < arrayList.size() - 1 ? ", " : ""));
        }
        return stringBuilder.toString();
    }
}
