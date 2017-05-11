import java.util.Comparator;

public class DistComparator implements Comparator<GameObject> {
  
  private final IViewPort holder;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
 
  /**
  * Constructor with one parameter
  * @param holder the player
  */
  public DistComparator(IViewPort holder) {
    this.holder = holder;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  /**
  * Function with two parameter which compute which of these two GameObject is the most far from the player 
  * @param o first GameObject
  * @param g second GameObject
  */
  @Override 
  public int compare(GameObject o, GameObject g) {
    double distO = this.holder.getLocation().distanceTo(o.getR());
    double distG = this.holder.getLocation().distanceTo(g.getR());
    if (distO > distG)
      return -1;
    return 1;
  }
}
