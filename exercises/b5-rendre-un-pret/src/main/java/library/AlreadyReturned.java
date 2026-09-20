package library;

public class AlreadyReturned extends RuntimeException {

    public AlreadyReturned() {
        super("Ce prêt a déjà été rendu");
    }
}
