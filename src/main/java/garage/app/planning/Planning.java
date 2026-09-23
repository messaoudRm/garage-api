package garage.app.planning;

import java.util.List;

public class Planning {

    private final List<Creneau> occupes;

    public Planning(Creneau... occupes) {
        this.occupes = List.of(occupes);
    }

    public boolean estDisponible(Creneau demande) {
        return demande.estDansLesHorairesDuGarage()
                && occupes.stream()
                .allMatch(occupe -> demande.estDisponiblePendant(occupe));
    }
}