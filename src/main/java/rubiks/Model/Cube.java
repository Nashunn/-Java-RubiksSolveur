package rubiks.Model;

import rubiks.Annotations.Description;
import rubiks.Annotations.Move;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representation of a cube, with its faces(x6)
 *
 * @author BOULLET Nicolas & PARDIEU TimothÃ©
 */
public class Cube implements Serializable {
  //Number of faces
  private static final int NUMBER_OF_FACES = 6;
  //Init an array of 6 faces
  private Face[] faces = new Face[NUMBER_OF_FACES];
  private List<String> tmpMoveDone = new ArrayList<>();
  //=================================
  // Constructors
  //=================================

  /**
   * Default constructor for a cube
   * For each case in the array we create a new Face
   */
  public Cube() {
    for (int i = 0; i < NUMBER_OF_FACES; i++) {
      faces[i] = new Face(i);
    }
  }


  /**
   * Copy the cube
   *
   * @param cube
   */
  public Cube(Cube cube) {
    this.faces[0] = new Face(cube.faces, 0);
    this.faces[1] = new Face(cube.faces, 1);
    this.faces[2] = new Face(cube.faces, 2);
    this.faces[3] = new Face(cube.faces, 3);
    this.faces[4] = new Face(cube.faces, 4);
    this.faces[5] = new Face(cube.faces, 5);
  }

  /**
   * Construction of a cube from array of number
   * ex :
   * {1,1,1,5,0,0,3,3,3},
   * {5,1,2,0,1,2,0,1,4},
   * {3,3,3,2,2,4,1,1,1},
   * {5,0,0,3,3,3,2,2,4},
   * {0,0,0,4,4,4,4,4,4},
   * {5,5,5,5,5,5,2,2,2}
   *
   * @param to array of number (transform into Face)
   */
  public Cube(Integer[][] to) {
    int index = 0;
    for (Integer[] face : to) {
      this.faces[index] = new Face(face);
      index++;
    }
  }


  public Cube copy(Cube other) {
    Cube newCube = new Cube();
    //... etc.

    Face[] dummy = other.faces.clone();
    //Cube dummyCube = new DummyCube(this);
    // We set the array with the wanted face in first
    // Then for each face we assign its right,left,top... by getting the info from the Description
    dummy[0] = other.faces[0];
    dummy[1] = other.faces[1];
    dummy[2] = other.faces[2];
    dummy[3] = other.faces[3];
    dummy[4] = other.faces[4];
    dummy[5] = other.faces[5];

    newCube.faces = dummy.clone();


    return newCube;
  }


  public List<String> getTmpMoveDone() {
    return tmpMoveDone;
  }
//=================================
  // Getters
  //=================================

  /**
   * Return a face
   *
   * @param iFace index of the wanted face
   * @return Face on the cube
   */
  public Face getFace(int iFace) {
    return this.faces[iFace];
  }

  /**
   * Return the current face of the cube
   *
   * @return current face
   */
  Face getCurrentFace() {
    return this.getFace(0);
  }

  /**
   * Return the index of a given face on the cube
   *
   * @param faceColor Color of the wanted face
   * @return id of the face by its color
   * -1 if the given color is not on the cube
   */
  public int getIndex(int faceColor) {
    int index = 0;

    // check all faces one by one
    for (Face face : this.faces) {
      //if the center of the face match the given face color,
      // return the index
      if (face.getCenter() == faceColor)
        return index;
      index++; // Got to the next index
    }

    return -1;
  }

  //=================================
  // Setters
  //=================================

  /**
   * Set new values to the face of the given id
   *
   * @param idFace id of the targetted face
   * @param values new values for the targetted face
   */
  public void setFace(int idFace, int[][] values) {
    this.faces[idFace].setFace(values);
  }

  /**
   * Set a face as the main face of the cube
   *
   * @param idFace The index of the face we want to focus
   */
  public void setFocusedFace(int idFace) {
    // We retrieve the matching description of the face by its number
    Description faceDescription = searchDescription(idFace);

    Face[] dummy = faces.clone();
    //Cube dummyCube = new DummyCube(this);
    // We set the array with the wanted face in first
    // Then for each face we assign its right,left,top... by getting the info from the Description
    this.faces[0] = dummy[faceDescription.center()];
    this.faces[1] = dummy[faceDescription.rigth()];
    this.faces[2] = dummy[faceDescription.back()];
    this.faces[3] = dummy[faceDescription.left()];
    this.faces[4] = dummy[faceDescription.bottom()];
    this.faces[5] = dummy[faceDescription.top()];
  }


