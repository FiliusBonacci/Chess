package chess;

import figury.*;
import chess.Pole;


public class HistoriaRuchow {
	
	Pole p;
	Figura f;
	
	public static int licznikHistori=0;
	
//	ArrayList<Pole> ruchy = new ArrayList<Pole>();
	static Object historia[][] = new Object[999][2];
	

	public HistoriaRuchow() {
		
	}
	

	
	public static void addRuch(final Pole m) {
        try {
			historia[licznikHistori][0] = m ;
			historia[licznikHistori][1] = m.getFigura() ;
			licznikHistori++;
		} catch (Exception e) {
			System.out.println("Indeks spoza tablicy historii");
			e.printStackTrace();
		}
    }
	
	
}