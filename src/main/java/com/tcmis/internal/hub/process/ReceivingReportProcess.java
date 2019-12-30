package com.tcmis.internal.hub.process;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.hub.beans.ReceivingReportInputBean;
import com.tcmis.internal.hub.beans.ReceivingReportViewBean;
import com.tcmis.internal.hub.factory.ReceivingReportViewBeanFactory;

/******************************************************************************
 * Process to build a web page for user to view receiving report
 * @version 1.0
 *****************************************************************************/
public class ReceivingReportProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ReceivingReportProcess(String client) {
		super(client);
	}
	
	public ReceivingReportProcess(String client,String locale) {
	    super(client,locale);
    }

	public Collection getSearchResult(ReceivingReportInputBean bean, PersonnelBean personnelBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ReceivingReportViewBeanFactory factory = new ReceivingReportViewBeanFactory(dbManager);
		String whereClause = "";
		SearchCriteria criteria = new SearchCriteria();
		
/*		if (!"ALL".equalsIgnoreCase(bean.getOpsEntityId()) && !"".equalsIgnoreCase(bean.getOpsEntityId())) {
			whereClause = "OPS_ENTITY_ID = '"+bean.getOpsEntityId()+"'";
		} */
		
		if (!"ALL".equalsIgnoreCase(bean.getHub()) && !"".equalsIgnoreCase(bean.getHub())) {
			whereClause += " hub = "+bean.getHub();
		}
		
		if (!"ALL".equalsIgnoreCase(bean.getInventoryGroup()) && !"".equalsIgnoreCase(bean.getInventoryGroup())) {
				whereClause +=" and inventory_group = '"+bean.getInventoryGroup()+"'";
		}
		else  {
			whereClause +=" and INVENTORY_GROUP IN ( select distinct inventory_group from user_inventory_group where personnel_id = "+personnelBean.getPersonnelId()+" and company_id = 'Radian' )";
		}

		//date
		if (bean.getBeginDate() != null && bean.getEndDate() != null) {
			whereClause += " and date_of_receipt >= to_date('"+DateHandler.formatDate(bean.getBeginDate(),"MM/dd/yyyy")+"','mm/dd/yyyy') and "+
			"date_of_receipt < to_date('"+DateHandler.formatDate(bean.getEndDate(),"MM/dd/yyyy")+"','mm/dd/yyyy') + 1";
		}else if (bean.getBeginDate() != null) {
			whereClause += " and date_of_receipt >= to_date('"+DateHandler.formatDate(bean.getBeginDate(),"MM/dd/yyyy")+"','mm/dd/yyyy')";
		}else if (bean.getEndDate() != null) {
			whereClause += " and date_of_receipt < to_date('"+DateHandler.formatDate(bean.getEndDate(),"MM/dd/yyyy")+"','mm/dd/yyyy') + 1";
		}

		//search by
		if (!StringHandler.isBlankString(bean.getSearchText())) {
			if ("IS".equalsIgnoreCase(bean.getSearchType())) {
				whereClause += " and "+bean.getSearchWhat()+" = '"+bean.getSearchText().trim()+"'";
			}else if ("LIKE".equalsIgnoreCase(bean.getSearchType())) {
				whereClause += " and "+bean.getSearchWhat()+" like '%"+bean.getSearchText().trim()+"%'";
			}else if ("STARTSWITH".equalsIgnoreCase(bean.getSearchType())) {
				whereClause += " and "+bean.getSearchWhat()+" like '"+bean.getSearchText().trim()+"%'";
			}else if ("ENDSWITH".equalsIgnoreCase(bean.getSearchType())) {
				whereClause += " and "+bean.getSearchWhat()+" like '%"+bean.getSearchText().trim()+"'";
			}
		}

		String query = "";
		if ("uos".equalsIgnoreCase(bean.getUnitOfMessure())) {
			query = "select x.*, fx_item_desc(x.item_id) item_desc, fx_kit_packaging(x.item_id) packaging from" +
			" (select hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,sum(quantity_received_uos-quantity_returned_uos) quantity_received," +
			" company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description," +
			" po_line_quantity_uos po_line_quantity,inventory_group_name,uos uom" +
			" from customer_receipt_history_view where " + whereClause +
			" group by hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,company_id, facility_id," +
			" customer_po_no, release_no, cat_part_no, part_description,po_line_quantity_uos,inventory_group_name,uos"; 
								
/*			query = "select hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,"+
			"sum(quantity_received_uos-quantity_returned_uos) quantity_received, packaging,"+  // diff
			"item_desc, company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description," +
			"po_line_quantity_uos po_line_quantity,inventory_group_name,uos uom"+   // diff
			" from customer_receipt_history_view where " +whereClause+
			" group by hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,"+
			"packaging, item_desc, company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description,"+
			"po_line_quantity_uos,inventory_group_name,uos";  // diff   */
		}else {
			query = "select x.*, fx_item_desc(x.item_id) item_desc, fx_kit_packaging(x.item_id) packaging from" +
			" (select hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,sum(quantity_received_item_uom-quantity_returned_item_uom) quantity_received," +
			" company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description,po_line_quantity_item_uom po_line_quantity,inventory_group_name,item_uom uom" +
			" from customer_receipt_history_view where " + whereClause + 
			" group by hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description,po_line_quantity_item_uom,inventory_group_name,item_uom"; 

/*			query = "select hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,"+
			"sum(quantity_received_item_uom-quantity_returned_item_uom) quantity_received, packaging,"+    // diff
			"item_desc, company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description," +
			"po_line_quantity_item_uom po_line_quantity,inventory_group_name,item_uom uom"+  // diff
			" from customer_receipt_history_view where " +whereClause+
			" group by hub, inventory_group, date_of_receipt, radian_po, po_line, item_id,"+
			"packaging, item_desc, company_id, facility_id, customer_po_no, release_no, cat_part_no, part_description,"+
			"po_line_quantity_item_uom,inventory_group_name,item_uom";   // diff  */
		}
		query += " order by "+bean.getSortBy() + ") x";

		return factory.selectData(query);
	} //end of method

	public Collection createObjectFromData(Collection dataCollection)  throws Exception {
		Vector finalDataCollection = new Vector(dataCollection.size());
		String lastRowKey = "";
		//String lastRowMonth = "";
		Vector collectionVector = new Vector(dataCollection);
		for (int loop = 0; loop < collectionVector.size(); loop++) {
			ReceivingReportViewBean bean = (ReceivingReportViewBean)collectionVector.elementAt(loop);
			String currentRowKey =  bean.getInventoryGroup()+bean.getRadianPo().toString()+bean.getPoLine().toString()+
			bean.getCustomerPoNo()+bean.getReleaseNo()+bean.getCatPartNo()+bean.getItemId();
			if (lastRowKey.equals(currentRowKey)) {
				ReceivingReportViewBean currentBean = (ReceivingReportViewBean)finalDataCollection.lastElement();
				Collection innerBeanColl = (Collection)currentBean.getQtyDorCollection();
				ReceivingReportViewBean currentInnerBean = new ReceivingReportViewBean();
				currentInnerBean.setQuantityReceived(bean.getQuantityReceived());
				currentInnerBean.setDateOfReceipt(bean.getDateOfReceipt());
				//innerBeanColl.
				//innerBeanColl.add(currentInnerBean);
			}else {
				ReceivingReportViewBean currentBean = new ReceivingReportViewBean();
				BeanHandler.copyAttributes(bean,currentBean);
				Collection qtyDorColl = new Vector();
				ReceivingReportViewBean currentInnerBean = new ReceivingReportViewBean();
				currentInnerBean.setQuantityReceived(bean.getQuantityReceived());
				currentInnerBean.setDateOfReceipt(bean.getDateOfReceipt());
				currentInnerBean.setRowWithSameMonth("New");
				qtyDorColl.add(currentInnerBean);
				currentBean.setQtyDorCollection(qtyDorColl);
				finalDataCollection.addElement(currentBean);
				/*try to figure out which month
				if (bean.getDateOfReceipt() != null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(bean.getDateOfReceipt());
				}else {
					lastRowMonth = "";	
				} */
			}
			lastRowKey = currentRowKey;
		}

		return finalDataCollection;
	}

	public ExcelHandler getExcelReport(ReceivingReportInputBean bean, PersonnelBean personnelBean, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<ReceivingReportViewBean> data = getSearchResult(bean, personnelBean);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	  write column headers
		pw.addRow();
		pw.addCellKeyBold("label.hub");
		pw.addCell(bean.getHubName());
		pw.addCell("");
		pw.addCell("");
		pw.addCellKeyBold("label.dorbegin");
		SimpleDateFormat dateParser =
	        new SimpleDateFormat("dd-MMM-yyyy");
		if (bean.getBeginDate() != null) {
			pw.addCell(dateParser.format(bean.getBeginDate()));
		}else {
			pw.addCell("");
		}

		pw.addRow();
		pw.addCellKeyBold("label.inventory");
		pw.addCell(bean.getInventoryGroupName());
		pw.addCell("");
		pw.addCell("");
		pw.addCellKeyBold("label.dorend");
		if(bean.getEndDate() != null) {
			pw.addCell(dateParser.format(bean.getEndDate()));
		}else {
			pw.addCell("");
		}

		pw.addRow();
		pw.addCellKeyBold("label.searchby");
		pw.addCell(bean.getSearchWhatDesc());
		pw.addCell(bean.getSearchTypeDesc());
		pw.addCell(bean.getSearchText());
		pw.addCellKeyBold("label.sortby");
		pw.addCell(bean.getSortByDesc());

		pw.addRow();
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCellKeyBold("label.unitofmeasure");
		pw.addCell(bean.getUnitOfMessureDesc());
		//blank row
		pw.addRow();

		//result table
		//write column headers
		pw.addRow();
		String[] headerkeys = {
				"label.inventorygroup","label.haaspo","label.poline", "label.customerpo","label.releaseno",
				"label.part","label.partdescription","label.item", "label.itemdescription", "label.poquantity",
				"label.itemuom","label.qtyreceived","label.dor"};

		if ("uos".equalsIgnoreCase(bean.getUnitOfMessure())) 
			headerkeys[10] = "label.uos";
//		}else {
//			headerkeys[10] = "label.itemuom"; 
//		}

		/*This array defines the type of the excel column.
		    0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,
				0,pw.TYPE_PARAGRAPH,0, pw.TYPE_PARAGRAPH,0,
				0,0,pw.TYPE_CALENDAR
		};
		/*This array defines the default width of the column when the Excel file opens.
		    0 means the width will be default depending on the data type.*/
		int[] widths = {
				22,12,0,10,12,
				26,0,9,0,0,
				0,0,0};
		/*This array defines the horizontal alignment of the data in a cell.
		    0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,pw.ALIGN_CENTER,0,0,0,
				0,0,pw.ALIGN_CENTER,0,0,
				0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//now write data
		for (ReceivingReportViewBean member : data) {

			pw.addRow();
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getRadianPo());
			pw.addCell(member.getPoLine());
			pw.addCell(member.getCustomerPoNo());
			pw.addCell(member.getReleaseNo());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getPartDescription());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getPoLineQuantity());
			pw.addCell(member.getUom());
			pw.addCell(member.getQuantityReceived());
			pw.addCell(member.getDateOfReceipt());
		}
		return pw;
	} //end of method
} //end of class
