package chessView;

public class PromotionEvent extends ViewEvent {

	private PieceView.Pieces piece;
	private boolean white;
	
	public PromotionEvent(SquareView s1, SquareView s2, PieceView.Pieces piece, boolean white) {
		super(s1, s2);
		this.piece = piece;
		this.white = white;
		// TODO Auto-generated constructor stub
	}
	
	public PieceView.Pieces getPieceType() {
		return piece;
	}
	
	public boolean getIsWhite() {
		return white;
	}

	@Override
	public boolean isPromotion() {
		return true;
	}
	
}
