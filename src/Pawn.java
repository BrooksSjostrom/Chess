import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javalib.worldimages.Posn;

public class Pawn extends Piece {
  boolean justMovedTwo;
  
  public Pawn(Piece piece) {
    super(piece.color, piece.position, "Pawn");
    if (!piece.isPawn()) {
      throw new UnsupportedOperationException();
    } else {
      this.justMovedTwo = piece.justMovedTwo();
    }
  }
  
  public Pawn(ChessColor color) {
    super(color, null, "Pawn");
    this.justMovedTwo = false;
  }

  public Pawn(ChessColor color, Posn position) {
    super(color, position, "Pawn");
    this.justMovedTwo = false;
  }
  
  public Pawn(ChessColor color, Posn position, boolean justMovedTwo) {
    super(color, position, "Pawn");
    this.justMovedTwo = justMovedTwo;
  }
  
  Posn direction() {
    return this.color.equals(ChessColor.BLACK) ? new Posn(0, 1) : new Posn(0, -1);
  }

  List<Capture> getCaptures(Board currentState) {
    return this.getCapturesIncludingPromotions(currentState).stream()
//        .filter(capture -> capture.to.y != currentState.getBackLineY(Utils.opposite(this.color)))
        .collect(Collectors.toList());
  }
  
  List<Capture> getCapturesIncludingPromotions(Board currentState) {
    return Stream.of(new Posn(1, 0), new Posn(-1, 0))
        .map(posn -> Utils.add(posn, this.direction()))
        .map(posn -> Utils.add(posn, this.position))
        .filter(posn-> currentState.containsPiece(posn, this.color.opposite()))
        .map(posn -> new Capture(this, this.position, posn, currentState.pieces.get(posn)))
        .collect(Collectors.toList());
  }

  List<Move> getMoves(Board currentState) {
    return this.getMovesIncludingPromotions(currentState).stream()
//        .filter(move -> move.to.y != currentState.getBackLineY(Utils.opposite(this.color)))
        .collect(Collectors.toList());
  }
  
  List<Move> getMovesIncludingPromotions(Board currentState) {
    return Stream.of(this.direction(), this.position.y == ((this.direction().y + 7) % 7) ? Utils.multiply(this.direction(), 2) : null)
        .map(Optional::ofNullable)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(x -> Utils.add(this.position, x))
        .takeWhile(Predicate.not(currentState::containsPiece))
        .map(x -> new Move(this, this.position, x))
        .collect(Collectors.toList());
  }

  Piece movedTo(Posn posn) {
    if (this.position != null && Math.abs(this.position.y - posn.y) == 2) {
      return new Pawn(this.color, posn, true);
    } else {
      return new Pawn(this.color, posn, false);
    }
  }
  
  Piece atPosn(Posn posn) {
    return new Pawn(this.color, posn, this.justMovedTwo);
  }

  boolean isKing() {
    return false;
  }

  List<Promotion> getPromotions(Board currentState) {
    return Stream.concat(
        this.getMovesIncludingPromotions(currentState).stream(),
        this.getCapturesIncludingPromotions(currentState).stream()
    )
        .filter(action -> action.getLocation().y == currentState.getBackLineY(this.color.opposite()))
        .map(action -> new Promotion(this, this.position, action.getLocation(), new Queen(this.color, action.getLocation())))
        .collect(Collectors.toList());
  }

  List<Castle> getCastles(Board currentState) {
    return List.of();
  }

  List<EnPassant> getEnPassants(Board currentState) {
    return currentState.pieces.entrySet().stream()
        .map(Map.Entry::getValue)
        .filter(Piece::isPawn)
        .filter(Piece::justMovedTwo)
        .filter(pawn -> Math.abs(pawn.position.x - this.position.x) == 1 && pawn.position.y == this.position.y)
        .map(Pawn::new)
        .map(pawn -> new EnPassant(this, pawn))
        .collect(Collectors.toList());
  }

  boolean isRook() {
    return false;
  }

  boolean hasMoved() {
    throw new UnsupportedOperationException();
  }

  boolean justMovedTwo() {
    return this.justMovedTwo;
  }

  boolean isPawn() {
    return true;
  }

  int positionScore() {
    return this.color.equals(Color.WHITE) ? PieceTables.WPAWN_POSITION_TABLE[this.position.y * 16 + this.position.x] : PieceTables.BPAWN_POSITION_TABLE[this.position.y * 16 + this.position.x];
  }

  String uppercaseLetter() {
    return "";
  }
  
  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/notify.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
