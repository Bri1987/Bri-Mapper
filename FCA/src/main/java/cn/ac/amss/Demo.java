package cn.ac.amss; /****************************************
 *                                      *
 *    file:///src/main/java/cn.ac.amss.Demo.java   *
 *                                      *
 ****************************************/

import cn.ac.amss.semanticweb.alignment.Mapping;
import cn.ac.amss.semanticweb.model.ModelStorage;
import cn.ac.amss.semanticweb.matching.MatcherFactory;
import cn.ac.amss.semanticweb.matching.LexicalMatcher;
import cn.ac.amss.semanticweb.matching.StructuralMatcher;

import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;

public class Demo
{
    public static Mapping map_lexi()
    {
        ModelStorage source = new ModelStorage("FCA/src/test/resources/metadata4/metadata4.owl");
        ModelStorage target = new ModelStorage("FCA/src/test/resources/metadata4/wrong4.owl");

        /************************** Lexical-level Matching ***************************/
        LexicalMatcher lm = MatcherFactory.createLexicalMatcher();

        lm.setSourceTarget(source, target);
        lm.setExtractType(true, true);

        Mapping lexicalOntClassMappings = new Mapping();
        lm.mapOntClasses(lexicalOntClassMappings);
        System.out.println(lexicalOntClassMappings);

        return lexicalOntClassMappings;
    }

    public static void main(String[] args) {
        ModelStorage source = new ModelStorage("FCA/src/test/resources/metadata4/metadata4.owl");
        ModelStorage target = new ModelStorage("FCA/src/test/resources/metadata4/wrong4.owl");

        /************************** Lexical-level Matching ***************************/
        LexicalMatcher lm = MatcherFactory.createLexicalMatcher();

        lm.setSourceTarget(source, target);
        lm.setExtractType(true, true);

        Mapping lexicalOntClassMappings = new Mapping();
        lm.mapOntClasses(lexicalOntClassMappings);
        System.out.println(lexicalOntClassMappings);

        Mapping lexicalObjectPropertyMappings = new Mapping();
        lm.mapObjectProperties(lexicalObjectPropertyMappings);
        System.out.println(lexicalObjectPropertyMappings);

        /************************* Structural-level Matching *************************/
        StructuralMatcher sm = MatcherFactory.createStructuralMatcher();
        sm.setSourceTarget(source, target);
        sm.setExtractType(true, true);
        sm.addCommonPredicate(RDFS.subClassOf);
        sm.addCommonPredicate(OWL.disjointWith);
        sm.addAllSubjectAnchors(lexicalOntClassMappings);
        sm.addAllObjectAnchors(lexicalOntClassMappings);

        Mapping structuralOntClassMappings = new Mapping();
        sm.mapOntClasses(structuralOntClassMappings);
        System.out.println(structuralOntClassMappings);

        source.clear();
        target.clear();
    }
}