/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * Use the methods in this class to get the header and footer information.
 * You can pass a title variable to add to the Title tag in the HEAD section
 * of the HTML
 *
 * 04-28-03 : added getmultipleDropDown method
 * 04-30-03 : Chenged Login Screen options
 * 05-12-03 : Code cleanup refactoring
 * 07-01-03 : Bol Data Chage
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 * 08-20-03 - Getting the DOT information for parts
 * 09-21-03 - Allocation analysis showing drop ship
 * 09-23-03 - Only People in user_group BuyOrder are buyers. People in Purchasing can change PO's but can not create them from buy_page
 * 10-26-03 - Adding Net Weight to the BOL
 * 11-26-03 - Added new methods for the Quality Control process in receiving and logistics
 * 12-11-03 - Added new method to sort BIN location in descending order of date last used
 * 01-27-04 - Code Refactoring of two methods
 * 02-10-04 - Refactoring some code. Sorting Drop Downs on the webpages.
 * 02-20-04 - changed the Bin Query to Only show Active BINs
 * 04-26-04 - Changes to make seperable kits work. New procedure to get hub list from hub_pref table
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 * 10-04-04 - Not including Incoming as a stauts in vv_lot_status for receipts
 * 05-16-05 - Included IAI on the client pages link
 */
package radian.web;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.common.util.SqlHandler;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.InventoryGroupObjBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.HubRoomOvBean;
import com.tcmis.internal.hub.beans.VvHubRoomBean;
import com.tcmis.internal.hub.factory.HubRoomOvBeanFactory;

public abstract class HTMLHelpObj {
	private static org.apache.log4j.Logger	log				= org.apache.log4j.Logger.getLogger("");

