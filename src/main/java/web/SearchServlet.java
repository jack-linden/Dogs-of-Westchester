package web;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Dog;
import services.RecordRetrievalService;

public class SearchServlet extends HttpServlet {

	public final Gson gson = new Gson();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		RecordRetrievalService recordService = new RecordRetrievalService();
		String queryText = req.getParameter("search-text");
		
		Type collectionType = new TypeToken<List<String>>(){}.getType();
		List<String> queryProperties = gson.fromJson(req.getParameter("search-properties"), collectionType);
		
		Set<Dog> dogs = new HashSet<Dog>();
		for (String property : queryProperties) {
			dogs.addAll(recordService.queryDogRecords(property, queryText, false));
		}
		
		String jsonDogsString = gson.toJson(dogs);
		resp.getWriter().print("{ \"query\": \"" + dogs.size() + "\", \"dogs\": "+ jsonDogsString + " }");;
		resp.getWriter().flush();
	}
	
}
