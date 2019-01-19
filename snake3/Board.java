package snake3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{

	private Tile[][] tiles;
	private Snake snake;
	private Timer timer;
	private TimerTask timerTask;
	private Display display;
	
	public Board(int size, Display display) {
		this.display = display;
		setLayout(new GridLayout(size,size));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		setMinimumSize(new Dimension(400,400));
		setMaximumSize(new Dimension(400,400));
		
		tiles = new Tile[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Tile t = new Tile(i,j);
				t.setBackground(Color.WHITE);
				tiles[i][j] = t;
				add(t);
			}
		}
		snake = new Snake(this);
		for (Tile t: snake.getTiles()) {
			t.repaint();
		}
		moveBall();
//		timer = new Timer();
//		timerTask = new TimerTask() {
//			public void run() {
//				update();
//			}
//		};
//		timer.schedule(timerTask, 1000l, 50l);
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public void resetSnake() {
		for (Tile t: snake.getTiles()) {
			t.setIsOccupied(false);
			t.setDirection(null);
			t.setOccupantIsSquare(false);
			t.setBackground(Color.WHITE);
			t.repaint();
		}
		snake = new Snake(this);
	}
	
	public void growSnake() {
		snake.getTiles().add(0, getTileInDirection(snake.getTiles().get(0), 
				snake.getTiles().get(0).getDirection()));
		snake.getHeadTile().setIsOccupied(true);
		snake.getHeadTile().setOccupantIsSquare(true);
		snake.getHeadTile().setDirection(snake.getTiles().get(1).getDirection());
		snake.getHeadTile().repaint();
	}
	
	public void shrinkSnake() {
		snake.getTiles().get(snake.getTiles().size() -1).setIsOccupied(false);
		snake.getTiles().get(snake.getTiles().size() -1).setDirection(null);
		snake.getTiles().get(snake.getTiles().size() -1).repaint();
		
		snake.getTiles().remove(snake.getTiles().size() -1);
	}
	
	public void update() {
		if (snake.getTiles().size() == Math.pow(tiles.length,2)) {
			display.resetClicks();
			display.getScoreLabel().setText("Score: " + display.getScoreLabel().getText() + "! Press N to Start Over");
			JOptionPane.showMessageDialog(null, "YOU WIN!");
		}
		try {
			if (getTileInDirection(snake.getTiles().get(0), 
					snake.getTiles().get(0).getDirection()).getIsOccupied()) {
				if (getTileInDirection(snake.getTiles().get(0), 
					snake.getTiles().get(0).getDirection()).getOccupantIsSquare()) {
					endGame();
				} else {
					growSnake();
					moveBall();
					display.getScoreLabel().setText(Integer.toString(Integer.parseInt(display.getScoreLabel().getText())+1));
				}
			} else {
				growSnake();
				shrinkSnake();
			}
			
		} catch (NullPointerException e) {
			endGame();
		} finally {
			display.resetClicks();
		}
		
	}
	
	public void endGame() {
		display.resetClicks();
		display.stopTimer();
		display.getScoreLabel().setText("Score: " + display.getScoreLabel().getText() + "! Press N to Start Over");
		display.setIsAlive(false);
	}
	
	public void moveBall() {
		List<Tile> ts = new ArrayList<Tile>();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if (!tiles[i][j].getIsOccupied()) {
					ts.add(tiles[i][j]);
				}
			}
		}
		Tile balltile = ts.get(randomInt(0, ts.size()));
		balltile.setIsOccupied(true);
		balltile.setOccupantIsSquare(false);
		balltile.repaint();
	}
	
	//returns tile in direction d respective to tile t
	//returns null if impossible
	public Tile getTileInDirection(Tile t, Tile.Direction d) {
		if (d.equals(Tile.Direction.N)) {
			if (t.getRow() > 0) {
				return tiles[t.getRow() - 1][t.getColumn()];
			} else {
				return null;
			}
		} else if (d.equals(Tile.Direction.E)) {
			if (t.getColumn() < tiles.length - 1) {
				return tiles[t.getRow()][t.getColumn() + 1];
			} else {
				return null;
			}
		} else if (d.equals(Tile.Direction.S)) {
			if (t.getRow() < tiles.length -1) {
				return tiles[t.getRow() + 1][t.getColumn()];
			} else {
				return null;
			}
		} else {
			if (t.getColumn() > 0) {
				return tiles[t.getRow()][t.getColumn() - 1];
			} else {
				return null;
			}
		}
	}


	
	public static int randomInt(int min, int max) {
		int range = max - min;
		return (int) ((Math.random() * range) + min);
	}
}
