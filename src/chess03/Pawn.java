package chess03;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends PieceImpl {

	private boolean isPinned;
	private boolean hasMoved;
	private boolean justMovedOutTwo;
	
	public Pawn(boolean isWhite) {
		super(Piece.Type.PAWN, isWhite, 1.0);
		this.isPinned = false;
		this.hasMoved = false;
		this.justMovedOutTwo = false;
		this.setWasPawn();
	}
	
	public boolean getIsPinned() {
		return this.isPinned;
	}
	
	public boolean getHasMoved() {
		return this.hasMoved;
	}
	
	public boolean getJustMovedOutTwo() {
		return this.justMovedOutTwo;
	}
	
	public String getInitial() {
		return "";
	}
	
	public void setHasMoved() {
		this.hasMoved = true;
	}
	
	public void setJustMovedOutTwo(boolean b) {
		this.justMovedOutTwo = b;
	}

	public void setIsPinned() {
		this.isPinned = true;
	}
	
	public void removePin() {
		this.isPinned = false;
	}
	
	@Override
	public List<Square> getValidMoves() {
		checkForPins();
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		int n;
		if (this.getIsWhite()) {
			n = 1;
		} else {
			n = -1;
		}
//		King k = null;
//		for (Piece p: this.getBoard().getPieces()) {
//			if (p.getType().equals(Piece.Type.KING) && p.getIsWhite() == this.getIsWhite()) {
//				k = (King) p;
//			}
//		}
		King k = (King) this.getPlayer().getKing();
		k.getIsInDoubleCheck();
		k.checkForChecks();
//		if (k!= null) {
//		if (!k.getIsInDoubleCheck()) {
		if (!this.getPlayer().getIsInDoubleCheck()) {
//			if (!k.checkForChecks()) {
			if (!this.getPlayer().getIsInCheck()) {
					if (!this.isPinned) {
						int index = this.getBoard().findSquareIndex(this.getPosition());
						//move out one
						if (index + (8 * n) > -1 && index + (8 * n) < 64) {
							if (!this.getBoard().getSquareAtPosition(index + (8 * n)).getIsOccupied()) {
								validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * n)));
								//move out two
								if (!this.getHasMoved()) {
									if (!this.getBoard().getSquareAtPosition(index + (8 * (2 * n))).getIsOccupied()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * (2 * n))));
									}
								}
							} 
						}
						//captures
						if (index + (8 * n) > -1 && index + (8 * n) < 64) {
							if (this.getPosition().getFile() < 7) {
								if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
									if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
									}
								}
							}
							if (this.getPosition().getFile() > 0) {
								if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
									if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
									}
								}
							}
							//en passent
							if (this.getPosition().getFile() < 7) {
								if (this.getBoard().getSquareAtPosition(index + 1).getIsOccupied()) {
									if (this.getBoard().getSquareAtPosition(index + 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
											this.getBoard().getSquareAtPosition(index + 1).getOccupant().getIsWhite() != this.getIsWhite()) {
										Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index + 1).getOccupant();
										if (p.getJustMovedOutTwo()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
										}
									}
								}
							}
							if (this.getPosition().getFile() > 0) {
								if (this.getBoard().getSquareAtPosition(index - 1).getIsOccupied()) {
									if (this.getBoard().getSquareAtPosition(index - 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
											this.getBoard().getSquareAtPosition(index - 1).getOccupant().getIsWhite() != this.getIsWhite()) {
										Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index - 1).getOccupant();
										if (p.getJustMovedOutTwo()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
										}
									}
								}
							}
						}
						
					} else {
						int index = this.getBoard().findSquareIndex(this.getPosition());
						if (this.getIsWhite()) {
							if (getPinDirection().equals(Piece.Direction.S) || getPinDirection().equals(Piece.Direction.N)) {
								//move out one
								if (!this.getBoard().getSquareAtPosition(index + (8 * n)).getIsOccupied()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * n)));
									//move out two
									if (!this.getHasMoved()) {
										if (!this.getBoard().getSquareAtPosition(index + (8 * (2 * n))).getIsOccupied()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * (2 * n))));
										}
									}
								}
							} else if (getPinDirection().equals(Piece.Direction.SE) || getPinDirection().equals(Piece.Direction.NW)) {
								if (this.getPosition().getFile() > 0) {
									if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
										}
									}
									if (this.getBoard().getSquareAtPosition(index - 1).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index - 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
												this.getBoard().getSquareAtPosition(index - 1).getOccupant().getIsWhite() != this.getIsWhite()) {
											Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index - 1).getOccupant();
											if (p.getJustMovedOutTwo()) {
												validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
											}
										}
									}
								}
							} else if (getPinDirection().equals(Piece.Direction.SW) || getPinDirection().equals(Piece.Direction.NE)) {
								if (this.getPosition().getFile() < 7) {
									if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
										}
									}
									if (this.getBoard().getSquareAtPosition(index + 1).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index + 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
												this.getBoard().getSquareAtPosition(index + 1).getOccupant().getIsWhite() != this.getIsWhite()) {
											Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index + 1).getOccupant();
											if (p.getJustMovedOutTwo()) {
												validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
											}
										}
									}
								}
							}
						} else {
							if (getPinDirection().equals(Piece.Direction.N) || getPinDirection().equals(Piece.Direction.S)) {
								//move out one
								if (!this.getBoard().getSquareAtPosition(index + (8 * n)).getIsOccupied()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * n)));
									//move out two
									if (!this.getHasMoved()) {
										if (!this.getBoard().getSquareAtPosition(index + (8 * (2 * n))).getIsOccupied()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * (2 * n))));
										}
									}
								}
							} else if (getPinDirection().equals(Piece.Direction.NE) || getPinDirection().equals(Piece.Direction.SW)) {
								if (this.getPosition().getFile() < 7) {
									if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
										}
									}
									if (this.getBoard().getSquareAtPosition(index + 1).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index + 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
												this.getBoard().getSquareAtPosition(index + 1).getOccupant().getIsWhite() != this.getIsWhite()) {
											Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index + 1).getOccupant();
											if (p.getJustMovedOutTwo()) {
												validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
											}
										}
									}
								}
							} else if (getPinDirection().equals(Piece.Direction.NW) || getPinDirection().equals(Piece.Direction.SE)) {
								if (this.getPosition().getFile() > 0) {
									if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
											validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
										}
									}
									if (this.getBoard().getSquareAtPosition(index - 1).getIsOccupied()) {
										if (this.getBoard().getSquareAtPosition(index - 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
												this.getBoard().getSquareAtPosition(index - 1).getOccupant().getIsWhite() != this.getIsWhite()) {
											Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index - 1).getOccupant();
											if (p.getJustMovedOutTwo()) {
												validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
											}
										}
									}
								}
							}
						}
					}
				} else {
					if (k.getChecker() == null) {
						if (!k.checkForChecks()) {
							return this.getValidMoves();
						} else {
							for (Piece pez: this.getPlayer().getOpponent().getPieces()) {
								for (Square sz: pez.getPotentialMoves()) {
									if (sz.equals(k.getPosition())) {
										k.setChecker(pez);
										return this.getValidMoves();
									}
								}
							}
						}
					} else if (((King) this.getPlayer().getKing()).getChecker().getType().equals(Piece.Type.KNIGHT)) {
						for (Square s: this.getMovesWithoutChecks()) {
							if (s.equals(((King) this.getPlayer().getKing()).getChecker().getPosition())) {
								validMoves.add(s);
							}
						}
					} else {
						//if king is in check
						((King) this.getPlayer().getKing()).checkForChecks();
//						Square scheck = null;
//						for (Piece pce: this.getPlayer().getOpponent().getPieces()) {
//							for (Square stem: pce.getPotentialMoves()) {
//								if (stem.equals(((King) this.getPlayer().getKing()).getPosition())) {
//									scheck = pce.getPosition();
//								}
//							}
//						}
//						if (scheck == null) {System.out.println("What is going on");}
//						else {
//							for (Square s1: this.getBoard().getLineBetweenSquares(this.getPlayer().getKing().getPosition(), scheck)) {
//								for (Square s2: this.getMovesWithoutChecks()) {
//									if (s1.equals(s2) ) {
//										validMoves.add(s2);
//									}
//								}
//							}
//						}
						for (Square s1: k.getLineToChecker()) {
							for (Square s2: this.getMovesWithoutChecks()) {
								if (s1.equals(s2)) {
									validMoves.add(s2);
								}
							}
						}
					}
				}
				
			} 
