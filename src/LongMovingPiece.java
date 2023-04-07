import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javalib.worldimages.Posn;

public abstract class LongMovingPiece extends Piece {

  public LongMovingPiece(ChessColor color, Posn position, String name) {
    super(color, position, name);
  }
  
  public List<Move> getDiagonalMoves(Board currentState) {
    List<Move> diagonalMoves = new ArrayList<>();
    diagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(1, 1)));
    diagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(-1, 1)));
    diagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(-1, -1)));
    diagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(1, -1)));
    return diagonalMoves;
  }
  
  public List<Move> getOrthagonalMoves(Board currentState) {
    List<Move> orthagonalMoves = new ArrayList<>();
    orthagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(1, 0)));
    orthagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(-1, 0)));
    orthagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(0, -1)));
    orthagonalMoves.addAll(this.getMovesInDirection(currentState, new Posn(0, 1)));
    return orthagonalMoves;
  }
  
  public List<Move> getMovesInDirection(Board currentState, Posn direction) {
    return Stream.iterate(this.position, currentState::inBounds, x -> Utils.add(x, direction))
      .skip(1)
      .takeWhile(Predicate.not(currentState::containsPiece))
      .map(x -> new Move(this, x))
      .collect(Collectors.toList());
  }
  
  public List<Capture> getDiagonalCaptures(Board currentState) {
    List<Capture> diagonalCaptures = new ArrayList<>();
    diagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(1, 1)));
    diagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(-1, 1)));
    diagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(-1, -1)));
    diagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(1, -1)));
    return diagonalCaptures;
  }
  
  public List<Capture> getOrthagonalCaptures(Board currentState) {
    List<Capture> orthagonalCaptures = new ArrayList<>();
    orthagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(1, 0)));
    orthagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(-1, 0)));
    orthagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(0, -1)));
    orthagonalCaptures.addAll(this.getCapturesInDirection(currentState, new Posn(0, 1)));
    return orthagonalCaptures;
  }
  
  public List<Capture> getCapturesInDirection(Board currentState, Posn direction) {
    return Stream.iterate(this.position, currentState::inBounds, x -> Utils.add(x, direction))
      .skip(1)
      .dropWhile(Predicate.not(currentState::containsPiece))
      .findFirst()
      .filter(x -> currentState.containsPiece(x, this.color.opposite()))
      .map(x -> new Capture(this, this.position, x, currentState.pieceAt(x)))
      .map(List::of)
      .orElse(List.of());
  }

}
