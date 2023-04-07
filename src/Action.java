import javalib.worldimages.*;

public interface Action {
  public Posn getLocation();
  public Board apply(Board currentState);
  // TODO: add purpose statements
  public String toSAN();
  public String toLAN();
  public boolean isPromotion();
  public void playSound();
}