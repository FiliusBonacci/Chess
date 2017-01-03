package figury;

import java.util.ArrayList;

import chess.Pole;

/**
 * This is the Bishop Class. The Move Function defines the basic rules for
 * movement of Bishop on a chess board
 * 
 *
 */
public class Goniec extends Figura {

	int wartosc = 3;

	// Constructor
	public Goniec(String i, String p, int c) {
		setId(i);
		setPath(p);
		setColor(c);
	}

	// move function defined. It returns a list of all the possible destinations
	// of a Bishop
	// The basic principle of Bishop Movement on chess board has been
	// implemented
	public ArrayList<Pole> move(Pole state[][], int x, int y) {
		// Bishop can Move diagonally in all 4 direction (NW,NE,SW,SE)
		// This function defines that logic
		possiblemoves.clear();
		int tempx = x + 1, tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getFigura() == null) {
				possiblemoves.add(state[tempx][tempy]);
			} else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
		tempx = x - 1;
		tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getFigura() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
		tempx = x - 1;
		tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getFigura() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
		tempx = x + 1;
		tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getFigura() == null)
				possiblemoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				possiblemoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
		return possiblemoves;
	}

	@Override
	public int getWartosc() { return wartosc; }
}