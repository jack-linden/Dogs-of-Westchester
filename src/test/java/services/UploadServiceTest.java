package services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.FileNotFoundException;

import model.Dog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import dataaccess.DogDao;
import dataaccess.DogDaoImpl;

public class UploadServiceTest {
	
	public UploadService uploadService;	
	private DogDao mockedDogDao = null;    
	//public final String NON_EXISTENT_FILE = "NON_EXISTENT_FILE.lol";

	@Before
	public void prepareTests() {
		uploadService = UploadService.getInstance();	
		mockedDogDao = new DogDaoImpl();
	}

	/*	 
	 * This tests the uploadCSV method will throw an illegal argument exception
	 * if a null byte array is passed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullParameterFilenameTest() throws IOException {
		uploadService.uploadCSV(null);
	}

	/*	 
	 * This tests that the uploadCSV method will throw an illegal argument exception if
	 * the file contents are empty
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nonExistentFileTest() throws IOException {
		uploadService.uploadCSV(new byte[0]);
	}
		
	/*	 
	 * This tests that the uploadCSV method can parse a well formatted csv file
	 * correctly Expects an array of dog info
	 */
	@Test
	public void wellFormattedCSVFileTest() throws IOException {			
		String expected = "WHITE PLAINS,,,,,\nDUKE,ALTERED,MALE,GREAT DANE,BLACK,\n";
		String newResult = "DUKE,ALTERED,MALE,GREAT DANE,BLACK,0000000000000001\n";
		uploadService.uploadCSV(expected.getBytes());
		DogDaoImpl mock = createMock(DogDaoImpl.class);
		expect(mock.insertDog(new Dog("UNKNOWN", "DUKE", "ALTERED", "MALE", "GREAT DANE", "BLACK", "WHITE PLAINS"))).andReturn("0000000000000001");
		byte[] returnedByteArr = uploadService.uploadCSV(expected.getBytes());
		assertEquals(newResult.getBytes(), returnedByteArr);
	}
}
