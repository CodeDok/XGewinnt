/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Die Klasse bildet die Zentrale Kommunikationsschnittstelle fuer die Partner
 */

package pack;

import java.util.*;

public class KonkreterVermittler implements Vermittler {
	private final Daten daten;

	/**
	 * Initialisiert das KonkreterVermittler Objekt mit einem Objekt der Daten Klasse
	 */
	public KonkreterVermittler() {
		daten = new Daten();
	}


	/**
	 * Gibt die Suchen, Angebote und die Anzahl der Transportmittel als einen formatierten String zurueck
	 *
	 * @param header Ein String der ueber die Informationen gesetzt wird
	 */
	public String getInformationen(String header) {
		String suchen = UtilityArrayList.GET_AS_STRING(daten.getAlleSuchen());
		String angebote = UtilityArrayList.GET_AS_STRING(daten.getAlleAngebote());
		String[] data = {
				suchen.equals("") ? "leer" : suchen,
				angebote.equals("") ? "leer" : angebote,
				getFahrzeuganzahlStandorte().toString()
		};
		String output = header + "\nAlleSuchen: %s \nAlleAngebote: %s \nAnzahl Transportmittel an den Standorten: %s \n"
				.formatted(data);
		return output;
	}

	/**
	 * Gibt die Angebote in einer Auflistung mit einer Kommatrennung zurueck
	 *
	 * @return Auflistung der Angebote getrennt durch ein Komma
	 */
	public String getInformationenAngebote() {
		return UtilityArrayList.GET_AS_STRING(daten.getAlleAngebote());
	}

	/**
	 * Gibt die Anzahl der Transportmittel an den jeweiligen Standorten zurueck
	 *
	 * @return Key -> Standort, Value -> Anzahl der Transportmittel
	 */
	public Map<Standort, Integer> getFahrzeuganzahlStandorte() {
		Map<Standort,Integer> liste = new HashMap<>();
		List<Standort> standorts = Arrays.asList(pack.Standort.values());
		Collections.reverse(standorts);
		for (Standort standort : standorts) {
			if(daten.getFahrzeuganzahlStandort(standort) > 0) {
				liste.put(standort, daten.getFahrzeuganzahlStandort(standort));
			}
		}
		return liste;
	}



	/**
	 * Fuegt den Partner in die entsprechende Liste hinzu
	 *
	 * @param derPartner Das Angebot/ bzw. die Suche fuer den Vermittler
	 */
	@Override
	public void registrierePartner(Partner derPartner) {
		if (derPartner instanceof PartnerAngebot) {
			daten.addAngebot((PartnerAngebot) derPartner);
		} else if (derPartner instanceof PartnerSuche) {
			daten.addSuche((PartnerSuche) derPartner);
		}
	}

	/**
	 * Sucht fuer die Suche ein Angebot heraus und aktualisiert anschliessend das Angebot.
	 * Die Suche wird immer geloescht.
	 *
	 * @param derPartner ist ein Objekt mit der Information, was gesendet wurde
	 * @throws NoSuchElementException wird geworfen, wenn kein Angebot gefunden wurde
	 */
	public void empfangen(Partner derPartner) throws NoSuchElementException {
		if(derPartner instanceof PartnerSuche) {
			try {
				PartnerAngebot partnerAngebot = daten.sucheAngebote((PartnerSuche) derPartner);
				updateAngebot(partnerAngebot);
			} catch(NoSuchElementException e) {
				throw new NoSuchElementException(e.getMessage());
			} finally {
				daten.removeSuche((PartnerSuche) derPartner);
			}
		}
	}



	/**
	 * Aktualisiert den Standort bzw. das Angebot
	 *
	 * @param partnerAngebot Das zu aktualisierende Angebot
	 */
	private void updateAngebot(PartnerAngebot partnerAngebot) {
		Standort nextStandort = getNextStandort(partnerAngebot.getStandort());
		PartnerAngebot refreshedAngebot = new PartnerAngebot(partnerAngebot);
		refreshedAngebot.setStandort(nextStandort);
		daten.addAngebot(refreshedAngebot);
		daten.removeAngebot(partnerAngebot);
	}


	/**
	 * Gibt fuer die uebergebene Standort die naechsten Standort an
	 * @param standort aktuelle Standort
	 * @return naechste Standort
	 */
	private Standort getNextStandort(Standort standort) {
		return switch (standort) {
			case A -> pack.Standort.B;
			case B -> pack.Standort.C;
			case C -> pack.Standort.A;
			default -> pack.Standort.A;
		};
	}
}
