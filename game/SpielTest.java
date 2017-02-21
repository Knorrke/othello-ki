package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SpielTest {
        private Controller game;
    private int[][] spielfeld;
    
    @Before
    public void setup() {
        game = new Controller();
        spielfeld = game.getSpielfeld();
    }
    
    @Test
    public void feldGroesse() {
        assertEquals("Spielfeld sollte L&auml;nge 8 haben", 8, spielfeld.length);
        assertEquals("Spielfeld sollte Breite 8 haben", 8, spielfeld[0].length);
    }
    
    @Test
    public void startposition() {
        assertEquals("Startposition sollte aufgestellt sein", Controller.WEISS, spielfeld[3][3]);
        assertEquals("Startposition sollte aufgestellt sein", Controller.SCHWARZ, spielfeld[3][4]);
        assertEquals("Startposition sollte aufgestellt sein", Controller.SCHWARZ, spielfeld[4][3]);
        assertEquals("Startposition sollte aufgestellt sein", Controller.WEISS, spielfeld[4][4]);
    }
    
    @Test
    public void zugregel() {
        assertTrue("Wei&szlig; sollte auf Feld [4][2] legen k&ouml;nnen", game.zugMoeglich(Controller.WEISS, 4,2));
        assertFalse("Wei&szlig; sollte nicht auf Feld [3][2] legen k&ouml;nnen", game.zugMoeglich(Controller.WEISS, 3,2));
        assertTrue("Schwarz sollte auf Feld [3][2] legen k&ouml;nnen", game.zugMoeglich(Controller.SCHWARZ, 3,2));
        assertFalse("Schwarz sollte nicht auf Feld [4][2] legen k&ouml;nnen", game.zugMoeglich(Controller.SCHWARZ, 4,2));
        assertFalse("Wei&szlig; sollte nicht auf belegtes Feld [3][3] legen k&ouml;nnen", game.zugMoeglich(Controller.WEISS, 3,3));
        assertFalse("Schwarz sollte nicht auf belegtes Feld [3][3] legen k&ouml;nnen", game.zugMoeglich(Controller.SCHWARZ, 3,3));
    }
    
    @Test
    public void zugAusfuehren() {
        setzen(Controller.WEISS,4,2);
        assertEquals("Auf gesetztem Feld sollte Stein liegen", Controller.WEISS, spielfeld[4][2]);
    }
    
    @Test
    public void zugNurAusfuehrenWennMoeglich() {
        setzen(Controller.WEISS,3,2);
        assertEquals("Auf nicht m&ouml;glichem Feld sollte kein Stein liegen", 0, spielfeld[3][2]);
    }
    
    @Test
    public void steineUmdrehenBeiZug() {
        setzen(Controller.WEISS,4,2);
        assertEquals("Stein auf [4][3] sollte nach Zug Weiss sein", Controller.WEISS, spielfeld[4][3]);
    }
    
    @Test
    public void schwarzBeginnt() {
        setzen(3, 2);
        assertEquals("Gesetzter Stein ist Schwarz", Controller.SCHWARZ, spielfeld[3][2]);

        setzen(4, 2);
        assertEquals("Zweiter Zug ist wei\u00DF", Controller.WEISS, spielfeld[4][2]);
    }
    
    @Test
    public void spielstand() {
        assertEquals("Spielstand ist 0 zu Beginn des Spiels", 0, game.getSpielstand());
        setzen(3, 2);
        assertEquals("Spielstand ist +3 nach erstem Zug", 3, game.getSpielstand());
    }
    
    @Test
    public void doppelterZug() {
        int[][] spiel = {{0,0,0,0,0,0,0,0},
                         {0,1,1,1,1,1,0,0},
                         {0,1,2,1,0,1,0,0},
                         {0,1,1,1,1,1,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0},
                         {0,0,0,0,0,0,0,0}};
        game = new Controller(spiel, Controller.SCHWARZ);
        assertEquals("Schwarz ist am Zug", Controller.SCHWARZ, game.getAktuelleFarbe());
        setzen(2,4);//Vorsicht: Zeile,Spalte vertauscht...
        assertEquals("Schwarz darf nochmal, wenn Weiß keinen Zug hat", Controller.SCHWARZ, game.getAktuelleFarbe());
    }
    
    private void setzen(int i, int j) {
        game.setzen(i,j);
        spielfeld = game.getSpielfeld();
    }
    
    private void setzen(int farbe, int i, int j) {
        game.setzen(farbe, i,j);
        spielfeld = game.getSpielfeld();
    }
}
