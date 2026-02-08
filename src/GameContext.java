import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class GameContext implements Context {

    private static final Set<String> READ_ONLY_SPECIALS = Set.of(
            "row", "col", "Budget", "MaxBudget", "SpawnsLeft", "random"
    );

    private final GameState state;
    private final Minion self;
    private final Map<String, Integer> backing = new HashMap<>();

    private boolean terminated = false;

    private final int maxTicks;
    private int ticks = 0;

    public GameContext(GameState state, Minion self) {
        this(state, self, 50_000);
    }

    public GameContext(GameState state, Minion self, int maxTicks) {
        this.state = state;
        this.self = self;
        this.maxTicks = Math.max(1, maxTicks);
    }

    @Override
    public GameState gameState() {
        return state;
    }

    @Override
    public Minion minion() {
        return self;
    }

    @Override
    public Map<String, Integer> variables() {
        // Custom view so AST can do context.variables().get/put
        return new AbstractMap<>() {
            @Override
            public Integer get(Object keyObj) {
                if (!(keyObj instanceof String key)) return null;

                // special vars (computed)
                return switch (key) {
                    case "row" -> self.pos().row;
                    case "col" -> self.pos().col;
                    case "Budget" -> (int) Math.floor(state.player(self.owner()).budget());
                    case "MaxBudget" -> (int) state.maxBudget();
                    case "SpawnsLeft" -> (int) state.spawnsLeft(self.owner());
                    case "random" -> state.player(self.owner()).random0to999();
                    default -> backing.get(key);
                };
            }

            @Override
            public Integer put(String key, Integer value) {
                if (key == null) return null;
                if (READ_ONLY_SPECIALS.contains(key)) {
                    throw new RuntimeException("Cannot assign to read only variable: " + key);
                }
                if (value == null) return backing.remove(key);
                return backing.put(key, value);
            }

            @Override
            public Set<Entry<String, Integer>> entrySet() {
                // Executor/tests usually don't need iteration. Keep it simple.
                return backing.entrySet();
            }
        };
    }

    @Override
    public void terminate() {
        this.terminated = true;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public void tick() {
        ticks++;
        if (ticks > maxTicks) {
            throw new RuntimeException("Strategy exceeded max steps (" + maxTicks + ") - possible infinite loop.");
        }
    }
}
