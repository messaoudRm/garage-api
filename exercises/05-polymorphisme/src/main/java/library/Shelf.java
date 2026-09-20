package library;

/** L'étiquette du rayon. Cette signature ne change pas. */
public class Shelf {

    public String label(Item item) {
        if (item instanceof Book book) {
            return book.title() + " — " + book.pages() + " pages";
        }
        if (item instanceof Dvd dvd) {
            return dvd.title() + " — " + dvd.minutes() + " min";
        }
        throw new IllegalArgumentException("Type inconnu : " + item);
    }
}
