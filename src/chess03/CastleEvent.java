package chess03;

public class CastleEvent extends ModelEvent {
	
	private boolean white;
	private boolean kingside;

	public CastleEvent(boolean white, boolean kingside) {
		this.white = white;
		this.kingside = kingside;
	}
	
	public boolean getIsWhite() {
		return white;
	}
	
	public boolean getIsKingside() {
		return kingside;
	}
	
	@Override
	public boolean getIsCastleEvent() {
		return true;
	}
}
