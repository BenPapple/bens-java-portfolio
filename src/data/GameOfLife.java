package data;

public class GameOfLife {

	private boolean[][] GridWorld;
	private boolean[][] CalcGridWorld;
	private Boolean isWrapEdge;
	int cellWidth;
	int cellHeight;
	Double randomness;

	/**
	 * Constructor.
	 * 
	 * @param inWidth cell width
	 * @param inHeight cell height
	 * @param inRandomness spawn randomness
	 * @param inIsWrap is wrap-around edge
	 */
	public GameOfLife(int inWidth, int inHeight, Double inRandomness, Boolean inIsWrap) {
		cellWidth = inWidth;
		cellHeight = inHeight;
		randomness = inRandomness;
		isWrapEdge = inIsWrap;

		init2DField();

	}

	/**
	 * Return GOL field Gridworld.
	 * 
	 * @return boolean[][] of GridWorld
	 */
	public boolean[][] getGridWorld() {
		return GridWorld;
	}

	/**
	 * Init field with starting values.
	 * 
	 */
	public void init2DField() {

		Double rand;
		GridWorld = new boolean[cellWidth][cellHeight];

		rand = randomness;

		if (!isWrapEdge) {

			for (int y = 1; y < GridWorld[0].length - 1; y++) {
				for (int x = 1; x < GridWorld.length - 1; x++) {
					GridWorld[x][y] = Math.random() <= rand;
				}
			}
		} else {
			for (int i = 0; i < GridWorld[0].length; i++) {
				for (boolean[] GridWesen1 : GridWorld) {
					GridWesen1[i] = Math.random() <= rand;
				}
			}
		}
	}

	/**
	 * Calculate next iteration of field with Game of Life rules.
	 * 
	 */
	public void calcNextGenField() {
		int counter;
		CalcGridWorld = new boolean[GridWorld.length][GridWorld[0].length];

		if (isWrapEdge) {

			// Calculate Edge Cases
			// corners right top
			counter = 0;
			CalcGridWorld[GridWorld.length - 1][0] = false;
			if (CalcGridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][0]) {
				counter++;
			}
			setNewCellStatus(counter, GridWorld.length - 1, 0);

			// corners right bottom
			counter = 0;
			CalcGridWorld[GridWorld.length - 1][GridWorld[0].length - 1] = false;
			if (CalcGridWorld[0][0]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][0]) {
				counter++;
			}
			setNewCellStatus(counter, GridWorld.length - 1, GridWorld[0].length - 1);

			// corners left bottom
			counter = 0;
			CalcGridWorld[0][GridWorld[0].length - 1] = false;
			if (CalcGridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			if (GridWorld[1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[1][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[0][1]) {
				counter++;
			}
			if (GridWorld[0][2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 1]) {
				counter++;
			}
			setNewCellStatus(counter, 0, GridWorld[0].length - 1);

			// corners left top
			counter = 0;
			CalcGridWorld[0][0] = false;
			if (CalcGridWorld[GridWorld.length - 1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[1][1]) {
				counter++;
			}
			if (GridWorld[0][1]) {
				counter++;
			}
			if (GridWorld[1][0]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			setNewCellStatus(counter, 0, 0);

			// top edge
			for (int x = 1; x < GridWorld.length - 1; x++) {
				counter = 0;
				CalcGridWorld[x][0] = false;
				if (GridWorld[x - 1][1]) {
					counter++;
				}
				if (GridWorld[x][1]) {
					counter++;
				}
				if (GridWorld[x + 1][1]) {
					counter++;
				}
				if (GridWorld[x - 1][0]) {
					counter++;
				}
				if (GridWorld[x + 1][0]) {
					counter++;
				}
				if (GridWorld[x - 1][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x + 1][GridWorld[0].length - 1]) {
					counter++;
				}
				setNewCellStatus(counter, x, 0);
			}

			// bottom edge
			for (int x = 1; x < GridWorld.length - 1; x++) {
				counter = 0;
				CalcGridWorld[x][GridWorld[0].length - 1] = false;
				if (GridWorld[x - 1][0]) {
					counter++;
				}
				if (GridWorld[x][0]) {
					counter++;
				}
				if (GridWorld[x + 1][0]) {
					counter++;
				}
				if (GridWorld[x - 1][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x + 1][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x - 1][GridWorld[0].length - 2]) {
					counter++;
				}
				if (GridWorld[x][GridWorld[0].length - 2]) {
					counter++;
				}
				if (GridWorld[x + 1][GridWorld[0].length - 2]) {
					counter++;
				}
				setNewCellStatus(counter, x, GridWorld[0].length - 1);
			}

			// right side
			for (int y = 1; y < GridWorld[0].length - 1; y++) {
				counter = 0;
				CalcGridWorld[GridWorld.length - 1][y] = false;
				if (GridWorld[0][y - 1]) {
					counter++;
				}
				if (GridWorld[0][y]) {
					counter++;
				}
				if (GridWorld[0][y + 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y - 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y + 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 2][y - 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 2][y]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 2][y + 1]) {
					counter++;
				}
				setNewCellStatus(counter, GridWorld.length - 1, y);
			}

			// left side
			for (int y = 1; y < GridWorld[0].length - 1; y++) {
				counter = 0;
				CalcGridWorld[0][y] = false;
				if (GridWorld[GridWorld.length - 1][y - 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y + 1]) {
					counter++;
				}
				if (GridWorld[0][y - 1]) {
					counter++;
				}
				if (GridWorld[0][y + 1]) {
					counter++;
				}
				if (GridWorld[0 + 1][y - 1]) {
					counter++;
				}
				if (GridWorld[0 + 1][y]) {
					counter++;
				}
				if (GridWorld[0 + 1][y + 1]) {
					counter++;
				}
				setNewCellStatus(counter, 0, y);
			}

		}

		// Calculate Inner Field
		for (int y = 1; y < GridWorld[0].length - 1; y++) {
			for (int x = 1; x < GridWorld.length - 1; x++) {
				counter = 0;
				CalcGridWorld[x][y] = false;
				if (GridWorld[x - 1][y - 1]) {
					counter++;
				}
				if (GridWorld[x - 1][y]) {
					counter++;
				}
				if (GridWorld[x - 1][y + 1]) {
					counter++;
				}
				if (GridWorld[x][y - 1]) {
					counter++;
				}
				if (GridWorld[x][y + 1]) {
					counter++;
				}
				if (GridWorld[x + 1][y - 1]) {
					counter++;
				}
				if (GridWorld[x + 1][y]) {
					counter++;
				}
				if (GridWorld[x + 1][y + 1]) {
					counter++;
				}

				setNewCellStatus(counter, x, y);
			}
		}

		copyGridworld();
	}

	/**
	 * Calculates dead/alive by comparing living neighbours with target cell
	 * status.
	 *
	 * @param counter Number of living neighbours
	 * @param inX inX coordinate pixel
	 * @param inY inY coordinate pixel
	 */
	private void setNewCellStatus(int counter, int inX, int inY) {
		if (counter > 3 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = false;
		}
		if (counter == 2 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = true;
		}
		if (counter == 3 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = true;
		}
		if (counter < 2 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = false;
		}
		if (counter == 3 && !(GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = true;
		}
	}

	/**
	 * Copies array CalcGridWorld with new results into old array Gridworld.
	 *
	 */
	private void copyGridworld() {
		for (int y = 0; y < GridWorld[0].length; y++) {
			for (int x = 0; x < GridWorld.length; x++) {
				GridWorld[x][y] = CalcGridWorld[x][y];
			}
		}
	}

}
