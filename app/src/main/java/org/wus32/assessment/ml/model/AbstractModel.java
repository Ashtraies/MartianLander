package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.SoundPool;

import org.wus32.assessment.ml.util.LevelSelector;
import org.wus32.assessment.ml.util.ResLoader;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/29.
 * An abstract class which supplies basic implementation the interface IModel.
 * In addition,it supplies some necessary fields and some useful componet for its subclass.
 * @see IModel
 */
public abstract class AbstractModel implements IModel {

  /**
   * X,y coordinate of model's left top point,which can specify the location of the model.
   */
  protected int x, y;

  /**
   * The width and height of the model.
   */
  protected int modelWidth, modelHeight;

  /**
   * The  width and height of the surfaceview which model draws on.
   * To know these is necessary for model drawing.
   */
  protected int viewWidth, viewHeight;

  /**
   * An android compnet which loads and plays sound effects.
   */
  protected SoundPool soundPool;

  /**
   * A tool used to load resources in res folder.
   */
  protected ResLoader resLoader;

  /**
   * Supply the parameters based on the difficulty level.
   */
  protected LevelSelector levelSelector;

  /**
   * The bitmap will be drawn.
   * Sometimes this bitmap need to be processed before drawing.
   */
  protected Bitmap bitmap;

  /**
   * The constructor.
   * @param context The context.
   * @param viewWidth The width of the surfaceview which the model draws on.
   * @param viewHeight The height of the surfaceview which the model draws on.
   */
  public AbstractModel(Context context,int viewWidth,int viewHeight) {
    this.viewWidth = viewWidth;
    this.viewHeight = viewHeight;
    //Initialize the componets for subclasses.Note that the order is important.
    resLoader = new ResLoader(context);
    levelSelector = new LevelSelector(resLoader);
    soundPool = new SoundPool.Builder().setMaxStreams(5).build();
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public int getModelWidth() {
    return modelWidth;
  }

  @Override
  public int getModelHeight() {
    return modelHeight;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
            "x=" + x +
            ", y=" + y +
            ", modelWidth=" + modelWidth +
            ", modelHeight=" + modelHeight +
            ", viewWidth=" + viewWidth +
            ", viewHeight=" + viewHeight +
            '}';
  }
}
