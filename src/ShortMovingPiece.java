import java.awt.Color;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javalib.worldimages.Posn;

public abstract class ShortMovingPiece extends Piece {
  
  List<Posn> deltas;
  
  public ShortMovingPiece(ChessColor color, Posn position, String name) {
    super(color, position, name);
  }

  public ShortMovingPiece(ChessColor color, Posn position, String name, List<Posn> deltas) {
    super(color, position, name);
    this.deltas = deltas;
  }

  List<Capture> getCaptures(Board currentState) {
    return this.deltas.stream()
        .map(x -> Utils.add(x, this.position))
        .filter(x -> currentState.containsPiece(x, this.color.opposite()))
        .map(x -> new Capture(this, this.position, x, currentState.pieces.get(x)))
        .collect(Collectors.toList());
  }

  List<Move> getMoves(Board currentState) {
    return this.deltas.stream()
        .map(x -> Utils.add(x, this.position))
        .filter(Predicate.not(currentState::containsPiece))
        .map(x -> new Move(this, this.position, x))
        .collect(Collectors.toList());
  }
}
