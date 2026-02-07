public interface VarStore {
    long get(String name);
    void set(String name, long value);
    boolean ReadOnly(String name);
}