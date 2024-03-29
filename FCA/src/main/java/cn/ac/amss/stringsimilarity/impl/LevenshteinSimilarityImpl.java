
package cn.ac.amss.stringsimilarity.impl;

import cn.ac.amss.stringsimilarity.StringSimilarity;

public class LevenshteinSimilarityImpl implements StringSimilarity
{
  public LevenshteinSimilarityImpl() {}

  private final LevenshteinDistanceImpl ld = new LevenshteinDistanceImpl();

  public final double similarity(String s1, String s2) {
    if (null == s1 || null == s2) return .0;
    return 1.0 - (ld.distance(s1, s2) / (s1.length() + s2.length()));
  }
}
