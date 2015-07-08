package dataaccess;

import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import model.Dog;

public class DogDaoImpl implements DogDao {
	private DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();

	public Set<Dog> getDogsFromQuery(String propertyType, String query) {
		Set<Dog> dogRecords = new HashSet<Dog>();
		query = query.toUpperCase();

		Filter dogNameFilter = new FilterPredicate(propertyType, FilterOperator.EQUAL, query);
		Query q = new Query("Dog").setFilter(dogNameFilter);
		PreparedQuery pq = dataStoreService.prepare(q);

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

	public String insertDog(Dog dog) {
		Entity dogEntity = createDogEntity(dog);

		return dataStoreService.put(dogEntity).toString();
	}

	private Entity createDogEntity(Dog dog) {

		Entity dogEntity = new Entity("Dog");
		dogEntity.setProperty("City", dog.getLocation());
		dogEntity.setProperty("Name", dog.getName());
		dogEntity.setProperty("Condition", dog.getCondition());
		dogEntity.setProperty("Sex", dog.getSex());
		dogEntity.setProperty("Breed", dog.getBreed());
		dogEntity.setProperty("Color", dog.getColor());
		return dogEntity;
	}
}