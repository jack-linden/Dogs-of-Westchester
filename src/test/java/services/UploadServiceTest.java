package services;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import model.Dog;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import dataaccess.DogDaoImpl;

public class UploadServiceTest {

	public UploadService uploadService = null;
	public DogDaoImpl mockedDogDao = null;

	// This is the ID String returned by Google App Engine
	public final String VALID_DOG_ID_STRING = "Dog(0123456789123456)";
	// The 16-digit number extracted from ID_STRING
	public final String VALID_DOG_ID_NUMBER = "0123456789123456";

	@Before
	public void prepareTests() {
		uploadService = new UploadServiceImpl();
		mockedDogDao = createMock(DogDaoImpl.class);
		uploadService.setDogDao(mockedDogDao);
	}

	/*
	 * This is a helper method that sets up tests on private methods with
	 * reflection
	 */
	public Method getPrivateMethod(Class<UploadServiceImpl> class1, String methodName, Class[] parameterTypes) {

		Method method = null;
		try {
			method = class1.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		method.setAccessible(true);
		return method;
	}

	/*
	 * This is a helper method that reads in data from a given file path
	 */
	public byte[] getBytesFromFile(String filepath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		byte[] fileBytes = null;
		try {
			fileBytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileBytes;
	}

	/*
	 * This tests the uploadCSV() method will throw an illegal argument
	 * exception if a null byte array is passed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullParameterTest() throws IOException {
		uploadService.uploadCSV(null);
	}

	/*
	 * This tests that the uploadCSV() method will throw an illegal argument
	 * exception if the file contents are empty.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void uploadCSVEmptyFileContentsTest() throws IOException {
		uploadService.uploadCSV(new byte[0]);
	}

	/*
	 * This tests that the uploadCSV() method's byte[] output matches the byte[]
	 * contents of the test-file White_Plains_Expected.csv
	 */
	@Test
	public void uploadCSVNormalTest() {
		byte[] bytesToPass = getBytesFromFile("test-files/White_Plains.csv");
		byte[] expectedBytes = getBytesFromFile("test-files/White_Plains_Expected.csv");
		expect(mockedDogDao.insertDog(isA(Dog.class))).andReturn(VALID_DOG_ID_STRING).times(3);
		replay(mockedDogDao);
		try {
			byte[] returnedBytes = uploadService.uploadCSV(bytesToPass);
			for (int i = 0; i < expectedBytes.length; i++) {
				assertEquals((char) returnedBytes[i], (char) expectedBytes[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	/*
	 * This tests that the private method validIdExists() will throw an
	 * IllegalArgumentException if a null String is passed
	 */
	@Test
	public void validIdExistsNullParameterTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { null };
		Method validIdExists = getPrivateMethod(UploadServiceImpl.class, "validIdExists", parameterTypes);
		try {
			validIdExists.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method validIdExists() will fail if an id
	 * that isn't 16 digits is passed
	 */
	@Test
	public void validIdExistsInvalidIdLengthTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "1234" };
		Method validIdExists = getPrivateMethod(UploadServiceImpl.class, "validIdExists", parameterTypes);
		try {
			boolean isValidId = (Boolean) validIdExists.invoke(uploadService, parameters);
			assertFalse(isValidId);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method validIdExists() will fail if an id
	 * contains non-numerical characters
	 */
	@Test
	public void validIdExistsInvalidCharactersTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "EEEEEE12EEEEEEEE" };
		Method validIdExists = getPrivateMethod(UploadServiceImpl.class, "validIdExists", parameterTypes);
		try {
			boolean isValidId = (Boolean) validIdExists.invoke(uploadService, parameters);
			assertFalse(isValidId);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method validIdExists() will pass because a
	 * valid 16-digit numerical id is passed
	 */
	@Test
	public void validIdExistsValidIdTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { VALID_DOG_ID_NUMBER };
		Method validIdExists = getPrivateMethod(UploadServiceImpl.class, "validIdExists", parameterTypes);
		try {
			boolean isValidId = (Boolean) validIdExists.invoke(uploadService, parameters);
			assertTrue(isValidId);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method appendDogIdToCSVLine() will throw an
	 * IllegalArgumentException if a null String is passed
	 */
	@Test
	public void appendDogIdToCSVLineNullParameterTest() {
		Class[] parameterTypes = { java.lang.String.class, java.lang.String.class };
		Object[] parameters = { null, VALID_DOG_ID_NUMBER };
		Method appendDogIdToCSVLine = getPrivateMethod(UploadServiceImpl.class, "appendDogIdToCSVLine", parameterTypes);
		try {
			appendDogIdToCSVLine.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
		Object[] parameters2 = { "CSVLINE,", null };
		try {
			appendDogIdToCSVLine.invoke(uploadService, parameters2);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method appendDogIdToCSVLine() throws an
	 * IllegalArgumentException if an empty string is passed
	 */
	@Test
	public void appendDogIdToCSVLineEmptyStringTest() {
		Class[] parameterTypes = { java.lang.String.class, java.lang.String.class };
		Object[] parameters = { "", VALID_DOG_ID_NUMBER };
		Method appendDogIdToCSVLine = getPrivateMethod(UploadServiceImpl.class, "appendDogIdToCSVLine", parameterTypes);

		try {
			appendDogIdToCSVLine.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
		Object[] parameters2 = { "CSVLINE,", "" };
		try {
			appendDogIdToCSVLine.invoke(uploadService, parameters2);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method appendDogIdToCSVLine() correctly
	 * appends the two arguments together and returns the resulting string
	 */
	@Test
	public void appendDogIdToCSVLineNormalTest() {
		Class[] parameterTypes = { java.lang.String.class, java.lang.String.class };
		Object[] parameters = { "CSVLINE,", VALID_DOG_ID_NUMBER };
		Method appendDogIdToCSVLine = getPrivateMethod(UploadServiceImpl.class, "appendDogIdToCSVLine", parameterTypes);
		String expectedResult = "CSVLINE,0123456789123456";
		try {
			String result = (String) appendDogIdToCSVLine.invoke(uploadService, parameters);
			assertEquals(expectedResult, result);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method getCityName() will throw an
	 * IllegalArgumentException if a null string is passed
	 */
	@Test
	public void getCityNameNullParameterTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { null };
		Method getCityName = getPrivateMethod(UploadServiceImpl.class, "getCityName", parameterTypes);
		try {
			getCityName.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method getCityName() will thrown an
	 * IllegalArgumentException if an empty string is passed.
	 */
	@Test
	public void getCityNameEmptyStringTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "" };
		Method getCityName = getPrivateMethod(UploadServiceImpl.class, "getCityName", parameterTypes);
		try {
			getCityName.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method getCityName() correctly parse the line
	 * and return the city name
	 */
	@Test
	public void getCityNameNormalTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "NEW YORK CITY,,,,," };
		Method getCityName = getPrivateMethod(UploadServiceImpl.class, "getCityName", parameterTypes);
		String expectedCityName = "NEW YORK CITY";
		try {
			String returnedCityName = (String) getCityName.invoke(uploadService, parameters);
			assertEquals(expectedCityName, returnedCityName);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/*
	 * This tests that the private method prepareTokens() will throw an
	 * IllegalArgumentException if a null string is passed
	 */
	@Test
	public void prepareTokensNullParameterTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { null };
		Method prepareTokens = getPrivateMethod(UploadServiceImpl.class, "prepareTokens", parameterTypes);
		try {
			prepareTokens.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method prepareTokens() will thrown an
	 * IllegalArgumentException if an empty string is passed.
	 */
	@Test
	public void prepareTokensEmptyStringTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "" };
		Method prepareTokens = getPrivateMethod(UploadServiceImpl.class, "prepareTokens", parameterTypes);
		try {
			prepareTokens.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method prepareTokens() fills in "UNKNOWN" for
	 * the first 5 CSV line columns that are empty and the empty string for the
	 * 6th column (the dog ID).
	 */
	@Test
	public void prepareTokensMissingValuesTest() {
		Class[] parameterTypes = { java.lang.String.class };
		String csvLine = "MAGGIE,,FEMALE,GOLDEN RETRIEVER,,";
		Object[] parameters = { csvLine };
		Method prepareTokens = getPrivateMethod(UploadServiceImpl.class, "prepareTokens", parameterTypes);
		String[] expectedTokens = { "MAGGIE", "UNKNOWN", "FEMALE", "GOLDEN RETRIEVER", "UNKNOWN", "" };

		try {
			String[] resultTokens = (String[]) prepareTokens.invoke(uploadService, parameters);
			for (int i = 0; i < expectedTokens.length; i++) {
				assertEquals(expectedTokens[i], resultTokens[i]);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method prepareTokens() fills in "UNKNOWN" for
	 * the first 5 CSV line columns if they are empty and the present dog ID in
	 * the 6th.
	 */
	@Test
	public void prepareTokensDogIdExistsTest() {
		Class[] parameterTypes = { java.lang.String.class };
		String csvLine = "MAGGIE,,FEMALE,GOLDEN RETRIEVER,,0123456789123456";
		Object[] parameters = { csvLine };
		Method prepareTokens = getPrivateMethod(UploadServiceImpl.class, "prepareTokens", parameterTypes);
		String[] expectedTokens = { "MAGGIE", "UNKNOWN", "FEMALE", "GOLDEN RETRIEVER", "UNKNOWN", VALID_DOG_ID_NUMBER };

		try {
			String[] resultTokens = (String[]) prepareTokens.invoke(uploadService, parameters);
			for (int i = 0; i < expectedTokens.length; i++) {
				assertEquals(expectedTokens[i], resultTokens[i]);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method prepareTokens() fills in all the
	 * correct tokens for a CSV line that contains all requested values/columns.
	 */
	@Test
	public void prepareTokensAllTokensExistTest() {
		Class[] parameterTypes = { java.lang.String.class };
		String csvLine = "MAGGIE,NEUTERED,FEMALE,GOLDEN RETRIEVER,BROWN,0123456789123456";
		Object[] parameters = { csvLine };
		Method prepareTokens = getPrivateMethod(UploadServiceImpl.class, "prepareTokens", parameterTypes);
		String[] expectedTokens = { "MAGGIE", "NEUTERED", "FEMALE", "GOLDEN RETRIEVER", "BROWN", VALID_DOG_ID_NUMBER };

		try {
			String[] resultTokens = (String[]) prepareTokens.invoke(uploadService, parameters);
			for (int i = 0; i < expectedTokens.length; i++) {
				assertEquals(expectedTokens[i], resultTokens[i]);
			}
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method extractIdNumberFromIdString() will
	 * throw an IllegalArgumentException if a null String is passed
	 */
	@Test
	public void extractIdNumberFromIdStringNullParameterTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { null };
		Method extractIdNumberFromIdString = getPrivateMethod(UploadServiceImpl.class, "extractIdNumberFromIdString", parameterTypes);
		try {
			extractIdNumberFromIdString.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method extractIdNumberFromIdString() will
	 * fail if an id string that isn't length 21 is passed
	 */
	@Test
	public void extractIdNumberFromIdStringInvalidIdLengthTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "1234" };
		Method extractIdNumberFromIdString = getPrivateMethod(UploadServiceImpl.class, "extractIdNumberFromIdString", parameterTypes);
		try {
			extractIdNumberFromIdString.invoke(uploadService, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				e.getCause().printStackTrace();
				fail();
			}
		}
	}

	/*
	 * This tests that the private method validIdExists() will fail if an id
	 * that isn't 16 digits is passed
	 */
	@Test
	public void extractIdNumberFromIdStringNormalTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { VALID_DOG_ID_STRING };
		Method extractIdNumberFromIdString = getPrivateMethod(UploadServiceImpl.class, "extractIdNumberFromIdString", parameterTypes);
		try {
			String result = (String) extractIdNumberFromIdString.invoke(uploadService, parameters);
			assertEquals(result, VALID_DOG_ID_NUMBER);
		} catch (Exception e) {
			fail();
		}
	}
}
