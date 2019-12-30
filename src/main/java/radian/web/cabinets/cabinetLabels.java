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
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.StringHandler;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 10-27-03 - Added option to sort by and showing Workarea in the results
 * 04-18-04 - Giving an option to print all cabinet,bin and receipt labels for a cabinet
 * 04-26-04 - Giving Option to print cabinet and bin labels on a roll
 * 06-01-04 - Code Refactoring and Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 */

public class cabinetLabels {
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private PrintWriter out = null;
	private Vector cabinetIdstoLabel = null;
	private Vector cabinetlabelIds = null;
	//private Vector cabinetIdshown = null;
	private boolean intcmIsApplication = false;

	protected boolean allowupdate;

	public void setupdateStatus(boolean id) {
		this.allowupdate = id;
	}

	private boolean getupdateStatus() throws Exception {
		return this.allowupdate;
	}

	public cabinetLabels(ServerResourceBundle b, TcmISDBModel d) {
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
		String searchwhat = "";
		String containsorlike = "";
		String facility = "";
		String workarea = "";
		String selHub = "";

		searchtext = request.getParameter("searchtext");
		if (searchtext == null)
			searchtext = "";
		searchtext = searchtext.trim();

		User_Action = request.getParameter("UserAction");
		if (User_Action == null)
			User_Action = "New";
		User_Action = User_Action.trim();

		searchwhat = request.getParameter("searchwhat");
		if (searchwhat == null)
			searchwhat = "CAT_PART_NO";
		searchwhat = searchwhat.trim();

		containsorlike = request.getParameter("containsorlike");
		if (containsorlike == null)
			containsorlike = "Like";
		containsorlike = containsorlike.trim();

		facility = request.getParameter("facilityName");
		if (facility == null)
			facility = "";
		facility = facility.trim();

		workarea = request.getParameter("workAreaName");
		if (workarea == null)
			workarea = "All Work Areas";
		workarea = workarea.trim();

		selHub = request.getParameter("hubC");
		if (selHub == null)
			selHub = "";
		selHub = selHub.trim();

		boolean fromWebApp = "Y".equals(request.getParameter("fromWebApp"));

		/*String selcab =request.getParameter( "binids" );
		if ( selcab == null )
		  selcab="";
		selcab = selcab.trim();*/

		String paper_size = request.getParameter("paperSize");
		if (paper_size == null)
			paper_size = "31";

		String User_Sort = request.getParameter("SortBy");
		if (User_Sort == null)
			User_Sort = "APPLICATION";

		String[] cabsarray = { "All" };
		cabsarray = request.getParameterValues("binids");
		String cabsStringfromArray = "";
		Vector selectedcabs = new Vector();
		int cabadded = 0;
		if (cabsarray != null) {
			for (int loop = 0; loop < cabsarray.length; loop++) {
				if (cabadded > 0) {
					cabsStringfromArray += ",";
				}
				if ("All".equalsIgnoreCase(cabsarray[loop])) {

				}
				else {
					cabsStringfromArray += "'" + cabsarray[loop] + "'";
					//System.out.println(statusStringfromArray);
					cabadded++;
				}
				selectedcabs.add(cabsarray[loop]);
			}
		}
		if (cabsStringfromArray.length() == 0) {
			cabsStringfromArray = "'All'";
			selectedcabs.add("New");
		}

		//System.out.println( "User_Action   "+User_Action+"  User Search is " + searchwhat + "  containsorlike" + containsorlike + "  facility " + facility+ "  workarea   "+workarea );

		try {
			Hashtable initialData = null;
			Hashtable hub_list_set = new Hashtable();
			String initialDataLoaded = "";

			if (!fromWebApp) {
				try {
					initialDataLoaded = relabelsession.getAttribute("CABINET_LABEL_DATA_LOADED").toString();
				}
				catch (Exception getdoneFlag) {
				}

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
			}
			//PersonnelBean personnelBean = relabelsession.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) relabelsession.getAttribute( "personnelBean" );
			if (personnelBean.getPermissionBean().hasFacilityPermission("Cabinet Label", selHub, null)) {
				this.setupdateStatus(true);
			}
			else {
				this.setupdateStatus(false);
			}

			if (User_Action.equalsIgnoreCase("getselbins")) {
				out.println(radian.web.HTMLHelpObj.getcablist(selHub, facility, workarea, db, relabelsession));
				//relabelsession.setAttribute("CABIDS_SHOWN", cabinetIdshown );
			}
			else if (User_Action.equalsIgnoreCase("Search")) {
				Vector data = new Vector();
				Vector showncabs = (Vector) relabelsession.getAttribute("CABIDS_SHOWN");
				try {
					data = getResult(selHub, facility, workarea, cabsStringfromArray, User_Sort);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					out.println(radian.web.HTMLHelpObj.printHTMLError());
				}

				relabelsession.setAttribute("CAB_LABEL_DATA", data);

				Hashtable sum = (Hashtable) (data.elementAt(0));
				int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();

				buildHeader(selHub, facility, workarea, initialData, "", selectedcabs, showncabs, paper_size, User_Sort);

				if (0 == count) {
					out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
				}
				else {
					buildDetails(data, selHub);
				}

				//clean up
				data = null;
			}
			else if (User_Action.equalsIgnoreCase("generatecablabels") || User_Action.equalsIgnoreCase("generatecabbinlabels") || User_Action.equalsIgnoreCase("generatebaseline") || User_Action.equalsIgnoreCase("generateallcablabels")) {
				Vector retrn_data = (Vector) relabelsession.getAttribute("CAB_LABEL_DATA");
				Vector showncabs = (Vector) relabelsession.getAttribute("CABIDS_SHOWN");

				Vector synch_data = null;
				int count = 0;
				if (retrn_data != null && retrn_data.size() > 0) {
					synch_data = SynchServerData(request, retrn_data);
					relabelsession.setAttribute("CAB_LABEL_DATA", synch_data);

					Hashtable sum = (Hashtable) (synch_data.elementAt(0));
					count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();
				}
				else {
					cabinetIdstoLabel = new Vector();
					cabinetlabelIds = new Vector();
					Integer padLength = new Integer("8");

					String[] cabinets = request.getParameterValues("cabId");
					if (cabinets == null) {
						cabinets = getCabinetsForWorkArea(facility, workarea);
					}
					count = cabinets.length;
					for (int i = 0; i < count; i++) {
						cabinetIdstoLabel.add(cabinets[i]);
						cabinetlabelIds.add(StringHandler.padCharacter(cabinets[i], padLength, "0", "left"));
					}
				}
				relabelsession.setAttribute("LABEL_CAB_IDS", cabinetIdstoLabel);
				relabelsession.setAttribute("LABEL_CAB_LABEL_IDS", cabinetlabelIds);

				if (0 == count) {
					buildHeader(selHub, facility, workarea, initialData, "", selectedcabs, showncabs, paper_size, User_Sort);
					out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
				}
				else {
					if (User_Action.equalsIgnoreCase("generatecablabels")) {
						buildHeader(selHub, facility, workarea, initialData, "GENERATE_LABELS", selectedcabs, showncabs, paper_size, User_Sort);
					}
					else if (User_Action.equalsIgnoreCase("generatebaseline")) {
						buildHeader(selHub, facility, workarea, initialData, "GENERATE_BASE_LINE", selectedcabs, showncabs, paper_size, User_Sort);
					}
					else if (User_Action.equalsIgnoreCase("generateallcablabels")) {
						buildHeader(selHub, facility, workarea, initialData, "GENERATE_ALL_CAB_LABELS", selectedcabs, showncabs, paper_size, User_Sort);
					}
					else {
						buildHeader(selHub, facility, workarea, initialData, "GENERATE_ALL_LABELS", selectedcabs, showncabs, paper_size, User_Sort);
					}

					if (synch_data != null) {
						buildDetails(synch_data, selHub);
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
			else {
				Hashtable hub_sslist1 = (Hashtable) (hub_list_set.get("HUB_LIST"));
				Vector showncabs = new Vector();
				Hashtable result1 = new Hashtable();
				result1.put("All Cabinets", "All");
				showncabs.add(result1);
				relabelsession.setAttribute("CABIDS_SHOWN", showncabs);

				if (hub_sslist1.size() < 1) {
					buildHeader(selHub, facility, workarea, initialData, "", selectedcabs, showncabs, paper_size, User_Sort);
					out.println(radian.web.HTMLHelpObj.printHTMLNoFacilities());
					hub_list_set = null;
					out.close();
				}
				else {
					buildHeader(selHub, facility, workarea, initialData, "", selectedcabs, showncabs, paper_size, User_Sort);
					out.println(radian.web.HTMLHelpObj.printHTMLSelect());
					out.close();
					hub_list_set = null;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
			}
			catch (Exception e) {
				//ignore
			}
		}
	}

	/* public Hashtable getcabinetInitialData(Hashtable hublist) throws Exception
	 {

	   Hashtable hubsetdetails = ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
	   String allowedhubs = "";
	   for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
	   {
	       String branch_plant= ( String ) ( e.nextElement() );
	       String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
	       allowedhubs += "'"+hub_name+ "',";
	   }
	   allowedhubs = allowedhubs.substring(0,allowedhubs.length()-1);

	   String query="select * from hub_cabinet_view where PREFERRED_WAREHOUSE in ("+allowedhubs+") order by PREFERRED_WAREHOUSE,FACILITY_ID,APPLICATION";
	   DBResultSet dbrs=null;
	   ResultSet rs=null;
	   Hashtable resultdata =new Hashtable();
	   Vector hubID = new Vector();
	   Vector hubDesc = new Vector();

	   try
	   {
	     dbrs=db.doQuery( query );
	     rs=dbrs.getResultSet();

	     String lastHub="";

	     while ( rs.next() )
	     {
	       String tmpFacID=rs.getString( "facility_id" );
	       String tmpHub=rs.getString( "PREFERRED_WAREHOUSE" );

	       if ( !tmpHub.equalsIgnoreCase( lastHub ) )
	       {
	         //hub info
	         hubID.addElement(tmpHub);
	         hubDesc.addElement(tmpHub);

	         Hashtable fh=new Hashtable();
	         //facility data
	         Vector facID=new Vector();
	         Vector facDesc=new Vector();
	         facID.addElement( tmpFacID );
	         facDesc.addElement( rs.getString( "facility_name" ) );
	         Hashtable h=new Hashtable();
	         //application data
	         Vector application=new Vector();
	         Vector applicationDesc=new Vector();
	         application.addElement( rs.getString( "application" ) );
	         applicationDesc.addElement( rs.getString( "application_desc" ) );
	         h.put( "APPLICATION",application );
	         h.put( "APPLICATION_DESC",applicationDesc );
	         fh.put( tmpFacID,h );
	         //putting them all together
	         fh.put( "FACILITY_ID",facID );
	         fh.put( "FACILITY_DESC",facDesc );
	         //resultdata.put( "DATA",fh );
	         resultdata.put(tmpHub,fh);
	       }
	       else
	       { //hub already exist
	         Hashtable fh= ( Hashtable ) resultdata.get( tmpHub );
	         Vector facID= ( Vector ) fh.get( "FACILITY_ID" );
	         Vector facDesc= ( Vector ) fh.get( "FACILITY_DESC" );
	         if ( !facID.contains( tmpFacID ) )
	         {
	           facID.addElement( tmpFacID );
	           facDesc.addElement( rs.getString( "facility_name" ) );
	           Hashtable h=new Hashtable( 3 );
	           Vector application=new Vector();
	           Vector applicationDesc=new Vector();
	           application.addElement( rs.getString( "application" ) );
	           applicationDesc.addElement( rs.getString( "application_desc" ) );
	           h.put( "APPLICATION",application );
	           h.put( "APPLICATION_DESC",applicationDesc );
	           fh.put( tmpFacID,h );
	         }
	         else
	         {
	           Hashtable h= ( Hashtable ) fh.get( tmpFacID );
	           Vector application= ( Vector ) h.get( "APPLICATION" );
	           Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
	           if ( !application.contains( rs.getString( "application" ) ) )
	           {
	             application.addElement( rs.getString( "application" ) );
	             applicationDesc.addElement( rs.getString( "application_desc" ) );
	           }
	           h.put( "APPLICATION",application );
	           h.put( "APPLICATION_DESC",applicationDesc );
	           fh.put( tmpFacID,h );
	         }
	         fh.put( "FACILITY_ID",facID );
	         fh.put( "FACILITY_DESC",facDesc );
	         //resultdata.put( "DATA",fh );
	         resultdata.put(tmpHub,fh);
	       }
	       lastHub=tmpHub;
	     }
	   }
	   catch ( Exception e )
	   {
	     e.printStackTrace();
	   }
	   finally
	   {
	     if ( dbrs != null )
	     {
	       dbrs.close();
	     }
	   }
	   resultdata.put("HUB_ID",hubID);
	   resultdata.put("HUB_DESC",hubDesc);
	   return resultdata;
	 }

	   private String createComboBoxData( Hashtable initialData )
	   {
	     //System.out.print(initialData);

	     String tmp=new String( "" );
	     String tmpHub = "var hubID = new Array(";
	     String altFacID="var altFacID = new Array();\n ";
	     String altFacDesc="var altFacDesc = new Array();\n ";
	     String altWAID="var altWAID = new Array();\n ";
	     String altWADesc="var altWADesc = new Array();\n ";
	     int i=0;
	     Vector hubIDV = (Vector)initialData.get("HUB_ID");
	     for (int ii = 0 ; ii < hubIDV.size(); ii++)
	     {
	       String hubID = (String)hubIDV.elementAt(ii);
	       tmpHub += "\""+hubID+"\""+",";

	       Hashtable fh = (Hashtable)initialData.get(hubID);

	       Vector facIDV= ( Vector ) fh.get( "FACILITY_ID" );
	       Vector facDescV= ( Vector ) fh.get( "FACILITY_DESC" );
	       if ( facIDV.size() > 1 && !facIDV.contains( "All Facilities" ) )
	       {
	         facIDV.insertElementAt( "All Facilities",0 );
	         facDescV.insertElementAt( "All Facilities",0 );
	         Vector wAreaID=new Vector( 1 );
	         wAreaID.addElement( "All Work Areas" );
	         Vector wAreaDesc=new Vector( 1 );
	         wAreaDesc.addElement( "All Work Areas" );
	         Vector tmpAcS=new Vector( 1 );
	         tmpAcS.addElement( "All Accounting Systems" );
	         Hashtable tmpFacWA=new Hashtable( 3 );
	         tmpFacWA.put( "APPLICATION",wAreaID );
	         tmpFacWA.put( "APPLICATION_DESC",wAreaDesc );
	         tmpFacWA.put( "ACCOUNTING_SYSTEM",tmpAcS );
	         fh.put( "All Facilities",tmpFacWA );
	       }
	       altFacID += "altFacID[\""+hubID+"\"] = new Array(";
	       altFacDesc += "altFacDesc[\""+hubID+"\"] = new Array(";
	       for ( i=0; i < facIDV.size(); i++ )
	       {
	         String facID= ( String ) facIDV.elementAt( i );
	         tmp+="\"" + facID + "\"" + ",";
	         altFacID+="\"" + facID + "\"" + ",";
	         altFacDesc+="\"" + ( String ) facDescV.elementAt( i ) + "\"" + ",";
	         //build work area
	         Hashtable h= ( Hashtable ) fh.get( facID );
	         Vector application= ( Vector ) h.get( "APPLICATION" );
	         Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
	         if ( application.size() > 1 && !application.contains( "All Work Areas" ) )
	         {
	           application.insertElementAt( "All Work Areas",0 );
	           applicationDesc.insertElementAt( "All Work Areas",0 );
	         }
	         altWAID+="altWAID[\"" + facID + "\"] = new Array(";
	         altWADesc+="altWADesc[\"" + facID + "\"] = new Array(";
	         for ( int j=0; j < application.size(); j++ )
	         {
	           altWAID+="\"" + ( String ) application.elementAt( j ) + "\"" + ",";
	           altWADesc+="\"" + HelpObjs.findReplace( ( String ) application.elementAt( j ),"\"","'" ) + "\"" + ",";
	         }
	         altWAID=altWAID.substring( 0,altWAID.length() - 1 ) + ");\n";
	         altWADesc=altWADesc.substring( 0,altWADesc.length() - 1 ) + ");\n";
	       }
	       //removing the last commas ','
	       altFacID=altFacID.substring( 0,altFacID.length() - 1 ) + ");\n";
	       altFacDesc=altFacDesc.substring( 0,altFacDesc.length() - 1 ) + ");\n";
	     }

	     if (tmpHub.indexOf(",") > 1) {
	     tmpHub = tmpHub.substring(0,tmpHub.length()-1) +");\n";
	     }

	     return ( tmpHub+" " + altFacID + " " + altFacDesc + " " + altWAID + " " + altWADesc + "" );
	    }*/

	//Build page - To build the gui page.
	private void buildDetails(Vector data, String hubname) {
		//StringBuffer Msg=new StringBuffer();
		String checkednon = "";

		try {
			Hashtable summary = new Hashtable();
			summary = (Hashtable) data.elementAt(0);
			int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();
			//System.out.println( "Total Number : " + total );
			int vsize = data.size();
			//System.out.println( "Vector Size  : " + vsize );

			//start table #3
			out.println("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");

			String error_item;

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
					out.println("<TH width=\"5%\"  height=\"38\">Cabinet Desc</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">Work Area</TH>\n");
					out.println("</tr>\n");
				}

				Hashtable hD = new Hashtable();
				hD = (Hashtable) data.elementAt(i);

				String cab_id = hD.get("CABINET_ID") == null ? "&nbsp;" : hD.get("CABINET_ID").toString();
				String cab_id1 = hD.get("CABINET_ID1") == null ? "&nbsp;" : hD.get("CABINET_ID1").toString();
				String cab_desc = hD.get("CABINET_NAME") == null ? "&nbsp;" : hD.get("CABINET_NAME").toString();
				String workarea = hD.get("APPLICATION") == null ? "&nbsp;" : hD.get("APPLICATION").toString();
				String facilityId = hD.get("FACILITY_ID") == null ? "" : hD.get("FACILITY_ID").toString();
				String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());

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

				out.println("<td " + Color + "\" width=\"5%\" height=\"38\"><A HREF=\"javascript:showbindetails('" + cab_id1 + "','" + hubname + "', '" + facilityId +"')\">" + cab_id1 + "</A></td>\n");

				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + cab_desc + "</td>\n");
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + workarea + "</td>\n");
				out.println("</tr>\n");
			}

			out.println("</table>\n");
			out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println("<tr>");
			out.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"" + i + "\">\n");
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

	/*
	     public StringBuffer getcablist(  String hubSel,String facSelected,String waSelected, String cabselected )
	        {
	         StringBuffer Msgn = new StringBuffer();
	         StringBuffer Msgbody = new StringBuffer();
	         Hashtable result = null;
			 DBResultSet dbrs=null;
	  		 ResultSet searchRs=null;

	         Msgn.append(radian.web.HTMLHelpObj.printHTMLHeader("Get Bins","cabinetlabels.js"));
	         Msgn.append("</HEAD>\n");

	         Msgbody.append("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n");

	         StringBuffer Msgtemp = new StringBuffer();

	         //Build the Java Script Here and Finish the thing
	         Msgtemp.append("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
	         Msgtemp.append("<!--\n");

	         Msgtemp.append("function sendSupplierData()\n");
	         Msgtemp.append("{\n");

	         int count = 0;

	         String revisionfromquery = "";

	         Msgtemp.append("opener.removeAllOptionItem(opener.document.getElementById(\"binids\"));\n");
	         Msgtemp.append("opener.addOptionItem(opener.document.getElementById(\"binids\"),'All','All Cabinets');\n");
	         cabinetIdshown = new Vector();
	         result = new Hashtable();
	         result.put("All Cabinets","All");
	         cabinetIdshown.add(result);

	          try
	          {
	            boolean falgforand = false;
	            String binquery = "select * from hub_cabinet_view where ";

	            if ( facSelected.length() > 0 && ( !"All Facilities".equalsIgnoreCase( facSelected ) ) )
	            {
	              binquery+="FACILITY_ID='" + facSelected + "'";
	              falgforand=true;
	            }
				else if ( "All Facilities".equalsIgnoreCase(facSelected) )
				{
				  binquery+="PREFERRED_WAREHOUSE='" + hubSel + "'";
				  falgforand=true;
				}


	            if ( "All Work Areas".equalsIgnoreCase( waSelected ) )
	            {

	            }
	            else
	            {
	              if ( falgforand )
	                binquery+=" and APPLICATION = '" + waSelected + "'  ";
	              else
	                binquery+=" APPLICATION = '" + waSelected + "'  ";
	            }

	            binquery+=" order by APPLICATION";

	             dbrs = db.doQuery(binquery);
	             searchRs=dbrs.getResultSet();

	             while(searchRs.next())
	             {
	               result = new Hashtable();

	               String hazcode = searchRs.getString("CABINET_ID")==null?"":searchRs.getString("CABINET_ID");
	               String hazcodedesc = searchRs.getString("CABINET_NAME")==null?"":searchRs.getString("CABINET_NAME");
	               result.put(hazcodedesc,hazcode);
	               cabinetIdshown.add(result);

	               Msgtemp.append("opener.addOptionItem(opener.document.getElementById(\"binids\"),'"+hazcode+"','"+hazcodedesc+"');\n");
	              }
	           }
	           catch (Exception e)
	           {
	           e.printStackTrace();
	           }
	           finally
	           {
	             if (dbrs != null)  { dbrs.close(); }
	           }

	           DBResultSet compdbrs = null;
	           ResultSet comprs = null;

	         Msgtemp.append("window.close();\n");
	         Msgtemp.append(" }\n");

	         Msgtemp.append("// -->\n</SCRIPT>\n");
	         Msgn.append(Msgtemp);
	         //Msgn.append("<BODY><BR><BR>\n");
	         Msgn.append("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");

	         Msgbody.append("<CENTER><BR><BR>\n");
	         Msgbody.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
	         Msgbody.append("</FORM></BODY></HTML>\n");
	         Msgn.append(Msgbody);

	         return Msgn;
	        }*/

	//Build HTML Header
	private void buildHeader(String selectedHub, String selectedFac, String tmpwaSelect, Hashtable initialData, String buildlabels, Vector selectedcabs, Vector showncabs, String paper_size, String sortby) {
		//StringBuffer Msg=new StringBuffer();
		out.println(radian.web.HTMLHelpObj.printHTMLHeader("Cabinet Labels", "cabinetlabels.js", intcmIsApplication));
		out.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		out.println("</HEAD>  \n");

		if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels)) {
			out.println("<BODY onLoad=\"docabPopup()\">\n");
		}
		else if ("GENERATE_ALL_LABELS".equalsIgnoreCase(buildlabels)) {
			out.println("<BODY onLoad=\"docaballPopup()\">\n");
		}
		else if ("GENERATE_ALL_CAB_LABELS".equalsIgnoreCase(buildlabels)) {
			out.println("<BODY onLoad=\"docaballlabelsPopup()\">\n");
		}
		else if ("GENERATE_BASE_LINE".equalsIgnoreCase(buildlabels)) {
			out.println("<BODY onLoad=\"dobaselinePopup()\">\n");
		}
		else {
			out.println("<BODY>\n");
		}

