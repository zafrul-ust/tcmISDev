package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.KitIndexingQueueInputBean;
import com.tcmis.internal.catalog.beans.KitIndexingQueueViewBean;

public class KitIndexingQueueProcess extends GenericProcess {
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	
	private final String KIT_READY = "READY";

	public KitIndexingQueueProcess(String client,String locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}

    public BigDecimal getNewKitNumber() throws BaseException {
        return factory.getSequence("CUSTOMER_KIT_MSDS_SEQ");
    }

    @SuppressWarnings("unchecked")
	public Collection getCompanyCatalogs(int personnelId) throws BaseException {
		StringBuilder query = new StringBuilder("select * from user_catalog_view where personnel_id = ").append(personnelId);
        query.append(" and (company_id,catalog_id) in (select cf.company_id,cf.catalog_id from customer.facility f, catalog_facility cf where f.allow_mixture = 'Y'");
        query.append(" and f.company_id = cf.company_id and f.facility_id = cf.facility_id)");
        query.append(" order by company_name,catalog_desc");
		return getSearchResult(query.toString(), new KitIndexingQueueInputBean());
	}
	
	public Collection getSearchResult(KitIndexingQueueInputBean bean) throws BaseException {
		StringBuilder queryBuffer = new StringBuilder();
		if ( ! StringHandler.isBlankString(bean.getSearchField())) {
	 		queryBuffer.append(" where lower(MATERIAL_ID || MATERIAL_DESC || CUSTOMER_MIXTURE_NUMBER || MIXTURE_DESC) like lower('%");
			queryBuffer.append(bean.getSearchField());
			queryBuffer.append("%')");
		}
		if ( ! bean.getCompanyId().equalsIgnoreCase("ALL")) {
			if (queryBuffer.length() > 0) {
				queryBuffer.append(" and");
			}
			else {
				queryBuffer.append(" where");
			}
			queryBuffer.append(" COMPANY_ID = ").append(getSqlString(bean.getCompanyId()));
			if ( ! StringHandler.isBlankString(bean.getCatalogId())) {
				queryBuffer.append(" and CUSTOMER_MSDS_DB in (select customer_msds_db from catalog_company where company_id = ").append(getSqlString(bean.getCompanyId()));
                queryBuffer.append(" and catalog_id = ").append(getSqlString(bean.getCatalogId())).append(")");
			}
		}
		
		StringBuilder query = new StringBuilder("select * from kit_index_queue_vw where (customer_msds_db,customer_mixture_number) in (");
		query.append("select distinct customer_msds_db,CUSTOMER_MIXTURE_NUMBER from kit_index_queue_vw").append(queryBuffer);
		query.append(") order by customer_msds_db,CUSTOMER_MIXTURE_NUMBER, MATERIAL_ID");
		return getSearchResult(query.toString(), new KitIndexingQueueViewBean());
	}
	
