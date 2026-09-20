package library;

import java.util.ArrayList;
import java.util.List;

public class Fines {

    private final List<String> settled = new ArrayList<>();

    public void settle(String loanId) {
        settled.add(loanId);
    }

    public List<String> settled() {
        return List.copyOf(settled);
    }
}
