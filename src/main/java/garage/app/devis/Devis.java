package garage.app.devis;

import garage.domain.Montant;

public record Devis(Montant mainOeuvre , Montant pieces) {



    public Montant total(){

        return Montant.ajouter(mainOeuvre ,pieces ) ;
    }
}
