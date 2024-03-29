import java.util.*;
import java.awt.Dimension;
import java.util.TreeSet;
import java.util.Comparator;   

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
  
  /**
  * Constructor with two parameters
  * @param holder the player
  * @FOV is the fiel of view of the player
  */  
  public Camera(IViewPort holder, double FOV) {
    // Constructs a camera with field of view FOV, held by holder, and rendered on canvas dr.
    this.holder = holder;
    this.FOV = FOV;
    this.dr = new Draw();
    this.dr.setLocationOnScreen(this.width / 2, 1);
    this.dr.setCanvasSize(this.width / 2, (int)(height * 0.92));
    this.dr.setXscale(FOV/2.0, -FOV/2.0);
    this.dr.setYscale(-1.0, 1.0);
  }
  
  /**************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/

  /**
  * Function with one parameter which give an acces to the Draw
  * @return the Draw
  */
  public Draw getDr() {
    return this.dr;
  }
  
  /**
  * Function with one parameter which create a new Draw
  * @return a new Draw
  */
  public Draw getDraw() {
    return new Draw();
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  /**
  * Function with one parameter which create a tree from an object collection in the aim to sort elements which are in
  * @param o first GameObject
  * @return a tree were there are collecntion's object sort 
  */
  public TreeSet<GameObject> createTreeSet(Collection<GameObject> objects) {
    Comparator<GameObject> comparator = new DistComparator(this.holder);
    TreeSet<GameObject> tree = new TreeSet<GameObject>(comparator);
    for (GameObject o : objects) {
      tree.add(o);
    }
    return tree;
  }
  
  /**
  * Function with two parameters which display on the Draw, GameObject that are in the player's FOV
  * @param objects all object that are in the universe
  * @param player it is the player 
  */
  void render(Collection<GameObject> objects, PlayerObject player) {
    // Renders the collection from the camera perspective
    this.dr.setLocationOnScreen(width / 2 + 1, 0);
    Vector pos = this.holder.getLocation();
    Vector dir = this.holder.getFacingVector();
    TreeSet<GameObject> tree = createTreeSet(objects);
    dr.clear();
    for(GameObject o : tree) {
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
  
  /**
  * Function with two parameters which draw a GameObject on the Draw
  * @param o GameObject that will be draw
  * @param angle is the angle between the player and the GameObject o
  */
  public void draw(GameObject o, double angle) {
    double dist = this.holder.getLocation().distanceTo(o.getR());
    this.dr.setLocationOnScreen(width / 2 + 1, 0);
    if (dist > 0)
      this.dr.setPenRadius(o.sizeToDisplay(dist, 0));
    else 
    this.dr.setPenRadius(o.getLevel() * 0.01 + 0.025);
    this.dr.setPenColor(o.getColor());
    this.dr.point(Math.sin(angle), 0);
  }
  
  /**
  * Function with two parameters draw a red circle around the GameObject
  * @param o GameObject that will be draw
  * @param angle is the angle between the player and the GameObject o
  */
  public void drawSup(GameObject o, double angle) {
    double dist = this.holder.getLocation().distanceTo(o.getR());
    this.dr.setLocationOnScreen(width / 2 + 1, 0);
    if (dist > 0)
      this.dr.setPenRadius(o.sizeToDisplay(dist, 0.005));
    else 
    this.dr.setPenRadius(o.getLevel() * 0.01 + 0.030);
    this.dr.setPenColor(Draw.RED);
    this.dr.point(Math.sin(angle), 0);
  }
}
