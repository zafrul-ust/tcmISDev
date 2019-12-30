package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.WorkAreaAcknowledgementBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ManualCabinetCountInputBean;;


/******************************************************************************
 * Process for ManualCabinetCount
 * 
 * @version 1.0
 *****************************************************************************/

public class WorkAreaAcknowledgementProcess extends BaseProcess {
	Log				log					= LogFactory.getLog(this.getClass());
	private boolean	calledFromNewPage	= false;

	public WorkAreaAcknowledgementProcess(String client) {
		super(client);
	}

	public Collection getSearchData(ManualCabinetCountInputBean input) throws Exception{
		DbManager dbManager = null;
		GenericSqlFactory factory = null;
		try
		{
			dbManager = new DbManager(getClient());
			factory = new GenericSqlFactory(dbManager, new WorkAreaAcknowledgementBean());
				
			StringBuilder query = new StringBuilder("select cbiv.*,wabc.cabinet_count_id,fx_personnel_id_to_name(wabc.personnel_id) disbursed_by,fx_personnel_id_to_name(wabc.ACKNOWLEDGED_BY) ACKNOWLEDGED_BY,wabc.ACKNOWLEDGED_DATE,wabc.disbursed_amount count_quantity,wabc.date_processed from cabinet_bin_item_view cbiv, work_area_bin_count wabc where  cbiv.company_id = wabc.company_id and cbiv.bin_id = wabc.bin_id and rownum <= 100 and cbiv.company_id = '");
			query.append(input.getCompanyId()).append("' and cbiv.facility_id = '").append(input.getFacilityId()).append("'");
			
			if(!StringHandler.isBlankString(input.getApplicationId()))
					query.append(" and cbiv.cabinet_id in ('").append(input.getApplicationId().replaceAll("\\|", "','")).append("')");
			if(input.getDateProcessed() != null) 
				query.append(" and wabc.date_processed > sysdate - ").append(input.getDateProcessed());
			if(!input.isIncludeAckDisburse()) 
				query.append(" and acknowledged_by is null ");
			else
				query.append(" and (acknowledged_by is not null or acknowledged_by is null)  ");
			
			if(!StringHandler.isBlankString(input.getSearchArgument()))
				if("description".equalsIgnoreCase(input.getSearchField()))
					if("contains".equalsIgnoreCase(input.getSearchMode()))
						query.append(" and cbiv.description like '%").append(input.getSearchArgument()).append("%'");
					else	
						query.append(" and cbiv.description = '").append(input.getSearchArgument()).append("'");
				else if("catPartNo".equalsIgnoreCase(input.getSearchField()))
					if("contains".equalsIgnoreCase(input.getSearchMode()))
						query.append(" and cbiv.cat_part_no like '%").append(input.getSearchArgument()).append("%'");
					else	
						query.append(" and cbiv.cat_part_no = '").append(input.getSearchArgument()).append("'");
				else
				{
					if("contains".equalsIgnoreCase(input.getSearchMode()))
						query.append(" and cbiv.item_id like '%").append(input.getSearchArgument()).append("%'");
					else	
						query.append(" and cbiv.item_id = '").append(input.getSearchArgument()).append("'");
				}
			
			query.append(" and cbiv.status = 'A' and (cbiv.cpig_status <> 'O') and cbiv.count_type != 'N' and wabc.processing_status = 'PROCESSED' order by cbiv.cabinet_id,cbiv.bin_id,cbiv.cat_part_no,cbiv.item_id,wabc.count_quantity,wabc.date_processed asc");
	
			return factory.selectQuery(query.toString());
		}
		finally
		{
			dbManager = null;
			factory = null;
		}
	}

	public Collection update(Collection manualCabinetCountInputBeanCollection, BigDecimal personnelId, Locale locale) throws BaseException {
		Vector errorMessages = new Vector();
		DbManager dbManager = null;
		GenericSqlFactory factory = null;
		Connection conn = null;
		try
		{
			dbManager = new DbManager(getClient());
			factory = new GenericSqlFactory(dbManager, new WorkAreaAcknowledgementBean());
			conn = dbManager.getConnection();
			Iterator iterator = manualCabinetCountInputBeanCollection.iterator();
			while (iterator.hasNext()) {
				WorkAreaAcknowledgementBean updateBean = (WorkAreaAcknowledgementBean) iterator.next();
				
				factory.deleteInsertUpdate(new StringBuilder("update work_area_bin_count set ACKNOWLEDGED_BY = ").append(personnelId).append(", ACKNOWLEDGED_DATE = sysdate where cabinet_count_id = ").append(updateBean.getCabinetCountId()).toString(),conn);
				
			}
		}
		catch(Exception e)
		{
			errorMessages.add(new ResourceLibrary("com.tcmis.common.resources.CommonResources",locale).getString("error.db.update"));
			return errorMessages;
		}
		finally
		{
			dbManager.returnConnection(conn);
			factory = null;
			dbManager = null;
		}

		return (errorMessages.size() > 0 ? errorMessages : null);
	}

	public ExcelHandler getExcelReport(ManualCabinetCountInputBean bean, Locale locale) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		Collection data;
		try {
			data = this.getSearchData(bean);
		}
		catch (BaseException e) {
			log.debug("caught BaseException; " + e.toString());
			return pw;
		}
		Iterator iterator = data.iterator();
		pw.addTable();

		// write column headers
		pw.addRow();
		
		String[] headerkeys = { "label.workarea", "label.catalog", "label.partno", "label.disbursedqty", "label.disburseddate", "label.disbursedby", "dbuystatus.dateacknowledged", "label.acknowledgedby", "label.item", "label.packaging", "label.description", "label.binname" };
		
		int[] types = { 0, 0, pw.TYPE_PARAGRAPH, 0,pw.TYPE_NUMBER, pw.TYPE_CALENDAR, 0, pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH, };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths =  { 10, 10, 20, 0, 0, 0, 0, 0, 0, 30, 50, 20};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		pw.setColumnDigit(5, 0);
		
		while (iterator.hasNext()) {
			WorkAreaAcknowledgementBean cabinetBinItemViewBean = (WorkAreaAcknowledgementBean) iterator.next();
			pw.addRow();
			pw.addCell(cabinetBinItemViewBean.getApplicationDesc());
			pw.addCell(cabinetBinItemViewBean.getCatalogDesc());
			pw.addCell(cabinetBinItemViewBean.getCatPartNo());
			pw.addCell(cabinetBinItemViewBean.getCountQuantity());
			pw.addCell(cabinetBinItemViewBean.getDateProcessed());
			pw.addCell(cabinetBinItemViewBean.getDisbursedBy());
			pw.addCell(cabinetBinItemViewBean.getAcknowledgedDate());
			pw.addCell(cabinetBinItemViewBean.getAcknowledgedBy());
			pw.addCell(cabinetBinItemViewBean.getItemId());
			pw.addCell(cabinetBinItemViewBean.getPackaging());
			pw.addCell(cabinetBinItemViewBean.getDescription());
			pw.addCell(cabinetBinItemViewBean.getBinName());
		}
		return pw;
	}

}

	