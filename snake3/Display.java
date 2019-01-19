package snake3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import flappy.ViewMain;

public class Display extends JFrame implements KeyListener {

	private mainPane main_panel;
	private int clicksPerTurn;
	private Timer timer;
	private TimerTask timerTask;
	private boolean alive;
	
	public Display(int boardSize) {
		alive = true;
		setTitle("Snake");
		setDefaultCloseOperation(3);
		
		main_panel = new mainPane(boardSize, this);
		clicksPerTurn = 0;

		setContentPane(main_panel);
		setPreferredSize(new Dimension(580,600));
		setFocusable(true);
		addKeyListener(this);
		
		pack();
		setVisible(true);
		
		startTimer();
	}
	
	
	public void resetClicks() {
		clicksPerTurn = 0;
	}
	
	public JLabel getScoreLabel() {
		return main_panel.getScoreLabel();
	}
	
	public Board getBoard() {
		return main_panel.getBoard();
	}
	
	public void setIsAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void startTimer() {
		timer = new Timer();
		timerTask = new TimerTask() {
			public void run() {
				main_panel.getBoard().update();
			}
		};
		timer.schedule(timerTask, 1000l, 50l);
	}
	
	public void stopTimer() {
		timer.purge();
		timer.cancel();
	}
	
	public void restart() {
		main_panel.getBoard().resetSnake();
		main_panel.resetScore();
		startTimer();
	}
	
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if (clicksPerTurn < 1) {
			if (key == 39) {
				if (!getBoard().getSnake().getHeadTile().getDirection().equals(Tile.Direction.W)) {
					getBoard().getSnake().getHeadTile().setDirection(Tile.Direction.E);
					clicksPerTurn++;
				}
			} else if (key == 40) {
				if (!getBoard().getSnake().getHeadTile().getDirection().equals(Tile.Direction.N)) {
					getBoard().getSnake().getHeadTile().setDirection(Tile.Direction.S);
					clicksPerTurn++;
				}
			} else if (key == 37) {
				if (!getBoard().getSnake().getHeadTile().getDirection().equals(Tile.Direction.E)) {
					getBoard().getSnake().getHeadTile().setDirection(Tile.Direction.W);
					clicksPerTurn++;
				}
			} else if (key == 38) {
				if (!getBoard().getSnake().getHeadTile().getDirection().equals(Tile.Direction.S)) {
					getBoard().getSnake().getHeadTile().setDirection(Tile.Direction.N);
					clicksPerTurn++;
				}
			} else if (key == KeyEvent.VK_N) {
				if (!alive) {
					clicksPerTurn = 0;
					alive = true;
					restart();
				}
			}
		}
			
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
}
