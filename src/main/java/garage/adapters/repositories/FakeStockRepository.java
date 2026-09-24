package garage.adapters.repositories;

import garage.domain.Stock;
import garage.domain.Piece;
import garage.domain.repositories.StockRepository;


import java.util.HashMap;

public class FakeStockRepository implements StockRepository {
  
  public Stock get() {
    HashMap<Piece, Integer> init = new HashMap<>();
    init.put(new Piece("joint de culasse"), 4);
    return new Stock(init);
  }
}
