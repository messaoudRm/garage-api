package garage.app.planning;

import garage.domain.Vehicule;

public class Baie {

    private final int capaciteKg;

    private Baie(int capaciteKg) {
        this.capaciteKg = capaciteKg;
    }

    public static Baie pont(int capaciteKg) {
        return new Baie(capaciteKg);
    }

    public boolean peutAccueillir(Vehicule vehicule) {
        return vehicule.poidsKg() <= capaciteKg;
    }
}