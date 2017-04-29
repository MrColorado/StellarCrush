import java.util.*;

public class Camera {
  // Virtual camera - uses a plane one unit away from the focal point
  // For ease of use, this simply locates where the centre of the object is, and renders it if that is in the field of view.
  // Further, the correct rendering is approximated by a circle centred on the projected centre point.

  private final IViewPort holder; // Object from whose perspective the first-person view is drawn
  private Draw dr; // Canvas on which to draw
  private double FOV; // field of view of camera

  public Camera(IViewPort holder, double FOV) {
    // Constructs a camera with field of view FOV, held by holder, and rendered on canvas dr.
    this.holder = holder;
    this.FOV = FOV;
    this.dr = new Draw();
    this.dr.setXscale(FOV/2.0, -FOV/2.0);
    this.dr.setYscale(-1.0, 1.0);
  }

  public Draw getDr() {
    return this.dr;
  }
  
  void render(Collection<GameObject> objects) {
    // Renders the collection from the camera perspective
    Vector pos = this.holder.getLocation();
    Vector dir = this.holder.getFacingVector();
    dr.clear();
    for(GameObject o : objects) {
      double deltaX = (pos.cartesian(0) - o.getR().cartesian(0)) / StellarCrush.scale;
      double deltaY = (pos.cartesian(1) - o.getR().cartesian(1)) / StellarCrush.scale;
      double angle = Math.atan2(deltaY, deltaX) /*- Math.atan2(dir.cartesian(1), dir.cartesian(0))*/;
      if (Math.abs(angle) < FOV/2.0)
        draw(o, angle);
    }
  }
  
  public void draw(GameObject o, double angle) {
    dr.setPenRadius(o.getSize());
    dr.setPenColor(o.getColor());
    dr.point(Math.sin(angle), 0);
  }
  
  public Draw getDraw() {
    return new Draw();
  }
}
