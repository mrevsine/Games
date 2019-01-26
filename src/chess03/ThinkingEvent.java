package chess03;

public class ThinkingEvent extends ModelEvent {

	private boolean startedThinking;
	private Square initialSquare;
	private Square finalSquare;
	
	public ThinkingEvent(boolean started, Square initial, Square fin) {
		super();
		startedThinking = started;
		initialSquare = initial;
		finalSquare = fin;
	}
	
	public boolean getStartedThinking() {
		return startedThinking;
	}
	
	public Square getInitialSquare() {
		return initialSquare;
	}
	
	public Square getFinalSquare() {
		return finalSquare;
	}
	
	@Override
	public boolean getIsThinkingEvent() {
		return true;
	}
	
}
