package chess03;

import java.sql.Time;
import java.util.List;

public class BlankEngine implements Runnable {

	private Board board;
	private List<Move> moves;
	private Thread[] threads;
	private long start;
	
	public BlankEngine(Board b) {
		board = b;
		moves = b.getPlayerWithTurn().getMoves();
		threads = new Thread[8];
		start = 0;
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		try {
			for (int i = 0; i < moves.size(); i++) {
				if (i % 8 == Integer.parseInt(t.getName())) {
					System.out.println(moves.get(i).getName());
					if (i == moves.size() - 1) {
						System.out.println("Done after " + ((System.nanoTime() - start)/1000000000.0) + " seconds");
					}
					Thread.sleep(1); 
					
				}
			}
		} catch(InterruptedException e) {
		}
//		System.out.println("Collected thread " + (Integer.parseInt(t.getName()) + 1));
	}
	
	public void start() {
		if (start == 0) {
			start = System.nanoTime();
		}
		System.out.println("Creating threads");
		for (int i = 0; i < 8; i++) {
//			System.out.println("Created thread " + Integer.toString(i + 1));
			threads[i] = new Thread(this, Integer.toString(i));
			threads[i].start();
		}
	}
}
