package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.util.BitmapUtil;

import java.util.List;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/29.
 * This class process the explosion effect once the rocket crash.
 */
public class Explosion extends AbstractModel {

  /**
   * Which frame of explosion to display.
   */
  private int index;

  /**
   * A collection of all the explosion frmaes.
   */
  private List<Bitmap> images;

  /**
   * The image of rocket's wreckage and crater.
   */
  private Bitmap wreckage, crater;

  private boolean isFinishDraw;

  public Explosion(Context context,int viewWidth,int viewHeight) {
    super(context,viewWidth,viewHeight);
    //Load the explosion image and process.
    bitmap = resLoader.loadBitmap(R.drawable.explosion);
    //Cut the whole bitmap into four equal parts and scale to 1/4.
    images = BitmapUtil.cut(bitmap,2,2,0.25f);
    //Load and scale.
    wreckage = BitmapUtil.scale(resLoader.loadBitmap(R.drawable.lander_crashed),Rocket.SCALE);
    crater = BitmapUtil.scale(resLoader.loadBitmap(R.drawable.crater),0.15f);
    //Will lose a bit of accuracy.
    modelWidth = (int)(bitmap.getWidth() / 2 * 0.25);
    modelHeight = (int)(bitmap.getHeight() / 2 * 0.25);
  }

  @Override
  public void draw(Canvas canvas) {
    //Get every frame of explosion effect.
    if (index < images.size()) {
      canvas.drawBitmap(images.get(index),x,y,null);
      //Get next frame;
      index++;
    } else {
      //When explosion animation ends,leave the wreckage and crater on the terrain.
      canvas.drawBitmap(wreckage,x,y,null);
      canvas.drawBitmap(crater,x,y + wreckage.getHeight() / 2,null);
      isFinishDraw = true;
    }
  }

  public boolean isFinishDraw() {
    return isFinishDraw;
  }
}
