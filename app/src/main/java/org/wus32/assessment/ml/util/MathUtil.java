package org.wus32.assessment.ml.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * MartianLander
 * <p>
 * Created by Wu Shuang on 2016/9/20.
 * A tool used to get special numbers.
 */
public final class MathUtil {

  /**
   * Get a random number in the half-open range [0, seed).
   *
   * @param seed The max number will be seed-1.
   * @return A random number in the half-open range [0, seed).
   */
  public static int random(int seed) {
    return new Random().nextInt(seed);
  }

  /**
   * Get a random number in the range (min,max).
   *
   * @param min From this number.
   * @param max To this number.
   * @return A random number in the range (min,max).
   */
  public static int random(int min,int max) {
    Random random = new Random();
    return random.nextInt(max) % (max - min + 1) + min;
  }

  /**
   * Get sevral unique number in in the half-open range [0, seed).
   *
   * @param seed The max number will be seed-1.
   * @param size How many number.
   * @return A list of unique random numbers.
   */
  public static List<Integer> getUniqueNums(int seed,int size) {
    //If the size lager than seed,a runtime exception will happen.
    if (size > seed) {
      throw new RuntimeException("Size cannot be larger than seed!");
    }
    Set<Integer> nums = new HashSet<>();
    List<Integer> list = new ArrayList<>(size);
    do {
      int x = random(seed);
      nums.add(x);
    } while (nums.size() < size);
    for (int i : nums) {
      list.add(i);
    }
    //If need sort the list.
//    Collections.sort(list);
    return list;
  }
}
