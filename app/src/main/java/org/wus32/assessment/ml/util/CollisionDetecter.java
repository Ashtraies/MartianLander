package org.wus32.assessment.ml.util;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/26.
 * Checking the rocket land sucessfully or crash on the terrain.
 * <p>
 * THIS CODE IS FROM SIMPLE GRAVITY
 * Sample code for subject ISCG7424 Mobile Application Development.
 *
 * @author John Casey 11/04/2014
 */
public class CollisionDetecter {

  /**
   * Checking whether the right or left bottom point of the model,
   * is contained in a polygon.
   *
   * @param xcor All the polygon's points' x coordinate.
   * @param ycor All the polygon's points' y coordinate.
   * @param x0   The x coordinate of the model's bottom point.
   * @param y0   The y coordinate of the model's bottom point.
   * @return
   */
  public static boolean contains(int[] xcor,int[] ycor,double x0,double y0) {
    int crossings = 0;
    for (int i = 0;i < xcor.length - 1;i++) {
      int x1 = xcor[i];
      int x2 = xcor[i + 1];
      int y1 = ycor[i];
      int y2 = ycor[i + 1];
      int dy = y2 - y1;
      int dx = x2 - x1;
      double slope = 0;
      if (dx != 0) {
        slope = (double)dy / dx;
      }
      boolean cond1 = (x1 <= x0) && (x0 < x2);
      boolean cond2 = (x2 <= x0) && (x0 < x1);
      boolean above = (y0 < slope * (x0 - x1) + y1);
      if ((cond1 || cond2) && above) {
        crossings++;
      }
    }
    return (crossings % 2 != 0);
  }
}
