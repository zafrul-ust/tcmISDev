<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/receivingreportmain.js"></script> 

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>



<title><fmt:message key="label.receivingreport" /></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", searchInteger:"<fmt:message key="label.errorinteger"/>",
missingData:"<fmt:message key="label.missingsearchordate"/>", search:"<fmt:message key="button.search"/>"};
// -->
		// Can be commented out to show My Entities, My hubs and My Inventory Groups in the pulldowns
		defaults.ops.nodefault = true;
		defaults.hub.nodefault = true;
		// defaults.inv.nodefault = true;
		
		//if hub is changed, call hubchanged function
		//defaults.hub.callback = hubchanged;
</script>
</head>

<body bgcolor="#ffffff"
	onload="loadLayoutWin('','customerReceivingHistory');setOps();try{document.getElementById('searchText').focus()}catch(x){}"
	onresize="resizeFrames()">

<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
<div id="searchFrameDiv"><%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea"><tcmis:form
	action="/receivingreportresults.do"
	onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
	<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
			<div class="roundright">
			<div class="roundtop">
			<div class="roundtopright"><img
				src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15"
				height="10" class="corner_filter" style="display: none" /></div>
			</div>
			<div class="roundContent"><!-- Insert all the search option within this div -->
			<table width="500" border="0" cellpadding="0" cellspacing="0"
				class="tableSearch">
				<tr>
					<td nowrap width="20%" class="optionTitleBoldRight" nowrap><fmt:message
						key="label.operatingentity" />:</td>
					<td width="50%" class="optionTitleBoldLeft"><select
						name="opsEntityId" id="opsEntityId" class="selectBox"></select></td>
				</tr>
				<%-- Row 1 --%>
				<tr>
					<td nowrap width="20%" class="optionTitleBoldRight" nowrap><fmt:message
						key="label.hub" />:</td>
					<td width="50%" class="optionTitleBoldLeft"><select name="hub"
						id="hub" class="selectBox"></select></td>
					<td nowrap width="20%" class="optionTitleBoldRight" nowrap><fmt:message
						key="label.dorbegin" />:</td>
					<td width="40%" class="optionTitleBoldLeft"><input
						class="inputBox" readonly name="beginDate" id="beginDate"
						type="text" value="" size="8" readonly="true"
						onClick="return getCalendar(document.getElementById('beginDate'),null,null,null,document.getElementById('endDate'));">
					<%--<a href="javascript: void(0);" ID="linkbeginDate" class="datePopUpLink" onClick="return getCalendar(document.getElementById('beginDate'),null,null,null,document.getElementById('endDate'));"></a> --%></td>
				</tr>
				<%-- row 2 --%>
				<tr>
					<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message
						key="label.inventorygroup" />:&nbsp;</td>
					<td width="40%" class="optionTitleBoldLeft"><select
						name="inventoryGroup" id="inventoryGroup" class="selectBox">
					</select></td>
					<td width="10%" class="optionTitleBoldRight"><fmt:message
						key="label.dorend" />:&nbsp;</td>
					<td width="40%" class="optionTitleBoldLeft"><input
						class="inputBox" readonly name="endDate" id="endDate" type="text"
						value="" size="8" readonly="true"
						onClick="return getCalendar(document.getElementById('endDate'),null,null,document.getElementById('beginDate'));">
					<%--<a href="javascript: void(0);" ID="linkendDate" class="datePopUpLink" onClick="return getCalendar(document.getElementById('endDate'),null,null,document.getElementById('beginDate'));"></a>  --%></td>
				</tr>
				<%-- row 3 --%>
				<tr>
					<td width="10%" class="optionTitleBoldRight"><fmt:message
						key="label.search" />:&nbsp;</td>
					<td width="50%" class="optionTitleBoldLeft" nowrap><html:select
						property="searchWhat" styleId="searchWhat" styleClass="selectBox">
						<html:option value="radian_po" key="label.haaspo" />
						<html:option value="customer_po_no" key="label.customerpo" />
						<html:option value="release_no" key="label.releaseno" />
						<html:option value="cat_part_no" key="label.partNno" />
						<html:option value="item_id" key="label.itemid" />
					</html:select> <html:select property="searchType" styleId="searchType"
						styleClass="selectBox">
						<html:option value="LIKE" key="label.contains" />
						<html:option value="IS" key="label.is" />
						<html:option value="STARTSWITH" key="label.startswith" />
						<html:option value="ENDSWITH" key="label.endswith" />
					</html:select> <input class="inputBox" type="text" name="searchText"
						id="searchText" value="<c:out value='${param.searchText}'/>"
						size="10"></td>
					<td width="10%" class="optionTitleBoldRight"><fmt:message
						key="label.sort" />:&nbsp;</td>
					<td width="40%" class="optionTitleBoldLeft"><html:select
						property="sortBy" styleId="sortBy" styleClass="selectBox">
						<html:option
							value="radian_po,po_line,inventory_group,customer_po_no,release_no,cat_part_no,item_id,date_of_receipt"
							key="label.haaspo" />
						<html:option
							value="customer_po_no,release_no,radian_po,po_line,inventory_group,cat_part_no,item_id,date_of_receipt"
							key="label.customerpo" />
						<html:option
							value="release_no,customer_po_no,radian_po,po_line,inventory_group,cat_part_no,item_id,date_of_receipt"
							key="label.releaseno" />
						<html:option
							value="cat_part_no,radian_po,po_line,inventory_group,customer_po_no,release_no,item_id,date_of_receipt"
							key="label.partNno" />
						<html:option
							value="item_id,radian_po,po_line,inventory_group,customer_po_no,release_no,cat_part_no,date_of_receipt"
							key="label.item" />
					</html:select></td>
				</tr>
				<%-- row 4 --%>
				<tr>
					<td width="10%">&nbsp;</td>
					<td width="40%" class="optionTitleBoldRight">&nbsp;</td>
					<td width="10%" nowrap class="optionTitleBoldRight"><fmt:message
						key="label.unitofmeasure" />:&nbsp;</td>

					<td width="40%" class="optionTitleBoldLeft"><html:select
						property="unitOfMessure" styleId="unitOfMessure"
						styleClass="selectBox">
						<html:option value="uos" key="label.uos" />
						<html:option value="itemuom" key="label.itemuom" />
					</html:select></td>
				</tr>
				<%-- buttons --%>
				<tr>
					<td width="10%" colspan="2" class="optionTitleBoldLeft"><input
						name="submitSearch" id="submitSearch" type="submit"
						class="inputBtns" value="<fmt:message key="button.search"/>"
						onmouseover="this.className='inputBtns inputBtnsOver'"
						onMouseout="this.className='inputBtns'"
						onclick="return submitSearchForm();"> <input
						name="buttonCreateExcel" id="buttonCreateExcel" type="submit"
						class="inputBtns"
						value="<fmt:message key="label.createexcelfile"/>"
						onmouseover="this.className='inputBtns inputBtnsOver'"
						onmouseout="this.className='inputBtns'"
						onclick="generateExcel(); return false;"></td>
				</tr>
			</table>
			<!-- End search options --></div>
			<div class="roundbottom">
			<div class="roundbottomright"><img
				src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15"
				class="corner" style="display: none" /></div>
			</div>
			</div>
			</div>
			</td>
		</tr>
	</table>

	<!-- Error Messages Begins -->
	<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
	<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
		<div class="spacerY">&nbsp;
		<div id="errorMessagesArea" class="errorMessages"><html:errors />
		</div>
		</div>
	</c:if>
	<!-- Error Messages Ends -->

	<!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"><input
		name="uAction" id="uAction" type="hidden" value=""> <input
		name="startSearchTime" id="startSearchTime" type="hidden" value="">
	<input name="hubName" id="hubName" type="hidden" value=""> <input
		name="inventoryGroupName" id="inventoryGroupName" type="hidden"
		value=""> <input name="facilityName" id="facilityName"
		type="hidden" value=""> <input name="searchWhatDesc"
		id="searchWhatDesc" type="hidden" value=""> <input
		name="searchTypeDesc" id="searchTypeDesc" type="hidden" value="">
	<input name="sortByDesc" id="sortByDesc" type="hidden" value="">
	<input name="companyId" id="companyId" type="hidden" value="">
	<input name="unitOfMessureDesc" id="unitOfMessureDesc" type="hidden"
		value=""></div>