	public static Locale getTcmISLocale(HttpServletRequest request) {
		Locale locale = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				PersonnelBean bean = (PersonnelBean) session.getAttribute("personnelBean");
				if (bean != null) {
					locale = bean.getLocale();
				}
			}
			catch (Exception e) {
				//we'll eat the error but log it
				//log.fatal("Error getting locale from personnel bean.", e);
			}
		}
		if (locale == null) {
			//no locale set in personnel bean, try to get it from browser
			locale = request.getLocale();
			if (locale == null) {
				//no locale in personnel bean or request, set to default
				locale = Locale.getDefault();
			}
		}
		return locale;
	}

	public static String getTcmISLocaleString(HttpServletRequest request) {
		return getTcmISLocale(request).toString();
	}

	public static void redirectPage(PrintWriter out, String reDirectTo, int beforeYear, int beforeMonth, int beforeDay) {
		Calendar calToday = Calendar.getInstance();
		Calendar cal30Days = Calendar.getInstance();
		cal30Days.set(beforeYear, beforeMonth, beforeDay);

		String httphost = radian.web.tcmisResourceLoader.gethosturl();
		out.println("<html><head>\n");
		out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
		if (calToday.before(cal30Days)) {
			out.println("<meta http-equiv=\"Refresh\" content=\"5; url=" + httphost + reDirectTo + "\">\n");
		}
		out.println("<link rel=\"SHORTCUT ICON\" href=\"" + httphost + "images/buttons/tcmIS.ico\"></link>\n");
		out.println("<meta http-equiv=\"Expires\" content=\"-1\">\n");
		out.println("<title>tcmIS Redirect Page</title>\n");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/global.css\"></link>\n");
		out.println("<script src=\"/clientscripts/redirect.js\" language=\"JavaScript\"></script>\n");
		out.println("</head>  \n");
		out.println("<body bgcolor=\"#FFFFFF\" text=\"#000000\">  \n");
		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("tcmIS Redirect Page"));
		out.println("<table width=\"800\" border=\"0\" class=\"moveup\">\n");
		out.println("<tr>");
		out.println("<td>\n");
		if (calToday.before(cal30Days)) {
			out.println("This page has moved, you will be redirected shortly.<br>\n");
			out.println("<br>You can also get to the new page by clicking on the link below.<br>\n");
			out.println("<br>Update your Bookmark/Favorites to the new address after you are redirected (but before you login).<br>\n");
		}
		else {
			out.println("This page has moved.<br>\n");
			out.println("<br>You can get to the new page by clicking on the link below.<br>\n");
			out.println("<br>Update your Bookmark/Favorites to the new address after clicking on the link below (but before you login).<br>\n");
		}
		out.println("<p><a href=\"" + httphost + reDirectTo + "\">" + httphost + reDirectTo + "</a></p>\n");
		out.println("</td>\n");
		out.println("</tr></table>\n");
		out.println("</body></html>    \n");
		out.close();
	}

	public static StringBuffer printError(String Title, boolean intcmIsApplication) {
		StringBuffer MsgPE = new StringBuffer();
		MsgPE.append(printHTMLHeader(Title, intcmIsApplication));
		MsgPE.append(printHTMLError());

		return MsgPE;
	}

	public static StringBuffer printError(String TitleKey, boolean intcmIsApplication, ResourceLibrary res) {
		StringBuffer MsgPE = new StringBuffer();
		MsgPE.append(printHTMLHeader(TitleKey, intcmIsApplication, res));
		MsgPE.append(printHTMLError(res));

		return MsgPE;
	}

	public static Date getDateValueFromString(String CountDateString) throws Exception {
		Date countdate1 = null;
		SimpleDateFormat countDateT = new SimpleDateFormat("MM/dd/yyyy");

		try {
			countdate1 = countDateT.parse(CountDateString);

		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return countdate1;
	}

	public static Timestamp getDateFromString(String CountDateString) throws Exception {
		Date countdate1 = null;
		Timestamp CountDateTimeStamp = null;
		SimpleDateFormat countDateT = new SimpleDateFormat("MM/dd/yyyy");

		try {
			countdate1 = countDateT.parse(CountDateString);
			CountDateTimeStamp = new Timestamp(countdate1.getTime());

		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return CountDateTimeStamp;
	}

	public static Timestamp getDateFromString(String dateformat, String CountDateString) throws Exception {
		Date countdate1 = null;
		Timestamp CountDateTimeStamp = null;
		SimpleDateFormat countDateT = null;
		if (dateformat.length() == 0) {
			if (CountDateString.length() == 10) {
				if (CountDateString.length() == 10 && CountDateString.substring(2, 3).equalsIgnoreCase("/")) {
					countDateT = new SimpleDateFormat("MM/dd/yyyy");
				}
				else if (CountDateString.length() == 10 && CountDateString.substring(2, 3).equalsIgnoreCase("-")) {
					countDateT = new SimpleDateFormat("MM-dd-yyyy");
				}
				else if (CountDateString.length() == 10 && CountDateString.substring(2, 3).equalsIgnoreCase(":")) {
					countDateT = new SimpleDateFormat("MM:dd:yyyy");
				}
				else if (CountDateString.length() == 10 && CountDateString.substring(2, 3).equalsIgnoreCase(" ")) {
					countDateT = new SimpleDateFormat("MM dd yyyy");
				}
				else if (CountDateString.length() == 10 && CountDateString.substring(2, 3).equalsIgnoreCase(".")) {
					countDateT = new SimpleDateFormat("MM.dd.yyyy");
				}
			}
			else if (CountDateString.length() == 8) {
				countDateT = new SimpleDateFormat("MMddyyyy");
			}
			else if (CountDateString.length() == 6) {
				countDateT = new SimpleDateFormat("MMddyy");
			}
		}
		else {
			countDateT = new SimpleDateFormat(dateformat);
		}

		try {
			countdate1 = countDateT.parse(CountDateString);
			CountDateTimeStamp = new Timestamp(countdate1.getTime());

		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return CountDateTimeStamp;
	}

	public static StringBuffer printHTMLSelect() {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append("<BR><BR>\n");
		MsgER.append(printMessageinTable(" Please Select Options Provided and Proceed."));
		MsgER.append("</form>\n");
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLSelect(ResourceLibrary res) {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append("<BR><BR>\n");
		MsgER.append(printMessageinTable(res.getString("label.proceed")));
		MsgER.append("</form>\n");
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printJavaScriptError() {
		return printMessageinTable("Java Script System Error Please Resubmit your last action");
	}

	public static StringBuffer printJavaScriptError(ResourceLibrary res) {
		return printMessageinTable(res.getString("msg.javascript"));
	}

	public static StringBuffer printHTMLNoData(String Message) {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable(Message));
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printMessageinTable(String Message) {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append("<TABLE BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\"><TR>\n");
		MsgER.append("<TD width=\"10%\" CLASS=\"announce\">\n");
		MsgER.append("</TD>\n");
		MsgER.append("<TD width=\"30%\" CLASS=\"announce\">" + Message + "<BR>\n");
		MsgER.append("</TD>\n");
		MsgER.append("</TR></TABLE>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLNoFacilities() {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable("You don't have access to Any Hub."));
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLNoFacilities(ResourceLibrary res) {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable(res.getString("msg.noaccesstohub")));
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLPartialSuccess(Vector errMesages) {
		String errmsgto = "<B>An Error Occurred.</B><BR>Some of Your Selections were not Updated<BR>Please Check Data and try Again for Unsuccessful Transactions Shown Below.<BR><BR>";
		for (int m = 0; m < errMesages.size(); m++) {
			errmsgto += "" + errMesages.elementAt(m).toString() + "<BR>";
		}

		return printMessageinTable(errmsgto);
	}

	public static StringBuffer printHTMLPartialSuccess(Vector errMesages, ResourceLibrary res) {
		String errmsgto = res.getString("msg.html.partialsuccess");
		for (int m = 0; m < errMesages.size(); m++) {
			errmsgto += "" + errMesages.elementAt(m).toString() + "<BR>";
		}

		return printMessageinTable(errmsgto);
	}

	public static StringBuffer printHTMLCompletSuccess() {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable("All Your Selections Were Successfully Updated."));
		MsgER.append("</form>\n");
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLHeader(String Title, boolean intcmIsApplication) {
		return printHTMLHeader(Title, "", intcmIsApplication);
	}

	public static StringBuffer printHTMLHeader(String TitleKey, boolean intcmIsApplication, ResourceLibrary res) {
		return printHTMLHeader(TitleKey, "", intcmIsApplication, res);
	}

	public static StringBuffer printHTMLHeader(String Title, String jsname, boolean intcmIsApplication) {
		StringBuffer MsgHH = new StringBuffer();
		MsgHH.append("<HTML><HEAD>\n");
		MsgHH.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE8\" />\n");
		MsgHH.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
		String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
		MsgHH.append("<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n");
		MsgHH.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		MsgHH.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		MsgHH.append("<TITLE>" + Title + "</TITLE>\n");

		if (Title.startsWith("Purc")) {
			MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/poglobal.css\"></LINK>\n");
		}
		else {
			MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		}

		if (intcmIsApplication) {
			MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/css/hidetopnavigation.css\"></LINK>\n");
		}

		if (jsname.length() > 1) {
			MsgHH.append("<SCRIPT SRC=\"/clientscripts/" + jsname + "\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		}

		/*This handels which key press events are disabeled on this page*/
		MsgHH.append("<script src=\"/js/common/disableKeys.js\" language=\"JavaScript\"></script>\n");
		/*This handels the menu style and what happens to the right click on the whole page */
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/milonic_src.js\"></script>\n");
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/mmenudom.js\"></script>\n");
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/mainmenudata.js\"></script>\n");
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/contextmenu.js\"></script>\n");

		return MsgHH;
	}

	public static StringBuffer printHTMLHeader(String Title, String jsname, boolean intcmIsApplication, ResourceLibrary res) {
		StringBuffer MsgHH = new StringBuffer();
		MsgHH.append("<HTML><HEAD>\n");
		MsgHH.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
		String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
		MsgHH.append("<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n");
		MsgHH.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		MsgHH.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		MsgHH.append("<TITLE>" + res.getString(Title) + "</TITLE>\n");

		if (Title.startsWith("Purc") || Title.startsWith("purc")) {
			MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/poglobal.css\"></LINK>\n");
		}
		else {
			MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		}

		if (intcmIsApplication) {
			MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/css/hidetopnavigation.css\"></LINK>\n");
		}

		if (jsname.length() > 1) {
			MsgHH.append("<SCRIPT SRC=\"/clientscripts/" + jsname + "\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		}

		/*This handels which key press events are disabeled on this page*/
		MsgHH.append("<script src=\"/js/common/disableKeys.js\" language=\"JavaScript\"></script>\n");
		/*This handels the menu style and what happens to the right click on the whole page */
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/milonic_src.js\"></script>\n");
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/mmenudom.js\"></script>\n");
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/mainmenudata.js\"></script>\n");
		MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/contextmenu.js\"></script>\n");

		return MsgHH;
	}

	public static StringBuffer printHTMLNoneToUpdate() {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable("None to Update.<BR>Please Select Ok for Each Line you Want to Update."));
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLError() {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable("An Error Occurred.<BR>Please Check Data and try Again."));
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer printHTMLError(ResourceLibrary res) {
		StringBuffer MsgER = new StringBuffer();
		MsgER.append(printMessageinTable(res.getString("msg.tcmiserror")));
		MsgER.append("</BODY></HTML>\n");

		return MsgER;
	}

	public static StringBuffer PrintTitleBar(String Message, String whichhome) {
		StringBuffer Msg = new StringBuffer();
		/*Top Navigation- we will not display this section after sometime, when we move to the new menu structure*/
		Msg.append("<div class=\"topNavigation\" id=\"topNavigation\">\n");
		Msg.append("<TABLE BORDER=0 WIDTH=100% CLASS=\"moveupmore\">\n");
		Msg.append("<TR VALIGN=\"TOP\">\n");
		Msg.append("<TD WIDTH=\"200\">\n");
		Msg.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
		Msg.append("</TD>\n");
		Msg.append("<TD ALIGN=\"right\">\n");
		Msg.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
		Msg.append("</TD>\n");
		Msg.append("</TR>\n");
		Msg.append("</Table>\n");
		Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

		Msg.append("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">\n");
		Msg.append(Message);
		Msg.append("</TD>\n");
		Msg.append("<TD WIDTH=\"30%\" ALIGN=\"RIGHT\" HEIGHT=\"22\" CLASS=\"headingr\">\n");
		if ("pohome".equalsIgnoreCase(whichhome)) {
			Msg.append("<A HREF=\"/tcmIS/supply/home.do\" STYLE=\"color:#FFFFFF\">Home</A>\n");
		}
		else if ("aphome".equalsIgnoreCase(whichhome)) {
			Msg.append("<A HREF=\"/tcmIS/accountspayable/home.do\" STYLE=\"color:#FFFFFF\">Home</A>\n");
		}
		else if ("cataloghome".equalsIgnoreCase(whichhome)) {
			Msg.append("<A HREF=\"/tcmIS/catalog/home.do\" STYLE=\"color:#FFFFFF\">Home</A>\n");
		}
		else if ("supplierhome".equalsIgnoreCase(whichhome)) {
			Msg.append("<A HREF=\"/tcmIS/supplier/Home\" STYLE=\"color:#FFFFFF\">Home</A>\n");
		}
		else if ("nohome".equalsIgnoreCase(whichhome)) {

		}
		else {
			Msg.append("<A HREF=\"/tcmIS/hub/home.do\" STYLE=\"color:#FFFFFF\">Home</A>\n");
		}
		Msg.append("</TD>\n");

		Msg.append("</TR>\n");
		Msg.append("</TABLE>\n");
		Msg.append("</div>");
		return Msg;

	}

	public static StringBuffer PrintTitleBar(String Message) {
		return PrintTitleBar(Message, "");
	}

	public static StringBuffer PrintTitleBarNolink(String Message) {
		return PrintTitleBar(Message, "nohome");
	}

	public static StringBuffer printApTitleBar(String Message) {
		return PrintTitleBar(Message, "aphome");
	}

	public static StringBuffer printPurchaseOrderTitleBar(String Message) {
		return PrintTitleBar(Message, "pohome");
	}

	public static StringBuffer PrintCatalogTitleBar(String Message) {
		return PrintTitleBar(Message, "cataloghome");
	}

	public static StringBuffer printSupplierTitleBar(String Message) {
		return PrintTitleBar(Message, "supplierhome");
	}

	public static StringBuffer printlinkstoClientHome() {
		StringBuffer MsgSB = new StringBuffer();

		MsgSB.append("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
		MsgSB.append("<TR><TH WIDTH=\"550\" COLSPAN=\"4\" ALIGN=\"CENTER\" height=\"30\">Customer Home Pages</TH></TR>\n");
		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/drs/Register\" STYLE=\"color:#e86915\" TARGET=\"drs\">DRS</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/ray/Register\" STYLE=\"color:#e86915\" TARGET=\"ray\">Raytheon</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/seagate/Register\" STYLE=\"color:#e86915\" TARGET=\"seagate\">Seagate</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/swa/Register\" STYLE=\"color:#e86915\" TARGET=\"swa\">Southwest</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/bae/Register\" STYLE=\"color:#e86915\" TARGET=\"bae\">BAE</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/utc/Register\" STYLE=\"color:#e86915\" TARGET=\"utc\">UTC</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/lmco/Register\" STYLE=\"color:#e86915\" TARGET=\"lmco\">LMCO</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/sd/Register\" STYLE=\"color:#e86915\" TARGET=\"sd\">SD</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/miller/Register\" STYLE=\"color:#e86915\" TARGET=\"miller\">Miller</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/boeing/Register\" STYLE=\"color:#e86915\" TARGET=\"boeing\">Boeing</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/cal/Register\" STYLE=\"color:#e86915\" TARGET=\"cal\">CAL</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/ael/Register\" STYLE=\"color:#e86915\" TARGET=\"aec\">Aero Echo</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/gm/Register\" STYLE=\"color:#e86915\" TARGET=\"gm\">GM</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/dana/Register\" STYLE=\"color:#e86915\" TARGET=\"dana\">Dana</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/doe/Register\" STYLE=\"color:#e86915\" TARGET=\"doe\">DOE</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/fec/Register\" STYLE=\"color:#e86915\" TARGET=\"fec\">FEC</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/iai/Register\" STYLE=\"color:#e86915\" TARGET=\"drs\">IAI</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/am/Register\" STYLE=\"color:#e86915\" TARGET=\"am\">AM</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/gema/Register\" STYLE=\"color:#e86915\" TARGET=\"gema\">GEMA</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/l3/Register\" STYLE=\"color:#e86915\" TARGET=\"l3\">L3</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/pge/Register\" STYLE=\"color:#e86915\" TARGET=\"pge\">PG & E</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/dcx/Register\" STYLE=\"color:#e86915\" TARGET=\"dcx\">DCX</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/gd/Register\" STYLE=\"color:#e86915\" TARGET=\"gd\">GD</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/algat/Register\" STYLE=\"color:#e86915\" TARGET=\"algat\">ALGAT</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/baz/Register\" STYLE=\"color:#e86915\" TARGET=\"baz\">BAZ</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/cmm/Register\" STYLE=\"color:#e86915\" TARGET=\"cmm\">CMM</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/fedco/Register\" STYLE=\"color:#e86915\" TARGET=\"fedco\">FEDCO</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/imco/Register\" STYLE=\"color:#e86915\" TARGET=\"imco\">IMCO</A></TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("<TR>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\"  height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/kanfit/Register\" STYLE=\"color:#e86915\" TARGET=\"kanfit\">Kanfit</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("<A HREF=\"/tcmIS/verasun/Register\" STYLE=\"color:#e86915\" TARGET=\"verasun\">Verasun</A></TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("&nbsp;</TD>\n");
		MsgSB.append("<TD ALIGN=\"CENTER\" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh1'\" onMouseout=\"className='menu1'\" width=\"25%\" height=\"30\">\n");
		MsgSB.append("&nbsp;</TD>\n");
		MsgSB.append("</TR>\n");

		MsgSB.append("</TABLE>\n");

		return MsgSB;
	}

	public static StringBuffer printLoginScreen(String errmsg1, String ScreenName, String whichhome, String compaId) {
		StringBuffer body = new StringBuffer("");
		body.append("<HTML><HEAD>\n");
		body.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
		String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
		body.append("<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n");
		body.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		body.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		body.append("<TITLE>Login</TITLE>\n");
		body.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		/*This handels which key press events are disabeled on this page*/
		body.append("<script src=\"/js/common/disableKeys.js\" language=\"JavaScript\"></script>\n");
		/*This handels the menu style and what happens to the right click on the whole page */
		body.append("<script type=\"text/javascript\" src=\"/js/menu/milonic_src.js\"></script>\n");
		body.append("<script type=\"text/javascript\" src=\"/js/menu/mmenudom.js\"></script>\n");
		body.append("<script type=\"text/javascript\" src=\"/js/menu/mainmenudata.js\"></script>\n");
		body.append("<script type=\"text/javascript\" src=\"/js/menu/contextmenu.js\"></script>\n");
		body.append("<script type=\"text/javascript\" src=\"/js/common/sessiontimeout.js\"></script>\n");
		body.append("</HEAD>  \n");
		if (!"nologin".equalsIgnoreCase(compaId)) {
			body.append("<BODY BGCOLOR=\"#FFFFFF\" onload=\"sessionLogout()\" TEXT=\"#000000\">  \n");
		}
		else {
			body.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
		}
		if (ScreenName.equalsIgnoreCase("tcmIS Main Menu")) {
			whichhome = "nohome";
		}

		body.append(radian.web.HTMLHelpObj.PrintTitleBarNolink(ScreenName));
		body.append("<form method=\"POST\" name=\"form1\">\n");
		body.append("<TABLE WIDTH=\"800\" BORDER=\"0\" CLASS=\"moveup\">\n");
		body.append("<TR VALIGN=\"TOP\"><TD BGCOLOR=\"#E6E8FA\" HEIGHT=\"400\">\n");
		body.append("<TABLE WIDTH=\"150\" BORDER=\"0\" CLASS=\"moveup\">\n");

		if (!"nologin".equalsIgnoreCase(compaId)) {
			body.append("<tr>\n");
			body.append("<td width=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\" ><B>Logon Id : </B></td>\n");
			body.append("<td width=\"95%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"text\" name=\"loginId\" value=\"\" size=\"10\"></td>\n");
			body.append("</tr>\n");

			if (!"seagate".equalsIgnoreCase(compaId)) {
				body.append("<tr>\n");
				body.append("<td width=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\" ><B>Password : </B></td>\n");
				body.append("<td width=\"95%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"password\" name=\"passwd\" value=\"\" size=\"10\"></td>\n");
				body.append("</tr>\n");
			}

			body.append("<tr>\n");
			body.append("<td width=\"5%\" align=\"right\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Login\" name=\"Login\"></td>\n");
			body.append("<td width=\"95%\" align=\"left\"><input type=\"reset\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Reset\" name=\"Reset\"></td>\n");
			body.append("</tr>\n");
		}

		body.append("<INPUT TYPE=\"hidden\" NAME=\"flashversion\" VALUE=\"\">\n");
		body.append("<INPUT TYPE=\"hidden\" NAME=\"flashInstalled\" VALUE=\"No\">\n");

		body.append("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-init.js\"></SCRIPT>\n");
		body.append("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-writevb.js\"></SCRIPT>\n");
		body.append("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-main.js\"></SCRIPT>\n");

		body.append("<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD COLSPAN=\"2\" CLASS=\"bluel\"><FONT SIZE=\"2\">" + errmsg1 + "</FONT></TD></TR></TABLE></TD>");

		body.append("<TD WIDTH=\"650\" HEIGHT=\"400\" VALIGN=\"TOP\">\n");
		body.append("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
		body.append("<TR><TD WIDTH=\"550\" COLSPAN=\"3\">\n");
		/*body.append("<BR><FONT FACE=\"Arial\" SIZE=\"3\"><B><U>Data Center Move</U></B><BR><BR>As most of you know, tcmIS is operated from a professional hosting facility.  This facility provides us with a superb infrastructure to operate -redundant power, high level physical security, redundant switches and routers, and expandable bandwidth connected to 10 Internet providers.<BR><BR>");
		body.append("Our hosting company is moving its operation to another location with an even better infrastructure.  Unfortunately, this will mean that our servers, routers and firewalls will need to be transferred to this new location.  <B>This move is planned for this Friday evening, December 3, 2004.  We plan to turn off all of our systems at 7:00 PM CST Friday Dec 3, 2004 and plan on being back up at the new location by 7:00 AM Saturday CST Dec 4, 2004.</B><BR><BR>");
		body.append("We hope this does not cause any inconvenience.</FONT><BR><BR>");*/
		body.append("<TD>\n");
		body.append("</TR>\n");
		body.append("</TABLE>\n");
		body.append("</TD>\n");

		body.append("</TR></TABLE>\n");
		body.append("</form></body></html>");
		return body;
	}

	public static StringBuffer printLoginScreen(String errmsg1, String ScreenName) {
		return printLoginScreen(errmsg1, ScreenName, "", "");
	}

	public static StringBuffer printpoLoginScreen(String errmsg1, String ScreenName) {
		return printLoginScreen(errmsg1, ScreenName, "pohome", "");
	}

	public static StringBuffer printSeagateLoginScreen(String errmsg1, String ScreenName) {
		return printLoginScreen(errmsg1, ScreenName, "nohome", "seagate");
	}

	public static StringBuffer printapLoginScreen(String errmsg1, String ScreenName) {
		return printLoginScreen(errmsg1, ScreenName, "aphome", "");
	}

	public static StringBuffer printCatalogLoginScreen(String errmsg1, String ScreenName) {
		return printLoginScreen(errmsg1, ScreenName, "cataloghome", "");
	}

	public static StringBuffer printLoginScreenOptions(String errmsg1, String ScreenName, HttpSession session) {
		return printLoginScreen(errmsg1, ScreenName, "", "nologin");
	}

	public static StringBuffer printpoLoginScreenOptions(String errmsg1, String ScreenName, HttpSession session) {
		return printLoginScreen(errmsg1, ScreenName, "pohome", "nologin");
	}

	public static String getSysDate(TcmISDBModel dbObj) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String temp2 = "";
		try {
			String querys = "select to_char(SYSDATE,'mm/dd/yyyy') SYS_DATE from dual ";

			dbrs = dbObj.doQuery(querys);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				temp2 = rs.getString("SYS_DATE");
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
		return temp2;
	}

	public static int numOfClients(TcmISDBModel dbObj, String HubName) throws Exception {
		int test_number = DbHelpers.countQuery(dbObj, "select count(company_id) from(select distinct company_id, preferred_warehouse from customer.facility where company_id <>'Radian' order by 2) where preferred_warehouse='" + HubName + "'");
		return test_number;
	}

	public static Vector createallowedusrgrps(TcmISDBModel dbObj6, String persid, String compId) {
		Vector usrgrpids = new Vector();
		String query = "";
		DBResultSet dbrs = null;
		ResultSet rs = null;

		try {
			//	   query = "select distinct USER_GROUP_ID from user_group_member where personnel_id = "+persid+"" ;
			query = "select distinct USER_GROUP_ID from personnel_user_group_view where personnel_id = " + persid + "";
			/*query += "Select distinct USER_GROUP_ID from (select personnel_id , company_id ,USER_GROUP_ID from user_group_member ";
			query += "union all select personnel_id , company_id ,USER_GROUP_ID from user_group_member_ig union all select personnel_id , company_id ,USER_GROUP_ID from user_group_member_location) ";
			query += "where personnel_id = "+persid+" ";*/

			if (compId.length() > 0) {
				query += " and company_id = '" + compId + "'";
			}

			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String facn = (rs.getString("USER_GROUP_ID") == null ? "" : rs.getString("USER_GROUP_ID"));
				usrgrpids.addElement(facn);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return usrgrpids;
	}

	public static String getfullname(TcmISDBModel dbObj6, String persid, String compId) {
		String query = "";
		String FullName = "";
		DBResultSet dbrs = null;
		ResultSet rs = null;

		try {
			query = "select first_name ||' '|| last_name Full_name from personnel where personnel_id = " + persid + "";
			if (compId.length() > 0) {
				query += " and company_id = '" + compId + "'";
			}
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				FullName = rs.getString("FULL_NAME") == null ? "No Name" : rs.getString("FULL_NAME");
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
			return "";
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return FullName;
	}

	public static void LoginSetup(HttpSession session, TcmISDBModel dbObj, String personnelID, String CompanyID, String LoginID) throws Exception {
		try {
			//String FullName = "";
			//DBResultSet dbrs = null;
			ResultSet rs = null;
			/*try
			{
			dbrs = dbObj.doQuery("select first_name ||' '|| last_name Full_name from personnel where personnel_id = "+personnelID+" and company_id = '"+CompanyID+"'" );
			rs=dbrs.getResultSet();
			while(rs.next())
			{
			FullName = rs.getString("FULL_NAME")==null?"No Name":rs.getString("FULL_NAME");
			}
			}
			catch (Exception e)
			{
			e.printStackTrace();
			return;
			}
			finally
			{
			if (dbrs!= null) {dbrs.close();}
			}*/

			//session.setAttribute("FULLNAME",getfullname(dbObj,personnelID,CompanyID));
			session.setAttribute("loginState", "authenticated");
			session.setAttribute("loginId", LoginID);
			//session.setAttribute("ALLOWEDGROUPS", createallowedusrgrps(dbObj,personnelID,CompanyID));

			{
				String company = CompanyID;
				Connection con = dbObj.getConnection();
				String userid = personnelID;

				PreparedStatement pstmt = null;
				rs = null;

				try {
					// Can be commented out
					Map hubInfo = (Map) session.getAttribute("hubInfo");
					if (hubInfo == null) {
						// create the list if this is first time for this session
						hubInfo = new HashMap();
						session.setAttribute("hubInfo", hubInfo);
						//pstmt = con.prepareStatement("select distinct h.preferred_warehouse,h.branch_plant from user_group_member u, hub_destination_facility_view h where u.personnel_id = ? and u.company_id = ? and h.preferred_warehouse = u.facility_id and h.company_id = u.company_id" );
						pstmt = con.prepareStatement("select distinct FACILITY_ID PREFERRED_WAREHOUSE, HUB BRANCH_PLANT from hub_pref where personnel_id = ? and company_id = ? ORDER BY HUB, FACILITY_ID");
						if (log.isDebugEnabled()) {
							log.debug("select distinct FACILITY_ID PREFERRED_WAREHOUSE, HUB BRANCH_PLANT from hub_pref where personnel_id = " + userid + " and company_id = '" + company + "'  ORDER BY HUB, FACILITY_ID");
						}

						pstmt.setString(1, userid);
						pstmt.setString(2, company);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							String fid = rs.getString("PREFERRED_WAREHOUSE");
							String bp = rs.getString("BRANCH_PLANT");
							hubInfo.put(fid, bp);
						}
						pstmt.close();
					}

					// get a list of facilities
					Map warehouseInfo = (Map) session.getAttribute("warehouseInfo");
					if (warehouseInfo == null) {
						// create the list if this is first time for this session
						warehouseInfo = new HashMap();
						session.setAttribute("warehouseInfo", warehouseInfo);
						String queryStatement = "SELECT distinct igd.hub branch_plant,x.facility_id,fx_facility_name(x.facility_id, x.company_id) facility_name, ";
						queryStatement += "x.company_id FROM facility_inventory_group x,inventory_group_definition igd WHERE "
							+ " x.inventory_group IN( SELECT inventory_group FROM user_inventory_group WHERE  personnel_id = ? AND company_id = ?) AND "
							+ "x.company_id <> 'Radian' AND status = 'ACTIVE' AND x.inventory_group = igd.inventory_group order by igd.hub, facility_name";
						
						if (log.isDebugEnabled()) {
							log.debug(queryStatement + " -> " + userid + ", " + company);
						}
						pstmt = con.prepareStatement(queryStatement);
						pstmt.setString(1, userid);
						pstmt.setString(2, company);
						//pstmt.setString(3, company);
						rs = pstmt.executeQuery();
						String warehouse = null;
						Map facilityInfo = null;
						while (rs.next()) {
							String pwh = rs.getString("BRANCH_PLANT");
							if (warehouse == null || !warehouse.equals(pwh)) {
								warehouse = rs.getString("BRANCH_PLANT");
								warehouseInfo.put(warehouse, facilityInfo = new HashMap());
							}
							String fid = rs.getString("FACILITY_ID");
							String fname = rs.getString("FACILITY_NAME");
							facilityInfo.put(fid, fname);
						}
						pstmt.close();
					}

					/*// Can be commented out
					pstmt =	con.prepareStatement("select u.user_group_id, u.facility_id, h.preferred_warehouse, h.branch_plant, h.facility_name from user_group_member u, hub_destination_facility_view h where u.personnel_id = ? and u.company_id = ? and u.USER_GROUP_ID ='Issuing' and h.facility_id = u.facility_id and h.company_id = u.company_id order by h.facility_name");
					pstmt.setString(1, userid);
					pstmt.setString(2, company);
					rs = pstmt.executeQuery();
					while (rs.next()) {
					String group = rs.getString("USER_GROUP_ID");
					String facility = rs.getString("FACILITY_ID");
					String warehouse = rs.getString("PREFERRED_WAREHOUSE");
					List facilities = (List) session.getAttribute(group);

					if (facilities == null)
					facilities = new ArrayList();
					if (hubInfo.containsKey(facility)) {
					List groups = (List) session.getAttribute(facility);
					if (groups == null)
					groups = new ArrayList();
					if (!facilities.contains(facility))
					facilities.add(facility);
					if (!groups.contains(group))
					groups.add(group);
					// save the list of facilities where the user may perform a role
					session.setAttribute(group, facilities);
					// save the list of roles the user may perform for a facility
					session.setAttribute(facility, groups);
					} else if ("All".equals(facility)) {
					for (Iterator it = hubInfo.keySet().iterator(); it.hasNext();) {
					facility = (String) it.next();
					List groups = (List) session.getAttribute(facility);
					if (groups == null)
					groups = new ArrayList();
					if (!facilities.contains(facility))
					facilities.add(facility);
					if (!groups.contains(group))
					groups.add(group);
					// save the list of facilities where the user may perform a role
					session.setAttribute(group, facilities);
					// save the list of roles the user may perform for a facility
					session.setAttribute(facility, groups);
					}
					}
					}
					pstmt.close();*/
				}
				finally {
					try {
						if (rs != null)
							rs.close();
					}
					catch (Exception ignore) {
					}
					try {
						if (pstmt != null)
							pstmt.close();
					}
					catch (Exception ignore) {
					}
				}
			}
		}
		catch (Exception e11) {
			e11.printStackTrace();
			throw e11;
		}
	}

	public static boolean hasaccessto(HttpSession usrsession, String usrgroup) {
		Vector allusergrpall = null;
		boolean rtnusrgrpval = false;
		try {
			allusergrpall = (Vector) usrsession.getAttribute("ALLOWEDGROUPS");
		}
		catch (Exception ex) {
			return rtnusrgrpval;
		}

		if (allusergrpall == null) {
			allusergrpall = new Vector();
		}

		if (allusergrpall.contains(usrgroup)) {
			rtnusrgrpval = true;
		}
		allusergrpall = null;
		return rtnusrgrpval;
	}

	public static ArrayList sortstringcollec(ArrayList ascoll) {
		Collections.sort(ascoll, new Comparator() {
			public int compare(Object o1, Object o2) {
				Map.Entry e1 = (Map.Entry) o1;
				Map.Entry e2 = (Map.Entry) o2;
				String first = (String) e1.getValue();
				String second = (String) e2.getValue();
				if (first == null) {
					first = "";
				}
				if (second == null) {
					second = "";
				}
				return first.compareTo(second);
			}
		});
		return ascoll;
	}

	public static ArrayList sortintcollec(ArrayList ascoll) {
		Collections.sort(ascoll, new Comparator() {
			public int compare(Object o1, Object o2) {
				Map.Entry e1 = (Map.Entry) o1;
				Map.Entry e2 = (Map.Entry) o2;
				Integer first = (Integer) e1.getValue();
				Integer second = (Integer) e2.getValue();
				if (first == null) {
					first = new Integer(0);
				}
				if (second == null) {
					second = new Integer(0);
				}
				return first.compareTo(second);
			}
		});
		return ascoll;
	}

	public static boolean isItemCountingHub(Collection hubInventoryGroupOvBeanColl, String selectedBranchPlant) throws Exception {
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
		int itemCountingHubCount = 0;

		while (hubIterator.hasNext()) {
			HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
			String currentBranchPlant = hubInventoryGroupOvBean.getBranchPlant();

			if (selectedBranchPlant.equalsIgnoreCase(currentBranchPlant)) {
				Collection inventoryGroupCollection = hubInventoryGroupOvBean.getInventoryGroupCollection();

				Iterator inventoryGroupIterator = inventoryGroupCollection.iterator();
				while (inventoryGroupIterator.hasNext()) {
					InventoryGroupObjBean inventoryGroupObjBean = (InventoryGroupObjBean) inventoryGroupIterator.next();
					if ("Item Counting".equalsIgnoreCase(inventoryGroupObjBean.getIssueGeneration())) {
						itemCountingHubCount++;
					}
				}
			}
		}

		return (itemCountingHubCount > 0);
	}
	
	
	public static boolean isAutomatedPutawayHub(Collection hubInventoryGroupOvBeanColl, String selectedBranchPlant) throws Exception {
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();

		while (hubIterator.hasNext()) {
			HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
			if (hubInventoryGroupOvBean.getBranchPlant().equalsIgnoreCase(selectedBranchPlant) && "Y".equalsIgnoreCase(hubInventoryGroupOvBean.getAutomatedPutaway()))
				return true;
		}

		return false;
	}

	public static String getHubName(Collection hubInventoryGroupOvBeanColl, String selectedBranchPlant) {
		String selectedHubName = "";
		//String selected = "";
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
		while (hubIterator.hasNext()) {
			HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
			String currentBranchPlant = hubInventoryGroupOvBean.getBranchPlant();
			//selected = "";
			if (selectedBranchPlant.equalsIgnoreCase(currentBranchPlant)) {
				selectedHubName = hubInventoryGroupOvBean.getHubName();
			}
		}
		return selectedHubName;
	}

	public static String getFirstBranchPlant(Collection hubInventoryGroupOvBeanColl) {
		String selectedBranchPlant = "";
		int hubCount = 0;
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
		while (hubIterator.hasNext()) {
			HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
			if (hubCount == 0) {
				selectedBranchPlant = hubInventoryGroupOvBean.getBranchPlant();
			}
			hubCount++;
		}
		return selectedBranchPlant;
	}

	public static StringBuffer getHubDropDown(Collection hubInventoryGroupOvBeanColl, String selectedBranchPlant) {
		StringBuffer hubDropDown = new StringBuffer();
		String selected = "";
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
		while (hubIterator.hasNext()) {
			HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
			String currentBranchPlant = hubInventoryGroupOvBean.getBranchPlant();
			selected = "";
			if (selectedBranchPlant.equalsIgnoreCase(currentBranchPlant)) {
				selected = "selected";
			}
			hubDropDown.append("<option  " + selected + " value=\"" + currentBranchPlant + "\">" + hubInventoryGroupOvBean.getHubName() + "</option>\n");
		}
		return hubDropDown;
	}

	public static StringBuffer getInventoryGroupDropDown(Collection hubInventoryGroupOvBeanColl, String selectedBranchPlant, String selectedInventoryGroup, boolean showOnlyItemCountable) {
		StringBuffer inventoryGroupDropDown = new StringBuffer();
		String selected = "";
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
		while (hubIterator.hasNext()) {
			HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean) hubIterator.next();
			String currentBranchPlant = hubInventoryGroupOvBean.getBranchPlant();

			if (selectedBranchPlant.equalsIgnoreCase(currentBranchPlant)) {
				Collection inventoryGroupCollection = hubInventoryGroupOvBean.getInventoryGroupCollection();
				int inventoryGroupCount = 0;
				inventoryGroupDropDown.append("<option value=\"All\">All</option>\n");

				Iterator inventoryGroupIterator = inventoryGroupCollection.iterator();
				while (inventoryGroupIterator.hasNext()) {
					InventoryGroupObjBean inventoryGroupObjBean = (InventoryGroupObjBean) inventoryGroupIterator.next();
					String currentInventoryGroup = inventoryGroupObjBean.getInventoryGroup();
					selected = "";
					if (selectedInventoryGroup.equalsIgnoreCase(currentInventoryGroup)) {
						selected = "selected";
					}
					if ((showOnlyItemCountable && "Item Counting".equalsIgnoreCase(inventoryGroupObjBean.getIssueGeneration())) || !showOnlyItemCountable) {
						inventoryGroupDropDown.append("<option  " + selected + " value=\"" + currentInventoryGroup + "\">" + inventoryGroupObjBean.getInventoryGroupName() + "</option>\n");
						inventoryGroupCount++;
					}
				}

				if (inventoryGroupCount == 0) {

				}
			}
		}
		return inventoryGroupDropDown;
	}
	
	public static StringBuffer getHubRoomDropDown(TcmISDBModel dbObj, String selectedBranchPlant) {
		StringBuffer hubRoomDropDown = new StringBuffer("<option value=\"\">Please Select</option>");
		DBResultSet dbrs = null;
		ResultSet rs = null;
	    try {
	      java.util.Map map = dbObj.getConnection().getTypeMap();
	      map.put("HUB_ROOM_OBJ",
	              Class.forName(
	          "com.tcmis.internal.hub.beans.HubRoomOvBean"));
	      map.put("ROOM_OBJ",
	              Class.forName(
	          "com.tcmis.internal.hub.beans.VvHubRoomBean"));
	      
	      String query = "select value(p) from HUB_ROOM_OV p where HUB="+SqlHandler.delimitString(selectedBranchPlant)+" order by HUB_NAME";
	      
	      dbrs = dbObj.doQuery(query);
	      rs = dbrs.getResultSet();
	      while (rs.next()) {
	    	  HubRoomOvBean b = (HubRoomOvBean) rs.getObject(1);
	    	  for (Object roomBean : b.getRoomVar()) {
	    		  if (roomBean instanceof VvHubRoomBean) {
	    			  VvHubRoomBean roomObj = (VvHubRoomBean) roomBean;
	    			  String room = roomObj.getRoom();
	    			  String roomDesc = roomObj.getRoomDescription();
	    			  hubRoomDropDown.append("<option value=\"").append(room).append("\">").append(roomDesc).append("</option>\n");
	    		  }
	    	  }
	      }
	    }
	    catch(Exception e) {
	      log.error("Error getting hub room dropdown data", e);
	    }
	    finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
		return hubRoomDropDown;
	}

	public static StringBuffer sorthashbyValue(Collection colla, String hub_branch_id, Hashtable hub_list) {
		StringBuffer MsgSB = new StringBuffer();

		ArrayList as = new ArrayList(colla);
		as = sortintcollec(as);

		String hub_selected = "";
		Iterator i1 = as.iterator();
		while (i1.hasNext()) {
			Map.Entry e1 = (Map.Entry) i1.next();
			//String keyvalue= ( String ) e1.getValue();
			String strkey = (String) e1.getKey();

			String keyvalue = (String) hub_list.get(strkey);

			hub_selected = "";
			if (strkey.equalsIgnoreCase(hub_branch_id)) {
				hub_selected = "selected";
			}
			MsgSB.append("<option  " + hub_selected + " value=\"" + strkey + "\">" + keyvalue + "</option>\n");
		}

		return MsgSB;
	}

	public static StringBuffer sorthashbyValue(Collection colla, String hub_branch_id) {
		StringBuffer MsgSB = new StringBuffer();

		ArrayList as = new ArrayList(colla);
		as = sortstringcollec(as);

		String hub_selected = "";
		Iterator i1 = as.iterator();
		while (i1.hasNext()) {
			Map.Entry e1 = (Map.Entry) i1.next();
			String keyvalue = (String) e1.getValue();
			String strkey = (String) e1.getKey();

			hub_selected = "";
			if (strkey.equalsIgnoreCase(hub_branch_id)) {
				hub_selected = "selected";
			}
			MsgSB.append("<option  " + hub_selected + " value=\"" + strkey + "\">" + keyvalue + "</option>\n");
		}

		return MsgSB;
	}

	public static StringBuffer sorthashbyValueshowkey(Collection colla, String selectedkey) {
		StringBuffer MsgSB = new StringBuffer();

		ArrayList as = new ArrayList(colla);
		as = sortstringcollec(as);

		String key_selected = "";
		Iterator i1 = as.iterator();
		while (i1.hasNext()) {
			Map.Entry e1 = (Map.Entry) i1.next();
			String keyvalue = (String) e1.getValue();
			String strkey = (String) e1.getKey();

			key_selected = "";
			if (keyvalue.equalsIgnoreCase(selectedkey)) {
				key_selected = "selected";
			}
			MsgSB.append("<option  " + key_selected + " value=\"" + keyvalue + "\">" + strkey + "</option>\n");
		}

		return MsgSB;
	}

	public static String intselectedhub(Collection colla) {
		String initialselHub = "";

		ArrayList as = new ArrayList(colla);
		as = sortintcollec(as);

		String hub_selected = "";
		Iterator i1 = as.iterator();
		int i = 0;
		while (i1.hasNext()) {
			i++;
			Map.Entry e1 = (Map.Entry) i1.next();
			//String keyvalue= ( String ) e1.getValue();
			String strkey = (String) e1.getKey();
			if (i == 1) {
				initialselHub = strkey;
			}
		}

		return initialselHub;
	}

	public static Hashtable createSupplierList(TcmISDBModel dbObj, String personnelid, String CompanyID) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable supplier_list = new Hashtable();

		try {
			String query = "select x.SUPPLIER, x.SUPPLIER_NAME from supplier x, user_group_member y where x.SUPPLIER = y.FACILITY_ID and y.PERSONNEL_ID = " + personnelid + " and y.user_group_Id = 'suppliershipping' ";
			/*if (CompanyID.length() > 0)
			{
			query += " and y.company_id = '"+CompanyID+"' ";
			}*/
			query += "order by x.SUPPLIER_NAME";

			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String temp1 = rs.getString("SUPPLIER_NAME") == null ? "" : rs.getString("SUPPLIER_NAME");
				String temp2 = rs.getString("SUPPLIER") == null ? "" : rs.getString("SUPPLIER");

				supplier_list.put(temp2, temp1);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return supplier_list;
	}

	public static Hashtable getShiptoInitialData(TcmISDBModel dbObj3, Hashtable supplierlist, boolean showall) throws Exception {
		String allowedsuppliers = "";
		for (Enumeration e = supplierlist.keys(); e.hasMoreElements();) {
			String branch_plant = (String) (e.nextElement());
			allowedsuppliers += "'" + branch_plant + "',";
		}
		allowedsuppliers = allowedsuppliers.substring(0, allowedsuppliers.length() - 1);

		String query = "select distinct SUPPLIER,SHIP_TO_LOCATION_ID from virtual_hub_ship_to_view where SUPPLIER in (" + allowedsuppliers + ") ";
		query += "order by SUPPLIER,SHIP_TO_LOCATION_ID ";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable resultdata = new Hashtable();
		Vector suppID = new Vector();
		int resultcount = 0;

		try {
			dbrs = dbObj3.doQuery(query);
			rs = dbrs.getResultSet();
			String lastHub = "";

			while (rs.next()) {
				String tmpFacID = rs.getString("SHIP_TO_LOCATION_ID");
				String tmpHub = rs.getString("SUPPLIER");
				resultcount++;

				if (!tmpHub.equalsIgnoreCase(lastHub)) {
					suppID.addElement(tmpHub);
					Hashtable fh = new Hashtable();
					Vector facID = new Vector();
					if (showall) {
						facID.addElement("All");
					}
					facID.addElement(tmpFacID);
					fh.put("SHIP_TO_LOCATION_ID", facID);
					resultdata.put(tmpHub, fh);
				}
				else {
					Hashtable fh = (Hashtable) resultdata.get(tmpHub);
					Vector facID = (Vector) fh.get("SHIP_TO_LOCATION_ID");
					if (!facID.contains(tmpFacID)) {
						facID.addElement(tmpFacID);
					}
					fh.put("SHIP_TO_LOCATION_ID", facID);
					resultdata.put(tmpHub, fh);
				}
				lastHub = tmpHub;
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

		if (resultcount == 0) {
			for (Enumeration e = supplierlist.keys(); e.hasMoreElements();) {
				String branch_plant = (String) (e.nextElement());
				suppID.addElement(branch_plant);

				Hashtable fh = new Hashtable();
				Vector facID = new Vector();

				if (showall) {
					facID.addElement("All");
				}

				fh.put("SHIP_TO_LOCATION_ID", facID);
				resultdata.put(branch_plant, fh);
			}
		}

		resultdata.put("SUPPLIER", suppID);
		return resultdata;
	}

	public static StringBuffer createshiptoinvgrjs(Hashtable finalresultdata) {
		StringBuffer Msgjs = new StringBuffer();
		Vector hubid = (Vector) finalresultdata.get("SUPPLIER");
		String tmp = "";
		String althubid = "var althubid = new Array(";
		String altinvid = "var altinvid = new Array();\n ";

		int i = 0;

		for (int ii = 0; ii < hubid.size(); ii++) {
			String wacid = (String) hubid.elementAt(ii);
			althubid += "\"" + wacid + "\"" + ",";

			Hashtable fh = (Hashtable) finalresultdata.get(wacid);
			Vector cabIDv = (Vector) fh.get("SHIP_TO_LOCATION_ID");
			altinvid += "altinvid[\"" + wacid + "\"] = new Array(";

			for (i = 0; i < cabIDv.size(); i++) {
				String facID = (String) cabIDv.elementAt(i);

				tmp += "\"" + facID + "\"" + ",";
				altinvid += "\"" + facID + "\"" + ",";
			}
			//removing the last commas ','
			altinvid = altinvid.substring(0, altinvid.length() - 1) + ");\n";
		}

		if (althubid.indexOf(",") > 1) {
			althubid = althubid.substring(0, althubid.length() - 1) + ");\n";
		}

		Msgjs.append(althubid + " " + altinvid);

		return Msgjs;
	}

	public static Hashtable createHubList(TcmISDBModel dbObj, String personnelid, String CompanyID) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable hub_list = new Hashtable();
		Hashtable hub_prioritylist = new Hashtable();
		Hashtable hub_currencylist = new Hashtable();

		try {
			String query = "select distinct FACILITY_ID, HUB, PRIORITY,HOME_CURRENCY_ID from hub_pref_view where PERSONNEL_ID = " + personnelid + " ";
			if (CompanyID.length() > 0) {
				query += " and company_id = '" + CompanyID + "'";
			}
			query += " order by PRIORITY, HUB, FACILITY_ID ";
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String temp1 = rs.getString("FACILITY_ID") == null ? "" : rs.getString("FACILITY_ID");
				String temp2 = rs.getString("HUB") == null ? "" : rs.getString("HUB");
				String temp3 = rs.getString("PRIORITY") == null ? "1000" : rs.getString("PRIORITY");
				if (temp1.length() > 0) {
					hub_list.put(temp2, temp1);
					hub_prioritylist.put(temp2, new Integer(temp3));
				}
				String tmpcurrency = rs.getString("HOME_CURRENCY_ID") == null ? "" : rs.getString("HOME_CURRENCY_ID");
				hub_currencylist.put(temp2, tmpcurrency);
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

		Hashtable hub_list_set = new Hashtable();
		hub_list_set.put("HUB_LIST", hub_list);
		hub_list_set.put("HUB_PRORITY_LIST", hub_prioritylist);
		hub_list_set.put("HUB_CURRENCY_LIST", hub_currencylist);

		return hub_list_set;
	}

	public static Hashtable createCustomerHubList(TcmISDBModel dbObj) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable hub_list = new Hashtable();
		Hashtable hub_prioritylist = new Hashtable();
		//Hashtable hub_currencylist = new Hashtable();

		try {
			//String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE,rownum PRIORITY from facility where BRANCH_PLANT in (select DISTINCT SOURCE_HUB from inventory_group) ";
			//String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE,rownum PRIORITY from hub_preferred_warehouse_view";
			String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE,1000 PRIORITY from hub_preferred_warehouse_view ORDER BY PREFERRED_WAREHOUSE";
			//query += " order by PREFERRED_WAREHOUSE ";
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String temp1 = rs.getString("PREFERRED_WAREHOUSE") == null ? "" : rs.getString("PREFERRED_WAREHOUSE");
				String temp2 = rs.getString("BRANCH_PLANT") == null ? "" : rs.getString("BRANCH_PLANT");
				String temp3 = rs.getString("PRIORITY") == null ? "1000" : rs.getString("PRIORITY");
				if (temp1.length() > 0) {
					hub_list.put(temp2, temp1);
					hub_prioritylist.put(temp2, new Integer(temp3));
				}
				//String tmpcurrency = rs.getString("HOME_CURRENCY_ID")==null?"":rs.getString("HOME_CURRENCY_ID");
				//hub_currencylist.put(temp2 , tmpcurrency);

			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		Hashtable hub_list_set = new Hashtable();
		hub_list_set.put("HUB_LIST", hub_list);
		hub_list_set.put("HUB_PRORITY_LIST", hub_prioritylist);
		//hub_list_set.put( "HUB_CURRENCY_LIST" , hub_currencylist );

		return hub_list_set;
	}
	
	public static Vector createBinTypeList(TcmISDBModel dbObj) {
		String query = "select bin_type from vv_bin_type";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String faci = (rs.getString("bin_type") == null ? "" : rs.getString("bin_type"));
				ResultV.addElement(faci);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	/*public static Hashtable createHubList(TcmISDBModel dbObj, String group, String personnelid, String CompanyID)
	{
	DBResultSet dbrs = null;
	ResultSet rs = null;
	Hashtable hub_list = new Hashtable();
	Hashtable hub_prioritylist = new Hashtable();
	int prioritycount = 0;

	try
	{
	//String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from facility where PREFERRED_WAREHOUSE is not null and BRANCH_PLANT in (2106)";
	String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from customer.facility f, customer.user_group_member u where branch_plant is not null and f.FACILITY_ID = u.FACILITY_ID ";
	       query += " and u.PERSONNEL_ID = "+personnelid+" and U.user_group_id = '"+group+"' and u.company_id = '"+CompanyID+"' order by PREFERRED_WAREHOUSE";

	dbrs = dbObj.doQuery(query);
	rs=dbrs.getResultSet();
	while (rs.next())
	{
	    prioritycount++;
			String temp1 =  rs.getString("PREFERRED_WAREHOUSE")==null?"BLANK":rs.getString("PREFERRED_WAREHOUSE");
	    String temp2 =  rs.getString("BRANCH_PLANT")==null?"BLANK":rs.getString("BRANCH_PLANT");
	    if ( ! temp1.equalsIgnoreCase("BLANK"))
	    {
	        hub_list.put(temp2 , temp1);
				hub_prioritylist.put(temp2 , new Integer (prioritycount));
	    }
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	finally
	{
	if (dbrs!= null) {dbrs.close();}
	}

	Hashtable hub_list_set = new Hashtable();
	hub_list_set.put( "HUB_LIST" , hub_list );
	hub_list_set.put( "HUB_PRORITY_LIST" , hub_prioritylist );
	return hub_list_set;
	}*/

	public static Vector createItemStatusSet(TcmISDBModel dbObj6) {
		//String query="select lot_status from vv_lot_status order by lot_status ";
		String query = "select lot_status from vv_lot_status where ACTIVE='Y' order by lot_status ";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String faci = (rs.getString("lot_status") == null ? "" : rs.getString("lot_status"));
				result.put(faci, faci);
				ResultV.addElement(result);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Vector autoShipConfirmlist(TcmISDBModel dbObj6) {
		String query = "select BRANCH_PLANT from hub where AUTO_SHIP_CONFIRM <> 'N' ";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String branchPlant = (rs.getString("BRANCH_PLANT") == null ? "" : rs.getString("BRANCH_PLANT"));
				ResultV.addElement(branchPlant);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Vector createCompanylist(TcmISDBModel dbObj6) {
		String query = "select * from customer.company order by COMPANY_NAME ";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String faci = (rs.getString("COMPANY_NAME") == null ? "" : rs.getString("COMPANY_NAME"));
				String facn = (rs.getString("COMPANY_ID") == null ? "" : rs.getString("COMPANY_ID"));
				result.put(faci, facn);
				ResultV.addElement(result);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Vector createRootCaauselist(TcmISDBModel dbObj6, String lotstatus) {
		String query = "select * from VV_lot_status_root_cause where LOT_STATUS = '" + lotstatus + "' order by ROOT_CAUSE_TITLE ";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String facn = (rs.getString("ROOT_CAUSE") == null ? "" : rs.getString("ROOT_CAUSE"));
				String faci = (rs.getString("ROOT_CAUSE_TITLE") == null ? "" : rs.getString("ROOT_CAUSE_TITLE"));
				result.put(faci, facn);
				ResultV.addElement(result);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Vector createinvgrplist(TcmISDBModel dbObj6, String shipttolocid) {
		//String query="select distinct INVENTORY_GROUP,INVENTORY_GROUP_NAME,OPS_ENTITY_ID from inventory_group_definition where hub in (Select BRANCH_PLANT from location_to_hub_view where LOCATION_ID = '"+shipttolocid+"') ";
		String query = "select * from location_to_ig_view where LOCATION_ID = '" + shipttolocid + "'";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String facn = (rs.getString("INVENTORY_GROUP") == null ? "" : rs.getString("INVENTORY_GROUP"));
				String faci = (rs.getString("INVENTORY_GROUP_NAME") == null ? "" : rs.getString("INVENTORY_GROUP_NAME"));
				result.put(faci, facn);
				ResultV.addElement(result);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Vector createStatusSet(TcmISDBModel dbObj6) {
		//String query="select lot_status from vv_lot_status order by lot_status ";
		String query = "select lot_status,PICKABLE,CERTIFIED,ALLOCATION_ORDER from vv_lot_status where ACTIVE='Y' and LOT_STATUS <> 'Incoming' order by lot_status ";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String faci = (rs.getString("lot_status") == null ? "" : rs.getString("lot_status"));
				String pickable = (rs.getString("PICKABLE") == null ? "" : rs.getString("PICKABLE"));
				String certified = (rs.getString("CERTIFIED") == null ? "" : rs.getString("CERTIFIED"));
				String allocorder = (rs.getString("ALLOCATION_ORDER") == null ? "" : rs.getString("ALLOCATION_ORDER"));

				if ("Y".equalsIgnoreCase(pickable) && "0".equalsIgnoreCase(allocorder)) {
					pickable = "N";
				}

				if ("Y".equalsIgnoreCase(certified)) {
					pickable = "Y";
				}

				result.put(faci, pickable);
				ResultV.addElement(result);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Vector createvgrpmemlist(TcmISDBModel dbObj6, String personnel_id, String usergroup) {
		String query = "select distinct INVENTORY_GROUP from user_group_member_ig where personnel_Id = " + personnel_id + " and user_group_id = '" + usergroup + "'";
		DBResultSet dbrs = null;
		ResultSet rs = null;

		Vector ResultV = new Vector();
		try {
			dbrs = dbObj6.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				String invengrp = (rs.getString("INVENTORY_GROUP") == null ? "" : rs.getString("INVENTORY_GROUP"));

				ResultV.addElement(invengrp);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return ResultV;
	}

	public static Hashtable createAllHubList(TcmISDBModel dbObj) {
		return createAllHubList(dbObj, "");
		/*DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable hub_list = new Hashtable();
		Hashtable hub_prioritylist = new Hashtable();
		int prioritycount = 0;

		try
		{
		//String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from facility where PREFERRED_WAREHOUSE is not null and BRANCH_PLANT in (2106)";
		//String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from customer.facility where branch_plant is not null and branch_plant !=2202 and COMPANY_ID='Radian' order by PREFERRED_WAREHOUSE";
		String query= "select BRANCH_PLANT,PREFERRED_WAREHOUSE from customer.facility where branch_plant is not null and branch_plant !=2202 order by PREFERRED_WAREHOUSE";

		dbrs=dbObj.doQuery( query );
		rs=dbrs.getResultSet();
		while ( rs.next() )
		{
		prioritycount++;
		String temp1=rs.getString( "PREFERRED_WAREHOUSE" ) == null ? "BLANK" : rs.getString( "PREFERRED_WAREHOUSE" );
		String temp2=rs.getString( "BRANCH_PLANT" ) == null ? "BLANK" : rs.getString( "BRANCH_PLANT" );
		if ( !temp1.equalsIgnoreCase( "BLANK" ) )
		{
		hub_list.put( temp2,temp1 );
		  hub_prioritylist.put(temp2 , new Integer (prioritycount));
		}
		}
		}
		catch ( Exception e )
		{
		e.printStackTrace();
		}
		finally
		{
		if (dbrs!= null) {dbrs.close();}
		}

		Hashtable hub_list_set = new Hashtable();
		hub_list_set.put( "HUB_LIST" , hub_list );
		hub_list_set.put( "HUB_PRORITY_LIST" , hub_prioritylist );
		return hub_list_set;*/
	}

	public static Hashtable createAllHubList(TcmISDBModel dbObj, String CompanyID) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable hub_list = new Hashtable();
		Hashtable hub_prioritylist = new Hashtable();
		int prioritycount = 0;

		try {
			String query = "";
			if ("purch".equalsIgnoreCase(CompanyID)) {
				query = "select BRANCH_PLANT, HUB_NAME PREFERRED_WAREHOUSE from hub where branch_plant is not null order by PREFERRED_WAREHOUSE";
			}
			else if (CompanyID.length() > 0) {
				//String query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from facility where PREFERRED_WAREHOUSE is not null and BRANCH_PLANT in (2106)";
				query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from customer.facility where branch_plant is not null and branch_plant !=2202 and COMPANY_ID='" + CompanyID + "' order by PREFERRED_WAREHOUSE";
			}
			else {
				query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from customer.facility where branch_plant is not null and branch_plant !=2202 order by PREFERRED_WAREHOUSE";
			}

			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				prioritycount++;
				String temp1 = rs.getString("PREFERRED_WAREHOUSE") == null ? "BLANK" : rs.getString("PREFERRED_WAREHOUSE");
				String temp2 = rs.getString("BRANCH_PLANT") == null ? "BLANK" : rs.getString("BRANCH_PLANT");
				if (!temp1.equalsIgnoreCase("BLANK")) {
					hub_list.put(temp2, temp1);
					hub_prioritylist.put(temp2, new Integer(prioritycount));
				}
			}
		}
		catch (Exception e) {	
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		Hashtable hub_list_set = new Hashtable();
		hub_list_set.put("HUB_LIST", hub_list);
		hub_list_set.put("HUB_PRORITY_LIST", hub_prioritylist);
		return hub_list_set;
	}

	/*public static Hashtable createAllocHubList(TcmISDBModel dbObj,String CompanyID)
	{
	  DBResultSet dbrs = null;
	  ResultSet rs = null;
	  Hashtable hub_list = new Hashtable();
	  Hashtable hub_prioritylist = new Hashtable();
	  int prioritycount = 0;

	  try
	  {
	  String query = "";
	  query = "select BRANCH_PLANT,PREFERRED_WAREHOUSE from customer.facility where branch_plant is not null and COMPANY_ID='"+CompanyID+"' order by PREFERRED_WAREHOUSE";

	  dbrs=dbObj.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		prioritycount++;
		String temp1=rs.getString( "PREFERRED_WAREHOUSE" ) == null ? "BLANK" : rs.getString( "PREFERRED_WAREHOUSE" );
		String temp2=rs.getString( "BRANCH_PLANT" ) == null ? "BLANK" : rs.getString( "BRANCH_PLANT" );
		if ( !temp1.equalsIgnoreCase( "BLANK" ) )
		{
		  hub_list.put( temp2,temp1 );
		  hub_prioritylist.put(temp2 , new Integer (prioritycount));
		}
	  }
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }
	  finally
	  {
		  if (dbrs!= null) {dbrs.close();}
	  }

	  Hashtable hub_list_set = new Hashtable();
	  hub_list_set.put( "HUB_LIST" , hub_list );
	  hub_list_set.put( "HUB_PRORITY_LIST" , hub_prioritylist );
	  return hub_list_set;
	}*/

	public static StringBuffer getmultipleDropDown(Hashtable data1, Vector MatchString) {
		StringBuffer Msgd = new StringBuffer();
		ArrayList as = new ArrayList(data1.entrySet());
		as = sortstringcollec(as);

		Iterator i1 = as.iterator();
		while (i1.hasNext()) {
			Map.Entry e1 = (Map.Entry) i1.next();
			String key = (String) e1.getKey();

			String keyvalue = data1.get(key).toString();
			Msgd.append("<OPTION " + (MatchString.contains(key) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(key) + "\">" + keyvalue + "</OPTION>\n");
		}

		/*Enumeration E;
		for ( E=data1.keys(); E.hasMoreElements(); )
		{
		String key= ( String ) E.nextElement();
		String keyvalue=data1.get( key ).toString();
		Msgd.append( "<OPTION " + ( MatchString.contains( key ) ? "selected" : "" ) + " VALUE=\"" + BothHelpObjs.makeBlankFromNull( key ) + "\">" + keyvalue + "</OPTION>\n" );
		}*/
		return Msgd;
	}

	public static StringBuffer getLogisticsMultipleDropDown(Vector dataV, Vector MatchString) {
		StringBuffer Msgd = new StringBuffer();

		for (int i = 0; i < dataV.size(); i++) {
			Hashtable data1 = (Hashtable) dataV.elementAt(i);
			Enumeration E;
			for (E = data1.keys(); E.hasMoreElements();) {
				String key = (String) E.nextElement();
				String keyvalue = data1.get(key).toString();
				Msgd.append("<OPTION " + (MatchString.contains(key) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(key) + "\">" + key + "</OPTION>\n");
			}
		}

		return Msgd;
	}

	public static StringBuffer getmultipleDropDown(Vector dataV, Vector MatchString) {
		StringBuffer Msgd = new StringBuffer();
		for (int i = 0; i < dataV.size(); i++) {
			Hashtable data1 = (Hashtable) dataV.elementAt(i);
			Enumeration E;
			for (E = data1.keys(); E.hasMoreElements();) {
				String key = (String) E.nextElement();
				String keyvalue = data1.get(key).toString();
				Msgd.append("<OPTION " + (MatchString.contains(keyvalue) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(keyvalue) + "\">" + key + "</OPTION>\n");
			}
		}
		return Msgd;
	}

	public static StringBuffer getDropDown(Hashtable dataV, String MatchString) {
		StringBuffer Msgd = new StringBuffer();

		Enumeration E;
		for (E = dataV.keys(); E.hasMoreElements();) {
			String key = (String) E.nextElement();
			String keyvalue = dataV.get(key).toString();
			// Msgd.append("<OPTION "+(MatchString.equalsIgnoreCase(data1.get(key).toString())?"selected":"")+" VALUE=\""+BothHelpObjs.addSpaceForUrl(data1.get(key).toString())+"\">"+key+"</OPTION>\n");
			Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(keyvalue) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(keyvalue) + "\">" + key + "</OPTION>\n");
		}

		return Msgd;
	}

	public static String getReadonlyDropDownValue(Hashtable dataV, String MatchString) {
		String Msgd = "";

		Enumeration E;
		for (E = dataV.keys(); E.hasMoreElements();) {
			String key = (String) E.nextElement();
			String keyvalue = dataV.get(key).toString();
			if (MatchString.equalsIgnoreCase(keyvalue)) {
				Msgd = key;
			}
		}

		return Msgd;
	}

	public static StringBuffer getDropDown(Vector dataV, String MatchString) {
		StringBuffer Msgd = new StringBuffer();

		for (int i = 0; i < dataV.size(); i++) {
			Hashtable data1 = (Hashtable) dataV.elementAt(i);
			Enumeration E;
			for (E = data1.keys(); E.hasMoreElements();) {
				String key = (String) E.nextElement();
				String keyvalue = data1.get(key).toString();
				keyvalue = BothHelpObjs.makeBlankFromNull(keyvalue);
				String selected = "";
				if (MatchString.equalsIgnoreCase(keyvalue)) {
					selected = "selected";
				}

				// Msgd.append("<OPTION "+(MatchString.equalsIgnoreCase(data1.get(key).toString())?"selected":"")+" VALUE=\""+BothHelpObjs.addSpaceForUrl(data1.get(key).toString())+"\">"+key+"</OPTION>\n");
				Msgd.append("<OPTION " + selected + " VALUE=\"" + HelpObjs.addescapesForJavascript(keyvalue) + "\">" + key + "</OPTION>\n");
			}
		}

		return Msgd;
	}

	public static StringBuffer getDropDown(Vector dataV, String MatchString, Vector excludelist) {
		StringBuffer Msgd = new StringBuffer();

		for (int i = 0; i < dataV.size(); i++) {
			Hashtable data1 = (Hashtable) dataV.elementAt(i);
			Enumeration E;
			for (E = data1.keys(); E.hasMoreElements();) {
				String key = (String) E.nextElement();
				String keyvalue = data1.get(key).toString();

				if (excludelist.contains(keyvalue) && MatchString.equalsIgnoreCase(keyvalue)) {
					Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(keyvalue) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(keyvalue) + "\">" + key + "</OPTION>\n");
				}
				else if (!excludelist.contains(keyvalue)) {
					Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(keyvalue) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(keyvalue) + "\">" + key + "</OPTION>\n");
				}
			}
		}

		return Msgd;
	}

	public static StringBuffer getlogisticsDropDown(Vector dataV, String MatchString, boolean updateallowed, BigDecimal netPendingAdjValue) {
		StringBuffer Msgd = new StringBuffer();

		for (int i = 0; i < dataV.size(); i++) {
			Hashtable data1 = (Hashtable) dataV.elementAt(i);
			Enumeration E;
			for (E = data1.keys(); E.hasMoreElements();) {
				String key = (String) E.nextElement();
				String keyvalue = data1.get(key).toString();
				if (key.equalsIgnoreCase("Write Off Approved") || key.equalsIgnoreCase("Write Off Rejected")) {
					if (MatchString.equalsIgnoreCase("Write Off Approved")) {
						if ("Y".equalsIgnoreCase(keyvalue) && !updateallowed) {
							Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"\">" + key + "</OPTION>\n");
						}
						else {
							Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(key) + "\">" + key + "</OPTION>\n");
						}
					}
					else if (MatchString.equalsIgnoreCase("Write Off Rejected")) {
						if ("Y".equalsIgnoreCase(keyvalue) && !updateallowed) {
							Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"\">" + key + "</OPTION>\n");
						}
						else {
							Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(key) + "\">" + key + "</OPTION>\n");
						}
					}
				}
				else {
					if ("Y".equalsIgnoreCase(keyvalue) && !updateallowed) {
						Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"\">" + key + "</OPTION>\n");
					}
					else {
						if (netPendingAdjValue != null && netPendingAdjValue.intValue() != 0 && key.equalsIgnoreCase("Write Off Requested")) {

						}
						else {
							Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(key) + "\">" + key + "</OPTION>\n");
						}
					}
				}
			}
		}

		return Msgd;
	}

	public static StringBuffer getDescDropDown(Vector dataV, String MatchString) {
		StringBuffer Msgd = new StringBuffer();

		for (int i = 0; i < dataV.size(); i++) {
			Hashtable data1 = (Hashtable) dataV.elementAt(i);
			Enumeration E;
			for (E = data1.keys(); E.hasMoreElements();) {
				String key = (String) E.nextElement();
				String keyvalue = data1.get(key).toString();
				// Msgd.append("<OPTION "+(MatchString.equalsIgnoreCase(data1.get(key).toString())?"selected":"")+" VALUE=\""+BothHelpObjs.addSpaceForUrl(data1.get(key).toString())+"\">"+key+"</OPTION>\n");
				Msgd.append("<OPTION " + (MatchString.equalsIgnoreCase(keyvalue) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.makeBlankFromNull(keyvalue).toString() + "\">" + keyvalue + " : " + key + "</OPTION>\n");
			}
		}

		return Msgd;
	}

	public static StringBuffer getHubCompanyJs(Hashtable hub_list_set, Vector hubcompanyfacs, HttpSession session) {
		StringBuffer MsgJs = new StringBuffer();
		StringBuffer Msgtemp = new StringBuffer();
		StringBuffer Msgtempid = new StringBuffer();
		StringBuffer MsgJs1 = new StringBuffer();
		MsgJs.append("var hubnumbers = {All:0,");
		MsgJs1.append("var hubnames = new Array(\"All\",");
		Msgtemp.append("var altName = new Array();\n");
		Msgtemp.append("" + getHubArray("All", null) + "");
		Msgtempid.append("var altNameid = new Array();\n");
		Msgtempid.append("" + getHubIDArray("All", null) + "");

		String test1 = "";
		String test2 = "";

		Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
		int count = 1;
		for (Enumeration e = hub_list.keys(); e.hasMoreElements();) {
			String branch_plant11 = (String) (e.nextElement());
			String hub_name = (String) (hub_list.get(branch_plant11));

			test1 += "" + branch_plant11 + ":" + count + ",";
			test2 += "'" + hub_name.toUpperCase().replaceAll("'", "\\\\'") + "'" + ",";

			count++;
			Msgtemp.append("" + getComfacHubArray(branch_plant11, hubcompanyfacs, hub_name.toUpperCase()) + "");
			Msgtempid.append("" + getComfacHubIDArray(branch_plant11, hubcompanyfacs, hub_name.toUpperCase()) + "");
		}

		test1 = test1.substring(0, test1.length() - 1);
		test2 = test2.substring(0, test2.length() - 1);

		MsgJs.append(test1);
		MsgJs.append("};\n");
		MsgJs1.append(test2);
		MsgJs1.append(");\n");
		MsgJs.append(MsgJs1);
		MsgJs.append(Msgtemp);
		MsgJs.append(Msgtempid);
		//System.out.println(MsgJs);

		return MsgJs;
	}

	public static String getComfacHubArray(String company_Id, Vector facilityInfo, String hubName) {
		String JsArray = "altName[\"" + hubName + "\"] = new Array(";

		if ("All".equalsIgnoreCase(company_Id)) {
			JsArray += "\"Please Choose a Hub\"";
			JsArray += ");\n";
			return JsArray;
		}

		JsArray += "\"All\",";

		for (int i = 0; i < facilityInfo.size(); i++) {
			Hashtable hD2 = new Hashtable();
			hD2 = (Hashtable) (facilityInfo.elementAt(i));

			String brachplant = hD2.get("BRANCHPLANT").toString();
			if (company_Id.equalsIgnoreCase(brachplant)) {
				String facname = hD2.get("FACILITYID").toString();
				//System.out.println(facname+"   "+facid);
				if( facname == null ) facname ="null";
				JsArray += "'" + facname.replaceAll("'", "\\\\'") + "',";
			}
		}
		JsArray = JsArray.substring(0, JsArray.length() - 1);
		JsArray += ");\n";

		return JsArray;
	}

	public static String getComfacHubIDArray(String company_Id, Vector facilityInfo, String hubName) {
		String JsArray = "altNameid[\"" + hubName + "\"] = new Array(";

		if ("All".equalsIgnoreCase(company_Id)) {
			JsArray += "\"All\"";
			JsArray += ");\n";
			return JsArray;
		}

		JsArray += "\"All\",";

		for (int i = 0; i < facilityInfo.size(); i++) {
			Hashtable hD2 = new Hashtable();
			hD2 = (Hashtable) (facilityInfo.elementAt(i));

			String brachplant = hD2.get("BRANCHPLANT").toString();
			if (company_Id.equalsIgnoreCase(brachplant)) {
				String facname = hD2.get("FACILITYID").toString();
				//System.out.println(facname+"   "+facid);
				if( facname == null ) facname ="null";
				JsArray += "'" + facname.replaceAll("'", "\\\\'") + "',";
			}
		}

		JsArray = JsArray.substring(0, JsArray.length() - 1);
		JsArray += ");\n";

		return JsArray;
	}

	public static StringBuffer getHubJs(Hashtable hub_list_set, HttpSession session) {
		StringBuffer MsgJs = new StringBuffer();
		StringBuffer Msgtemp = new StringBuffer();
		StringBuffer Msgtempid = new StringBuffer();
		StringBuffer MsgJs1 = new StringBuffer();
		MsgJs.append("var hubnumbers = {All:0,");
		MsgJs1.append("var hubnames = new Array(\"All\",");
		Msgtemp.append("var altName = new Array();\n");
		Msgtemp.append("" + getHubArray("All", null) + "");
		Msgtempid.append("var altNameid = new Array();\n");
		Msgtempid.append("" + getHubIDArray("All", null) + "");

		String test1 = "";
		String test2 = "";

		Map warehouseInfo = (Map) session.getAttribute("warehouseInfo");

		Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
		Vector v = new Vector(hub_list.keySet());
	    Collections.sort(v);
	    
		int count = 1;
		for (Enumeration e = v.elements(); e.hasMoreElements();) {
			String branch_plant11 = (String) (e.nextElement());
			String hub_name = (String) (hub_list.get(branch_plant11));

			test1 += "" + branch_plant11 + ":" + count + ",";
			test2 += "\"" + hub_name.toUpperCase() + "\"" + ",";

			count++;

			Map facilityInfo = (Map) warehouseInfo.get(branch_plant11);
			if (facilityInfo == null) {
				facilityInfo = new HashMap();
			}

			Msgtemp.append("" + getHubArray(hub_name.toUpperCase(), facilityInfo) + "");
			Msgtempid.append("" + getHubIDArray(hub_name.toUpperCase(), facilityInfo) + "");

		}

		test1 = test1.substring(0, test1.length() - 1);
		test2 = test2.substring(0, test2.length() - 1);

		MsgJs.append(test1);
		MsgJs.append("};\n");
		MsgJs1.append(test2);
		MsgJs1.append(");\n");
		MsgJs.append(MsgJs1);
		MsgJs.append(Msgtemp);
		MsgJs.append(Msgtempid);

		return MsgJs;
	}

	public static String getWorkAreaArray(String company_Id) {
		return " ";
	}

	public static String getHubArray(String company_Id, Map facilityInfo) {
		String JsArray = "altName[\"" + company_Id + "\"] = new Array(";

		if ("All".equalsIgnoreCase(company_Id)) {
			JsArray += "\"Please Choose a Hub\"";
			JsArray += ");\n";
			return JsArray;
		}

		JsArray += "\"All\",";

		ArrayList as1 = new ArrayList(facilityInfo.entrySet());
		as1 = sortstringcollec(as1);
		Iterator i11 = as1.iterator();
		while (i11.hasNext()) {
			Map.Entry e1 = (Map.Entry) i11.next();
			String facid = (String) e1.getKey();
			String facname = (String) e1.getValue();
			if( facname == null ) facname ="null";
			JsArray += "'" + facname.replaceAll("'", "\\\\'") + "',";
		}

		/*Iterator it=facilityInfo.keySet().iterator();
		while ( it.hasNext() )
		{
		String facid= ( String ) it.next();
		String facname= ( String ) facilityInfo.get( facid );
		JsArray+="\"" + facname + "\",";
		}*/
		JsArray = JsArray.substring(0, JsArray.length() - 1);
		JsArray += ");\n";

		return JsArray;
	}

	public static String getHubIDArray(String company_Id, Map facilityInfo) {
		String JsArray = "altNameid[\"" + company_Id + "\"] = new Array(";

		if ("All".equalsIgnoreCase(company_Id)) {
			JsArray += "\"All\"";
			JsArray += ");\n";
			return JsArray;
		}

		JsArray += "\"All\",";

		ArrayList as2 = new ArrayList(facilityInfo.entrySet());
		as2 = sortstringcollec(as2);
		Iterator i12 = as2.iterator();
		while (i12.hasNext()) {
			Map.Entry e1 = (Map.Entry) i12.next();
			String facid = (String) e1.getKey();
			String facname = (String) e1.getValue();
			JsArray += "\"" + facid + "\",";
		}

		/*Iterator it=facilityInfo.keySet().iterator();
		while ( it.hasNext() )
		{
		String facid= ( String ) it.next();
		String facname= ( String ) facilityInfo.get( facid );
		JsArray+="\"" + facid + "\",";
		}*/
		JsArray = JsArray.substring(0, JsArray.length() - 1);
		JsArray += ");\n";

		return JsArray;
	}

	public static Vector getBolData(TcmISDBModel dbObj, String where) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector resultF = new Vector();
		Hashtable data = new Hashtable();

		try {
			dbrs = dbObj.doQuery("Select * from BILL_OF_LADING_VIEW where " + where + "");
			rs = dbrs.getResultSet();
			while (rs.next()) {
				data = new Hashtable();

				data.put("PICKLIST_ID", rs.getString("PICKLIST_ID") == null ? " " : rs.getString("PICKLIST_ID"));
				data.put("TOTAL_QTY", rs.getString("TOTAL_QTY") == null ? " " : rs.getString("TOTAL_QTY"));
				data.put("QUANTITY_SHIPPED", rs.getString("QUANTITY_SHIPPED") == null ? " " : rs.getString("QUANTITY_SHIPPED"));
				data.put("PACKAGING", rs.getString("PACKAGING") == null ? " " : rs.getString("PACKAGING"));
				data.put("HAZARDOUS", rs.getString("HAZARDOUS") == null ? " " : rs.getString("HAZARDOUS"));
				data.put("DOT", rs.getString("DOT") == null ? " " : rs.getString("DOT"));
				//data.put("ERG",rs.getString("ERG")==null?" ":rs.getString("ERG"));
				data.put("ITEM_ID", rs.getString("ITEM_ID") == null ? " " : rs.getString("ITEM_ID"));
				data.put("MFG_LOT", rs.getString("MFG_LOT") == null ? " " : rs.getString("MFG_LOT"));
				//data.put("DATE_OF_RECEIPT",rs.getString("DATE_OF_RECEIPT")==null?" ":rs.getString("DATE_OF_RECEIPT"));
				//data.put("RADIAN_PO",rs.getString("RADIAN_PO")==null?" ":rs.getString("RADIAN_PO"));
				data.put("EXPIRE_DATE", rs.getString("EXPIRE_DATE") == null ? " " : rs.getString("EXPIRE_DATE"));
				data.put("PR_NUMBER", rs.getString("PR_NUMBER") == null ? " " : rs.getString("PR_NUMBER"));
				data.put("LINE_ITEM", rs.getString("LINE_ITEM") == null ? " " : rs.getString("LINE_ITEM"));
				data.put("MR_LINE", rs.getString("MR_LINE") == null ? " " : rs.getString("MR_LINE"));
				data.put("RECEIPT_ID", rs.getString("RECEIPT_ID") == null ? " " : rs.getString("RECEIPT_ID"));
				//data.put("SALES_ORDER",rs.getString("SALES_ORDER")==null?" ":rs.getString("SALES_ORDER"));
				data.put("SHIPPER_ID", rs.getString("SHIPPER_ID") == null ? " " : rs.getString("SHIPPER_ID"));
				data.put("CARRIER", rs.getString("CARRIER") == null ? " " : rs.getString("CARRIER"));
				data.put("CARRIER_NO", rs.getString("CARRIER_NO") == null ? " " : rs.getString("CARRIER_NO"));
				data.put("REQUIRED_DATETIME", rs.getString("REQUIRED_DATETIME") == null ? " " : rs.getString("REQUIRED_DATETIME"));
				data.put("SHIP_DATE", rs.getString("SHIP_DATE") == null ? " " : rs.getString("SHIP_DATE"));
				//data.put("SOURCE_HUB",rs.getString("SOURCE_HUB")==null?" ":rs.getString("SOURCE_HUB"));
				data.put("END_USER", rs.getString("END_USER") == null ? " " : rs.getString("END_USER"));
				data.put("DEPARTMENT", rs.getString("DEPARTMENT") == null ? " " : rs.getString("DEPARTMENT"));
				//data.put("APPLICATION",rs.getString("APPLICATION")==null?" ":rs.getString("APPLICATION"));
				data.put("APPLICATION_DESC", rs.getString("APPLICATION_DESC") == null ? " " : rs.getString("APPLICATION_DESC"));
				data.put("DELIVERY_POINT", rs.getString("DELIVERY_POINT_DESC") == null ? " " : rs.getString("DELIVERY_POINT_DESC"));
				//data.put("DELIVERY_POINT_DESC",rs.getString("DELIVERY_POINT_DESC")==null?" ":rs.getString("DELIVERY_POINT_DESC"));
				data.put("BATCH", rs.getString("BATCH") == null ? " " : rs.getString("BATCH"));
				//data.put("SHIP_TO_LOCATION_ID",rs.getString("SHIP_TO_LOCATION_ID")==null?" ":rs.getString("SHIP_TO_LOCATION_ID"));
				//data.put("HUB_LOCATION_ID",rs.getString("HUB_LOCATION_ID")==null?" ":rs.getString("HUB_LOCATION_ID"));
				//data.put("SHIP_TO_COUNTRY_ABBREV",rs.getString("SHIP_TO_COUNTRY_ABBREV")==null?" ":rs.getString("SHIP_TO_COUNTRY_ABBREV"));
				data.put("SHIP_TO_STATE_ABBREV", rs.getString("SHIP_TO_STATE_ABBREV") == null ? " " : rs.getString("SHIP_TO_STATE_ABBREV"));
				data.put("SHIP_TO_ADDRESS_LINE_1", rs.getString("SHIP_TO_ADDRESS_LINE_1") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_1"));
				data.put("SHIP_TO_ADDRESS_LINE_2", rs.getString("SHIP_TO_ADDRESS_LINE_2") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_2"));
				//data.put("SHIP_TO_ADDRESS_LINE_3",rs.getString("SHIP_TO_ADDRESS_LINE_3")==null?" ":rs.getString("SHIP_TO_ADDRESS_LINE_3"));
				data.put("SHIP_TO_CITY", rs.getString("SHIP_TO_CITY") == null ? " " : rs.getString("SHIP_TO_CITY"));
				data.put("SHIP_TO_ZIP", rs.getString("SHIP_TO_ZIP") == null ? " " : rs.getString("SHIP_TO_ZIP"));
				data.put("SHIP_TO_LOCATION_DESC", rs.getString("SHIP_TO_LOCATION_DESC") == null ? " " : rs.getString("SHIP_TO_LOCATION_DESC"));
				//data.put("HUB_COUNTRY_ABBREV",rs.getString("HUB_COUNTRY_ABBREV")==null?" ":rs.getString("HUB_COUNTRY_ABBREV"));
				data.put("HUB_STATE_ABBREV", rs.getString("HUB_STATE_ABBREV") == null ? " " : rs.getString("HUB_STATE_ABBREV"));
				data.put("HUB_ADDRESS_LINE_1", rs.getString("HUB_ADDRESS_LINE_1") == null ? " " : rs.getString("HUB_ADDRESS_LINE_1"));
				data.put("HUB_ADDRESS_LINE_2", rs.getString("HUB_ADDRESS_LINE_2") == null ? " " : rs.getString("HUB_ADDRESS_LINE_2"));
				//data.put("HUB_ADDRESS_LINE_3",rs.getString("HUB_ADDRESS_LINE_3")==null?" ":rs.getString("HUB_ADDRESS_LINE_3"));
				data.put("HUB_CITY", rs.getString("HUB_CITY") == null ? " " : rs.getString("HUB_CITY"));
				data.put("HUB_ZIP", rs.getString("HUB_ZIP") == null ? " " : rs.getString("HUB_ZIP"));
				data.put("HUB_LOCATION_DESC", rs.getString("HUB_LOCATION_DESC") == null ? " " : rs.getString("HUB_LOCATION_DESC"));
				data.put("PO_NUMBER", rs.getString("PO_NUMBER") == null ? " " : rs.getString("PO_NUMBER"));
				data.put("PO_RELEASE_NUMBER", rs.getString("PO_RELEASE_NUMBER") == null ? " " : rs.getString("PO_RELEASE_NUMBER"));
				data.put("CAT_PART_NO", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
				data.put("CATALOG_ID", rs.getString("CATALOG_ID") == null ? " " : rs.getString("CATALOG_ID"));
				data.put("ITEM_DESC", rs.getString("ITEM_DESC") == null ? " " : rs.getString("ITEM_DESC"));
				data.put("BILL_TO_ADDRESS_1", rs.getString("BILL_TO_ADDRESS_1") == null ? " " : rs.getString("BILL_TO_ADDRESS_1"));
				data.put("BILL_TO_ADDRESS_2", rs.getString("BILL_TO_ADDRESS_2") == null ? " " : rs.getString("BILL_TO_ADDRESS_2"));
				data.put("BILL_TO_ADDRESS_3", rs.getString("BILL_TO_ADDRESS_3") == null ? " " : rs.getString("BILL_TO_ADDRESS_3"));
				data.put("BILL_TO_ADDRESS_4", rs.getString("BILL_TO_ADDRESS_4") == null ? " " : rs.getString("BILL_TO_ADDRESS_4"));
				data.put("OCONUS", rs.getString("OCONUS") == null ? " " : rs.getString("OCONUS"));
				data.put("TRACKING_NUMBER", rs.getString("TRACKING_NUMBER") == null ? " " : rs.getString("TRACKING_NUMBER"));
				data.put("COMPANY_ID", rs.getString("COMPANY_ID") == null ? " " : rs.getString("COMPANY_ID"));
				data.put("FREIGHT_ORDER_NUMBER", rs.getString("FREIGHT_ORDER_NUMBER") == null ? " " : rs.getString("FREIGHT_ORDER_NUMBER"));
				data.put("SHIP_TO_ADDRESS", rs.getString("SHIP_TO_ADDRESS") == null ? " " : rs.getString("SHIP_TO_ADDRESS"));
				data.put("INVENTORY_GROUP", rs.getString("INVENTORY_GROUP") == null ? " " : rs.getString("INVENTORY_GROUP"));

				resultF.addElement(data);
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

		return resultF;
	}

	public static Vector getBolHashData(TcmISDBModel dbObj, String where) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector resultF = new Vector();
		Hashtable data = new Hashtable();

		try {
			dbrs = dbObj.doQuery("Select * from BILL_OF_LADING_VIEW where " + where + "");
			rs = dbrs.getResultSet();
			while (rs.next()) {
				data = new Hashtable();

				data.put("PICKLIST_ID", rs.getString("PICKLIST_ID") == null ? " " : rs.getString("PICKLIST_ID"));
				data.put("TOTAL_QTY", rs.getString("TOTAL_QTY") == null ? " " : rs.getString("TOTAL_QTY"));
				data.put("QUANTITY_SHIPPED", rs.getString("QUANTITY_SHIPPED") == null ? " " : rs.getString("QUANTITY_SHIPPED"));
				data.put("PACKAGING", rs.getString("PACKAGING") == null ? " " : rs.getString("PACKAGING"));
				data.put("HAZARDOUS", rs.getString("HAZARDOUS") == null ? " " : rs.getString("HAZARDOUS"));
				data.put("DOT", rs.getString("DOT") == null ? " " : rs.getString("DOT"));
				data.put("ITEM_ID", rs.getString("ITEM_ID") == null ? " " : rs.getString("ITEM_ID"));
				data.put("MFG_LOT", rs.getString("MFG_LOT") == null ? " " : rs.getString("MFG_LOT"));
				data.put("EXPIRE_DATE", rs.getString("EXPIRE_DATE") == null ? " " : rs.getString("EXPIRE_DATE"));
				data.put("PR_NUMBER", rs.getString("PR_NUMBER") == null ? " " : rs.getString("PR_NUMBER"));
				data.put("LINE_ITEM", rs.getString("LINE_ITEM") == null ? " " : rs.getString("LINE_ITEM"));
				data.put("MR_LINE", rs.getString("MR_LINE") == null ? " " : rs.getString("MR_LINE"));
				data.put("RECEIPT_ID", rs.getString("RECEIPT_ID") == null ? " " : rs.getString("RECEIPT_ID"));
				data.put("SHIPPER_ID", rs.getString("SHIPPER_ID") == null ? " " : rs.getString("SHIPPER_ID"));
				data.put("CARRIER", rs.getString("CARRIER") == null ? " " : rs.getString("CARRIER"));
				data.put("CARRIER_NO", rs.getString("CARRIER_NO") == null ? " " : rs.getString("CARRIER_NO"));
				data.put("REQUIRED_DATETIME", rs.getString("REQUIRED_DATETIME") == null ? " " : rs.getString("REQUIRED_DATETIME"));
				data.put("SHIP_DATE", rs.getString("SHIP_DATE") == null ? " " : rs.getString("SHIP_DATE"));
				data.put("END_USER", rs.getString("END_USER") == null ? " " : rs.getString("END_USER"));
				data.put("DEPARTMENT", rs.getString("DEPARTMENT") == null ? " " : rs.getString("DEPARTMENT"));
				data.put("APPLICATION_DESC", rs.getString("APPLICATION_DESC") == null ? " " : rs.getString("APPLICATION_DESC"));
				data.put("DELIVERY_POINT", rs.getString("DELIVERY_POINT_DESC") == null ? " " : rs.getString("DELIVERY_POINT_DESC"));
				data.put("BATCH", rs.getString("BATCH") == null ? " " : rs.getString("BATCH"));
				data.put("SHIP_TO_COUNTRY_ABBREV", rs.getString("SHIP_TO_COUNTRY_ABBREV") == null ? " " : rs.getString("SHIP_TO_COUNTRY_ABBREV")); //
				data.put("SHIP_TO_STATE_ABBREV", rs.getString("SHIP_TO_STATE_ABBREV") == null ? " " : rs.getString("SHIP_TO_STATE_ABBREV"));
				String shipToLocDesc = rs.getString("SHIP_TO_LOCATION_DESC") == null ? " " : rs.getString("SHIP_TO_LOCATION_DESC");
				String companyId = rs.getString("COMPANY_ID") == null ? " " : rs.getString("COMPANY_ID");
				if (shipToLocDesc.trim().length() == 0) {
					data.put("SHIP_TO_LOCATION_DESC", rs.getString("SHIP_TO_ADDRESS_LINE_1") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_1"));
					data.put("SHIP_TO_ADDRESS_LINE_1", rs.getString("SHIP_TO_ADDRESS_LINE_2") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_2"));
					data.put("SHIP_TO_ADDRESS_LINE_2", rs.getString("SHIP_TO_ADDRESS_LINE_3") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_3"));
					data.put("SHIP_TO_ADDRESS_LINE_3", rs.getString("SHIP_TO_ADDRESS_LINE_3") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_3"));
				}
				else {
					data.put("SHIP_TO_LOCATION_DESC", shipToLocDesc);
					data.put("SHIP_TO_ADDRESS_LINE_1", rs.getString("SHIP_TO_ADDRESS_LINE_1") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_1"));
					data.put("SHIP_TO_ADDRESS_LINE_2", rs.getString("SHIP_TO_ADDRESS_LINE_2") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_2"));
					data.put("SHIP_TO_ADDRESS_LINE_3", rs.getString("SHIP_TO_ADDRESS_LINE_3") == null ? " " : rs.getString("SHIP_TO_ADDRESS_LINE_3"));
				}
				data.put("SHIP_TO_CITY", rs.getString("SHIP_TO_CITY") == null ? " " : rs.getString("SHIP_TO_CITY"));
				data.put("SHIP_TO_ZIP", rs.getString("SHIP_TO_ZIP") == null ? " " : rs.getString("SHIP_TO_ZIP"));
				data.put("HUB_COUNTRY_ABBREV", rs.getString("HUB_COUNTRY_ABBREV") == null ? " " : rs.getString("HUB_COUNTRY_ABBREV")); //
				data.put("HUB_STATE_ABBREV", rs.getString("HUB_STATE_ABBREV") == null ? " " : rs.getString("HUB_STATE_ABBREV"));
				data.put("HUB_ADDRESS_LINE_1", rs.getString("HUB_ADDRESS_LINE_1") == null ? " " : rs.getString("HUB_ADDRESS_LINE_1"));
				data.put("HUB_ADDRESS_LINE_2", rs.getString("HUB_ADDRESS_LINE_2") == null ? " " : rs.getString("HUB_ADDRESS_LINE_2"));
				data.put("HUB_ADDRESS_LINE_3", rs.getString("HUB_ADDRESS_LINE_3") == null ? " " : rs.getString("HUB_ADDRESS_LINE_3"));
				data.put("HUB_CITY", rs.getString("HUB_CITY") == null ? " " : rs.getString("HUB_CITY"));
				data.put("HUB_ZIP", rs.getString("HUB_ZIP") == null ? " " : rs.getString("HUB_ZIP"));
				data.put("HUB_LOCATION_DESC", rs.getString("HUB_LOCATION_DESC") == null ? " " : rs.getString("HUB_LOCATION_DESC"));
				data.put("PO_NUMBER", rs.getString("PO_NUMBER") == null ? " " : rs.getString("PO_NUMBER"));
				data.put("PO_RELEASE_NUMBER", rs.getString("PO_RELEASE_NUMBER") == null ? " " : rs.getString("PO_RELEASE_NUMBER"));
				data.put("CAT_PART_NO", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
				data.put("CATALOG_ID", rs.getString("CATALOG_ID") == null ? " " : rs.getString("CATALOG_ID"));
				data.put("ITEM_DESC", rs.getString("ITEM_DESC") == null ? " " : rs.getString("ITEM_DESC"));
				data.put("CHARGE_NUMBER", rs.getString("CHARGE_NUMBER") == null ? " " : rs.getString("CHARGE_NUMBER"));

				if (companyId.equalsIgnoreCase("USGOV")) {
					data.put("NET_WEIGHT", rs.getString("GROSS_WEIGHT_LB") == null ? " " : rs.getString("GROSS_WEIGHT_LB"));
				}
				else {
					data.put("NET_WEIGHT", "");
				}
				data.put("COMPANY_ID", companyId);
				data.put("OCONUS", rs.getString("OCONUS") == null ? " " : rs.getString("OCONUS"));
				data.put("BILL_TO_ADDRESS_1", rs.getString("BILL_TO_ADDRESS_1") == null ? " " : rs.getString("BILL_TO_ADDRESS_1"));
				data.put("BILL_TO_ADDRESS_2", rs.getString("BILL_TO_ADDRESS_2") == null ? " " : rs.getString("BILL_TO_ADDRESS_2"));
				data.put("BILL_TO_ADDRESS_3", rs.getString("BILL_TO_ADDRESS_3") == null ? " " : rs.getString("BILL_TO_ADDRESS_3"));
				data.put("BILL_TO_ADDRESS_4", rs.getString("BILL_TO_ADDRESS_4") == null ? " " : rs.getString("BILL_TO_ADDRESS_4"));
				data.put("TRACKING_NUMBER", rs.getString("TRACKING_NUMBER") == null ? " " : rs.getString("TRACKING_NUMBER"));
				data.put("FREIGHT_ORDER_NUMBER", rs.getString("FREIGHT_ORDER_NUMBER") == null ? " " : rs.getString("FREIGHT_ORDER_NUMBER"));
				data.put("SHIP_TO_ADDRESS", rs.getString("SHIP_TO_ADDRESS") == null ? " " : rs.getString("SHIP_TO_ADDRESS"));
				data.put("INVENTORY_GROUP", rs.getString("INVENTORY_GROUP") == null ? " " : rs.getString("INVENTORY_GROUP"));
				data.put("NUMBER_OF_BOXES", rs.getString("NUMBER_OF_BOXES") == null ? " " : rs.getString("NUMBER_OF_BOXES"));

				resultF.addElement(data);
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

		Vector newdotvec = getBolDotData(dbObj, resultF);
		resultF = newdotvec;

		return resultF;
	}

	public static Vector getBolDotData(TcmISDBModel dbObj, Vector boldata) {
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
		Vector new_data = new Vector();
		DecimalFormat netwtTotal = new DecimalFormat("####.000");
		try {
			for (int i = 0; i < boldata.size(); i++) {
				Hashtable temp = (Hashtable) (boldata.elementAt(i));
				String item_id = (temp.get("ITEM_ID") == null ? "" : temp.get("ITEM_ID").toString());
				String dotname = (temp.get("DOT") == null ? "" : temp.get("DOT").toString());
				String hazardous = (temp.get("HAZARDOUS") == null ? "" : temp.get("HAZARDOUS").toString());
				String packaging = (temp.get("PACKAGING") == null ? "" : temp.get("PACKAGING").toString());
				String totalqty = (temp.get("TOTAL_QTY") == null ? "" : temp.get("TOTAL_QTY").toString());
				String netweight = (temp.get("NET_WEIGHT") == null ? "" : temp.get("NET_WEIGHT").toString());
				String oconus = (temp.get("OCONUS") == null ? "" : temp.get("OCONUS").toString());
				String companyId = (temp.get("COMPANY_ID") == null ? "" : temp.get("COMPANY_ID").toString());

				float toatQty = 0;
				if (totalqty.trim().length() > 0) {
					try {
						toatQty = Float.parseFloat(totalqty);
					}
					catch (NumberFormatException ex) {
						toatQty = 0;
					}
				}

				try {
					//String query=" select * from global.material m where material_Id in (select MATERIAL_ID from global.part where item_id = " + item_id + ")";
					if (item_id.trim().length() > 0) {
						String query = " select * from part_dot_view where item_id = " + item_id + " order by HAZARDOUS";

						dbrs = dbObj.doQuery(query);
						searchRs = dbrs.getResultSet();
						int count = 0;

						while (searchRs.next()) {
							//String ITEM_ID = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
							String tmpPACKAGING = searchRs.getString("PACKAGING") == null ? "" : searchRs.getString("PACKAGING");
							String tmpHAZARDOUS = searchRs.getString("HAZARDOUS") == null ? "" : searchRs.getString("HAZARDOUS");
							String tempdotname = searchRs.getString("DOT") == null ? "" : searchRs.getString("DOT");
							String tmpIataHazardous = searchRs.getString("IATA_HAZARDOUS") == null ? "" : searchRs.getString("IATA_HAZARDOUS");
							String tempIataIdentification = searchRs.getString("IATA_IDENTIFICATION") == null ? "" : searchRs.getString("IATA_IDENTIFICATION");
							//String ERG=searchRs.getString( "ERG" ) == null ? "" : searchRs.getString( "ERG" ) );
							//String MATERIAL_DESC=searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
							//String ITEM_DESC=searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
							String tmpnetweightlb = searchRs.getString("NET_WEIGHT_LB") == null ? "" : searchRs.getString("NET_WEIGHT_LB");
							//String ORM_D=searchRs.getString( "ORM_D" ) == null ? "" : searchRs.getString( "ORM_D" ) );
							//String BULK_PKG_MARINE_POLLUTANT=searchRs.getString( "BULK_PKG_MARINE_POLLUTANT" ) == null ? "" : searchRs.getString( "BULK_PKG_MARINE_POLLUTANT" ) );

							float netwtLb = 0;
							if (tmpnetweightlb.trim().length() > 0) {
								try {
									netwtLb = Float.parseFloat(tmpnetweightlb);
								}
								catch (NumberFormatException ex) {
									netwtLb = 0;
								}
							}
							float totalnetwt = 0;
							totalnetwt = netwtLb * toatQty;

							if (oconus.equalsIgnoreCase("Y")) {
								tempdotname = tempIataIdentification;
								tmpHAZARDOUS = tmpIataHazardous;
							}

							//String tempdotname=DOT;
							if (count > 0) {
								dotname += "\n\n" + tempdotname;
							}
							else {
								dotname = tempdotname;
							}

							if (count > 0) {
								hazardous += "\n\n" + tmpHAZARDOUS;
							}
							else {
								hazardous = tmpHAZARDOUS;
							}

							if (count > 0) {
								packaging += "\n\n" + tmpPACKAGING;
							}
							else {
								packaging = tmpPACKAGING;
							}

							if (count > 0) {
								netweight += "\n\n" + "" + netwtTotal.format(totalnetwt) + "";
							}
							else {
								netweight = "" + netwtTotal.format(totalnetwt) + "";
							}

							count++;
						}
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

				temp.remove("DOT");
				temp.put("DOT", dotname);

				temp.remove("HAZARDOUS");
				temp.put("HAZARDOUS", hazardous);

				temp.remove("PACKAGING");
				temp.put("PACKAGING", packaging);

				if (!companyId.equalsIgnoreCase("USGOV")) {
					temp.remove("NET_WEIGHT");
					temp.put("NET_WEIGHT", netweight);
				}
				new_data.addElement(temp);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new_data;
	}

	public static Vector getBolinvenData(TcmISDBModel dbObj, Vector boldata, String branchplant) {
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
		Vector new_inv_data = new Vector();
		Vector new_data = new Vector();
		int totalqty = 0;
		int count = 0;

		try {
			for (int i = 0; i < boldata.size(); i++) {
				Hashtable temp = (Hashtable) (boldata.elementAt(i));
				String item_id = (temp.get("ITEM_ID") == null ? "" : temp.get("ITEM_ID").toString());
				totalqty = 0;
				count = 0;
				new_inv_data = new Vector();

				try {
					String user_query = "select x.* from (select b.quantity_received,b.INVENTORY_GROUP,b.LOT_STATUS_ROOT_CAUSE, b.RESPONSIBLE_COMPANY_ID, b.LAST_MODIFIED_BY, b.LOT_STATUS_ROOT_CAUSE_NOTES,b.LOT_STATUS_AGE,b.NOTES,b.owner_company_id,a.item_id,b.hub,b.RECERT_NUMBER,to_char(b.LAST_LABEL_PRINT_DATE,'dd Mon yyyy hh24:mi') LAST_PRINT_DATE,a.item_desc,lower (to_char(b.item_id) ||' '|| a.item_desc ||' '|| b.bin ||' '|| b.receipt_id ||' '|| b.mfg_lot ||' '|| b.LOT_STATUS) search, ";
					user_query += "b.LOT_STATUS, b.bin, b.MFG_LOT, b.receipt_id, b.quantity_received - nvl(b.quantity_issued,0) quantity, ";
					user_query += "b.quantity_received - nvl(b.quantity_issued,0) no_of_labels,b.EXPIRE_DATE EXPIRE_DATE1,decode(b.EXPIRE_DATE,null,'INDEFINITE',to_char(b.EXPIRE_DATE,'mm/dd/yyyy')) EXPIRE_DATE from item a , simple_inventory_view b ";
					user_query += "where a.item_id = b.item_id and b.quantity_received - nvl(b.quantity_issued,0) <> 0) x where ";
					user_query += "x.HUB = " + branchplant + " and x.item_id= " + item_id + " order by x.bin ";

					dbrs = dbObj.doQuery(user_query);
					searchRs = dbrs.getResultSet();

					while (searchRs.next()) {
						Hashtable DataInv = new Hashtable();

						String quantity = searchRs.getString("QUANTITY") == null ? "" : searchRs.getString("QUANTITY");
						int qty = Integer.parseInt(quantity);
						totalqty = totalqty + qty;

						DataInv.put("QUANTITY_RECEIVED", searchRs.getString("QUANTITY_RECEIVED") == null ? "" : searchRs.getString("QUANTITY_RECEIVED"));
						DataInv.put("INVENTORY_GROUP", searchRs.getString("INVENTORY_GROUP") == null ? "" : searchRs.getString("INVENTORY_GROUP"));
						DataInv.put("ITEM_ID", searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID"));
						DataInv.put("HUB", searchRs.getString("HUB") == null ? "" : searchRs.getString("HUB"));
						DataInv.put("RECERT_NUMBER", searchRs.getString("RECERT_NUMBER") == null ? "" : searchRs.getString("RECERT_NUMBER"));
						DataInv.put("LAST_PRINT_DATE", searchRs.getString("LAST_PRINT_DATE") == null ? "" : searchRs.getString("LAST_PRINT_DATE"));
						DataInv.put("LOT_STATUS", searchRs.getString("LOT_STATUS") == null ? "" : searchRs.getString("LOT_STATUS"));
						DataInv.put("BIN", searchRs.getString("BIN") == null ? "" : searchRs.getString("BIN"));
						DataInv.put("MFG_LOT", searchRs.getString("MFG_LOT") == null ? "" : searchRs.getString("MFG_LOT"));
						DataInv.put("RECEIPT_ID", searchRs.getString("RECEIPT_ID") == null ? "" : searchRs.getString("RECEIPT_ID"));
						DataInv.put("QUANTITY", searchRs.getString("QUANTITY") == null ? "" : searchRs.getString("QUANTITY"));
						DataInv.put("EXPIRE_DATE1", searchRs.getString("EXPIRE_DATE1") == null ? "" : searchRs.getString("EXPIRE_DATE1"));
						DataInv.put("EXPIRE_DATE", searchRs.getString("EXPIRE_DATE") == null ? "" : searchRs.getString("EXPIRE_DATE"));
						new_inv_data.add(DataInv);

						count++;
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

				for (int kk = count; kk < 18; kk++) {
					Hashtable DataInv = new Hashtable();
					DataInv.put("QUANTITY_RECEIVED", "");
					DataInv.put("INVENTORY_GROUP", "");
					DataInv.put("ITEM_ID", item_id);
					DataInv.put("HUB", "");
					DataInv.put("RECERT_NUMBER", "");
					DataInv.put("LAST_PRINT_DATE", "");
					DataInv.put("LOT_STATUS", "");
					DataInv.put("BIN", "");
					DataInv.put("MFG_LOT", "");
					DataInv.put("RECEIPT_ID", "");
					DataInv.put("QUANTITY", "");
					DataInv.put("EXPIRE_DATE1", "");
					DataInv.put("EXPIRE_DATE", "");
					new_inv_data.add(DataInv);
				}

				temp.put("INVDATA", new_inv_data);
				if (!(count == 0)) {
					temp.remove("TOTAL_QTY");
					temp.put("TOTAL_QTY", "" + totalqty + "");
				}

				new_data.addElement(temp);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new_data;
	}

	public static void printDatabaseHas(ResultSet rs) {
		//Get Column Name and put them in a hashtable
		try {
			ResultSetMetaData rsMeta = rs.getMetaData();
			for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
				System.out.println("data.put(\"" + rsMeta.getColumnName(i).toString() + "\",rs.getString(\"" + rsMeta.getColumnName(i).toString() + "\")==null?\" \":rs.getString(\"" + rsMeta.getColumnName(i).toString() + "\"));");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {

		}
	}

	public static Vector getbuyernames(TcmISDBModel dbObj) {
		String query = "select distinct a.PERSONNEL_ID, b.first_name ||' '|| b.last_name Full_name from customer.user_group_member a, customer.personnel b where a.personnel_id = b.personnel_id and user_group_id = 'BuyOrder' and b.company_id = 'Radian' order by Full_name";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String faci = (rs.getString("PERSONNEL_ID") == null ? "" : rs.getString("PERSONNEL_ID"));
				String facn = (rs.getString("Full_name") == null ? "" : rs.getString("Full_name"));
				result.put(facn, faci);
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
		return ResultV;
	}

	public static Vector getLorClerknames(TcmISDBModel dbObj) {
		String query = "select distinct a.PERSONNEL_ID, b.first_name ||' '|| b.last_name Full_name from customer.user_group_member a, customer.personnel b where a.personnel_id = b.personnel_id and user_group_id ='Acountspayable' and b.company_id = 'Radian' order by Full_name";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable result = null;
		Vector ResultV = new Vector();
		try {
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			while (rs.next()) {
				result = new Hashtable();
				String faci = (rs.getString("PERSONNEL_ID") == null ? "" : rs.getString("PERSONNEL_ID"));
				String facn = (rs.getString("Full_name") == null ? "" : rs.getString("Full_name"));
				result.put(facn, faci);
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
		return ResultV;
	}

	public static String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue) {
		/*if (cookies != null)
		{
		for ( int i=0; i < cookies.length; i++ )
		{
		Cookie cookie=cookies[i];
		System.out.println( "Servlet Name : " + cookieName + " Cookie Value : " + cookie.getValue() + "" );
		if ( cookie != null )
		{
		System.out.println( "Cookie Domain: " + cookie.getDomain() + "" );
		System.out.println( "Cookie Name: " + cookie.getName() + "" );
		System.out.println( "Cookie Path: " + cookie.getPath() + "" );
		System.out.println( "Cookie Secure: " + cookie.getSecure() + "" );
		System.out.println( "Cookie Comment: " + cookie.getComment() + "" );
		}
		if ( cookieName.equals( cookie.getName() ) )
		return ( cookie.getValue() );
		}
		}*/

		return (defaultValue);
	}

	public static String destroyCookies(Cookie[] cookies) {
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie != null) {
					cookie.setMaxAge(0);
				}

			}
		}
		return "";
	}

	public static StringBuffer labelredirection(String redurl) {
		StringBuffer MsgSB = new StringBuffer();
		if (redurl.length() > 1) {
			MsgSB.append("<HTML><HEAD>\n");
			MsgSB.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + redurl + "\">\n");
			MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
			MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
			MsgSB.append("<TITLE>Label Generator</TITLE>\n");
			MsgSB.append("</HEAD>  \n");
			MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
			MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Labels</b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b><BR><BR>You May Close this Window after Printing the Labels</b></font><P></P>\n");
			MsgSB.append("</CENTER>\n");
			MsgSB.append("</BODY></HTML>    \n");
		}
		else {
			MsgSB.append("<HTML><HEAD>\n");
			MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
			MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
			MsgSB.append("<TITLE>Label Generator</TITLE>\n");
			MsgSB.append("</HEAD>  \n");
			MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
			MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>An Error Occured Producing Labels</b></font><P></P><BR>\n");
			MsgSB.append("</CENTER>\n");
			MsgSB.append("</BODY></HTML>    \n");
		}
		return MsgSB;
	}

	public static String hiddenPrintTxtFile (String txtFileUrl) {
		StringBuffer html = new StringBuffer();
		html.append("<html>\n");
		html.append("<head>\n");
		html.append("<title> Printing TXT file</title>\n");
		html.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE4\">\n");
		html.append("<script type='text/javascript' src='/js/common/cabinet/clientcabinetlabelprint.js'></script>\n");
		html.append("</head>\n");
		html.append("<body onLoad='initPage();'>\n");
		html.append("<iframe id=\"hiddenPrintTxtFrame\" name=\"hiddenPrintTxtFrame\" width=\"0\" height=\"0\" frameborder=\"0\" marginwidth=\"0\" src=\"");		
		html.append(txtFileUrl.replaceFirst("http.*?//.*?/", "/"));
		html.append("\" onLoad=\"finishPrinting();\"></iframe>\n");
		html.append("</body>\n");
		html.append("</html>");
		return html.toString();
	}

	public static Vector CreateShipmentForIssues(TcmISDBModel dbObj, String shipToLocationId, String shipToCompanyId) throws Exception {
		//String query="select distinct s.shipment_id from shipment s , issue iss where iss.shipment_id=s.shipment_id and iss.SHIP_CONFIRM_DATE is null and ";
		//query += "s.SHIP_TO_LOCATION_ID='"+shipToLocationId+"' and s.COMPANY_ID='"+shipToCompanyId+"' ";
		//String query="select  s.shipment_id from shipment s  where exists ( select 'x' from issue iss where iss.shipment_id=s.shipment_id and iss.SHIP_CONFIRM_DATE is null ) ";
		//query += "and s.SHIP_TO_LOCATION_ID='"+shipToLocationId+"' and s.COMPANY_ID='"+shipToCompanyId+"' ";
		String query = "select  s.shipment_id from shipment s  where SHIP_CONFIRM_DATE is null ";
		query += "and s.SHIP_TO_LOCATION_ID='" + shipToLocationId + "' and s.COMPANY_ID='" + shipToCompanyId + "' ";

		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector shipmentIdV = new Vector();

		try {
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			int i = 0;
			while (rs.next()) {
				String temp = rs.getString("shipment_id") == null ? "" : rs.getString("shipment_id");
				shipmentIdV.addElement(temp);
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

		return shipmentIdV;
	}

	public static Hashtable CreateBinForItem(TcmISDBModel dbObj, String item_id, String branch_plant) throws Exception {
		//String query="select distinct BIN,TRANSACTION_DATE from receipt where item_id = " + item_id + " and hub = " + branch_plant + " order by nvl(TRANSACTION_DATE,to_date('01/01/1900','/mm/dd/yyyy')) desc";
		String query = "select BIN from receipt_item_prior_bin_view where status = 'A' and item_id = " + item_id + " and hub = '" + branch_plant + "'";

		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable bin_for_item = new Hashtable();
		boolean bin_added = false;
		Vector testbin = new Vector();

		try {
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();
			int i = 0;
			while (rs.next()) {
				String temp = rs.getString("BIN") == null ? "BLANK" : rs.getString("BIN");
				if (!temp.equalsIgnoreCase("BLANK") && !testbin.contains(temp)) {
					bin_for_item.put(new Integer(i), temp);
					i++;
					bin_added = true;
				}
				testbin.addElement(temp);
			}
			if (false == bin_added) {
				bin_for_item.put(new Integer(0), "NONE");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			bin_for_item = null;
			throw e;
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
		testbin = null;
		return bin_for_item;
	}

	public static boolean insreceiptcomp(TcmISDBModel dbObj, Hashtable hD, String receiptID, String loginID) {
		boolean result = false;
		String errorcode = "";
		Connection connect1;
		CallableStatement cs = null;

		String Item_id = hD.get("ITEM_ID") == null ? " " : hD.get("ITEM_ID").toString();
		String compID = hD.get("COMPONENT_ID") == null ? " " : hD.get("COMPONENT_ID").toString();
		String Mfg_lot = hD.get("MFG_LOT") == null ? " " : hD.get("MFG_LOT").toString();
		String Sel_bin = hD.get("BIN_NAME") == null ? " " : hD.get("BIN_NAME").toString();
		String Expiry_Date = hD.get("EXPIRE_DATE") == null ? " " : hD.get("EXPIRE_DATE").toString();
		String dateOfRepackaging = hD.get("DATE_OF_REPACKAGING") == null ? " " : hD.get("DATE_OF_REPACKAGING").toString();
		if ("Indefinite".equalsIgnoreCase(Expiry_Date)) {
			Expiry_Date = "01/01/3000";
		}
        String mfgLabelExpireDate = hD.get("MFG_LABEL_EXPIRE_DATE") == null ? " " : hD.get("MFG_LABEL_EXPIRE_DATE").toString();
        if ("Indefinite".equalsIgnoreCase(mfgLabelExpireDate)) {
            mfgLabelExpireDate = "01/01/3000";
        }

		//String invengrp= hD.get( "INVENTORY_GROUP" ) == null ? " " : hD.get( "INVENTORY_GROUP" ).toString();

		try {
			connect1 = dbObj.getConnection();
			try {
				cs = connect1.prepareCall("{call p_receipt_component(?,?,?,?,?,?,?,?)}");
				cs.setInt(1, Integer.parseInt(receiptID));
				cs.setInt(2, Integer.parseInt(Item_id));
				cs.setInt(3, Integer.parseInt(compID));
				if (Mfg_lot.trim().length() > 0) {
					cs.setString(4, Mfg_lot);
				}
				else {
					cs.setNull(4, java.sql.Types.VARCHAR);
				}
				if (Sel_bin.trim().length() > 0) {
					cs.setString(5, Sel_bin);
				}
				else {
					cs.setNull(5, java.sql.Types.VARCHAR);
				}
				if (Expiry_Date.length() > 1) {
					cs.setTimestamp(6, radian.web.HTMLHelpObj.getDateFromString("", Expiry_Date));
				}
				else {
					cs.setNull(6, java.sql.Types.DATE);
				}
                if (mfgLabelExpireDate.length() > 1) {
					cs.setTimestamp(7, radian.web.HTMLHelpObj.getDateFromString("", mfgLabelExpireDate));
				}
				else {
					cs.setNull(7, java.sql.Types.DATE);
				}
                if (dateOfRepackaging.length() > 1) {
					cs.setTimestamp(8, radian.web.HTMLHelpObj.getDateFromString("", dateOfRepackaging));
				}
				else {
					cs.setNull(8, java.sql.Types.DATE);
				}

                //cs.registerOutParameter(21,java.sql.Types.VARCHAR);
				cs.execute();

				//errorcode = BothHelpObjs.makeBlankFromNull(cs.getString(21));
				result = true;
			}
			catch (Exception e) {
				e.printStackTrace();
				result = false;
				radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling Procedure call p_receipt_component in HubReceiving", "Error occured while running call p_receipt_component \nError Msg:\n" + e.getMessage()
						+ "\n\n Parameters passed P_receipt_INSERT receiptID " + receiptID + "\n Item_id " + Item_id + "\n Mfg_lot " + Mfg_lot + "\n Bin " + Sel_bin + "\n Expiry_Date " + Expiry_Date + "  personnel Id " + loginID
						+ "\n");
				throw e;
			}
			finally {
				connect1.commit();
				if (cs != null) {
					try {
						cs.close();
					}
					catch (Exception se) {
						se.printStackTrace();
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	public static Vector createEmptyBinSet(TcmISDBModel dbObj, String Hub) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector bin_set = new Vector();

		try {
			String query = "select * from vv_hub_bins where branch_plant = '" + Hub + "' and STATUS = 'A' order by BIN";
			dbrs = dbObj.doQuery(query);
			rs = dbrs.getResultSet();

			while (rs.next()) {
				String temp = rs.getString("BIN") == null ? "NONE" : rs.getString("BIN");
				bin_set.addElement(temp);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		return bin_set;
	}

	public static String gethubNamefromBP(Hashtable hub_list_set, String selHubnum) {
		Hashtable hubsetdetails = (Hashtable) (hub_list_set.get("HUB_LIST"));
		String hubname = "";
		for (Enumeration e = hubsetdetails.keys(); e.hasMoreElements();) {
			String branchplant = (String) (e.nextElement());
			String hub_name = (String) (hubsetdetails.get(branchplant));
			if (branchplant.equalsIgnoreCase(selHubnum)) {
				hubname = hub_name;
				break;
			}
		}
		return hubname;
	}

	public static String gethubfromname(TcmISDBModel dbObjh, String Hubname) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String HubNum = "";
		String Query_Statement = "Select distinct BRANCH_PLANT from HUB where HUB_NAME = '" + Hubname + "'";

		try {
			dbrs = dbObjh.doQuery(Query_Statement);
			rs = dbrs.getResultSet();

			while (rs.next()) {
				HubNum = rs.getString("BRANCH_PLANT") == null ? " " : rs.getString("BRANCH_PLANT");
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}
		return HubNum;
	}

	public static boolean isBlank(String testforblank) {
		boolean isblank = false;

		if (testforblank == null) {
			isblank = true;
		}
		else if (testforblank.trim().length() == 0) {
			isblank = true;
		}
		return isblank;
	}

	/**
	 * <p>Wrap text at a certain line length. </p>
	 *
	 * <pre>
	 * StringUtil.wrap("Hello World!, "<br>", 9) = Hello<br>World!
	 * StringUtil.wrap("1234512345", "<br>", 5) = 12345<br>12345
	 * StringUtil.wrap("12345", 10) = 12345
	 *</pre>
	 *
	 * @param string The String
	 * @param lineSeparator Line break
	 * @param wrapLength The position to create a line break
	 *
	 * @return String
	 */
	public static String wrap(String string, String lineSeparator, int wrapLength) {
		// Null or blank string should return an empty ("") string
		if (isBlank(string)) {
			return "";
		}

		// Wrap length got to be >= 1
		if (wrapLength < 1) {
			return string;
		}

		// Default to HTML line break since web app is client
		if (isBlank(lineSeparator)) {
			lineSeparator = "<br>";
		}

		int stringLength = string.length();
		StringBuffer sb = new StringBuffer(stringLength);

		if (stringLength > wrapLength) {
			int beginIndex = 0, endIndex = wrapLength;
			boolean done = false;

			while (endIndex <= stringLength) {
				/*if ( !done && string.charAt( endIndex - 1 ) != ' '
				&& string.lastIndexOf( ' ',endIndex ) > -1 )
				{
				endIndex=string.lastIndexOf( ' ',endIndex );
				}*/
				sb.append(string.substring(beginIndex, endIndex).trim());

				beginIndex = endIndex;
				endIndex = beginIndex + wrapLength;

				if (!done) {
					sb.append(lineSeparator);

					if (endIndex >= stringLength) {
						endIndex = stringLength;
						done = true;
					}
				}
			}
		}
		else {
			sb.append(string);
		}

		return sb.toString();
	}

	public static Hashtable getcabinetInitialData(TcmISDBModel cabdb, Hashtable hublist) throws Exception {
		DBResultSet dbrs = null;
		ResultSet rs = null;

		Hashtable hubsetdetails = (Hashtable) (hublist.get("HUB_LIST"));
		String allowedhubs = "";
		for (Enumeration e = hubsetdetails.keys(); e.hasMoreElements();) {
			String branch_plant = (String) (e.nextElement());
			String hub_name = (String) (hubsetdetails.get(branch_plant));
			allowedhubs += "'" + hub_name + "',";
		}
		allowedhubs = allowedhubs.substring(0, allowedhubs.length() - 1);

		String companyIdstg = "COMPANY_ID";
		if (!"tcm_ops".equalsIgnoreCase(cabdb.getClient())) {
			companyIdstg = "'" + cabdb.getClient().toUpperCase() + "' COMPANY_ID";
		}

		String query = "select PREFERRED_WAREHOUSE, " + companyIdstg + ", FACILITY_ID, FACILITY_NAME, APPLICATION, APPLICATION_DESC from hub_cabinet_view where PREFERRED_WAREHOUSE in (" + allowedhubs
		+ ") order by PREFERRED_WAREHOUSE,FACILITY_ID,APPLICATION_DESC";
		Hashtable resultdata = new Hashtable();
		Vector hubID = new Vector();
		Vector hubDesc = new Vector();
		Hashtable facCompanyData = new Hashtable();

		try {
			dbrs = cabdb.doQuery(query);
			rs = dbrs.getResultSet();

			String lastHub = "";

			while (rs.next()) {
				String tmpFacID = rs.getString("facility_id");
				String tmpHub = rs.getString("PREFERRED_WAREHOUSE");
				String tmpCompanyID = rs.getString("COMPANY_ID");

				if (!tmpHub.equalsIgnoreCase(lastHub)) {
					//hub info
					hubID.addElement(tmpHub);
					hubDesc.addElement(tmpHub);

					Hashtable fh = new Hashtable();
					//facility data
					Vector facID = new Vector();
					Vector facDesc = new Vector();
					facID.addElement(tmpFacID);
					facDesc.addElement(rs.getString("facility_name"));
					facCompanyData.put(tmpFacID, tmpCompanyID);
					Hashtable h = new Hashtable();
					//application data
					Vector application = new Vector();
					Vector applicationDesc = new Vector();
					application.addElement(rs.getString("application"));
					applicationDesc.addElement(rs.getString("application_desc"));
					h.put("APPLICATION", application);
					h.put("APPLICATION_DESC", applicationDesc);
					fh.put(tmpFacID, h);
					//putting them all together
					fh.put("FACILITY_ID", facID);
					fh.put("FACILITY_DESC", facDesc);
					//resultdata.put( "DATA",fh );
					resultdata.put(tmpHub, fh);
				}
				else { //hub already exist
					Hashtable fh = (Hashtable) resultdata.get(tmpHub);
					Vector facID = (Vector) fh.get("FACILITY_ID");
					Vector facDesc = (Vector) fh.get("FACILITY_DESC");
					if (!facID.contains(tmpFacID)) {
						facID.addElement(tmpFacID);
						facDesc.addElement(rs.getString("facility_name"));
						facCompanyData.put(tmpFacID, tmpCompanyID);
						Hashtable h = new Hashtable(3);
						Vector application = new Vector();
						Vector applicationDesc = new Vector();
						application.addElement(rs.getString("application"));
						applicationDesc.addElement(rs.getString("application_desc"));
						h.put("APPLICATION", application);
						h.put("APPLICATION_DESC", applicationDesc);
						fh.put(tmpFacID, h);
					}
					else {
						Hashtable h = (Hashtable) fh.get(tmpFacID);
						Vector application = (Vector) h.get("APPLICATION");
						Vector applicationDesc = (Vector) h.get("APPLICATION_DESC");
						if (!application.contains(rs.getString("application"))) {
							application.addElement(rs.getString("application"));
							applicationDesc.addElement(rs.getString("application_desc"));
						}
						h.put("APPLICATION", application);
						h.put("APPLICATION_DESC", applicationDesc);
						fh.put(tmpFacID, h);
					}
					fh.put("FACILITY_ID", facID);
					fh.put("FACILITY_DESC", facDesc);
					resultdata.put(tmpHub, fh);
				}
				lastHub = tmpHub;
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		resultdata.put("HUB_ID", hubID);
		resultdata.put("HUB_DESC", hubDesc);
		resultdata.put("FAC_COMP_DATA", facCompanyData);
		return resultdata;
	}

	public static StringBuffer getcablist(String hubSel, String facSelected, String waSelected, TcmISDBModel cabdb, HttpSession cabsession) {
		StringBuffer Msgn = new StringBuffer();
		StringBuffer Msgbody = new StringBuffer();
		Hashtable result = null;
		DBResultSet dbrs = null;
		ResultSet searchRs = null;

		PersonnelBean personnelBean = (PersonnelBean) cabsession.getAttribute("personnelBean");
		boolean intcmIsApplication = false;
		if (personnelBean != null) {
			Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
				intcmIsApplication = true;
			}
		}
		Msgn.append(radian.web.HTMLHelpObj.printHTMLHeader("Get Bins", "cabinetlabels.js", intcmIsApplication));
		Msgn.append("</HEAD>\n");

		Msgbody.append("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n");

		StringBuffer Msgtemp = new StringBuffer();

		//Build the Java Script Here and Finish the thing
		Msgtemp.append("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
		Msgtemp.append("<!--\n");

		Msgtemp.append("function sendSupplierData()\n");
		Msgtemp.append("{\n");
		Msgtemp.append("opener.removeAllOptionItem(opener.document.getElementById(\"binids\"));\n");
		Msgtemp.append("opener.addOptionItem(opener.document.getElementById(\"binids\"),'All','All Cabinets');\n");
		Vector cabinetIdshown = new Vector();
		result = new Hashtable();
		result.put("All Cabinets", "All");
		cabinetIdshown.add(result);

		try {
			boolean falgforand = false;
			String binquery = "select * from hub_cabinet_view where ";

			if (facSelected.length() > 0 && (!"All Facilities".equalsIgnoreCase(facSelected))) {
				binquery += "FACILITY_ID='" + facSelected + "'";
				falgforand = true;
			}
			else if ("All Facilities".equalsIgnoreCase(facSelected)) {
				binquery += "PREFERRED_WAREHOUSE='" + hubSel + "'";
				falgforand = true;
			}

			if (!"All Work Areas".equalsIgnoreCase(waSelected)) {
				if (falgforand)
					binquery += " and APPLICATION = '" + waSelected + "'  ";
				else
					binquery += " APPLICATION = '" + waSelected + "'  ";
			}

			binquery += " order by CABINET_NAME";

			dbrs = cabdb.doQuery(binquery);
			searchRs = dbrs.getResultSet();

			while (searchRs.next()) {
				result = new Hashtable();

				String hazcode = searchRs.getString("CABINET_ID") == null ? "" : searchRs.getString("CABINET_ID");
				String hazcodedesc = searchRs.getString("CABINET_NAME") == null ? "" : searchRs.getString("CABINET_NAME");
				result.put(hazcodedesc, hazcode);
				cabinetIdshown.add(result);

				Msgtemp.append("opener.addOptionItem(opener.document.getElementById(\"binids\"),'" + hazcode + "','" + hazcodedesc + "');\n");
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

		Msgtemp.append("window.close();\n");
		Msgtemp.append(" }\n");

		Msgtemp.append("// -->\n</SCRIPT>\n");
		Msgn.append(Msgtemp);
		//Msgn.append("<BODY><BR><BR>\n");
		Msgn.append("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");

		Msgbody.append("<CENTER><BR><BR>\n");
		Msgbody
		.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
		Msgbody.append("</FORM></BODY></HTML>\n");
		Msgn.append(Msgbody);

		cabsession.setAttribute("CABIDS_SHOWN", cabinetIdshown);

		return Msgn;
	}

	public static String createComboBoxData(Hashtable initialData) {
		String tmp = "";
		String tmpHub = "var hubID = new Array(";
		String altFacID = "var altFacID = new Array();\n ";
		String altFacDesc = "var altFacDesc = new Array();\n ";
		String altWAID = "var altWAID = new Array();\n ";
		String altWADesc = "var altWADesc = new Array();\n ";
		int i = 0;
		Vector hubIDV = (Vector) initialData.get("HUB_ID");
		for (int ii = 0; ii < hubIDV.size(); ii++) {
			String hubID = (String) hubIDV.elementAt(ii);
			tmpHub += "\"" + hubID + "\"" + ",";

			Hashtable fh = (Hashtable) initialData.get(hubID);

			Vector facIDV = (Vector) fh.get("FACILITY_ID");
			Vector facDescV = (Vector) fh.get("FACILITY_DESC");
			if (facIDV.size() > 1 && !facIDV.contains("All Facilities")) {
				facIDV.insertElementAt("All Facilities", 0);
				facDescV.insertElementAt("All Facilities", 0);
				Vector wAreaID = new Vector(1);
				wAreaID.addElement("All Work Areas");
				Vector wAreaDesc = new Vector(1);
				wAreaDesc.addElement("All Work Areas");
				Vector tmpAcS = new Vector(1);
				tmpAcS.addElement("All Accounting Systems");
				Hashtable tmpFacWA = new Hashtable(3);
				tmpFacWA.put("APPLICATION", wAreaID);
				tmpFacWA.put("APPLICATION_DESC", wAreaDesc);
				tmpFacWA.put("ACCOUNTING_SYSTEM", tmpAcS);
				fh.put("All Facilities", tmpFacWA);
			}
			altFacID += "altFacID[\"" + hubID + "\"] = new Array(";
			altFacDesc += "altFacDesc[\"" + hubID + "\"] = new Array(";
			for (i = 0; i < facIDV.size(); i++) {
				String facID = (String) facIDV.elementAt(i);
				tmp += "\"" + facID + "\"" + ",";
				altFacID += "\"" + facID + "\"" + ",";
				altFacDesc += "\"" + (String) facDescV.elementAt(i) + "\"" + ",";
				//build work area
				Hashtable h = (Hashtable) fh.get(facID);
				Vector application = (Vector) h.get("APPLICATION");
				Vector applicationDesc = (Vector) h.get("APPLICATION_DESC");
				if (application.size() > 1 && !application.contains("All Work Areas")) {
					application.insertElementAt("All Work Areas", 0);
					applicationDesc.insertElementAt("All Work Areas", 0);
				}
				altWAID += "altWAID[\"" + facID + "\"] = new Array(";
				altWADesc += "altWADesc[\"" + facID + "\"] = new Array(";
				for (int j = 0; j < application.size(); j++) {
					altWAID += "\"" + (String) application.elementAt(j) + "\"" + ",";
					altWADesc += "\"" + HelpObjs.findReplace((String) applicationDesc.elementAt(j), "\"", "'") + "\"" + ",";
				}
				altWAID = altWAID.substring(0, altWAID.length() - 1) + ");\n";
				altWADesc = altWADesc.substring(0, altWADesc.length() - 1) + ");\n";
			}
			//removing the last commas ','
			altFacID = altFacID.substring(0, altFacID.length() - 1) + ");\n";
			altFacDesc = altFacDesc.substring(0, altFacDesc.length() - 1) + ");\n";
		}

		if (tmpHub.indexOf(",") > 1) {
			tmpHub = tmpHub.substring(0, tmpHub.length() - 1) + ");\n";
		}

		return (tmpHub + " " + altFacID + " " + altFacDesc + " " + altWAID + " " + altWADesc + "");
	}

	public static String changeSingleQuotetoTwoSingleQuote(String myString) {
		String result = "";
		for (int i = 0; i < myString.length(); i++) {
			String temp = myString.substring(i, i + 1);
			if (temp.equalsIgnoreCase("'"))
				result = result + temp + "'";
			else
				result = result + temp;
		}

		return result;
	}

	public static String findReplace(String source, String toReplace, String replacement) {
		if (source.indexOf(toReplace) > -1) {
			StringBuffer sb = new StringBuffer();
			for (int ix = -1; (ix = source.indexOf(toReplace)) >= 0;) {
				//sb.append( source.substring( 0,ix ) ).append( replacement );
				sb.append(source.substring(0, ix));
				sb.append(replacement);
				source = source.substring(ix + toReplace.length());
			}

			if (source.length() > 1)
				sb.append(source);
			return sb.toString();
		}
		else {
			return source;
		}
	}

}