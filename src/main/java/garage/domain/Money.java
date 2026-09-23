package garage;

public class Money {
  private long cents;

  public Money(long cents) {
    cents = cents;
  }

  public long centimes() {
    return this.cents;
  }

  public long euros() {
    return this.cents / 100;
  }

  public void add(long cents) {
    this.cents += cents;
  }

  public void add(Money b) {
    this.cents += b.cents;
  }

  public void sub(long cents) {
    if (this.cents - cents < 0) {
      //throw new IllegaleStateException("Negative substraction forbidden");
      System.out.println("error");
    }

    this.cents -= cents;
  }

  public void sub(Money b) {
    if (this.cents - b.centimes() < 0) {
      //throw new IllegaleStateException("Negative substraction forbidden");
      System.out.println("error");
    }

    this.cents -= b.centimes();
  }

  public void mult(long cents) {
    this.cents *= cents;
  }

  public void mult(Money b) {
    this.cents *= b.centimes();
  }

  static int comp(Money a, Money b) {
    if (a.centimes() > b.centimes())
      return 1;

    if (a.centimes() < b.centimes())
      return 2;

    return 0;
  }
}
