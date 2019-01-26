package chess03;

import java.util.List;

public interface Piece {

	public enum Type {KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN};
	public enum Direction {N, NE, E, SE, S, SW, W, NW};
	boolean getIsWhite();
	List<Square> getValidMoves();
	List<Square> getPotentialMoves();
	List<Square> getPotentialMovesNoKing();
	List<Square> getDefensiveMoves();
	List<Square> getMovesWithoutChecks();
	Square getPosition();
	String getInitial();
	Type getType();
	Player getPlayer();
	void setPlayer(Player p);
	void setPosition(Square s);
	void setBoard(Board b);
	boolean checkForPins();
	boolean getIsDefended();
	int getNumberOfDefenders();
	int getNumberOfDefendersNoKing();
	int getNumberOfMoves();
	double getBaseValue();
	int getStartingFile();
	void setStartingFile(int n);
	boolean getWasPawn();
	void setWasPawn();
	Board getBoard();
	String getName();
}
