package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public class TarificationDiagnostic implements Tarification {

    private static final long TARIF_QUART_HEURE = 2375;





    @Override
    public Montant calculer(Duration duree) {

        long minutes = duree.toMinutes();

        long nombreDeQuarts = minutes / 15;

        if (minutes % 15 != 0) {
            nombreDeQuarts++;
        }

        return Montant.centimes(
                nombreDeQuarts * TARIF_QUART_HEURE
        );
    }
}
