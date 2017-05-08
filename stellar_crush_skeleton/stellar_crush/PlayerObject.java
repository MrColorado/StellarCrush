import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayerObject extends GameObject implements IViewPort {

 private static final Color DEFAULT_COLOR = StdDraw.WHITE;
 private static final Color DEFAULT_FACING_COLOR = StdDraw.BLACK;
 private static final double DEFAULT_FOV = Math.PI/2; // field of view of player's viewport
 private static final double FOV_INCREMENT = Math.PI/36; // rotation speed

 private Camera cam;
 private double rot;
 
 /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
 
 public PlayerObject(Vector r, Vector v, double mass) {
   super(r, v, mass);
   this.cam = new Camera(this, DEFAULT_FOV);
   this.rot = 0;
 }
 
 /**************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/
 
 public double getRot() {
   return this.rot;
 }
 
 public Camera getCam() {
   return this.cam;
 }
 
 /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
 
 void processCommand(int delay) {
   if (cam != null) {
     // No commands if no draw canvas to retrieve them from!
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_UP))
       this.setR(this.getR().minus(this.getFacingVector().times(1e8)));
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_DOWN)) 
       this.setR(this.getR().plus(this.getFacingVector().times(1e8)));
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_LEFT)) 
       this.rot += FOV_INCREMENT;
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_RIGHT)) 
       this.rot -= FOV_INCREMENT; 
   }
 }
 
public int compare(GameObject o, GameObject g) {
   double distO = this.getR().distanceTo(o.getR());
   double distG = this.getR().distanceTo(g.getR());
   if (distO > distG)
     return 1;
   if (distO == distG)
     return 0;
   return -1;
 }

 public Vector getFacing() {
   return VectorUtil.direction(this.getR());
 }
 
 @Override 
 public Vector getLocation() {
   return this.getR();
 }
 
 @Override
 public Vector getFacingVector() {
   double[] rotation = {Math.cos(this.rot), Math.sin(this.rot)};
   Vector v = new Vector(rotation);
   v = VectorUtil.direction(v);
   Vector w = VectorUtil.direction(this.getR());
   v.plus(w);
   return v;
 }
 
 @Override 
 public double highlightLevel(GameObject o) {
   if (this.getLevel() < o.getLevel())
     return 1;
   return 0;
 }
}
