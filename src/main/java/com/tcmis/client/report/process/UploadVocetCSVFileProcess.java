package com.tcmis.client.report.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.GtImportInventoryBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

/******************************************************************************
 * Process to upload Vocet CSV file to our server.
 * @version 1.0
 *****************************************************************************/
public class UploadVocetCSVFileProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

    public UploadVocetCSVFileProcess(String client) {
		super(client);
	}

    public Object[] saveFile(ScannerUploadInputBean bean,String companyId,String facilityId,String application,String personnelId, HttpServletRequest request) throws BaseException {
    	String errMsg = "";
    	String seqId = ""; 
    	if (bean.getTheFile() != null ) {
		    //copy file to server
    		if(log.isDebugEnabled()) {
				log.debug("Save Vocet CVS file to database.");
    		}
    		DbManager dbManager = new DbManager(getClient(),getLocale());
    		Connection conn = dbManager.getConnection();
    		SqlManager sql = new SqlManager();
    		try { 
				String dirname = resource.getString("saveltempreportpath");
				File dir = new File(dirname);
				
				String fileName = bean.getDocumentName();
				
				String[] fileNames = fileName.split("[.]"); 	
				File f = File.createTempFile("tmp", "."+fileNames[fileNames.length-1], dir);
				FileHandler.copy(bean.getTheFile(),f);
                String importedFile = f.getName();

                BufferedReader br = new BufferedReader( new FileReader(f));
				String lineData = null;
				StringTokenizer st = null;
				int lineNumber = 0;
	 
        		conn.setAutoCommit(false);
				while( ( lineData = br.readLine()) != null) {
					lineNumber++;
					if( lineNumber <=1 ) continue;
					String[] cellValue = lineData.split(",");
					StringBuilder query = new StringBuilder("insert into GT_IMPORT_VOCET_CHEM_STAGE ( CAS_NUMBER,SHORT_TERM_ESL,LONG_TERM_ESL,VOCET_SOURCE_ID,VOCET_CATEGORY_ID,APPLICATION,COMPANY_ID,FACILITY_ID,INSERTED_BY,INSERT_DATE,DATA_LOAD_ROW_ID,IMPORTED_FILE,ENTRY_TYPE) values ('");
                    query.append(StringHandler.emptyIfNull(cellValue[0])).append("',");
                    query.append(StringHandler.emptyIfNull(cellValue[1])).append(",");
                    query.append(StringHandler.emptyIfNull(cellValue[2])).append(",'");
                    query.append(StringHandler.emptyIfNull(cellValue[3])).append("','");
                    query.append(StringHandler.emptyIfNull(cellValue[4])).append("','");
                    query.append(StringHandler.emptyIfNull("")).append("','");// application
                    query.append(StringHandler.emptyIfNull(companyId)).append("','");
                    query.append(StringHandler.emptyIfNull(facilityId)).append("',");
                    query.append(StringHandler.emptyIfNull(personnelId)).append(",sysdate,").append(lineNumber);
                    query.append(",'").append(importedFile).append("'");
                    query.append(",'I')");
                    sql.update(conn, query.toString());
				}

				 Vector out = new Vector();
				 out.add( new Integer(java.sql.Types.INTEGER) );
				 Vector seqIdV = (Vector) sql.doProcedure(conn, "PKG_IMPORT_VOCET_CHEMICAL.P_LOAD_IMPORT_VOCET_CHEMICAL ",new Vector(),out);
				 if( seqIdV != null && seqIdV.size() == 1 && seqIdV.get(0) != null) {
					 seqId = seqIdV.get(0)+"";
					 errMsg += "Upload Successful!!<br/>";
                     errMsg += library.getString("label.totallinesadded")+": "+(lineNumber-1);
                     request.setAttribute("done", "OK");
                 } else {
					 String q = "select DATA_LOAD_ROW_ID, ERROR_STATEMENT from GT_IMPORT_VOCET_CHEM_ERROR ";
					 Statement stt = conn.createStatement();
					 stt.executeQuery(q);
					 ResultSet r = stt.executeQuery(q);
					 while( r.next() ) {
						 String row = r.getString("DATA_LOAD_ROW_ID");
						 String error = r.getString("ERROR_STATEMENT");
						 errMsg += "Line "+row+":"+error+"<br/>";
					 }
				 }
				conn.commit();
    		}catch(Exception ex){
				ex.printStackTrace();
				errMsg = "Error Saving CSV file to database.";
    		}finally{ try {if( conn != null ) conn.commit();dbManager.returnConnection(conn);}catch(Exception x){}; };
    	}
    	Object objs[] = { errMsg, seqId };
    	return objs;
	}
 
}
