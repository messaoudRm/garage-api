package garage.app.devis;

import garage.domain.Montant;

import java.util.List;



public record Devis(List<LigneDevis> lignes) {

    public Devis {
        lignes = List.copyOf(lignes);
    }

    public Devis(Montant mainOeuvre, Montant pieces) {
        this(List.of(
                new LigneDevis("Main-d'œuvre", mainOeuvre),
                new LigneDevis("Pièces", pieces)
        ));
    }

    public Montant total() {

        Montant total = Montant.centimes(0);

        for (LigneDevis ligne : lignes) {
            total = Montant.ajouter(total, ligne.montant());
        }

        return total;
    }




}