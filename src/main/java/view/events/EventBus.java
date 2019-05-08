package view.events;

import org.json.JSONObject;

import java.util.Observable;

/**
 * Created by Jingwu Xu on 2019-05-06
 */
public class EventBus extends Observable {
  public static final String HEADER = "EVENT";
  private EventType etype;

  public void setEventType(EventType etype){
    this.etype = etype;
  }

  public void fireEvent(String message) {
    JSONObject jsonObj = new JSONObject(message);
    addHeader(jsonObj);
    setChanged();
    notifyObservers(jsonObj.toString());
  }

  public void fireEvent() {
    JSONObject jsonObj = new JSONObject();
    addHeader(jsonObj);
    setChanged();
    notifyObservers(jsonObj.toString());
  }

  private JSONObject addHeader(JSONObject jsonObj) {
    jsonObj.put("HEADER", HEADER);
    jsonObj.put("TYPE", etype);
    return jsonObj;
  }
}

