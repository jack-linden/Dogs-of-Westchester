package model;

public class Dog {

	private String idNumber;
	private String name;
	private String condition;
	private String sex;
	private String breed;
	private String color;
	private String location;

	public Dog() {

	}

	public Dog(String idNumber, String name, String condition, String sex, String breed, String color, String location) {
		this.setIdNumber(idNumber);
		this.setName(name);
		this.setCondition(condition);
		this.setSex(sex);
		this.setBreed(breed);
		this.setColor(color);
		this.setLocation(location);
	}

	/*
	 * Getters and Setters for DogRecord fields.
	 */
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/*
	 * Override the equals() method
	 */
	public boolean equals(Dog other) {
		if (this.idNumber.equals(other.idNumber))
			return true;
		else
			return false;
	}
}
