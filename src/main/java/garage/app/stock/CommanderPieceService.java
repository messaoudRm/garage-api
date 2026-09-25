package garage.app.stock;

import garage.domain.repositories.StockRepository;
import garage.domain.repositories.CommandeRepository;
import garage.domain.repositories.FournisseurRepository;
import garage.domain.Stock;
import garage.domain.Commande;
import garage.domain.Fournisseur;
import garage.domain.Piece;

import java.util.Map;

public class CommanderPieceService {
  
  private final CommandeRepository commandes;
  private final FournisseurRepository fournisseurs;

  public CommanderPieceService(CommandeRepository c, FournisseurRepository f) {
    commandes = c;
    fournisseurs = f;
  }

  public void executer(Map<Piece, Integer> besoin) {

    Fournisseur fournisseur = fournisseurs.get(0);
    Commande commande = new Commande(besoin, fournisseur);

    fournisseur.fc().commandePiece(commande);

  }

}
