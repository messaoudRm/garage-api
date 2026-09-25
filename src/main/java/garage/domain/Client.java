package garage.domain;

public record Client(String nom, boolean professionnel, boolean habitue) {
  public Client(String nom) {
    this(nom, false, false);
  }
}
