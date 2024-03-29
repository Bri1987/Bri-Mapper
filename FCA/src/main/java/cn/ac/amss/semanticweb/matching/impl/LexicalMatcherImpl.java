package cn.ac.amss.semanticweb.matching.impl;

import cn.ac.amss.semanticweb.alignment.Mapping;
import cn.ac.amss.semanticweb.constant.MatchingSpec.MatchType;
import cn.ac.amss.semanticweb.constant.MatchingSpec.Owner;
import cn.ac.amss.semanticweb.fca.Context;
import cn.ac.amss.semanticweb.fca.FCABuilder;
import cn.ac.amss.semanticweb.matching.LexicalMatcher;
import cn.ac.amss.semanticweb.model.PlainRDFNode;
import cn.ac.amss.semanticweb.model.ModelStorage;
import cn.ac.amss.semanticweb.text.CHPreprocessing;
import cn.ac.amss.semanticweb.text.Preprocessing;
import cn.ac.amss.semanticweb.util.AbstractTable;
import cn.ac.amss.semanticweb.vocabulary.DBkWik;
import cn.ac.amss.stringsimilarity.StringMetricFactory;
import cn.ac.amss.stringsimilarity.StringSimilarity;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The implement of lexical matcher based on formal concept analysis.
 */
public class LexicalMatcherImpl extends AbstractMatcherByFCA implements LexicalMatcher
{
  private final static Logger logger       = LogManager.getLogger(LexicalMatcherImpl.class.getName());
  private final static StringSimilarity ss = StringMetricFactory.createLevenshteinSimilarity();

  private class LookupTable extends AbstractTable<String, PlainRDFNode> {
    public LookupTable() { super(); }
  }

