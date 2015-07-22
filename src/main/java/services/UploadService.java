package services;

import java.io.IOException;

import dataaccess.DogDaoImpl;

public interface UploadService {

	/*
	 * Sets dogDao (Dog data-access object)
	 */
	public void setDogDao(DogDaoImpl mockedDogDao);
	
	/**
	 * This function will upload a CSV file to google's datastore. It will
	 * return a byte array output file that contains each stored dogs'
	 * information and new idNumber. Refer to the excel template in test-files.
	 * 
	 * @param fileContents
	 * @throws IOException
	 * @return byte[] containing contents of the new excel file with appended
	 *         dog id's on each line
	 */
	public byte[] uploadCSV(byte[] fileContents) throws IOException;

}
