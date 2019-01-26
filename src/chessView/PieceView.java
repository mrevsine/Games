package chessView;

import java.awt.Polygon;


//Drawings for each piece
//consists of array of x-coordinates, array of y-coordinates, and number of points
//(x,y) pairings will make up the outline of the piece. Pay attention to order
//these values will be passed into g.fillPolygon() when paintComponent is called by a squareView
abstract public class PieceView {

	private int[] xPoints;
	private int[] yPoints;
	private int numPoints;
	public enum Pieces {KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN};
	private Pieces piece;
	
	public PieceView(int[] xs, int[] ys, int n, Pieces piece) { 
		xPoints = xs;
		yPoints = ys;
		numPoints = n;
		this.piece = piece;
	}
	
	public int[] getXPoints() {
		return xPoints;
	}
	
	public int[] getYPoints() {
		return yPoints;
	}
	
	public int getNumPoints() {
		return numPoints;
	}
	
	public Pieces getPiece() {
		return piece;
	}
}
