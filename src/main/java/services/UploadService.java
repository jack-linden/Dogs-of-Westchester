package services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import model.Dog;
import dataaccess.DogDao;
import dataaccess.DogDaoImpl;

public class UploadService {
	private final int ID_NUMBER_LENGTH = 16;
	private final int EXPECTED_TOKENS_LENGTH = 6;
	private static UploadService _instance = null;
	private DogDao dogDao = null;

	/**
	 * Class constructor.
	 * 
	 */
	protected UploadService() {
		setDogDao(new DogDaoImpl());
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
	 * Class constructor.
	 * 
	 * Used singleton pattern and lazy thread-safe implementation
	 */

	public void setDogDao(DogDaoImpl dogDao) {
		this.dogDao = dogDao;
	}

	/**
	 * This function will upload a CSV file to google's datastore. Refer to
	 * excel template for formatting.
	 * 
	 * @param fileContents
	 *            The content of the data file to upload
	 * @throws IOException
	 * @return byte[] containing contents of the new excel file with appended
	 *         dog id's on each line
	 */
	public byte[] uploadCSV(byte[] fileContents) throws IOException {

		if (fileContents == null || fileContents.length == 0) {
			throw new IllegalArgumentException("Did not expect a null filename argument or an empty/null byte array.");
		}
		// Create InputStream from byte[] for the BufferedReader to read
		InputStream is = new ByteArrayInputStream(fileContents);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		// First two lines contain the city name and excel headers
		String firstLine = in.readLine();
		String cityName = getCityName(firstLine);
		String headersLine = in.readLine();

		// Create StringBuilder and append the first two lines
		StringBuilder sb = new StringBuilder();
		sb.append(firstLine);
		sb.append("\r\n");
		sb.append(headersLine);
		sb.append("\r\n");

		// Iterate through the file line by line storing each dog record
		for (String line = in.readLine(); line != null; line = in.readLine()) {
			String[] tokens = prepareTokens(line.toUpperCase());

			Dog dog = createDogFromTokens(tokens, cityName);
			String idNumber = dog.getIdNumber();
			String newCSVLine = line;

			// If an id doesn't exist we must store this dog in the datastore
			if (!validIdExists(idNumber)) {
				String newDogKeyString = dogDao.insertDog(dog);
				String newIdNumber = extractIdNumberFromIdString(newDogKeyString);
				newCSVLine = appendDogIdToCSVLine(line, newIdNumber);
			}
			sb.append(newCSVLine);
			sb.append("\r\n");
		}

		is.close();// Close the input stream

		return sb.toString().getBytes();
	}

	/**
	 * Creates and returns a Dog object based on the tokens received from the
	 * row in the excel/csv file.
	 * 
	 * @param tokens
	 *            Array of length 6 containing descriptive info for the dog
	 * @param cityName
	 *            Name of city from which the dog is from
	 * @return Dog object containing all the information
	 */
	private Dog createDogFromTokens(String[] tokens, String cityName) {
		if (tokens == null || tokens.length != EXPECTED_TOKENS_LENGTH || cityName == null || cityName.length() == 0) {
			throw new IllegalArgumentException("Expected tokens to be a non-null array of length 6 and cityName to be a non-null non empty string. ");
		}
		Dog dog = new Dog();
		dog.setLocation(cityName);
		dog.setName(tokens[0]);
		dog.setCondition(tokens[1]);
		dog.setSex(tokens[2]);
		dog.setBreed(tokens[3]);
		dog.setColor(tokens[4]);
		dog.setIdNumber(tokens[5]);

		return dog;
	}

	/**
	 * This is a helper method that changes all the empty data fields of a dog
	 * to be "UNKNOWN" except for the 6th field idNumber which is left blank if
	 * empty
	 * 
	 * @param tokens
	 *            a list of dog properties (eg. Name, Sex, Color)
	 * @return a list of non-empty string tokens
	 */
	private String[] prepareTokens(String line) {

		if (line == null || line.length() == 0) {
			throw new IllegalArgumentException("Did not expect line to be null or empty.");
		}
		String[] tokens = line.split(",");
		String[] preparedTokens = new String[6];
		Arrays.fill(preparedTokens, 0, 5, "UNKNOWN");
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].equals("")) {
				preparedTokens[i] = tokens[i];
			}
		}
		if (tokens.length == 6) {
			preparedTokens[5] = tokens[5];
		} else {
			preparedTokens[5] = "";
		}
		return preparedTokens;
	}

	/**
	 * This is a helper method that checks the validity of the dog idNumber
	 * 
	 * @param idNumber
	 *            the idNumber to be validated
	 * @return true if idNumber is 16-digit long, otherwise return false
	 */
	private boolean validIdExists(String idNumber) {
		if (idNumber == null) {
			throw new IllegalArgumentException("Expected a non-null idNumber String.");
		}
		return idNumber.matches("[0-9]+") && idNumber.length() == ID_NUMBER_LENGTH;
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
		if (line == null || idNumber == null || line.length() == 0 || idNumber.length() == 0) {
			throw new IllegalArgumentException("Did not expect line or idNumber Strings to be null or empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(idNumber);
		return sb.toString();
	}

	/**
	 * Google App Engine datastore key strings are in the format
	 * Dog(xxxxxxxxxxxxxxxx) where x is a 16 digit number. This method extracts
	 * that number and returns it as a string
	 * 
	 * @param idString
	 *            Dog(xxxxxxxxxxxxxxxx)
	 * @return xxxxxxxxxxxxxxxx
	 * 
	 */
	private String extractIdNumberFromIdString(String idString) {
		if (idString == null || idString.length() != 21) {
			throw new IllegalArgumentException("Expected a non null key string of length 21.");
		}
		// The idNumber is between these two indices
		return idString.substring(4, 20);
	}

	/**
	 * This is a helper method that parses first line of CSV file and returns
	 * the city name which is located at the first cell.
	 * 
	 * @param line
	 * @return String of city name.
	 */
	private String getCityName(String line) {
		if (line == null || line.length() == 0) {
			throw new IllegalArgumentException("Did not expect line to be null or empty.");
		}
		return line.split(",")[0].toUpperCase();
	}
}
