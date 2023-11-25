import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javalib.worldimages.Posn;

public class Bishop extends LongMovingPiece {
  
  public Bishop(ChessColor color) {
    this(color, null);
  }

  public Bishop(ChessColor color, Posn position) {
    super(color, position, "Bishop");
  }

  List<Capture> getCaptures(Board currentState) {
    return this.getDiagonalCaptures(currentState);
  }

  List<Move> getMoves(Board currentState) {
    return this.getDiagonalMoves(currentState);
  }

  Piece movedTo(Posn posn) {
    return new Bishop(this.color, posn);
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

  String uppercaseLetter() {
    return "B";
  }

  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/church.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  PieceType getType() {
    return PieceType.BISHOP;
  }
}
