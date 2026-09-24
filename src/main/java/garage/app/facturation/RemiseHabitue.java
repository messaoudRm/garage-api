package garage.app.facturation;

import garage.domain.Client;
import garage.domain.Montant;

public class RemiseHabitue implements StrategieRemise {

    private static final long POURCENTAGE_RESTANT = 95;
    private static final long CENT = 100;

    @Override
    public Montant appliquer(Montant montant, Client client) {
        if (!client.habitue()) {
            return montant;
        }
        return Montant.centimes(montant.getCentimes() * POURCENTAGE_RESTANT / CENT);
    }
}