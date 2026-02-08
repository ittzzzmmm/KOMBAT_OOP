import java.util.Map;

public interface Context {
    GameState gameState();
    Minion minion();

    /**
     * Strategy-visible variables map.
     * - Normal identifiers are stored here.
     * - Special read-only variables (row/col/Budget/MaxBudget/SpawnsLeft/random) are computed on get().
     */
    Map<String, Integer> variables();

    /** Optional: executor can call to stop current script. */
    default void terminate() {}
    default boolean isTerminated() { return false; }

    /** Optional: executor can call each executed statement to prevent infinite loops. */
    default void tick() {}
}