<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<%@ include file="/common/locale.jsp" %>
<tcmis:fontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/opsinvoiceinventorydetail.js"></script>

<title>
<fmt:message key="invoiceinventorydetail.title"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--

var companyColl = [];
var facilityColl = {};
var endDateColl = {
	"" : {
		"" : [
			{
				id: "",
				name: "<fmt:message key="label.pleaseselect"/>"
			}
		]
	}
};
<c:forEach var="companyBean" items="${companyFacInvoiceDateBeanColl}" varStatus="status">
	<tcmis:jsReplace var="curCompanyId" value="${companyBean.companyId}"/>
	companyColl.push(
		{
			id: "${curCompanyId}",
			name: "<tcmis:jsReplace value='${companyBean.companyName}'/>"
		}
	);
	
	facilityColl["${curCompanyId}"] = [
		{
			id: "",
			name: "<fmt:message key="label.pleaseselect"/>"
		}
	];
	
	endDateColl["${curCompanyId}"] = {
		"" : [
			{
				id: "",
				name: "<fmt:message key="label.pleaseselect"/>"
			}
		]
	}
	
	<c:forEach var="facilityBean" items="${companyBean.facilityColl}">
		<tcmis:jsReplace var="curFacilityId" value="${facilityBean.facilityId}"/>
		facilityColl["${curCompanyId}"].push(
			{
				id: "${curFacilityId}",
				name: "<tcmis:jsReplace value='${facilityBean.facilityName}'/>"
			}
		);
		
		endDateColl["${curCompanyId}"]["${curFacilityId}"] = [
			{
				id: "",
				name: "<fmt:message key="label.pleaseselect"/>"
			}
		];
		
		<c:forEach var="invoicePeriodBean" items="${facilityBean.invoicePeriodColl}">
			<fmt:formatDate var="formattedEndDate" value="${invoicePeriodBean.endDate}" pattern="${dateFormatPattern}"/>
			endDateColl["${curCompanyId}"]["${curFacilityId}"].push(
				{
					id: "<tcmis:jsReplace value='${invoicePeriodBean.invoicePeriod}'/>",
					name: "${formattedEndDate}"
				}
			);
		</c:forEach>
	</c:forEach>
</c:forEach>
// -->
</script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",pleaseselect:"<fmt:message key="label.pleaseselect"/>",
facilityid:"<fmt:message key="label.facilityid"/>",invoicedate:"<fmt:message key="label.invoicedate"/>",validvalues:"<fmt:message key="label.validvalues"/>",
all:"<fmt:message key="label.all"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize(); initializeDropDowns();">
<tcmis:form action="/opsinvoiceinventorydetailresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="searchMainPage">
<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
        <td width="5%" class="optionTitleBoldRight" nowrap="nowrap">
          <fmt:message key="label.company"/>:
        </td>
<td width="15%" class="optionTitleBoldLeft">
<select name="companyId" id="companyId" class="selectBox" onchange="companyChanged()"/>
</td>

<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
<c:set var="selectedUnitsOrDollars" value='${param.unitsOrDollars}'/>
<c:if test="${empty selectedUnitsOrDollars}">
<c:set var="selectedUnitsOrDollars" value='units'/>
</c:if>

<input type="radio" class="radioBtns" name="unitsOrDollars" id="unitsOrDollars" value="units" <c:if test="${selectedUnitsOrDollars == 'units'}">checked</c:if>/><fmt:message key="monthlyinventorydetail.label.units"/>

<input type="radio" class="radioBtns" name="unitsOrDollars" id="unitsOrDollars" value="dollars" <c:if test="${selectedUnitsOrDollars == 'dollars'}">checked</c:if>/><fmt:message key="monthlyinventorydetail.label.dollars"/>

</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight" nowrap="nowrap">
<fmt:message key="label.facility"/>:
</td>

<td width="15%" class="optionTitleBoldLeft">
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()"/>
</td>

<td width="10%" class="optionTitleBoldLeft">
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight" nowrap="nowrap">
<fmt:message key="monthlyinventorydetail.label.invoicedate"/>:
</td>

<td width="15%" class="optionTitleBoldLeft">
<select name="invoiceDate" id="invoiceDate" class="selectBox"/>
</td>

<td width="10%" class="optionTitleBoldLeft">
</td>
</tr>
<tr>
<td colspan="3" align="left" nowrap="nowrap">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return search()">
     <fmt:message key="label.search"/>
</html:submit>
<html:submit property="buttonCreateExcel" styleId="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "doexcelpopup(); return false;">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
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


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="userAction" id="userAction" value=""/>
<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
<input type="hidden" name="selectedCompanyId" id="selectedCompanyId" value="<tcmis:jsReplace value='${param.companyId}'/>"/>
<input type="hidden" name="selectedFacilityId" id="selectedFacilityId" value="<tcmis:jsReplace value='${param.facilityId}'/>"/>
<input type="hidden" name="selectedInvoiceDate" id="selectedInvoiceDate" value="<tcmis:jsReplace value='${param.invoiceDate}'/>"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>