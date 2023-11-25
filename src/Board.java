import java.awt.Color;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.ArrayList;

import javalib.impworld.WorldScene;
import javalib.worldimages.*;


public class Board {
  Map<Posn, Piece> pieces;
  
  WorldImage boardImage;
  int lastTileSize;
  
  public Board(Map<Posn, Piece> pieces) {
    this.pieces = pieces;
  }
  
  static Board start() {
    return BoardBuilder.start()
        .build();
  }
  
  public WorldScene generateScene(WorldScene scene, String pieceFolder, int tileSize) {
    if (this.boardImage != null && this.lastTileSize == tileSize) {
      scene.placeImageXY(boardImage, tileSize * 4, tileSize * 4);
    } else {
      this.boardImage = new EmptyImage();
      for (int i = 0; i < 8; i++) {
        WorldImage row = new EmptyImage();
        for (int j = 0; j < 8; j++) {
          WorldImage square = new RectangleImage(tileSize, tileSize, OutlineMode.SOLID, 
              (i + j) % 2 == 0 ? Color.WHITE : new Color(70, 70, 70));
          row = new BesideImage(row, square);
        }
        this.boardImage = new AboveImage(this.boardImage, row);
      }
      this.lastTileSize = tileSize;
      scene.placeImageXY(this.boardImage, tileSize * 4, tileSize * 4);
    }
    
    pieces.entrySet().stream()
      .forEach(entry -> {
        Piece piece = entry.getValue();
        WorldImage img = new FromFileImage("src/pieces/" + pieceFolder + "/" + piece.filename());
        img = new ScaleImage(img, tileSize / img.getWidth());
        scene.placeImageXY(img, tileSize * piece.position.x + tileSize / 2, 
            tileSize * piece.position.y + tileSize / 2);
      });
    return scene;
  }
  
  public boolean containsPiece(Posn posn) {
    return this.pieces.containsKey(posn);
  }

  public boolean containsPiece(Posn posn, ChessColor color) {
    return this.pieces.containsKey(posn) && this.pieces.get(posn).color.equals(color);
  }
  
  public boolean inBounds(Posn posn) {
    return posn.x >= 0 && posn.x <= 7 && posn.y >= 0 && posn.y <= 7;
  }
  
  public Piece pieceAt(Posn posn) {
    return this.pieces.get(posn);
  }
  
  public List<Piece> getPiecesOfColor(ChessColor color) {
    return this.pieces.values().stream()
        .filter(piece -> piece.color.equals(color))
        .collect(Collectors.toList());
  }
  
  public boolean inCheck(ChessColor color) {
    return this.getPiecesOfColor(color.opposite()).stream()
        .flatMap(piece -> piece.getCaptures(this).stream())
        .anyMatch(capture -> capture.captured.isKing());
  }
  
  public boolean inCheckmate(ChessColor color) {
    return this.inCheck(color) && this.outOfActions(color);
  }
  
  public boolean outOfActions(ChessColor color) {
    List<Action> allActions = this.pieces.entrySet().stream()
        .filter(entry -> entry.getValue().color.equals(color))
        .flatMap(entry -> entry.getValue().getActions(this).stream())
        .collect(Collectors.toList());
    return allActions.isEmpty();
  }
  
  public boolean underThreat(Posn posn, ChessColor color) {
    return this.pieces.entrySet().stream()
        .filter(entry -> entry.getValue().color.equals(color.opposite()))
        .flatMap(entry -> entry.getValue().getCaptures(this).stream())
        .anyMatch(capture -> capture.to.equals(posn));
  }
  
  public static int getBackLineY(ChessColor color) {
    return color.equals(ChessColor.BLACK) ? 0 : 7;
  }
  
  public Board removeJustMovedTwo(Piece except) {
    Map<Posn, Piece> pieces = new HashMap<>(this.pieces);
    pieces = pieces.entrySet().stream()
        .map(Map.Entry::getValue)
        .map(piece -> {
          if (piece.isPawn() && !piece.equals(except)) {
            return new Pawn(piece.color, piece.position, false);
          } else {
            return piece;
          }
        })
        .collect(Collectors.toMap(Piece::getPosition, Function.identity()));
    return new Board(pieces);
  }
  
  static class BoardBuilder {
    Map<Posn, Piece> pieces;
    
    public BoardBuilder() {
      this.pieces = new HashMap<>();
    }
    
