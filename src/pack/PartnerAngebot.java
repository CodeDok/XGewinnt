/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Stellt ein Angebot in Form eines Transportmittels an einen Standort dar
 */

package pack;

public class PartnerAngebot extends Partner {

	private static double ZAEHLER = 1;

	/**
	 * Instanziiert ein Objekt der Klasse mit einem statischen Zaehler
	 *
	 * @param v zustaendiger Vermittler
	 * @param p aktueller Standort
	 */
	public PartnerAngebot(Vermittler v, Standort p) {
		super(v, p);
		setNr("a" + ZAEHLER);
		ZAEHLER++;
	}

	/**
	 * Copy-Konstruktor (Shallow Copy - Da der Vermittler gleich bleibt)
	 *
	 * @param pa Das zu kopierende Angebot
	 */
	public PartnerAngebot(PartnerAngebot pa) {
		super(pa.getVermittler(),pa.getStandort());
		setNr(pa.getNr());
	}

	/**
	 * Angebote sind nicht in der Lage ein senden Aufruf an den Vermittler zu stellen
	 *
	 * @throws UnavailableMethodException Fehler wenn es versucht wird
	 */
	@Override
	public void senden() throws UnavailableMethodException{
		throw new UnavailableMethodException("Methode nicht verfuegbar fuer Angebote!");
	}

}
