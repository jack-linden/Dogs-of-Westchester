package services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
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
			_instance = new UploadService();
		}
		return _instance;
	}

	public void uploadCSV(String filename, boolean inTestingMode) throws IOException {
		InputStream is;
		mockDB = new ArrayList<String>();
		if (inTestingMode) {
			is = new FileInputStream("/Users/jiayanzhang/Desktop/1530/project_repo/src/main/webapp/WEB-INF/" + filename);
		} else {
			datastore = DatastoreServiceFactory.getDatastoreService();
			context = this.getServletContext();
			is = context.getResourceAsStream("/WEB-INF/" + filename);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String firstLine = in.readLine();
		String[] getCityName = firstLine.split(",");
		String cityName = getCityName[1];

		in.readLine(); // ignore the second line
		in.readLine(); // ignore the third line

		String line = in.readLine();
		while (line != null) {
			String[] tokens = line.split(",");

			if (tokens.length == 5) {
				String name = tokens[0];
				String condition = tokens[1];
				String sex = tokens[2];
				String breed = tokens[3];
				String color = tokens[4];

				if (inTestingMode) {
					mockDB.add(cityName);
					mockDB.add(name);
					mockDB.add(condition);
					mockDB.add(sex);
					mockDB.add(breed);
					mockDB.add(color);
				}

				else {
					Entity dog = new Entity("Dog");
					dog.setProperty("City", cityName);
					dog.setProperty("Name", name);
					dog.setProperty("Condition", condition);
					dog.setProperty("Sex", sex);
					dog.setProperty("Breed", breed);
					dog.setProperty("Color", color);
					datastore.put(dog);
				}
			}

			line = in.readLine();
		}
	}
}
