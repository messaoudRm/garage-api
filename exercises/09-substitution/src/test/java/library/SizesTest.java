package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Redimensionner une étagère")
class SizesTest {

    @Test
    void cinq_sur_quatre_font_vingt() {
        assertThat(Sizes.resizeTo5By4(new Rectangle())).isEqualTo(20);
    }
}
