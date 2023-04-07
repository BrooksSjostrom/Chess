import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javalib.worldimages.Posn;
import tester.Tester;

class ExamplesChess {
  void testGame(Tester t) {
    new ChessGame().launchGame();
  }
  
  void testPawnCaptures(Tester t) {
    Board board1 = Board.BoardBuilder.start()
        .removePiece(new Posn(4, 1))
        .removePiece(new Posn(5, 1))
        .removePiece(new Posn(5, 6))
        .addPiece(new Posn(4, 2), new Pawn(ChessColor.BLACK))
        .addPiece(new Posn(5, 2), new Pawn(ChessColor.BLACK))
        .addPiece(new Posn(5, 3), new Pawn(ChessColor.WHITE))
        .build();
    Piece blackPawn1 = board1.pieceAt(new Posn(4, 2));
    Piece blackPawn2 = board1.pieceAt(new Posn(5, 2));
    Piece whitePawn1 = board1.pieceAt(new Posn(5, 3));
    t.checkExpect(blackPawn1.getCaptures(board1), List.of(new Capture(blackPawn1, new Posn(4, 2),new Posn(5, 3), whitePawn1)));
    t.checkExpect(blackPawn2.getCaptures(board1), List.of());
    t.checkExpect(whitePawn1.getCaptures(board1), List.of(new Capture(whitePawn1, new Posn(5, 3), new Posn(4, 2), blackPawn1)));
  }
  
  void testPawnMoves(Tester t) {
    Board board1 = Board.BoardBuilder.start()
        .removePiece(new Posn(4, 6))
        .removePiece(new Posn(3, 1))
        .removePiece(new Posn(7, 1))
        .addPiece(new Posn(3, 2), new Pawn(ChessColor.BLACK))
        .addPiece(new Posn(4, 4), new Pawn(ChessColor.WHITE))
        .addPiece(new Posn(7, 5), new Pawn(ChessColor.BLACK))
        .build();
    Piece blackPawn1 = board1.pieceAt(new Posn(3, 2));
    Piece whitePawn1 = board1.pieceAt(new Posn(4, 4));
    Piece whitePawn2 = board1.pieceAt(new Posn(7, 6));
    t.checkExpect(blackPawn1.getMoves(board1), List.of(new Move(blackPawn1, new Posn(3, 3))));
    t.checkExpect(whitePawn1.getMoves(board1), List.of(new Move(whitePawn1, new Posn(4, 3))));
    t.checkExpect(whitePawn2.getMoves(board1), List.of());
    Board board2 = Board.BoardBuilder.start()
        .build();
    Piece whitePawn3 = board2.pieceAt(new Posn(4, 6));
    t.checkExpect(whitePawn3.getMoves(board2),
        List.of(new Move(whitePawn3, new Posn(4, 5)), new Move(whitePawn3, new Posn(4, 4))));
  }
  
  void testKingMoves(Tester t) {
    Map<Posn, Piece> pieces = new HashMap<>();
    Piece king1 = new King(ChessColor.BLACK, new Posn(4, 1));
    pieces.put(new Posn(4, 1), king1);
    Board board = new Board(pieces);
    t.checkExpect(king1.getMoves(board), List.of(
        new Move(king1, new Posn(5, 1)), 
        new Move(king1, new Posn(5, 0)),
        new Move(king1, new Posn(4, 0)),
        new Move(king1, new Posn(3, 0)),
        new Move(king1, new Posn(3, 1)),
        new Move(king1, new Posn(3, 2)),
        new Move(king1, new Posn(4, 2)),
        new Move(king1, new Posn(5, 2))
    ));
  }
  
  void testInBounds(Tester t) {
    Board board = Board.start();
    t.checkExpect(board.inBounds(new Posn(4, 7)), true);
    t.checkExpect(board.inBounds(new Posn(3, 7)), true);
  }
  
  void testAdd(Tester t) {
    t.checkExpect(Utils.add(new Posn(3, 7), new Posn(1, 0)), new Posn(4, 7));
  }
  
  void testIterate(Tester t) {
    Board board = Board.start();
//    Stream.iterate(new Posn(3, 7), board::inBounds, x -> Utils.add(x, new Posn(1, 0)))
//    .peek(System.out::println);
    t.checkExpect(Stream.iterate(new Posn(3, 7), board::inBounds, x -> Utils.add(x, new Posn(1, 0)))
          .collect(Collectors.toList()), 
        List.of(new Posn(3, 7), new Posn(4, 7), new Posn(5, 7), new Posn(6, 7), new Posn(7, 7)));
//    Stream.iterate(new Posn(3, 7), board::inBounds, x -> Utils.add(x, new Posn(1, 0)))
//      .peek(System.out::println);
  }
  
  void testGetMovesInDirection(Tester t) {
    Map<Posn, Piece> pieces = new HashMap<>();
    LongMovingPiece queen1 = new Queen(ChessColor.BLACK, new Posn(4, 1));
    pieces.put(new Posn(4, 1), queen1);
    Board board = new Board(pieces);
    t.checkExpect(queen1.getMovesInDirection(board, new Posn(1, 0)), 
        List.of(new Move(queen1, new Posn(5, 1)), new Move(queen1, new Posn(6, 1)), new Move(queen1, new Posn(7, 1))));
  }
  
  void testCastle(Tester t) {
    Board board = new Board.BoardBuilder()
        .addPiece(new Posn(0, 7), new Rook(ChessColor.WHITE))
        .addPiece(new Posn(4, 7), new King(ChessColor.WHITE))
        .build();
    Piece king = board.pieceAt(new Posn(4, 7));
    Piece rook = board.pieceAt(new Posn(0, 7));
    t.checkExpect(king.getCastles(board), List.of(new Castle(king, rook)));
  }
}
