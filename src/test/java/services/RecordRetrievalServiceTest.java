package services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import model.Dog;

import org.junit.Before;
import org.junit.Test;

public class RecordRetrievalServiceTest {

	public final String NON_EXISTENT_DOG_NAME = "LD@()";	
	public final String NON_EXISTENT_PROPERTY_TYPE = "Age";
	public final String NON_EXISTENT_PROPERTY_VALUE = "5";
	
	public RecordRetrievalService recordService;

	@Before
	public void prepareTests() {
		recordService = new RecordRetrievalService();
	}

	/*
	 * TC1.queryDogRecords
	 * 
	 * This test queries the database with a null argument for property type Breed and
	 * expects an IllegalArgumentException to be thrown.
	 */
	@Test
	public void queryDogRecordsNullParameterTest() {
		try{
			recordService.queryDogRecords("Breed", null, true);
			fail("Expected an IllegalArgumentException");
		}
		catch (IllegalArgumentException e){
			assertTrue(true);
		}
		
		try{
			recordService.queryDogRecords(null, "Lucy", true);
			fail("Expected an IllegalArgumentException");
		}
		catch (IllegalArgumentException e){
			assertTrue(true);
		}				
	}

	/*
	 * TC2.queryDogRecords
	 * 
	 * This test queries the database with a non-existent argument for property type Name and 
	 * expects to receive an empty list of dog records.
	 */
	@Test
	public void queryDogRecordsNonExistentNameTest() {
		Set<Dog> dogRecords = recordService.queryDogRecords("Name", NON_EXISTENT_DOG_NAME, true);
		assertTrue(dogRecords.isEmpty());
	}
	
	/*
	 * TC3.queryDogRecords
	 * 
	 * This test queries the database with a non-existent property type and 
	 * expects an IllegalArgumentException to be thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void queryDogRecordsNonExistentPropertyTypeTest() {
		recordService.queryDogRecords(NON_EXISTENT_PROPERTY_TYPE, NON_EXISTENT_PROPERTY_VALUE, true);		
	}
}
