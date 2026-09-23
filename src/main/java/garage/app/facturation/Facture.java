package garage.app.facturation;

import garage.domain.Montant;

public class Facture {

    private Montant montant;
    private boolean soldeEncaisse;

    public Facture(Montant montant) {
        this.montant = montant;
    }

    public Montant montantAcompte() {
        long acompte = montant.getCentimes() * 30 / 100;
        return Montant.centimes(acompte);
    }

    public Montant montantSolde() {
        long acompte = montant.getCentimes() * 30 / 100;
        long solde = montant.getCentimes() - acompte;
        return Montant.centimes(solde);
    }

    public Montant montantTTC() {
        long tva = montant.getCentimes() * 20 / 100;
        long total = montant.getCentimes() + tva;
        return Montant.centimes(total);
    }

    public void encaisserSolde() {
        if (soldeEncaisse == true) {
            throw new IllegalStateException("déjà soldée");
        }

        soldeEncaisse = true;
    }

}