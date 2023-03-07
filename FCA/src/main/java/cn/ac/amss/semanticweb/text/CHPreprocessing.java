package cn.ac.amss.semanticweb.text;

import java.util.HashSet;
import java.util.Set;

public class CHPreprocessing
{
    private static Set<String> stopWords = new HashSet<>();

    static
    {
        stopWords.add("符");
    }


    public static Set<String> removeCHStopWords(Set<String> words) {
        if (words == null || words.isEmpty() || stopWords == null || stopWords.isEmpty()) return words;

        Set<String> s=new HashSet<>();
        for(String word:words)
        {
            for(String stepWord:stopWords)
            {
                if(word.contains(stepWord))
                {
                    String r_word=word.replaceAll(stepWord,"");
                    s.add(r_word);
                }
                else
                    s.add(word);
            }
        }
        return s;
    }
}
