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
<script type="text/javascript" src="/js/hub/distributedcountsearch.js"></script>

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
<fmt:message key="distributedcount.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
partno:"<fmt:message key="label.partno"/>",
inventoryGroup:"<fmt:message key="label.inventorygroup"/>",
item:"<fmt:message key="label.item"/>",
itemid:"<fmt:message key="label.itemid"/>",
validValues:"<fmt:message key="label.validvalues"/>",
hubMustBeSelected:"<fmt:message key="label.hubmustbeselected"/>",
dataChanged:"<fmt:message key="label.datachanged"/>",
changesLost:"<fmt:message key="label.changeslost"/>",
clickCancel:"<fmt:message key="label.clickcancel"/>",
distributedUsage:"<fmt:message key="distributedcount.label.distributedusage"/>",
distributedUsageGreater:"<fmt:message key="distributedcount.label.distributedusagegreater"/>"};

var althubid = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
<c:set var="currentHub" value='${status.current.branchPlant}'/>
<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

altinvid["<c:out value="${currentHub}"/>"] = new Array(""
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>

  );
altinvName["<c:out value="${currentHub}"/>"] = new Array("All"
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroupName}"/>"
  </c:forEach>

  );
 </c:forEach>
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/distributedcountresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="700" border="0" cellpadding="0" cellspacing="0">
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
 <td width="10%" class="optionTitleBoldRight">
  <fmt:message key="label.hub"/>:
 </td>
 <td width="15%" class="optionTitleBoldLeft">
 <c:set var="selectedHub" value='${param.sourceHub}'/>
 <c:set var="selectedHubName" value=''/>
 <select name="sourceHub" id="sourceHub" onchange="hubchanged()" class="selectBox">
  <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="currentHubName" value='${status.current.hubName}'/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount" facilityId="${currentHubName}">
  <c:choose>
   <c:when test="${empty selectedHub}" >
    <c:set var="selectedHub" value='${status.current.branchPlant}'/>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
    <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
   </c:when>
   <c:when test="${currentHub == selectedHub}" >
    <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentHub == selectedHub}">
    <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
   </c:otherwise>
  </c:choose>
  </tcmis:inventoryGroupPermission>
  </c:forEach>
 </select>
 </td>
 <td width="20%">
&nbsp;
 </td>
 <td width="10%">
&nbsp;
 </td>
</tr>

<tr>
 <td width="10%" class="optionTitleBoldRight" nowrap>
  <fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="15%" class="optionTitleBoldLeft">
 <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>
 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
  <option value="All"><fmt:message key="label.all"/></option>
  <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
    <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
    <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${empty selectedInventoryGroup}" >
        <c:set var="selectedInventoryGroup" value=""/>
      </c:when>
      <c:when test="${currentIg == selectedInventoryGroup}" >
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentIg == selectedInventoryGroup}">
        <option value="<c:out value="${currentIg}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
 </td>

  <td width="10%" class="optionTitleBoldRight" nowrap>
  <fmt:message key="label.search"/>:
 </td>
 <td width="20%" class="optionTitleBoldLeft" nowrap>
  <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox" onchange="checkItemId()">
    <html:option value="itemId" key="label.itemid"/>
    <html:option value="catPartNo" key="label.partnumber"/>
  </html:select>
  <html:select property="searchType" styleId="searchType" styleClass="selectBox">
    <html:option value="is" key="label.is"/>
    <html:option value="contains" key="label.contain"/>
  </html:select>
<%-- </td>
 <td width="10%" class="optionTitleBoldLeft"> --%> 
   <html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
 </td>
</tr>

<tr>
<td colspan="4" class="optionTitleBoldLeft">
<html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm()">
     <fmt:message key="label.search"/>
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

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>