package chess03;

abstract public class ModelEvent {

	public boolean getIsOutcomeEvent() {
		return false;
	}
	
	public boolean getIsCastleEvent() {
		return false;
	}
	
	public boolean getIsPromotionEvent() {
		return false;
	}
	
	public boolean getIsEnPassentEvent() {
		return false;
	}
	
	//make king's square red
	public boolean getIsCheckEvent() {
		return false;
	}
	
	public boolean getIsThinkingEvent() {
		return false;
	}
}
