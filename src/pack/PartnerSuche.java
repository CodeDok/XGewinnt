/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Eine Suche fuer ein Transportmittel an einem bestimmten Standort
 */

package pack;

public class PartnerSuche extends Partner {

	private static double ZAEHLER = 1;

	/**
	 * Instanziiert ein Objekt der Klasse mit einem statischen Zaehler
	 *
	 * @param v zustaendiger Vermittler
	 * @param p zu suchender Standort
	 */
	public PartnerSuche(Vermittler v, Standort p) {
		super(v, p);
		setNr("s" + ZAEHLER);
		ZAEHLER++;
	}

	/**
	 * Teilt dem Vermittler mit, dass diese Suche ausgefuehrt werden soll
	 */
	public void senden() {
		try {
			this.getVermittler().empfangen(this);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
