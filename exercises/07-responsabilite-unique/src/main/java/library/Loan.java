package library;

import java.time.LocalDate;
import java.util.Map;

public class Loan {

    private final String reference;
    private final LocalDate borrowedOn;
    private final int daysAllowed;

    public Loan(String reference, LocalDate borrowedOn, int daysAllowed) {
        this.reference = reference;
        this.borrowedOn = borrowedOn;
        this.daysAllowed = daysAllowed;
    }

    public String reference() {
        return reference;
    }

    /** Le bibliothécaire. */
    public LocalDate dueDate() {
        return borrowedOn.plusDays(daysAllowed);
    }

    /** La comptabilité. */
    public String toCsvRow() {
        return reference + ";" + borrowedOn + ";" + dueDate();
    }

    /** L'équipe technique. */
    public void saveTo(Map<String, String> database) {
        database.put(reference, toCsvRow());
    }

    /** Le service communication. */
    public String reminder() {
        return "Bonjour, votre prêt " + reference + " est à rendre le " + dueDate() + ".";
    }
}
