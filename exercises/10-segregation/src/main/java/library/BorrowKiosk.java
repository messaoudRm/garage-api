package library;

/** Le kiosque de l'entrée ne fait qu'une chose : prêter. Cette signature ne change pas. */
public class BorrowKiosk implements SelfService {

    @Override
    public String borrow(String reference) {
        return reference + " emprunté";
    }

    @Override
    public String giveBack(String reference) {
        throw new UnsupportedOperationException("Le kiosque de prêt ne reçoit pas de retours");
    }

    @Override
    public String payFine(long cents) {
        throw new UnsupportedOperationException("Le kiosque de prêt n'encaisse pas");
    }

    @Override
    public String printReceipt() {
        return null;
    }

    @Override
    public void reloadPaper() {
        // pas d'imprimante ici
    }
}
