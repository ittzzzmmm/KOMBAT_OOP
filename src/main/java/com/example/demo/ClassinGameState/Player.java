import java.util.HashMap;
import java.util.Map;

public final class Player {
    private double budget;
    private int spawnsUsed;
    private final Map<String, Long> globalVars = new HashMap<>();

    public Player(double budget) {
        this.budget = budget;
    }

    public double budget() {
        return budget;
    }

    public void setBudget(double b) {
        this.budget = b;
    }

    public void addBudget(double newBudget) {
        this.budget += newBudget;
    }

    public int spawnsUsed() {
        return spawnsUsed;
    }
    public void incSpawnsUsed() {
        spawnsUsed++;
    }

    public Map<String, Long> globalVars() {
        return globalVars;
    }
}
