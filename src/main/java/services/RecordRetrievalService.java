package services;

import java.util.List;
import java.util.Set;

import dataaccess.DogDaoImpl;
import model.Dog;

public interface RecordRetrievalService {

	/**
	 * This method will run queries against Google App Engine's datastore
	 * 
	 * @param propertyTypes
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @param query
	 *            The query user typed in the search bar
	 * @return a list of records matching that name
	 */
	public Set<Dog> queryDogRecords(List<String> propertyTypes, String query);
	
	/**
	 * Set dogDao (Dog data-access object)
	 * @param dogDao
	 * 		DogDaoImpl (data-access object)
	 */
	public void setDogDao(DogDaoImpl dogDao);
}
