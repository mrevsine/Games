package chessView;

public class QueenView extends PieceView {

	public QueenView() {
		super(new int[] {30, 36, 42, 42, 34, 34, 42, 50, 50, 10, 10, 18, 26, 26, 18, 18, 24},
				new int[] {10, 16, 10, 20, 28, 34, 34, 26, 50, 50, 26, 34, 34, 28, 20, 10, 16},
				17, PieceView.Pieces.QUEEN);
	}
}
