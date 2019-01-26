package chessController;

import chessView.*;
import chessView.MoveEvent;
import chessView.PieceView.Pieces;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import chess03.*;
import chess03.Board;

public class Controller implements ModelListener, ViewListener {
	
	private GameModel model;
	private BoardView view;

	public Controller(GameModel g, BoardView v) {
		model = g;
		view = v;
		model.addModelListener(this);
		view.addViewListener(this);
	}
	
//	public static void main(String[] args) {
//		GameModel g = new GameModel();
//		BoardView v = new BoardView();
//		Controller c = new Controller(g,v);
//	}

	
	//---------------------------------------VIEW_EVENTS-------------------------------------------------------------
	
	public void handleViewEvent(ViewEvent e) {
		// TODO Auto-generated method stub
		Square s = model.getBoard().getSquareByName(e.getInitialSquare().getName());
		if (e.isPromotion()) {
			//This should all be gone
			System.out.println("Clearing out " + s.getName());
			model.getBoard().getPieces().remove(s.getOccupant());
			if (e.getInitialSquare().getOccIsWhite()) {
				model.getWhitePlayer().getPieces().remove(s.getOccupant());
			} else {
				model.getBlackPlayer().getPieces().remove(s.getOccupant());
			}
			s.removeOccupant();
			Piece p;
			PieceView.Pieces type = ((chessView.PromotionEvent)e).getPieceType();
			if (type.toString().equals("QUEEN")) { 
				p = new Queen(((chessView.PromotionEvent)e).getIsWhite());
			} else if (type.toString().equals("ROOK")) {
				p = new Rook(((chessView.PromotionEvent)e).getIsWhite());
			} else if (type.toString().equals("BISHOP")) {
				p = new Bishop(((chessView.PromotionEvent)e).getIsWhite());
			} else {
				p = new Knight(((chessView.PromotionEvent)e).getIsWhite());
			}
			if (((chessView.PromotionEvent)e).getIsWhite()) {
				model.getWhitePlayer().getPieces().add(p);
				p.setPlayer(model.getWhitePlayer());
			} else {
				model.getBlackPlayer().getPieces().add(p);
				p.setPlayer(model.getBlackPlayer());
			}
			model.getBoard().initializePiece(p, s);
			System.out.println(s.getIsOccupied());
		} else if (e.getIsEstablishEngineEvent()) {
			model.setEngine(((EstablishEngineEvent)e).getIsEngineWhite());
		} else if (e.getIsEstablishHumanEvent()) {
			model.setHumanPlayers();
		} else {
			if (e.getLiteral()) {
				if (s.getIsOccupied()) {
					model.move(s.getOccupant(), model.getBoard().getSquareByName(e.getFinalSquare().getName()));
					
					//castling
//					if (e.getInitialSquare().getPiece().equals(Pieces.KING)) {
//						if (Math.abs(s.getFile() - e.getFinalSquare().getColumn()) == 2) {
//							if (e.getInitialSquare().getOccIsWhite()) {
//								if (e.getFinalSquare().getColumn() == 6) {
//									model.move("O-O");
//									view.completeCastleMove(true, true);
//								} else {
//									model.move("O-O-O");
//									view.completeCastleMove(true, false);
//								}
//							} else {
//								if (e.getFinalSquare().getColumn() == 6) {
//									model.move("O-O");
//									view.completeCastleMove(false, true);
//								} else {
//									model.move("O-O-O");
//									view.completeCastleMove(false, false);
//								}
//							}
//						} else {
//							model.move(s.getOccupant(), model.getBoard().getSquareByName(e.getFinalSquare().getName()));
//						}
//						
					//pawn special moves
//					if (e.getInitialSquare().getPiece().equals(Pieces.PAWN)) {
//						if ((e.getInitialSquare().getOccIsWhite() && e.getFinalSquare().getRow() == 7) ||
//								(!e.getInitialSquare().getOccIsWhite() && e.getFinalSquare().getRow() == 0)) {
////							view.completePromotionMove(e.getInitialSquare().getOccIsWhite(), e.getFinalSquare());
//						}
//						if (s.getFile() != e.getFinalSquare().getColumn()) {
//							if (!e.getFinalSquare().getIsOccupied()) {
//								view.completeEnPassentMove(e.getInitialSquare().getOccIsWhite(), e.getFinalSquare().getColumn());
//							}
//						}
//						model.move(s.getOccupant(), model.getBoard().getSquareByName(e.getFinalSquare().getName()));
//					} else {
//						model.move(s.getOccupant(), model.getBoard().getSquareByName(e.getFinalSquare().getName()));
//					}
				}
				
				
			} else {
				if (s.getIsOccupied()) {
					List<Square> squares = s.getOccupant().getValidMoves();
					List<SquareView> squareViews = new ArrayList<SquareView>();
					for (Square sq: squares) {
						squareViews.add(view.getSquareByName(sq.getName()));
					}
					view.receiveValidMovesFromClick(squareViews);
				}
			}
		}
	}

	
	//-----------------------------------------MODEL_EVENTS----------------------------------------------------------
	
	public void handleModelEvent(ModelEvent e) {
		if (e.getIsOutcomeEvent()) {
			
			view.declareOutcome(((OutcomeEvent)e).getOutcome());
			
		} else if (e.getIsPromotionEvent()) {
			
			chess03.PromotionEvent p = (chess03.PromotionEvent) e;
			view.completePromotionMove(p.getType(), view.getSquareByName(p.getSquare().getName()), p.getIsWhite());
		
		} else if (e.getIsCastleEvent()) {
			
			view.completeCastleMove(((CastleEvent)e).getIsWhite(), ((CastleEvent)e).getIsKingside());
		
		} else if (e.getIsEnPassentEvent()) {
			
			view.completeEnPassentMove(((EnPassentEvent)e).getIsWhite(), ((EnPassentEvent)e).getFile());
			
		} else if (e.getIsCheckEvent()) {
			
			view.completeCheckEvent(((CheckEvent)e).getIsWhite());
			
		} else if (e.getIsThinkingEvent()) {
			
			view.completeThinkingEvent(((ThinkingEvent)e).getStartedThinking(), ((ThinkingEvent)e).getInitialSquare(), ((ThinkingEvent)e).getFinalSquare());
			
		}
		
		
	}
	
	
}
