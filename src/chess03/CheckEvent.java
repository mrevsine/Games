package chess03;

public class CheckEvent extends ModelEvent {

	private boolean white;
	
	public CheckEvent(boolean white) {
		this.white = white;
	}
	
	public boolean getIsWhite() {
		return white;
	}
	
	@Override
	public boolean getIsCheckEvent() {
		return true;
	}
}