	public ExcelHandler getExcelReport(KitIndexingQueueInputBean input, Locale locale) throws NoDataException, BaseException, Exception {

		Collection<KitIndexingQueueViewBean> data = getSearchResult(input);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.company", "label.msdsdatabase", "label.kitmsds", "report.label.kitDescription", 
						"label.vocdetect", "label.voc", "label.voclower", "label.vocupper", "label.vocunit", "label.vocsource",
						"label.voclwesdetect", "label.voclwes", "label.voclweslower", "label.voclwesupper", "label.voclwesunit", "label.voclwessource",
						"label.materialid", "label.revisiondate", "label.materialdescription", "label.manufacturer", "label.tradename"};
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, ExcelHandler.TYPE_PARAGRAPH, 
						0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0,
                        0, ExcelHandler.TYPE_DATE, ExcelHandler.TYPE_PARAGRAPH, ExcelHandler.TYPE_PARAGRAPH, ExcelHandler.TYPE_PARAGRAPH };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 16, 0, 0, 0, 0, 0, 0,
                         0, 0, 0, 0, 0, 0, 0,
                         0, 0, 0, 0, 0, 16, 0 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (KitIndexingQueueViewBean member : data) {
            pw.addRow();
            pw.addCell(member.getCompanyName());
            pw.addCell(member.getCustomerMsdsDb());
            pw.addCell(member.getCustomerMixtureNumber());
            pw.addCell(member.getMixtureDesc());
            pw.addCell(member.getMixtureVocDetect());
            pw.addCell(member.getMixtureVoc());
            pw.addCell(member.getMixtureVocLower());
            pw.addCell(member.getMixtureVocUpper());
            pw.addCell(member.getMixtureVocUnit());
            pw.addCell(member.getMixtureVocSource());
            pw.addCell(member.getMixtureVocLwesDetect());
            pw.addCell(member.getMixtureVocLwes());
            pw.addCell(member.getMixtureVocLwesLower());
            pw.addCell(member.getMixtureVocLwesUpper());
            pw.addCell(member.getMixtureVocLwesUnit());
            pw.addCell(member.getMixtureVocLwesSource());
            pw.addCell(member.getMaterialId());
            pw.addCell(member.getComponentRevDate());
            pw.addCell(member.getMaterialDesc());
            pw.addCell(member.getMfgDesc());
            pw.addCell(member.getTradeName());
            
        }
		return pw;
	}
	
	public void updateQueue(Collection data) throws BaseException {
        Vector processedMixtureId = new Vector(data.size());
        Iterator itr = data.iterator();
        while(itr.hasNext()) {
            KitIndexingQueueViewBean bean = (KitIndexingQueueViewBean)itr.next();
            if (bean.getMixtureId() != null && !processedMixtureId.contains(bean.getMixtureId())) {
                StringBuilder query = new StringBuilder("update customer.mixture_material_index_queue set ");
                query.append("mixture_voc=").append(getSqlString((bean.getMixtureVoc()==null)?"":bean.getMixtureVoc())).append(", ");
                query.append("mixture_voc_detect=").append(getSqlString(bean.getMixtureVocDetect())).append(", ");
                query.append("mixture_voc_lower=").append(getSqlString((bean.getMixtureVocLower()==null)?"":bean.getMixtureVocLower())).append(", ");
                query.append("mixture_voc_upper=").append(getSqlString((bean.getMixtureVocUpper()==null)?"":bean.getMixtureVocUpper())).append(", ");
                query.append("mixture_voc_unit=").append(getSqlString(bean.getMixtureVocUnit())).append(", ");
                query.append("mixture_voc_source=").append(getSqlString(bean.getMixtureVocSource())).append(", ");
                query.append("mixture_voc_lwes=").append(getSqlString((bean.getMixtureVocLwes()==null)?"":bean.getMixtureVocLwes())).append(", ");
                query.append("mixture_voc_lwes_detect=").append(getSqlString(bean.getMixtureVocLwesDetect())).append(", ");
                query.append("mixture_voc_lwes_lower=").append(getSqlString((bean.getMixtureVocLwesLower()==null)?"":bean.getMixtureVocLwesLower())).append(", ");
                query.append("mixture_voc_lwes_upper=").append(getSqlString((bean.getMixtureVocLwesUpper()==null)?"":bean.getMixtureVocLwesUpper())).append(", ");
                query.append("mixture_voc_lwes_unit=").append(getSqlString(bean.getMixtureVocLwesUnit())).append(", ");
                query.append("mixture_voc_lwes_source=").append(getSqlString(bean.getMixtureVocLwesSource()));
                query.append(", processing_status=").append(getSqlString(KIT_READY));
                query.append(" where mixture_id =").append(getSqlString(bean.getMixtureId()));
                factory.deleteInsertUpdate(query.toString());
                processedMixtureId.add(bean.getMixtureId());
            }
        } //end of while
    } //end of method

} //end of class
