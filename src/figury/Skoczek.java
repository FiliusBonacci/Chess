package figury;

import java.util.ArrayList;

import chess.Pole;

/**
 * This is the Knight Class inherited from the Piece abstract class
 *  
 *
 */
public class Skoczek extends Figura{
	
	int wartosc = 3;
	
	//Constructor
	public Skoczek(String i,String p,int c)
	{
		setId(i);
		setPath(p);
		setColor(c);
	}
	
	//Move Function overridden
	//There are at max 8 possible moves for a knight at any point of time.
	//Knight moves only 2(1/2) steps
	public ArrayList<Pole> ruch(Pole state[][],int x,int y)
	{
		mozliweRuchy.clear();
		int posx[]={x+1,x+1,x+2,x+2,x-1,x-1,x-2,x-2};
		int posy[]={y-2,y+2,y-1,y+1,y-2,y+2,y-1,y+1};
		for(int i=0;i<8;i++)
			if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8))
				if((state[posx[i]][posy[i]].getFigura()==null||state[posx[i]][posy[i]].getFigura().getcolor()!=this.getcolor()))
				{
					mozliweRuchy.add(state[posx[i]][posy[i]]);
				}
		return mozliweRuchy;
	} //--------------------------------------------------
	
	public int getWartosc() { return wartosc; }

}