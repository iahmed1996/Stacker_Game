package sameGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import graphics.ColorDef;

public class SameGame {

	
	// the board variable needs to be public so that
	// it can be accessed by the JUnit test. Please
	// do not modify its visibility.
	
	public ArrayList<ArrayList<Tile>> board;
	
	// counts the number of total tile positions for scoring purposes
	private int totalPositions = 0;

	/**
	 * 5 marks (Pass level)
	 * 
	 * Constructor: create a board of size rows x columns
	 * containing Tiles of different colours using the 
	 * random number generator as discussed in the assignment 
	 * specification.
	 * 
	 * Both rows and columns are going to be an integer > 0 and <= 1000.
	 * You can assume that rgen is going to be a valid Random object 
	 * (rgen won't be null)
	 * 
	 * You can look at the constructor for SimpleSameGame to get
	 * some ideas. To construct a Tile with a random colour, you
	 * can use.
	 * 
	 * Tile random = new Tile(colors.get(rgen.nextInt(colors.size())),1);
	 * 
	 * Remember that row 0 is the bottom-most row. 
	 * 
	 * @param rgen	  - the random number generator (non null)
	 * @param rows    - the number of rows on the board, 0 < rows <= 1000
	 * @param columns - the number of columns on the board, 0 < columns <= 1000
	 */
	public SameGame(Random rgen, int rows, int columns) {
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.decode(ColorDef.BLUE));
		colors.add(Color.decode(ColorDef.PURPLE));
		colors.add(Color.decode(ColorDef.YELLOW));
		colors.add(Color.decode(ColorDef.GREEN));

		board = new ArrayList<>(rows);
		for (int i = 0; i < rows; i++) {
			board.add(new ArrayList<>(columns));
			for (int j = 0; j < columns; j++) {
				board.get(i).add(
					new Tile(colors.get(rgen.nextInt(colors.size())),1));
					totalPositions++;
			}
		}
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Computes the score obtained by the user at the end of the
	 * game. The score for the game is the number of tiles that 
	 * the player manages to remove, so if the player removes 10
	 * tiles, then the player should receive a score of 10.
	 * 
	 * The ideal way to complete this method is by completing the 
	 * removeTile() method (e.g. by adding a point for each tile removed),
	 * but you should be able to obtain full marks even if you cannot
	 * implement removeTile() (see the JUnit test)
	 * 
	 * @return the score obtained by the user
	 */
	
