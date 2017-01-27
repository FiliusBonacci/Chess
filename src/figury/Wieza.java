package figury;

import java.util.ArrayList;

import chess.Pole;

/**
 * This is the Rook class inherited from abstract Piece class
 *
 */
public class Wieza extends Figura{
	
	int wartosc = 5;

	//Constructor
	public Wieza(String i,String p,int c)
	{
		setId(i);
		setPath(p);
		setColor(c);
	}
	
	//Move function defined
	public ArrayList<Pole> ruch(Pole state[][],int x,int y)
	{
		//Rook can move only horizontally or vertically
		mozliweRuchy.clear();
		int tempx=x-1;
		while(tempx>=0)
		{
			if(state[tempx][y].getFigura()==null)
				mozliweRuchy.add(state[tempx][y]);
			else if(state[tempx][y].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[tempx][y]);
				break;
			}
			tempx--;
		}
		tempx=x+1;
		while(tempx<8)
		{
			if(state[tempx][y].getFigura()==null)
				mozliweRuchy.add(state[tempx][y]);
			else if(state[tempx][y].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[tempx][y]);
				break;
			}
			tempx++;
		}
		int tempy=y-1;
		while(tempy>=0)
		{
			if(state[x][tempy].getFigura()==null)
				mozliweRuchy.add(state[x][tempy]);
			else if(state[x][tempy].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[x][tempy]);
				break;
			}
			tempy--;
		}
		tempy=y+1;
		while(tempy<8)
		{
			if(state[x][tempy].getFigura()==null)
				mozliweRuchy.add(state[x][tempy]);
			else if(state[x][tempy].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[x][tempy]);
				break;
			}
			tempy++;
		}
		return mozliweRuchy;
	} //--------------------------------------------------
	
	public int getWartosc() { return wartosc; }

}
