package garage;

import garage.infra.DatabaseInitializer;

public class main {
  
  public static void main(String[] args) throws Exception {
    // Bootstrap de l'application 
    System.out.println("OK");

    // init db
    DatabaseInitializer.initialize();
  }

}

