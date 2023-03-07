package cn.ac.amss.stringsimilarity;

import cn.ac.amss.stringsimilarity.impl.LevenshteinDistanceImpl;
import cn.ac.amss.stringsimilarity.impl.LevenshteinSimilarityImpl;


public class StringMetricFactory
{
  private StringMetricFactory() {}

  public static StringDistance createLevenshteinDistance() {
    return new LevenshteinDistanceImpl();
  }

  public static StringSimilarity createLevenshteinSimilarity() {
    return new LevenshteinSimilarityImpl();
  }
}

