//package services;
//
//import static org.junit.Assert.assertArrayEquals;
//import java.io.IOException;
//import java.io.FileNotFoundException;
//import org.junit.Before;
//import org.junit.Test;
//
//public class UploadServiceTest {
//	public UploadService uploadService;
//	public final String NON_EXISTENT_FILE = "NON_EXISTENT_FILE.lol";
//
//	@Before
//	public void prepareTests() {
//		uploadService = UploadService.getInstance();
//	}
//
//	/*
//	 * TC1.nullParameterFilenameTest
//	 * 
//	 * Tests that the uploadCSV method will throw an illegal argument exception
//	 * if a null is passed
//	 */
//	@Test(expected = IllegalArgumentException.class)
//	public void nullParameterFilenameTest() throws IOException {
//		uploadService.uploadCSV(null, true);
//	}
//
//	/*
//	 * TC2.nonExistentFileTest
//	 * 
//	 * Tests that the uploadCSV method will throw a file not found exception if
//	 * the file does not exist
//	 */
//	@Test(expected = FileNotFoundException.class)
//	public void nonExistentFileTest() throws IOException {
//		uploadService.uploadCSV(NON_EXISTENT_FILE, true);
//	}
//
//		
//	/*
//	 * TC3.parseWellFormattedCSV
//	 * 
//	 * Tests that the uploadCSV method can parse a well formatted csv file
//	 * correctly Expects an array of dog info
//	 */
//	@Test
//	public void wellFormattedCSVFileTest() throws IOException {
//		uploadService.uploadCSV("White_Plains.csv", true);
//		String[] expected = {"WHITE PLAINS", "DUKE", "ALTERED", "MALE", "GREAT DANE", "BLACK", "WHITE PLAINS", "RICO", "INTACT", "MALE", "CHIHUAHUA",
//				"GREY/BLUEMERLE", "WHITE PLAINS", "LUCKY", "ALTERED", "MALE", "BOSTON TERRIER", "RED AND WHITE"};
//		assertArrayEquals(expected, uploadService.mockDB.toArray());
//	}
//
//	/*
//	 * TC4.parseBadlyFormattedCSV
//	 * 
//	 * Tests that the uploadCSV method will ignore the lines that are not
//	 * well-formated in the csv file Expects an array of dog info
//	 */
//	@Test
//	public void badlyFormattedCSVFileTest() throws IOException {
//		uploadService.uploadCSV("White_Plains_bad.csv", true);
//		String[] expected = {"WHITE PLAINS", "LUCKY", "ALTERED", "MALE", "BOSTON TERRIER", "RED AND WHITE"};
//		assertArrayEquals(expected, uploadService.mockDB.toArray());
//	}
//}
