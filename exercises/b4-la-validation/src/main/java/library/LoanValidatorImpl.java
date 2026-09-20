package library;

public class LoanValidatorImpl implements LoanValidator {

    @Override
    public void validate(Loan loan) {
        if (loan.reference()) {
            throw new IllegalArgumentException("Un ouvrage de référence ne se prolonge pas");
        }
    }
}
