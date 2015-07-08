package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class DogTest {
	
	/*
	 * TC1.equals()
	 * 
	 * This tests the equals() method of the Dog class
	 * pass in two dogs with the same location, name, condition, sex, breed and color but different idNumber
	 * expects that the two dogs are not the same
	 */
	@Test
	public void dogsWithDifferentIdTest() {
		Dog dog1 = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		Dog dog2 = new Dog("00000002", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");	
		assertFalse(dog1.equals(dog2));
	}		
	
	/*
	 * TC2.getIdNumber()
	 * 
	 * This tests the getIdNumber method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetID(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("00000001", dog.getIdNumber());
	}
	
	/*
	 * TC3.setIdNumber()
	 * 
	 * This tests the setIdNumber method of the Dog class
	 * creates an initialized dog object, and changes the idNumber value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetID(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setIdNumber("00000002");
		assertEquals("00000002", dog.getIdNumber());
	}
	
	/*
	 * TC4.getName()
	 * 
	 * This tests the getName method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetName(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("lucky", dog.getName());
	}
	
	/*
	 * TC5.setName()
	 * 
	 * This tests the setName method of the Dog class
	 * creates an initialized dog object, and changes the name value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetName(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setName("scooby");
		assertEquals("scooby", dog.getName());
	}
	
	/*
	 * TC6.getCondition()
	 * 
	 * This tests the getCondition method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetCondition(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("altered", dog.getCondition());
	}
	
	/*
	 * TC7.setCondition()
	 * 
	 * This tests the setCondition method of the Dog class
	 * creates an initialized dog object, and changes the condition value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetCondition(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setCondition("intact");
		assertEquals("intact", dog.getCondition());
	}
	
	/*
	 * TC8.getSex()
	 * 
	 * This tests the getSex method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetSex(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("male", dog.getSex());
	}
	
	/*
	 * TC9.setSex()
	 * 
	 * This tests the setSex method of the Dog class
	 * creates an initialized dog object, and changes the sex value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetSex(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setSex("female");
		assertEquals("female", dog.getSex());
	}
	
	/*
	 * TC10.getBreed()
	 * 
	 * This tests the getBreed method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetBreed(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("boston terrier", dog.getBreed());
	}
	
	/*
	 * TC11.setBreed()
	 * 
	 * This tests the setBreed method of the Dog class
	 * creates an initialized dog object, and changes the breed value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetBreed(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setBreed("labrador");
		assertEquals("labrador", dog.getBreed());
	}
	
	/*
	 * TC12.getColor()
	 * 
	 * This tests the getColor method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetColor(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("red and white", dog.getColor());
	}
	
	/*
	 * TC13.setColor()
	 * 
	 * This tests the setColor method of the Dog class
	 * creates an initialized dog object, and changes the color value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetColor(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setColor("chocolate");
		assertEquals("chocolate", dog.getColor());
	}
	
	/*
	 * TC14.getLocation()
	 * 
	 * This tests the getLocation method of the Dog class
	 * creates an initialized dog object
	 * expects that initialized value is the same as the tested one
	 */
	@Test
	public void dogGetLocation(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		assertEquals("white plains", dog.getLocation());
	}
	
	/*
	 * TC15.setLocation()
	 * 
	 * This tests the setLocation method of the Dog class
	 * creates an initialized dog object, and changes the location value
	 * expects that changed value is the same as the tested one
	 */
	@Test
	public void dogSetLocation(){
		Dog dog = new Dog("00000001", "lucky", "altered", "male", "boston terrier", "red and white", "white plains");
		dog.setLocation("yorktown");
		assertEquals("yorktown", dog.getLocation());
	}
}
