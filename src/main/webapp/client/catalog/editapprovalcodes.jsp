<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/editapprovalcodes.js"></script>

<!-- Popup window support -->
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title><fmt:message key="label.editapprovalcodeexpiration"/></title>

<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={
	pleasewait:'<fmt:message key="label.pleasewait"/>',
	requirestartenddates:'<fmt:message key="error.requirestartenddates"/>'
};

var windowCloseOnEsc = true;

var gridConfig = {
	divName:'EditApprovalCodeBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:rowSelect,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onAfterHaasRenderRow:finishRenderingRow,
	onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
};

var config = [
  {
  	columnId:"startDatePermission"
  },
  {
	columnId:"endDatePermission"
  },
  {
	columnId:"approvalCode",
	submit:true
  },
  {
	columnId:"approvalCodeDisplay",
	columnName:'<fmt:message key="label.approvalcode"/>',
	width:20
  },
  {
	columnId:"startDate",
	columnName:"<fmt:message key="label.startDate"/>",
	sorting:'int',
	hiddenSortingColumn:'hiddenStartDateSort',
	width:5
  },
  {
	  columnId:"hiddenStartDateSort",
	  sorting:'int'
  },
  {
  	columnId:"endDate",
  	columnName:'<fmt:message key="label.enddate"/>',
  	type:'hed',
  	align:'center',
	hiddenSortingColumn:'hiddenEndDateSort',
	sorting:'int',
	width:10,
	permission:true,
	onChange:changeDateSort,
    submit:true
  },
  {
	columnId:"originalEndDate",
	columnName:"<fmt:message key="label.originalenddate"/>",
	sorting:'int',
	hiddenSortingColumn:'hiddenOriginalEndDateSort',
	width:5
  },
  {
	  columnId:"hiddenEndDateSort",
	  sorting:'int'
  },
  {
	  columnId:"hiddenOriginalEndDateSort",
	  sorting:'int'
  },
  {
  	columnId:"approvedId",
  	submit:true
  },
  {
  	columnId:"msdsOrMixtureUseId",
  	submit:true
  }
  ];
