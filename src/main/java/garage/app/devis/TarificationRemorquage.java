package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public class TarificationRemorquage  {
    private static final long PRIX_PAR_KM = 210;
    public Montant calculer(int kilometre ) {
        return Montant.centimes(PRIX_PAR_KM*kilometre );
    }
}
