import java.awt.Color;

import java.awt.PopupMenu;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.ButtonGroup;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javalib.impworld.*;
import javalib.worldimages.*;

public class ChessGame extends World implements ActionListener, ItemListener {
  Player black;
  Player white;
  Board board;
  
  boolean whiteTurn;
  Optional<Piece> selectedPiece;
  List<Action> possibleActions;
  
  List<ActionPair> gameLog;
  
  JMenuItem humanVsHuman, humanVsAI, AIVsAI, increaseSize, decreaseSize;
  JRadioButtonMenuItem pieces1, pieces2, pieces3, pieces4;
  String pieceFolder = "basic";
  int tileSize = 50;
  
  
  public ChessGame() {
//    this(new RandumbAI("Black", Color.BLACK), new Human("White", Color.WHITE));
//    this(new Human("Black", Color.BLACK), new Human("White", Color.WHITE));
//    this(new GNUChessAI("Black", ChessColor.BLACK, 1000), new Human("White", ChessColor.WHITE));
    this(new AlphaBetaPruningAI("Black", ChessColor.BLACK), new Human("White", ChessColor.WHITE));
//    this(new GNUChessAI("Black", Color.BLACK, 100), new GNUChessAI("White", Color.WHITE, 100));
  }

  public ChessGame(Player black, Player white) {
    this(black, white, Board.start());
  }

  public ChessGame(Player black, Player white, Board board) {
    this.black = black;
    this.white = white;
    this.board = board;
    this.whiteTurn = true;
    this.selectedPiece = Optional.empty();
    this.possibleActions = new ArrayList<>();
    this.gameLog = new ArrayList<>();
  }
  
  public WorldScene makeScene() {
    WorldScene result = this.getEmptyScene();
    result = this.board.generateScene(result, this.pieceFolder, this.tileSize);
    if (this.selectedPiece.isPresent()) {
      Posn posn = this.selectedPiece.get().position;
      result.placeImageXY(new CircleImage(this.tileSize / 3, OutlineMode.OUTLINE, Color.GREEN), this.tileSize * posn.x + this.tileSize / 2, this.tileSize * posn.y + this.tileSize / 2);
    }
    for (Action action : this.possibleActions) {
      Posn posn = action.getLocation();
      result.placeImageXY(new CircleImage(this.tileSize / 3, OutlineMode.OUTLINE, new Color(138, 43, 226)), this.tileSize * posn.x + this.tileSize / 2, this.tileSize * posn.y + this.tileSize / 2);
    }
    return result;
  }
  
  public void onMouseClicked(Posn location) {
    Posn square = new Posn((int) Math.floor(location.x / this.tileSize), (int) Math.floor(location.y / this.tileSize));
    Player player = this.whiteTurn ? this.white : this.black;
    List<Posn> actionPosns = this.possibleActions.stream()
        .map(Action::getLocation)
        .collect(Collectors.toList());
    if (actionPosns.contains(square)) {
      Action action = this.possibleActions.stream()
          .filter(x -> x.getLocation().equals(square))
          .findAny()
          .orElseThrow();
      this.doAction(action);
    } else if (!(this.whiteTurn ? this.white : this.black).canMoveAutomatically() && this.board.containsPiece(square, this.whiteTurn ? ChessColor.WHITE : ChessColor.BLACK)) {
      this.selectedPiece = Optional.of(this.board.pieces.get(square));
      this.possibleActions = this.selectedPiece.get().getActions(board);
    } else {
      this.selectedPiece = Optional.empty();
      this.possibleActions = new ArrayList<>();
    }
  }
  
  public void onTick() {
    Player player = this.whiteTurn ? white : black;
    if (player.canMoveAutomatically()) {
      Action action = player.getAutomaticAction(this.board);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.doAction(action);
    }
  }
  
  public void doAction(Action action) {
    this.board = action.apply(this.board);
    action.playSound();
    if (this.whiteTurn) {
      this.gameLog.add(new ActionPair(action, new WuWei(), this.gameLog.size() + 1));
    } else {
      this.gameLog.get(this.gameLog.size() - 1).black = action;
    }
    this.whiteTurn = !this.whiteTurn;
    this.selectedPiece = Optional.empty();
    this.possibleActions = new ArrayList<>();
    Player player = this.whiteTurn ? this.white : this.black;
    if (this.board.outOfActions(player.getColor())) {
      if (this.board.inCheck(player.getColor())) {
        this.endOfWorld(String.format("Checkmate %s wins", this.whiteTurn ? "black" : "white"));
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/sounds/wompwompwomp.wav"));
          clip.open(inputStream);
          clip.start();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        this.endOfWorld("Stalemate");
      }
    }
  }
  
  public WorldScene lastScene(String msg) {
    WorldScene result = this.makeScene();
    TextImage text = new TextImage(msg, this.tileSize / 2, Color.BLUE);
    result.placeImageXY(text, this.tileSize * 4, this.tileSize * 4);
    return result;
  }
  
  public void onKeyEvent(String s) {
    if (s.equals("h")) {
      this.board.pieces.entrySet().stream()
          .filter(entry -> entry.getValue().color.equals(this.whiteTurn ? Color.white : Color.black))
          .flatMap(entry -> entry.getValue().getActions(this.board).stream())
          .map(Action::toSAN)
          .forEach(System.out::println);
    }
  }
  
