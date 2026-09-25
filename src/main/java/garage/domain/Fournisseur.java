package garage.domain;

import java.time.Duration;

public record Fournisseur(String nom, Duration delaiLivraison, FournisseurCommande fc) {
}
