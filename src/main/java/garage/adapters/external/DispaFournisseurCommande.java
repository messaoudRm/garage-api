package garage.adapters.external;

import garage.domain.FournisseurCommande;
import garage.domain.Commande;
import java.util.Map;

public class DispaFournisseurCommande implements FournisseurCommande {
  public boolean commandePiece(Commande commande) {
    return true;
  }
}
