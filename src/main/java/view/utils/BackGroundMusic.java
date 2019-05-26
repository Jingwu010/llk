package view.utils;

import javafx.scene.media.AudioClip;

/**
 * Created by Jingwu Xu on 2019-05-04
 */
public class BackGroundMusic {
  static AudioClip bgm;
  public BackGroundMusic() {
    bgm = new AudioClip("file:src/main/resources/sounds/bg1.m4a");
    bgm.setVolume(0.7);
    bgm.setCycleCount(AudioClip.INDEFINITE);
  }

  public static void play() {
    bgm.play();
  }
}
