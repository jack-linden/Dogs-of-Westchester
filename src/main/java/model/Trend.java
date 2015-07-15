package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Trend {

	private List<TrendData> trendData;
	private TrendType trendType;

	public Trend(TrendType trendType) {
		setTrendData(new ArrayList<TrendData>());
		setTrendType(trendType);
	}

	public List<TrendData> getTrendData() {
		return trendData;
	}

	public void setTrendData(List<TrendData> trendData) {
		this.trendData = trendData;
	}

	public TrendType getTrendType() {
		return trendType;
	}

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
		if (dogs == null || dogs.isEmpty()) {
			throw new IllegalArgumentException("Did not expect a null or empty set of dogs.");
		}
		Map<String, TrendData> trendDataMap = new HashMap<String, TrendData>();
		for (Dog dog : dogs) {
			String value = getDogValueBasedOnTrendType(dog);
			if (trendDataMap.containsKey(value)) {
				trendDataMap.get(value).incrementCount();
			} else {
				trendDataMap.put(value, new TrendData(value, 1));
			}
		}

		List<TrendData> sortedTrendData = new ArrayList<TrendData>(trendDataMap.values());
		Collections.sort(sortedTrendData, Collections.reverseOrder());

		this.setTrendData(sortedTrendData);
	}

	/**
	 * This method returns the correct dog field based on the Trend's private
	 * field TrendType
	 * 
	 * @param dog
	 * @return field of dog (String) based on TrendType
	 */
	private String getDogValueBasedOnTrendType(Dog dog) {
		if (dog == null) {
			throw new IllegalArgumentException("Did not expect a null dog.");
		}
		String dogValue = "";
		if (trendType == TrendType.MOST_POPULAR_NAME) {
			dogValue = dog.getName();
		} else if (trendType == TrendType.MOST_POPULAR_BREED) {
			dogValue = dog.getBreed();
		}

		return dogValue;
	}
}
