package chess03;

import java.util.ArrayList;
import java.util.List;

abstract public class PieceImpl implements Piece {

	private Type type;
	private boolean isWhite;
	private Square position;
	private Board board;
	private Direction[] directions;
	private Piece.Direction pinnedDirection;
	private Player player;
	private double baseValue;
	private int startingFile;
	private boolean wasPawn;
	private boolean fileHasBeenSet;
	
	public PieceImpl(Type type, boolean isWhite, double baseValue) {
		this.type = type;
		this.isWhite = isWhite;
		this.position = null;
		this.board = null;
		this.directions = null;
		this.pinnedDirection = null;
		this.player = null;
		this.baseValue = baseValue;
		this.startingFile = 0;
		this.wasPawn = false;
		fileHasBeenSet = false;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public boolean getIsWhite() {
		return this.isWhite;
	}
	
	public Square getPosition() {
		return this.position;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public boolean getWasPawn() {
		return this.wasPawn;
	}
	
	abstract public String getInitial();
	
	public Direction[] getDirections() {
		return this.directions;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public double getBaseValue() {
		return this.baseValue;
	}
	
	public int getStartingFile() {
		return this.startingFile;
	}
	
	public void setStartingFile(int n) {
		if (!fileHasBeenSet) {
			if (n > -1 && n < 8) {
				this.startingFile = n;
			} else {
				throw new IllegalArgumentException();
			}
			fileHasBeenSet = true;
		}
		
		
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public Direction getPinnedDirection() {
		return this.pinnedDirection;
	}
	
	public void setWasPawn() {
		this.wasPawn = true;
	}
	
	public void setPinnedDirection(Piece.Direction d) {
		this.pinnedDirection = d;
	}
	
	public void setPosition(Square s) {
		this.position = s;
		s.setOccupant(this);
		
	}
	
	public void setBoard(Board b) {
		if (this.board == null) {
			this.board = b;
		}
	}
	
	public String getName() {
		String state = "";
		state += this.getInitial();
		if (this.getIsWhite()) {
			state += "W";
		} else {
			state += "B";
		}
		state += Integer.toString(this.getStartingFile());
		if (this.getWasPawn()) {
			state += "Y";
		} else {
			state += 'N';
		}
		return state;
	}
	
	public List<Square> getValidMoves() {
		List<Square> validMoves = new ArrayList<Square>();
		return validMoves;
	}
	
	abstract public List<Square> getPotentialMoves();
	
	public boolean getIsDefended() {
		if (this.getBoard() == null) {
			this.getPlayer().getBoard().initializePiece(this, this.getPosition());
		}
		if (this.getPlayer() == null) {
			if (this.isWhite) {
				this.setPlayer(this.getBoard().getWhitePlayer());
			} else {
				this.setPlayer(this.getBoard().getBlackPlayer());
			}
		}
		else if (this.getPlayer().getPieces() == null) { System.out.println("no pieces");}
		for (Piece p: this.getPlayer().getPieces()) {
			if (p.getPosition() == null) {throw new IllegalArgumentException("no position");}
			if (!p.equals(this)) {
				for (Square s: p.getDefensiveMoves()) {
					if (s.equals(this.getPosition())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public int getNumberOfDefenders() {
		int count = 0;
		for (Piece p: this.getBoard().getPieces()) {
			if (p.getIsWhite() == this.getIsWhite()) {
				for (Square s: p.getDefensiveMoves()) {
					if (s.equals(this.getPosition())) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	public int getNumberOfDefendersNoKing() {
		int count = 0;
		for (Piece p: this.getPlayer().getPieces()) {
			if (!p.getType().equals(Piece.Type.KING)) {
				for (Square s: p.getDefensiveMoves()) {
					if (s.equals(this.getPosition())) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
	public int getNumberOfMoves() {
		return this.getValidMoves().size();
	}
	
	public int findSquareIndex(Square s) {
		for (int i = 0; i < this.board.getSquares().length; i++) {
			if (this.board.getSquares()[i].getName().equals(s.getName())) {
				return i;
			}
		}
		return -1;
	}
	
	public List<Square> getDefensiveMovesInDirection(Direction d) {
		List<Square> moves = new ArrayList<Square>();
		if (d.equals(Piece.Direction.N)) {
			if (this.getBoard() == null) {throw new IllegalArgumentException("no board");}
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = 8;
			int iterNum = 8 - this.getPosition().getRank();
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else if (d.equals(Piece.Direction.NE)) {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = 9;
			int iterNum;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = 8 - this.getPosition().getRank();
			} else {
				iterNum = 8 - this.getPosition().getFile();
			}
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else if (d.equals(Piece.Direction.E)) {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = 1;
			int iterNum = 8 - this.getPosition().getFile();
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else if (d.equals(Piece.Direction.SE)) {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = -7;
			int iterNum;
			if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
				iterNum = 8 - this.getPosition().getFile();
			} else {
				iterNum = this.getPosition().getRank() + 1;
			} 
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else if (d.equals(Piece.Direction.S)) {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = -8;
			int iterNum = this.getPosition().getRank() + 1;
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else if (d.equals(Piece.Direction.SW)) {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = -9;
			int iterNum;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else if (d.equals(Piece.Direction.W)) {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = -1;
			int iterNum = this.getPosition().getFile() + 1;
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		} else {
			int n = this.getBoard().findSquareIndex(this.getPosition());
			int x = 7;
			int iterNum;
			if (this.getPosition().getRank() + this.getPosition().getFile() < 7) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = 8 - this.getPosition().getRank();
			} 
			outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				} else {
//					if (this.getIsWhite() == this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
						moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}
					break outerLoop;
				}
			}
		}
		return moves;
	}
	
	public List<Square> getMovesInDirection(Direction d) {
		List<Square> moves = new ArrayList<Square>();
		int n;
		int x;
		int iterNum;
		if (d.equals(Piece.Direction.N)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 8;
			iterNum = 8 - this.getPosition().getRank();
		} else if (d.equals(Piece.Direction.NE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = 8 - this.getPosition().getRank();
			} else {
				iterNum = 8 - this.getPosition().getFile();
			}
		} else if (d.equals(Piece.Direction.E)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 1;
			iterNum = 8 - this.getPosition().getFile();
		} else if (d.equals(Piece.Direction.SE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -7;
			if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
				iterNum = 8 - this.getPosition().getFile();
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.S)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -8;
			iterNum = this.getPosition().getRank() + 1;
		} else if (d.equals(Piece.Direction.SW)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.W)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -1;
			iterNum = this.getPosition().getFile() + 1;
		} else {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 7;
			if (this.getPosition().getRank() + this.getPosition().getFile() < 7) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = 8 - this.getPosition().getRank();
			} 
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
			} else {
				if (this.getIsWhite() != this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				}
				break outerLoop;
			}
		}
		return moves;
	}
	
	//If a king is lined up with a rook, for example, this accounts for the king moving away from the rook
	//Otherwise, the program wouldn't know that the rook could move to that square, because
	//the king is blocking it
	public List<Square> getMovesInDirectionNoKing(Direction d) {
		List<Square> moves = new ArrayList<Square>();
		int n;
		int x;
		int iterNum;
		if (d.equals(Piece.Direction.N)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 8;
			iterNum = 8 - this.getPosition().getRank();
		} else if (d.equals(Piece.Direction.NE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = 8 - this.getPosition().getRank();
			} else {
				iterNum = 8 - this.getPosition().getFile();
			}
		} else if (d.equals(Piece.Direction.E)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 1;
			iterNum = 8 - this.getPosition().getFile();
		} else if (d.equals(Piece.Direction.SE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -7;
			if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
				iterNum = 8 - this.getPosition().getFile();
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.S)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -8;
			iterNum = this.getPosition().getRank() + 1;
		} else if (d.equals(Piece.Direction.SW)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.W)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -1;
			iterNum = this.getPosition().getFile() + 1;
		} else {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 7;
			if (this.getPosition().getRank() + this.getPosition().getFile() < 7) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = 8 - this.getPosition().getRank();
			} 
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (!this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
			} else {
				if (this.getIsWhite() != this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite()) {
					moves.add(this.getBoard().getSquareAtPosition(n + (x * i)));
				}
				if (!(this.getIsWhite() != this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() &&
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING))) {
					break outerLoop;
				}
				
			}
		}
		return moves;
	}
	
	//returns IF there is a direct line to king of either color depending on input
	public boolean directLineToKing(Piece.Direction d, boolean white) {
		int n;
		int x;
		int iterNum;
		if (d.equals(Piece.Direction.N)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 8;
			iterNum = 8 - this.getPosition().getRank();
		} else if (d.equals(Piece.Direction.NE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = 8 - this.getPosition().getRank();
			} else {
				iterNum = 8 - this.getPosition().getFile();
			}
		} else if (d.equals(Piece.Direction.E)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 1;
			iterNum = 8 - this.getPosition().getFile();
		} else if (d.equals(Piece.Direction.SE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -7;
			if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
				iterNum = 8 - this.getPosition().getFile();
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.S)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -8;
			iterNum = this.getPosition().getRank() + 1;
		} else if (d.equals(Piece.Direction.SW)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.W)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -1;
			iterNum = this.getPosition().getFile() + 1;
		} else {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 7;
			if (this.getPosition().getRank() + this.getPosition().getFile() < 7) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = 8 - this.getPosition().getRank();
			}
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == white) {
					return true;	
				} else {
					break outerLoop;
				}
			}
		}
		return false;
	}
	
	//returns IF it has direct line to a rook, bishop, or queen depending on input
	public boolean directLineToPinner(Piece.Direction d, Piece.Type t) {
		int n;
		int x;
		int iterNum;
		if (d.equals(Piece.Direction.N)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 8;
			iterNum = 8 - this.getPosition().getRank();
		} else if (d.equals(Piece.Direction.NE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = 8 - this.getPosition().getRank();
			} else {
				iterNum = 8 - this.getPosition().getFile();
			}
		} else if (d.equals(Piece.Direction.E)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 1;
			iterNum = 8 - this.getPosition().getFile();
		} else if (d.equals(Piece.Direction.SE)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -7;
			if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
				iterNum = 8 - this.getPosition().getFile();
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.S)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -8;
			iterNum = this.getPosition().getRank() + 1;
		} else if (d.equals(Piece.Direction.SW)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -9;
			if (this.getPosition().getRank() > this.getPosition().getFile()) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = this.getPosition().getRank() + 1;
			}
		} else if (d.equals(Piece.Direction.W)) {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = -1;
			iterNum = this.getPosition().getFile() + 1;
		} else {
			n = this.getBoard().findSquareIndex(this.getPosition());
			x = 7;
			if (this.getPosition().getRank() + this.getPosition().getFile() < 7) {
				iterNum = this.getPosition().getFile() + 1;
			} else {
				iterNum = 8 - this.getPosition().getRank();
			}
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(t) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() != this.getIsWhite()) {
					return true;	
				} else {
					break outerLoop;
				}
			}
		}
		return false;
	}
	
	//returns direction TOWARDS own king. Called if there is such a direct line
	public Piece.Direction getPinDirection() {
		int n;
		int x;
		int iterNum;
		Piece.Direction d;
		
		//NORTH
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = 8;
		iterNum = 8 - this.getPosition().getRank();
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
					return Piece.Direction.N;
				} else {
					break outerLoop;
				}
			}
		}
		
		//NORTHEAST
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = 9;
		if (this.getPosition().getRank() > this.getPosition().getFile()) {
			iterNum = 8 - this.getPosition().getRank();
		} else {
			iterNum = 8 - this.getPosition().getFile();
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
					return Piece.Direction.NE;
				} else {
					break outerLoop;
				}
			}
		}
		
		//EAST
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = 1;
		iterNum = 8 - this.getPosition().getFile();
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
					return Piece.Direction.E;
				} else {
					break outerLoop;
				}
			}
		}
		
		//SOUTHEAST
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = -7;
		if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
			iterNum = 8 - this.getPosition().getFile();
		} else {
			iterNum = this.getPosition().getRank() + 1;
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
					return Piece.Direction.SE;
				} else {
					break outerLoop;
				}
			}
		}
		
		//SOUTH
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = -8;
		iterNum = this.getPosition().getRank() + 1;
		outerLoop:
			for (int i = 1; i < iterNum; i++) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
					if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
							this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
						return Piece.Direction.S;
					} else {
						break outerLoop;
					}
				}
			}
		
		//SOUTHWEST
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = -9;
		if (this.getPosition().getRank() > this.getPosition().getFile()) {
			iterNum = this.getPosition().getFile() + 1;
		} else {
			iterNum = this.getPosition().getRank() + 1;
		}
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
					return Piece.Direction.SW;
				} else {
					break outerLoop;
				}
			}
		}
		
		//WEST
		n = this.getBoard().findSquareIndex(this.getPosition());
		x = -1;
		iterNum = this.getPosition().getFile() + 1;
		outerLoop:
		for (int i = 1; i < iterNum; i++) {
			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getType().equals(Piece.Type.KING) && 
						this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().getIsWhite() == this.getIsWhite()) {
					return Piece.Direction.W;
				} else {
					break outerLoop;
				}
			}
		}
		
		//NORTHWEST
		return Piece.Direction.NW;
	}
	
	abstract public boolean checkForPins();
	
	public boolean checkForPotentialPins() {
		for (Piece.Direction d: Piece.Direction.values()) {
			if (directLineToKing(d, this.getIsWhite())) {
				return true;
			}
		}
		return false;
	}
	
	public Piece.Direction oppositeDirection(Piece.Direction d) {
		if (d.equals(Piece.Direction.N)) {
			return Piece.Direction.S;
		} else if (d.equals(Piece.Direction.NE)) {
			return Piece.Direction.SW;
		} else if (d.equals(Piece.Direction.E)) {
			return Piece.Direction.W;
		} else if (d.equals(Piece.Direction.SE)) {
			return Piece.Direction.NW;
		} else if (d.equals(Piece.Direction.S)) {
			return Piece.Direction.N;
		} else if (d.equals(Piece.Direction.SW)) {
			return Piece.Direction.NE;
		} else if (d.equals(Piece.Direction.W)) {
			return Piece.Direction.E;
		} else {
			return Piece.Direction.SE;
		}
	}
}
