package chess03;

//return a single, numerical value for a position
//takes in a board and has methods that culminate in assessBoard()
//when the engine picks a move, it produces an evaluation for each position
public class Evaluator {

	//----------------------------------------Fields------------------------------------------------------------------
	private Board board;
	private boolean white;
	private double eval;
	
	
	//----------------------------------------Constructor--------------------------------------------------------------
	public Evaluator(Board board, boolean white) {
		this.board = board;
		this.white = white;
		this.eval = assessBoard();
	}
	
	//----------------------------------------Getters------------------------------------------------------------------
	public Board getBoard() {
		return this.board;
	}
	
	public boolean getWhite() {
		return white;
	}
	
	public double getEval() {
		return this.eval;
	}
	
	
	//----------------------------------------MAIN__ALGORITHM----------------------------------------------------------
	
	/*
	 * --material 
	 * --center control
	 * --overall square control
	 * -~king safety**
	 * --available moves
	 * pawn structure
	 * ~-development
	 * chances
	 * attacking pressure
	 * ~-defensive security
	 */
	public double assessBoard() {
		return getBaseMaterialDifference() + getCenterControlDifference();
	}
	
	
	//-----------------------------------------Helpers-----------------------------------------------------------------
	
	//point values of all white pieces minus all black pieces
	//piece point values are based on convention
	//Pawn: 1, Knight: 3, Bishop: 3, Rook: 5, Queen: 9, King: *0*
	public double getBaseMaterialDifference() {
		double s1 = 0;
		for (Piece p: board.getWhitePlayer().getPieces()) {
			s1 += p.getBaseValue();
		}
		double s2 = 0;
		for (Piece p: board.getBlackPlayer().getPieces()) {
			s2 += p.getBaseValue();
		}
		return s1 - s2;
	}
	
	//number of available moves to center squares for both sides
	//for pawns, only moves involving captures count -- pawn on e3 only controls d4, not e4
	public double getCenterControlDifference() {
		double s1 = 0;
		for (Piece p: board.getWhitePlayer().getPieces()) {
			for (Square s: p.getDefensiveMoves()) {
				if (s.getName().equals("e4") || s.getName().equals("e5") ||
					s.getName().equals("d4") || s.getName().equals("d5")) {
						s1++;
				}
			}
		}
		double s2 = 0;
		for (Piece p: board.getBlackPlayer().getPieces()) {
			for (Square s: p.getDefensiveMoves()) {
				if (s.getName().equals("e4") || s.getName().equals("e5") ||
					s.getName().equals("d4") || s.getName().equals("d5")) {
						s2++;
				}
			}
		}
		return s1 - s2;
	}
	
	//straightforward
	//should encourage centralization of pieces, rooks getting open files, etc
	public double getAvailableMovesDifference() {
		return board.getWhitePlayer().getMoves().size() - board.getBlackPlayer().getMoves().size();
	}
	
	//returns average rank of white pieces minus avg rank of black pieces
	public double getTotalPieceAdvencementDifference() {
		double whiteRanks = 0;
		for (Piece p: board.getWhitePlayer().getPieces()) {
			whiteRanks += p.getPosition().getRank();
		}
		double blackRanks = 0;
		for (Piece p: board.getBlackPlayer().getPieces()) {
			blackRanks += (7 - p.getPosition().getRank());
		}
		return (whiteRanks/(double)board.getWhitePlayer().getPieces().size() - blackRanks/(double)board.getBlackPlayer().getPieces().size());
	}
	
	//0.3 is arbitrary and subject to change
	//this whole criterion should perhaps be reconsidered - think zugzwang
	public double getTurnDifference() {
		if (board.getWhitePlayer().getTurn()) {
			return 0.3;
		} else {
			return -0.3;
		}
	}
	
	//checks every square controlled by every piece
	//returns number with more white control minus number with more black control
	public double getTotalSquaresControlledDifference() {
		double whiteSquares = 0;
		double blackSquares = 0;
		int[] squares = new int[64];
		for (int i = 0; i < 64; i++) {
			squares[i] = 0;
		}
		for (Piece p: board.getPieces()) {
			for (Square s: p.getValidMoves()) {
				if (p.getIsWhite()) {
					squares[board.findSquareIndex(s)]++;
				} else {
					squares[board.findSquareIndex(s)]--;
				}
			}
		}
		for (int i = 0; i < 64; i++) {
			if (squares[i] < 0) {
				blackSquares--;
			} else if (squares[i] > 0) {
				whiteSquares++;
			}
		}
		return whiteSquares - blackSquares;
	}
	
