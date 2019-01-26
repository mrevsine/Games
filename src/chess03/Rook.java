package chess03;

import java.util.ArrayList;
import java.util.List;

public class Rook extends PieceImpl {

	private boolean isPinned;
	private boolean hasMoved;
	
	public Rook(boolean isWhite) {
		super(Piece.Type.ROOK, isWhite, 5.0);
		this.isPinned = false;
		this.hasMoved = false;
	}
	
	public boolean getIsPinned() {
		return this.isPinned;
	}
	
	public boolean getHasMoved() {
		return this.hasMoved;
	}
	
	public String getInitial() {
		return "R";
	}

	public void setIsPinned() {
		this.isPinned = true;
	}
	
	public void setHasMoved() {
		this.hasMoved = true;
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
					if (!this.isPinned) {
						List<Square> tempN = this.getMovesInDirection(Piece.Direction.N);
						List<Square> tempE = this.getMovesInDirection(Piece.Direction.E);
						List<Square> tempW = this.getMovesInDirection(Piece.Direction.W);
						List<Square> tempS = this.getMovesInDirection(Piece.Direction.S);
						for (Square s: tempN) {
							validMoves.add(s);
						}
						for (Square s: tempE) {
							validMoves.add(s);
						}
						for (Square s: tempW) {
							validMoves.add(s);
						}
						for (Square s: tempS) {
							validMoves.add(s);
						}
					} else {
						if (getPinDirection().equals(Piece.Direction.N) || getPinDirection().equals(Piece.Direction.E) ||
							getPinDirection().equals(Piece.Direction.S) || getPinDirection().equals(Piece.Direction.W)) {
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
//		}
		return validMoves;
	}
	
	public List<Square> getDefensiveMoves() {
		List<Square> squares = new ArrayList<Square>();
		if (!this.isPinned) {
			List<Square> tempN = this.getDefensiveMovesInDirection(Piece.Direction.N);
			List<Square> tempE = this.getDefensiveMovesInDirection(Piece.Direction.E);
			List<Square> tempW = this.getDefensiveMovesInDirection(Piece.Direction.W);
			List<Square> tempS = this.getDefensiveMovesInDirection(Piece.Direction.S);
			for (Square s: tempN) {
				squares.add(s);
			}
			for (Square s: tempE) {
				squares.add(s);
			}
			for (Square s: tempW) {
				squares.add(s);
			}
			for (Square s: tempS) {
				squares.add(s);
			}
		}
		return squares;
	}
	
	public List<Square> getMovesWithoutChecks() {
		checkForPins();
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		if (!this.isPinned) {
			List<Square> tempN = this.getMovesInDirection(Piece.Direction.N);
			List<Square> tempE = this.getMovesInDirection(Piece.Direction.E);
			List<Square> tempW = this.getMovesInDirection(Piece.Direction.W);
			List<Square> tempS = this.getMovesInDirection(Piece.Direction.S);
			for (Square s: tempN) {
				validMoves.add(s);
			}
			for (Square s: tempE) {
				validMoves.add(s);
			}
			for (Square s: tempW) {
				validMoves.add(s);
			}
			for (Square s: tempS) {
				validMoves.add(s);
			}
		} else {
			if (getPinDirection().equals(Piece.Direction.N) || getPinDirection().equals(Piece.Direction.E) ||
				getPinDirection().equals(Piece.Direction.S) || getPinDirection().equals(Piece.Direction.W)) {
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
	
	public List<Square> getPotentialMoves() {
		List<Square> potentialMoves = new ArrayList<Square>();
		List<Square> tempN = this.getMovesInDirection(Piece.Direction.N);
		List<Square> tempE = this.getMovesInDirection(Piece.Direction.E);
		List<Square> tempW = this.getMovesInDirection(Piece.Direction.W);
		List<Square> tempS = this.getMovesInDirection(Piece.Direction.S);
		for (Square s: tempN) {
			potentialMoves.add(s);
		}
		for (Square s: tempE) {
			potentialMoves.add(s);
		}
		for (Square s: tempW) {
			potentialMoves.add(s);
		}
		for (Square s: tempS) {
			potentialMoves.add(s);
		}
		return potentialMoves;
	}
	
	public List<Square> getPotentialMovesNoKing() {
		List<Square> potentialMoves = new ArrayList<Square>();
		List<Square> tempN = this.getMovesInDirectionNoKing(Piece.Direction.N);
		List<Square> tempE = this.getMovesInDirectionNoKing(Piece.Direction.E);
		List<Square> tempW = this.getMovesInDirectionNoKing(Piece.Direction.W);
		List<Square> tempS = this.getMovesInDirectionNoKing(Piece.Direction.S);
		for (Square s: tempN) {
			potentialMoves.add(s);
		}
		for (Square s: tempE) {
			potentialMoves.add(s);
		}
		for (Square s: tempW) {
			potentialMoves.add(s);
		}
		for (Square s: tempS) {
			potentialMoves.add(s);
		}
		return potentialMoves;
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
