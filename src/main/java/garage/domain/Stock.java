package garage.domain;

import java.util.Map;
import java.util.HashMap;

public final class Stock {
  
  private final HashMap<Piece, Integer> rayon;
  private final HashMap<Piece, Integer> disponnible;

  public Stock(HashMap<Piece, Integer> init) {
    rayon = new HashMap<Piece, Integer>(init);
    disponnible = new HashMap<Piece, Integer>(init);
  }

  public Stock(HashMap<Piece, Integer> initRayon, HashMap<Piece, Integer> initDispo) {
    rayon = new HashMap<Piece, Integer>(initRayon);
    disponnible = new HashMap<Piece, Integer>(initDispo);
  }
  
  public Integer nombrePieceDisponnible(Piece p) {
    return disponnible.getOrDefault(p, 0);
  }

  public Integer nombrePieceRayon(Piece p) {
    return rayon.getOrDefault(p, 0);
  }

  public Integer nombrePieceReserve(Piece p) {
    return rayon.getOrDefault(p, 0) - disponnible.getOrDefault(p, 0);
  }

  public void reserverPiece(Piece p, Integer quantite) {
    if (quantite > nombrePieceDisponnible(p)) {
      throw new IllegalStateException("stock insuffisant");
    }

    disponnible.put(p, nombrePieceDisponnible(p) - quantite);
  }

  public void reserverListPiece(Map<Piece, Integer> demande) {
    demande.forEach((p, q) -> {
      if (q > nombrePieceDisponnible(p)) {
            throw new IllegalStateException("stock insuffisant : " + p);
        }
    });

    demande.forEach(this::reserverPiece);
  }
  
  public void consommerPiece(Piece p, Integer quantite) {
    if (quantite > nombrePieceReserve(p)) {
      throw new IllegalStateException("pas assez de piece reservé");
    }

    rayon.put(p, nombrePieceRayon(p) - quantite);
  }
  
  public void consommerListPiece(Map<Piece, Integer> demande) {
    demande.forEach((p, q) -> {
      if (q > nombrePieceReserve(p)) {
            throw new IllegalStateException("pas assez de piece reservé : " + p);
        }
    });

    demande.forEach(this::consommerPiece);
  }

  public void ajouterPiece(Piece p, Integer q) {
    rayon.put(p, nombrePieceRayon(p) + q);
    disponnible.put(p, nombrePieceDisponnible(p) + q);
  }

  public void ajouterListPiece(Map<Piece, Integer> demande) {
    demande.forEach(this::ajouterPiece);
  }
  
  public Map<Piece, Integer> piecesManquantes() {
    Map<Piece, Integer> pieces = new HashMap<>();

    rayon.forEach((p, q) -> {
      if (q < p.seuil()) {
        pieces.put(p, p.cible() - q);
      }
    });

    return pieces;
  }

}
