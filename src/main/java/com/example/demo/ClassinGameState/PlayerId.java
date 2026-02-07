public enum PlayerId {
    P1, P2;

    public PlayerId opponent() {
        return this == P1 ? P2 : P1;
    }
}
