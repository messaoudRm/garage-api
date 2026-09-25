package garage.app.facturation;

import java.util.ArrayList;
import java.util.List;

public class RecapitulatifConstructeurAdapter {

    private static final String REFERENCE_MAIN_OEUVRE = "MAIN_OEUVRE";

    public List<LigneRecapitulatifConstructeur> adapter(DemandeRecapitulatifConstructeur demande) {
        List<LigneRecapitulatifConstructeur> lignes = new ArrayList<>();

        for (PieceGarantie piece : demande.pieces()) {
            lignes.add(new LigneRecapitulatifConstructeur(
                    demande.immatriculation(),
                    piece.reference(),
                    piece.montant()
            ));
        }

        lignes.add(new LigneRecapitulatifConstructeur(
                demande.immatriculation(),
                REFERENCE_MAIN_OEUVRE,
                demande.montantMainOeuvre()
        ));

        return lignes;
    }

}
