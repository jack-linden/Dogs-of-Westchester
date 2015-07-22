package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Trend {

	// This is the list of trend data object with count information
	private List<TrendData> trendData;
	// This is the TrendType enum defines type of trend
	private TrendType trendType;

	/**
	 * Class constructor
	 * 
	 * @param trendType
	 */
	public Trend(TrendType trendType) {
		setTrendData(new ArrayList<TrendData>());
		setTrendType(trendType);
	}

	/**
	 * Getter for trendData
	 * 
	 * @return a list of TrendData
	 */
	public List<TrendData> getTrendData() {
		return trendData;
	}

	/**
	 * Setter for the private field: trendData
	 */
	public void setTrendData(List<TrendData> trendData) {
		this.trendData = trendData;
	}

	/**
	 * Getter for trendType
	 *
	 * @return a TrendType object
	 */
	public TrendType getTrendType() {
		return trendType;
	}

	/**
	 * Setter for the private field: trendType
	 */
	public void setTrendType(TrendType trendType) {
		this.trendType = trendType;
	}

	/**
	 * This method creates a sorted list of trendData that represents the counts
	 * of data given the specified TrendType. It is sorted by count first and
	 * value second.
	 * 
	 * @param dogs
	 */
	public void createTrendData(Set<Dog> dogs) {
		// Illegal input checks, will throw IAException
		if (dogs == null || dogs.isEmpty()) {
			throw new IllegalArgumentException("Did not expect a null or empty set of dogs.");
		}

		// Map of String value to count of the value
		Map<String, TrendData> trendDataMap = new HashMap<String, TrendData>();

		// Iterate over dataset of dogs and store the counts of each specified
		// value
		for (Dog dog : dogs) {
			// Get the dog field we are looking for
			String value = getDogValueBasedOnTrendType(dog);

			// If map contains it, increment the count by one
			// Else put value in map with count of 1
			if (trendDataMap.containsKey(value)) {
				trendDataMap.get(value).incrementCount();
			} else {
				trendDataMap.put(value, new TrendData(value, 1));
			}
		}

		List<TrendData> sortedTrendData = new ArrayList<TrendData>(trendDataMap.values());

		// Sort data by counts
		Collections.sort(sortedTrendData, Collections.reverseOrder());
		int numberOfResults = sortedTrendData.size() < 10 ? sortedTrendData.size() : 10;

		// Set trendData field
		this.setTrendData(sortedTrendData.subList(0, numberOfResults));
	}

	/**
	 * This method returns the correct dog field based on the Trend's private
	 * field TrendType
	 * 
	 * @param dog
	 * @return field of dog (String) based on TrendType
	 */
	private String getDogValueBasedOnTrendType(Dog dog) {
		// Illegal input checks, will throw IAException
		if (dog == null) {
			throw new IllegalArgumentException("Did not expect a null dog.");
		}
		String dogValue = "";

		// Return the dogValue according to the type of trend
		if (trendType == TrendType.MOST_POPULAR_NAME) {
			dogValue = dog.getName();
		} else if (trendType == TrendType.MOST_POPULAR_BREED) {
			dogValue = dog.getBreed();
		} else if (trendType == TrendType.MOST_POPULATED_CITY) {
			dogValue = dog.getLocation();
		}

		return dogValue;
	}
}
