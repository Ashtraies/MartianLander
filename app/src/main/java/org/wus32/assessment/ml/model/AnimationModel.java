package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import org.wus32.assessment.ml.R;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/8/29.
 */
public class AnimationModel {

  private Context ctx;

  private Bitmap craft;

  private float craftX,craftY;

  private float velX,velY;

  public AnimationModel(Context ctx) {
    this.ctx = ctx;
    craft = loadImageById(R.drawable.craftmain);
  }

  public Bitmap loadImageById(int id) {
    return BitmapFactory.decodeResource(ctx.getResources(),id);
  }

  public void move() {
    craftX += velX;
    craftY += velY;
  }

  public void draw(Canvas canvas) {
  canvas.drawBitmap(craft,craftX,craftY,new Paint());
  }
}
