package garage.app.facturation;

import garage.domain.Client;
import garage.domain.Montant;

public class Remise {

    // Ordre fixé une seule fois, ici : professionnel avant habitué (cf. critère 256,50€)
    public Montant calculer(Montant mainOeuvre, Montant pieces, Client client) {
        Montant moApresPro = new RemiseProfessionnelle().appliquer(mainOeuvre, client);
        Montant total = Montant.centimes(moApresPro.getCentimes() + pieces.getCentimes());
        return new RemiseHabitue().appliquer(total, client);
    }

    public Montant appliquerGesteCommercial(Montant montant, Montant geste) {
        long resultat = montant.getCentimes() - geste.getCentimes();
        return Montant.centimes(Math.max(resultat, 0));
    }
}