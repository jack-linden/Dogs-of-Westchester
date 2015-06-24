package services;

import static org.junit.Assert.assertTrue;
import java.util.List;
import model.Dog;
import org.junit.Before;
import org.junit.Test;

public class RecordRetrievalServiceTest {

	public final String NON_EXISTENT_DOG_NAME = "LD@()";
	public final String DOG_NAME_A = "Buddy";

	public RecordRetrievalService recordService;

	@Before
	public void prepareTests() {
		recordService = new RecordRetrievalService();
	}

	/*
	 * TC1.queryRecordsByName
	 * 
	 * This test queries the database with a null argument for name. Expects an
	 * IllegalArgumentException to be thrown.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void queryRecordsByNameNullParameterTest() {
		recordService.queryRecordsByName(null);
	}

	/*
	 * TC2.queryRecordsByName
	 * 
	 * This test queries the database with a non-existent name. Expects to
	 * receive an empty list of dog records.
	 */
	@Test
	public void queryRecordsByNameNonExistentRecordTest() {

		List<Dog> dogRecords = recordService.queryRecordsByName(NON_EXISTENT_DOG_NAME);
		assertTrue(dogRecords.isEmpty());
	}
}
