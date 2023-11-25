import java.awt.Color;
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javalib.worldimages.Posn;

public class King extends ShortMovingPiece {
  boolean hasMoved;
  
  static List<Posn> kingDeltas = List.of(new Posn(1, 0), new Posn(1, -1), new Posn(0, -1), new Posn(-1, -1),
      new Posn(-1, 0), new Posn(-1, 1), new Posn(0, 1), new Posn(1, 1));
  
  public King(ChessColor color) {
    this(color, null);
  }

  public King(ChessColor color, Posn position) {
    this(color, position, false);
  }
  
  public King(ChessColor color, Posn position, boolean hasMoved) {
    super(color, position, "King", kingDeltas);
    this.hasMoved = hasMoved;
  }

  Piece movedTo(Posn posn) {
    return new King(this.color, posn);
  }

  Piece atPosn(Posn posn) {
    return new King(this.color, posn, this.hasMoved);
  }

  public boolean isKing() {
    return true;
  }

  List<Promotion> getPromotions(Board currentState) {
    return List.of();
  }

  List<Castle> getCastles(Board currentState) {
    if (this.hasMoved) {
      return List.of();
    }
    if (currentState.inCheck(this.color)) {
      return List.of();
    }
    return currentState.pieces.entrySet().stream()
      .map(Map.Entry::getValue)
      .filter(Piece::isRook)
      .filter(Predicate.not(Piece::hasMoved))
      .filter(rook -> {
        int leftX = Math.min(rook.position.x, this.position.x);
        int rightX = Math.max(rook.position.x, this.position.x);
        return IntStream.range(leftX + 1, rightX)
          .mapToObj(x -> new Posn(x, this.position.y))
          .allMatch(posn -> {
            return !currentState.containsPiece(posn) && !currentState.underThreat(posn, this.color.opposite());
          });
//              Predicate.not(currentState::containsPiece)
//                .and(Predicate.not(posn -> currentState.underThreat(posn, this.color)))
//          );
      })
      .map(rook -> new Castle(this, rook))
      .collect(Collectors.toList());
  }

  List<EnPassant> getEnPassants(Board currentState) {
    return List.of();
  }

  boolean isRook() {
    return false;
  }

  boolean hasMoved() {
    return this.hasMoved;
  }

  boolean justMovedTwo() {
    return false;
  }

  boolean isPawn() {
    return false;
  }

  String uppercaseLetter() {
    return "K";
  }
  
  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/fanfare.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  PieceType getType() {
    return PieceType.KING;
  }
}
