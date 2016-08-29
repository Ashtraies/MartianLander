package org.wus32.assessment.ml.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.wus32.assessment.ml.animation.AnimationView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new AnimationView(getApplicationContext()));
  }
}
