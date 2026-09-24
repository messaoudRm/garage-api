package garage.app.stock;

import garage.domain.Dossier;
import garage.domain.Stock;
import garage.adapters.repositories.DossierRepository;
import garage.adapters.repositories.StockRepository;

import java.util.Map;
import java.util.HashMap;

public class ReserverPieceService {

  private final DossierRepository dossiers;
  private final StockRepository stocks;

  public ReserverPieceService(DossierRepository d, StockRepository s) {
    dossiers = d;
    stocks = s;
  }

  public void executer(Integer dossierId) {
    Dossier dossier = dossiers.get(dossierId);
    Stock stock = stocks.get();
    dossier.operations().forEach((o) -> stock.reserverListPiece(o.pieces()));
  }

}
