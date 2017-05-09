import java.util.*;

public class Projectile extends GameObject {
  
  private double dmg;
  
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
  * @param dmg the dommage of the projectile
  */
  public Projectile(Vector r, Vector v, double mass, double dmg) {
    super(r, v, mass);
    this.dmg = dmg;
  }
  
  /***************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/
 
  /**
  * Function which give the dmg of the projectile
  * @return the dommage of the projectile
  */
  public double getDmg() {
    return this.dmg;
  }
  
  /**
  * Function with one parameter which set the dommage of the projectile
  * @param dmg is the dommage of the plqyer
  */
  public void setDmg(double dmg) {
    this.dmg = dmg;
  }
}