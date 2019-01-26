package chessView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import chess03.Game;
import chess03.Piece;
import chessView.PieceView.Pieces;

import java.util.concurrent.TimeUnit;

//visuals for the board
public class BoardView extends JComponent implements ActionListener, MouseListener {

	//------------------------------------------Fields----------------------------------------------------------------
	private SquareView[] squares;
	private JFrame frame;
	private JPanel main;
	private JPanel supre;
	private JPanel rows;
	private JPanel columns;
	private JPanel menu;
	private JLabel prompt;
	private JButton option1;
	private JButton option2;
	private JButton option3;
	private String[] alphabet;
	private JLabel[] rowLabels;
	private JLabel[] columnLabels;
	private Color light;
	private Color dark;
	private boolean clickedOption1;
	private SquareView storedSquare;
	private ArrayList<ViewListener> listeners;
	private boolean flipped;
	private boolean whiteTurn;
	private boolean finishedSetup;
	private boolean vsEngine;
	private boolean engineIsWhite;
	private SquareView engineMove;
	
	//for promotions.indicates need
	private boolean promotion;
	private Pieces promoType;
	private boolean promoWhite;
	
	//-----------------------------------------Constructor-------------------------------------------------------------
	public BoardView() {
		light = new Color(250,230,210); //for light squares
		dark = new Color(152,105,96);  //for dark squares
		squares = new SquareView[64]; //array of squareViews
		alphabet = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"}; //for column labels
		rowLabels = new JLabel[8];
		columnLabels = new JLabel[8];
		clickedOption1 = false;
		storedSquare = null;
		listeners = new ArrayList<ViewListener>();
		flipped = false;
		whiteTurn = true;
		promotion = false;
		promoType = null;
		promoWhite = false;
		finishedSetup = false;
		vsEngine = false;
		engineIsWhite = false;
		engineMove = null;
		
		frame = new JFrame();
		frame.setTitle("Test");
		frame.setDefaultCloseOperation(3);
		
		//MAIN jpanel holding all others
		//frame's content pane is this jpanel
		main = new JPanel();
		main.setLayout(new BorderLayout());
		
		//main jpanel, contains all squares
		supre = new JPanel();
		supre.setLayout(new GridLayout(8,8));
		supre.setBorder(BorderFactory.createLineBorder(dark, 3, false));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				SquareView panel = new SquareView(7-i,j);
				if ((i+j)%2 == 0) {
					panel.setBackground(light);
				} else {
					panel.setBackground(dark);
				}
				panel.addMouseListener(this);
				squares[(i*8)+j] = panel;
				supre.add(panel);
			}
		}
		
		//jpanel on left side, contains row numbers
		rows = new JPanel();
		rows.setLayout(new GridLayout(8,1));
		for (int i = 0; i < 8; i++) {
			JLabel l = new JLabel(Integer.toString(8-i));
			rows.add(l);
			l.setHorizontalAlignment(JLabel.CENTER);
			rowLabels[i] = l;
		}
		
		//jpanel on bottom, contains file names
		columns = new JPanel();
		columns.setLayout(new GridLayout(1,8));
		for (int i = 0; i < 8; i++) {
			JLabel l = new JLabel(alphabet[i]);
			columns.add(l);
			l.setHorizontalAlignment(JLabel.CENTER);
			columnLabels[i] = l;
		}
		
		//jpanel on top, has control buttons in menu
		menu = new JPanel();
		menu.setLayout((new GridLayout(1,4)));
		prompt = new JLabel("Play vs.");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		menu.add(prompt);
		option1 = new JButton("Human");
		option1.setActionCommand("Option1");
		option1.addActionListener(this);
		menu.add(option1);
		option2 = new JButton("Computer");
		option2.setActionCommand("Option2");
		option2.addActionListener(this);
		menu.add(option2);
		option3 = new JButton("Random");
		option3.setActionCommand("Option3");
		option3.addActionListener(this);
		option3.setVisible(false);
		menu.add(option3);
		
		
		//add the panels
		main.add(supre, BorderLayout.CENTER);
		main.add(rows, BorderLayout.WEST);
		main.add(columns, BorderLayout.SOUTH);
		main.add(menu, BorderLayout.NORTH);
		
		//set the content
		frame.setContentPane(main);
		frame.setMinimumSize(new Dimension(495,555));
		frame.setMaximumSize(new Dimension(495,555));
		
		
		
		frame.pack();
		frame.setVisible(true);
		
		//check square dimensions
		//square dimensions should be 60x60
