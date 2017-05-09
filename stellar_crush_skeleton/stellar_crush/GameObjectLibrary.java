import java.util.*;
import java.util.Random;
import java.util.Map;
import java.util.HashSet;

public class GameObjectLibrary {
// Class for defining various game objects, and putting them together to create content
// for the game world.  Default assumption is objects face in the direction of their velocity, and are spherical.
  
  // UNIVERSE CONSTANTS - TUNED BY HAND FOR RANDOM GENERATION
  private static final double ASTEROID_RADIUS = 0.5; // Location of asteroid belt for random initialization
  private static final double ASTEROID_WIDTH = 0.2; // Width of asteroid belt
  private static final double ASTEROID_MASS = 1E25;
  private static final double PLAYER_MASS = 1E25;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  /**
  * Function wich create a new player
  * @return a new player
  */
  public static PlayerObject createPlayer() {
    double[] velocity = {0.0, 0.0};
    Vector v = new Vector(velocity);
    PlayerObject player = new PlayerObject(new Vector(2), v, PLAYER_MASS);
    return player;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  /**
  * Function wich create a Collection of GameObject
  * @return a Collection of GameObject
  */
  public static Collection<GameObject> createCollection() {
    int n = 50;
    double distance = 2.0 * Math.PI / (double)n;
    Random rand = new Random();
    StdDraw.setXscale(-5.0e10 , 5.0e10 );
    StdDraw.setYscale(-5.0e10 , 5.0e10 );
    Collection<GameObject> data = new HashSet<GameObject>();
   
    // read in the N bodies
    for (int i = 0; i < n; i++) {
      double body = distance * (double)i;
      double rx = Math.cos(body) * 3e10;
      double ry = Math.sin(body) * 3e10;
      double vx = rand.nextInt(10000);
      double vy = rand.nextInt(10000);
      if (Math.random() > .5)
        vx *= -1;
      if (Math.random() > .5)
        vy *= -1;
      double mass = ASTEROID_MASS;
      double[] position = { rx, ry };
      double[] velocity = { vx, vy };
      Vector r = new Vector(position);
      Vector v = new Vector(velocity);
      GameObject newPlanet = new GameObject(r, v, mass);
      data.add(newPlanet);
    }
    return data;
  }
  
  /**
  * Function with one parameter wich create a map from a GameObject Collection
  * @return a map of GameObject
  */
  public static Map<GameObject, Vector> createMap (Collection<GameObject> object) {
    Map<GameObject, Vector> map = new LinkedHashMap<GameObject, Vector>();
    for (GameObject o : object) { 
      map.put(o, new Vector(2));
    }
    return map;
  }
}
