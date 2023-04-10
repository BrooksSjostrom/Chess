import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlphaBetaPruningAI implements Player {
  String name;
  ChessColor color;

  public AlphaBetaPruningAI(String name, ChessColor color) {
    this.name = name;
    this.color = color;
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
    return currentState.getPiecesOfColor(this.color).stream()
        .flatMap(piece -> piece.getActions(currentState).stream())
        .max(Comparator.comparing(action -> this.alphaBeta(action.apply(currentState), 3, Integer.MIN_VALUE, Integer.MAX_VALUE, false)))
        .orElse(new WuWei());
  }

  public int alphaBeta(Board node, int depth, int alpha, int beta, boolean maximizingPlayer) {
//    if depth == 0 or node is a terminal node
    if (depth == 0) {
//      return heuristic value of node
      return new SimpleEvaluator().eval(node, maximizingPlayer ? this.color : this.color.opposite());
    }
    if (maximizingPlayer) {
      int value = Integer.MIN_VALUE;
      List<Board> children = node.getPiecesOfColor(this.color).stream()
          .flatMap(piece -> piece.getActions(node).stream())
          .map(action -> action.apply(node))
          .collect(Collectors.toList());
      for (Board child : children) {
        value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta, false));
        if (value > beta) {
          break;
        }
        alpha = Math.max(alpha, value);
      }
      return value;
    } else {
      int value = Integer.MAX_VALUE;
      List<Board> children = node.getPiecesOfColor(this.color.opposite()).stream()
          .flatMap(piece -> piece.getActions(node).stream())
          .map(action -> action.apply(node))
          .collect(Collectors.toList());
      for (Board child : children) {
        value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta, true));
        if (value < alpha) {
          break;
        }
        beta = Math.min(beta, value);
      }
      return value;
    }
  }
}
