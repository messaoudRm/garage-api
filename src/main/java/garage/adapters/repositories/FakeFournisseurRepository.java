package garage.adapters.repositories;

import garage.domain.Fournisseur;
import garage.adapters.external.DispaFournisseurCommande;
import garage.domain.repositories.FournisseurRepository;

import java.time.Duration;

public class FakeFournisseurRepository implements FournisseurRepository {
  public Fournisseur get(Integer id) {
    return new Fournisseur("Dispa", Duration.ofDays(2), new DispaFournisseurCommande());
  }
}
