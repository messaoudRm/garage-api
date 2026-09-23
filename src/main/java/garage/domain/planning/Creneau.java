package garage.domain.planning;

import java.time.LocalTime;

public class Creneau {

    private static final LocalTime OUVERTURE_MATIN = LocalTime.of(8, 0);
    private static final LocalTime PAUSE_DEJEUNER = LocalTime.of(12, 0);
    private static final LocalTime OUVERTURE_APRES_MIDI = LocalTime.of(14, 0);
    private static final LocalTime FERMETURE = LocalTime.of(18, 0);

    private final LocalTime debut;
    private final LocalTime fin;

    public Creneau(LocalTime debut, LocalTime fin) {
        if (fin.isBefore(debut)) {
            throw new IllegalArgumentException(
                    "La fin du créneau ne peut pas être avant le début"
            );
        }

        this.debut = debut;
        this.fin = fin;
    }

    public boolean estDisponiblePendant(Creneau occupe) {
        return this.fin.compareTo(occupe.debut) <= 0
                || this.debut.compareTo(occupe.fin) >= 0;
    }

    public boolean estDansLesHorairesDuGarage() {
        boolean matin = !debut.isBefore(OUVERTURE_MATIN)
                && !fin.isAfter(PAUSE_DEJEUNER);

        boolean apresMidi = !debut.isBefore(OUVERTURE_APRES_MIDI)
                && !fin.isAfter(FERMETURE);

        return matin || apresMidi;
    }
}