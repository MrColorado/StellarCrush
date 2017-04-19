public class GameObject {
  // Default implementation of a game object
  
  private Vector r;
  private Vector v; 
  private double mass;
  
  public GameObject(Vector r, Vector v, double mass) {
    this.r = r; 
    this.v = v; 
    this.mass = mass;
  }
  
  public void move(Vector f, double dt) {
    Vector a = f.times(1/mass);
    this.v = v.plus(a.times(dt));
    this.r = r.plus(v.times(dt));
  }
  
  public Vector forceFrom(GameObject that) {
   double G = 6.67e-11;
   Vector delta = that.r.minus(this.r);
   double dist = delta.magnitude();
   double F = (G * this.mass * that.mass) / (dist * dist); 
   return delta.direction().times(F);
  }
  
  public void draw() {
    StdDraw.setPenRadius(0.025);
    StdDraw.point(r.cartesian(0), r.cartesian(1));
  }
}
