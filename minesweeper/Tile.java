package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Tile extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Board board;
	private boolean clicked;
	private boolean hasFlag;
	private boolean hasBomb;
	private int numberOfBorderingBombs;
	private int i;
	private int j;
	
	public Tile(Board b, int i, int j) {
		this.i = i;
		this.j = j;
		board = b;
		clicked = false;
		hasFlag = false;
		hasBomb = false;
		numberOfBorderingBombs = 0;
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		addMouseListener(this);
	}
	
	public int getNumberOfBorderingBombs() {
		return numberOfBorderingBombs;
	}
	
	public boolean getHasBeenClicked() {
		return clicked;
	}
	
	public boolean getHasBomb() {
		return hasBomb;
	}
	
	public boolean getHasFlag() {
		return hasFlag;
	}
	
	public void increaseNumber() {
		numberOfBorderingBombs++;
//		label.setText(Integer.toString(Integer.parseInt(label.getText())+1));
	}
	
	public void setHasBomb() {
		hasBomb = true;
	}
	
	public void setNumberOfBorderingBombs(int n) {
		numberOfBorderingBombs = n;
		repaint();
	}
	
	public void showClick() {
		if (!clicked) {
			clicked = true;
			hasFlag = false;
			setBackground(Color.LIGHT_GRAY.brighter());
			repaint();
			board.checkForWin();
		}	
	}
	
	public void showRightClick() {
		if (!clicked) {
			if (!hasFlag) {
				hasFlag = true;
				repaint();
				board.getScreen().markBomb();
			} else {
				hasFlag = false;
				repaint();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//dominant dimension is smaller dimension. For sizing
		int domDim = getWidth();
		if (getHeight() < getWidth()) {
			domDim = getHeight();
		}
		double scale = 1.5;
		
		if (clicked) {
			if (numberOfBorderingBombs > 0) {
				g.setFont(new Font("Sans-serif", Font.BOLD, (int)(domDim/scale)));
				g.setColor(Color.RED);
				for (int i = 0; i < numberOfBorderingBombs; i++) {
					g.setColor(g.getColor().darker());
				}
				g.drawString(Integer.toString(numberOfBorderingBombs), (int)(getWidth()/(scale*2)), (int)(getHeight()/(scale*0.9)));
			} else if (hasBomb) {
				g.setColor(Color.BLACK);
				g.fillOval((int) ((getWidth()/2) - (domDim/(scale*2))), (int) ((getHeight()/2) - (domDim/(scale*2))), (int)(domDim/scale), (int)(domDim/scale));
				board.endGameLoss();
			} else {
				board.showBlankNeighbors(i, j);
			}
		} else {
			if (hasFlag) {
				g.setColor(Color.RED);
				g.fillRect((int)(getWidth()/2.5), (int)(getHeight()/6), (int)(getWidth()/10), (int) (getHeight()/1.5));
				g.fillPolygon(
						new int[] {(int)(getWidth()/2.5) + (int)(getWidth()/10), (int)(getWidth()/2.5) + (int)(getWidth()/3), (int)(getWidth()/2.5) + (int)(getWidth()/10)},  
						new int[] {(int)(getHeight()/6), (int) (getHeight()/3), (int)(getHeight()/2)}, 
						3);
			} else {
				g.drawRect(0, 0, 0, 0);
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		
		if (SwingUtilities.isRightMouseButton(arg0)) {
			showRightClick();
		} else {
			showClick();
		}
		
	}

	//unused
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
}
