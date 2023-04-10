import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javalib.worldimages.Posn;

public abstract class Piece {
  ChessColor color;
  Posn position;
  String name;
  
  public Piece(ChessColor color, Posn position, String name) {
    this.color = color;
    this.position = position;
    this.name = name;
  }
  
  abstract List<Capture> getCaptures(Board currentState);
  
  abstract List<Move> getMoves(Board currentState);
  
  abstract List<Promotion> getPromotions(Board currentState);
  
  abstract List<Castle> getCastles(Board currentState);
  
  abstract List<EnPassant> getEnPassants(Board currentState);
  
  abstract Piece movedTo(Posn posn);
  
  abstract Piece atPosn(Posn posn);
  
  List<Action> getActions(Board currentState) {
    List<Action> actions = new ArrayList<>();
    actions.addAll(this.getMoves(currentState));
    actions.addAll(this.getCaptures(currentState));
//    actions.addAll(this.getPromotions(currentState));
    actions.addAll(this.getCastles(currentState));
    actions.addAll(this.getEnPassants(currentState));
    return actions.stream()
        .filter(action -> !action.apply(currentState).inCheck(this.color))
        .filter(action -> currentState.inBounds(action.getLocation()))
        .collect(Collectors.toList());
  }
  
  public String filename() {
    return this.color.toLowercaseString() + this.name + ".png";
  }
  
  public Posn getPosition() {
    return this.position;
  }

  abstract boolean isKing();
  
  abstract boolean isRook();
  
  abstract boolean isPawn();
  
  abstract boolean hasMoved();
  
  abstract boolean justMovedTwo();
  
  abstract int positionScore();
  
  abstract String uppercaseLetter();

  public void playSound() {
    
  }
  
  abstract PieceType getType();
}