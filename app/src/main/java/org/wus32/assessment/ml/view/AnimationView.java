package org.wus32.assessment.ml.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.activity.MainActivity;
import org.wus32.assessment.ml.controller.MainController;
import org.wus32.assessment.ml.model.Explosion;
import org.wus32.assessment.ml.model.Rocket;
import org.wus32.assessment.ml.model.Terrain;
import org.wus32.assessment.ml.util.BitmapUtil;
import org.wus32.assessment.ml.util.CollisionDetecter;
import org.wus32.assessment.ml.util.Dialog;
import org.wus32.assessment.ml.util.ResLoader;

/**
 * Martian Lander
 * <p/>
 * Created by Wu Shuang on 2016/8/27.
 * The main view of this game,all the models are drawn on this view.
 * The view is a subclass of SurfaceView,which can draw efficiently.
 *
 * @see SurfaceView
 */
public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {

  /**
   * A thread controls animation;
   */
  private AnimationThread thread;

  /**
   * Model rocket.
   *
   * @see Rocket
   */
  private Rocket rocket;

  /**
   * Model terrain.
   *
   * @see Terrain
   */
  private Terrain terrain;

  /**
   * Model explosion.
   *
   * @see Explosion
   */
  private Explosion explosion;

  /**
   * Resource loader.
   */
  private ResLoader resLoader;

  /**
   * Back ground of this game.
   */
  private Bitmap backgroundImage;

  /**
   * The width and height of this view.
   */
  private int viewWidth, viewHeight;

  /**
   * A componet used to load and play sound effect.
   */
  private SoundPool soundPool;

  /**
   * Id of sound effect,returned by SoundPool.load().
   */
  private int soundFinish, soundExplosion;

  /**
   * A controller which controls the movement of rocket.
   */
  private MainController controller;

  /**
   * Counter to control the sound effect just play once.
   */
  private int soundExplosionCounter, soundFinishCounter;

  /**
   * Use this to receive message from sub thread.
   */
  private Handler restartHandler;

  /**
   * Game finish or game over.
   */
  private boolean isFinsish, isGameover;

  public AnimationView(Context context,AttributeSet attrs) {
    super(context,attrs);
    //Register a listener.
    getHolder().addCallback(this);
    //Load sound effect.
    soundPool = new SoundPool.Builder().setMaxStreams(5).build();
    soundFinish = soundPool.load(context,R.raw.finish,1);
    soundExplosion = soundPool.load(context,R.raw.explosion,1);
    //Load bacground image.
    resLoader = new ResLoader(getContext());
    backgroundImage = resLoader.loadBitmap(R.drawable.bg_2);
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    /*
     * Get the width and height of this view.
     * Important!Only when the view has been created,
     * we can know the real width and height of this view.
     */
    viewWidth = getWidth();
    viewHeight = getHeight();
  }

  /**
   * Initialize all the models,After players have choosen a difficulty level.
   *
   * @see Dialog
   */
  public void initialize() {
    //Reset to 0.
    soundExplosionCounter = 0;
    soundFinishCounter = 0;
    //Reset to flase/
    isGameover = false;
    isFinsish = false;
    //Initialize all the models.
    rocket = new Rocket(getContext(),viewWidth,viewHeight);
    terrain = new Terrain(getContext(),viewWidth,viewHeight);
    explosion = new Explosion(getContext(),viewWidth,viewHeight);
    //Binding controller.
    controller = new MainController((MainActivity)getContext(),rocket);
    //Register message handler.After the sub thread return a message,will call back this.
    restartHandler = new Handler() {

      @Override
      public void handleMessage(Message msg) {
        //Game complete.
        if (isFinsish) {
          reset(R.string.main_dialog_success);
        }
        //Game over.
        if (isGameover) {
          reset(R.string.main_dialog_gameover);
        }
      }
    };
    //Initialize main thread(loop)
    thread = new AnimationThread(this);
    thread.setHandler(restartHandler);
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    //Stop animation.
    stop();
  }

  /**
   * Stop all the things been doing.
   */
  public void stop() {
    boolean retry = true;
    //Unregister the listener.
    controller.unregister();
    //Rocket stop thruster.
    rocket.stopLeftThruster();
    rocket.stopRightThruster();
    rocket.stopMainThruster();
    while (retry) {
      try {
        //Stop the thread.
        thread.setRunning(false);
        thread.join();
        retry = false;
      } catch (InterruptedException e) {
        //Do nothing,keep trying.
      }
    }
  }

  /**
   * Receive the movement of rocket.
   */
  public void update() {
    rocket.move();
  }

  /**
   * Start a new game.
   *
   * @param titleId The title of the difficculty level select dialog.
   */
  public void reset(int titleId) {
    //Stop current game.
    stop();
    //Get view width and height.
    surfaceCreated(getHolder());
    //Show dialog.
    new Dialog(getContext(),this).showSelectDialog(titleId);
  }

  /**
   * Draw all the elements in the view.
   *
   * @param canvas
   */
  public void render(Canvas canvas) {
    //Draw the background image.
    BitmapUtil.fullDrawing(canvas,backgroundImage,viewWidth,viewHeight);
    //Draw the terrain.
    terrain.draw(canvas);
    //Draw the fule gauage/
    rocket.getFuelGauge().draw(canvas);
    //If land successfully,game finish.
    if (finish()) {
      isFinsish = true;
      doFinish(canvas);
    } else if (crash()) {
      doCrash(canvas);
    } else {
      //Normal state.
      rocket.draw(canvas);
    }
  }

  /**
   * When game finished,call this method.
   *
   * @param canvas
   */
  private void doFinish(Canvas canvas) {
    //Play sound effect of finishing.Use counter to play this sound effect only once.
    if (soundFinishCounter == 0) {
      soundPool.play(soundFinish,1,1,1,0,1);
    }
    //Counter add 1.
    soundFinishCounter = 1;
    //Notify the model that game is finished.
    rocket.setFinish(true);
    rocket.stopRightThruster();
    rocket.stopLeftThruster();
    rocket.stopMainThruster();
    rocket.draw(canvas);
  }

  /**
   * When rocket crashed,call this method to draw explosion,wreckage and crater.
   *
   * @param canvas
   */
  private void doCrash(Canvas canvas) {
    //Play sound effect of finishing.Use counter to play this sound effect only once.
    if (soundExplosionCounter == 0) {
      soundPool.play(soundExplosion,0.3f,0.3f,1,0,1);
    }
    //Counter add 1.
    soundExplosionCounter = 1;
    //Notify the model that game is over.
    rocket.setCrash(true);
    /*
     * Set the position of explosion animation.
     * This position should be based on the position of rocket.
     */
    explosion.setX(rocket.getX() +
            rocket.getModelWidth() / 2 -
            explosion.getModelWidth() / 2);
    explosion.setY(rocket.getY() +
            rocket.getModelHeight() / 2 -
            explosion.getModelHeight() / 2);
    explosion.draw(canvas);
    //After the animation ended,the game is over.
    if (explosion.isFinishDraw()) {
      isGameover = true;
    }
  }

  /**
   * Check the the rocket is crashed or not.
   * If one of the bottom point of the rocket is contained by the terrain,
   * the rocket is crashed.
   *
   * @return Whether the rocket is crashed.
   */
  private boolean crash() {
    //Check whether the left bottom point is contained by the by the terrain.
    boolean left = CollisionDetecter.contains(
            terrain.getXs(),
            terrain.getYs(),
            //Reduce the width of model,to make the game easier.
            rocket.getX() + 10,
            //15 is an offset.
            rocket.getY() + rocket.getModelHeight() - 15);
    //Check whether the right bottom point is contained by the by the terrain.
    boolean right = CollisionDetecter.contains(
            terrain.getXs(),
            terrain.getYs(),
            //Reduce the width of model,to make the game easier.
            rocket.getX() + rocket.getModelWidth() - 10,
            //15 is a offset.
            rocket.getY() + rocket.getModelHeight() - 15);
    //If one of the point is contained by the by the terrain.The rocket crashes.
    return left || right;
  }


  /**
   * Check the the rocket lands or not.
   * If both of the bottom point of the rocket is contained by the terrain at the same time,
   * the rocket lands successfully.
   *
   * @return Whether the rocket lands.
   */
  private boolean finish() {
    //Check whether the left bottom point is contained by the by the terrain.
    boolean left = CollisionDetecter.contains(
            terrain.getXs(),
            terrain.getYs(),
            //Reduce the width of model,to make the game easier.
            rocket.getX() + 10,
            //15 is an offset.
            rocket.getY() + rocket.getModelHeight() - 15);
    //Check whether the right bottom point is contained by the by the terrain.
    boolean right = CollisionDetecter.contains(
            terrain.getXs(),
            terrain.getYs(),
            //Reduce the width of model,to make the game easier.
            rocket.getX() + rocket.getModelWidth() - 10,
            //15 is an offset.
            rocket.getY() + rocket.getModelHeight() - 15);
    /*
     * If both of the points are contained by the by the terrain at the same time.
     * The rocket lands successfully.
     */
    return left && right;
  }

  /**
   * Start up the game thread.
   */
  public void startAnimation() {
    if (thread != null) {
      thread.setRunning(true);
      thread.start();
    }
  }

  /**
   * Whether the game is finished.
   *
   * @return Whether the game is finished.
   */
  public boolean isFinsish() {
    return isFinsish;
  }

  /**
   * Whether the game is over.
   *
   * @return Whether the game is over.
   */
  public boolean isGameover() {
    return isGameover;
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i1,int i2) {
    //No need to do anything.
  }
}
