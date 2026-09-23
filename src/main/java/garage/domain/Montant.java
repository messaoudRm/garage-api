package garage.domain;

public final class Montant implements Comparable<Montant> {

  private final long centimes;

  public Montant(long centimes) {
    if (centimes < 0) {
      throw new IllegalArgumentException(
              "Un montant ne peut pas être négatif"
      );
    }

    this.centimes = centimes;
  }

  public static Montant euros(long euros) {
    return new Montant(euros * 100);
  }

  public static Montant centimes(long centimes) {
    return new Montant(centimes);
  }

  public long getCentimes() {
    return centimes;
  }

  public static Montant ajouter(Montant a, Montant b) {
    return new Montant(a.centimes + b.centimes);
  }

  public static Montant multiplierPar(Montant a, long quantite) {
    if (quantite < 0) {
      throw new IllegalArgumentException(
              "La quantité ne peut pas être négative"
      );
    }

    return new Montant(a.centimes * quantite);
  }

  @Override
  public int compareTo(Montant o) {
    return Long.compare(this.centimes, o.centimes);
  }
}