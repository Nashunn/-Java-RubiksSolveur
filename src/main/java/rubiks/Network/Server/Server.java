package rubiks.Network.Server;

import rubiks.Model.Cube;
import rubiks.Network.Worker.ClientWorker;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Server {

  private final int PORT = 2983;

  private ServerSocket server = null;
  private boolean isRunning = true;
  private Cube cube = null;
  private boolean isCubeSet = false;
  private List<String[]> solutions = new ArrayList<>();
  private Socket android;


  public Server() throws InvocationTargetException, IllegalAccessException {
    try {
      server = new ServerSocket(PORT);
      System.out.println(" ___                          \n" +
        "/ __> ___  _ _  _ _  ___  _ _ \n" +
        "\\__ \\/ ._>| '_>| | |/ ._>| '_>\n" +
        "<___/\\___.|_|  |__/ \\___.|_|");

      //if no Master, fake it
      //cube = new Cube();
      //cube.scramble(4);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void open() throws InterruptedException, IOException {

    Thread t = new Thread(new Runnable() {
      public void run() {

        while (isRunning) {
          try {
            System.out.println("Attente du client");
            //On attend une connexion d'un client
            Socket client = server.accept();
            System.out.println();

            if (!isCubeSet)
              fetchTheCube(client);
            else {
              //traite dans un thread séparé
              System.out.println("Connexion cliente reçue et cube ok.");
              ServerThread t = new ServerThread(client, cube, solutions);
              t.start();
            }

            System.out.println(solutions.size());
            if (solutions.size() > 2) // ou timer ?
            {
              isRunning = false;
              //on met en première position la plus courte solution
              solutions.sort(Comparator.comparing(a -> a[0].length()));
              sendToMaster();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
    t.start();
  }

  /**
   * This method will wait until a cube is set in the server
   * @param client
   */
  private void fetchTheCube(Socket client) {
    android = client;
    System.out.println("le cube n'est pas dispo");
    while (!isCubeSet) {
      System.out.println("on cherche le cube");
      try {
        //On attend une connexion d'un client
        InputStream is = android.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String type = br.readLine();
        System.out.println("Type du client : " + type);
        if (type.equals("MASTER")) {
          System.out.println("Hello Master");
          ObjectInputStream ois = new ObjectInputStream(is);
          Integer[][] to = (Integer[][]) ois.readObject();
          if (to != null) {
            cube = new Cube(to);
            System.out.println(cube.toString());
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (cube != null)
        isCubeSet = true;
    }
  }

  /**
   * Return to android the result
   * But return only the best solutions (less moves)
   *
   * @throws IOException
   */
  private void sendToMaster() throws IOException {
    isRunning = false;
    System.out.println("SENDING TO ANDROID");
    System.out.println(this.solutions.size());
    System.out.println(Arrays.toString(this.solutions.get(0)));

    OutputStream os = android.getOutputStream();
    OutputStreamWriter osw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(osw);

    String sendMessage = Arrays.toString(this.solutions.get(0)) + "\n";
    bw.write(sendMessage);
    bw.flush();
  }

}
