package figury;

import java.io.Serializable;
import java.util.ArrayList;

import chess.Pole;

public class Krol extends Figura implements Serializable {

	private int x, y; // zmienne króla do śledzenia pozycji

	
	int wartosc = 0;
	
	// konstruktor Król
	public Krol(String i, String p, int c, int x, int y) {
		setx(x);
		sety(y);
		setId(i);
		setPath(p);
		setColor(c);
	}

	// gettery i settery
	public void setx(int x) {
		this.x = x;
	}

	public void sety(int y) {
		this.y = y;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	// Move Function for King Overridden from Pieces
	public ArrayList<Pole> ruch(Pole state[][], int x, int y) {
		// King can move only one step. So all the adjacent 8 cells have been
		// considered.
		mozliweRuchy.clear();
		int posx[] = { x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1 };
		int posy[] = { y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if ((state[posx[i]][posy[i]].getFigura() == null
						|| state[posx[i]][posy[i]].getFigura().getcolor() != this.getcolor()))
					mozliweRuchy.add(state[posx[i]][posy[i]]);
		return mozliweRuchy;
	}

	// Function to check if king is under threat
	// It checks whether there is any piece of opposite color that can attack
	// king for a given board state
	public boolean isindanger(Pole state[][]) {

		// Checking for attack from left,right,up and down
		for (int i = x + 1; i < 8; i++) {
			if (state[i][y].getFigura() == null)
				continue;
			else if (state[i][y].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if ((state[i][y].getFigura() instanceof Wieza) || (state[i][y].getFigura() instanceof Hetman))
					return true;
				else
					break;
			}
		}
		for (int i = x - 1; i >= 0; i--) {
			if (state[i][y].getFigura() == null)
				continue;
			else if (state[i][y].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if ((state[i][y].getFigura() instanceof Wieza) || (state[i][y].getFigura() instanceof Hetman))
					return true;
				else
					break;
			}
		}
		for (int i = y + 1; i < 8; i++) {
			if (state[x][i].getFigura() == null)
				continue;
			else if (state[x][i].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if ((state[x][i].getFigura() instanceof Wieza) || (state[x][i].getFigura() instanceof Hetman))
					return true;
				else
					break;
			}
		}
		for (int i = y - 1; i >= 0; i--) {
			if (state[x][i].getFigura() == null)
				continue;
			else if (state[x][i].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if ((state[x][i].getFigura() instanceof Wieza) || (state[x][i].getFigura() instanceof Hetman))
					return true;
				else
					break;
			}
		}

		// checking for attack from diagonal direction
		int tempx = x + 1, tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getFigura() == null) {
				tempx++;
				tempy--;
			} else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getFigura() instanceof Goniec || state[tempx][tempy].getFigura() instanceof Hetman)
					return true;
				else
					break;
			}
		}
		tempx = x - 1;
		tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getFigura() == null) {
				tempx--;
				tempy++;
			} else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getFigura() instanceof Goniec || state[tempx][tempy].getFigura() instanceof Hetman)
					return true;
				else
					break;
			}
		}
		tempx = x - 1;
		tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getFigura() == null) {
				tempx--;
				tempy--;
			} else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getFigura() instanceof Goniec || state[tempx][tempy].getFigura() instanceof Hetman)
					return true;
				else
					break;
			}
		}
		tempx = x + 1;
		tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getFigura() == null) {
				tempx++;
				tempy++;
			} else if (state[tempx][tempy].getFigura().getcolor() == this.getcolor())
				break;
			else {
				if (state[tempx][tempy].getFigura() instanceof Goniec || state[tempx][tempy].getFigura() instanceof Hetman)
					return true;
				else
					break;
			}
		}

		// Checking for attack from the Knight of opposite color
		int posx[] = { x + 1, x + 1, x + 2, x + 2, x - 1, x - 1, x - 2, x - 2 };
		int posy[] = { y - 2, y + 2, y - 1, y + 1, y - 2, y + 2, y - 1, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if (state[posx[i]][posy[i]].getFigura() != null
						&& state[posx[i]][posy[i]].getFigura().getcolor() != this.getcolor()
						&& (state[posx[i]][posy[i]].getFigura() instanceof Skoczek)) {
					return true;
				}

		// Checking for attack from the Pawn of opposite color
		int pox[] = { x + 1, x + 1, x + 1, x, x, x - 1, x - 1, x - 1 };
		int poy[] = { y - 1, y + 1, y, y + 1, y - 1, y + 1, y - 1, y };
		{
			for (int i = 0; i < 8; i++)
				if ((pox[i] >= 0 && pox[i] < 8 && poy[i] >= 0 && poy[i] < 8))
					if (state[pox[i]][poy[i]].getFigura() != null
							&& state[pox[i]][poy[i]].getFigura().getcolor() != this.getcolor()
							&& (state[pox[i]][poy[i]].getFigura() instanceof Krol)) {
						return true;
					}
		}
		if (getcolor() == 0) {
			if (x > 0 && y > 0 && state[x - 1][y - 1].getFigura() != null
					&& state[x - 1][y - 1].getFigura().getcolor() == 1
					&& (state[x - 1][y - 1].getFigura() instanceof Pionek))
				return true;
			if (x > 0 && y < 7 && state[x - 1][y + 1].getFigura() != null
					&& state[x - 1][y + 1].getFigura().getcolor() == 1
					&& (state[x - 1][y + 1].getFigura() instanceof Pionek))
				return true;
		} else {
			if (x < 7 && y > 0 && state[x + 1][y - 1].getFigura() != null
					&& state[x + 1][y - 1].getFigura().getcolor() == 0
					&& (state[x + 1][y - 1].getFigura() instanceof Pionek))
				return true;
			if (x < 7 && y < 7 && state[x + 1][y + 1].getFigura() != null
					&& state[x + 1][y + 1].getFigura().getcolor() == 0
					&& (state[x + 1][y + 1].getFigura() instanceof Pionek))
				return true;
		}
		return false;
	} //--------------------------------------------------
	
	public int getWartosc() { return wartosc; }

}