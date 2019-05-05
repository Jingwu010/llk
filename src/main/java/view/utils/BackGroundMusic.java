package view.utils;

import javafx.scene.media.AudioClip;

/**
 * Created by Jingwu Xu on 2019-05-04
 */
public class BackGroundMusic {
  static AudioClip bgm;
  public BackGroundMusic() {
    bgm = new AudioClip("file:src/main/resources/bgm.wav");
    bgm.setVolume(0.5);
    bgm.setCycleCount(AudioClip.INDEFINITE);
  }

  public static void play() {
    bgm.play();
  }
}
