package chess03;

//used in setting up board without pieces at starting squares. Helpful for running mate in one puzzles
public class CustomBoard extends Board {

	//same as regular board, only thing different is players' pieces
	public CustomBoard() {
		super();
		this.setGameModel(new GameModel());
	}
	
	// used in board.clone(), which i might change later
	public void addPiece(Piece p, int n) {
		if (n > -1 && n < 64) {
			p.setPosition(this.getSquares()[n]);
//			this.getSquares()[n].setOccupant(p);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	//used to set up a piece in a certain position
	//kings aren't added; custom players start out with a king to avoid errors, "placing" a king just sets--
	//--the position of the existing one
	public void customPiece(Piece p, int n) {
		if (p.getType() != Piece.Type.KING) {
			if (p.getIsWhite()) {
				p.setPlayer(this.getWhitePlayer());
				this.getWhitePlayer().getPieces().add(p);
			} else {
				p.setPlayer(this.getBlackPlayer());
				this.getBlackPlayer().getPieces().add(p);
			}
			if (n > -1 && n < 64) {
				this.initializePiece(p, getSquareAtPosition(n));
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			if (n > -1 && n < 64) {
				if (p.getIsWhite()) {
					((King)this.getWhitePlayer().getKing()).getPosition().removeOccupant();
					((King)this.getWhitePlayer().getKing()).setPosition(this.getSquareAtPosition(n));
				} else {
					((King)this.getBlackPlayer().getKing()).getPosition().removeOccupant();
					((King)this.getBlackPlayer().getKing()).setPosition(this.getSquareAtPosition(n));
				}
			}
		}
	}
		
}
