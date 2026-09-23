package garage.app.devis;

import garage.domain.Montant;

public class LignePiece {


    private final String name ;
    private final Montant PUnitaire ;
    private final int quantite ;

    public LignePiece(String name, Montant PUnitaire, int quantite) {

if(quantite<=0){
    throw new IllegalArgumentException("attention  quantité doit etre >0") ;
}

        this.name = name;
        this.PUnitaire = PUnitaire;
        this.quantite = quantite;
    }

    public Montant montant (){

        return Montant.multiplierPar(PUnitaire,quantite ) ;
    }
}
