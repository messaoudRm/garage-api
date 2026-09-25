package garage.domain;

import java.time.Duration;
import java.util.HashMap;

public record Operation(Atelier atelier, Duration temps, HashMap<Piece, Integer> pieces) {
  public Operation(Atelier atelier, Duration temps) {
    this(atelier, temps, new HashMap<Piece, Integer>());
  }

}
