package com.tcmis.client.report.process;


import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.client.report.beans.GlobalAndCustomerListViewBean;
import com.tcmis.client.report.beans.ListManagementViewBean;
import com.tcmis.client.report.beans.ListManagementInputBean;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;


public class SpecificListManagementProcess extends GenericProcess {

    public SpecificListManagementProcess(String client) {
        super(client);
    }

    public SpecificListManagementProcess(String client, String locale) {
        super(client, locale);
    }

    public Collection<ListManagementViewBean> getListChemicals(ListManagementInputBean bean, PersonnelBean personnelBean)
            throws BaseException {

        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ListManagementViewBean());
        /*
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("listId", SearchCriterion.EQUALS, bean.getListId());
        
        SortCriteria sort = new SortCriteria();
        sort.setSortAscending(true);*/
        
        StringBuilder query = new StringBuilder("select * from REPORT_LIST_DISTINCT_VIEW where LIST_ID = '").append(bean.getListId());
        query.append("' order by fx_first_number_parse(replace(CAS_NUMBER, '-', ''))");
        
        return factory.selectQuery(query.toString());
    }
    
    
    public Collection<GlobalAndCustomerListViewBean> getThresholdColNames(ListManagementInputBean bean)
    throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new GlobalAndCustomerListViewBean());
		
		return factory.selectQuery("select * from list where LIST_ID = '"+ bean.getListId() +"'");
	}
    
    
    public Collection<ListManagementInputBean> getLists(PersonnelBean personnelBean)
            throws BaseException {

        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ListManagementInputBean());
        SearchCriteria criteria = new SearchCriteria();

        criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
        criteria.addCriterion("active", SearchCriterion.EQUALS, "Y");


        SortCriteria sort = new SortCriteria();
        sort.setSortAscending(true);

        sort.addCriterion("listName");
        return factory.select(criteria, sort, "COMPANY_LIST");
    }


    public Collection updateSpecificList(Collection<ListManagementViewBean> beans, PersonnelBean personnelBean) throws
            BaseException, Exception {
        Collection inArgs = null;
        Collection outArgs = null;
        Vector error = null;
        String errorMsg = "";

        Vector errorMessages = new Vector();
        DbManager dbManager = new DbManager(getClient());
        GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

        if ((beans != null) && (!beans.isEmpty())) {
            for (ListManagementViewBean bean : beans) {
                try {

                    if ((!StringHandler.isBlankString(bean.getOk()))) {
                        inArgs = new Vector(3);
                        inArgs.add(bean.getCasNumber());
                        inArgs.add(bean.getListId());
                        inArgs.add(bean.getCompanyId());
                        inArgs.add(personnelBean.getPersonnelId());

                        outArgs = new Vector(1);
                        outArgs.add(new Integer(java.sql.Types.VARCHAR));

                        error = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_CHEMICAL_DELETE", inArgs, outArgs);
                    } else {
                        if (bean.getIsAddLine() || !StringHandler.isBlankString(bean.getUpdated())) {
                            inArgs = new Vector(6);
                            inArgs.add(bean.getCasNumber());
                            inArgs.add(bean.getListId());
                            if (bean.getCompanyId() != null) {
                                inArgs.add(bean.getCompanyId());
                            } else {
                                inArgs.add("");
                            }
                            inArgs.add(bean.getRptChemical());
                            inArgs.add(personnelBean.getPersonnelId());
                            if(bean.getThreshold() != null)
                            	 inArgs.add(bean.getThreshold());
                            else
                            	 inArgs.add(null);
                            if(bean.getThreshold2() != null)
                           	 inArgs.add(bean.getThreshold2());
                            else
                           	 inArgs.add(null);
                            if(bean.getThreshold3() != null)
                           	 inArgs.add(bean.getThreshold3());
                            else
                           	 inArgs.add(null);

                            outArgs = new Vector(1);
                            outArgs.add(new Integer(java.sql.Types.VARCHAR));
                            if (bean.getIsAddLine()) {
                                error = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_CHEMICAL_INSERT", inArgs, outArgs);
                            } else {
                                if (!StringHandler.isBlankString(bean.getUpdated())) {
                                    error = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_CHEMICAL_UPDATE", inArgs, outArgs);
                                }
                            }
                        }
                    }
                    if (error != null) {
                        if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                            errorMsg = (String) error.get(0);
                            errorMessages.add(errorMsg);

                        }
                    }


                } catch (Exception e) {
                    errorMsg = "Error updating record : " + bean.getCasNumber();
                    errorMessages.add(errorMsg);
                }

            }
        }

        return errorMessages;


    }

    public ExcelHandler getExcelReport(ListManagementInputBean bean,Collection<ListManagementViewBean> data, Locale locale) throws
            NoDataException, BaseException, Exception {

        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        ExcelHandler pw = new ExcelHandler(library);
        Vector<GlobalAndCustomerListViewBean> cols = (Vector<GlobalAndCustomerListViewBean>)getThresholdColNames(bean);
        GlobalAndCustomerListViewBean b = cols.firstElement();
        pw.addTable();
        //write column headers
        pw.setColumnAlign(1, pw.ALIGN_LEFT);
        pw.addCellBold(library.getString("label.listname"));
        pw.addCell(b.getListName());
        pw.addRow();
        pw.addCellBold(library.getString("label.listdescription"));
        pw.addCellWrap(b.getListDescription());
        pw.addRow();
        pw.addCellBold(library.getString("label.listthresholdname"));
        pw.addCell(b.getListThresholdName());
        pw.addRow();
        pw.addCellBold(library.getString("label.listthresholdvalue"));
        pw.addCell(b.getListThreshold());
        pw.addRow();
        pw.addCellBold(library.getString("label.listthresholdunit"));
        pw.addCell(b.getListThresholdUnit());
        pw.addRow();
        pw.addCellBold(library.getString("label.reference"));
        pw.addCell(b.getReference());
        pw.addRow();
        pw.addCellBold(library.getString("label.thresholdname1"));
        pw.addCell(b.getThresholdName());
        pw.addRow();
        pw.addCellBold(library.getString("label.thresholdunit1"));
        pw.addCell(b.getThresholdUnit());
        pw.addRow();
        pw.addCellBold(library.getString("label.thresholdname2"));
        pw.addCell(b.getThresholdName2());
        pw.addRow();
        pw.addCellBold(library.getString("label.thresholdunit2"));
        pw.addCell(b.getThresholdUnit2());
        pw.addRow();
        pw.addCellBold(library.getString("label.thresholdname3"));
        pw.addCell(b.getThresholdName3());
        pw.addRow();
        pw.addCellBold(library.getString("label.thresholdunit3"));
        pw.addCell(b.getThresholdUnit3());
        
        boolean thresholdName = false, thresholdName2 = false, thresholdName3 = false;
        
        String[] headerkeys = {
                "label.casnumber", "label.chemicalname"
        };

        int[] types = {
                0, pw.TYPE_PARAGRAPH
        };


        int[] widths = {
                15, 80
        };

        int[] horizAligns = {
                0, 0
        };

        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
        
        if(b.getThresholdName() != null && b.getThresholdName().length() > 0)
        {
        	thresholdName = true;
        	pw.addCellBold(b.getThresholdName() + " (" + b.getThresholdUnit() + ")");
        }
        if(b.getThresholdName2() != null && b.getThresholdName2().length() > 0)
        {
        	thresholdName2 = true;
        	pw.addCellBold(b.getThresholdName2() + " (" + b.getThresholdUnit2() + ")");
        }
        if(b.getThresholdName3() != null && b.getThresholdName3().length() > 0)
        {
        	thresholdName3 = true;
        	pw.addCellBold(b.getThresholdName3() + " (" + b.getThresholdUnit3() + ")");   
        }

        // format the numbers to the special columns
        pw.setColumnDigit(6, 2);
        pw.setColumnDigit(7, 2);

        for (ListManagementViewBean member : data) {

            pw.addRow();
            pw.addCell(member.getCasNumber());
            pw.addCell(member.getRptChemical());
            if(thresholdName)
            	 pw.addCell(member.getThreshold());
            if(thresholdName2)
            	 pw.addCell(member.getThreshold2());
            if(thresholdName3)
            	pw.addCell(member.getThreshold3());
        }
        return pw;
    }
}