    public static BoardBuilder start() {
      return new BoardBuilder()
          .addRowOfPieces(1, new Pawn(ChessColor.BLACK))
          .addRowOfPieces(6, new Pawn(ChessColor.WHITE))
          .addPiece(new Posn(0, 0), new Rook(ChessColor.BLACK))
          .addPiece(new Posn(7, 0), new Rook(ChessColor.BLACK))
          .addPiece(new Posn(1, 0), new Knight(ChessColor.BLACK))
          .addPiece(new Posn(6, 0), new Knight(ChessColor.BLACK))
          .addPiece(new Posn(2, 0), new Bishop(ChessColor.BLACK))
          .addPiece(new Posn(5, 0), new Bishop(ChessColor.BLACK))
          .addPiece(new Posn(3, 0), new Queen(ChessColor.BLACK))
          .addPiece(new Posn(4, 0), new King(ChessColor.BLACK))
          .addPiece(new Posn(0, 7), new Rook(ChessColor.WHITE))
          .addPiece(new Posn(7, 7), new Rook(ChessColor.WHITE))
          .addPiece(new Posn(1, 7), new Knight(ChessColor.WHITE))
          .addPiece(new Posn(6, 7), new Knight(ChessColor.WHITE))
          .addPiece(new Posn(2, 7), new Bishop(ChessColor.WHITE))
          .addPiece(new Posn(5, 7), new Bishop(ChessColor.WHITE))
          .addPiece(new Posn(3, 7), new Queen(ChessColor.WHITE))
          .addPiece(new Posn(4, 7), new King(ChessColor.WHITE));
    }
    
    public BoardBuilder addPiece(Posn posn, Piece piece) {
      this.pieces.put(posn, piece.atPosn(posn));
      return this;
    }
    
    public BoardBuilder addRowOfPieces(int y, Piece piece) {
      for (int i = 0; i < 8; i++) {
        Posn posn = new Posn(i, y);
        Piece piece2 = piece.movedTo(posn);
        this.pieces.put(posn, piece2);
      }
      return this;
    }
    
    public BoardBuilder removePiece(Posn posn) {
      this.pieces.remove(posn);
      return this;
    }
    
    public BoardBuilder removePiece(Piece piece) {
      this.pieces.remove(piece.position, piece);
      return this;
    }
    
    public Board build() {
      return new Board(this.pieces);
    }
  }
  
  public String getFENPiecePositions() {
    Map<Integer, List<Piece>> rows =  this.pieces.values().stream()
        .collect(Collectors.groupingBy(piece -> piece.getPosition().y))
        .entrySet().stream()
        .sorted(Comparator.comparing(Map.Entry::getKey))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    List<String> rowStrings = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      List<Piece> row = rows.getOrDefault(i, List.of());
      Map<Integer, Piece> row2 = row.stream()
          .collect(Collectors.groupingBy(piece -> (Integer) piece.getPosition().x))
          .entrySet().stream()
          .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
      String result = "";
      int spaceLength = 0;
      for (int j = 0; j < 8; j++) {
        Optional<Piece> piece = Optional.ofNullable(row2.get(j));
        if (piece.isPresent()) {
          Piece p = piece.get();
          if (spaceLength > 0) {
            result += Integer.toString(spaceLength);
          }
          String uppercaseLetter = p.isPawn() ? "P" : p.uppercaseLetter();
          result += p.color.equals(ChessColor.WHITE) ? uppercaseLetter : uppercaseLetter.toLowerCase();
          spaceLength = 0;
        } else {
          spaceLength += 1;
        }
      }
      if (spaceLength > 0) {
        result += Integer.toString(spaceLength);
      }
      rowStrings.add(result);
    }
    return rowStrings.stream()
        .collect(Collectors.joining("/"));
  }
  
  public String getFENCastlingAbility() {
    String result = "";
    Optional<Piece> rook1 = Optional.ofNullable(this.pieceAt(new Posn(0, 0)));
    Optional<Piece> rook2 = Optional.ofNullable(this.pieceAt(new Posn(7, 0)));
    Optional<Piece> rook3 = Optional.ofNullable(this.pieceAt(new Posn(0, 7)));
    Optional<Piece> rook4 = Optional.ofNullable(this.pieceAt(new Posn(7, 7)));
    Optional<Piece> blackKing = Optional.ofNullable(this.pieceAt(new Posn(4, 0)));
    Optional<Piece> whiteKing = Optional.ofNullable(this.pieceAt(new Posn(4, 7)));
    if (whiteKing.isPresent() && whiteKing.get().isKing() && !whiteKing.get().hasMoved()) {
      if (rook4.isPresent() && rook4.get().isRook() && !rook4.get().hasMoved()) {
        result += "K";
      }
      if (rook3.isPresent() && rook3.get().isRook() && !rook3.get().hasMoved()) {
        result += "Q";
      }
    }
    if (blackKing.isPresent() && blackKing.get().isKing() && !blackKing.get().hasMoved()) {
      if (rook2.isPresent() && rook2.get().isRook() && !rook2.get().hasMoved()) {
        result += "k";
      }
      if (rook1.isPresent() && rook1.get().isRook() && !rook1.get().hasMoved()) {
        result += "q";
      }
    }
    return result;
  }
  
  public Action toAction(String text) {
    if (text.length() == 4 || text.length() == 5) {
      if (text.equals("a1a1")) {
        return new WuWei();
      }
      Posn from = Utils.toSquare(text.substring(0, 2));
      Posn to = Utils.toSquare(text.substring(2, 4));
      Piece piece = this.pieceAt(from);
      Optional<Piece> captured = Optional.ofNullable(this.pieceAt(to));
      if (captured.isEmpty()) {
        return new Move(piece, to);
      } else {
        return new Capture(piece, from, to, captured.get());
      }
    } else {
      throw new IllegalArgumentException("Invalid movetext");
    }
  }
}
