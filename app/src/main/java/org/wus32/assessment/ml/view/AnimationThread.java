package org.wus32.assessment.ml.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.util.ResLoader;

/**
 * Martian Lander
 * <p>
 * Created by Wu Shuang on 2016/8/27.
 * A sub thread use a loop to process the game progress.
 */
public class AnimationThread extends Thread {

  /**
   * @see AnimationView
   */
  private AnimationView view;

  /**
   * @see SurfaceHolder
   */
  private SurfaceHolder holder;

  /**
   * The main loop is running or not.
   */
  private boolean isRunning;

  /**
   * The width and height of main view.
   */
  private int viewWidth, viewHeight;

  /**
   * Used to load constant.
   * @see ResLoader
   */
  private ResLoader resLoader;

  /**
   * A secondary cache,use this to create a custom canvas,
   * and all the elemets will be drawn on this canvas,
   * then draw this cache to the canvas that provided by the view.
   * By doing this,make drawing more efficiently.
   */
  private Bitmap secondaryCache;

  /**
   * How long draw once.
   */
  private int refreshRate;

  /**
   * Use this handler to post message to the main thread.
   * Because sub thread cannot use UI componet,
   * for example,in sub thread,you cannot show a dialog.
   */
  private Handler handler;

  public AnimationThread(AnimationView view) {
    this.view = view;
    this.holder = view.getHolder();
    this.viewWidth = view.getWidth();
    this.viewHeight = view.getHeight();
    resLoader = new ResLoader(view.getContext());
    //Load refresh rate from xml config.
    refreshRate = resLoader.loadIntRes(R.integer.thread_refresh_rate);
    //Create a empty bitmap as secondary cache.
    secondaryCache = Bitmap.createBitmap(viewWidth,viewHeight,Bitmap.Config.ARGB_8888);
  }

  @Override
  public void run() {
    Canvas canvas = null;
    Canvas customCanvas;
    //A loop controlled by a boolean.
    while (isRunning) {
      try {
        //This section should be synchronized.
        synchronized (holder) {
          //Get system canvas.
          canvas = holder.lockCanvas();
          //Use secondary cache to create a custom canvas.
          customCanvas = new Canvas(secondaryCache);
          //*******************DO WORK HERE*********************
          //Update model.
          view.update();
          //Use custom canvas to draw models.
          view.render(customCanvas);
          //*******************DO WORK HERE*********************
          //Draw secondary cache to the system canvas.
          canvas.drawBitmap(secondaryCache,0,0,null);
        }
        //Refresh frequency:how long this thread draws once.
        Thread.sleep(refreshRate);
        //Stop loop and send message to main thread.
        if(view.isGameover() || view.isFinsish()) {
          //Delay 1s.
          handler.sendEmptyMessageDelayed(0,1000);
          isRunning = false;
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (canvas != null) {
          //Post the drawing content to the user interface.
          holder.unlockCanvasAndPost(canvas);
        }
      }
    }
  }

  /**
   * Register a handler.
   * @param handler
   */
  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  /**
   * Controll the main loop running or not.
   * @param isRunning
   */
  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }
}
