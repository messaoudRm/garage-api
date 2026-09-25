package garage.app.facturation;

import garage.domain.Montant;

public record LigneRecapitulatifConstructeur(
        String immatriculation,
        String reference,
        Montant montant
) {}
