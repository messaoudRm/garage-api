package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public interface Tarification {
    Montant calculer(Duration duree);
}

