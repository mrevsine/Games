package chess03;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends PieceImpl {

	private boolean isPinned;
	
	public Bishop(boolean isWhite) {
		super(Piece.Type.BISHOP, isWhite, 3.0);
		this.isPinned = false;
	}
	
	public boolean getIsPinned() {
		return this.isPinned;
	}
	
	public String getInitial() {
		return "B";
	}

	public void setIsPinned() {
		this.isPinned = true;
	}
	
	public void removePin() {
		this.isPinned = false;
	}
	
	public boolean getIsLightSquared() {
		if ((this.getPosition().getRank() + this.getPosition().getFile()) % 2 == 0) {
			return false;
		} else {
			return true;
		}
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
					if (!this.isPinned) {
						List<Square> tempNE = this.getMovesInDirection(Piece.Direction.NE);
						List<Square> tempSE = this.getMovesInDirection(Piece.Direction.SE);
						List<Square> tempSW = this.getMovesInDirection(Piece.Direction.SW);
						List<Square> tempNW = this.getMovesInDirection(Piece.Direction.NW);
						for (Square s: tempNE) {
							validMoves.add(s);
						}
						for (Square s: tempSE) {
							validMoves.add(s);
						}
						for (Square s: tempSW) {
							validMoves.add(s);
						}
						for (Square s: tempNW) {
							validMoves.add(s);
						}
					} else {
						if (getPinDirection().equals(Piece.Direction.NE) || getPinDirection().equals(Piece.Direction.SE) ||
							getPinDirection().equals(Piece.Direction.SW) || getPinDirection().equals(Piece.Direction.NW)) {
								List<Square> tempA = this.getMovesInDirection(getPinDirection());
								List<Square> tempB = this.getMovesInDirection(oppositeDirection(getPinDirection()));
								for (Square s: tempA) {
									validMoves.add(s);
								}
								for (Square s: tempB) {
									validMoves.add(s);
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
			}
// 		}
		return validMoves;
	}
	
	public List<Square> getDefensiveMoves() {
		List<Square> squares = new ArrayList<Square>();
		if (!this.isPinned) {
			List<Square> tempNE = this.getDefensiveMovesInDirection(Piece.Direction.NE);
			List<Square> tempSE = this.getDefensiveMovesInDirection(Piece.Direction.SE);
			List<Square> tempSW = this.getDefensiveMovesInDirection(Piece.Direction.SW);
			List<Square> tempNW = this.getDefensiveMovesInDirection(Piece.Direction.NW);
			for (Square s: tempNE) {
				squares.add(s);
			}
			for (Square s: tempSE) {
				squares.add(s);
			}
			for (Square s: tempSW) {
				squares.add(s);
			}
			for (Square s: tempNW) {
				squares.add(s);
			}
		}
		return squares;
	}
	
	public List<Square> getPotentialMoves() {
		List<Square> potentialMoves = new ArrayList<Square>();
		List<Square> tempNE = this.getMovesInDirection(Piece.Direction.NE);
		List<Square> tempSE = this.getMovesInDirection(Piece.Direction.SE);
		List<Square> tempSW = this.getMovesInDirection(Piece.Direction.SW);
		List<Square> tempNW = this.getMovesInDirection(Piece.Direction.NW);
		for (Square s: tempNE) {
			potentialMoves.add(s);
		}
		for (Square s: tempSE) {
			potentialMoves.add(s);
		}
		for (Square s: tempSW) {
			potentialMoves.add(s);
		}
		for (Square s: tempNW) {
			potentialMoves.add(s);
		}
		return potentialMoves;
	}
	
	public List<Square> getPotentialMovesNoKing() {
		List<Square> potentialMoves = new ArrayList<Square>();
		List<Square> tempNE = this.getMovesInDirectionNoKing(Piece.Direction.NE);
		List<Square> tempSE = this.getMovesInDirectionNoKing(Piece.Direction.SE);
		List<Square> tempSW = this.getMovesInDirectionNoKing(Piece.Direction.SW);
		List<Square> tempNW = this.getMovesInDirectionNoKing(Piece.Direction.NW);
		for (Square s: tempNE) {
			potentialMoves.add(s);
		}
		for (Square s: tempSE) {
			potentialMoves.add(s);
		}
		for (Square s: tempSW) {
			potentialMoves.add(s);
		}
		for (Square s: tempNW) {
			potentialMoves.add(s);
		}
		return potentialMoves;
	}
	
	public List<Square> getMovesWithoutChecks() {
		checkForPins();
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		if (!this.isPinned) {
			List<Square> tempNE = this.getMovesInDirection(Piece.Direction.NE);
			List<Square> tempSE = this.getMovesInDirection(Piece.Direction.SE);
			List<Square> tempSW = this.getMovesInDirection(Piece.Direction.SW);
			List<Square> tempNW = this.getMovesInDirection(Piece.Direction.NW);
			for (Square s: tempNE) {
				validMoves.add(s);
			}
			for (Square s: tempSE) {
				validMoves.add(s);
			}
			for (Square s: tempSW) {
				validMoves.add(s);
			}
			for (Square s: tempNW) {
				validMoves.add(s);
			}
		} else {
			if (getPinDirection().equals(Piece.Direction.NE) || getPinDirection().equals(Piece.Direction.SE) ||
					getPinDirection().equals(Piece.Direction.SW) || getPinDirection().equals(Piece.Direction.NW)) {
				List<Square> tempA = this.getMovesInDirection(getPinDirection());
				List<Square> tempB = this.getMovesInDirection(oppositeDirection(getPinDirection()));
				for (Square s: tempA) {
					validMoves.add(s);
				}
				for (Square s: tempB) {
					validMoves.add(s);
				}
			}
		}
		return validMoves;
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
