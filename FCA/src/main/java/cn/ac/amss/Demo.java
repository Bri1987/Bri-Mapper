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
        ModelStorage source = new ModelStorage("FCA/src/test/resources/metadata2/m88.owl");
        ModelStorage target = new ModelStorage("FCA/src/test/resources/metadata2/w88.owl");

//        /************************** Lexical-level Matching ***************************/
//        LexicalMatcher lm = MatcherFactory.createLexicalMatcher();
//
//        lm.setSourceTarget(source, target);
//        lm.setExtractType(true, true);
//
//        Mapping lexicalOntClassMappings = new Mapping();
//        lm.mapOntClasses(lexicalOntClassMappings);
//        System.out.println(lexicalOntClassMappings);
//        System.out.println("________________________________________");

        /************************** Lexical-level Matching ***************************/
        LexicalMatcher lm = MatcherFactory.createLexicalMatcher();

        lm.setSourceTarget(source, target);
        lm.setExtractType(true, true);

        Mapping lexicalOntIndividualMappings = new Mapping();
        lm.mapInstances(lexicalOntIndividualMappings);
        System.out.println(lexicalOntIndividualMappings);
        System.out.println("________________________________________");

        /************************* Structural-level Matching *************************/
        StructuralMatcher sm = MatcherFactory.createStructuralMatcher();
        sm.setSourceTarget(source, target);
        sm.setExtractType(true, true);
        sm.addCommonPredicate(RDFS.subClassOf);
        sm.addCommonPredicate(OWL.disjointWith);
        sm.addAllSubjectAnchors(lexicalOntIndividualMappings);
        sm.addAllObjectAnchors(lexicalOntIndividualMappings);

        Mapping structuralOntClassMappings = new Mapping();
        sm.mapOntClasses(structuralOntClassMappings);
        System.out.println(structuralOntClassMappings);

        source.clear();
        target.clear();

        return lexicalOntIndividualMappings;
    }

    public static void main(String[] args) {
        ModelStorage source = new ModelStorage("FCA/src/test/resources/metadata2/m88.owl");
        ModelStorage target = new ModelStorage("FCA/src/test/resources/metadata2/w88.owl");
        map_lexi();

//        /************************** Lexical-level Matching ***************************/
//        LexicalMatcher lm = MatcherFactory.createLexicalMatcher();
//
//        lm.setSourceTarget(source, target);
//        lm.setExtractType(true, true);
//
//        Mapping lexicalOntClassMappings = new Mapping();
//        lm.mapOntClasses(lexicalOntClassMappings);
//        System.out.println(lexicalOntClassMappings);
//
//        Mapping lexicalObjectPropertyMappings = new Mapping();
//        lm.mapObjectProperties(lexicalObjectPropertyMappings);
//        System.out.println(lexicalObjectPropertyMappings);

//        /************************* Structural-level Matching *************************/
//        StructuralMatcher sm = MatcherFactory.createStructuralMatcher();
//        sm.setSourceTarget(source, target);
//        sm.setExtractType(true, true);
//        sm.addCommonPredicate(RDFS.subClassOf);
//        sm.addCommonPredicate(OWL.disjointWith);
//        sm.addAllSubjectAnchors(lexicalOntClassMappings);
//        sm.addAllObjectAnchors(lexicalOntClassMappings);
//
//        Mapping structuralOntClassMappings = new Mapping();
//        sm.mapOntClasses(structuralOntClassMappings);
//        System.out.println(structuralOntClassMappings);
//
//        source.clear();
//        target.clear();
    }
}