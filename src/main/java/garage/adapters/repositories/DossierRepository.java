package garage.adapters.repositories;

import garage.domain.Dossier;
import garage.domain.Atelier;
import garage.domain.Operation;
import garage.domain.Piece;
import garage.domain.Vehicule;
import garage.domain.Client;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class DossierRepository {
  
  public Dossier get(Integer id) {
    HashMap<Piece, Integer> pieces = new HashMap<>();
    pieces.put(new Piece("joint de culasse"), 1);
    Operation o = new Operation(Atelier.MECANIQUE, Duration.ofMinutes(105), pieces);
    List<Operation> ops = List.of(o);

    return new Dossier(new Client("Redwane"), new Vehicule("", "", 1500), ops);
  }
}
