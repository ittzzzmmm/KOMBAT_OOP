import java.util.Optional;

public final class GameMap {
    private final int width;
    private final int height;
    private final HexTile[][] tiles; // [row][col]

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new HexTile[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                tiles[r][c] = new HexTile(new HexCoord(c, r));
            }
        }
    }

    public int width() { return width; }
    public int height() { return height; }

    public boolean inBounds(HexCoord c) {
        return 0 <= c.col && c.col < width && 0 <= c.row && c.row < height;
    }

    public HexTile get(HexCoord c) {
        if (!inBounds(c)) throw new IllegalArgumentException("Out of bounds: " + c);
        return tiles[c.row][c.col];
    }

    public boolean isOccupied(HexCoord c) {
        return inBounds(c) && get(c).isOccupied();
    }

    public Optional<minionId> occupantAt(HexCoord c) {
        if (!inBounds(c)) return Optional.empty();
        return Optional.ofNullable(get(c).occupant());
    }

    public HexCoord step(HexCoord from, direction dir) {
        return from.neighbor(dir);
    }

    public void moveMinion(minionId id, HexCoord from, HexCoord to) {
        if (!inBounds(from) || !inBounds(to)) throw new IllegalArgumentException("OOB move");
        var a = get(from);
        var b = get(to);
        if (!id.equals(a.occupant())) throw new IllegalStateException("Source not owned by minion");
        if (b.isOccupied()) throw new IllegalStateException("Target occupied");
        a.clearOccupant();
        b.setOccupant(id);
    }

    public void removeMinion(minionId id, HexCoord at) {
        if (!inBounds(at)) return;
        var t = get(at);
        if (id.equals(t.occupant())) t.clearOccupant();
    }

    // encoding
    public int encodeClosestOpponent(GameState s, minionId me) {
        Minion self = s.minion(me);
        if (self == null) return 0;
        return encodeClosestByOwner(s, self, true);
    }

    public int encodeClosestAlly(GameState s, minionId me) {
        Minion self = s.minion(me);
        if (self == null) return 0;
        return encodeClosestByOwner(s, self, false);
    }

    private int encodeClosestByOwner(GameState s, Minion me, boolean wantOpponent) {
        PlayerId target = wantOpponent ? me.owner().opponent() : me.owner();
        int best = 0;
        for (direction dir : direction.values()) {
            int found = scanDirForOwner(s, me.pos(), dir, target);
            if (found == 0) continue;
            if (best == 0 || found < best) best = found;
        }
        return best;
    }

    // return distance*10 + dirCode หรือ 0 ถ้าไม่เจอ
    private int scanDirForOwner(GameState s, HexCoord start, direction dir, PlayerId owner) {
        HexCoord cur = start;
        for (int dist = 1; dist <= 1000; dist++) {
            cur = step(cur, dir);
            if (!inBounds(cur)) return 0;

            Optional<minionId> occ = occupantAt(cur);
            if (occ.isEmpty()) continue;

            Minion m = s.minion(occ.get());
            if (m != null && m.owner() == owner) {
                return dist * 10 + dirCode(dir);
            }
            // blocked by other minion
            return 0;
        }
        return 0;
    }

    /**
     * Encode "nearby" : 100*x + 10*y + z
     * x = digits(hp), y = digits(defense), z = dist*10 + dirCode.
     * Ally -> negative, Opponent -> positive, Nothing -> 0.
     */
    public int encodeNearby(GameState s, minionId me, direction dir) {
        Minion self = s.minion(me);
        if (self == null) return 0;

        HexCoord cur = self.pos();

        for (int dist = 1; dist <= 6; dist++) {
            cur = step(cur, dir);
            if (!inBounds(cur)) return 0;

            Optional<minionId> occ = occupantAt(cur);
            if (occ.isEmpty()) continue;

            Minion other = s.minion(occ.get());
            if (other == null) return 0;

            int z = dist * 10 + dirCode(dir);
            int x = digits(other.hp());
            int y = digits(other.defenseFactor());
            int val = 100 * x + 10 * y + z;

            return (other.owner() == self.owner()) ? -val : val;
        }
        return 0;
    }

    // mapping 1..6 (เลือกแบบง่ายให้คงที่)
    private static int dirCode(direction d) {
        return switch (d) {
            case UP -> 1;
            case UPRIGHT -> 2;
            case DOWNRIGHT -> 3;
            case DOWN -> 4;
            case DOWNLEFT -> 5;
            case UPLEFT -> 6;
        };
    }

    private static int digits(long v) {
        v = Math.abs(v);
        if (v < 10) return 1;
        if (v < 100) return 2;
        if (v < 1000) return 3;
        if (v < 10000) return 4;
        if (v < 100000) return 5;
        if (v < 1000000) return 6;
        if (v < 10000000) return 7;
        if (v < 100000000) return 8;
        if (v < 1000000000L) return 9;
        if (v < 10000000000L) return 10;
        if (v < 100000000000L) return 11;
        if (v < 1000000000000L) return 12;
        if (v < 10000000000000L) return 13;
        if (v < 100000000000000L) return 14;
        if (v < 1000000000000000L) return 15;
        if (v < 10000000000000000L) return 16;
        if (v < 100000000000000000L) return 17;
        if (v < 1000000000000000000L) return 18;
        return 19;
    }
}