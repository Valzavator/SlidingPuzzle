package game.enumeration;

public enum Difficulty {
    Easy(3, 3), Hard(4, 4);

    private int numberOfColumns;
    private int numberOfRows;

    Difficulty(int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfCells() {
        return this.numberOfColumns * this.numberOfRows;
    }

    public int getRows() {
        return this.numberOfRows;
    }

    public int getColumns() {
        return this.numberOfColumns;
    }
}
