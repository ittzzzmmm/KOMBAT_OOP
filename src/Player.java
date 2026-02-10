import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class Player {
    private double budget;
    private long spawnsUsed;

    private final Map<String, Long> globalVars = new HashMap<>();
    private final Random rng;

    public Player(double budget, PlayerId id) {
        this(budget, seedFor(id));
    }

    public Player(double budget, long rngSeed) {
        this.budget = budget;
        this.rng = new Random(rngSeed);
    }

    private static long seedFor(PlayerId id) {
        if (id == null) return 0L;
        return (id == PlayerId.P1) ? 1L : 2L;
    }

    public double budget() { return budget; }
    public void setBudget(double b) { this.budget = b; }

    public long spawnsUsed() { return spawnsUsed; }
    public void incSpawnsUsed() { spawnsUsed++; }

    public Map<String, Long> globalVars() { return globalVars; }

    public int random0to999() { return rng.nextInt(1000); }
}