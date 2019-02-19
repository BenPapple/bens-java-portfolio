package data;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Creates a Nagel-Schreckenberg model from user input as an array.
 * 
 * @author BenGe47
 *
 */
public class NaSch {

	private int[][] GridWorld;
	private int currentRow;
	private int cellWidth;
	private int cellHeight;
	private Double spawnRand;
	private Double brakeRand;

	/**
	 * Constructor.
	 * 
	 * @param inWidth cell width
	 * @param inHeight cell height
	 * @param inSpawnRandomness spawn randomness
	 * @param inBrakeRandomness brake randomness
	 * 
	 */
	public NaSch(int inWidth, int inHeight, Double inSpawnRandomness, Double inBrakeRandomness ) {
		cellWidth = inWidth;
		cellHeight = inHeight;
		spawnRand = inSpawnRandomness;
		brakeRand = inBrakeRandomness;

		init2DField();
	}

	/**
	 * Fill field with starting values.
	 * 
	 */
	private void init2DField() {

		currentRow = 1;
		GridWorld = new int[cellWidth][cellHeight];

		// because ThreadLocalRandom does not return 1.0 make line all cars
		if (spawnRand == 1.0) {

			for (int x = 0; x < GridWorld.length; x++) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
				GridWorld[x][0] = randomNum;
			}
			// init first row with random number of cars and speed
		} else {
			for (int x = 0; x < GridWorld.length; x++) {

				int randomNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);

				if (Math.random() <= spawnRand) {
					GridWorld[x][0] = randomNum;
				} else {
					GridWorld[x][0] = 6;
				}
			}
		}

		// init rest of field with street
		for (int y = 1; y < GridWorld[0].length; y++) {
			for (int x = 0; x < GridWorld.length; x++) {

				GridWorld[x][y] = 6;
			}
		}

	}

	/**
	 * Calculate next iteration of field.
	 * 
	 */
	public void calcNextGenField() {
		int newSpeed;
		int nextCarDistance;

		for (int x = 0; x < GridWorld.length; x++) {
			// for all cars
			if (GridWorld[x][currentRow - 1] <= 5) {
				newSpeed = GridWorld[x][currentRow - 1];
				// add 1 to speed if not already at speed 5
				if (GridWorld[x][currentRow - 1] < 5) {
					newSpeed = GridWorld[x][currentRow - 1] + 1;
				}

				nextCarDistance = 6;

				// calculate distance to next car for next 5 fields
				for (int i = 1; i < 6; i++) {
					// consider world wrap around
					int newX = ((x + i) % (GridWorld.length));

					if (GridWorld[newX][currentRow - 1] < 6) {
						nextCarDistance = i - 1;
						break;
					}

				}

				// final speed of new car
				newSpeed = (newSpeed >= nextCarDistance) ? nextCarDistance : newSpeed;

				// randomly brake or not brake each car
				if ((Math.random() <= brakeRand) && (newSpeed > 0)) {
					newSpeed -= 1;
				}
				// set speed of car in new row
				GridWorld[Math.abs((x + newSpeed) % (GridWorld.length))][currentRow] = newSpeed;

			}
		}

		currentRow += 1;
	}
	
	/**
	 * Return NaSch field Gridworld.
	 * 
	 * @return boolean[][] of GridWorld
	 */
	public int[][] getGridWorld() {
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
