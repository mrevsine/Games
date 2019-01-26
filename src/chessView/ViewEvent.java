package chessView;

abstract public class ViewEvent {

	private SquareView initialSquare;
	private SquareView finalSquare;
	
	public ViewEvent(SquareView s1, SquareView s2) {
		initialSquare = s1;
		finalSquare = s2;
	}
	
	public SquareView getInitialSquare() {
		return initialSquare;
	}
	
	public SquareView getFinalSquare() {
		return finalSquare;
	}
	
	//if the move is attempted, this is true. Else, this is false
	public boolean getLiteral() {
		return false;
	}
	
	public boolean isPromotion() {
		return false;
	}
	
	public boolean getIsEstablishEngineEvent() {
		return false;
	}
	
	public boolean getIsEstablishHumanEvent() {
		return false;
	}
	
}


