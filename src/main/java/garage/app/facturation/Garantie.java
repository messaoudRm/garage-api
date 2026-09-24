package garage.app.facturation;

import garage.domain.Montant;

public class Garantie {

    public ResultatGarantie calculer(
            Montant montantTotal,
            Montant montantPieces,
            int moisEcoules,
            CauseRetour cause,
            TypePiece typePiece
    ) {
        if (moisEcoules > typePiece.dureeGarantieMois()) {
            return new ResultatGarantie(montantTotal, "hors garantie");
        }

        if (cause == CauseRetour.PIECE_DEFECTUEUSE) {
            return new ResultatGarantie(Montant.centimes(0), "garantie pièce");
        }

        return new ResultatGarantie(montantPieces, "garantie pose");
    }

}
