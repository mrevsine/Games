package chessView;

public class KingView extends PieceView {

	public KingView() {
		super(new int[] {26, 34, 34, 42, 42, 34, 34, 42, 50, 50, 10, 10, 18, 26, 26, 18, 18, 26},
				new int[] {10, 10, 18, 18, 26, 26, 34, 34, 26, 50, 50, 26, 34, 34, 26, 26, 18, 18},
				18, PieceView.Pieces.KING);
	}
}
