package org.wus32.assessment.ml.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.util.BitmapUtil;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/29.
 */
public class Rocket extends AbstractModel {

  /**
   * The image of rocket,thruster and main thruster.
   */
  private Bitmap rocket, thruster, mainThruster;

  /**
   * The current speed of rocket in x and y direction.
   * speedY will increase because of the gravity.
   */
  private float speedX = 0, speedY = 0;

  /**
   * Current speedY.
   */
  private float speedYWithGravity;

  /**
   * When thruster starts,the speed in x direction.
   */
  private float boostX;

  /**
   * When main thruster starts,the speed in y direction.
   * boostY < 0,rocket moves upwards.
   */
  private float boostY;

  /**
   * The gravity
   */
  private float gravity;

  /**
   * Which thruster is started.
   */
  private boolean isLeftBoost, isRightBoost, isMainBoost;

  /**
   * The sound id of thruster.
   */
  private int soundThruster;

  /**
   * The sound id of now playing thruster.
   * Use this id to stop playing.
   */
  private int currentPlayingLeft,currentPlayingRight,currentPlayingTop;

  /**
   * Fuel gauge can be seemed as a part of rocket.
   */
  private FuelGauge fuelGauge;

  /**
   * Is the rocket crashed.
   */
  private boolean isCrash;

  /**
   * Does the rocket land successfully.
   */
  private boolean isFinish;

  public static final float SCALE = 0.5f;

  public Rocket(Context context,int viewWidth,int viewHeight) {
    super(context,viewWidth,viewHeight);
    fuelGauge = new FuelGauge(context,viewWidth,viewHeight);
    //Load sound effect.
    soundThruster = soundPool.load(context,R.raw.thruster,1);
    //Load images.
    rocket = resLoader.loadBitmap(R.drawable.lander_plain);
    rocket = BitmapUtil.scale(rocket,SCALE);
    mainThruster = resLoader.loadBitmap(R.drawable.main_flame);
    thruster = resLoader.loadBitmap(R.drawable.thruster);
    //Only after the image has been loaded,can get the width and height of the rocket.
    modelWidth = rocket.getWidth();
    modelHeight = rocket.getHeight();
    //Set the initial location of the rocket.
    //X location is in the centre of the view(screen).
    x = (viewWidth - modelWidth) / 2;
    y = -modelWidth;
    //Load constant from xml
    boostX = resLoader.loadIntRes(R.integer.rocket_boost_x);
    boostY = resLoader.loadIntRes(R.integer.rocket_boost_y);
    gravity = levelSelector.getGravity();
    //Set a initial Y speed.
//    speedY = boostY;
  }

  /**
   * Stimulate gravity,speedY will increase by gravity acceleration.
   */
  private void gravity() {
    speedY += gravity;
    //Record current downward speedY.
    if (speedY > 0) {
      speedYWithGravity = speedY;
    }
  }

  /**
   * Change x and y speed to make the rocket change it x and y position.
   */
  public void move() {
    //If game is over or finished,will not move anymore.
    if (!isCrash && !isFinish) {
      //Garvity always exists.
      gravity();
      //If the rocket move horizontally and move upword then consume fuel.
      if (speedX != 0 || speedY < 0) {
        fuelGauge.useFuel();
      }
      //If fuel is used up then stop all the thruster,rocket cannot move anymore.
      if (fuelGauge.isFuelUsedUp()) {
        stopRightThruster();
        stopLeftThruster();
        stopMainThruster();
      }
      //Move with speedX.
      x += speedX;
      if (y >= 0) {
        y += speedY;
      } else {
        //If rocket is at the top of screen,it will stay at the top.
        y = 0;
      }
    }
  }

  @Override
  public void draw(Canvas canvas) {
    /*
     * If the rocket has moved out the left side of screen,
     * then it will appear at the right side of screen.
     */
    if (x < -modelWidth) {
      x = viewWidth;
    }
    /*
     * If the rocket has moved out the right side of screen,
     * then it will appear at the left side of screen.
     */
    else if (x >= viewWidth) {
      x = -modelWidth;
    }
    canvas.drawBitmap(rocket,x,y,null);
    //Draw right thruster.
    //TODO Need to adjust the relative position of rocket and flame.
    if (isRightBoost) {
      canvas.drawBitmap(thruster,
              x + modelWidth / 2,
              y + modelHeight - 25,null);
    }
    //Draw left thruster.Need to adjust the relative position of rocket and flame.
    //TODO Need to adjust the relative position of rocket and flame.
    if (isLeftBoost) {
      canvas.drawBitmap(thruster,
              x + modelWidth / 2 - 15,
              y + modelHeight - 25,null);
    }
    //Draw main thruster.
    //TODO Need to adjust the relative position of rocket and flame.
    if (isMainBoost) {
      canvas.drawBitmap(mainThruster,
              x + (modelWidth - mainThruster.getWidth()) / 2 + 4,
              y + modelHeight - 22,null);
    }
  }

  /**
   * Start up left thruster,rocket will move rightward.
   */
  public void leftThruster() {
    //Only if fuel is not used up and game continues.
    if (!fuelGauge.isFuelUsedUp() && !isCrash && !isFinish) {
      isLeftBoost = true;
      //Play the sound effect.-1 means loop forever until stop.
      currentPlayingLeft = soundPool.play(soundThruster,1,1,1,-1,1);
      //speedX > 0,means move rightward.
      speedX = boostX;
    }
  }

  /**
   * Stop left thruster.
   */
  public void stopLeftThruster() {
    isLeftBoost = false;
    //Stop the sound effect.
    soundPool.stop(currentPlayingLeft);
    //speedX = 0,means the rocket will not move horizontally.
    speedX = 0;
  }

  /**
   * Start up right thruster,rocket will move leftward.
   */
  public void rightThruster() {
    //Only if fuel is not used up and game continues.
    if (!fuelGauge.isFuelUsedUp() && !isCrash && !isFinish) {
      isRightBoost = true;
      //Play the sound effect.-1 means loop forever until stop.
      currentPlayingRight = soundPool.play(soundThruster,1,1,1,-1,1);
      //speedX < 0,means move rightward.
      speedX = -boostX;
    }
  }

  /**
   * Stop right thruster.
   */
  public void stopRightThruster() {
    isRightBoost = false;
    //Stop the sound effect.
    soundPool.stop(currentPlayingRight);
    //speedX = 0,means the rocket will not move horizontally.
    speedX = 0;
  }

  /**
   * Start up main thruster,rocket will move upward.
   */
  public void mainThruster() {
    if (!fuelGauge.isFuelUsedUp() && !isCrash && !isFinish) {
      isMainBoost = true;
      //Play the sound effect.-1 means loop forever until stop.
      currentPlayingTop = soundPool.play(soundThruster,1,1,1,-1,1);
      //speedY < 0,means rocket move upward.
      speedY = -boostY;
    }
  }

  /**
   * Stop main thruster,and speedY will revert.
   */
  public void stopMainThruster() {
    isMainBoost = false;
    soundPool.stop(currentPlayingTop);
    speedY = speedYWithGravity;
  }

  public FuelGauge getFuelGauge() {
    return fuelGauge;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getModelWidth() {
    return modelWidth;
  }

  public int getModelHeight() {
    return modelHeight;
  }

  public boolean isLeftBoost() {
    return isLeftBoost;
  }

  public boolean isMainBoost() {
    return isMainBoost;
  }

  public boolean isRightBoost() {
    return isRightBoost;
  }

  public void setCrash(boolean crash) {
    isCrash = crash;
  }

  public void setFinish(boolean finish) {
    isFinish = finish;
  }
}
