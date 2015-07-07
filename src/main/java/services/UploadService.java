package services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class UploadService extends HttpServlet {

	private static UploadService _instance = null;
	private DatastoreService datastore;

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
		datastore = DatastoreServiceFactory.getDatastoreService();

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String firstLine = in.readLine();
		in.readLine(); // Skip the excel headers
		String cityName = getCityName(firstLine);

		StringBuilder sb = new StringBuilder();
		for (String line = in.readLine(); line != null; line = in.readLine()) {
			String[] tokens = prepareTokens(line.toUpperCase().split(","));
			
			String dogname = tokens[0];
			String condition = tokens[1];
			String sex = tokens[2];
			String breed = tokens[3];
			String color = tokens[4];
			String idNumber = tokens[5];
			String newCSVLine = line;
			
			if (!validIdExists(idNumber)) {
				String newIdNumber = datastore.put(createDogEntity(cityName, dogname, condition, sex, breed, color)).toString();
				newCSVLine = appendDogIdToCSVLine(line, newIdNumber);
			}
			sb.append(newCSVLine);
			sb.append("\n");
		}
		return sb.toString().getBytes();
	}

	private String[] prepareTokens(String[] tokens) {
		String [] newTokens = new String[6];
		for (int i = 0; i < newTokens.length - 1; i++) {
			if (tokens[i].equals("")) {
				newTokens[i] = "UNKOWN";
			}
		}
		
		if (tokens.length==6)
		{
			if (tokens[5].equals("")) {
				newTokens[5] = "UNKOWN";
			}
			else {
				newTokens[5] = tokens[5];
			}
		}
		
		else {
			newTokens[5] = "UNKNOWN";
		}
		return newTokens;
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

	private Entity createDogEntity(String cityName, String dogname, String condition, String sex, String breed, String color) {
		Entity dog = new Entity("Dog");

		dog.setProperty("City", cityName);
		dog.setProperty("Name", dogname);
		dog.setProperty("Condition", condition);
		dog.setProperty("Sex", sex);
		dog.setProperty("Breed", breed);
		dog.setProperty("Color", color);

		return dog;
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
