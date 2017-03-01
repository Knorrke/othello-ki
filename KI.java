public class KI {
    private int rechenTiefe;
    private Spiel spiel;
    private int farbe;

    /**
     * Erstellt eine KI mit der angegebenen rechenTiefe und farbe für das übergebene Spiel
     * @param rechenTiefe   je höher, desto stärker ist die KI, aber braucht länger zum Ziehen
     * @param spiel         Ein Objekt der Klasse Spiel, in dem die KI Züge ausführen soll.
     * @param farbe         1 für Weiß, 2 für Schwarz
     */
    public KI(int rechenTiefe, Spiel spiel, int farbe) {
        this.rechenTiefe = rechenTiefe;
        this.spiel = spiel;
        this.farbe = farbe;
    }

    /**
     * Aufgabe i) Bewertet die Stellung unter Berücksichtigung von X- und C-Feldern
	 * (siehe Wikipedia: https://de.wikipedia.org/wiki/Othello_(Spiel)#Strategie)
     */
    public double stellungBewerten(int[][] spielfeld) {
        double stand = 0;
        for(int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if(spielfeld[i][j] == 0) continue;
                if(spielfeld[i][j] == farbe) {
                    if((i==0 && j==0) || (i==0 && j==7) || (i==7 && j==0) || (i==7 && j==7)){
                        stand = stand + 5;
                    } else if((i==1 && j==1) || (i==1 && j==6) || (i==6 && j==1) || (i==6 && j==6)) {
                        stand = stand - 1;
                    } else if (i+j == 7 || i==j) {
                        stand = stand + 1.5;
                    } else if (i==0 || j==0 || i==7 || j==7) {
                        stand = stand + 2;
                    } else {
                        stand = stand + 1;
                    }
                }
                else {
                    if((i==0 && j==0) || (i==0 && j==7) || (i==7 && j==0) || (i==7 && j==7)){
                        stand = stand - 5;
                    } else if((i==1 && j==1) || (i==1 && j==6) || (i==6 && j==1) || (i==6 && j==6)) {
                        stand = stand + 1;
                    } else if (i+j == 7 || i==j) {
                        stand = stand - 1.5;
                    } else if (i==0 || j==0 || i==7 || j==7) {
                        stand = stand - 2;
                    } else {
                        stand = stand - 1;
                    }
                }
            }
        }
        return stand;
    }

    /**
     * Aufgabe h) Zufall ergänzt, um Verhalten abzuändern ohne aber Spielstärke einzuschränken.
     */
    public double minimax(int[][] spielfeld, int aktuelleFarbe, int tiefe) {
        if (tiefe == rechenTiefe || !spiel.zuegeMoeglich(spielfeld, aktuelleFarbe)) {
            return stellungBewerten(spielfeld);
        } else {
            Double bisherigerWert = null;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (Spiel.zugMoeglich(spielfeld,aktuelleFarbe, i, j)) {
                        int[][] spielfeldNeu = Spiel.zugSimulieren(spielfeld, aktuelleFarbe, i, j);
                        int naechsteFarbe = Spiel.getGegner(aktuelleFarbe);
                        double neueBewertung = minimax(spielfeldNeu,naechsteFarbe, tiefe + 1);
                        if (farbe == aktuelleFarbe) {
                            //Maximum auswählen
                            //Hier kommt der Zufall ins Spiel: Gleichwertige Züge
                            //werden zufällig ausgewählt
                            if (bisherigerWert == null || bisherigerWert < neueBewertung
                            || (bisherigerWert == neueBewertung && Math.random()>0.5)) {
                                bisherigerWert = neueBewertung;
                            }
                        } else {
                            //Minimum auswählen
                            //Hier kommt der Zufall ins Spiel: Gleichwertige Züge
                            //werden zufällig ausgewähle
                            if (bisherigerWert == null || bisherigerWert > neueBewertung
                            || (bisherigerWert == neueBewertung && Math.random()>0.5)) {
                                bisherigerWert = neueBewertung;
                            }
                        }
                    }
                }
            }
            return bisherigerWert;
        }
    }

    public void ziehen() {
        int bestesX=-1,bestesY=-1;
        Double besteBewertung = null;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if(spiel.zugMoeglich(farbe, i,j)) {
                    double neueBewertung = minimax(spiel.zugSimulieren(farbe, i, j),Spiel.getGegner(farbe), 1);
                    if(besteBewertung == null || neueBewertung > besteBewertung) {
                        bestesX = i;
                        bestesY = j;
                        besteBewertung = neueBewertung;
                    }
                }
            }
        }
        spiel.setzen(bestesX,bestesY);
    }

    public int getFarbe() {
        return farbe;
    }

}
