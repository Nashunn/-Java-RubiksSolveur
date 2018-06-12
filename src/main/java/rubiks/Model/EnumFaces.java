package rubiks.Model;

import rubiks.Annotations.Description;

/**
 * Enumeration for all faces of the cube
 *
 * @author BOULLET Nicolas & PARDIEU Timothé
 */
public enum EnumFaces {
    //todo: do others

    @Description(center = 1, top = 0, bottom = 2, left = 4, back = 3, rigth = 5)
    FACE1,

    @Description(center = 3, top = 0, bottom = 2, left = 5, back = 1, rigth = 4)
    FACE3,

    @Description(center = 4, top = 0, bottom = 2, left = 3, back = 5, rigth = 1)
    FACE4,

    @Description(center = 5, top = 0, bottom = 2, left = 1, back = 4, rigth = 3)
    FACE5,

    @Description(center = 0, top = 5, bottom = 4, left = 3, back = 2, rigth = 1)
    FACE0;
}
