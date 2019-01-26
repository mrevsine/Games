package chess03;

public class CustomPlayerImpl extends PlayerImpl {

	public CustomPlayerImpl(Board b, boolean white) {
		super(b, white, false);
//		for (int i = 0; i < 64; i++) {
//			if (b.getSquares()[i].getIsOccupied()) {
//				if (b.getSquares()[i].getOccupant().getIsWhite() == white) {
//					this.getPieces().remove(b.getSquares()[i].getOccupant());
//					b.clearSquareAtPosition(i);
//				}
//			}
//		}
		
		//avoid errors, players always have to have a king
//		setUpKing();
	}
	
	public void setUpKing() {
		King k = new King(this.getIsWhite());
		int n = 4;
		if (!this.getIsWhite()) {
			n = 60;
		}
		this.getBoard().initializePiece(k, this.getBoard().getSquareAtPosition(n));
		this.getPieces().add(k);
		k.setPlayer(this);
	}
}
