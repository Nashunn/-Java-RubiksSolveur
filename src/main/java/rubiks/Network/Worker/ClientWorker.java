package rubiks.Network.Worker;

import rubiks.Model.Cube;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Timothé PARDIEU
 * rubiks.Network
 * Created on - 08/06/2018 (01:42)
 * Build for project rubiks_boullet_pardieu
 */
public class ClientWorker implements Runnable {

  private static int count = 0;
  List<String> retMoves = new ArrayList<>();
  private Socket connexion = null;
  private PrintWriter writer = null;
  private BufferedInputStream reader = null;
  private String name = "Client-";

  public ClientWorker(String host, int port) {
    name += ++count;
    try {
      connexion = new Socket(host, port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public ClientWorker(Socket connection, Cube cube) {
    this.connexion = connection;
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.println("Hello my Worker");
    System.out.println("Please, enter the IP of your related Server :");
    String ip = input.next();
    Thread t = new Thread(new ClientWorker(ip, 2983));
    t.start();
  }

  /**
   * Run the Thread
   */
  public void run() {
    try {
      System.out.println("Worker go !! :D ");
      OutputStream os = connexion.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os);
      BufferedWriter bw = new BufferedWriter(osw);

      String type = "SLAVE";

      String sendMessage = type + "\n";
      bw.write(sendMessage);
      bw.flush();
      System.out.println("Message sent to the server : " + sendMessage);

      //Get the return from the server
      InputStream is = connexion.getInputStream();
      //Get the return as object (Cube)
      ObjectInputStream ois = new ObjectInputStream(is);
      Cube c = (Cube) ois.readObject();
      if (c != null) {
        System.out.println("Voici le cube");
        System.out.println(c.toString());
        resolve(c);
      } else System.out.println("Oh non, pas de cube !");
    } catch (IOException | ClassNotFoundException e2) {
      e2.printStackTrace();
    } finally {
      try {
        connexion.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Solving Part - brute forcing it
   *
   * @param c the cube to solve
   * @throws IOException
   */
  private void resolve(Cube c) throws IOException {
    int i = 0;
    int stock = 10;
    //Stock all the moves made to show them at the end
    List<String> moves = new ArrayList<>();
    long start = System.currentTimeMillis();
    while (!c.isCompleted()) {
      //Maximal hits before trying an all brand new solution is set to :
      int nbHit = 150;
      System.out.println("TEST NUMERO : " + i);
      //Clone the cube in order not to affect the original (Deep Copy, not Shallow one)
      Cube clone = new Cube(c);
      while (nbHit != 0 && !c.isCompleted()) {
        try {
          //Do a random move
          clone.scramble();
        } catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
        //System.out.println(clone.toString());
        if (clone.isCompleted()) {
          System.out.println(nbHit);
          stock = i;
          moves = clone.getTmpMoveDone();
          retMoves = clone.getTmpMoveDone();
          //if clompeted the original becomes the cloned with its solution
          c = new Cube(clone);
        }
        nbHit--;
      }
      i++;
    }
    long end = System.currentTimeMillis();

    System.out.println("RESOLU en " + stock + " coups et " + ((end - start) / 1000) + " s");


    OutputStream os = connexion.getOutputStream();
    OutputStreamWriter osw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(osw);
    //write to server
    String sendMessage = Arrays.toString(moves.toArray()) + "\n";
    bw.write(sendMessage);
    bw.flush();
  }

}
