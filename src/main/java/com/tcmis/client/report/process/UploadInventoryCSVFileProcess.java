package com.tcmis.client.report.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.ClientChemListInputBean;
import com.tcmis.client.report.beans.GtImportInventoryBean;
import com.tcmis.client.report.beans.UploadFileInputBean;
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
 * Process to upload Inventory CSV file to our server.
 * @version 1.0
 *****************************************************************************/
public class UploadInventoryCSVFileProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

    public UploadInventoryCSVFileProcess(String client) {
		super(client);
	}

    public Object[] saveFile(ScannerUploadInputBean bean,String companyId,String facilityId,String application,String personnelId) throws BaseException {
    	String errMsg = "";
    	String seqId = ""; 
    	if (bean.getTheFile() != null ) {
		    //copy file to server
    		if(log.isDebugEnabled()) {
				log.debug("Save inventory CVS file to database.");
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
					StringBuilder query = new StringBuilder("insert into GT_IMPORT_INVENTORY_STAGE (STORAGE_DATE, WORK_AREA_NAME, MATERIAL_ID ,CUSTOMER_MSDS_OR_MIXTURE_NO, MSDS_UNIT_OF_MEASURE, MSDS_UNIT_AMOUNT, PART_NO, PART_AMOUNT, PURCHASE_SOURCE,APPLICATION,COMPANY_ID,FACILITY_ID,INSERTED_BY,INSERT_DATE,ENTRY_TYPE,DATA_LOAD_ROW_ID,IMPORTED_FILE) values (");
                    query.append("to_date('").append(cellValue[0]).append("', 'MM/DD/YYYY'),'");
                    query.append(StringHandler.emptyIfNull(cellValue[1])).append("','");
                    query.append(StringHandler.emptyIfNull(cellValue[2])).append("','");
                    query.append(StringHandler.emptyIfNull(cellValue[3])).append("','");
                    query.append(StringHandler.emptyIfNull(cellValue[4])).append("',");
                    query.append(cellValue[5].length() == 0? null:cellValue[5]).append(",'");
                    query.append(StringHandler.emptyIfNull(cellValue[6])).append("',");
                    query.append(cellValue[7].length() == 0? null:cellValue[7]).append(",'");
                    query.append(StringHandler.emptyIfNull(cellValue[8])).append("','");
                    query.append(StringHandler.emptyIfNull("")).append("','");// application
                    query.append(StringHandler.emptyIfNull(companyId)).append("','");
                    query.append(StringHandler.emptyIfNull(facilityId)).append("',");
                    query.append(StringHandler.emptyIfNull(personnelId)).append(",sysdate,'I',").append(lineNumber);
                    query.append(",'").append(importedFile).append("'");
                    query.append(")");
                    sql.update(conn, query.toString());
				}

				 Vector out = new Vector();
				 out.add( new Integer(java.sql.Types.INTEGER) );
				 Vector seqIdV = (Vector) sql.doProcedure(conn, "PKG_IMPORT_INVENTORY.P_LOAD_IMPORT_INVENTORY",new Vector(),out);
				 if( seqIdV != null && seqIdV.size() == 1 && seqIdV.get(0) != null) {
					 seqId = seqIdV.get(0)+"";
					 errMsg += "Upload Successful!!<br/>";
					 log.debug("upload seq id:"+seqIdV.get(0).toString());
				 } else {
					 String q = "select DATA_LOAD_ROW_ID, ERROR_STATEMENT from GT_IMPORT_INVENTORY_ERROR";
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
				errMsg += library.getString("label.totallinesadded")+": "+(lineNumber-1);
    		}catch(Exception ex){
				ex.printStackTrace();
				errMsg = "Error Saving CSV file to database.";
    		}
    		finally{ try {if( conn != null ) conn.commit();dbManager.returnConnection(conn);}catch(Exception x){}; };
    	}
    	Object objs[] = { errMsg, seqId };
    	return objs;
	}
    
    public Object[] saveInventory(GtImportInventoryBean bean,String companyId,String facilityId,String personnelId) throws BaseException {
    	String errMsg = "";
    	String seqId = ""; 
        DbManager dbManager = new DbManager(getClient(),getLocale());
        Connection conn = dbManager.getConnection();
        SqlManager sql = new SqlManager();
        try {
            int lineNumber = 0;
            conn.setAutoCommit(false);
            lineNumber++;
            BigDecimal quantity = bean.getPartAmount();
            StringBuilder query = new StringBuilder("insert into GT_IMPORT_INVENTORY_STAGE (STORAGE_DATE, WORK_AREA_NAME, MATERIAL_ID ,CUSTOMER_MSDS_OR_MIXTURE_NO, MSDS_UNIT_OF_MEASURE, MSDS_UNIT_AMOUNT, PART_NO, PART_AMOUNT, PURCHASE_SOURCE,APPLICATION,COMPANY_ID,FACILITY_ID,INSERTED_BY,INSERT_DATE,ENTRY_TYPE,DATA_LOAD_ROW_ID) values (");
            query.append( GenericProcess.getSqlString(bean.getStorageDate()) +",'");
            query.append(StringHandler.emptyIfNull(bean.getWorkAreaName())).append("','");
            query.append(StringHandler.emptyIfNull("")).append("','");
            query.append(StringHandler.emptyIfNull(bean.getCustomerMsdsOrMixtureNo())).append("','");
            query.append(StringHandler.emptyIfNull(bean.getMsdsUnitOfMeasure())).append("',");
            //msds_unit_amount
            if (StringHandler.isBlankString(bean.getPartNo())) {
                query.append(bean.getPartAmount()==null?"null":bean.getPartAmount()).append(",'");
            }else {
                query.append("null").append(",'");
            }
            query.append(StringHandler.emptyIfNull(bean.getPartNo())).append("',");
            //part_amount
            if (!StringHandler.isBlankString(bean.getPartNo())) {
                query.append(bean.getPartAmount()==null?"null":bean.getPartAmount()).append(",'");
            }else {
                query.append("null").append(",'");
            }
            query.append(StringHandler.emptyIfNull(bean.getPurchaseSourceName())).append("','");
            query.append(StringHandler.emptyIfNull(bean.getApplication())).append("','");
            query.append(StringHandler.emptyIfNull(companyId)).append("','");
            query.append(StringHandler.emptyIfNull(facilityId)).append("',");
            query.append(StringHandler.emptyIfNull(personnelId)).append(",sysdate,'M',"+lineNumber+")");
            sql.update(conn, query.toString());

            Vector out = new Vector();
            out.add( new Integer(java.sql.Types.INTEGER) );
            Vector seqIdV = (Vector) sql.doProcedure(conn, "pkg_import_inventory.P_LOAD_IMPORT_INVENTORY",new Vector(),out);
            if( seqIdV != null && seqIdV.size() == 1 && seqIdV.get(0) != null) {
                 seqId = seqIdV.get(0)+"";
            } else {
                 DataSet result =  sql.select(conn,"select DATA_LOAD_ROW_ID, ERROR_STATEMENT from GT_IMPORT_INVENTORY_ERROR");
                 Iterator i = result.iterator();
                 while( i.hasNext() ) {
                     DataSetRow r = (DataSetRow)i.next();
                     String row = r.getString("DATA_LOAD_ROW_ID");
                     String error = r.getString("ERROR_STATEMENT");
                     errMsg += "Line "+row+":"+error+"<br>";
                 }
            }
            conn.commit();
        }catch(Exception ex){
            ex.printStackTrace();
            errMsg = "Error Saving CSV file to database.";
        }
        finally{ try {if( conn != null ) conn.commit();dbManager.returnConnection(conn);}catch(Exception x){}; };
        Object objs[] = { errMsg, seqId };
        return objs;
    }
    
    public String getInventoryTradeName(GtImportInventoryBean bean, String facilityId, String uploadSeqId) throws BaseException {
        String result = "";
        try {
            DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("select m.trade_name from import_inventory ic, material m");
            query.append(" where ic.material_id = m.material_id and ic.company_id = '").append(bean.getCompanyId()).append("'");
            query.append(" and ic.facility_id = '").append(facilityId).append("' and ic.application = '").append(bean.getApplication()).append("'");
            query.append(" and ic.upload_seq_id = ").append(uploadSeqId);
            result = genericSqlFactory.selectSingleValue(query.toString());
        }catch(Exception e) {}
        return result;
    } //end of method
}
