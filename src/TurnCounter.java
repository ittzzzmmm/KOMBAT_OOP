public final class TurnCounter {
    private int turnP1 = 0;
    private int turnP2 = 0;

    public int turnOf(PlayerId p) {
        return p == PlayerId.P1 ? turnP1 : turnP2;
    }

    public void inc(PlayerId p) {
        if (p == PlayerId.P1) turnP1++;
        else turnP2++;
    }
}