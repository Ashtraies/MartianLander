package org.wus32.assessment.ml.activity;

import android.app.Activity;
import android.os.Bundle;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.controller.MainController;
import org.wus32.assessment.ml.model.AnimationModel;
import org.wus32.assessment.ml.view.AnimationView;

public class MainActivity extends Activity {

  private AnimationView view;

  private AnimationModel model;

  private MainController controller;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    model = new AnimationModel(this);
    view = (AnimationView)findViewById(R.id.main_view);
    view.setModel(model);
    controller = new MainController(this,model);
  }

  @Override
  protected void onPause() {
    super.onPause();
    view.stopAnimation();
  }

  @Override
  protected void onResume() {
    super.onResume();
    view.startAnimation();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    view.stop();
  }
}
