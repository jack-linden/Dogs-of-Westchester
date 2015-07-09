package services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import model.Dog;
import static org.easymock.EasyMock.*;


public class RecordRetrievalServiceTest {

	public final String NON_EXISTENT_DOG_NAME = "LD@()";
	public final String NON_EXISTENT_PROPERTY_TYPE = "Age";
	public final String NON_EXISTENT_PROPERTY_VALUE = "5";

	public RecordRetrievalService recordService;
	public DogDaoImpl dogDao;
	
	@Before
	public void prepareTests() {
		recordService = new RecordRetrievalService();		
		dogDao = createMock(DogDaoImpl.class);
		recordService.setDogDao(dogDao);

	}

	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a null argument for property type Breed 
	 * and expects an IllegalArgumentException to be thrown.
	 */
	@Test
	public void queryDogRecordsNullParameterTest() {
		try {
			recordService.queryDogRecords("Breed", null);
			fail("Expected an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		try {
			recordService.queryDogRecords(null, "Lucy");
			fail("Expected an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/*	 
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a non-existent argument for property type Name
	 * and expects to receive an empty list of dog records.
	 */
	@Test
	public void queryDogRecordsNonExistentNameTest() {
		expect(dogDao.getDogsFromQuery("Name", NON_EXISTENT_DOG_NAME)).andReturn(new HashSet<Dog>());
		replay(dogDao);
		Set<Dog> dogs = recordService.queryDogRecords("Name", NON_EXISTENT_DOG_NAME);
		assertTrue(dogs.isEmpty());
	}

	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a non-existent property type 
	 * and expects an IllegalArgumentException to be thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void queryDogRecordsNonExistentPropertyTypeTest() {
		recordService.queryDogRecords(NON_EXISTENT_PROPERTY_TYPE, NON_EXISTENT_PROPERTY_VALUE);
	}
	
	// TODO ADD TEST: SELECT MULTIPLE PROPERTIES
	
	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a valid unknown location value
	 * and expects to get a set of dogs that contains the query
	 */
	@Test
	public void queryDogRecordsUnknownLocationTest() {			
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "female", "boston terrier", "red and white", "UNKNOWN");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery("City", "UNKNOWN")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords("City", "UNKNOWN");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
		
	}
	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a valid name value
	 * and expects to get a set of dogs that contains the query
	 */
	@Test
	public void queryDogRecordsNameTest() {			
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000001", "lucky", "ALTERED", "FEMALE", "boston terrier", "red and white", "White Plains");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery("Name", "LUCKY")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords("Name", "lucky");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
		
	}
	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a valid breed value
	 * and expects to get a set of dogs that contains the query
	 */
	@Test
	public void queryDogRecordsBreedTest() {			
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "female", "BOSTON TERRIER", "red and white", "UNKNOWN");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery("Breed", "BOSTON TERRIER")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords("Breed", "boston terrier");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
		
	}
}
