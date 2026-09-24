package garage.domain.repositories;

import garage.domain.Dossier;

public interface DossierRepository {
  Dossier get(Integer id);
}
