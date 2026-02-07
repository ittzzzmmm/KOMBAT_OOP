public final class HexTile {
    private final HexCoord coord;
    private int ownedMinionId; // null = empty

    private boolean spawnableP1;
    private boolean spawnableP2;

    public HexTile(HexCoord coord) {
        this.coord = coord;
    }

    public HexCoord coord() {
        return coord;
    }
    public int ownedBy() {
        return ownedMinionId;
    }
    public void setOwner(int minionId) {
        this.ownedMinionId = minionId;
    }

    public boolean isSpawnable(PlayerId p) {
        return p == PlayerId.P1 ? spawnableP1 : spawnableP2;
    }

    public void setSpawnable(PlayerId p, boolean v) {
        if (p == PlayerId.P1) spawnableP1 = v;
        else spawnableP2 = v;
    }


}
