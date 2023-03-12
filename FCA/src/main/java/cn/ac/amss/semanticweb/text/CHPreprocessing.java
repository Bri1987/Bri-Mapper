package cn.ac.amss.semanticweb.text;

import java.util.HashSet;
import java.util.Set;
import static cn.ac.amss.neartone.nl.HomonymRevisor.fixTone;

public class CHPreprocessing
{
    private static Set<String> stopWords = new HashSet<>();
    private static Set<String> removeWords=new HashSet<>();

    static
    {
        stopWords.add("符");
        stopWords.add("树");

        removeWords.add("！");
        removeWords.add("的");
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

    public static String fixCHString(String words)
    {
        if (words == null || words.isEmpty() ) return words;

        return fixTone(words);
    }

    public static Set<String> removeToken(Set<String> words)
    {
        if (words == null || words.isEmpty() || removeWords == null || removeWords.isEmpty()) return words;

        Set<String> s=new HashSet<>();
        for(String word:words)
        {
            for(String removeWord:removeWords)
            {
                if(word.contains(removeWord))
                {
                    String r_word=word.replaceAll(removeWord,"");
                    s.add(r_word);
                }
                else
                    s.add(word);
            }
        }
        return s;
    }
}
