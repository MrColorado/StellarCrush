import java.awt.event.KeyEvent;
import java.awt.Dimension;

/* Acknowledgements/notes:
 - Some of this code based on code for Rubrica by Steve Kroon
 - Original inspiration idea for this project was IntelliVision's AstroSmash, hence the name
 */

/* Ideas for extensions/improvements:
 PRESENTATION:
 -theme your game
 -hall of fame/high score screen
 -modifiable field of view, rear-view mirror, enhance first-person display by showing extra information on screen
 -mouse control
 -autoscaling universe to keep all universe objects on screen (or making the edge of the universe repel objects)
 -better rendering in camera (better handling of objects on edges, and more accurate location rendering
 -improved gameplay graphics, including pictures/sprites/textures for game objects
 -add sounds for for various game events/music: Warning: adding both sounds and music will likely lead to major
 headaches and frustration, due to the way the StdAudio library works.  If you go down this route, you choose
 to walk the road alone...
 -full 3D graphics with 3D universe (no libraries)
 
 MECHANICS/GAMEPLAY CHANGES:
 -avoid certain other game objects rather than/in addition to riding into them
 -more interactions - missiles, auras, bombs, explosions, shields, etc.
 -more realistic physics for thrusters, inertia, friction, momentum, relativity?
 -multiple levels/lives
 -energy and hit points/health for game objects and players
 -multi-player mode (competitive/collaborative)
 -checking for impacts continuously during moves, rather than at end of each time step
 -Optimize your code to be able to deal with more objects (e.g. with a quad-tree) - document the improvement you get
 --QuadTree implementation with some of what you may want at : http://algs4.cs.princeton.edu/92search/QuadTree.java.html
 --https://github.com/phishman3579/java-algorithms-implementation/blob/master/src/com/jwetherell/algorithms/data_structures/QuadTree.java may also be useful - look at the Point Region Quadtree
 */

public class StellarCrush {
  // Main game class
  
  // CONSTANTS TUNED FOR GAMEPLAY EXPERIENCE
  static final int GAME_DELAY_TIME = 5000; // in-game time units between frame updates
  static final int TIME_PER_MS = 1000; // how long in-game time corresponds to a real-time millisecond
  static final double G = 6.67e-11; // gravitational constant
  static final double softE = 0.001; // softening factor to avoid division by zero calculating force for co-located objects
  static double scale = 5e10; // plotted universe size
  
  public static boolean gameOver(PlayerObject player) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.text(0.5, 0.5, "GAME OVER");
    StdDraw.show(1);
    player.getCam().getDr().clear();
    player.getCam().getDr().setPenColor(StdDraw.RED);
    player.getCam().getDr().text(0, 0, "GAME OVER");
    player.getCam().getDr().show(1);
    if (StdDraw.isKeyPressed(KeyEvent.VK_M))
      return false;
    return true;
  }
  
  public static boolean gameWin(PlayerObject player) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.text(0.5, 0.5, "WIN");
    StdDraw.show(1);
    player.getCam().getDr().clear();
    player.getCam().getDr().setPenColor(StdDraw.RED);
    player.getCam().getDr().text(0, 0, "WIN");
    player.getCam().getDr().show(1);
    if (StdDraw.isKeyPressed(KeyEvent.VK_M))
      return false;
    return true;
  }
  
  public static boolean startGame() {
    PlayerObject player = GameObjectLibrary.createPlayer();
    GameState game = new GameState(player);
    boolean win = false, loose = false;
    while (!win && !loose) {
      StdDraw.clear();
      player.getCam().getDr().clear();
      player.processCommand(GAME_DELAY_TIME);
      game.update(GAME_DELAY_TIME);
      game.draw(player.getCam());
      if (!game.getObjects().contains(player))
        loose = true;
      if (game.getObjects().size() == 1)
        win = true;
      StdDraw.show(1);
    }
    while (loose)
      loose = gameOver(player);
    while (win)
      win = gameWin(player);
    return false;
  }
  
  public static void main(String[] args) {
    // https://www.greenfoot.org/topics/1127
    Dimension scrnSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int height = (int)scrnSize.getHeight();
    int width  = (int)scrnSize.getWidth();
    //
    StdDraw.setCanvasSize(width / 2, (int)(height * 0.90));
    StdDraw.setXscale(0, 1);
    StdDraw.setYscale(0, 1);
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.text(0.5, 0.75, "STELLAR CRUSH");
    StdDraw.text(0.5, 0.65, "Press any key to start");
    StdDraw.text(0.5, 0.45, "Arrows to rotate left or right, accelerate or decelerate");
    StdDraw.text(0.5, 0.35, "You are borg. Assimilate all who stand against you !");
    StdDraw.text(0.5, 0.25, "Quit (m)");
    
    boolean b = true;
    while (b) {
      if (StdDraw.isKeyPressed(KeyEvent.VK_M))
        return;
      if (StdDraw.hasNextKeyTyped()) {
        startGame();
      }
    }
    return;
  }
}
