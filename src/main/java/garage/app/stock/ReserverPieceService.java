package garage.app.stock;

import garage.domain.Dossier;
import garage.domain.Stock;
import garage.domain.CarnetCommande;
import garage.domain.Piece;
import garage.domain.repositories.DossierRepository;
import garage.domain.repositories.StockRepository;
import garage.domain.repositories.CommandeRepository;

import java.util.Map;
import java.util.HashMap;

public class ReserverPieceService {

  private final DossierRepository dossiers;
  private final StockRepository stocks;
  private final CommandeRepository commandes;
  private final CommanderPieceService cps;

  public ReserverPieceService(DossierRepository d, StockRepository s, CommandeRepository c, CommanderPieceService cmdps) {
    dossiers = d;
    stocks = s;
    commandes = c;
    cps = cmdps;
  }

  public void executer(Integer dossierId) {
    Dossier dossier = dossiers.get(dossierId);
    Stock stock = stocks.get();
    dossier.operations().forEach((o) -> stock.reserverListPiece(o.pieces()));
    CarnetCommande cc = new CarnetCommande(commandes.getEnCours());
    Map<Piece, Integer> besoin = cc.genereBesoin(stock.piecesManquantes());
    if (!besoin.isEmpty()) 
      cps.executer(besoin);
  }

}
