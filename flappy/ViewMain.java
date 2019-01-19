package flappy;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewMain extends JPanel {

	private Display display;
	private JLabel score;
	private JPanel scoreboard;
	private ViewPanel view;
	private Timer timer;
	private TimerTask timertask;
	
	public ViewMain(Display d) {
		display = d;
		setLayout(new BorderLayout());
		view = new ViewPanel(this);
		score = new JLabel("Press Enter to Start");
		score.setFont(new Font("Sans-serif", Font.BOLD, 24));
		scoreboard = new JPanel();
		scoreboard.add(score);
		add(view, BorderLayout.CENTER);
		add(scoreboard, BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
		
		view.onTap();
		
		timer = new Timer();
		timertask = new TimerTask() {
			public void run() {
				update();
			}
		};
		
	}
	
	public void incrementScore() {
		score.setText(Integer.toString(Integer.parseInt(score.getText()) +1));
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public void setText(String s) {
		score.setText(s);
	}
	
	public void startGame() {
		score.setText("0");
		startTimer();
	}
	
	public void endGame() {
		display.setHasStarted(false);
		display.setIsAlive(false);
		score.setText("Game Over! Press Enter To Restart. Score: " + score.getText());
		stopTimer();
	}
	
	public void startTimer() {
		timer.schedule(timertask, 0l, 25l);
	}
	
	public void stopTimer() {
		timer.cancel();
		timer.purge();
	}
	
	public ViewPanel getView() {
		return view;
	}
	
	public void update() {
		view.update();
	}
}
