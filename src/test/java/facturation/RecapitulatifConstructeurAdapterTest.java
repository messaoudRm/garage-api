package facturation;

import garage.app.facturation.DemandeRecapitulatifConstructeur;
import garage.app.facturation.LigneRecapitulatifConstructeur;
import garage.app.facturation.PieceGarantie;
import garage.app.facturation.RecapitulatifConstructeurAdapter;
import org.junit.jupiter.api.Test;
import garage.domain.Montant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;


public class RecapitulatifConstructeurAdapterTest {

    // 3 pièces reprises : 3 lignes + 1 ligne mo
    @Test
    void doit_produire_une_ligne_par_piece_reprise_plus_une_ligne_main_oeuvre() {
        RecapitulatifConstructeurAdapter adapter = new RecapitulatifConstructeurAdapter();

        DemandeRecapitulatifConstructeur demande = new DemandeRecapitulatifConstructeur(
                "AB-123-CD",
                List.of(
                        new PieceGarantie("REF-001", Montant.centimes(5000)),
                        new PieceGarantie("REF-002", Montant.centimes(3000)),
                        new PieceGarantie("REF-003", Montant.centimes(2000))
                ),
                Montant.centimes(15000)
        );

        List<LigneRecapitulatifConstructeur> lignes = adapter.adapter(demande);

        assertEquals(4, lignes.size());
        assertLigne(lignes.get(0), "AB-123-CD", "REF-001", 5000);
        assertLigne(lignes.get(1), "AB-123-CD", "REF-002", 3000);
        assertLigne(lignes.get(2), "AB-123-CD", "REF-003", 2000);
        assertLigne(lignes.get(3), "AB-123-CD", "MAIN_OEUVRE", 15000);
    }

    private void assertLigne(LigneRecapitulatifConstructeur ligne, String immatriculation, String reference, long montant) {
        assertEquals(immatriculation, ligne.immatriculation());
        assertEquals(reference, ligne.reference());
        assertEquals(montant, ligne.montant().getCentimes());
    }

}
