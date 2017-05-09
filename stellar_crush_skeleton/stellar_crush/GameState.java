import java.util.*;
import java.util.HashSet;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;
import java.awt.event.KeyEvent;

// This class is inspired by the nbody slide

public class GameState {
  // Class representing the game state and implementing main game loop update step.
  
  private boolean f = true;
  private final PlayerObject player;
  private Collection<GameObject> objects;
  private Instant previousTime;
  private double spawn;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
  /**
  * Constructor with one parameter
  * @param player it is the player of the game
  */
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
  
  /**
  * Function which give the Collection of the GameOject
  * @return the Collection of the GameObject
  */
  public Collection<GameObject> getObjects() {
    return this.objects;
  }
  
  /**
  * Function which give the f of the GameOject
  * @return the Collection of the GameObject
  */
  public boolean getF() {
    return this.f;
  }
  
  /**
  * Function with one parameter which give set the f of the GameOject
  * @paran f new value of this.f
  */
  public boolean setF(boolean f) {
    return this.f = f;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  /**
  * Function wich create a new GameObject from a corner
  */
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
  
  /**
  * Function with one parameter wich which draw all GameObject
  * @param cam it is the camera which is hold by the player 
  */
  public void draw(Camera cam) {
    for (GameObject o : this.objects) {
      if (o != this.player)
        if (this.player.highlightLevel(o) == 1)
          o.drawSup();
        o.draw();
    }
    this.player.draw();
    cam.render(this.objects, this.player);
    cam.getDr().show(0);
  }

  /**
  * Function with one parameter wich compute the forces that will be applied on each GameObject
  * @return a map which contain all GameObject and their associated forces
  */
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
  
  /**
  * Function wich check if a GameObject is still in our universe
  */
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
  
  /**
  * Function wich check if a GameObject have a contact with another one
  */
  public void checkContact() {
    Collection<GameObject> projectileToDestroy = new HashSet<GameObject>();
    Collection<GameObject> destroy = new HashSet<GameObject>();
    Collection<GameObject> data = new HashSet<GameObject>();
    for (GameObject o : this.objects) {
      for (GameObject g : this.objects) {
        if (o != g && o.getR().distanceTo(g.getR()) < 5e10 * (o.getLevel() * 0.0005 + 0.025) && o.getLevel() > g.getLevel()) {
          if (g instanceof Projectile && o != this.player) {
            projectileToDestroy.add(g);
            o.setLife(o.getLife() - 1);
            if (o.getLife() <= 0)
              destroy.add(o);
          }
          else {
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
    }
    for(GameObject o : data) 
      this.objects.remove(o);
    for(GameObject o : destroy) 
      this.objects.add(o.splitDestroy());
    for(GameObject o : projectileToDestroy) 
      this.objects.remove(o);
  }
  
  /**
  * Function with one parameter wich update the game
  * @param delay is the day between to actualisation
  */
  public void update(double delay) {
    // Main game loop update step
    
    Duration deltaTime = Duration.between(this.previousTime, Instant.now());
    this.previousTime = Instant.now();
    this.createPlanet();
    
    this.player.setDelay(this.player.getDelay() - deltaTime.toMillis());
    if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) || this.player.getCam().getDr().isKeyPressed(KeyEvent.VK_SPACE) 
          && this.player.getDelay() <= 0) {
      this.player.throwProjectile(this.objects);
      this.player.setDelay(1000);
    }
    if (f) {
      StdDraw.text(.1, 0.9, "Forces on player : ON");
      Map<GameObject, Vector> forces = calculateForces();
      for (GameObject o : forces.keySet()) {
        o.clampSpeed();
        o.move(forces.get(o), delay);
      }
    }
    else {
      StdDraw.text(.1, 0.9, "Forces on player : OFF");
      this.objects.remove(this.player);
      this.player.setV(new Vector(2));
      Map<GameObject, Vector> forces = calculateForces();
      this.objects.add(this.player);
      for (GameObject o : forces.keySet()) {
        o.clampSpeed();
        o.move(forces.get(o), delay);
      }
    }
    
    GameObject newObject = null;
    for (GameObject o : this.objects) {
      o.setTime(o.getTime() - deltaTime.toMillis());
      if (o.getTime() <= 0 && o != player)
        newObject = o.split(); 
    }
    if (newObject != null)
      this.objects.add(newObject);
    this.checkPosition();
    this.checkContact();
  }
}
