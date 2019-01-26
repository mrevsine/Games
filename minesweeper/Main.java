package minesweeper;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	
	/**
	 * @author MahlerRevsine
	 * @param args
	 * Date: 8/8/18
	 * Replica of MineSweeper
	 * Argument for setup() is the number of tiles to make up the board's width and height
	 * Default is 20
	 */
	
	public static void main(String[] args) {
		
		setUp();
		
	}
	
	public static void setUp() {
		JFrame frame = new JFrame();
		frame.setTitle("MineSweeper");
		frame.setDefaultCloseOperation(3);
		
		frame.setContentPane(new Screen(20));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void setUp(int size) {
		JFrame frame = new JFrame();
		frame.setTitle("MineSweeper");
		frame.setDefaultCloseOperation(3);
		
		frame.setContentPane(new Screen(size));
		
		frame.pack();
		frame.setVisible(true);
	}
}
