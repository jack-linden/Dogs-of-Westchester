package model;

public class TrendData implements Comparable<TrendData> {
	private String value;
	private Integer count;

	/**
	 * Class constructor
	 * 
	 * @param value	
	 */
	public TrendData(String value) {
		this.setValue(value);
		this.setCount(0);
	}

	/**
	 * Class constructor
	 * 
	 * @param value	
	 * @param count
	 */
	public TrendData(String value, Integer count) {
		this.setValue(value);
		this.setCount(count);
	}

	
	/**
	 * Getter and Setter for the private field: count
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Getter, Setter, and Incrementer for the private field: value
	 */
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void incrementCount() {
		this.count++;
	}
	
	/**
	 * In case of equal counts, this method compares the value field
	 */
	public int compareTo(TrendData otherTrendData) {
		if( this.count == otherTrendData.count ){
			return otherTrendData.value.compareTo(this.value);
		}
		
		return this.count.compareTo(otherTrendData.count);
	}

	/**
	 * This method prints our the TrendData in plain text
	 * 
	 * @ return a String of the Trend Data
	 */
	public String toString() {
		return value + "," + count;
	}
}
