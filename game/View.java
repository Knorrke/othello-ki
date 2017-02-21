package game;

public class View {

	private Controller controller;
	
	public View(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Gibt das im Controller zugängliche Spiel und den ziehenden Spieler auf der Konsole aus.
	 */
	public void spielAusgeben() {
		int[][] spielfeld = controller.getSpielfeld();
		System.out.println("   0 1 2 3 4 5 6 7");
		for (int y=0; y<8; y++) {
		    System.out.print(y + " ");
			for (int x=0; x<8; x++) {
				System.out.print("|" + getSpielzeichen(spielfeld[x][y]));
			}
			System.out.println("|");
		}
		System.out.println((controller.getAktuelleFarbe() == Controller.WEISS ? "Spieler Wei\u00DF" : "Spieler Schwarz")+ " ist am Zug\n");
	}

	/**
	 * Gibt den übergebenen Text auf der Konsole aus.
	 */
	public void textAusgeben(String string) {
		System.out.println(string);
	}
	
	/**
	 * Private Funktion, die den Farbzahlen Zeichen zuordnet
	 */
	private char getSpielzeichen(int farbe) {
	    if (farbe == Controller.WEISS) return '\u25E6';
	    if (farbe == Controller.SCHWARZ) return '\u2022';
	    else return '_';
	}
}
