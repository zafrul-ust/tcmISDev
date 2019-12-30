<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


<%-- Add any other javascript you need for the page here --%>
<!-- <script src="/js/report/formattedreport.js" language="JavaScript"></script> -->
<script language="JavaScript" type="text/javascript">
<!--
var altFacilityId = new Array(
   <c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
   <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

function generateMonthlyVOCReport(){	
	var dateStr = $('beginYear').value + $('beginMonth').value + '01';
	var reportType = '';
	var criteria = '';
	
	var criteriaList = document.getElementsByName('criteria');
	for (var i=0; i < criteriaList.length; i++){
		if(criteriaList[i].checked)
			criteria = criteriaList[i].value;
	}
	
	if(criteria == 'hourly')
		reportType = 'Hourly';
	else if(criteria == 'paint')
		reportType = 'Five-Hour Average 433.6A';
	else if(criteria == 'coating')
		reportType = 'Five-Hour Average 433.7A';
	
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
	                    "?pr_facilityId=" + encodeURIComponent($('facilityId').value) +
	                    "&pr_reportType=" + encodeURIComponent(reportType) +
	                    "&pr_dateStr=" + dateStr;
	
	if(reportType == 'Hourly')
		reportLoc += "&rpt_ReportBeanId=VOCHourlyReportDefinition";
	else
		reportLoc += "&rpt_ReportBeanId=FiveHourVOCReportDefinition";
		
	openWinGeneric(reportLoc,"viewVOCReport","800","550","yes","100","100");
}
-->
</script>

<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="formattedmonthlyvocreport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
youmustentercasnumber:"<fmt:message key="label.youmustentercasnumber"/>",
pleaseenterreportname:"<fmt:message key="label.pleaseenterreportname"/>",
daterequired:"<fmt:message key="label.daterequired"/>",
begindaterequired:"<fmt:message key="label.begindaterequired"/>",
enddaterequired:"<fmt:message key="label.enddaterequired"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">
<c:if test="org.apache.struts.action.MESSAGE == null">
  <div class="errorMessages">
    ERROR:  Application resources not loaded
  </div>
</c:if>

<tcmis:form action="/monthlyvocreport.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">


<tr>
<td width="15%" class="optionTitleBoldRight">
   <fmt:message key="label.facility"/>:
</td>

<td class="optionTitleLeft" colspan="3">
<html:select property="facilityId" styleClass="selectBox" styleId="facilityId">
<html:options collection="facAppReportViewBeanCollection" name="FacAppReportViewBean" labelProperty="facilityName" property="facilityId"/>
</html:select>
</td>

</tr>

<tr>

<td class="optionTitleBoldRight"><fmt:message key="label.date"/>:</td>
<td class="optionTitleLeft" colspan="3">
	<html:select property="beginMonth" name="beginMonth" styleClass="selectBox" value="${beginMonth}">
       <html:option value="01">January</html:option>
       <html:option value="02">February</html:option>
       <html:option value="03">March</html:option>
       <html:option value="04">April</html:option>
       <html:option value="05">May</html:option>
       <html:option value="06">June</html:option>
       <html:option value="07">July</html:option>
       <html:option value="08">August</html:option>
       <html:option value="09">September</html:option>
       <html:option value="10">October</html:option>
       <html:option value="11">November</html:option>
       <html:option value="12">December</html:option>
    </html:select>

	
    <html:select property="beginYear" name="beginYear" styleClass="selectBox" value="${beginYear}">
    	<html:options collection="yearCollection" name="KeyValuePairBean" labelProperty="value" property="key"/>
    </html:select> 
</td>

</tr>
<tr>
      <td class="optionTitleBoldLeft" colspan="4">
         <html:radio styleClass="radioBtns" styleId="criteria" property="criteria" value="hourly"/>
         <fmt:message key="label.hourly"/>
</td>
</tr>
<tr>
      <td class="optionTitleBoldLeft" colspan="4">
         <html:radio styleClass="radioBtns" styleId="criteria" property="criteria" value="paint"/>
         <fmt:message key="formattedmonthlyvocreport.label.paint"/>
</td>
</tr>
<tr>
      <td class="optionTitleBoldLeft" colspan="4">
         <html:radio styleClass="radioBtns" styleId="criteria" property="criteria" value="coating"/>
         <fmt:message key="formattedmonthlyvocreport.label.coating"/>
</td>
</tr>
<%-- <tr>
   <td class="optionTitleBoldLeft">
<html:radio styleClass="radioBtns" styleId="reportGenerationType" property="reportGenerationType" value="interactive"/>
      <fmt:message key="adhocusagereport.label.interactive"/>
   </td>

   <td class="optionTitleBoldRight" width="15%">
<html:radio styleClass="radioBtns" styleId="reportGenerationType" property="reportGenerationType" value="batch"/>
<fmt:message key="label.batch"/>
   </td>
   <td class="optionTitleBoldRight" width="15%">
<fmt:message key="adhocusagereport.label.reportname"/>:
   </td>
   <td class="optionTitleLeft">
<html:text property="reportName" styleClass="inputBox"/>
   </td>
</tr> --%>

<tr>
<html:hidden property="id"/>
<html:hidden property="templateName"/>
<html:hidden property="submitValue"/>
   <td class="optionTitleLeft" colspan="4">
      <html:button property="submitReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"  onclick="generateMonthlyVOCReport();">
     <fmt:message key="button.generatereport"/>
     </html:button>
   </td>
</tr>

    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
