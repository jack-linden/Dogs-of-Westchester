package services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import model.Dog;

public class RecordRetrievalService {

	private DogDaoImpl dogDao;

	/**
	 * Class constructor.
	 */
	public RecordRetrievalService() {
		setDogDao(new DogDaoImpl());
	}

	public void setDogDao(DogDaoImpl dogDao) {
		this.dogDao = dogDao;
	}

	/**
	 * This method will run queries against google's datastore
	 * 
	 * @param propertyTypes
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @param query
	 *            The query user typed in the search bar
	 * @return a list of records matching that name
	 */
	public Set<Dog> queryDogRecords(List<String> propertyTypes, String query) {
		if (propertyTypes == null || query == null) {
			throw new IllegalArgumentException("Did not expect name to be null.");
		}

		if (!propertyTypeIsValid(propertyTypes)) {
			throw new IllegalArgumentException("Property type can only be Name, Condition, Sex, Breed, Color, or City.");
		}

		Set<Dog> dogRecords = new HashSet<Dog>();
		query = query.toUpperCase();
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
		for (String property : propertyTypes) {
			if (!(property.equals("Name") || property.equals("Condition") || property.equals("Sex") || property.equals("Breed") || property.equals("Color")
					|| property.equals("City"))) {
				return false;
			}
		}
		return true;
	}
}
