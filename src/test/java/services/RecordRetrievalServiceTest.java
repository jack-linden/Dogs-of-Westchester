package services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dataaccess.DogDao;
import model.Dog;
import mockit.*;
import mockit.integration.junit4.JMockit;

public class RecordRetrievalServiceTest {

	public final String NON_EXISTENT_DOG_NAME = "LD@()";
	public final String NON_EXISTENT_PROPERTY_TYPE = "Age";
	public final String NON_EXISTENT_PROPERTY_VALUE = "5";

	public RecordRetrievalService recordService;
	@Mocked private DogDao mockedDao = null;
	@Before
	public void prepareTests() {
		recordService = new RecordRetrievalService();
		mockedDao = recordService.dogDao;
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
//	@Test
//	public void queryDogRecordsNonExistentNameTest() {
//		new Expectations(){{
//				mockedDao.getDogsFromQuery("Name", NON_EXISTENT_DOG_NAME); result = new HashSet<Dog>(); 
//		}};
//		Set<Dog> dogRecords = recordService.queryDogRecords("Name", NON_EXISTENT_DOG_NAME);
//		assertTrue(dogRecords.isEmpty());
//	}

	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a non-existent property type 
	 * and expects an IllegalArgumentException to be thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void queryDogRecordsNonExistentPropertyTypeTest() {
		recordService.queryDogRecords(NON_EXISTENT_PROPERTY_TYPE, NON_EXISTENT_PROPERTY_VALUE);
	}
	
	/*
	 * This tests the queryDogRecords() method. 
	 * It queries the database with a valid location value
	 * and expects to get a set of dogs that matches the query
	 */
//	@Test
//	public void queryDogRecordsLocationTest() {			
//		mockedDog.setLocation("UNKNOWN");
//		mockedDog.setIdNumber("0000000000000001");		
//		assertTrue(recordService.queryDogRecords("Location", "UNKNOWN").contains(mockedDog));
//	}
}
