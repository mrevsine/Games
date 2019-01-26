	package chess03;

public class SquareImpl implements Square {

	private int rank;
	private int file;
	private boolean isWhite;
	private boolean isOccupied;
	private Piece occupant;
	
	public SquareImpl(int rank, int file) {
		this.rank = rank;
		this.file = file;
		if ((rank + file) % 2 == 1) {
			this.isWhite = true;
		} else {
			this.isWhite = false;
		}
		this.isOccupied = false;
		this.occupant = null;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public int getFile() {
		return this.file;
	}
	
	public String getFileName() {
		return intToFile(file).toLowerCase();
	}
	
	public boolean getIsWhite() {
		return this.isWhite;
	}
	
	public boolean getIsOccupied() {
		return this.isOccupied;
	}
	
	public Piece getOccupant() {
		return this.occupant;
	}
	
	public String getName() {
		return ""  + intToFile(this.file).toLowerCase() + (this.rank+ 1);
	}
	
	public void setOccupant(Piece p) {
		this.occupant = p;
		this.isOccupied = true;
	}
	
	public void removeOccupant() {
		this.occupant = null;
		this.isOccupied = false;
	}
	
	public boolean borders(Square s) {
		if (!this.getName().equals(s.getName())) {
			if (this.getRank() == s.getRank() || Math.abs(this.getRank() - s.getRank()) == 1) {
				if (this.getFile() == s.getFile() || Math.abs(this.getFile() - s.getFile()) == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isDiagonalTo(Square s) {
		if (!this.getName().equals(s.getName())) {
			if ((Math.abs(this.getRank() - s.getRank())) == (Math.abs(this.getFile() - s.getFile()))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAKnightMoveFrom(Square s) {
		if (!this.getName().equals(s.getName())) {
			if (Math.abs(this.getRank() - s.getRank()) == 1 && Math.abs(this.getFile() - s.getFile()) == 2) {
				return true;
			} else if (Math.abs(this.getFile() - s.getFile()) == 1 && Math.abs(this.getRank() - s.getRank()) == 2) {
				return true;
			}
		}
		return false;
	}
	
	public String intToFile(int n) {
		String s = "";
		switch (n) {
		case 0: s = "A";
		break;
		case 1: s = "B";
		break;
		case 2: s = "C";
		break;
		case 3: s = "D";
		break;
		case 4: s = "E";
		break;
		case 5: s = "F";
		break;
		case 6: s = "G";
		break;
		case 7: s = "H";
		break;
		}
		return s;
	}
	
}
