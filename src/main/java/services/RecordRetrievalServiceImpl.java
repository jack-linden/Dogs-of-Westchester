package services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import model.Dog;

public class RecordRetrievalServiceImpl implements RecordRetrievalService{

	private DogDao dogDao;

	/**
	 * Class constructor.
	 */
	public RecordRetrievalServiceImpl() {
		setDogDao(new DogDaoImpl());
	}
	
	/**
	 * This method sets the private field dogDao (Dog data-access object)
	 * 
	 * @param dogDao
	 */
	public void setDogDao(DogDaoImpl dogDao) {
		this.dogDao = dogDao;
	}

	/**
	 * This method will run queries against Google App Engine's datastore
	 * 
	 * @param propertyTypes
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @param query
	 *            The query user typed in the search bar
	 * @return a list of records matching that name
	 */
	public Set<Dog> queryDogRecords(List<String> propertyTypes, String query) {
		//Illegal input checks, will throw IAException
		if (propertyTypes == null || query == null) {
			throw new IllegalArgumentException("Did not expect name to be null.");
		}
		
		//Illegal input checks, will throw IAException
		if (!propertyTypeIsValid(propertyTypes)) {
			throw new IllegalArgumentException("Property type can only be Name, Condition, Sex, Breed, Color, or City.");
		}
		
		Set<Dog> dogRecords = new HashSet<Dog>();
		
		// Dog fields are stored in uppercase
		query = query.toUpperCase();
		
		// Query datastore
		dogRecords = dogDao.getDogsFromQuery(propertyTypes, query);  

		return dogRecords;
	}

	/**
	 * This is a helper method that checks the validity of the property types
	 * 
	 * @param propertyTypes
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @return true if the propertyType is one of the supported property types
	 *         (eg. Name, Breed, City or etc.) otherwise return false
	 */
	private boolean propertyTypeIsValid(List<String> propertyTypes) {
		//Each property type must equal one of the following, return false if not
		for (String property : propertyTypes) {
			if (!(property.equals("Name") || property.equals("Condition") || property.equals("Sex") || property.equals("Breed") || property.equals("Color")
					|| property.equals("City"))) {
				return false;
			}
		}
		//All properties passed, return true
		return true;
	}
}
