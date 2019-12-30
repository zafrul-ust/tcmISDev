package com.tcmis.batch.processdocuments;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.factory.ReceiptDocumentViewBeanFactory;

/**
 * ***************************************************************************
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class ProcessDocumentsProcess
        extends BaseProcess{
    Log log = LogFactory.getLog(this.getClass());
    private ResourceLibrary library = null;
    private DbManager dbManager = null;

    public ProcessDocumentsProcess(String client) throws BaseException {
        super(client);
        dbManager = new DbManager(client);
        library = new ResourceLibrary("receiptdocument");
    }

    public void processDoc() throws
            BaseException {
        try {
    	    // Incoming directory
        	File dir = new File( library.getString("process.document.path") );///webdata/html/process_documents

    	    // Move file to new directory

        	GenericSqlFactory gf = new GenericSqlFactory(dbManager);
        	String urlr= library.getString("receipt.document.urlpath");
        	String urlm= library.getString("mr.document.urlpath");
        	String urlp= library.getString("po.document.urlpath");
        	String rs = library.getString("receipt.document.path");
        	String ms = library.getString("mr.document.path");
        	String ps = library.getString("po.document.path");
        	String urlh = "";
        	String newdir = "";
           	Pattern newp = Pattern.compile("RDOC[0-9]{8}");
            	File[] files = dir.listFiles();
            	for(File f:files) {
            		if( f.isDirectory() ) continue;
            		String fname = f.getName();
                    try {
                    // Larry Note: The new type match is also needed in ftp code. rec_doc_move_new.pl 
                    boolean newtype = newp.matcher(fname).matches();
            		String p1[] = fname.split("[.]");
                    if(newtype) {
                    	String docinfo[] = p1[0].split("_"); 
                    	String in_doc_sequence = docinfo[0].substring(4); // without the pdf and the last 8 digit.
                    	// new query is for receipt document only
                    	String newUpdateQuery = 
                    			"update inbound_shipment_document set DOCUMENT_FILE_NAME = '"+rs+fname+"' where INBOUND_SHIPMENT_DOCUMENT_ID = "+in_doc_sequence;
                    	gf.deleteInsertUpdate(newUpdateQuery);
                    	continue;
                    }
            		String p2[] = p1[0].split("-");
            		BigDecimal recId = new BigDecimal(p2[0]);
            		String code  = p2[1];
            		String coms[]  = p2[2].split("_"); 
            		
        			String type = "Other";
        			String table = "RECEIPT_DOCUMENT";
        			String column = "receipt_id";
        			String remains[] = p2[3].split("_");
        			String doc_sequence = remains[0];
//        			String timeStamp = "";
//        			if( remains.length > 1)
//        				timeStamp = remains[1];
        			urlh = urlr;
        			newdir = rs;
        			if( "R".equals(code) ) {
        				type = "Checklist";
        			}
        			else if( "C".equals(code) ) {
        				type = "COC";
        			}
        			else if( "M".equals(code) ) {
        				type = "General";
        				table = "MR_DOCUMENT";
        				column = "PR_NUMBER";
            			urlh = urlm;
            			newdir = ms;
        			}
        			else if( "P".equals(code) ) {
        				type = "General";
        				table = "PO_DOCUMENT";
        				column = "RADIAN_PO";
            			urlh = urlp;
            			newdir = ps;
        			}
        			if( "G".equals(code) ) {
        				type = "COC";
        				BigDecimal rectipGroupId = recId;
        				String rquery = "select RECEIPT_ID from RECEIPT_DOCUMENT_GROUP where RECEIPT_DOC_GROUP_NO = " + rectipGroupId;
        				Object[] c = gf.selectIntoObjectArray(rquery);
        				Vector<String> comIds = new Vector();
                		for(String s:coms) {
                			String query = "select company_id from customer.company where BARCODE_DESIGNATION = '" + s +"'";
                			try {
    								String companyId =(String) ((Object[])((Vector)(gf.selectIntoObjectArray(query))[2]).get(0))[0];
    								comIds.add(companyId);
    							} 
                			catch(Exception ex){ex.printStackTrace();}
                		}
        				Vector rids = (Vector) c[2];
        				for(Object rido:rids) {
        					Object[] objArr = (Object[]) rido; 
        					String rid = objArr[0].toString();
                    		for(String companyId:comIds) {
                    			try {
        								String insertQuery = "insert into "+table+" (document_id,"+column+",document_name,document_type,insert_date,document_date,document_url,entered_by,company_id,doc_sequence)" +
        													" (select nvl(max(document_id),0)+1,"+rid+",'"+rid+"','"+type+"',sysdate,trunc(sysdate),'"+urlh+fname+"',-1,'"+companyId+"'"+","+doc_sequence+
        													" from "+table+" where " +column+" = "+rid+
        													" )";//" group by receipt_id)";
        								gf.deleteInsertUpdate(insertQuery);
        							} 
                    			catch(Exception ex){ex.printStackTrace();}
                    		}
        				}
        				
//                		boolean success = 
        				System.out.println("Moving file:"+fname);
                		FileHandler.move(f, new File( new File( newdir ),fname));
                		continue;
        			}
            		for(String s:coms) {
            			String query = "select company_id from customer.company where BARCODE_DESIGNATION = '" + s +"'";
            			try {
								String companyId =(String) ((Object[])((Vector)(gf.selectIntoObjectArray(query))[2]).get(0))[0];
								String insertQuery = "insert into "+table+" (document_id,"+column+",document_name,document_type,insert_date,document_date,document_url,entered_by,company_id,doc_sequence)" +
													" (select nvl(max(document_id),0)+1,"+recId+",'"+recId+"','"+type+"',sysdate,trunc(sysdate),'"+urlh+fname+"',-1,'"+companyId+"'"+","+doc_sequence+
													" from "+table+" where " +column+" = "+recId+
													" )";//" group by receipt_id)";
								gf.deleteInsertUpdate(insertQuery);
							} 
            			catch(Exception ex){ex.printStackTrace();}
        			}
            			//            		boolean success = 
            		FileHandler.move(f, new File( new File( newdir ),fname));
                    }
                    catch(Exception e) {
                        log.fatal("Error saving pdf file from process_documents file:" + fname+":"+e.getMessage(),e);
                    }
            	}
//            	fac.done();
        }
        catch(Exception e) {
            log.fatal("process_documents error.", e);
            throw new BaseException(e.getCause());
        }
    }
}

