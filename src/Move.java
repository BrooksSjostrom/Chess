import javalib.worldimages.Posn;
import java.util.HashMap;
import java.util.Map;

public class Move implements Action {
  Piece piece;
  Posn from;
  Posn to;
  
  public Move(Piece piece, Posn from, Posn to) {
    this.piece = piece;
    this.from = from;
    this.to = to;
  }
  
  public Move(Piece piece, Posn to) {
    this(piece, piece.position, to);
  }

  public Posn getLocation() {
    return this.to;
  }

  public Board apply(Board currentState) {
    Map<Posn, Piece> pieces = new HashMap<>(currentState.pieces);
    pieces.remove(this.from);
    Piece piece2 = this.isPromotion() ? new Queen(this.piece.color).movedTo(this.to) : piece.movedTo(this.to);
    pieces.put(this.to, piece2);
    return new Board(pieces).removeJustMovedTwo(piece2);
  }

  public String toSAN() {
    return piece.uppercaseLetter() + Utils.squareString(this.to);
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
