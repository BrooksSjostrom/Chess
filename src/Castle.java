import javalib.worldimages.Posn;
import java.util.Map;
import java.util.HashMap;

public class Castle implements Action {
  Piece king;
  Piece rook;
  Posn kingFrom;
  Posn rookFrom;
  Posn kingTo;
  Posn rookTo;
  boolean queenside;
  
  public Castle(Piece king, Piece rook) {
    this.king = king;
    this.rook = rook;
    this.kingFrom = this.king.position;
    this.rookFrom = this.rook.position;
    this.queenside = this.rookFrom.x < this.kingFrom.x;
    if (this.queenside) {
      this.kingTo = new Posn(this.kingFrom.x - 2, this.kingFrom.y);
      this.rookTo = new Posn(this.kingTo.x + 1, this.kingTo.y);
    } else {
      this.kingTo = new Posn(this.kingFrom.x + 2, this.kingFrom.y);
      this.rookTo = new Posn(this.kingTo.x - 1, this.kingTo.y);
    }
  }

  public Castle(Piece king, Piece rook, Posn kingFrom, Posn rookFrom, Posn kingTo, Posn rookTo) {
    this.king = king;
    this.rook = rook;
    this.kingFrom = kingFrom;
    this.rookFrom = rookFrom;
    this.kingTo = kingTo;
    this.rookTo = rookTo;
  }

  public Posn getLocation() {
    return this.kingTo;
  }

  public Board apply(Board currentState) {
    Map<Posn, Piece> pieces = new HashMap<>(currentState.pieces);
    pieces.remove(this.kingFrom);
    pieces.remove(this.rookFrom);
    pieces.put(this.kingTo, this.king.movedTo(this.kingTo));
    pieces.put(this.rookTo, this.rook.movedTo(this.rookTo));
    return new Board(pieces);
  }

  public String toSAN() {
    return this.queenside ? "O-O-O" : "O-O";
  }

  public boolean isPromotion() {
    return false;
  }

  public String toLAN() {
    return Utils.squareString(this.kingFrom) + Utils.squareString(this.kingTo);
  }

  public void playSound() {
    
  }

}
