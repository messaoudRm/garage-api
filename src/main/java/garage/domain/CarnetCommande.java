package garage.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class CarnetCommande {
  private final List<Commande> encours;

  public CarnetCommande(List<Commande> ec) {
    encours = ec;
  }

  public boolean verifieDoublonsCommande(Piece p) {
    return encours.stream().anyMatch(c -> c.pieces().containsKey(p));
  }
  
  public Map<Piece, Integer> genereBesoin(Map<Piece, Integer> demande) {
    Map<Piece, Integer> besoin = new HashMap<>();

    demande.forEach((p, q) -> {
      if (verifieDoublonsCommande(p)) {
        besoin.put(p, q);
      }
    });
    
    return besoin;
  }

  public void enregistrerCommande(Commande commande) {
    encours.add(commande);
  }

}
