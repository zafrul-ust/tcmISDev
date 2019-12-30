package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

public class ListApprover {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String facilityID;
   protected String application;
   protected String personnelID;

   public ListApprover(TcmISDBModel db){
      this.db = db;
   }
   public ListApprover(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public String getFacilityId(){return facilityID;}
   public String getApplication(){return application;}
   public String getPersonnelId(){return personnelID;}

   // set Methods
   public void setFacilityId(String s){facilityID = s;}
   public void setApplication(String s){application = s;}
   public void setPersonnelId(String s){personnelID = s;}

  public void sendMsgToListApprovers(int reqNum) throws Exception{
    String subject = "Material Request #"+reqNum+" is waiting for your use approval.";
	 Hashtable approverData = getLinePendingApproval(reqNum);
	 if (!approverData.isEmpty()) {
	    Enumeration enuma = approverData.keys();
		 while(enuma.hasMoreElements()) {
			 String key = (String)enuma.nextElement();
			 String message = (String)approverData.get(key);
			 message += "\n\nhttps://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n";
			 HelpObjs.sendMail(db,subject,message,(new Integer(key)).intValue(),false);
		 }
	 }
  }

	private Hashtable getLinePendingApproval(int reqNum) {
		Hashtable result = new Hashtable(13);
		DBResultSet dbrs = null;
		ResultSet rs = null;
		try {
			String query = "select fx_personnel_id_to_name(pr.requestor) requestor_name, rli.pr_number,rli.line_item,rli.fac_part_no,"+
			               "rli.quantity,rflr.list_id,l.list_name,rflr.over_mr_limit,rflr.over_mr_limit_threshold,rflr.over_ytd_limit,rflr.over_ytd_limit_threshold"+
			               ",rflr.over_period_limit,rflr.over_period_limit_threshold,"+
			               "fla.personnel_id from purchase_request pr, request_line_item rli, rli_facility_list_release rflr, facility_list_approver fla,list_selection_view l" +
				            " where pr.company_id = rli.company_id and pr.pr_number = rli.pr_number and rli.company_id = rflr.company_id and rflr.company_id = l.company_id and" +
				            " rflr.list_id = l.list_id and rli.pr_number = rflr.pr_number and rli.line_item = rflr.line_item and rli.list_approval_status = rflr.list_approval_status and"+
				            " rli.company_id = fla.company_id and rli.facility_id = fla.facility_id and rflr.list_id = fla.list_id and"+
			               " rli.list_approval_status = 'Pending' and rli.pr_number = "+reqNum+
			               " order by to_number(rli.line_item),rflr.list_id,fla.personnel_id";
			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			String lastApprover = "";
			String lastLineItem = "";
			while (rs.next()){
				String currentApprover = rs.getString("personnel_id");
				String currentLineItem = rs.getString("line_item");
				if (lastApprover.equals(currentApprover)) {
					String message = (String)result.get(currentApprover);
					if (lastLineItem.equals(currentLineItem)) {
						message +="       List: "+rs.getString("list_name")+"\n";
						if ("Y".equals(rs.getString("over_mr_limit"))) {
							message += "     Reason: Over MR limit.\n";
						}
						if ("Y".equals(rs.getString("over_mr_limit_threshold"))) {
							message += "     Reason: Over MR limit threshold.\n";
						}
						if ("Y".equals(rs.getString("over_ytd_limit"))) {
							message += "     Reason: Over year to date limit.\n";
						}
						if ("Y".equals(rs.getString("over_ytd_limit_threshold"))) {
							message += "     Reason: Over year to date limit threshold.\n";
						}
						if ("Y".equals(rs.getString("over_period_limit"))) {
							message += "     Reason: Over period limit.\n";
						}
						if ("Y".equals(rs.getString("over_period_limit_threshold"))) {
							message += "     Reason: Over period limit threshold.\n";
						}
					}else {
						message += "\n\n\n";
						message +="    MR-Line: "+rs.getString("pr_number")+"-"+rs.getString("line_item")+"\n"+
						              "     Part #: "+rs.getString("fac_part_no")+"\n"+
						 	           "Ordered Qty: "+rs.getString("quantity")+"\n"+
						              "       List: "+rs.getString("list_name")+"\n";
						if ("Y".equals(rs.getString("over_mr_limit"))) {
							message += "     Reason: Over MR limit.\n";
						}
						if ("Y".equals(rs.getString("over_mr_limit_threshold"))) {
							message += "     Reason: Over MR limit threshold.\n";
						}
						if ("Y".equals(rs.getString("over_ytd_limit"))) {
							message += "     Reason: Over year to date limit.\n";
						}
						if ("Y".equals(rs.getString("over_ytd_limit_threshold"))) {
							message += "     Reason: Over year to date limit threshold.\n";
						}
						if ("Y".equals(rs.getString("over_period_limit"))) {
							message += "     Reason: Over period limit.\n";
						}
						if ("Y".equals(rs.getString("over_period_limit_threshold"))) {
							message += "     Reason: Over period limit threshold.\n";
						}
					}
					result.put(currentApprover,message);
				}else {
					String message = "  Requestor: "+rs.getString("requestor_name")+"\n"+
						              "    MR-Line: "+rs.getString("pr_number")+"-"+rs.getString("line_item")+"\n"+
						              "     Part #: "+rs.getString("fac_part_no")+"\n"+
						 	           "Ordered Qty: "+rs.getString("quantity")+"\n"+
						              "       List: "+rs.getString("list_name")+"\n";
					if ("Y".equals(rs.getString("over_mr_limit"))) {
						message += "     Reason: Over MR limit.\n";
					}
					if ("Y".equals(rs.getString("over_mr_limit_threshold"))) {
						message += "     Reason: Over MR limit threshold.\n";
					}
					if ("Y".equals(rs.getString("over_ytd_limit"))) {
						message += "     Reason: Over year to date limit.\n";
					}
					if ("Y".equals(rs.getString("over_ytd_limit_threshold"))) {
							message += "     Reason: Over year to date limit threshold.\n";
					}
					if ("Y".equals(rs.getString("over_period_limit"))) {
						message += "     Reason: Over period limit.\n";
					}
					if ("Y".equals(rs.getString("over_period_limit_threshold"))) {
						message += "     Reason: Over period limit threshold.\n";
					}
					result.put(currentApprover,message);
				}
				lastApprover = currentApprover;
				lastLineItem = currentLineItem;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbrs.close();
		}
		return result;
	} //end of method


	public void rejectListUsage(Integer prNumber, String lineItem, String userId,String comment) throws Exception {
		try {
			String query = "update rli_facility_list_release rflr set list_approval_status = 'Rejected',list_approval_date = sysdate,list_approver = "+userId;
			if (!BothHelpObjs.isBlankString(comment)) {
				query += ",list_approval_comment = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comment)+"'";
			}
			query +=	" where (company_id,list_id,pr_number,line_item) in"+
						" (select fla.company_id,fla.list_id,rli.pr_number,rli.line_item"+
						" from request_line_item rli, facility_list_approver fla"+
						" where rli.list_approval_status = 'Pending' and rli.pr_number = "+prNumber+" and rli.line_item = '"+lineItem+"'"+
						" and rli.company_id = rflr.company_id and rli.pr_number = rflr.pr_number"+
						" and rli.line_item = rflr.line_item and rli.list_approval_status = rflr.list_approval_status"+
						" and rli.company_id = fla.company_id and rli.facility_id = fla.facility_id"+
						" and rflr.list_id = fla.list_id and fla.personnel_id = "+userId+")"+
						" and list_approval_status = 'Pending'";
			db.doUpdate(query);

			//update request_line_item
			query = "update request_line_item set list_approval_status = 'Rejected'"+
						",request_line_status = 'Rejected',category_status = 'Rejected',last_updated = sysdate"+
						",last_updated_by = "+userId+" where pr_number = "+prNumber.toString()+" and line_item = '"+lineItem+"'";
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
      }
   }

   public void approveListUsage(Integer prNumber, String lineItem, String userId, String comment) throws Exception {
      String query = "update rli_facility_list_release rflr set list_approval_status = 'Approved',list_approval_date = sysdate,list_approver = "+userId;
		if (!BothHelpObjs.isBlankString(comment)) {
			query += ",list_approval_comment = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comment)+"'";
		}
		query += " where (company_id,list_id,pr_number,line_item) in"+
					" (select fla.company_id,fla.list_id,rli.pr_number,rli.line_item"+
					" from request_line_item rli, facility_list_approver fla"+
					" where rli.list_approval_status = 'Pending' and rli.pr_number = "+prNumber+" and rli.line_item = '"+lineItem+"'"+
					" and rli.company_id = rflr.company_id and rli.pr_number = rflr.pr_number"+
					" and rli.line_item = rflr.line_item and rli.list_approval_status = rflr.list_approval_status"+
					" and rli.company_id = fla.company_id and rli.facility_id = fla.facility_id"+
					" and rflr.list_id = fla.list_id and fla.personnel_id = "+userId+")"+
					" and list_approval_status = 'Pending'";
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
      }
		//check to see if this is the last approval then update rli.list_approval_status = 'Approved'
		if (HelpObjs.countQuery(db,"select count(*) from rli_facility_list_release where (list_approval_status is null or list_approval_status = 'Pending') and pr_number = "+prNumber+" and line_item = '"+lineItem+"'") == 0) {
			try {
				db.doUpdate("update request_line_item set list_approval_status = 'Approved' where pr_number = "+prNumber+" and line_item = '"+lineItem+"'");	                     	
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}

	}

	public static boolean needMyListApproval(TcmISDBModel db, int rNum, String lineNum, String userId) throws Exception {
    boolean result = false;
	 String query = "select count(*) from request_line_item rli, rli_facility_list_release rflr, facility_list_approver fla"+
		             " where rli.list_approval_status = 'Pending' and rli.pr_number = "+rNum+
		  				 " and rli.company_id = rflr.company_id and rli.pr_number = rflr.pr_number and"+
		             " rli.line_item = rflr.line_item and rli.list_approval_status = rflr.list_approval_status"+
		             " and rli.company_id = fla.company_id and rli.facility_id = fla.facility_id and rflr.list_id = fla.list_id"+
		             " and fla.personnel_id = "+userId;
	 if (!BothHelpObjs.isBlankString(lineNum)) {
		 query += " and rli.line_item = '"+lineNum+"'";
	 }
	 try {
      int count = DbHelpers.countQuery(db,query);
      result = count > 0;
    }catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
   }

	public Hashtable getListApproverInfoPerLineTest(int reqNum, String lineItem, String status) throws Exception {
    Hashtable result = new Hashtable(10);
    Vector dataV = new Vector();
    String comment = "";

	String[] colHeads = {"List","Status","Approver","Item Id","MR Value"};
	int[] colWidths = {120,100,180,50,50};
	int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
							 BothHelpObjs.TABLE_COL_TYPE_STRING};

	for (int i = 0; i < 1; i++){
		Object[] oa = new Object[colHeads.length];
		int count = 0;
		oa[count++] = "trong";
		oa[count++] = "Pending";
		oa[count++] = "Mike;Trong";
		oa[count++] = "item_id"+i;
		oa[count++] = "mr_value"+i;
		dataV.add(oa);
	}

	for (int i = 0; i < 2; i++){
		Object[] oa = new Object[colHeads.length];
		int count = 0;
		oa[count++] = "test";
		oa[count++] = "Pending";
		oa[count++] = "Mike;Trong";
		oa[count++] = "item_id"+i;
		oa[count++] = "mr_value"+i;
		dataV.add(oa);
	}

	for (int i = 0; i < 1; i++){
		Object[] oa = new Object[colHeads.length];
		int count = 0;
		oa[count++] = "ngo";
		oa[count++] = "Approved";
		oa[count++] = "Trong";
		oa[count++] = "item_id"+i;
		oa[count++] = "mr_value"+i;
		dataV.add(oa);
	}

	 result.put("LIST_COL",new Integer(0));
	 result.put("ITEM_ID_COL",new Integer(3));
	 result.put("TABLE_HEADERS",colHeads);
    result.put("TABLE_WIDTHS",colWidths);
    result.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    result.put("COMMENT",comment);
    return result;
   }

	//return a list of approvers info who can approve my usage level
   public Hashtable getListApproverInfoPerLine(int reqNum, String lineItem, String status) throws Exception {
    Hashtable result = new Hashtable(10);
    Vector dataV = new Vector();
    String comment = "";
	 int listCol = 3;
	 int itemIdCol = 8;
	 String[] colHeads = {"MR-Line","Part #","Qty","List","Approval Status","Approver","Approval Date","Approval Comments","Item","MR : Limit","YTD : Limit","Period : Limit"};
    int[] colWidths = {50,70,30,120,90,150,80,70,50,90,100,90};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
		  					 BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
							 BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
		                BothHelpObjs.TABLE_COL_TYPE_STRING,
							 BothHelpObjs.TABLE_COL_TYPE_STRING};

	String query = "select * from rli_fac_list_rel_detail_view where pr_number = "+reqNum+" and line_item = '"+lineItem+"'"+
		            " order by list_name";
	getListApproverInfo(dataV,query,colHeads.length);

	//get admin if no approver setup
	if (dataV.size() == 0) {
		itemIdCol = 0;
		colHeads = new String[]{"List","Administrator","Phone","E-mail"};
		colWidths = new int[]{0,120,100,180};
		colTypes = new int[]{BothHelpObjs.TABLE_COL_TYPE_STRING,
		                     BothHelpObjs.TABLE_COL_TYPE_STRING,
			                  BothHelpObjs.TABLE_COL_TYPE_STRING,
									BothHelpObjs.TABLE_COL_TYPE_STRING};
		query = "select b.personnel_id,b.email,b.phone,b.last_name || ', ' ||b.first_name full_name from user_group_member a, personnel b, request_line_item r";
      query += " where a.personnel_id = b.personnel_id and a.user_group_id = 'Administrator' and";
      query += " a.facility_id = r.facility_id and";
      query += " r.pr_number = "+reqNum+ " and";
      query += " r.line_item = '"+lineItem+"'";
      comment = "There is no list approver. Please call your adminstrators.";
		query += " order by b.last_name";
		getAdminInfo(dataV,query,colHeads.length);
	 }

	 result.put("PR_NUMBER",new Integer(reqNum));
	 result.put("LINE_ITEM",lineItem);
	 result.put("LIST_COL",new Integer(listCol));
	 result.put("ITEM_ID_COL",new Integer(itemIdCol));
	 result.put("TABLE_HEADERS",colHeads);
    result.put("TABLE_WIDTHS",colWidths);
    result.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    result.put("COMMENT",comment);
    return result;
   }

