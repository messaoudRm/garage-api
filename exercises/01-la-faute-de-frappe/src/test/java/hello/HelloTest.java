package hello;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Le programme salue")
class HelloTest {

    @Test
    void il_dit_bonjour() {
        assertThat(Hello.greeting()).isEqualTo("Bonjour");
    }
}
