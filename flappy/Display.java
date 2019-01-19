package flappy;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Display extends JFrame implements KeyListener {

	private ViewMain content;
	private boolean alive;
	private boolean started;
	
	public Display() {
		alive = true;
		started = false;
		
		setTitle("Flappy");
		setDefaultCloseOperation(3);
		setPreferredSize(new Dimension(700,500));
		
		content = new ViewMain(this);
		setContentPane(content);
		
		setFocusable(true);
		addKeyListener(this);
		
		pack();
		setVisible(true);
		
		
		
	}
	
	public boolean getIsAlive() {
		return alive;
	}
	
	public void setIsAlive(boolean b) {
		alive = b;
	}
	
	public boolean getHasStarted() {
		return started;
	}
	
	public void setHasStarted(boolean b) {
		started = b;
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (alive) {
			if (started) {
				if (arg0.getExtendedKeyCode() == 32) {
					content.getView().onTap();
				}
			} else {
				if (arg0.getExtendedKeyCode() == 10) {
					started = true;
					content.startGame();
				}
			}
		} else {
			if (arg0.getExtendedKeyCode() == 10) {
				content = new ViewMain(this);
				setContentPane(content);
				alive = true;
				started = true;
				content.startGame();
			}
		}
	}
	
}
