package garage.domain;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarnetCommandeTest {

  private Fournisseur fournisseur() {
    return new Fournisseur("Fournisseur A", Duration.ofDays(3), c -> true);
  }

  @Test
  void doit_retourner_false_si_carnet_vide() {
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>());
    Piece piece = new Piece("filtre à huile");

    assertFalse(carnet.verifieDoublonsCommande(piece));
  }

  @Test
  void doit_retourner_false_si_piece_absente_des_commandes_en_cours() {
    Piece piece = new Piece("filtre à huile");
    Piece autrepiece = new Piece("bougie");

    Commande commande = new Commande(Map.of(autrepiece, 2), fournisseur());
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>(List.of(commande)));

    assertFalse(carnet.verifieDoublonsCommande(piece));
  }

  @Test
  void doit_retourner_true_si_piece_deja_en_commande() {
    Piece piece = new Piece("filtre à huile");

    Commande commande = new Commande(Map.of(piece, 2), fournisseur());
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>(List.of(commande)));

    assertTrue(carnet.verifieDoublonsCommande(piece));
  }

  @Test
  void doit_retourner_map_vide_si_demande_vide() {
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>());

    Map<Piece, Integer> besoin = carnet.genereBesoin(new HashMap<>());

    assertTrue(besoin.isEmpty());
  }

  @Test
  void doit_inclure_piece_deja_en_commande_dans_besoin() {
    Piece piece = new Piece("filtre à huile");

    Commande commande = new Commande(Map.of(piece, 2), fournisseur());
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>(List.of(commande)));

    Map<Piece, Integer> besoin = carnet.genereBesoin(Map.of(piece, 5));

    assertEquals(5, besoin.get(piece));
  }

  @Test
  void doit_exclure_piece_non_commandee_du_besoin() {
    Piece piece = new Piece("filtre à huile");
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>());

    Map<Piece, Integer> besoin = carnet.genereBesoin(Map.of(piece, 5));

    assertFalse(besoin.containsKey(piece));
  }

  @Test
  void doit_inclure_uniquement_les_pieces_deja_commandees() {
    Piece pieceCommandee = new Piece("filtre à huile");
    Piece pieceNonCommandee = new Piece("bougie");

    Commande commande = new Commande(Map.of(pieceCommandee, 2), fournisseur());
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>(List.of(commande)));

    Map<Piece, Integer> demande = new HashMap<>();
    demande.put(pieceCommandee, 5);
    demande.put(pieceNonCommandee, 3);

    Map<Piece, Integer> besoin = carnet.genereBesoin(demande);

    assertTrue(besoin.containsKey(pieceCommandee));
    assertFalse(besoin.containsKey(pieceNonCommandee));
  }

  @Test
  void doit_enregistrer_une_commande_dans_le_carnet() {
    Piece piece = new Piece("filtre à huile");
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>());

    assertFalse(carnet.verifieDoublonsCommande(piece));

    Commande commande = new Commande(Map.of(piece, 2), fournisseur());
    carnet.enregistrerCommande(commande);

    assertTrue(carnet.verifieDoublonsCommande(piece));
  }

  @Test
  void doit_enregistrer_plusieurs_commandes() {
    Piece piece1 = new Piece("filtre à huile");
    Piece piece2 = new Piece("bougie");
    CarnetCommande carnet = new CarnetCommande(new ArrayList<>());

    carnet.enregistrerCommande(new Commande(Map.of(piece1, 2), fournisseur()));
    carnet.enregistrerCommande(new Commande(Map.of(piece2, 4), fournisseur()));

    assertTrue(carnet.verifieDoublonsCommande(piece1));
    assertTrue(carnet.verifieDoublonsCommande(piece2));
  }
}
