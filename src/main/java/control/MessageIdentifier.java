package control;

/**
 * Created by Jingwu Xu on 2019-05-07
 */
public enum MessageIdentifier {
  NONE,
  // update/delete a block at view
  BLOCK,
  // put a new path to display at view
  PATH,
  // update timer
  TIMER;

  @Override
  public String toString() {
    switch (this) {
      case BLOCK:
        return "BLOCK";
      case PATH:
        return "PATH";
      case TIMER:
        return "TIMER";
      default:
        return "NONE";
    }
  }

  public static MessageIdentifier toIdentifier(String id) {
    switch (id) {
      case "BLOCK":
        return BLOCK;
      case "PATH":
        return PATH;
        case "TIMER":
        return TIMER;
      default:
        return NONE;
    }
  }
}
