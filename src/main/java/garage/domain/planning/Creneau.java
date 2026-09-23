package garage.domain.planning;

import java.time.LocalTime;

public class Creneau {

    private final LocalTime debut;
    private final LocalTime fin;

    public Creneau(LocalTime debut, LocalTime fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public boolean estDisponiblePendant(Creneau occupe) {
        return this.fin.compareTo(occupe.debut) <= 0
                || this.debut.compareTo(occupe.fin) >= 0;
    }
}