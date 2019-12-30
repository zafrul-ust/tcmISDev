package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.common.process.ApprovedMaterialProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ExcelHandlerXlsx;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;

public class PLUploadProcess extends GenericProcess implements Runnable{
	ResourceLibrary library = null;
    Connection connection = null;
    private String uploadSeq = null;
    
    
	public PLUploadProcess(TcmISBaseAction client) throws BaseException{
		super(client);
		library = new ResourceLibrary("scannerupload");
	}
	public PLUploadProcess(String client, String locale) throws BaseException{
		super(client,locale);
	}	
	
	public String uploadNewFile(FileUploadBean inputBean,PersonnelBean pbean) throws DbReturnConnectionException, SQLException {
		String errorMessage = "";
		StringBuilder error = new StringBuilder();
	
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", pbean.getLocale());
			
			// read the file 
			try {
			    connection = dbManager.getConnection();
				connection.setAutoCommit(false); 
				Vector<Vector<String>> sv = ExcelHandlerXlsx.read(inputBean.getTheFile().getCanonicalPath());
				int row = 0;
				int count = 0;
				StringBuilder lineError;
				Date startDate = null;
				String supplierPartNumber=null,leadTimeInDays=null,multipleOf=null,minimumBuyQty=null,minimumOrderValue=null,
				breakQuantity=null,breakPrice=null,remainingShelfLife=null,buyerId=null;
				
				for(Vector<String> v : sv) {
					row++;
					if( row == 1 ) continue;// line 1 is header.
					if( v == null  )  continue;
					
					lineError = new StringBuilder();				
					// validate permission
					if(v.size() < 1 || StringHandler.isBlankString(v.get(0)) || v.get(0).length() > 80)
						lineError.append(library.getLabel("label.operatingentity")); 
					else
					{
							// validate format
							if(v.size() < 2 || StringHandler.isBlankString(v.get(1)) || !StringHandler.isValidInteger(v.get(1))){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.catalogid"));
							}
							if(v.size() < 4 || StringHandler.isBlankString(v.get(3)) || v.get(3).length() > 80 ){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.inventorygroupname"));
							}
							if(v.size() < 5 || StringHandler.isBlankString(v.get(4)) || !StringHandler.isValidInteger(v.get(4))){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.priority"));
							}
							if(v.size() < 7 || StringHandler.isBlankString(v.get(6)) || !StringHandler.isValidInteger(v.get(6)) || v.get(6).length() > 30){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.supplierid"));
							}
							if(v.size() >= 8 && !StringHandler.isBlankString(v.get(7)))
								if(v.get(7).length() > 500){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.supplierpartnumber"));
								}
								else
									supplierPartNumber = "'"+ v.get(7) + "'";
							if(v.size() >= 9 && !StringHandler.isBlankString(v.get(8)))
								if(!StringHandler.isValidDouble(v.get(8))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.leadtimeindays"));
								}
								else 
									leadTimeInDays = v.get(8);
							if(v.size() < 10 || StringHandler.isBlankString(v.get(9))){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.startdate"));
							}
							else
							{
								try{
									startDate = ExcelHandler.getDate((int)Double.parseDouble(v.get(9)));
								}catch(Exception e)
								{
									lineError.append(library.getLabel("label.startdate"));
								}								
							}
							if(v.size() < 11 || StringHandler.isBlankString(v.get(10)) || v.get(10).length() > 4){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.currency"));
							}
							if(v.size() < 12 || StringHandler.isBlankString(v.get(11)) || !StringHandler.isValidDouble(v.get(11))){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.unitprice"));
							}
							if(v.size() >= 13 && !StringHandler.isBlankString(v.get(12)))
								if(!StringHandler.isValidInteger(v.get(12))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.multipleof"));
								}
								else
									multipleOf = v.get(12);
							if(v.size() >= 14 && !StringHandler.isBlankString(v.get(13)))
								if(!StringHandler.isValidDouble(v.get(13))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.minimumbuyqty"));
								}
								else
									minimumBuyQty = v.get(13);
							if(v.size() >= 15 && !StringHandler.isBlankString(v.get(14)))
								if(!StringHandler.isValidDouble(v.get(14))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.minimumordervalue"));
								}
								else
									minimumOrderValue = v.get(14);
							if(v.size() >= 16 && !StringHandler.isBlankString(v.get(15)))
								if(!StringHandler.isValidDouble(v.get(15))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.breakquantity"));
								}
								else
									breakQuantity = v.get(15);
							if(v.size() >= 17 && !StringHandler.isBlankString(v.get(16)))
								if(!StringHandler.isValidDouble(v.get(16))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.breakprice"));
								}
								else 
									breakPrice = v.get(16);
							if(v.size() < 18  || StringHandler.isBlankString(v.get(17))){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.supplypath"));
							}
							if(v.size() >= 19 && !StringHandler.isBlankString(v.get(18)))
								if(!StringHandler.isValidDouble(v.get(18))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.remainingshelflife"));
								}
								else
									remainingShelfLife = v.get(18);
							if(v.size() < 20 || StringHandler.isBlankString(v.get(19)) || v.get(19).length() > 100){
								if(lineError.length() > 0)
									lineError.append(", ");
								lineError.append(library.getLabel("label.incoterms"));
							}
							if(v.size() >= 21)
								if(!StringHandler.isBlankString(v.get(20)) && !StringHandler.isValidInteger((String)v.get(20))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.buyerid"));
								}
								else
									buyerId = v.get(20);
							// only build the query if there are no errors
							if(lineError.length() > 0)
								error.append("Row " + row + ": Invalid format found in " + lineError.toString() + "<br>");
							else if(error.length() < 1)
							{	
								if(count == 0)
									this.uploadSeq = factory.selectSingleValue("SELECT upload_sequence.NEXTVAL FROM DUAL");
								count++;
								StringBuilder query = new StringBuilder("INSERT INTO DBUY_CONTRACT_UPLOAD_STAGE (operating_entity_name,item_id,INVENTORY_GROUP_NAME,priority, SUPPLIER_ID,");
										query.append("supplier_part_no,LEAD_TIME_DAYS,start_date,CURRENCY_ID,UNIT_PRICE,");
										query.append("multiple_of,MIN_BUY_QUANTITY,MIN_BUY_VALUE,BREAK_QUANTITY,BREAK_UNIT_PRICE,supply_path,");
										query.append("remaining_shelf_life_percent,incoterms,buyer_id,uploaded_by,UPLOAD_SEQUENCE) ");
										query.append("VALUES ('").append(v.get(0)).append("',").append(v.get(1)).append(",'").append(v.get(3)).append("',").append(v.get(4)).append(",").append(v.get(6)).append(",");
										query.append(supplierPartNumber).append(",").append(leadTimeInDays).append(",").append(DateHandler.getOracleToDateFunction(startDate)).append(",'").append(v.get(10)).append("',").append(v.get(11)).append(",");  
										query.append(multipleOf).append(",").append(minimumBuyQty).append(",").append(minimumOrderValue).append(",").append(breakQuantity).append(",").append(breakPrice).append(",'");   
										query.append(v.get(17)).append("',").append(remainingShelfLife).append(",'").append(v.get(19)).append("',").append(buyerId).append(",").append(pbean.getPersonnelId()).append(",").append(this.uploadSeq).append(")");
								factory.deleteInsertUpdate(query.toString(),connection);
							}
					}
				}
				if(count > 0)
				{	
					Vector inArgs = new Vector();
					Vector outArgs = new Vector();
					inArgs.add(uploadSeq);					
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					factory.doProcedure(connection, "pkg_dbuy_contract_upload.p_move_data_from_stage", inArgs,outArgs);
					connection.commit();
					Thread thread = new Thread(this);
					thread.start();
				}	
				else
					error.append(library.getLabel("error.exceluploadnotreadable"));
			}
			catch (Exception e) {
				connection.rollback();
				errorMessage = "Error reading uploaded file:" + e.getMessage();
				error.append(errorMessage);
				log.error(errorMessage);
			} finally {
				connection.setAutoCommit(true);
				dbManager.returnConnection(connection);
			}		
		}
		return error.length() > 0 ? error.toString():null;
	}
	
    public void run() {
        try {
			Vector inArgs = new Vector();
			inArgs.add(this.uploadSeq);
			Vector outArgs = new Vector();
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			factory.doProcedure("pkg_dbuy_contract_upload.p_process", inArgs,outArgs);
        } catch(Exception e){
            log.error("Error calling pkg_dbuy_contract_upload.p_process " + e.getMessage(), e);
        }
    }	

	public String uploadUpdateFile(FileUploadBean inputBean,PersonnelBean pbean) throws DbReturnConnectionException, SQLException {
		String errorMessage = "";
		StringBuilder error = new StringBuilder();
	
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", pbean.getLocale());
			
			// read the file 
			try {
			    connection = dbManager.getConnection();	                
		        
				Vector<Vector<String>> sv = ExcelHandlerXlsx.read(inputBean.getTheFile().getCanonicalPath());
				int row = 0;
				int count = 0;

				String currencyId;
				Date currentStartDate;
				Date newStartDate;
				StringBuilder query = new StringBuilder("INSERT ALL \n");
				StringBuilder lineError; 
				String minBuyOrder = "";
				String minBuyQty = "";
				String leadTime = "";
				String price = "";
				String currentPrice = "";
				String breakPrice = "";
				String breakQty = "";
				String currentDBContractId = "";
				String previousDBContractId = "";
				String prevUnitPrice = "";
				for(Vector<String> v : sv) {
					row++;
					if( row == 1 ) continue;// line 1 is header.
					if( v == null  )  continue;
					
					lineError = new StringBuilder();
					
					currentDBContractId = v.get(0);
					currentPrice = v.get(13);
										
					boolean priceModified = false;					
					if( v.get(13) != null && v.get(13).length() > 0 ) {
						// if new price isn't empty, put the row in DBUY_CONTRACT_PRICE_STAGE
						priceModified = true;
					}
					
					boolean startDateModified = false;					
					if ( v.get(14) != null && v.get(14).length() > 0 ){
						 // if new start date isn't empty, put the row in DBUY_CONTRACT_PRICE_STAGE
						startDateModified = true;
					}
					
					boolean leadTimeModified = false;					
					if ( v.get(20) != null && v.get(20).length() > 0 ) {
						// if new lead time in days isn't empty, put the row in DBUY_CONTRACT_PRICE_STAGE
						leadTimeModified = true;
					}
					
					boolean minBuyQtyModified = false;
					if ( v.get(23) != null && v.get(23).length() > 0 ) {
						// if new minimum buy qty isn't empty, put the row in DBUY_CONTRACT_PRICE_STAGE
						minBuyQtyModified = true;
					}
					
					boolean minBuyOrderModified = false;
					if ( v.get(24) != null && v.get(24).length() > 0) {
						// if new minimum buy order isn't empty, put the row in DBUY_CONTRACT_PRICE_STAGE
						minBuyOrderModified = true;
					}
					
					boolean breakQtyModified = false;
					if ( v.get(17) != null && v.get(17).length() > 0) {
						// if new minimum buy order isn't empty, put the row in BREAK_QUANTITY
						breakQtyModified = true;
					}
					
					boolean breakPriceModified = false;					
					if( v.get(18) != null && v.get(18).length() > 0 ) {
						// if new price isn't empty, put the row in UNIT_PRICE
						breakPriceModified = true;
					}
					
					if(priceModified || startDateModified || leadTimeModified || minBuyQtyModified || minBuyOrderModified || breakQtyModified || breakPriceModified){
						// validate permission
						if(v.get(1) == null)
							lineError.append(library.getLabel("label.operatingentityid"));
						else {
							if (pbean.getPermissionBean().hasOpsEntityPermission("supplierPriceList", v.get(1), null)) {
								// validate format
								if(v.get(0) == null || !StringHandler.isValidDouble(v.get(0))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.id"));
								}
								if(v.get(3) == null){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.inventorygroupname"));
								}
								if(v.get(4) == null){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.shipto"));
								}
								if(v.get(5) == null){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.supplier"));
								}
								if(v.get(8) == null || !StringHandler.isValidDouble((String)v.get(8))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.catalogitem"));
								}
								if(v.get(10) != null && !StringHandler.testValidLetter(v.get(10))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.currency"));
								}
								if(v.get(11) == null || !StringHandler.isValidDouble((String)v.get(11))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.unitprice"));
								}
								if(!StringHandler.isValidDouble((String)v.get(12))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.startDate"));
								}
								if(v.get(19) != null && !StringHandler.isValidDouble((String)v.get(19))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.leadtimeindays"));
								}
								if(v.get(21) != null && !StringHandler.isValidDouble((String)v.get(21))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.minimumbuyqty"));
								}
								if(v.get(22) != null && !StringHandler.isValidDouble((String)v.get(22))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.minimumordervalue"));
								}
								if(v.get(15) != null && !StringHandler.isValidDouble((String)v.get(15))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.breakquantity"));
								}
								if(v.get(16) != null && !StringHandler.isValidDouble((String)v.get(16))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.breakprice"));
								}
								if(priceModified && !StringHandler.isValidDouble((String)v.get(13))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newprice"));
								} 
								else {
									if (v.get(13) != null && !v.get(13).equals("")) {
										BigDecimal tmpVal = (new BigDecimal(v.get(13)));
					                    if (tmpVal.compareTo(BigDecimal.ZERO) < 0) {
					                        if(lineError.length() > 0)
											    lineError.append(", ");
										    lineError.append("label.newprice");
					                    } else 
					                        price = tmpVal.toString();
					                        currentPrice = price;
									}
								}
								
								if(startDateModified && !StringHandler.isValidDouble((String)v.get(14))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newstartdate"));
								}
								if(leadTimeModified && !StringHandler.isValidDouble((String)v.get(20))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newleadtimeindays"));
								} 
								else {
									if (v.get(20) != null && !v.get(20).equals("")) {
										BigDecimal tmpVal = (new BigDecimal(v.get(20)));
										if (tmpVal.compareTo(BigDecimal.ZERO) < 0) {
					                        if(lineError.length() > 0)
											    lineError.append(", ");
										    lineError.append("label.newleadtimeindays");
					                    } else
					                        leadTime = tmpVal.toString();
									}
								}
								
								if(minBuyQtyModified && !StringHandler.isValidDouble((String)v.get(23))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newminimumbuyqty"));
								}
								else {
									if (v.get(23) != null && !v.get(23).equals("")) {
										BigDecimal tmpVal = (new BigDecimal(v.get(23)));
										if (tmpVal.compareTo(BigDecimal.ZERO) < 0) {
					                        if(lineError.length() > 0)
											    lineError.append(", ");
										    lineError.append("label.newminimumbuyqty");
					                    } else
					                        minBuyQty = tmpVal.toString();
									}
								}
								
								if(minBuyOrderModified && !StringHandler.isValidDouble((String)v.get(24))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newminimumordervalue"));
								} 
								else {
									if (v.get(24) != null && !v.get(24).equals("")) {
										BigDecimal tmpVal = (new BigDecimal(v.get(24)));
										if (tmpVal.compareTo(BigDecimal.ZERO) < 0) {
					                        if(lineError.length() > 0)
											    lineError.append(", ");
										    lineError.append("label.newminimumordervalue");
					                    } else
					                        minBuyOrder = tmpVal.toString();
									}
								}
								
								if(breakQtyModified && !StringHandler.isValidDouble((String)v.get(17))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newbreakquantity"));
								}
								else {
									if (v.get(17) != null && !v.get(17).equals("")) {
										BigDecimal tmpVal = (new BigDecimal(v.get(17)));
										if (tmpVal.compareTo(BigDecimal.ZERO) < 0) {
					                        if(lineError.length() > 0)
											    lineError.append(", ");
										    lineError.append("label.newbreakquantity");
					                    } else
					                    	breakQty = tmpVal.toString();
									}
								}
								
								if(breakPriceModified && !StringHandler.isValidDouble((String)v.get(18))){
									if(lineError.length() > 0)
										lineError.append(", ");
									lineError.append(library.getLabel("label.newbreakprice"));
								} 
								else {
									if (v.get(18) != null && !v.get(18).equals("")) {
										BigDecimal tmpVal = (new BigDecimal(v.get(18)));
					                    if (tmpVal.compareTo(BigDecimal.ZERO) < 0) {
					                        if(lineError.length() > 0)
											    lineError.append(", ");
										    lineError.append("label.newbreakprice");
					                    } else
					                    	breakPrice = tmpVal.toString();
									}
								}
								//log.debug("For row = " + row + " price = " + price + " and prevUnitPrice = " + prevUnitPrice + " !prevUnitPrice.trim().equalsIgnoreCase(price.trim()) = " + !prevUnitPrice.trim().equalsIgnoreCase(price.trim()));
								if (previousDBContractId.trim().equalsIgnoreCase(currentDBContractId.trim())) {									
									if ((!prevUnitPrice.equalsIgnoreCase("") && !currentPrice.equalsIgnoreCase("")) && 
											(prevUnitPrice != null && currentPrice != null) &&
											((new BigDecimal(prevUnitPrice.toString())).compareTo(new BigDecimal(currentPrice.toString())) != 0)) {
										if(lineError.length() > 0)
										    lineError.append(", ");
									    lineError.append(library.getLabel("label.unitpricenomatch"));
									}
								}
								
								   
								// only build the query if there are no errors
								if(lineError.length() > 0)
									error.append("Row " + row + ": Invalid format found in " + lineError.toString() + "<br>");
								else if(error.length() < 1){
									currencyId = v.get(10)== null ? "''" : "'"+v.get(10)+"'";
									currentStartDate = ExcelHandler.getDate((int)Double.parseDouble(v.get(12)));
									newStartDate = ExcelHandler.getDate((int)Double.parseDouble(v.get(14)));
									price = (v.get(13) == null || v.get(13).equals("")) ? "''" : "'"+v.get(13)+"'";
									leadTime = (v.get(20) == null || v.get(20).equals("")) ? "''" : "'"+v.get(20)+"'";
									minBuyQty = (v.get(23) == null || v.get(23).equals("")) ? "''" : "'"+v.get(23)+"'";
									minBuyOrder = (v.get(24) == null || v.get(24).equals("")) ? "''" : "'"+v.get(24)+"'";
									breakQty = (v.get(17) == null || v.get(17).equals("")) ? "''" : "'"+v.get(17)+"'";
									breakPrice = (v.get(18) == null || v.get(18).equals("")) ? "''" : "'"+v.get(18)+"'";									
									count++;
									
									query.append("INTO DBUY_CONTRACT_PRICE_STAGE (DBUY_CONTRACT_ID, OPS_ENTITY_ID, INVENTORY_GROUP_NAME, SHIP_TO_LOCATION_ID, " +
											"SUPPLIER, ITEM_ID, CURRENCY_ID, CURRENT_PRICE, CURRENT_PRICE_DT, NEW_UNIT_PRICE, NEW_PRICE_DT, PERSONNEL_ID, STATUS," +
											"CURRENT_LEAD_TIME_DAYS, NEW_LEAD_TIME_DAYS, CURRENT_MIN_BUY_QUANTITY,  NEW_MIN_BUY_QUANTITY, CURRENT_MIN_BUY_VALUE," +
											"NEW_MIN_BUY_VALUE, PRICE_BREAK_QUANTITY, PRICE_BREAK_UNIT_PRICE, CUR_BREAK_QUANTITY, CUR_BREAK_PRICE)");
									query.append("VALUES (" + v.get(0) + ",'"  + v.get(1) + "','"  + v.get(3) + "','"  + v.get(4) + "','"  + v.get(5) + "',"  
											+ v.get(8) + ","  + currencyId + ","  + v.get(11) + "," + DateHandler.getOracleToDateFunction(currentStartDate) + ","  
											+ price + ","  + DateHandler.getOracleToDateFunction(newStartDate) + ", "+ pbean.getPersonnelId() + ",'NEW'," 
											+ v.get(19) + ","+ leadTime + ","+ v.get(21) + ","+ minBuyQty + ","+ v.get(22) + ","+ minBuyOrder + "," + breakQty + "," 
											+ breakPrice + ","+  v.get(15) + ","+ v.get(16) +")\n");
								}
							}  
							else   
								error.append("Row " + row + " : No update permission for this operating entity<br>");

						}						
					}
					previousDBContractId = currentDBContractId;					
					prevUnitPrice = currentPrice;					
				}
				query.append("SELECT * FROM dual");
				
				if(error.length() > 0) {
					error.append("<br> Supplier Price List template was not uploaded <br>");
					
					//If the file has any errors while loading send the file with an error message
					//send email to the logged in user for any mismatch found
					StringBuffer messageBody = new StringBuffer();
					messageBody.append("Following errors were encountered during the upload\n");
					messageBody.append(error.toString() + "\n");
					MailProcess.sendEmail(pbean.getEmail(), null, MailProcess.DEFAULT_FROM_EMAIL, "Price list upload failed", messageBody.toString());
					log.debug("Price list upload failed; Error message email sent to the following email address - " + pbean.getEmail());
					
				} else if( count > 0){
					connection.setAutoCommit(false); 
					factory.deleteInsertUpdate(query.toString(),connection);
					
					Vector inArgs = new Vector();					
					factory.doProcedure(connection, "P_SUPPLIER_PRICE_UPDATE", inArgs);
					connection.commit();				}
			}
			catch (Exception e) {
				connection.rollback();
				errorMessage = "Error reading uploaded file:" + e.getMessage();
				error.append(errorMessage);
				log.error(errorMessage);
			} finally {
				connection.setAutoCommit(true);
				dbManager.returnConnection(connection);
			}
		}
		else {
			errorMessage = "File is null";
			error.append(errorMessage);
			log.debug(errorMessage);
		}

		return error.toString();
	}
}
