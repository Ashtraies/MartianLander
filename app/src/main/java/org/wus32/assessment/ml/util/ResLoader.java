package org.wus32.assessment.ml.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * MartianLander
 * <p/>
 * Created by Wu Shuang on 2016/9/13.
 * A tool used to load all kinds of config which defined in the xml or drawable.
 */
public class ResLoader {

  /**
   * Android resources object.
   */
  private Resources res;

  /**
   * @param context Use context to get the resources object.
   */
  public ResLoader(Context context) {
    this.res = context.getResources();
  }

  /**
   * Get int config value.
   *
   * @param id resource id defiend in int_const.xml.
   * @return The int value.
   */
  public int loadIntRes(int id) {
    return res.getInteger(id);
  }

  /**
   * Get the text size.
   *
   * @param id resource id defiend in dimens.xml.
   * @return The text size.
   */
  public float getTextSize(int id) {
    return res.getDimension(id);
  }

  /**
   * Load bitmap from drawable folder.
   *
   * @param id The name of the bitmap.
   * @return A bitmap.
   */
  public Bitmap loadBitmap(int id) {
    return BitmapFactory.decodeResource(res,id);
  }
}
