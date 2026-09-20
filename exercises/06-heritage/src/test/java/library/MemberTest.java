package library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Un adhérent a un badge")
class MemberTest {

    @Test
    void le_badge_est_le_nom_en_majuscules() {
        assertThat(new Member("Dupont").badge()).isEqualTo("DUPONT");
    }
}
