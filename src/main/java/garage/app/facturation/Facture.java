package garage.app.facturation;

import garage.domain.Montant;

public class Facture {

    private static final long POURCENTAGE_ACOMPTE = 30;
    private static final long POURCENTAGE_TVA = 20;
    private static final long CENT = 100;

    private final Montant montant;
    private boolean soldeEncaisse;

    public Facture(Montant montant) {
        this.montant = montant;
    }

    public Montant montantAcompte() {
        return calculerPourcentage(montant, POURCENTAGE_ACOMPTE);
    }

    public Montant montantSolde() {
        Montant acompte = montantAcompte();
        return Montant.centimes(montant.getCentimes() - acompte.getCentimes());
    }

    public Montant montantTTC() {
        Montant tva = calculerPourcentage(montant, POURCENTAGE_TVA);
        return Montant.centimes(montant.getCentimes() + tva.getCentimes());
    }

    public void encaisserSolde() {
        if (soldeEncaisse) {
            throw new IllegalStateException("déjà soldée");
        }

        soldeEncaisse = true;
    }

    private Montant calculerPourcentage(Montant montant, long pourcentage) {
        return Montant.centimes(montant.getCentimes() * pourcentage / CENT);
    }
}