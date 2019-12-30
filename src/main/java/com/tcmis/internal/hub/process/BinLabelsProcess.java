package com.tcmis.internal.hub.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandlerXlsx;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ZplHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.hub.beans.BinLabelsInputBean;
import com.tcmis.internal.hub.beans.HubCountDateBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.VvHubBinsBean;
import com.tcmis.internal.hub.beans.VvHubRoomBean;
import com.tcmis.internal.hub.factory.ReceiptItemPriorBinViewBeanFactory;
import com.tcmis.internal.hub.factory.VvHubBinsBeanFactory;
import com.tcmis.internal.hub.factory.VvHubRoomBeanFactory;
import com.tcmis.ws.scanner.beans.HubBin;

import java.sql.Connection;

/******************************************************************************
 * Process to build a web page for user to view and print bin labels.
 * @version 1.0
 *****************************************************************************/


/**
 * Change History
 * --------------
 * 03/05/09 - Shahzad Butt - Added Update functionality.
 *
 */

public class BinLabelsProcess extends GenericProcess {
	
	Log log = LogFactory.getLog(this.getClass());
    Connection connection = null;

	public BinLabelsProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection getSearchResult(BinLabelsInputBean inputBean) throws Exception {
		DbManager dbManager = new DbManager(getClient());
		VvHubBinsBeanFactory factory = new VvHubBinsBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();		
		
		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, inputBean.getBranchPlant());
		if (!StringHandler.isBlankString(inputBean.getRoom())) {
			criteria.addCriterion("room", SearchCriterion.EQUALS, inputBean.getRoom());
		}
		if (!StringHandler.isBlankString(inputBean.getBinType())) {
			criteria.addCriterion("binType", SearchCriterion.EQUALS, inputBean.getBinType());
		}
		/* Searching by Open Pos Only */

		if ((!StringHandler.isBlankString(inputBean.getShowActiveOnly())) && ("Yes".equalsIgnoreCase(inputBean.getShowActiveOnly()))) {
			criteria.addCriterion("status", SearchCriterion.EQUALS,"A" );
		}
		String s = null;
		s = inputBean.getSearchArgument();
		if (s != null && !s.equals("")) {
			String mode = inputBean.getSearchMode();
			String field = inputBean.getSearchField();
			if (mode.equals("is"))
				criteria.addCriterion(field, SearchCriterion.EQUALS, s);
			if (mode.equals("like"))
				criteria.addCriterion(field, SearchCriterion.LIKE, s,
						SearchCriterion.IGNORE_CASE);
			if (mode.equals("startsWith"))
				criteria.addCriterion(field, SearchCriterion.STARTS_WITH, s,
						SearchCriterion.IGNORE_CASE);
			if (mode.equals("endsWith"))
				criteria.addCriterion(field, SearchCriterion.ENDS_WITH, s,
						SearchCriterion.IGNORE_CASE);
		}
		
