package garage.app.planning;

import java.time.LocalTime;
import java.util.List;

public class Planning {

    private static final LocalTime FERMETURE = LocalTime.of(18, 0);
    private static final int PAS_RECHERCHE_MINUTES = 30;

    private final List<Creneau> occupes;

    public Planning(Creneau... occupes) {
        this.occupes = List.of(occupes);
    }

    public boolean estDisponible(Creneau demande) {
        return demande.estDansLesHorairesDuGarage()
                && occupes.stream()
                .allMatch(occupe -> demande.estDisponiblePendant(occupe));
    }

    public Creneau trouverPremierCreneauDisponible(
            LocalTime heureDebut,
            int dureeMinutes
    ) {
        LocalTime heure = heureDebut;

        while (!heure.isAfter(FERMETURE)) {
            LocalTime fin = heure.plusMinutes(dureeMinutes);

            Creneau demande = new Creneau(heure, fin);

            if (estDisponible(demande)) {
                return demande;
            }

            heure = heure.plusMinutes(PAS_RECHERCHE_MINUTES);
        }

        return null;
    }
}