	// Takes totalPositions and subtracts 1 for every non null in board
	public int calculateScore() {
		int nullCount = 0;
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).size(); j++) {
				if (board.get(i).get(j) != null) {
					nullCount--;
				}
			}
		}
		return totalPositions + nullCount;
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Returns an instance copy of the board. Do not return a reference
	 * copy since the calling method may modify the returned ArrayList.
	 * 
	 * @return an instance copy of the board
	 */
	public ArrayList<ArrayList<Tile>> getBoard() {
		ArrayList<ArrayList<Tile>> newboard = new ArrayList<>(board.size());
		for (int i = 0; i < board.size(); i++) {
			newboard.add(new ArrayList<>(board.get(i)));
		}
		return newboard;
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Checks if row i column j is a valid index, that is, if there is a
	 * tile on that location. Return false if i and j does not
	 * correspond to a valid index, or if a null value is found at
	 * that location.
	 * 
	 * @param i - the row index
	 * @param j - the column index
	 * @return true if row i column j is a valid location that contains a Tile, 
	 * false otherwise
	 */
	public boolean isValidIndex(int i, int j) {
		return getTile(i, j) != null;
	}
	
	// Returns null if either index is out of bounds or the Tile is null, else returns the Tile.
	private Tile getTile(int i, int j) {
		if (i < 0 || i >= board.size()) {
			return null;
		}
		ArrayList<Tile> column = board.get(i);
		if (j < 0 || j >= column.size()) {
			return null;
		}
		return column.get(j);
	}

	
	/**
	 * 10 marks (Pass level)
	 * 
	 * Checks if it is legal to remove a tile on row i column j. A tile
	 * can be removed if there is an adjacent tile with the same colour
	 * (please see the assignment specification for more information). 
	 * Return false if the location given is invalid. 
	 * 
	 * @param i - the row index
	 * @param j - the column index
	 * @return true if it is legal to remove the tile at row i column j, 
	 * false otherwise
	 */
	public boolean isValidMove(int i, int j) {
		if (isValidIndex(i, j)) {
			Tile tile = getTile(i, j);
			return compareTiles(tile, i - 1, j) 
				|| compareTiles(tile, i + 1, j) 
				|| compareTiles(tile, i, j - 1) 
				|| compareTiles(tile, i, j + 1);
		}
		return false;
	}	
	
	// Returns true if the colour of the passed Tile matches the colour of the Tile 
	// at the passed index parameters, false otherwise. 
	private boolean compareTiles(Tile tile, int i, int j) {
		Tile compare = getTile(i, j);
		return tile.equals(compare);
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Checks if the player has run out of moves, that is, if it is not possible
	 * to remove any more tiles from the board. Return true if there are no more
	 * tiles on the board.
	 * 
	 * @return true if there are no more valid moves, false otherwise
	 */
	public boolean noMoreValidMoves() {
		for (int i = 0; i < board.size(); i++) {
			ArrayList<Tile> current = board.get(i);
			for (int j = 0; j < current.size(); j++) {
				if (isValidMove(i, j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 5 marks (Credit Level)
	 * 
	 * Imagine two rows of Tiles, 'bottom' and 'top', with 'top' placed on top of 'bottom'.
	 * 
	 * This method rearranges the two rows such that if there is an empty (i.e. null)
	 * slot in the 'bottom' row and there is a tile at the same index in the 'top' row, 
	 * then it will move the tile down to 'bottom' row (effectively you are copying
	 * the tile into 'bottom', and set the index to null in 'top').
	 * You are basically moving tiles downward, but only between two rows.
	 * 
	 * Don't do anything if the sizes of the two ArrayLists are not the same,
	 * or if one of them is null
	 * 
	 * @param bottom - the bottom row of Tiles
	 * @param top    - the top row of Tiles
	 */
	public void trickleDown(ArrayList<Tile> bottom, ArrayList<Tile> top) {
		if (bottom == null || top == null) {
			return;
		}
		for (int i = 0; i < bottom.size(); i++) {
			if (bottom.get(i) == null) {
				bottom.set(i, top.get(i));
				top.set(i, null);
			}
		}
	}
	
	
	/** 5 marks (Credit Level)
	 * 
     * Rearranges the board so that all tiles are moved downwards and all empty columns are
     * removed. 
     * 
     * You do not need to complete the deleteEmptyColumns() method to receive full
     * marks for this method as it will not be tested in the JUnit test for this method,
     * that is, the JUnit test for rearrangeBoard() will NOT test if your implementation
     * of rearrangeBoard() removes the empty columns.
     * 
     * However, a proper implementation of rearrangeBoard() SHOULD call deleteEmptyColumns()
     * (after you move the tiles downwards), so if you manage to implement 
     * deleteEmptyColumns(), you need to call that method in rearrangeBoard().
     * 
     * Hint: use the trickleDown() method (although you can also do it without
     * calling trickleDown(), but calling trickleDown() will make the code simpler,
     * albeit less efficient).
	 * 
	 * @param board
	 */
	public void rearrangeBoard() {
		for (int i = 0; i < board.size(); i++) {
			rearrangeColumn(i);
		}
		deleteEmptyColumns();
	}
	
	// Rearranges the items of each ArrayList in board (a column) at the given index
	private void rearrangeColumn(int j) {
		int to = firstNull(j);
		int from = to + 1;
		while (from < board.size()) {
			while (from < board.size() && getTile(from, j) == null) {
				from++;
			}
			if (from < board.size()) {
				setTile(to, j, getTile(from, j));
				setTile(from, j, null);
				to++;
				from++;
			}
		}
	}
	
	// Finds the first null in a column
	private int firstNull(int j) {
		for (int i = 0; i < board.size(); i++) {
			if (getTile(i, j) == null) {
				return i;
			}
		}
		return board.size();
	}
	
	// Sets a Tile in a column to the given value
	private void setTile(int i, int j, Tile val) {
		board.get(i).set(j, val);
	}
	
	/** 10 marks (Distinction Level)
	 * 
     * The following method removes all empty columns in the board. 
     * You MUST PASS testRearrangeBoard() before attempting deleteEmptyColumns, 
     * because the JUnit test will NOT directly test deleteEmptyColumns().
     * The JUnit test will call rearrangeBoard(), assuming that you have
     * implemented deleteEmptyColumns() properly and call it from rearrangeBoard().
	 * 
	 */
	
	public void deleteEmptyColumns() {	
		ArrayList<Tile> bottom = board.get(0);
		for (int j = 0; j < bottom.size(); j++) {
			if (bottom.get(j) == null) {
				for (int i = 0; i < board.size(); i++) {
					board.get(i).remove(j);
					totalPositions++;
				}
				j--;
			}
		}
	}
	
	/** 16 marks (High Distinction Level)
	 * 
	 * The following method removes a tile at row i column j from the board
	 * and all adjacent tiles of the same colour, as per the game rules
	 * (as discussed in the assignment specification).
	 * 
	 * The JUnit test suite will include one test that tries a large board
	 * (1000 x 1000) --- see testRemoveTileLarge. The removeTile method
	 * must finish within 10 seconds for you to pass this test (it is
	 * worth 5 marks)
	 * 
	 * Do nothing if the given row and column index is not valid, or if it
	 * is not legal to move the tile. 
	 * 
	 * @param i  -  the row index of the tile to be removed
	 * @param j  -  the column index of the tile to be removed
	 */
	public void removeTile(int i, int j) {
		if (!isValidMove(i, j)) {
			return;
		}
		ArrayList<Job> jobs = new ArrayList<>();
		jobs.add(new Job(i, j));
		Tile tile = getTile(i, j);
		setTile(i, j, null);
		while (!jobs.isEmpty()) {
			Job job = jobs.remove(jobs.size() - 1);
			int c = job.column;
			int r = job.row;
			remover(jobs, tile, c - 1, r);
			remover(jobs, tile, c + 1, r);
			remover(jobs, tile, c, r - 1);
			remover(jobs, tile, c, r + 1);
		}
		rearrangeBoard();
	}
	
	// Stores the coordinates of Tiles for removal
	private void remover(ArrayList<Job> jobs, Tile tile, int c, int r) {
		if  (compareTiles(tile, c, r)) {
			jobs.add(new Job(c, r));
			setTile(c, r, null);
		}
	}
	
	// Helper class for logging coordinates of Tiles
	private static class Job {
		final int column;
		final int row;
		
		Job(int c, int r) {
			column = c;
			row = r;
		}	
	}
}

