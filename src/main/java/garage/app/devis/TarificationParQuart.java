package garage.app.devis;
import garage.domain.Montant;
import java.time.Duration;
public abstract class TarificationParQuart implements Tarification {

    private final long tarifQuartHeure;

    protected TarificationParQuart(long tarifQuartHeure) {
        this.tarifQuartHeure = tarifQuartHeure;
    }

    @Override
    public Montant calculer(Duration duree) {

        long minutes = duree.toMinutes();
        long nombreDeQuarts = minutes / 15;

        if (minutes % 15 != 0) {
            nombreDeQuarts++;
        }

        return Montant.centimes(
                nombreDeQuarts * tarifQuartHeure
        );
    }
}