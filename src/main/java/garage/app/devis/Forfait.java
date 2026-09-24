package garage.app.devis;

import garage.domain.Montant;

import java.time.Duration;

public class Forfait implements Tarification{


   private final  Montant prix ;
   private final String forfaitName ;

    public Forfait( String forfaitName , Montant prix) {
        this.prix = prix;
        this.forfaitName = forfaitName;
    }

    @Override
    public Montant calculer(Duration duree) {
        return prix;
    }
}
