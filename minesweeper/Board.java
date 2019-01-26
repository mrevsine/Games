package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Board extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
	private Tile[][] tiles;
	private Screen screen;
	
	public Board(int size, Screen screen) {
		
		this.screen = screen;
		this.size = size;
		tiles = new Tile[size][size];
		setLayout(new GridLayout(size, size));
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Tile t = new Tile(this, i, j);
				tiles[i][j] = t;
				add(t);
			}
		}
		setPreferredSize(new Dimension(500,500));
		setBombs();
	}
	
	public void setBombs() {
		int num = (int) (Math.pow(size, 2)/8);
		num++;
		for (int i = 0; i < num; i++) {
			boolean settled = false;
			while(!settled) {
				Tile t = tiles[randomInt(0, size)][randomInt(0, size)];
				if (!t.equals(tiles[0][0])) {
					if (!t.getHasBomb()) {
						t.setHasBomb();
						settled = true;
					}
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (tiles[i][j].getHasBomb()) {
					for (Tile t: getBorderingTiles(i,j)) {
						if (!t.getHasBomb()) {
							t.increaseNumber();
						}
					}
				}
			}
		}
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public int getNumOfRows() {
		return size;
	}
	
	public Screen getScreen() {
		return screen;
	}

	public void endGameLoss() {
		screen.stopTimer();
		screen.gameLost();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (tiles[i][j].getHasBomb()) {
					tiles[i][j].showClick();
				}
				tiles[i][j].removeMouseListener(tiles[i][j]);
			}
		}
		
		
	}
	
	public void endGameWin() {
		screen.stopTimer();
		screen.gameWon();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tiles[i][j].removeMouseListener(tiles[i][j]);
				if (!tiles[i][j].getHasBomb()) {
					tiles[i][j].showClick();
				}
			}
		}
	}
	
	public void checkForWin() {
		boolean over = true;
		outerloop:
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (tiles[i][j].getHasBomb()) {
					if (!tiles[i][j].getHasFlag()) {
						over = false;
						break outerloop;
					}
				}
			}
		}
		if (over) {
			endGameWin();
		}
	}
	
	public List<Tile> getBorderingTiles(int a , int b) {
		List<Tile> borders = new ArrayList<Tile>();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (Math.abs(a-i) < 2 && Math.abs(b-j) < 2) { 
					if (!(i == a && j == b)) {
						borders.add(tiles[i][j]);
					}
				}
			}
		}
		return borders;
		
	}
	
	public void showBlankNeighbors(int a, int b) {
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (Math.abs(i-a) < 2 && Math.abs(j-b) < 2) {
					if (!(a==i && b==j)) {
						if (!tiles[i][j].getHasBeenClicked()) {
							if (!tiles[i][j].getHasBomb()) {
								if (tiles[i][j].getNumberOfBorderingBombs() > 0) {
									tiles[i][j].showClick();
								} else if (tiles[i][j].getNumberOfBorderingBombs() == 0) {
									tiles[i][j].showClick();
									showBlankNeighbors(i,j);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static int randomInt(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