-->
</script>
</head>
<c:choose>
<c:when test="${param.uAction eq 'submitClose'}">
<body onload="submitCancel();">
</c:when>
<c:otherwise>
<body onload="initGrid(gridConfig);">
</c:otherwise>
</c:choose>
<div class="interface" id="mainPage" style="">
<!-- Result Frame Begins -->
	<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
		<!-- Transit Page Begins -->
		<div id="transitDailogWin" style="display: none;overflow:auto" class="errorMessages">
			<table width="100%" border="0" cellpadding="2" cellspacing="1">
				<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td align="center" id="transitLabel"></td></tr>
				<tr><td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"></td></tr>
			</table>
		</div>
		<!-- Transit Page Ends -->
		
		<div id="resultGridDiv"> 
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
									<div class="boxhead"> <%-- boxhead Begins --%>
										<div>
											<c:choose>
												<c:when test="${not empty param.catPartNo}">
											<span class="optionTitleBold"><fmt:message key="label.part"/>: ${param.catPartNo}</span>
												</c:when>
												<c:otherwise>
											<span class="optionTitleBold"><fmt:message key="label.msds"/>: {param.customerMsdsOrMixtureNo}</span>
												</c:otherwise>
											</c:choose>
										</div>
										<div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
											<div id="updateResultLink">
									           <a href="#" onclick="submitUpdate(false);"><fmt:message key="label.submit"/></a> |
									           <a href="#" onclick="submitUpdate(true);"><fmt:message key="label.submitandclose"/></a> |  
									           <a href="#" onclick="submitCancel();"><fmt:message key="label.cancel"/></a>
											</div>
										</div>
									</div>
									<div class="dataContent"> 
										<tcmis:form action="/editapprovalcodes.do" onsubmit="return submitFrameOnlyOnce();">

											<div class="interface" id="resultsPage">
											<div class="backGroundContent">
											<c:set var="dataCount" value='${0}'/>
											
											<c:if test="${!empty EditApprovalCodeBeanColl}" >
											<div id="EditApprovalCodeBean" style="width:100%;height:400px;"></div>
											<!-- Search results start -->
											<script type="text/javascript">
											<!--
											/*Storing the data to be displayed in a JSON object array.*/
											var jsonMainData = new Array();
											var jsonMainData = {
											rows:[
											<c:forEach var="bean" items="${EditApprovalCodeBeanColl}" varStatus="status">
											<fmt:formatDate var="fmtStartDate" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
											<fmt:formatDate var="fmtEndDate" value="${bean.endDate}" pattern="${dateFormatPattern}"/>
											<c:if test="${status.index > 0}">,</c:if>
											
											{ id:${status.index +1},
											 data:[
											  "Y",
											  "Y",
											  "${bean.applicationUseGroupId}",
											  "${bean.applicationUseGroupId}: ${bean.usageSubcategoryName}",
											  "${fmtStartDate}",
											  "${bean.startDate.time}",
											  "${fmtEndDate}",
											  "${fmtEndDate}",
											  "${bean.endDate.time}",
											  "${bean.endDate.time}",
											  "${bean.approvedId}",
											  "${bean.msdsOrMixtureUseId}"
											  ]
											}
											
											 <c:set var="dataCount" value='${dataCount+1}'/>
											 </c:forEach>
											]};
											//-->
											</script>
											
											</div> <!-- end of grid div. -->
											</c:if>
											
											<c:if test="${empty EditApprovalCodeBeanColl}" >
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
											 <tr>
											  <td width="100%">
											   <fmt:message key="main.nodatafound"/>
											  </td>
											 </tr>
											</table>
											</c:if>
											
												<div id="hiddenElements" style="display: none;">
													<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
													<input name="uAction" id="uAction" value="" type="hidden"/>
													<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden"/>
													<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
													<input name="fullName" id="fullName" value="${fullName}" type="hidden"/>
													<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
													<input name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}" type="hidden"/>
													<input name="catalogId" id="catalogId" value="${param.catalogId}" type="hidden"/>
													<input name="catPartNo" id="catPartNo" value="${param.catPartNo}" type="hidden"/>
													<input name="partGroupNo" id="partGroupNo" value="${param.partGroupNo}" type="hidden"/>
													<input name="customerMsdsDb" id="customerMsdsDb" value="${param.customerMsdsDb}" type="hidden"/>
													<input name="customerMsdsOrMixtureNo" id="customerMsdsOrMixtureNo" value="${param.customerMsdsOrMixtureNo}" type="hidden"/>
													<input type="hidden" name="blockBefore_startDate " id="blockBefore_startDate" value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
													<input type="hidden" name="blockAfter_startDate " id="blockAfter_startDate" value=""/>
													<input type="hidden" name="blockBeforeExclude_startDate " id="blockBeforeExclude_startDate" value=""/>
													<input type="hidden" name="blockAfterExclude_startDate " id="blockAfterExclude_startDate" value=""/>
													<input type="hidden" name="inDefinite_startDate " id="inDefinite_startDate" value=""/>
													<input type="hidden" name="blockBefore_endDate " id="blockBefore_endDate" value="<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>"/>
													<input type="hidden" name="blockAfter_endDate " id="blockAfter_endDate" value=""/>
													<input type="hidden" name="blockBeforeExclude_endDate " id="blockBeforeExclude_endDate" value=""/>
													<input type="hidden" name="blockAfterExclude_endDate " id="blockAfterExclude_endDate" value=""/>
													<input type="hidden" name="inDefinite_endDate " id="inDefinite_endDate" value=""/>
													
													
												</div>
											
											</div> <!-- close of backGroundContent -->
											
										</tcmis:form>
									</div>
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
</body>
</html:html>