package utils;

import java.io.Serializable;

import figury.Figura;

public class Informacja implements Serializable {

	private static final long serialVersionUID = 8970544647395954736L;
	private int x, y; //pola na ktorych stoi figura
	private Figura figura;
	
	public Informacja() {
		super();
	}
	public Informacja(Figura figura, int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.figura = figura;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Figura getFigura() {
		return figura;
	}

	public void setFigura(Figura figura) {
		this.figura = figura;
	}
	
}
