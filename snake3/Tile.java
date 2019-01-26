package snake3;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Tile extends JPanel {

	private int row;
	private int column;
	private boolean occupied;
	private boolean square;
	public enum Direction {N, E, S, W};
	private Direction direction;
	
	public Tile(int row, int column) {
		this.row = row;
		this.column = column;
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		occupied = false;
		square = false;
		direction = null;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public boolean getIsOccupied() {
		return occupied;
	}
	
	public boolean getOccupantIsSquare() {
		return square;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	//direction at (index + 2) % 4
	//i.e. opposite direction
	public Direction getOppositeDirection() {
		return (Direction.values()[(Direction.valueOf(direction.toString()).ordinal() + 2) % 4]);
	}
	
	public void setDirection(Direction d) {
		direction = d;
	}
	
	public void setIsOccupied(boolean b) {
		occupied = b;
	}
	
	public void setOccupantIsSquare(boolean b) {
		square = b;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (occupied) {
			if (square) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, getWidth(), getWidth());
			} else {
				g.setColor(Color.RED);
				g.fillOval((int) (getWidth() * 0.1), (int) (getWidth() * 0.1), 
						(int) (getWidth() * 0.8), (int) (getWidth() * 0.8));
			}
		}
		
	}
	
}
