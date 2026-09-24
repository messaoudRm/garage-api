package garage.app.facturation;

import garage.domain.Client;
import garage.domain.Montant;

public class Remise {

    public Montant calculer(Montant mainOeuvre, Montant pieces, Client client) {
        long mo = mainOeuvre.getCentimes();

        if (client.professionnel()) {
            mo = mo * 85 / 100;
        }

        long total = mo + pieces.getCentimes();

        if (client.habitue()) {
            total = total * 95 / 100;
        }

        return Montant.centimes(total);
    }

    public Montant appliquerGesteCommercial(Montant montant, Montant geste) {
        long resultat = montant.getCentimes() - geste.getCentimes();
        return Montant.centimes(Math.max(resultat, 0));
    }
}