package com.tcmis.ws.scanner.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:48:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Vector;
import java.util.Iterator;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.DateHandler;

import com.tcmis.ws.scanner.beans.*;

public class CabinetItemReceiptProcess extends GenericProcess{
	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;

	public CabinetItemReceiptProcess(String client) throws BaseException {
		super(client);
		library = new ResourceLibrary("scannerupload");
	}

	public CabinetListBean getCabinetReceiptItem(PersonnelBean personnel){
		CabinetListBean list = new CabinetListBean();
		list.setStatus("Failed");
		list.setReason("Invalid token. Please sign in again.");

        Connection connection = null;
		try {
            connection = dbManager.getConnection();
            String logonId = this.factory.selectSingleValue("select logon_id from personnel where personnel_id = " + personnel.getPersonnelId(),connection);
			String query = null;
			list.setStatus("TokenFailure");
		    String s = this.factory.getFunctionValue(connection,"Pkg_token.fx_verify_tcmis_token",logonId, personnel.getCompanyId(),personnel.getToken());
			if( !"OK".equals(s) ) return list;

			list.setStatus("Failed");
			list.setReason("Personnel Id required.");
			BigDecimal personnelId = personnel.getPersonnelId();
			if( personnelId == null ) return list;

			list.setReason("Facility Id(s) required.");
			list.setPersonnelId(personnelId);
			Vector<Facility> facss = personnel.getFacilityInfo();
			if( facss == null || facss.size() == 0 ) return list;

			list.setReason("Failed while running query to get cabinets.");

			int len = facss.size();
			int i = 0;
			String pre="";
            StringBuilder facilityIds = new StringBuilder("");
            StringBuilder facilityIds2 = new StringBuilder("");
            for( ;i< len ;i++) {
				String facilityId = facss.get(i).getFacilityId().trim();
				if( facilityId.length() == 0 ) { list.setReason("Valid Facility Id required. Position:"+i); return list; }
                facilityIds.append(pre);
                facilityIds.append(facilityId);
                facilityIds2.append(pre);
                facilityIds2.append("'"+facilityId+"'");              
                pre =",";
			}

			list.setReason("Failed while running query to get receipt data.");
            query = "select * from table (pkg_scanner_upload.fx_receipt_item ('"+facilityIds+"'))";
            log.debug(query);
            factory.setBeanObject(new Receipt());
            list.setReceiptInfo(factory.selectQuery(query,connection));

			list.setReason("Failed while running query to get item data.");
            query = "select * from table (pkg_scanner_upload.fx_app_bin_item ('"+facilityIds+"'))";
            log.debug(query);
            factory.setBeanObject(new BinItem());
            list.setItemInfo(factory.selectQuery(query,connection));

            //container data
            if ("2".equals(personnel.getVersion())) {
                query = "select * from table (pkg_scanner_upload.fx_put_away_rcpt_cont ('"+facilityIds+"'";
                if (personnel.getTimeLastLoaded() != null)
                    query += ","+DateHandler.getOracleToDateFunction(personnel.getTimeLastLoaded());
                query += "))";
                log.debug(query);
                factory.setBeanObject(new Receipt());
                list.setContainerInfo(factory.selectQuery(query,connection));
            }

            query = "select distinct b.* from label_format_2D_symbol a,label_format_2D_data b,inventory_group_label_format c " +
                    "where A.LABEL_FORMAT = C.LABEL_FORMAT " +
                    "and B.SYMBOL_FORMAT = A.SYMBOL_FORMAT " +
                    "and C.INVENTORY_GROUP in (select inventory_group from facility_inventory_group where facility_Id in ("+facilityIds2+"))";
            log.debug(query);
            factory.setBeanObject(new Symbol2DDetailBean());
            list.setSymbol2DDetailInfo(factory.selectQuery(query,connection));

            if( list.getReceiptInfo().size() > 0 || list.getItemInfo().size() > 0 ) {
                list.setReason("Download Successful.");
				list.setStatus("Success");
			}else {
                list.setReason("No Data Found.");
			}
		}catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try {
                dbManager.returnConnection(connection);
            }catch(Exception ee) {ee.printStackTrace();}
        }

