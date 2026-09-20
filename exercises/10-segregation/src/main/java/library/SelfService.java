package library;

public interface SelfService {

    String borrow(String reference);

    String giveBack(String reference);

    String payFine(long cents);

    String printReceipt();

    void reloadPaper();
}
