package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UploadService;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;

public class UploadServlet extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/csv");
		Map<String, List<BlobInfo>> blobInfos = blobstoreService.getBlobInfos(req);

		if (blobInfos == null || blobInfos.isEmpty()) {
			res.sendRedirect("/");
		} else {
			List<BlobInfo> infos = blobInfos.get("myFile");
			BlobInfo blobInfo = infos.get(0);
			BlobKey blobKey = blobInfo.getBlobKey();
			byte[] byteArray = blobstoreService.fetchData(blobKey, 0, blobInfo.getSize());
			UploadService uploadFile = UploadService.getInstance();
			byte[] newByteArray = uploadFile.uploadCSV(blobInfo.getFilename(), byteArray);
			res.getOutputStream().write(newByteArray);
		}
	}
}
