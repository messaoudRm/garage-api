package garage.app.stock;

import garage.domain.Dossier;
import garage.domain.Stock;

import java.util.Map;
import java.util.HashMap;

public class StockService {

  public static void reserverPiece(Dossier dossier, Stock stock) {
    dossier.operations().forEach((o) -> stock.reserverListPiece(o.pieces()));
  }

}
