package names;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static names.Letters.namesThatStartWith;
import static names.Main.FIRST_YEAR_IN_FOLDER;
import static names.Main.LAST_YEAR_IN_FOLDER;
import static names.Main.createSumMap;
import static names.Main.female;
import static names.Main.getYearData;
import static names.Main.male;
import static names.Ranks.averageRankForRecentYears;
import static names.Ranks.averageRankOverTime;
import static names.Ranks.greatestRankDifferenceBetweenYears;
import static names.Ranks.highestAverageRankOverTime;
import static names.Ranks.mostPopularStartingLetterInRange;
import static names.Ranks.nameHoldingRankInRangeLongest;
import static names.Ranks.namesWithRankInRange;
import static names.Ranks.rankDifferenceBetweenYears;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

  @BeforeAll
  static void setupForTests() {
    Main tester = new Main("ssa_2000s", 2000, 2009);
  }

  @Test
  void basic_test_Q1_TopFemaleName() {
    assertEquals(new ArrayList<Integer>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 3, 6)),
        Ranks.nameRankOverTime(FIRST_YEAR_IN_FOLDER, LAST_YEAR_IN_FOLDER, "Emily", female));
  }

  @Test
  void basic_test_Q1_TopMaleName() {
    assertEquals(new ArrayList<Integer>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)),
        Ranks.nameRankOverTime(FIRST_YEAR_IN_FOLDER, LAST_YEAR_IN_FOLDER, "Jacob", male));
  }

  @Test
  void basic_test_Q1_FakeName() {
    assertEquals(new ArrayList<Integer>(List.of(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)),
        Ranks.nameRankOverTime(FIRST_YEAR_IN_FOLDER, LAST_YEAR_IN_FOLDER, "zyacascxkhb", female));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  void basic_test_Q2_RecentYearTopFemaleName() {
    assertEquals("Isabella", Ranks.recentYearNameWithRankEquivalent(2000, "Emily", female));
  }

  @Test
  void basic_test_Q2_RecentYearSecondMaleName() {
    assertEquals("Ethan", Ranks.recentYearNameWithRankEquivalent(2000, "Michael", male));
  }

  @Test
  void basic_test_Q2_FakeName() {
    assertEquals("Name does not exist in this data set",
        Ranks.recentYearNameWithRankEquivalent(2000, "foawfinapiwfni", male));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  void basic_test_Q3_topNameFemale() {
    assertEquals("Emily", Ranks.mostPopularNameInRange(2000, 2005, female));
  }

  @Test
  void basic_test_Q3_topNameMale() {
    assertEquals("Jacob", Ranks.mostPopularNameInRange(2000, 2008, male));
  }

  @Test
  void basic_test_Q3_oneYearRange() {
    assertEquals("Isabella", Ranks.mostPopularNameInRange(2009, 2009, female));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  void basic_test_Q4_topFemaleName() {
    assertEquals(namesThatStartWith(createSumMap(2000, 2003, female, false), "A"),
        mostPopularStartingLetterInRange(2000, 2003, female));

  }

  @Test
  void basic_test_Q4_topMaleName() {
    assertEquals(namesThatStartWith(createSumMap(2000, 2009, male, false), "J"),
        mostPopularStartingLetterInRange(2000, 2009, male));
  }

  @Test
  void basic_test_Q4_oneYearRange() {
    assertEquals(namesThatStartWith(createSumMap(2009, 2009, female, false), "A"),
        mostPopularStartingLetterInRange(2009, 2009, female));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  void complete_test_Q1_TopFemaleName() {
    assertEquals(new ArrayList<Integer>(List.of(1, 1, 3, 6)),
        Ranks.nameRankOverTime(2006, 2009, "Emily", female));
  }

  @Test
  void complete_test_Q1_TopMaleName() {
    assertEquals(new ArrayList<Integer>(List.of(1, 1, 1, 1, 1, 1)),
        Ranks.nameRankOverTime(2000, 2005, "Jacob", male));
  }

  @Test
  void complete_test_Q1_FakeName() {
    assertEquals(new ArrayList<Integer>(List.of(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)),
        Ranks.nameRankOverTime(
            2000, 2009, "zyacascxkhb", female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test
void complete_test_Q2_TopFemaleName() {
  assertEquals(-5, rankDifferenceBetweenYears(2000,2009,"Emily", female));
}

  @Test
  void complete_test_Q2_TopMaleName() {
    assertEquals(0, rankDifferenceBetweenYears(2000,2005,"Jacob", male));
  }

  @Test
  void complete_test_Q2_NotTopName() {
    assertEquals(-7, rankDifferenceBetweenYears(2002,2006,"Sarah", female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test //these tests have high runtime
void complete_test_Q3_LargestFemaleRankChange() {
  assertEquals("Tyeisha", greatestRankDifferenceBetweenYears(2000, 2009, female));
}

  @Test
  void complete_test_Q3_LargestMaleRankChange() {
    assertEquals("Infant", greatestRankDifferenceBetweenYears(2000, 2009, male));
  }

  @Test
  void complete_test_Q3_OneYearRange() {
    assertEquals("", greatestRankDifferenceBetweenYears(2005, 2005, female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test
void complete_test_Q4_mediumRankedFemale() {
  assertEquals((4+4+6+8+8+10)/6.0, averageRankOverTime(2000,2005, "Ashley", female));
}

  @Test
  void complete_test_Q4_topRankedMale() {
    assertEquals(1.0, averageRankOverTime(2000,2009, "Jacob", male));
  }

  @Test
  void complete_test_Q4_OneYearRange() {
    assertEquals(3.0, averageRankOverTime(2007,2007, "Ethan", male));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test
void complete_test_Q5_topRankedFemale() {
  assertEquals("Emily", highestAverageRankOverTime(2000, 2009, female));
}

  @Test
  void complete_test_Q5_topRankedMale() {
    assertEquals("Jacob", highestAverageRankOverTime(2000, 2005, male));
  }

  @Test
  void complete_test_Q5_OneYearRange() {
    assertEquals("Isabella", highestAverageRankOverTime(2009, 2009, female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test
void complete_test_Q6_mediumRankedFemale() {
  assertEquals(4.5, averageRankForRecentYears(2, "Emily", female));
}

  @Test
  void complete_test_Q6_topRankedMale() {
    assertEquals(1, averageRankForRecentYears(8, "Jacob", male));
  }

  @Test
  void complete_test_Q6_OneYearRange() {
    assertEquals(10, averageRankForRecentYears(1, "Mia", female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test
void complete_test_Q7_secondRankedFemale() {
  assertEquals(new ArrayList<String>(List.of("Isabella","Isabella","Emma")), namesWithRankInRange(2007, 2009, 2, female));
}

  @Test
  void complete_test_Q7_topRankedMale() {
    assertEquals(new ArrayList<String>(List.of("Jacob", "Jacob", "Jacob", "Jacob", "Jacob", "Jacob")), namesWithRankInRange(2000, 2005, 1, male));
  }

  @Test
  void complete_test_Q7_OneYearRange() {
    assertEquals(new ArrayList<String>(List.of("Morgan")), namesWithRankInRange(2000, 2000, 25, female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
@Test
void complete_test_Q8_secondRankedFemale() {
  assertEquals("Isabella for 2 years", nameHoldingRankInRangeLongest(2007, 2009, 2, female));
}

  @Test
  void complete_test_Q8_topRankedMale() {
    assertEquals("Jacob for 6 years", nameHoldingRankInRangeLongest(2000, 2005, 1, male));
  }

  @Test
  void complete_test_Q8_OneYearRange() {
    assertEquals("Morgan for 1 years", nameHoldingRankInRangeLongest(2000, 2000, 25, female));
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
}