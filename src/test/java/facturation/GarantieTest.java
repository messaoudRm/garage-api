package facturation;

import garage.domain.Montant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarantieTest {

    rivate static final Montant MONTANT_TOTAL = Montant.centimes(30000);
    private static final Montant MONTANT_PIECES = Montant.centimes(10000);

    // Intervention reprise 8 mois après pour pièce défectueuse : tout est offert
    @Test
    void doit_ne_rien_facturer_pour_une_piece_defectueuse_dans_les_12_mois() {
        Garantie garantie = new Garantie();

        Montant resultat = garantie.calculerMontantFacture(
                MONTANT_TOTAL, MONTANT_PIECES, 8, "piece", false
        );

        assertEquals(0, resultat.getCentimes());
        assertEquals("garantie pièce", garantie.motif());
    }

    // Intervention reprise 8 mois après pour défaut de pose : seules les pièces sont facturées
    @Test
    void doit_facturer_seulement_les_pieces_pour_un_defaut_de_pose_dans_les_12_mois() {
        Garantie garantie = new Garantie();

        Montant resultat = garantie.calculerMontantFacture(
                MONTANT_TOTAL, MONTANT_PIECES, 8, "pose", false
        );

        assertEquals(MONTANT_PIECES.getCentimes(), resultat.getCentimes());
        assertEquals("garantie pose", garantie.motif());
    }

    // Intervention reprise 14 mois après : hors garantie, tout est facturé
    @Test
    void doit_tout_facturer_apres_12_mois() {
        Garantie garantie = new Garantie();

        Montant resultat = garantie.calculerMontantFacture(
                MONTANT_TOTAL, MONTANT_PIECES, 14, "piece", false
        );

        assertEquals(MONTANT_TOTAL.getCentimes(), resultat.getCentimes());
        assertEquals("hors garantie", garantie.motif());
    }

    // Un embrayage repris 20 mois après reste sous garantie (24 mois), rien n'est facturé
    @Test
    void doit_ne_rien_facturer_pour_un_embrayage_defectueux_sous_24_mois() {
        Garantie garantie = new Garantie();

        Montant resultat = garantie.calculerMontantFacture(
                MONTANT_TOTAL, MONTANT_PIECES, 20, "piece", true
        );

        assertEquals(0, resultat.getCentimes());
        assertEquals("garantie pièce", garantie.motif());
    }
}
