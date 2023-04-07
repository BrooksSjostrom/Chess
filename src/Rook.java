import java.awt.Color;
import java.util.List;

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

  int positionScore() {
    return this.color.equals(Color.WHITE) ? PieceTables.WROOK_POSITION_TABLE[this.position.y * 16 + this.position.x] : PieceTables.BROOK_POSITION_TABLE[this.position.y * 16 + this.position.x];
  }

  String uppercaseLetter() {
    return "R";
  }

}
