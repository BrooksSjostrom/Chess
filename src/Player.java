import java.awt.Color;

public interface Player {
  public String getName();
  public ChessColor getColor();
  public boolean canMoveAutomatically();
  public Action getAutomaticAction(Board currentState);
//  public boolean is
}
