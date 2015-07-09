package services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import model.Dog;

public class UploadService extends HttpServlet {
	private final int IDNUMBERLENGTH = 16;	

	private static UploadService _instance = null;

	/**
	 * Class constructor.
	 * 
	 */
	protected UploadService() {

	}

	/**
	 * Class constructor.
	 * 
	 * Used singleton pattern and lazy thread-safe implementation
	 */
	public static UploadService getInstance() {
		if (_instance == null) {
			synchronized (UploadService.class) {
				if (_instance == null) {
					_instance = new UploadService();
				}
			}
		}
		return _instance;
	}

	/**
	 * This function will upload a CSV file to google's datastore. Refer to
	 * excel template for formatting.
	 * 
	 * @param fileContents
	 *            The content of the data file to upload
	 * @throws IOException
	 */
	public byte[] uploadCSV(byte[] fileContents) throws IOException {

		if (fileContents == null || fileContents.length == 0) {
			throw new IllegalArgumentException("Did not expect a null filename argument or an empty/null byte array.");
		}

		InputStream is = new ByteArrayInputStream(fileContents);
		DogDao dogDao = new DogDaoImpl();

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String firstLine = in.readLine();
		in.readLine(); // Skip the excel headers
		String cityName = getCityName(firstLine);

		StringBuilder sb = new StringBuilder();
		for (String line = in.readLine(); line != null; line = in.readLine()) {
			String[] tokens = prepareTokens(line.toUpperCase().split(","));

			Dog dog = new Dog();
			dog.setLocation(cityName);
			dog.setName(tokens[0]);
			dog.setCondition(tokens[1]);
			dog.setSex(tokens[2]);
			dog.setBreed(tokens[3]);
			dog.setColor(tokens[4]);
			String idNumber = tokens[5];
			String newCSVLine = line;

			if (!validIdExists(idNumber)) {
				String newIdNumber = dogDao.insertDog(dog);
				newCSVLine = appendDogIdToCSVLine(line, newIdNumber);
			}
			sb.append(newCSVLine);
			sb.append("\n");
		}
		return sb.toString().getBytes();
	}

	/**
	 * This is a helper method that changes all the empty data fields of a dog
	 * to be "UNKNOWN"
	 * 
	 * @param tokens
	 *            a list of dog properties (eg. Name, Sex, Color)
	 * @return a list of non-empty string tokens
	 */
	private String[] prepareTokens(String[] tokens) {
		String[] newTokens = new String[6];
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals("")) {
				newTokens[i] = "UNKNOWN";
			} else{
				newTokens[i] = tokens[i];
			}
		}
		if (tokens.length == 6) {
			if (tokens[5].equals("")) {
				newTokens[5] = "UNKNOWN";
			} else {
				newTokens[5] = tokens[5];
			}
		}
		if(newTokens[5] == null){
			newTokens[5] = "UNKNOWN";
		}

		return newTokens;
	}

	/**
	 * This is a helper method that checks the validity of the dog idNumber
	 * 
	 * @param idNumber
	 *            the idNumber to be validated
	 * @return true if idNumber is 16-digit long, otherwise return false
	 */
	private boolean validIdExists(String idNumber) {
		if( idNumber == null ){
			throw new IllegalArgumentException("Expected a non-null idNumber String.");
		}
		return idNumber.matches("[0-9]+") && idNumber.length() == IDNUMBERLENGTH;		 
	}

	/**
	 * This is a helper method that appends the idNumber of the dog returned by
	 * the database to a CSV file
	 * 
	 * @param line
	 *            the specific line that the idNumber should be appended to
	 * @param idNumber
	 *            the idNumber to be appended
	 * @return String of dog data that now has the idNumber appended to the end
	 */
	private String appendDogIdToCSVLine(String line, String idNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(idNumber);
		return sb.toString();
	}

	/**
	 * This is a helper method that parses first line of CSV file and returns
	 * the city name which is located at the first cell.
	 * 
	 * @param line
	 * @return String of city name.
	 */
	private String getCityName(String line) {
		return line.split(",")[0].toUpperCase();
	}
}
