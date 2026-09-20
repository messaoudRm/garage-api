package library;

/** Le seul appelant du projet, depuis deux ans. Ces signatures ne changent pas. */
public class Library {

    private final LoanPolicy policy = new LoanPolicy(15, 2, true);

    public int daysAllowed() {
        return policy.daysAllowed();
    }

    public int maxRenewals() {
        return policy.maxRenewals();
    }

    public long fineFor(int lateDays) {
        return policy.fineFor(lateDays);
    }
}
