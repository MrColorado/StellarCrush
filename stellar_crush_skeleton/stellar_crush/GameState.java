import java.util.*;
import java.util.LinkedHashMap;

public class GameState {
  // Class representing the game state and implementing main game loop update step.

  private Collection<GameObject> objects;
  //private final PlayerObject player;
  
  private int N;
  private double radius;
  private GameObject[] orbs;
  
  public GameState(/*PlayerObject player*/) {
    //this.player = player;
    
    N = StdIn.readInt();
    radius = StdIn.readDouble();
    StdDraw.setXscale(-radius, +radius);
    StdDraw.setYscale(-radius, +radius);
    // read in the N bodies
    orbs = new GameObject[N];
    for (int i = 0; i < N; i++) {
      double rx = StdIn.readDouble();
      double ry = StdIn.readDouble();
      double vx = StdIn.readDouble();
      double vy = StdIn.readDouble();
      double mass = StdIn.readDouble();
      double[] position = { rx, ry };
      double[] velocity = { vx, vy };
      Vector r = new Vector(position);
      Vector v = new Vector(velocity);
      orbs[i] = new GameObject(r, v, mass);
    } 
  }
  
  public void update(double delay) {
    // Main game loop update step
    Vector[] f = new Vector[N];
    for (int i = 0; i < N; i++)
      f[i] = new Vector(new double[2]);
    
    //Map<GameObject, Vector> data = new LinkedHashMap<GameObject, Vector>();
    
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
      if (i != j) 
          f[i] = f[i].plus(orbs[i].forceFrom(orbs[j]));
    
    System.out.println("position x : " + orbs[0].r.data[0] + " position y : " + orbs[0].r.data[1]);
    System.out.println();
    
    for (int i = 0; i < N; i++)
      orbs[i].move(f[i], delay);
  }
  
  public void draw() {
    for (int i = 0; i < N; i++)
      orbs[i].draw();
  } 

  private Map<GameObject, Vector> calculateForces() {
    return null;
  }
  
  public static void main(String[] args) {
    
    GameState game = new GameState();
    double dt = Double.parseDouble(args[0]); 
    while (true) {
      StdDraw.clear();
      game.update(dt);
      game.draw();
      StdDraw.show(10);
    }
  }
}
