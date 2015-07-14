package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DogTest {

	private final String DEFAULTIDNUMBER = "";
	private final String DEFAULTSEX = "UNKNOWN";

	/*
	 * This tests the equals() method of the Dog class pass in two dogs with the
	 * same location, name, condition, sex, breed and color but different
	 * idNumber expects that the two dogs are not the same
	 */
	@Test
	public void dogsWithDifferentIdTest() {
		Dog dog1 = new Dog("0000000000000001", "lucky", "altered", "female", "boston terrier", "red and white", "white plains");
		Dog dog2 = new Dog("0000000000000002", "lucky", "altered", "female", "boston terrier", "red and white", "white plains");
		assertFalse(dog1.equals(dog2));
	}

	/*
	 * This tests the equals() method of the Dog class pass in two dogs with
	 * different location, name, breed and color information but has the same
	 * idNumber expects that the two dogs are the same
	 */
	@Test
	public void dogsWithTheSameIdTest() {
		Dog dog1 = new Dog("0000000000000010", "lucky", "altered", "female", "labrador", "red & white", "white plains");
		Dog dog2 = new Dog("0000000000000010", "maggie", "altered", "female", "boston terrier", "red and white", "nyc");
		assertTrue(dog1.equals(dog2));
	}

	/*
	 * This tests the getIdNumber() method of the Dog class creates an
	 * initialized dog object expects that initialized value is the same as the
	 * tested one
	 */
	@Test
	public void dogGetID() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("0000000000000001", dog.getIdNumber());
	}

	/*
	 * This tests the setIdNumber() method of the Dog class creates an
	 * initialized dog object, and changes the idNumber to an invalid expects
	 * that the idNumber will be set to a default value
	 */
	@Test
	public void dogSetID() {
		Dog dog = new Dog("oooooooooooooool", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setIdNumber("oooooooooooooool");
		assertEquals(DEFAULTIDNUMBER, dog.getIdNumber());
	}

	/*
	 * This tests the getName() method of the Dog class creates an initialized
	 * dog object expects that initialized value is the same as the tested one
	 * 
	 * Yes! There is a dog named 2011 found in our database!
	 */
	@Test
	public void dogGetName() {
		Dog dog = new Dog("0000000000000001", "2011", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("2011", dog.getName());
	}

	/*
	 * This tests the setName() method of the Dog class creates an initialized
	 * dog object, and changes the name value expects that changed value is the
	 * same as the tested one
	 */
	@Test
	public void dogSetName() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setName("pink!");
		assertEquals("pink!", dog.getName());
	}

	/*
	 * This tests the getCondition() method of the Dog class creates an
	 * initialized dog object expects that initialized value is the same as the
	 * tested one
	 */
	@Test
	public void dogGetCondition() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("altered", dog.getCondition());
	}

	/*
	 * This tests the setCondition() method of the Dog class creates an
	 * initialized dog object, and changes the condition value expects that
	 * changed value is the same as the tested one
	 */
	@Test
	public void dogSetCondition() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setCondition("intact");
		assertEquals("intact", dog.getCondition());
	}

	/*
	 * This tests the getSex() method of the Dog class creates an initialized
	 * dog object expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetSex() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("male", dog.getSex());
	}

	/*
	 * This tests the setSex() method of the Dog class creates an initialized
	 * dog object, and changes the sex to an invalid value expects that the sex
	 * will be set to a default value
	 */
	@Test
	public void dogSetSex() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setSex("both");
		assertEquals(DEFAULTSEX, dog.getSex());
	}

	/*
	 * This tests the getBreed() method of the Dog class creates an initialized
	 * dog object expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetBreed() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("boston terrier", dog.getBreed());
	}

	/*
	 * This tests the setBreed() method of the Dog class creates an initialized
	 * dog object, and changes the breed value expects that changed value is the
	 * same as the tested one
	 */
	@Test
	public void dogSetBreed() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setBreed("labrador");
		assertEquals("labrador", dog.getBreed());
	}

	/*
	 * This tests the getColor() method of the Dog class creates an initialized
	 * dog object expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetColor() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red & white", "white plains");
		assertEquals("red & white", dog.getColor());
	}

	/*
	 * This tests the setColor() method of the Dog class creates an initialized
	 * dog object, and changes the color value expects that changed value is the
	 * same as the tested one
	 */
	@Test
	public void dogSetColor() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setColor("chocolate");
		assertEquals("chocolate", dog.getColor());
	}

	/*
	 * This tests the getLocation() method of the Dog class creates an
	 * initialized dog object expects that initialized value is the same as the
	 * tested one
	 */
	@Test
	public void dogGetLocation() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("white plains", dog.getLocation());
	}

	/*
	 * This tests the setLocation() method of the Dog class creates an
	 * initialized dog object, and changes the location value expects that
	 * changed value is the same as the tested one
	 */
	@Test
	public void dogSetLocation() {
		Dog dog = new Dog("0000000000000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setLocation("yorktown");
		assertEquals("yorktown", dog.getLocation());
	}
}
