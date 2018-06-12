package rubiks.Test;

import rubiks.Model.Cube;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Timothé PARDIEU
 * rubiks
 * Created on - 25/05/2018 (11:14)
 * Build for project rubiks_boullet_pardieu
 */
public class Test {

  public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InterruptedException {

    Cube c = new Cube();


    c.scramble(2);
    System.out.println(c.getTmpMoveDone());
    Thread.sleep(1000);

    System.out.println("ETAT ORIGINEL");
    System.out.println(c.toString());
/*
    Cube clone = new Cube(c);
    clone.scramble();
    System.out.println("CLONE");
    System.out.println(clone.toString());
*/

    int i = 0;
    int stock=10;
    List<String> moves = new ArrayList<>();
    long start = System.currentTimeMillis();
    while (!c.isCompleted())
    {
      int nbHit = 150;
      System.out.println("TEST NUMERO : " + i);
      Cube clone = new Cube(c);
      while (nbHit != 0 && !c.isCompleted()) {
        clone.scramble();
        //System.out.println(clone.toString());
        if (clone.isCompleted()) {
          System.out.println(nbHit);
          //Thread.sleep(1000);
          stock = i;
          moves = clone.getTmpMoveDone();
          c = new Cube(clone);
        }
        nbHit--;
      }
      i++;
      //Thread.sleep(5000);
    }
    long end = System.currentTimeMillis();


    System.out.println("RESOOOOOOOLU en " + stock +" coups et " + ((end - start) / 1000) + " s");
    System.out.println(moves);
    System.out.println(c.toString());
  }

  public static void compare(Object ob1, Object ob2)
  {
    if (ob1 == ob2)
      System.out.println("Shallow Copy");
    else
      System.out.println("Deep Copy");
  };


}

