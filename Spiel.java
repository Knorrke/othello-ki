import game.*;
import java.util.Arrays;

public class Spiel
{
    Controller controller;

    /**
     * Startet ein neues Othello spiel
     */
    public Spiel()
    {
        controller = new Controller();
    }
    

    /**
     * Gibt eine Kopie des Spielfelds zurück. Änderungen an diesem Spielfeld haben keine
     * Auswirkung auf das echte Spiel
     */
    public int[][] getSpielfeld() {
        return controller.getSpielfeld();
    }

    /**
     * Gibt zurück, ob für die angegebene Farbe in dem angegebenen Spielfeld noch Züge möglich sind
     * @param spielfeld
     * @param farbe     1 für Weiss, 2 für Schwarz
     * @return true, falls möglich, sonst false
     */
    public boolean zuegeMoeglich(int[][] spielfeld, int farbe) {
        Model model = new Model(spielfeld);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (model.zugMoeglich(farbe, x, y))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Gibt zurück das Setzen auf (x,y) für die übergebene Farbe möglich ist. 
     * @param farbe     1 für Weiss, 2 für Schwarz
     * @param x,y       position zum Setzen
     * @return true, falls möglich, sonst false
     */
    public boolean zugMoeglich(int farbe, int x, int y) {
        return controller.zugMoeglich(farbe,x,y);
    }

    /**
     * Gibt zurück, ob in der übergebenen Stellung das Setzen auf (x,y) für die 
     * übergebene farbe möglich ist.
     * @param spielfeld
     * @param farbe     1 für Weiss, 2 für Schwarz
     * @param x,y       position zum Setzen
     * @return true, falls möglich, sonst false
     */
    public static boolean zugMoeglich(int[][] spielfeld, int farbe, int x, int y) {
        Model model = new Model(spielfeld);
        return model.zugMoeglich(farbe,x,y);
    }

    /**
     * Führt den Zug (x,y) aus, falls erlaubt
     */
    public void setzen(int x, int y) {
        controller.setzen(x,y);
    }

    /**
     * Berechnet aus dem übergebenen Spielfeld ein neues nach dem Zug (x,y) für die 
     * übergebene Farbe und gibt dieses Spielfeld zurück.
     * @param spielfeld
     * @param farbe     1 für Weiss, 2 für Schwarz
     * @param x,y       position zum Setzen
     * @return neues Spielfeld nach dem Zug
     */
    public static int[][] zugSimulieren(int[][] spielfeld, int farbe, int x, int y) {
        Model model = new Model(spielfeld);
        model.setzen(farbe,x,y);
        return model.getSpielfeld();
    }

    /**
     * Berechnet für das aktuelle Spiel das Spielfeld nach dem Zug (x,y) für die übergebene Farbe
     * und gibt dieses Spielfeld zurück.
     * @param farbe     1 für Weiss, 2 für Schwarz
     * @param x,y       position zum Setzen
     * @return neues Spielfeld nach dem Zug
     */
    public int[][] zugSimulieren(int farbe, int x, int y) {
        Model model = new Model(getSpielfeld());
        model.setzen(farbe,x,y);
        return model.getSpielfeld();
    }

    /**
     * Gibt den Gegner der übergebenen Farbe zurück.
     * @param farbe     1 für Weiss, 2 für Schwarz
     * @return 2, falls farbe == 1, sonst 1
     */
    public static int getGegner(int farbe) {
        if (farbe == Controller.WEISS) {
            return Controller.SCHWARZ;
        } else {
            return Controller.WEISS;
        }
    }
}
