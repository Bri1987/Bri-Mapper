package cn.ac.amss.semanticweb.io;

import cn.ac.amss.semanticweb.alignment.Mapping;

public abstract class MappingReader
{
  protected final static String CELL     = "Cell";
  protected final static String ENTITY1  = "entity1";
  protected final static String ENTITY2  = "entity2";
  protected final static String RELATION = "relation";
  protected final static String MEASURE  = "measure";

  protected Mapping m_mappings = null;

  public MappingReader() {
    m_mappings = new Mapping();
  }

  public Mapping getMapping() {
    return m_mappings;
  }

  public int getMappingSize() {
    return m_mappings.size();
  }
}
