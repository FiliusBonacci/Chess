package chess;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import figury.Figura;
import figury.Goniec;
import figury.Hetman;
import figury.Krol;
import figury.Pionek;
import figury.Skoczek;
import figury.Wieza;
import utils.Informacja;

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
	private JPanel controlPanel, temp;
	private JPanel gameHistoryPanel = new JPanel();
	private JSplitPane split;
	private JLabel label;

	public static Main Mainboard;
	private boolean end = false;
	private Container content;
	
	Pole[][] KopiastanSzachownicy = new Pole[8][8];
	Figura F;

	private String winner = "";
	static String move;

	private Button nowaGra, zapisz, wczytaj, doTylu, doPrzodu, pomoc;

	private ArrayList<Pole> historiaRuchow = new ArrayList<Pole>();

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
		// bg1 = new Goniec("WB01", "White_Bishop.png", 0);
		// bg2 = new Goniec("WB02", "White_Bishop.png", 0);
		cg1 = new Goniec("BB01", "Black_Bishop.png", 1);
		// cg2 = new Goniec("BB02", "Black_Bishop.png", 1);
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
		gameHistoryPanel = new JPanel();
		gameHistoryPanel.setBorder(BorderFactory.createTitledBorder(null, "White Player", TitledBorder.TOP,
				TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.RED));
		// gameHistoryPanel.setLayout(new BorderLayout());

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

		// --- nowa gra przycisk
		nowaGra = new Button("Nowa gra");
		nowaGra.setBackground(new Color(57, 219, 141)); // zielony pastelowy
		nowaGra.setForeground(Color.white);
		nowaGra.addActionListener(new START());
		nowaGra.setPreferredSize(new Dimension(240, 80));
		nowaGra.setFont(new Font("Arial", Font.BOLD, 16));
		controlPanel.add(nowaGra);
		nowaGra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Mainboard.dispose();
				main(new String[] {});
			}
		});

		// ------ historia gry
		zapisz = new Button("zapisz");
		zapisz.setBackground(new Color(51, 190, 255)); // niebieski
		zapisz.setForeground(Color.white);
		zapisz.addActionListener(new ZAPISZ());
		zapisz.setPreferredSize(new Dimension(120, 40));
		zapisz.setFont(new Font("Arial", Font.BOLD, 16));
		zapisz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				zapisz();
			}
		});

		wczytaj = new Button("wczytaj");
		wczytaj.setBackground(new Color(255, 181, 51)); // pomarancz
		wczytaj.setForeground(Color.white);
		wczytaj.addActionListener(new ZAPISZ());
		wczytaj.setPreferredSize(new Dimension(120, 40));
		wczytaj.setFont(new Font("Arial", Font.BOLD, 16));
		wczytaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				wczytaj();
			}
		});

		doTylu = new Button("do tyłu");
		doTylu.setBackground(new Color(255, 51, 51)); // czerwony
		doTylu.setForeground(Color.white);
		doTylu.addActionListener(new ZAPISZ());
		doTylu.setPreferredSize(new Dimension(120, 40));
		doTylu.setFont(new Font("Arial", Font.BOLD, 16));
		doTylu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wykonajRuchDoTylu();
			}
		});

		doPrzodu = new Button("do przodu");
		doPrzodu.setBackground(new Color(168, 51, 255)); // fiolet
		doPrzodu.setForeground(Color.white);
		doPrzodu.addActionListener(new ZAPISZ());
		doPrzodu.setPreferredSize(new Dimension(120, 40));
		doPrzodu.setFont(new Font("Arial", Font.BOLD, 16));

		// --- nowa gra przycisk
		pomoc = new Button("pomoc");
		pomoc.setBackground(new Color(102, 188, 233)); // zielony pastelowy
		pomoc.setForeground(Color.white);
		// pomoc.addActionListener(new POMOC());
		pomoc.setPreferredSize(new Dimension(120, 40));
		pomoc.setFont(new Font("Arial", Font.BOLD, 16));
		controlPanel.add(pomoc);
		pomoc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"Gra w szachy:\nGra kończy się w momencie zamatowania króla lub pozycji remisowej na szachownicy\n"
								+ "należą do nich król przeciwko królowi lub król przeciwko królowi i lekkiej figurze(Goniec, skoczek)",
						"Pomoc", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		controlPanel.add(zapisz);
		controlPanel.add(wczytaj);
		controlPanel.add(doTylu);
		controlPanel.add(doPrzodu);
		controlPanel.add(pomoc);

		szachownica.setMinimumSize(new Dimension(800, 700));

		controlPanel.setMinimumSize(new Dimension(285, 700));
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, controlPanel);

		content.add(split);

		split.add(szachownica);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// f zmienia ruch z bialego przeciwnika na czarnego
	public void changechance() {
		if (stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].jestSzachowane()) {
			ruch ^= 1;
			koniecGry();
		}
		if (destinationlist.isEmpty() == false)
			cleandestinations(destinationlist);
		if (poprzednie != null)
			poprzednie.odznacz();
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
			newboardstate[tocell.x][tocell.y].usunFigure();

		newboardstate[tocell.x][tocell.y].setFigura(newboardstate[fromcell.x][fromcell.y].getFigura());
		if (newboardstate[tocell.x][tocell.y].getFigura() instanceof Krol) {
			((Krol) (newboardstate[tocell.x][tocell.y].getFigura())).setx(tocell.x);
			((Krol) (newboardstate[tocell.x][tocell.y].getFigura())).sety(tocell.y);
		}
		newboardstate[fromcell.x][fromcell.y].usunFigure();
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
				newboardstate[tempc.x][tempc.y].usunFigure();
			newboardstate[tempc.x][tempc.y].setFigura(newboardstate[fromcell.x][fromcell.y].getFigura());
			x = getKing(ruch).getx();
			y = getKing(ruch).gety();
			if (newboardstate[fromcell.x][fromcell.y].getFigura() instanceof Krol) {
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).setx(tempc.x);
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].usunFigure();
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
				newboardstate[tempc.x][tempc.y].usunFigure();
			newboardstate[tempc.x][tempc.y].setFigura(newboardstate[fromcell.x][fromcell.y].getFigura());
			x = getKing(color).getx();
			y = getKing(color).gety();
			if (newboardstate[tempc.x][tempc.y].getFigura() instanceof Krol) {
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).setx(tempc.x);
				((Krol) (newboardstate[tempc.x][tempc.y].getFigura())).sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].usunFigure();
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
				if (stanSzachownicy[i][j].getFigura() != null
						&& stanSzachownicy[i][j].getFigura().getcolor() == color) {
					dlist.clear();
					dlist = stanSzachownicy[i][j].getFigura().ruch(stanSzachownicy, i, j);
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
	 * suma = 3 => zostal krol, krol, goniec(skoczek) lub suma = 0 => zostal
	 * krol przeciwko krolowi
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
			poprzednie.usunFigure();
		if (ruch == 0) {
			winner = "Białe";
		} else {
			winner = "Czarne";
		}
		JOptionPane.showMessageDialog(szachownica, "Mat\n" + winner + " wygrywają");

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
		c = (Pole) arg0.getSource();

		// pierwszy klik
		if (poprzednie == null) {
			if (c.getFigura() != null) {
				if (c.getFigura().getcolor() != ruch)
					return;
				c.zaznacz();
				poprzednie = c;
				destinationlist.clear();
				destinationlist = c.getFigura().ruch(stanSzachownicy, c.x, c.y);
				if (c.getFigura() instanceof Krol)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].jestSzachowane())
						destinationlist = new ArrayList<Pole>(filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
			// zapis w historii
			HistoriaRuchow.addRuch(c);
//			zapiszStanSzachownicy();

		}
		// drugi klik
		else {
			if (c.x == poprzednie.x && c.y == poprzednie.y) {
				c.odznacz();
				cleandestinations(destinationlist);
				destinationlist.clear();
				poprzednie = null;
				// docelowe pole jest puste lub jeśli figura jest innego koloru
				// na polu docelowym
			} else if (c.getFigura() == null || poprzednie.getFigura().getcolor() != c.getFigura().getcolor()) {
				if (c.jestMozliwymCelem()) {
					if (c.getFigura() != null) // jeśli stoi na polu figura
						c.usunFigure(); // to ją usun

					if (poprzednie.getFigura() instanceof Pionek && poprzednie.x == 1) {
						Hetman bh = new Hetman("WQ", "White_Queen.png", 0);
						c.setFigura(bh);
					} else {
						c.setFigura(poprzednie.getFigura()); // ustaw figure na
																// pole docelowe
						try {
							historiaRuchow.add(new Pole(poprzednie));
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					}

					if (poprzednie.jestSzachowane())
						poprzednie.usunSzachowanePole();

					poprzednie.usunFigure(); // i usun z pola poprzedniego

					if (getKing(ruch ^ 1).isindanger(stanSzachownicy)) {
						stanSzachownicy[getKing(ruch ^ 1).getx()][getKing(ruch ^ 1).gety()].setcheck();
						if (checkmate(getKing(ruch ^ 1).getcolor())) {
							poprzednie.odznacz();
							if (poprzednie.getFigura() != null)
								poprzednie.usunFigure();
							koniecGry();
						}
					}
					if (getKing(ruch).isindanger(stanSzachownicy) == false)
						stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].usunSzachowanePole();
					if (c.getFigura() instanceof Krol) {
						((Krol) c.getFigura()).setx(c.x);
						((Krol) c.getFigura()).sety(c.y);
					}
					changechance();
				}
				if (poprzednie != null) {
					poprzednie.odznacz();
					poprzednie = null;
				}
				cleandestinations(destinationlist);
				destinationlist.clear();

			} else if (poprzednie.getFigura().getcolor() == c.getFigura().getcolor()) {
				poprzednie.odznacz();
				cleandestinations(destinationlist);
				destinationlist.clear();
				c.zaznacz();
				poprzednie = c;
				destinationlist = c.getFigura().ruch(stanSzachownicy, c.x, c.y);
				if (c.getFigura() instanceof Krol)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (stanSzachownicy[getKing(ruch).getx()][getKing(ruch).gety()].jestSzachowane())
						destinationlist = new ArrayList<Pole>(filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
			// zapis w historii
			HistoriaRuchow.addRuch(c);
			
		}
		if (c.getFigura() != null && c.getFigura() instanceof Krol) {
			((Krol) c.getFigura()).setx(c.x);
			((Krol) c.getFigura()).sety(c.y);
		}
		// sprawdz czy nie ma remisu na desce
		remis();
	}

	class START implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		}
	}

	class ZAPISZ implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		}
	}

	/**
	 * Zapisuje aktualna pozycje do pliku.
	 */
	private void zapisz() {
		ArrayList<Pole> pola = new ArrayList<Pole>();
		Component[] components = szachownica.getComponents();
		for (Component component : components) {
			if (component instanceof Pole) {
				Figura f = ((Pole) component).getFigura();
				if (f != null)
					pola.add((Pole) component);
			}
		}
		zapiszDoPliku(pola.toArray(new Pole[0]));
	}

	private void zapiszDoPliku(Pole[] pola) {
		File saveGame = new File(
				new File("").getAbsolutePath() + File.separator + "save" + File.separator + "save_game.abc");
		Informacja[] informacje = new Informacja[pola.length];
		if (saveGame.exists())
			saveGame.delete();
		copyToInfo(pola, informacje);
		FileOutputStream fileOutput = null;
		ObjectOutputStream objectOutput = null;
		try {
			fileOutput = new FileOutputStream(saveGame);
			objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(informacje);
			objectOutput.close();
			JOptionPane.showMessageDialog(null, "Zapisałem ;)", "OK ;)",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nie moge zapisac do pliku!", "Blad zapisywania do pliku",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void copyToInfo(Pole[] pola, Informacja[] informacje) {
		for (int i = 0; i < pola.length; i++) {
			informacje[i] = new Informacja();
			informacje[i].setFigura(pola[i].getFigura());
			informacje[i].setX(pola[i].x);
			informacje[i].setY(pola[i].y);
		}

	}

	private void wczytaj() {
		Informacja[] informacje = null;
		File saveGame = new File(
				new File("").getAbsolutePath() + File.separator + "save" + File.separator + "save_game.abc");
		if (saveGame.exists()) {
			FileInputStream input;
			try {
				input = new FileInputStream(saveGame);
				ObjectInputStream objectStream = new ObjectInputStream(input);
				informacje = (Informacja[]) objectStream.readObject();
				objectStream.close();
				// dopisac ustawienie na tablicy
				// w obiekcie informacje sa dane ktore nalezy wrzucic na
				// szachownice
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Nie moge odczytac z pliku!", "Blad wczytywania z pliku",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	private void clearPosition() {
		Figura P;
		Pole pole;
		stanSzachownicy = new Pole[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				P = null;
				pole = new Pole(i, j, P);
				pole.addMouseListener(this);
				szachownica.add(pole);
				stanSzachownicy[i][j] = pole;
			}
		Mainboard.repaint();
	}

	protected void wykonajRuchDoTylu() {
		/*
		 * for (int i = historiaRuchow.size() - 1; i >= 0; i--) { c.x =
		 * historiaRuchow.get(i).x; c.y = historiaRuchow.get(i).y;
		 * c.setFigura(historiaRuchow.get(i).getFigura()); Pole pole = new
		 * Pole(c.x, c.y, c.getFigura()); pole.addMouseListener(this);
		 * szachownica.add(pole); stanSzachownicy[c.x][c.y] = pole; }
		 */

		Pole po;
		Figura fig;

		// 2.
		// wez przedostatni ruch
		po = (Pole) HistoriaRuchow.historia[HistoriaRuchow.licznikHistori - 2][0];
		fig = (Figura) HistoriaRuchow.historia[HistoriaRuchow.licznikHistori - 2][1];

		// figure z ostatniego ruchu na poprzednie pole
		stanSzachownicy[po.x][po.y].setFigura(fig);

		// 1. wez ostatni ruch
		po = (Pole) HistoriaRuchow.historia[HistoriaRuchow.licznikHistori - 1][0];
		fig = (Figura) HistoriaRuchow.historia[HistoriaRuchow.licznikHistori - 1][1];

		// usun figure z obecnego pola
		stanSzachownicy[po.x][po.y].usunFigure();

		changechance();
		
		
//		odswiezStanSzachownicy();

	}
	// -------------------------------

	

	void zapiszStanSzachownicy() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				try {
					if (stanSzachownicy[i][j].getFigura() != null) {
						F = stanSzachownicy[i][j].getFigura();
						KopiastanSzachownicy[i][j].setFigura(F);
					}
				} catch (Exception e) {
					System.out.println("Nie złapałem figury z pola");
					e.printStackTrace();
				}

			}
		}
	}

	void odswiezStanSzachownicy() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (KopiastanSzachownicy[i][j].getFigura() != null) {
					stanSzachownicy[i][j].setFigura(KopiastanSzachownicy[i][j].getFigura());
				}

			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
