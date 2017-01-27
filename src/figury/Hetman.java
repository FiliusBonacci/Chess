package figury;

import java.util.ArrayList;

import chess.Pole;

public class Hetman extends Figura{
	
	int wartosc = 8;
	// Konstruktor
	public Hetman(String i, String sciezka, int kolor)	{
		setId(i);
		setPath(sciezka);
		setColor(kolor);
	}
	
	
	public ArrayList<Pole> ruch(Pole state[][],int x,int y)
	{
		//Hetman porusza sie w kazdym kierunku
		//Mozliwe ruchy to kombinacja ruchow wierzy i gonca
		mozliweRuchy.clear();
		
		//mozliwe ruchy po diagonali
		int tempx = x-1;
		while(tempx >= 0)
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
		
		
		//mozliwe ruchy horyzontalnie
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
		
		//mozliwe ruchy diagonalnie
		tempx=x+1;tempy=y-1;
		while(tempx<8&&tempy>=0)
		{
			if(state[tempx][tempy].getFigura()==null)
				mozliweRuchy.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
		tempx=x-1;tempy=y+1;
		while(tempx>=0&&tempy<8)
		{
			if(state[tempx][tempy].getFigura()==null)
				mozliweRuchy.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
		tempx=x-1;tempy=y-1;
		while(tempx>=0&&tempy>=0)
		{
			if(state[tempx][tempy].getFigura()==null)
				mozliweRuchy.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
		tempx=x+1;tempy=y+1;
		while(tempx<8&&tempy<8)
		{
			if(state[tempx][tempy].getFigura()==null)
				mozliweRuchy.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getFigura().getcolor()==this.getcolor())
				break;
			else
			{
				mozliweRuchy.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
		return mozliweRuchy;
	} //---------------------------------------------------------
	
	
	public int getWartosc() { return wartosc; }
}