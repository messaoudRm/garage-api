package garage.app.planning;

public class Planning {

    private final Creneau occupe;

    public Planning(Creneau occupe) {
        this.occupe = occupe;
    }

    public boolean estDisponible(Creneau demande) {
        return demande.estDansLesHorairesDuGarage()
                && demande.estDisponiblePendant(occupe);
    }
}