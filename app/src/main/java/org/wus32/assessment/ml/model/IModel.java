package org.wus32.assessment.ml.model;

import android.graphics.Canvas;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/29.
 * A interface of models in this game,define basic method of a game model.
 * All the models of this game should implement this interface.
 */
public interface IModel {

  /**
   * Draw this model on the user interface.
   * @param canvas On which canvas to draw the model.
   */
  void draw(Canvas canvas);

  /**
   * Get the x axis location of the model.
   * Use the x,y value,the model's location can be specified.
   * @return The x value of the model's left top point.
   */
  int getX();

  /**
   * Get the y axis location of the model.
   * Use the x,y value,the model's location can be specified.
   * @return The y value of the model's left top point.
   */
  int getY();

  /**
   * Set the model's x axis location.
   * By this method,we can locate the model on a particular location.
   * @param x X axis location,how much the model is away from the left edge of the screen.
   */
  void setX(int x);

  /**
   * Set the model's y axis location.
   * By this method,we can locate the model on a particular location.
   * @param y Y axis location,how much the model is away from the top edge of the screen.
   */
  void setY(int y);

  /**
   * Get the model's width.
   * @return The model's width.
   */
  int getModelWidth();

  /**
   * Get the model's height.
   * @return The model's height.
   */
  int getModelHeight();
}
