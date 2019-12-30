package com.tcmis.client.report.process;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.client.report.beans.GlobalAndCustomerListViewBean;
import com.tcmis.client.report.beans.ListManagementInputBean;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;


public class ListManagementProcess extends GenericProcess {

    public ListManagementProcess(String client) {
        super(client);
    }

    public ListManagementProcess(String client, String locale) {
        super(client, locale);
    }

    public String deleteList(ListManagementInputBean inputBean) {
        String result = "OK";
        try {
            DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			Vector cin = new Vector(2);
			cin.addElement(inputBean.getCompanyId());
            cin.add(inputBean.getListId());
            Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.VARCHAR));
			Collection resultData = factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_DELETE", cin, cout);
			Iterator i11 = resultData.iterator();
			String val = "";
			while (i11.hasNext()) {
				val = (String) i11.next();
			}
            if (!StringHandler.isBlankString(val)) {
				result = val;
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Deleting list Error","PKG_CHEMICAL_LIST.P_LIST_DELETE - List_id - "+inputBean.getListId()+"\n"+val);
			}
        }catch(Exception e) {
            e.printStackTrace();
            ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources",this.getLocaleObject());
            result = library.getString("msg.tcmiserror");
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Deleting list Error","PKG_CHEMICAL_LIST.P_LIST_DELETE - List_id - "+inputBean.getListId()+"\n");
        }
        return result;
    } //end of method
   
    public Collection<GlobalAndCustomerListViewBean> getListOfListChemicals(ListManagementInputBean bean, PersonnelBean personnelBean)
    throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new GlobalAndCustomerListViewBean());
		StringBuilder query = new StringBuilder("select * from LIST");
        StringBuilder where = new StringBuilder("");
        String searchText = bean.getListName();
		if (!StringHandler.isBlankString(searchText)) {
            try {
                where.append(" where " ).append( doSearchLogic(StringHandler.replace(searchText,"'","''")));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

		return factory.selectQuery(query.append(where.toString()).append(" order by list_name").toString());
    }
    
    public String doSearchLogic(String search) {
    	 Hashtable r = StringHandler.getLogicalHashFromString(search);
 		Vector opers = (Vector) r.get("OPERATORS");
 		Vector likes = (Vector) r.get("LIKES");
 		String result = "";
 		//no operator
 		if (opers.size() < 1) {
 			result =  "(search_string like lower('%"+search+"%'))";
             return result;
 		}

 		//contains operation in search text
 		result += " ( (search_string like lower('%" + likes.elementAt(0).toString().trim() + "%'))";

         boolean butNot = false;
 		for (int i = 0; i < opers.size(); i++) {
 			String op = opers.elementAt(i).toString();
 			String lk = "like";
 			if (op.equalsIgnoreCase("but not")) {
 				op = "and";
 				lk = "not like";
 				butNot = true;
 			}
 			String searchS = likes.elementAt(i + 1).toString().trim();
 			if (butNot) {
 				result += " " + op + " (search_string "+lk+" lower('%"+searchS+"%'))";
             }else {
 				result += " " + op + " (search_string "+lk+" lower('%"+searchS+"%'))";
             }
 		}
 		result += " ) ";

 		return result;
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
    
    public Vector updateListofLists(Collection<GlobalAndCustomerListViewBean> beans, PersonnelBean personnel) throws BaseException {

        Vector errorMsg = new Vector();
        if ((beans != null) && (!beans.isEmpty())) {
	        SqlHandler handle = new SqlHandler();
	            for (GlobalAndCustomerListViewBean bean : beans) {
			        Collection inArgs = null;
			        Collection outArgs = null;
			    			
			        DbManager dbManager = new DbManager(getClient(), getLocale());
			        GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			
			        if(bean.getIsAddLine() || bean.isUpdated())
			        {
				        String query = handle.validString("" + bean.getListName());
				        /*String tmpVal = factory.selectSingleValue("select count(*) from " +
				                "COMPANY_LIST where list_name = '" + query + "' " +
				                " and company_id = '" + personnel.getCompanyId() + "'");*/
				        //if ("0".equalsIgnoreCase(tmpVal) || bean.isUpdated()) 
				        {
				            try {
				                inArgs = new Vector(14);
				                if(bean.isUpdated())
				                	inArgs.add(bean.getListId());
				                else
				                	inArgs.add("");
				                inArgs.add(personnel.getCompanyId());
				                inArgs.add(bean.getListName());
				                inArgs.add("Y");
				                inArgs.add(bean.getReference());
				                inArgs.add("");
				                inArgs.add("");
				                inArgs.add(bean.getListDescription());
				                inArgs.add(personnel.getPersonnelId());
				                boolean twoUsed = false;
				                boolean threeUsed = false;
				                if(bean.getThresholdName() != null && bean.getThresholdName().length() > 0)
				                {
				                	inArgs.add(bean.getThresholdName());
				                	inArgs.add(bean.getThresholdUnit());
				                }
				                else if(bean.getThresholdName2() != null && bean.getThresholdName2().length()  > 0)
				                {
				                	twoUsed = true;
				                	inArgs.add(bean.getThresholdName2());
				                	inArgs.add(bean.getThresholdUnit2());
				                	
				                }
				                else if(bean.getThresholdName3() != null && bean.getThresholdName3().length()  > 0)
				                {
				                	threeUsed = true;
				                	inArgs.add(bean.getThresholdName3());
				                	inArgs.add(bean.getThresholdUnit3());
				                }
				                else
				                {
				                	inArgs.add(null);
				                	inArgs.add(null);
				                }

				                if(!twoUsed && bean.getThresholdName2() != null && bean.getThresholdName2().length() > 0)
				                {
				                	inArgs.add(bean.getThresholdName2());
				                	inArgs.add(bean.getThresholdUnit2());
				                	
				                }   	
						        else if(!threeUsed && bean.getThresholdName3() != null && bean.getThresholdName3().length() > 0)
				                {
				                	threeUsed = true;
				                	inArgs.add(bean.getThresholdName3());
				                	inArgs.add(bean.getThresholdUnit3());
				                }
						        else
				                {
				                	inArgs.add(null);
				                	inArgs.add(null);
				                }
						        	
						        if(!threeUsed && bean.getThresholdName3() != null && bean.getThresholdName3().length() > 0)
						        {
						           	inArgs.add(bean.getThresholdName3());
				                	inArgs.add(bean.getThresholdUnit3());
						        }
					        	else
					        	{
					        		inArgs.add(null);
					        		inArgs.add(null);
					        	}	
						        inArgs.add(bean.getListThresholdName());
						        inArgs.add(bean.getListThresholdUnit());
						        inArgs.add(bean.getListThreshold());
				                outArgs = new Vector(1);
				                outArgs.add(new Integer(java.sql.Types.VARCHAR));
				                outArgs.add(new Integer(java.sql.Types.VARCHAR));
				                if (log.isDebugEnabled()) {
				                    log.debug("Input Args for PKG_CHEMICAL_LIST.P_LIST_UPSERT :" + inArgs);
				                }
				                Vector error = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_UPSERT", inArgs, outArgs);
				                errorMsg.add(error.get(0));
				                errorMsg.add(error.get(1));
				
				
				            } catch (Exception e) {
				            	e.printStackTrace();
				                errorMsg.add("Error Adding List");
				            }
				        } 
				    }
	            }
        }


        return errorMsg;

    }


    public ExcelHandler getExcelReport(Collection<GlobalAndCustomerListViewBean> data, Locale locale) throws
            NoDataException, BaseException, Exception {

        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        ExcelHandler pw = new ExcelHandler(library);

        pw.addTable();
//write column headers
        pw.addRow();

        String[] headerkeys = {
                "label.name", "label.description","label.listthresholdname","label.listthresholdvalue","label.listthresholdunit","label.reference","label.author","label.date","label.thresholdname1","label.thresholdunit1","label.thresholdname2","label.thresholdunit2","label.thresholdname3","label.thresholdunit3"
        };

        int[] types = {
                0, pw.TYPE_PARAGRAPH,0,0,0,0,0,pw.TYPE_CALENDAR,0,0,0,0,0,0
        };


        int[] widths = {
                30,55,15,15,10,30,10,15,15,10,15,10,15,10
        };

        int[] horizAligns = {
                0,0,0,0,0,0,0,0,0,0,0,0,0,0
        };

        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

        // format the numbers to the special columns
        pw.setColumnDigit(6, 2);
        pw.setColumnDigit(7, 2);

        for (GlobalAndCustomerListViewBean member : data) {

            pw.addRow();
            pw.addCell(member.getListName());
            pw.addCell(member.getListDescription());
            pw.addCell(member.getListThresholdName());
            pw.addCell(member.getListThreshold());
            pw.addCell(member.getListThresholdUnit());
            pw.addCell(member.getReference());
            pw.addCell(member.getInsertName());
            pw.addCell(member.getInsertDate());
            pw.addCell(member.getThresholdName());
            pw.addCell(member.getThresholdUnit());
            pw.addCell(member.getThresholdName2());
            pw.addCell(member.getThresholdUnit2());
            pw.addCell(member.getThresholdName3());
            pw.addCell(member.getThresholdUnit3());
        }
        return pw;
    }

    public Vector uploadListFile(ScannerUploadInputBean inputBean, PersonnelBean pbean) throws BaseException {
        int chemicalListStartIndex = 13;    //NOTE this is to keep track of where the CAS data begin
        int loadcount = 0;
        Vector error = new Vector();
        String listId = "";
        String errorS = "";
        int thresholdCount = 0;
        boolean missing = false;
        // get
        File input = inputBean.getTheFile();
        if (input != null) {
            if (log.isDebugEnabled()) {
                log.debug(inputBean.getTheFile().getName());
            }
            Collection inArgs = null;
            Collection outArgs = null;
            // reading the file and putting the values in a bean
            try {
                ResourceLibrary rLib = new ResourceLibrary("com.tcmis.common.resources.CommonResources",this.getLocaleObject());
                //first make sure that the upload file is an excel file (.xls)
                if (!inputBean.getTheFile().getName().endsWith(".xls")) {
                    error.addElement("");
                    error.addElement(rLib.getString("label.xlsfiletype"));
                    return error;
                }

                ResourceLibrary library = new ResourceLibrary("scannerupload");
                String dirname = library.getString("upload.backupdir");
                File dir = new File(dirname);
                File f = File.createTempFile("CASList_", ".xls", dir);
                FileHandler.copy(inputBean.getTheFile(), f);
                Vector<Vector<String>> sv = ExcelHandler.read(new FileInputStream(f));
                //verify file format
                if (sv.size() < 14) {
                    error.addElement("");
                    error.addElement(rLib.getString("label.wrongformat"));
                    return error;
                }else {  
                    //check number of thresholds
                    if (!StringHandler.isBlankString((String)sv.get(6).get(1))) {
                        thresholdCount++;
                    }
                    if (!StringHandler.isBlankString((String)sv.get(8).get(1))) {
                        thresholdCount++;
                    }
                    if (!StringHandler.isBlankString((String)sv.get(10).get(1))) {
                        thresholdCount++;
                    }

                    if (!"List Name".equals(((Vector<String>) sv.get(0)).get(0)) ||
                        !"List Description".equals(((Vector<String>) sv.get(1)).get(0)) ||
                        !"List Threshold Name".equals(((Vector<String>) sv.get(2)).get(0)) ||
                        !"List Threshold".equals(((Vector<String>) sv.get(3)).get(0)) ||
                        !"List Threshold Unit".equals(((Vector<String>) sv.get(4)).get(0)) ||
                        !"Reference".equals(((Vector<String>) sv.get(5)).get(0)) ||
                        !"Chemical Threshold Name".equals(((Vector<String>) sv.get(6)).get(0)) ||
                        !"Chemical Threshold Unit".equals(((Vector<String>) sv.get(7)).get(0)) ||
                        !"Chemical Threshold 2 Name".equals(((Vector<String>) sv.get(8)).get(0)) ||
                        !"Chemical Threshold 2 Unit".equals(((Vector<String>) sv.get(9)).get(0)) ||
                        !"Chemical Threshold 3 Name".equals(((Vector<String>) sv.get(10)).get(0)) ||
                        !"Chemical Threshold 3 Unit".equals(((Vector<String>) sv.get(11)).get(0)) ||
                        !"CAS Number".equals(((Vector<String>) sv.get(12)).get(0)) ||
                        !"Chemical Name".equals(((Vector<String>) sv.get(12)).get(1)))  {

                        error.addElement("");
                        error.addElement(rLib.getString("label.wrongformat"));
                        return error;
                    } else if (thresholdCount == 1 && StringHandler.isBlankString((String)sv.get(12).get(2))) {
                        error.addElement("");
                        error.addElement(rLib.getString("label.wrongformat"));
                        return error;
                    }else if (thresholdCount == 2 && StringHandler.isBlankString((String)sv.get(12).get(2)) &&
                                                     StringHandler.isBlankString((String)sv.get(12).get(3))) {
                        error.addElement("");
                        error.addElement(rLib.getString("label.wrongformat"));
                        return error;
                    }else if (thresholdCount == 3 && StringHandler.isBlankString((String)sv.get(12).get(2)) &&
                                                     StringHandler.isBlankString((String)sv.get(12).get(3)) &&
                                                     StringHandler.isBlankString((String)sv.get(12).get(4)) ) {
                        error.addElement("");
                        error.addElement(rLib.getString("label.wrongformat"));
                        return error;
                    }
                    else if (StringHandler.isBlankString(((Vector<String>) sv.get(0)).get(1)) || ((Vector<String>) sv.get(0)).get(1).toLowerCase().indexOf("your list name here") != -1)
                    {
                        error.addElement("");
	                   	error.addElement(rLib.getString("label.nolistname"));
	                    return error; 
                    }
                }

                String listName = ((Vector<String>) sv.get(0)).get(1);
                String companyId = pbean.getCompanyId();
                String deleteListId = factory.selectSingleValue("select list_id from " +
                        " COMPANY_LIST where list_name = " + this.getSqlString(listName) +
                        " and company_id = " + this.getSqlString(companyId));

                if (!this.isBlank(deleteListId)) {
                    factory.deleteInsertUpdate("delete from list_chemical where list_id = "
                            + this.getSqlString(deleteListId) ); //+ " and company_id = " + this.getSqlString(companyId)
                    factory.deleteInsertUpdate("delete from unprotected_co_list where list_id = "
                            + this.getSqlString(deleteListId) );//+ " and company_id = " + this.getSqlString(companyId)
                }

                int count = 0;
                Vector<String> errorMsg = new Vector();
                try {
                    inArgs = new Vector(18);
                    inArgs.add("");
                    inArgs.add(companyId);
                    inArgs.add(listName);//bean.getListName());
                    inArgs.add("Y");
                    String[] testArr = new String[25];
                    sv.get(5).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//bean.getReference());
                    }else {
                        inArgs.add("");
                    }
                    inArgs.add("");
                    inArgs.add("");
                    testArr = new String[25];
                    sv.get(1).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	inArgs.add(bean.getListDescription());
                    }else {
                        inArgs.add("");
                    }
                    inArgs.add(pbean.getPersonnelId());
                    testArr = new String[25];
                    sv.get(6).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_threshold_name
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(7).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_threshold_unit
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(8).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_threshold_name2
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(9).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_threshold_unit2
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(10).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_threshold_name3
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(11).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_threshold_unit3
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(2).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_list_threshold_name
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(4).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_list_threshold_unit
                    }else {
                        inArgs.add("");
                    }
                    testArr = new String[25];
                    sv.get(3).copyInto(testArr);
                    if (!this.isBlank(testArr[1])) {
                        inArgs.add(testArr[1]);//	a_list_threshold
                    }else {
                        inArgs.add("");
                    }

                    outArgs = new Vector(1);
                    outArgs.add(new Integer(java.sql.Types.VARCHAR));
                    outArgs.add(new Integer(java.sql.Types.VARCHAR));
                    if (log.isDebugEnabled()) {
                        log.debug("Input Args for PKG_CHEMICAL_LIST.P_LIST_UPSERT :" + inArgs);
                    }
                    Vector errors1 = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_UPSERT", inArgs, outArgs);
                    listId = (String) errors1.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMsg.add("");
                    errorMsg.add("Error Adding List");
                }
                // adding list chemical
                for (int j = chemicalListStartIndex; j < sv.size(); j++) {
                    String[] chemicalData = new String[25];
                    sv.get(j).copyInto(chemicalData);
                    inArgs = new Vector();
                    try {
                        //CAS Number
                        if (!this.isBlank(chemicalData[0])) {
                            inArgs.add(chemicalData[0]);// 0 is number.
                        }else {
                            inArgs.add("");
                        }
                        inArgs.add(listId);
                        inArgs.add(companyId);
                        if (!this.isBlank(chemicalData[1])) {
                            inArgs.add(chemicalData[1]);
                        }else {
                            inArgs.add("");
                        }
                        inArgs.add(pbean.getPersonnelId());
                        if (thresholdCount == 1) {
                            if (!this.isBlank(chemicalData[2])) {
                                inArgs.add(chemicalData[2]);
                            }else {
                                inArgs.add("");
                            }
                            inArgs.add("");
                            inArgs.add("");
                        }else if (thresholdCount == 2) {
                            if (!this.isBlank(chemicalData[2])) {
                                inArgs.add(chemicalData[2]);
                            }else {
                                inArgs.add("");
                            }     
                            if (!this.isBlank(chemicalData[3])) {
                                inArgs.add(chemicalData[3]);
                            }else {
                                inArgs.add("");
                            }
                            inArgs.add("");
                        }else if (thresholdCount == 3) {
                            if (!this.isBlank(chemicalData[2])) {
                                inArgs.add(chemicalData[2]);
                            }else {
                                inArgs.add("");
                            }
                            if (!this.isBlank(chemicalData[3])) {
                                inArgs.add(chemicalData[3]);
                            }else {
                                inArgs.add("");
                            }
                            if (!this.isBlank(chemicalData[4])) {
                                inArgs.add(chemicalData[4]);
                            }else {
                                inArgs.add("");
                            }
                        }else {
                            inArgs.add("");
                            inArgs.add("");
                            inArgs.add("");
                        }

                        outArgs = new Vector(1);
                        outArgs.add(new Integer(java.sql.Types.VARCHAR));
                        error = (Vector) factory.doProcedure("PKG_CHEMICAL_LIST.P_LIST_CHEMICAL_INSERT", inArgs, outArgs);
                        if (error != null && error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                            errorMsg.add("<br/>Encounter problem on line " + (count+1) + ": " + (String) error.get(0));
                            errorS+=("<br/>Encounter problem on line " + (count+1) + ": " + (String) error.get(0));
                            missing = true;
                        } else loadcount++;
                    } catch (Exception ex) {
                        errorS += ("<br/>Encounter problem on line " + (count+1));
                        missing = true;
                    }
                }
            }catch (Exception ex) {
                errorS += ex.getMessage();
                ex.printStackTrace();
            }
        } else {
            log.debug("file saving problem.");
        }
        //		if( loadcount != 0 )
        if( errorS != null && errorS.length() != 0 )
        	errorS += "<br/>Total records loaded: " + loadcount;
        Vector returnV = new Vector();
        returnV.add(listId);
        returnV.add(errorS);
        return returnV;
    }

}