//		}
		
		return validMoves;
	}
	
	public List<Square> getDefensiveMoves() {
		int n;
		if (this.getIsWhite()) {
			n = 1;
		} else {
			n = -1;
		}
		int index = this.getBoard().findSquareIndex(this.getPosition());
		List<Square> squares = new ArrayList<Square>();
		if (!this.isPinned) {
			if (this.getPosition().getFile() < 7) {
				if (index + 1 + (8 * n) < 64 && index + 1 + (8 * n) > -1) {
					squares.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
//					if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
//						if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() == this.getIsWhite()) {
//							squares.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
//						}
//					}
				}
			}
			if (this.getPosition().getFile() > 0) {
				if (index - 1 + (8 * n) < 64 && index - 1 + (8 * n) > -1) {
//					if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
//						if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() == this.getIsWhite()) {
//							squares.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
//						}
//					}
					squares.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
				}
			}
		}
		return squares;
	}
	
	public List<Square> getMovesWithoutChecks() {
		checkForPins();
		List<Square> validMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		int n;
		if (this.getIsWhite()) {
			n = 1;
		} else {
			n = -1;
		}
		int index = this.getBoard().findSquareIndex(this.getPosition());
		if (index + (8 * n) > -1 && index + (8 * n) < 64) {
			if (!this.isPinned) {
				
				//move out one
				if (!this.getBoard().getSquareAtPosition(index + (8 * n)).getIsOccupied()) {
					validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * n)));
					//move out two
					if (!this.getHasMoved()) {
						if (!this.getBoard().getSquareAtPosition(index + (8 * (2 * n))).getIsOccupied()) {
							validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * (2 * n))));
						}
					}
				}
				//captures
				if (this.getPosition().getFile() < 7) {
					if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
						if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
							validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
						}
					}
				}
				if (this.getPosition().getFile() > 0) {
					if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
						if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
							validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
						}
					}
				}
				//en passent
				if (this.getPosition().getFile() < 7) {
					if (this.getBoard().getSquareAtPosition(index + 1).getIsOccupied()) {
						if (this.getBoard().getSquareAtPosition(index + 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
								this.getBoard().getSquareAtPosition(index + 1).getOccupant().getIsWhite() != this.getIsWhite()) {
							Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index + 1).getOccupant();
							if (p.getJustMovedOutTwo()) {
								validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
							}
						}
					}
				}
				if (this.getPosition().getFile() > 0) {
					if (this.getBoard().getSquareAtPosition(index - 1).getIsOccupied()) {
						if (this.getBoard().getSquareAtPosition(index - 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
								this.getBoard().getSquareAtPosition(index - 1).getOccupant().getIsWhite() != this.getIsWhite()) {
							Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index - 1).getOccupant();
							if (p.getJustMovedOutTwo()) {
								validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
							}
						}
					}
				}
			} else {
				if (this.getIsWhite()) {
					if (getPinDirection().equals(Piece.Direction.S) || getPinDirection().equals(Piece.Direction.N)) {
						//move out one
						if (!this.getBoard().getSquareAtPosition(index + (8 * n)).getIsOccupied()) {
							validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * n)));
							//move out two
							if (!this.getHasMoved()) {
								if (!this.getBoard().getSquareAtPosition(index + (8 * (2 * n))).getIsOccupied()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * (2 * n))));
								}
							}
						}
					} else if (getPinDirection().equals(Piece.Direction.SE) || getPinDirection().equals(Piece.Direction.NW)) {
						if (this.getPosition().getFile() > 0) {
							if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
								}
							}
							if (this.getBoard().getSquareAtPosition(index - 1).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index - 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
										this.getBoard().getSquareAtPosition(index - 1).getOccupant().getIsWhite() != this.getIsWhite()) {
									Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index - 1).getOccupant();
									if (p.getJustMovedOutTwo()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
									}
								}
							}
						}
					} else if (getPinDirection().equals(Piece.Direction.SW) || getPinDirection().equals(Piece.Direction.NE)) {
						if (this.getPosition().getFile() < 7) {
							if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
								}
							}
							if (this.getBoard().getSquareAtPosition(index + 1).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index + 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
										this.getBoard().getSquareAtPosition(index + 1).getOccupant().getIsWhite() != this.getIsWhite()) {
									Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index + 1).getOccupant();
									if (p.getJustMovedOutTwo()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
									}
								}
							}
						}
					}
				} else {
					if (getPinDirection().equals(Piece.Direction.N) || getPinDirection().equals(Piece.Direction.S)) {
						//move out one
						if (!this.getBoard().getSquareAtPosition(index + (8 * n)).getIsOccupied()) {
							validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * n)));
							//move out two
							if (!this.getHasMoved()) {
								if (!this.getBoard().getSquareAtPosition(index + (8 * (2 * n))).getIsOccupied()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index + (8 * (2 * n))));
								}
							}
						}
					} else if (getPinDirection().equals(Piece.Direction.NE) || getPinDirection().equals(Piece.Direction.SW)) {
						if (this.getPosition().getFile() < 7) {
							if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
								}
							}
							if (this.getBoard().getSquareAtPosition(index + 1).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index + 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
										this.getBoard().getSquareAtPosition(index + 1).getOccupant().getIsWhite() != this.getIsWhite()) {
									Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index + 1).getOccupant();
									if (p.getJustMovedOutTwo()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
									}
								}
							}
						}
					} else if (getPinDirection().equals(Piece.Direction.NW) || getPinDirection().equals(Piece.Direction.SE)) {
						if (this.getPosition().getFile() > 0) {
							if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
									validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
								}
							}
							if (this.getBoard().getSquareAtPosition(index - 1).getIsOccupied()) {
								if (this.getBoard().getSquareAtPosition(index - 1).getOccupant().getType().equals(Piece.Type.PAWN) &&
										this.getBoard().getSquareAtPosition(index - 1).getOccupant().getIsWhite() != this.getIsWhite()) {
									Pawn p = (Pawn)this.getBoard().getSquareAtPosition(index - 1).getOccupant();
									if (p.getJustMovedOutTwo()) {
										validMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
									}
								}
							}
						}
					}
				}
			}	
		}
		return validMoves;
	}
	
	public List<Square> getPotentialMoves() {
		List<Square> potentialMoves = new ArrayList<Square>();
		if (this.getPosition() == null) {throw new IllegalArgumentException("Piece has no starting square");}
		int n;
		if (this.getIsWhite()) {
			n = 1;
		} else {
			n = -1;
		}
		int index = this.getBoard().findSquareIndex(this.getPosition());
		if (index + 1 + (8 * n) < 64 && index + 1 + (8 * n) > -1) {
			if (this.getBoard().getSquareAtPosition(index + 1 + (8 * n)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(index + 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
					if (this.getPosition().borders(this.getBoard().getSquareAtPosition(index + 1 + (8* n)))) {
						potentialMoves.add(this.getBoard().getSquareAtPosition(index + 1 + (8 * n)));
					}
				}
			}
		}
		if (index - 1 + (8 * n) < 64 && index - 1 + (8 * n) > -1) {
			if (this.getBoard().getSquareAtPosition(index - 1 + (8 * n)).getIsOccupied()) {
				if (this.getBoard().getSquareAtPosition(index - 1 + (8* n)).getOccupant().getIsWhite() != this.getIsWhite()) {
					if (this.getPosition().borders(this.getBoard().getSquareAtPosition(index - 1 + (8* n)))) {
						potentialMoves.add(this.getBoard().getSquareAtPosition(index - 1 + (8 * n)));
					}
				}
			}
		}
		return potentialMoves;
	}
	
	public List<Square> getPotentialMovesNoKing() {
		return this.getPotentialMoves();
	}

	public boolean checkForPins() {
		if (checkForPotentialPins()) {
			Piece.Direction d = oppositeDirection(getPinDirection());
			if (directLineToPinner(d, Piece.Type.QUEEN)) {
				this.isPinned = true;
				this.setPinnedDirection(d);
				return true;
			}
			if (d.equals(Piece.Direction.N) || d.equals(Piece.Direction.E) ||
				d.equals(Piece.Direction.W) || d.equals(Piece.Direction.S)) {
				if (directLineToPinner(d, Piece.Type.ROOK)) {
					this.isPinned = true;
					this.setPinnedDirection(d);
					return true;
				}
			} else {
				if (directLineToPinner(d, Piece.Type.BISHOP)) {
					this.isPinned = true;
					this.setPinnedDirection(d);
					return true;
				}
			}
		}
		this.isPinned = false;
		this.setPinnedDirection(null);
		return false;
	}
	
}
