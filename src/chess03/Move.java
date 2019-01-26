package chess03;

public class Move {

	private Piece p;
	private Square ogs;
	private Square s;
	private boolean occupied;
	private boolean check;
	private boolean checkmate;
	private String name;
	
	
	public Move(Piece p, Square s, Square ogs, boolean occupied, boolean check, boolean checkmate) {
		this.p = p;
		this.ogs = ogs;
		this.occupied = occupied;
		this.s = s;
		this.check = check;
		this.checkmate = checkmate;
		this.name = generateName();
	}
	
	public Move(Piece p, Square s, boolean occupied, Square ogs) {
		this.p = p;
		this.s = s;
		this.ogs = ogs;
		this.occupied = occupied;
		this.check = false;
		this.checkmate = false;
		this.name = generateName();
	}
	
	public Move(Piece p, Square s, Square ogs) {
		this.p = p;
		this.s = s;
		if (ogs == null) {throw new IllegalArgumentException("Starting square is null");}
		this.ogs = ogs;
		this.occupied = false;
		this.check = false;
		this.checkmate = false;
		this.name = generateName();
	}
	
	public Piece getPiece() {
		return p;
	}
	
	public Square getSquare() {
		return s;
	}
	
	public boolean getIsCheck() {
		return check;
	}
	
	public boolean getIsCheckMate() {
		return checkmate;
	}

	public boolean getIsPromotionMove() {
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getIsACapture() {
		return occupied;
	}
	
	public String generateName() {
		String str = "";
		if (p.getType() == null) {throw new IllegalArgumentException("word");}
		if ((p.getType().equals(Piece.Type.PAWN)) && (ogs.getFile() != s.getFile())) {
			if (occupied) {
				str = ogs.getFileName() + "x" + s.getName();
			} else {
				str = ogs.getFileName() + "x" + s.getName();
				str = str.substring(0,3);
				if (p.getIsWhite()) {
					str += Integer.toString(s.getRank());
				} else {
					str += Integer.toString(s.getRank() + 2);
				}
			}	
		} else if (occupied) {
			str = p.getInitial() + "x" + s.getName();
		} else {
			str = p.getInitial() + s.getName();
		}	
		
		boolean b = false;
		boolean sameFile = false;
		for (Piece newP: p.getPlayer().getPieces()) {
			if (!newP.equals(p)) {
				if (newP.getType().equals(p.getType()) && p.getType() != Piece.Type.PAWN) {
					for (Square s2: newP.getValidMoves()) {
						if (s2.getName().equals(s.getName())) {
							b = true;
							if (newP.getPosition().getFile() == ogs.getFile()) {
								sameFile = true;
							}
							break;
						}
					}
				}
			}
		}
		if (b) {
			if (str.length() == 3) {
				if (sameFile) {
					str = p.getInitial() + (ogs.getRank() + 1) + s.getName();
				} else {
					str = p.getInitial() + ogs.getFileName() + s.getName();
				}
			} else {
				str = str.substring(1, str.length());
				if (sameFile) {
					str = p.getInitial() + (ogs.getRank() + 1) + str;
				} else {
					str = p.getInitial() + ogs.getFileName() + str;
				}
			}
		}
//		if (checkmate) {
//			str += "#";
//		} else if (check) {
//			str += "+";
//		}
		 
		return str;
	}
	
}