		return list;
	}

    public String processOrder(String Company,String xmlString) throws
	BaseException {
		try {
			String dirname = library.getString("upload.backupdir");
			//        	dirname = "c:\\GeneratedJava\\";
			File orderFile = FileHandler.saveTempFile(xmlString, "CabinetItemReceipt_", ".xml", dirname);
			OrderParser parser = new OrderParser(getClient());
			PersonnelBean personnel = parser.parseCabinetReceiptItemRequest(orderFile);
			personnel.setCompanyId(Company);
			CabinetListBean list = getCabinetReceiptItem(personnel);
			return getResponseXml(list,personnel);

		}
		catch(Exception e) {
			log.fatal("Error processing CabinetItemReceipt", e);
			throw new BaseException(e.getCause());
		}
	}

    /*todo
        the order of the top level in the XML below CANNOT be changed without talking with Steve Skidmore.  Scanners are depending on
        this order.   tngo Sept 3, 2014
     */
	public String getResponseXml(CabinetListBean list, PersonnelBean personnel) {
        StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><CabinetReceiptItemResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\">");
        sw.append("<Status><Result>"+list.getStatus()+"</Result><Message>"+list.getReason()+"</Message></Status>");
        //bin data
        sw.append("<BinItems>");
        Iterator iter = list.getItemInfo().iterator();
        while(iter.hasNext()) {
            BinItem bin = (BinItem)iter.next();
            sw.append("<BinItem>");
            sw.append("<BinId>"+bin.getBinId()+"</BinId>");
            sw.append("<CabinetId>"+bin.getApplicationId()+"</CabinetId>");
            sw.append("<CompanyId>"+StringEscapeUtils.escapeXml(bin.getCompanyId())+"</CompanyId>");
            sw.append("<CountType>"+bin.getCountType()+"</CountType>");
            sw.append("<ItemId>"+bin.getItemId()+"</ItemId>");
            sw.append("<Status>"+bin.getStatus()+"</Status>");
            if ("2".equals(personnel.getVersion())) {
                sw.append("<DaysToPull>"+bin.getPullWithinDaysToExpiration()+"</DaysToPull>");
                sw.append("<AllowExpiredMaterial>"+bin.getIncludeExpiredMaterial()+"</AllowExpiredMaterial>");
            }
            sw.append("</BinItem>");
        }
        sw.append("</BinItems>");

        //receipt data
        sw.append("<Receipts>");
        iter = list.getReceiptInfo().iterator();
        while(iter.hasNext()) {
            Receipt bean = (Receipt)iter.next();
            sw.append("<Receipt><CustomerReceiptId>"+bean.getCustomerReceiptId()+"</CustomerReceiptId>");
            sw.append("<ItemId>"+bean.getItemId()+"</ItemId>");
            sw.append("<ReceiptId>"+bean.getReceiptId()+"</ReceiptId></Receipt>");
        }
        sw.append("</Receipts>");

        //container data
        if ("2".equals(personnel.getVersion())) {
            sw.append("<Containers>");
            iter = list.getContainerInfo().iterator();
            while(iter.hasNext()) {
                Receipt bean = (Receipt)iter.next();
                sw.append("<Container>");
                sw.append("<ReceiptId>"+bean.getReceiptId()+"</ReceiptId>");
                sw.append("<ContainerNumber>"+bean.getContainerNumber()+"</ContainerNumber>");
                sw.append("</Container>");
            }
            sw.append("</Containers>");
        }


        sw.append("<Symbol2DDetails>");
        iter = list.getSymbol2DDetailInfo().iterator();
        while(iter.hasNext()) {
            Symbol2DDetailBean bean = (Symbol2DDetailBean)iter.next();
            sw.append("<Symbol2DDetailType>");
            sw.append("<SymbolFormat>"+bean.getSymbolFormat()+"</SymbolFormat>");
            sw.append("<FieldNum>"+bean.getFieldNum()+"</FieldNum>");
            sw.append("<FieldName>"+bean.getFieldName()+"</FieldName>");
            sw.append("<TrailingDelimiter>"+bean.getTrailingDelimiter()+"</TrailingDelimiter>");
            sw.append("<MaxLength>"+bean.getMaxLength()+"</MaxLength>");
            sw.append("<EmptyValue>"+bean.getEmptyValue()+"</EmptyValue>");
            sw.append("<FieldFormat>"+bean.getFieldFormat()+"</FieldFormat>");
            sw.append("<List>"+bean.getList()+"</List>");
            sw.append("<ListDelimiter>"+bean.getListDelimiter()+"</ListDelimiter>");
            sw.append("<ListMaxLength>"+bean.getListMaxLength()+"</ListMaxLength>");
            sw.append("<SymbolId>"+bean.getSymbolId()+"</SymbolId>");
            sw.append("<StaticValue>"+bean.getStaticValue()+"</StaticValue>");
            sw.append("</Symbol2DDetailType>");
        }
        sw.append("</Symbol2DDetails>");

        if (log.isDebugEnabled()) {
            log.debug("returning:" + sw.toString());
        }
        sw.append("</CabinetReceiptItemResponse>");
        return sw.toString();
	}
      /*todo
        the order of the top level in the XML below CANNOT be changed without talking with Steve Skidmore.  Scanners are depending on
        this order.   tngo Sept 3, 2014
     */
}
