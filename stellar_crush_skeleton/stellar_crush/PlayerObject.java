import java.util.*;
import java.awt.event.KeyEvent;

public class PlayerObject extends GameObject implements IViewPort {

 private static final double DEFAULT_FOV = Math.PI/2; // field of view of player's viewport
 private static final double FOV_INCREMENT = Math.PI/36; // rotation speed

 private Camera cam;
 private double rot;
 private long delay = 2000;
 
 /***************************************
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
 public PlayerObject(Vector r, Vector v, double mass) {
   super(r, v, mass);
   this.cam = new Camera(this, DEFAULT_FOV);
   this.rot = 0;
 }
 
 /***************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/
 
 /**
  * Function which give the current rotation of the Player
  * @return the current rotation of the player
  */
 public double getRot() {
   return this.rot;
 }
 
 /**
  * Function which give the camera of the Player
  * @return the camera of the player
  */
 public Camera getCam() {
   return this.cam;
 }
 
 /**
  * Function which give the time before the next available projectile
  * @return the delay of the player
  */
 public long getDelay() {
   return this.delay;
 }
 
 /**
  * Function with one parameter which set the time before the next available projectile
  * @return the new delay of the player
  */
 public void setDelay(long delay) {
   this.delay = delay;
 }
 
 /***************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
 
 /**
  * Function with one parameter wich will throw a projectile from the player
  * @param object the collection that contain all GameObject in the universe
  */
 public void throwProjectile(Collection<GameObject> objects) {
   double radius = (this.getLevel() * 0.0001 + 0.025) * 5e10;
   double x = this.getR().cartesian(0) - Math.cos(rot) * radius;
   double y = this.getR().cartesian(1) - Math.sin(rot) * radius;   
   
   double[] newRDouble = {x, y};
   Vector newR = new Vector(newRDouble);
   double[] newVDouble = {-Math.cos(rot) * 1e5, -Math.sin(rot) * 1e5};
   Vector newV = new Vector(newVDouble);
   Projectile proj = new Projectile(newR, newV, 1E25, 1);
   proj.setLevel(1);
   proj.setMass(1e20);
   objects.add(proj);
 }
 
 /**
  * Function wich will modify location and the rotation of the player
  */
 public void processCommand() {
   if (cam != null) {
     // No commands if no draw canvas to retrieve them from!
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_UP) || StdDraw.isKeyPressed(KeyEvent.VK_UP))
       this.setR(this.getR().minus(this.getFacingVector().times(1e8)));
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_DOWN) || StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) 
       this.setR(this.getR().plus(this.getFacingVector().times(1e8)));
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_LEFT) || StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) 
       this.rot += FOV_INCREMENT;
     if (this.cam.getDr().isKeyPressed(KeyEvent.VK_RIGHT) || StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) 
       this.rot -= FOV_INCREMENT; 
   }
 }
 
 /**
  * Function which draw the player on the stdDraw
  */
 @Override
 public void draw() {
   super.draw(); 
   double radius = (this.getLevel() * 0.0001 + 0.025) * 2.5e10;
   double x = this.getR().cartesian(0) - Math.cos(rot) * radius;
   double y = this.getR().cartesian(1) - Math.sin(rot) * radius;
   StdDraw.setPenRadius(0.01);
   StdDraw.setPenColor(StdDraw.RED);
   StdDraw.point(x, y);
   
 }
 
 /**
  * Function which give the location of the player
  * @return the location of the player
  */
 @Override 
 public Vector getLocation() {
   return this.getR();
 }
 
 /**
  * Function which give the facing vector of the GameOject
  * @return the facing vector of the GameObject
  */
 @Override
 public Vector getFacingVector() {
   double[] rotation = {Math.cos(this.rot), Math.sin(this.rot)};
   Vector v = new Vector(rotation);
   v = VectorUtil.direction(v);
   Vector w = VectorUtil.direction(this.getR());
   v.plus(w);
   return v;
 }
 
 /**
  * Function which indicates if a GameObject is bigger that the player
  * @param o GameObject that is comparate to the player
  * @return if a GameObject is bigger that the player
  */
 @Override 
 public double highlightLevel(GameObject o) {
   if (this.getLevel() < o.getLevel())
     return 1;
   return 0;
 }
}
