package org.wus32.assessment.ml.util;

import org.wus32.assessment.ml.R;
import org.wus32.assessment.ml.activity.MainActivity;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/21.
 * A tool which load the int value based on the difficulty level.
 */
public class LevelSelector {

  /**
   * Which level the player chosen.
   */
  private Level level;

  /**
   * Resource loader.
   */
  private ResLoader resLoader;

  public LevelSelector(ResLoader resLoader){
    this.resLoader = resLoader;
    //The level is a global variable,defined in MainActivity.
    this.level = MainActivity.level;
  }

  /**
   * Get the number of terrain's section.
   * @return The number of terrain's section based on the difficulty level.
   */
  public int getSections() {
    int sectionsId;
    switch (level) {
      case EASY:
        sectionsId = R.integer.terrain_sections_e;
        break;
      case NORMAL:
        sectionsId = R.integer.terrain_sections_n;
        break;
      case HARD:
        sectionsId = R.integer.terrain_sections_h;
        break;
      default:
        sectionsId = R.integer.terrain_sections_n;
    }
    return resLoader.loadIntRes(sectionsId);
  }

  /**
   * Get the number of terrain's flat section(rocket can land on it safely).
   * @return The number of terrain's flat section based on the difficulty level.
   */
  public int getFlatNum() {
    int flatNumId;
    switch (level) {
      case EASY:
        flatNumId = R.integer.terrain_flat_e;
        break;
      case NORMAL:
        flatNumId = R.integer.terrain_flat_n;
        break;
      case HARD:
        flatNumId = R.integer.terrain_flat_h;
        break;
      default:
        flatNumId = R.integer.terrain_flat_n;
    }
    return resLoader.loadIntRes(flatNumId);
  }

  /**
   * Get the min height of terrain.
   * @return The min height of terrain based on the difficulty level.
   */
  public int getMinHeight() {
    int minHeightId;
    switch (level) {
      case EASY:
        minHeightId = R.integer.terrian_min_height_e;
        break;
      case NORMAL:
        minHeightId = R.integer.terrian_min_height_n;
        break;
      case HARD:
        minHeightId = R.integer.terrian_min_height_h;
        break;
      default:
        minHeightId = R.integer.terrian_min_height_n;
    }
    return resLoader.loadIntRes(minHeightId);
  }

  /**
   * Get the max height of terrain.
   * @return The max height of terrain based on the difficulty level.
   */
  public int getMaxHeight() {
    int maxHeightId;
    switch (level) {
      case EASY:
        maxHeightId = R.integer.terrian_max_height_e;
        break;
      case NORMAL:
        maxHeightId = R.integer.terrian_max_height_n;
        break;
      case HARD:
        maxHeightId = R.integer.terrian_max_height_h;
        break;
      default:
        maxHeightId = R.integer.terrian_max_height_n;
    }
    return resLoader.loadIntRes(maxHeightId);
  }

  /**
   * Get the max amount of fuel.
   * @return The max amount of fuel based on the difficulty level.
   */
  public int getMaxFuel() {
    int fuelId;
    switch (level) {
      case EASY:
        fuelId = R.integer.max_fuel_e;
        break;
      case NORMAL:
        fuelId = R.integer.max_fuel_n;
        break;
      case HARD:
        fuelId = R.integer.max_fuel_h;
        break;
      default:
        fuelId = R.integer.max_fuel_n;
    }
    return resLoader.loadIntRes(fuelId);
  }

  /**
   * Get the gravity.
   * @return The gravity based on the difficulty level.
   */
  public float getGravity() {
    int gravityId;
    switch (level) {
      case EASY:
        gravityId = R.integer.rocket_gravity_e;
        break;
      case NORMAL:
        gravityId = R.integer.rocket_gravity_n;
        break;
      case HARD:
        gravityId = R.integer.rocket_gravity_h;
        break;
      default:
        gravityId = R.integer.rocket_gravity_n;
    }
    //Gravity must not be too large.
    return resLoader.loadIntRes(gravityId) / 100f;
  }

  /**
   * Define three difficulty level.
   */
  public enum Level {
    EASY,NORMAL,HARD
  }
}
