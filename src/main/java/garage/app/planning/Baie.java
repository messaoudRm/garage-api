package garage.app.planning;

import garage.domain.Atelier;
import garage.domain.Vehicule;

public class Baie {

    private final int capaciteKg;
    private final TypeBaie type;

    private Baie(TypeBaie type, int capaciteKg) {
        this.type = type;
        this.capaciteKg = capaciteKg;
    }

    public static Baie pont(int capaciteKg) {
        return new Baie(TypeBaie.PONT, capaciteKg);
    }

    public static Baie fosse() {
        return new Baie(TypeBaie.FOSSE, Integer.MAX_VALUE);
    }

    public static Baie cabinePeinture() {
        return new Baie(TypeBaie.CABINE_PEINTURE, Integer.MAX_VALUE);
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

    private enum TypeBaie {
        PONT,
        FOSSE,
        CABINE_PEINTURE
    }
}