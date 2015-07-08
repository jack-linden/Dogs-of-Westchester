package services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;

import com.google.appengine.api.datastore.DatastoreService;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import model.Dog;

public class UploadService extends HttpServlet {

	private static UploadService _instance = null;
	
	protected UploadService() {

	}

	public static UploadService getInstance() {
		if (_instance == null) {
			// Lazy threadsafe implementation
			synchronized (UploadService.class) {
				if (_instance == null) {
					_instance = new UploadService();
				}
			}
		}
		return _instance;
	}

	/**
	 * UploadService.uploadCSV
	 * 
	 * This function will upload a CSV file to google's datastore. Refer to
	 * excel template for formatting.
	 * 
	 * @param filename
	 *            The filename of the file to upload
	 * @param inTestingMode
	 *            Boolean flag true if for testing purposes
	 * @throws IOException
	 */
	public byte[] uploadCSV(String filename, byte[] fileContents) throws IOException {

		if (filename == null || fileContents == null || fileContents.length == 0) {
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
			if( tokens.length != 6){
				continue;
			}
			Dog dog = new Dog();
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

	private String[] prepareTokens(String[] tokens) {

		for (int i = 0; i < tokens.length - 1; i++) {
			if ( tokens[i].equals("")) {
				tokens[i] = "UNKNOWN";
			}
		}
		return tokens;
	}

	private boolean validIdExists(String idNumber) {
		return idNumber.length() == 16;
	}

	private String appendDogIdToCSVLine(String line, String idNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(idNumber);
		return sb.toString();

	}



	/**
	 * Parses first line of CSV file and returns the city name which is located
	 * at cell 0.
	 * 
	 * @param line
	 * @return String of city name.
	 * 
	 */
	private String getCityName(String line) {
		return line.split(",")[0].toUpperCase();
	}
}
