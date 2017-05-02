import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayerObject extends GameObject implements IViewPort {

 private static final Color DEFAULT_COLOR = StdDraw.WHITE;
 private static final Color DEFAULT_FACING_COLOR = StdDraw.BLACK;
 private static final double DEFAULT_FOV = Math.PI/2; // field of view of player's viewport
 private static final double FOV_INCREMENT = Math.PI/36; // rotation speed

 private Camera cam;
 private double rot;
 
 public PlayerObject(Vector r, Vector v, double mass) {
   super(r, v, mass);
   this.cam = new Camera(this, DEFAULT_FOV);
   this.rot = Math.PI;
 }
 
 public Camera getCam() {
   return this.cam;
 }
 
 void processCommand(int delay) {
   boolean up; 
   boolean down;
   // Process keys applying to the player
   // Retrieve 
   if (cam != null) {
     // No commands if no draw canvas to retrieve them from!
     //Draw dr = cam.getDraw();
     //if (dr != null) {
       // Example code
       if (this.cam.getDr().isKeyPressed(KeyEvent.VK_UP))
         this.setR(this.getR().minus(this.getFacingVector().times(5e8)));
       if (this.cam.getDr().isKeyPressed(KeyEvent.VK_DOWN)) 
         this.setR(this.getR().plus(this.getFacingVector().times(5e8)));
       if (this.cam.getDr().isKeyPressed(KeyEvent.VK_LEFT)) {
         this.rot += FOV_INCREMENT;
       }
       if (this.cam.getDr().isKeyPressed(KeyEvent.VK_RIGHT)) {
         this.rot -= FOV_INCREMENT; 
       }
     //}
   }
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
   return v;
 }
 
 @Override 
 public double highlightLevel(GameObject o) {
   return 0; 
 }
}
