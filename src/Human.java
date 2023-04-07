import java.awt.Color;

public class Human implements Player {
  String name;
  ChessColor color;
  
  public String getName() {
    return this.name;
  }

  public ChessColor getColor() {
    return this.color;
  }

  public Human(String name, ChessColor color) {
    this.name = name;
    this.color = color;
  }

  public boolean canMoveAutomatically() {
    return false;
  }

  public Action getAutomaticAction(Board currentState) {
    throw new UnsupportedOperationException("Human cannot move automatically");
  }
}
