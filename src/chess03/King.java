package chess03;

import java.util.ArrayList;
import java.util.List;

public class King extends PieceImpl {
	
	private boolean isInCheck;
	private boolean isInCheckMate;
	private boolean canCastleKingside;
	private boolean canCastleQueenside;
	private boolean hasMoved;
	private Piece checker;

	public King(boolean isWhite) {
		super(Piece.Type.KING, isWhite, 0.0);
		this.isInCheck = false;
		this.isInCheckMate = false;
		this.canCastleKingside = false;
		this.canCastleQueenside = false;
		this.hasMoved = false;
		this.checker = null;
	}
	
	public String getInitial() {
		return "K";
	}
	
	public boolean getIsInCheck() {
//		checkForChecks();
//		return this.isInCheck;
		for (Piece p: this.getPlayer().getOpponent().getPieces()) {
			for (Square s: p.getPotentialMoves()) {
				if (s.getName().equals(this.getPosition().getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean getIsInDoubleCheck() {
		int n = 0;
		for (Piece p: this.getPlayer().getOpponent().getPieces()) {
			for (Square s: p.getPotentialMoves()) {
				if (s.equals(this.getPosition())) {
					n++;
					break;
				}
			}
		}
		if (n > 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getIsInCheckmate() {
		return this.isInCheckMate;
	}
	
	public boolean getCanCastleKingside() {
		this.getValidMoves();
		return this.canCastleKingside;
	}
	
	public boolean getCanCastleQueenside() {
		this.getValidMoves();
		return this.canCastleQueenside;
	}
	
	public boolean getHasMoved() {
		return this.hasMoved;
	}
	
	public Piece getChecker() {
		return this.checker;
	}
	
	public void setChecker(Piece p) {
		this.checker = p;
	}
	
	public void setIsInCheck(boolean b) {
		this.isInCheck = b;
	}
	
	public void setIsInCheckmate() {
		this.isInCheckMate = true;
	}
	
	public void setCanCastleKingside(boolean b) {
		this.canCastleKingside = b;
	}
	
	public void setCanCastleQueenside(boolean b) {
		this.canCastleQueenside = b;
	}
	
	public void setHasMoved() {
		this.hasMoved = true;
	}
	
	public boolean getIsDefended() {
		return false;
	}
	
	public int getNumberOfDefenders() {
		return 0;
	}
	
	public List<Square> getDefensiveMoves() {
		List<Square> squares = new ArrayList<Square>();
		for (int i = 0; i < 64; i++) {
			if (getBoard().getSquares()[i].borders(getPosition())) {
				if (!getBoard().getSquares()[i].getIsOccupied()) {
					if (!checkForChecksAt(getBoard().getSquares()[i])) {
						squares.add(getBoard().getSquares()[i]);
					}
				} else {
					if (getBoard().getSquares()[i].getOccupant().getIsWhite() != getIsWhite()) {
						if (!getBoard().getSquares()[i].getOccupant().getIsDefended()) {
							squares.add(getBoard().getSquares()[i]);
						}
					} else {
						if ((getOppAttacksOnSquare(getBoard().getSquares()[i]) - getBoard().getSquares()[i].getOccupant().getNumberOfDefendersNoKing()) < 2) {
							squares.add(getBoard().getSquares()[i]);
						}
					}
				}
			}
		}
		return squares;
	}
	
	@Override
	public List<Square> getValidMoves() {
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		for (int i = 0; i < this.getBoard().getSquares().length; i++) {
			Square square = this.getBoard().getSquares()[i];
			if (!square.getIsOccupied()) {
				if (this.getPosition().borders(square)) {
					if (!checkForChecksAt(square)) {
						validMoves.add(this.getBoard().getSquares()[i]);
					} else {
//						System.out.println(square.getName());
					}
				}
			} else if (square.getOccupant().getIsWhite() != this.getIsWhite()) {
//				System.out.println("bad1: " + square.getOccupant().getInitial() + square.getName());
				if (square.getOccupant().getPlayer() == null) {
					square.getOccupant().setPlayer(this.getPlayer().getOpponent());
				}
				if (square.getOccupant().getBoard() == null) {
					square.getOccupant().setBoard(this.getBoard());
				}
				if (!square.getOccupant().getIsDefended()) {
					if (this.getPosition().borders(square)) {
						if (!checkForChecksAt(square)) {
//							System.out.println("B");
							validMoves.add(this.getBoard().getSquares()[i]);
						}
					}
				}
			}
		}
		//UNFINISHED ---- CASTLING
		if (!this.hasMoved) {
//			System.out.println("bad2");
//			System.out.println(this.getIsWhite());
//			System.out.println(this.getType());
			if (this.getIsWhite()) {
				if (this.getPosition().equals(this.getBoard().getSquareAtPosition(4))) {
					Rook r = (Rook) this.getPlayer().getKingsideRook();
					if (r != null) {
						if (!this.checkForChecks()) {
							if (!checkForChecksAt(this.getBoard().getSquareAtPosition(5)) &&
									!checkForChecksAt(this.getBoard().getSquareAtPosition(6)) &&
									!this.getBoard().getSquareAtPosition(5).getIsOccupied() && 
									!this.getBoard().getSquareAtPosition(6).getIsOccupied()) {
								this.setCanCastleKingside(true);
//								System.out.println("C");
								validMoves.add(this.getBoard().getSquareAtPosition(6));
							}
						}
					}
					Rook r2 = (Rook) this.getPlayer().getQueensideRook();
					if (r2!= null) {
						if (!this.checkForChecks()) {
							if (!checkForChecksAt(this.getBoard().getSquareAtPosition(3)) &&
									!checkForChecksAt(this.getBoard().getSquareAtPosition(2)) &&
									!this.getBoard().getSquareAtPosition(3).getIsOccupied() &&
									!this.getBoard().getSquareAtPosition(2).getIsOccupied()) {
								this.setCanCastleKingside(true);
//								System.out.println("D");
								validMoves.add(this.getBoard().getSquareAtPosition(2));
							}
						}
					}
				}
			} else {
				if (this.getPosition().equals(this.getBoard().getSquareAtPosition(60))) {
					Rook r = (Rook) this.getPlayer().getKingsideRook();
					if (r != null) {
						if (!this.checkForChecks()) {
							if (!checkForChecksAt(this.getBoard().getSquareAtPosition(61)) &&
									!checkForChecksAt(this.getBoard().getSquareAtPosition(62)) &&
									!this.getBoard().getSquareAtPosition(61).getIsOccupied() &&
									!this.getBoard().getSquareAtPosition(62).getIsOccupied()) {
								this.setCanCastleKingside(true);
//								System.out.println("E");
								validMoves.add(this.getBoard().getSquareAtPosition(62));
							}
						}
					}
					Rook r2 = (Rook) this.getPlayer().getQueensideRook();
					if (r2!= null) {
						if (!this.checkForChecks()) {
							if (!checkForChecksAt(this.getBoard().getSquareAtPosition(59)) &&
									!checkForChecksAt(this.getBoard().getSquareAtPosition(58)) &&
									!this.getBoard().getSquareAtPosition(59).getIsOccupied() &&
									!this.getBoard().getSquareAtPosition(58).getIsOccupied()) {
								this.setCanCastleKingside(true);
//								System.out.println("F");
								validMoves.add(this.getBoard().getSquareAtPosition(58));
							}
						}
					}
				}
			}
		}
		return validMoves;
	}
	
	public List<Square> getPotentialMoves() {
		return this.getValidMoves();
	}
	
	public List<Square> getPotentialMovesNoKing() {
		return this.getValidMoves();
	}
	
	public boolean checkForChecks() {
//		for (Piece p: this.getBoard().getPieces()) {
//			if (!p.getType().equals(Piece.Type.KING)) {
//				if (p.getIsWhite() != this.getIsWhite()) {
//					for (Square s: p.getPotentialMoves()) {
//						if (s.equals(this.getPosition())) {
//							this.setIsInCheck(true);
//							return true;
//						}
//					}
//				}
//			}
//		}
//		this.setIsInCheck(false);
//		return false;
		for (Piece p: this.getPlayer().getOpponent().getPieces()) {
			if (!p.getType().equals(Piece.Type.KING)) {
				for (Square s: p.getPotentialMoves()) {
					if (s.equals(this.getPosition()) ) {
						this.setIsInCheck(true);
						this.setChecker(p);
						return true;
					}
				}
			}
		}
		this.setIsInCheck(false);
		this.setChecker(null);
		return false;
	}
	
//	public Piece getChecker() {
////		for (Piece p: this.getBoard().getPieces()) {
////			if (p.getIsWhite() != this.getIsWhite()) {
////				for (Square s: p.getPotentialMoves()) {
////					if (s.equals(this.getPosition())) {
////						return p;
////					}
////				}
////			}
////		}
//		for (Piece p: this.getPlayer().getOpponent().getPieces()) {
//			for (Square s: p.getPotentialMoves()) {
//				if (s.equals(this.getPosition())) {
//					return p;
//				}
//			}
//		}
//		return null;
//	}
	
	public List<Square> getLineToChecker() {
		List<Square> squares = new ArrayList<Square>();
		if (this.getChecker() != null) {
			squares.add(this.getChecker().getPosition());
			if (!this.getChecker().getType().equals(Piece.Type.KNIGHT) && !this.getChecker().getType().equals(Piece.Type.PAWN)) {
				for (Square s: this.getBoard().getLineBetweenSquares(this.getPosition(), this.getChecker().getPosition())) {
					squares.add(s);
				}
			} 
			return squares;
		} else {
			throw new IllegalArgumentException("Houston...");
		}
		
	}
	
//	public List<Square> getLineToChecker(Piece.Direction d) {
//		List<Square> squares = new ArrayList<Square>();
//		if (this.getChecker()!= null) {
//			if (!this.getChecker().getType().equals(Piece.Type.KNIGHT)) {
//				int n;
//				int x;
//				int iterNum;
//				if (d.equals(Piece.Direction.N)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = 8;
//					iterNum = 8 - this.getPosition().getRank();
//				} else if (d.equals(Piece.Direction.NE)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = 9;
//					if (this.getPosition().getRank() > this.getPosition().getFile()) {
//						iterNum = 8 - this.getPosition().getRank();
//					} else {
//						iterNum = 8 - this.getPosition().getFile();
//					}
//				} else if (d.equals(Piece.Direction.E)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = 1;
//					iterNum = 8 - this.getPosition().getFile();
//				} else if (d.equals(Piece.Direction.SE)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = -7;
//					if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
//						iterNum = 8 - this.getPosition().getFile();
//					} else {
//						iterNum = this.getPosition().getRank() + 1;
//					}
//				} else if (d.equals(Piece.Direction.S)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = -8;
//					iterNum = this.getPosition().getRank() + 1;
//				} else if (d.equals(Piece.Direction.SW)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = -9;
//					if (this.getPosition().getRank() > this.getPosition().getFile()) {
//						iterNum = this.getPosition().getFile() + 1;
//					} else {
//						iterNum = this.getPosition().getRank() + 1;
//					}
//				} else if (d.equals(Piece.Direction.W)) {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = -1;
//					iterNum = this.getPosition().getFile() + 1;
//				} else {
//					n = this.getBoard().findSquareIndex(this.getPosition());
//					x = 7;
//					if (this.getPosition().getRank() + this.getPosition().getFile() < 7) {
//						iterNum = this.getPosition().getFile() + 1;
//					} else {
//						iterNum = 8 - this.getPosition().getRank();
//					}
//				}
//				outerLoop:
//				for (int i = 1; i < iterNum; i++) {
//					if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//						if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker()))  {
//							squares.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//						} else {
//							throw new RuntimeException("Checker was not correct");
//						}
//						break outerLoop;
//					} else {
//						squares.add(this.getBoard().getSquareAtPosition(n + (x * i)));
//					}	
//				}
//			} else {
//				squares.add(this.getChecker().getPosition());
//			}
//		}
//		return squares;
//	}
	
	public boolean checkForChecksAt(Square square) {

		for (Piece p: this.getPlayer().getOpponent().getPieces()) {
			if (p.getType().equals(Piece.Type.KING)) {
				if (square.borders(p.getPosition())) {
					return true;
				}
			} else if (!p.getType().equals(Piece.Type.PAWN)) {
				for (Square s: p.getPotentialMovesNoKing()) {
					if (s.getName().equals(square.getName())) {
						return true;
					}
				}
			} else {
				Square s = p.getPosition();
				if (p.getIsWhite()) {
					if (square.getRank() - s.getRank() == 1) {
						if (Math.abs(square.getFile() - s.getFile()) == 1) {
							return true;
						}
					}
				} else {
					if (s.getRank() - square.getRank() == 1) {
						if (Math.abs(square.getFile() - s.getFile()) == 1) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	
	public boolean checkForCheckmate() {
		if (this.getIsInCheck()) { 
			if (this.getPlayer().getMoves().size() == 0) {
				if (this.getIsWhite()) {
					getBoard().getGameModel().announceOutcome(Game.Outcomes.BLACK_WINS);
				} else {
					getBoard().getGameModel().announceOutcome(Game.Outcomes.WHITE_WINS);
				}
				return true;
			}	
		}
		return false;
	}

	public boolean checkForPins() {
		return false;
	}
	
//	public Piece.Direction getDirectionToChecker() {
//		int n;
//		int x;
//		int iterNum;
//		
//		//NORTH
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = 8;
//		iterNum = 8 - this.getPosition().getRank();
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.N;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//NORTHEAST
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = 9;
//		if (this.getPosition().getRank() > this.getPosition().getFile()) {
//			iterNum = 8 - this.getPosition().getRank();
//		} else {
//			iterNum = 8 - this.getPosition().getFile();
//		}
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.NE;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//EAST
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = 1;
//		iterNum = 8 - this.getPosition().getFile();
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.E;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//SOUTHEAST
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = -7;
//		if (this.getPosition().getRank() + this.getPosition().getFile() > 7) {
//			iterNum = 8 - this.getPosition().getFile();
//		} else {
//			iterNum = this.getPosition().getRank() + 1;
//		}
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.SE;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//SOUTH
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = -8;
//		iterNum = this.getPosition().getRank() + 1;
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.S;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//SOUTHWEST
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = -9;
//		if (this.getPosition().getRank() > this.getPosition().getFile()) {
//			iterNum = this.getPosition().getFile() + 1;
//		} else {
//			iterNum = this.getPosition().getRank() + 1;
//		}
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.SW;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//WEST
//		n = this.getBoard().findSquareIndex(this.getPosition());
//		x = -1;
//		iterNum = this.getPosition().getFile() + 1;
//		outerLoop:
//		for (int i = 1; i < iterNum; i++) {
//			if (this.getBoard().getSquareAtPosition(n + (x * i)).getIsOccupied()) {
//				if (this.getBoard().getSquareAtPosition(n + (x * i)).getOccupant().equals(this.getChecker())) {
//					return Piece.Direction.W;
//				} else {
//					break outerLoop;
//				}
//			}
//		}
//		
//		//NORTHWEST
//		return Piece.Direction.NW;
//	}	
	
	
	//used to determine if theres <2 attackers of own piece
	//if not, king can defend that square
	public int getOppAttacksOnSquare(Square square) {
		int n = 0;
		for (Piece p: getPlayer().getOpponent().getPieces()) {
			if (!p.getType().equals(Piece.Type.KING)) {
				innerloop:
				for (Square s: p.getMovesWithoutChecks()) {
					if (s.getName().equals(square.getName())) {
						n++;
						break innerloop;
					}
				}
			} else {
				if (square.borders(p.getPosition())) {
					n++;
				}
			}
		}
		return n;
	}
	
	
	public List<Square> getMovesWithoutChecks() {
		return getValidMoves();
	}
	
}
