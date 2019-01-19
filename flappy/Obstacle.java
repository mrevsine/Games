package flappy;

import java.awt.Dimension;

public class Obstacle {
	
	private int gap;
	private Dimension dim;
	private int x;
	private boolean passed;

	public Obstacle(int gap, Dimension dim) {
		this.gap = gap + 100;
		this.dim = dim;
		this.x = dim.width;
		passed = false;
	}
	
	public boolean getHasPassed() {
		return passed;
	}
	
	public void setHasPassed() {
		passed = true;
	}
	
	public int getGapWidth() {
		return 100;
	}
	
	public int getX() {
		return x;
	}
	
	public int getGap() {
		return gap;
	}
	
	public int getWidth() {
		return 40;
	}
	
	public void update() {
		x -= 4;
	}
	
}