	//number of undefended black pieces minus number of undefended white pieces
	//not sure how to use this
	public double getUndefendedPiecesDifference() {
		int w = 0;
		for (Piece p: board.getWhitePlayer().getPieces()) {
			if (!p.getIsDefended()) {
				w++;
			}
		}
		int b = 0;
		for (Piece p: board.getBlackPlayer().getPieces()) {
			if (!p.getIsDefended()) {
				b++;
			}
		}
		return b - w;
	}
	
	//number of less defended white pieces minus less defended black pieces
	//indication of defensive tightness
	public double getLessDefendedPiecesDifference() {
		int w = 0;
		for (Piece p: board.getWhitePlayer().getPieces()) {
			if (p.getNumberOfDefenders() - board.getBlackPlayer().getNumOfAttackersOnSquare(p.getPosition()) < 0) {
				w++;
			}
		}
		int b = 0;
		for (Piece p: board.getBlackPlayer().getPieces()) {
			if (p.getNumberOfDefenders() - board.getWhitePlayer().getNumOfAttackersOnSquare(p.getPosition()) < 0) {
				b++;
			}
		}
		return b - w;
	}
	
	//complete estimation
	//take into account other factors
	public double getKingSafetyDifference() {
		return getOpenSquaresNextToKingDifference() + (0.2)*(getAverageNumberOfOpponentMovesToKingDifference() + getAverageNumberOfOwnMovesToKingDifference());
	}
	
	//factor into king safety
	//difference in squares bordering the king that are either empty or occupied by an opponent's piece
	public double getOpenSquaresNextToKingDifference() {
		int w = 0;
		int b = 0;
		for (int i = 0; i < 64; i++) {
			if (board.getSquares()[i].borders(board.getWhitePlayer().getKing().getPosition())) {
				if (!board.getSquares()[i].getIsOccupied()) {
					w++;
				} else if (!board.getSquares()[i].getOccupant().getIsWhite()) {
					w++;
				}
			}
			if (board.getSquares()[i].borders(board.getBlackPlayer().getKing().getPosition())) {
				if (!board.getSquares()[i].getIsOccupied()) {
					b++;
				} else if (board.getSquares()[i].getOccupant().getIsWhite()) {
					b++;
				}
			}
		}
		return b - w;
	}
	
	//returns average number of moves for each piece to the opp. king, black minus white
	//doesnt take into account the board layout. Number of moves is calculated as if the board were empty
	//for bishop, rook, and queen, that number is therefore always either one or two
	//bishop color is not factored in; having a bishop target a square bordering the king is scary enough
	public double getAverageNumberOfOpponentMovesToKingDifference() {
		double w = 0;
		double b = 0;
		Square ws = board.getWhitePlayer().getKing().getPosition();
		Square bs = board.getBlackPlayer().getKing().getPosition();
		for (Piece p: board.getPieces()) {
			if (p.getIsWhite()) {
				if (p.getType().equals(Piece.Type.PAWN)) {
					w+= Math.abs(bs.getRank() - p.getPosition().getRank());
				} else if (p.getType().equals(Piece.Type.KNIGHT)) {
					w += ((Math.abs(bs.getRank() - p.getPosition().getRank()) + 
							Math.abs(bs.getFile() - p.getPosition().getFile()))/3);
				} else if (p.getType().equals(Piece.Type.BISHOP)) {
					if (bs.isDiagonalTo(p.getPosition())) {
						w+=1;
					} else {
						w +=2;
					}
				} else if (p.getType().equals(Piece.Type.ROOK)) {
					if (bs.getRank() == p.getPosition().getRank() || bs.getFile() == p.getPosition().getFile()) {
						w +=1;
					} else {
						w +=2;
					}
				} else if (p.getType().equals(Piece.Type.QUEEN)) {
					if (bs.getRank() == p.getPosition().getRank() || bs.getFile() == p.getPosition().getFile() || bs.isDiagonalTo(p.getPosition()) ) {
						w +=1;
					} else {
						w+=2;
					}
				}
			} else {
				if (p.getType().equals(Piece.Type.PAWN)) {
					b+= Math.abs(ws.getRank() - p.getPosition().getRank());
				} else if (p.getType().equals(Piece.Type.KNIGHT)) {
					b += ((Math.abs(ws.getRank() - p.getPosition().getRank()) + 
							Math.abs(ws.getFile() - p.getPosition().getFile()))/3);
				} else if (p.getType().equals(Piece.Type.BISHOP)) {
					if (ws.isDiagonalTo(p.getPosition())) {
						b+=1;
					} else {
						b +=2;
					}
				} else if (p.getType().equals(Piece.Type.ROOK)) {
					if (ws.getRank() == p.getPosition().getRank() || ws.getFile() == p.getPosition().getFile()) {
						b +=1;
					} else {
						b +=2;
					}
				} else if (p.getType().equals(Piece.Type.QUEEN)) {
					if (ws.getRank() == p.getPosition().getRank() || ws.getFile() == p.getPosition().getFile() || ws.isDiagonalTo(p.getPosition()) ) {
						b +=1;
					} else {
						b+=2;
					}
				}
			}
		}
		return (b/board.getBlackPlayer().getPieces().size()) - (w/board.getWhitePlayer().getPieces().size());
	}
	