		String Search_servlet = radian.web.tcmisResourceLoader.getcabinetlabelurl();

		out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
		out.println("</DIV>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		out.println(radian.web.HTMLHelpObj.PrintTitleBar("Cabinet Labels\n"));
		if (initialData == null) {
			out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
			return;
		}

		Vector hubID = (Vector) initialData.get("HUB_ID");
		if (hubID.size() == 0) {
			out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>There are no Cabinets in the hubs you have access to.<BR><BR>Contact tech support for more information."));
			return;
		}

		int i = 0;
		if (selectedHub.trim().length() == 0) {
			selectedHub = (String) hubID.firstElement(); //select the first facility if none were selected
		}

		out.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
		out.println(radian.web.HTMLHelpObj.createComboBoxData(initialData));
		out.println("// -->\n </SCRIPT>\n");

		out.println("<FORM METHOD=\"POST\" NAME=\"cabinetlabels\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n");
		out.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		//Hub
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		out.println("<B>Hub:</B>&nbsp;\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		out.println("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.cabinetlabels.hubC)\">\n");

		/*Vector hubID = (Vector)initialData.get("HUB_ID");
		int i = 0;
		if (selectedHub.trim().length() == 0 )
		{
		  selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
		}*/
		String preSelect = "";
		for (i = 0; i < hubID.size(); i++) {
			preSelect = "";
			if (selectedHub.equalsIgnoreCase((String) hubID.elementAt(i))) {
				preSelect = "selected";
				selectedHub = (String) hubID.elementAt(i);
			}
			out.println("<option " + preSelect + " value=\"" + (String) hubID.elementAt(i) + "\">" + (String) hubID.elementAt(i) + "</option>\n");
		}
		out.println("</SELECT>\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		out.println("&nbsp;\n");
		out.println("</TD>\n");

		//Search
		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"CENTER\">\n");
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		out.println("</TD>\n");

