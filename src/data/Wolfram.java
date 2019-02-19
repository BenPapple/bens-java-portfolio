package data;

import java.util.BitSet;

public class Wolfram {
	private boolean[][] GridWorld;
	private int currentRow;
	private Boolean isWrapEdge;
	private int cellWidth;
	private int cellHeight;
	private Double randomness;
	private BitSet myWolframRules = new BitSet(8);

	/**
	 * Constructor.
	 * 
	 * @param inWidth cell width
	 * @param inHeight cell height
	 * @param inRandomness spawn randomness
	 * @param inIsWrap is wrap-around edge
	 * @param inRules BitSet of Wolfram rules
	 */
	public Wolfram(int inWidth, int inHeight, Double inRandomness, Boolean inIsWrap, BitSet inRules) {
		cellWidth = inWidth;
		cellHeight = inHeight;
		randomness = inRandomness;
		isWrapEdge = inIsWrap;
		myWolframRules = inRules;

		init2DField();

	}

	/**
	 * Calculates next generation of values for all lines except the edge value
	 * on both sides.
	 */
	private void calculateInnerField() {
		for (int x = 1; x < GridWorld.length - 1; x++) {
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = myWolframRules.get(0);
			}
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = myWolframRules.get(1);
			}
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = myWolframRules.get(2);
			}
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = myWolframRules.get(3);
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = myWolframRules.get(4);
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = myWolframRules.get(5);
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = myWolframRules.get(6);
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = myWolframRules.get(7);
			}
		}
		currentRow += 1;
	}

	/**
	 * Fill field with starting values.
	 * 
	 */
	public void init2DField() {

		Double rand;
		currentRow = 1;
		GridWorld = new boolean[cellWidth][cellHeight];

		rand = randomness;

		if (!isWrapEdge) {
			for (int x = 1; x < GridWorld.length - 1; x++) {
				GridWorld[x][0] = Math.random() <= rand;
			}
		} else {
			for (int x = 0; x < GridWorld.length; x++) {
				GridWorld[x][0] = Math.random() <= rand;
			}
		}

	}

	/**
	 * Calculate next iteration of field.
	 * 
	 */
	public void calcNextGenField() {

		// System.out.println(myWolframRules);

		if (isWrapEdge) {

			// Left Edge
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(0);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(1);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(2);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(3);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(4);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(5);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(6);
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) myWolframRules.get(7);
			}

			// Right Edge
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(0);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(1);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(2);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(3);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(4);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(5);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(6);
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = myWolframRules.get(7);
			}

			calculateInnerField();

		} else {
			calculateInnerField();

		}
	}

	/**
	 * Return Wolfram field Gridworld.
	 * 
	 * @return boolean[][] of GridWorld
	 */
	public boolean[][] getGridWorld() {
		return GridWorld;
	}

	/**
	 * Get current row number.
	 * 
	 * @return currentRow
	 */
	public int getCurrentRow() {
		return currentRow;
	}

}
