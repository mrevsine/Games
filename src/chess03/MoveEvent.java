package chess03;

public class MoveEvent {

	public enum EventType {PIECE_MOVED, PIECE_PLACED, PIECE_CAPTURED};
	private EventType type;
	private Square startingPosition;
	private Square finalPosition;
	private PieceImpl piece;
	
	public MoveEvent(EventType type, PieceImpl p, Square s) {
		this.type = type;
		this.finalPosition = s;
		this.piece = p;
		this.startingPosition = p.getPosition();
	}
	
	public EventType getType() {
		return this.type;
	}
	
	public Square getStartingPosition() {
		return this.startingPosition;
	}
	
	public Square getFinalPosition() {
		return this.finalPosition;
	}

	public Piece getPiece() {
		return this.piece;
	}
}
