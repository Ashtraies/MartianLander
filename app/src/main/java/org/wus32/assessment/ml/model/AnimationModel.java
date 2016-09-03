package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.constant.SysConst;

/**
 * MartianLander
 * <p/>
 * Created by Wu Shuang on 2016/8/29.
 */
public class AnimationModel {

  private Context ctx;

  private Bitmap craft, background;

  private float craftX, craftY;

  private float speedH, speedV = SysConst.BASE_SPEED;

  public AnimationModel(Context ctx) {
    this.ctx = ctx;
    craft = loadImageById(R.drawable.craftmain);
    background = loadImageById(R.drawable.mars);
  }

  public Bitmap loadImageById(int id) {
    return BitmapFactory.decodeResource(ctx.getResources(),id);
  }

  public void move() {
    craftX += speedH;
    craftY += speedV;
  }

  public void draw(Canvas canvas) {
    canvas.drawColor(Color.BLACK);
//    canvas.drawBitmap(background,0,0,null);
    canvas.drawBitmap(craft,craftX,craftY,null);
  }

  public void changeSpeed(Directtion d) {
    switch (d) {
      case LEFT:
        speedH = SysConst.BASE_SPEED * -1;
        break;
      case RIGHT:
        speedH = SysConst.BASE_SPEED;
        break;
      case UP:
        speedV = SysConst.BASE_SPEED * -1;
        break;
      case DOWN:
        speedV = SysConst.BASE_SPEED;
        break;
    }
  }

  public enum Directtion {
    LEFT,UP,RIGHT,DOWN
  }
}
