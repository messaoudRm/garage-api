package garage.domain;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockTest {
  @Test
  void doit_renvoyer_zero_si_pas_de_piece_dispo() {
    Stock stock = new Stock(new HashMap<Piece, Integer>());
    Piece piece = new Piece("joint de culasse", Montant.euros(30));

    assertEquals(stock.nombrePieceDisponnible(piece), 0);
  }
  
  @Test
  void doit_renvoyer_nombre_piece_dispo() {
    HashMap<Piece, Integer> stockInit = new HashMap<Piece, Integer>();
    Piece piece = new Piece("joint de culasse", Montant.euros(30));
    
    stockInit.put(piece, 3);

    Stock stock = new Stock(stockInit);

    assertEquals(stock.nombrePieceDisponnible(piece), 3); 
  }
  
  @Test
  void doit_reserver_piece() {
    HashMap<Piece, Integer> stockInit = new HashMap<Piece, Integer>();
    Piece piece = new Piece("joint de culasse", Montant.euros(30));
    
    stockInit.put(piece, 3);

    Stock stock = new Stock(stockInit);

    stock.reserverPiece(piece, 1);

    assertEquals(stock.nombrePieceDisponnible(piece), 2); 
  }

  @Test
  void doit_throw_error_reserver_piece() {
    HashMap<Piece, Integer> stockInit = new HashMap<Piece, Integer>();
    Piece piece = new Piece("joint de culasse", Montant.euros(30));
    
    stockInit.put(piece, 3);

    Stock stock = new Stock(stockInit);

    assertThrows(IllegalStateException.class, () -> stock.reserverPiece(piece, 4));
  }


  @Test
  void doit_reserver_list_piece() {
    HashMap<Piece, Integer> stockInit = new HashMap<Piece, Integer>();
    Piece piece = new Piece("joint de culasse", Montant.euros(30));
    Piece piece2 = new Piece("démarreur", Montant.euros(200));

    stockInit.put(piece, 3);
    
    stockInit.put(piece2, 4);

    Stock stock = new Stock(stockInit);

    stock.reserverListPiece(stockInit);

    assertEquals(stock.nombrePieceDisponnible(piece), 0);
    assertEquals(stock.nombrePieceDisponnible(piece2), 0);
  }

  @Test
  void doit_consommer_piece() {
    HashMap<Piece, Integer> stockInit = new HashMap<Piece, Integer>();
    Piece piece = new Piece("joint de culasse", Montant.euros(30));
    
    stockInit.put(piece, 3);

    Stock stock = new Stock(stockInit);

    stock.consommerPiece(piece, 1);

    assertEquals(stock.nombrePieceDisponnible(piece), 2); 
  }

  @Test
  void doit_throw_error_consommer_piece() {
    HashMap<Piece, Integer> stockInit = new HashMap<Piece, Integer>();
    Piece piece = new Piece("joint de culasse", Montant.euros(30));
    
    stockInit.put(piece, 3);

    Stock stock = new Stock(stockInit);

    assertThrows(IllegalStateException.class, () -> stock.consommerPiece(piece, 4));
  }


} 
