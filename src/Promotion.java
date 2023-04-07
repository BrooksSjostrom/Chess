import javalib.worldimages.Posn;
import java.util.Map;
import java.util.HashMap;

public class Promotion implements Action {
  Piece piece;
  Posn from;
  Posn to;
  Piece promotedTo;
  
  public Promotion(Piece piece, Posn from, Posn to, Piece promotedTo) {
    this.piece = piece;
    this.from = from;
    this.to = to;
    this.promotedTo = promotedTo;
  }

  public Posn getLocation() {
    return this.to;
  }

  public Board apply(Board currentState) {
    Map<Posn, Piece> pieces = new HashMap<>(currentState.pieces);
    pieces.remove(this.from);
    pieces.remove(this.to);
    pieces.put(this.to, promotedTo);
    return new Board(pieces);
  }

  public String toSAN() {
    throw new UnsupportedOperationException("Promotion class doesn't exist");
//    return this.piece.uppercaseLetter() + Utils.squareString(this.to) + "=" + this.promotedTo.uppercaseLetter();
  }

  public boolean isPromotion() {
    return true;
  }

  public String toLAN() {
    throw new UnsupportedOperationException("Promotion class doesn't exist");
  }

  public void playSound() {
  }

}
