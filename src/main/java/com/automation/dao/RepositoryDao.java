package com.automation.dao;

import com.automation.beanclass.RepositoryBean;
import java.util.List;

public interface RepositoryDao {

  public  List<String> searchValue(String name);
  
  public void insertValue(RepositoryBean respository);
}