  //=================================
  // Actions
  //=================================

  /**
   * This method will search if a Description exist for the wanted face
   *
   * @param idFace The face's index wanted
   * @return A description if exist
   * @todo : avoid nullable
   */
  private Description searchDescription(int idFace) {
    //Retrieve where we search (our enum file)
    Class clazz = EnumFaces.class;
    //Get all the field of the file
    Field fields[] = clazz.getFields();

    //For each fild..
    for (Field field : fields) {
      //We retrieve the description associated to the field
      Description desc = field.getAnnotation(Description.class);
      if (desc != null) //To improve
        //We check the face which will be in center, if it match with our faceNumber we return it.
        //Example: we want the 5 face to be center so we check :
        // @Description(center = 5, top = 0,bottom = 2, left = 1, back = 4, rigth = 3)
        //Center is set to 5 so its our face !
        if (desc.center() == idFace)
          return desc;
    }
    return null;
  }

  /**
   * Check if the cube is completed
   *
   * @return boolean that indicate if the cube is complete
   * false if not complete
   * true if complete
   */
  public boolean isCompleted() {
    for (Face f : faces) {
      int color = f.getCenter();
      if (!f.isComplete(color))
        return false;
    }
    return true;
  }

  /**
   * Default.
   * Scramble the cube with 15 random moves
   */
  public void scramble() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    ArrayList<Method> methods = getMovesMethods();
    int maxLength = methods.size();

