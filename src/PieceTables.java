public final class PieceTables {

  public static final int[] BPAWN_POSITION_TABLE =
  { 0,   0,   0,   0,   0,   0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0,
    50, 50,  50,  50,  50,  50, 50, 50,  0, 0, 0, 0, 0, 0, 0, 0,
    10, 10,  20,  30,  30,  20, 10, 10,  0, 0, 0, 0, 0, 0, 0, 0,
    5,   5,  10,  25,  25,  10,  5,  5,  0, 0, 0, 0, 0, 0, 0, 0,
    0,   0,   0,  20,  20,   0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0,
    5,  -5, -10,   0,   0, -10, -5,  5,  0, 0, 0, 0, 0, 0, 0, 0,
    5,  10,  10, -20, -20,  10, 10,  5,  0, 0, 0, 0, 0, 0, 0, 0,
    0,   0,   0,   0,   0,   0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] WPAWN_POSITION_TABLE =
  { 0,   0,   0,   0,   0,   0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0,
    5,  10,  10, -20, -20,  10, 10,  5,  0, 0, 0, 0, 0, 0, 0, 0,
    5,  -5, -10,   0,   0, -10, -5,  5,  0, 0, 0, 0, 0, 0, 0, 0,
    0,   0,   0,  20,  20,   0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0,
    5,   5,  10,  25,  25,  10,  5,  5,  0, 0, 0, 0, 0, 0, 0, 0,
    10, 10,  20,  30,  30,  20, 10,  10, 0, 0, 0, 0, 0, 0, 0, 0,
    50, 50,  50,  50,  50,  50, 50,  50, 0, 0, 0, 0, 0, 0, 0, 0,
    0,   0,   0,   0,   0,   0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] KNIGHT_POSITION_TABLE =
  { -50, -40, -30, -30, -30, -30, -40, -50, 0, 0, 0, 0, 0, 0, 0, 0,
    -40, -20,   0,   0,   0,   0, -20, -40, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,   0,  10,  15,  15,  10,   0, -30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,   5,  15,  20,  20,  15,   5, -30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,   0,  15,  20,  20,  15,   0, -30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,   5,  10,  15,  15,  10,   5, -30, 0, 0, 0, 0, 0, 0, 0, 0,
    -40, -20,   0,   5,   5,   0, -20, -40, 0, 0, 0, 0, 0, 0, 0, 0,
    -50, -40, -30, -30, -30, -30, -40, -50, 0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] BBISHOP_POSITION_TABLE =
  { -20, -10, -10, -10, -10, -10, -10, -20, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   0,   0,   0,   0,   0,   0, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   0,   5,  10,  10,   5,   0, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   5,   5,  10,  10,   5,   5, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   0,  10,  10,  10,  10,   0, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,  10,  10,  10,  10,  10,  10, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   5,   0,   0,   0,   0,   5, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -20, -10, -10, -10, -10, -10, -10, -20, 0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] WBISHOP_POSITION_TABLE =
  { -20, -10, -10, -10, -10, -10, -10, -20, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   5,   0,   0,   0,   0,   5, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,  10,  10,  10,  10,  10, 10,  -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   5,  10,  10,  10,  10,   0, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   5,   5,  10,  10,   5,   5, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   0,   5,  10,  10,   5,   0, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,   0,   0,   0,   0,   0,   0, -10, 0, 0, 0, 0, 0, 0, 0, 0,
    -20, -10, -10, -10, -10, -10, -10, -20, 0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] BROOK_POSITION_TABLE =
  {   0,  0,  0,  0,  0,  0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0,
      5, 10, 10, 10, 10, 10, 10,  5,  0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  0,  0,  0,  0,  0, -5,  0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  0,  0,  0,  0,  0, -5,  0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  0,  0,  0,  0,  0, -5,  0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  0,  0,  0,  0,  0, -5,  0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  0,  0,  0,  0,  0, -5,  0, 0, 0, 0, 0, 0, 0, 0,
      0,  0,  0,  5,  5,  0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] WROOK_POSITION_TABLE =
  {  0,  0,  0,  5,  5,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0, 0,
    -5,  0,  0,  0,  0,  0,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
    -5,  0,  0,  0,  0,  0,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
    -5,  0,  0,  0,  0,  0,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
    -5,  0,  0,  0,  0,  0,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
    -5,  0,  0,  0,  0,  0,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
     5, 10, 10, 10, 10,  10, 10, 5, 0, 0, 0, 0, 0, 0, 0, 0,
     0,  0,  0,  0,  0,  0,  0,  0, 0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] QUEEN_POSITION_TABLE =
  { -20,-10,-10, -5, -5,-10,-10,-20, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,  0,  0,  0,  0,  0,  0,-10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,  0,  5,  5,  5,  5,  0,-10, 0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  5,  5,  5,  5,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
     -5,  0,  5,  5,  5,  5,  0, -5, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,  0,  5,  5,  5,  5,  0,-10, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,  0,  0,  0,  0,  0,  0,-10, 0, 0, 0, 0, 0, 0, 0, 0,
    -20,-10,-10, -5, -5,-10,-10,-20, 0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] OPENING_QUEEN_POSITION_TABLE =
  { -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0,
    -200,-100,-100, -50, -50,-100,-100,-200, 0, 0, 0, 0, 0, 0, 0, 0 };


  public static final int[] BKING_POSITION_TABLE =
  { -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -20,-30,-30,-40,-40,-30,-30,-20, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,-20,-20,-20,-20,-20,-20,-10, 0, 0, 0, 0, 0, 0, 0, 0,
     20, 20,  0,  0,  0,  0, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0,
     20, 30, 10,  0,  0, 10, 30, 20, 0, 0, 0, 0, 0, 0, 0, 0 };

  public static final int[] WKING_POSITION_TABLE =
  {  20, 30, 10,  0,  0, 10, 30, 20, 0, 0, 0, 0, 0, 0, 0, 0,
     20, 20,  0,  0,  0,  0, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0,
    -10,-20,-20,-20,-20,-20,-20,-10, 0, 0, 0, 0, 0, 0, 0, 0,
    -20,-30,-30,-40,-40,-30,-30,-20, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0,
    -30,-40,-40,-50,-50,-40,-40,-30, 0, 0, 0, 0, 0, 0, 0, 0 };

}