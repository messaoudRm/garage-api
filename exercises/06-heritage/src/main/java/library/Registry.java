package library;

import java.util.HashMap;
import java.util.Map;

/**
 * Le registre range tout par identifiant. Comme tout le monde porte un Long,
 * rien n'empêche d'aller chercher un prêt avec le numéro d'un adhérent.
 */
public class Registry {

    private final Map<Long, Loan> loans = new HashMap<>();

    public void record(Long id, Loan loan) {
        loans.put(id, loan);
    }

    public Loan loan(Long id) {
        return loans.get(id);
    }
}