	public double getAverageNumberOfOwnMovesToKingDifference() {
		double w = 0;
		double b = 0;
		Square ws = board.getWhitePlayer().getKing().getPosition();
		Square bs = board.getBlackPlayer().getKing().getPosition();
		for (Piece p: board.getPieces()) {
			if (p.getIsWhite()) {
				if (p.getType().equals(Piece.Type.PAWN)) {
					w+= Math.abs(ws.getRank() - p.getPosition().getRank());
				} else if (p.getType().equals(Piece.Type.KNIGHT)) {
					w += ((Math.abs(ws.getRank() - p.getPosition().getRank()) + 
							Math.abs(ws.getFile() - p.getPosition().getFile()))/3);
				} else if (p.getType().equals(Piece.Type.BISHOP)) {
					if (ws.isDiagonalTo(p.getPosition())) {
						w+=1;
					} else {
						w +=2;
					}
				} else if (p.getType().equals(Piece.Type.ROOK)) {
					if (ws.getRank() == p.getPosition().getRank() || ws.getFile() == p.getPosition().getFile()) {
						w +=1;
					} else {
						w +=2;
					}
				} else if (p.getType().equals(Piece.Type.QUEEN)) {
					if (ws.getRank() == p.getPosition().getRank() || ws.getFile() == p.getPosition().getFile() || ws.isDiagonalTo(p.getPosition()) ) {
						w +=1;
					} else {
						w+=2;
					}
				}
			} else {
				if (p.getType().equals(Piece.Type.PAWN)) {
					b+= Math.abs(bs.getRank() - p.getPosition().getRank());
				} else if (p.getType().equals(Piece.Type.KNIGHT)) {
					b += ((Math.abs(bs.getRank() - p.getPosition().getRank()) + 
							Math.abs(bs.getFile() - p.getPosition().getFile()))/3);
				} else if (p.getType().equals(Piece.Type.BISHOP)) {
					if (bs.isDiagonalTo(p.getPosition())) {
						b+=1;
					} else {
						b +=2;
					}
				} else if (p.getType().equals(Piece.Type.ROOK)) {
					if (bs.getRank() == p.getPosition().getRank() || bs.getFile() == p.getPosition().getFile()) {
						b +=1;
					} else {
						b +=2;
					}
				} else if (p.getType().equals(Piece.Type.QUEEN)) {
					if (bs.getRank() == p.getPosition().getRank() || bs.getFile() == p.getPosition().getFile() || bs.isDiagonalTo(p.getPosition()) ) {
						b +=1;
					} else {
						b+=2;
					}
				}
			}
		}
		return (b/board.getBlackPlayer().getPieces().size()) - (w/board.getWhitePlayer().getPieces().size());
	}
	
}
