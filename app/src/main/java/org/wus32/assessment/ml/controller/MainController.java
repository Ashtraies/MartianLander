package org.wus32.assessment.ml.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.activity.MainActivity;
import org.wus32.assessment.ml.model.Rocket;

/**
 * MartianLander
 * <p/>
 * Created by Wu Shuang on 2016/9/3.
 * The main controller which controls the movement of the rocket through accelerometer sensor.
 */
public class MainController {

  /**
   * Functional buttons on the layout.
   */
  private View btnLeft, btnUp, btnRight;

  /**
   * To get the accelerometer sensor.
   */
  private SensorManager sensorManager;

  /**
   * Listening the changes detected by accelerometer sensor.
   */
  private SensorEventListener listener;

  /**
   * The rocket,model of this game,which is controllered by this controller.
   */
  private Rocket model;

  /**
   * When the accelerometer is lager or less this value,then the rocket will move.
   * To avoid the movement of the rocket is too sensitive.
   */
  public static final float THRESHOLD = 1.5f;

  /**
   * Context
   */
  private MainActivity activity;

  public MainController(MainActivity activity,Rocket model) {
    this.activity = activity;
    this.model = model;
    btnLeft = activity.findViewById(R.id.main_controller_left);
    btnUp = activity.findViewById(R.id.main_controller_up);
    btnRight = activity.findViewById(R.id.main_controller_right);
    //Choose a way to control the rocket.
    if (MainActivity.control == Control.BTN) {
      btnLeft.setVisibility(View.VISIBLE);
      btnUp.setVisibility(View.VISIBLE);
      btnRight.setVisibility(View.VISIBLE);
      Toast.makeText(activity,R.string.controller_use_btns,Toast.LENGTH_SHORT).show();
      useButton();
    } else if (MainActivity.control == Control.SENSOR) {
      //Hide the buttons.
      btnLeft.setVisibility(View.INVISIBLE);
      btnUp.setVisibility(View.INVISIBLE);
      btnRight.setVisibility(View.INVISIBLE);
      Toast.makeText(activity,R.string.controller_use_sensor,Toast.LENGTH_SHORT).show();
      useSenor(activity);
    }
  }

  /**
   * Use buttons to control the rocket.
   */
  private void useButton() {
    btnLeft.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v,MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            //Press button,start up thruster.
            MainController.this.model.leftThruster();
            break;
          case MotionEvent.ACTION_UP:
            //Release,stop thruster.
            MainController.this.model.stopLeftThruster();
            break;
          default:
            break;
        }
        return true;
      }
    });
    btnUp.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v,MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            //Press button,start up thruster.
            MainController.this.model.mainThruster();
            break;
          case MotionEvent.ACTION_UP:
            //Release,stop thruster.
            MainController.this.model.stopMainThruster();
            break;
          default:
            break;
        }
        return true;
      }
    });
    btnRight.setOnTouchListener(new View.OnTouchListener() {

      @Override
      public boolean onTouch(View v,MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            //Press button,start up thruster.
            MainController.this.model.rightThruster();
            break;
          case MotionEvent.ACTION_UP:
            //Release,stop thruster.
            MainController.this.model.stopRightThruster();
            break;
          default:
            break;
        }
        return true;
      }
    });
  }

  /**
   * Use accelerometer sensor to control the model.
   *
   * @param activity The context,using it to get the top buttons on the layout.
   */
  private void useSenor(MainActivity activity) {
    //Use context to get the SensorManager.
    sensorManager = (SensorManager)activity.getSystemService(Context.SENSOR_SERVICE);
    //Get the accelerometer sensor.
    Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    //Create a listener.
    listener = new SensorEventListener() {

      @Override
      public void onSensorChanged(SensorEvent event) {
        //Get the horizontal accelerometer.
        float x = event.values[0];
        //Get the vertical accelerometer.
        float y = event.values[1];
        /*
         * Start he right thruster when the accelerometer larger than the threshold,
         * and the right thruster has not started up.
         */
        if (x > THRESHOLD && !MainController.this.model.isRightBoost()) {
          MainController.this.model.rightThruster();
        }
        /*
         * Start he left thruster when the accelerometer less than the minus threshold,
         * and the left thruster has not started up.
         */
        if (x < -THRESHOLD && !MainController.this.model.isLeftBoost()) {
          MainController.this.model.leftThruster();
        }
        if (y < -THRESHOLD && !MainController.this.model.isMainBoost()) {
          MainController.this.model.mainThruster();
        }
        /*
         * When the accelerometer is between the threshold and the minus threshold,
         * stop the right and left thruster.
         */
        if (x < THRESHOLD && x > -THRESHOLD) {
          MainController.this.model.stopRightThruster();
          MainController.this.model.stopLeftThruster();
        }
        if (y > THRESHOLD) {
          MainController.this.model.stopMainThruster();
        }
      }

      @Override
      public void onAccuracyChanged(Sensor sensor,int accuracy) {
        //No need to do anything.
      }
    };
    //Register the sensor and listener to the sensor manager.
    sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_GAME);
  }

  /**
   * Remove the sensor listener from the sensor manager,when quit the game.
   */
  public void unregister() {
    if (sensorManager != null) {
      sensorManager.unregisterListener(listener);
    }
  }

  public enum Control {
    BTN,SENSOR
  }
}
