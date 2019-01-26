package chess03;

public class BoardRep {

	private String state;
	private int repeatCount;
	private int halfTurnAdded;
	
	public BoardRep(Board b) {
		repeatCount = 1;
		halfTurnAdded = b.getGameModel().getMoveLog().getMoves().size();
		state = "";
		for (int i = 0; i < b.getSquares().length; i++) {
			if (i > 0) {
				state += "--";
			}
			if (!b.getSquares()[i].getIsOccupied()) {
				state += "NONE";
			} else {
				state += b.getSquares()[i].getOccupant().getInitial();
				if (b.getSquares()[i].getOccupant().getIsWhite()) {
					state += "W";
				} else {
					state += "B";
				}
				state += Integer.toString(b.getSquares()[i].getOccupant().getStartingFile());
				if (b.getSquares()[i].getOccupant().getWasPawn()) {
					state += "Y";
				} else {
					state += 'N';
				}
			}
		}
	}
	
	public String getState() {
		return state;
	}
	
	public int getRepeatCount() {
		return repeatCount;
	}
	
	public void incrementRepeatCount() {
		repeatCount++;
	}
	
	public int getHalfTurnAdded() {
		return halfTurnAdded;
	}
	
	public void setHalfTurnAdded(int n) {
		this.halfTurnAdded = n;
	}
	
	public boolean equals(BoardRep b) {
		return state.equals(b.getState());
	}
}
