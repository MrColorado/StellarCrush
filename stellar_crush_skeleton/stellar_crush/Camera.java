import java.util.*;
import java.awt.Dimension;

public class Camera {
  // Virtual camera - uses a plane one unit away from the focal point
  // For ease of use, this simply locates where the centre of the object is, and renders it if that is in the field of view.
  // Further, the correct rendering is approximated by a circle centred on the projected centre point.

  private final IViewPort holder; // Object from whose perspective the first-person view is drawn
  private Draw dr; // Canvas on which to draw
  private double FOV; // field of view of camera
  private final Dimension scrnSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
  private final int height = (int)scrnSize.getHeight();
  private final int width  = (int)scrnSize.getWidth();

  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
  public Camera(IViewPort holder, double FOV) {
    // Constructs a camera with field of view FOV, held by holder, and rendered on canvas dr.
    this.holder = holder;
    this.FOV = FOV;
    this.dr = new Draw();
    this.dr.setLocationOnScreen(width / 2, 1);
    this.dr.setCanvasSize(width / 2, (int)(height * 0.90));
    this.dr.setXscale(FOV/2.0, -FOV/2.0);
    this.dr.setYscale(-1.0, 1.0);
  }
  
  /**************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/

  public Draw getDr() {
    return this.dr;
  }
  
  public Draw getDraw() {
    return new Draw();
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  void render(Collection<GameObject> objects, PlayerObject player) {
    // Renders the collection from the camera perspective
    Vector pos = this.holder.getLocation();
    Vector dir = this.holder.getFacingVector();
    dr.clear();
    for(GameObject o : objects) {
      boolean same = pos.cartesian(0) != o.getR().cartesian(0) || pos.cartesian(1) != o.getR().cartesian(1);
      double deltaX = pos.cartesian(0) - o.getR().cartesian(0);
      double deltaY = pos.cartesian(1) - o.getR().cartesian(1);
      double angle = Math.atan2(deltaY, deltaX) - Math.atan2(dir.cartesian(1), dir.cartesian(0));
      if (Math.abs(angle) < FOV/2.0 && same) {
        if (player.highlightLevel(o) == 1)
          drawSup(o, angle);
        draw(o, angle);
      }
    }
  }
  
  public void draw(GameObject o, double angle) {
    this.dr.setLocationOnScreen(width / 2 + 1, 0);
    this.dr.setPenRadius(o.getLevel() * 0.01 + 0.025);
    this.dr.setPenColor(o.getColor());
    this.dr.point(Math.sin(angle), 0);
  }
  
  public void drawSup(GameObject o, double angle) {
    this.dr.setLocationOnScreen(width / 2 + 1, 0);
    this.dr.setPenRadius(o.getLevel() * 0.01 + 0.030);
    this.dr.setPenColor(Draw.RED);
    this.dr.point(Math.sin(angle), 0);
  }
}
