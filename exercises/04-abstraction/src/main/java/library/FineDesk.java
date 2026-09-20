package library;

/** Le guichet des amendes. Ces signatures ne changent pas. */
public class FineDesk {

    public String label(Fine fine) {
        return fine.cents() / 100 + "," + String.format("%02d", fine.cents() % 100) + " €";
    }

    public String receipt(Fine fine) {
        return "À payer : " + fine.cents() / 100 + "," + String.format("%02d", fine.cents() % 100) + " €";
    }

    public boolean isFree(Fine fine) {
        return fine.cents() == 0;
    }
}
