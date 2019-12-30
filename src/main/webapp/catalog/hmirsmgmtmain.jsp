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
<script type="text/javascript" src="/js/catalog/hmirsmgmtmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<title><fmt:message key="hmirsMgmt"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
searchError:"<fmt:message key="label.hmirssearchreq"/>"};

<fmt:message var="nsn" key="label.nsn"/>
<fmt:message var="itemId" key="label.itemid"/>
<fmt:message var="mfgName" key="label.manufacturer"/>
var searchArray = [
    { id:'nsn', text:'<c:out value="${nsn}"/>'},
    { id:'itemId', text:'<c:out value="${itemId}"/>'},
    { id:'mfgName', text:'<c:out value="${mfgName}"/>'}
];

var searchMyArr = [
		{value:'contains', text: '<fmt:message key="label.contains"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
];


function setDropDowns()  
{ 
	setSearchWhat(searchArray);
}
//-->
</script>
</head>
<body onload="loadLayoutWin('','hmirsMgmt');setDropDowns();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="hmirsmgmtresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.loaddate"/>:</td>
				        <td width="80%" class="optionTitleBoldLeft">
				          <label for="loadDateFrom" class="optionTitleBoldRight"><fmt:message key="label.from"/></label>
				          <input type="text" id="loadDateFrom" name="loadDateFrom" class="inputBox pointer" readonly value="" onclick="return getCalendar(document.genericForm.loadDateFrom,null,document.genericForm.loadDateTo);" maxlength="10" size="8"/>
				          <label for="loadDateTo" class="optionTitleBoldRight"><fmt:message key="label.to"/></label>
				          <input type="text" id="loadDateTo" name="loadDateTo" class="inputBox pointer" readonly value="" onclick="return getCalendar(document.genericForm.loadDateTo,document.genericForm.loadDateFrom);" maxlength="10" size="8"/>
				        </td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.search"/>:</td>
				        <td width="80%">
				        	<select name="searchWhat" id="searchWhat" class="selectBox" onchange="changeSearchTypeOptions(this.value)">
							</select>
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
				        <td width="10%" class="optionTitleBoldRight">&nbsp;</td>
				        <td width="80%">
				          <label for="displayOnlyUnmappedNsns" class="optionTitleBoldLeft">
					        <input name="displayOnlyUnmappedNsns" id="displayOnlyUnmappedNsns" type="checkbox" checked class="radioBtns"/>
					        <fmt:message key="label.displayunmappednsn"/>
					      </label>
				        </td>
				      </tr>
				      <tr>
					      <td colspan="4">
							<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/>
							<input name="buttonCreateExcel" id="buttonCreateExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;"/>
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
	<input type="hidden" name="maxData" id="maxData" value="500"/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="searchWhatDesc" id="searchWhatDesc" type="hidden"/>
	<input name="searchTypeDesc" id="searchTypeDesc" type="hidden"/>
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
							<tcmis:facilityPermission indicator="true" userGroupId="UpdateHMIRS">
								<span id="updateLink"><a href="#" onclick="call('updateRoadMap')"><fmt:message key="label.update"/></a></span>
							</tcmis:facilityPermission>
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