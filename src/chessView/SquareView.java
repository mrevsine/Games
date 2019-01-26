package chessView;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import chess03.Piece;

//Visual for each Square
public class SquareView extends JPanel {
	
	//---------------------------------------------Fields------------------------------------------------------------
	private int row;
	private int column;
	private boolean occupied;
	private boolean occIsWhite;
	private boolean white;
	private PieceView.Pieces piece;
	private String[] alphabet;

	//---------------------------------------------Constructor-------------------------------------------------------
	public SquareView(int row, int column) {
		this.white = ((row+column)%2 == 1);
		this.row = row;
		this.column = column;
		occupied = (row == 0 || row == 1 || row == 6 || row == 7);
		occIsWhite = false;
		if (row == 0 || row == 1) {
			occIsWhite = true;
		}
		if (!occupied) {
			piece = null;
		} else if (row == 1 || row == 6) {
			piece = PieceView.Pieces.PAWN;
		} else if (column == 0 || column == 7) {
			piece = PieceView.Pieces.ROOK;
		} else if (column == 1 || column == 6) {
			piece = PieceView.Pieces.KNIGHT;
		} else if (column == 2 || column == 5) {
			piece = PieceView.Pieces.BISHOP;
		} else if (column == 3) {
			piece = PieceView.Pieces.QUEEN;
			//I think else is the only option left. Maybe double-check
		} else {
			piece = PieceView.Pieces.KING;
		}
		
		alphabet = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
	}
	
	
	//------------------------------------Getters-&-Setters-----------------------------------------------------------
	public int getRow() {
		return row;
	}
	
	public void setRow(int n) {
		row = n;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int n) {
		column = n;
	}
	
	public boolean getIsOccupied() {
		return occupied;
	}
	
	public void setIsOccupied(boolean b) {
		occupied = b;
	}
	
	public boolean getOccIsWhite() {
		return occIsWhite;
	}
	
	public void setOccIsWhite(boolean b) {
		occIsWhite = b;
	}
	
	public PieceView.Pieces getPiece() {
		return piece;
	}
	
	public void setPieces(PieceView.Pieces p) {
		piece = p;
	}
	
	public boolean getIsWhite() {
		return white;
	}
	
	public String getName() {
		return alphabet[column].toLowerCase() + (row+1);
	}
	
	
	//----------------------------------------------Paint------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (occIsWhite) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}
		if (occupied) {
			PieceView r;
			if (piece.equals(PieceView.Pieces.ROOK)) {
				r = new RookView();
			} else if (piece.equals(PieceView.Pieces.KNIGHT)) {
				r = new KnightView();
			} else if (piece.equals(PieceView.Pieces.BISHOP)){
				r = new BishopView();
			} else if (piece.equals(PieceView.Pieces.KING)){
				r = new KingView();
			} else if (piece.equals(PieceView.Pieces.QUEEN)){
				r = new QueenView();
			} else {
				r = new PawnView();
			}
			g.fillPolygon(r.getXPoints(), r.getYPoints(), r.getNumPoints());
			if (occIsWhite) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawPolygon(r.getXPoints(), r.getYPoints(), r.getNumPoints());
		}
	}
		
		
	//--------------------------------------------------Actions---------------------------------------------------
	public void clearSquare() {
		occupied = false;
		piece = null;
		
		
		//Much faster!
		paintComponent(getGraphics());
//		repaint();
	}
	
	public void setOccupant(PieceView.Pieces piece, boolean white) {
		this.piece = piece;
		occIsWhite = white;
		occupied = true;
		paintComponent(getGraphics());
//		repaint();
	}

}
