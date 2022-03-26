package com.automation.dao;

import com.automation.beanclass.RepositoryBean;
import java.util.List;

public interface RepositoryDao {

  List<String> searchValue(String name);
  
  void insertValue(RepositoryBean respository);
}
