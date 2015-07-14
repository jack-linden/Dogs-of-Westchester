package dataaccess;

import java.util.List;
import java.util.Set;

import model.Dog;

public interface DogDao {
	/**
	 * The method searches the database based on the user-specified query
	 * 
	 * @param propertyType
	 *            The property of the dog to search for (e.g. Name, Breed, City)
	 * @param query
	 *            The query user typed in the search bar
	 * @return a set of unique dogs that match the query
	 */
	public Set<Dog> getDogsFromQuery(List<String> propertyTypes, String query);

	/**
	 * The method inserts new dog data into the database
	 * 
	 * @param dog
	 *            The Dog object that contains the dog information
	 * @return the idNumber of the dog stored in the database
	 * 
	 */
	public String insertDog(Dog dog);

	/**
	 *  The method returns all dog records.
	 * @return Set containing all dog records.
	 */
	public Set<Dog> getAllDogs();
}
