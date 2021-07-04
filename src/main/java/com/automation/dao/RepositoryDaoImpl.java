package com.automation.dao;

import com.automation.beanclass.RepositoryBean;
import com.automation.configuration.DatabaseConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public class RepositoryDaoImpl implements RepositoryDao {

  @Autowired
 private DatabaseConfiguration data;


  @Transactional
  @Override
  public List<String> searchValue(String name) {
    // TODO Auto-generated method stub
    RepositoryBean bean = data.getHibernateTemplate().get(RepositoryBean.class, name);
    List<String> object = new ArrayList<String>();
    object.add(bean.getObjectType());
    object.add(bean.getObjectValue());
    return object;
  }

  @Transactional
  @Override
  public void insertValue(RepositoryBean respository) {
    // TODO Auto-generated method stub
    data.getHibernateTemplate().save(respository);
  }

}
