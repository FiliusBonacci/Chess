package chess;

import java.awt.*;
import javax.swing.*;

import figury.*;

/**
 * This is the Cell Class. It is the token class of our GUI.
 * There are total of 64 cells that together makes up the Chess Board
 *
 */
public class Pole extends JPanel implements Cloneable{

	//Member Variables
	private static final long serialVersionUID = 1L;
	private boolean ispossibledestination;
	private JLabel content;
	private Figura figura;
	int x,y;
	private boolean isSelected=false;
	private boolean ischeck=false;

	//Constructors
	public Pole(int x,int y,Figura p)
	{
		this.x=x;
		this.y=y;

		setLayout(new BorderLayout());

	 if((x+y)%2==0)
		 setBackground(new Color(255,206,158));  // jasny beż
	 else
		 setBackground(new Color(209,139,71)); // ciemny beż

	 if(p!=null)
		 setPiece(p);
	}

	//A constructor that takes a cell as argument and returns a new cell will the same data but different reference
	public Pole(Pole pole) throws CloneNotSupportedException
	{
		this.x=pole.x;
		this.y=pole.y;
		setLayout(new BorderLayout());
		if((x+y)%2==1)
			setBackground(new Color(209,139,71)); // ciemny beż
		else
			setBackground(new Color(255,206,158));  // jasny beż
		if(pole.getpiece()!=null)
		{
			setPiece(pole.getpiece().getcopy());
		}
		else
			figura=null;
	}

	public void setPiece(Figura p)    //Function to inflate a cell with a piece
	{
		figura=p;
		ImageIcon img = new javax.swing.ImageIcon(this.getClass().getResource(p.getPath()));
		content=new JLabel(img);
		this.add(content);
	}

	public void removePiece()      //usuwa figure z pola
	{
		if (figura instanceof Krol)
		{
			figura=null;
			this.remove(content);
		}
		else
		{
			figura=null;
			this.remove(content);
		}
	}


	public Figura getpiece()    //zwraca figure na danym polu
	{
		return this.figura;
	}

	public void select()       //Function to mark a cell indicating it's selection
	{
		this.setBorder(BorderFactory.createLineBorder(new Color(222,71,51), 4));   // czerwona ramka
		this.isSelected=true;
	}

	public boolean isselected()   //zwraca czy pole jest zaznaczone
	{
		return this.isSelected;
	}

	public void deselect()      //usuń obramowanie pola
	{
		this.setBorder(null);
		this.isSelected=false;
	}

	public void setpossibledestination()     //Function to highlight a cell to indicate that it is a possible valid move
	{
		this.setBorder(BorderFactory.createLineBorder(new Color(54,199,63), 3));  //zielone ramki
		this.ispossibledestination=true;
	}

	public void removepossibledestination()      //Remove the cell from the list of possible moves
	{
		this.setBorder(null);
		this.ispossibledestination=false;
	}

	public boolean ispossibledestination()    //Function to check if the cell is a possible destination
	{
		return this.ispossibledestination;
	}

	public void setcheck()     //Function to highlight the current cell as checked (For King)
	{
		this.setBackground(Color.RED);
		this.ischeck=true;
	}

	//funkcja usuwajaca zaznaczenie
	public void removecheck()
	{
		this.setBorder(null);
		if((x+y)%2==1)
			setBackground(new Color(209,139,71)); // ciemny beż
		else
			setBackground(new Color(255,206,158));  // jasny beż
		this.ischeck=false;
	}

	public boolean ischeck()    //Function to check if the current cell is in check
	{
		return ischeck;
	}
}
