import java.awt.Color;
import java.util.Map;

import javalib.worldimages.Posn;

public class Utils {
  public static Posn add(Posn a, Posn b) {
    return new Posn(a.x + b.x, a.y + b.y);
  }
  
  public static Posn multiply(Posn p, int m) {
    return new Posn(m * p.x, m * p.y);
  }
  
//  public static Color opposite(ChessColor color) {
//    return Map.ofEntries(Map.entry(Color.BLACK, Color.WHITE), Map.entry(Color.WHITE, Color.BLACK)).get(color);
//  }
  
  public static String squareString(Posn square) {
    return "abcdefgh".charAt(square.x) + Integer.toString(8 - square.y);
  }
  
  public static Posn toSquare(String square) {
    if (square.length() != 2) {
      throw new IllegalArgumentException();
    }
    return new Posn("abcdefgh".indexOf(square.charAt(0)), 8 - Character.getNumericValue(square.charAt(1)));
  }
  
  public static String file(Posn square) {
    return Character.toString("abcdefgh".charAt(square.x));
  }
}
