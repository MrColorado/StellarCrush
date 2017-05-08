import java.util.*;
import java.awt.Color;
import java.util.Random;

public class GameObject {
  // Default implementation of a game object
  private Vector r;
  private Vector v;
  private double mass; 
  private Color color;
  private double level;
  private long time;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
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
  }
   
  /**************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/
  
  public Vector getR () {
    return this.r;
  }
  
  public Vector getV() {
    return this.v;
  }
  
  public double getMass() {
    return this.mass;
  }
  
  public Color getColor() {
    return this.color;
  }
  
  public double getLevel() {
    return this.level;
  }
  
  public long getTime() {
    return this.time;
  }
 
  public void setR(Vector r) {
    this.r = r;
  }
  
  public void setV(Vector v) {
    this.v = v;
  }
   
  public void setMass(double mass) {
    this.mass = mass;
  }
  
  public void setLevel(double level) {
    this.level = level;
  }
 
  public void setTime(long time) {
    this.time = time;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/  
  
  public double sizeToDisplay(double dist, double sup) {
    double result = ((this.getLevel() * 0.001 + 0.025 + sup) * 5.0e10) / (dist * 2);
    return result;
  }
  
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
  
  public void move(Vector f, double dt) {
    Vector a = f.times(1/mass);
    this.v = this.v.plus(a.times(dt));
    this.r = this.r.plus(this.v.times(dt));
  }
  
  public Vector forceFrom(GameObject that) {
    double G = 6.67e-11;
    Vector delta = that.r.minus(this.r);
    double dist = delta.magnitude();
    double F = (G * this.mass * that.mass) / (dist * dist);
    return VectorUtil.direction(delta).times(F);
  }
  
  public void draw() {
    StdDraw.setPenRadius(this.level * 0.001 + 0.025);
    StdDraw.setPenColor(this.color);
    StdDraw.point(this.r.cartesian(0), this.r.cartesian(1));
  }
  
  public void drawSup() {
    StdDraw.setPenRadius(this.level * 0.001 + 0.03);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.point(this.r.cartesian(0), this.r.cartesian(1));
  }
  
  public GameObject split() {
    double[] newRDouble = {(this.getLevel() * 0.001 + 0.025) * 5.0e10, 0};
    Vector newR = this.getR().plus(new Vector(newRDouble));
    double[] newVDouble = {Math.abs(this.getV().cartesian(0)), this.getV().cartesian(1)};
    Vector newV = new Vector(newVDouble);
    GameObject newPlanet = new GameObject(newR, newV, this.getMass() / 1.5);
    newPlanet.setLevel(this.getLevel() / 1.5);
    
    this.setV(newV.times(-1));
    this.setTime((int)(Math.random() * 10000) + 10000);
    return newPlanet;
  }
}
