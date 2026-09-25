package garage.domain;

public record Piece(String reference, Integer seuil, Integer cible) {
  public Piece(String ref) {
    this(ref, 4, 12);
  }
}