//		for (int i = 0; i < 64; i++) {
//			System.out.println("Square " + (i+1) + " dimensions: " + "(" + squares[i].getX() + "," + squares[i].getY() + "), " + squares[i].getWidth() + "x" + squares[i].getHeight());
//		}
		
		
		
		
	}
	
	
	//-------------------------------------------------Getters--------------------------------------------------------
	
	//returns the jpanel in the squares array representing the square of the given name
	public SquareView getSquareByName(String str) {
		int column = 0;
		int row;
		if (flipped) {
			row = (Integer.parseInt(str.substring(1,2))) -1;
		} else {
			row = (8 - (Integer.parseInt(str.substring(1,2))));
		}
		for (int i = 0; i < 8; i++) {
			if (str.substring(0,1).equalsIgnoreCase(alphabet[i])) {
				if (flipped) {
					column = 7 - i;
				} else {
					column = i;
				}
				break;
			}
		}
		return squares[(row*8) + column];
	}
	
	//for flipping board
	public int getIndexOfLetter(String s) {
		int n = 0;
		switch (s) {
		case "A": break;
		case "B": n = 1; break;
		case "C": n = 2; break;
		case "D": n = 3; break;
		case "E": n = 4; break;
		case "F": n = 5; break;
		case "G": n = 6; break;
		case "H": n = 7; break;
		}
		return n;
	}
	
	
	//--------------------------------------------------Misc---------------------------------------------------------
	
	public void addViewListener(ViewListener l) {
		listeners.add(l);
	}
	
	public void notifyListeners(ViewEvent e) {
		for (ViewListener l: listeners) {
			l.handleViewEvent(e);
		}
	}
	
	
	//called from the controller to light up squares for valid moves
	public void receiveValidMovesFromClick(List<SquareView> squares) {
		for (SquareView s: squares) {
			s.setBackground(Color.YELLOW);
		}
	}
	
	//called when we want the yellow choice squares to go back to original colors
	public void revertSquareColors() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (squares[(i*8)+j].getBackground().equals(Color.YELLOW)) {
					if ((i+j)%2 ==0) {
						squares[(i*8)+j].setBackground(light);
					} else {
						squares[(i*8)+j].setBackground(dark);
					}
				}
			}
			
		}
	}
	
	//every square up until halfway through the array swaps info with the square at index 63-i
	public void flipBoard() {
		for (int i = 0; i < 32; i++) {
			int row = squares[i].getRow();
			int column = squares[i].getColumn();
			boolean occ = squares[i].getIsOccupied();
			boolean occWhite = squares[i].getOccIsWhite();
			PieceView.Pieces p = squares[i].getPiece();
			
			squares[i].setRow(squares[63-i].getRow());
			squares[i].setColumn(squares[63-i].getColumn());
			squares[i].setIsOccupied(squares[63-i].getIsOccupied());
			squares[i].setOccIsWhite(squares[63-i].getOccIsWhite());
			squares[i].setPieces(squares[63-i].getPiece());
			
			squares[63-i].setRow(row);
			squares[63-i].setColumn(column);
			squares[63-i].setIsOccupied(occ);
			squares[63-i].setOccIsWhite(occWhite);
			squares[63-i].setPieces(p);
		}
		for (int i = 0; i < 8; i++) {
			rowLabels[i].setText(Integer.toString(9 - (Integer.parseInt(rowLabels[i].getText()))));
			columnLabels[i].setText(alphabet[7 - getIndexOfLetter(columnLabels[i].getText())]);
		}
		
		//cosmetic choice; i like this look better
		for (int i = 0; i < 64; i++) {
			squares[i].paintComponent(squares[i].getGraphics());
			//squares[i].repaint();
		}
		flipped = !flipped;
	}
	
	//called during a castle. moves the rook
	public void completeCastleMove(boolean white, boolean kingside) {
		if (white) {
			if (kingside) {
				getSquareByName("h1").clearSquare();
				getSquareByName("f1").setOccupant(Pieces.ROOK, true);
			} else {
				getSquareByName("a1").clearSquare();
				getSquareByName("d1").setOccupant(Pieces.ROOK, true);
			}
		} else {
			if (kingside) {
				getSquareByName("h8").clearSquare();
				getSquareByName("f8").setOccupant(Pieces.ROOK, false);
			} else {
				getSquareByName("a8").clearSquare();
				getSquareByName("d8").setOccupant(Pieces.ROOK, false);
			}
		}
	}
	
	//called for en passent. removes the captured pawn
	public void completeEnPassentMove(boolean white, int column) {
		if (white) {
			String s = alphabet[column].toLowerCase() + 5;
			getSquareByName(s).clearSquare();
		} else {
			String s = alphabet[column].toLowerCase() + 4;
			getSquareByName(s).clearSquare();
		}
	}
	 
	//called when a pawn promotes. creates a popup prompting user to pick a piece to promote to
	public void completePromotionMove(chess03.Piece.Type type, SquareView s, boolean white) {
		if (type.equals(chess03.Piece.Type.QUEEN)) {
			getSquareByName(s.getName()).setOccupant(Pieces.QUEEN, white);
		} else if (type.equals(chess03.Piece.Type.ROOK)) {
			getSquareByName(s.getName()).setOccupant(Pieces.ROOK, white);
		} else if (type.equals(chess03.Piece.Type.BISHOP)) {
			getSquareByName(s.getName()).setOccupant(Pieces.BISHOP, white);
		} else {
			getSquareByName(s.getName()).setOccupant(Pieces.KNIGHT, white);
		}
		promotion = true;
//		Object[] options = {"KNIGHT", "BISHOP", "ROOK", "QUEEN"};
//		int n = JOptionPane.showOptionDialog(frame, "Choose Your Promotion", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
//		SquareView newS = getSquareByName(s.getName());
//		if (n == 3) {
//			promotion = true;
//			promoType = Pieces.QUEEN;
//			promoWhite = white;
//		} else if (n == 2) {
//			promotion = true;
//			promoType = Pieces.ROOK;
//			promoWhite = white;
//		} else if (n == 1) {
//			promotion = true;
//			promoType = Pieces.BISHOP;
//			promoWhite = white;
//		} else if (n == 0) {
//			promotion = true;
//			promoType = Pieces.KNIGHT;
//			promoWhite = white;
//		}
//		//tells the model which piece to replace the pawn with
//		notifyListeners(new PromotionEvent(newS, null, promoType, promoWhite));
	}
	
	
	public void completeCheckEvent(boolean white) {
		for (SquareView s: squares) {
			if (s.getIsOccupied()) {
				if (s.getOccIsWhite() == white && s.getPiece().equals(PieceView.Pieces.KING)) {
					s.setBackground(Color.RED);
					Timer t = new Timer();
					t.schedule(new TimerTask() { public void run() {
						if ((s.getRow() + s.getColumn()) % 2 == 1) {
							s.setBackground(light);
						} else {
							s.setBackground(dark);
						}
					}}, 500l);
					break;
				}
			}
		}
	}
	
	public void completeThinkingEvent(boolean started, chess03.Square s1, chess03.Square s2) {
		if (started) {
			prompt.setText("Thinking...");
			
		//remember to account for special moves
		} else {
			SquareView init = getSquareByName(s1.getName());
			SquareView fin = getSquareByName(s2.getName());
			fin.setOccupant(init.getPiece(), init.getOccIsWhite());
			init.clearSquare();
			
			if (engineMove != null) {
				if (((engineMove.getRow() + engineMove.getColumn()) % 2) == 1) {
					engineMove.setBackground(light);
				} else {
					engineMove.setBackground(dark);
				}
			}
			
			engineMove = fin;
			engineMove.setBackground(Color.GREEN);
			whiteTurn = !whiteTurn;
			prompt.setText("Options:");
		}
	}
	
	//called for a decisive outcome. creates a popup window announcing the result
	public void declareOutcome(Game.Outcomes o) {
		String s = "";
		if (o.equals(Game.Outcomes.BLACK_WINS)) {
			s = "BLACK WINS!";
		} else if (o.equals(Game.Outcomes.WHITE_WINS)) {
			s = "WHITE WINS!";
		} else {
			s = "DRAW";
		}
		JOptionPane.showMessageDialog(frame, s, "", JOptionPane.INFORMATION_MESSAGE, null);
		
	}
	
	//-----------------------------------------ActionListener--------------------------------------------------------

	//button controls
	public void actionPerformed(ActionEvent arg0) {
		
		String cmd = arg0.getActionCommand();
		
		//step one
		if (prompt.getText().equals("Play vs.")) {
			
			if (cmd.equals("Option1")) {
				prompt.setText("Play as:");
				option1.setText("White");
				option2.setText("Black");
				option3.setVisible(true);
				clickedOption1 = true;
			} else if (cmd.equals("Option2")) {
				prompt.setText("Play as:");
				option1.setText("White");
				option2.setText("Black");
				option3.setVisible(true);
			}
		//step two
		} else if (prompt.getText().equals("Play as:")){
			prompt.setText("Options:");
			option1.setText("Resign");
			option2.setText("Offer draw");
			option3.setText("Flip Board");
			if (cmd.equals("Option1")) {
				if (!clickedOption1) {
					//not very pretty, please change
					//controller needs viewevents to have a valid initial square
					notifyListeners(new EstablishEngineEvent(getSquareByName("a1"),false));
					vsEngine = true;
					engineIsWhite = false;
				} else {
					notifyListeners(new EstablishHumanEvent(getSquareByName("a1")));
				}
			} else if (cmd.equals("Option2")) {
				flipBoard();
				if (!clickedOption1) {
					notifyListeners(new EstablishEngineEvent(getSquareByName("a1"),true));
					vsEngine = true;
					engineIsWhite = true;
				} else {
					notifyListeners(new EstablishHumanEvent(getSquareByName("a1")));
				}
			} else {
				if (!clickedOption1) {
					if (Math.random() < 0.5) {
						notifyListeners(new EstablishEngineEvent(getSquareByName("a1"),false));
						vsEngine = true;
						engineIsWhite = false;
					} else {
						flipBoard();
						notifyListeners(new EstablishEngineEvent(getSquareByName("a1"),true));
						vsEngine = true;
						engineIsWhite = true;
						
					}
				} else {
					notifyListeners(new EstablishHumanEvent(getSquareByName("a1")));
					if (Math.random() > 0.5) {
						flipBoard();
					}
				}
				
			}
			finishedSetup = true;
		} else {
			if (cmd.equals("Option1")) {
				if (whiteTurn) {
					declareOutcome(Game.Outcomes.BLACK_WINS);
				} else {
					declareOutcome(Game.Outcomes.WHITE_WINS);
				}
			} else if (cmd.equals("Option2")) {
				if (vsEngine) {
					//see what engine thinks
				} else {
					int n = JOptionPane.showConfirmDialog(frame, "Do You Agree To A Draw?");
					if (n == 0) {
						declareOutcome(Game.Outcomes.DRAW);
					}
				}
			} else {
				flipBoard();
			}
		}
	}
	
	
	//-----------------------------------------MouseListener---------------------------------------------------------

	
	//current, not very advanced
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (finishedSetup) {
			SquareView s = (SquareView) arg0.getSource();
			if (storedSquare == null) {
				if (s.getIsOccupied()) {
					if (s.getOccIsWhite() == whiteTurn) {
						if (!vsEngine || engineIsWhite != s.getOccIsWhite()) {
							notifyListeners(new GaugeEvent(s));
							storedSquare = s;
						}
					}
				} 
			} else {
				if (!s.equals(storedSquare)) {
					if (s.getBackground().equals(Color.YELLOW)) {
						if (!promotion) {
							s.setOccupant(storedSquare.getPiece(), storedSquare.getOccIsWhite());
							storedSquare.clearSquare();
						} else {
							promotion = false;
						}
						notifyListeners(new MoveEvent(storedSquare, s));
						whiteTurn = !whiteTurn;
					}
				} 
				storedSquare = null;
				revertSquareColors();
			}
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
		
//		SquareView s = (SquareView) arg0.getSource();
//		if (storedPieceType == null) {
//			storedWhite = s.getOccIsWhite();
//			storedPieceType = s.getPiece();
//		} else {
//			if (!s.equals(clickedSquare)) {
//				s.setOccupant(storedPieceType, storedWhite);
//				clickedSquare.clearSquare();
//			}
//			storedPieceType = null;
//		}
//		
//		clickedSquare = s;
		
//		SquareView s = (SquareView) arg0.getSource();
//		System.out.println(s.getName());
//		clickedSquare = s;
//		storedWhite = s.getOccIsWhite();
//		storedPieceType = s.getPiece();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
//		SquareView s = (SquareView) arg0.getSource();
//		System.out.println(s.getName());
//		if (storedPieceType != null) {
//			s.setOccupant(storedPieceType, storedWhite);
//		}
//		clickedSquare = null;
//		storedPieceType = null;
	}
	
}
