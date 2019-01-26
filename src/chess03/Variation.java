package chess03;

import java.util.ArrayList;
import java.util.List;

public class Variation {

	private Board board;
	private List<Move> moves;
	
	public Variation(Board b, Move m) {
		
		this.board = b.clone();
		this.moves = new ArrayList<Move>();
		this.moves.add(m);
		
		Piece p = this.getBoard().getPieceByName(m.getPiece().getName());
		Square s = equivalentSquare(m.getSquare(), this.getBoard());
		
		
		if (p == null) {
			System.out.println(new BoardRep(board).getState());
			throw new IllegalArgumentException("Null Piece: " + m.getPiece().getName() + " to " + s.getName());	
		}
		if (s == null) {throw new IllegalArgumentException("Null Square: " + m.getSquare().getName());}
		
		
//		this.board.movePiece(board.getPieceByName(m.getPiece().getName()), board.getSquareByName(m.getSquare().getName()));
		this.board.movePiece(p, s);
	}
	
	public Variation(Variation v, Move m) {
		
		this.board = v.getBoard().clone();
		this.moves = new ArrayList<Move>();
		this.moves.add(m);
		
		Piece p = this.getBoard().getPieceByName(m.getPiece().getName());
		Square s = equivalentSquare(m.getSquare(), this.getBoard());
		
		if (p == null) {throw new IllegalArgumentException("Null Piece: " + m.getPiece().getName());}
		if (s == null) {throw new IllegalArgumentException("Null Square: " + m.getSquare().getName());}
		
		this.board.movePiece(this.getBoard().getPieceByName(m.getPiece().getName()), equivalentSquare(m.getSquare(), this.getBoard()));
	
	}
	
	public Variation(Board b, String s) {
		
		this.board = b.clone();
		this.moves = new ArrayList<Move>();
		this.moves.add(b.getPlayerWithTurn().stringToMove(s));
		this.board.movePiece(equivalentPiece(this.moves.get(0).getPiece(), this.getBoard()), equivalentSquare(this.moves.get(0).getSquare(), this.getBoard()));
	
	}
	
	public Board getBoard() {
		return board;
	}
	
	public List<Move> getMoves() {
		return moves;
	}
	
	public boolean getIsCheckmate() {
//		Boolean white = !moves.get(moves.size() - 1).getPiece().getIsWhite();
//		if (white) {
//			return board.getBlackPlayer().getIsInCheckmate();
//		} else {
//			return board.getWhitePlayer().getIsInCheckmate();
//		}
		return board.getBlackPlayer().getIsInCheckmate() || board.getBlackPlayer().getIsInCheckmate();
	}
	
	public boolean getIsCheck() {
//		Boolean white = !moves.get(moves.size() - 1).getPiece().getIsWhite();
//		if (white) {
//			return ((King) board.getBlackPlayer().getKing()).checkForChecks();
//		} else {
//			return ((King) board.getWhitePlayer().getKing()).checkForChecks();
//		}
		return ((King)board.getWhitePlayer().getKing()).getIsInCheck() || ((King) board.getBlackPlayer().getKing()).getIsInCheck();
	}
	
	public Square equivalentSquare(Square s, Board b) {
		for (int i = 0; i < 64; i++) {
			if (b.getSquares()[i].getName().equals(s.getName())) {
				return b.getSquares()[i];
			}
		}
		throw new IllegalArgumentException("Square not found");
	}
	
	public Square equivalentSquare(Square s) {
		for (int i = 0; i < 64; i++) {
			if (this.getBoard().getSquares()[i].getName().equals(s.getName())) {
				return this.getBoard().getSquares()[i];
			}
		}
		throw new IllegalArgumentException("Square not found");
	}
	
	public Piece equivalentPiece(Piece p, Board b) {
		for (int i = 0; i < 64; i++) {
			if (b.getSquares()[i].getName().equals(p.getPosition().getName())) {
				return b.getSquares()[i].getOccupant();
			}
		}
		throw new IllegalArgumentException("Piece not found");
	}
	
	public Piece equivalentPiece(Piece p) {
		for (int i = 0; i < 64; i++) {
			if (this.getBoard().getSquares()[i].getName().equals(p.getPosition().getName())) {
				if (this.getBoard().getSquares()[i].getOccupant() == null) {
					throw new RuntimeException("check piece movement");
				}
				return this.getBoard().getSquares()[i].getOccupant();
			}
		}
		throw new IllegalArgumentException("Piece not found");
	}
	
	//copy super-variation move list with this board's pieces and squares
	public List<Move> cloneArray(List<Move> moves) {
		List<Move> newMoves = new ArrayList<Move>();
		for (Move m: moves) {
			String s = m.getSquare().getName();
			Piece p = this.getBoard().getPieceByName(m.getPiece().getName());
			if (p == null) {throw new IllegalArgumentException("Piece not found");}
			String str = m.getPiece().getPosition().getName();
			Move newMove = new Move(p, this.getBoard().getSquareByName(s), this.getBoard().getSquareByName(str));
			newMoves.add(newMove);
		}
		return newMoves;
	}
	
	
}
