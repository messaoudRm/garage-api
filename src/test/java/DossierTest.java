package garage.domain;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DossierTest {

    @Test
    void doit_creer_un_dossier() {
        Vehicule vehicule = new Vehicule(
                "AB-123-CD",
                "Peugeot 308",
                1500
        );

        Operation operation = new Operation(
                Atelier.MECANIQUE,
                Duration.ofMinutes(105)
        );

        Client client = new Client("Yassine");

        Dossier dossier = new Dossier(
                client,
                vehicule,
                List.of(operation)
        );

        assertEquals(client, dossier.client());
        assertEquals(vehicule, dossier.vehicule());
        assertEquals(List.of(operation), dossier.operations());
    }

    @Test
    void ne_doit_pas_pouvoir_modifier_les_operations_du_dossier() {
        Vehicule vehicule = new Vehicule(
                "AB-123-CD",
                "porsche 911 gt3 rs",
                1500
        );

        Operation operation = new Operation(Atelier.MECANIQUE,
                Duration.ofMinutes(105)
        );

        Dossier dossier = new Dossier(
                new Client("Yassine"),
                vehicule,
                List.of(operation)
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> dossier.operations().add(
                        new Operation(Atelier.CARROSSERIE, Duration.ofMinutes(60))
                )
        );
    }
}