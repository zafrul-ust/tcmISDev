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

import com.tcmis.client.catalog.beans.PurchasingMethodBean;
import com.tcmis.client.report.beans.ClientChemListInputBean;
import com.tcmis.client.report.beans.GtImportConsumptionStageBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.*;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;

public class ClientChemListProcess extends GenericProcess {

	public ClientChemListProcess(String client) {
		super(client);
	}

	public ClientChemListProcess(String client, String locale) {
		super(client, locale);
	}

	public ClientChemListProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection getListChemicals(ClientChemListInputBean bean, PersonnelBean personnelBean, String companyId) throws BaseException {
		return getSearchResult("select * from table (PKG_IMPORT_CONSUMPTION.FX_SEARCH_IMPORT_CONSUMPTION({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12})) order by part_no, customer_msds_or_mixture_no,upload_seq_id,customer_msds_number",
				new GtImportConsumptionStageBean(),
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
		//        return factory.setBean(new GtImportConsumptionStageBean()).selectQuery("select * from GT_IMPORT_CONSUMPTION_STAGE");
	}

	public Collection<ClientChemListInputBean> getLists(PersonnelBean personnelBean) throws BaseException {

		factory.setBean( new ClientChemListInputBean());

		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
		criteria.addCriterion("active", SearchCriterion.EQUALS, "Y");

		SortCriteria sort = new SortCriteria("listName");
		sort.setSortAscending(true);
		return factory.select(criteria, sort, "COMPANY_LIST");
	}

	public Collection update(Collection<GtImportConsumptionStageBean> beans, PersonnelBean personnelBean) throws BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		Vector error = null;
		String errorMsg = "";

		Vector errorMessages = new Vector();

		if ((beans != null) && (!beans.isEmpty())) {
			for (GtImportConsumptionStageBean bean : beans) {
				try {
					inArgs = new Vector(2);
					inArgs.add(bean.getUploadSeqId());
					inArgs.add(bean.getDataLoadRowId());

					outArgs = new Vector();
					// no error right now.
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					error = (Vector) factory.doProcedure("PKG_IMPORT_CONSUMPTION.P_IMPORT_CONSUMPTION_DELETE", inArgs, outArgs);
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


	public ExcelHandler getExcelReport(Collection<GtImportConsumptionStageBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();

		String[] headerkeys = { "label.partno", "label.msdsnumber", "label.usagedate", "label.quantity", "label.unitofmeasure",
                                "label.compmsds", "label.totalusagepounds", "label.tradename", "label.workarea", "label.purchasesource",
                                 "label.entrydate", "label.enteredby", "label.entrytype", "cyclecountresults.label.uploadid" };

		int[] types = { 0, 0, pw.TYPE_DATE, pw.TYPE_NUMBER, 0,
                        0, pw.TYPE_NUMBER, 0, 0, 0,
                        pw.TYPE_DATE, 0, 0, pw.TYPE_NUMBER };

		pw.applyColumnHeader(headerkeys, types);

		for (GtImportConsumptionStageBean member : data) {
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
            pw.addCell(member.getUsageDate());
            pw.addCell(tmpQuantity);
			pw.addCell(member.getMsdsUnitOfMeasure());
            pw.addCell(member.getCustomerMsdsNumber());
            pw.addCell(member.getUsageLb());
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

	public Vector uploadListFile(ScannerUploadInputBean inputBean, PersonnelBean pbean) throws BaseException {

		int loadcount = 0;
		Vector error = new Vector();
		String listId = "";
		String errorS = "";
		boolean missing = false;
		// get
		String fileName = inputBean.getDocumentName();
		File input = inputBean.getTheFile();
		if (input != null) {
			if (log.isDebugEnabled()) {
				log.debug(inputBean.getTheFile().getName());
			}

			Collection inArgs = null;
			Collection outArgs = null;
			Iterator iterator = null;
			String message = null;
			HashMap<String, Integer> pos = new HashMap();

			// reading the file and putting the values in a bean
			try {
				ResourceLibrary library = new ResourceLibrary("scannerupload");
				String dirname = library.getString("upload.backupdir");
				File dir = new File(dirname);
				File f = File.createTempFile("CASList_", ".xls", dir);
				FileHandler.copy(inputBean.getTheFile(), f);
				//        	dirname = "c:\\GeneratedJava\\";
				StringBuilder xmlString = new StringBuilder();
				//    	  java.io.PrintWriter outtxt = new java.io.PrintWriter(new File("C:\\AirgasMapping.txt"));

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
				//				java.util.Date now = new java.util.Date();
				//				String nowS = dateFormat.format(now);

				Vector<Vector<String>> sv = ExcelHandler.read(new FileInputStream(f));
				String listName = (sv.get(0)).get(1);
				String companyId = pbean.getCompanyId();
				String tmpVal = factory.selectSingleValue("select list_id from " + " COMPANY_LIST where list_name = " + this.getSqlString(listName) + " and company_id = " + this.getSqlString(companyId));

				if (!this.isBlank(tmpVal)) // tmpVal is listId
				{
					factory.deleteInsertUpdate("delete from customer.list_chemical# where list_id = " + this.getSqlString(tmpVal) + " and company_id = " + this.getSqlString(companyId));
					factory.deleteInsertUpdate("delete from COMPANY_LIST where list_name = " + this.getSqlString(listName) + " and company_id = " + this.getSqlString(companyId));
				}

				//				BigDecimal nextupLoadSeq = this.factory.getSequence("upload_sequence");
				String header = "";
				int count = 0;
				Vector<String> errorMsg = new Vector();
				//			    if ("0".equalsIgnoreCase(tmpVal))
				// Always get a new list
				{
					(sv.get(2)).get(1);// reference

					try {
						inArgs = new Vector(8);
						inArgs.add("");
						inArgs.add(companyId);
						inArgs.add(listName);//bean.getListName());
						inArgs.add("Y");
						//						((Vector<String>)sv.get(2)).get(1);// reference
						inArgs.add((sv.get(2)).get(1));//bean.getReference());
						inArgs.add("");
						inArgs.add("");
						//						((Vector<String>)sv.get(1)).get(1);// description
						inArgs.add((sv.get(1)).get(1));//	inArgs.add(bean.getListDescription());
						inArgs.add(pbean.getPersonnelId());
						outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.VARCHAR));
						outArgs.add(new Integer(java.sql.Types.VARCHAR));
						if (log.isDebugEnabled()) {
							log.debug("Input Args for PKG_CHEMICAL_LIST.P_LIST_UPSERT :" + inArgs);
						}
						Vector errors1 = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_UPSERT", inArgs, outArgs);
						listId = (String) errors1.get(0);

						//						errorMsg.add(errors1.get(0));
						//						errorMsg.add(errors1.get(1));

					}
					catch (Exception e) {
						errorMsg.add("");
						errorMsg.add("Error Adding List");
					}
					// adding list
					for (Vector<String> v : sv) {
						if (count++ < 4)
							continue;
						inArgs = new Vector();
						try {
							{

								inArgs.add(v.get(0));// 0 is number.
								inArgs.add(listId);
								inArgs.add(companyId);
								inArgs.add(v.get(1)); // 1 is descrption
								inArgs.add(pbean.getPersonnelId());

								outArgs = new Vector(1);
								outArgs.add(new Integer(java.sql.Types.VARCHAR));
								//						     if(bean.getIsAddLine())
								{
									error = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_CHEMICAL_INSERT", inArgs, outArgs);
								}
								//						     else
								//						     {
								//						       if(!StringHandler.isBlankString(bean.getUpdated()))
								//						       {
								//						        error = (Vector)factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_CHEMICAL_UPDATE", inArgs, outArgs);
								//						       }
								//						     }
							}
							if (error != null && error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
								errorMsg.add("<br/>Encounter problem on line " + (count + 1) + ": " + (String) error.get(0));
								errorS += ("<br/>Encounter problem on line " + (count + 1) + ": " + (String) error.get(0));
								missing = true;
							}
							else
								loadcount++;
						}
						catch (Exception ex) {
							errorS += ("<br/>Encounter problem on line " + (count + 1));
							missing = true;
						}
					}
					//				System.out.println("calling proc with records: " + loadcount);
				}
			}
			catch (Exception ex) {
				errorS += ex.getMessage();
				ex.printStackTrace();
			}
		}
		else {
			log.debug("file saving problem.");
		}
		//		if( loadcount != 0 )
		if (errorS != null && errorS.length() != 0)
			errorS += "<br/>Total records loaded: " + loadcount;
		Vector returnV = new Vector();
		returnV.add(listId);
		returnV.add(errorS);
		return returnV;
	}

	public Vector<PurchasingMethodBean> getPurchasingMethod(String companyId) throws BaseException {
		return getPurchasingMethod(companyId,"");
	}

	public Vector<PurchasingMethodBean> getPurchasingMethod(String companyId, String facilityId) throws BaseException {
        StringBuilder query = new StringBuilder("select * from purchasing_method where company_id = ");
        query.append(this.getSqlString(companyId));
        if (!StringHandler.isBlankString(facilityId))
            query.append(" and facility_id = ").append(this.getSqlString(facilityId));
        query.append(" order by facility_id,purchasing_method_name");
        return (Vector<PurchasingMethodBean>) factory.setBean(new PurchasingMethodBean()).selectQuery(query.toString());
	}

	public String getUnitOfMeasure(String companyId, String facilityId, String msdsNumber,String partNumber) throws BaseException {
		return factory.getFunctionValue("pkg_import_consumption.fx_unit_of_measure", companyId, facilityId, msdsNumber, partNumber);
	}

	public Collection getUnitOfMeasureList(String facilityId) {
		Collection coll = new Vector();
		try {
			String query = "select unit_of_measure as MSDS_UNIT_OF_MEASURE from import_uom where facility_id = " + this.getSqlString(facilityId);
			coll = factory.setBean(new GtImportConsumptionStageBean()).selectQuery(query);
		}
		catch (Exception ex) {
		}
		;
		return coll;
	}

}
