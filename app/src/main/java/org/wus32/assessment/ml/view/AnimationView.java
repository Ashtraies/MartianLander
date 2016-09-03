package org.wus32.assessment.ml.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.wus32.assessment.ml.model.AnimationModel;

/**
 * Martian Lander
 * <p/>
 * Created by Wu Shuang on 2016/8/27.
 */
public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {

  /**
   * A thread controls animation;
   */
  private AnimationThread thread;

  private AnimationModel model;

  public AnimationView(Context context) {
    super(context);
    initialize(context);
  }

  public AnimationView(Context context,AttributeSet attrs) {
    super(context,attrs);
    initialize(context);
  }

  private void initialize(Context context) {
    getHolder().addCallback(this);
    thread = new AnimationThread(this);
    model = new AnimationModel(context);
  }


  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    thread.setRunning(true);
    thread.start();
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    stop();
  }

  public void stop() {
    boolean retry = true;
    while (retry) {
      try {
        thread.setRunning(false);
        thread.join();
        retry = false;
      } catch (InterruptedException e) {
        //Do nothing,keep trying.
      }
    }
  }

  public void update() {
    model.move();
  }

  public void render(Canvas canvas) {
    model.draw(canvas);
  }

  public void startAnimation() {
    thread.setRunning(true);
  }

  public void stopAnimation() {
    thread.setRunning(false);
  }

  public void setModel(AnimationModel model) {
    this.model = model;
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i1,int i2) {
  }
}
