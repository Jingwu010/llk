package view.events;

public enum EventType {
  NONE,
  TIMEOUT,
  PAUSE;

  @Override
  public String toString() {
    switch (this) {
      case PAUSE:
        return "PAUSE";
      case TIMEOUT:
        return "TIMEOUT";
      default:
        return "NONE";
    }
  }

  public static EventType toType(String type) {
    switch (type) {
      case "PAUSE":
        return PAUSE;
      case "TIMEOUT":
        return TIMEOUT;
      default:
        return NONE;
    }
  }
}
