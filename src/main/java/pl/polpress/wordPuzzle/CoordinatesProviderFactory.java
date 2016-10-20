package pl.polpress.wordPuzzle;

public class CoordinatesProviderFactory {
    public static Coordinates createProviderDependsOnWordDirection(final int initialWordCoordinateY, final int initialWordCoordinateX, Direction direction) {
        switch (direction) {
            case SOUTH:
                return createProviderForSouth(initialWordCoordinateY, initialWordCoordinateX);
            case SOUTHEAST:
                return createProviderForSoutheast(initialWordCoordinateY, initialWordCoordinateX);
            case EAST:
                return createProviderForEast(initialWordCoordinateY, initialWordCoordinateX);
            case NORTHEAST:
                return createProviderForNortheast(initialWordCoordinateY, initialWordCoordinateX);
            case NORTH:
                return createProviderForNorth(initialWordCoordinateY, initialWordCoordinateX);
            case NORTHWEST:
                return createProviderForNorthwest(initialWordCoordinateY, initialWordCoordinateX);
            case WEST:
                return createProviderForWest(initialWordCoordinateY, initialWordCoordinateX);
            case SOUTHWEST:
                return createProviderForSouthwest(initialWordCoordinateY, initialWordCoordinateX);
        }
        return null;
    }

    private static Coordinates createProviderForSouth(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY + currentPositionInWord;
            }
        };
    }

    private static Coordinates createProviderForSoutheast(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
           @Override
            public int getX(int currentPositionInWord) {
               return initialWordCoordinateX + currentPositionInWord;
           }

           @Override
            public int getY(int currentPositionInWord) {
               return initialWordCoordinateY + currentPositionInWord;
           }
        };
    }


    private static Coordinates createProviderForEast(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX + currentPositionInWord;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY;
            }
        };
    }

    private static Coordinates createProviderForNortheast(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX + currentPositionInWord;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY - currentPositionInWord;
            }
        };
    }

    private static Coordinates createProviderForNorth(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY - currentPositionInWord;
            }
        };
    }

    private static Coordinates createProviderForNorthwest(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX - currentPositionInWord;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY - currentPositionInWord;
            }
        };
    }

    private static Coordinates createProviderForWest(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX - currentPositionInWord;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY;
            }
        };
    }

    private static Coordinates createProviderForSouthwest(final int initialWordCoordinateY, final int initialWordCoordinateX) {
        return new Coordinates() {
            @Override
            public int getX(int currentPositionInWord) {
                return initialWordCoordinateX - currentPositionInWord;
            }

            @Override
            public int getY(int currentPositionInWord) {
                return initialWordCoordinateY + currentPositionInWord;
            }
        };
    }
}