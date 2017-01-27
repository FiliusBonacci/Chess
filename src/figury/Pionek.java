package figury;

import java.io.Serializable;
//import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//import javax.swing.JOptionPane;

import chess.Pole;
//import pieces.Queen;


public class Pionek extends Figura implements Serializable {
	
	int wartosc = 3;

	//pionek promowany na hetmana
	Hetman wq = null;
	
	// Konstruktory
	public Pionek(String i, String ikona, int kolor) {
		setId(i);
		setPath(ikona);
		setColor(kolor);
	}

	// Move Function Overridden
	public ArrayList<Pole> ruch(Pole state[][], int x, int y) {
		// Pionek może poruszac sie co 1 pole z wyjątkiem polozenia poczatkowego gdzie moze 2 
		// po diagonali moze tylko atakowac figure przeciwnika
		// nie moze poruszac sie do tyłu
		
		//pionek dochodzi do linii przemiany zmienia sie w hetmana
		if (getcolor() == 0 && x == 1) {
			//this.setPath("White_Queen.png");

			//possiblemoves = wq.possiblemoves;
		}
	
		mozliweRuchy.clear();
		if (getcolor() == 0) {
			//promocja pionka
			if (x == 0)	//return possiblemoves;
			{
//				this.setPath(null);
//				if (state[x][y].getFigura() != null) {
//					state[x][y].usunFigure();
//				}
//				wq = new Hetman("WQ", "White_Queen.png", 0);
//				state[x][y].setFigura(wq);
				
				//JOptionPane.showMessageDialog(null , "Eggs are not supposed to be green.");
//				if (this.getWartosc() > 0) {
//					state[x][y].setPiece(wq);
//				}
		
				//return wq.move(state, 0, y);
				return mozliweRuchy;
			}

			if (state[x - 1][y].getFigura() == null ) { //&& wq == null
				mozliweRuchy.add(state[x - 1][y]);
				if (x == 6) {
					if (state[4][y].getFigura() == null)
						mozliweRuchy.add(state[4][y]);
				}
			} //else {
				//return wq.move(state, x, y);
			//}
			
			if ((y > 0) && (state[x - 1][y - 1].getFigura() != null)
					&& (state[x - 1][y - 1].getFigura().getcolor() != this.getcolor()))
				mozliweRuchy.add(state[x - 1][y - 1]);
			if ((y < 7) && (state[x - 1][y + 1].getFigura() != null)
					&& (state[x - 1][y + 1].getFigura().getcolor() != this.getcolor()))
				mozliweRuchy.add(state[x - 1][y + 1]);
			
			// to samo dla czernego pionka
		} else {
			if (x == 7)
				return mozliweRuchy;
			if (state[x + 1][y].getFigura() == null) {
				mozliweRuchy.add(state[x + 1][y]);
				if (x == 1) {
					if (state[3][y].getFigura() == null)
						mozliweRuchy.add(state[3][y]);
				}
			}
			if ((y > 0) && (state[x + 1][y - 1].getFigura() != null)
					&& (state[x + 1][y - 1].getFigura().getcolor() != this.getcolor()))
				mozliweRuchy.add(state[x + 1][y - 1]);
			if ((y < 7) && (state[x + 1][y + 1].getFigura() != null)
					&& (state[x + 1][y + 1].getFigura().getcolor() != this.getcolor()))
				mozliweRuchy.add(state[x + 1][y + 1]);
		}
		return mozliweRuchy;
	} //--------------------------------------------------
	
	public int getWartosc() { return wartosc; }

}
