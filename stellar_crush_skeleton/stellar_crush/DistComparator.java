import java.util.Comparator;

public class DistComparator implements Comparator<GameObject> {
  
  private final IViewPort holder;
  
  /**************************************
  *                                     *
  *             Constructor             *
  *                                     *
  **************************************/
  
  public DistComparator(IViewPort holder) {
    this.holder = holder;
  }
  
  /**************************************
  *                                     *
  *               Method                *
  *                                     *
  **************************************/
  
  @Override 
  public int compare(GameObject o, GameObject g) {
    double distO = this.holder.getLocation().distanceTo(o.getR());
    double distG = this.holder.getLocation().distanceTo(g.getR());
    if (distO > distG)
      return -1;
    return 1;
  }
}
