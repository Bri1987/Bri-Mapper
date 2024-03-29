
package cn.ac.amss.semanticweb.matching;

import cn.ac.amss.semanticweb.model.OntModelWrapper;

public interface Matcher extends InstanceMatcher, PropertyMatcher, ClassMatcher
{
  public void setSourceOntModelWrapper(OntModelWrapper source);

  public void setTargetOntModelWrapper(OntModelWrapper target);

  public void setSourceTargetOntModelWrapper(OntModelWrapper source, OntModelWrapper target);

  public void addIntermediateOntModelWrapper(OntModelWrapper intermediate);

  public void close();
}
