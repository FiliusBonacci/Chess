package chess;

import javax.swing.*;

import figury.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * @author Paweł Jadanowski
 *
 */

public class Main extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L;

	// deklaracja zmiennych
	private static final int wysokosc = 700;
	private static final int szerokosc = 1110;
	private static Wieza bw1, bw2, cw1, cw2;
	private static Skoczek bs1, bs2, cs1, cs2;
	private static Goniec bg1, bg2, cg1, cg2;
	private static Pionek bp[], cp[];
	private static Hetman bh, ch;
	private static Krol bk, ck;

	private Pole c, poprzednie;
	private int ruch = 0;
	private Pole stanSzachownicy[][];
	private ArrayList<Pole> destinationlist = new ArrayList<Pole>();
	// private Player White = null, Black = null;
	private JPanel szachownica = new JPanel(new GridLayout(8, 8));
	// private JPanel wdetails=new JPanel(new GridLayout(3,3));
	// private JPanel bdetails=new JPanel(new GridLayout(3,3));
	// private JPanel wcombopanel = new JPanel();
	// private JPanel bcombopanel = new JPanel();
	private JPanel controlPanel, temp;
	private JSplitPane split;
	private JLabel label;

	public static Main Mainboard;
	private boolean end = false;
	private Container content;

	private String winner = "";
	static String move;

	private Button start;

	public static void main(String[] args) {

		// inicjalizowanie figur

		// bw1 = new Wierza("WR01", "White_Rook.png", 0);
		// bw2 = new Wierza("WR02", "White_Rook.png", 0);
		// cw1 = new Wierza("BR01", "Black_Rook.png", 1);
		// cw2 = new Wierza("BR02", "Black_Rook.png", 1);
		// bs1 = new Skoczek("WK01", "White_Knight.png", 0);
		// bs2 = new Skoczek("WK02", "White_Knight.png", 0);
		// cs1 = new Skoczek("BK01", "Black_Knight.png", 1);
		// cs2 = new Skoczek("BK02", "Black_Knight.png", 1);
		bg1 = new Goniec("WB01", "White_Bishop.png", 0);
		bg2 = new Goniec("WB02", "White_Bishop.png", 0);
		cg1 = new Goniec("BB01", "Black_Bishop.png", 1);
		cg2 = new Goniec("BB02", "Black_Bishop.png", 1);
		// bh = new Queen("WQ", "White_Queen.png", 0);
		// ch = new Queen("BQ", "Black_Queen.png", 1);
		bk = new Krol("WK", "White_King.png", 0, 2, 2);
		ck = new Krol("BK", "Black_King.png", 1, 6, 7);
		bp = new Pionek[8];
		cp = new Pionek[8];
		for (int i = 0; i < 8; i++) {
			bp[i] = new Pionek("WP0" + (i + 1), "White_Pawn.png", 0);
			cp[i] = new Pionek("BP0" + (i + 1), "Black_Pawn.png", 1);
		}

		// Ustawianie szachowinicy
		Mainboard = new Main();
		// wycentruj okno na ekranie
		Mainboard.setLocationRelativeTo(null);
		Mainboard.setVisible(true);
		Mainboard.setResizable(false);
	}

	// Główny konstruktor
	private Main() {
		move = "White";
		winner = null;
		szachownica = new JPanel(new GridLayout(8, 8));

		szachownica.setMinimumSize(new Dimension(800, 700));
		ImageIcon img = new ImageIcon(this.getClass().getResource("icon.png"));
		this.setIconImage(img.getImage());

		Pole pole;
		// szachownica.setBorder(BorderFactory.createLoweredBevelBorder());
		figury.Figura P;
		content = getContentPane();
		setSize(szerokosc, wysokosc);
		setTitle("Szachy - Paweł Jadanowski");
		content.setBackground(Color.black);
		controlPanel = new JPanel();
		content.setLayout(new BorderLayout());
		controlPanel.setLayout(new FlowLayout());

		// ustawianie figur na szachownicy
		stanSzachownicy = new Pole[8][8];

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				P = null;

				if (i == 2 && j == 2)
					P = bk; // biały król na c6
				else if (i == 2 && j == 3)
					P = bp[0]; // biały pionek na d6

				else if (i == 6 && j == 7)
					P = ck; // czarny król na h2
				else if (i == 3 && j == 7)
					P = cg1; // czarny goniec na h5

				pole = new Pole(i, j, P);
				pole.addMouseListener(this);
				szachownica.add(pole);
				stanSzachownicy[i][j] = pole;
			}

		start = new Button("Nowa gra");
		start.setBackground(new Color(57, 219, 141)); // zielony pastelowy
		start.setForeground(Color.white);
		start.addActionListener(new START());
		start.setPreferredSize(new Dimension(120, 40));
		start.setFont(new Font("Arial", Font.BOLD, 16));
		controlPanel.add(start);

		szachownica.setMinimumSize(new Dimension(800, 700));

		controlPanel.setMinimumSize(new Dimension(285, 700));
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, controlPanel);

		content.add(split);

		split.add(szachownica);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// A function to change the ruch from White Player to Black Player or vice
	// verse
	// It is made public because it is to be accessed in the Time Class
	public void changechance() {
		if (stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].ischeck()) {
			ruch ^= 1;
			koniecGry();
		}
		if (destinationlist.isEmpty() == false)
			cleandestinations(destinationlist);
		if (poprzednie != null)
			poprzednie.deselect();
		poprzednie = null;
		ruch ^= 1;

	}

	private Krol getKing(int color) {
		if (color == 0)
			return bk;
		else
			return ck;
	}

	// funkcja czyszczaca zaznaczenie mozliwych ruchów
	private void cleandestinations(ArrayList<Pole> destlist) {
		ListIterator<Pole> it = destlist.listIterator();
		while (it.hasNext())
			it.next().removepossibledestination();
	}

	// funkcja wskazujaca mozliwe ruchy poprzez zaznaczanie pol na zielono
	private void highlightdestinations(ArrayList<Pole> destlist) {
		ListIterator<Pole> it = destlist.listIterator();
		while (it.hasNext())
			it.next().setpossibledestination();
	}

	// funkcja sprawdzająca czy król jest zagrożony jeśli jest zrobiony ruch
	private boolean willkingbeindanger(Pole fromcell, Pole tocell) {
		Pole newboardstate[][] = new Pole[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				try {
					newboardstate[i][j] = new Pole(stanSzachownicy[i][j]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					System.out.println("Nie można klonować!");
				}
			}

		if (newboardstate[tocell.x][tocell.y].getFigura() != null)
			newboardstate[tocell.x][tocell.y].removePiece();

		newboardstate[tocell.x][tocell.y].setFigura(newboardstate[fromcell.x][fromcell.y].getFigura());
		if (newboardstate[tocell.x][tocell.y].getFigura() instanceof Krol) {
			((Krol) (newboardstate[tocell.x][tocell.y].getFigura())).setx(tocell.x);
			((Krol) (newboardstate[tocell.x][tocell.y].getFigura())).sety(tocell.y);
		}
		newboardstate[fromcell.x][fromcell.y].removePiece();
		if (((Krol) (newboardstate[getKing(ruch).getx()][getKing(ruch).gety()].getFigura()))
				.isindanger(newboardstate) == true)
			return true;
		else
			return false;
	}

	// eliminuj ruchy ktore narazilyby krola na zagrozenie
	private ArrayList<Pole> filterdestination(ArrayList<Pole> destlist, Pole fromcell) {
		ArrayList<Pole> newlist = new ArrayList<Pole>();
		Pole newboardstate[][] = new Pole[8][8];
		ListIterator<Pole> it = destlist.listIterator();
		int x, y;
		while (it.hasNext()) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					try {
						newboardstate[i][j] = new Pole(stanSzachownicy[i][j]);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}

			Pole tempc = it.next();
			if (newboardstate[tempc.x][tempc.y].getFigura() != null)
				newboardstate[tempc.x][tempc.y].removePiece();
			newboardstate[tempc.x][tempc.y].setFigura(newboardstate[fromcell.x][fromcell.y].getFigura());
			x = getKing(ruch).getx();
			y = getKing(ruch).gety();
			if (newboardstate[fromcell.x][fromcell.y].getFigura() instanceof Krol) {
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).setx(tempc.x);
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].removePiece();
			if ((((Krol) (newboardstate[x][y].getFigura())).isindanger(newboardstate) == false))
				newlist.add(tempc);
		}
		return newlist;
	}

	// mozliwe ruchy kiedy krol jest szachowany
	private ArrayList<Pole> incheckfilter(ArrayList<Pole> destlist, Pole fromcell, int color) {
		ArrayList<Pole> newlist = new ArrayList<Pole>();
		Pole newboardstate[][] = new Pole[8][8];
		ListIterator<Pole> it = destlist.listIterator();
		int x, y;
		while (it.hasNext()) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					try {
						newboardstate[i][j] = new Pole(stanSzachownicy[i][j]);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			Pole tempc = it.next();
			if (newboardstate[tempc.x][tempc.y].getFigura() != null)
				newboardstate[tempc.x][tempc.y].removePiece();
			newboardstate[tempc.x][tempc.y].setFigura(newboardstate[fromcell.x][fromcell.y].getFigura());
			x = getKing(color).getx();
			y = getKing(color).gety();
			if (newboardstate[tempc.x][tempc.y].getFigura() instanceof Krol) {
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).setx(tempc.x);
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].removePiece();
			if ((((Krol) (newboardstate[x][y].getFigura())).isindanger(newboardstate) == false))
				newlist.add(tempc);
		}
		return newlist;
	}

	// sprawdz czy krol jest w macie | gra sie konczy kiedy f zwraca true
	public boolean checkmate(int color) {
		ArrayList<Pole> dlist = new ArrayList<Pole>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (stanSzachownicy[i][j].getFigura() != null && stanSzachownicy[i][j].getFigura().getcolor() == color) {
					dlist.clear();
					dlist = stanSzachownicy[i][j].getFigura().move(stanSzachownicy, i, j);
					dlist = incheckfilter(dlist, stanSzachownicy[i][j], color);
					if (dlist.size() != 0)
						return false;
				}
			}
		}
		return true;
	}
	// ------------------------------------------------------------------

	/*
	 * remis gdy
	 * 
	 * suma = 3 => zostal krol, krol, goniec(skoczek) 
	 * lub 
	 * suma = 0 => zostal krol przeciwko krolowi
	 * 
	 */
	public void remis() {
		int suma = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (stanSzachownicy[i][j].getFigura() != null)
					suma += stanSzachownicy[i][j].getFigura().getWartosc();
			}
		}

		if (suma == 0 || suma == 3) {
			JOptionPane.showMessageDialog(szachownica, "Remis!\n", "Koniec gry", JOptionPane.INFORMATION_MESSAGE);
			end = true;
		} else {
			end = false;
		}
		// return end;
	}
	// ------------------------------------------------------------------

	@SuppressWarnings("deprecation")
	private void koniecGry() {
		cleandestinations(destinationlist);

		if (poprzednie != null)
			poprzednie.removePiece();
		if (ruch == 0) {
			winner = "Białe";
		} else {
			winner = "Czarne";
		}
		JOptionPane.showMessageDialog(szachownica, "Checkmate!!!\n" + winner + " wins");

		// split.add(szachownica);
		end = true;
		Mainboard.disable();
		Mainboard.dispose();
		Mainboard = new Main();
		Mainboard.setVisible(true);
		Mainboard.setResizable(false);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		c = (Pole) arg0.getSource();

		if (poprzednie == null) {
			if (c.getFigura() != null) {
				if (c.getFigura().getcolor() != ruch)
					return;
				c.zaznacz();
				poprzednie = c;
				destinationlist.clear();
				destinationlist = c.getFigura().move(stanSzachownicy, c.x, c.y);
				if (c.getFigura() instanceof Krol)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].ischeck())
						destinationlist = new ArrayList<Pole>(filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		} else {
			if (c.x == poprzednie.x && c.y == poprzednie.y) {
				c.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				poprzednie = null;
			} else if (c.getFigura() == null || poprzednie.getFigura().getcolor() != c.getFigura().getcolor()) {
				if (c.ispossibledestination()) {
					if (c.getFigura() != null)
						c.removePiece();
					c.setFigura(poprzednie.getFigura());
					if (poprzednie.ischeck())
						poprzednie.removecheck();
					poprzednie.removePiece();
					if (getKing(ruch ^ 1).isindanger(stanSzachownicy)) {
						stanSzachownicy[getKing(ruch ^ 1).getx()][getKing(ruch ^ 1).gety()].setcheck();
						if (checkmate(getKing(ruch ^ 1).getcolor())) {
							poprzednie.deselect();
							if (poprzednie.getFigura() != null)
								poprzednie.removePiece();
							koniecGry();
						}
					}
					if (getKing(ruch).isindanger(stanSzachownicy) == false)
						stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].removecheck();
					if (c.getFigura() instanceof Krol) {
						((Krol) c.getFigura()).setx(c.x);
						((Krol) c.getFigura()).sety(c.y);
					}
					changechance();
				}
				if (poprzednie != null) {
					poprzednie.deselect();
					poprzednie = null;
				}
				cleandestinations(destinationlist);
				destinationlist.clear();
			} else if (poprzednie.getFigura().getcolor() == c.getFigura().getcolor()) {
				poprzednie.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				c.zaznacz();
				poprzednie = c;
				destinationlist = c.getFigura().move(stanSzachownicy, c.x, c.y);
				if (c.getFigura() instanceof Krol)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].ischeck())
						destinationlist = new ArrayList<Pole>(filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		if (c.getFigura() != null && c.getFigura() instanceof Krol) {
			((Krol) c.getFigura()).setx(c.x);
			((Krol) c.getFigura()).sety(c.y);
		}
		//sprawdz czy nie ma remisu na desce
		remis();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//remis();
	}

	class START implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		}
	}

	class SelectHandler implements ActionListener {
		private int color;

		SelectHandler(int i) {
			color = i;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

		}
	}

	class Handler implements ActionListener {
		private int color;

		Handler(int i) {
			color = i;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
}
