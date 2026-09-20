package library;

/** La règle de prolongation. Cette signature ne change pas. */
public class Renewals {

    private final SqlLoans loans = new SqlLoans();

    public String renew(String reference) {
        Loan loan = loans.selectById(reference);
        if (loan.reference_work()) {
            throw new IllegalArgumentException("Un ouvrage de référence ne se prolonge pas");
        }
        return reference + " prolongé";
    }
}
