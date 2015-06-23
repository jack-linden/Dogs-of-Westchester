package services;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class UploadServiceTest {
	public UploadService uploadService;
	
	@Before
	public void prepareTests( ){
		uploadService = UploadService.getInstance();
	}
	
	/*
	 *  TC1.parseWellFormattedCSV
	 *  
	 *  Tests that the uploadCSV method can parse a well formatted csv file correctly
	 *  Expects an array of dog info
	 */
	@Test
	public void wellFormattedCSVFileTest( ) throws IOException{
		uploadService.uploadCSV("White_Plains.csv", true);
		String[] expected = {"White Plains", "Duke", "Altered", "Male", "Great Dane", "Black", 
				"White Plains", "Rico", "Intact", "Male", "Chihuahua", "Grey/Bluemerle", 
				"White Plains", "Lucky", "Altered" ,"Male", "Boston Terrier", "Red and white"};
		assertArrayEquals(expected, uploadService.mockDB.toArray());
	}
	
	/*
	 *  TC2.parseBadlyFormattedCSV
	 *  
	 *  Tests that the uploadCSV method will ignore the lines that are not well-formated in the csv file
	 *  Expects an array of dog info
	 */
	@Test
	public void badlyFormattedCSVFileTest( ) throws IOException{
		uploadService.uploadCSV("White_Plains_bad.csv", true);
		String[] expected = {"White Plains", "Lucky", "Altered" ,"Male", "Boston Terrier", "Red and white"};
		assertArrayEquals(expected, uploadService.mockDB.toArray());
	}
			
	
}
