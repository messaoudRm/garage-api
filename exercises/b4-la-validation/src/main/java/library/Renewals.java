package library;

/** Le service de prolongation. Cette signature ne change pas. */
public class Renewals {

    private final LoanValidator validator = new LoanValidatorImpl();

    public String renew(Loan loan) {
        validator.validate(loan);
        return loan.title() + " prolongé";
    }
}
