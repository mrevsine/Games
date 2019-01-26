package chessView;

public class MoveEvent extends ViewEvent {
	
	public MoveEvent(SquareView sI, SquareView sF) {
		super(sI, sF);
	}
	
	@Override
	public boolean getLiteral() {
		return true;
	}
}
