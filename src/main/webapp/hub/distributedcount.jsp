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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss currentCss="haasGlobalAjaxGrid.css"/>

<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/css/useApproval.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<script SRC="/js/hub/distributedcount.js" language="JavaScript"></script>
<script SRC="/js/hub/distributedcountajax.js" language="JavaScript"></script>
<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>

<%--<script src="/dhtmlxGrid/js/dhtmlXCommon.js"></script>
<script src="/dhtmlxGrid/js/dhtmlXGrid.js"></script>
<script src="/dhtmlxGrid/js/dhtmlXGridCell.js"></script>
<script src="/dhtmlxGrid/js/dhtmlXGrid_excell_mro.js"></script>--%>

<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
<%--<script type="text/javascript" src="/yui/build/connection/connection.js" ></script>

<link rel="stylesheet" type="text/css" href="/yui/build/fonts/fonts.css" />
<link rel="stylesheet" type="text/css" href="/css/example.css" />
--%>

<title>
<fmt:message key="distributedcount.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
partno:"<fmt:message key="label.partno"/>",inventoryGroup:"<fmt:message key="label.inventorygroup"/>",item:"<fmt:message key="label.item"/>",validValues:"<fmt:message key="label.validvalues"/>",hubMustBeSelected:"<fmt:message key="label.hubmustbeselected"/>",
dataChanged:"<fmt:message key="label.datachanged"/>",changesLost:"<fmt:message key="label.changeslost"/>",clickCancel:"<fmt:message key="label.clickcancel"/>",
distributedUsage:"<fmt:message key="distributedcount.label.distributedusage"/>",distributedUsageGreater:"<fmt:message key="distributedcount.label.distributedusagegreater"/>"};

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

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitUpdate" value='${param.submitUpdate}'/>
<c:set var="submitSave" value='${param.submitSave}'/>

<c:choose>
  <c:when test="${!empty submitUpdate || !empty submitSave}" >
    <body onload="buildFirstSetRowsAfterUpdate()" onresize="resizeGrid()">
  </c:when>
  <c:otherwise>
   <body onresize="resizeGrid()">
  </c:otherwise>
</c:choose>


<tcmis:form action="/distributedcount.do" onsubmit="return SubmitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
</div>

<div class="interface" id="mainPage" style="overflow:hidden;">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table border=0 width=100% >
  <tr>
  <td width="200">
  <img src="/images/tcmtitlegif.gif" border=0 align="left">
  </td>
  <td>
   <img src="/images/tcmistcmis32.gif" border=0 align="right">
  </td>
  </tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="distributedcount.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="80%" border="0" cellpadding="0" cellspacing="0">
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
 <td width="10%" class="optionTitleBoldRight">
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

 <td width="20%" class="optionTitleBoldRight">
  <html:select property="searchWhat" styleClass="selectBox" onchange="checkItemId()">
    <html:option value="itemId" key="label.itemid"/>
    <html:option value="catPartNo" key="label.partnumber"/>
  </html:select>
  <html:select property="searchType" styleClass="selectBox">
    <html:option value="is" key="label.is"/>
    <html:option value="contains" key="label.contain"/>
  </html:select>
 </td>
 <td width="10%" class="optionTitleBoldLeft">
   <html:text property="searchText" size="20" styleClass="inputBox"/>
 </td>
</tr>

<tr>
<td colspan="4" class="optionTitleBoldLeft">
<html:button property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
     <fmt:message key="label.search"/>
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
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:3;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">
<c:set var="errorMessage" value="${errorMessage}"/>
<c:out value="${errorMessage}"/>
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<table id="resultsMaskTable" style="display:none;" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
 <div class="boxhead">
  <div id="distributedCountUpdates">
  <a id="submitSaveButton" style="" href="javascript:save()"><fmt:message key="label.save"/></a>
  |
  <a id="submitUpdateButton" style="" href="javascript:update()"><fmt:message key="label.process"/></a>
 </div>
  <div class="dataContent">

<div id="parentGridBox" width="100%">
<div id="gridbox" width="100%" height="300px">
</div>
</div>

</div>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</tr></td>
</table>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
 <div id="invisibleElements" style="display: none;"></div>
