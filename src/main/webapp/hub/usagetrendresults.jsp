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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/usagetrend.js"></script>

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

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="usagetrend.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
startdate:"<fmt:message key="usagetrend.label.startdate"/>",
facilityid:"<fmt:message key="label.facilityid"/>",
invoicedate:"<fmt:message key="label.invoicedate"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
all:"<fmt:message key="label.all"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload();">

<tcmis:form action="/usagetrendresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${usageTrendViewCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="biInventoryValueTotal"  value='${0}'/>
<c:set var="biHaasOwnedValueTotal"  value='${0}'/>
<c:set var="biCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="recInventoryValueTotal"  value='${0}'/>
<c:set var="recHaasOwnedValueTotal"  value='${0}'/>
<c:set var="recCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="eiInventoryValueTotal"  value='${0}'/>
<c:set var="eiHaasOwnedValueTotal"  value='${0}'/>
<c:set var="eiCustomerOwnedValueTotal"  value='${0}'/>
<c:set var="netUsageValueTotal"  value='${0}'/>
<c:set var="usageHaasOwnedValueTotal"  value='${0}'/>
<c:set var="usageCustomerOwnedValueTotal"  value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty usageTrendViewCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
<c:forEach var="usageTrendViewBean" items="${usageTrendViewCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:if test="${status.index % 10 == 0}">
<tr>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.inventorygroup"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.item"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.description"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.manufacturer"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.packaging"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.countuom"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.anualbaseline"/></th>
<th class="results" rowspan="2" width="5%"><fmt:message key="label.prioryear"/></th>
<th class="results" width="4%"  height="15" colspan="3"><fmt:message key="label.ytd"/></th>
<th class="results" width="4%"  height="15" colspan="12"><fmt:message key="label.current"/></th>
</tr>

<tr>
<th class="results" width="5%"><fmt:message key="label.bl"/></th>
<th class="results" width="5%"><fmt:message key="label.prioryear"/></th>
<th class="results" width="5%"><fmt:message key="label.curyear"/></th>

<c:set var="selectedAniversaryDate" value='${param.aniversaryDate}'/>
<tcmis:usageTrendHeadingTag reportStartDate="${selectedAniversaryDate}" />
<%--
<th class="results" width="5%">Month 1</th>
<th class="results" width="5%">Month 2</th>
<th class="results" width="5%">Month 3</th>
<th class="results" width="5%">Month 4</th>
<th class="results" width="5%">Month 5</th>
<th class="results" width="5%">Month 6</th>
<th class="results" width="5%">Month 7</th>
<th class="results" width="5%">Month 8</th>
<th class="results" width="5%">Month 9</th>
<th class="results" width="5%">Month 10</th>
<th class="results" width="5%">Month 11</th>
<th class="results" width="5%">Month 12</th>--%>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="rowClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="rowClass" value='alt'/>
  </c:otherwise>
</c:choose>

<tr class="<c:out value="${rowClass}"/>">

  <td width="5%"><c:out value="${status.current.inventoryGroup}"/></td>
  <td width="5%"><c:out value="${status.current.itemId}"/></td>
  <td width="5%"><c:out value="${status.current.itemDesc}"/></td>
  <td width="5%"><c:out value="${status.current.manufacturer}"/></td>
  <td width="5%"><c:out value="${status.current.packaging}"/></td>
  <td width="5%"><b><c:out value="${status.current.countUom}"/></b></td>

  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.baselineAnnualUsage}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.priorYearUsage}"/></fmt:formatNumber></td>

  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.baselineAnnualUsageYtd}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.priorYearUsageYtd}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.currentYearYtd}"/></fmt:formatNumber></td>

  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month0}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month1}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month2}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month3}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month4}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month5}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month6}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month7}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month8}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month9}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month10}"/></fmt:formatNumber></td>
  <td width="5%"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2"><c:out value="${status.current.month11}"/></fmt:formatNumber></td>
  </tr>
</c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty usageTrendViewCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>