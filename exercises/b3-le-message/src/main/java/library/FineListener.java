package library;

public class FineListener {

    private final Fines fines;

    public FineListener(Fines fines) {
        this.fines = fines;
    }

    public void on(LoanReturned event) {
        fines.settle(event.loanId());
    }
}
