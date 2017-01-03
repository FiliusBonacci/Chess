package figury;

import java.util.ArrayList;

import chess.Pole;

/**
 * This is the Piece Class. It is an abstract class from which all the actual
 * pieces are inherited. It defines all the function common to all the pieces
 * The move() function an abstract function that has to be overridden in all the
 * inherited class It implements Cloneable interface as a copy of the piece is
 * required very often
 */
public abstract class Figura implements Cloneable {

	int wartosc;
	
	
	// Member Variables
	private int color;
	private String id = null;
	private String path;
	
	protected ArrayList<Pole> possiblemoves = new ArrayList<Pole>(); 

	public abstract ArrayList<Pole> move(Pole pos[][], int x, int y); 

	// Id Setter
	public void setId(String id) {
		this.id = id;
	}

	// Path Setter
	public void setPath(String path) {
		this.path = path;
	}

	// Color Setter
	public void setColor(int c) {
		this.color = c;
	}

	// Path getter
	public String getPath() {
		return path;
	}

	// Id getter
	public String getId() {
		return id;
	}

	// Color Getter
	public int getcolor() {
		return this.color;
	}

	// Function to return the a "shallow" copy of the object. The copy has exact
	// same variable value but different reference
	public Figura getcopy() throws CloneNotSupportedException {
		return (Figura) this.clone();
	}
	//---------------------------------------------------------
	
	
	public abstract int getWartosc();
}