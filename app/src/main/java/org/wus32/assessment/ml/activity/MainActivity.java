package org.wus32.assessment.ml.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.controller.MainController;
import org.wus32.assessment.ml.util.Dialog;
import org.wus32.assessment.ml.util.LevelSelector;
import org.wus32.assessment.ml.view.AnimationView;

/**
 * MartianLander
 * <p/>
 * Created by Wu Shuang on 2016/9/3.
 * The portal of this game.
 * 1.Set the layout,get the main view,and buttons.
 * 2.Bind the reset function to the button.
 * 3.Show the dialog to let the player choose a level to start the game.
 * 4.When the activity is onPause or onDestroy,stop the main game loop,through call the
 * stop method of AnimationView
 *
 * @see AnimationView
 */
public class MainActivity extends Activity {

  /**
   * The main view of this game.
   */
  private AnimationView mainView;

  /**
   * If debug the programme will do something needed in debug mode.
   */
  public static final boolean DEBUG = false;

  /**
   * A class which define the difficult of this game.
   * The default level is easy.
   */
  public static LevelSelector.Level level = LevelSelector.Level.EASY;

  /**
   * Use which way to control the rocket.
   */
  public static MainController.Control control = MainController.Control.BTN;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //Get the main view.
    mainView = (AnimationView)findViewById(R.id.main_view);
    //Bind reset function.
    if(MainActivity.DEBUG) {
      View reset = findViewById(R.id.reset);
      reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mainView.reset(R.string.main_dialog_title);
        }
      });
    }
    //Before start the game,show dialog to let the players choose a level.
    new Dialog(this,mainView).showSelectDialog();
  }

  @Override
  protected void onPause() {
    super.onPause();
    //Stop the game loop.
    mainView.stop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //Stop the game loop.
    mainView.stop();
  }
  
  public static  void main(String [] aa) {
    System.out.print("da");
  }
}
