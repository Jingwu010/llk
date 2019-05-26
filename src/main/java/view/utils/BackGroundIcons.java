package view.utils;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Random;

/**
 * Created by Jingwu Xu on 2019-05-25
 */
public class BackGroundIcons extends ImageView {
  public static BackGroundIcon[] backgroundIcons;

  public BackGroundIcons(int WIDTH, int HEIGHT) {
    File folder = new File(System.getProperty("user.dir") + "/src/main/resources/icons");
    File[] listOfFiles = folder.listFiles();
    BackGroundIcons.backgroundIcons = new BackGroundIcon[listOfFiles.length];
    int i = 0;
    for (final File fileEntry : listOfFiles) {
      BackGroundIcons.backgroundIcons[i] = new BackGroundIcon(fileEntry.getName(), WIDTH, HEIGHT);
      i += 1;
    }
    BackGroundIcons.backgroundIcons = randomizeArray(BackGroundIcons.backgroundIcons);
  }

  private static BackGroundIcon[] randomizeArray(BackGroundIcon[] array){
    Random rgen = new Random();  // Random number generator

		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    BackGroundIcon temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
		return array;
	}

  public static ImageView getImageView(int idx) {
    ImageView iv = new ImageView(backgroundIcons[idx]);
    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setContrast(-0.20);
    iv.setEffect(colorAdjust);
    return iv;
  }
}

class BackGroundIcon extends Image {
  public BackGroundIcon(String filename, int WIDTH, int HEIGHT) {
    super("file:src/main/resources/icons/"+filename, 32, 32, false, true);
  }
}
