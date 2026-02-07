public final class GameVarStore implements VarStore {

    private final Minion minion;
    private final Player player;

    public GameVarStore(Minion minion, Player player) {
        this.minion = minion;
        this.player = player;
    }

    @Override
    public long get(String name) {
        if (name == null || name.isEmpty()) return 0;

        // ตัวแปรขึ้นต้นตัวใหญ่ => global
        if (Character.isUpperCase(name.charAt(0))) {
            return player.globalVars().getOrDefault(name, 0L);
        }

        // ตัวแปรขึ้นต้นตัวเล็ก => local
        return minion.localVars().getOrDefault(name, 0L);
    }

    @Override
    public void set(String name, long value) {
        if (name == null || name.isEmpty()) return;
        if (ReadOnly(name)) return; // กันแก้ special vars

        if (Character.isUpperCase(name.charAt(0))) {
            player.globalVars().put(name, value);
        } else {
            minion.localVars().put(name, value);
        }
    }

    //special variable
    @Override
    public boolean ReadOnly(String name) {
        return switch (name) {
            case "row", "col",
                 "Budget", "Int", "MaxBudget", "SpawnsLeft",
                 "random", "opponent", "ally", "nearby" -> true;
            default -> false;
        };
    }
}