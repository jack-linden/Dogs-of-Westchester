package dataaccess;

import model.Dog;

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

public class DogDAO {	
	public static Set<Dog> getDogsFromQuery(String propertyType, String query){
		Set<Dog> dogRecords = new HashSet<Dog>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();			
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
		
		return dogRecords;		
	}
}
