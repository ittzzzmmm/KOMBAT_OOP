import java.util.HashMap;
import java.util.Map;

public final class Minion {
    private final minionId id;
    private final PlayerId owner;
    private int hp;
    private final int defenseFactor;
    private HexCoord pos;
    private final Map<String, Long> localVars = new HashMap<>();

    public Minion(minionId id, PlayerId owner, int hp, int defenseFactor, HexCoord pos) {
        this.id = id;
        this.owner = owner;
        this.hp = hp;
        this.defenseFactor = defenseFactor;
        this.pos = pos;
    }

    public minionId id() { return id; }
    public PlayerId owner() { return owner; }

    public int hp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int defenseFactor() { return defenseFactor; }

    public HexCoord pos() { return pos; }
    public void setPos(HexCoord pos) { this.pos = pos; }

    public Map<String, Long> localVars() { return localVars; }
}