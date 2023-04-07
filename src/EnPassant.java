import java.util.HashMap;
import java.util.Map;

import javalib.worldimages.Posn;

public class EnPassant implements Action {
  Piece piece;
  Posn from;
  Posn to;
  Piece captured;
  Posn capturedPosn;

  public EnPassant(Pawn piece, Pawn captured) {
    this(piece, piece.position, new Posn(captured.position.x, captured.position.y + piece.direction().y), captured, captured.position);
  }
  
  public EnPassant(Piece piece, Posn from, Posn to, Piece captured, Posn capturedPosn) {
    this.piece = piece;
    this.from = from;
    this.to = to;
    this.captured = captured;
    this.capturedPosn = capturedPosn;
  }

  public Posn getLocation() {
    return this.to;
  }

  public Board apply(Board currentState) {
    Map<Posn, Piece> pieces = new HashMap<>(currentState.pieces);
    pieces.remove(this.from);
    pieces.remove(this.captured.position);
    pieces.put(this.to, this.piece.movedTo(this.to));
    return new Board(pieces);
  }

  public String toSAN() {
    return Utils.file(this.from) + "x" + Utils.squareString(this.to);
  }

  public boolean isPromotion() {
    return false;
  }

  public String toLAN() {
    return Utils.squareString(this.from) + Utils.squareString(this.to);
  }

  public void playSound() {
    
  }

}
