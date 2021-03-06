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
     * Aufgabe c)
     * Bewertet die Stellung. Je besser die Stellung für die KI, desto höher der Rückgabewert
     */
    public double stellungBewerten(int[][] spielfeld) {
        //TODO
        return 0;
    }

    /**
     * Aufgabe e) Bitte ergänze in den mit __ gekennzeichneten Lücken
     * 
     * Berechnet rekursiv den besten Zug nach dem Minimax-Algorithmus bis zur in den 
     * Attributen gesetzten rechenTiefe
     */
    public double minimax(int[][] spielfeld, int aktuelleFarbe, int tiefe) {
        //TODO
        /*if ( _______________________  || !spiel.zuegeMoeglich(spielfeld, aktuelleFarbe)) {
            return stellungBewerten(spielfeld);
        } else {
            Double bisherigerWert = null;
            for(int i=__; i<__; i++) {
                for(int j=__; j<__; j++) {
                    if ( ___________________________________ ) {
                        int[][] spielfeldNeu = Spiel.zugSimulieren(spielfeld, aktuelleFarbe, i, j);
                        int naechsteFarbe = Spiel.getGegner(aktuelleFarbe);
                        double neueBewertung = _________________________________________ ;
                        if (farbe == aktuelleFarbe) {
                            //Maximum auswählen
                            if (bisherigerWert == null || _____________________________) {
                                bisherigerWert = neueBewertung;
                            }
                        } else {
                            //Minimum auswählen
                            if (bisherigerWert == null || _____________________________) {
                                bisherigerWert = neueBewertung;
                            }
                        }
                    }
                }
            }
            return bisherigerWert;
        }*/
        
        return 0;
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
