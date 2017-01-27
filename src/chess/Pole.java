package chess;

import java.awt.*;
import java.io.Serializable;

import javax.swing.*;

import figury.*;


public class Pole extends JPanel implements Cloneable {

	int x,y;
	private Figura figura;
	private static final long serialVersionUID = 1L;
	private boolean jestMozliwymCelem;
	private JLabel content;
	
	private boolean jestZaznaczone=false;
	private boolean jestSzachowane=false;

	// Konstruktor
	public Pole(int x, int y, Figura f)
	{
		this.x=x;
		this.y=y;

		setLayout(new BorderLayout());

	 if((x+y)%2==0)
		 setBackground(new Color(255,206,158));  // jasny beż
	 else
		 setBackground(new Color(209,139,71)); // ciemny beż

	 // jeśli pole wolne postaw na nim figure
	 if(f!=null)
		 setFigura(f);
	}

	// Konstruktor ktory jako argument przyjmuje pole i zwraca nowe pole z tymi samymi danymi ale inna referencja
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

	public void setFigura(Figura f)    //Ustawia figure na danym polu
	{
		figura=f;
		ImageIcon img = new javax.swing.ImageIcon(this.getClass().getResource(f.getPath()));
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

	public void setpossibledestination()     // podswietl dostepne pola do ruchu
	{
		this.setBorder(BorderFactory.createLineBorder(new Color(54,199,63), 3));  //zielone ramki
		this.jestMozliwymCelem=true;
	}

	public void removepossibledestination()      //usun pole z mozliwych ruchow
	{
		this.setBorder(null);
		this.jestMozliwymCelem=false;
	}

	public boolean jestMozliwymCelem()    // sprawdz czy pole jest mozliwym celem
	{
		return this.jestMozliwymCelem;
	}

	public void setcheck()     // oznacz pole na ktorym stoi krol jako szachowane
	{
		this.setBackground(Color.RED);
		this.jestSzachowane=true;
	}

	//funkcja usuwajaca zaznaczenie
	public void usunSzachowanePole()
	{
		this.setBorder(null);
		if((x+y)%2==1)
			setBackground(new Color(209,139,71)); // ciemny beż
		else
			setBackground(new Color(255,206,158));  // jasny beż
		this.jestSzachowane=false;
	}

	// sprawdz czy pole jest szachowane
	public boolean jestSzachowane()	{ return jestSzachowane; }
	
	public void historiaPola() {
		Figura f = null;
		Pole p = new Pole(this.x, this.y, f);
		f = p.getFigura();

	}
	
	
}
