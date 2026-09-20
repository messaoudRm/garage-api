package library;

import java.time.LocalDate;

public class Loan extends AbstractEntity {

    private final LocalDate borrowedOn;
    private final int daysAllowed;

    public Loan(LocalDate borrowedOn, int daysAllowed) {
        this.borrowedOn = borrowedOn;
        this.daysAllowed = daysAllowed;
    }

    public LocalDate dueDate() {
        return borrowedOn.plusDays(daysAllowed);
    }
}
