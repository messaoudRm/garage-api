package garage; 

import garage.app.stock.ReserverPieceService;
import garage.adapters.http.ReserverPieceHandler;
import garage.app.stock.ConsommerPieceService;
import garage.adapters.http.ConsommerPieceHandler;
import garage.adapters.repositories.StockRepository;
import garage.adapters.repositories.DossierRepository;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class main {
  
  public static void main(String[] args) {
    // Bootstrap de l'application 
    DossierRepository dr = new DossierRepository();
    StockRepository sr = new StockRepository();

    ReserverPieceService rps = new ReserverPieceService(dr, sr);
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

