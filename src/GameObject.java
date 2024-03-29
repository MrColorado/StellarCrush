import java.awt.Color;
import java.util.Random;

public class GameObject {
  // Default implementation of a game object
  
  private Vector r;
  private Vector v;
  private double mass; 
  private double level;
  private double life;
  private Color color;
  private long time;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
  /**
  * Constructor with tree parameters
  * @param r the location of the GameObject
  * @param v the velocity of the GameObject
  * @param mass the mass of the GameObject
  */
  public GameObject(Vector r, Vector v, double mass) {
    int red = (int)(255 * Math.random());
    int green = (int)(255 * Math.random());
    int blue = (int)(255 * Math.random());
    this.color = new Color(red, green, blue);
    this.r = r;
    this.v = v;
    this.mass = mass;
    Random rand = new Random();
    this.level = rand.nextInt(10) + 2;
    this.time = rand.nextInt(15000) + 10000;
    this.life = 3;
  }
   
  /**************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/
   
  /**
  * Function which give the location of the GameOject
  * @return the location of the GameObject
  */
  public Vector getR () {
    return this.r;
  }
  
  /**
  * Function which give the velocity of the GameOject
  * @return the velocity of the GameObject
  */
  public Vector getV() {
    return this.v;
  }
  
  /**
  * Function which give the mass of the GameOject
  * @return the mass of the GameObject
  */
  public double getMass() {
    return this.mass;
  }
  
  /**
  * Function which give the color of the GameOject
  * @return the color of the GameObject
  */
  public Color getColor() {
    return this.color;
  }
  
  /**
  * Function which give the level of the GameOject
  * @return the level of the GameObject
  */
  public double getLevel() {
    return this.level;
  }
  
  /**
  * Function which give the the time before the GameOject will be split 
  * @return the time before the GameObject will be split 
  */
  public long getTime() {
    return this.time;
  }
  
  /**
  * Function which give the the life of GameOject
  * @return the life of the GameObject
  */
  public double getLife() {
    return this.life;
  }
 
  /**
  * Function which set a new GameObject location
  * @param r is the new GameObject location
  */
  public void setR(Vector r) {
    this.r = r;
  }
  
  /**
  * Function which set a new GameObject velocity
  * @param v is the new GameObject velocity
  */
  public void setV(Vector v) {
    this.v = v;
  }
  
  /**
  * Function which set a new GameObject mass
  * @param mass is the new GameObject mass
  */
  public void setMass(double mass) {
    this.mass = mass;
  }
  
  /**
  * Function which set a new GameObject color
  * @param mass is the new GameObject color
  */
  public void setColor(Color color) {
    this.color = color;
  }
  
  /**
  * Function which set a new GameObject level
  * @param level is the new GameObject level
  */
  public void setLevel(double level) {
    this.level = level;
  }
 
  /**
  * Function which set a new GameObject time
  * @param time is the new GameObject time
  */
  public void setTime(long time) {
    this.time = time;
  }
  
  /**
  * Function which set a new GameObject life
  * @param time is the new GameObject life
  */
  public void setLife(double life) {
    this.life = life;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/  
  
  /**
  * Function with two parameters wich compute the size of a GameObject fort the first view
  * @param dist it is the distance between the player and the GameObject
  * @param sup is the extra size for display a red circle around the GameObject if this last one is bigger than the player 
  */
  public double sizeToDisplay(double dist, double sup) {
    double result = ((this.getLevel() * 0.001 + 0.025 + sup) * 5.0e10) / (dist * 2);
    return result;
  }
  
  /**
  * Function which limit the welocity of a GameObject
  */
  public void clampSpeed() {
    double vX = this.v.cartesian(0);
    double vY = this.v.cartesian(1);
    boolean b = false;
    if (vX > 1e4) {
      vX = 1e4;
      b = true;
    }
    if (vX < -1e4) {
      vX = -1e4;
      b = true;
    }
      if (vY > 1e4) {
      vY = 1e4;
      b = true;
      }
    if (vY < -1e4) {
      vY = -1e4;
      b = true;
    }
    if (b) {
      double[] newV = {vX, vY};
      this.setV(new Vector(newV));
    }
  }
  
  /**
  * Function with two parameters wich modify the location of a GameObject
  * @param f force apply on the GameObject
  * @param dt is the delay 
  */
  public void move(Vector f, double dt) {
    Vector a = f.times(1/mass);
    this.v = this.v.plus(a.times(dt));
    this.r = this.r.plus(this.v.times(dt));
  }
  
  /**
  * Function with one parameter which compute a force that a GameObject as on another
  * @param that the object that apply a force
  * @return the force that will be apply 
  */
  public Vector forceFrom(GameObject that) {
    double G = 6.67e-11;
    Vector delta = that.r.minus(this.r);
    double dist = delta.magnitude();
    double F = (G * this.mass * that.mass) / (dist * dist);
    return VectorUtil.direction(delta).times(F);
  }
  
  /**
  * Function which draw GameObject on the stdDraw
  */
  public void draw() {
    StdDraw.setPenRadius(this.level * 0.0001 + 0.025);
    StdDraw.setPenColor(this.color);
    StdDraw.point(this.r.cartesian(0), this.r.cartesian(1));
  }
  
  /**
  * Function which draw red circle around the GameObject on the stdDraw if it is bigger than the player
  */
  public void drawSup() {
    StdDraw.setPenRadius(this.level * 0.0001 + 0.03);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.point(this.r.cartesian(0), this.r.cartesian(1));
  }
  
  /**
  * Function which create a new GameObject from one which exist yet
  * @return the new GameObject
  */
  public GameObject split() {
    double[] newRDouble = {(this.getLevel() * 0.0001 + 0.025) * 5.0e10, 0};
    Vector newR = this.getR().plus(new Vector(newRDouble));
    double[] newVDouble = {Math.abs(this.getV().cartesian(0)), this.getV().cartesian(1)};
    Vector newV = new Vector(newVDouble);
    GameObject newPlanet = new GameObject(newR, newV, this.getMass() / 1.5);
    newPlanet.setLevel(this.getLevel() / 1.5);
    
    this.setV(newV.times(-1));
    this.setTime((int)(Math.random() * 10000) + 10000);
    return newPlanet;
  }
  
  /**
  * Function which split a object into two sub objects
  * @return the new GameObject
  */
  public GameObject splitDestroy() {
    double[] newRDouble = {(this.getLevel() * 0.0001 + 0.025) * 5.0e10, 0};
    Vector newR = this.getR().plus(new Vector(newRDouble));
    double[] newVDouble = {Math.abs(this.getV().cartesian(0)), this.getV().cartesian(1)};
    Vector newV = new Vector(newVDouble);
    GameObject newPlanet = new GameObject(newR, newV, this.getMass() / 2);
    newPlanet.setLevel(this.getLevel() / 2);
    
    this.setV(newV.times(-1));
    this.setLevel(this.getLevel() / 2);
    return newPlanet;
  }
}
