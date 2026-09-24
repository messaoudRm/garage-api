package facturation;

import garage.app.facturation.CauseRetour;
import garage.app.facturation.Garantie;
import garage.app.facturation.ResultatGarantie;
import garage.app.facturation.TypePiece;
import garage.domain.Montant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarantieTest {

    private static final Montant MONTANT_TOTAL = Montant.centimes(30000);
    private static final Montant MONTANT_PIECES = Montant.centimes(10000);

    // Intervention reprise 8 mois après pour pièce défectueuse : tout est offert
    @Test
    void doit_ne_rien_facturer_pour_une_piece_defectueuse_dans_les_12_mois() {
        Garantie garantie = new Garantie();

        ResultatGarantie resultat = garantie.calculer(
                MONTANT_TOTAL, MONTANT_PIECES, 8, CauseRetour.PIECE_DEFECTUEUSE, TypePiece.STANDARD
        );

        assertEquals(0, resultat.montantFacture().getCentimes());
        assertEquals("garantie pièce", resultat.motif());
    }

    // Intervention reprise 8 mois après pour défaut de pose : seules les pièces sont facturées
    @Test
    void doit_facturer_seulement_les_pieces_pour_un_defaut_de_pose_dans_les_12_mois() {
        Garantie garantie = new Garantie();

        ResultatGarantie resultat = garantie.calculer(
                MONTANT_TOTAL, MONTANT_PIECES, 8, CauseRetour.DEFAUT_POSE, TypePiece.STANDARD
        );

        assertEquals(MONTANT_PIECES.getCentimes(), resultat.montantFacture().getCentimes());
        assertEquals("garantie pose", resultat.motif());
    }

    // Intervention reprise 14 mois après : hors garantie, tout est facturé
    @Test
    void doit_tout_facturer_apres_12_mois() {
        Garantie garantie = new Garantie();

        ResultatGarantie resultat = garantie.calculer(
                MONTANT_TOTAL, MONTANT_PIECES, 14, CauseRetour.PIECE_DEFECTUEUSE, TypePiece.STANDARD
        );

        assertEquals(MONTANT_TOTAL.getCentimes(), resultat.montantFacture().getCentimes());
        assertEquals("hors garantie", resultat.motif());
    }

    // Un embrayage repris 20 mois après reste sous garantie (24 mois), rien n'est facturé
    @Test
    void doit_ne_rien_facturer_pour_un_embrayage_defectueux_sous_24_mois() {
        Garantie garantie = new Garantie();

        ResultatGarantie resultat = garantie.calculer(
                MONTANT_TOTAL, MONTANT_PIECES, 20, CauseRetour.PIECE_DEFECTUEUSE, TypePiece.EMBRAYAGE
        );

        assertEquals(0, resultat.montantFacture().getCentimes());
        assertEquals("garantie pièce", resultat.motif());
    }
}
