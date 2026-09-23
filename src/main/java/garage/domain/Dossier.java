package garage.domain;

import java.util.List;

public record Dossier(Client client, Vehicule vehicule, List<Operation> operations) {
}