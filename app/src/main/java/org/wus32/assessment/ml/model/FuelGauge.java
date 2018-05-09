package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.util.BitmapUtil;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/26.
 * Use number to indicate the fuel gauge remaining.
 */
public class FuelGauge extends AbstractModel{

  /**
   * The max amuount of fuel can be used.
   */
  private int fuel;

  /**
   * The paint which draws fuel ramaining number.
   */
  private Paint paint;

  /**
   * How much fuel will be used for one movement.
   */
  private int perConsume;

  public FuelGauge(Context context,int viewWidth,int viewHeight) {
    super(context,viewWidth,viewHeight);
    //Soma margin is necessary.
    x = 5;
    y = 5;
    //Load fuel gauge image and scale.
    bitmap = resLoader.loadBitmap(R.drawable.fuel_guage);
    bitmap = BitmapUtil.scale(bitmap,0.25f);
    //Get per consume.
    perConsume = resLoader.loadIntRes(R.integer.fuel_per_consume);
    //Get max amount of fuel.
    fuel = levelSelector.getMaxFuel();
    //Initialize the paint,set color and text size.
    paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setTextSize(resLoader.getTextSize(R.dimen.text_size_l));
  }

  @Override
  public void draw(Canvas canvas) {
    //Draw the image.
    canvas.drawBitmap(bitmap,x,y,null);
    //Draw the number.
    canvas.drawText(Integer.toString(fuel),
            bitmap.getWidth()/2-20,
            bitmap.getHeight()/2+10,paint);
  }

  /**
   * Call this method while rocket is moving to cosume fuel.
   */
  public void useFuel() {
    if(!isFuelUsedUp()){
      fuel -= perConsume;
    }
  }

  /**
   * @return Whether the fuel is used up.
   */
  public boolean isFuelUsedUp() {
    return fuel == 0;
  }

}
