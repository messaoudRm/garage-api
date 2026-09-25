package garage.app.facturation;

import garage.domain.Montant;
import java.util.List;

public record DemandeRecapitulatifConstructeur(
        String immatriculation,
        List<PieceGarantie> pieces,
        Montant montantMainOeuvre
) {}
