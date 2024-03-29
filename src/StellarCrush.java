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
  private static final int GAME_DELAY_TIME = 5000; // in-game time units between frame updates
  private static int nbr1 = 0;
  private static int nbr2 = 0;
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  /**
  * Function with one parameters which draw the GameOver message
  * @param player it is the player 
  */
  public static void gameOver(PlayerObject player) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.text(0.5, 0.5, "GAME OVER");
    StdDraw.show(1);
    player.getCam().getDr().clear();
    player.getCam().getDr().setPenColor(StdDraw.RED);
    player.getCam().getDr().text(0, 0, "GAME OVER");
    player.getCam().getDr().show(1);
    while (true)
      if (StdDraw.isKeyPressed(KeyEvent.VK_M) || player.getCam().getDr().isKeyPressed(KeyEvent.VK_M))
        System.exit(0);
  }
  
  /**
  * Function with one parameters which draw the GameWin message
  * @param player it is the player 
  */
  public static void gameWin(PlayerObject player) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.text(0.5, 0.5, "WIN");
    StdDraw.show(1);
    player.getCam().getDr().clear();
    player.getCam().getDr().setPenColor(StdDraw.RED);
    player.getCam().getDr().text(0, 0, "WIN");
    player.getCam().getDr().show(1);
    while (true)
      if (StdDraw.isKeyPressed(KeyEvent.VK_M) || player.getCam().getDr().isKeyPressed(KeyEvent.VK_M))
        System.exit(0);
  }
  
  /**
  * Function which start the game
  */
  public static boolean startGame() {
    PlayerObject player = GameObjectLibrary.createPlayer();
    GameState game = new GameState(player);
    boolean win = false, loose = false;
    while (!win && !loose) {
      StdDraw.clear();
      player.getCam().getDr().clear();
      player.processCommand();
      game.update(GAME_DELAY_TIME);
      game.draw(player.getCam());
      if (!game.getObjects().contains(player))
        loose = true;
      win = true;
      for (GameObject o : game.getObjects()) {
        if (!(o instanceof Trap) && !(o instanceof PlayerObject)) {
          win = false;
        }
      }
      if (StdDraw.isKeyPressed(KeyEvent.VK_F) || player.getCam().getDr().isKeyPressed(KeyEvent.VK_F))
        game.setF(!game.getF());
      if (StdDraw.isKeyPressed(KeyEvent.VK_P)) {
        StdDraw.save("capture/thirdView/screenCapture" + nbr1 + ".png");
        nbr1++;
      }
      if (player.getCam().getDr().isKeyPressed(KeyEvent.VK_P)) {
        player.getCam().getDr().save("capture/firstView/screenCapture" + nbr2 + ".png");
        nbr2++;
      }
      if (StdDraw.isKeyPressed(KeyEvent.VK_M) || player.getCam().getDr().isKeyPressed(KeyEvent.VK_M))
        System.exit(0);
      StdDraw.show(1);
    }
    while (loose)
      gameOver(player);
    while (win)
      gameWin(player);
    return false;
  }
  
  /**************************************
  *                                     *
  *                main                 *
  *                                     *
  **************************************/
  
  /**
  * Function main function
  * @param args this parameter is not use
  */
  public static void main(String[] args) {
    // https://www.greenfoot.org/topics/1127
    Dimension scrnSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int height = (int)scrnSize.getHeight();
    int width  = (int)scrnSize.getWidth();
    //
    StdDraw.setCanvasSize(width / 2, (int)(height * 0.92));
    StdDraw.setXscale(0, 1);
    StdDraw.setYscale(0, 1);
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.text(0.5, 0.75, "STELLAR CRUSH");
    StdDraw.text(0.5, 0.65, "Press any key to start");
    StdDraw.text(0.5, 0.45, "Arrows to rotate left or right, accelerate or decelerate");
    StdDraw.text(0.5, 0.35, "You are borg. Assimilate all who stand against you !");
    StdDraw.text(0.5, 0.25, "Quit (m). Chose your view and make your screen capture (p)");
    StdDraw.text(0.5, 0.15, "You can substitute forces from and on the player (f). You can throw projectile to devide them (space)");
    
    while (true) {
      if (StdDraw.isKeyPressed(KeyEvent.VK_M))
        System.exit(0);
      if (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && StdDraw.hasNextKeyTyped()) {
        startGame();
      }
    }
  }
}
