<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/hub/pickingstatusmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<title><fmt:message key="pickingStatus"/></title>
<script language="JavaScript" type="text/javascript"/>
<!-- 
var messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
mr:"<fmt:message key="label.mr"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
expireDaysInteger:"<fmt:message key="error.expiredays.integer"/>",
picklistConfirm:"<fmt:message key="picklistpicking.confirm.generate"/>",
total:"<fmt:message key="label.total"/>",
selectpickingstate:"<fmt:message key="label.inputSearchText"/>",
errors:"<fmt:message key="label.errors"/>"};

useLayout = false;

var pickingGroups = [
	{hub:"",
		  groups:[{name:'<fmt:message key="label.any"/>',id:''}]
	},
	<c:set var="pgHub" value="0"/>
	<c:set var="pgCount" value="0"/>
	<c:forEach var="pg" items="${vvPickingGroupColl}" varStatus="status">
 		<c:if test="${pgHub eq pg.hub}">
			<c:if test="${pgCount > 0}">,</c:if>
		</c:if>
		<c:if test="${pgHub != pg.hub}">
			<c:if test="${pgHub != 0}">]},</c:if>
			<c:set var="pgCount" value="0"/>
			<c:set var="pgHub" value="${pg.hub}"/>
		{hub:"${pg.hub}",
		  groups:[{name:'<fmt:message key="label.any"/>',id:''},
		</c:if>
		{name:'<tcmis:jsReplace value="${pg.pickingGroupName}" processMultiLines="false"/>',
				id:'${pg.pickingGroupId}'}
		<c:set var="pgCount" value="${pgCount+1}"/>
	</c:forEach>
]}];

<c:set var="receiptid"><fmt:message key="label.receiptid"/></c:set>
<c:set var="picklistid"><fmt:message key="label.picklistid"/></c:set>
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
<c:set var="pdoc"><fmt:message key="label.pdoc"/></c:set>
<c:set var="prnumber"><fmt:message key="label.prnumber"/></c:set>
<c:set var="facpartno"><fmt:message key="label.customerpartnumber"/></c:set>
<c:set var="pickingunit"><fmt:message key="label.pickingunit"/></c:set>
var searchArray = [
    { id:'receiptId', text:'<tcmis:jsReplace value="${receiptid}"/>'},
    { id:'picklistId', text:'<tcmis:jsReplace value="${picklistid}"/>'},
    { id:'pdoc', text:'<tcmis:jsReplace value="${pdoc}"/>'},
    { id:'itemId', text:'<tcmis:jsReplace value="${itemid}"/>'},
    { id:'prNumber', text:'<tcmis:jsReplace value="${prnumber}"/>'},
    { id:'facPartNo', text:'<tcmis:jsReplace value="${facpartno}"/>'},
    { id:'pickingUnitId', text:'<tcmis:jsReplace value="${pickingunit}"/>'}
];

