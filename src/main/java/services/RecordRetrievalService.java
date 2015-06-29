package services;

import java.util.Set;
import java.util.HashSet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import model.Dog;

public class RecordRetrievalService {
	DatastoreService datastore;	
	
	public RecordRetrievalService() {

	}

	/**
	 * RecordRetrievalService.queryRecordsByName
	 * 
	 * This function will run queries against google's datastore
	 * 
	 * @param propertyType
	 * 			The property of the dog to search for. (e.g. Name, Breed, City)
	 * @param name
	 *          The dog name to search for.
	 * @param inTestingMode
	 *          Boolean flag true if for testing purposes
	 * @return The list of records matching that name.
	 */
	public Set<Dog> queryDogRecords(String propertyType, String query, boolean inTestingMode) {
		if (query == null) {
			throw new IllegalArgumentException("Did not expect name to be null.");
		}
		
		if (propertyType != "Name" && propertyType != "Condition" && propertyType != "Sex" && propertyType != "Breed"
				&& propertyType != "Color" && propertyType != "City") {
			throw new IllegalArgumentException("Property type can only be Name, Condition, Sex, Breed, Color, or City.");
		}

		Set<Dog> dogRecords = new HashSet<Dog>();
				
		if (!inTestingMode) {
			datastore = DatastoreServiceFactory.getDatastoreService();
			Filter dogNameFilter = new FilterPredicate(propertyType, FilterOperator.EQUAL, query);
			Query q = new Query("Dog").setFilter(dogNameFilter);
			PreparedQuery pq = datastore.prepare(q);
			for (Entity result : pq.asIterable()) {
				Dog dog = new Dog();
				dog.setName((String) result.getProperty("Name"));
				dog.setCondition((String) result.getProperty("Condition"));
				dog.setSex((String) result.getProperty("Sex"));
				dog.setBreed((String) result.getProperty("Breed"));
				dog.setColor((String) result.getProperty("Color"));
				dog.setLocation((String) result.getProperty("City"));
				dogRecords.add(dog);
			}
		}

		return dogRecords;
	}
}
