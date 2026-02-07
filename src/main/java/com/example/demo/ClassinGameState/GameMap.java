public final class GameMap {
    private final int width;
    private final int height;
    private final HexTile[][] tiles; // [r][q]

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new HexTile[height][width];
        for (int r = 0; r < height; r++) {
            for (int q = 0; q < width; q++) {
                tiles[r][q] = new HexTile(new HexCoord(q, r));
            }
        }
    }

    public boolean inBounds(HexCoord c) {
        return 0 <= c.col && c.col < width && 0 <= c.row && c.row < height;
    }

    public HexTile get(HexCoord c) {
        if (!inBounds(c)) throw new IllegalArgumentException("Out of bounds: " + c);
        return tiles[c.row][c.col];
    }

    public int width() {
        return width;
    }
    public int height() {
        return height;
    }

}
