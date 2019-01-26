package chess03;

import java.util.List;
import java.util.ArrayList;

public class MoveLog {

	private List<Move> moves;
	private GameModel game;
	
	public MoveLog(GameModel game) {
		this.moves = new ArrayList<Move>();
		this.game = game;
	}
	
	public List<Move> getMoves() {
		return this.moves;
	}
	
	public GameModel getGame() {
		return game;
	}
	
	public void handleMoveEvent(Move m) {
		moves.add(m);
		checkForDraws();
	}
	
	public String movesToString() {
		String s = "";
		int i = 1;
		int j = 0;
		for (Move m: moves) {
			if (j % 2 == 0) {
				s += " ";
				s += Integer.toString(i);
				s += ".";
			} else {
				i++;
				s += " ";
			}
			s += m.getName();
			j++;
		}
		return s;
	}
	
	public void checkForDraws() {
		boolean b = true;
		if (this.moves.size() > 100) {
			for (int i = 0; i < 100; i++) {
				Move m = moves.get(moves.size() - (1 + i));
				if (m.getPiece().getType().equals(Piece.Type.PAWN) || m.getIsACapture()) {
					b = false;
					break;
				}
			}
		} else {
			b = false;
		}
		if (b) {
			this.game.announceOutcome(Game.Outcomes.DRAW);
		}
	}
}
