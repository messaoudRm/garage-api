package garage.app.stock;

import garage.domain.*;
import garage.domain.repositories.DossierRepository;
import garage.domain.repositories.StockRepository;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsommerPieceServiceTest {

  private Dossier dossierAvec(Piece piece, int quantite) {
    HashMap<Piece, Integer> pieces = new HashMap<>();
    pieces.put(piece, quantite);
    Operation op = new Operation(Atelier.MECANIQUE, Duration.ofMinutes(60), pieces);
    return new Dossier(new Client("Test"), new Vehicule("", "", 1000), List.of(op));
  }

  @Test
  void doit_consommer_les_pieces_des_operations_du_dossier() {
    Piece piece = new Piece("joint de culasse");
    Dossier dossier = dossierAvec(piece, 1);

    // rayon=3, dispo=2 → 1 piece réservée
    HashMap<Piece, Integer> rayon = new HashMap<>();
    HashMap<Piece, Integer> dispo = new HashMap<>();
    rayon.put(piece, 3);
    dispo.put(piece, 2);
    Stock stock = new Stock(rayon, dispo);

    DossierRepository dossiers = id -> dossier;
    StockRepository stocks = () -> stock;

    new ConsommerPieceService(dossiers, stocks).executer(1);

    assertEquals(2, stock.nombrePieceRayon(piece));
  }

  @Test
  void doit_throw_si_piece_non_reservee() {
    Piece piece = new Piece("joint de culasse");
    Dossier dossier = dossierAvec(piece, 1);

    HashMap<Piece, Integer> init = new HashMap<>();
    init.put(piece, 3);
    Stock stock = new Stock(init); // rien de réservé

    DossierRepository dossiers = id -> dossier;
    StockRepository stocks = () -> stock;

    assertThrows(IllegalStateException.class,
        () -> new ConsommerPieceService(dossiers, stocks).executer(1));
  }
}
