package chess03;

import java.util.List;

public class ValidMovesEvent extends ModelEvent {

	private List<Square> validSquares;
	
	public ValidMovesEvent(List<Square> squares) {
		super();
		validSquares = squares;
	}
	
	public List<Square> getValidSquares() {
		return validSquares;
	}
}
