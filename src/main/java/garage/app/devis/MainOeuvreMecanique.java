package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public class MainOeuvreMecanique {

    private static final long PRIX_QUART_HEURE = 1550;


    public Montant totalMainOeuvre(Duration duree){
long minutes = duree.toMinutes() ;

        long nombreDeQuarts = minutes / 15;

        if (minutes % 15 != 0) {
            nombreDeQuarts++;}

return Montant.centimes( nombreDeQuarts * PRIX_QUART_HEURE) ;
    }
}
