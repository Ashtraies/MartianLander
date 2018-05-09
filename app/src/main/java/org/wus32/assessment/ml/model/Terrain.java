package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.activity.MainActivity;
import org.wus32.assessment.ml.util.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MartianLander
 * <p/>
 * Created by Wu Shuang on 2016/9/12.
 * A class used to describe the terrain of the Mars.
 * It is a polygon linked with a series of points.
 */
public class Terrain extends AbstractModel {

  /**
   * The texture.
   */
  private Bitmap terrainTexture;

  /**
   * How many sections this terrain contains.
   */
  private int sections;

  /**
   * How may flat sections where the rocket can land sucessfully this terrain contains.
   */
  private int flatNum;

  /**
   * The lowest and highest point this terrain may reach.
   */
  private int minHeight, maxHeight;

  /**
   * Every points' x coordinate of this terrain.
   */
  private int[] xs;

  /**
   * Every points' y coordinate of this terrain.
   */
  private int[] ys;

  /**
   * A collection contains all the flat sections of this terrain.
   */
  private List<Map<Integer, int[]>> landAreas;

  /**
   * The height of the flat sections.
   */
  private int offset = 5;

  /**
   * Use this paint to draw flat sections.
   */
  private Paint paint;

  /**
   * Use this paint to draw terrain.
   */
  private Paint terrainPaint;

  public Terrain(Context context,int viewWidth,int viewHeight) {
    super(context,viewWidth,viewHeight);
    //Initialize the paint.
    paint = new Paint();
    paint.setColor(Color.GREEN);
    //Load texture bitmap.
    terrainTexture = resLoader.loadBitmap(R.drawable.st_mars);
    terrainPaint = getTerrainPaint();
    //Get parameters based on level.
    sections = levelSelector.getSections();
    flatNum = levelSelector.getFlatNum();
    landAreas = new ArrayList<>(flatNum);
    minHeight = levelSelector.getMinHeight();
    maxHeight = levelSelector.getMaxHeight();
    //Add four more additional ponit to close the polygon.
    xs = new int[sections + 4];
    ys = new int[sections + 4];
    //Build all the points of the terrain.
    buildCoordinate();
  }

  /**
   * Build x and y array.
   * A pair of them is a point of the polygon(terrain).
   * Meanwhile set the flat sections.
   */
  private void buildCoordinate() {
    int unitWidth = viewWidth / sections;
    for (int i = 0;i < sections;i++) {
      xs[i] = i * unitWidth;
      ys[i] = viewHeight - MathUtil.random(minHeight,maxHeight);
    }
    //Set last points of the terrain.The four ponit are special.
    xs[sections + 0] = viewWidth;
    ys[sections + 0] = viewHeight - MathUtil.random(minHeight,maxHeight);
    xs[sections + 1] = viewWidth;
    ys[sections + 1] = viewHeight;
    xs[sections + 2] = 0;
    ys[sections + 2] = viewHeight;
    //The last point must be the first point to close the polygon.
    xs[sections + 3] = xs[0];
    ys[sections + 3] = ys[0];
    for (int i : MathUtil.getUniqueNums(sections - 1,flatNum)) {
      //Choose some point to reset the value of y coordinate to make it be a flat section.
      ys[i + 1] = ys[i];
      Map<Integer, int[]> landArea = new HashMap<>();
      landArea.put(0,new int[] {xs[i],xs[i + 1],xs[i + 1],xs[i],xs[i]});
      landArea.put(1,new int[] {ys[i],ys[i],ys[i] - offset,ys[i] - offset,ys[i]});
      landAreas.add(landArea);
    }
  }

  @Override
  public void draw(Canvas canvas) {
    canvas.drawPath(terrainPath(),terrainPaint);
    //If need,draw the flat sections.
    if (MainActivity.DEBUG) {
//      drawLandAreas(canvas,paint);
    }
  }

  /**
   * Draw all the flat section above the terrain to tip the users where is a flat section.
   *
   * @param canvas On which canvas to draw.
   * @param paint  Use this paint to draw.
   */
  @SuppressWarnings("unused")
  private void drawLandAreas(Canvas canvas,Paint paint) {
    for (Map<Integer, int[]> area : landAreas) {
      Path path = new Path();
      int[] x = area.get(0);
      int[] y = area.get(1);
      for (int i = 0;i < x.length;i++) {
        path.lineTo(x[i],y[i]);
      }
      canvas.drawPath(path,paint);
    }
  }

  /**
   * Create a paint,which can fill up the polygon with texture bitmap.
   *
   * @return
   */
  private Paint getTerrainPaint() {
    Paint terrainPaint = new Paint();
    terrainPaint.setAntiAlias(true);
    //Use bitmap shader and set the rule of filling.
    Shader terrainShader = new BitmapShader(terrainTexture,
            Shader.TileMode.REPEAT,
            Shader.TileMode.MIRROR);
    terrainPaint.setShader(terrainShader);
    return terrainPaint;
  }

  /**
   * Link all the points of terrain.
   *
   * @return The outline of the polygon.
   */
  private Path terrainPath() {
    Path path = new Path();
    for (int i = 0;i < xs.length;i++) {
      path.lineTo(xs[i],ys[i]);
    }
    return path;
  }

  /**
   * Get the x coordinate of all the terrain's point.
   *
   * @return The x coordinate of all the terrain's point.
   */
  public int[] getXs() {
    return xs;
  }

  /**
   * Get the y coordinate of all the terrain's point.
   *
   * @return The y coordinate of all the terrain's point.
   */
  public int[] getYs() {
    return ys;
  }
}
