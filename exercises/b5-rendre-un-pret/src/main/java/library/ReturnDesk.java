package library;

import java.time.LocalDate;

/** Le guichet de retour. Cette signature ne change pas. */
public class ReturnDesk {

    public void giveBack(Loan loan, LocalDate today) {
        if (loan.isReturned()) {
            throw new AlreadyReturned();
        }
        loan.setReturned(true);
        loan.setReturnedOn(today);
    }
}
