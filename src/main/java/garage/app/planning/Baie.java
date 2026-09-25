package garage.app.planning;

import garage.domain.Atelier;
import garage.domain.Vehicule;

import java.util.List;

public class Baie {

    private final int capaciteKg;
    private final TypeBaie type;
    private final List<Creneau> occupes;

    private Baie(
            TypeBaie type,
            int capaciteKg,
            Creneau... occupes
    ) {
        this.type = type;
        this.capaciteKg = capaciteKg;
        this.occupes = List.of(occupes);
    }

    public static Baie pont(int capaciteKg, Creneau... occupes) {
        return new Baie(
                TypeBaie.PONT,
                capaciteKg,
                occupes
        );
    }

    public static Baie fosse(Creneau... occupes) {
        return new Baie(
                TypeBaie.FOSSE,
                Integer.MAX_VALUE,
                occupes
        );
    }

    public static Baie cabinePeinture(Creneau... occupes) {
        return new Baie(
                TypeBaie.CABINE_PEINTURE,
                Integer.MAX_VALUE,
                occupes
        );
    }

    public boolean peutAccueillir(Vehicule vehicule) {
        return vehicule.poidsKg() <= capaciteKg;
    }

    public boolean estCompatibleAvec(Atelier atelier) {
        if (atelier == Atelier.CARROSSERIE) {
            return type == TypeBaie.CABINE_PEINTURE;
        }

        return type == TypeBaie.PONT
                || type == TypeBaie.FOSSE;
    }

    public boolean estDisponible(Creneau demande) {
        return demande.estDansLesHorairesDuGarage()
                && occupes.stream()
                .allMatch(occupe -> demande.estDisponiblePendant(occupe));
    }

    public boolean estCabinePeinture() {
        return type == TypeBaie.CABINE_PEINTURE;
    }

    private enum TypeBaie {
        PONT,
        FOSSE,
        CABINE_PEINTURE
    }
}