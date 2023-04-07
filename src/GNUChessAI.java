import java.awt.Color;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;

public class GNUChessAI implements Player {
  String name;
  ChessColor color;
  int moveTime;
  
  private Process process;
  private BufferedReader reader;
  private OutputStreamWriter writer;
  
  public GNUChessAI(String name, ChessColor color, int moveTime) {
    this.name = name;
    this.color = color;
    this.moveTime = moveTime;
    var pb = new ProcessBuilder("/opt/homebrew/bin/gnuchess", "-u");
    try {
        this.process = pb.start();
        this.reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        this.writer = new OutputStreamWriter(process.getOutputStream());
        try {
          this.command("isready", Function.identity(), msg -> msg.equals("readyok"), 3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        } catch (TimeoutException e) {
          e.printStackTrace();
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getName() {
    return this.name;
  }

  public ChessColor getColor() {
    return this.color;
  }

  public boolean canMoveAutomatically() {
    return true;
  }
  
  public <T> T command(String cmd, Function<List<String>, T> commandProcessor, Predicate<String> breakCondition, long timeout)
      throws InterruptedException, ExecutionException, TimeoutException {

  // This completable future will send a command to the process
  // And gather all the output of the engine in the List<String>
  // At the end, the List<String> is translated to T through the
  // commandProcessor Function
  CompletableFuture<T> command = CompletableFuture.supplyAsync(() -> {
      final List<String> output = new ArrayList<>();
      try {
          writer.flush();
          writer.write(cmd + "\n");
          writer.write("isready\n");
          writer.flush();
          String line = "";
          while (true) {
            line = reader.readLine();
            if (line == null) {
              continue;
            }
              if (line.contains("Unknown command")) {
                  throw new RuntimeException(line);
              }
              if (line.contains("Unexpected token")) {
                  throw new RuntimeException("Unexpected token: " + line);
              }
              output.add(line);
              if (breakCondition.test(line)) {
                  break;
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      return commandProcessor.apply(output);
  });
  return command.get(timeout, TimeUnit.MILLISECONDS);
}


  public Action getAutomaticAction(Board currentState) {
    String fenPositions = currentState.getFENPiecePositions();
    String positionCommand = String.format("position fen %s %s %s %s 0 1", 
        fenPositions,
        this.color.equals(ChessColor.WHITE) ? "w" : "b",
        currentState.getFENCastlingAbility(),
        "-");
    try {
      this.command("uci", Function.identity(), (s) -> s.startsWith("uciok"), 2000l);
      this.command(positionCommand, Function.identity(), s -> s.startsWith("readyok"), 3000);
      String bestAction = this.command("go movetime " + this.moveTime, 
          lines -> lines.stream().filter(s->s.startsWith("bestmove")).findFirst().get(),
          line -> line.startsWith("bestmove"),
          5000)
          .split(" ")[1];
      return currentState.toAction(bestAction);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("no best action found");
  }
}
