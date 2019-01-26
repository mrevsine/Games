package chess03;

public interface Square {

	int getRank();
	int getFile();
	String getFileName();
	boolean getIsWhite();
	boolean getIsOccupied();
	Piece getOccupant();
	String getName();
	void setOccupant(Piece p);
	void removeOccupant();
	boolean borders(Square s);
	boolean isDiagonalTo(Square s);
	boolean isAKnightMoveFrom(Square s);
}
