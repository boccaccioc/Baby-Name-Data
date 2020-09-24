package names;

import static names.Letters.namesThatStartWith;
import static names.Letters.numBabiesStartWith;
import static names.Main.FIRST_YEAR_IN_FOLDER;
import static names.Main.LAST_YEAR_IN_FOLDER;
import static names.Main.createSumMap;
import static names.Main.getYearData;
import static names.Main.getYearRankData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Ranks {

  /**
   * Solution to Complete: Question 1, Given a name, gender, and range of years, what are the
   * rankings of that name/gender pair within the range?
   * <p>
   * Solution to Basic: Question 1, Given a name and a gender, what are all rankings of that
   * name/gender pair in the data set?
   *
   * @param startYear first year to look at in range
   * @param endYear   last year in the range
   * @param name      name we are looking for the ranks of
   * @param gender    gender of the name
   * @return list of all the rankings of the name/gender pair over years specified by params,
   * ranking is -1 in case that the name does not appear in a given year
   */
  public static List<Integer> nameRankOverTime(int startYear, int endYear, String name,
      String gender) {
    List<Integer> rankHistory = new ArrayList<>();
    for (int i = startYear; i <= endYear; i++) {
      rankHistory.add(getNameRanking(i, name, gender));
    }
    return rankHistory;
  }

  /**
   * gives the ranking of a name
   *
   * @param year   year of data to look at
   * @param name   name we are looking for the rank of
   * @param gender gender of the name
   * @return rank of param name in the data for the year and gender
   */
  public static int getNameRanking(int year, String name, String gender) {
    Map<String, Integer> data = Main.getYearData(year, gender);
    int ranking = 1;
    try {
      int freqToBeat = data.get(name);
      for (Map.Entry<String, Integer> entry : data.entrySet()) {
        if (entry.getValue() > freqToBeat) {
          ranking++;
        }
      }
    } catch (Exception e) {
      System.out.println("The name " + name + " does not exist in year " + year + " data.");
      return -1;
    }
    return ranking;
  }

  /**
   * Solution to Basic: Question 2, Given a name, a gender, and a year, what name/gender pair has
   * the same rank as that name in the most recent year in the data set?
   *
   * @param name   name we are looking for the ranks of
   * @param gender gender of the name
   * @param year   year of name we are looking at
   * @return gives the name in the most recent year that has the same rank as the name with the
   * parameters rank
   */
  public static String recentYearNameWithRankEquivalent(int year, String name, String gender) {
    return getNameWithRank(Main.getYearData(LAST_YEAR_IN_FOLDER, gender),
        getNameRanking(year, name, gender)); //last year in folder is most recent year
  }

  private static String getNameWithRank(Map<String, Integer> data, int desiredRank) {
    if (desiredRank < 0) {
      return "Name does not exist in this data set";
    }
    int CurrRank = 1;
    String CurrName = "";
    try {
      Map<String, Integer> copy = new HashMap<>(data);
      CurrName = topRankedName(copy);
      while (CurrRank < desiredRank) {
        copy.remove(topRankedName(
            copy)); //continues to remove the top ranked name until we reach desired rank
        CurrName = topRankedName(copy);
        CurrRank++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CurrName;
  }

  private static String topRankedName(Map<String, Integer> data) {
    String currLeader = "";
    int leaderFreq = 0;
    for (Map.Entry<String, Integer> entry : data.entrySet()) {
      if (entry.getValue() > leaderFreq) {
        currLeader = entry.getKey();
        leaderFreq = entry.getValue();
      }
    }
    return currLeader;
  }

  /**
   * Solution to Basic: Question 3, Given a range of years (start and end inclusive) and a gender,
   * what name(s) were ranked as the years' most popular name for that gender the most often and how
   * many years did they have the top rank?
   *
   * @param startYear first year of range
   * @param endYear   last year of range
   * @param gender    gender that we are looking for most popular name
   * @return name with the most popularity within a range of years
   */
  public static String mostPopularNameInRange(int startYear, int endYear, String gender) {
    Map<String, Integer> sumMap = createSumMap(startYear, endYear, gender, false);
    return topRankedName(sumMap);
  }

  /**
   * Solution to Basic: Question 4, Given a range of years (start and end inclusive), what is the
   * most popular letter that girls? names started with?
   *
   * @param startYear first year of range
   * @param endYear   last year of range
   * @param gender    gender that we are looking for most popular name
   * @return alphabetized list of all girls' names that start with this letter, in the case of a
   * tie, return only the alphabetically first letters
   */
  public static List<String> mostPopularStartingLetterInRange(int startYear, int endYear,
      String gender) {
    Map<String, Integer> sumMap = createSumMap(startYear, endYear, gender, false);
    Map<String, Integer> letterMap = new HashMap<>();
    for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
      String s = Character.toString(alphabet);
      letterMap.put(s, numBabiesStartWith(sumMap, s));
    }
    String letter = topRankedName(
        letterMap); //not using topRankedName to for intended purpose, instead used to find highest freq letter
    int winningFreq = letterMap.get(letter);//getRankFreq(letterMap, 1);
    if (winningFreq != letterMap.get(
        getNameWithRank(letterMap, 2))) {//if(winningFreq != getRankFreq(letterMap, 2)){
      return namesThatStartWith(sumMap, letter);
    }
    List<String> tieLetters = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : letterMap.entrySet()) {
      if (entry.getValue() == winningFreq) {
        tieLetters.add(entry.getKey().toUpperCase());
      }
    }
    return tieLetters;
  }

  /**
   * Solution to Complete: Question 2, Given a range of years, a name, and a gender, what is the
   * difference in that name/gender pair's ranking from only the first and the last years of the
   * range?
   *
   * @param startYear first year to look at in range
   * @param endYear   last year in the range
   * @param name      name we are looking for the ranks of
   * @param gender    gender of the name
   * @return rank of the name at the starting year minus the rank at the end year
   */
  public static int rankDifferenceBetweenYears(int startYear, int endYear, String name,
      String gender) {
    return getNameRanking(startYear, name, gender) - getNameRanking(endYear, name, gender);
  }

  /**
   * Solution to Complete: Question 3, Given a range of years, and a gender, what name's rank
   * changed the most (up or down) from the only first and the last years of the range? HIGH
   * RUNTIME!
   *
   * @param startYear first year to look at in range
   * @param endYear   last year in the range
   * @param gender    gender of the name
   * @return name that had the greatest change in rank from start to end year
   */
  public static String greatestRankDifferenceBetweenYears(int startYear, int endYear,
      String gender) {
    int maxChange = 0;
    String maxChangeName = "";
    Map<String, Integer> startYearRankMap = getYearRankData(startYear, gender);
    Map<String, Integer> endYearRankMap = getYearRankData(endYear, gender);
    for (Map.Entry<String, Integer> entry : startYearRankMap.entrySet()) {
      if (endYearRankMap.containsKey(entry.getKey())) {
        int rankDifference = Math.abs(entry.getValue() - endYearRankMap
            .get(entry.getKey())); //absolute value to get largest magnitude of rank change
        if (rankDifference > maxChange) {
          maxChange = rankDifference;
          maxChangeName = entry.getKey();
        }
      }
    }
    return maxChangeName;
  }

  /**
   * Solution to Complete: Question 4, Given a name, a gender, and a range of years, what is its
   * average rank during that time?
   *
   * @param startYear first year to look at in range
   * @param endYear   last year in the range
   * @param name      name we are finding ranks of
   * @param gender    gender of the name
   * @return average rank of name over the range of years
   */
  public static double averageRankOverTime(int startYear, int endYear, String name, String gender) {
    double rankSum = 0;
    for (int i = startYear; i <= endYear; i++) {
      rankSum += getNameRanking(i, name, gender);
    }
    return (rankSum) / (Math.abs(endYear - startYear + 1)); //plus one because years are inclusive
  }

  /**
   * Solution to Complete: Question 5, Given a range of years, and a gender, what name had the
   * highest average rank during that time?
   *
   * @param startYear first year to look at in range
   * @param endYear   last year in the range
   * @param gender    gender of the name
   * @return name that had the highest average rank over the range of years
   */
  public static String highestAverageRankOverTime(int startYear, int endYear, String gender) {
    Map<String, Integer> rankSumMap = createSumMap(startYear, endYear, gender, true);
    int bestRankSum = Integer.MAX_VALUE;
    String bestRankSumName = "";
    for (Map.Entry<String, Integer> entry : rankSumMap.entrySet()) {
      if (entry.getValue() < bestRankSum) {
        bestRankSum = entry.getValue();
        bestRankSumName = entry.getKey();
      }
    }
    return bestRankSumName;
  }

  /**
   * Solution to Complete: Question 6, Given a name, a gender, and a number of years, what is its
   * average rank for the most recent number of years?
   *
   * @param numYears how many recent years that will be looked at
   * @param name     name we are finding average rank of
   * @param gender   gender of the name
   * @return average rank of name over the past numYears years
   */
  public static double averageRankForRecentYears(int numYears, String name, String gender) {
    return averageRankOverTime((LAST_YEAR_IN_FOLDER - numYears + 1), LAST_YEAR_IN_FOLDER, name,
        gender); //plus one because years are inclusive and it would otherwise include an extra year
  }

  /**
   * Solution to Complete: Question 7,   Given a range of years, a gender, and a rank, what name(s)
   * held that rank at each year during the range?
   *
   * @param startYear first year in range
   * @param endYear   last year in range
   * @param rank      rank we are finding names with
   * @param gender    gender of the name
   * @return list of names that had the given rank within the range
   */
  public static List<String> namesWithRankInRange(int startYear, int endYear, int rank,
      String gender) {
    List<String> namesWithRank = new ArrayList<>();
    for (int i = startYear; i <= endYear; i++) {
      namesWithRank.add(getNameWithRank(getYearData(i, gender), rank));
    }
    return namesWithRank;
  }

  /**
   * Solution to Complete: Question 8, Given a range of years, a gender, and a rank, what name(s)
   * held that rank at each year during the range?
   *
   * @param startYear first year in range
   * @param endYear   last year in range
   * @param rank      rank we are finding names with
   * @param gender    gender of the name
   * @return String with format "(name holding rank longest) for (number years the name held rank)
   * years" ex. "Bob for 3 years"
   */
  public static String nameHoldingRankInRangeLongest(int startYear, int endYear, int rank,
      String gender) {
    List<String> namesWithRank = namesWithRankInRange(startYear, endYear, rank, gender);
    Set<String> uniqueNames = new HashSet<String>(namesWithRank);
    int mostYears = 0;
    String mostYearsName = "";
    for (String name : uniqueNames) {
      if (Collections.frequency(namesWithRank, name) > mostYears) {
        mostYears = Collections.frequency(namesWithRank, name);
        mostYearsName = name;
      }
    }
    return mostYearsName + " for " + Collections.frequency(namesWithRank, mostYearsName) + " years";
  }

}

