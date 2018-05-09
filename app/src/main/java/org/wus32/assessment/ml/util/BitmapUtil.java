package org.wus32.assessment.ml.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/20.
 * A tool use to process bitmaps.
 */
public class BitmapUtil {

  /**
   * Draw the whole bitmap on a certain area(rectangle).
   *
   * @param canvas Draw on which on which canvas.
   * @param bitmap The image to draw.
   * @param tW     The width of the target rectangle.
   * @param tH     The height of the target rectangle.
   */
  public static void fullDrawing(Canvas canvas,Bitmap bitmap,int tW,int tH) {
    //The whole image.
    Rect r1 = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
    //Target arae.
    Rect r2 = new Rect(0,0,tW,tH);
    canvas.drawBitmap(bitmap,r1,r2,null);
  }

  /**
   * Scale the bitmap.
   *
   * @param src   The source bitmap.
   * @param scale The scaling ratio.
   * @return New scaled bitmap.
   */
  public static Bitmap scale(Bitmap src,float scale) {
    //Get the width and height of source bitmap.
    int width = src.getWidth();
    int height = src.getHeight();
    //Create a matrix,and set the scale of width and height.Use the same sacling ratio.
    Matrix matrix = new Matrix();
    matrix.postScale(scale,scale);
    //Create a new scaled bitmap.
    return Bitmap.createBitmap(src,0,0,width,height,matrix,true);
  }

  /**
   * Cut the source bitmap into equal parts.
   *
   * @param src    The source bitmap.
   * @param column How mang column will the source bitmap be cut into.
   * @param row    How mang column will the source bitmap be cut into.
   * @param scale  The scaling ratio.If set 1.0f,then the bitmap will not be scaled.
   * @return A series of bitmap frame.
   */
  public static List<Bitmap> cut(Bitmap src,int column,int row,float scale) {
    List<Bitmap> bitmapList = new ArrayList<>(column * row);
    //Get the width and height of source bitmap.
    int width = src.getWidth();
    int height = src.getHeight();
    int unitWidth = width / column;
    int unitHeight = height / row;
    Bitmap bitmap;
    for (int i = 0;i < row;i++) {
      for (int j = 0;j < column;j++) {
        bitmap = Bitmap.createBitmap(src,
                unitWidth * j,
                unitHeight * i,
                unitWidth,
                unitHeight);
        if (scale != 1.0f) {
          bitmap = scale(bitmap,scale);
        }
        bitmapList.add(bitmap);
      }
    }
    return bitmapList;
  }
}