defaults.hub.nodefault = true;
//-->
</script>
</head>
<body onload="loadLayoutWin('','pickingStatus');setOps();setSearchWhat(searchArray);" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="pickingstatusresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont filterContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					<table width="710" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      				<tr>
					 	<td width="20%" class="optionTitleBoldRight">
							<fmt:message key="label.operatingentity"/>:
					 	</td>
					 	<td width="50%" class="optionTitleLeft">
							<select name="opsEntityId" id="opsEntityId" class="selectBox">
							</select>
							<input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
						</td>
						<td nowrap class="optionTitleBoldRight"><fmt:message key="label.pickcreationdate"/>:</td>
						<td nowrap class="optionTitleBoldLeft">
							<fmt:message key="label.from"/>
							<input class="inputBox pointer" readonly type="text" name="pickCreationFromDate" id="pickCreationFromDate" value="" onClick="return getCalendar(document.genericForm.pickCreationFromDate,null,document.genericForm.pickCreationToDate);" maxlength="10" size="9"/>
							<fmt:message key="label.to"/>&nbsp;
							<input class="inputBox pointer" readonly type="text" name="pickCreationToDate" id="pickCreationToDate" value="" onClick="return getCalendar(document.genericForm.pickCreationToDate,document.genericForm.pickCreationFromDate);" maxlength="10" size="9"/>
						</td>	
					</tr>

					<tr>
						<td  class="optionTitleBoldRight">
							<fmt:message key="label.hub"/>:
						</td>
						<td  class="optionTitleLeft">
							<select name="sourceHub" id="sourceHub" class="selectBox">
							</select>
							<input type="hidden" name="selectedHub" id="selectedHub" value="${param.sourceHub}"/>
							<c:set var="selectedHubName" value='${param.sourceHubName}'/>
						</td>

						<td  class="optionTitleBoldRight">
							<fmt:message key="label.neededbyoron"/>:
						</td>
						<td  class="optionTitleLeft">
							<input type="text" name="filterDate" id="filterDate" onclick="return getCalendar(document.genericForm.filterDate,null,null,null,null);" size="12" maxlength="12" class="inputBox pointer"/>	
						</td>
					</tr>
					<tr>
						<td  class="optionTitleBoldRight">
							<fmt:message key="label.pickingstate"/>:
						</td>
						<td  class="optionTitleLeft">
							<select name="pickingState" id="pickingState" class="selectBox">
								<option value=""><fmt:message key="label.any"/></option>
								<option value="Open"><fmt:message key="label.open"/></option>
								<option value="Hold"><fmt:message key="label.hold"/></option>
								<option value="REJECTED"><fmt:message key="label.rejected"/></option>
								<option value="Picking"><fmt:message key="label.picking"/></option>
								<option value="Packing"><fmt:message key="label.packing"/></option>
								<option value="QC"><fmt:message key="label.qc"/></option>
								<option value="Shipping"><fmt:message key="label.shipping"/></option>
								<option value="SHIP_CONFIRMED"><fmt:message key="label.shipped"/></option>
							</select>	
						</td>
						
					  <td  class="optionTitleBoldRight">
						 <fmt:message key="label.search"/>:
					  </td>
					  <td class="optionTitleLeft" nowrap>
						 <select name="searchWhat" id="searchWhat"  onchange="getSelectedSearchWhat();" class="selectBox">
						 </select>
						 <input type="hidden" name="selectedSearchWhat" id="selectedSearchWhat" value="${param.searchWhat}"/>
						 <html:select property="searchType" styleId="searchType" styleClass="selectBox">
								<html:option value="is" ><fmt:message key="label.is"/></html:option>
								<html:option value="contains"><fmt:message key="label.contains"/></html:option>
								<html:option value="startsWith" key="label.startswith"/>
								<html:option value="endsWith" key="label.endswith"/>
						 </html:select>
						 <html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
					  </td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight">
							<fmt:message key="label.pickinggroup"/>:
						</td>
						<td  class="optionTitleLeft">
							<select name="pickingGroupId" id="pickingGroupId" class="selectBox">
							</select>		
						</td>
						<td  class="optionTitleBoldRight">
						  <fmt:message key="label.sortby"/>:
					    </td>
					    <td>
					    	<html:select property="orderBy" styleId="orderBy" styleClass="selectBox" value="prNumber">             
            				<html:option value="pickingUnit" key="label.pickingunit"/>          
            				<html:option value="picklist" key="label.picklistid"/>          
				            <html:option value="facility" key="picklistpicking.facilityidworkarea"/>
				            <html:option value="deliveryPoint" key="label.deliverypoint"/>
				            <html:option value="item" key="label.itemid"/>
				            <html:option value="prNumber" key="label.mr"/>
				            <html:option value="needDate" key="label.needdate"/>
				            <html:option value="facPartNo" key="label.partnumber"/>
				            <html:option value="shipToLocation" key="label.shipto"/>
				            <html:option value="pickingState" key="label.pickingstate"/>
				            </html:select>
					    </td>
					</tr>
					<tr>
						<td  class="optionTitleLeft" colspan="3">
							<input onclick="return submitSearchForm()" type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="searchButton" id="searchButton"/>&nbsp;
							<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createXSL()"/>
						</td>
					</tr>
					</table>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!-- Search Option Ends -->
<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame -->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages">
			<html:errors />
		</div>
	</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="today" id="today" type="hidden" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
	<input name="facility" id="facility" type="hidden" value="${selectedHubName}"/>
</div>
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	<br><br><br><fmt:message key="label.pleasewait"/>
	<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont contentContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
						<%-- boxhead Begins --%>
						<div class="boxhead">
							<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
							<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
								<a href="#" onclick="call('submitUpdate')"><fmt:message key="label.update"/></a>
								<span id="updateResultLink" style="display: none"></span>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="400" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
						</div>
						<%-- result count and time --%>
						<div id="footer" class="messageBar"></div>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!-- Search results end -->
</div>
</div>
<!-- Result Frame Ends -->
</div>
<!-- close of interface -->
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
	<div id="errorMessagesAreaBody" class="bd">
		<html:errors/>
	</div>
</div>
<%-- show legend Ends --%>
</body>
</html:html>