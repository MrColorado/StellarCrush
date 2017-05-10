# StellarCrush

StellarCrush is a game in which you are a planet. Your goal is to absorb all the other planets. This game was fully written in Java.

# How play
    
  - First you need to unzip the file 21450668_project.zip
  - You need to compile each `.java` file that are located in the folder 21450668_project
  - In order to start the game, if you are using an integrated development environment, you just need to run the `StellarCrush` file. If you are using command line, you need to execute the command line:
```sh
$ java StellarCrush
```
  - Then there will be a menu which explains you all the keyboard keys useful for StallarCrush

  - Press any key to start the game

# Interface inheritance 

The PlayerObject class is implementing the IViewPort interface.
```
public interface IViewPort {
  Vector getLocation(); // location of camera
  Vector getFacingVector(); // direction camera is facing in
  double highlightLevel(GameObject o); // highlight objects below this mass
  void draw(); // draw a point on the player which indicate the player direction
}
````

# Class inheritance 

The PlayerObject class extend the GameObject class.
```
public PlayerObject(Vector r, Vector v, double mass) {
   super(r, v, mass);
   this.cam = new Camera(this, DEFAULT_FOV);
   this.rot = 0;
 }
```
The Projectile class extend the GameObject class.
```
public Projectile(Vector r, Vector v, double mass, double dmg) {
    super(r, v, mass);
    this.dmg = dmg;
  }
```
The trap class also extend the GameObject class.

#  Additional work

  - In first view, the planets which are in front of the player are displayed starting from the farthest to the closest one. It takes into consideration the size and the distance between the player and other planets to display them with a good size on the first view.
  - There is a system of projectile that allow the player to throw projectile than can divide in two parts a planet. If you hit a planet three times, The planet will divide in two parts.
  - There is a system of trap, if a planet touch a trap, even if it is the player this planet will be destroyed.
  - You can destroy a trap with one projectile.
  - There is a level system for the player and each planet, according to their level, they can absorb or be absorbed by other planets.
  - There is an age system for each planet that allows them to create new planets according to their age.
  - New planets are created and thrown in the middle of the universe from the corners of the screen according to the player's level in order to create some suspence (even if the player is the biggest planet in the universe).
  - In order not to lose sight of the planets in the universe, when a planet go out of the screen from one side, this same planet come back from the opposite side.
  - There is a little red point on the player in the third view, that allows him to know in which direction he is going.
  - Possibility to make screen capture, from the first or third view.
  - Each planet have their own color.
  - Each planet have their own size depending on their level.
  - There is two way to end the game and not just one. One if your a win and one if you lose
  
# Deviation from the provided skeleton

There is no real deviation from the skeleton provided, I just added a new class `DistComparator` which implements `Comparator<GameObject>`. This class allows me to sort each planet in the universe (according to their distance from the player) using a `TresSet`. I also add the `Trap` and `Projectile` class which extend `GameObject`.

# Additional libraries required

```
java.util.*
java.util.TreeSet
java.util.Comparator
java.util.Random
java.util.Map
java.util.HashSet
java.awt.event.KeyEvent
java.awt.Dimension
java.awt.Dimension
java.awt.Color
java.time.Duration
java.time.Instant
```
# Changes made to the standard library

Original fonction from Draw.java : 
```
public void setLocationOnScreen(int x, int y) {
        if (x <= 0 || y <= 0) throw new IllegalArgumentException();
        frame.setLocation(x, y);
    }
```

The same function after modification : 
```
public void setLocationOnScreen(int x, int y) {
        frame.setLocation(x, y);
    }
```