		return factory.select(criteria);
	} //end of method

	public Collection update(Collection<VvHubBinsBean> beans) throws BaseException {
		Vector errorMessages = new Vector();
		
		String errorMsg = "";	
		
		DbManager dbManager = new DbManager(this.getClient(), getLocale());
		
		VvHubBinsBeanFactory factory = new VvHubBinsBeanFactory(dbManager);				
		
		for (VvHubBinsBean bean : beans) {
			if ( !StringHandler.isBlankString(bean.getOk()) ) {
				try {
					  SearchCriteria criteria = new SearchCriteria();
					  criteria.addCriterion("status", SearchCriterion.EQUALS, bean.getOldStatus());
					  criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getBranchPlant());					
					  criteria.addCriterion("bin", SearchCriterion.EQUALS, bean.getBin());
					  
					  factory.update(bean, criteria);             
					
				
				} catch (Exception e) {
					errorMsg = "Error updating Bin: "+ bean.getBin()+ e.toString();
					errorMessages.add(errorMsg);
				}
			}
		}

		factory = null;
		dbManager = null;

		return (errorMessages.size()>0?errorMessages:null);
	}
	
	
	public Collection addNewRoom(	VvHubRoomBean inputBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		String errorMsg = "";
		Vector errorMessages = new Vector();
		VvHubRoomBeanFactory factory = new VvHubRoomBeanFactory(dbManager);
		
		try {
			VvHubRoomBean dataBean = new VvHubRoomBean();
			dataBean.setHub(inputBean.getHub());
			dataBean.setRoom(inputBean.getRoom());
			dataBean.setRoomDescription((!StringHandler.isBlankString(inputBean.getRoomDescription()))?inputBean.getRoomDescription():inputBean.getRoom());

			factory.insert(dataBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorMsg = "Error Adding New Room Record"+e.toString();
			errorMessages.add(errorMsg);
		}		
		return errorMessages;
	}

	
	public boolean insertNewBin(VvHubBinsBean bean) throws
	NoDataException,
	BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvHubBinsBeanFactory vvHubBinsBeanFactory = new
		VvHubBinsBeanFactory(dbManager);
		boolean result = true;
		try {
			vvHubBinsBeanFactory.insert(bean);
		}
		catch (Exception ex) {
			result = false;
		}
		return result;
	}
	
	public int updateRecon(String receiptId, String bin) throws BaseException {
		int error = 0;
		DbManager dbManager;
		GenericSqlFactory factory;
		try {
		dbManager = new DbManager(this.getClient(), getLocale());
		factory = new GenericSqlFactory(dbManager);				
		error = factory.deleteInsertUpdate("update receipt set bin = '" + bin + "' where receipt_id = " + receiptId);
		}catch(Exception e){}
		finally{
			factory = null;
			dbManager = null;
		}
		return (error);
	}
	
	public Collection fillRecon(String itemId, String hub) throws BaseException {
		
		ReceiptItemPriorBinViewBeanFactory factory = new ReceiptItemPriorBinViewBeanFactory(new DbManager(this.getClient(), getLocale()));
		SearchCriteria crit = new SearchCriteria();
		crit.addCriterion("itemId", SearchCriterion.EQUALS, "" + itemId + "");
		crit.addCriterion("hub", SearchCriterion.EQUALS, hub);
		crit.addCriterion("status", SearchCriterion.EQUALS, "A");
		crit.addCriterion("onSite", SearchCriterion.EQUALS, "Y");
		return factory.select(crit);
	}
	
	public Collection getRoom(String branchPlant, String bin) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
        String query = "select room from vv_hub_bins where branch_plant = '" + branchPlant + "' and bin = '" + bin + "'";
		Vector v = new Vector();
        HubCountDateBean b = new HubCountDateBean();
		b.setRoom(factory.selectSingleValue(query));
		v.add(b);
		return v;
	}
	
	public Collection<String> getBinTypes() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
        String query = "select bin_type from vv_bin_type order by bin_type";
        Collection<String> binTypes = (Collection<String>) factory.setBean(new HubBin()).selectQuery(query).stream().map(hubbin -> ((HubBin)hubbin).getBinType()).collect(Collectors.toList());
		return binTypes;
	}
	
	public Collection getReceivingOnlyBins(String branchPlant) throws Exception {
		DbManager dbManager = new DbManager(getClient());
		VvHubBinsBeanFactory factory = new VvHubBinsBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();		
		
		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, branchPlant);
		/* Searching by Open Pos Only */
		criteria.addCriterion("binType", SearchCriterion.EQUALS, "Receiving");
		
		return factory.select(criteria);
	} //end of method
	
	public Collection getReceivingOnlyBins(String itemId,String branchPlant) throws Exception {
		DbManager dbManager = new DbManager(getClient());
		/*VvHubBinsBeanFactory factory = new VvHubBinsBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();		
		
		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, branchPlant);
		/* Searching by Open Pos Only */
		/*criteria.addCriterion("receivingOnly", SearchCriterion.EQUALS, "Y");
		
		return factory.select(criteria);*/
		
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new LogisticsViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("status", SearchCriterion.EQUALS,	"A");
		criteria.addCriterion("itemId", SearchCriterion.EQUALS,	itemId);
		criteria.addCriterion("hub", SearchCriterion.EQUALS,	branchPlant);
		return  (Vector)factory.select(criteria,null, "receipt_item_prior_bin_view");
		
		
	} //end of method
	
	public String generateContainerLabel(Collection<VvHubBinsBean> beans,Collection<LocationLabelPrinterBean> printers, BigDecimal personnelId,String hubNum) throws BaseException {
		Random rand = new Random();
		Integer tmpReqNum = new Integer(rand.nextInt());

		ResourceLibrary resource = new ResourceLibrary("label");

		String wwwHome=resource.getString("label.hosturl");
		String urlPath = resource.getString("label.urlpath");
		String fileName = "hubbin"+tmpReqNum.toString();
		String url = wwwHome + urlPath + fileName +".pdf";
		try {
			File dir = new File(resource.getString("label.serverpath"));
			File txtfile = File.createTempFile(fileName, ".txt", dir);
			String jnlpurl = wwwHome + urlPath + fileName + ".jnlp";
	
			if (beans.size() == 0) {
				return "";
			}

			String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '31'";
			String printerPath = "";
			BigDecimal printerRes = null;
			if (printers == null || printers.isEmpty()) {
				printers = factory.setBean(new LocationLabelPrinterBean()).selectQuery(iszplprinter);
			}

			for (LocationLabelPrinterBean printer : printers) {
				printerPath=printer.getPrinterPath();
				printerRes=printer.getPrinterResolutionDpi();
				break;
			}

			if (printerPath.trim().length() > 0) {
				StringBuffer zpl = buildBinLabels(beans,hubNum,printerPath,printerRes);
				ZplHandler.writeJnlpFileToDisk(resource.getString("label.serverpath")+fileName+".jnlp", txtfile.getName(), printerPath, zpl.toString());
				url=jnlpurl;
			}
			else {
				return "";
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			return "";
		}

		return url;
	}
	
	private StringBuffer buildBinLabels(Collection<VvHubBinsBean> beans, String hubNum, String printerpath, BigDecimal printRes) throws BaseException {
		StringBuffer recLabel = new StringBuffer();
		Vector invgrpsprint = new Vector();
		invgrpsprint.add("Dallas");

		ZplDataProcess zplProcess = new ZplDataProcess(getClient());
		recLabel.append(ZplHandler.printTemplatesNoFiles(zplProcess.getTemplates(invgrpsprint, "hubbin", printRes.toPlainString())));

		try {
			for (VvHubBinsBean hubBin : beans) {
				String binname = (String) hubBin.getBin();

				if ( ! StringHandler.isBlankString(hubBin.getOk()) ) {
					recLabel.append(binlabel(binname));
				}
			}
		} catch (Exception e11) {
			e11.printStackTrace();
		}

		return recLabel;
	}
	
	private StringBuffer binlabel(String binname) {
		StringBuffer reclabel = new StringBuffer();
		String linefeedd = "";
		linefeedd += (char) (13);
		linefeedd += (char) (10);

		reclabel.append("^XA" + linefeedd);
		reclabel.append("^XFHUB_BIN^FS" + linefeedd);

		reclabel.append("^FN1^FD" + binname + "^FS" + linefeedd);
		reclabel.append("^FN2^FD^FS" + linefeedd);
		reclabel.append("^PQ1" + linefeedd);
		reclabel.append("^XZ" + linefeedd);

		return reclabel;
	}
	
	public String uploadNewBinsFile(FileUploadBean inputBean,String hub) throws DbReturnConnectionException, SQLException {
		StringBuilder error = null,errorAllAppend = new StringBuilder();
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
	
		if (inputBean.getTheFile() != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}
			//read the file 
			try {
			    connection = dbManager.getConnection();
				GenericSqlFactory factory = new GenericSqlFactory(dbManager,connection);
				Vector<Vector<String>> sv = ExcelHandlerXlsx.read(inputBean.getTheFile().getCanonicalPath());
				Vector<VvHubBinsBean> binsBeansToAdd = new Vector<VvHubBinsBean>();
				int row = 0;
				//preprocess rows to validate data
				boolean insertOK = true;
				for(Vector<String> v : sv) {
					row++;
					if( row == 1 ) continue; // line 1 is header
					if( v == null  )  continue;
					
					StringBuilder lineError = new StringBuilder();
					if(v.size() < 3) //not enough columns check
					{
						lineError.append(library.getLabel("error.excelrowmissingdata")).append(" ").append(row).append("<br/>");
						errorAllAppend.append(lineError);
						insertOK = false;
						continue;	
					}			
					
					boolean nullCellFound = false;
					for(String s : v) //null cell check
						if(s == null) 
						{
							lineError.append(library.getLabel("error.excelrowmissingdata")).append(" ").append(row).append("<br/>");
							errorAllAppend.append(lineError);
							insertOK = false;
							nullCellFound = true;
							break;
						}			
					if(nullCellFound)
						continue;

					String bin = v.get(0),onSite = v.get(1), binType = v.get(2), roomDesc = v.get(3), roomId = null;
					if(bin.length() > 25) //bin name too long check
					{
						lineError.append(library.getLabel("error.excelrowbinnametoolong")).append(" ").append(row).append("<br/>");
						insertOK = false;
					}
					if(!"Y".equalsIgnoreCase(onSite) && !"N".equalsIgnoreCase(onSite) ) //Y or N check
					{
						lineError.append(library.getLabel("error.excelrowinvalidyorn")).append(" ").append(row).append("<br/>");
						insertOK = false;
					}
					if(!validateBinType(binType,factory)) //bin type check
					{
						lineError.append(library.getLabel("error.excelrowinvalidbintype")).append(" ").append(row).append("<br/>");
						insertOK = false;
					}
					roomId = getRoomId(hub,roomDesc,factory);
					if(StringHandler.isBlankString(roomId)) //room id for hub is valid check
					{
						lineError.append(library.getLabel("error.excelrowinvalidbinforthehub")).append(" ").append(row).append("<br/>");
						insertOK = false;
					}
					if(validateBinExists(hub,roomId,bin,factory)) //check if bin exists in room
					{
						lineError.append(library.getLabel("error.binexitsinroom")).append(" ").append(row).append("<br/>");
						insertOK = false;
					}
					
					if(insertOK) //add good bins to insert later
					{
						VvHubBinsBean bean = new VvHubBinsBean();
						bean.setBranchPlant(hub);
						bean.setBin(bin);
						bean.setOnSite(onSite);
						bean.setBinType(binType);
						bean.setRoom(roomId);
						binsBeansToAdd.add(bean);
					}
					else //add good bins to insert later
						errorAllAppend.append(lineError);
					
				}
				if(insertOK && !binsBeansToAdd.isEmpty()) //good bins to insert
				{
					//rollback all if an error occurs
					connection.setAutoCommit(false);
					VvHubBinsBeanFactory vvHubBinsBeanFactory = new VvHubBinsBeanFactory(dbManager);
					for(VvHubBinsBean bean:binsBeansToAdd)
						batchInsertNewBin(bean,vvHubBinsBeanFactory,connection);
					connection.commit();					
				}
				else //append all bad data for all lines
				{
					error = new StringBuilder(library.getLabel("error.exceldatamissingorisincorrect"));
					if(errorAllAppend != null)
						error.append("<br/>").append(errorAllAppend);
				}
			}
			catch (Exception e) {
				error = new StringBuilder(library.getLabel("inventorycountupload.errormessage"));
				log.error("Error uploading new bin file:" + e.getMessage());
			} finally {
				connection.setAutoCommit(true);
				dbManager.returnConnection(connection);
				factory = null;
			}		
		}
		return error != null && error.length() > 0 ? error.toString():null;
	  }
	
		public boolean batchInsertNewBin(VvHubBinsBean bean,VvHubBinsBeanFactory vvHubBinsBeanFactory,Connection connection) throws NoDataException, BaseException, Exception {
			boolean result = true;
			try {
				vvHubBinsBeanFactory.insert(bean,connection);
			}
			catch (Exception ex) {
				result = false;
				connection.rollback();
			}
			return result;
		}
		
		private boolean validateBinType(String binType,GenericSqlFactory factory) throws BaseException {
			return Integer.parseInt(factory.selectSingleValue("select count(bin_type) from vv_bin_type where bin_type = "+SqlHandler.delimitString(binType))) > 0 ? true:false;
		}
		
		private String getRoomId(String hub,String room,GenericSqlFactory factory) throws BaseException {
			return factory.selectSingleValue("select room from vv_hub_room where hub = "+SqlHandler.delimitString(hub)+" and lower(ROOM_DESCRIPTION) = "+SqlHandler.delimitString(room.toLowerCase()));
		}
		
		private boolean validateBinExists(String hub,String room,String bin,GenericSqlFactory factory) throws BaseException {
			return Integer.parseInt(factory.selectSingleValue("select count(bin) from VV_HUB_BINS where BRANCH_PLANT = "+SqlHandler.delimitString(hub)+" and lower(ROOM) = "+SqlHandler.delimitString(room.toLowerCase())+" and lower(bin) = "+SqlHandler.delimitString(bin.toLowerCase())+" and STATUS = 'A'")) > 0 ? true:false;
		}
		
		public ExcelHandlerXlsx getUploadNewBinsTemplateExcel(Locale locale) throws BaseException {

			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandlerXlsx pw = new ExcelHandlerXlsx(library);

			pw.addTable();

			//  write column headers
			pw.addRow();

			/*Pass the header keys for the Excel.*/		
			String[] headerkeys = { "label.bin", "label.atthehubyorn", "label.bintype", "label.roomname"};
			/*This array defines the type of the excel column.
			  0 means default depending on the data type. */
			int[] types = { 0, 0, 0, 0};
			
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/

			int[] widths = {50, 15, 25, 50};
			
			pw.applyColumnHeader(headerkeys, types, widths);

			return pw;
		}
	
} //end of class
