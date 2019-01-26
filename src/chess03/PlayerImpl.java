package chess03;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerImpl implements Player {

	private List<Piece> pieces;
	private boolean white;
	private boolean turn;
	private Board b;
	private boolean inCheck;
	private boolean inDoubleCheck;
	
	public PlayerImpl(Board b, boolean white) {
		this.white = white;
		if (white) {
			this.turn = true;
		} else {
			this.turn = false;
		}
		this.b = b;
		this.pieces = new ArrayList<Piece>();
		this.inCheck = false;
		this.inDoubleCheck = false;
		setUpPieces();
	}
	
	public PlayerImpl(Board b, boolean white, boolean notCustom) {
		this.white = white;
		this.b = b;
		this.pieces = new ArrayList<Piece>();
		this.inCheck = false;
		this.inDoubleCheck = false;
//		if (notCustom) {
//			setUpPieces();
//		}
	}
	
	public List<Piece> getPieces() {
		return this.pieces;
	}
	
	public Board getBoard() {
		return this.b;
	}
	
	public boolean getIsWhite() {
		return this.white;
	}
	
	public boolean getTurn() {
		return this.turn;
	}
	
	public boolean getIsInCheck() {
//		return inCheck;
		return ((King)this.getKing()).getIsInCheck();
	}
	
	public boolean getIsInDoubleCheck() {
//		return inDoubleCheck;
		return ((King)this.getKing()).getIsInDoubleCheck();
	}
	
	public void setIsInCheck(boolean b) {
		this.inCheck = b;
	}
	
	public void setIsInDoubleCheck(boolean b) {
		this.inDoubleCheck = b;
	}
	
	public void setTurn(boolean b) {
		this.turn = b;
	}
	
	public Piece getKing() {
		for (Piece p: this.getPieces()) {
			if (p.getType().equals(Piece.Type.KING)) {
				return p;
			}
		}
		return null;
	}
	
	public boolean getIsInCheckmate() {
		if (this.getKing() == null) {System.out.println("No king");}
		if (this.getMoves() == null) {System.out.println("No moves");}
//		if (((King) this.getKing()).getIsInCheck()) { System.out.println("Check");}
//		if (this.getMoves().size() == 0) {System.out.println("No moves");}
		return (((King) this.getKing()).getIsInCheck()) && (this.getMoves().size() == 0);
	}
	
	public boolean getIsInStalemate() {
		int count = 0;
		for (Piece p: this.getPieces()) {
			count += p.getNumberOfMoves();
		}
		if (count == 0) {
			King k = (King) this.getKing();
			if (!k.getIsInCheck()) {
				return true;
			}
		}
		return false;
	}
	
	//used to determine kingside castling rights
	public Piece getKingsideRook() {
		for (Piece p: this.getPieces()) {
			if (p.getType().equals(Piece.Type.ROOK)) {
				if (this.getIsWhite()) {
					if (b.findSquareIndex(p.getPosition()) == 7) {
						return p;
					}
				} else {
					if (b.findSquareIndex(p.getPosition()) == 63) {
						return p;
					}
				}
				
			}
		}
		return null;
	}
	
	//used to determine queenside castling rights
	public Piece getQueensideRook() {
		for (Piece p: this.getPieces()) {
			if (p.getType().equals(Piece.Type.ROOK)) {
				if (this.getIsWhite()) {
					if (b.findSquareIndex(p.getPosition()) == 0) {
						return p;
					}
				} else {
					if (b.findSquareIndex(p.getPosition()) == 56) {
						return p;
					}
				}
				
			}
		}
		return null;
	}
	
	public void move(Piece p, Square s) {
//		if (!p.getType().equals(Piece.Type.KING) || p.getPosition().borders(s)) {
//			this.getBoard().getGameModel().update(p, s);
//		} 
		this.getBoard().movePiece(p, s);
	}
	
	public void move(Move m) {
		this.move(m.getPiece(), m.getSquare());
	}
	
	//so that you can castle without it being two moves
	public void move(Piece p, Square s, boolean castle) {
//		this.getBoard().getGameModel().update(p, s, castle);
		this.getBoard().movePiece(p, s);
	}
	
	//for promotions
	public void move(Piece p, Square s, Piece.Type t) {
//		this.getBoard().getGameModel().update(p, s, t);
		p.getPosition().removeOccupant();
		if (p.getType().equals(Piece.Type.PAWN)) {
			if ((p.getIsWhite() && s.getRank() == 7) || (!p.getIsWhite() && s.getRank() == 0)) {
				if (s.getIsOccupied()) {
					getOpponent().getPieces().remove(p);
					s.removeOccupant();
				}
				this.getPieces().remove(p);
				Piece p2;
				if (t.equals(Piece.Type.QUEEN)) {
					p2 = new Queen(this.getIsWhite());
				} else if (t.equals(Piece.Type.ROOK)) {
					p2 = new Rook(this.getIsWhite());
				} else if (t.equals(Piece.Type.BISHOP)) {
					p2 = new Bishop(this.getIsWhite());
				} else {
					p2 = new Knight(this.getIsWhite());
				}
				this.getBoard().initializePiece(p2, s);
				this.pieces.add(p2);
				p2.setPlayer(this);
			}
		}
	}
	
	public void move(String str) {
		if (str.length() < 2 || str.length() > 7) {throw new IllegalArgumentException();}
		Square s;
		Piece p;
		if (str.equals("O-O")) {
			King k = (King) this.getKing();
			if (k.getCanCastleKingside()) {
				if (this.getIsWhite()) {
					move(k, b.getSquareAtPosition(6));
					move(this.getKingsideRook(), b.getSquareAtPosition(5), true);
				} else {
					move(k, b.getSquareAtPosition(62));
					move(this.getKingsideRook(), b.getSquareAtPosition(61), true);
				}
			}
		}
		if (str.equals("O-O-O")) {
			King k = (King) this.getKing();
			if (k.getCanCastleQueenside()) {
				if (this.getIsWhite()) {
					move(k, b.getSquareAtPosition(2));
					move(this.getKingsideRook(), b.getSquareAtPosition(3), true);
				} else {
					move(k, b.getSquareAtPosition(58));
					move(this.getKingsideRook(), b.getSquareAtPosition(59), true);
				}
			}
		}
		if (str.substring(str.length() -2, str.length() - 1).equals("=")) {
			Piece.Type t;
			Square sEnd;
			String strEnd = str.substring(str.length() -1, str.length());
			if (strEnd.equals("Q")) {
				t = Piece.Type.QUEEN;
			} else if (strEnd.equals("R")) {
				t = Piece.Type.ROOK;
			} else if (strEnd.equals("B")) {
				t = Piece.Type.BISHOP;
			} else {
				t = Piece.Type.KNIGHT;
			}
			if (!str.substring(1, 2).equals("x")) {
				sEnd = this.getBoard().getSquareByName(str.substring(0,2));
			} else {
				sEnd = this.getBoard().getSquareByName(str.substring(2,4));
			}
			outerLoop:
			for (Piece pce: this.getPieces()) {
				if (pce.getType().equals(Piece.Type.PAWN)) {
					for (Square stemp: pce.getValidMoves()) {
						if (stemp.equals(sEnd)) {
							this.move(pce, sEnd, t);
							break outerLoop;
						}
					}
				}
			}
		}
		if (str.length() == 2) {
			s = this.getBoard().getSquareByName(str);
			for (Piece pt: this.getPieces()) {
				if (pt.getType().equals(Piece.Type.PAWN)) {
					for (Square st: pt.getValidMoves()) {
						if (st.equals(s)) {
							p = pt;
							move(pt, st);
							break;
						} 
					}
				}
			}
		} else if (str.length() == 3){
			Square st = this.getBoard().getSquareByName(str.substring(1));
			for (Piece pt: this.getPieces()) {
				if (pt.getInitial().equals(Character.toString(str.charAt(0)).toUpperCase())) {
					for (Square st2: pt.getValidMoves()) {
						if (st2.equals(st)) {
							move(pt, st);
							break;
						}
					}
				}
			}
		} else if (str.length() == 4) {
			if (str.substring(3).equals("+") || str.substring(3).equals("#")) {
				this.move(str.substring(0, 3));
			} else {
				if (str.substring(1, 2).equals("x")) {
					String sfirst = str.substring(0, 1);
					if (str.startsWith("N") || str.startsWith("B") || str.startsWith("R") || str.startsWith("Q") || str.startsWith("K")) {
						for (Piece pce: this.getPieces()) {
							if (pce.getInitial().equals(sfirst)) {
								this.move(pce, this.getBoard().getSquareByName(str.substring(2, 4)));
							}
						}
					} else {
						for (Piece pce: this.getPieces()) {
							if (pce.getType().equals(Piece.Type.PAWN)) {
								if (pce.getPosition().getFileName().equals(sfirst)) {
									this.move(pce,this.getBoard().getSquareByName(str.substring(2, 4)));
								}
							}
						}
					}
					String s2 = str.substring(0, 1) + str.substring(2);
					this.move(s2);
				} else {
					Square st = this.getBoard().getSquareByName(str.substring(2));
					for (Piece pt: this.getPieces()) {
						if (pt.getInitial().equals(Character.toString(str.charAt(0)).toUpperCase())) {
							for (Square st2: pt.getValidMoves()) {
								if (st2.equals(st)) {
									
								}
							}
						}
					}
				}
			}
		}
	}
	
	public List<Move> getMoves() {
		List<Move> moves = new ArrayList<Move>();
		for (Piece p: this.getPieces()) {
			if (p.getPosition() == null) {throw new IllegalArgumentException("Word");}
			for (Square s: p.getValidMoves()) {
				if (p.getType().equals(Piece.Type.PAWN)) {
					if (p.getIsWhite() && s.getRank() == 7) {
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.QUEEN));
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.BISHOP));
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.KNIGHT));
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.ROOK));
					} else if (!p.getIsWhite() && s.getRank() == 0) {
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.QUEEN));
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.BISHOP));
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.KNIGHT));
						moves.add(new PromotionMove(p, s, p.getPosition(), s.getIsOccupied(), Piece.Type.ROOK));
					} else {
						moves.add(new Move(p, s, s.getIsOccupied(), p.getPosition()));
					}
				} else if (p.getType().equals(Piece.Type.KING)) {
					if (Math.abs(p.getPosition().getFile() - s.getFile()) == 2) {
						moves.add(new CastleMove(p, s, p.getPosition(), s.getIsOccupied(), true));
					} else if (Math.abs(p.getPosition().getFile() - s.getFile()) == 3) {
						moves.add(new CastleMove(p, s, p.getPosition(), s.getIsOccupied(), false));
					} else {
						moves.add(new Move(p, s, s.getIsOccupied(), p.getPosition()));
					}
				} else {
					moves.add(new Move(p, s, s.getIsOccupied(), p.getPosition()));
				}
			}
		}
		return moves;
	}
	
	public void setUpPieces() {
		Pawn p1 = new Pawn(white);
		Pawn p2 = new Pawn(white);
		Pawn p3 = new Pawn(white);
		Pawn p4 = new Pawn(white);
		Pawn p5 = new Pawn(white);
		Pawn p6 = new Pawn(white);
		Pawn p7 = new Pawn(white);
		Pawn p8 = new Pawn(white);
		
		Rook r1 = new Rook(white);
		Knight n1 = new Knight(white);
		Bishop b1 = new Bishop(white);
		King k = new King(white);
		Queen q = new Queen(white);
		Bishop b2 = new Bishop(white);
		Knight n2 = new Knight(white);
		Rook r2 = new Rook(white);
		
		if (white) {
			b.initializePiece(r1, b.getSquareAtPosition(0));
			b.initializePiece(n1, b.getSquareAtPosition(1));
			b.initializePiece(b1, b.getSquareAtPosition(2));
			b.initializePiece(q, b.getSquareAtPosition(3));
			b.initializePiece(k, b.getSquareAtPosition(4));
			b.initializePiece(b2, b.getSquareAtPosition(5));
			b.initializePiece(n2, b.getSquareAtPosition(6));
			b.initializePiece(r2, b.getSquareAtPosition(7));
			
			b.initializePiece(p1, b.getSquareAtPosition(8));
			b.initializePiece(p2, b.getSquareAtPosition(9));
			b.initializePiece(p3, b.getSquareAtPosition(10));
			b.initializePiece(p4, b.getSquareAtPosition(11));
			b.initializePiece(p5, b.getSquareAtPosition(12));
			b.initializePiece(p6, b.getSquareAtPosition(13));
			b.initializePiece(p7, b.getSquareAtPosition(14));
			b.initializePiece(p8, b.getSquareAtPosition(15));
		} else {
			b.initializePiece(r1, b.getSquareAtPosition(56));
			b.initializePiece(n1, b.getSquareAtPosition(57));
			b.initializePiece(b1, b.getSquareAtPosition(58));
			b.initializePiece(q, b.getSquareAtPosition(59));
			b.initializePiece(k, b.getSquareAtPosition(60));
			b.initializePiece(b2, b.getSquareAtPosition(61));
			b.initializePiece(n2, b.getSquareAtPosition(62));
			b.initializePiece(r2, b.getSquareAtPosition(63));
			
			b.initializePiece(p1, b.getSquareAtPosition(48));
			b.initializePiece(p2, b.getSquareAtPosition(49));
			b.initializePiece(p3, b.getSquareAtPosition(50));
			b.initializePiece(p4, b.getSquareAtPosition(51));
			b.initializePiece(p5, b.getSquareAtPosition(52));
			b.initializePiece(p6, b.getSquareAtPosition(53));
			b.initializePiece(p7, b.getSquareAtPosition(54));
			b.initializePiece(p8, b.getSquareAtPosition(55));
		}
		
		this.pieces.add(p1);
		this.pieces.add(p2);
		this.pieces.add(p3);
		this.pieces.add(p4);
		this.pieces.add(p5);
		this.pieces.add(p6);
		this.pieces.add(p7);
		this.pieces.add(p8);
		this.pieces.add(r1);
		this.pieces.add(r2);
		this.pieces.add(n1);
		this.pieces.add(n2);
		this.pieces.add(b1);
		this.pieces.add(b2);
		this.pieces.add(k);
		this.pieces.add(q);
		
		for (Piece p: this.pieces) {
			p.setPlayer(this);
			p.setStartingFile(p.getPosition().getFile());
		}
	}
	
	public Player getOpponent() {
		if (this.getIsWhite()) {
			return this.getBoard().getBlackPlayer();
		} else {
			return this.getBoard().getWhitePlayer();
		}
	}
	
	public Move stringToMove(String str) {
		if (str.length() < 2 || str.length() > 6) {throw new IllegalArgumentException();}
		Square s;
		Piece p;
		if (str.equals("O-O")) {
			King k = (King) this.getKing();
			if (k.getCanCastleKingside()) {
				if (this.getIsWhite()) {
					move(k, b.getSquareAtPosition(6));
					return new CastleMove(k, b.getSquareAtPosition(5), k.getPosition(),false, true);
				} else {
					move(k, b.getSquareAtPosition(62));
					return new CastleMove(k, b.getSquareAtPosition(61), k.getPosition(), false, true);
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else if (str.equals("O-O-O")) {
			King k = (King) this.getKing();
			if (k.getCanCastleQueenside()) {
				if (this.getIsWhite()) {
					move(k, b.getSquareAtPosition(2));
					return new CastleMove(k, b.getSquareAtPosition(3), k.getPosition(),false,false);
				} else {
					move(k, b.getSquareAtPosition(58));
					return new CastleMove(k, b.getSquareAtPosition(59), k.getPosition(),false,false);
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else if (str.substring(str.length() -2, str.length() - 1).equals("=")) {
			Piece.Type t;
			Square sEnd;
			String strEnd = str.substring(str.length() -1, str.length());
			if (strEnd.equals("Q")) {
				t = Piece.Type.QUEEN;
			} else if (strEnd.equals("R")) {
				t = Piece.Type.ROOK;
			} else if (strEnd.equals("B")) {
				t = Piece.Type.BISHOP;
			} else {
				t = Piece.Type.KNIGHT;
			}
			if (!str.substring(1, 2).equals("x")) {
				sEnd = this.getBoard().getSquareByName(str.substring(0,2));
			} else {
				sEnd = this.getBoard().getSquareByName(str.substring(2,4));
			}
			for (Piece pce: this.getPieces()) {
				if (pce.getType().equals(Piece.Type.PAWN)) {
					for (Square stemp: pce.getValidMoves()) {
						if (stemp.equals(sEnd)) {
							return new PromotionMove(pce, sEnd, pce.getPosition(), false, t);
							
						} else {
							throw new IllegalArgumentException();
						}
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
		} else if (str.length() == 2) {
			s = this.getBoard().getSquareByName(str);
			for (Piece pt: this.getPieces()) {
				if (pt.getType().equals(Piece.Type.PAWN)) {
					for (Square st: pt.getValidMoves()) {
						if (st.equals(s)) {
							return new Move(pt, st, false, pt.getPosition());
						} else {
							throw new IllegalArgumentException();
						}
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
		} else if (str.length() == 3) {
			Square st = this.getBoard().getSquareByName(str.substring(1));
			for (Piece pt: this.getPieces()) {
				if (pt.getInitial().equals(Character.toString(str.charAt(0)).toUpperCase())) {
					for (Square st2: pt.getValidMoves()) {
						if (st2.equals(st)) {
							return new Move(pt, st, false, pt.getPosition());
						} else {
							throw new IllegalArgumentException();
						}
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
		} else if (str.length() == 4) {
			String ts = str.substring(0, 1);
			Square tsq;
			if (str.endsWith("+") || str.endsWith("#")) {
				tsq = this.getBoard().getSquareByName(str.substring(1, 3));
				if (ts.equals("K") || ts.equals("Q") || ts.equals("R") || ts.equals("B") || ts.equals("N")) {
					for (Piece pt: this.getPieces()) {
						if (pt.getInitial().equals(ts)) {
							for (Square st2: pt.getValidMoves()) {
								if (st2.equals(tsq)) {
									return new Move(pt, tsq, false, pt.getPosition());
								} else {
									throw new IllegalArgumentException();
								}
							}
						} else {
							throw new IllegalArgumentException();
						}
					}
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				tsq = this.getBoard().getSquareByName(str.substring(2,4));
				if (ts.equals("K") || ts.equals("Q") || ts.equals("R") || ts.equals("B") || ts.equals("N")) {
					if (str.substring(1, 2).equals("x")) {
						for (Piece pt: this.getPieces()) {
							if (pt.getInitial().equals(ts)) {
								for (Square st2: pt.getValidMoves()) {
									if (st2.equals(tsq)) {
										return new Move(pt, tsq, false, pt.getPosition());
									} 
								}
							}
						}
					} else {
						for (Piece pt: this.getPieces()) {
							if (pt.getInitial().equals(ts)) {
								if (pt.getPosition().getFileName().equals(str.substring(1, 2))) {
									return new Move(pt, tsq, false, pt.getPosition());
								} 
							}
						}
					}
				} else {
					for (Piece pt: this.getPieces()) {
						if (pt.getPosition().getFileName().equals(ts)) {
							return new Move(pt, tsq, false, pt.getPosition());
						} 
					}
				}
			}
			
 		} 
		
//		if (str.length() == 2) {
//			s = this.getBoard().getSquareByName(str);
//			for (Piece pt: this.getPieces()) {
//				if (pt.getType().equals(Piece.Type.PAWN)) {
//					for (Square st: pt.getValidMoves()) {
//						if (st.equals(s)) {
//							p = pt; 
//							return new Move(pt, st, false, pt.getPosition());
//						} 
//					}
//				}
//			}
//		} else if (str.length() == 3){
//			Square st = this.getBoard().getSquareByName(str.substring(1));
//			for (Piece pt: this.getPieces()) {
//				if (pt.getInitial().equals(Character.toString(str.charAt(0)).toUpperCase())) {
//					for (Square st2: pt.getValidMoves()) {
//						if (st2.equals(st)) {
//							return new Move(pt, st, false, pt.getPosition());
//						}
//					}
//				}
//			}
//		} else if (str.length() == 4) {
//			if (str.substring(3).equals("+") || str.substring(3).equals("#")) {
//				this.move(str.substring(0, 3)); 
//			} else {
//				if (str.substring(1, 2).equals("x")) {
//					String s2 = str.substring(0, 1) + str.substring(2);
//					this.move(s2);
//				} else {
//					Square st = this.getBoard().getSquareByName(str.substring(2));
//					for (Piece pt: this.getPieces()) {
//						if (pt.getInitial().equals(Character.toString(str.charAt(0)).toUpperCase())) {
//							for (Square st2: pt.getValidMoves()) {
//								if (st2.equals(st)) {
//									
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		return new Move(new Pawn(true), b.getSquareAtPosition(0), false, b.getSquareAtPosition(0));
	}
	
	
	public boolean hasForcedMate() {
		for (int i = 0; i < 10; i++) {
			
		}
		return false;
	}
	
	public void updateChecks() {
		boolean check = false;
		boolean doubleCheck = false;
		for (Piece p: this.getOpponent().getPieces()) {
			for (Square s: p.getValidMoves()) {
				if (s.getName().equals(this.getKing().getPosition().getName())) {
					if (check) {
						doubleCheck = true;
					} else {
						check = true;
					}
				}
			}
		}
		
		if (check) {
			this.setIsInCheck(true);
		} else {
			this.setIsInCheck(false);
		}
		if (doubleCheck) {
			this.setIsInDoubleCheck(true);
		} else {
			this.setIsInDoubleCheck(false);
		}
	}
	
	public int getNumOfAttackersOnSquare(Square s) {
		int n = 0;
		for (Move m: getMoves()) {
			if (m.getSquare().getName().equals(s.getName())) {
				n++;
			}
		}
		return n;
	}
	
	public void checkForStalemate() {
		if (getMoves().size() == 0 && !((King)getKing()).getIsInCheck()) {
			getBoard().getGameModel().notifyListeners(new OutcomeEvent(Game.Outcomes.DRAW));
		}
	}
}
