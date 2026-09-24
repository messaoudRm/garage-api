package devis;

import garage.app.devis.*;
import garage.domain.Montant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DevisTest {

    @Test
    @DisplayName("US-1 1h45 de main-d'œuvre et quatre plaquettes coûtent 284,10 €")
    void devis_additionne_main_oeuvre_et_pieces() {
        MainOeuvreMecanique mainOeuvreMecanique = new MainOeuvreMecanique() ;
        LignePiece plaquettes = new LignePiece("plaquettes", Montant.centimes(4390),4) ;
        Montant prixMainOeuvre = mainOeuvreMecanique.totalMainOeuvre(Duration.ofMinutes(105));
        Montant pieces = plaquettes.montant();

        Devis devis = new Devis(prixMainOeuvre , pieces) ;


assertThat(devis.total().getCentimes()).isEqualTo(28410) ;
    }



    @Test
    @DisplayName("US-1 · le détail d'un devis rendu ne peut pas être modifié")
    void le_detail_du_devis_ne_change_pas_si_la_liste_source_est_modifiee() {

        List<LigneDevis> lignes = new ArrayList<>();

        lignes.add(
                new LigneDevis(
                        "Main-d'œuvre mécanique 1h45",
                        Montant.centimes(10850)
                )
        );

        lignes.add(
                new LigneDevis(
                        "4 plaquettes à 43,90 €",
                        Montant.centimes(17560)
                )
        );

        Devis devis = new Devis(lignes);

        // Le devis est maintenant rendu : 284,10 €

        lignes.add(
                new LigneDevis(
                        "1 pneu à 10 €",
                        Montant.centimes(1000)
                )
        );

        // La modification de la liste d'origine
        // ne doit PAS modifier le devis déjà rendu.
        assertThat(devis.total().getCentimes())
                .isEqualTo(28410);
    }



    @Test
    @DisplayName("US-2 · 1 h de mécanique et 1 h de carrosserie coûtent 140,00 €")
    void une_heure_mecanique_et_une_heure_carrosserie_coutent_140_euros() {

        MainOeuvreMecanique mecanique = new MainOeuvreMecanique();
        TarificationCarrosserie carrosserie = new TarificationCarrosserie();

        Montant prixMecanique =
                mecanique.totalMainOeuvre(Duration.ofHours(1));

        Montant prixCarrosserie =
                carrosserie.calculer(Duration.ofHours(1));

        Montant total =
                Montant.ajouter(prixMecanique, prixCarrosserie);

        assertThat(total.getCentimes())
                .isEqualTo(14000);
    }
}
