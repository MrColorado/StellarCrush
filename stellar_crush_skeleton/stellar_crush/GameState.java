import java.util.*;
import java.util.HashSet;
import java.util.Iterator;
import java.time.Duration;
import java.time.Instant;


// This class is inspired by the nbody slide

public class GameState {
  // Class representing the game state and implementing main game loop update step.

  private final PlayerObject player;
  private Collection<GameObject> objects;
  private Instant previousTime;
  
  public GameState(PlayerObject player) {
    this.player = player;
    this.objects = GameObjectLibrary.createCollection();
    this.objects.add(this.player);
    this.previousTime = Instant.now();
  }
  
  public Collection<GameObject> getObjects() {
    return this.objects;
  }
  
  public void update(double delay) {
    // Main game loop update step
    Map<GameObject, Vector> forces = calculateForces();
    Duration deltaTime = Duration.between(this.previousTime, Instant.now());
    this.previousTime = Instant.now();
    for (GameObject o : forces.keySet()) {
      o.move(forces.get(o), delay);
    }
    GameObject newObject = null;
    for (GameObject o : this.objects) {
      o.setTime(o.getTime() - deltaTime.toMillis());
      if (o.getTime() <= 0 && o != player)
        newObject = o.split(); 
    }
    if (newObject != null)
      this.objects.add(newObject);
    forces = null;
    this.checkPosition();
    this.checkContact();
  }
  
  public void draw(Camera cam) {
    for (GameObject o : this.objects) {
      o.draw();
      /*if (o == this.player) 
        o.draw(this.player);*/
    }
    cam.render(this.objects);
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
        if (o != g && o.getR().distanceTo(g.getR()) / 5.0e10 < o.getLevel() * 0.001 + 0.025 && o.getLevel() > g.getLevel()) {          
          data.add(g);
          o.setLevel(o.getLevel() + g.getLevel());
          o.setMass(o.getMass() + g.getMass());
        }
      }
    }
    for(GameObject o : data) 
      this.objects.remove(o);
  }
}
