package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.UpdateItemShelfLifeBean;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for UpdateItemShelfLifeProcess
 * @version 1.0
 *****************************************************************************/
public class UpdateItemShelfLifeProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public UpdateItemShelfLifeProcess(String client) {
		super(client);
	}

	public UpdateItemShelfLifeProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection<UpdateItemShelfLifeBean> search(ReceivingInputBean input, PersonnelBean personnelBean) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select cig.inventory_group, fx_inventory_group_name(cig.inventory_group) inventory_group_name, ");
		query.append("cig.item_id, fx_item_desc(cig.item_id) item_desc, cig.part_id, m.material_desc, ");
		query.append("cig.shelf_life_days, cig.storage_temp, cig.shelf_life_basis, b.shelf_life_basis_desc, ");
		query.append("cig.last_updated_by, fx_personnel_id_to_name(cig.last_updated_by) last_updated_by_name, ");
		query.append("cig.last_updated_on, cig.label_color, cig.source, cig.comments, ");
		query.append("fx_packaging(cig.item_id) packaging ");
		query.append("from component_inventory_group cig, vv_shelf_life_basis b, part p, material m where ");
		//query.append("hub=").append(input.getHub()).append(" and ");
		query.append("b.shelf_life_basis(+) = cig.shelf_life_basis and ");
		query.append("p.part_id = cig.part_id and p.item_id = cig.item_id and p.material_id = m.material_id and ");
		
		if ( ! StringHandler.isBlankString(input.getInventoryGroup())) {
			query.append("cig.inventory_group = ").append(SqlHandler.delimitString(input.getInventoryGroup()));
		}
		else {
			query.append("cig.inventory_group in (select inventory_group from user_inventory_group where ");
			query.append("personnel_id = ").append(personnelBean.getPersonnelId());
			if(personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0) {
				query.append(" and company_id = '").append(personnelBean.getCompanyId()).append("'");
			}
            if( ! StringHandler.isBlankString(input.getSourceHub())) {
                query.append(" and hub = ").append(input.getSourceHub());
            }
			query.append(")");
		}
		
		String search = input.getSearch();
		if ( ! StringHandler.isBlankString(search)) {
			query.append(" and ");
			if (input.getSearchWhat().equalsIgnoreCase("item id")) {
				query.append("cig.item_id");
			}
			else if (input.getSearchWhat().equalsIgnoreCase("part number")) {
				query.append("");
			}
			
			if (input.getSearchType().equalsIgnoreCase("is")) {
				query.append(" = ").append(SqlHandler.delimitString(search)).append("");
			}
			else if (input.getSearchType().equalsIgnoreCase("contains")) {
				query.append(" like '%").append(SqlHandler.validQuery(search)).append("%'");
			}
			else if (input.getSearchType().equalsIgnoreCase("startswith")) {
				query.append(" like '").append(SqlHandler.validQuery(search)).append("%'");
			}
			else if (input.getSearchType().equalsIgnoreCase("endswith")) {
				query.append(" like '%").append(SqlHandler.validQuery(search)).append("'");
			}
		}
		
		query.append(" order by cig.item_id, inventory_group_name, cig.part_id");
		
		@SuppressWarnings("unchecked")
		Collection<UpdateItemShelfLifeBean> shelfLifeBeanColl = factory.setBean(new UpdateItemShelfLifeBean()).selectQuery(query.toString());
		return shelfLifeBeanColl;
	}

	public ExcelHandler writeExcelFile(ReceivingInputBean headerBean, Collection searchCollection, Locale locale) throws BaseException, Exception {
		log.debug("create excel file....");
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		
		pw.addRow();
		pw.addCellKeyBold("label.hub");
		pw.addCell(headerBean.getSourceHubName());
		
		pw.addRow();
		pw.addCellKeyBold("label.invengroup");
		pw.addCell(headerBean.getInventoryGroup());
		pw.addCellKeyBold("label.search");
		pw.addCell(headerBean.getSearchWhat() + " " + headerBean.getSearchType() + "  " + headerBean.getSearch());

		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.item", "label.description", "label.materialdesc", "label.inventorygroup", "label.part", "label.shelflife(days)", "label.basis", "label.storagetemp", "label.lastupdatedby", "label.lastupdatedon", "label.labelcolor",
				"label.source", "label.comments"};
		/*This array defines the type of the excel column.
		    0 means default depending on the data type. */
		int[] types = { 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH };
		/*This array defines the default width of the column when the Excel file opens.
		    0 means the width will be default depending on the data type.*/
		int[] widths = { 5, 20, 20, 10, 5, 5, 18, 10, 7, 7, 7, 7, 30 };

		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//print rows
		Iterator mainIterator = searchCollection.iterator();
		while (mainIterator.hasNext()) {
			pw.addRow();

			UpdateItemShelfLifeBean updateItemShelfLifeBean = (UpdateItemShelfLifeBean) mainIterator.next();;

			pw.addCell(updateItemShelfLifeBean.getItemId());
			pw.addCell(updateItemShelfLifeBean.getItemDesc());
			pw.addCell(updateItemShelfLifeBean.getMaterialDesc());
			pw.addCell(updateItemShelfLifeBean.getInventoryGroupName());
			pw.addCell(updateItemShelfLifeBean.getPartId());
			pw.addCell(updateItemShelfLifeBean.getShelfLifeDays());
			pw.addCell(updateItemShelfLifeBean.getShelfLifeBasisDesc());
			pw.addCell(updateItemShelfLifeBean.getStorageTemp());
			pw.addCell(updateItemShelfLifeBean.getLastUpdatedByName());
			pw.addCell(updateItemShelfLifeBean.getLastUpdatedOn());
			pw.addCell(updateItemShelfLifeBean.getLabelColor());
			pw.addCell(updateItemShelfLifeBean.getSource());
			pw.addCell(updateItemShelfLifeBean.getComments());
		}
		return pw;
	}
}