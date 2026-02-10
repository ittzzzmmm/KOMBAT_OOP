public final class HexCoord {
    public final int col;
    public final int row;

    public HexCoord(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public HexCoord neighbor(direction d) {
        boolean oddCol = (col & 1) == 1;
        return switch (d) {
            case UP -> new HexCoord(col, row - 1);
            case DOWN -> new HexCoord(col, row + 1);
            case UPLEFT -> new HexCoord(col - 1, row - (oddCol ? 0 : 1));
            case UPRIGHT -> new HexCoord(col + 1, row - (oddCol ? 0 : 1));
            case DOWNLEFT -> new HexCoord(col - 1, row + (oddCol ? 1 : 0));
            case DOWNRIGHT -> new HexCoord(col + 1, row + (oddCol ? 1 : 0));
        };
    }

    @Override
    public String toString() {
        return "(" + col + "," + row + ")";
    }
}
