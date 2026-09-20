package library;

public class LoanPolicy {

    private final int daysAllowed;
    private final int maxRenewals;
    private final boolean finesEnabled;

    public LoanPolicy(int daysAllowed, int maxRenewals, boolean finesEnabled) {
        this.daysAllowed = daysAllowed;
        this.maxRenewals = maxRenewals;
        this.finesEnabled = finesEnabled;
    }

    public int daysAllowed() {
        return daysAllowed;
    }

    public int maxRenewals() {
        return maxRenewals;
    }

    public long fineFor(int lateDays) {
        if (!finesEnabled) {
            return 0;
        }
        return 10L * lateDays;
    }
}
