import java.util.*;

// This class is inspired by the nbody slide

public class GameState {
  // Class representing the game state and implementing main game loop update step.

  private Collection<GameObject> objects;
  private final PlayerObject player;
  
  public GameState(PlayerObject player) {
    this.player = player;
    this.objects = GameObjectLibrary.createCollection();
    this.objects.add(this.player);    
  }
  
  public void update(double delay) {
    // Main game loop update step
    Map<GameObject, Vector> forces = calculateForces();
    for (GameObject o : forces.keySet()) {
      o.move(forces.get(o), delay);
    }
  }
  
  public void draw(Camera cam) {
    for (GameObject o : this.objects) {
      o.draw();
      if (o == this.player) 
        o.draw(this.player);
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
  
  public void checkContact () {
    for (Object o : this.objects) {
      
    }
  }
  
  public static void main(String[] args) {
    PlayerObject fakePlayer = GameObjectLibrary.createPlayer();
    GameState game = new GameState(fakePlayer);      
    double dt = 10000; 
    while (true) {
      StdDraw.clear();
      fakePlayer.getCam().getDr().clear();
      game.update(dt);
      game.checkPosition();
      game.draw(fakePlayer.getCam());
      StdDraw.show(10);
    }
  }
}