<input type="hidden" name="selectedHub" id="selectedHub" value="<c:out value="${selectedHub}"/>">
<input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="<c:out value="${selectedInventoryGroup}"/>">
<input type="hidden" name="submitUpdate" id="submitUpdate" value="">
<input type="hidden" name="submitSave" id="submitSave" value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
<!-- Footer message start -->
<div id="footer" class="messageBar"></div>
<!-- Footer message end -->
</div> <!-- close of interface -->

</tcmis:form>

<c:if test="${!empty submitUpdate || !empty submitSave}">
<script type="text/javascript">
<!--
<c:set var="hubItemCountPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount" facilityId="${selectedHubName}">
 <c:set var="hubItemCountPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>
<%--hubItemCountPermission="<c:out value="${hubItemCountPermission}"/>";
<c:set var="hubItemCountPermission" value='Yes'/>--%>
hubItemCountPermission="<c:out value="${hubItemCountPermission}"/>";

headerString = "<fmt:message key="label.inventorygroup"/>,<fmt:message key="label.catalog"/>,<fmt:message key="label.partnumber"/>,";
headerString +="<fmt:message key="label.partdescription"/>,<fmt:message key="label.ok"/><br><input type=\"checkbox\" name=\"updateAll\" ID=\"updateAll\" value=\"Yes\" onclick=\"updateAllRows()\">,<fmt:message key="label.item"/>,";
headerString +="<fmt:message key="label.usage"/>,<fmt:message key="label.countuom"/>,";
headerString +="<fmt:message key="distributedcount.label.usingfacility"/>,<fmt:message key="distributedcount.label.usingworkarea"/>,<fmt:message key="distributedcount.label.distributedusage"/>";
window.parent.headerString = headerString;

<c:set var="dataCount" value='${0}'/>
<c:forEach var="distributedCountUsageViewBean" items="${distributedCountUsageViewRelationBeanCollection}" varStatus="status">
distributedCountData[<c:out value="${dataCount}"/>] = {hub:"<c:out value="${status.current.hub}"/>",inventoryGroup:"<c:out value="${status.current.inventoryGroup}"/>",catalogId:"<c:out value="${status.current.catalogId}"/>",
catPartNo:"<c:out value="${status.current.catPartNo}"/>",partDescription:"<c:out value="${status.current.partDescription}"/>",rowSpan:"<c:out value="${status.current.rowSpan}"/>",
<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
itemcollection:[
<c:forEach var="distributedCountUsageViewItemBean" items="${itemCollection}" varStatus="status1">
  <c:set var="workAreaCollection"  value='${status1.current.workAreaCollection}'/>
  <bean:size id="workAreaSize" name="workAreaCollection"/>
   <c:if test="${status1.index > 0 && listSize > 1 }">
   ,
   </c:if>
  {workAreaSize:"<c:out value="${workAreaSize}"/>",itemId:"<c:out value="${status1.current.itemId}"/>",usage:"<c:out value="${status1.current.usage}"/>",
   uom:"<c:out value="${status1.current.uom}"/>",active:"<img src=/images/item_chk0.gif border=0 alt=OK onclick=\"javascript:checkImageClicked=true;\" align=>",
   workAreaCollection:[
  <c:forEach var="wistributedCountUsageViewWorkAreaBean" items="${workAreaCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && workAreaSize > 1 }">
     ,
    </c:if>
    {facilityId:"<c:out value="${status2.current.facilityId}"/>",application:"<c:out value="${status2.current.application}"/>",uomDistributedUsage:"<c:out value="${status2.current.uomDistributedUsage}"/>"}
  </c:forEach>
  ]}
 </c:forEach>
]};
<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

totalRows = <c:out value="${dataCount}"/>;
endingRowNumber = 0;
rowCount = 0;
lastSearchInventoryGroup="<c:out value="${selectedInventoryGroup}"/>";

<c:set var="errorMessage" value="${errorMessage}"/>
<c:choose>
  <c:when test="${empty errorMessage}">
   showErrorMessage = false;
  </c:when>
  <c:otherwise>
   showErrorMessage = true;
  </c:otherwise>
</c:choose>

//-->
</script>
</c:if>
</body>
</html:html>

