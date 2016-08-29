package org.wus32.assessment.ml.activity;

import android.app.Activity;
import android.os.Bundle;

import org.wus32.assessment.ml.animation.AnimationView;

public class MainActivity extends Activity {

  private AnimationView view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    view = new AnimationView(getApplicationContext());
    setContentView(view);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    view.stop();
  }
}
