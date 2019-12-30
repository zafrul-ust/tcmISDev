package com.tcmis.internal.hub.process;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.ReceivingDocumentReportBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;

/******************************************************************************
 * Process used by ReceivingDocumentReportProcess
 * @version 1.0
 *****************************************************************************/

public class ReceivingDocumentReportProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ReceivingDocumentReportProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getSearchResult(ReceivingDocumentReportBean inputBean) throws Exception {
		Collection results = new Vector();
		DbManager dbManager = new DbManager(getClient());
		Connection connection = dbManager.getConnection();
		try {
			StringBuilder query = new StringBuilder("select * from (");
			query.append("select rd.receipt_id,rd.document_type,rd.document_url,rd.document_id,rd.company_id,");
			query.append("substr(rd.document_url,instr(rd.document_url,'receipt_documents/')+length('receipt_documents/')) document_name,");
			query.append("fx_personnel_id_to_name(r.qc_user_id) qc_user_name,r.mfg_lot,r.qc_date,fx_inventory_group_name(r.inventory_group) inventory_group_name");
			query.append(" from receipt_document rd, receipt r where rd.receipt_id = r.receipt_id and r.qc_date is not null");
			query.append(" and r.original_receipt_id is null and r.quantity_received > 0 and document_url in (");
			//for document types that are not Supplier Packing List
			query.append(" select rd.document_url from receipt_document rd, receipt r");
			query.append(" where rd.receipt_id = r.receipt_id and rd.document_url not like '%/receiptImages/%'");
			query.append(" and rd.receipt_id = r.receipt_id and r.original_receipt_id is null and r.quantity_received > 0");
			query.append(" and rd.document_type <> 'Supplier Packing List'");
			if (inputBean.hasSourceHub())
				query.append(" and r.hub = ").append(inputBean.getSourceHub());
			if (inputBean.hasInventoryGroup())
				query.append(" and r.inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
			if (inputBean.hasBeginDateJsp())
				query.append(" and r.qc_date >= ").append(DateHandler.getOracleToDateFunction(inputBean.getBeginDateJsp()));
			if (inputBean.hasEndDateJsp())
				query.append(" and r.qc_date <= ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDateJsp()));
			query.append(" group by document_url");
			query.append(" having count(*) > 1 ");
			//for Supplier Packing List document type
			query.append(" union all ");
			query.append(" select document_url from (");
			query.append(" select rd.document_url,rd.receipt_id from receipt_document rd, receipt r");
			query.append(" where rd.receipt_id = r.receipt_id and rd.document_url not like '%/receiptImages/%'");
			query.append(" and rd.receipt_id = r.receipt_id and r.original_receipt_id is null and r.quantity_received > 0");
			query.append(" and rd.document_type = 'Supplier Packing List'");
			if (inputBean.hasSourceHub())
				query.append(" and r.hub = ").append(inputBean.getSourceHub());
			if (inputBean.hasInventoryGroup())
				query.append(" and r.inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
			if (inputBean.hasBeginDateJsp())
				query.append(" and r.qc_date >= ").append(DateHandler.getOracleToDateFunction(inputBean.getBeginDateJsp()));
			if (inputBean.hasEndDateJsp())
				query.append(" and r.qc_date <= ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDateJsp()));
			query.append(" group by document_url,rd.receipt_id");
			query.append(" having count(*) > 1 )");
			query.append(")");
			//receipts without documents
			query.append(" union all");
			query.append(" select r.receipt_id,'' document_type,'' document_url,to_number('') document_id,'' company_id,'' document_name,");
			query.append(" fx_personnel_id_to_name(r.qc_user_id) qc_user_name,r.mfg_lot,r.qc_date,fx_inventory_group_name(r.inventory_group) inventory_group_name");
			query.append(" from receipt r where mfg_lot not like 'Service Fee%' and bin not in ('Dropship Receiving','NBO') and radian_po is null");
			query.append(" and not exists (select null from receipt_document rd where rd.receipt_id = r.receipt_id)");
			if (inputBean.hasSourceHub())
				query.append(" and r.hub = ").append(inputBean.getSourceHub());
			if (inputBean.hasInventoryGroup())
				query.append(" and r.inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
			if (inputBean.hasBeginDateJsp())
				query.append(" and r.qc_date >= ").append(DateHandler.getOracleToDateFunction(inputBean.getBeginDateJsp()));
			if (inputBean.hasEndDateJsp())
				query.append(" and r.qc_date <= ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDateJsp()));
			query.append(" union all");
			query.append(" select r.receipt_id,'' document_type,'' document_url,to_number('') document_id,'' company_id,'' document_name,");
			query.append(" fx_personnel_id_to_name(r.qc_user_id) qc_user_name,r.mfg_lot,r.qc_date,fx_inventory_group_name(r.inventory_group) inventory_group_name");
			query.append(" from receipt r, po p, inventory_group_definition igd where mfg_lot not like 'Service Fee%' and bin not in ('Dropship Receiving','NBO')");
			query.append(" and r.radian_po = p.radian_po and r.inventory_group = igd.inventory_group and igd.location_id = p.ship_to_location_id");
			query.append(" and not exists (select null from receipt_document rd where rd.receipt_id = r.receipt_id)");
			if (inputBean.hasSourceHub())
				query.append(" and r.hub = ").append(inputBean.getSourceHub());
			if (inputBean.hasInventoryGroup())
				query.append(" and r.inventory_group = '").append(inputBean.getInventoryGroup()).append("'");
			if (inputBean.hasBeginDateJsp())
				query.append(" and r.qc_date >= ").append(DateHandler.getOracleToDateFunction(inputBean.getBeginDateJsp()));
			if (inputBean.hasEndDateJsp())
				query.append(" and r.qc_date <= ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDateJsp()));
			query.append(") order by document_url,receipt_id");
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ReceivingDocumentReportBean());
			results = factory.selectQuery(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}

		return results;
	}  //end of method

	public ExcelHandler createExcelReport(ReceivingDocumentReportBean inputbean) throws NoDataException, BaseException, Exception {
		Vector<ReceivingDocumentReportBean> data = (Vector)getSearchResult(inputbean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.operatingentity")+":"+inputbean.getOpsEntityName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.hub")+":"+inputbean.getSourceHubName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.inventorygroup")+":"+inputbean.getInventoryGroupName(),1,3);
		pw.addRow();
		String tmpVal = "";
		if (inputbean.getBeginDateJsp() != null && inputbean.getEndDateJsp() != null)
			tmpVal = " from "+DateHandler.formatDate(inputbean.getBeginDateJsp(),"dd-MMM-yyyy")+ " to "+DateHandler.formatDate(inputbean.getEndDateJsp(),"dd-MMM-yyyy");
		else if (inputbean.getBeginDateJsp() != null)
			tmpVal = " after "+DateHandler.formatDate(inputbean.getBeginDateJsp(),"dd-MMM-yyyy");
		else if (inputbean.getEndDateJsp() != null)
			tmpVal = " before "+DateHandler.formatDate(inputbean.getEndDateJsp(),"dd-MMM-yyyy");
		pw.addTdRegionBold(library.getString("label.qced")+tmpVal,1,3);
		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.inventorygroup",
				"label.receiptid",
				"label.mfglot",
				"label.qceddate",
				"label.qcedby",
				"label.type",
				"label.document"
				};
		/*This array defines the type of the excel column.
 		  0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,0,pw.TYPE_PARAGRAPH};
		/*This array defines the default width of the column when the Excel file opens.
 		  0 means the width will be default depending on the data type.*/
		int[] widths = {20,0,20,20,20,20,50};
		/*This array defines the horizontal alignment of the data in a cell.
 		  0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(ReceivingDocumentReportBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getInventoryGroupName());
			pw.addCell(bean.getReceiptId());
			pw.addCell(bean.getMfgLot());
			pw.addCell(bean.getQcDate());
			pw.addCell(bean.getQcUserName());
			pw.addCell(bean.getDocumentType());
			pw.addCell(bean.getDocumentName());
		}
		return pw;
	}

} //end of class
