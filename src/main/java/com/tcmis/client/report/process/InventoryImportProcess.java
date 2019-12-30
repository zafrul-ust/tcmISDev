package com.tcmis.client.report.process;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.client.report.beans.ClientChemListInputBean;
import com.tcmis.client.report.beans.GtImportInventoryBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

public class InventoryImportProcess extends GenericProcess {

	public InventoryImportProcess(String client) {
		super(client);
	}

	public InventoryImportProcess(String client, String locale) {
		super(client, locale);
	}

	public InventoryImportProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection getInventoryImport(ClientChemListInputBean bean, PersonnelBean personnelBean, String companyId) throws BaseException {
		return getSearchResult("select * from table (pkg_import_inventory.FX_SEARCH_IMPORT_INVENTORY({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12})) order by part_no, customer_msds_or_mixture_no,upload_seq_id,customer_msds_number",
				new GtImportInventoryBean(),
				companyId,
				bean.getFacilityId(),
				bean.getWorkAreaGroup(),
				bean.getWorkArea(),
				bean.getType(),
				bean.getSource(),
				bean.getPartNo(),
				bean.getMsdsNo(),
				bean.getUsageStartDate(),
				bean.getUsageEndDate(),
				bean.getUploadId(),
				bean.getEntryStartDate(),
				bean.getEntryEndDate());
	}

	public Collection update(Collection<GtImportInventoryBean> beans, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		Vector error = null;
		String errorMsg = "";

		Vector errorMessages = new Vector();

		if ((beans != null) && (!beans.isEmpty())) {
			for (GtImportInventoryBean bean : beans) {
				try {
					inArgs = new Vector(2);
					inArgs.add(bean.getUploadSeqId());
					inArgs.add(bean.getDataLoadRowId());

					outArgs = new Vector();
					// no error right now.
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					error = (Vector) factory.doProcedure("pkg_import_inventory.P_IMPORT_INVENTORY_DELETE", inArgs, outArgs);
					if (error != null) {
						if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
							errorMsg = (String) error.get(0);
							errorMessages.add(errorMsg);

						}
					}

				}
				catch (Exception e) {
					errorMsg = "Error updating record : " + bean.getUploadSeqId() + ":" + bean.getDataLoadRowId();
					errorMessages.add(errorMsg);
				}

			}
		}

		return errorMessages;

	}


	public ExcelHandler getExcelReport(Collection<GtImportInventoryBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();

		String[] headerkeys = { "label.partno", "label.msdsnumber","label.storagedate","label.quantity","label.unitofmeasure",
                                "label.compmsds","label.totalstoragepounds","label.tradename", "label.workarea",
                                "label.purchasesource", "label.entrydate", "label.enteredby","label.entrytype", "cyclecountresults.label.uploadid" };

		int[] types = { 0, 0, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, 0,
                        0, pw.TYPE_NUMBER, 0, 0,
                        0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_NUMBER };

		pw.applyColumnHeader(headerkeys, types);

		for (GtImportInventoryBean member : data) {
            String tmpEntryType = member.getEntryType();
            if ("I".equals(member.getEntryType())) {
                tmpEntryType = library.getString("label.import");
            }
            if ("M".equals(member.getEntryType())) {
                tmpEntryType = library.getString("label.manual");
            }
            
            BigDecimal tmpQuantity = member.getMsdsUnitAmount();
            if (member.getPartAmount() != null) {
                tmpQuantity = member.getPartAmount();
            }
            String tmpKitMsds = member.getCustomerMsdsOrMixtureNo();
            if (member.getCustomerMsdsOrMixtureNo().equals(member.getCustomerMsdsNumber()))
                tmpKitMsds = "";
            
            pw.addRow();
			pw.addCell(member.getPartNo());
			pw.addCell(tmpKitMsds);
            pw.addCell(member.getStorageDate());
            pw.addCell(tmpQuantity);
            pw.addCell(member.getMsdsUnitOfMeasure());
			pw.addCell(member.getCustomerMsdsNumber());
            pw.addCell(member.getWtLb());
            pw.addCell(member.getTradeName());
            pw.addCell(member.getWorkAreaName());
			pw.addCell(member.getPurchaseSource());
			pw.addCell(member.getInsertDate());
			pw.addCell(member.getInsertedByName());
			pw.addCell(tmpEntryType);
			pw.addCell(member.getUploadSeqId());
		}
		return pw;
	}

	public String getUnitOfMeasure(String companyId, String facilityId, String msdsNumber,String partNumber) throws BaseException {
		return factory.getFunctionValue("pkg_import_inventory.fx_unit_of_measure", companyId, facilityId, msdsNumber, partNumber);
	}

}
