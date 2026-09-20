package library;

public class Fine {

    private final long cents;

    public Fine(long cents) {
        this.cents = cents;
    }

    public long cents() {
        return cents;
    }
}
