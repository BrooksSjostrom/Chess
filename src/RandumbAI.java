import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandumbAI implements Player {
  String name;
  ChessColor color;
  Random random;

  public RandumbAI(String name, ChessColor color) {
    this(name, color, new Random());
  }

  public RandumbAI(String name, ChessColor color, Random random) {
    this.name = name;
    this.color = color;
    this.random = random;
  }

  public String getName() {
    return this.name;
  }

  public ChessColor getColor() {
    return this.color;
  }

  public boolean canMoveAutomatically() {
    return true;
  }

  public Action getAutomaticAction(Board currentState) {
    List<Action> allActions = currentState.pieces.entrySet().stream()
        .filter(entry -> entry.getValue().color.equals(this.color))
        .flatMap(entry -> entry.getValue().getActions(currentState).stream())
        .collect(Collectors.toList());
    if (allActions.isEmpty()) {
      if (currentState.inCheck(this.color)) {
//        System.out.println(String.format("Checkmate %s loses", this.name));
      } else {
//        System.out.println("Stalemate");
      }
      return new WuWei();
    }
    return allActions.get(random.nextInt(allActions.size()));
  }

}
