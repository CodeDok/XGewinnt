/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Verwaltet und verarbeitet die Daten fuer die KonkretenVermittler Klasse
 */


package pack;

import java.util.*;

public class Daten {
    private final HashMap<Standort, LinkedList<PartnerAngebot>> angebote = new HashMap<>();
    private final HashMap<Standort, LinkedHashMap<Double, PartnerSuche>> suche = new HashMap<>();

    /**
     * Erstellt fuer jeden Standort ein Key-Value Paar in der entsprechenden HashMap
     */
    public Daten() {
        for(Standort pos : Standort.values()) {
            angebote.put(pos, new LinkedList<>());
            suche.put(pos, new LinkedHashMap<>());
        }
    }

    public HashMap<Standort, LinkedList<PartnerAngebot>> getAngebote() {
        return angebote;
    }

    public HashMap<Standort, LinkedHashMap<Double, PartnerSuche>> getSuche() {
        return suche;
    }

    /**
     * Gibt alle Angebote an allen Standorten zurueck
     *
     * @return ArrayListe mit allen Angeboten
     */
    public ArrayList<PartnerAngebot> getAlleAngebote() {
        ArrayList<PartnerAngebot> alle = new ArrayList<>();
        angebote.forEach((key, value) -> {
            alle.addAll(value);
        });
        return alle;
    }

    /**
     * Gibt alle Suchen an allen Standorten zurueck
     *
     * @return ArrayListe mit allen Angeboten
     */
    public ArrayList<PartnerSuche> getAlleSuchen() {
        ArrayList<PartnerSuche> alle = new ArrayList<>();
        suche.forEach((key, value) -> {
            alle.addAll(value.values());
        });
        return alle;
    }

    /**
     * Fuegt eine Suche der Liste hinzu
     * Key -> numerischer Teil des Attributs Nr
     * Value -> PartnerSuche
     *
     * @param partnerSuche zu speichernde Suche
     */
    public void addSuche(PartnerSuche partnerSuche) {
        suche.get(partnerSuche.getStandort()).put(partnerSuche.getNrWithoutChar(),partnerSuche);
    }

    /**
     * Sucht fuer die uebergebene Suche ein passendes Angebot heraus.
     *
     * @param partnerSuche Suche fuer das ein Angebot gesucht werden soll
     * @return	Gibt das gefundene Angebot zurueck
     * @throws NoSuchElementException	Wenn kein passendes Angebot gefunden wurde
     */
    public PartnerAngebot sucheAngebote(PartnerSuche partnerSuche) throws NoSuchElementException {
        LinkedList<PartnerAngebot> angeboteList = angebote.get(partnerSuche.getStandort());
        if(angebote.isEmpty()) throw new NoSuchElementException("Kein passendes Angebot vorhanden fuer: " + partnerSuche);
        return angeboteList.get(0);
    }

    /**
     * Entfernt die uebergebene Suche aus der Liste
     * @param partnerSuche Die zu loeschende Suche
     */
    public void removeSuche(PartnerSuche partnerSuche) {
        suche.get(partnerSuche.getStandort()).remove(partnerSuche.getNrWithoutChar());
    }

    /**
     * Entfernt das uebergebene Angebot aus der Liste
     * @param partnerAngebot Das zu loeschende Angebot
     */
    public void removeAngebot(PartnerAngebot partnerAngebot) {
        angebote.get(partnerAngebot.getStandort()).remove(partnerAngebot);
    }

    /**
     * Fuegt das uebergebene Angebot der Liste hinzu
     * @param partnerAngebot Das hinzu zu fuegende Angebot
     */
    public void addAngebot(PartnerAngebot partnerAngebot) {
        angebote.get(partnerAngebot.getStandort()).add(partnerAngebot);
    }

    /**
     * Gibt die Anzahl der Transportmittel an einem Standort zurueck
     * @param standort  Die gewuenschte Standort
     * @return
     */
    public int getFahrzeuganzahlStandort(Standort standort) {
        return angebote.get(standort).size();
    }
}
