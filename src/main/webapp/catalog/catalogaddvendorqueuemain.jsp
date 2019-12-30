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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/catalog/catalogaddvendorqueuemain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title><fmt:message key="catalogaddqc.title"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	pleaseselect:"<fmt:message key="error.norowselected"/>",
	showlegend:"<fmt:message key="label.showlegend"/>"
};

var defaults = {
	   	assignedTo: {id:'',name:'<fmt:message key="label.all"/>'}
};
	   		
var assignedUsers = [
	{id: '-1', name: 'Unassigned'}<c:if test="${not empty catalogUsers}">,</c:if>
   	    	<c:forEach var="catalogUser" items="${catalogUsers}" varStatus="status">
   	    		{id: '${catalogUser.personnelId}', name: '${catalogUser.personnelName}'}<c:if test="${!status.last}">,</c:if>
   	    	</c:forEach>
];

function buildDropDown( arr, defaultObj, eleName ) {
	 var obj = $(eleName);
	 for (var i = obj.length; i > 0;i--) {
	   obj[i] = null;
	 }
	// if size = 1 or 2 show last one, first one is all, second one is the only choice.
	if( arr == null || arr.length == 0 ) 
	 setOption(0,defaultObj.name,defaultObj.id, eleName); 
	else if( arr.length == 1 )
	 setOption(0,arr[0].name,arr[0].id, eleName);
	else {
	    var start = 0;
	if( defaultObj.nodefault ) {
		start = 0 ; // will do something??
	}
	else {
		setOption(0,defaultObj.name,defaultObj.id, eleName); 
	  	start = 1;
	 }
	 for ( var i=0; i < arr.length; i++) {
	   	setOption(start++,arr[i].name,arr[i].id,eleName);
	 }
	}
	obj.selectedIndex = 0;
}

function setDropdowns() {
 	buildDropDown(assignedUsers,defaults.assignedTo,"assignedTo");
}
// -->
</script>
</head>
<body onload="loadLayoutWin('','catAddVendorQueue');setDropdowns();" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="catalogaddvendorqueueresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
						<table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
							<tr>
								<td width="15%" class="optionTitleBoldRight">
									<fmt:message key="label.status" />:&nbsp;
								</td>
								<td width="35%" class="optionTitleLeft">
									<html:select property="status" styleId="status" styleClass="selectBox">
										<html:option value=""><fmt:message key="label.all"/></html:option>
										<c:forEach var="qStatus" items="${statusList}" varStatus="status">
                                        <html:option value="${qStatus.status}">${qStatus.status}</html:option>
										</c:forEach>
									</html:select>
								</td>
							</tr>
							<tr>
								<td width="15%" class="optionTitleBoldRight">
									<fmt:message key="label.task" />:&nbsp;
								</td>
								<td width="35%" class="optionTitleLeft">
									<html:select property="task" styleId="task" styleClass="selectBox">
										<html:option value=""><fmt:message key="label.all"/></html:option>
										<c:forEach var="qTask" items="${taskList}" varStatus="status">
                                        <html:option value="${qTask.task}">${qTask.task}</html:option>
										</c:forEach>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.assignedto" />:
								</td>
								<td class="optionTitleLeft">
									<select name="assignedTo" id="assignedTo" class="selectBox">
									</select>
								</td>
                            </tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.search" />:&nbsp;
								</td>
								<td colspan="3" class="optionTitleLeft">
									<select name="searchField" class="selectBox" id="searchField">
										<option value="q_id"><fmt:message key="label.queueid" /></option>
										<option value="manufacturer"><fmt:message key="label.manufacturer" /></option>
										<option value="material_desc"><fmt:message key="label.materialdesc" /></option>
									</select>
									<select name="searchMode" class="selectBox" id="searchMode">
										<option value="contains"><fmt:message key="label.contains"/></option>
                                        <option value="is"><fmt:message key="label.is"/></option>
                                        <option value="startsWith"><fmt:message key="label.startswith"/></option>
                                        <option value="endsWith"><fmt:message key="label.endswith" /></option>
									</select>
									<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchText}"/>' size="25" />
								</td>
							</tr>
							<tr class="alignLeft">
								<td width="10%" class="optionTitleLeft" colspan="4">
								<input name="SearchButton" id="SearchButton" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/> 
								<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" 
									onmouseout="this.className='inputBtns'" onclick="return createXSL()" />
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
	<input type="hidden" name="calledFrom" id="calledFrom" value="vendorQueuePage"/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
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
							<div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
								<span id="updateResultLink" style="display: none"><a href="#" onclick="resultFrame.update();"><fmt:message key="label.update"/></a> |</span>
								<%--<span id="invoiceResultLink" style="display: none"><a href="#" onclick="resultFrame.submitInvoice();"><fmt:message key="label.submitinvoice"/></a></span>--%>
								<span id="qcChecked" style="display: none"><a href="#" onclick="resultFrame.approve();"><fmt:message key="label.approve"/></a> |</span>
								<span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<%-- show legend Begins --%>
	<div id="showLegendArea" style="display: none;overflow: auto;">
		<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px" class="red legendText">&nbsp;</td>
				<td class="legendText"><fmt:message key="label.overdue"/></td>
			</tr>
		</table>
	</div>
	<%-- show legend Ends --%>
</body>
</html:html>
