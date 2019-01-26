package chess03;

import java.util.Timer;
import java.util.TimerTask;

public class Engine extends PlayerImpl {
	
	public Engine(Board b, boolean white) {
		super(b, white);
//		threads = new Thread[8];
//		if (white) {
//			p = b.getWhitePlayer();
//		} else {
//			p = b.getBlackPlayer();
//		}
//		
//		//maybe?
//		super.setUpPieces();
	}
	
//	public void run() {
//		Thread t = Thread.currentThread();
//		try {
//			for (int i = 0; i < getMoves().size(); i++) {
//				if (i % 8 == Integer.parseInt(t.getName())) {
//					System.out.println(getMoves().get(i).getName());
//					Thread.sleep(1);
//				}
//			}
//		} catch(InterruptedException e) {
//		}
//	}
//	
//	public void start() {
//		for (int i = 0; i < 8; i++) {
//			threads[i] = new Thread(this, Integer.toString(i));
//			threads[i].start();
//		}
//	}
	
	
	//MAIN ENGINE FUNCTION
	public void getBestMove() {
		getBoard().getGameModel().notifyListeners(new ThinkingEvent(true, null, null));
		
		//this line needs to be edited
		//establish evaluators for each move
		//best evaluation is passed to gamemodel
//		Move m = getMoves().get(randomInt(0, getMoves().size()));
		
		Move m = getMoves().get(0);
		double n = (new Evaluator((new Variation(getBoard(), m)).getBoard(), getIsWhite())).getEval();
		
		for (Move m2: getMoves()) {
			Variation v = new Variation(getBoard(), m2);
			Evaluator e = new Evaluator(v.getBoard(), getIsWhite());
			if (getIsWhite()) {
				if (e.getEval() > n) {
					m = m2;
					n = e.getEval();
				}
			} else {
				if (e.getEval() < n) {
					m = m2;
					n = e.getEval();
				}
			}
		}
		
		
		//promotions
		if (m.getIsPromotionMove()) {
			getBoard().getGameModel().notifyListeners(new ThinkingEvent(false, m.getPiece().getPosition(), m.getSquare()));
			getBoard().getGameModel().move(m.getPiece(), m.getSquare(), ((PromotionMove)m).getType());
		} else {
			getBoard().getGameModel().notifyListeners(new ThinkingEvent(false, m.getPiece().getPosition(), m.getSquare()));
			getBoard().getGameModel().move(m.getPiece(), m.getSquare());
		}
		
		
		
	}
	
	public static int randomInt(int min, int max) {
		int range = max - min;
		return (int) ((Math.random() * range) + min);
	}
	
}
