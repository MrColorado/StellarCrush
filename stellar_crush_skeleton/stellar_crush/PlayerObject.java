import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayerObject extends GameObject implements IViewPort {

 private static final Color DEFAULT_COLOR = StdDraw.WHITE;
 private static final Color DEFAULT_FACING_COLOR = StdDraw.BLACK;
 private static final double DEFAULT_FOV = Math.PI/2; // field of view of player's viewport
 private static final double FOV_INCREMENT = Math.PI/36; // rotation speed

 private Camera cam;
 
 public PlayerObject(Vector r, Vector v, double mass) {
   super(r, v, mass);
   this.cam = new Camera(this, DEFAULT_FOV);
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
     Draw dr = cam.getDraw();
     if (dr != null) {
       // Example code
       if (dr.isKeyPressed(KeyEvent.VK_UP)) 
         this.getV();
       if (dr.isKeyPressed(KeyEvent.VK_DOWN)) 
         down = true;
     }
   }
 }
 
 @Override 
 public Vector getLocation() {
   return this.getR();
 }
 
 @Override
 public Vector getFacingVector() {
   return this.getV().direction();
 }
 
 @Override 
 public double highlightLevel(GameObject o, PlayerObject p) {
   if (p.getMass() > o.getMass())
     return 1;
   return 0; 
 }
 
}
