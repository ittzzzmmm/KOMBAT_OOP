import java.util.*;

public final class GameState {

    public enum ApplyResult { APPLIED, NOOP, TERMINATE }

    private final GameMap map;
    private final Map<PlayerId, Player> players;
    private final Map<minionId, Minion> minions;

    //from config
    private final long maxBudget;
    private final long maxSpawns;

    //constructors

    public GameState(GameMap map,
                     Map<PlayerId, Player> players,
                     Map<minionId, Minion> minions,
                     GameConfig cfg) {
        Objects.requireNonNull(cfg, "GameConfig cannot be null");
        this.map = Objects.requireNonNull(map);
        this.players = Objects.requireNonNull(players);
        this.minions = Objects.requireNonNull(minions);
        this.maxBudget = cfg.maxBudget();
        this.maxSpawns = cfg.maxSpawns();
    }

    public GameMap map() { return map; }
    public Map<PlayerId, Player> players() { return players; }
    public Map<minionId, Minion> minions() { return minions; }

    public Player player(PlayerId p) { return players.get(p); }
    public Minion minion(minionId id) { return minions.get(id); }

    public long maxBudget() { return maxBudget; }
    public long maxSpawns() { return maxSpawns; }

    public long spawnsLeft(PlayerId p) {
        return Math.max(0, maxSpawns - player(p).spawnsUsed());
    }

    // rule checks
    //ซื้อ hex ได้มั้ย
    public boolean canBuyHex(PlayerId p, HexCoord at) {
        if (!map.inBounds(at)) return false;

        HexTile t = map.get(at);
        if (t.isOwned()) return false;

        // seed hex
        if (!hasAnyOwnedHex(p)) {
            return t.isSpawnable(p);
        }

        // adjacency
        for (direction d : direction.values()) {
            HexCoord nb = at.neighbor(d);
            if (map.inBounds(nb) && map.get(nb).isOwnedBy(p)) return true;
        }
        return false;
    }

    //วาง minion ได้ไหม
    public boolean canSpawnMinion(PlayerId p, HexCoord at) {
        if (!map.inBounds(at)) return false;
        HexTile t = map.get(at);
        if (!t.isOwnedBy(p)) return false;
        if (t.isOccupied()) return false;
        return spawnsLeft(p) > 0;
    }

    private boolean hasAnyOwnedHex(PlayerId p) {
        for (int r = 0; r < map.height(); r++) {
            for (int c = 0; c < map.width(); c++) {
                if (map.get(new HexCoord(c, r)).isOwnedBy(p)) return true;
            }
        }
        return false;
    }

    //minion factory
    public minionId spawnMinion(PlayerId owner, HexCoord at, int hp, int defense) {
        if (!map.inBounds(at)) throw new IllegalArgumentException("spawn out of bounds");
        if (map.isOccupied(at)) throw new IllegalStateException("spawn on occupied tile");

        minionId id = new minionId(nextMinionIdValue());
        Minion m = new Minion(id, owner, hp, defense, at);

        minions.put(id, m);
        map.get(at).setOccupant(id);
        return id;
    }

    private long nextMinionIdValue() {
        long max = 0;
        for (minionId id : minions.keySet()) {
            if (id.v() > max) max = id.v();
        }
        return max + 1;
    }

    // strategy apply
    public ApplyResult tryApply(PlayerId actor, Action action) {

        if (action instanceof DoneAction) return ApplyResult.TERMINATE;

        if (action instanceof MoveAction mv) {
            if (!owns(actor, mv.MinId())) return ApplyResult.NOOP;
            return applyMove(actor, mv);
        }

        if (action instanceof ShootAction sh) {
            if (!owns(actor, sh.MinId())) return ApplyResult.NOOP;
            return applyShoot(actor, sh);
        }

        return ApplyResult.NOOP;
    }

    private boolean owns(PlayerId p, minionId id) {
        Minion m = minion(id);
        return m != null && m.owner() == p;
    }

    private ApplyResult applyMove(PlayerId p, MoveAction mv) {
        Player pl = player(p);
        if (pl.budget() < 1.0) return ApplyResult.TERMINATE;

        pl.setBudget(pl.budget() - 1.0);

        Minion m = minion(mv.MinId());
        HexCoord target = map.step(m.pos(), mv.dir());

        if (!map.inBounds(target)) return ApplyResult.NOOP;
        if (map.isOccupied(target)) return ApplyResult.NOOP;

        map.moveMinion(mv.MinId(), m.pos(), target);
        m.setPos(target);
        return ApplyResult.APPLIED;
    }

    private ApplyResult applyShoot(PlayerId p, ShootAction sh) {
        Player pl = player(p);
        double cost = sh.costfor() + 1.0;
        if (pl.budget() < cost) return ApplyResult.NOOP;

        pl.setBudget(pl.budget() - cost);

        Minion attacker = minion(sh.MinId());
        HexCoord target = map.step(attacker.pos(), sh.dir());

        if (!map.inBounds(target)) return ApplyResult.APPLIED;
        Optional<minionId> victimId = map.occupantAt(target);
        if (victimId.isEmpty()) return ApplyResult.APPLIED;

        Minion victim = minion(victimId.get());
        long dmg = Math.max(1, sh.costfor() - victim.defenseFactor());
        victim.setHp(Math.max(0, victim.hp() - (int) dmg));

        if (victim.hp() <= 0) {
            map.removeMinion(victimId.get(), victim.pos());
            minions.remove(victimId.get());
        }

        return ApplyResult.APPLIED;
    }
}