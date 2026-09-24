package garage.app.facturation;

import garage.domain.Client;
import garage.domain.Montant;

public interface StrategieRemise {
    Montant appliquer(Montant montant, Client client);
}