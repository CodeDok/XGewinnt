/**
 * @author Frank Mehler 
 * @version 1.0
 * 30.12.2020
 * Vorlage fuer Abgabeaufgabe 1 im SoSe21
 */

package pack;

import java.util.NoSuchElementException;

public interface Vermittler {

	/**
	 * Diese Methode meldet einen Partner fuer das Verteilungs-Netzwerk an
	 * 
	 * @param derPartner Das Angebot/ bzw. die Suche fuer den Vermittler
	 */
	void registrierePartner(Partner derPartner);

	/**
	 * Diese Methode empfaengt als input ein Partnerobjekt zur Weiterverteilung
	 * 
	 * @param derPartner ist ein Objekt mit der Information, was gesendet wurde
	 * @throws NoSuchElementException falls kein passendes Angebot gefunden wird
	 */
	void empfangen(Partner derPartner) throws NoSuchElementException;

}
