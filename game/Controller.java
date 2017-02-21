package game;

public class Controller {

    public static final int WEISS = 1;
    public static final int SCHWARZ = 2;
    private Model model;
    private View view;
    private int aktuelleFarbe;

    /**
     * Erstellt einen Controller mit zugehörigem Model und View mit den Standardeinstellungen
     */
    public Controller() {
        aktuelleFarbe = SCHWARZ;
        model = new Model();
        view = new View(this);
        view.spielAusgeben();
    }

    /**
     * Erstellt einen Controller mit zugehörigem Model und View mit der übergebenen 
     * Startstellung und dem übergebenen Startspieler
     */
    public Controller(int[][] spielfeld, int startspieler) {
        aktuelleFarbe = startspieler;
        model = new Model(spielfeld);
        view = new View(this);
        view.spielAusgeben();
    }

    /**
     * Gibt eine Kopie des im Model gespeicherte Spielfeld zurück. 
     * Änderungen an dieser Kopie ändern nicht das Model selbst.
     */
    public int[][] getSpielfeld() {
        return model.getSpielfeld();
    }

    /**
     * Gibt zurück, ob das Setzen auf (posX,posY) für die übergebene Farbe möglich ist.
     */
    public boolean zugMoeglich(int farbe, int posX, int posY) {
        return model.zugMoeglich(farbe, posX, posY);
    }
    
    /**
     * Setzt auf (posX,posY) einen Spielstein der übergebenen Farbe, ohne auf SpielerReihenfolge zu achten
     * und ohne Bildschirmausgabe.
     * @return true, wenn erfolgreich, sonst false
     */
    public boolean setzen(int farbe, int posX, int posY) {
        return model.setzen(farbe, posX, posY);
    }

    /**
     * Setzt auf (posX,posY) für den gerade aktiven Spieler. Wechselt bei Erfolg anschließend 
     * entsprechend den Regeln den ziehenden Spieler.
     * @return true, wenn erfolgreich, sonst false
     */
    public void setzen(int posX, int posY) {
        boolean erfolgreich = setzen(aktuelleFarbe, posX, posY);

        if (erfolgreich) {
            naechsterSpieler();
            view.spielAusgeben();

            if (!zuegeMoeglich(aktuelleFarbe)) {
                String spielername = aktuelleFarbe == WEISS ? "Wei\u00DF" : "Schwarz";
                String gegnername = aktuelleFarbe == WEISS ? "Schwarz" : "Wei\u00DF";

                view.textAusgeben("Spieler " + spielername + " hat keine m\u00F6glichen Z\u00FCge mehr...");
                naechsterSpieler();
                if (zuegeMoeglich(aktuelleFarbe)) {
                    view.textAusgeben("Spieler " + gegnername + " ist nochmal dran!");
                } else {
                    view.textAusgeben("Spieler " + gegnername + " hat keine m\u00F6glichen Z\u00FCge mehr... Das Spiel ist vorbei");
                    ende();
                }
            }
        } else {
            view.textAusgeben("Zug " + posX + "," + posY + " nicht m\u00F6glich");
        }

    }

    /**
     * Private Funktion, die das Spielende an die View sendet.
     */
    private void ende() {
        aktuelleFarbe = 0;
        if (model.getSpielstand() > 0) {
            view.textAusgeben("Spieler Schwarz hat gewonnen!");
        } else if (model.getSpielstand() < 0) {
            view.textAusgeben("Spieler Wei\u00DF hat gewonnen!");
        } else {
            view.textAusgeben("Unentschieden!");
        }
    }

    /**
     * Private Funktion, die prüft, ob noch Züge möglich sind
     */
    private boolean zuegeMoeglich(int farbe) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (model.zugMoeglich(farbe, x, y))
                    return true;
            }
        }
        return false;
    }

    /**
     * Private Funktion, die den Zug zum nächsten Spieler setzt
     */
    private void naechsterSpieler() {
        aktuelleFarbe = (aktuelleFarbe == WEISS ? SCHWARZ : WEISS);
    }
    
    /**
     * Gibt die Farbe des Spielers zurück, der aktuell an der Reihe ist
     */
    public int getAktuelleFarbe() {
        return aktuelleFarbe;
    }

    /**
     * Gibt den aktuellen Spielstand zurück
     */
    public int getSpielstand() {
        return model.getSpielstand();
    }
}
