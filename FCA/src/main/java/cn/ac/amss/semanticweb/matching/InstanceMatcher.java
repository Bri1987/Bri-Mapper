/*
 * InstanceMatcher.java
 * Copyright (C) 2019 Guowei Chen <icgw@outlook.com>
 *
 * Distributed under terms of the GPL license.
 */

package cn.ac.amss.semanticweb.matching;

import cn.ac.amss.semanticweb.alignment.Mapping;

public interface InstanceMatcher
{
  public void mapInstances(Mapping mappings);
}
