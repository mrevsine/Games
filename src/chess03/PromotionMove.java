package chess03;

public class PromotionMove extends Move {

	private Piece.Type type;
	
	public PromotionMove(Piece p, Square s, Square ogs, boolean occupied, boolean check, boolean checkmate, Piece.Type t) {
		super(p, s, ogs, occupied, check, checkmate);
		this.type = t;
	}
	
	public PromotionMove(Piece p, Square s, Square ogs, boolean occupied, Piece.Type t) {
		super(p, s, occupied, ogs);
		this.type = t;
	}
	
	public Piece.Type getType() {
		return this.type;
	}
	
	@Override
	public boolean getIsPromotionMove() {
		return true;
	}
	
	@Override
	public String getName() {
		String s = "";
		if (type.equals(Piece.Type.QUEEN)) {
			s = "Q";
		} else if (type.equals(Piece.Type.ROOK)) {
			s = "R";
		} else if (type.equals(Piece.Type.BISHOP)) {
			s = "B";
		} else if (type.equals(Piece.Type.KNIGHT)) {
			s = "N";
		} 
		String str = super.getName();
		if (str.substring(str.length() -1, str.length()).equals("+") || 
				str.substring(str.length() -1, str.length()).equals("#")) {
			return str.substring(0, str.length() - 1) + "=" + s + str.substring(str.length() -1, str.length());
		} else {
			return super.getName() + "=" + s;
		}
	}
}
