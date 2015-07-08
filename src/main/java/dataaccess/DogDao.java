package dataaccess;

import java.util.Set;

import model.Dog;

public interface DogDao {

	public Set<Dog> getDogsFromQuery(String propertyType, String query);

	public String insertDog(Dog dog);
}
