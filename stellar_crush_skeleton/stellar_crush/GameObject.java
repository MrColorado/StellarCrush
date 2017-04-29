import java.awt.Color;

public class GameObject /*implements Comparable<GameObject>*/ {
  // Default implementation of a game object
  
  private Color color;
  private Vector r;
  private Vector v; 
  private double mass;
  private double size; 
  
  public GameObject(Vector r, Vector v, double mass) {
    int red = (int)(255 * Math.random());
    int green = (int)(255 * Math.random());
    int blue = (int)(255 * Math.random());
    this.color = new Color(red, green, blue);
    this.r = r;
    this.v = v;
    this.mass = mass;
    this.size = sizeFromMass(mass);
  }
  
  public double getMass() {
    return this.mass;
  }
  
  public double getSize() {
    return this.size;
  }
  
  public Color getColor() {
    return this.color;
  }
  
  public Vector getR () {
    return this.r;
  }
  
  public Vector getV() {
    return this.v;
  }
  
  public void setR(Vector r) {
    this.r = r;
  }
  
  public double sizeFromMass(double mass) {
    double result = mass / 1e25;
    result /= Math.PI;
    result = Math.sqrt(result);
    return result * 0.03;
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
   return delta.direction().times(F);
  }
  
  public void draw() {
    StdDraw.setPenRadius(this.size);
    StdDraw.setPenColor(this.color);
    StdDraw.point(this.r.cartesian(0), this.r.cartesian(1));
  }
  
  public double dist(Vector player) {
    double result = Math.pow(player.cartesian(0) - this.r.cartesian(0), 2) + Math.pow(player.cartesian(1) - this.r.cartesian(1), 2);
    result = Math.sqrt(result);
    return result;
  }
  
  /*public int compareTo(GameObject that) {
        if      (this.count < that.count) return -1;
        else if (this.count > that.count) return +1;
        else                              return  0;
    }*/
}
