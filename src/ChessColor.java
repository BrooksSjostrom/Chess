import java.awt.Color;

public enum ChessColor {
  BLACK, WHITE;
  
  Color getColor() {
    switch (this) {
    case BLACK:
      return Color.BLACK;
    case WHITE:
      return Color.WHITE;
    default:
      throw new RuntimeException();
    }
  }
  
  ChessColor opposite() {
    switch(this) {
    case BLACK:
      return WHITE;
    case WHITE:
      return BLACK;
    default:
      throw new RuntimeException();
    }
  }
  
  String toLowercaseString() {
    switch(this) {
    case BLACK:
      return "black";
    case WHITE:
      return "white";
    default:
      throw new RuntimeException();
    }
  }
}
