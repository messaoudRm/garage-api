package garage.app.devis;

import garage.domain.Montant;

public class LignePiece {


    private final String name ;
    private final Montant PUnitaire ;
    private final int quantite ;

    public LignePiece(String name, Montant PUnitaire, int quantite) {
        this.name = name;
        this.PUnitaire = PUnitaire;
        this.quantite = quantite;
    }

    public Montant montant (){

        return Montant.multiplierPar(PUnitaire,quantite ) ;
    }
}
