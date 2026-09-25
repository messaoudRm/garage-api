package facturation;

import garage.app.facturation.DemandeRecapitulatifConstructeur;
import garage.app.facturation.LigneRecapitulatifConstructeur;
import garage.app.facturation.PieceGarantie;
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

        assertEquals("AB-123-CD", lignes.get(0).immatriculation());
        assertEquals("REF-001", lignes.get(0).reference());
        assertEquals(5000, lignes.get(0).montant().getCentimes());

        assertEquals("AB-123-CD", lignes.get(1).immatriculation());
        assertEquals("REF-002", lignes.get(1).reference());
        assertEquals(3000, lignes.get(1).montant().getCentimes());

        assertEquals("AB-123-CD", lignes.get(2).immatriculation());
        assertEquals("REF-003", lignes.get(2).reference());
        assertEquals(2000, lignes.get(2).montant().getCentimes());

        assertEquals("AB-123-CD", lignes.get(3).immatriculation());
        assertEquals("MAIN_OEUVRE", lignes.get(3).reference());
        assertEquals(15000, lignes.get(3).montant().getCentimes());
    }

}
