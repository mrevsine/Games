package flappy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.sound.sampled.*;

public class ViewPanel extends JPanel {

	//---------------------------------------------FIELDS------------------------------------------------------------
	private ViewMain viewMain;
	private int time;
	private int y;
	private int x;
	private int width;
	final private int HITBOX;
	private int velo;
	private final int gravity = 1;
	private final int lift = -10;
	private List<Obstacle> obstacles;
	private Image image;
	private Color ballColor;
	
	
	//--------------------------------------------CONSTRUCTOR---------------------------------------------------------
	public ViewPanel(ViewMain v) {
		obstacles = new ArrayList<Obstacle>();
		time = 0;
		viewMain = v;
		setPreferredSize(getPreferredSize());
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		x = (int) getWidth() / 3;
		y = (int) getHeight() / 2;
		width = 30;
		HITBOX = (int) Math.ceil(Math.sqrt(width * width / 2)); //square inscribed in the circle
		System.out.println(HITBOX);
		velo = 0;
		ballColor = new Color(50,150,0);
		try {
			image = ImageIO.read(new File("/Users/MahlerRevsine/Documents/BudgieLookRight.png"));
		} catch(IOException e) {
		}
	}
	
	//----------------------------------------------GETTERS----------------------------------------------------------
	@Override
	public int getHeight() {
		return (int) getPreferredSize().getHeight();
	}
	
	@Override
	public int getWidth() {
		return (int) getPreferredSize().getWidth();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(680,420);
	}
	
	public int getCenterX() {
		return x + (int)(width/2);
	}
	
	public int getCenterY() {
		return x + (int)(width/2);
	}
	
	
	//------------------------------------------------PAINT-----------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(ballColor);
		
		g.fillOval(x, y, width, width);
//		g.drawImage(image, x, y, width, width, null);
		
		g.setColor(Color.BLACK);
		for (Obstacle o: obstacles) {
			g.fillRect(o.getX(), 0, o.getWidth(), o.getGap());
			g.fillRect(o.getX(), o.getGap() + o.getGapWidth(), o.getWidth(), getHeight());
		}
	}
	
	
	//-------------------------------------------------EVENTS--------------------------------------------------------
	public void update() {
		time++;
		if (time % 40 == 0) {
			obstacles.add(new Obstacle((int) (Math.random() * width * 4), getPreferredSize()));
			if (obstacles.get(0).getX() <= -obstacles.get(0).getWidth()) {
				obstacles.remove(0);
			}
		}
		for (Obstacle o: obstacles) {
			o.update();
			if (o.getX() >= x - o.getWidth() && o.getX() <= x + width) {
				if ((y <= o.getGap()) || (y >= o.getGap() + o.getGapWidth() - width)) {
					if (Math.abs(x - o.getX()) < HITBOX || Math.abs(x - (o.getX() + o.getWidth())) < HITBOX) { // x for hit
						if (y > o.getGap() - HITBOX/2 || y < o.getGap() + o.getGapWidth() + HITBOX/2) {        // y for hit
							soundThud();
							soundGameOver();
							viewMain.endGame();	
						}
					}
					
				}
			} else if (o.getX() < x - o.getWidth()) {
				if (!o.getHasPassed()) {
					o.setHasPassed();
					soundDing();
					viewMain.incrementScore();
				}
			}
			repaint();
		}
		velo += gravity;
		y += velo;
		
		if (y <= 0) {
			y = 0;
			velo = 1;
		} else if (y >= 400) {
			velo = 0;
			y = 400;
			viewMain.endGame();
		}
		repaint();
	}
	
	public void onTap() {
		velo = 0;
		velo += lift;
		y += velo;
		repaint();
		soundBounce();
	}
	
	
	//---------------------------------------------------SOUNDS-----------------------------------------------------
	
	public void soundDing() {
		playSound(new File("/Users/MahlerRevsine/Documents/MATLAB/arcade_ding.wav"));
		
	}
	
	public void soundBounce() {
		playSound(new File("/Users/MahlerRevsine/Downloads/arcade_bounce.wav"));

	}
	
	public void soundThud() {
		playSound(new File("/Users/MahlerRevsine/Documents/MATLAB/Songbird/arcade_thud2.wav"));

	}
	
	public void soundGameOver() {
		playSound(new File("/Users/MahlerRevsine/Downloads/arcade_gameover.wav"));

	}
	
	
	public void playSound(File soundFile) {
        try {
        		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
        		Clip clip = AudioSystem.getClip();
        		clip.open(audioIn);
        		clip.start();
        } catch (IOException e) {
        	
        } catch (UnsupportedAudioFileException e) {
        	
        } catch (LineUnavailableException e) {

		}

	}
	
	
	
//	public static double distance(int x1, int y1, int x2, int y2) {
//		return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
//	}
}
