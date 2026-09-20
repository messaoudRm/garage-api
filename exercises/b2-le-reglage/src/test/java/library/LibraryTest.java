package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Les règles de la médiathèque")
class LibraryTest {

    private final Library library = new Library();

    @Test
    void un_pret_dure_quinze_jours() {
        assertThat(library.daysAllowed()).isEqualTo(15);
    }

    @Test
    void on_prolonge_deux_fois_au_maximum() {
        assertThat(library.maxRenewals()).isEqualTo(2);
    }

    @Test
    void un_jour_de_retard_coute_dix_centimes() {
        assertThat(library.fineFor(3)).isEqualTo(30L);
    }
}