	private void getListApproverInfo(Vector dataV, String query, int columnLength) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		try {
			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			while (rs.next()){
				int count = 0;
				Object[] oa = new Object[columnLength];
				oa[count++] = rs.getString("pr_number")+"-"+rs.getString("line_item");
				oa[count++] = rs.getString("cat_part_no");
				oa[count++] = rs.getString("quantity");
				oa[count++] = rs.getString("list_name");
				oa[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("list_approval_status"));
				oa[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("list_approver"));
				oa[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("list_approval_date"));
				oa[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("list_approval_comment"));
				oa[count++] = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
				//mr value/limit
				String tmpVal = BothHelpObjs.makeBlankFromNull(rs.getString("mr_value"));
				if (!BothHelpObjs.isBlankString(tmpVal)) {
					if (!tmpVal.equals("0")) {
						BigDecimal t = new BigDecimal(tmpVal);
						tmpVal = t.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
					}
					tmpVal += " : "+BothHelpObjs.makeBlankFromNull(rs.getString("mr_limit"))+" "+BothHelpObjs.makeBlankFromNull(rs.getString("mr_limit_unit"));
				}
				oa[count++] = tmpVal;
				//ytd value/limit
				tmpVal = BothHelpObjs.makeBlankFromNull(rs.getString("ytd_limit_value"));
				if (!BothHelpObjs.isBlankString(tmpVal)) {
					if (!tmpVal.equals("0")) {
						BigDecimal t = new BigDecimal(tmpVal);
						tmpVal = t.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
					}
					tmpVal += " : "+BothHelpObjs.makeBlankFromNull(rs.getString("ytd_limit"))+" "+BothHelpObjs.makeBlankFromNull(rs.getString("ytd_limit_unit"));
				}
				oa[count++] = tmpVal;
				//period value/limit
				tmpVal = BothHelpObjs.makeBlankFromNull(rs.getString("period_limit_value"));
				if (!BothHelpObjs.isBlankString(tmpVal)) {
					if (!tmpVal.equals("0")) {
						BigDecimal t = new BigDecimal(tmpVal);
						tmpVal = t.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
					}
					tmpVal += " : "+BothHelpObjs.makeBlankFromNull(rs.getString("period_limit"))+" "+BothHelpObjs.makeBlankFromNull(rs.getString("period_limit_unit"));
				}
				oa[count++] = tmpVal;
				dataV.addElement(oa);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbrs.close();
		}
	} //end of method

	private void getAdminInfo(Vector dataV, String query, int columnLength) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		try {
			dbrs = db.doQuery(query);
			rs=dbrs.getResultSet();
			int count = 0;
			while (rs.next()){
				Object[] oa = new Object[columnLength];
				oa[0] = "list"+count++;
				oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("full_name"));
				oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("phone"));
				oa[3] = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
				dataV.addElement(oa);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbrs.close();
		}
	} //end of method



}    //end of class
