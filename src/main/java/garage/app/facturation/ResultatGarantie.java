package garage.app.facturation;

import garage.domain.Montant;

public record ResultatGarantie(Montant montantFacture, String motif) {}
