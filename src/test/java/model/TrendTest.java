package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TrendTest {
	public Trend trend;

	/*
	 * This is a helper method that creates and returns a dog
	 */
	private Dog createDog(String value, TrendType type) {

		Dog dog = new Dog();

		if (type == TrendType.MOST_POPULAR_NAME) {
			dog.setName(value);
		} else if (type == TrendType.MOST_POPULAR_BREED) {
			dog.setBreed(value);
		}

		return dog;
	}

	/*
	 * This is a helper method that sets up tests on private methods with
	 * reflection
	 */
	public Method getPrivateMethod(Class<Trend> targetClass, String methodName, Class[] parameterTypes) {

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

	/*
	 * This tests that the createTendData method will throw an
	 * IllegalArgumentException if a null is passed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createTendDataNullParameterTest() {
		trend = new Trend(TrendType.MOST_POPULAR_BREED);
		trend.createTrendData(null);
	}

	/*
	 * This tests that the createTendData method will throw an
	 * IllegalArgumentException if a an empty set is passed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createTendDataEmptyDogSetTest() {
		trend = new Trend(TrendType.MOST_POPULAR_BREED);
		trend.createTrendData(new HashSet<Dog>());
	}

	/*
	 * This tests that the createTendData method will return the correctly
	 * aggregate the most popular name data into TrendData objects and store
	 * those objects in a List sorted by count. This list is then stored within
	 * the Trend object
	 */
	@Test
	public void createTendDataPopularNameNormalTest() {
		trend = new Trend(TrendType.MOST_POPULAR_NAME);
		Set<Dog> dogList = new HashSet<Dog>();
		dogList.add(createDog("Maggie", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Maggie", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Maggie", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Maggie", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Jack", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Jack", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Jack", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Scott", TrendType.MOST_POPULAR_NAME));
		dogList.add(createDog("Scott", TrendType.MOST_POPULAR_NAME));

		List<TrendData> expectedTrendDataList = new ArrayList<TrendData>();
		expectedTrendDataList.add(new TrendData("Maggie", 4));
		expectedTrendDataList.add(new TrendData("Jack", 3));
		expectedTrendDataList.add(new TrendData("Scott", 2));
		Collections.sort(expectedTrendDataList, Collections.reverseOrder());

		trend.createTrendData(dogList);
		List<TrendData> returnedTrendDataList = trend.getTrendData();

		for (int i = 0; i < expectedTrendDataList.size(); i++) {
			TrendData observed = returnedTrendDataList.get(i);
			TrendData expected = expectedTrendDataList.get(i);
			assertEquals(expected.getValue(), observed.getValue());
			assertEquals(expected.getCount(), observed.getCount());
		}
	}

	/*
	 * This tests that the createTendData method will return the correctly
	 * aggregate the most popular breed data into TrendData objects and store
	 * those objects in a List sorted by count. This list is then stored within
	 * the Trend object
	 */
	@Test
	public void createTendDataPopularBreedNormalTest() {
		trend = new Trend(TrendType.MOST_POPULAR_BREED);
		Set<Dog> dogList = new HashSet<Dog>();
		dogList.add(createDog("Golden Retriever", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Golden Retriever", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Golden Retriever", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Golden Retriever", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Jack Russel Terrier", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Jack Russel Terrier", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Jack Russel Terrier", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Lab", TrendType.MOST_POPULAR_BREED));
		dogList.add(createDog("Lab", TrendType.MOST_POPULAR_BREED));

		List<TrendData> expectedTrendDataList = new ArrayList<TrendData>();
		expectedTrendDataList.add(new TrendData("Golden Retriever", 4));
		expectedTrendDataList.add(new TrendData("Jack Russel Terrier", 3));
		expectedTrendDataList.add(new TrendData("Lab", 2));
		Collections.sort(expectedTrendDataList, Collections.reverseOrder());

		trend.createTrendData(dogList);
		List<TrendData> returnedTrendDataList = trend.getTrendData();

		for (int i = 0; i < expectedTrendDataList.size(); i++) {
			TrendData observed = returnedTrendDataList.get(i);
			TrendData expected = expectedTrendDataList.get(i);
			assertEquals(expected.getValue(), observed.getValue());
			assertEquals(expected.getCount(), observed.getCount());
		}
	}

	/*
	 * This tests that the getDogValueBasedOnTrendType private method will throw
	 * an IllegalArgumentException if a null is passed
	 */
	@Test
	public void getDogValueBasedOnTrendTypeNullParameterTest() {
		trend = new Trend(TrendType.MOST_POPULAR_BREED);
		Dog dog = new Dog("1", "Maggie", "Neutered", "Female", "Golden Retriever", "Brown", "NYC");
		Class[] parameterTypes = { Dog.class };
		Object[] parameters = { null };
		Method getDogValueBasedOnTrendType = getPrivateMethod(Trend.class, "getDogValueBasedOnTrendType", parameterTypes);

		try {
			getDogValueBasedOnTrendType.invoke(trend, parameters);
			fail();
		} catch (Exception e) {
			if (!(e.getCause() instanceof IllegalArgumentException)) {
				fail();
			}
		}
	}

	/*
	 * This tests that the getDogValueBasedOnTrendType private method return the
	 * correct dog field (dogName) based on the type of trend that is being
	 * calculated
	 */
	@Test
	public void getDogValueBasedOnTrendTypeNormalNameTest() {
		trend = new Trend(TrendType.MOST_POPULAR_NAME);
		Dog dog = new Dog("1", "Maggie", "Neutered", "Female", "Golden Retriever", "Brown", "NYC");
		Class[] parameterTypes = { Dog.class };
		Object[] parameters = { dog };
		Method getDogValueBasedOnTrendType = getPrivateMethod(Trend.class, "getDogValueBasedOnTrendType", parameterTypes);

		try {
			String value = (String) getDogValueBasedOnTrendType.invoke(trend, parameters);
			assertEquals(value, dog.getName());
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the getDogValueBasedOnTrendType private method return the
	 * correct dog field (dogBreed) based on the type of trend that is being
	 * calculated
	 */
	@Test
	public void getDogValueBasedOnTrendTypeNormalBreedTest() {
		trend = new Trend(TrendType.MOST_POPULAR_BREED);
		Dog dog = new Dog("1", "Maggie", "Neutered", "Female", "Golden Retriever", "Brown", "NYC");
		Class[] parameterTypes = { Dog.class };
		Object[] parameters = { dog };
		Method getDogValueBasedOnTrendType = getPrivateMethod(Trend.class, "getDogValueBasedOnTrendType", parameterTypes);

		try {
			String value = (String) getDogValueBasedOnTrendType.invoke(trend, parameters);
			assertEquals(value, dog.getBreed());
		} catch (Exception e) {
			fail();
		}
	}

	/*
	 * This tests that the getDogValueBasedOnLocationType private method return
	 * the correct dog field (dogLocation) based on the type of trend that is
	 * being calculated
	 */
	@Test
	public void getDogValueBasedOnTrendTypeNormalLocationTest() {
		trend = new Trend(TrendType.MOST_POPULATED_CITY);
		Dog dog = new Dog("1", "Maggie", "Neutered", "Female", "Golden Retriever", "Brown", "NYC");
		Class[] parameterTypes = { Dog.class };
		Object[] parameters = { dog };
		Method getDogValueBasedOnTrendType = getPrivateMethod(Trend.class, "getDogValueBasedOnTrendType", parameterTypes);

		try {
			String value = (String) getDogValueBasedOnTrendType.invoke(trend, parameters);
			assertEquals(value, dog.getLocation());
		} catch (Exception e) {
			fail();
		}
	}
}
