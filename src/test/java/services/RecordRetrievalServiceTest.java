package services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	 * This tests the queryDogRecords() method. It queries the database with a
	 * null argument for property type Breed and expects an
	 * IllegalArgumentException to be thrown.
	 */
	@Test
	public void queryDogRecordsNullParameterTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Breed");
		try {
			recordService.queryDogRecords(propertyTypes, null);
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
	 * This tests the queryDogRecords() method. It queries the database with a
	 * non-existent argument for property type Name and expects to receive an
	 * empty list of dog records.
	 */
	@Test
	public void queryDogRecordsNonExistentNameTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Name");
		expect(dogDao.getDogsFromQuery(propertyTypes, NON_EXISTENT_DOG_NAME)).andReturn(new HashSet<Dog>());
		replay(dogDao);
		Set<Dog> dogs = recordService.queryDogRecords(propertyTypes, NON_EXISTENT_DOG_NAME);
		assertTrue(dogs.isEmpty());
	}

	/*
	 * This tests the queryDogRecords() method. It queries the database with a
	 * non-existent property type and expects an IllegalArgumentException to be
	 * thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void queryDogRecordsNonExistentPropertyTypeTest() {
		List<String> invalidPropertyTypes = new ArrayList<String>();
		invalidPropertyTypes.add("Fish");
		recordService.queryDogRecords(invalidPropertyTypes, "LOL");
	}

	/*
	 * This tests the queryDogRecords() method. It queries the database with a
	 * valid unknown location value and expects to get a set of dogs that
	 * contains the query.
	 */
	@Test
	public void queryDogRecordsUnknownLocationTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("City");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "female", "labrador", "white", "UNKNOWN");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "UNKNOWN")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "UNKNOWN");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));

	}

	/*
	 * This tests the queryDogRecords() method. It queries the database with a
	 * valid name value and expects to get a set of dogs that contains the
	 * query.
	 */
	@Test
	public void queryDogRecordsNameTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Name");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000001", "lucky", "ALTERED", "FEMALE", "boston terrier", "red and white", "White Plains");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "LUCKY")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "lucky");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
	}

	/*
	 * This tests the queryDogRecords() method. It queries the database with a
	 * valid breed value and expects to get a set of dogs that contains the
	 * query.
	 */
	@Test
	public void queryDogRecordsBreedTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Breed");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "female", "DACHSHUND", "red", "Rye");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "DACHSHUND")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "dachshund");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
	}

	/*
	 * This tests the queryDogRecords() method. It queries the database with
	 * multiple properties (both Breed and Name) selected and expects to get a
	 * set of dogs that contains the query.
	 */
	@Test
	public void queryDogRecordsBreedAndNameTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Breed");
		propertyTypes.add("Name");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000002", "jack", "altered", "male", "BOSTON TERRIER", "black", "UNKNOWN");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "BOSTON TERRIER")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "boston terrier");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
	}
	
	/*
	 * This tests the queryDogRecords() method. It queries the database with
	 * multiple properties (both Breed and Location) selected and expects to get a
	 * set of dogs that contains the query.
	 */
	@Test
	public void queryDogRecordsBreedAndLocationTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Breed");
		propertyTypes.add("City");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000003", "mark", "altered", "male", "yorkshire terrier", "red and white", "RYE");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "RYE")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "rye");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
	}
	
	/*
	 * This tests the queryDogRecords() method. It queries the database with
	 * multiple properties (both Name and Location) selected and expects to get a
	 * set of dogs that contains the query.
	 */
	@Test
	public void queryDogRecordsNameAndLocationTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Name");
		propertyTypes.add("City");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000004", "JAMIE", "altered", "female", "PUG", "black", "White Plains");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "JAMIE")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "jamie");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
	}
	
	/*
	 * This tests the queryDogRecords() method. It queries the database with
	 * all properties (Breed, Name and Location) selected and expects to get a
	 * set of dogs that contains the query.
	 */
	@Test
	public void queryDogRecordsBreedNameLocationTest() {
		List<String> propertyTypes = new ArrayList<String>();
		propertyTypes.add("Breed");
		propertyTypes.add("Name");		
		propertyTypes.add("City");
		Set<Dog> mockedDogs = new HashSet<Dog>();
		Dog dog = new Dog("0000000000000005", "bill", "altered", "male", "GOLDEN RETRIEVER", "cream", "UNKNOWN");
		mockedDogs.add(dog);
		expect(dogDao.getDogsFromQuery(propertyTypes, "GOLDEN RETRIEVER")).andReturn(mockedDogs);
		replay(dogDao);
		Set<Dog> returnedDogs = recordService.queryDogRecords(propertyTypes, "golden retriever");
		assertTrue(returnedDogs.size() == 1);
		assertTrue(returnedDogs.contains(dog));
	}
}
