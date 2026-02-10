import java.util.*;

public final class GameSetup {

    private GameSetup() {}

    public static GameEngine newGame(GameMap map, StrategyRunner runner, GameConfig cfg) {

        Objects.requireNonNull(map);
        Objects.requireNonNull(cfg);

        //players
        Map<PlayerId, Player> players = new EnumMap<>(PlayerId.class);
        players.put(PlayerId.P1, new Player(cfg.initBudget(), 1));
        players.put(PlayerId.P2, new Player(cfg.initBudget(), 2));

        // minions
        Map<minionId, Minion> minions = new HashMap<>();

        //set zones
        setDefultZones(map);

        // state + engine
        GameState state = new GameState(map, players, minions, cfg);
        return new GameEngine(state, runner, cfg);
    }

    private static void setDefultZones(GameMap map) {

        // ---- Player 1 (green) ----
        setZone(map, PlayerId.P1,
                new HexCoord(1, 1),
                new HexCoord(1, 2),
                new HexCoord(2, 1),
                new HexCoord(2, 2),
                new HexCoord(3, 1)
        );

        // ---- Player 2 (red) ----
        setZone(map, PlayerId.P2,
                new HexCoord(6, 8),
                new HexCoord(7, 7),
                new HexCoord(7, 8),
                new HexCoord(8, 7),
                new HexCoord(8, 8)
        );
    }

    private static void setZone(GameMap map, PlayerId p, HexCoord... coords) {
        for (HexCoord hc : coords) {
            if (!map.inBounds(hc)) {
                throw new IllegalArgumentException(
                        "Hex out of bounds for " + p + ": " + hc
                );
            }
            map.get(hc).setSpawnable(p, true);
        }
    }
}