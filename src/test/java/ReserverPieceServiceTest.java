package garage.app.stock;

import garage.domain.*;
import garage.domain.repositories.CommandeRepository;
import garage.domain.repositories.DossierRepository;
import garage.domain.repositories.FournisseurRepository;
import garage.domain.repositories.StockRepository;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReserverPieceServiceTest {

  private Dossier dossierAvec(Piece piece, int quantite) {
    HashMap<Piece, Integer> pieces = new HashMap<>();
    pieces.put(piece, quantite);
    Operation op = new Operation(Atelier.MECANIQUE, Duration.ofMinutes(60), pieces);
    return new Dossier(new Client("Test"), new Vehicule("", "", 1000), List.of(op));
  }

  private ReserverPieceService serviceAvec(
      DossierRepository dossiers,
      StockRepository stocks,
      CommandeRepository commandes,
      List<Commande> commandesCapturees) {

    FournisseurCommande fc = c -> { commandesCapturees.add(c); return true; };
    Fournisseur fournisseur = new Fournisseur("Dispa", Duration.ofDays(2), fc);
    FournisseurRepository fournisseurs = id -> fournisseur;

    CommanderPieceService cps = new CommanderPieceService(commandes, fournisseurs);
    return new ReserverPieceService(dossiers, stocks, commandes, cps);
  }

  @Test
  void doit_reserver_les_pieces_du_dossier_dans_le_stock() {
    Piece piece = new Piece("joint de culasse");
    Dossier dossier = dossierAvec(piece, 2);

    HashMap<Piece, Integer> init = new HashMap<>();
    init.put(piece, 5);
    Stock stock = new Stock(init);

    List<Commande> commandesCapturees = new ArrayList<>();
    ReserverPieceService service = serviceAvec(
        id -> dossier,
        () -> stock,
        ArrayList::new,
        commandesCapturees);

    service.executer(1);

    assertEquals(3, stock.nombrePieceDisponnible(piece));
  }

  @Test
  void doit_ne_pas_passer_commande_si_pas_de_pieces_manquantes() {
    // seuil=4, cible=12 → stock=5 (5 >= 4 donc pas manquant)
    Piece piece = new Piece("joint de culasse");
    Dossier dossier = dossierAvec(piece, 1);

    HashMap<Piece, Integer> init = new HashMap<>();
    init.put(piece, 5);
    Stock stock = new Stock(init);

    List<Commande> commandesCapturees = new ArrayList<>();
    ReserverPieceService service = serviceAvec(
        id -> dossier,
        () -> stock,
        ArrayList::new,
        commandesCapturees);

    service.executer(1);

    assertTrue(commandesCapturees.isEmpty());
  }

  @Test
  void doit_passer_commande_si_piece_manquante_et_deja_commandee() {
    // seuil=4, cible=12 → stock=2 (2 < 4 donc manquant)
    // + commande en cours pour cette pièce → besoin non vide → commande passée
    Piece piece = new Piece("joint de culasse");
    Dossier dossier = dossierAvec(piece, 1);

    HashMap<Piece, Integer> init = new HashMap<>();
    init.put(piece, 2);
    Stock stock = new Stock(init);

    Fournisseur f = new Fournisseur("Dispa", Duration.ofDays(2), c -> true);
    Commande commandeEnCours = new Commande(Map.of(piece, 5), f);
    CommandeRepository commandes = () -> new ArrayList<>(List.of(commandeEnCours));

    List<Commande> commandesCapturees = new ArrayList<>();
    ReserverPieceService service = serviceAvec(
        id -> dossier,
        () -> stock,
        commandes,
        commandesCapturees);

    service.executer(1);

    assertEquals(1, commandesCapturees.size());
  }
}
