package org.wus32.assessment.ml.thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import org.wus32.assessment.ml.animation.AnimationView;

/**
 * Martian Lander
 * <p>
 * Created by Wu Shuang on 2016/8/27.
 */
public class AnimationThread extends Thread {

  private AnimationView view;

  private SurfaceHolder holder;

  private boolean isRunning;

  public AnimationThread(AnimationView view) {
    this.view = view;
    this.holder = view.getHolder();
  }

  @Override
  public void run() {
    Canvas canvas = null;
    while (isRunning) {
      try {
        canvas = holder.lockCanvas();
        synchronized (holder) {
          view.update();
          view.render(canvas);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (canvas != null) {
          holder.unlockCanvasAndPost(canvas);
        }
      }
    }
  }

  public void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }
}
