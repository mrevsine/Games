package chessView;

public class BishopView extends PieceView {

	public BishopView() {
		super(new int[] {30, 50, 42, 42, 50, 50, 10, 10, 18, 18, 10, 18, 28, 32, 22},
				new int[] {10, 30, 38, 42, 42, 50, 50, 42, 42, 38, 30, 22, 32, 28, 18},
				15, PieceView.Pieces.BISHOP);
	}
}
