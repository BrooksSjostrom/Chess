
public class ActionPair {
  Action white;
  Action black;
  int index;
  
  
  public ActionPair(Action white, Action black, int index) {
    this.white = white;
    this.black = black;
    this.index = index;
  }
  
  public String toPGN() {
    return String.format("%d. %s %s", this.index, this.white.toSAN(), this.black.toSAN());
  }
  
  public String toLAN() {
    return this.white.toLAN() + " " + this.black.toLAN();
  }
}
