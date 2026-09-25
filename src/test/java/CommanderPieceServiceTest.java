package garage.app.stock;

import garage.domain.*;
import garage.domain.repositories.CommandeRepository;
import garage.domain.repositories.FournisseurRepository;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommanderPieceServiceTest {

  private Fournisseur fournisseurAvec(List<Commande> commandesCapturees) {
    FournisseurCommande fc = c -> { commandesCapturees.add(c); return true; };
    return new Fournisseur("Dispa", Duration.ofDays(2), fc);
  }

  @Test
  void doit_passer_commande_au_fournisseur() {
    Piece piece = new Piece("joint de culasse");
    Map<Piece, Integer> besoin = Map.of(piece, 3);

    List<Commande> commandesPassees = new ArrayList<>();
    Fournisseur fournisseur = fournisseurAvec(commandesPassees);

    FournisseurRepository fournisseurs = id -> fournisseur;
    CommandeRepository commandes = ArrayList::new;

    new CommanderPieceService(commandes, fournisseurs).executer(besoin);

    assertEquals(1, commandesPassees.size());
  }

  @Test
  void doit_inclure_les_bonnes_pieces_dans_la_commande() {
    Piece piece = new Piece("joint de culasse");
    Map<Piece, Integer> besoin = Map.of(piece, 5);

    List<Commande> commandesPassees = new ArrayList<>();
    Fournisseur fournisseur = fournisseurAvec(commandesPassees);

    FournisseurRepository fournisseurs = id -> fournisseur;
    CommandeRepository commandes = ArrayList::new;

    new CommanderPieceService(commandes, fournisseurs).executer(besoin);

    assertEquals(5, commandesPassees.get(0).pieces().get(piece));
  }

  @Test
  void doit_associer_le_bon_fournisseur_a_la_commande() {
    Piece piece = new Piece("joint de culasse");

    List<Commande> commandesPassees = new ArrayList<>();
    Fournisseur fournisseur = fournisseurAvec(commandesPassees);

    FournisseurRepository fournisseurs = id -> fournisseur;
    CommandeRepository commandes = ArrayList::new;

    new CommanderPieceService(commandes, fournisseurs).executer(Map.of(piece, 2));

    assertEquals(fournisseur, commandesPassees.get(0).fournisseur());
  }
}