</tcmis:form></div>
<!-- close of contentArea --></div>


<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;"><!-- Transit Page Begins -->
<div id="transitPage" style="display: none;"
	class="optionTitleBoldCenter"><br><br><br><fmt:message
	key="label.pleasewait" /> <br><br><br><img
	src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends --> <!-- Result Frame Begins -->
<div id="resultGridDiv" style="display: none; margin: 0px 4px 0px 4px;">
<!-- Search results start --> <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td>
		<div class="roundcont contentContainer">
		<div class="roundright">
		<div class="roundtop">
		<div class="roundtopright"><img
			src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15"
			class="corner" style="display: none" /></div>
		</div>
		<div class="roundContent">
		<div class="boxhead"><%-- boxhead Begins --%> <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
		<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%>
		</div>
		<%-- mainUpdateLinks Ends --%></div>
		<%-- boxhead Ends --%>

		<div class="dataContent"><iframe scrolling="no" id="resultFrame"
			name="resultFrame" width="100%" height="" frameborder="0"
			marginwidth="0" src="/blank.html"></iframe></div>

		<%-- result count and time --%>
		<div id="footer" class="messageBar"></div>

		</div>
		<div class="roundbottom">
		<div class="roundbottomright"><img
			src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15"
			class="corner" style="display: none" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
</div>
<div id="errorMessagesArea" class="errorMessages"
	style="display: none; overflow: auto;"></div>
</body>
</html:html>