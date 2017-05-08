import java.util.*;
import java.util.HashSet;
import java.util.Iterator;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.awt.event.KeyEvent;

// This class is inspired by the nbody slide

public class GameState {
  // Class representing the game state and implementing main game loop update step.
  private static int nbr = 0;
  private final PlayerObject player;
  private Collection<GameObject> objects;
  private Instant previousTime;
  private double spawn;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
  public GameState(PlayerObject player) {
    this.player = player;
    this.objects = GameObjectLibrary.createCollection();
    this.objects.add(this.player);
    this.previousTime = Instant.now();
    this.spawn = Math.random() * 3000 + 2000;
  }
  
  /**************************************
  *                                     *
  *           Getter/Setter             *
  *                                     *
  **************************************/
  
  public Collection<GameObject> getObjects() {
    return this.objects;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  public void createPlanet() {
    Duration deltaTime = Duration.between(this.previousTime, Instant.now());
    this.spawn -= deltaTime.toMillis();
    if (this.spawn <= 0) {
      Random rand = new Random();
      int prob = rand.nextInt(4);
      Vector r , v;
      if (prob == 0) {
        double[] newR = {-5e10, 5e10};
        r = new Vector(newR);
        double[] newV = {10000, -10000};
        v = new Vector(newV);
      }
      else if (prob == 1) {
        double[] newR = {5e10, 5e10};
        r = new Vector(newR);
        double[] newV = {-10000, -10000};
        v = new Vector(newV);
      }
      else if (prob == 2) {
        double[] newR = {5e10, -5e10};
        r = new Vector(newR);
        double[] newV = {-10000, 10000};
        v = new Vector(newV);
      }
      else {
        double[] newR = {-5e10, -5e10};
        r = new Vector(newR);
        double[] newV = {10000, 10000};
        v = new Vector(newV);
      }
      GameObject o = new GameObject(r, v, 1E25);
      this.objects.add(o);
      this.spawn = Math.random() * 3000 + 2000;
    }
  }
  
  public void draw(Camera cam) {
    for (GameObject o : this.objects) {
      if (o != this.player)
        if (this.player.highlightLevel(o) == 1)
          o.drawSup();
        o.draw();
    }
    this.player.draw(this.player);
    cam.render(this.objects, this.player);
    cam.getDr().show(0);
  }

  private Map<GameObject, Vector> calculateForces() {
    Map<GameObject, Vector> map = GameObjectLibrary.createMap(this.objects);
    for (GameObject o : this.objects) {
      Vector nextForces = new Vector(2);
      for (GameObject g : this.objects) {
        if (o != g)
          nextForces = nextForces.plus(o.forceFrom(g));
      }
      map.put(o, nextForces);
    }
    return map;
  }
  
  public void checkPosition() {    
    for (GameObject o : this.objects) {
      double rx = o.getR().cartesian(0);
      double ry = o.getR().cartesian(1);
      if (rx > 5.7e10) {
        double[] position = {11.2e10, 0.0};
        o.setR(o.getR().minus(new Vector(position)));
      }
      if (ry > 5.7e10) {
        double[] position = {0.0, 11.2e10};
        o.setR(o.getR().minus(new Vector(position)));
      }
      if (rx < -5.7e10) {
        double[] position = {11.2e10, 0.0};
        o.setR(o.getR().plus(new Vector(position)));
      }
      if (ry < -5.7e10) {
        double[] position = {0.0, 11.2e10};
        o.setR(o.getR().plus(new Vector(position)));
      }
    }
  }
  
  public void checkContact() {
    Collection<GameObject> data = new HashSet<GameObject>();
    for (GameObject o : this.objects) {
      for (GameObject g : this.objects) {
        if (o != g && o.getR().distanceTo(g.getR()) < 5e10 * (o.getLevel() * 0.0005 + 0.025) && o.getLevel() > g.getLevel()) {          
          data.add(g);
          double massSum = g.getMass() + o.getMass();
          double vx = g.getMass() * (g.getV().cartesian(0) - o.getV().cartesian(0)) + o.getMass() * o.getV().cartesian(0) + g.getMass() * g.getV().cartesian(0);
          vx /= massSum;
          double vy = g.getMass() * (g.getV().cartesian(1) - o.getV().cartesian(1)) + o.getMass() * o.getV().cartesian(1) + g.getMass() * g.getV().cartesian(1);
          vy /= massSum;
          double[] newV = {vx, vy};
          Vector v = new Vector(newV);
          o.setV(v);
          o.setLevel(o.getLevel() + g.getLevel());
          o.setMass(o.getMass() + g.getMass());
        }
      }
    }
    for(GameObject o : data) 
      this.objects.remove(o);
  }
  
  public void update(double delay) {
    // Main game loop update step
    this.createPlanet();
    Map<GameObject, Vector> forces = calculateForces();
    for (GameObject o : forces.keySet()) {
      o.clampSpeed();
      o.move(forces.get(o), delay);
    }
       
    Duration deltaTime = Duration.between(this.previousTime, Instant.now());
    this.previousTime = Instant.now();
    GameObject newObject = null;
    for (GameObject o : this.objects) {
      o.setTime(o.getTime() - deltaTime.toMillis());
      if (o.getTime() <= 0 && o != player)
        newObject = o.split(); 
    }
    if (newObject != null)
      this.objects.add(newObject);
    forces = null;
//    if (StdDraw.isKeyPressed(KeyEvent.VK_P)) {
//      StdDraw.save("capture/screenCaptureFirstView" + nbr + ".png");
//      nbr++;
//    }
    this.checkPosition();
    this.checkContact();
  }
}
