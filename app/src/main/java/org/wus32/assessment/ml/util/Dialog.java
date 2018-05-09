package org.wus32.assessment.ml.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.activity.MainActivity;
import org.wus32.assessment.ml.controller.MainController;
import org.wus32.assessment.ml.view.AnimationView;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/10/2.
 * A level select dialog,players could select difficulty level through this dialog.
 */
public class Dialog {

  /**
   * Context.
   */
  private Context context;

  /**
   * The game view.
   */
  private AnimationView mainView;

  public Dialog(Context context,AnimationView mainView) {
    this.context = context;
    this.mainView = mainView;
  }

  /**
   * Show this dialog with title "Plaese choose a level to start!".
   */
  public void showSelectDialog() {
    showSelectDialog(R.string.main_dialog_title);
  }

  /**
   * Choose a title then show the dialog.
   *
   * @param titleId Title string's id in xml.
   */
  public void showSelectDialog(int titleId) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    //This dialog cannot be canceled.
    builder.setCancelable(false);
    //Set title.
    builder.setTitle(getResString(titleId));
    //Add a button to select easy level.
    builder.setPositiveButton(getResString(R.string.main_easy),new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog,int whichButton) {
        MainActivity.level = LevelSelector.Level.EASY;
        //In easy level,use buttons to control the roket.
        MainActivity.control = MainController.Control.BTN;
        start(dialog);
      }
    });
    //Add a button to select normal level.
    builder.setNeutralButton(getResString(R.string.main_normal),new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog,int whichButton) {
        MainActivity.level = LevelSelector.Level.NORMAL;
        //In normal level,use buttons to control the roket.
        MainActivity.control = MainController.Control.BTN;
        start(dialog);
      }
    });
    //Add a button to select hard level.
    builder.setNegativeButton(getResString(R.string.main_hard),new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog,int whichButton) {
        MainActivity.level = LevelSelector.Level.HARD;
        //In hard level,use sensor to control the roket.
        MainActivity.control = MainController.Control.SENSOR;
        start(dialog);
      }
    });
    //Build and show.
    builder.create().show();
  }

  /**
   * Get string resource with given id.
   *
   * @param id String's id in xml.
   * @return The string.
   */
  private String getResString(int id) {
    Resources resources = context.getResources();
    return resources.getString(id);
  }

  /**
   * Restart game view,and dismiss this dialog.
   *
   * @param dialog The dialog it self.
   */
  private void start(DialogInterface dialog) {
    dialog.dismiss();
    //Main view do initialize and start thread.
    mainView.initialize();
    mainView.startAnimation();
  }
}
