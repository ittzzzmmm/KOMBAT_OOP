public final class Minion {
    private final int id;
    private final PlayerId owner;
    private HexCoord pos;
    private long hp;
    private long defense;

    // pointer ชี้ ไปที่เก็บ ast root
    private final StrategyId strategyId;

    // local vars
    private final java.util.Map<String, Long> localVars = new java.util.HashMap<>();

    // context
    private final java.util.Map<String, Long> context= new java.util.HashMap<>();

    public Minion(int id, PlayerId owner, HexCoord pos, long hp, long defense, StrategyId strategyId) {
        this.id = id;
        this.owner = owner;
        this.pos = pos;
        this.hp = hp;
        this.defense = defense;
        this.strategyId = strategyId;
    }

    public int id() { return id; }

    public PlayerId owner() { return owner; }

    public HexCoord pos() { return pos; }

    public void setPos(HexCoord p) { pos = p; }

    public long hp() { return hp; }

    public void setHp(long v) { hp = v; }

    public long defense() { return defense; }

    public StrategyId strategyId() { return strategyId; }

    public java.util.Map<String, Long> localVars() { return localVars; }

    public java.util.Map<String, Long> context() { return context; }

}