    for (int i = 0; i < 1; i++) {
      int randomInt = (int) (Math.random() * (maxLength));
      Random random = new Random();
      Boolean bool = random.nextBoolean();
      methods.get(randomInt).invoke(this, this.getFace(0), bool);
      Method m = methods.get(randomInt);
      Move d = m.getAnnotation(Move.class);
      this.tmpMoveDone.add(d.name().toLowerCase() + (bool ? "" : "i"));
    }
  }

  /**
   * Scramble the cube with random moves
   *
   * @param nbOfMoves the number of moves given to scramble the cube
   */
  public void scramble(int nbOfMoves) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    ArrayList<Method> methods = getMovesMethods();
    int maxLength = methods.size();

    for (int i = 0; i < nbOfMoves; i++) {
      int randomInt = (int) (Math.random() * (maxLength));
      Random random = new Random();
      Boolean bool = random.nextBoolean();
      methods.get(randomInt).invoke(this, this.getFace(0), bool);
      Method m = methods.get(randomInt);
      Move d = m.getAnnotation(Move.class);

      this.tmpMoveDone.add(d.name().toLowerCase() + (bool ? "" : "i"));
    }
  }

  /**
   * Get all the methods with the move annotation
   *
   * @return An arraylist of methods
   */
  public ArrayList<Method> getMovesMethods() {
    Class clazz = Cube.class;
    Method[] methods = clazz.getMethods(); //Get all the methods of the class
    ArrayList<Method> moves = new ArrayList<>();

    //Get all methods with annotations in an array
    for (Method method : methods) {
      if (method.getAnnotation(Move.class) != null)
        moves.add(method);
    }

    return moves;
  }

  /**
   * Print the value of faces on the cube in order
   *
   * @return the values of faces in the form of a string
   */
  @Override
  public String toString() {
    StringBuilder sRet = new StringBuilder();
    sRet.append("START\n");
    for (Face face : this.faces) {
      sRet.append(face.toString());
      sRet.append("---\n");
    }
    sRet.append("END\n\n\n");
    return String.valueOf(sRet);
  }

  /**
   * Move the back face clockwise
   * todo : need to be optimised (temporary solution)
   *
   * @param anti If true do an antirotation instead of a rotation
   */
  public void rotateBack(boolean anti) {
    // Rotate the back face
    faces[2].rotate(1, anti);
    int[] facesToVisit = {1, 5, 3, 4};
    move(facesToVisit, 2);

  }

  //*********************************
  // Horizontal
  //*********************************

  /**
   * Move the middle line from the face (clockwise)
   *
   * @param f     The targetted face
   * @param iLine Index of the line on the face :
   *              0 top line
   *              1 middle line
   *              2 bottom line
   */
  public void moveHorizontal(Face f, int iLine) {
    int[] line = f.getLine(iLine); // Get the line values

    // Set the values from the current face to the right
    int[] tmp1 = this.faces[1].getLine(iLine);
    this.faces[1].setLine(line, iLine);

    // Set the values from the right face to the back
    int[] tmp2 = this.faces[2].getLine(iLine);
    this.faces[2].setLine(tmp1, iLine);

    // Set the values from the back face to the left
    tmp1 = this.faces[3].getLine(iLine);
    this.faces[3].setLine(tmp2, iLine);

    // Set the values from the left face to the initial face (front)
    f.setLine(tmp1, iLine);
  }

  public void moveAntiHorizontal(Face f, int iLine) {
    int[] line = f.getLine(iLine); // Get the line values

    // Set the values from the current face to the right
    int[] tmp1 = this.faces[3].getLine(iLine);
    this.faces[3].setLine(line, iLine);

    // Set the values from the right face to the back
    int[] tmp2 = this.faces[2].getLine(iLine);
    this.faces[2].setLine(tmp1, iLine);

    // Set the values from the back face to the left
    tmp1 = this.faces[1].getLine(iLine);
    this.faces[1].setLine(tmp2, iLine);

    // Set the values from the left face to the initial face (front)
    f.setLine(tmp1, iLine);
  }

  /**
   * Move the up line of the face
   *
   * @param face The targetted face
   * @param anti If true do an antirotation instead of a rotation
   */
  @Move(name = "U")
  public void moveUp(Face face, boolean anti) {
    if (!anti)
      moveHorizontal(face, 0);
    else
      moveAntiHorizontal(face, 0);

    //todo : else moveAntivertical()

    // Rotate the up face
    faces[5].rotate(1, anti);
  }

  /**
   * Move the middle line of the face
   *
   * @param face The targetted face
   * @param anti If true do an antirotation instead of a rotation
   */
  @Move(name = "E")
  public void moveMiddleHori(Face face, boolean anti) {
    if (!anti)
      moveHorizontal(face, 1);
    else
      moveAntiHorizontal(face, 1);
    //todo : else moveAntivertical()
  }

  /**
   * Move the up line of the face
   *
   * @param face The targetted face
   * @param anti If true do an antirotation instead of a rotation
   */
  @Move(name = "D")
  public void moveBottom(Face face, boolean anti) {
    if (!anti)
      moveHorizontal(face, 2);
      //todo : else moveAntivertical()
    else
      moveAntiHorizontal(face, 2);
    // Rotate the bottom face
    faces[4].rotate(1, anti);
  }

  //*********************************
  // Vertical
  //*********************************

  /**
   * Move the middle column from the face (clockwise)
   *
   * @param f    The targetted face
   * @param iCol Index of the column on the face :
   *             0 left line
   *             1 middle line
   *             2 right line
   */
  // @Tim (checked): normalement ca fait pas 'top, back, bottom, initial' plutot que 'right, back, left, initial' ?
  // j'ai remplacÃ© ici par 'top, back, bottom, initial' mais si j'ai faux remets 'right, back, left, initial'
  public void moveVertical(Face f, int iCol) {

    int[] column = f.getColumn(iCol); // Get column values

    // Set the values from the current face to the top
    int[] tmp1 = this.faces[5].getColumn(iCol);
    this.faces[5].setColumn(column, iCol);

    // Set the values from the top face to the back
    int[] tmp2 = this.faces[2].getColumn(iCol);
    this.faces[2].setColumn(tmp1, iCol);

    // Set the values from the back face to the bottom
    tmp1 = this.faces[4].getColumn(iCol);
    this.faces[4].setColumn(tmp2, iCol);

    // Set the values from the bottom face to the initial face (front)
    f.setColumn(tmp1, iCol);
  }

  public void moveAntiVertical(Face f, int iCol) {


    int[] column = f.getColumn(iCol); // Get column values

    // Set the values from the current face to the top
    int[] tmp1 = this.faces[4].getColumn(iCol);
    this.faces[4].setColumn(column, iCol);

    // Set the values from the top face to the back
    int[] tmp2 = this.faces[2].getColumn(iCol);
    this.faces[2].setColumn(tmp1, iCol);

    // Set the values from the back face to the bottom
    tmp1 = this.faces[5].getColumn(iCol);
    this.faces[5].setColumn(tmp2, iCol);

    // Set the values from the bottom face to the initial face (front)
    f.setColumn(tmp1, iCol);
  }

  /**
   * Move the left column of the face
   *
   * @param face The targetted face
   * @param anti If true do an antirotation instead of a rotation
   */
  @Move(name = "L")
  public void moveLeft(Face face, boolean anti) {
    if (!anti)
      moveVertical(face, 0);
    else
      moveAntiVertical(face, 0);
    //todo : else moveAntivertical()

    // Rotate the left face
    this.faces[3].rotate(1, anti);
  }

  /**
   * Move the middle column of the face
   *
   * @param face The targetted face
   * @param anti If true do an antirotation instead of a rotation
   */
  @Move(name = "M")
  public void moveMiddleV(Face face, boolean anti) {
    if (!anti)
      moveVertical(face, 1);
    else
      moveAntiVertical(face, 1);
    //todo : else moveAntivertical()
  }

  /**
   * Move the right column of the face
   *
   * @param face The targetted face
   * @param anti If true do an antirotation instead of a rotation
   */
  @Move(name = "R")
  public void moveRight(Face face, boolean anti) {
    if (!anti)
      moveVertical(face, 2);
    else
      moveAntiVertical(face, 2);
    //todo : else moveAntivertical()

    // Rotate the right face
    faces[1].rotate(1, anti);
  }


  @Move(name = "F")
  public void rotate(Face face, boolean anti) {
    face.rotate(1, anti);
  }
  //=================================
  // Deprecated
  //=================================

  /**
   * @return the array of faces
   */
  public Face[] getFaces() {
    return faces;
  }

  public void setFaces(Face[] faces) {
    this.faces = faces;
  }

  /**
   * Do a move on the cube
   *
   * @param direction  the direction (clockwise or anti)
   * @param f          the main face
   * @param nb_of_line the line to move (0 for left, 1 for middle, 2 for left)
   * @param c          the cube
   * @deprecated replaced by the method 'move'
   */
  public void move2(int direction, Face f, int nb_of_line, Cube c) {
    switch (direction) {
      //Clockwise
      case 0:
        // move cube's front side to the right
        int col = 0;
        // get the line from cube's front side
        int[] line = f.getLine(nb_of_line);

        //right
        int[] tmp1 = faces[1].getLine(nb_of_line);
        faces[1].setLine(line, 0);

        //back
        int[] tmp2 = faces[2].getLine(nb_of_line);
        faces[2].setLine(tmp1, 0);

        //left
        tmp1 = faces[3].getLine(nb_of_line);
        faces[3].setLine(tmp2, 0);

        //front
        f.setLine(tmp1, 0);
        break;
      // Anti
      case 1:
        //todo
        break;
      default:
        throw new InternalError("unknown side ");
    }
  }

  /**
   * todo : having face[index[x]] is pretty ugly :|
   */
  public void move(int[] index, int iCol) {
    int i = 0;
    int[] column = this.faces[index[i]].getColumn(iCol); // Get column values

    // Set the values from the current face to the top
    int[] tmp1 = this.faces[index[++i]].getColumn(iCol);
    this.faces[index[i]].setColumn(column, iCol);

    // Set the values from the top face to the back
    int[] tmp2 = this.faces[index[++i]].getColumn(iCol);
    this.faces[index[i]].setColumn(tmp1, iCol);

    // Set the values from the back face to the bottom
    tmp1 = this.faces[index[++i]].getColumn(iCol);
    this.faces[index[i]].setColumn(tmp2, iCol);

    // Set the values from the bottom face to the initial face (front)
    this.faces[index[0]].setColumn(tmp1, iCol);

  }

  public Object clone() throws CloneNotSupportedException {
    Cube cloned = (Cube) super.clone();
    int[][] m = new int[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        m[i][j] = i * j;
      }
    }
    cloned.setFace(4, m);
    return cloned;
  }
}
