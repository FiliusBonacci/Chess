package chess;

import javax.swing.*;
import pieces.*;
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
	private static final int Height = 700;
	private static final int Width = 1110;
	private static Rook wr01, wr02, br01, br02;
	private static Knight wk01, wk02, bk01, bk02;
	private static Bishop wb01, wb02, bb01, bb02;
	private static Pawn wp[], bp[];
	private static Queen wq, bq;
	private static King wk, bk;
	private Cell c, previous;
	private int chance = 0;
	private Cell boardState[][];
	private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
	// private Player White = null, Black = null;
	private JPanel board = new JPanel(new GridLayout(8, 8));
	// private JPanel wdetails=new JPanel(new GridLayout(3,3));
	// private JPanel bdetails=new JPanel(new GridLayout(3,3));
	// private JPanel wcombopanel = new JPanel();
	// private JPanel bcombopanel = new JPanel();
	private JPanel controlPanel, temp, displayTime;
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

		// wr01 = new Rook("WR01", "White_Rook.png", 0);
		// wr02 = new Rook("WR02", "White_Rook.png", 0);
		// br01 = new Rook("BR01", "Black_Rook.png", 1);
		// br02 = new Rook("BR02", "Black_Rook.png", 1);
		// wk01 = new Knight("WK01", "White_Knight.png", 0);
		// wk02 = new Knight("WK02", "White_Knight.png", 0);
		// bk01 = new Knight("BK01", "Black_Knight.png", 1);
		// bk02 = new Knight("BK02", "Black_Knight.png", 1);
		wb01 = new Bishop("WB01", "White_Bishop.png", 0);
		wb02 = new Bishop("WB02", "White_Bishop.png", 0);
		bb01 = new Bishop("BB01", "Black_Bishop.png", 1);
		bb02 = new Bishop("BB02", "Black_Bishop.png", 1);
		// wq = new Queen("WQ", "White_Queen.png", 0);
		// bq = new Queen("BQ", "Black_Queen.png", 1);
		wk = new King("WK", "White_King.png", 0, 2, 2);
		bk = new King("BK", "Black_King.png", 1, 6, 7);
		wp = new Pawn[8];
		bp = new Pawn[8];
		for (int i = 0; i < 8; i++) {
			wp[i] = new Pawn("WP0" + (i + 1), "White_Pawn.png", 0);
			bp[i] = new Pawn("BP0" + (i + 1), "Black_Pawn.png", 1);
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
		board = new JPanel(new GridLayout(8, 8));

		board.setMinimumSize(new Dimension(800, 700));
		ImageIcon img = new ImageIcon(this.getClass().getResource("icon.png"));
		this.setIconImage(img.getImage());

		Cell cell;
		// board.setBorder(BorderFactory.createLoweredBevelBorder());
		pieces.Piece P;
		content = getContentPane();
		setSize(Width, Height);
		setTitle("Szachy - Paweł Jadanowski");
		content.setBackground(Color.black);
		controlPanel = new JPanel();
		content.setLayout(new BorderLayout());
		controlPanel.setLayout(new FlowLayout());

		// ustawianie figur na szachownicy
		boardState = new Cell[8][8];

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				P = null;

				if (i == 2 && j == 2)
					P = wk; // biały król na c6
				else if (i == 2 && j == 3)
					P = wp[0]; // biały pionek na d6

				else if (i == 6 && j == 7)
					P = bk; // czarny król na h2
				else if (i == 3 && j == 7)
					P = bb01; // czarny goniec na h5

				cell = new Cell(i, j, P);
				cell.addMouseListener(this);
				board.add(cell);
				boardState[i][j] = cell;
			}

		start = new Button("Nowa gra");
		start.setBackground(new Color(57, 219, 141)); // zielony pastelowy
		start.setForeground(Color.white);
		start.addActionListener(new START());
		start.setPreferredSize(new Dimension(120, 40));
		start.setFont(new Font("Arial", Font.BOLD, 16));
		controlPanel.add(start);

		board.setMinimumSize(new Dimension(800, 700));

		controlPanel.setMinimumSize(new Dimension(285, 700));
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, controlPanel);

		content.add(split);

	
		split.add(board);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// A function to change the chance from White Player to Black Player or vice
	// verse
	// It is made public because it is to be accessed in the Time Class
	public void changechance() {
		if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck()) {
			chance ^= 1;
			gameend();
		}
		if (destinationlist.isEmpty() == false)
			cleandestinations(destinationlist);
		if (previous != null)
			previous.deselect();
		previous = null;
		chance ^= 1;

	}

	private King getKing(int color) {
		if (color == 0)
			return wk;
		else
			return bk;
	}

	// funkcja czyszczaca zaznaczenie mozliwych ruchów
	private void cleandestinations(ArrayList<Cell> destlist) {
		ListIterator<Cell> it = destlist.listIterator();
		while (it.hasNext())
			it.next().removepossibledestination();
	}

	// funkcja wskazujaca mozliwe ruchy poprzez zaznaczanie pol na zielono
	private void highlightdestinations(ArrayList<Cell> destlist) {
		ListIterator<Cell> it = destlist.listIterator();
		while (it.hasNext())
			it.next().setpossibledestination();
	}

	// funkcja sprawdzająca czy król jest zagrożony jeśli jest zrobiony ruch
	private boolean willkingbeindanger(Cell fromcell, Cell tocell) {
		Cell newboardstate[][] = new Cell[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				try {
					newboardstate[i][j] = new Cell(boardState[i][j]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					System.out.println("Nie można klonować!");
				}
			}

		if (newboardstate[tocell.x][tocell.y].getpiece() != null)
			newboardstate[tocell.x][tocell.y].removePiece();

		newboardstate[tocell.x][tocell.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
		if (newboardstate[tocell.x][tocell.y].getpiece() instanceof King) {
			((King) (newboardstate[tocell.x][tocell.y].getpiece())).setx(tocell.x);
			((King) (newboardstate[tocell.x][tocell.y].getpiece())).sety(tocell.y);
		}
		newboardstate[fromcell.x][fromcell.y].removePiece();
		if (((King) (newboardstate[getKing(chance).getx()][getKing(chance).gety()].getpiece()))
				.isindanger(newboardstate) == true)
			return true;
		else
			return false;
	}

	// eliminuj ruchy ktore narazilyby krola na zagrozenie
	private ArrayList<Cell> filterdestination(ArrayList<Cell> destlist, Cell fromcell) {
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newboardstate[][] = new Cell[8][8];
		ListIterator<Cell> it = destlist.listIterator();
		int x, y;
		while (it.hasNext()) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					try {
						newboardstate[i][j] = new Cell(boardState[i][j]);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}

			Cell tempc = it.next();
			if (newboardstate[tempc.x][tempc.y].getpiece() != null)
				newboardstate[tempc.x][tempc.y].removePiece();
			newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
			x = getKing(chance).getx();
			y = getKing(chance).gety();
			if (newboardstate[fromcell.x][fromcell.y].getpiece() instanceof King) {
				((King) (newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
				((King) (newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].removePiece();
			if ((((King) (newboardstate[x][y].getpiece())).isindanger(newboardstate) == false))
				newlist.add(tempc);
		}
		return newlist;
	}

	// mozliwe ruchy kiedy krol jest szachowany
	private ArrayList<Cell> incheckfilter(ArrayList<Cell> destlist, Cell fromcell, int color) {
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newboardstate[][] = new Cell[8][8];
		ListIterator<Cell> it = destlist.listIterator();
		int x, y;
		while (it.hasNext()) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					try {
						newboardstate[i][j] = new Cell(boardState[i][j]);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			Cell tempc = it.next();
			if (newboardstate[tempc.x][tempc.y].getpiece() != null)
				newboardstate[tempc.x][tempc.y].removePiece();
			newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
			x = getKing(color).getx();
			y = getKing(color).gety();
			if (newboardstate[tempc.x][tempc.y].getpiece() instanceof King) {
				((King) (newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
				((King) (newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].removePiece();
			if ((((King) (newboardstate[x][y].getpiece())).isindanger(newboardstate) == false))
				newlist.add(tempc);
		}
		return newlist;
	}

	// sprawdz czy krol jest w macie | gra sie konczy kiedy f zwraca true
	public boolean checkmate(int color) {
		ArrayList<Cell> dlist = new ArrayList<Cell>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardState[i][j].getpiece() != null && boardState[i][j].getpiece().getcolor() == color) {
					dlist.clear();
					dlist = boardState[i][j].getpiece().move(boardState, i, j);
					dlist = incheckfilter(dlist, boardState[i][j], color);
					if (dlist.size() != 0)
						return false;
				}
			}
		}
		return true;
	}
	//------------------------------------------------------------------
	
	/* remis gdy 
	 * 
	 * suma = 3 => zostal krol krol goniec (skoczek)
	 * 		lub
	 * suma = 0 => zostal krol przeciwko krolowi
	 * 
	 */
	public void remis() {
		int suma = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardState[i][j].getpiece() != null)
					suma += boardState[i][j].getpiece().getWartosc();
			}
		}
		
		JOptionPane.showMessageDialog(board, "Remis!\n");
		
		if (suma == 0 || suma == 3) {
			end = true;
		} else {
			end = false;
		}
		//return end;
	}
	//------------------------------------------------------------------
	
	@SuppressWarnings("deprecation")
	private void gameend() {
		cleandestinations(destinationlist);

		if (previous != null)
			previous.removePiece();
		if (chance == 0) {
			winner = "Białe";
		} else {
			winner = "Czarne";
		}
		JOptionPane.showMessageDialog(board, "Checkmate!!!\n" + winner + " wins");

		//split.add(board);
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
		c = (Cell) arg0.getSource();
		
		if (previous == null) {
			if (c.getpiece() != null) {
				if (c.getpiece().getcolor() != chance)
					return;
				c.select();
				previous = c;
				destinationlist.clear();
				destinationlist = c.getpiece().move(boardState, c.x, c.y);
				if (c.getpiece() instanceof King)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
						destinationlist = new ArrayList<Cell>(filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		} else {
			if (c.x == previous.x && c.y == previous.y) {
				c.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				previous = null;
			} else if (c.getpiece() == null || previous.getpiece().getcolor() != c.getpiece().getcolor()) {
				if (c.ispossibledestination()) {
					if (c.getpiece() != null)
						c.removePiece();
					c.setPiece(previous.getpiece());
					if (previous.ischeck())
						previous.removecheck();
					previous.removePiece();
					if (getKing(chance ^ 1).isindanger(boardState)) {
						boardState[getKing(chance ^ 1).getx()][getKing(chance ^ 1).gety()].setcheck();
						if (checkmate(getKing(chance ^ 1).getcolor())) {
							previous.deselect();
							if (previous.getpiece() != null)
								previous.removePiece();
							gameend();
						}
					}
					if (getKing(chance).isindanger(boardState) == false)
						boardState[getKing(chance).getx()][getKing(chance).gety()].removecheck();
					if (c.getpiece() instanceof King) {
						((King) c.getpiece()).setx(c.x);
						((King) c.getpiece()).sety(c.y);
					}
					changechance();
				}
				if (previous != null) {
					previous.deselect();
					previous = null;
				}
				cleandestinations(destinationlist);
				destinationlist.clear();
			} else if (previous.getpiece().getcolor() == c.getpiece().getcolor()) {
				previous.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				c.select();
				previous = c;
				destinationlist = c.getpiece().move(boardState, c.x, c.y);
				if (c.getpiece() instanceof King)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
						destinationlist = new ArrayList<Cell>(filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		if (c.getpiece() != null && c.getpiece() instanceof King) {
			((King) c.getpiece()).setx(c.x);
			((King) c.getpiece()).sety(c.y);
		}
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
		remis();
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