  public LexicalMatcherImpl() {
    super();

    isEnabledGSH     = true;
    isEnabledLattice = true;

    lowerBoundOfGSHObjectsSize = 1;
    upperBoundOfGSHObjectsSize = -1;

    lowerBoundOfGSHAttributesSize = 0;
    upperBoundOfGSHAttributesSize = -1;

    lowerBoundOfLatticeObjectsSize = 2;
    upperBoundOfLatticeObjectsSize = 2;

    lowerBoundOfLatticeAttributesSize = 1;
    upperBoundOfLatticeAttributesSize = -1;

    maximumSizeOfConcepts = 300_000;

    try {
      Preprocessing.defaultInit();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }

  public void mapInstances(Mapping mappings) {
    if (logger.isInfoEnabled()) {
      logger.info("<<<<<<< Start matching instance >>>>>>>");
    }
    mapResources(MatchType.INSTANCE, mappings);
  }

  public void mapCategories(Mapping mappings) {
    if (logger.isInfoEnabled()) {
      logger.info("<<<<<<< Start matching category >>>>>>>");
    }
    mapResources(MatchType.CATEGORY, mappings);
  }

  public void mapOntProperties(Mapping mappings) {
    if (logger.isInfoEnabled()) {
      logger.info("<<<<<<< Start matching property >>>>>>>");
    }
    mapResources(MatchType.ONT_PROPERTY, mappings);
  }

  public void mapDataTypeProperties(Mapping mappings) {
    if (logger.isInfoEnabled()) {
      logger.info("<<<<<<< Start matching data property >>>>>>>");
    }
    mapResources(MatchType.DATA_TYPE_PROPERTY, mappings);
  }

  public void mapObjectProperties(Mapping mappings) {
    if (logger.isInfoEnabled()) {
      logger.info("<<<<<<< Start matching object property >>>>>>>");
    }
    mapResources(MatchType.OBJECT_PROPERTY, mappings);
  }

  public void mapOntClasses(Mapping mappings) {
    if (logger.isInfoEnabled()) {
      logger.info("<<<<<<< Start matching class >>>>>>>");
    }
    mapResources(MatchType.ONT_CLASS, mappings);
  }

  private void mapResources(MatchType type, Mapping mappings) {
    if (null == this.source || null == this.target || null == mappings) return;

    LookupTable labelOrName2PlainRDFNodes = new LookupTable();
    constructLookupTable(type, labelOrName2PlainRDFNodes);

    Context<String, String> labelOrName2tokensContext = constructTokenBasedContext(labelOrName2PlainRDFNodes.keySet());

    FCABuilder<String, String> fca = new FCABuilder<>();

    if (logger.isInfoEnabled()) {
      logger.info("Init Formal Concept Analysis Builder...");
    }
    fca.init(labelOrName2tokensContext);

    if (logger.isInfoEnabled()) {
      logger.info("Start formal concept analysis...");
    }
    fca.exec();

    if (isEnabledGSH) {
      if (logger.isInfoEnabled()) {
        logger.info(
          String.format("Start getting simplified concepts (Size of object: [%d, %d], Size of attribute: [%d, %d])...",
            lowerBoundOfGSHObjectsSize, upperBoundOfGSHObjectsSize,
            lowerBoundOfGSHAttributesSize, upperBoundOfGSHAttributesSize
          )
        );
      }
      Set<Set<String>> simplifiedExtents
        = fca.listSimplifiedExtents(lowerBoundOfGSHObjectsSize, upperBoundOfGSHObjectsSize,
                                    lowerBoundOfGSHAttributesSize, upperBoundOfGSHAttributesSize);

      if (logger.isInfoEnabled()) {
        logger.info("Finish getting simplified concepts!");
      }

      for (Set<String> labelsOrNames : simplifiedExtents) {
        matchPlainRDFNodes(getPlainRDFNodes(labelsOrNames, labelOrName2PlainRDFNodes), mappings);
      }
      if (logger.isInfoEnabled()) {
        logger.info("Finish extracting mappings from simplified concepts!");
      }
    }

    if (isEnabledLattice) {
      if (logger.isInfoEnabled()) {
        logger.info(
          String.format("Start building complete concepts (Size of object: [%d. %d], Size of attribute: [%d, %d])...",
            lowerBoundOfLatticeObjectsSize, upperBoundOfLatticeObjectsSize,
            lowerBoundOfLatticeAttributesSize, upperBoundOfLatticeAttributesSize
          )
        );
      }
      Set<Set<String>> extents
        = fca.listExtents(lowerBoundOfLatticeObjectsSize, upperBoundOfLatticeObjectsSize,
                          lowerBoundOfLatticeAttributesSize, upperBoundOfLatticeAttributesSize,
                          maximumSizeOfConcepts);

      if (logger.isInfoEnabled()) {
        logger.info("Finish building complete concepts!");
        if (!fca.isComplete()) {
          logger.info("NOTE: NOT complete concept!");
        }
      }

      for (Set<String> labelsOrNames : extents) {
        matchPlainRDFNodes(getPlainRDFNodes(labelsOrNames, labelOrName2PlainRDFNodes), mappings);
      }
      if (logger.isInfoEnabled()) {
        logger.info("Finish extracting mappings from complete concepts!");
      }
    }

    fca.clear();
    if (logger.isInfoEnabled()) {
      logger.info("Finish analysis!");
    }
  }

  private Context<String, String> constructTokenBasedContext(Set<String> labelsOrNames) {
    Context<String, String> labelOrName2tokensContext = new Context<>();
    if (null == labelsOrNames) return labelOrName2tokensContext;

    for (String ln : labelsOrNames) {
      labelOrName2tokensContext.put(ln, getAllTokens(ln));
    }

    return labelOrName2tokensContext;
  }

  private void constructLookupTable(MatchType type, LookupTable labelOrName2PlainRDFNodes) {
    switch (type) {
      case INSTANCE:
        constructLookupTable(Owner.SOURCE, this.source.getIndividuals(), labelOrName2PlainRDFNodes);
        constructLookupTable(Owner.TARGET, this.target.getIndividuals(), labelOrName2PlainRDFNodes);
        for (ModelStorage other : this.otherModels) {
          constructLookupTable(Owner.DUMMY, other.getIndividuals(), labelOrName2PlainRDFNodes);
        }
        break;
      case CATEGORY:
        constructLookupTable(Owner.SOURCE, this.source.getCategories(), labelOrName2PlainRDFNodes);
        constructLookupTable(Owner.TARGET, this.target.getCategories(), labelOrName2PlainRDFNodes);
        for (ModelStorage other : this.otherModels) {
          constructLookupTable(Owner.DUMMY, other.getCategories(), labelOrName2PlainRDFNodes);
        }
        break;
      case ONT_PROPERTY:
        constructLookupTable(Owner.SOURCE, this.source.getOntProperties(), labelOrName2PlainRDFNodes);
        constructLookupTable(Owner.TARGET, this.target.getOntProperties(), labelOrName2PlainRDFNodes);
        for (ModelStorage other : this.otherModels) {
          constructLookupTable(Owner.DUMMY, other.getOntProperties(), labelOrName2PlainRDFNodes);
        }
        break;
      case DATA_TYPE_PROPERTY:
        constructLookupTable(Owner.SOURCE, this.source.getDataTypeProperties(), labelOrName2PlainRDFNodes);
        constructLookupTable(Owner.TARGET, this.target.getDataTypeProperties(), labelOrName2PlainRDFNodes);
        for (ModelStorage other : this.otherModels) {
          constructLookupTable(Owner.DUMMY, other.getDataTypeProperties(), labelOrName2PlainRDFNodes);
        }
        break;
      case OBJECT_PROPERTY:
        constructLookupTable(Owner.SOURCE, this.source.getObjectProperties(), labelOrName2PlainRDFNodes);
        constructLookupTable(Owner.TARGET, this.target.getObjectProperties(), labelOrName2PlainRDFNodes);
        for (ModelStorage other : this.otherModels) {
          constructLookupTable(Owner.DUMMY, other.getObjectProperties(), labelOrName2PlainRDFNodes);
        }
        break;
      case ONT_CLASS:
        constructLookupTable(Owner.SOURCE, this.source.getOntClasses(), labelOrName2PlainRDFNodes);
        constructLookupTable(Owner.TARGET, this.target.getOntClasses(), labelOrName2PlainRDFNodes);
        for (ModelStorage other : this.otherModels) {
          constructLookupTable(Owner.DUMMY, other.getOntClasses(), labelOrName2PlainRDFNodes);
        }
        break;
      default:
        break;
    }
  }

  private <T extends Resource> void constructLookupTable(Owner owner,
                                                         Set<T> resources,
                                                         LookupTable labelOrName2PlainRDFNodes) {
    for (T r : resources) {
      Set<String> labelsOrNames = getLabelsOrNames(r);
      PlainRDFNode prn = new PlainRDFNode(r.getURI(), owner);

      prn.setLabelOrNames(labelsOrNames);

      for (String ln : labelsOrNames) {
        labelOrName2PlainRDFNodes.put(ln, prn);
      }
    }
  }

  private <T extends Resource> Set<String> getLabelsOrNames(T resource) {
    Set<String> labelsOrNames = new HashSet<>();

    labelsOrNames.addAll(getAllLiteralString(resource, RDFS.label));

    labelsOrNames.addAll(getAllLiteralString(resource, SKOS.altLabel));

    labelsOrNames.addAll(getAllLiteralString(resource, SKOS.prefLabel));

    labelsOrNames.addAll(getAllLiteralString(resource, SKOS.hiddenLabel));

    if (labelsOrNames.isEmpty()) {
     String defaultName=resource.getURI().split("#")[1];
     labelsOrNames.add(defaultName);
//      String localName = resource.getLocalName();
//      if (null != localName && !localName.equals("")) {
//        labelsOrNames.add(localName);
//      }
    }

    if (labelsOrNames.isEmpty()) {
      String name = DBkWik.getName(resource.getURI());
      if (null != name && !name.isEmpty()) {
        labelsOrNames.add(name);
      }
    }

    if (labelsOrNames.isEmpty()) {
      // XXX: check if labelsOrNames is still empty
    }

    if (Preprocessing.isNormalizationEnabled()) {
      Set<String> normalizedLabelsOrNames = new HashSet<>();

      for (String lb : labelsOrNames) {
        String nlb = Preprocessing.normalize(lb);
        normalizedLabelsOrNames.add(nlb);
      }

      labelsOrNames.clear();
      labelsOrNames.addAll(normalizedLabelsOrNames);
    }

    return labelsOrNames;
  }

  private <T extends Resource> Set<String> getAllLiteralString(T r, Property property) {
    Set<String> literalStrings = new HashSet<>();
    for (StmtIterator it = r.listProperties(property); it.hasNext(); ) {
      Statement stmt = it.nextStatement();
      RDFNode obj = stmt.getObject();
      if (!obj.isLiteral()) continue;
      String ls = obj.asLiteral().getLexicalForm();
      if (null != ls && !ls.isEmpty()) {
        literalStrings.add(ls);
      }
    }
    return literalStrings;
  }

  private Set<String> getAllTokens(String labelOrName) {
    Set<String> tokens = new HashSet<>();
    Set<String> tokensBuffer;

    //加载自定义词典
    //Path path = FileSystems.getDefault().getPath("D:\\A39\\new\\Bri-Mapper\\FCA\\src\\main\\java\\cn\\ac\\amss\\semanticweb\\matching\\impl\\resources", "dict.txt");
    //WordDictionary.getInstance().loadUserDict(path);
    JiebaSegmenter segmenter = new JiebaSegmenter();

    labelOrName=labelOrName.replaceAll("\\(","");
    labelOrName=labelOrName.replaceAll("\\)","");
    if(labelOrName.startsWith("-"))
    {
      labelOrName=labelOrName.replaceFirst("-","");
      labelOrName=labelOrName.replaceFirst("！","");
    }

    //中文
    String regex="^([\\u4e00-\\u9fa5])(.)*";
    if(labelOrName.matches(regex))
    {
      if(labelOrName.contains(" "))
      {
        labelOrName=labelOrName.replaceAll(" ","");
      }

      //处理近音错别字
      labelOrName=CHPreprocessing.fixCHString(labelOrName);
      List<String> words =segmenter.sentenceProcess(labelOrName);
      //System.out.println(labelOrName);

      tokens.addAll(words);
      //单词处理
      //比如将标识符处理成标识
      tokens=CHPreprocessing.removeCHStopWords(tokens);
      //删除不必要的token
      tokens=CHPreprocessing.removeToken(tokens);
    }
    else
    {
      //英文
      tokensBuffer= new HashSet<>(Arrays.asList(Preprocessing.stringTokenize(labelOrName)));


      if (Preprocessing.isStopWordsEnabled()) {
        Preprocessing.removeStopWords(tokensBuffer);
        tokens.clear();
        tokens.addAll(tokensBuffer);

        tokensBuffer.clear();
        tokensBuffer.addAll(tokens);
      }

      if (Preprocessing.isStemmerEnabled()) {
        tokens.clear();
        for (String t : tokensBuffer) {
          String st = Preprocessing.stem(t);
          if (null == st || st.isEmpty()) continue;
          tokens.add(st);
        }

        // XXX: if has another processing step then should add the following two line codes
        // tokensBuffer.clear();
        // tokensBuffer.addAll(tokens);
      }
    }
    return tokens;
  }

  private Set<PlainRDFNode> getPlainRDFNodes(Set<String> labelsOrNames, LookupTable labelOrName2PlainRDFNodes) {
    Set<PlainRDFNode> candidates = new HashSet<>();
    for (String ln : labelsOrNames) {
      candidates.addAll(labelOrName2PlainRDFNodes.get(ln));
    }
    return candidates;
  }

  @Override
  protected void matchPlainRDFNodes(Set<PlainRDFNode> sources, Set<PlainRDFNode> targets, Mapping mappings) {
    // XXX: redefine it by yourself.
    for (PlainRDFNode s : sources) {
      for (PlainRDFNode t : targets) {
        if (null == s.getLabelOrNames() || null == t.getLabelOrNames()) {
          mappings.add(s.getRepresent(), t.getRepresent());
          continue;
        }
        double cnt = 1e-15, confidence = 1e-15;
        for (String sln : s.getLabelOrNames()) {
          for (String tln : t.getLabelOrNames()) {
            confidence += ss.similarity(sln, tln);
            cnt += 1.0f;
          }
        }
        mappings.add(s.getRepresent(), t.getRepresent(), confidence / cnt);
      }
    }
  }
}
