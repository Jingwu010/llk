package view.events;

import org.json.JSONObject;

import java.util.Observable;

/**
 * Created by Jingwu Xu on 2019-05-06
 */
public class EventBus extends Observable implements Runnable{
  public static final String HEADER = "EVENT";
  private EventType etype;
  private boolean suspended;

  public void setEventType(EventType etype){
    this.etype = etype;
    this.suspended = true;
  }

  public synchronized void fireEvent() {
    this.suspended = !this.suspended;
    notify();
  }

  public void sendEvent() {
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

  @Override
  public void run() {
    Thread.currentThread().setName("LLK EVENTBUS THREAD");
    synchronized (this) {
      while (true) {
        while (suspended) {
          try {
            // System.out.println(Thread.currentThread().getName() + " sleep");
            wait();
            // System.out.println(Thread.currentThread().getName() + " awake");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        sendEvent();
        // System.out.println(Thread.currentThread().getName() + " message sent");
        this.suspended = !this.suspended;
      }
    }
  }

}

