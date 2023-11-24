import javalib.worldimages.Posn;

public class WuWei implements Action {
  Piece piece;

  public Posn getLocation() {
    return piece.position;
  }

  public Board apply(Board currentState) {
    // Does nothing
    return currentState;
  }

  public String toSAN() {
    return "";
  }

  public boolean isPromotion() {
    return false;
  }

  public String toLAN() {
    return "";
  }

  public void playSound() {
  }

}
