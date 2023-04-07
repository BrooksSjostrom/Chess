import java.awt.Color;
import java.util.List;

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

  int positionScore() {
    return this.color.equals(ChessColor.WHITE) ? PieceTables.WBISHOP_POSITION_TABLE[this.position.y * 16 + this.position.x] : PieceTables.BBISHOP_POSITION_TABLE[this.position.y * 16 + this.position.x];
  }

  String uppercaseLetter() {
    return "B";
  }

}
