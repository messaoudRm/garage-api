package facturation;

import garage.app.facturation.Remise;
import garage.domain.Client;
import garage.domain.Montant;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemiseTest {

    private static final Montant MAIN_OEUVRE = Montant.centimes(20000);
    private static final Montant PIECES = Montant.centimes(10000);
    private static final Montant MONTANT_PROFESSIONNEL = Montant.centimes(27000);
    private static final Montant MONTANT_HABITUE = Montant.centimes(28500);
    private static final Montant MONTANT_PROFESSIONNEL_HABITUE = Montant.centimes(25650);
    private static final Montant FACTURE = Montant.centimes(2500);
    private static final Montant GESTE_COMMERCIAL = Montant.centimes(4000);

    // Vérifie que le client professionnel bénéficie de 15 % de remise sur la main-d'œuvre uniquement
    @Test
    void doit_appliquer_15_pourcent_sur_la_main_oeuvre_pour_un_client_professionnel() {
        Remise remise = new Remise();
        Client client = new Client("Yassine", true, false);
        Montant resultat = remise.calculer(MAIN_OEUVRE, PIECES, client);
        assertEquals(MONTANT_PROFESSIONNEL.getCentimes(), resultat.getCentimes());
    }

    // Vérifie que le client habitué bénéficie de 5 % de remise sur la totalité
    @Test
    void doit_appliquer_5_pourcent_sur_tout_pour_un_client_habitue() {
        Remise remise = new Remise();
        Client client = new Client("Yassine", false, true);
        Montant resultat = remise.calculer(MAIN_OEUVRE, PIECES, client);
        assertEquals(MONTANT_HABITUE.getCentimes(), resultat.getCentimes());
    }

    // Vérifie que la remise professionnelle est appliquée avant la remise habitué
    @Test
    void doit_appliquer_la_remise_professionnelle_avant_la_remise_habitue() {
        Remise remise = new Remise();
        Client client = new Client("Yassine", true, true);
        Montant resultat = remise.calculer(MAIN_OEUVRE, PIECES, client);
        assertEquals(MONTANT_PROFESSIONNEL_HABITUE.getCentimes(), resultat.getCentimes());
    }

    // Vérifie qu'un client sans avantage ne bénéficie d'aucune remise
    @Test
    void ne_doit_appliquer_aucune_remise_pour_un_client_sans_avantage() {
        Remise remise = new Remise();
        Client client = new Client("Yassine", false, false);
        Montant resultat = remise.calculer(MAIN_OEUVRE, PIECES, client);
        assertEquals(MAIN_OEUVRE.getCentimes() + PIECES.getCentimes(), resultat.getCentimes());
    }

    // Vérifie qu'une remise supérieure au montant de la facture donne toujours zéro
    @Test
    void doit_ne_jamais_descendre_en_dessous_de_zero() {
        Remise remise = new Remise();
        Montant resultat = remise.appliquerGesteCommercial(FACTURE, GESTE_COMMERCIAL);
        assertEquals(0, resultat.getCentimes());
    }
}