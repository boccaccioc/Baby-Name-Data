package names;


import static names.Ranks.averageRankForRecentYears;
import static names.Ranks.averageRankOverTime;
import static names.Ranks.getNameRanking;
import static names.Ranks.greatestRankDifferenceBetweenYears;
import static names.Ranks.highestAverageRankOverTime;
import static names.Ranks.mostPopularNameInRange;
import static names.Ranks.mostPopularStartingLetterInRange;
import static names.Ranks.nameHoldingRankInRangeLongest;
import static names.Ranks.nameRankOverTime;
import static names.Ranks.namesWithRankInRange;
import static names.Ranks.rankDifferenceBetweenYears;
import static names.Ranks.recentYearNameWithRankEquivalent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

  private static String FILE_PATH; //Folder that contains the data set
  public static int FIRST_YEAR_IN_FOLDER; //specify the first year of data that the folder FILE_PATH contains
  public static int LAST_YEAR_IN_FOLDER; //last year of data in FILE_PATH
  private static Map<Integer, Map<String, Integer>> multiYearMapMale;
  private static Map<Integer, Map<String, Integer>> multiYearMapFemale;
  private static Map<Integer, Map<String, Integer>> multiYearRankMapMale;
  private static Map<Integer, Map<String, Integer>> multiYearRankMapFemale;
  public static final String male = "M";
  public static final String female = "F";

  public Main(String path, int firstYear, int lastYear) {
    FILE_PATH = path;
    FIRST_YEAR_IN_FOLDER = firstYear;
    LAST_YEAR_IN_FOLDER = lastYear;
    Map[] multiYearMapsMale = createMultiYearMap(male);
    Map[] multiYearMapsFemale = createMultiYearMap(female);
    multiYearMapMale = multiYearMapsMale[0];
    multiYearMapFemale = multiYearMapsFemale[0];
    ;
    multiYearRankMapMale = multiYearMapsMale[1];
    multiYearRankMapFemale = multiYearMapsFemale[1];
  }

  public static void main(String[] args) {
    Main completeDataSet = new Main("ssa_complete", 1880, 2018);
    System.out.println(
        "BASIC Q1 : " + nameRankOverTime(FIRST_YEAR_IN_FOLDER, LAST_YEAR_IN_FOLDER, "Mary",
            female));
    System.out.println("BASIC Q2 : " + recentYearNameWithRankEquivalent(1900, "Mary", female));
    System.out.println("BASIC Q3 : " + mostPopularNameInRange(1880, 1925, female));
    System.out.println("BASIC Q4 : " + mostPopularStartingLetterInRange(1917, 1955, male));
    System.out.println("COMPLETE Q1 : " + nameRankOverTime(1982, 1990, "Bob", male));
    System.out.println("COMPLETE Q2 : " + rankDifferenceBetweenYears(2000, 2015, "Emily", female));
    System.out.println("COMPLETE Q3 : " + greatestRankDifferenceBetweenYears(1900, 1905, female));
    System.out.println("COMPLETE Q4 : " + averageRankOverTime(1901, 1964, "John", male));
    System.out.println("COMPLETE Q5 : " + highestAverageRankOverTime(1999, 2002, male));
    System.out.println("COMPLETE Q6 : " + averageRankForRecentYears(32, "Colin", male));
    System.out.println("COMPLETE Q7 : " + namesWithRankInRange(1944, 1955, 6, female));
    System.out.println("COMPLETE Q8 : " + nameHoldingRankInRangeLongest(1900, 1970, 4, female));

  }

  /**
   * returns the data for a given year and gender
   *
   * @param year   year to return data for
   * @param gender to return the data for
   * @return data of for the given year and gender params
   */
  public static Map<String, Integer> getYearData(int year, String gender) {
    if (gender.equals(male)) {
      return multiYearMapMale.get(year);
    }
    return multiYearMapFemale.get(year);
  }

  //creates map for a given gender from data in file
  private static Map<String, Integer>[] createMap(String file, String gend) {
    Map<String, Integer> baseMap = new HashMap<>();
    Map<String, Integer> rankMap = new HashMap<>();
    gend = gend.toUpperCase();
    int rank = 0;
    int rankIgnoreTies = 0; //this is to keep track of what rank we are on when setting a rank after there was a tie
    boolean tie = false;
    int previousFreq = Integer.MAX_VALUE;
    try {
      Path path = Paths.get(Main.class.getClassLoader().getResource(file).toURI());
      for (String line : Files.readAllLines(path)) {
        String[] lineArray = line.split(",");
        String name = lineArray[0];
        String gender = lineArray[1];
        String freq = lineArray[2];
        if (gender.equals(gend)) {
          baseMap.put(name, Integer.valueOf(freq));
          if (Integer.valueOf(freq)
              == previousFreq) { //Checks previous value to see if it is a tie, in which case rank is not added to
            tie = true;
          } else if (tie == tie
              && Integer.valueOf(freq) < previousFreq) { //this condition is when a tie is ending
            rank = rankIgnoreTies;
            tie = false;
          } else {
            rank++;
          }
          rankMap.put(name, rank);
          rankIgnoreTies++; //always increments regardless of above conditionals
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Map<String, Integer>[] baseAndRankMap = new Map[]{baseMap, rankMap};
    return baseAndRankMap;
  }

  /**
   * creates a map that sums together all occurrences of names across the year range
   *
   * @param startYear first year of range
   * @param endYear   last year of range
   * @param gender    gender we are creating map for
   * @param ranks     creates a rank sum map if true
   * @return map with sum of name occurences, keys are names, values are occurences, or ranks
   * instead of occurences if ranks
   */
  public static Map<String, Integer> createSumMap(int startYear, int endYear, String gender,
      boolean ranks) {
    Map<Integer, Map<String, Integer>> multiYearMap;
    if (ranks) {
      multiYearMap = gender.equals(female) ? multiYearRankMapFemale : multiYearRankMapMale;
      /*multiYearMap = multiYearRankMapMale;
      if (gender.equals(female)) {
        multiYearMap = multiYearRankMapFemale;
      }*/
    } else {
      multiYearMap = gender.equals(female) ? multiYearMapFemale : multiYearMapMale;
      /*multiYearMap = multiYearMapMale;
      if (gender.equals(female)) {
        multiYearMap = multiYearMapFemale;
      }*/
    }
    Map<String, Integer> sumMap = new HashMap<>();
    for (int i = startYear; i <= endYear; i++) {
      for (Map.Entry<String, Integer> entry : multiYearMap.get(i).entrySet()) {
        sumMap.put(entry.getKey(), sumMap.getOrDefault(entry.getKey(), 0) + entry.getValue());
        /*if (sumMap.containsKey(entry.getKey())) {
          sumMap.put(entry.getKey(), sumMap.get(entry.getKey()) + entry
              .getValue()); //summing the data over the range of years
        } else {
          sumMap.put(entry.getKey(), entry.getValue());
        }*/
      }
    }
    return sumMap;
  }

  /**
   * creates a map where the keys are years and the values are the maps created by createMap
   *
   * @param gender to create the map for
   * @return map of maps for every year in the data set specified in the instance variables
   * @throws throws exception if the file does not exist
   */
  private static Map<Integer, Map<String, Integer>>[] createMultiYearMap(String gender) {
    Map<Integer, Map<String, Integer>> multiYearMap = new HashMap<>();
    Map<Integer, Map<String, Integer>> multiYearRankMap = new HashMap<>();
    for (int i = FIRST_YEAR_IN_FOLDER; i <= LAST_YEAR_IN_FOLDER; i++) {
      String fileName = FILE_PATH + "/yob" + i + ".txt"; //format of text files
      try {
        Map<String, Integer>[] baseAndRankMap = createMap(fileName, gender);
        multiYearMap.put(i, baseAndRankMap[0]);
        multiYearRankMap.put(i, baseAndRankMap[1]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    Map<Integer, Map<String, Integer>>[] multiYearBaseAndRankMap = new Map[]{multiYearMap,
        //array so it can effectively have two outputs
        multiYearRankMap};
    return multiYearBaseAndRankMap;
  }

  /**
   * returns the rank data for a given year and gender
   *
   * @param year   year to return rank data for
   * @param gender to return the rank data for
   * @return data of ranks for the given year and gender params
   */
  public static Map<String, Integer> getYearRankData(int year, String gender) {
    if (gender.equals(male)) {
      return multiYearRankMapMale.get(year);
    }
    return multiYearRankMapFemale.get(year);
  }
}
