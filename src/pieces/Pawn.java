package pieces;

//import java.awt.Component;
import java.util.ArrayList;

//import javax.swing.JOptionPane;

import chess.Cell;
//import pieces.Queen;


public class Pawn extends Piece {
	
	int wartosc = 3;

	//pionek promowany na hetmana
	Queen wq = null;
	
	// Konstruktory
	public Pawn(String i, String ikona, int kolor) {
		setId(i);
		setPath(ikona);
		setColor(kolor);
	}

	// Move Function Overridden
	public ArrayList<Cell> move(Cell state[][], int x, int y) {
		// Pionek może poruszac sie co 1 pole z wyjątkiem polozenia poczatkowego gdzie moze 2 
		// po diagonali moze tylko atakowac figure przeciwnika
		// nie moze poruszac sie do tyłu
		
		//pionek dochodzi do linii przemiany zmienia sie w hetmana
		if (getcolor() == 0 && x == 1) {
			//wq = new Queen("WQ", "White_Queen.png", 0);
			this.setPath("White_Queen.png");
			//possiblemoves = wq.possiblemoves;
		}
		
		possiblemoves.clear();
		if (getcolor() == 0) {
			//promocja pionka
			if (x == 0)	//return possiblemoves;
			{
				wq = new Queen("WQ", "White_Queen.png", 0);
//				this.setPath("White_Queen.png");
				//JOptionPane.showMessageDialog(null , "Eggs are not supposed to be green.");
				return wq.move(state, 0, y);
			}

			if (state[x - 1][y].getpiece() == null && wq == null) {
				possiblemoves.add(state[x - 1][y]);
				if (x == 6) {
					if (state[4][y].getpiece() == null)
						possiblemoves.add(state[4][y]);
				}
			} else {
				return wq.move(state, x, y);
			}
			
			if ((y > 0) && (state[x - 1][y - 1].getpiece() != null)
					&& (state[x - 1][y - 1].getpiece().getcolor() != this.getcolor()))
				possiblemoves.add(state[x - 1][y - 1]);
			if ((y < 7) && (state[x - 1][y + 1].getpiece() != null)
					&& (state[x - 1][y + 1].getpiece().getcolor() != this.getcolor()))
				possiblemoves.add(state[x - 1][y + 1]);
			
			
		} else {
			if (x == 7)
				return possiblemoves;
			if (state[x + 1][y].getpiece() == null) {
				possiblemoves.add(state[x + 1][y]);
				if (x == 1) {
					if (state[3][y].getpiece() == null)
						possiblemoves.add(state[3][y]);
				}
			}
			if ((y > 0) && (state[x + 1][y - 1].getpiece() != null)
					&& (state[x + 1][y - 1].getpiece().getcolor() != this.getcolor()))
				possiblemoves.add(state[x + 1][y - 1]);
			if ((y < 7) && (state[x + 1][y + 1].getpiece() != null)
					&& (state[x + 1][y + 1].getpiece().getcolor() != this.getcolor()))
				possiblemoves.add(state[x + 1][y + 1]);
		}
		return possiblemoves;
	} //--------------------------------------------------
	
	public int getWartosc() { return wartosc; }

}
