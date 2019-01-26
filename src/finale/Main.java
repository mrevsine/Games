package finale;

/**
 * author: mrevsine
 * date: TBD
 * First Version of an interactive chess program
 * User can play against a human or against the included engine
 * Created using model-view-controller design pattern
 */

import chess03.*;
import chessView.*;
import chessController.*;


//Main method to launch program
//Establishes MVC connection

public class Main {

	public static void main(String[] args) {
		GameModel m = new GameModel(true);
		BoardView v = new BoardView();
		Controller c = new Controller(m,v);
	}
	
}
