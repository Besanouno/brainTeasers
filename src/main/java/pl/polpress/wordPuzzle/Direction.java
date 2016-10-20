package pl.polpress.wordPuzzle;

import java.util.Random;

public enum Direction {
	WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH, NORTHWEST;

	public static Direction getRandom() {
		int directionIndex = new Random().nextInt(8);
		switch (directionIndex) {
		case 0:
			return WEST;
		case 1:
			return SOUTHWEST;
		case 2:
			return SOUTH;
		case 3:
			return SOUTHEAST;
		case 4:
			return EAST;
		case 5:
			return NORTHEAST;
		case 6:
			return NORTH;
		case 7:
			return NORTHWEST;
		default:
			return EAST;
		}
	}
}
