public final class HexCoord {
    public final int col;
    public final int row;


    public HexCoord(int q, int r) {
        this.col = q;
        this.row = r;
    }

    public HexCoord neighbor(direction d) {
        return switch (d) {
            case E  -> new HexCoord(col + 1, row);
            case W  -> new HexCoord(col - 1, row);
            case NE -> new HexCoord(col + 1, row - 1);
            case NW -> new HexCoord(col, row - 1);
            case SE -> new HexCoord(col, row + 1);
            case SW -> new HexCoord(col - 1, row + 1);
        };
    }

}
