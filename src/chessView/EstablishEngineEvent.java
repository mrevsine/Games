package chessView;

public class EstablishEngineEvent extends ViewEvent {

	private boolean engineIsWhite;
	
	public EstablishEngineEvent(SquareView s, boolean white) {
		super(s, null);
		engineIsWhite = white;
	}
	
	public boolean getIsEngineWhite() {
		return engineIsWhite;
	}
	
	@Override
	public boolean getIsEstablishEngineEvent() {
		return true;
	}
}
