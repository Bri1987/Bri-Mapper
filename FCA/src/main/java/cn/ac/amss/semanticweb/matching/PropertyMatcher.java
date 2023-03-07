package cn.ac.amss.semanticweb.matching;

import org.apache.jena.rdf.model.Resource;

import java.util.Set;

import cn.ac.amss.semanticweb.alignment.Mapping;

public interface PropertyMatcher
{
  public <T extends Resource> void matchProperties(Set<T> sources, Set<T> targets, Mapping mappings);

  public void mapOntProperties(Mapping mappings);

  public void mapDatatypeProperties(Mapping mappings);

  public void mapObjectProperties(Mapping mappings);
}
