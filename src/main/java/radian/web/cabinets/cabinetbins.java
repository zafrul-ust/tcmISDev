package radian.web.cabinets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import com.tcmis.common.admin.beans.PersonnelBean;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-20-03 - When checking for use approval of a partnumber also checking with the cabinet name not just application
 * 04-29-04 - Fixed a BUG in dropdowns
 * 06-01-04 - Code Refactoring and Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 * 07-28-04 - Entering a count of Zero for new bins added
 * 07-27-05 - Giving a error message if there is not item in active status for the pasrt they choose to add to a cabinet
 */

public class cabinetbins {
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private PrintWriter out = null;
	//private DBResultSet dbrs=null;
	//private ResultSet searchRs=null;
	private Vector binIdstoLabel = null;
	private boolean allowupdate;
	private boolean intcmIsApplication = false;

	public void setupdateStatus(boolean id) {
		this.allowupdate = id;
	}

	private boolean getupdateStatus() throws Exception {
		return this.allowupdate;
	}

	public cabinetbins(ServerResourceBundle b, TcmISDBModel d) {
		bundle = b;
		db = d;
	}

	public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		response.setContentType("text/html");
		HttpSession relabelsession = request.getSession();

		String personnelid = relabelsession.getAttribute("PERSONNELID") == null ? "" : relabelsession.getAttribute("PERSONNELID").toString();
		PersonnelBean personnelBean = (PersonnelBean) relabelsession.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
			intcmIsApplication = true;
		}

		String searchtext = "";
		String User_Action = "";
		String facility = "";
		String workarea = "";
		String selHub = "";

		searchtext = request.getParameter("cabinetId");
		if (searchtext == null)
			searchtext = "";
		searchtext = searchtext.trim();

		User_Action = request.getParameter("UserAction");
		if (User_Action == null)
			User_Action = "New";
		User_Action = User_Action.trim();

		facility = request.getParameter("facilityName");
		if (facility == null)
			facility = "";
		facility = facility.trim();

		workarea = request.getParameter("workAreaName");
		if (workarea == null)
			workarea = "";
		workarea = workarea.trim();

		selHub = request.getParameter("hubC");
		if (selHub == null)
			selHub = "";
		selHub = selHub.trim();

		String paper_size = request.getParameter("paperSize");
		if (paper_size == null)
			paper_size = "31";

		String selectedcab = request.getParameter("cabName");
		if (selectedcab == null)
			selectedcab = "";
		selectedcab = selectedcab.trim();

		String selcatalog = request.getParameter("catname");
		if (selcatalog == null)
			selcatalog = "";
		selcatalog = selcatalog.trim();

		String binname = request.getParameter("binname");
		if (binname == null)
			binname = "";
		binname = binname.trim();

		String partno = request.getParameter("catpartno");
		if (partno == null)
			partno = "";
		partno = partno.trim();

		String reorderpt = request.getParameter("rorpoint");
		if (reorderpt == null)
			reorderpt = "0";
		reorderpt = reorderpt.trim();

		String stocklvl = request.getParameter("stklvl");
		if (stocklvl == null)
			stocklvl = "0";
		stocklvl = stocklvl.trim();

		String leadtimedays = request.getParameter("leadtimedays");
		if (leadtimedays == null)
			leadtimedays = "0";
		leadtimedays = leadtimedays.trim();

		//System.out.println( "User_Action   "+User_Action+"  Cabinet is " + searchtext + " facility " + facility+ "  workarea   "+workarea+"  selHub  "+selHub+"" );

		try {
			Hashtable initialData = null;
			Hashtable hub_list_set = new Hashtable();
			String initialDataLoaded = "";

			initialDataLoaded = relabelsession.getAttribute("CABINET_LABEL_DATA_LOADED") == null ? "" : relabelsession.getAttribute("CABINET_LABEL_DATA_LOADED").toString();

			if (initialDataLoaded.equalsIgnoreCase("Yes")) {
				initialData = (Hashtable) relabelsession.getAttribute("INITIAL_CABINET_LABEL_DATA");
				hub_list_set = (Hashtable) relabelsession.getAttribute("HUB_PREF_LIST");
			}
			else {
				String CompanyID = relabelsession.getAttribute("COMPANYID").toString();
				Hashtable hub_list_set1 = (Hashtable) relabelsession.getAttribute("HUB_PREF_LIST");
				Hashtable hub_list1 = (Hashtable) (hub_list_set1.get("HUB_LIST"));

				/*if (this.getupdateStatus())
				{
				   hub_list_set1  = radian.web.HTMLHelpObj.createHubList(db,"Cabinet Label",personnelid,CompanyID);
				}
				else
				{
				   hub_list_set1  = radian.web.HTMLHelpObj.createAllHubList(db);
				}*/
				//relabelsession.setAttribute("CABINET_LABEL_HUB_SET", hub_list_set1 );

				if (hub_list1.size() > 0) {
					initialData = radian.web.HTMLHelpObj.getcabinetInitialData(db, hub_list_set1);
					relabelsession.setAttribute("INITIAL_CABINET_LABEL_DATA", initialData);
				}

				relabelsession.setAttribute("CABINET_LABEL_DATA_LOADED", "Yes");

				hub_list_set = hub_list_set1;
			}

			if (User_Action.equalsIgnoreCase("Bindetails")) {
				Vector data = new Vector();

				try {
					data = getResult(searchtext);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					out.println(radian.web.HTMLHelpObj.printHTMLError());
				}

				relabelsession.setAttribute("CAB_BIN_LABEL_DATA", data);

				Hashtable sum = (Hashtable) (data.elementAt(0));
				int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();

				buildHeader("", paper_size, selHub);

				if (0 == count) {
					out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
				}
				else {
					//out.println( buildDetails( data ) );
					buildDetails(data, facility);
				}

				//clean up
				data = null;
			}
			else if (User_Action.equalsIgnoreCase("generatebinlabels")) {
				Vector retrn_data = (Vector) relabelsession.getAttribute("CAB_BIN_LABEL_DATA");
				Vector synch_data = null;
				int count = 0;

				if (retrn_data != null && retrn_data.size() > 0) {
					synch_data = SynchServerData(request, retrn_data);
					relabelsession.setAttribute("CAB_BIN_LABEL_DATA", synch_data);

					Hashtable sum = (Hashtable) (synch_data.elementAt(0));
					count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();
				}
				else {
					binIdstoLabel = new Vector();
					String[] bins = request.getParameterValues("binId");
					count = bins.length;
					for(int i=0; i < count; i++){
						binIdstoLabel.add(bins[i]);
					}
				}

				relabelsession.setAttribute("LABEL_BIN_IDS", binIdstoLabel);
				relabelsession.setAttribute("LABEL_FACILITY_NAME", facility);


				if (0 == count) {
					buildHeader("", paper_size, selHub);
					out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
				}
				else {
					buildHeader("GENERATE_LABELS", paper_size, selHub);
					//out.println( buildDetails( synch_data ) );
					if (synch_data != null) {
						buildDetails(synch_data, facility);
					}
					else {
						if ("Andover".equalsIgnoreCase(facility))
                        {
                       out.println("<script>var noPopUp= false;");	
						out.println("</script></body></html>\n");
                        }
                        else
                        {
                        out.println("<script>var noPopUp=");
						out.println(User_Action.equalsIgnoreCase("generatebaseline") ? "false" : "true" + ";");
						out.println("</script></body></html>\n");
                        }                                                
					}

				}
			}

			else if (User_Action.equalsIgnoreCase("AddandQuitbin") || User_Action.equalsIgnoreCase("AddandContinuebin")) {
				boolean addresult = true;
				int checkpartcount = 0;
				String errmsg = "";
				String cmpid = "";
				String orderingap = "";
				String binid = "";

				if (binname.length() > 0 && partno.length() > 0) {
					String addquery = "select COMPANY_ID, FACILITY_ID, ORDERING_APPLICATION, CABINET_ID, USE_APPLICATION, CABINET_NAME, ACCOUNT_SYS_ID from cabinet where CABINET_ID = " + selectedcab + " ";
					ResultSet rs = null;
					DBResultSet dbrs = null;
					try {
						dbrs = db.doQuery(addquery);
						rs = dbrs.getResultSet();
						while (rs.next()) {
							cmpid = (rs.getString("COMPANY_ID") == null ? "" : rs.getString("COMPANY_ID"));
							orderingap = (rs.getString("ORDERING_APPLICATION") == null ? "" : rs.getString("ORDERING_APPLICATION"));
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					finally {
						if (dbrs != null) {
							dbrs.close();
						}
					}

					String checkquery = "select count(*) from customer.use_approval where COMPANY_ID='" + cmpid + "' and FACILITY_ID='" + facility + "' and FAC_PART_NO='" + partno + "' and CATALOG_ID='" + selcatalog
					+ "' and APPLICATION in ('All','" + workarea + "','" + orderingap + "') and APPROVAL_STATUS='approved'";

					try {
						checkpartcount = DbHelpers.countQuery(db, checkquery);
					}
					catch (Exception e) {
						e.printStackTrace();
						addresult = false;
					}

					if (checkpartcount > 0) {
						String bquery = "select cabinet_seq.nextval BINID from dual";

						try {
							dbrs = db.doQuery(bquery);
							rs = dbrs.getResultSet();
							while (rs.next()) {
								binid = rs.getString("BINID");
							}

						}
						catch (Exception e) {
							e.printStackTrace();
							throw e;
						}
						finally {
							if (dbrs != null) {
								dbrs.close();
							}
						}

						String itemqry = "select  ITEM_ID, BUNDLE, CATALOG_ITEM_ID from customer.catalog_part_item_group where COMPANY_ID = '" + cmpid + "' and CAT_PART_NO='" + partno + "' and CATALOG_ID='" + selcatalog
						+ "' and PRIORITY = 1 and STATUS = 'A' ";
						String item_id = "";
						try {
							dbrs = db.doQuery(itemqry);
							rs = dbrs.getResultSet();
							while (rs.next()) {
								item_id = (rs.getString("ITEM_ID") == null ? "" : rs.getString("ITEM_ID"));
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						finally {
							if (dbrs != null) {
								dbrs.close();
							}
						}

						if (item_id.trim().length() > 0) {
							String addupdateqry = "Insert into cabinet_bin (bin_id,cabinet_id,company_id,bin_name,FACILITY_ID,APPLICATION) Select '" + binid + "',cabinet_id,company_id,'" + binname + "_" + binid
							+ "',FACILITY_ID,ORDERING_APPLICATION from cabinet where cabinet_id = " + selectedcab + "";

							String addcatpartquery = "Insert into cabinet_part_inventory (company_id, facility_id, application, catalog_id, cat_part_no, part_group_no, reorder_point, stocking_level,LEAD_TIME_DAYS) ";
							addcatpartquery += "Values ('" + cmpid + "', '" + facility + "', '" + orderingap + "', '" + selcatalog + "','" + partno + "', 1, " + reorderpt + "," + stocklvl + "," + leadtimedays + ")";

							String addcatpritemqry = "Insert into cabinet_part_item_bin (company_id,facility_id, application, catalog_id, cat_part_no, part_group_no, item_id, bin_id) ";
							addcatpritemqry += "Values ('" + cmpid + "', '" + facility + "', '" + orderingap + "', '" + selcatalog + "','" + partno + "', 1, " + item_id + "," + binid + ")";

							String addinitiacount = "insert into cabinet_inventory_count (BIN_ID, COUNT_DATETIME, RECEIPT_ID, COMPANY_ID, PERSONNEL_ID, COUNT_QUANTITY, DATE_PROCESSED) ";
							addinitiacount += "values (" + binid + ",sysdate,0,'" + cmpid + "'," + personnelid + ",0,sysdate)";
							try {
								db.doUpdate(addupdateqry);
								db.doUpdate(addcatpartquery);
								db.doUpdate(addcatpritemqry);
								db.doUpdate(addinitiacount);
							}
							catch (Exception ex) {
								addresult = false;
								errmsg = "<BR><B>An Error Occured <BR>" + ex.getMessage();
							}
						}
						else {
							errmsg += "<BR><B>There is no active item for this part you choose.</B>";
							addresult = false;
						}
					}
					else {
						errmsg += "<BR><B>The part entered is not Approved for the work area you choose.</B>";
						addresult = false;
					}
				}
				else {
					errmsg = "<BR><B>No Bin Name or Part Number Specified</B>";
					addresult = false;
				}

				if (addresult) {
					errmsg = "<BR><B>New Bin Created Successfully.</B>";
					binname = binname + "-" + binid;
				}
				if (User_Action.equalsIgnoreCase("AddandQuitbin") && !addresult) {
					User_Action = "";
				}

				buildnewbin(selHub, facility, workarea, selectedcab, selcatalog, binname, partno, reorderpt, stocklvl, leadtimedays, User_Action, errmsg);
			}
			else if (User_Action.equalsIgnoreCase("newBin")) {
				//PersonnelBean personnelBean = relabelsession.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) relabelsession.getAttribute( "personnelBean" );
				//System.out.println("User Group Id: CabinetMgmt  Facility Id: "+selHub+"  "+personnelBean.getPermissionBean().hasFacilityPermission("CabinetMgmt",selHub)+"",null);

				if (personnelBean.getPermissionBean().hasFacilityPermission("CabinetMgmt", selHub, null)) {
					buildnewbin(selHub, facility, workarea, selectedcab, selcatalog, binname, partno, reorderpt, stocklvl, leadtimedays, User_Action, "");
				}
				else {
					out.println(radian.web.HTMLHelpObj.printMessageinTable("You do not have access to create a bin at " + selHub + "."));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			out.close();
		}
	}

	//Build page - To build the gui page.
	private void buildDetails(Vector data, String facilityName) {
		//StringBuffer Msg=new StringBuffer();
		String checkednon = "";

		try {
			Hashtable summary = new Hashtable();
			summary = (Hashtable) data.elementAt(0);
			int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();

			//start table #3
			out.println("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			int i = 0; //used for detail lines
			for (int loop = 0; loop < total; loop++) {
				i++;
				boolean createHdr = false;

				if (loop % 10 == 0) {
					createHdr = true;
				}

				if (createHdr) {
					out.println("<tr align=\"center\">\n");
					if (this.getupdateStatus()) {
						out.println("<TH width=\"2%\"  height=\"38\">Print");
						if (loop == 0) {
							out.println("<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\">");
						}
						out.println("</TH>\n");
					}
					out.println("<TH width=\"5%\"  height=\"38\">Cabinet ID</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">Bin ID</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">Bin Name</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">Item ID</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">Part No</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">Part Description</TH>\n");
					out.println("</tr>\n");
				}

				Hashtable hD = new Hashtable();
				hD = (Hashtable) data.elementAt(i);

				String cab_id = hD.get("CABINET_ID1") == null ? "&nbsp;" : hD.get("CABINET_ID1").toString();
				String binid = hD.get("BIN_ID1") == null ? "&nbsp;" : hD.get("BIN_ID1").toString();
				String bindesc = hD.get("BIN_NAME") == null ? "&nbsp;" : hD.get("BIN_NAME").toString();
				String catpartno = hD.get("CAT_PART_NO") == null ? "&nbsp;" : hD.get("CAT_PART_NO").toString();
				String partdesc = hD.get("PART_DESCRIPTION") == null ? "&nbsp;" : hD.get("PART_DESCRIPTION").toString();
				String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
				String item_id = hD.get("ITEM_ID") == null ? "&nbsp;" : hD.get("ITEM_ID").toString();

				if (Line_Check.equalsIgnoreCase("yes")) {
					checkednon = "checked";
				}
				else {
					checkednon = "";
				}

				String Color = " ";
				if (i % 2 == 0) {
					Color = "CLASS=\"blue";
				}
				else {
					Color = "CLASS=\"white";
				}

				String chkbox_value = "no";
				if (checkednon.equalsIgnoreCase("checked")) {
					chkbox_value = "yes";
				}

				out.println("<tr align=\"center\">\n");
				if (this.getupdateStatus()) {
					out.println("<td height=\"25\" " + Color + "\" width=\"2%\" ALIGN=\"LEFT\"><INPUT TYPE=\"checkbox\" onClick= \"checkValues(name,this)\" value=\"" + (chkbox_value) + "\" " + checkednon + " NAME=\"row_chk" + i
							+ "\"></td>\n");
				}

				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + cab_id + "</td>\n");
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + binid + "</td>\n");
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + bindesc + "</td>\n");
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + item_id + "</td>\n");
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + catpartno + "</td>\n");
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + partdesc + "</td>\n");
				out.println("</tr>\n");
			}

			out.println("</table>\n");
			out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println("<tr>");
			out.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"" + i + "\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"facilityName\" ID=\"facilityName\" VALUE=\"" + facilityName + "\">\n");
			out.println("</TD></tr>");
			out.println("</table>\n");

			out.println("</form>\n");
			out.println("</body></html>\n");
		}
		catch (Exception e) {
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Logistics", intcmIsApplication));
		}

		return;
	}

	private Hashtable createwacabData(String selhub, String selecfac) {
		String query = "select distinct APPLICATION,APPLICATION_DESC,CABINET_ID,CABINET_NAME from hub_cabinet_view where PREFERRED_WAREHOUSE = '" + selhub + "' and  FACILITY_ID = '" + selecfac + "' order by APPLICATION";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable resultdata = new Hashtable();
		Hashtable finalresultdata = new Hashtable();
		Vector appID = new Vector();
		Vector appDesc = new Vector();

		try {
			dbrs = db.doQuery(query);
			rs = dbrs.getResultSet();

			String lastApp = "";

			while (rs.next()) {
				String tmpApp = rs.getString("APPLICATION");

				if (!tmpApp.equalsIgnoreCase(lastApp)) {
					appID.addElement(tmpApp);
					appDesc.addElement(rs.getString("APPLICATION_DESC"));
					Hashtable h = new Hashtable();

					Vector cabid = new Vector();
					Vector cabdesc = new Vector();
					cabid.addElement(rs.getString("CABINET_ID"));
					cabdesc.addElement(rs.getString("CABINET_NAME"));
					h.put("CABINET_ID", cabid);
					h.put("CABINET_DESC", cabdesc);
					resultdata.put(tmpApp, h);

				}
				else { //hub already exist
					Hashtable fh = (Hashtable) resultdata.get(tmpApp);
					Vector cabid = (Vector) fh.get("CABINET_ID");
					Vector cabdesc = (Vector) fh.get("CABINET_DESC");

					cabid.addElement(rs.getString("CABINET_ID"));
					cabdesc.addElement(rs.getString("CABINET_NAME"));

					fh.put("CABINET_ID", cabid);
					fh.put("CABINET_DESC", cabdesc);

					resultdata.put(tmpApp, fh);
				}
				lastApp = tmpApp;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		String catquery = "select CATALOG_ID from catalog_facility where facility_id = '" + selecfac + "'";
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = db.doQuery(catquery);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				//String faci= ( rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ) );
				String facn = (rs.getString("CATALOG_ID") == null ? "" : rs.getString("CATALOG_ID"));
				result.put(facn, facn);
				ResultV.addElement(result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		finalresultdata.put("ALL_VECTOR", resultdata);
		finalresultdata.put("WAC_IDS", appID);
		finalresultdata.put("WAC_DESCS", appDesc);
		finalresultdata.put("CATALOG_IDS", ResultV);

		return finalresultdata;
	}

	private StringBuffer createwacabjs(Hashtable finalresultdata) {
		StringBuffer Msgjs = new StringBuffer();
		Hashtable resultdata = (Hashtable) finalresultdata.get("ALL_VECTOR");
		Vector appID = (Vector) finalresultdata.get("WAC_IDS");
		Vector appDesc = (Vector) finalresultdata.get("WAC_DESCS");

		String tmp = "";
		String altWaId = "var altWaId = new Array(";
		String altWaDesc = "var altWaDesc = new Array(";
		String altCabId = "var altCabId = new Array();\n ";
		String altCabDesc = "var altCabDesc = new Array();\n ";
		int i = 0;

		//System.out.println("WAId SIze     "+appID.size()+"  altWaDesc   size       "+appDesc.size()+"");
		for (int ii = 0; ii < appID.size(); ii++) {
			String wacid = (String) appID.elementAt(ii);
			altWaId += "\"" + wacid + "\"" + ",";
			altWaDesc += "\"" + (String) appDesc.elementAt(ii) + "\"" + ",";

			Hashtable fh = (Hashtable) resultdata.get(wacid);
			//System.out.println(resultdata);
			Vector cabIDv = (Vector) fh.get("CABINET_ID");
			Vector cabDescV = (Vector) fh.get("CABINET_DESC");

			altCabId += "altCabId[\"" + wacid + "\"] = new Array(";
			altCabDesc += "altCabDesc[\"" + wacid + "\"] = new Array(";
			for (i = 0; i < cabIDv.size(); i++) {
				String facID = (String) cabIDv.elementAt(i);
				tmp += "\"" + facID + "\"" + ",";
				altCabId += "\"" + facID + "\"" + ",";
				altCabDesc += "\"" + (String) cabDescV.elementAt(i) + "\"" + ",";
			}
			//removing the last commas ','
			altCabId = altCabId.substring(0, altCabId.length() - 1) + ");\n";
			altCabDesc = altCabDesc.substring(0, altCabDesc.length() - 1) + ");\n";
		}

		if (altWaId.indexOf(",") > 1) {
			altWaId = altWaId.substring(0, altWaId.length() - 1) + ");\n";
		}

		if (altWaDesc.indexOf(",") > 1) {
			altWaDesc = altWaDesc.substring(0, altWaDesc.length() - 1) + ");\n";
		}

		Msgjs.append(altWaId + " " + altWaDesc + " " + altCabId + " " + altCabDesc);

		return Msgjs;
	}

	private void buildnewbin(String selectedHub, String selectedFac, String selectedwa, String selectedcab, String selcatalog, String bin, String partno, String reorderpt, String stocklvl, String leadtimedays, String useraction, String message) {
		//StringBuffer Msg=new StringBuffer();
		out.println(radian.web.HTMLHelpObj.printHTMLHeader("New Bin", "cabinetlabels.js", intcmIsApplication));

		Hashtable waccabdata = createwacabData(selectedHub, selectedFac);

		out.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
		out.println(createwacabjs(waccabdata));
		out.println("// -->\n </SCRIPT>\n");

		out.println("<BODY>\n");
		String Search_servlet = radian.web.tcmisResourceLoader.getcabinetbinlabelurl();

		out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
		out.println("</DIV>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("New Bin\n"));
		out.println(message);

		out.println("<FORM METHOD=\"POST\" NAME=\"cabinetlabels\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n");
		out.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");

		//Facility
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Facility:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"facilityName\" ID=\"facId\" value=\"" + selectedFac + "\" size=\"40\" readonly>\n");
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Work Area
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Work Area:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		int i = 0;
		Vector wacId = (Vector) waccabdata.get("WAC_IDS");
		Vector wacDesc = (Vector) waccabdata.get("WAC_DESCS");
		Vector catalogids = (Vector) waccabdata.get("CATALOG_IDS");

		Hashtable resultdata = (Hashtable) waccabdata.get("ALL_VECTOR");
		String preSelect = "";

		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"workAreaName\" ID=\"wacId\" value=\"" + selectedwa + "\" size=\"12\" readonly>\n");
		}
		else {
			out.println("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"wacId\" size=\"1\" OnChange=\"wacabchanged(document.cabinetlabels.workAreaName)\">\n");

			if (selectedwa.trim().length() == 0) {
				selectedwa = (String) wacId.firstElement(); //select the first facility if none were selected
			}

			for (i = 0; i < wacId.size(); i++) {
				preSelect = "";
				String wacid = (String) wacId.elementAt(i);
				String wacdesc = (String) wacDesc.elementAt(i);

				if (selectedwa.equalsIgnoreCase(wacid)) {
					preSelect = "selected";
					selectedwa = wacid;
				}

				out.println("<option " + preSelect + " value=\"" + wacid + "\"> " + wacdesc + "</option>\n");
			}
		}
		out.println("</SELECT>\n");
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Cabinet
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Cabinet:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"cabName\" ID=\"cabName\" value=\"" + selectedcab + "\" size=\"12\" readonly>\n");
		}
		else {
			out.println("<SELECT CLASS=\"HEADER\" NAME=\"cabName\" ID=\"cabName\" size=\"1\">\n");
			Hashtable h = (Hashtable) resultdata.get(selectedwa);
			Vector cabid = (Vector) h.get("CABINET_ID");
			Vector cabidDesc = (Vector) h.get("CABINET_DESC");

			for (i = 0; i < cabid.size(); i++) {
				preSelect = "";
				String cabId = (String) cabid.elementAt(i);
				String cabdesc = (String) cabidDesc.elementAt(i);

				if (selectedcab.equalsIgnoreCase(cabId)) {
					preSelect = "selected";
				}
				out.println("<option " + preSelect + " value=\"" + cabId + "\">" + cabdesc + "</option>\n");
			}
		}
		out.println("</SELECT>\n");
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Catalog
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Catalog:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"catname\" ID=\"catname\" value=\"" + selcatalog + "\" size=\"12\" readonly>\n");
		}
		else {
			out.println("<SELECT CLASS=\"HEADER\" NAME=\"catname\" ID=\"catname\" size=\"1\">\n");
			out.println(radian.web.HTMLHelpObj.getDropDown(catalogids, selcatalog));
			out.println("</SELECT>\n");
		}
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Bin Name
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Bin Name:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"binname\" ID=\"binname\" value=\"" + bin + "\" size=\"15\" readonly>\n");
		}
		else {
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"binname\" ID=\"binname\" value=\"" + bin + "\" size=\"15\" MAXLENGTH=\"30\">\n");
		}
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Part NO
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Part No:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"catpartno\" ID=\"catpartno\" value=\"" + partno + "\" size=\"20\" readonly>\n");
		}
		else {
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"catpartno\" ID=\"catpartno\" value=\"" + partno + "\" size=\"20\" MAXLENGTH=\"30\">\n");
		}
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Stocking Level
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Stocking Level:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"stklvl\" ID=\"stklvl\" value=\"" + stocklvl + "\" size=\"5\" readonly>\n");
		}
		else {
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"stklvl\" ID=\"stklvl\" value=\"" + stocklvl + "\" size=\"5\" MAXLENGTH=\"30\">\n");
		}
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Reorder Point
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Reorder Point:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"rorpoint\" ID=\"rorpoint\" value=\"" + reorderpt + "\" size=\"5\" readonly>\n");
		}
		else {
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"rorpoint\" ID=\"rorpoint\" value=\"" + reorderpt + "\" size=\"5\" MAXLENGTH=\"30\">\n");
		}
		out.println("</TD>\n");
		out.println("</TR>\n");

		//Lead Time in Days
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Lead Time in Days:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"leadtimedays\" ID=\"leadtimedays\" value=\"" + leadtimedays + "\" size=\"5\" readonly>\n");
		}
		else {
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"leadtimedays\" ID=\"leadtimedays\" value=\"" + leadtimedays + "\" size=\"5\" MAXLENGTH=\"30\">\n");
		}
		out.println("</TD>\n");
		out.println("</TR>\n");

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		if (useraction.equalsIgnoreCase("AddandQuitbin")) {
			//Cancel
			out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
			out.println("&nbsp;\n");
			out.println("</TD>\n");

			//Blank
			out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onclick= \"closeUserWin()\" NAME=\"closewin\">&nbsp;\n");
			out.println("</TD>\n");

			//Blank
			out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
			out.println("&nbsp;\n");
			out.println("</TD>\n");
		}
		else {
			//Add & Quit
			out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
			out.println("<INPUT TYPE=\"submit\" VALUE=\"Add & Quit\" onclick= \"return addquitbin(this)\" NAME=\"Addquitbut\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
			out.println("</TD>\n");

			//Add & Continue
			out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
			out.println("<INPUT TYPE=\"submit\" VALUE=\"Add & Continue\" onclick= \"return addcontbin(this)\" NAME=\"AddConbutton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
			out.println("</TD>\n");

			//Cancel
			out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Cancel\" onclick= \"closeUserWin()\" NAME=\"closewin\">&nbsp;\n");
			out.println("</TD>\n");
		}
		out.println("</TR>\n");

		out.println("</TABLE>\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"hubC\" VALUE=\"" + selectedHub + "\">\n");

		return;
	}

	//Build HTML Header
	private void buildHeader(String buildlabels, String paper_size, String hubname) {
		//StringBuffer Msg=new StringBuffer();
		out.println(radian.web.HTMLHelpObj.printHTMLHeader("Bin Labels", "cabinetlabels.js", intcmIsApplication));
		out.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		out.println("</HEAD>  \n");

		if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels)) {
			out.println("<BODY onLoad=\"dobinlabelspopup()\">\n");
		}
		else if ("GENERATE_ALL_LABELS".equalsIgnoreCase(buildlabels)) {
			out.println("<BODY onLoad=\"docaballPopup()\">\n");
		}
		else {
			out.println("<BODY>\n");
		}

		String Search_servlet = radian.web.tcmisResourceLoader.getcabinetbinlabelurl();

		out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
		out.println("</DIV>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("Cabinet Labels\n"));

		out.println("<FORM METHOD=\"POST\" NAME=\"cabinetlabels\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n");
		out.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		//Label size options
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" " + ("31".equalsIgnoreCase(paper_size) ? "checked" : "") + ">3''/1''&nbsp;\n");
		out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\" " + ("811".equalsIgnoreCase(paper_size) ? "checked" : "") + ">8.5''/11''\n");
		out.println("</TD>\n");
		//Generate Cabinet Labels
		out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Bin Labels\" onclick= \"generatebinlistlabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"yes\">&nbsp;\n");
		out.println("</TD>\n");
		out.println("</TR>\n");
		out.println("</TABLE>\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\"" + paper_size + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"hubC\" VALUE=\"" + hubname + "\">\n");

		return;
	}

	private Vector getResult(String cabSelected) throws Exception {
		Vector Data = new Vector();
		Hashtable DataH = new Hashtable();
		Hashtable summary = new Hashtable();

		int count = 0;
		summary.put("TOTAL_NUMBER", new Integer(count));
		Data.addElement(summary);
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
		try {
			String query = "select ITEM_ID,lpad(BIN_ID,8,'0') BIN_ID1,lpad(CABINET_ID,8,'0') CABINET_ID1,BIN_ID,CABINET_ID,BIN_NAME,NUM_CAT_PARTS,CAT_PART_NO,PART_DESCRIPTION,PART_SHORT_NAME from Cabinet_bin_label_view where CABINET_ID = "
				+ cabSelected + " order by BIN_ID";

			dbrs = db.doQuery(query);
			searchRs = dbrs.getResultSet();

			while (searchRs.next()) {

				int tmpnoofprts = searchRs.getInt("NUM_CAT_PARTS");
				String tmppartId = searchRs.getString("CAT_PART_NO") == null ? "" : searchRs.getString("CAT_PART_NO");
				String tmppartdesc = searchRs.getString("PART_DESCRIPTION") == null ? "" : searchRs.getString("PART_DESCRIPTION");

				DataH = new Hashtable();
				DataH.put("CABINET_ID", searchRs.getString("CABINET_ID") == null ? "" : searchRs.getString("CABINET_ID"));
				DataH.put("CABINET_ID1", searchRs.getString("CABINET_ID1") == null ? "" : searchRs.getString("CABINET_ID1"));
				DataH.put("BIN_ID", searchRs.getString("BIN_ID") == null ? "" : searchRs.getString("BIN_ID"));
				DataH.put("BIN_ID1", searchRs.getString("BIN_ID1") == null ? "" : searchRs.getString("BIN_ID1"));
				DataH.put("BIN_NAME", searchRs.getString("BIN_NAME") == null ? "" : searchRs.getString("BIN_NAME"));
				DataH.put("ITEM_ID", searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID"));
				DataH.put("PART_SHORT_NAME", searchRs.getString("PART_SHORT_NAME") == null ? "" : searchRs.getString("PART_SHORT_NAME"));

				if (tmpnoofprts > 1) {
					DataH.put("CAT_PART_NO", "Multiple");
					DataH.put("PART_DESCRIPTION", "Multiple");
				}
				else {
					DataH.put("CAT_PART_NO", tmppartId);
					DataH.put("PART_DESCRIPTION", tmppartdesc);
				}

				DataH.put("USERCHK", "");
				Data.addElement(DataH);
				count++;
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}

		}

		Hashtable recsum = new Hashtable();
		recsum.put("TOTAL_NUMBER", new Integer(count));
		Data.setElementAt(recsum, 0);

		return Data;
	}

	private Vector SynchServerData(HttpServletRequest request, Vector in_data) {
		Vector new_data = new Vector();
		Hashtable sum = (Hashtable) (in_data.elementAt(0));
		int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();
		new_data.addElement(sum);
		binIdstoLabel = new Vector();

		try {
			for (int i = 1; i < count + 1; i++) {
				Hashtable hD = new Hashtable();
				hD = (Hashtable) (in_data.elementAt(i));

				String check = "";
				check = BothHelpObjs.makeBlankFromNull(request.getParameter("row_chk" + i));
				hD.remove("USERCHK");
				hD.put("USERCHK", check);
				if (check.equalsIgnoreCase("yes")) {
					String binId = (hD.get("BIN_ID") == null ? " " : hD.get("BIN_ID").toString());
					binIdstoLabel.add(binId);
				}

				new_data.addElement(hD);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Inventory", intcmIsApplication));
		}

		return new_data;
	}

}
