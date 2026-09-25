package garage.app.planning;

import garage.domain.Atelier;
import garage.domain.Dossier;
import garage.domain.Operation;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

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
        return trouverPremierCreneau(
                heureDebut,
                dureeMinutes,
                this::estDisponible
        );
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

        Baie meilleureBaie = null;
        Creneau meilleurCreneau = null;

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

            if (creneau == null) {
                continue;
            }

            if (meilleurCreneau == null
                    || creneau.getDebut().isBefore(meilleurCreneau.getDebut())) {

                meilleureBaie = baie;
                meilleurCreneau = creneau;
            }
        }

        if (meilleurCreneau != null) {
            return Resultat.disponible(
                    meilleureBaie,
                    meilleurCreneau
            );
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
        return trouverPremierCreneau(
                heureDebut,
                dureeMinutes,
                baie::estDisponible
        );
    }

    private Creneau trouverPremierCreneau(
            LocalTime heureDebut,
            int dureeMinutes,
            Predicate<Creneau> conditionDisponible
    ) {
        LocalTime heure = heureDebut;

        while (!heure.isAfter(FERMETURE)) {

            Creneau demande = new Creneau(
                    heure,
                    heure.plusMinutes(dureeMinutes)
            );

            if (conditionDisponible.test(demande)) {
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

    public Resultat chercherCreneau(
            Dossier dossier,
            DayOfWeek jour,
            LocalTime heureDebut
    ) {
        Operation operation = dossier.operations().get(0);

        int dureeMinutes = (int) operation.temps().toMinutes();

        Baie meilleureBaie = null;
        Creneau meilleurCreneau = null;

        for (Baie baie : baies) {

            if (!baie.estCompatibleAvec(operation.atelier())) {
                continue;
            }

            if (!baie.peutAccueillir(dossier.vehicule())) {
                continue;
            }

            Creneau creneau = trouverPremierCreneauPourBaie(
                    baie,
                    jour,
                    heureDebut,
                    dureeMinutes
            );

            if (creneau == null) {
                continue;
            }

            if (meilleurCreneau == null
                    || creneau.getDebut().isBefore(meilleurCreneau.getDebut())) {

                meilleureBaie = baie;
                meilleurCreneau = creneau;
            }
        }

        if (meilleurCreneau != null) {
            return Resultat.disponible(
                    meilleureBaie,
                    meilleurCreneau
            );
        }

        return Resultat.indisponible(
                "Aucun créneau compatible disponible aujourd'hui"
        );
    }

    private Creneau trouverPremierCreneauPourBaie(
            Baie baie,
            DayOfWeek jour,
            LocalTime heureDebut,
            int dureeMinutes
    ) {
        return trouverPremierCreneau(
                heureDebut,
                dureeMinutes,
                creneau -> estDisponiblePourJour(
                        baie,
                        jour,
                        creneau
                )
        );
    }

    private boolean estDisponiblePourJour(
            Baie baie,
            DayOfWeek jour,
            Creneau creneau
    ) {
        if (jour == DayOfWeek.WEDNESDAY
                && baie.estCompatibleAvec(Atelier.CARROSSERIE)
                && !creneau.getDebut().isBefore(LocalTime.of(14, 0))) {
            return false;
        }

        return baie.estDisponible(creneau);
    }
}