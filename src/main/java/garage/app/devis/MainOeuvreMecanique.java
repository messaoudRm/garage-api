package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public class MainOeuvreMecanique extends TarificationParQuart {

    public MainOeuvreMecanique() {
        super(1550);
    }

    public Montant totalMainOeuvre(Duration duree) {
        return calculer(duree);
    }
}
