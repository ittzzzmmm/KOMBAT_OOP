public record GameConfig(
        long spawnCost,
        long hexPurchaseCost,
        long initBudget,
        int initHp,
        long turnBudget,
        long maxBudget,
        double interestPct,
        int maxTurns,
        int maxSpawns,
        int maxTicks // (ไม่มีในไฟล์ sample) ใช้สำหรับจำกัดการรัน strategy
) {
    public GameConfig {
        if (spawnCost < 0) throw new IllegalArgumentException("spawn_cost must be >= 0");
        if (hexPurchaseCost < 0) throw new IllegalArgumentException("hex_purchase_cost must be >= 0");
        if (initBudget < 0) throw new IllegalArgumentException("init_budget must be >= 0");
        if (initHp <= 0) throw new IllegalArgumentException("init_hp must be > 0");
        if (turnBudget < 0) throw new IllegalArgumentException("turn_budget must be >= 0");
        if (maxBudget < 0) throw new IllegalArgumentException("max_budget must be >= 0");
        if (interestPct < 0) throw new IllegalArgumentException("interest_pct must be >= 0");
        if (maxTurns <= 0) throw new IllegalArgumentException("max_turns must be > 0");
        if (maxSpawns < 0) throw new IllegalArgumentException("max_spawns must be >= 0");
        if (maxTicks <= 0) throw new IllegalArgumentException("maxTicks must be > 0");

    }
}