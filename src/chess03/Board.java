package chess03;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import chessView.SquareView;
import chessView.PieceView.Pieces;

public class Board {

	
	//--------------------------------------Fields--------------------------------------------------------------------
	private Square[] squares;
	
//	private List<Piece> pieces;
	
	private Player p1;
	private Player p2;
	private GameModel game;
	private int equalCount;
	private int halfTurnAdded;
	
	
	//--------------------------------------Constructor--------------------------------------------------------------
	public Board() {
		Square[] tempSquares = new Square[64];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tempSquares[(i *8) + j] = new SquareImpl(i, j);
			}
		}
		this.squares = tempSquares;
		
//		this.pieces = new ArrayList<Piece>();
		
		this.p1 = null;
		this.p2 = null;
		this.game = null;
		this.equalCount = 1;
		this.halfTurnAdded = 0;
	}
	
	
	//---------------------------------------Getters-----------------------------------------------------------------
	public Square[] getSquares() {
		return this.squares;
	}
	
	public List<Piece> getPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		if (getWhitePlayer() != null && getBlackPlayer() != null) {
			for (Piece p: getWhitePlayer().getPieces()) {
				pieces.add(p);
			}
			for (Piece p: getBlackPlayer().getPieces()) {
				pieces.add(p);
			}
		}
		return pieces;
	}
	
	public Player getWhitePlayer() {
		return this.p1;
	}
	
	public Player getBlackPlayer() {
		return this.p2;
	}
	
	public Player getPlayerWithTurn() {
		if (p1.getTurn()) {
			return p1;
		} else {
			return p2;
		}
	}
	
	public Square getSquareAtPosition(int n) {
		return this.getSquares()[n];
	}
	
	public GameModel getGameModel() {
		return this.game;
	}
	
	public int getEqualsCount() {
		return equalCount;
	}
	
	public int getNumberOfMovesFor(boolean b) {
		int count = 0;
		for (Piece p: this.getPieces()) {
			if (p.getIsWhite() == b) {
				count += p.getValidMoves().size();
			}
		}
		return count;
	}
	
	public int getHalfTurnAdded() {
		return halfTurnAdded;
	}
	
	
	//--------------------------------------Setters-------------------------------------------------------------------
	
	public void setHalfTurnAdded(int n) {
		this.halfTurnAdded = n;
	}
	
	public void setGameModel(GameModel g) {
		this.game = g;
	}
	
	
	//-------------------------------------------------Misc----------------------------------------------------------
	
	public void clearSquareAtPosition(int n) {
		
//		pieces.remove(this.squares[n].getOccupant());
		
		this.squares[n].removeOccupant();
	}
	
	public void incrementEqualCount() {
		equalCount++;
	}
	
	public void initializePiece(Piece p, Square s) {
		if (p == null || s == null) {throw new IllegalArgumentException("Invalid argument");}
		if (s.getIsOccupied()) {throw new IllegalArgumentException("Square is occupied: " + s.getName() + " " + s.getOccupant().getName());}
		p.setBoard(this);
		p.setPosition(s);
		p.setStartingFile(s.getFile());
		this.squares[findSquareIndex(s)].setOccupant(p);
		
//		if (p!= null) {
//			pieces.add(p);
//		}
		
		
		
//		if (p.getIsWhite()) {
//			this.getWhitePlayer().getPieces().add(p);
//			p.setPlayer(this.getWhitePlayer());
//		} else {
//			this.getBlackPlayer().getPieces().add(p);
//			p.setPlayer(this.getBlackPlayer());
//		}
	}
	
	
	//---------------------------------------------MOVE_PIECE--------------------------------------------------------
	
	//incomplete, read below
	public void movePiece(Piece p, Square s) {
		int n = findSquareIndex(p.getPosition());
		p.getPosition().removeOccupant();
		if (s.getIsOccupied()) {
			if (s.getOccupant().getPlayer() == null) {System.out.println(s.getName() + ", " + p.getInitial() + p.getPosition().getFileName() + p.getPosition().getName());}
			else if (s.getOccupant().getPlayer().getPieces() == null) {System.out.println("No Pieces");}
			else if (s.getOccupant() == null) {System.out.println("What");}
			s.getOccupant().getPlayer().getPieces().remove(s.getOccupant());
		}
		for (Piece piece: this.getPieces()) {
			if (piece.getType().equals(Piece.Type.PAWN)) {
				if (((Pawn)piece).getJustMovedOutTwo()) {
					((Pawn)piece).setJustMovedOutTwo(false);
				}
			}
		}
//		s.setOccupant(p);
//		p.setPosition(s);
		if (p.getType().equals(Piece.Type.PAWN)) {
			Pawn temp = (Pawn) p;
			temp.setHasMoved();
			if (Math.abs(findSquareIndex(s) - n) == 16) {
				temp.setJustMovedOutTwo(true);
			} 
			
			//en passent
			if (p.getPosition().getFile() != s.getFile() && !s.getIsOccupied()) {
				game.notifyListeners(new EnPassentEvent(p.getIsWhite(), s.getFile()));
				if (p.getIsWhite()) {
					getSquareAtPosition(findSquareIndex(s)-8).removeOccupant();
				} else {
					getSquareAtPosition(findSquareIndex(s)+8).removeOccupant();
				}
			}
//			s.removeOccupant();
			if (p.getIsWhite()) {
				if (s.getRank() == 7) {
					s.removeOccupant();
					
					Piece pce = new Queen(true);
					Object[] options = {"KNIGHT", "BISHOP", "ROOK", "QUEEN"};
					int num = JOptionPane.showOptionDialog(null, "Choose Your Promotion", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
//					Square newS = getSquareByName(s.getName());
					if (num == 2) {
						pce = new Rook(true);
					} else if (num == 1) {
						pce = new Bishop(true);
					} else if (num == 0) {
						pce = new Knight(true);
					}
					getWhitePlayer().getPieces().remove(p);
					getWhitePlayer().getPieces().add(pce);
					pce.setPlayer(getWhitePlayer());
					initializePiece(pce, s);
					game.notifyListeners(new PromotionEvent(pce.getType(), s, true));
//					//see below
				} else {
					s.setOccupant(p);
					p.setPosition(s);
				}
			} else {
				if (s.getRank() == 0) {
					s.removeOccupant();
					//not making a queen because that is done with a promotionevent sent from the view
					//make this process better in future versions
					
					
					Piece pce = new Queen(false);
					Object[] options = {"KNIGHT", "BISHOP", "ROOK", "QUEEN"};
					int num = JOptionPane.showOptionDialog(null, "Choose Your Promotion", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
					if (num == 2) {
						pce = new Rook(false);
					} else if (num == 1) {
						pce = new Bishop(false);
					} else if (num == 0) {
						pce = new Knight(false);
					}
					getBlackPlayer().getPieces().remove(p);
					getBlackPlayer().getPieces().add(pce);
					pce.setPlayer(getBlackPlayer());
					initializePiece(pce, s);
					game.notifyListeners(new PromotionEvent(pce.getType(), s, false));

				} else {
					s.setOccupant(p);
					p.setPosition(s);
				}
			}
		} else if (p.getType().equals(Piece.Type.KING)) {
			if (Math.abs(p.getPosition().getFile() - s.getFile()) == 2) {
				if (p.getIsWhite()) {
					if (s.getFile() == 6) {
						
						//eventually just set canCastle to false here
						((Rook)getWhitePlayer().getKingsideRook()).setHasMoved();
						
						getSquareByName("f1").setOccupant(getWhitePlayer().getKingsideRook());
						getWhitePlayer().getKingsideRook().setPosition(getSquareByName("f1"));
						getSquareByName("h1").removeOccupant();
						game.notifyListeners(new CastleEvent(true, true));
					} else {
						((Rook)getWhitePlayer().getQueensideRook()).setHasMoved();
						getSquareByName("d1").setOccupant(getWhitePlayer().getQueensideRook());
						getWhitePlayer().getQueensideRook().setPosition(getSquareByName("d1"));
						getSquareByName("a1").removeOccupant();
						game.notifyListeners(new CastleEvent(true, false));
					}
				} else {
					if (s.getFile() == 6) {
						((Rook)getBlackPlayer().getKingsideRook()).setHasMoved();
						getSquareByName("f8").setOccupant(getBlackPlayer().getKingsideRook());
						getBlackPlayer().getKingsideRook().setPosition(getSquareByName("f8"));
						getSquareByName("h8").removeOccupant();
						game.notifyListeners(new CastleEvent(false, true));
					} else {
						((Rook)getBlackPlayer().getQueensideRook()).setHasMoved();
						getSquareByName("d8").setOccupant(getBlackPlayer().getQueensideRook());
						getBlackPlayer().getQueensideRook().setPosition(getSquareByName("d8"));
						getSquareByName("a8").removeOccupant();
						game.notifyListeners(new CastleEvent(false, false));
					}
				}
			}
			s.removeOccupant();
			s.setOccupant(p);
			p.setPosition(s);
			((King)p).setHasMoved();
		} else {
			s.removeOccupant();
			s.setOccupant(p);
			p.setPosition(s);
		}
		if (p.getIsWhite()) {
			if (((King)getBlackPlayer().getKing()).getIsInCheck()) {
				game.notifyListeners(new CheckEvent(false));
			}
			((King)getBlackPlayer().getKing()).checkForCheckmate();
			getBlackPlayer().checkForStalemate();
		} else {
			if (((King)getWhitePlayer().getKing()).getIsInCheck()) {
				game.notifyListeners(new CheckEvent(true));
			}
			((King)getWhitePlayer().getKing()).checkForCheckmate();
			getWhitePlayer().checkForStalemate();
		}
//		if (this.game != null) {
////			Board b2 = this.clone();
////			b2.setHalfTurnAdded(this.game.getMoveLog().getMoves().size());
//			this.game.update(p, s);
//			this.game.getBoardLog().getBoards().add(new BoardRep(this));
//			this.game.getBoardLog().checkForDraw();
//			this.game.getMoveLog().checkForDraw();
//		}
	}
	
	//----------------------------------------END_MOVE_PIECE---------------------------------------------------------
	
	
	
	
	public void moveAndPromoteEngine(Piece p, Square s, Piece.Type t) {
		if (p.getType() != Piece.Type.PAWN) {throw new IllegalArgumentException("Promotions only");}
		if (p.getIsWhite() && s.getRank() != 7) {throw new IllegalArgumentException("Promotions only");}
		if (!p.getIsWhite() && s.getRank() != 0) {throw new IllegalArgumentException("Promotions only");}
		if (t.equals(Piece.Type.PAWN) || t.equals(Piece.Type.KING)) {throw new IllegalArgumentException("Invalid Promotion Type");}
		
		s.removeOccupant();
		if (s.getIsOccupied()) {
			s.getOccupant().getPlayer().getPieces().remove(s.getOccupant());
		}
		p.getPosition().removeOccupant();
		p.getPlayer().getPieces().remove(p);
		
		Piece newP;
		if (t.equals(Piece.Type.ROOK)) {
			newP = new Rook(p.getIsWhite());
		} else if (t.equals(Piece.Type.BISHOP)) {
			newP = new Bishop(p.getIsWhite());
		} else if (t.equals(Piece.Type.KNIGHT)) {
			newP = new Knight(p.getIsWhite());
		} else {
			newP = new Queen(p.getIsWhite());
		}
		
		if (newP.getIsWhite()) {
			getWhitePlayer().getPieces().add(newP);
		} else {
			getBlackPlayer().getPieces().add(newP);
		}
		
		initializePiece(newP, s);
		
		game.notifyListeners(new PromotionEvent(t, s, newP.getIsWhite()));
		
	}
	
	
	public int findSquareIndex(Square square) {
		for (int i = 0; i < squares.length; i++) {
			if (squares[i].getRank() == square.getRank() && squares[i].getFile() == square.getFile()) {
				return i;
			}
		}
		System.out.println(square.getName());
		return -1;
	}
	
	public Square getSquareByName(String str) {
		for (Square s: this.getSquares()) {
			if (s.getName().equals(str)) {
				return s;
			}
		}
		return null;
	}
	
	public List<Square> getLineBetweenSquares(Square s1, Square s2) {
		List<Square> squares = new ArrayList<Square>();
		int n1 = this.findSquareIndex(s1);
		int n2 = this.findSquareIndex(s2);
		if (n1 > n2) {
			int temp = n1;
			n1 = n2;
			n2 = temp;
		}
		if (s1.isDiagonalTo(s2)) {
			for (Square s: this.getSquares()) {
				if (this.findSquareIndex(s) > n1 && this.findSquareIndex(s) < n2) {
					if (s.isDiagonalTo(s1) && s.isDiagonalTo(s2)) {
						squares.add(s);
					}
				}
			}
		} else if (s1.getFile() == s2.getFile()) {
			for (Square s: this.getSquares()) {
				if (this.findSquareIndex(s) > n1 && this.findSquareIndex(s) < n2) {
					if (s.getFile() == s1.getFile()) {
						squares.add(s);
					}
				}
			}
		} else if (s1.getRank() == s2.getRank()) {
			for (Square s: this.getSquares()) {
				if (this.findSquareIndex(s) > n1 && this.findSquareIndex(s) < n2) {
					if (s.getRank() == s1.getRank()) {
						squares.add(s);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Squares dont line up");
		}
		return squares;
	}
	
	public void incorportatePlayers(Player p1, Player p2) {
		if (p1.getIsWhite() == p2.getIsWhite()) {throw new IllegalArgumentException("Both players cannot use the same color");}
		if (p1.getIsWhite()) {
			this.p1 = p1;
			this.p2 = p2;
		} else {
			this.p1 = p2;
			this.p2 = p1;
		}
	}
	
//	public void handleMoveEvent(MoveEvent e) {
//		
//	}
	
	@Override
	public Board clone() {
		Board b = new Board();
		Player wp = new CustomPlayerImpl(b, true);
		Player bp = new CustomPlayerImpl(b, false);
		for (int i = 0; i < 64; i++) {
			if (squares[i].getIsOccupied()) {
				boolean white = squares[i].getOccupant().getIsWhite();
				boolean wasPawn = squares[i].getOccupant().getWasPawn();
				int startingFile = squares[i].getOccupant().getStartingFile();
				Piece.Type type = squares[i].getOccupant().getType();
				Piece p;
				
				if (type.equals(Piece.Type.PAWN)) {
					p = new Pawn(white);
					if (((Pawn)squares[i].getOccupant()).getHasMoved()) {
						((Pawn)p).setHasMoved();
					}
					if (((Pawn)squares[i].getOccupant()).getJustMovedOutTwo()) {
						((Pawn)p).setJustMovedOutTwo(true);
					}
					p.setStartingFile(startingFile);
				} else if (type.equals(Piece.Type.KING)) {
					p = new King(white);
					if (((King)squares[i].getOccupant()).getHasMoved()) {
						((King)squares[i].getOccupant()).setHasMoved();
					}
					p.setStartingFile(startingFile);
				} else if (type.equals(Piece.Type.ROOK)) {
					p = new Rook(white);
					if (((Rook)squares[i].getOccupant()).getHasMoved()) {
						((Rook)p).setHasMoved();
					}
					if (wasPawn) {
						p.setWasPawn();
					}
					p.setStartingFile(startingFile);
				} else if (type.equals(Piece.Type.QUEEN)) {
					p = new Queen(white);
					if (wasPawn) {
						p.setWasPawn();
					}
					p.setStartingFile(startingFile);
				} else if (type.equals(Piece.Type.BISHOP)) {
					p = new Bishop(white);
					if (wasPawn) {
						p.setWasPawn();
					}
					p.setStartingFile(startingFile);
				} else {
					p = new Knight(white);
					if (wasPawn) {
						p.setWasPawn();
					}
					p.setStartingFile(startingFile);
				}
				b.initializePiece(p, b.getSquares()[i]);
				if (p.getIsWhite()) {
					p.setPlayer(wp);
					wp.getPieces().add(p);
				} else {
					p.setPlayer(bp);
					bp.getPieces().add(p);
				}
			}
		}
		b.incorportatePlayers(wp, bp);
		b.setGameModel(game);
		return b;
	}
	
	public boolean equals(Board b) {
		return new BoardRep(this).equals(new BoardRep(b));
	}
	
	public Piece getPieceByName(String str) {
		boolean b1 = false;
		boolean b2 = false;
		if (str.length() == 4) {
			if (str.substring(1, 2).equals("W")) {
				b1 = true;
			}
			if (str.substring(3, 4).equals("Y")) {
				b2 = true;
			}
		} else if (str.length() == 3) {
			if (str.substring(0, 1).equals("W")) {
				b1 = true;
			}
			if (str.substring(2, 3).equals("Y")) {
				b2 = true;
			}
		}
		if (str.length() == 4) {
			for (Piece p: this.getPieces()) {
				if (p.getInitial().equals(str.substring(0, 1))) {
					if (p.getIsWhite() == b1) {
						if (p.getStartingFile() == Integer.parseInt(str.substring(2, 3))) {
							if (p.getWasPawn() == b2) {
								return p;
							}
						}
					}
				}
			}
		} else {
			for (Piece p: this.getPieces()) {
				if (p.getType().equals(Piece.Type.PAWN)) {
					if (p.getIsWhite() == b1) {
						if (p.getStartingFile() == Integer.parseInt(str.substring(1, 2))) {
							if (p.getWasPawn() == b2) {
								return p;
							}
						}
					}
				}
			}
		}
		
		System.out.println(str);
		System.out.println("Piece not found");
		return null;
	}
	
}
