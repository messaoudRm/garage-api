package garage; 

import garage.app.stock.ReserverPieceService;
import garage.adapters.http.ReserverPieceHandler;
import garage.app.stock.ConsommerPieceService;
import garage.adapters.http.ConsommerPieceHandler;
import garage.adapters.repositories.FakeStockRepository;
import garage.domain.repositories.StockRepository;
import garage.adapters.repositories.FakeDossierRepository;
import garage.domain.repositories.DossierRepository;
import garage.adapters.repositories.FakeFournisseurRepository;
import garage.domain.repositories.FournisseurRepository;
import garage.adapters.repositories.FakeCommandeRepository;
import garage.domain.repositories.CommandeRepository;
import garage.app.stock.CommanderPieceService;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class main {
  
  public static void main(String[] args) {
    // Bootstrap de l'application 
    DossierRepository dr = new FakeDossierRepository();
    StockRepository sr = new FakeStockRepository();
    CommandeRepository cr = new FakeCommandeRepository();
    FournisseurRepository fr = new FakeFournisseurRepository();

    CommanderPieceService cmdps = new CommanderPieceService(cr, fr);
    ReserverPieceService rps = new ReserverPieceService(dr, sr, cr, cmdps);
    ConsommerPieceService cps = new ConsommerPieceService(dr, sr);
    
    ReserverPieceHandler rph = new ReserverPieceHandler(rps);
    ConsommerPieceHandler cph = new ConsommerPieceHandler(cps);

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      // Ajout des handler via la methode server.createContext
      server.createContext("/reserver", rph);
      server.createContext("/consommer", cph);

      server.start();
      Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(5)));
      System.out.println("http://localhost:8080");

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Fail to launch server : " + e.getMessage());
    }
  }

}

