package garage.domain;

import java.util.Map;
import java.util.HashMap;

public final class Stock {
  
  private final HashMap<Piece, Integer> rayon;
  private final HashMap<Piece, Integer> disponnible;

  public Stock(HashMap<Piece, Integer> init) {
    rayon = init;
    disponnible = init;
  }
  
  public Integer nombrePieceDisponnible(Piece p) {
    return disponnible.getOrDefault(p, 0);
  }

  public Integer nombrePieceRayon(Piece p) {
    return rayon.getOrDefault(p, 0);
  }

  public void reserverPiece(Piece p, Integer quantite) {
    Integer piecesDisponnibles = disponnible.getOrDefault(p, 0);
    
    if (quantite > piecesDisponnibles) {
      throw new IllegalStateException("stock insuffisant");
    }

    disponnible.put(p, piecesDisponnibles - quantite);
  }

  public void reserverListPiece(Map<Piece, Integer> list) {
    list.forEach((k, v) -> this.reserverPiece(k, v));
  }
  
  public void consommerPiece(Piece p, Integer quantite) {
    Integer piecesEnRayon = rayon.getOrDefault(p, 0);
    Integer piecesDisponnibles = disponnible.getOrDefault(p, 0);

    if (quantite > piecesEnRayon || quantite > piecesDisponnibles) {
      throw new IllegalStateException("desynchronisation du stock");
    }

    rayon.put(p, piecesEnRayon - quantite);
    disponnible.put(p, piecesDisponnibles - quantite);
  }
  
}
