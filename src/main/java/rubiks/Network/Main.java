package rubiks.Network;

import rubiks.Network.Server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException, InvocationTargetException, IllegalAccessException {

    System.out.println("Initialisation du serveur ");
    Server server = new Server();
    System.out.println("Serveur créé");
    System.out.println("Serveur ouvert");
    server.open();



    /*ServerSocket ss = null;
    Cube cube = new Cube();

    try{
       ss=new ServerSocket(2983);
      cube.scramble(6);
    } catch (IOException e) {
      System.exit(-1);
    }
    while(true){
      ClientWorker w;
      try{
        Socket connection = ss.accept();
        w = new ClientWorker(connection, cube);
        Thread t = new Thread(w);
        t.start();
      } catch (IOException e) {
        System.exit(-1);
      }
    }*/
  }
}
