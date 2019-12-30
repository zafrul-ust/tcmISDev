package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.WorkAreaStockTransferBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for WorkAreaStockTransferProcess
 * @version 1.0
 * 
 *****************************************************************************/

public class WorkAreaStockTransferProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());

	public WorkAreaStockTransferProcess(String client) {
		super(client);
	}

	public WorkAreaStockTransferProcess(String client,String locale)  {
		super(client,locale);
	}
			
	public Collection getSearchData(WorkAreaSearchTemplateInputBean input, Collection<WorkAreaSearchTemplateInputBean> workAreas) throws BaseException, Exception {		
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new WorkAreaStockTransferBean());

		Iterator<WorkAreaSearchTemplateInputBean> itr = workAreas.iterator();
        String fromApplicationId = itr.next().getApplicationId();
        String toApplicationId = itr.next().getApplicationId();
		
        StringBuilder query = new StringBuilder("select * from table (pkg_work_area_management.fx_work_area_transfer_search(");
		query.append(fromApplicationId).append(", "); // from applicationId
        if (StringHandler.isBlankString(toApplicationId))
            query.append("'', '"); // to applicationId
        else
            query.append(toApplicationId).append(", '"); // to applicationId
        query.append(StringHandler.emptyIfNull(input.getSearchArgument())).append("','");
		query.append(StringHandler.emptyIfNull(input.getCriterion())).append("','");
		query.append(StringHandler.emptyIfNull(input.getCriteria())).append("','");
        query.append(StringHandler.emptyIfNull(input.getInventoryGroup())).append("'");
        query.append(")) ");
		query.append(" order by cat_part_no, from_bin_id, to_bin_id");
		
		log.debug(query.toString());

		return factory.selectQuery(query.toString());
	}

	public Collection transferInventory(WorkAreaSearchTemplateInputBean inputBean, Collection<WorkAreaStockTransferBean> inputLines, PersonnelBean user) throws BaseException {
		Vector errorMessages = new Vector();
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		try{

			Vector results = null;			

			HashMap<String,StringBuffer> quantityList = new HashMap();
			HashMap<String,StringBuffer> itemList = new HashMap();
			HashMap<String,StringBuffer> receiptIDList = new HashMap();
			
			String key;
			StringBuffer quantities;
			StringBuffer items;
			StringBuffer receiptIDs;
	
			// separate data by from_bin_id and to_bin_id groups
			for (WorkAreaStockTransferBean workAreaStockTransferBean : inputLines) {
				key = workAreaStockTransferBean.getFromBinId().toString();
                if (workAreaStockTransferBean.getToBinId() != null)
                    key += "-" + workAreaStockTransferBean.getToBinId().toString();

                quantities = quantityList.get(key);
				items = itemList.get(key);
				receiptIDs = receiptIDList.get(key);
				
				if(quantities == null || quantities.length() < 0)
					quantities = new StringBuffer(Integer.toString(workAreaStockTransferBean.getTransferQuantity()));
				else 
					quantities.append(",").append(Integer.toString(workAreaStockTransferBean.getTransferQuantity()));
				
				if(items == null || items.length() < 0)
					items = new StringBuffer(workAreaStockTransferBean.getItemId().toString());
				else 
					items.append(",").append(workAreaStockTransferBean.getItemId().toString());
				
				if(receiptIDs == null || receiptIDs.length() < 0)
					receiptIDs = new StringBuffer(workAreaStockTransferBean.getReceiptId() != null ? workAreaStockTransferBean.getReceiptId().toString():" ");
				else
					receiptIDs.append(",").append(workAreaStockTransferBean.getReceiptId() != null ? workAreaStockTransferBean.getReceiptId().toString():" ");

                quantityList.put(key, quantities);
				itemList.put(key, items);
				receiptIDList.put(key, receiptIDs);
			}

            // call the procedure with a list of quantities, items and receipt IDs for each from_bin, to_bin key pair
			for(Entry<String, StringBuffer> entry: quantityList.entrySet()){
				key = entry.getKey();
				
				quantities = entry.getValue();
				items = itemList.get(key);
				receiptIDs = receiptIDList.get(key);
				
				String[] binIDs = key.split("-");
                String fromBinId = "";
                String toBinId = "";
                for (int i = 0; i < binIDs.length; i++) {
                    if (i == 0)
                        fromBinId = binIDs[i];
                    if (i == 1)
                        toBinId = binIDs[i];
                }

                Collection inArgs = new Vector(6);
                inArgs.add(fromBinId);
				inArgs.add(toBinId);
				inArgs.add(quantities.toString());
				inArgs.add(items.toString());
				inArgs.add(receiptIDs.toString());
				inArgs.add(user.getPersonnelId());

                Collection optArgs = new Vector(1);
                optArgs.add(StringHandler.emptyIfNull(inputBean.getInventoryGroup()));

                Collection outArgs = new Vector(1);
			    outArgs.add(new Integer(java.sql.Types.VARCHAR));

                results = (Vector) factory.doProcedure("pkg_work_area_management.p_work_area_stock_transfer", inArgs, outArgs, optArgs);
                log.debug(results.toString());

                // only provide one error message since a generic message is used
				if(errorMessages.size() < 1 && results.size()>0 && results.get(0) != null && !results.get(0).equals("OK"))
				{
					errorMessages.add(library.getString("error.generic"));
				} 
			}
			
		}
		catch(Exception e) {
			log.error("Error processing WorkAreaStockTransferProcess" + e.getMessage(), e);
			throw new BaseException(e);
		}
		finally{
			factory = null;
			dbManager = null;
		}
		
		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	
	public ExcelHandler getWorkAreaStockTransferExcelReport(WorkAreaSearchTemplateInputBean inputBean, Collection<WorkAreaSearchTemplateInputBean> workAreas, Locale locale) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		getWorkAreaStockTransferExcelReport(pw,inputBean, workAreas);
		return pw;
	}
	
	private void getWorkAreaStockTransferExcelReport(ExcelHandler pw, WorkAreaSearchTemplateInputBean inputBean, Collection<WorkAreaSearchTemplateInputBean> workAreas) throws Exception {
		Collection<WorkAreaStockTransferBean> data = this.getSearchData(inputBean, workAreas);
		pw.addTable();
		pw.addRow();

		int[] widths = {0, 0, 0, 0, 100};
		int[] types =  {0, pw.TYPE_NUMBER, 0, 0, 0};
		int[] aligns = {0, 0, 0, 0, 0};

		String[] headerkeys = {"label.part", "label.quantity", "label.receipt", "label.item", "label.description"};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		for (WorkAreaStockTransferBean member : data) {
			pw.addRow();

			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getQuantity());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
		}
	}
}
	
