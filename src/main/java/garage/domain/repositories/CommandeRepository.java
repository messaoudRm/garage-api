package garage.domain.repositories;

import garage.domain.Commande;
import java.util.List;

public interface CommandeRepository {
  List<Commande> getEnCours();
}

