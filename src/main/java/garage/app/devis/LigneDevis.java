package garage.app.devis;


import garage.domain.Montant;

public record LigneDevis(
            String libelle,
            Montant montant
    ) {

}
