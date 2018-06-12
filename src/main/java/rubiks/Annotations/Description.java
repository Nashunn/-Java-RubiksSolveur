package rubiks.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description that return face value
 *
 * @author BOULLET Nicolas & PARDIEU Timothé
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface Description {
    //Return the face value for each
    int center();

    int top();

    int bottom();

    int left();

    int rigth();

    int back();
}
