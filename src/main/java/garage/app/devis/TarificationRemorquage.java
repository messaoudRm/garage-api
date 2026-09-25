package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public class TarificationRemorquage  {
    private static final long PRIX_PAR_KM = 210;
    private static final long PRIX_MINIMUM = 4500;

    public Montant calculer(int kilometres ) {
        long prix = kilometres * PRIX_PAR_KM;

        if(prix <=4500){

       return Montant.centimes(PRIX_MINIMUM) ;
        }

        return Montant.centimes(PRIX_PAR_KM*kilometres );
    }
}
