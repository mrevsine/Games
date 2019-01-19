package snake3;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class mainPane extends JPanel {
	
	private Board board;
	private JPanel title;
	private JLabel score;

	public mainPane(int boardSize, Display d) {
		setLayout(new BorderLayout());
		
		board = new Board(boardSize, d);
		
		title = new JPanel();
		score = new JLabel();
		score.setFont(new Font("Sans-serif", Font.BOLD, 24));
		score.setText("0");
		title.add(score);
		
		add(board, BorderLayout.CENTER);
		add(title, BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public JLabel getScoreLabel() {
		return score;
	}
	
	public void resetScore() {
		score.setText("0");
	}
}
