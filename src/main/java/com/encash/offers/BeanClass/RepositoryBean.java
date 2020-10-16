package com.encash.offers.BeanClass;

import com.opencsv.bean.CsvBindByName;
/**
 * This is the Bean class to read Object file 
 * @author Vinod Kumar R
 *
 */
public class RepositoryBean {
	
	@CsvBindByName(column = "ObjectName", required = true)
	private String ObjectName;
	
	@CsvBindByName(column = "ObjectValue", required = true)
	private String ObjectValue;
	
	@CsvBindByName(column = "ObjectType",required = true)
	private String ObjectType;
	
	public String getObjectName() {
		return ObjectName;
	}
	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}
	public String getObjectValue() {
		return ObjectValue;
	}
	public void setObjectValue(String objectValue) {
		ObjectValue = objectValue;
	}
	public String getObjectType() {
		return ObjectType;
	}
	public void setObjectType(String objectType) {
		ObjectType = objectType;
	}
	
	

}
