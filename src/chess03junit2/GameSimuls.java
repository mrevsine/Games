package chess03junit2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import chess03.*;

import org.junit.jupiter.api.Test;


class GameSimuls {

//	@Test
//	void generateGame() {
//		outerLoop:
//		for (int j = 0; j < 100; j++) {
//			GameModel g = new GameModel();
//			for (int i = 0; i < 100; i++) {
//				if (g.getOutcome() != null) {
//					continue outerLoop;
//				}
//				Player p = g.getPlayerWithTurn();
//				int n = ((int) (Math.random() * p.getMoves().size()));
//				Move m;
//				if (n < p.getMoves().size()) {
//					m = p.getMoves().get(n);
//				} else {
//					int num = p.getMoves().size();
//					if (num == 0) {
//						if (((King)p.getKing()).checkForChecks()) {
//							System.out.println(g.getMoveLog().movesToString());
//							if (p.getIsWhite()) {
//								g.announceOutcome(Game.Outcomes.BLACK_WINS);
//								System.out.println(g.getMoveLog().movesToString());
//								continue outerLoop;
//							} else {
//								g.announceOutcome(Game.Outcomes.WHITE_WINS);
//								System.out.println(g.getMoveLog().movesToString());
//								continue outerLoop;
//							}
//						} else {
//							g.announceOutcome(Game.Outcomes.DRAW);
//							System.out.println(g.getMoveLog().movesToString());
//							continue outerLoop;
//						}
//					} else {
//						m = p.getMoves().get(0);
//					}
//				}
//				if (m.getSquare().getIsOccupied() && m.getSquare().getOccupant().getType().equals(Piece.Type.KING)) {
//					System.out.println(g.getMoveLog().movesToString());
//					System.out.println("Maybe thats why");
//					try {
//						m = p.getMoves().get(n - 1);
//					} catch (IndexOutOfBoundsException e) {
//						System.out.println("oops");
//						m = p.getMoves().get(n + 1);
//					}
//					
//				}
//				p.move(m);
//				
////				catch (Exception e) {
////					System.out.println("Caught");
//////					System.out.println(g.getMoveLog().movesToString());
////					break;
////				}
//				
//			}
//			System.out.println(Integer.toString(j+1));
//			System.out.println(g.getMoveLog().movesToString());
//		}
//		
//	}

	
//	@Test
//	void draw() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("Nf3");
//		p2.move("Nc6");
//		p1.move("Ng1");
//		p2.move("Nb8");
//		p1.move("Nf3");
//		p2.move("Nc6");
//		p1.move("Ng1");
//		p2.move("Nb8");
//	}
	
//	@Test
//	void matches() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		assertEquals(1, g.getBoardLog().getBoards().size());
//		assertTrue(new BoardRep(b).equals(g.getBoardLog().getBoards().get(0)));
//	}
	
//	@Test
//	void getLineToChecker() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("f4");
//		p2.move("e6");
//		p1.move("g4");
//		p2.move("Qh4");
//		for (Square s: ((King)p1.getKing()).getLineToChecker()) {
//			System.out.println(s.getName());
//		}
//	}
	
//	@Test
//	void promotions() {
//		GameModel g = new GameModel();
//		String[] s = {"g4", "h5", "h5", "Rh6", "h3", "Ra6", "h6", "Rb6", "h7", "Ra6", "h8=Q"};
//		g.move(s);
//		for (Move m: g.getWhitePlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void naming() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
////			System.out.println(m.getPiece().getType().toString() + m.getSquare().getName());
//		}
//	}
	
//	@Test
//	void remake() {
//		GameModel g = new GameModel();
//		assertEquals(64,g.getBoard().getSquares().length);
//		assertEquals(16,g.getBlackPlayer().getPieces().size());
//		assertEquals(g.getBoard(),g.getWhitePlayer().getBoard());
//	}
	
	
//	@Test
//	void seesMate() {
//		GameModel g = new GameModel();
//		g.move("e4");
//		g.move("e5");
//		g.move("Bc4");
//		g.move("Nc6");
//		g.move("Qh5");
//		g.move("Nf6");
//		Variation v = new Variation(g.getBoard(), "Qxf7");
//		assertTrue(v.getIsCheckmate());
//	}
//	
//	@Test
//	void findMate() {
//		GameModel g = new GameModel();
//		g.move("f4");
//		g.move("e5");
//		g.move("g4");
//		for (Move m: g.getPlayerWithTurn().getMoves()) {
//			Variation v = new Variation(g.getBoard(), m);
//			if (v.getIsCheckmate()) {
//				System.out.println(m.getName());
//			}
//		}
//	}
	
//	@Test
//	void findAnotherMate() {
//		GameModel g = new GameModel();
//		g.move("e4"); g.move("e5"); g.move("Nf3"); g.move("Nc6"); g.move("Bc4"); g.move("Nd4"); g.move("Nxe5"); 
//		g.move("Qg5"); g.move("Nxf7"); g.move("Qxg2"); g.move("Rf1"); g.move("Qxe4"); g.move("Be2");
//		for (Move m: g.getPlayerWithTurn().getMoves()) {
//			if (new Variation(g.getBoard(), m).getIsCheckmate()) {
//				System.out.println(m.getName());
//			}
//		}
//	}
	
//	@Test
//	void pawnBorders() {
//		GameModel g = new GameModel();
//		g.move("h4"); g.move("a5"); g.move("a4"); g.move("h5");
//		for (Move m: g.getWhitePlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void checks() {
//		GameModel g = new GameModel();
//		g.move("e4");
//		g.move("e5");
//		g.move("Bc4");
//		g.move("Nf6");
//		g.move("Bxf7");
//		for (Move m: g.getBlackPlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void pawnCheck() {
//		GameModel g = new GameModel();
//		g.move("e4");
//		g.move("h6");
//		g.move("e5");
//		g.move("h5");
//		g.move("e6");
//		g.move("h4");
//		g.move("exf7");
//		for (Move m: g.getBlackPlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
//	
//	@Test
//	void knightCheck() {
//		GameModel g = new GameModel();
//		g.move("Nc3");
//		g.move("a6");
//		g.move("Ne4");
//		g.move("a5");
//		g.move("Nf6");
//		for (Move m: g.getBlackPlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void ogMoves() {
//		GameModel g = new GameModel();
//		for (Move m: g.getWhitePlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void customBoard() {
//		CustomBoard b = new CustomBoard();
//		Player p1 = new CustomPlayerImpl(b, true);
//		Player p2 = new CustomPlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		b.customPiece(new King(true), 63);
//		b.customPiece(new King(false), 0);
//		b.customPiece(new Rook(false), 53);
//		b.customPiece(new Rook(false), 44);
//		GameModel g = new GameModel(b);
//		g.setTurn(false);
//		for (Move m: g.getPlayerWithTurn().getMoves()) {
//			Variation v = new Variation(g.getBoard(), m);
//				if (v.getIsCheckmate()) {
//					System.out.println(m.getName());
//				}
//		}
//		assertEquals(4, b.getPieces().size());
//		assertEquals(3, p2.getPieces().size());
//		assertEquals(1, p1.getPieces().size());
//		assertTrue(b.getSquareAtPosition(63).getIsOccupied());
//		assertEquals(b.getSquareAtPosition(63).getOccupant(), p1.getPieces().get(0));
//		assertFalse(b.getSquareAtPosition(5).getIsOccupied());
//	}
	
//	@Test
//	void boardRep() {
//		GameModel g = new GameModel();
//		BoardRep b = new BoardRep(g.getBoard());
//		assertEquals(0, b.getHalfTurnAdded());
//		assertEquals(1, b.getRepeatCount());
//		System.out.println(b.getState());
//	}
	
	
//	@Test
//	void mateInTwo() {
//		CustomBoard b = new CustomBoard();
//		Player p1 = new CustomPlayerImpl(b, true);
//		Player p2 = new CustomPlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		b.customPiece(new King(false), 63);
//		b.customPiece(new King(true), 0);
//		b.customPiece(new Rook(true), 33);
//		b.customPiece(new Rook(true), 24);
//		g.setTurn(true);
//		Player pl = g.getPlayerWithTurn();
//		int num = 0;
//		for (Move m: pl.getMoves()) {
////			System.out.println(m.getName());
////			assertEquals(g.getWhitePlayer(), pl);
//			Variation v = new Variation(g.getBoard(), m);
//			for (Move m2: v.getBoard().getBlackPlayer().getMoves()) {
////				System.out.println(m2.getName());
//				v.getBoard().getGameModel().setTurn(false);
////				assertEquals(g.getBlackPlayer(), pl.getOpponent());
//				Variation v2 = new Variation(v, m2);
//				for (Move m3: v2.getBoard().getWhitePlayer().getMoves()) {
////					System.out.println(v2.getBoard().getSquareAtPosition(4).getIsOccupied());
////					assertEquals(g.getWhitePlayer(), pl);
//					v2.getBoard().getGameModel().setTurn(true);
//					Variation v3 = new Variation(v2, m3);
//					num++;
////					System.out.println("1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName());
//					if (v3.getIsCheckmate()) {
//						System.out.println("Checkmate:" + "1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName());
//					}
//					
////					if (v3.getIsCheck()) {
////						System.out.println("Check: " + "1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName());
////					}
////					try {
////						if (v3.getIsCheckmate()) {
////							System.out.println("1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName());
////							break;
////						}
////					} catch (NullPointerException e) {
////						System.out.println(new BoardRep(v3.getBoard()).getState());
////						System.out.println("1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName());
////						break;
////					}
//					
//				}
//			}
//		}
////		System.out.println(num);
//	}
//	
//	@Test
//	void subVariation() {
//		CustomBoard b = new CustomBoard();
//		Player p1 = new CustomPlayerImpl(b, true);
//		Player p2 = new CustomPlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
////		System.out.println(new BoardRep(b).getState());
//		b.customPiece(new King(true), 59);
//		b.customPiece(new Queen(true), 1);
//		b.customPiece(new King(false), 63);
//		g.setTurn(false);
////		System.out.println(new BoardRep(b).getState());
////		System.out.println(g.getBlackPlayer().getMoves().get(0).getName());
//		Variation v = new Variation(g.getBoard(), g.getBlackPlayer().getMoves().get(0));
////		System.out.println(new BoardRep(v.getBoard()).getState());
//		g.setTurn(true);
////		System.out.println(v.getBoard().getWhitePlayer().getMoves().get(0).getName());
//		Variation v2 = new Variation(v, v.getBoard().getWhitePlayer().getMoves().get(0));
////		System.out.println(new BoardRep(v2.getBoard()).getState());
//	}
	
	
//	@Test
//	void mate() {
//		CustomBoard b = new CustomBoard();
//		Player p1 = new CustomPlayerImpl(b, true);
//		Player p2 = new CustomPlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		b.customPiece(new King(false), 63);
//		b.customPiece(new King(true), 0);
//		b.customPiece(new Rook(true), 40);
//		b.customPiece(new Rook(true), 33);
//		g.setTurn(true);
//		System.out.println(g.getWhitePlayer().getMoves().get(17).getName());
//		Variation v = new Variation(g.getBoard(), g.getWhitePlayer().getMoves().get(17));
//		System.out.println(v.getBoard().getBlackPlayer().getMoves().get(0).getName());
//		Variation v2 = new Variation(v, v.getBoard().getBlackPlayer().getMoves().get(0));
//		System.out.println(v2.getBoard().getWhitePlayer().getMoves().get(4).getName());
//		Variation v3 = new Variation(v2, v2.getBoard().getWhitePlayer().getMoves().get(4));
//		System.out.println(v3.getIsCheckmate());
//		System.out.println(new BoardRep(v3.getBoard()).getState());
//		System.out.println(((King)v3.getBoard().getBlackPlayer().getKing()).checkForChecksAt(v3.getBoard().getSquares()[63]));
//		for (Move m: v3.getBoard().getWhitePlayer().getMoves()) {
//			System.out.println("White move: " + m.getName());
//		}
//		for (Move m: v3.getBoard().getBlackPlayer().getMoves()) {
//			System.out.println("Black Move: " + m.getName());
//		}
//	}
	
//	@Test
//	void mateFromStart() {
//		GameModel g = new GameModel();
//		for (Move m: g.getPlayerWithTurn().getMoves()) {
//			Variation v = new Variation(g.getBoard(), m);
//			v.getBoard().getGameModel().setTurn(false);
//			for (Move m2: v.getBoard().getPlayerWithTurn().getMoves()) {
//				Variation v2 = new Variation(v, m2);
//				v2.getBoard().getGameModel().setTurn(true);
//				for (Move m3: v2.getBoard().getWhitePlayer().getMoves()) {
//					Variation v3 = new Variation(v2, m3);
//					v3.getBoard().getGameModel().setTurn(false);
//					for (Move m4: v3.getBoard().getBlackPlayer().getMoves()) {
//						if (m.getName().equals("a4") && m2.getName().equals("b5") && m3.getName().equals("a5")) {
//							System.out.println(new BoardRep(v3.getBoard()).getState());
//							System.out.println(v3.getBoard().getSquareByName("a4").getIsOccupied());
//							System.out.println(v3.getBoard().getSquareByName("a5").getIsOccupied());
//						}
//						System.out.println("1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName() + " " + m4.getName());
//						Variation v4 = new Variation(v3, m4);
//						if (v4.getIsCheckmate()) {
//							System.out.println("Checkmate: " + "1. " + m.getName() + " " + m2.getName() + " 2. " + m3.getName() + " " + m4.getName());
//						}
//					}
//				}
//			}
//		}
//	}
	
	@Test
	void threads() {
		GameModel g = new GameModel();
		BlankEngine e = new BlankEngine(g.getBoard());
		e.start();
		long start = System.nanoTime();
		for (Move m: g.getPlayerWithTurn().getMoves()) {
			System.out.println(m.getName());
		}
		System.out.println("Done after " + ((System.nanoTime() - start)/1000000000.0) + " seconds");
	}
}
