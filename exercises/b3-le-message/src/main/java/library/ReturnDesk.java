package library;

/** Le guichet de retour. Cette signature ne change pas. */
public class ReturnDesk {

    private final Bus bus = new Bus();

    public ReturnDesk(Fines fines) {
        bus.subscribe(new FineListener(fines)::on);
    }

    public void giveBack(String loanId) {
        bus.publish(new LoanReturned(loanId));
    }
}
