package com.tcmis.internal.fileUpload;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {

	private static final long			serialVersionUID	= 1L;

	private static Log					log					= LogFactory.getLog(UploadServlet.class.getName());

	private static DbManager			dbManager			= null;

	private static String				baseReceiptUrl		= null;
	private static String				baseMrUrl			= null;
	private static String				basePoUrl			= null;
	
	private static String				basePicUrl			= null;
	
	private static String				baseReceiptSavepath	= null;
	private static String				baseMrSavepath		= null;
	private static String				basePoSavepath		= null;
	
	private static String				basePicSavepath		= null;

	private String						receiptUrl			= null;
	private String						mrUrl				= null;
	private String						poUrl				= null;
	
	private String						picUrl				= null;
	
	private String						receiptSavepath		= null;
	private String						mrSavepath			= null;
	private String						poSavepath			= null;
	
	private String						picSavepath			= null;
	

	private String						dateDir				= "";

	private static ServletFileUpload	upload				= null;

	static {
		ResourceLibrary library = new ResourceLibrary("receiptdocument");

		baseReceiptUrl = library.getString("receipt.document.urlpath");
		if (!baseReceiptUrl.endsWith(File.separator)) {
			baseReceiptUrl += File.separator;
		}
		baseMrUrl = library.getString("mr.document.urlpath");
		if (!baseMrUrl.endsWith(File.separator)) {
			baseMrUrl += File.separator;
		}
		basePoUrl = library.getString("po.document.urlpath");
		if (!basePoUrl.endsWith(File.separator)) {
			basePoUrl += File.separator;
		}
		baseReceiptSavepath = library.getString("receipt.document.path");
		if (!baseReceiptSavepath.endsWith(File.separator)) {
			baseReceiptSavepath += File.separator;
		}
		baseMrSavepath = library.getString("mr.document.path");
		if (!baseMrSavepath.endsWith(File.separator)) {
			baseMrSavepath += File.separator;
		}
		basePoSavepath = library.getString("po.document.path");
		if (!basePoSavepath.endsWith(File.separator)) {
			basePoSavepath += File.separator;
		}
		
		ResourceLibrary library2 = new ResourceLibrary("tabletConfig");
		
		basePicUrl = library2.getString("relativePickingDocDirectory");
		if (!basePicUrl.endsWith(File.separator)) {
			basePicUrl += File.separator;
		}		
		basePicSavepath = library2.getString("pickingDocDirectory");
		if (!basePicSavepath.endsWith(File.separator)) {
			basePicSavepath += File.separator;
		}
	}

	@Override
	public void init() throws ServletException {

		dbManager = new DbManager("TCM_OPS");
		upload = new ServletFileUpload();	
		
		try {
			setPaths();
		} catch (IOException e) {			
			e.printStackTrace();
			log.error(e);
			throw new ServletException(e);
		}
	}

	private void setPaths() throws IOException {
		
		dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
		
		receiptUrl = Paths.get(baseReceiptUrl + dateDir).toString() + File.separator;
		receiptSavepath = Paths.get(baseReceiptSavepath + dateDir).toString() + File.separator;		
		makeDir(receiptSavepath);
		
		poUrl = Paths.get(basePoUrl + dateDir).toString() + File.separator;
		poSavepath = Paths.get(basePoSavepath + dateDir).toString() + File.separator;
		makeDir(poSavepath);
		
		mrUrl = Paths.get(baseMrUrl + dateDir).toString() + File.separator;
		mrSavepath = Paths.get(baseMrSavepath + dateDir).toString() + File.separator;
		makeDir(mrSavepath);
		
		picUrl = Paths.get(basePicUrl + dateDir).toString() + File.separator;
		picSavepath = Paths.get(basePicSavepath + dateDir).toString() + File.separator;
		makeDir(picSavepath);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Not supported
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

		// Check that we have a file upload request
		if (!ServletFileUpload.isMultipartContent(request))
			throw new ServletException("You must upload a multipart document.");

		FileItemIterator it;
		UploadRequest req = null;
		try {
						
			req = new UploadRequest(receiptSavepath);

			it = upload.getItemIterator(request);
			while (it.hasNext()) {
				FileItemStream fItem = it.next();

				if (fItem.isFormField())
					req.processFormField(fItem);
				else
					req.addStream(fItem);
			}

			processRequest(req, factory);

		}
		catch (Exception e) {

			// delete the file already created
			if (null != req && req.getStream(0) != null)
				req.getStream(0).file.delete();

			e.printStackTrace();
			log.error(e);
			throw new ServletException(e);
		}
	}

	private String makeDir(String dirPath) throws IOException {

		Path newDirectoryPath = Paths.get(dirPath);
		if (!Files.exists(newDirectoryPath))
			Files.createDirectories(newDirectoryPath);

		return dirPath;
	}

	private void processRequest(UploadRequest req, GenericSqlFactory gf) throws Exception {

		if (req.getFileType().trim().equals("Code128"))
			processCode128(req, gf);
		else if (req.getFileType().trim().equals("DataMatrix"))
			processDataMatrix(req, gf);
		else if (req.getFileType().trim().equals("jdeAttach"))
			processJdeAttachment(req, gf);
	}

	private void processCode128(UploadRequest req, GenericSqlFactory gf) throws Exception {

		String fName = req.getStream(0).fileName;
		log.debug("Processing Code128 file: " + fName);
		
		String p1[] = fName.split("[.]");
		String docinfo[] = p1[0].split("_");

		String in_doc_sequence = docinfo[0].substring(4); // without the pdf and
															// the last 8 digit.
		// new query is for receipt document only
		String newUpdateQuery = "update inbound_shipment_document set DOCUMENT_FILE_NAME = '" + receiptUrl + fName
				+ "' , DOCUMENT_FILE_DATE = sysdate where INBOUND_SHIPMENT_DOCUMENT_ID = " + in_doc_sequence;
		
		gf.deleteInsertUpdate(newUpdateQuery);		
	}

	private void processDataMatrix(UploadRequest req, GenericSqlFactory gf) throws Exception {

		String fName = req.getStream(0).fileName;
		log.debug("Processing DataMatrix file: " + fName);

		String p1[] = fName.split("[.]");

		String p2[] = p1[0].split("-");
		if (p2[0].startsWith("PVR")){
			processAPvr(req, gf);
			return;
		}
		else if (p2[0].startsWith("BOL")) {
			processBol(req, gf);
			return;
		}
		
		BigDecimal recId = new BigDecimal(p2[0]);
		String code = p2[1];
		String coms[] = p2[2].split("_");

		String type = "Other";
		String table = "RECEIPT_DOCUMENT";
		String column = "receipt_id";
		String remains[] = p2[3].split("_");
		String doc_sequence = remains[0];

		String urlh = receiptUrl;
		String defaultPath = receiptSavepath;
		String newdir = defaultPath;

		if ("R".equals(code)) {
			type = "Checklist";
		}
		else if ("C".equals(code)) {
			type = "COC";
		}
		else if ("M".equals(code)) {
			type = "General";
			table = "MR_DOCUMENT";
			column = "PR_NUMBER";
			urlh = mrUrl;
			newdir = mrSavepath;
		}
		else if ("P".equals(code)) {
			type = "General";
			table = "PO_DOCUMENT";
			column = "RADIAN_PO";
			urlh = poUrl;
			newdir = poSavepath;
		}
		else if ("Q".equals(code)) {
			processAQVR(req, gf);
			return;
		}
		else if ("G".equals(code)) {

			type = "COC";
			BigDecimal rectipGroupId = recId;
			String rquery = "select RECEIPT_ID from RECEIPT_DOCUMENT_GROUP where RECEIPT_DOC_GROUP_NO = " + rectipGroupId;
			Object[] c = null;
			c = gf.selectIntoObjectArray(rquery);

			Vector<String> comIds = new Vector<String>();
			for (String s : coms) {
				
				String query = "select company_id from customer.company where BARCODE_DESIGNATION = '" + s + "'";
				
				String companyId = (String) ((Object[]) ((Vector<?>) (gf.selectIntoObjectArray(query))[2]).get(0))[0];
				comIds.add(companyId);				
			}
			
			Vector<?> rids = (Vector<?>) c[2];
			for (Object rido : rids) {

				Object[] objArr = (Object[]) rido;
				String rid = objArr[0].toString();
				for (String companyId : comIds) {

					String insertQuery = "insert into " + table + " (document_id," + column
							+ ",document_name,document_type,insert_date,document_date,document_url,entered_by,company_id,doc_sequence)" + " (select nvl(max(document_id),0)+1,"
							+ rid + ",'" + rid + "','" + type + "',sysdate,trunc(sysdate),'" + urlh + fName + "',-1,'" + companyId + "'" + "," + doc_sequence + " from " + table
							+ " where " + column + " = " + rid + " )";

					gf.deleteInsertUpdate(insertQuery);
				}
			}									
			return;
		}
		
		for (String s : coms) {

			String query = "select company_id from customer.company where BARCODE_DESIGNATION = '" + s + "'";
		
			String companyId = (String) ((Object[]) ((Vector<?>) (gf.selectIntoObjectArray(query))[2]).get(0))[0];

			String insertQuery = "insert into " + table + " (document_id," + column
						+ ",document_name,document_type,insert_date,document_date,document_url,entered_by,company_id,doc_sequence)" + " (select nvl(max(document_id),0)+1," + recId
						+ ",'" + recId + "','" + type + "',sysdate,trunc(sysdate),'" + urlh + fName + "',-1,'" + companyId + "'" + "," + doc_sequence + " from " + table + " where "
						+ column + " = " + recId + " )";

			gf.deleteInsertUpdate(insertQuery);		
		}
		
		if (!newdir.equals(defaultPath)) {
			makeDir(newdir);
			FileHandler.move(req.getStream(0).file, new File(new File(newdir), fName));
		}
	}

	private void processBol(UploadRequest req, GenericSqlFactory gf) throws BaseException, IOException {
		
		String fName = req.getStream(0).fileName;
		log.debug("Processing BOL file: "+fName);
		
		// if we fail moving the file we will not have a dead link in the database
		FileHandler.move(req.getStream(0).file, new File(new File(picSavepath), fName));
		
		String p1[] = fName.split("[_]");
		String p2[] = p1[0].split("-");
		
		String shipId = p2[1];
		
		String type = "ANNOTATED_BOL_STRAIGHT";
		String docName = "Annotated Straight BOL";
		String url = Paths.get(picUrl + fName).toString();
		
		String insertQuery = "insert into SHIPMENT_DOCUMENT"				
				+ " (SHIPMENT_ID, DOCUMENT_NAME, DOCUMENT_TYPE, INSERT_DATE, DOCUMENT_DATE, DOCUMENT_URL, ENTERED_BY, COMPANY_ID)"
				+ " values (" + shipId + ", '"+ docName+ "', '"+ type + "', sysdate , sysdate , '"+url+"', -1, 'HAAS')";
								
		gf.deleteInsertUpdate(insertQuery);		
	}

	private void processAPvr(UploadRequest req, GenericSqlFactory gf) throws IOException, BaseException {
		
		String fName = req.getStream(0).fileName;
		log.debug("Processing APVR file: "+fName);
		
		// if we fail moving the file we will not have a dead link in the database
		FileHandler.move(req.getStream(0).file, new File(new File(picSavepath), fName));
		
		String p1[] = fName.split("[.]");
		String p2[] = p1[0].split("-");
		
		String pickUnitId = p2[0].replace("PVR", "");		
		String type = "PVR-A";
		
		String insertQuery = "insert into PICKING_UNIT_DOCUMENT"				
				+ " (PICKING_UNIT_ID, RECEIPT_ID, DOCUMENT_PATH, DOCUMENT_TYPE, LAST_UPDATED, LAST_UPDATED_BY)"
				+ " values (" + pickUnitId + ", "+ null+ ", '"+ picUrl + fName + "', '" +type+ "', " + "sysdate" + ", -1"+" )";
		
		gf.deleteInsertUpdate(insertQuery);		
	}
	
	private void processAQVR(UploadRequest req, GenericSqlFactory gf) throws BaseException, IOException {

		String fName = req.getStream(0).fileName;
		log.debug("Processing AQVR file: " + fName);
		
		String p1[] = fName.split("[.]");

		String p2[] = p1[0].split("-");
		BigDecimal recId = new BigDecimal(p2[0]);
		// String code = p2[1];
		String coms[] = p2[2].split("_");

		String type = "QVR-A";
		String table = "RECEIPT_DOCUMENT";
		String column = "receipt_id";
		String remains[] = p2[3].split("_");
		String doc_sequence = remains[0];

		String urlh = receiptUrl;

		String query = "select company_id from customer.company where BARCODE_DESIGNATION = '" + coms[0] + "'";
		String companyId = (String) ((Object[]) ((Vector<?>) (gf.selectIntoObjectArray(query))[2]).get(0))[0];

		String insertQuery = "insert into " + table + " (document_id," + column
				+ ",document_name,document_type,insert_date,document_date,document_url,entered_by,company_id,doc_sequence)" + " (select nvl(max(document_id),0)+1," + recId + ",'"
				+ recId + "','" + type + "',sysdate,trunc(sysdate),'" + urlh + fName + "',-1,'" + companyId + "'" + "," + doc_sequence + " from " + table + " where " + column
				+ " = " + recId + " )";

		gf.deleteInsertUpdate(insertQuery);
	}

	private void processJdeAttachment(UploadRequest req, GenericSqlFactory gf) throws IOException, BaseException {

		// file name is jdeOrderNumber_x-PrNumber.xxx
		String fName = req.getStream(0).fileName;
		log.debug("Processing JDE file attachment: " + fName);
		
		// if we fail moving the file we will not have a dead link in the database
		FileHandler.move(req.getStream(0).file, new File(new File(mrSavepath), fName));
		
		String p1[] = fName.split("[.]");
		String p2[] = p1[0].split("-");
		String p3[] = p2[0].split("_");

		BigDecimal jdeOrderNum = new BigDecimal(p3[0]);
		BigDecimal prNum = new BigDecimal(p2[1]);

		String document_name = jdeOrderNum.toString();

		String insertQuery = "insert into MR_DOCUMENT " + " (document_id, PR_NUMBER,document_name,document_type,insert_date,document_date,document_url,entered_by,company_id)"
					+ " ( select nvl(max(document_id),0)+1," + prNum + ",'" + document_name + "', 'General', sysdate, trunc(sysdate),'" + mrUrl + fName + "',-1,'Radian'"
					+ " from MR_DOCUMENT where PR_NUMBER = " + prNum + " )";

		gf.deleteInsertUpdate(insertQuery);			
	}
}
