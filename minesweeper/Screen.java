package minesweeper;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Screen extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	private JPanel title;
	private JLabel score;
	private JLabel time;
	private JButton restart;
	private Timer t;
	private int size;
	
	public Screen(int size) {
		this.size = size;
		setLayout(new BorderLayout());
		board = new Board(size, this);
		add(board, BorderLayout.CENTER);
		
		title = new JPanel();
		title.setLayout(new GridLayout(1,3));
		
		score = new JLabel(Integer.toString((int) (Math.pow(size, 2)/8) + 1));
		score.setHorizontalAlignment(JLabel.CENTER);
		restart = new JButton();
		restart.setText("RESTART");
		restart.setActionCommand("restart");
		restart.addActionListener(this);
		time = new JLabel("1000");
		time.setHorizontalAlignment(JLabel.CENTER);
		
		title.add(score);
		title.add(restart);
		title.add(time);
		
		add(title, BorderLayout.NORTH);

		startTimer();
	}
	
	public Screen(int size, boolean showing) {
		setLayout(new BorderLayout());
		board = new Board(size, this);
		add(board, BorderLayout.CENTER);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board.getTiles()[i][j].showClick();
			}
		}
		
		title = new JPanel();
		title.setLayout(new GridLayout(1,3));
		
		score = new JLabel(Integer.toString((int) (Math.pow(size, 2)/8) + 1));
		score.setHorizontalAlignment(JLabel.CENTER);
		restart = new JButton();
		restart.setText("RESTART");
		restart.setActionCommand("restart");
		restart.addActionListener(this);
		time = new JLabel("1000");
		time.setHorizontalAlignment(JLabel.CENTER);
		
		title.add(score);
		title.add(restart);
		title.add(time);
		
		add(title, BorderLayout.NORTH);
		
		startTimer();
	}
	
	public void markBomb() {
		score.setText(Integer.toString(Integer.parseInt(score.getText()) - 1));
	}
	
	public void gameWon() {
		score.setText("YOU WON!");
	}
	
	public void gameLost() {
		score.setText("GAME OVER");
	}
	
	public void countDown() {
		time.setText(Integer.toString(Integer.parseInt(time.getText()) - 1));
		if (time.getText().equals("0")) {
			board.endGameLoss();
		}
	}
	
	public void startTimer() {
		t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				countDown();
			}
		}, 1000l, 1000l);
	}
	
	public void stopTimer() {
		t.cancel();
		t.purge();
	}

	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getActionCommand().equals("restart")) {
			time.setText("1000");
			score.setText(Integer.toString((int)((Math.pow(size, 2))/8)));
			startTimer();
			remove(board);
			board = new Board(size, this);
			add(board, BorderLayout.CENTER);
			repaint();
		}
		
	}
}
