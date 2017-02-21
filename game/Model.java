package game;
import java.util.Arrays;
public class Model {

    private int[][] spielfeld;
    
    /**
     * Erstellt ein Model mit der normalen Startformation
     */
    public Model() {
        spielfeld = new int[8][8];
        
        spielfeld[3][3] = Controller.WEISS;
        spielfeld[3][4] = Controller.SCHWARZ;
        spielfeld[4][3] = Controller.SCHWARZ;
        spielfeld[4][4] = Controller.WEISS;
    }
    
    /**
     * Erstellt ein Model mit der übergebenen Startformation
     */
    public Model(int[][] spielfeld) {
        this.spielfeld = copy(spielfeld);
    }

    /**
     * Gibt eine Kopie des Spielfeldes heraus. Änderungen an dieser Kopie ändern nicht das Model.
     */
    public int[][] getSpielfeld() {
        return copy(spielfeld);
    }

    /**
     * Überprüft ob der Zug auf (posX,posY) für die übergebene Farbe möglich ist
     * @return true, falls möglich, sonst false
     */
    public boolean zugMoeglich(int farbe, int posX, int posY) {
        int gegnerFarbe = (farbe == Controller.WEISS ? Controller.SCHWARZ : Controller.WEISS);

        if (spielfeld[posX][posY] != 0)
            return false;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((dx == 0 && dy == 0) || posX + dx < 0 || posX + dx >= 8 || posY + dy < 0 || posY + dy >= 8)
                    continue;

                if (spielfeld[posX + dx][posY + dy] == gegnerFarbe && istSteinInLinie(farbe, posX, posY, dx, dy))
                    return true;
            }
        }
        return false;
    }

    /**
     * Setzt einen Stein der übergebenen Farbe auf (posX,posY)
     * @return true, falls erfolgreich, sonst false
     */
    public boolean setzen(int farbe, int posX, int posY) {
        if(zugMoeglich(farbe,posX,posY)) {
            spielfeld[posX][posY] = farbe;
            int gegnerFarbe = (farbe == Controller.WEISS ? Controller.SCHWARZ : Controller.WEISS);
            
            for (int dx=-1; dx<=1; dx++) {
                for (int dy=-1; dy<=1; dy++) {
                    if (dx==0 && dy==0) continue;
                    if (istSteinInLinie(farbe,posX,posY,dx,dy)){
                        for (int i=1; posX+i*dx >= 0 && posY+i*dy >= 0 && posX+i*dx < 8 && posY+i*dy < 8; i++) {
                            if(spielfeld[posX+i*dx][posY+i*dy]==gegnerFarbe){
                                spielfeld[posX+i*dx][posY+i*dy] = farbe;
                            } else if(spielfeld[posX+i*dx][posY+i*dy]==0 
                                || spielfeld[posX+i*dx][posY+i*dy]==farbe){
                                break;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Berechnet, ob in der Richtung (dx,dy) von (posX,posY) aus ein Stein der Farbe farbe liegt.
     * Gibt nur true zurück, wenn kein freies Feld dazwischen ist.
     */
    public boolean istSteinInLinie(int farbe, int posX, int posY, int dx, int dy) {
        for (int i=1; posX+i*dx >= 0 && posY+i*dy >= 0 && posX+i*dx < 8 && posY+i*dy < 8; i++) {
            if(spielfeld[posX+i*dx][posY+i*dy]==farbe) return true;
            if(spielfeld[posX+i*dx][posY+i*dy]==0) return false;
        }
        
        return false;
    }

    public int getSpielstand() {
        int spielstand = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++) {
                if(spielfeld[i][j] == Controller.SCHWARZ) spielstand++;
                if(spielfeld[i][j] == Controller.WEISS) spielstand--;
            }
        }
        return spielstand;
    }
    
    /**
     * Hilfsfunktion. Das Model gibt nur Kopien heraus, alle Änderungen muss das Model selbst vornehmen
     */
    private int[][] copy(int[][] array) {
        return Arrays.stream(array).map(x -> x.clone()).toArray(int[][]::new);
    }
}
