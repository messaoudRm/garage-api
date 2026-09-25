package garage.app.stock;

import garage.domain.Dossier;
import garage.domain.Stock;
import garage.domain.repositories.DossierRepository;
import garage.domain.repositories.StockRepository;

import java.util.Map;
import java.util.HashMap;

public class ConsommerPieceService {

  private final DossierRepository dossiers;
  private final StockRepository stocks;

  public ConsommerPieceService(DossierRepository d, StockRepository s) {
    dossiers = d;
    stocks = s;
  }

  public void executer(Integer dossierId) {
    Dossier dossier = dossiers.get(dossierId);
    Stock stock = stocks.get();
    dossier.operations().forEach((o) -> stock.consommerListPiece(o.pieces()));
  }

}
