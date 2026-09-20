package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Une étagère carrée")
class SquareTest {

    @Test
    void trois_de_cote_font_neuf() {
        assertThat(new Square(3).area()).isEqualTo(9);
    }
}
