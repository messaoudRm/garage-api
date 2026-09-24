package garage.app.facturation;

import garage.domain.Montant;

public class Garantie {

    private static final int DUREE_STANDARD_MOIS = 12;
    private static final int DUREE_EMBRAYAGE_MOIS = 24;

    private String motif;

    public Montant calculerMontantFacture(
            Montant montantTotal,
            Montant montantPieces,
            int moisEcoules,
            String cause,
            boolean estEmbrayage
    ) {
        int dureeGarantie = estEmbrayage ? DUREE_EMBRAYAGE_MOIS : DUREE_STANDARD_MOIS;

        if (moisEcoules > dureeGarantie) {
            motif = "hors garantie";
            return montantTotal;
        }

        if (cause.equals("piece")) {
            motif = "garantie pièce";
            return Montant.centimes(0);
        }

        motif = "garantie pose";
        return montantPieces;
    }

    public String motif() {
        return motif;
    }

}
