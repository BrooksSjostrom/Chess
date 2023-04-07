import java.awt.Color;
import java.io.File;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javalib.worldimages.Posn;

public class Knight extends ShortMovingPiece {
  
  static List<Posn> knightDeltas = List.of(new Posn(2, -1), new Posn(1, -2), new Posn(-1, -2), new Posn(-2, -1),
      new Posn(-2, 1), new Posn(-1, 2), new Posn(1, 2), new Posn(2, 1));
  
  public Knight(ChessColor color) {
    this(color, null);
  }

  public Knight(ChessColor color, Posn position) {
    super(color, position, "Knight", knightDeltas);
  }

  Piece movedTo(Posn posn) {
    return new Knight(this.color, posn);
  }
  
  Piece atPosn(Posn posn) {
    return this.movedTo(posn);
  }

  boolean isKing() {
    return false;
  }

  List<Promotion> getPromotions(Board currentState) {
    return List.of();
  }

  List<Castle> getCastles(Board currentState) {
    return List.of();
  }

  List<EnPassant> getEnPassants(Board currentState) {
    return List.of();
  }

  boolean isRook() {
    return false;
  }

  boolean hasMoved() {
    throw new UnsupportedOperationException();
  }

  boolean justMovedTwo() {
    return false;
  }

  boolean isPawn() {
    return false;
  }

  int positionScore() {
    return PieceTables.KNIGHT_POSITION_TABLE[this.position.y * 16 + this.position.x];
  }

  String uppercaseLetter() {
    return "N";
  }

//  List<Move> getMoves(Board currentState) {
//    return kingDeltas.stream()
//        .map(x -> Utils.add(x, this.position))
//        .filter(Predicate.not(currentState::containsPiece))
//        .map(x -> new Move(this, this.position, x))
//        .collect(Collectors.toList());
//  }

  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/neigh.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
