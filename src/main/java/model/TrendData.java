package model;

public class TrendData implements Comparable<TrendData> {
	private String value;
	private Integer count;

	public TrendData(String value) {
		this.setValue(value);
		this.setCount(0);
	}

	public TrendData(String value, Integer count) {
		this.setValue(value);
		this.setCount(count);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void incrementCount() {
		this.count++;
	}

	//In case of equal counts, compares value field
	public int compareTo(TrendData otherTrendData) {
		if( this.count == otherTrendData.count ){
			return otherTrendData.value.compareTo(this.value);
		}
		
		return this.count.compareTo(otherTrendData.count);
	}

	public String toString() {
		return value + "," + count;
	}

}
