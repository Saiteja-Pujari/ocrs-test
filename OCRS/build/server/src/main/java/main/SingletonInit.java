package main;

import d3e.core.QueryProvider;
import store.EntityMutator;

@org.springframework.stereotype.Component
public class SingletonInit {
  @org.springframework.beans.factory.annotation.Autowired private QueryProvider queryProvider;
  @org.springframework.beans.factory.annotation.Autowired private EntityMutator mutator;

  @javax.annotation.PostConstruct
  public void initialize() {
    d3e.core.QueryProvider.instance = queryProvider;
  }
}
