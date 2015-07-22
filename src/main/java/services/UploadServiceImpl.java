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

public class UploadServiceImpl implements UploadService {
	private final int ID_NUMBER_LENGTH = 16;
	private final int EXPECTED_TOKENS_LENGTH = 6;
	private static UploadServiceImpl _instance = null;
	private DogDao dogDao = null;

	/**
	 * Class constructor.
	 * 
	 */
	protected UploadServiceImpl() {
		setDogDao(new DogDaoImpl());
	}

	/**
	 * Class constructor.
	 * 
	 * Used singleton pattern and lazy thread-safe implementation
	 */
	public static UploadServiceImpl getInstance() {
		if (_instance == null) {
			synchronized (UploadServiceImpl.class) {
				if (_instance == null) {
					_instance = new UploadServiceImpl();
				}
			}
		}
		return _instance;
	}

	/**
	 * Sets the DogDao (data-access object)
	 * 
	 * @param dogDao (the data-access object)	            
	 */
	public void setDogDao(DogDaoImpl dogDao) {
		this.dogDao = dogDao;
	}

	/**
	 * This function will upload a CSV file to Google App Engine's datastore. It
	 * will return a byte array output file that contains each stored dogs'
	 * information and new idNumber. Refer to the excel template in test-files.
	 * 
	 * @param fileContents
	 * @throws IOException
	 * @return byte[] containing contents of the new excel file with appended
	 *         dog id's on each line
	 */
	public byte[] uploadCSV(byte[] fileContents) throws IOException {
		// Illegal input checks, will throw IAException if so
		if (fileContents == null || fileContents.length == 0) {
			throw new IllegalArgumentException("Did not expect a null filename argument or an empty/null byte array.");
		}
		// Create InputStream from byte[] for the BufferedReader to read
		InputStream is = new ByteArrayInputStream(fileContents);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		// First two lines contain the city name and excel headers
		String firstLine = in.readLine();
		
		// Extract city name (Row 1 Cell 1) from first line		
		String cityName = getCityName(firstLine);
		
		// Line containing column headers
		String headersLine = in.readLine(); 

		// Create StringBuilder and append the first two lines
		StringBuilder sb = new StringBuilder();
		sb.append(firstLine);
		
		// Appends carridge return and newline
		sb.append("\r\n"); 
		sb.append(headersLine);
		sb.append("\r\n");

		// Iterate through the file line by line storing each dog record in datastore		
		for (String line = in.readLine(); line != null; line = in.readLine()) {
			// Converts excel/csv line to 6 element String[] containing dog info			
			String[] tokens = prepareTokens(line.toUpperCase());
			
			Dog dog = createDogFromTokens(tokens, cityName);
			String idNumber = dog.getIdNumber();
			String newCSVLine = line;

			// If an id doesn't exist we must store this dog in the datastore
			if (!validIdExists(idNumber)) {
				// Returns key for new Dog entity				
				String newDogKeyString = dogDao.insertDog(dog); 
				String newIdNumber = extractIdNumberFromIdString(newDogKeyString);
				newCSVLine = appendDogIdToCSVLine(line, newIdNumber);
			}
			
			// Append update csv line with dog id to output filestream
			sb.append(newCSVLine); 									
			sb.append("\r\n");
		}

		// Close the input stream
		is.close();

		// Returns byte[] of output file
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
		// Illegal input checks, iwll throw IAException
		if (tokens == null || tokens.length != EXPECTED_TOKENS_LENGTH || cityName == null || cityName.length() == 0) {
			throw new IllegalArgumentException("Expected tokens to be a non-null array of length 6 and cityName to be a non-null non empty string. ");
		}
		// Set Dog object fields
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
		// Illegal argument checks, will throw IAException
		if (line == null || line.length() == 0) {
			throw new IllegalArgumentException("Did not expect line to be null or empty.");
		}
		
		// Splits line on "," to get each column data		
		String[] tokens = line.split(",");

		// Output [] should be length 6
		String[] preparedTokens = new String[6]; 
		
		// Initialize each value to "UNKNOWN"
		Arrays.fill(preparedTokens, 0, 5, "UNKNOWN"); 
		
		for (int i = 0; i < tokens.length; i++) {
			// If column data exists, set it appropriately
			if (!tokens[i].equals("")) {
				preparedTokens[i] = tokens[i];
			}
		}
		// If the idNumber column has data, set it
		// Else it becomes the empty string
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
		// Illegal input check, will throw IAException
		if (idNumber == null) {
			throw new IllegalArgumentException("Expected a non-null idNumber String.");
		}
		// Id must be a 16 digit number string
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
		// Illegal input check, will throw IAException
		if (line == null || idNumber == null || line.length() == 0 || idNumber.length() == 0) {
			throw new IllegalArgumentException("Did not expect line or idNumber Strings to be null or empty.");
		}
		
		// Append current line with newly generated id number
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(idNumber);
		return sb.toString();
	}

	/**
	 * Google App Engine's datastore key strings are in the format
	 * Dog(xxxxxxxxxxxxxxxx) where x's are digits in the 16 digit number. This
	 * method extracts that number and returns it as a string
	 * 
	 * @param idString, eg."Dog(0000000000000000)"
	 *            
	 * @return a 16-digit number, eg. 0000000000000000
	 * 
	 */
	private String extractIdNumberFromIdString(String idString) {
		// Illegal input check, will throw IAException
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
		// Illegal input check, will throw IAException
		if (line == null || line.length() == 0) {
			throw new IllegalArgumentException("Did not expect line to be null or empty.");
		}
		
		// City name is the first row, first cell in the file (line1[0])
		return line.split(",")[0].toUpperCase();
	}
}
