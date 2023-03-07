
package cn.ac.amss.semanticweb.matching;

import cn.ac.amss.semanticweb.alignment.Mapping;

public interface LexicalMatcher extends FCAMatcher
{
  public void mapInstances(Mapping mappings);

  public void mapCategories(Mapping mappings);

  public void mapOntProperties(Mapping mappings);

  public void mapDataTypeProperties(Mapping mappings);

  public void mapObjectProperties(Mapping mappings);

  public void mapOntClasses(Mapping mappings);
}
