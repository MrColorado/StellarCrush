public class Trap extends GameObject {
  
  /***************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
  /**
  * Constructor with four parameters
  * @param r the location of the GameObject
  * @param v the velocity of the GameObject
  * @param mass the mass of the GameObject
  */
  public Trap(Vector r, Vector v, double mass) {
    super(r, v, mass);
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
/**
  * Function which draw green circle around the GameObject on the stdDraw if it is bigger than the trap
  */
  public void drawSup() {
    StdDraw.setPenRadius(this.getLevel() * 0.0001 + 0.03);
    StdDraw.setPenColor(StdDraw.GREEN);
    StdDraw.point(this.getR().cartesian(0), this.getR().cartesian(1));
  }
}
