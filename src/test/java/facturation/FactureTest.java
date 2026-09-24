package facturation;


import garage.app.facturation.Facture;
import garage.domain.Montant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactureTest {

    private static final Montant MONTANT_FACTURE = Montant.centimes(28410);
    private static final Montant MONTANT_ACOMPTE = Montant.centimes(8523);
    private static final Montant MONTANT_SOLDE = Montant.centimes(19887);
    private static final Montant MONTANT_TTC = Montant.centimes(34092);

    // Vérifie que l'acompte correspond bien à 30 % du montant de la facture
    @Test
    void doit_calculer_lacompte_a_30_pourcent_du_montant() {
        Facture facture = new Facture(MONTANT_FACTURE);
        assertEquals(MONTANT_ACOMPTE.getCentimes(), facture.montantAcompte().getCentimes());
    }

    // Vérifie que le solde à payer au retrait correspond aux 70 % restants
    @Test
    void doit_calculer_le_solde_a_verser_au_retrait() {
        Facture facture = new Facture(MONTANT_FACTURE);
        assertEquals(MONTANT_SOLDE.getCentimes(), facture.montantSolde().getCentimes());
    }

    // Vérifie que l'acompte + le solde correspondent toujours au montant total de la facture
    @Test
    void doit_faire_correspondre_la_somme_acompte_et_solde_au_montant_de_la_facture() {
        Facture facture = new Facture(MONTANT_FACTURE);
        long somme = facture.montantAcompte().getCentimes() + facture.montantSolde().getCentimes();
        assertEquals(MONTANT_FACTURE.getCentimes(), somme);
    }

    // Vérifie que la TVA de 20 % est correctement ajoutée au montant hors taxe
    @Test
    void doit_calculer_le_montant_ttc_avec_une_tva_a_20_pourcent() {
        Facture facture = new Facture(MONTANT_FACTURE);
        assertEquals(MONTANT_TTC.getCentimes(), facture.montantTTC().getCentimes());
    }

    // Vérifie qu'il est impossible d'encaisser une deuxième fois le solde d'une facture déjà soldée
    @Test
    void doit_refuser_un_second_reglement_sur_une_facture_deja_soldee() {
        Facture facture = new Facture(MONTANT_FACTURE);
        facture.encaisserSolde();
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> facture.encaisserSolde());
        assertEquals("déjà soldée", exception.getMessage());
    }

}