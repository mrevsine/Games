package chess03;

import java.util.List;

public interface Player {

	void move(Piece p, Square s);
	void move(String tempStr);
	void move(Move m);
	List<Piece> getPieces();
	Board getBoard();
	boolean getTurn();
	void setTurn(boolean b);
	boolean getIsWhite();
	List<Move> getMoves();
	Piece getKingsideRook();
	Piece getQueensideRook();
	Piece getKing();
	boolean getIsInCheckmate();
	boolean getIsInStalemate();
	Player getOpponent();
	Move stringToMove(String str);
	boolean getIsInCheck();
	boolean getIsInDoubleCheck();
	void setIsInCheck(boolean b);
	void setIsInDoubleCheck(boolean b);
	void updateChecks();
	void checkForStalemate();
	int getNumOfAttackersOnSquare(Square s);
}
