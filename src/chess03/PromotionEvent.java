package chess03;

public class PromotionEvent extends ModelEvent {

	private Piece.Type type;
	private Square square;
	private boolean white;
	
	public PromotionEvent(Piece.Type type, Square s, boolean white) {
		this.type = type;
		square = s;
		this.white = white;
	}
	
	public Piece.Type getType() {
		return type;
	}
	
	public Square getSquare() {
		return square;
	}
	
	public boolean getIsWhite() {
		return white;
	}
	
	@Override
	public boolean getIsPromotionEvent() {
		return true;
	}
}
