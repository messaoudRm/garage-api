package garage.domain;

import java.time.LocalDateTime;
import java.util.Map;

public record Commande(Map<Piece, Integer> pieces, Fournisseur fournisseur, LocalDateTime dateLivraison) {

  public Commande(Map<Piece, Integer> p, Fournisseur f) {
    this(p, f, LocalDateTime.now().plus(f.delaiLivraison()));
  }
}
