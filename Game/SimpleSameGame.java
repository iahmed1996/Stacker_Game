package sameGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import graphics.ColorDef;

public class SimpleSameGame {

	// the board variable needs to be public so that
	// it can be accessed by the JUnit test. Please
	// do not modify its visibility.
	public ArrayList<Tile> board;
	
	/**
	 * Constructor: create a board of with 'size' tiles
	 * containing Tiles of different colours using the
	 * random number generator.
	 * 
	 * Please DO NOT MODIFY this method
	 * 
	 * You can use this constructor as the basis
	 * for the SameGame constructor.
	 * 
	 * @param rgen - the random number generator (non null)
	 * @param size - the number of tiles on the board 
	 */
	public SimpleSameGame(Random rgen, int size) {
		
		// create an ArrayList of four different Color objects
		// which will be used to populate the board
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.decode(ColorDef.BLUE));
		colors.add(Color.decode(ColorDef.PURPLE));
		colors.add(Color.decode(ColorDef.YELLOW));
		colors.add(Color.decode(ColorDef.GREEN));
		
		// initialise the ArrayList representing the board
		board = new ArrayList<Tile>();
		for(int i = 0; i < size; i++) {
			// get a random number from 0 to colors.size()-1
			int current = rgen.nextInt(colors.size());
			// create a tile with a random colour from the colors array
			// (based on the random number we just generated)
			board.add(new Tile(colors.get(current),1));
		}
	}
	
	/**
	 * 4 marks (Pass level)
	 * 
	 * Return an instance copy of the board. Do not
	 * return a reference copy since the calling method
	 * may modify the returned ArrayList.
	 * 
	 * @return an instance copy of the board
	 */
	public ArrayList<Tile> getBoard() {
		return new ArrayList<>(this.board);
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Check if index i is a valid index, that is, if there
	 * is a tile on that location. Return false if the index
	 * is invalid or if a null value is encountered in that 
	 * index
	 * 
	 * @param i	- the index to check
	 * @return true if index i is a valid location that contains a Tile,
	 * false otherwise
	 */
	public boolean isValidIndex(int i) {
		return (i >= 0 && i < board.size()) ? board.get(i) != null : false;
	}

	
	/**
	 * 5 marks (Pass level) 
	 * 
	 * Checks if it is legal to remove a tile on index i from
	 * the board. Recall that a tile can be removed only if
	 * there is an adjacent tile with the same colour (please
	 * see the assignment specification for more information).
	 * Return false if the location given is invalid.
	 * 
	 * @param i - the index to be checked
	 * @return true if it is legal to remove the tile at index i,
	 * false otherwise
	 */
	public boolean isValidMove(int i) {
		if (isValidIndex(i)) {
			Tile tile = board.get(i);
			return checkAdjacent(tile, i - 1) || checkAdjacent(tile, i + 1);
		}
		return false;
	}
	
	private boolean checkAdjacent(Tile tile, int i) {
		if (isValidIndex(i)) {
			Tile adjTile = board.get(i);
			return tile.equals(adjTile);
		}
		return false;		
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Checks if the player has run out of moves, that is, if it
	 * is not possible to remove any more tiles from the board.
	 * Return true if there are no more tiles on the board.
	 * 
	 * @return true if there are no more valid moves, false otherwise
	 */
	public boolean noMoreValidMoves() {
		for (int i = 1; i < board.size(); i += 2) {
			if (isValidMove(i)) {
				return false;
			}
		}
		return true;
	}
	
	/** 10 marks (Pass level)
	 * 
	 * Remove the tile at index i and all adjacent tiles
	 * of the same colour (as discussed in the assignment
	 * specification).
	 * 
	 * Do nothing if the index i is invalid or if it is
	 * illegal to remove the tile.
	 * @param i
	 */
	public void removeTile(int i) {
		if (!isValidMove(i)) {
			return;
		}
		Tile tile = board.get(i);
		int rightIndex = board.size();
		for (int j = i + 1; j < board.size(); j++) {
			if (!checkAdjacent(tile, j)) {
				rightIndex = j;
				break;
			}
		}
		int leftIndex = 0;
		for (int j = i - 1; j >= 0; j--) {
			if (!checkAdjacent(tile, j)) {
				leftIndex = j + 1;
				break;
			}
		}
		board.subList(leftIndex, rightIndex).clear();
	}
}
