package services;

import java.util.HashSet;
import java.util.Set;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import model.Dog;

public class RecordRetrievalService {

	/**
	 * Class constructor.
	 */
	public RecordRetrievalService() {

	}

	/**
	 * This method will run queries against google's datastore
	 * 
	 * @param propertyType
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @param query
	 *            The query user typed in the search bar
	 * @return a list of records matching that name
	 */
	public Set<Dog> queryDogRecords(String propertyType, String query) {
		if (propertyType == null || query == null) {
			throw new IllegalArgumentException("Did not expect name to be null.");
		}

		if (!propertyTypeIsValid(propertyType)) {
			throw new IllegalArgumentException("Property type can only be Name, Condition, Sex, Breed, Color, or City.");
		}

		Set<Dog> dogRecords = new HashSet<Dog>();
		query = query.toUpperCase();
		DogDao dogDao = new DogDaoImpl();
		dogRecords = dogDao.getDogsFromQuery(propertyType, query);

		return dogRecords;
	}

	/**
	 * This is a helper method that checks the validity of the property type
	 * 
	 * @param propertyType
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @return true if the propertyType is one of the supported property types
	 *         (eg. Name, Breed, City or etc.) otherwise return false
	 */
	private boolean propertyTypeIsValid(String propertyType) {
		return propertyType.equals("Name") && !propertyType.equals("Condition") && !propertyType.equals("Sex") && !propertyType.equals("Breed")
				&& !propertyType.equals("Color") && !propertyType.equals("City");
	}
}
