package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Trend;
import services.TrendServiceImpl;

public class TrendServlet extends HttpServlet {
	public final Gson gson = new Gson();

	/**
	 * The method handles the data request from the Trend tab on the website
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("application/json");
		TrendServiceImpl trendService = new TrendServiceImpl();

		List<Trend> trends = trendService.getAllTrends();
		String jsonTrendString = gson.toJson(trends);
		res.getWriter().print("{ \"trends\" : " + jsonTrendString + " }");
		res.getWriter().flush();
	}
}
