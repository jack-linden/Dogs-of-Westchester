package web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import services.TrendService;
import services.TrendServiceImpl;
import services.UploadService;
import services.UploadServiceImpl;

public class UploadServlet extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	/**
	 * The method handles the upload data request from the website
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		if (!blobExists(req)) {
			res.setContentType("text/plain");
			res.getWriter().println("Error uploading file.");
			return;
		}
		UploadService uploadFileService = UploadServiceImpl.getInstance();

		BlobInfo blobInfo = getBlobInfo(req);
		byte[] fileContents = blobstoreService.fetchData(blobInfo.getBlobKey(), 0, blobInfo.getSize());
		byte[] newByteArray = uploadFileService.uploadCSV(fileContents);

		TrendService trendService = new TrendServiceImpl();
		trendService.updateTrends();

		res.setContentType("text/csv");
		res.getOutputStream().write(newByteArray);
	}

	/**
	 * The is a helper method that gets a specific blob
	 */
	private BlobInfo getBlobInfo(HttpServletRequest req) {
		Map<String, List<BlobInfo>> blobInfoMap = blobstoreService.getBlobInfos(req);
		BlobInfo blobInfo = blobInfoMap.get("myFile").get(0);
		return blobInfo;
	}

	/**
	 * The is a helper method that checks if a specific blob exists
	 */
	private boolean blobExists(HttpServletRequest req) {
		return !blobstoreService.getBlobInfos(req).isEmpty();
	}
}
