package chess03;

public class CastleMove extends Move {

	private boolean kingsideCastle;
	
	public CastleMove(Piece p, Square s, Square ogs, boolean occupied, boolean check, boolean checkmate, boolean b) {
		super(p,s, ogs, occupied, check, checkmate);
		this.kingsideCastle = b;
	}
	
	public CastleMove(Piece p, Square s, Square ogs, boolean occupied, boolean b) {
		super(p,s, occupied, ogs);
		this.kingsideCastle = b;
	}
	
	public boolean getIsKingsideCastle() {
		return this.kingsideCastle;
	}
	
	@Override
	public String getName() {
		if (kingsideCastle) {
			return "O-O";
		} else {
			return "O-O-O";
		}
	}
}
