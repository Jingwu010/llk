package control;

/**
 * Created by Jingwu Xu on 2019-05-06
 */
public enum MessageType {
  NONE,
  PUT,
  POST,
  DELETE;

  @Override
  public String toString() {
    switch (this) {
      case PUT:
        return "PUT";
      case POST:
        return "POST";
      case DELETE:
        return "DELETE";
      default:
        return "NONE";
    }
  }

  public static MessageType toType(String type) {
    switch (type) {
      case "PUT":
        return PUT;
      case "POST":
        return POST;
      case "DELETE":
        return DELETE;
      default:
        return NONE;
    }
  }
}
