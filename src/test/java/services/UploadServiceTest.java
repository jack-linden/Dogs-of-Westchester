package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;

public class UploadServiceTest {

	public UploadService uploadService;
	private DogDao mockedDogDao = null;

	// public final String NON_EXISTENT_FILE = "NON_EXISTENT_FILE.lol";

	@Before
	public void prepareTests() {
		uploadService = UploadService.getInstance();
		mockedDogDao = new DogDaoImpl();
	}

	public Method getPrivateMethod(Class<UploadService> targetClass, String methodName, Class[] parameterTypes) {

		Method method = null;
		try {
			method = targetClass.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		method.setAccessible(true);
		return method;
	}

	// uploadCSV Tests
	/*
	 * This tests the uploadCSV method will throw an illegal argument exception
	 * if a null byte array is passed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullParameterTest() throws IOException {
		uploadService.uploadCSV(null);
	}

	/*
	 * This tests that the uploadCSV method will throw an illegal argument
	 * exception if the file contents are empty.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nonExistentFileTest() throws IOException {
		uploadService.uploadCSV(new byte[0]);
	}

	// validIdExists Tests
	/*
	 * This tests that the private method validIdExists will throw an
	 * IllegalArgumentException if a null String is passed
	 */
	@Test
	public void validIdExistsNullParameterTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { null };
		Method validIdExists = getPrivateMethod(UploadService.class, "validIdExists", parameterTypes);
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
	 * This tests that the private method validIdExists will fail if an id that
	 * isn't 16 digits is passed
	 */
	@Test
	public void validIdExistsInvalidIdLengthTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "1234" };
		Method validIdExists = getPrivateMethod(UploadService.class, "validIdExists", parameterTypes);
		try {
			boolean isValidId = (Boolean) validIdExists.invoke(uploadService, parameters);
			assertFalse(isValidId);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method validIdExists will fail if an id
	 * contains non-numerical characters
	 */
	@Test
	public void validIdExistsInvalidCharactersTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "EEEEEE12EEEEEEEE" };
		Method validIdExists = getPrivateMethod(UploadService.class, "validIdExists", parameterTypes);
		try {
			boolean isValidId = (Boolean) validIdExists.invoke(uploadService, parameters);
			assertFalse(isValidId);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the private method validIdExists will pass because a
	 * valid 16-digit numerical id is passed
	 */
	@Test
	public void validIdExistsValidIdTest() {
		Class[] parameterTypes = { java.lang.String.class };
		Object[] parameters = { "0123456789123456" };
		Method validIdExists = getPrivateMethod(UploadService.class, "validIdExists", parameterTypes);
		try {
			boolean isValidId = (Boolean) validIdExists.invoke(uploadService, parameters);
			assertTrue(isValidId);
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the uploadCSV method can parse a well formatted csv file
	 * correctly Expects an array of dog info
	 */
	// @Test
	// public void wellFormattedCSVFileTest() throws IOException {
	// String expected =
	// "WHITE PLAINS,,,,,\nDUKE,ALTERED,MALE,GREAT DANE,BLACK,\n";
	// String newResult =
	// "DUKE,ALTERED,MALE,GREAT DANE,BLACK,0000000000000001\n";
	// uploadService.uploadCSV(expected.getBytes());
	// DogDaoImpl mock = createMock(DogDaoImpl.class);
	// expect(mock.insertDog(new Dog("UNKNOWN", "DUKE", "ALTERED", "MALE",
	// "GREAT DANE", "BLACK", "WHITE PLAINS"))).andReturn("0000000000000001");
	// byte[] returnedByteArr = uploadService.uploadCSV(expected.getBytes());
	// assertEquals(newResult.getBytes(), returnedByteArr);
	// }
}
