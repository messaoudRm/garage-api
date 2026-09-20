package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("L'étiquette du rayon")
class ShelfTest {

    private final Shelf shelf = new Shelf();

    @Test
    void un_livre_annonce_ses_pages() {
        assertThat(shelf.label(new Book("Dune", 412))).isEqualTo("Dune — 412 pages");
    }

    @Test
    void un_dvd_annonce_sa_duree() {
        assertThat(shelf.label(new Dvd("Alien", 117))).isEqualTo("Alien — 117 min");
    }

    @Test
    void chaque_document_garde_son_titre() {
        assertThat(shelf.label(new Book("Dune", 412))).startsWith("Dune");
    }
}
