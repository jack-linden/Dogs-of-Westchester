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
	 */
	public byte[] uploadCSV(byte[] fileContents) throws IOException {

		if (fileContents == null || fileContents.length == 0) {
			throw new IllegalArgumentException("Did not expect a null filename argument or an empty/null byte array.");
		}

		InputStream is = new ByteArrayInputStream(fileContents);

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String firstLine = in.readLine();
		String cityName = getCityName(firstLine);
		String headersLine = in.readLine();

		StringBuilder sb = new StringBuilder();
		sb.append(firstLine);
		sb.append("\r\n");
		sb.append(headersLine);
		sb.append("\r\n");

		for (String line = in.readLine(); line != null; line = in.readLine()) {
			String[] tokens = prepareTokens(line.toUpperCase());

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
			sb.append("\r\n");
		}
		return sb.toString().getBytes();
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
