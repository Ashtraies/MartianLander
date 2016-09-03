package org.wus32.assessment.ml.controller;

import android.app.Activity;
import android.view.View;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.model.AnimationModel;

/**
 * MartianLander
 * <p/>
 * Created by Wu Shuang on 2016/9/3.
 */
public class MainController {

  private View btnLeft, btnUp, btnRight,btnDown;

  private AnimationModel model;

  public MainController(Activity activity,AnimationModel model) {
    this.model = model;
    btnLeft = activity.findViewById(R.id.main_controller_left);
    btnLeft.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainController.this.model.changeSpeed(AnimationModel.Directtion.LEFT);
      }
    });
    btnUp = activity.findViewById(R.id.main_controller_up);
    btnUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainController.this.model.changeSpeed(AnimationModel.Directtion.UP);
      }
    });
    btnRight = activity.findViewById(R.id.main_controller_right);
    btnRight.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainController.this.model.changeSpeed(AnimationModel.Directtion.RIGHT);
      }
    });
    btnDown = activity.findViewById(R.id.main_controller_down);
    btnDown.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainController.this.model.changeSpeed(AnimationModel.Directtion.DOWN);
      }
    });
  }
}
