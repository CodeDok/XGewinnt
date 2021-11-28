/**
 * @author Khanh An Do
 * @version 1.0
 * @since 26.03.2021
 *
 * Abstrakte Klasse mit der Grundfunktionalitaet der Partner
 */

package pack;

public abstract class Partner {

	private Vermittler vermittler;
	private String nr;
	private Standort standort;

	public Partner(Vermittler vermittler, Standort standort) {
		this.vermittler = vermittler;
		this.standort = standort;
	}

	// Wird von den ableitenden Klassen ueberschrieben
	public abstract void senden();

	/**
	 * Standardimplementation der equals-Methode nach dem Contract
	 *
	 * @param obj das zu ueberpruefende Objekt
	 * @return Resultat, ob es sich um das gleiche Objekt handelt
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;

		Partner other = (Partner) obj;
		return this.nr.equals(other.nr);
	}


	/**
	 * Gibt den Hashwert des Objektes zurueck
	 *
	 * @return Hashwert
	 */
	@Override
	public int hashCode() {
		StringBuilder hash = new StringBuilder();
		for(char bs: getNr().toCharArray()) {
			if (Character.isDigit(bs)) {
				hash.append(bs);
			}
		}
		return Integer.parseInt(hash.toString()) * 31 + Character.getNumericValue(getNr().charAt(0)) * 31;
	}

	/**
	 * Gibt die Nr und den Standort zurueck
	 *
	 * @return String im Format "nr.standort"
	 */
	@Override
	public String toString() {
		return getNr() + "." + getStandort();
	}

	/**
	 * Gibt den numerischen Teil von dem Attribut "nr" zurueck
	 *
	 * @return numerischer Teil des Attributs "nr"
	 */
	public double getNrWithoutChar() {
		return Double.parseDouble(nr.replaceAll("[^0-9]", ""));
	}



	public Vermittler getVermittler() {
		return vermittler;
	}

	public void setVermittler(Vermittler vermittler) {
		this.vermittler = vermittler;
	}

	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public Standort getStandort() {
		return standort;
	}

	public void setStandort(Standort standort) {
		this.standort = standort;
	}

}
