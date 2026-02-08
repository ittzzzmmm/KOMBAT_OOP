public final class HexTile {
    private final HexCoord coord;
    private minionId ownedMinionId;

    private boolean spawnableP1;
    private boolean spawnableP2;

    // owner ของ hex (null = ยังไม่ถูกซื้อ)
    private PlayerId owner;

    public HexTile(HexCoord coord) {
        this.coord = coord;
    }

    public HexCoord coord() { return coord; }

    public minionId occupant() { return ownedMinionId; }

    public boolean isOccupied() { return ownedMinionId != null; }

    public void setOccupant(minionId id) { this.ownedMinionId = id; }

    public void clearOccupant() { this.ownedMinionId = null; }

    public boolean isSpawnable(PlayerId p) {
        return p == PlayerId.P1 ? spawnableP1 : spawnableP2;
    }

    public void setSpawnable(PlayerId p, boolean v) {
        if (p == PlayerId.P1) spawnableP1 = v;
        else spawnableP2 = v;
    }

    public PlayerId owner() { return owner; }

    public boolean isOwned() { return owner != null; }

    public boolean isOwnedBy(PlayerId p) { return owner == p; }

    public void setOwner(PlayerId p) {
        if (owner != null) throw new IllegalStateException("Hex already owned");
        owner = p;
    }
}