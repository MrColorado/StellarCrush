public class VectorUtil {
  // Class containing additional utility functions for working with vectors.

  public static final Vector TWO_D_ZERO = new Vector(new double[]{0, 0});

  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  /**
  * Function with two parameters rotate an angle
  * @param currentAngle the angle that will be rotate
  * @param angle angle that will bu add to the current angla
  */
  static Vector rotate(double currentAngle, double angle) {
    double newAngle = currentAngle + angle;
    double[] data = {Math.cos(newAngle), Math.sin(newAngle)};
    Vector result = new Vector(data);
    return result;
  }

  /**
  * Function with one parameter wich wich give the direction of a vector
  * @param vector the vactor that from the direction will be take
  */
  static Vector direction(Vector v) {
    // Returns direction of v, but sets angle to Math.PI/2 when v is the zero vector
    // Used to avoid exception in Vector.java
    if (v.magnitude() == 0.0) {
      double[] sum = {0, 1};
      Vector newSum = new Vector(sum);
      v = v.plus(newSum);
    }
    return v.times(1.0 / v.magnitude());
  }
}
