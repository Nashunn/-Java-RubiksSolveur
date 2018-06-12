package rubiks.Network.Server;

import rubiks.Model.Cube;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @author Timothé PARDIEU
 * rubiks.Network
 * Created on - 10/06/2018 (23:58)
 * Build for project rubiks_boullet_pardieu
 */
public class ServerThread extends Thread {

  private Socket client;
  private PrintWriter writer = null;
  private BufferedInputStream reader = null;

  private Cube cube = null;
  boolean closeConnexion = false;
  private String type;

  private List<String[]> zeros = null;

  public ServerThread(Socket client, Cube cube, List<String[]> zeros) {
    this.client = client;
    this.cube = cube;
    this.zeros = zeros;


  }

  @Override
  public void run() {

    while (!client.isClosed()) {
      try {
        //On attend une connexion d'un client
        InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String type = br.readLine();
        System.out.println("Type du client : " + type);


        if (type.equals("SLAVE")) {
          if (cube != null) {
            System.out.println("Hi Slave");
            clientSlave(is, client);
          } else {
            System.out.println("Bye Slave! ");
            quitSlave(client);
          }
        } else {
          System.out.println("Hello Master");
          clientMaster(is, client);
        }


          System.err.println("CLIENT CLOSE ! ");
          writer = null;
          reader = null;
          client.close();
          break;

      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }

  private void quitSlave(Socket socket) throws IOException {
    OutputStream os = socket.getOutputStream();
    OutputStreamWriter osw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(osw);
    bw.write("Dégages t'as pas de boulot ! " +"\n");
    bw.flush();
  }

  private void clientSlave(InputStream is, Socket socket) throws IOException {
    //envoie le cube
    OutputStream os = socket.getOutputStream();

    ObjectOutputStream oos = new ObjectOutputStream(os);
    oos.writeObject(cube);
    oos.flush();
    //Sending the response back to the client.

    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    type = br.readLine();
    System.out.println("Type du client : " + type);
    String[] ary = type.split(",");

    zeros.add(ary);

    br.close();
    isr.close();
    oos.close();
    os.close();
    this.closeConnexion = true;

  }

  private void clientMaster(InputStream is, Socket socket) throws IOException, ClassNotFoundException, InterruptedException {
    ObjectInputStream ois = new ObjectInputStream(is);
    Integer[][] to = (Integer[][]) ois.readObject();
    if (to != null) {
      cube = new Cube(to);
      System.out.println(cube.toString());
    }
    ois.close();
  }

  public Cube getCube()
  {
    return this.cube;
  }

  public String geType() {
    return type ;
  }
}
