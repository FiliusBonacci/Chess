package chess;

import java.awt.*;
import javax.swing.*;

import figury.*;


public class Pole extends JPanel implements Cloneable{

	//Member Variables
	private static final long serialVersionUID = 1L;
	private boolean jestMozliwymCelem;
	private JLabel content;
	private Figura figura;
	int x,y;
	private boolean jestZaznaczone=false;
	private boolean jestSzachowana=false;

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
		 setFigura(p);
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
		if(pole.getFigura()!=null)
		{
			setFigura(pole.getFigura().getcopy());
		}
		else
			figura=null;
	}

	public void setFigura(Figura p)    //Ustawia figure na danym polu
	{
		figura=p;
		ImageIcon img = new javax.swing.ImageIcon(this.getClass().getResource(p.getPath()));
		content=new JLabel(img);
		this.add(content);
	}

	public void usunFigure()      //usuwa figure z pola
	{
		if (figura instanceof Krol){
			figura=null;
			this.remove(content);
		}
		else
		{
			figura=null;
			this.remove(content);
		}
	}


	public Figura getFigura()    //zwraca figure na danym polu
	{
		return this.figura;
	}
	

	public void zaznacz()       //zaznacza pole po kliknieciu czerwona ramka
	{
		this.setBorder(BorderFactory.createLineBorder(new Color(222,71,51), 4));   // czerwona ramka
		this.jestZaznaczone=true;
	}

	public boolean jestZaznaczone()   //zwraca czy pole jest zaznaczone
	{
		return this.jestZaznaczone;
	}

	public void odznacz()      //usuń obramowanie pola
	{
		this.setBorder(null);
		this.jestZaznaczone=false;
	}

	public void setpossibledestination()     //Function to highlight a cell to indicate that it is a possible valid move
	{
		this.setBorder(BorderFactory.createLineBorder(new Color(54,199,63), 3));  //zielone ramki
		this.jestMozliwymCelem=true;
	}

	public void removepossibledestination()      //Remove the cell from the list of possible moves
	{
		this.setBorder(null);
		this.jestMozliwymCelem=false;
	}

	public boolean jestMozliwymCelem()    //Function to check if the cell is a possible destination
	{
		return this.jestMozliwymCelem;
	}

	public void setcheck()     //Function to highlight the current cell as checked (For King)
	{
		this.setBackground(Color.RED);
		this.jestSzachowana=true;
	}

	//funkcja usuwajaca zaznaczenie
	public void usunSzachowanePole()
	{
		this.setBorder(null);
		if((x+y)%2==1)
			setBackground(new Color(209,139,71)); // ciemny beż
		else
			setBackground(new Color(255,206,158));  // jasny beż
		this.jestSzachowana=false;
	}

	public boolean jestSzachowana()    //Function to check if the current cell is in check
	{
		return jestSzachowana;
	}
}
