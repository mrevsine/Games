package chessView;

public class EstablishHumanEvent extends ViewEvent {

	public EstablishHumanEvent(SquareView s) {
		super(s, null);
	}
	
	@Override
	public boolean getIsEstablishHumanEvent() {
		return true;
	}
	
}
