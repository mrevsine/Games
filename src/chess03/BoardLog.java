package chess03;

import java.util.ArrayList;
import java.util.List;

public class BoardLog {

	private List<BoardRep> boards;
	private GameModel game;
	
	public BoardLog(GameModel game) {
		this.boards = new ArrayList<BoardRep>();
		this.game = game;
		BoardRep b = new BoardRep(game.getBoard());
//		CustomBoard b = new CustomBoard();
//		for (int i = 0; i < 2; i++) {
//			boolean white = false;
//			if (i == 0) {
//				white = true;
//			}
//			Pawn p1 = new Pawn(white);
//			Pawn p2 = new Pawn(white);
//			Pawn p3 = new Pawn(white);
//			Pawn p4 = new Pawn(white);
//			Pawn p5 = new Pawn(white);
//			Pawn p6 = new Pawn(white);
//			Pawn p7 = new Pawn(white);
//			Pawn p8 = new Pawn(white);
//			
//			Rook r1 = new Rook(white);
//			Knight n1 = new Knight(white);
//			Bishop b1 = new Bishop(white);
//			King k = new King(white);
//			Queen q = new Queen(white);
//			Bishop b2 = new Bishop(white);
//			Knight n2 = new Knight(white);
//			Rook r2 = new Rook(white);
//			
//			if (white) {
//				b.addPiece(r1, 0);
//				b.addPiece(n1, 1);
//				b.addPiece(b1, 2);
//				b.addPiece(q, 3);
//				b.addPiece(k, 4);
//				b.addPiece(b2, 5);
//				b.addPiece(n2, 6);
//				b.addPiece(r2, 7);
//				
//				b.addPiece(p1, 8);
//				b.addPiece(p2, 9);
//				b.addPiece(p3, 10);
//				b.addPiece(p4, 11);
//				b.addPiece(p5, 12);
//				b.addPiece(p6, 13);
//				b.addPiece(p7, 14);
//				b.addPiece(p8, 15);
//			} else {
//				b.addPiece(r1, 56);
//				b.addPiece(n1, 57);
//				b.addPiece(b1, 58);
//				b.addPiece(q, 59);
//				b.addPiece(k, 60);
//				b.addPiece(b2, 61);
//				b.addPiece(n2, 62);
//				b.addPiece(r2, 63);
//				
//				b.addPiece(p1, 48);
//				b.addPiece(p2, 49);
//				b.addPiece(p3, 50);
//				b.addPiece(p4, 51);
//				b.addPiece(p5, 52);
//				b.addPiece(p6, 53);
//				b.addPiece(p7, 54);
//				b.addPiece(p8, 55);
//			}
//		}
		b.setHalfTurnAdded(0);
		this.boards.add(b);
	}
	
	public List<BoardRep> getBoards() {
		return boards;
	}
	
	public GameModel getGame() {
		return game;
	}
	
	public void addBoard(Board b) {
		boards.add(new BoardRep(b));
		checkForDraw();
	}
	
	public void checkForDraw() {
		BoardRep b2 = boards.get(boards.size() - 1);
		for (BoardRep b: boards) {
			if (b2.getRepeatCount() >= 3) {
				game.announceOutcome(Game.Outcomes.DRAW);
				break;
			}
			if (b.getHalfTurnAdded() != b2.getHalfTurnAdded()) {
				if (b.equals(b2)) {
					b.incrementRepeatCount();
					b2.incrementRepeatCount();
				}
			}
		}
	}

}
