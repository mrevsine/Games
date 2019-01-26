package chess03;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import chessView.ViewEvent;
import chessView.ViewListener;

public class GameModel implements Game {

	
	//--------------------------------------------Fields-------------------------------------------------------------
	private Board board;
	private Player whitePlayer;
	private Player blackPlayer;
	private MoveLog moveLog;
	private BoardLog boardLog;
	private ArrayList<ModelListener> listeners;
	private boolean hasEngine;
	private boolean engineIsWhite;
	
	//--------------------------------------------Constructors--------------------------------------------------------
	public GameModel() {
		this.board = new Board();
		this.board.setGameModel(this);
		this.whitePlayer = new PlayerImpl(this.board, true);
		this.blackPlayer = new PlayerImpl(this.board, false);
		this.board.incorportatePlayers(this.whitePlayer, this.blackPlayer);
		this.whitePlayer.setTurn(true);
		this.blackPlayer.setTurn(false);
		this.moveLog = new MoveLog(this);
		this.boardLog = new BoardLog(this);
		listeners = new ArrayList<ModelListener>();
		hasEngine = false;
		engineIsWhite = false;
	}
	
	public GameModel(Board b) {
		b.setGameModel(this);
		this.whitePlayer = b.getWhitePlayer();
		this.blackPlayer = b.getBlackPlayer();
		b.getWhitePlayer().setTurn(true);
		b.getBlackPlayer().setTurn(false);
		this.board = b;
		this.moveLog = new MoveLog(this);
		this.boardLog = new BoardLog(this);
		listeners = new ArrayList<ModelListener>();
		hasEngine = false;
		engineIsWhite = false;
	}
	
	
	public GameModel(boolean b) {
		this.board = new Board();
		this.board.setGameModel(this);
		this.moveLog = new MoveLog(this);
		this.boardLog = new BoardLog(this);
		listeners = new ArrayList<ModelListener>();
		hasEngine = false;
		engineIsWhite = false;
		this.whitePlayer = null;
		this.blackPlayer = null;
	}
	
	//------------------------------------------Getters/Setters------------------------------------------------------
	
	public Board getBoard() {
		return this.board;
	}
	
	public Player getWhitePlayer() {
		return this.whitePlayer;
	}
	
	public Player getBlackPlayer() {
		return this.blackPlayer;
	}
	
	public Player getPlayerWithTurn() {
		return this.board.getPlayerWithTurn();
	}
	
	public MoveLog getMoveLog() {
		return this.moveLog;
	}
	
	public BoardLog getBoardLog() {
		return this.boardLog;
	}
	
	public void setEngine(boolean white) {
		if (white) {
			for (Piece p: this.getBoard().getPieces()) {
				if (p.getIsWhite()) {
					p.getPosition().removeOccupant();
					System.out.println(p.getPosition().getIsOccupied());
				}
			}
			this.whitePlayer = new Engine(board, true);
			if (blackPlayer == null) {
				this.blackPlayer = new PlayerImpl(board, false);
				this.whitePlayer.setTurn(true);
				this.blackPlayer.setTurn(false);
			}
			this.board.incorportatePlayers(this.whitePlayer, this.blackPlayer);
			engineIsWhite = true;
			((Engine)whitePlayer).getBestMove();
		} else {
			for (Piece p: this.getBoard().getPieces()) {
				if (!p.getIsWhite()) {
					p.getPosition().removeOccupant();
				}
			}
			this.blackPlayer = new Engine(board, false);
			if (whitePlayer == null) {
				this.whitePlayer = new PlayerImpl(board, true);
				this.whitePlayer.setTurn(true);
				this.blackPlayer.setTurn(false);
			}
			this.board.incorportatePlayers(this.whitePlayer, this.blackPlayer);
			//Just in case
			engineIsWhite = false;
		}
		hasEngine = true;
	}
	
	public void setHumanPlayers() {
		System.out.println(new BoardRep(board).getState());
		this.whitePlayer = new PlayerImpl(this.board, true);
		this.blackPlayer = new PlayerImpl(this.board, false);
		this.board.incorportatePlayers(this.whitePlayer, this.blackPlayer);
		this.whitePlayer.setTurn(true);
		this.blackPlayer.setTurn(false);
	}
	
	//----------------------------------------------Misc------------------------------------------------------------
	
	public void addModelListener(ModelListener l) {
		listeners.add(l);
	}
	
	public void notifyListeners(ModelEvent e) {
		for (ModelListener l: listeners) {
			l.handleModelEvent(e);
		}
	}
	
	
	//MAIN CALL TO GAME
	//Moves the piece, updates boardLog and moveLog, checks for wins/draws
	public void move(Piece p, Square s) {
		moveLog.handleMoveEvent(new Move(p, s, p.getPosition()));
		board.movePiece(p, s);
		boardLog.addBoard(board);
		swapTurns();
		if (!getPlayerWithTurn().getIsInCheckmate()) {
			if (hasEngine) {
				
				if (getPlayerWithTurn().getIsWhite() == engineIsWhite) {
					if (engineIsWhite) {
						((Engine)getWhitePlayer()).getBestMove();
					} else {
						((Engine)getBlackPlayer()).getBestMove();
					}
				}
				
			}
		}
		
	}
	
	
	//For engine promotions
	public void move(Piece p, Square s, Piece.Type t) {
		moveLog.handleMoveEvent(new PromotionMove(p, s, p.getPosition(), false, t));
		board.moveAndPromoteEngine(p, s, t);
		boardLog.addBoard(board);
		swapTurns();
		
		notifyListeners(new PromotionEvent(t, s, p.getIsWhite()));
	}
	
	public void announceOutcome(Outcomes o) {
		notifyListeners(new OutcomeEvent(o));
	}
	
	public void setTurn(Boolean b) {
		if (b) {
			this.getWhitePlayer().setTurn(true);
			this.getBlackPlayer().setTurn(false);
		} else {
			this.getWhitePlayer().setTurn(false);
			this.getBlackPlayer().setTurn(true);
		}
	}
	
	public void swapTurns() {
		boolean b = whitePlayer.getTurn();
		whitePlayer.setTurn(blackPlayer.getTurn());
		blackPlayer.setTurn(b);
	}
	
}
