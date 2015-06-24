package web;

import java.io.IOException;
import javax.servlet.http.*;

public class MainServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("{ \"name\": \"Dogs\" }");
	}
}
