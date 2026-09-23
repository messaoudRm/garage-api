import garage.domain.Montant;
import garage.domain.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehiculeTest {

    @Test
    void doit_creer_un_vehicule() {
        Vehicule vehicule = new Vehicule(
                "AB-123-CD",
                "Peugeot 308",
                1500
        );

        assertEquals("AB-123-CD", vehicule.immatriculation());
        assertEquals("Peugeot 308", vehicule.modele());
        assertEquals(1500, vehicule.poidsKg());
    }
}