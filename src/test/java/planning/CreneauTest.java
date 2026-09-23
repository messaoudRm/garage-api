package garage.planning;

import garage.domain.planning.Creneau;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CreneauTest {

    @Test
    void doit_refuser_un_creneau_qui_chevauche_un_creneau_occupe() {

        Creneau occupe = new Creneau(
                LocalTime.of(9, 0),
                LocalTime.of(11, 30)
        );

        Creneau demande = new Creneau(
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertFalse(demande.estDisponiblePendant(occupe));
    }
}