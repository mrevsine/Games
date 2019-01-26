package chess03;

public class EnPassentEvent extends ModelEvent {

	private boolean white;
	private int file;
	
	public EnPassentEvent(boolean white, int file) {
		this.white = white;
		this.file = file;
	}
	
	public boolean getIsWhite() {
		return white;
	}
	
	public int getFile() {
		return file;
	}
	
	@Override
	public boolean getIsEnPassentEvent() {
		return true;
	}
}
