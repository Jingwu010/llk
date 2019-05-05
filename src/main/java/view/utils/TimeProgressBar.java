package view.utils;

import javafx.scene.control.ProgressBar;

/**
 * Created by Jingwu Xu on 2019-05-04
 */
public class TimeProgressBar extends ProgressBar implements Runnable {
  // [TODO] runnable class thread safe update
  int time_remaining = 120;
  final double full_size = 120.0;

  public TimeProgressBar() {
    super();
    setMinSize(200, 20);
    updateProgress();
  }

  public void start() {
    while (time_remaining>0) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      time_remaining -= 1;
      updateProgress();
    }
    // [TODO] time runs out
  }

  public void updateProgress() {
    setProgress(time_remaining/full_size);
  }

  @Override
  public void run() {
    start();
  }
}
