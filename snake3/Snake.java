package snake3;

import java.util.ArrayList;
import java.util.List;

public class Snake {

	private List<Tile> tiles;
	private Board board;
	
	public Snake(Board board) {
		tiles = new ArrayList<Tile>();
		tiles.add(board.getTiles()[16][4]);
		tiles.add(board.getTiles()[16][3]);
		tiles.add(board.getTiles()[16][2]);
		this.board = board;
		renderTiles();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	public Tile getHeadTile() {
		return tiles.get(0);
	}
	
	public Tile getTailTile() {
		return tiles.get(tiles.size() -1);
	}
	
	public void renderTiles() {
		for (Tile t: tiles) {
			t.setIsOccupied(true);
			t.setOccupantIsSquare(true);
			t.setDirection(Tile.Direction.E);
		}
	}
	
}
