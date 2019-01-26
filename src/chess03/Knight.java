package chess03;

import java.util.ArrayList;
import java.util.List;

public class Knight extends PieceImpl {

	private boolean isPinned;
	
	public Knight(boolean isWhite) {
		super(Piece.Type.KNIGHT, isWhite, 3.0);
		this.isPinned = false;
	}
	
	public boolean getIsPinned() {
		return this.isPinned;
	}
	
	public String getInitial() {
		return "N";
	}
	
	public void setIsPinned() {
		this.isPinned = true;
	}
	
	public void removePin() {
		this.isPinned = false;
	}
	
	@Override
	public List<Square> getValidMoves() {
		checkForPins();
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		King k = null;
		for (Piece p: this.getBoard().getPieces()) {
			if (p.getType().equals(Piece.Type.KING) && p.getIsWhite() == this.getIsWhite()) {
				k = (King) p;
			}
		}
//		if (k!= null) {
//			if (!k.getIsInDoubleCheck()) {
			if (!this.getPlayer().getIsInDoubleCheck()) {
//				if (!k.checkForChecks()) {
				if (!this.getPlayer().getIsInCheck()) {
					for (int i = 0; i < this.getBoard().getSquares().length; i++) {
						if (!this.isPinned) {
							if (this.getBoard().getSquares()[i].isAKnightMoveFrom(this.getPosition())) {
								if (!this.getBoard().getSquares()[i].getIsOccupied()) {
									validMoves.add(this.getBoard().getSquares()[i]);
								} else if (this.getBoard().getSquares()[i].getOccupant().getIsWhite() != this.getIsWhite()){
									validMoves.add(this.getBoard().getSquares()[i]);
								}
							}
						} 
					}
				} else {
					if (k.getChecker() == null) {
						if (!k.checkForChecks()) {
							return this.getValidMoves();
						} else {
							for (Piece pez: this.getPlayer().getOpponent().getPieces()) {
								for (Square sz: pez.getPotentialMoves()) {
									if (sz.equals(k.getPosition())) {
										k.setChecker(pez);
										return this.getValidMoves();
									}
								}
							}
						}
					} else if (((King) this.getPlayer().getKing()).getChecker().getType().equals(Piece.Type.KNIGHT)) {
						for (Square s: this.getMovesWithoutChecks()) {
							if (s.equals(((King) this.getPlayer().getKing()).getChecker().getPosition())) {
								validMoves.add(s);
							}
						}
					} else {
						//if king is in check
						((King) this.getPlayer().getKing()).checkForChecks();
//						Square scheck = null;
//						for (Piece pce: this.getPlayer().getOpponent().getPieces()) {
//							for (Square stem: pce.getPotentialMoves()) {
//								if (stem.equals(((King) this.getPlayer().getKing()).getPosition())) {
//									scheck = pce.getPosition();
//								}
//							}
//						}
//						if (scheck == null) {System.out.println("What is going on");}
//						else {
//							for (Square s1: this.getBoard().getLineBetweenSquares(this.getPlayer().getKing().getPosition(), scheck)) {
//								for (Square s2: this.getMovesWithoutChecks()) {
//									if (s1.equals(s2) ) {
//										validMoves.add(s2);
//									}
//								}
//							}
//						}
						for (Square s1: k.getLineToChecker()) {
							for (Square s2: this.getMovesWithoutChecks()) {
								if (s1.equals(s2)) {
									validMoves.add(s2);
								}
							}
						}
					}
				}
			} else {System.out.println("Double check");}
//		}
		
		return validMoves;
	}
	
	public List<Square> getDefensiveMoves() {
		List<Square> squares = new ArrayList<Square>();
		for (int i = 0; i < this.getBoard().getSquares().length; i++) {
			if (!this.isPinned) {
				if (this.getBoard().getSquares()[i].isAKnightMoveFrom(this.getPosition())) {
					if (!this.getBoard().getSquares()[i].getIsOccupied()) {
						squares.add(this.getBoard().getSquares()[i]);
					} else if (this.getBoard().getSquares()[i].getOccupant().getIsWhite() == this.getIsWhite()){
						squares.add(this.getBoard().getSquares()[i]);
					}
				}
			}
		}
		return squares;
	}
	
	public List<Square> getMovesWithoutChecks() {
		checkForPins();
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		for (int i = 0; i < this.getBoard().getSquares().length; i++) {
			if (!this.isPinned) {
				if (this.getBoard().getSquares()[i].isAKnightMoveFrom(this.getPosition())) {
					if (!this.getBoard().getSquares()[i].getIsOccupied()) {
						validMoves.add(this.getBoard().getSquares()[i]);
					} else if (this.getBoard().getSquares()[i].getOccupant().getIsWhite() != this.getIsWhite()){
						validMoves.add(this.getBoard().getSquares()[i]);
					}
				}
			}
		}
		return validMoves;
	}
	
	public List<Square> getPotentialMoves() {
		List<Square> potentialMoves = new ArrayList<Square>();
		for (int i = 0; i < this.getBoard().getSquares().length; i++) {
			if (this.getBoard().getSquares()[i].isAKnightMoveFrom(this.getPosition())) {
				if (!this.getBoard().getSquares()[i].getIsOccupied()) {
					potentialMoves.add(this.getBoard().getSquares()[i]);
				} else if (this.getBoard().getSquares()[i].getOccupant().getIsWhite() != this.getIsWhite()){
					potentialMoves.add(this.getBoard().getSquares()[i]);
				}
			}
		}
		return potentialMoves;
	}
	
	public List<Square> getPotentialMovesNoKing() {
		return this.getPotentialMoves();
	}

	public boolean checkForPins() {
		if (checkForPotentialPins()) {
			Piece.Direction d = oppositeDirection(getPinDirection());
			if (directLineToPinner(d, Piece.Type.QUEEN)) {
				this.isPinned = true;
				this.setPinnedDirection(d);
				return true;
			}
			if (d.equals(Piece.Direction.N) || d.equals(Piece.Direction.E) ||
				d.equals(Piece.Direction.W) || d.equals(Piece.Direction.S)) {
				if (directLineToPinner(d, Piece.Type.ROOK)) {
					this.isPinned = true;
					this.setPinnedDirection(d);
					return true;
				}
			} else {
				if (directLineToPinner(d, Piece.Type.BISHOP)) {
					this.isPinned = true;
					this.setPinnedDirection(d);
					return true;
				}
			}
		}
		this.isPinned = false;
		this.setPinnedDirection(null);
		return false;
	}
}
