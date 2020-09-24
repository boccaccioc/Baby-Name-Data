package names;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Letters {

  public static int numBabiesStartWith(Map<String, Integer> data, String letter) {
    letter = letter.toUpperCase();
    int babyCount = 0;
    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      if (entry.getKey().substring(0, 1).equals(letter)) {
        babyCount += entry.getValue();
      }
    }
    return babyCount;
  }

  /**
   * Gets all names that start with a letter in data
   *
   * @param data   data to be looked through
   * @param letter letter that we are looking for names that start with it
   * @return letter with the most popularity within a range of years
   */
  public static List<String> namesThatStartWith(Map<String, Integer> data, String letter) {
    List<String> ret = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      if (entry.getKey().substring(0, 1).equals(letter.toUpperCase())) {
        ret.add(entry.getKey());
      }
    }
    Collections.sort(ret);
    return ret;
  }
}
