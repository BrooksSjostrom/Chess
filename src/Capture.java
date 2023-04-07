import java.util.HashMap;
import java.util.Map;

import javalib.worldimages.Posn;

public class Capture implements Action {
  Piece piece;
  Posn from;
  Posn to;
  Piece captured;
  
  public Capture(Piece piece, Posn from, Posn to, Piece captured) {
    this.piece = piece;
    this.from = from;
    this.to = to;
    this.captured = captured;
  }

  public Posn getLocation() {
    return this.to;
  }

  public Board apply(Board currentState) {
    Map<Posn, Piece> pieces = new HashMap<>(currentState.pieces);
    pieces.remove(this.from);
    pieces.remove(this.to);
    if (this.isPromotion()) {
      pieces.put(this.to, new Queen(this.piece.color).movedTo(this.to));
    } else {
      pieces.put(this.to, piece.movedTo(this.to));
    }
    return new Board(pieces);
  }

  public String toSAN() {
    return this.piece.uppercaseLetter() + (this.piece.isPawn() ? Utils.file(this.piece.getPosition()) : "") + "x" + Utils.squareString(this.to);
  }

  public boolean isPromotion() {
    return this.piece.isPawn() && this.to.y == Board.getBackLineY(this.piece.color.opposite());
  }

  public String toLAN() {
    return Utils.squareString(this.from) + Utils.squareString(this.to) + (this.isPromotion() ? "q" : "");
  }

  public void playSound() {
    this.piece.playSound();
  }
}
