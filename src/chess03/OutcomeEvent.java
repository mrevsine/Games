package chess03;

public class OutcomeEvent extends ModelEvent {

	private Game.Outcomes outcome;
	
	public OutcomeEvent(Game.Outcomes o) {
		outcome = o;
	}
	
	public Game.Outcomes getOutcome() {
		return outcome;
	}
	
	@Override
	public boolean getIsOutcomeEvent() {
		return true;
	}
}