		//Generate Base Line
		out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Base Line\" onclick= \"generatebaseline(this)\" NAME=\"UpdateButton\">&nbsp;\n");
		out.println("</TD>\n</TR>\n");

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Facility:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		out.println("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.cabinetlabels.facilityName)\">\n");
		Hashtable fh = (Hashtable) initialData.get(selectedHub);
		//System.out.println(fh);
		Vector facID = (Vector) fh.get("FACILITY_ID");
		Vector facDesc = (Vector) fh.get("FACILITY_DESC");
		i = 0;
		if (selectedFac.trim().length() == 0) {
			selectedFac = (String) facID.firstElement(); //select the first facility if none were selected
		}
		preSelect = "";
		for (i = 0; i < facID.size(); i++) {
			preSelect = "";
			if (selectedFac.equalsIgnoreCase((String) facID.elementAt(i))) {
				preSelect = "selected";
				selectedFac = (String) facID.elementAt(i);
			}
			out.println("<option " + preSelect + " value=\"" + (String) facID.elementAt(i) + "\">" + (String) facDesc.elementAt(i) + "</option>\n");
		}
		out.println("</SELECT>\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		out.println("&nbsp;\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		//out.println( "&nbsp;\n" );
		out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" " + ("31".equalsIgnoreCase(paper_size) ? "checked" : "") + ">3''/1''&nbsp;\n");
		out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\" " + ("811".equalsIgnoreCase(paper_size) ? "checked" : "") + ">8.5''/11''\n");
		out.println("</TD>\n");

		//Generate Cabinet Labels
		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Cabinet Labels\" onclick= \"generatecablabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"yes\">&nbsp;\n");
		out.println("</TD>\n");
		out.println("</TR>\n");

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		//Work Area
		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
		out.println("<B>Work Area:</B>\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
		//out.println("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
		out.println("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n");
		Hashtable h = (Hashtable) fh.get(selectedFac);
		//System.out.println(h);
		Vector application = (Vector) h.get("APPLICATION");
		Vector applicationDesc = (Vector) h.get("APPLICATION_DESC");
		for (i = 0; i < application.size(); i++) {
			preSelect = "";
			if (tmpwaSelect.equalsIgnoreCase((String) application.elementAt(i))) {
				preSelect = "selected";
			}
			out.println("<option " + preSelect + " value=\"" + (String) application.elementAt(i) + "\">" + (String) applicationDesc.elementAt(i) + "</option>\n");
		}
		out.println("</SELECT>\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		out.println("<B>Sort By:</B>\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		out.println("<SELECT CLASS=\"HEADER\" NAME=\"SortBy\" size=\"1\">\n");
		if (sortby.equalsIgnoreCase("CABINET_ID")) {
			out.println("<option selected value=\"CABINET_ID\">Cabinet ID</option>\n");
			out.println("<option value=\"CABINET_NAME\">Cabinet Desc</option>\n");
			out.println("<option value=\"APPLICATION\">Work Area</option>\n");
		}
		else if (sortby.equalsIgnoreCase("CABINET_NAME")) {
			out.println("<option value=\"CABINET_ID\">Cabinet ID</option>\n");
			out.println("<option selected value=\"CABINET_NAME\">Cabinet Desc</option>\n");
			out.println("<option value=\"APPLICATION\">Work Area</option>\n");
		}
		else {
			out.println("<option value=\"CABINET_ID\">Cabinet ID</option>\n");
			out.println("<option value=\"CABINET_NAME\">Cabinet Desc</option>\n");
			out.println("<option selected value=\"APPLICATION\">Work Area</option>\n");
		}

		out.println("</SELECT>\n");
		out.println("</TD>\n");

		//Generate Cabinet All BIN Labels
		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Cabinet & Bin Labels\" onclick= \"generatecabbinlabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"yes\">&nbsp;\n");
		out.println("</TD>\n");
		out.println("</TR>\n");

		out.println("<TR>\n");
		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		out.println("&nbsp;\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"15%\" CLASS=\"announce\">\n");
		out.println("&nbsp;\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		out.println("&nbsp;\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		out.println("&nbsp;\n");
		out.println("</TD>\n");
		out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate All Labels\" onclick= \"generateallcabbinlabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
		out.println("</TD>\n");

		out.println("</TR>\n");

		out.println("</TABLE>\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\"" + paper_size + "\">\n");

		return;
	}

	private String[] getCabinetsForWorkArea(String facilityId, String workArea) throws Exception {
		Vector results = new Vector();
		String query = "select CABINET_ID from hub_cabinet_view where ";
		query += "FACILITY_ID='" + facilityId + "'";
		query += " and APPLICATION = '" + workArea + "'";

		ResultSet dbResults = db.doQuery(query).getResultSet();
		while (dbResults.next()) {
			results.add(dbResults.getString("CABINET_ID"));
		}

		return (String[]) results.toArray(new String[results.size()]);
	}

	private Vector getResult(String hubSel, String facSelected, String waSelected, String cabselected, String sortBy) throws Exception {

		Vector Data = new Vector();
		Hashtable DataH = new Hashtable();
		Hashtable summary = new Hashtable();
		String nodaysago = "60";
		boolean falgforand = false;
		DBResultSet dbrs = null;
		ResultSet searchRs = null;

		int count = 0;
		summary.put("TOTAL_NUMBER", new Integer(count));
		Data.addElement(summary);

		try {
			String query = "select lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,CABINET_NAME,APPLICATION, FACILITY_ID from hub_cabinet_view ";
			query += "where ";

			falgforand = false;

			if (facSelected.length() > 0 && (!"All Facilities".equalsIgnoreCase(facSelected))) {
				query += "FACILITY_ID='" + facSelected + "'";
				falgforand = true;
			}
			else if ("All Facilities".equalsIgnoreCase(facSelected)) {
				query += "PREFERRED_WAREHOUSE='" + hubSel + "'";
				falgforand = true;
			}

			if ("All Work Areas".equalsIgnoreCase(waSelected)) {

			}
			else {
				if (falgforand)
					query += " and APPLICATION = '" + waSelected + "'  ";
				else
					query += " APPLICATION = '" + waSelected + "'  ";
			}

			if ((cabselected.trim().length() == 0) || ("'All'".equalsIgnoreCase(cabselected))) {

			}
			else {
				if (falgforand)
					query += " and CABINET_ID in (" + cabselected + ")  ";
				else
					query += " CABINET_ID in (" + cabselected + ")  ";
			}

			query += " order by " + sortBy;

			dbrs = db.doQuery(query);
			searchRs = dbrs.getResultSet();

			/*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
			System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
			for(int i =1; i<=rsMeta1.getColumnCount(); i++)
			{
			System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

			//System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
			//System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
			//System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
			}*/

			while (searchRs.next()) {
				DataH = new Hashtable();
				//CABINET_ID
				DataH.put("CABINET_ID", searchRs.getString("CABINET_ID") == null ? "" : searchRs.getString("CABINET_ID"));
				//CABINET_ID1
				DataH.put("CABINET_ID1", searchRs.getString("CABINET_ID1") == null ? "" : searchRs.getString("CABINET_ID1"));
				//CABINET_NAME
				DataH.put("CABINET_NAME", searchRs.getString("CABINET_NAME") == null ? "" : searchRs.getString("CABINET_NAME"));
				//APPLICATION
				DataH.put("APPLICATION", searchRs.getString("APPLICATION") == null ? "" : searchRs.getString("APPLICATION"));
				DataH.put("FACILITY_ID", searchRs.getString("FACILITY_ID") == null ? "" : searchRs.getString("FACILITY_ID"));
				DataH.put("USERCHK", "");
				DataH.put("NO_OF_LABELS", "");
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
		Data.trimToSize();
		return Data;
	}

	private Vector SynchServerData(HttpServletRequest request, Vector in_data) {
		Vector new_data = new Vector();
		Hashtable sum = (Hashtable) (in_data.elementAt(0));
		int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();
		new_data.addElement(sum);
		cabinetIdstoLabel = new Vector();
		cabinetlabelIds = new Vector();

		try {
			for (int i = 1; i < count + 1; i++) {
				Hashtable hD = new Hashtable();
				hD = (Hashtable) (in_data.elementAt(i));

				String check = "";
				check = BothHelpObjs.makeBlankFromNull(request.getParameter("row_chk" + i));
				hD.remove("USERCHK");
				hD.put("USERCHK", check);
				if (check.equalsIgnoreCase("yes")) {
					String cabinetId = (hD.get("CABINET_ID") == null ? " " : hD.get("CABINET_ID").toString());
					String cabinetId1 = (hD.get("CABINET_ID1") == null ? " " : hD.get("CABINET_ID1").toString());
					cabinetIdstoLabel.add(cabinetId);
					cabinetlabelIds.add(cabinetId1);
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
