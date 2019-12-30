<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
<tcmis:fontSizeCss/>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

	<%-- For Calendar support --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


	<%-- Add any other javascript you need for the page here --%>
<script src="/js/report/inventoryplanningreport.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var facCountyAreaArr = [
<c:forEach var="facCountyAreaBean" items="${facCountyAreaColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
{id:   '${status.current.facilityId}',
 name: '<tcmis:jsReplace value="${status.current.facilityName}"/>',
 coll:[	<c:forEach var="countyAreaBean" items="${status.current.planningReportAreaList}" varStatus="status1"> 
	<c:if test="${status1.index > 0}">,</c:if>
	{ 
	  id: '${status1.current.county}', 
	  name: '<tcmis:jsReplace value="${status1.current.county}"/>',
	  solidThreshold: '<tcmis:jsReplace value="${status1.current.solidThreshold}"/>',
	  solidThresholdUnit: '<tcmis:jsReplace value="${status1.current.solidThresholdUnit}"/>',
	  liquidThreshold: '<tcmis:jsReplace value="${status1.current.liquidThreshold}"/>',
	  liquidThresholdUnit: '<tcmis:jsReplace value="${status1.current.liquidThresholdUnit}"/>',
	  gasThreshold: '<tcmis:jsReplace value="${status1.current.gasThreshold}"/>',
	  gasThresholdUnit: '<tcmis:jsReplace value="${status1.current.gasThresholdUnit}"/>',
	  coll: [
	  	<c:forEach var="areaObjBean" items="${status1.current.areaList}" varStatus="status2"> 
	  	<c:if test="${status2.index > 0}">,</c:if>
	  	{ 
	  	  id: '${status2.current.areaId}', 
	  	  name: '<tcmis:jsReplace value="${status2.current.areaName}"/>'
		}
	    </c:forEach>]
	    }
	    </c:forEach>]
	}
	</c:forEach>]; 
// -->
</script>

<title>
	<fmt:message key="label.inventoryplanning"/>
</title>

<script language="JavaScript" type="text/javascript">
	<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		pleaseselect:"<fmt:message key="label.pleaseselect"/>",
		all:"<fmt:message key="label.all"/>",
		pleaseenterreportname:"<fmt:message key="label.pleaseenterreportname"/>",
        validvalues:"<fmt:message key="label.validvalues"/>",
        county:"<fmt:message key="label.county"/>",
        dateformat:"<fmt:message key="javascript.dateformat"/>"};
	// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setFacility();">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded.
	</div>
</c:if>

<tcmis:form action="/inventoryplanningreport.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
	<br><br><br><fmt:message key="label.pleasewait"/>
</div>
<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont filterContainer">
<div class="roundright">
<div class="roundtop">
	<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10"
											  class="corner_filter" style="display: none"/></div>
</div>
<div class="roundContent">
<!-- Insert all the search option within this div -->

<fieldset><legend><fmt:message key="label.searchfields"/></legend>
<table width="60%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
		<td class="optionTitleBoldRight">
			<fmt:message key="label.facility"/>:
		</td>
	
		<td class="optionTitleLeft">
			<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
			</select>
		</td>
	</tr>
	<tr>
		<td class="optionTitleBoldRight">
			<fmt:message key="label.county"/>:
		</td>
	
		<td class="optionTitleLeft">
			<select class="selectBox" id="county" name="county" onchange="countyChanged()">
			</select>
		</td>
	</tr>
	<tr>
		<td class="optionTitleBoldRight">
			<fmt:message key="label.thresholds"/>:
		</td>
	
		<td class="optionTitleLeft">
            <span id="solidThreshold"></span>
            &nbsp;
            <span id="solidThresholdUnit"></span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="liquidThreshold"></span>
            &nbsp;
            <span id="liquidThresholdUnit"></span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="gasThreshold"></span>
            &nbsp;
            <span id="gasThresholdUnit"></span>
		</td>
		
	</tr>
	<tr>
	<td class="optionTitleBoldRight">
			<fmt:message key="label.area"/>:
		</td>
	  <td  class="optionTitleBoldLeft">
		<select class="selectBox" id="areaListArray" size="7" multiple="multiple" name="areaListArray">
		</select>

	 </td>
	</tr>
	<tr>
	<td class="optionTitleBoldRight"><fmt:message key="label.reportdate"/>:</td>
	<td class="optionTitleBoldLeft">
		 <input type="text" readonly name="reportDate" id="reportDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}" />" 
         onClick="return getCalendar(document.inventoryPlanningReportForm.reportDate);" size="8" maxlength="10" class="inputBox pointer">
	</td>
	</tr>

    <tr>
        <td class="optionTitleBoldRight">
			<fmt:message key="label.reporttype"/>:
		</td>
		<td class="optionTitleBoldLeft">
            <input type="radio" class="radioBtns" id="reportType" name="reportType"  value="A" checked />
			<fmt:message key="label.annual"/>
            
            <input type="radio" class="radioBtns" id="reportType" name="reportType" value="C" />
			<fmt:message key="label.change"/>
		</td>
	</tr>
    <input type="hidden" name="trialRun" id="trialRun" value="N"/>
    <%--
    <tr>
        <td class="optionTitleBoldRight">
			<fmt:message key="label.trialrun"/>:
		</td>
		<td class="optionTitleBoldLeft">
			<input type="checkbox" class="radioBtns" id="trialRun" name="trialRun" value="Y" checked onclick="trialcheck(this);" />
			
		</td>
		
	</tr>
    <tr>
        <td class="optionTitleBoldRight">
			<fmt:message key="label.filetype"/>:
		</td>
		<td class="optionTitleBoldLeft">
			<input type="radio"  class="radioBtns" id="fileType" name="fileType" value="excel"  checked />
			<fmt:message key="label.excel"/>
		
			<!--<input type="radio"  class="radioBtns" id="fileType" name="fileType" value="ascii" />
			<fmt:message key="label.ascii"/>
		--></td>
	</tr>
    --%>
	
</table>



</fieldset>


<table width="80%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td class="optionTitleBoldLeft" colspan="4">
		<input type="radio"  class="radioBtns" id="reportGenerationType" name="reportGenerationType"
						value="INTERACTIVE" checked onclick="reportGeneratechecked(this);" />
		<fmt:message key="adhocusagereport.label.interactive"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio"  class="radioBtns" id="reportGenerationType" name="reportGenerationType"
		                value="BATCH"  onclick="reportGeneratechecked(this);"/>
		<fmt:message key="label.batch"/>
		&nbsp;&nbsp;&nbsp;
		<fmt:message key="adhocusagereport.label.reportname"/>:&nbsp;
		<input type="text" name="reportName" id="reportName" class="inputBox"/>
	</td>
</tr>

<tr>
	
	<td class="optionTitleLeft" colspan="4">

				<html:button property="submitReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="generateReport()">
			<fmt:message key="button.generatereport"/>
		</html:button>
	</td>
</tr>

</table>
<!-- End search options -->
</div>
<div class="roundbottom">
	<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner"
												  style="display: none"/></div>
</div>
</div>
</div>
</td>
</tr>
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
<input type="hidden" name="companyId" id="companyId" value=""/>
<input type="hidden" name="action" id="action" value="search"/>
<input type="hidden" name="reportGenerationType" id="reportGenerationType" value=""/>
<input type="hidden" name="defaultFacilityId" id="defaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>


<!-- Hidden elements end -->

</div>
<!-- close of contentArea -->

<!-- Footer message start -->
<div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>
