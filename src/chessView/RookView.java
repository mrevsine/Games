package chessView;


//coordinates for a rook
public class RookView extends PieceView {

	public RookView() {
		super(new int[] {10, 18, 18, 26, 26, 34, 34, 42, 42, 50, 50, 42, 42, 50, 50, 10, 10, 18, 18, 10},
				new int[] {10, 10, 18, 18, 10, 10, 18, 18, 10, 10, 26, 34, 42, 42, 50, 50, 42, 42, 34, 26}, 
				20, PieceView.Pieces.ROOK);
		
	}
}
