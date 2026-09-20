package library;

import java.util.HashMap;
import java.util.Map;

/** Tient lieu de base de données : une table, des lignes, une requête. */
public class SqlLoans {

    private final Map<String, String> rows = new HashMap<>();

    public SqlLoans() {
        rows.put("L-1", "L-1;ordinaire");
        rows.put("R-1", "R-1;reference");
    }

    public Loan selectById(String reference) {
        String row = rows.get(reference);
        if (row == null) {
            throw new IllegalArgumentException("Aucun prêt " + reference);
        }
        return new Loan(reference, row.endsWith("reference"));
    }
}
