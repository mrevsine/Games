package chess03Junit;

import java.util.ArrayList;
import java.util.List;

import chess03.*;

class chessJunit {

//	@Test
//	void testSquares() {
//		Board b = new Board();
//		assertEquals(64, b.getSquares().length);
//	}
////	
//	@Test
//	void testName() {
//		Board b = new Board();
//		assertEquals("h8", b.getSquareAtPosition(63).getName());
//	}
////	
//	@Test 
//	void testColors() {
//		Board b = new Board();
//		assertEquals(false, b.getSquareAtPosition(0).getIsWhite());
//	}
////
//	@Test
//	void testPiecePlacement() {
//		Board b = new Board();
//		Piece p = new Bishop(true);
//		b.getSquareAtPosition(0).setOccupant(p);
//		assertEquals(true, b.getSquareAtPosition(0).getIsOccupied());
//		assertEquals(false, b.getSquareAtPosition(1).getIsOccupied());
//		assertEquals(p, b.getSquareAtPosition(0).getOccupant());
//	}
//	
//	@Test
//	void inheritanceTest() {
//		King k = new King(true);
//		assertEquals(Piece.Type.KING, k.getType());
//		assertEquals(true, k.getIsWhite());
//		assertNull(k.getPosition());
//	}
//	
//	@Test
//	void inheritBoard() {
//		Board b = new Board();
//		King k = new King(true);
//		b.initializePiece(k, b.getSquareAtPosition(0));
//		assertEquals(b, k.getBoard());
//		assertTrue(b.getSquareAtPosition(0).getIsOccupied());
//		assertTrue(k.getBoard().getSquareAtPosition(0).getIsOccupied());
//		King k2 = new King(false);
//		b.initializePiece(k2, b.getSquareAtPosition(1));
//		assertTrue(b.getSquareAtPosition(1).getIsOccupied());
//		assertTrue(k.getBoard().getSquareAtPosition(1).getIsOccupied());
//		b.clearSquareAtPosition(0);
//		assertFalse(k2.getBoard().getSquareAtPosition(0).getIsOccupied());
//	}
//	
//	@Test
//	void kingMovesIgnoreChecksCorner() {
//		Board b = new Board();
//		List<Square> squares = new ArrayList<Square>();
//		squares.add(b.getSquareAtPosition(1));
//		squares.add(b.getSquareAtPosition(8));
//		squares.add(b.getSquareAtPosition(9));
//		King k = new King(true);
//		b.initializePiece(k, b.getSquareAtPosition(0));
//		assertEquals(squares, k.getValidMoves());
//	}
//	
//	@Test
//	void kingMovesIgnoreChecksSide() {
//		Board b = new Board();
//		List<Square> squares = new ArrayList<Square>();
//		squares.add(b.getSquareAtPosition(0));
//		squares.add(b.getSquareAtPosition(1));
//		squares.add(b.getSquareAtPosition(9));
//		squares.add(b.getSquareAtPosition(16));
//		squares.add(b.getSquareAtPosition(17));
//		King k = new King(true);
//		b.initializePiece(k, b.getSquareAtPosition(8));
//		assertEquals(squares, k.getValidMoves());
//	}
//	
//	@Test
//	void kingMovesIgnoreChecksMiddle() {
//		Board b = new Board();
//		List<Square> squares = new ArrayList<Square>();
//		squares.add(b.getSquareAtPosition(0));
//		squares.add(b.getSquareAtPosition(1));
//		squares.add(b.getSquareAtPosition(2));
//		squares.add(b.getSquareAtPosition(8));
//		squares.add(b.getSquareAtPosition(10));
//		squares.add(b.getSquareAtPosition(16));
//		squares.add(b.getSquareAtPosition(17));
//		squares.add(b.getSquareAtPosition(18));
//		King k = new King(true);
//		b.initializePiece(k, b.getSquareAtPosition(9));
//		assertEquals(squares, k.getValidMoves());
//	}
//	
//	@Test 
//	void kingMovesWithOtherPiece() {
//		Board b = new Board();
//		List<Square> squares = new ArrayList<Square>();
//		squares.add(b.getSquareAtPosition(0));
//		squares.add(b.getSquareAtPosition(1));
//		squares.add(b.getSquareAtPosition(2));
//		squares.add(b.getSquareAtPosition(8));
//		squares.add(b.getSquareAtPosition(16));
//		squares.add(b.getSquareAtPosition(17));
//		squares.add(b.getSquareAtPosition(18));
//		King k = new King(true);
//		King k2 = new King(true);
//		b.initializePiece(k, b.getSquareAtPosition(9));
//		b.initializePiece(k2, b.getSquareAtPosition(10));
//		assertEquals(squares, k.getValidMoves());
//	}
//
//	
//	@Test
//	void queenTest() {
//		Board b = new Board();
//		Queen q = new Queen(true);
//		b.initializePiece(q, b.getSquareAtPosition(27));
//		Pawn p = new Pawn(false);
//		Pawn p2 = new Pawn(true);
//		Pawn p3 = new Pawn(false);
//		b.initializePiece(p3, b.getSquareAtPosition(28));
//		b.initializePiece(p2, b.getSquareAtPosition(36));
//		b.initializePiece(p, b.getSquareAtPosition(35));
//		for (Square s: q.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
//	
//	@Test
//	void knightTest() {
//		Board b = new Board();
//		List<Square> squares = new ArrayList<Square>();
//		squares.add(b.getSquareAtPosition(16));
//		squares.add(b.getSquareAtPosition(18));
//		Knight kn = new Knight(true);
//		Bishop b1 = new Bishop(true);
//		Bishop b2 = new Bishop(false);
//		b.initializePiece(kn, b.getSquareAtPosition(1));
//		b.initializePiece(b1, b.getSquareAtPosition(11));
//		b.initializePiece(b2, b.getSquareAtPosition(16));
//		assertEquals(squares, kn.getValidMoves());
//	}
//	
//	@Test
//	void bishopTest() {
//		Board b = new Board();
//		Bishop bp = new Bishop(true);
//		b.initializePiece(bp, b.getSquareAtPosition(57));
//		for (Square s: bp.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
//	
//	@Test
//	void rookTest() {
//		Board b = new Board();
//		Rook r = new Rook(true);
//		b.initializePiece(r,  b.getSquareAtPosition(47));
//		Knight k = new Knight(false);
//		b.initializePiece(k, b.getSquareAtPosition(39));
//		for (Square s: r.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
//	
//	@Test
//	void whitePawnTest() {
//		Board b = new Board();
//		Pawn wp = new Pawn(false);
//		wp.setHasMoved();
//		Pawn wpObstruct = new Pawn(true);
//		b.initializePiece(wp, b.getSquareAtPosition(27));
//		b.initializePiece(wpObstruct, b.getSquareAtPosition(25));
//		Pawn bp = new Pawn(true);
//		bp.setJustMovedOutTwo(true);
//		b.initializePiece(bp, b.getSquareAtPosition(20));
//		for (Square s: wp.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
//	
//	@Test
//	void blackPawnTest() {
//		Board b = new Board();
//		Pawn bp = new Pawn(false);
//		b.initializePiece(bp, b.getSquareAtPosition(49));
//		for (Square s: bp.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
	
//	@Test
//	void pieceList() {
//		Board b = new Board();
//		Pawn p1 = new Pawn(true);
//		Pawn p2 = new Pawn(false);
//		Queen q = new Queen(true);
//		b.initializePiece(p1, b.getSquareAtPosition(20));
//		b.initializePiece(p2, b.getSquareAtPosition(14));
//		b.initializePiece(q, b.getSquareAtPosition(2));
//		assertEquals(3, b.getPieces().size());
//		b.clearSquareAtPosition(14);
//		assertEquals(2, b.getPieces().size());
//	}
//	
//	@Test
//	void pawnCheck() {
//		Board b = new Board();
//		Pawn p = new Pawn(false);
//		King k = new King(true);
//		b.initializePiece(p, b.getSquareAtPosition(19));
//		b.initializePiece(k, b.getSquareAtPosition(10));
//		assertTrue(k.checkForChecks());
//	}
//	
//	@Test
//	void knightCheck() {
//		Board b = new Board();
//		Knight p = new Knight(false);
//		King k = new King(true);
//		b.initializePiece(p, b.getSquareAtPosition(27));
//		b.initializePiece(k, b.getSquareAtPosition(10));
//		assertTrue(k.checkForChecks());
//	}
//	
//	@Test
//	void bishopTest() {
//		Board b = new Board();
//		Bishop p = new Bishop(false);
//		King k = new King(true);
//		b.initializePiece(p, b.getSquareAtPosition(55));
//		b.initializePiece(k, b.getSquareAtPosition(10));
//		assertTrue(k.checkForChecks());
//	}
//	
//	@Test
//	void rookTest() {
//		Board b = new Board();
//		Rook p = new Rook(false);
//		King k = new King(true);
//		b.initializePiece(p, b.getSquareAtPosition(50));
//		b.initializePiece(k, b.getSquareAtPosition(10));
//		assertTrue(k.checkForChecks());
//	}
//	
//	@Test
//	void queenTest() {
//		Board b = new Board();
//		Queen p = new Queen(false);
//		King k = new King(true);
//		b.initializePiece(p, b.getSquareAtPosition(17));
//		b.initializePiece(k, b.getSquareAtPosition(10));
//		assertTrue(k.checkForChecks());
//	}
//	
//	@Test
//	void noCheckSameColor() {
//		Board b = new Board();
//		Pawn p = new Pawn(true);
//		King k = new King(true);
//		b.initializePiece(p, b.getSquareAtPosition(19));
//		b.initializePiece(k, b.getSquareAtPosition(10));
//		assertFalse(k.checkForChecks());
//	}
	
//	@Test
//	void kingMovesWithCheck() {
//		Board b = new Board();
//		King k = new King(false);
//		Knight n = new Knight(true);
//		b.initializePiece(n, b.getSquareAtPosition(39));
//		b.initializePiece(k, b.getSquareAtPosition(44));
//		for (Square s: k.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
	
//	@Test
//	void checkmate() {
//		Board b = new Board();
//		King k = new King(true);
//		Rook r = new Rook(true);
//		Pawn p1 = new Pawn(true);
//		Pawn p2 = new Pawn(true);
//		Knight n = new Knight(false);
//		b.initializePiece(k, b.getSquareAtPosition(7));
//		b.initializePiece(r, b.getSquareAtPosition(6));
//		b.initializePiece(p1, b.getSquareAtPosition(15));
//		b.initializePiece(p2, b.getSquareAtPosition(14));
//		b.initializePiece(n, b.getSquareAtPosition(13));
//		assertTrue(k.checkForCheckmate());
//	}
	
//	@Test
//	void bishopPins() {
//		Board b = new Board();
//		King k = new King(true);
//		Bishop bp = new Bishop(true);
//		Queen q = new Queen(false);
//		b.initializePiece(k, b.getSquareAtPosition(7));
//		b.initializePiece(bp, b.getSquareAtPosition(21));
//		b.initializePiece(q, b.getSquareAtPosition(56));
//		for (Square s: bp.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//		assertTrue(true);
//	}
	
//	@Test
//	void knightPins() {
//		Board b = new Board();
//		King k = new King(false);
//		Knight n = new Knight(false);
//		Bishop bp = new Bishop(true);
//		b.initializePiece(k, b.getSquareAtPosition(60));
//		b.initializePiece(n, b.getSquareAtPosition(51));
//		b.initializePiece(bp, b.getSquareAtPosition(33));
//		for (Square s: n.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//	}
//	
//	@Test
//	void pawnPins() {
//		Board b = new Board();
//		King k = new King(true);
//		Pawn p = new Pawn(true);
//		Bishop r = new Bishop(false);
//		Pawn p2 = new Pawn(false);
//		p2.setJustMovedOutTwo(true);
//		b.initializePiece(k, b.getSquareAtPosition(6));
//		b.initializePiece(p, b.getSquareAtPosition(13));
//		b.initializePiece(r, b.getSquareAtPosition(34));
//		b.initializePiece(p2, b.getSquareAtPosition(12));
//		for (Square s: p.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//	}
	
//	@Test
//	void rookPins() {
//		Board b = new Board();
//		King k = new King(false);
//		Rook r = new Rook(false);
//		Bishop bp = new Bishop(true);
//		b.initializePiece(k, b.getSquareAtPosition(63));
//		b.initializePiece(r, b.getSquareAtPosition(45));
//		b.initializePiece(bp, b.getSquareAtPosition(0));
//		for (Square s: r.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//	}
	
//	@Test
//	void movesWithCheck() {
//		Board b = new Board();
//		King k = new King(false);
//		King k2 = new King(true);
//		Bishop bp = new Bishop(true);
//		Rook r = new Rook(true);
//		Queen q = new Queen(false);
//		b.initializePiece(k, b.getSquareAtPosition(49));
//		b.initializePiece(r, b.getSquareAtPosition(17));
//		assertTrue(k.getIsInCheck());
//		b.initializePiece(bp, b.getSquareAtPosition(27));
//		b.initializePiece(k2, b.getSquareAtPosition(6));
//		b.initializePiece(q, b.getSquareAtPosition(0));
//		for (Square s: bp.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//	}
	
//	@Test
//	void manyMovesWithoutCheck() {
//		Board b = new Board();
//		King k1 = new King(true);
//		King k2 = new King(false);
//		Pawn p21 = new Pawn(false);
//		Pawn p22 = new Pawn(false);
//		Queen q2 = new Queen(false);
//		b.initializePiece(k1, b.getSquareAtPosition(4));
//		b.initializePiece(k2, b.getSquareAtPosition(63));
//		b.initializePiece(q2, b.getSquareAtPosition(36));
//		b.initializePiece(p21, b.getSquareAtPosition(54));
//		b.initializePiece(p22, b.getSquareAtPosition(55));
//		assertTrue(k1.checkForChecks());
//		Rook kn = new Rook(true);
//		b.initializePiece(kn, b.getSquareAtPosition(60));
//		for (Square s: kn.getValidMoves()) {
//			System.out.println(s.getName());
//		}
//	}
//	
//	@Test
//	void players() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		assertEquals(p1, b.getWhitePlayer());
//		assertEquals(p2, b.getBlackPlayer());
//	}
	
//	@Test
//	void whiteMoves() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		assertEquals(p1, p1.getPieces().get(0).getPlayer());
//		System.out.println("PLAYER ONE MOVES");
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//		System.out.println("-----------------");
//		System.out.println("PLAYER TWO MOVES");
//		for (Move m: p2.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void pieceMove() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		assertFalse(b.getSquareAtPosition(28).getIsOccupied());
//		p1.move("Nf3");
////		assertNull(b.getSquareAtPosition(12).getOccupant());
////		assertFalse(b.getSquareAtPosition(12).getIsOccupied());
//		assertTrue(b.getSquareAtPosition(21).getIsOccupied());
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void squareByName() {
//		Board b = new Board();
//		assertEquals(b.getSquareAtPosition(0), b.getSquareByName("a1"));
//		assertEquals(b.getSquareByName("g3"), b.getSquareAtPosition(22));
//		assertFalse(b.getSquareByName("d4").equals(b.getSquareAtPosition(47)));
//		assertNull(b.getSquareByName("h9"));
//		assertNull(b.getSquareByName("E4"));
//	}
//	
//	@Test
//	void random() {
//		String s = "the";
//		assertEquals(s.substring(1, 3), "he");
//	}
//	
//	@Test
//	void castleNoOverflow() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		King k = (King) p1.getKing();
//		assertFalse(k.getCanCastleKingside());
//	}
	
//	@Test
//	void firstCheckmate() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("e5");
//		p1.move("Bc4");
//		p2.move("Nc6");
//		p1.move("Qh5");
//		p2.move("a6");
//		p1.move("Qf7");
//		assertTrue(p2.getIsInCheckmate());
////		p1.move("g4");
////		p2.move("e5");
////		p1.move("f3");
////		p2.move("Qh4");
////		assertTrue(p1.getIsInCheckmate());
//	}
	
//	@Test
//	void defended() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		for (Piece p: p1.getPieces()) {
//			if (p.getType().equals(Piece.Type.PAWN)) {
//				if (p.getPosition().equals(b.getSquareAtPosition(11))) {
//					assertEquals(3, p.getNumberOfDefenders());
//				}
//			}
//				
//		}
//	}
	
//	@Test
//	void probablyWontWork() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("g4");
//		p2.move("h5");
//		p1.move("h5");
//		p2.move("Rh6");
//		p1.move("a3");
//		p2.move("Rg6");
//		p1.move("h6");
//		p2.move("Nf6");
//		p1.move("h7");
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void ruyLopez() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("e5");
//		p1.move("Nf3");
//		p2.move("Nc6");
//		p1.move("Bb5");
//		p2.move("d6");
//		for (Move m: p2.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void canCastle() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("e5");
//		p1.move("Nf3");
//		p2.move("Nf6");
//		p1.move("Bb5");
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void moveLog() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("e5");
//		p1.move("Nf3");
//		p2.move("Nc6");
//		p1.move("Bb5");
//		p2.move("d6");
//		System.out.println(g.getMoveLog().movesToString());
//	}
	
//	@Test
//	void movesByGame() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		List<String> s = new ArrayList<String>();
//		s.add("e4"); s.add("e5"); s.add("Nf3"); s.add("Nc6"); s.add("Bb5"); s.add("d6"); s.add("O-O");
//		g.move(s);
//		System.out.println(g.getMoveLog().movesToString());
//	}
	
	
	
//	@Test
//	void pinButCheck() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		List<String> s = new ArrayList<String>();
//		s.add("e4"); s.add("e6"); s.add("d4"); s.add("f5"); s.add("f5"); s.add("f5");
//		s.add("Nf3"); s.add("Nf6"); s.add("d5"); s.add("Bd6"); s.add("Bd3"); s.add("g6");
//		s.add("Bh6"); s.add("b6"); s.add("O-O"); s.add("Qe7"); s.add("Re1"); s.add("Ne4"); 
//		s.add("Nc3"); s.add("Ba6"); s.add("Be4"); s.add("e4"); s.add("Ne4"); s.add("c6"); s.add("Nd6");
//		g.move(s);
//		for(Move m: g.getBlackPlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void capture() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		g.move("e4");
//		g.move("d5");
//		g.move("d5");
//		for (Move m: g.getBlackPlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
//	
//	@Test
//	void lightBishop() {
//		Board b = new Board();
//		Bishop p = new Bishop(true);
//		b.initializePiece(p, b.getSquareAtPosition(63));
//		assertFalse(p.getIsLightSquared());
//	}
	
//	@Test
//	void butIsItMate() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("f6");
//		p1.move("a3");
//		p2.move("g5");
//		p1.move("b3");
//		p2.move("d6");
//		p1.move("c3");
//		p2.move("Qd7");
//		p1.move("Qh5");
//		assertFalse(p2.getIsInCheckmate());
//		for (Move m: p2.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void cloneTest() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		Board b2 = b.clone();
//		for (int i = 0; i < b.getSquares().length; i++) {
//			assertTrue(b.getSquares()[i].getIsOccupied() == b2.getSquares()[i].getIsOccupied());
//			if (b.getSquares()[i].getIsOccupied()) {
//				assertEquals(b.getSquares()[i].getOccupant().getIsWhite(), b2.getSquares()[i].getOccupant().getIsWhite());
//				assertEquals(b.getSquares()[i].getOccupant().getType(), b2.getSquares()[i].getOccupant().getType());
//			}
//		}
//	}
	
//	@Test
//	void doubleCheck() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		List<String> s = new ArrayList<String>();
//		s.add("e4"); s.add("d5"); s.add("d5"); s.add("Qd5"); s.add("h3"); s.add("Nf6"); s.add("h4"); 
//		s.add("Ng4"); s.add("h5"); s.add("Ne5"); s.add("h6"); s.add("Qe6"); s.add("Rh2"); s.add("Nd3");
//		g.move(s);
//		King k = (King) p1.getKing();
//		assertTrue(k.getIsInDoubleCheck());
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void pawnNameCapture() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("a6");
//		p1.move("e5");
//		p2.move("h6");
//		p1.move("Nc3");
//		p2.move("d5");
//		p1.move("Ne4");
//		p2.move("h5");
//		p1.move("Ne2");
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void blackEnPassentName() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("h3");
//		p2.move("e5");
//		p1.move("h4");
//		p2.move("e4");
//		p1.move("d4");
//		for (Move m: p2.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
//	
//	@Test
//	void checkName() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("e5");
//		p1.move("Bc4");
//		for (Move m: p1.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
//	
//	@Test
//	void whatIsHappening() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		List<String> s = new ArrayList<String>();
//		s.add("e3");
//		g.move(s);
//		for (Move m: g.getMoveLog().getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
//	
//	@Test
//	void supposedMate() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		List<String> s = new ArrayList<String>();
//		s.add("e3"); s.add("h5"); s.add("Ne2"); s.add("f6"); s.add("f4"); s.add("c6"); s.add("Ng3"); s.add("g6");
//		s.add("Ba6"); s.add("d5"); s.add("Ne4"); s.add("Bg4"); s.add("Bd3"); s.add("Bd7"); s.add("Nd6");
//		g.move(s);
//		for (Move m: g.getBlackPlayer().getMoves()) {
//			System.out.println(m.getName());
//		}
//		
//	}
//	
//	@Test
//	void startingRank() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		assertEquals(4, p1.getKing().getStartingFile());
//	}
	
	
//	@Test
//	void lineUp() {
//		Board b = new Board();
//		for (Square s: b.getLineBetweenSquares(b.getSquareAtPosition(24), b.getSquareAtPosition(27))) {
//			System.out.println(s.getName());
//		}
//	}
	
//	@Test
//	void makeSure() {
//		Board b = new Board();
//		Player p1 = new PlayerImpl(b, true);
//		Player p2 = new PlayerImpl(b, false);
//		b.incorportatePlayers(p1, p2);
//		GameModel g = new GameModel(b);
//		p1.move("e4");
//		p2.move("d5");
//		p1.move("Bb5");
//		for (Move m: p2.getMoves()) {
//			System.out.println(m.getName());
//		}
//	}
	
//	@Test
//	void pointdif() {
//		GameModel g = new GameModel();
////		System.out.println((new Evaluator(g.getBoard()).getBaseMaterialDifference()));
//		System.out.println((new Evaluator(g.getBoard(), true).getCenterControlDifference()));
//		g.move("e4");
//		System.out.println((new Evaluator(g.getBoard(), true).getCenterControlDifference()));
//		g.move("d5");
//		System.out.println((new Evaluator(g.getBoard(), true).getCenterControlDifference()));
//		g.move("a3");
//		System.out.println((new Evaluator(g.getBoard(), true).getCenterControlDifference()));
//		g.move("dxe4");
//		System.out.println((new Evaluator(g.getBoard(), true).getCenterControlDifference()));
////		System.out.println((new Evaluator(g.getBoard()).getBaseMaterialDifference()));
//	}
}
//
//