  public String toPGN() {
    return this.gameLog.stream()
        .map(ActionPair::toPGN)
        .collect(Collectors.joining("\n"));
  }
  
  public String toEPD() {
    return String.format("%s %s %s %s id %s;",
        this.board.getFENPiecePositions(),
        this.whiteTurn ? "w" : "b",
        "KQkq",
        "-",
        String.format("\"Game %d\"", new Random().nextInt(1000))
    );
  }
  
  public String toUCIPosition() {
    return "position startpos moves " + this.gameLog.stream()
      .map(ActionPair::toLAN)
      .collect(Collectors.joining(" "));
  }
  
  public WorldImage gameLogImage() {
    return this.gameLog.stream()
        .map(ActionPair::toPGN)
        .map(text -> (WorldImage) new TextImage(text, this.tileSize / 4, Color.BLACK))
        .reduce(new EmptyImage(), (a, b) -> new AboveAlignImage("left", a, b));
  }
  
  public void launchGame() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("apple.awt.application.name", "Chess");
    System.setProperty("apple.awt.application.appearance", "system");
    this.bigBang(this.tileSize * 8, this.tileSize * 8, 0.01);
    this.theCanvas.frame.setTitle("Chess");
    ImageIcon icon = new ImageIcon("src/pieces/basic/whitePawn.png");
    Taskbar taskbar = Taskbar.getTaskbar();
    taskbar.setIconImage(icon.getImage());
    JMenuBar menubar = new JMenuBar();
    JMenu gameMenu = new JMenu("New Game");
    this.humanVsHuman = new JMenuItem("Human vs. Human");
    this.humanVsAI = new JMenuItem("Human vs. AI");
    this.AIVsAI = new JMenuItem("AI vs. AI");
    this.humanVsHuman.addActionListener(this);
    this.humanVsAI.addActionListener(this);
    this.AIVsAI.addActionListener(this);
    gameMenu.add(humanVsHuman);
    gameMenu.add(humanVsAI);
    gameMenu.add(AIVsAI);
    menubar.add(gameMenu);
    JMenu viewMenu = new JMenu("View");
    JMenu pieceMenu = new JMenu("Pieces");
    ButtonGroup pieceGroup = new ButtonGroup();
    this.pieces1 = new JRadioButtonMenuItem("Basic", true);
    pieces1.addActionListener(this);
    this.pieces2 = new JRadioButtonMenuItem("Caliente");
    pieces2.addActionListener(this);
    this.pieces3 = new JRadioButtonMenuItem("Chessnut");
    pieces3.addActionListener(this);
    this.pieces4 = new JRadioButtonMenuItem("Fantasy");
    pieces4.addActionListener(this);
    pieceGroup.add(pieces1);
    pieceGroup.add(pieces2);
    pieceGroup.add(pieces3);
    pieceGroup.add(pieces4);
    pieceMenu.add(pieces1);
    pieceMenu.add(pieces2);
    pieceMenu.add(pieces3);
    pieceMenu.add(pieces4);
    viewMenu.add(pieceMenu);
    this.increaseSize = new JMenuItem("Increase size");
    this.increaseSize.addActionListener(this);
    this.increaseSize.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_EQUALS, InputEvent.META_DOWN_MASK));
    this.decreaseSize = new JMenuItem("Decrease size");
    this.decreaseSize.addActionListener(this);
    this.decreaseSize.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_MINUS, InputEvent.META_DOWN_MASK));
    viewMenu.addSeparator();
    viewMenu.add(increaseSize);
    viewMenu.add(decreaseSize);
    menubar.add(viewMenu);
    this.theCanvas.frame.setJMenuBar(menubar);
  }
  
  public void itemStateChanged(ItemEvent e) {
    System.out.println("item state change");
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == humanVsHuman) {
      new ChessGame(new Human("Black", ChessColor.BLACK), new Human("White", ChessColor.WHITE)).launchGame();
    } else if (e.getSource() == humanVsAI){
      new ChessGame(new GNUChessAI("Black", ChessColor.BLACK, 1000), new Human("White", ChessColor.WHITE)).launchGame();
    } else if (e.getSource() == AIVsAI) {
      new ChessGame(new GNUChessAI("Black", ChessColor.BLACK, 100), new GNUChessAI("White", ChessColor.WHITE, 100)).launchGame();
    } else if (e.getSource() == pieces1) {
      this.pieceFolder = "basic";
    } else if (e.getSource() == pieces2) {
      this.pieceFolder = "caliente";
    } else if (e.getSource() == pieces3) {
      this.pieceFolder = "chessnut";
    } else if (e.getSource() == pieces4) {
      this.pieceFolder = "fantasy";
    } else if (e.getSource() == increaseSize) {
      this.tileSize += 10;
      this.resize(this.tileSize * 8, this.tileSize * 8);
    } else if (e.getSource() == decreaseSize) {
      this.tileSize -= 10;
      this.resize(this.tileSize * 8, this.tileSize * 8);
    } else {
      System.out.println("unknown");
    }
  }
}
