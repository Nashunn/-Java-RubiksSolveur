package rubiks.Test;


import rubiks.Model.Cube;
import rubiks.Model.Face;

import java.lang.reflect.InvocationTargetException;

/**
 * Main class of the Rubiks Solver project
 *
 * @author BOULLET Nicolas & PARDIEU Timothé
 */
public class RubiksSolver {
    public static void main(String[] args) {
            //Create a cube
            Cube cube = new Cube();
      try {
        cube.scramble();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      System.out.println(cube.toString());
      long debut = System.currentTimeMillis();

            int i = 0;
            while(!cube.isCompleted())
            {
              System.out.println("PASSAGE : " + i);
              try {
                cube.scramble(20);
              } catch (IllegalAccessException e) {
                e.printStackTrace();
              } catch (InvocationTargetException e) {
                e.printStackTrace();
              }
              i++;
            }

            if (i == 100000) System.out.println(System.currentTimeMillis()-debut);


   /*   try {
        cube.scramble(1);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
*/
/*

      Cube c = cube.copy(cube);
      DummyCube c1 = new DummyCube(cube);
      Face[] saved = c1.getFacesClone();
      c.setFaces(c1.getFacesClone());

      cube.moveLeft(cube,0,false);

      System.out.println(cube.toString());
      System.out.println(c.toString());

*/

            //Scramble the cube
     /* Cube cubeBAK = cube;
      int tries  =0;
      Cube cubeBACK = cube;

      //@todo : Need to clone the cube
      Cube cubeCloner = new DummyCube(cube);
      Cube cube2 = cubeCloner;
      while (!cube2.isCompleted()) {
        System.out.println("ESSAIE NUMERO " + tries);
        int nbHits = 1;
        Cube cubeBACKCloner = new DummyCube(cube);
        cubeBACK = cubeBACKCloner;
        while (nbHits != 0) {
          try {
            cubeBACK.scramble();
          } catch (IllegalAccessException e) {
            System.err.println(e);
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.err.println(e);
          }
          System.out.println(cubeBACK.toString());
          nbHits--;
        }
        cube2 = cubeBACK;
        tries++;
      }
      System.out.println("ENDING with " + tries);
      System.out.println(cube2.getTmpMoveDone());*/
    }

    /**
     * Deprecated, old main
     */
    @Deprecated
    public static void old_main() {
        Cube c = new Cube();

        int i = 0;
        for (Face f : c.getFaces()) {
            System.out.println("FACE  " + i);
            System.out.println(f.toString());
            i++;
        }
        Face zzz = new Face(6);
        //zzz.rotate(1);

        //c.moveUp(c.getFace(0));
        i = 0;
        for (Face f : c.getFaces()) {
            System.out.println("FACE  " + i);
            System.out.println(f.toString());
            i++;
        }

        int[] arrayContour = {5, 1, 4, 3};
        /*while (c.getFace(c.getIndex(0)).isComplete(0))
        {
            for (int z = 0; z < arrayContour.length; z++)
            {
                c.setFocusedFace(z);

                int[] current = c.faces[z].getLine(0);
                for (int zz = 0; zz < current.length; zz++)
                {
                    /*if (current[zz]==0 /*&& flag==true*//*) {
                        switch (zz) {
                            case 0:
                                c.moveLeft(c.faces[z]); //recursive + flag=false
                                break;
                            case 1:
                                c.moveMiddleVerti(c.faces[z]);
                                break;
                            case 2:
                                c.moveRight(c.faces[z]);
                                break;
                        }
                    }
                }
                i = 0;
                for (Face f : c.getFaces()) {

                    System.out.println("FACE ! " + i);
                    System.out.println(f.toString());
                    i++;


                }
                /*if (IntStream.of(current).anyMatch(x -> x == 0))
                {
                    IntStream.of(current).
                    c.setFocusedFace(i);
                }
            }
        }

       // c.moveBottom(c.faces[0]);


        i = 0;
        for (Face f : c.getFaces()) {

            System.out.println("FACE ! " + i);
            System.out.println(f.toString());
            i++;


        }

        /*
        System.out.println("FACE FRONT");
        Face front = c.getFaces()[0];
        System.out.println(front.toString());

        System.out.println("FACE RIGHT");
        Face right = c.getFaces()[1];
        System.out.println(right.toString());

        System.out.println("FACE BACK");
        Face back = c.getFaces()[2];
        System.out.println(back.toString());

        System.out.println("FACE LEFT");
        Face left = c.getFaces()[3];
        System.out.println(left.toString());


        System.out.println("Déplacement a droite de la 1 ere ligne du haut");
        c.move2(0,front,0,c);

        System.out.println("NOUVELLE FACE FRONT");
        System.out.println(front.toString());
        System.out.println("NOUVELLE FACE RIGHT");
        System.out.println(right.toString());
        System.out.println("NOUVELLE FACE BACK");
        System.out.println(back.toString());
        System.out.println("NOUVELLE FACE LEFT");
        System.out.println(left.toString());

        System.out.println("NOUVELLE ROTATE LEFT");
        left.rotate(2);
        System.out.println(left.toString());
*/
    }
}
