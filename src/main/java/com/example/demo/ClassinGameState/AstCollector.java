import java.util.ArrayList;
import java.util.List;

public final class AstCollector {
    private final List<Statement> astPool = new ArrayList<>();

    /** add AST root แล้วคืน id ไว้ให้ minion ชี้มา */
    public StrategyId add(Statement root) {
        if (root == null) throw new IllegalArgumentException("AST root is null");
        astPool.add(root);
        return new StrategyId(astPool.size() - 1);
    }

    public Statement get(StrategyId id) {
        int idx = id.value();
        if (idx < 0 || idx >= astPool.size()) {
            throw new IllegalArgumentException("Invalid strategy id: " + idx);
        }
        return astPool.get(idx);
    }

    public int size() { return astPool.size(); }
}
