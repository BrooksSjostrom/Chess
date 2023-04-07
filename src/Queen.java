import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.util.ArrayList;

import javalib.worldimages.Posn;

public class Queen extends LongMovingPiece {
  
  public Queen(ChessColor color) {
    this(color, null);
  }

  public Queen(ChessColor color, Posn position) {
    super(color, position, "Queen");
  }

  List<Capture> getCaptures(Board currentState) {
    List<Capture> captures = new ArrayList<>();
    captures.addAll(this.getDiagonalCaptures(currentState));
    captures.addAll(this.getOrthagonalCaptures(currentState));
    return captures;
  }

  List<Move> getMoves(Board currentState) {
    List<Move> moves = new ArrayList<>();
    moves.addAll(this.getDiagonalMoves(currentState));
    moves.addAll(this.getOrthagonalMoves(currentState));
    return moves;
  }

  Piece movedTo(Posn posn) {
    return new Queen(this.color, posn);
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
    return PieceTables.QUEEN_POSITION_TABLE[this.position.y * 16 + this.position.x];
  }

  String uppercaseLetter() {
    return "Q";
  }

  Piece atPosn(Posn posn) {
    return this.movedTo(posn);
  }
  
  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/doorbell.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
