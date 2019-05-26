package view.utils;

import control.LLKGame;
import control.MessageBus;
import control.MessageIdentifier;
import control.MessageType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import org.json.JSONObject;
import view.events.EventBus;
import view.events.EventType;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jingwu Xu on 2019-05-04
 */
public class TimeProgressBar extends ProgressBar implements Runnable, Observer {
  LLKGame game;
  EventBus ebus;
  int time_remaining = 180;
  double time_size = 180;
  boolean suspended;

  public TimeProgressBar(LLKGame game, EventBus ebus) {
    super();
    setMinSize(200, 20);
    setPadding(new Insets(20, 150, 20, 150));

    updateProgress();

    this.game = game;
    this.ebus = ebus;
    this.suspended = false;

    game.mbus.addObserver(this);
    ebus.addObserver(this);
  }


  public void start() {
    Thread.currentThread().setName("LLK TIMER THREAD");
    while (true) {
      try {
        Thread.sleep(1000);
        synchronized(this) {
          while(suspended) {
            wait();
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      time_remaining -= 1;
      updateProgress();
      if (time_remaining < 0) {
        ebus.setEventType(EventType.TIMEOUT);
        ebus.fireEvent();
        suspended = !suspended;
      }
    }
    // time runs out

  }

  @Override
  public void run() {
    start();
  }

  public void updateProgress() {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        setProgress(time_remaining/time_size);
      }
    });
  }

  @Override
  public synchronized void update(Observable o, Object arg) {
    JSONObject jsonObj = new JSONObject(arg.toString());
    String header = jsonObj.getString("HEADER");
    switch (header) {
      case MessageBus.HEADER:
        if (!jsonObj.getString("IDENTIFIER").equals(MessageIdentifier.TIMER.toString()))
          return;
        MessageType mtype = MessageType.toType(jsonObj.getString("TYPE"));
        switch (mtype) {
          case POST:
            time_remaining += jsonObj.getInt("DATA");
            time_remaining = Math.min(time_remaining, (int)time_size);
            break;
          case PUT:
            time_size = (double)jsonObj.getInt("DATA");
            time_remaining = (int)time_size;
            break;
        }
        updateProgress();
        break;
      case EventBus.HEADER:
        String etype = jsonObj.getString("TYPE");
        if (etype.equals(EventType.PAUSE.toString())) {
          suspended = !suspended;
          if (!suspended) {
            // System.out.println("notify timer");
            notify();
          }
        }
        break;
      default:
          break;
    }
  }
}
