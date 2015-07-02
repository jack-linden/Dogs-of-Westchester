package model;

import static org.junit.Assert.*;
import org.junit.*;

public class DogTest {
	
	/*
	 * TC1.equals()
	 * 
	 * This test the equals() method of the Dog class
	 * pass in two dogs with the same location, name, condition, sex, breed and color but different idNumber
	 * expects that the two dogs are not the same
	 */
	@Test
	public void dogsWithDifferentIdTest() {
		Dog dog1 = new Dog("00000001","white plains", "lucky", "altered", "male", "boston terrier", "red and white");
		Dog dog2 = new Dog("00000002","white plains", "lucky", "altered", "male", "boston terrier", "red and white");	
		assertFalse(dog1.equals(dog2));
	}		
}
