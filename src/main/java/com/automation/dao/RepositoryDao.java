package com.automation.dao;

import java.util.List;

import com.automation.beanclass.RepositoryBean;

public interface RepositoryDao {

  public  List<String> searchValue(String name);
  
  public void insertValue(RepositoryBean respository);
}
