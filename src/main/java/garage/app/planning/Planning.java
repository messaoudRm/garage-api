package garage.app.planning;

import garage.domain.Dossier;
import garage.domain.Operation;

import java.time.LocalTime;
import java.util.List;

public class Planning {

    private static final LocalTime FERMETURE = LocalTime.of(18, 0);
    private static final int PAS_RECHERCHE_MINUTES = 30;

    private final List<Creneau> occupes;
    private final List<Baie> baies;

    public Planning() {
        this.occupes = List.of();
        this.baies = List.of();
    }

    public Planning(Creneau... occupes) {
        this.occupes = List.of(occupes);
        this.baies = List.of();
    }

    public Planning(Baie... baies) {
        this.occupes = List.of();
        this.baies = List.of(baies);
    }

    public boolean estDisponible(Creneau demande) {
        return demande.estDansLesHorairesDuGarage()
                && occupes.stream()
                .allMatch(occupe -> demande.estDisponiblePendant(occupe));
    }

    public Creneau trouverPremierCreneauDisponible(
            LocalTime heureDebut,
            int dureeMinutes
    ) {
        LocalTime heure = heureDebut;

        while (!heure.isAfter(FERMETURE)) {
            LocalTime fin = heure.plusMinutes(dureeMinutes);

            Creneau demande = new Creneau(heure, fin);

            if (estDisponible(demande)) {
                return demande;
            }

            heure = heure.plusMinutes(PAS_RECHERCHE_MINUTES);
        }

        return null;
    }

    public Creneau trouverPremierCreneauPour(
            Dossier dossier,
            LocalTime heureDebut
    ) {
        Operation operation = dossier.operations().get(0);

        int dureeMinutes = (int) operation.temps().toMinutes();

        return trouverPremierCreneauDisponible(
                heureDebut,
                dureeMinutes
        );
    }

    public Resultat chercherCreneau(
            Dossier dossier,
            LocalTime heureDebut
    ) {
        Operation operation = dossier.operations().get(0);

        int dureeMinutes = (int) operation.temps().toMinutes();

        for (Baie baie : baies) {

            if (!baie.estCompatibleAvec(operation.atelier())) {
                continue;
            }

            if (!baie.peutAccueillir(dossier.vehicule())) {
                continue;
            }

            Creneau creneau = trouverPremierCreneauPourBaie(
                    baie,
                    heureDebut,
                    dureeMinutes
            );

            if (creneau != null) {
                return Resultat.disponible(baie, creneau);
            }
        }

        return Resultat.indisponible(
                "Aucun créneau compatible disponible aujourd'hui"
        );
    }

    private Creneau trouverPremierCreneauPourBaie(
            Baie baie,
            LocalTime heureDebut,
            int dureeMinutes
    ) {
        LocalTime heure = heureDebut;

        while (!heure.isAfter(FERMETURE)) {

            Creneau demande = new Creneau(
                    heure,
                    heure.plusMinutes(dureeMinutes)
            );

            if (baie.estDisponible(demande)) {
                return demande;
            }

            heure = heure.plusMinutes(PAS_RECHERCHE_MINUTES);
        }

        return null;
    }

    public record Resultat(
            Baie baie,
            Creneau creneau,
            String raison
    ) {

        public static Resultat disponible(
                Baie baie,
                Creneau creneau
        ) {
            return new Resultat(
                    baie,
                    creneau,
                    null
            );
        }

        public static Resultat indisponible(String raison) {
            return new Resultat(
                    null,
                    null,
                    raison
            );
        }

        public boolean estDisponible() {
            return baie != null && creneau != null;
        }
    }
}