import java.util.Map;
import java.util.Objects;

public final class GameContext {

    private final GameState state;
    private final Minion self;

    public GameContext(GameState state, Minion self) {
        this.state = Objects.requireNonNull(state);
        this.self = Objects.requireNonNull(self);
    }

    public GameState state() { return state; }
    public Minion self() { return self; }

    public PlayerId owner() { return self.owner(); }

    public long getVar(String name) {
        if (name == null || name.isBlank()) return 0L;

        Long special = readSpecial(name);
        if (special != null) return special;

        if (isGlobalName(name)) {
            return player().globalVars().getOrDefault(name, 0L);
        } else {
            return localVars().getOrDefault(name, 0L);
        }
    }

    public void setVar(String name, long value) {
        if (name == null || name.isBlank()) return;
        if (isReadOnly(name)) return;

        if (isGlobalName(name)) {
            player().globalVars().put(name, value);
        } else {
            localVars().put(name, value);
        }
    }

    private boolean isReadOnly(String name) {
        return switch (name) {
            case "row", "col",
                 "budget", "maxBudget", "spawnsLeft",
                 "opponent", "ally",
                 "random",
                 "nearby" -> true;
            default -> false;
        };
    }

    private Long readSpecial(String name) {
        return switch (name) {
            case "row" -> (long) self.pos().row;
            case "col" -> (long) self.pos().col;
            case "budget" -> (long) Math.floor(player().budget());
            case "maxBudget" -> state.maxBudget();
            case "spawnsLeft" -> state.spawnsLeft(owner());
            case "opponent" -> (long) state.map().encodeClosestOpponent(state, self.id());
            case "ally" -> (long) state.map().encodeClosestAlly(state, self.id());
            case "random" -> (long) player().random0to999();
            case "nearby" -> 0L;

            default -> null;
        };
    }

    public long nearby(direction dir) {
        return state.map().encodeNearby(state, self.id(), dir);
    }

    private Player player() {
        return state.player(owner());
    }

    private Map<String, Long> localVars() {
        return self.localVars();
    }

    private static boolean isGlobalName(String name) {
        return Character.isUpperCase(name.charAt(0));
    }
}