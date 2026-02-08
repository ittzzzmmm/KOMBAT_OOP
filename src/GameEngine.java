import java.util.*;

public final class GameEngine {

    private final GameState state;
    private final GameConfig cfg;
    private final StrategyRunner runner;
    private boolean gameOver = false;


    private final TurnCounter turnCounter = new TurnCounter();
    private final List<minionId> spawnOrder = new ArrayList<>();

    private TurnPhase phase = TurnPhase.P1_BUY_HEX;

    public GameEngine(GameState state, StrategyRunner runner, GameConfig cfg) {
        this.state = Objects.requireNonNull(state);
        this.runner = runner;
        this.cfg = Objects.requireNonNull(cfg);
        startPlayerTurn(PlayerId.P1);
    }

    public boolean isGameOver() { return gameOver; }
    public TurnPhase phase() { return phase; }
    public GameConfig config() { return cfg; }

    // purchase phase

    public boolean buyHex(HexCoord at) {
        requirePhaseBuyHex();
        PlayerId p = currentPlayer();

        if (!state.canBuyHex(p, at)) return false;

        Player pl = state.player(p);
        if (pl.budget() < cfg.hexPurchaseCost()) return false;

        pl.setBudget(pl.budget() - cfg.hexPurchaseCost());

        HexTile t = state.map().get(at);
        t.setOwner(p);
        t.setSpawnable(p, true);

        advanceAfterBuyHex();
        return true;
    }

    public boolean buyMinion(HexCoord at) {
        requirePhaseBuyMinion();
        PlayerId p = currentPlayer();

        if (!state.canSpawnMinion(p, at)) return false;

        Player pl = state.player(p);
        if (pl.budget() < cfg.spawnCost()) return false;

        pl.setBudget(pl.budget() - cfg.spawnCost());
        pl.incSpawnsUsed();

        minionId id = state.spawnMinion(p, at, cfg.initHp(), 0);
        spawnOrder.add(id);

        advanceAfterBuyMinion();
        return true;
    }

    public void skipBuyHex() {
        requirePhaseBuyHex();
        advanceAfterBuyHex();
    }

    public void skipBuyMinion() {
        requirePhaseBuyMinion();
        advanceAfterBuyMinion();
    }

    // strategy phase

    public void runStrategyPhase() {
        if (phase != TurnPhase.RUN_STRATEGY)
            throw new IllegalStateException("Not in strategy phase");

        if (runner != null) {
            for (minionId id : new ArrayList<>(spawnOrder)) {
                Minion self = state.minion(id);
                if (self == null) continue;

                GameContext ctx = new GameContext(state, self, cfg.maxTicks());
                runner.runForMinion(ctx);

                spawnOrder.removeIf(mid -> state.minion(mid) == null);
            }
        }
        if (reachedMaxTurns() || oneSideHasNoMinions()) {
            gameOver = true;
            return;
        }

        phase = TurnPhase.P1_BUY_HEX;
        startPlayerTurn(PlayerId.P1);
    }

    // economy

    private void startPlayerTurn(PlayerId p) {
        turnCounter.inc(p);
        Player pl = state.player(p);

        pl.setBudget(pl.budget() + cfg.turnBudget());

        double m = pl.budget();
        if (m >= 1.0) {
            int t = turnCounter.turnOf(p);
            double r = cfg.interestPct() * Math.log10(m) * Math.log(t);
            pl.setBudget(pl.budget() + (m * r / 100.0));
        }

        if (pl.budget() > cfg.maxBudget()) {
            pl.setBudget(cfg.maxBudget());
        }
    }

    // phase helpers

    private PlayerId currentPlayer() {
        return switch (phase) {
            case P1_BUY_HEX, P1_BUY_MINION -> PlayerId.P1;
            case P2_BUY_HEX, P2_BUY_MINION -> PlayerId.P2;
            default -> throw new IllegalStateException();
        };
    }

    private void advanceAfterBuyHex() {
        phase = (phase == TurnPhase.P1_BUY_HEX)
                ? TurnPhase.P1_BUY_MINION
                : TurnPhase.P2_BUY_MINION;
    }

    private void advanceAfterBuyMinion() {
        if (phase == TurnPhase.P1_BUY_MINION) {
            phase = TurnPhase.P2_BUY_HEX;
            startPlayerTurn(PlayerId.P2);
        } else {
            phase = TurnPhase.RUN_STRATEGY;
        }
    }

    private void requirePhaseBuyHex() {
        if (phase != TurnPhase.P1_BUY_HEX && phase != TurnPhase.P2_BUY_HEX)
            throw new IllegalStateException();
    }

    private void requirePhaseBuyMinion() {
        if (phase != TurnPhase.P1_BUY_MINION && phase != TurnPhase.P2_BUY_MINION)
            throw new IllegalStateException();
    }

    private boolean reachedMaxTurns() {
        return turnCounter.turnOf(PlayerId.P1) >= cfg.maxTurns()
                && turnCounter.turnOf(PlayerId.P2) >= cfg.maxTurns();
    }

    private boolean oneSideHasNoMinions() {
        boolean hasP1 = false;
        boolean hasP2 = false;

        for (Minion m : state.minions().values()) {
            if (m.owner() == PlayerId.P1) hasP1 = true;
            else if (m.owner() == PlayerId.P2) hasP2 = true;
            // เจอครบแล้ว ไม่ต้องวนต่อ
            if (hasP1 && hasP2) break;
        }
        // ถ้าฝ่ายใดฝ่ายหนึ่งไม่มีเลย -> เกมจบ
        return !(hasP1 && hasP2);
    }
}