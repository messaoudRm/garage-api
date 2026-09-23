package garage.domain;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.Duration;

import garage.domain.Client;
import garage.domain.Vehicule;
import garage.domain.Piece;
import garage.domain.Operation;
import garage.domain.Dossier;
import garage.domain.Stock;
import garage.app.stock.StockService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockServiceTest {
  
  @Test
  void doit_reserver_piece_du_dossier() {
    Client c = new Client("Redwane");
    Vehicule v = new Vehicule("1234", "Polo", 500);

    Piece p = new Piece("joint de culasse", Montant.euros(30));
    HashMap<Piece, Integer> pieces = new HashMap<>();
    pieces.put(p, 1);
    
    Operation o = new Operation(Atelier.MECANIQUE, Duration.ofMinutes(105), pieces);
    List<Operation> operations = new ArrayList<>();
    operations.add(o);

    Dossier dossier = new Dossier(c, v, operations);

    HashMap<Piece, Integer> initStock = new HashMap<>();
    initStock.put(p, 3);
    Stock stock = new Stock(initStock);

    StockService.reserverPiece(dossier, stock);

    assertEquals(stock.nombrePieceDisponnible(p), 2);
  }

}
