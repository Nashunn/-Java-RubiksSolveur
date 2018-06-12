package rubiks.Model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Representation of a face, with its number of rows and cols
 *
 * @author BOULLET Nicolas & PARDIEU Timothe
 */
public class Face implements Serializable{

  private int[][] face; //face[rows][cols]

  //=================================
  // Constructors
  //=================================

  /**
   * Constructor taking the index of the face
   * Use to create a perfect cube
   * @param number Index of the face
   */
  public Face(int number) {
    this.face = new int[3][3];
    // for all rows
    for (int i = 0; i < this.face.length; i++) {
      //for all cols of the row
      for (int j = 0; j < this.face[i].length; j++) {
        this.face[i][j] = number;
      }
    }
  }

  public Face(Face[] faces, int index) {
    this.face = new int[3][3];
    for (int i = 0; i < this.face.length; i++) {
      // for all cols of the row
      for (int j = 0; j < this.face[i].length; j++) {
        this.face[i][j] = faces[index].face[i][j];
      }
    }
  }

  /**
   * Create a face from with an Array of Integer (the binding of colors)
   * @param face the face as a Integer array
   */
  public Face(Integer[] face) {
    this.face = new int[3][3];
    int index = 0;
    for (int i = 0; i < this.face.length; i++) {
      // for all cols of the row
      for (int j = 0; j < this.face[i].length; j++) {
        this.face[i][j] = face[index];
        index++;
      }
    }
  }

  //=================================
  // Setters
  //=================================



  /**
   * Return the entire face
   *
   * @return An array containing values of the face
   */
  public int[][] getFace() {
    return this.face;
  }

  /**
   * Setting new values to the face
   *
   * @param values new values for the face
   */
  public void setFace(int[][] values) {
    for (int i = 0; i < values.length; i++)
      for (int j = 0; j < values[i].length; j++)
        this.face[i][j] = values[i][j];
  }

  //=================================
  // Actions
  //=================================
  //=================================
  // Getters
  //=================================
  /**
   * Return the center square of the face
   *
   * @return The value of the center
   */
  public int getCenter() {
    return this.face[1][1];
  }

  /**
   * Return a line of the face (easier to work with)
   *
   * @param i Index of the desired line
   * @return An array containing the values of the line
   */
  public int[] getLine(int i) {
    int[] line = new int[3];

    //todo : see that maths methods
    //BigMatrix matrix = MatrixUtils.createBigMatrix(data);
    //matrix.getColumnAsDoubleArray(0);

    for (int j = 0; j < 3; j++)
      line[j] = this.face[i][j];

    return line;
  }

  /**
   * Return a column of the face (easier to work with)
   *
   * @param j index of the desired column
   * @return An array containing the values of the column
   */
  public int[] getColumn(int j) {
    int[] column = new int[3];

    for (int i = 0; i < 3; i++)
      column[i] = this.face[i][j];

    return column;
  }

  /**
   * Set values into the given line
   *
   * @param values The values to replace with
   * @param iLine  Index of the targetted line
   */
  public void setLine(int[] values, int iLine) {
    //todo : see that maths methods
    //BigMatrix matrix = MatrixUtils.createBigMatrix(data);
    //matrix.getColumnAsDoubleArray(0);

    for (int x = 0; x < this.face[iLine].length; x++)
      this.face[iLine][x] = values[x];
  }

  /**
   * Set values into the given line
   *
   * @param values  The values to replace with
   * @param iColumn Index of the targetted column
   */
  public void setColumn(int[] values, int iColumn) {
    for (int y = 0; y < this.face[iColumn].length; y++)
      this.face[y][iColumn] = values[y];
  }

  /**
   * Return the face in the form of a string like :
   * xxx
   * xxx
   * xxx
   *
   * @return Face in the form of a string
   */
  @Override
  public String toString() {
    String s = "";
    for (int i = 0; i < this.face.length; i++) {
      for (int j = 0; j < this.face[i].length; j++) {
        s += this.face[i][j];
      }
      s += "\n";
    }
    return s;
  }

  /**
   * Determine if a face is complete with a given color
   *
   * @param faceColor The color that has to be check for the face
   * @return true if the face is complete with the same color,
   * false if not
   */
  public boolean isComplete(int faceColor) {
    int cpt = 0;

    // for all rows
    for (int i = 0; i < this.face.length; i++) {
      //for all cols of the row
      for (int j = 0; j < this.face[i].length; j++) {
        if (this.face[i][j] == faceColor)
          cpt++; //Increment if it's the given color
      }
    }
    //If all the 9 squares on the face are of the selected color return true
    return cpt == 9;
  }

  /**
   * Do a rotation of the face, thinking in a matrix way
   *
   * @param numberOfTime Repeat as many as numberOfTime
   * @param anti         if true do an antirotation instead of a rotation
   */
  public void rotate(int numberOfTime, boolean anti) {
    for (int nb = 0; nb < numberOfTime; nb++) {
      int M = this.face.length; //M = first dimension
      int N = this.face[0].length; //N = second dimension
      int[][] result = new int[N][M]; //result array

      // for all rows
      for (int i = 0; i < M; i++) {
        // for all cols of the row
        for (int j = 0; j < N; j++) {
          //Do the rotation
          if (!anti)
            result[i][M - 1 - j] = this.face[j][i];
            // Do the anti-rotation
          else
            result[(N - 1) - j][i] = this.face[i][j];
        }
      }

      this.face = result;
    }
  }
  //=================================
  // Deprecated (can be useful ?)
  //=================================
  @Deprecated
  static List<Integer> list_color;
  @Deprecated
  static Map<Integer, Integer> colors;
  @Deprecated
  private int color_choose;

  @Deprecated
  public Face() {
    this.face = new int[3][3];
    // for all rows
    for (int i = 0; i < this.face.length; i++) {
      // for all cols of the row
      for (int j = 0; j < this.face[i].length; j++) {
        //Map -> Stream -> Filter -> Map
        colors = colors.entrySet().stream()
          .filter(map -> map.getValue() != 0)
          .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        this.color_choose = new Random().nextInt(colors.size());
        colors.put(color_choose, colors.get(color_choose) - 1);
        this.face[i][j] = color_choose;
      }
    }
  }

  @Deprecated
  public static void init() {
    list_color = new LinkedList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
    colors = new HashMap();
    for (int i = 0; i < list_color.size(); i++)
      colors.put(i, 9);
  }

}
