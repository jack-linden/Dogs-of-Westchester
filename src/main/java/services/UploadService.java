package services;

import java.io.BufferedReader;
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
	private ServletContext context;
	protected ArrayList<String> mockDB;

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
	public void uploadCSV(HttpServletRequest req, HttpServletResponse resp, String filename, boolean inTestingMode) throws IOException {		
		
		if (filename == null) {
			throw new IllegalArgumentException("Did not expect a null filename argument");
		}

		InputStream is;
		mockDB = new ArrayList<String>();		
		PrintWriter writer = new PrintWriter(System.out);
		
		if (inTestingMode) {
			is = new FileInputStream(System.getProperty("user.dir") + "/test-files/" + filename);
		}
		else {
			datastore = DatastoreServiceFactory.getDatastoreService();
			context = this.getServletContext();
			is = context.getResourceAsStream("/WEB-INF/" + filename);
			resp.setContentType("text/plain");		
			writer = resp.getWriter();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		int lineCounter = 0;
		String firstLine = in.readLine();
		lineCounter++;
		String[] getCityName = firstLine.split(",");
		String cityName = getCityName[1].toLowerCase();
		
		in.readLine(); // ignore the second line
		lineCounter++;
		in.readLine(); // ignore the third line
		lineCounter++;
		
		String line = in.readLine();
		lineCounter++;
		
		while (line != null) {
			String[] tokens = line.split(",");

			if (tokens.length == 5) {
				String dogname = tokens[0].toLowerCase();
				String condition = tokens[1].toLowerCase();
				String sex = tokens[2].toLowerCase();
				String breed = tokens[3].toLowerCase();
				String color = tokens[4].toLowerCase();

				if (inTestingMode) {
					mockDB.add(cityName);
					mockDB.add(dogname);
					mockDB.add(condition);
					mockDB.add(sex);
					mockDB.add(breed);
					mockDB.add(color);
				}

				else {
					Entity dog = new Entity("Dog");
					
					//TODO output ID to an separate file or append it to the current csv file? 
					String idNumber = dog.getKey().toString();
					
					dog.setProperty("City", cityName);
					dog.setProperty("Name", dogname);
					dog.setProperty("Condition", condition);
					dog.setProperty("Sex", sex);
					dog.setProperty("Breed", breed);
					dog.setProperty("Color", color);
					datastore.put(dog);
				}
			}
			else {	
				if (inTestingMode) System.out.println("Warning: line " + lineCounter + " in " + filename + " is not properly formatted!");
				else writer.println("Warning: line " + lineCounter + " in " + filename + " is not properly formatted!"); 
			}

			line = in.readLine();
			lineCounter++;
		}
	}
}
