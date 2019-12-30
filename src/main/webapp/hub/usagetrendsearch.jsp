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
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

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
<script type="text/javascript" src="/js/hub/usagetrend.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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

<!-- Add any other javascript you need for the page here -->
<script language="javascript" type="text/javascript">
<!--
var altcompanyid = new Array(
<c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.companyId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.companyId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altfacilityid = new Array();
var altfacilityName = new Array();
<c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
<c:set var="currentCompanyId" value='${status.current.companyId}'/>
<c:set var="facilityInvoicePeriodObjBeanCollection" value='${status.current.facInvoicePeriodVar}'/>

<c:set var="facilityCount" value='${0}'/>
altfacilityid["<c:out value="${currentCompanyId}"/>"] = new Array(
 <c:forEach var="facilityInvoicePeriodObjBean" items="${facilityInvoicePeriodObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${facilityCount > 0}">
    ,"<c:out value="${status1.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="facilityCount" value='${facilityCount+1}'/>
  </c:forEach>
  );

<c:set var="facilityCount" value='${0}'/>
altfacilityName["<c:out value="${currentCompanyId}"/>"] = new Array(
 <c:forEach var="facilityInvoicePeriodObjBean" items="${facilityInvoicePeriodObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${facilityCount > 0}">
    ,"<c:out value="${status1.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="facilityCount" value='${facilityCount+1}'/>
  </c:forEach>
  );
 </c:forEach>


var altaniversaryDate = new Array();
var altinventoryGroup = new Array();
var altinventoryGroupName = new Array();
<c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
<c:set var="currentCompanyId" value='${status.current.companyId}'/>
<c:set var="facilityInvoicePeriodObjBeanCollection" value='${status.current.facInvoicePeriodVar}'/>

<c:forEach var="facilityInvoicePeriodObjBean" items="${facilityInvoicePeriodObjBeanCollection}" varStatus="status1">
<c:set var="currentFacilityId" value='${status1.current.facilityId}'/>
<c:set var="aniversaryDateObjBeanCollection" value='${status1.current.aniversaryDateVar}'/>
<c:set var="inventoryGroupObjBeanCollection" value='${status1.current.inventoryGroupVar}'/>

<c:set var="aniversaryDateCount" value='${0}'/>
altaniversaryDate["<c:out value="${currentFacilityId}"/>"] = new Array(
<c:forEach var="aniversaryDateObjBean" items="${aniversaryDateObjBeanCollection}" varStatus="status2">
<fmt:formatDate var="formattedaniversaryDate" value="${status2.current.aniversaryDate}" pattern="MM/dd/yyyy"/>
<c:choose>
 <c:when test="${aniversaryDateCount > 0}">
  ,"<c:out value="${formattedaniversaryDate}"/>"
 </c:when>
 <c:otherwise>
  "<c:out value="${formattedaniversaryDate}"/>"
 </c:otherwise>
</c:choose>
<c:set var="aniversaryDateCount" value='${aniversaryDateCount+1}'/>
</c:forEach>
);

altinventoryGroup["<c:out value="${currentFacilityId}"/>"] = new Array("All"
<c:forEach var="inventoryGroupObjBean" items="${inventoryGroupObjBeanCollection}" varStatus="invstatus">
,"<c:out value="${invstatus.current.inventoryGroup}"/>"
</c:forEach>
);

altinventoryGroupName["<c:out value="${currentFacilityId}"/>"] = new Array("All"
<c:forEach var="inventoryGroupObjBean" items="${inventoryGroupObjBeanCollection}" varStatus="invstatus">
,"<c:out value="${invstatus.current.inventoryGroupName}"/>"
</c:forEach>
);
</c:forEach>
</c:forEach>
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",pleaseselect:"<fmt:message key="label.pleaseselect"/>",
startdate:"<fmt:message key="usagetrend.label.startdate"/>",facilityid:"<fmt:message key="label.facilityid"/>",invoicedate:"<fmt:message key="label.invoicedate"/>",validvalues:"<fmt:message key="label.validvalues"/>",
all:"<fmt:message key="label.all"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/usagetrendresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.company"/>:&nbsp;
</td>
<td width="15%" class="optionTitleBoldLeft">
<c:set var="selectedCompanyId" value='${param.companyId}'/>
<select name="companyId" id="companyId" onchange="companyIdchanged()" class="selectBox">
  <c:forEach var="companyFacInvoiceDateOvBean" items="${companyFacInvoiceDateOvBeanColl}" varStatus="status">
  <c:set var="currentCompanyId" value='${status.current.companyId}'/>

  <c:choose>
   <c:when test="${empty selectedCompanyId}" >
    <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityInvoicePeriodObjBeanJspCollection" value='${status.current.facInvoicePeriodVar}'/>
   </c:when>
   <c:when test="${currentCompanyId == selectedCompanyId}" >
    <c:set var="facilityInvoicePeriodObjBeanJspCollection" value='${status.current.facInvoicePeriodVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentCompanyId == selectedCompanyId}">
    <option value="<c:out value="${currentCompanyId}"/>" selected><c:out value="${status.current.companyName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:&nbsp;
</td>

<td width="15%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<c:set var="facilityIdCount" value='${0}'/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityIdChanged()">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="facilityInvoicePeriodObjBean" items="${facilityInvoicePeriodObjBeanJspCollection}" varStatus="status">
  <c:set var="facilityIdCount" value='${facilityIdCount+1}'/>
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

  <c:choose>
   <c:when test="${empty selectedFacilityId}" >
   </c:when>
   <c:when test="${selectedFacilityId == currentFacilityId}" >
    <c:set var="aniversaryDateObjBeanJspCollection" value='${status.current.aniversaryDateVar}'/>
    <c:set var="inventoryGroupObjBeanJspCollection" value='${status.current.inventoryGroupVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${selectedFacilityId == currentFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
   </c:otherwise>
  </c:choose>

  </c:forEach>
<%--  <c:if test="${facilityIdCount == 0}">
   <option value="All"><fmt:message key="label.pleaseselect"/></option>
  </c:if>--%>
</select>
</td>
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="15%" class="optionTitleBoldLeft">
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>
 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
   <option value="All"><fmt:message key="label.all"/></option>
   <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupObjBeanJspCollection}" varStatus="status">
    <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
    <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${empty selectedIg}" >
        <c:set var="selectedIg" value=""/>
      </c:when>
      <c:when test="${currentIg == selectedIg}" >
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentIg == selectedIg}">
        <option value="<c:out value="${currentIg}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
 </select>
 </td>

<tr>
<td width="5%"class="optionTitleBoldRight">
<fmt:message key="usagetrend.label.startdate"/>:&nbsp;
</td>

<td width="15%" class="optionTitleBoldLeft">
<c:set var="selectedAniversaryDate" value='${param.aniversaryDate}'/>
<select name="aniversaryDate" id="aniversaryDate" class="selectBox">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
 <c:forEach var="aniversaryDateObjBean" items="${aniversaryDateObjBeanJspCollection}" varStatus="status">
 <c:set var="currentAniversaryDate" value='${status.current.aniversaryDate}'/>
  <fmt:formatDate var="formattedAniversaryDate" value="${status.current.aniversaryDate}" pattern="MM/dd/yyyy"/>

  <c:choose>
   <c:when test="${selectedAniversaryDate == formattedAniversaryDate}">
    <option value="<c:out value="${formattedAniversaryDate}"/>" selected><c:out value="${formattedAniversaryDate}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${formattedAniversaryDate}"/>"><c:out value="${formattedAniversaryDate}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

</tr>

<tr>
<td width="10%" colspan="2" class="optionTitleBoldLeft">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()">
     <fmt:message key="label.search"/>
</html:submit>

<html:submit property="buttonCreateExcel" styleId="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "generateExcel(); return false;">
     <fmt:message key="button.createexcelfile"/>
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
<input type="hidden" name="userAction" id="userAction" value="">
<input type="hidden" name="startSearchTime" id="startSearchTime" value="">
<input type="hidden" name="unitsOrDollars" id="unitsOrDollars" value="Y">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>