package control;

import org.json.JSONObject;

import java.util.Observable;

/**
 * Created by Jingwu Xu on 2019-05-06
 */
public class MessageBus extends Observable{
  public static final String HEADER = "Message";
  private MessageType mtype;
  private boolean suspended;

  public void setMessageType(MessageType etype){
    this.mtype = etype;
    this.suspended = true;
  }

  public void sendMessage(String message) {
    JSONObject jsonObj = new JSONObject(message);
    addHeader(jsonObj);
    setChanged();
    notifyObservers(jsonObj.toString());
  }

  private JSONObject addHeader(JSONObject jsonObj) {
    jsonObj.put("HEADER", HEADER);
    jsonObj.put("TYPE", mtype);
    return jsonObj;
  }
}
