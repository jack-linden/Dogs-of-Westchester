package model;

public class Dog {

	private String idNumber;
	private String name;
	private String condition;
	private String sex;
	private String breed;
	private String color;
	private String location;

	private final int IDNUMBERLENGTH = 16;
	private final String DEFAULTIDNUMBER = "0000000000000000";
	private final String DEFAULTSEX = "UNKNOWN";
	
	/**
	 * Class constructor.
	 */
	public Dog() {

	}

	/**
	 * Class constructor specifying the properties of the dog.
	 */
	public Dog(String idNumber, String name, String condition, String sex, String breed, String color, String location) {
		this.setIdNumber(idNumber);
		this.setName(name);
		this.setCondition(condition);
		this.setSex(sex);
		this.setBreed(breed);
		this.setColor(color);
		this.setLocation(location);
	}

	/**
	 * Override the equals() method so that Dog objects are comparable.
	 * 
	 * @param other
	 *            the other Dog object to be compared.
	 * @return true if two Dog objects have the same idNumber false if two Dog
	 *         objects have different idNumber
	 */
	public boolean equals(Dog other) {
		return this.idNumber.equals(other.idNumber);
	}

	/**
	 * Getter and Setter for the private field idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		if (idNumber.matches("[0-9]+") && idNumber.length() == IDNUMBERLENGTH)			
			this.idNumber = idNumber;	
		else
			this.idNumber = DEFAULTIDNUMBER;
	}

	/**
	 * Getter and Setter for the private field name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter and Setter for the private field condition
	 */
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Getter and Setter for the private field sex
	 */
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		if (sex.equalsIgnoreCase("male") || sex.equalsIgnoreCase("female") || sex.equalsIgnoreCase("unknown"))
			this.sex = sex;
		else
			this.sex = DEFAULTSEX;
	}

	/**
	 * Getter and Setter for the private field breed
	 */
	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	/**
	 * Getter and Setter for the private field color
	 */
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Getter and Setter for the private field location
	 */
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
