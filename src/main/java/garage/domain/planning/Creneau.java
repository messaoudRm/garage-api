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

    public boolean estDansLesHorairesDuGarage() {
        boolean matin = !debut.isBefore(LocalTime.of(8, 0))
                && !fin.isAfter(LocalTime.of(12, 0));

        boolean apresMidi = !debut.isBefore(LocalTime.of(14, 0))
                && !fin.isAfter(LocalTime.of(18, 0));

        return matin || apresMidi;
    }
}