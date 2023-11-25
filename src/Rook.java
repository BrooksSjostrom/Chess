import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javalib.worldimages.Posn;

public class Rook extends LongMovingPiece {
  boolean hasMoved;
  
  public Rook(ChessColor color) {
    this(color, null);
  }

  public Rook(ChessColor color, Posn position) {
    this(color, position, false);
  }
  
  public Rook(ChessColor color, Posn position, boolean hasMoved) {
    super(color, position, "Rook");
    this.hasMoved = hasMoved;
  }
  
//  public Rook(Piece piece) {
//    this(piece.color, piece.position, piece.hasMoved()); 
//  }

  List<Capture> getCaptures(Board currentState) {
    return this.getOrthagonalCaptures(currentState);
  }

  List<Move> getMoves(Board currentState) {
    return this.getOrthagonalMoves(currentState);
  }

  Piece movedTo(Posn posn) {
    return new Rook(this.color, posn, true);
  }
  
  Piece atPosn(Posn posn) {
    return new Rook(this.color, posn, this.hasMoved);
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
    return true;
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
    return "R";
  }

  public void playSound() {
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/bark.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  PieceType getType() {
    return PieceType.ROOK;
  }
}
