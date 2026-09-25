package garage.adapters.repositories;

import garage.domain.repositories.CommandeRepository;
import garage.domain.Commande;
import java.util.List;
import java.util.ArrayList;

public class FakeCommandeRepository implements CommandeRepository {
  public List<Commande> getEnCours() {
    return new ArrayList<Commande>();
  }
}
