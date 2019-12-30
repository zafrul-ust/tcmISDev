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
<%-- this version based on template.jsp --%>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
<tcmis:fontSizeCss />
<%-- CSS for YUI --%>
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<%-- Add any other stylesheets you need for the page here --%>
<link rel="SHORTCUT ICON" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%-- This handles which key press events are disabeled on this page --%>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handles the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/supply/customerbuyorders.js" ></script>
<script type="text/javascript" src="/js/common/formchek.js" ></script>


<title>
<fmt:message key="purchaserequests.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">

<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
</script>
</head>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <body bgcolor="#FFFFFF" text="#000000" link="#FFFFFF" alink="" vlink="#FFFF66" onload="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <body bgcolor="#FFFFFF" text="#000000" link="#FFFFFF" alink="" vlink="#FFFF66">
  </c:otherwise>
</c:choose>



<c:set var="purchaserequestsLinkPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing">
 <c:set var="purchaserequestsLinkPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="readonlyCustomerPurchasing">
 <c:set var="purchaserequestsLinkPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>
<c:choose>
<c:when test="${purchaserequestsLinkPermission == 'Yes'}">

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitUpdate" value='${param.submitUpdate}'/>
<c:set var="submitCreatePo" value='${param.submitCreatePo}'/>
<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>

<body bgcolor="#ffffff">


<tcmis:form action="/buyorders.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<%@ include file="/common/clientnavigation.jsp" %>
</div>

<div class="contentArea">

<%-- Search Option Begins --%>
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr class="alignCenter">
 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.buyer"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
  <select name="buyerId" id="buyerId" class="selectBox">
  <c:set var="selectedBuyerId" value='${param.buyerId}'/>
  <c:if test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty selectedBuyerId}">
   <c:set var="selectedBuyerId" value='${sessionScope.personnelBean.personnelId}'/>
  </c:if>

  <option value=""><fmt:message key="label.anybuyer"/></option>
  <c:choose>
   <c:when test="${-1 == selectedBuyerId}">
    <option value="-1" selected><fmt:message key="purchaserequests.label.nobuyerassigned"/></option>
   </c:when>
   <c:otherwise>
    <option value="-1"><fmt:message key="purchaserequests.label.nobuyerassigned"/></option>
   </c:otherwise>
  </c:choose>

  <c:forEach var="personnelNameUserGroupViewBean" items="${vvBuyerCollection}" varStatus="statusPersonnel">
  <c:set var="currentBuyerId" value='${statusPersonnel.current.personnelId}'/>
  <c:choose>
   <c:when test="${currentBuyerId == selectedBuyerId}">
    <option value="${currentBuyerId}" selected>${statusPersonnel.current.name}</option>
   </c:when>
   <c:otherwise>
    <option value="${currentBuyerId}">${statusPersonnel.current.name}</option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
  </select>
 </td>

 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="label.search"/>:
 </td>

 <td width="10%" class="optionTitleLeft">
  <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
   <html:option value="RAYTHEON_PO" key="label.customerpo"/>
   <html:option value="ITEM_ID" key="label.itemid"/>
   <html:option value="MR_NUMBER" key="label.mrnumber"/>
   <html:option value="MFG_ID" key="label.manufacturer"/>
   <html:option value="PR_NUMBER" key="label.requestno"/>
  </html:select>
   <html:select property="searchType" styleId="searchType" styleClass="selectBox">
      <html:option value="is" key="label.is"/>"
      <html:option value="contains" key="label.contains"/>"
    </html:select>
 </td>
 <td width="5%" colspan="2" class="optionTitleBoldLeft">
  <fmt:message key="label.for"/><html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
 </td>

 <td width="5%" class="optionTitleBoldLeft">
  <html:submit property="submitSearch" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
   <fmt:message key="label.search"/>
  </html:submit>
 </td>
</tr>

<tr class="alignCenter">
<td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>

 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
  <%--<option value=""><fmt:message key="label.all"/></option> --%>
  <c:forEach var="personnelNameUserGroupViewBean" items="${vvInventoryGroupCollection}" varStatus="status">
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
        <option value="${currentIg}" selected>${status.current.inventoryGroup}</option>
      </c:when>
      <c:otherwise>
        <option value="${currentIg}">${status.current.inventoryGroup}</option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
 </td>

 <td width="5%" class="optionTitleRight" >
  <c:choose>
  <c:when test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty submitCreateReport}">
   <input type="checkbox" name="showOnlyOpenBuys" id="showOnlyOpenBuys" value="Yes" checked class="radioBtns">
  </c:when>
  <c:otherwise>
   <html:checkbox property="showOnlyOpenBuys" styleId="showOnlyOpenBuys" value="Yes" styleClass="radioBtns"/>
  </c:otherwise>
  </c:choose>
 </td>
 <td width="5%" class="optionTitleLeft">
   <fmt:message key="purchaserequests.label.showonlyopenbuys"/>
 </td>

 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="label.sortby"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
  <html:select property="sort" styleId="sort" styleClass="selectBox">
   <html:option value="itemId,dateIssued" key="purchaserequests.sort.itemcreated"/>
   <html:option value="facility,status" key="purchaserequests.sort.facilitystatus"/>
   <html:option value="itemId,needDate" key="purchaserequests.sort.itempromised"/>
  </html:select>

 </td>

<c:set var="purchaserequestsPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing">
  <c:set var="purchaserequestsPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>

 <td width="5%" class="alignLeft" >
   <c:if test="${purchaserequestsPermission == 'Yes'}">
    <html:submit property="submitUpdate" styleId="submitUpdate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
      <fmt:message key="label.update"/>
    </html:submit>
   </c:if>
 </td>
</tr>
</table>

   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<%-- Search Option Ends --%>

<br><face="Arial" color="#fc0303"><a href="javascript:showpurchaserequestspagelegend()" style="color:#0000ff;cursor:pointer"><fmt:message key="label.legend"/></a><br>
<input type="hidden" name="supplyPath" id="supplyPath" value="Customer">

<c:if test="${!empty errorMessage}">
 ${errorMessage}
</c:if>

<c:if test="${empty errorMessage && !empty submitUpdate}">
 <center><fmt:message key="purchaserequests.label.successmessage"/></center>
</c:if>

<%-- end from-old --%>

<div class="spacerY">&nbsp;</div>

<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<%-- Error Messages Ends --%>

					<%-- * * * * * * RESULTS SECTION * * * * * *  --%>

<c:if test="${associatePrViewBeanCollection != null}" >
<%-- Search results start --%>
<%-- If you want your results section to span only 50% set this to 50% and your main table will be 100% --%>
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <%-- TEMPLATE EXAMPLE CODE: <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div> --%>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
												<%-- * * * * * * START OF RESULTS FOR-EACH BLOCK * * * * * * --%>


 <c:forEach var="associatePrViewBean" items="${associatePrViewBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr>
	   <th width="2%" ><fmt:message key="label.ok"/></th>
	   <th width="5%" ><fmt:message key="label.created"/></th>
	   <th width="5%" ><fmt:message key="label.requestno"/></th>
	   <th width="5%" ><fmt:message key="label.itemid"/></th>
	   <th width="15%" ><fmt:message key="label.itemdesc"/></th>
	   <th width="2%" ><fmt:message key="label.requestedqty"/></th>
	   <th width="5%" ><fmt:message key="label.pkg"/></th>
	   <th width="2%" ><fmt:message key="label.inventorygroup"/></th>
	   <th width="5%" ><fmt:message key="label.po"/></th>
 	   <th width="5%" ><fmt:message key="label.qty"/></th>
   </tr>
  </c:if>

    <c:choose>
  	    <c:when test="${status.index % 2 == 0}" >
  	    	<c:set var="colorClass" value=''/>
 	    </c:when>
	     <c:otherwise>
      		<c:set var="colorClass" value='alt'/>
     	    </c:otherwise>
   	 </c:choose>

  <c:set var="createPoPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="createPoPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <c:set var="critical" value='${status.current.critical}'/>
  <c:set var="priority" value='${status.current.priority}'/>
  <c:set var="engineeringEvaluation" value='${status.current.engineeringEvaluation}'/>
  <c:set var="cancelStatus" value='${status.current.cancelStatus}'/>
  <c:set var="requestLineStatus" value='${status.current.requestLineStatus}'/>
  <c:set var="buystatus" value='${status.current.status}'/>

  <c:if test="${priority == '1'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${priority == '2'}">
   <c:set var="colorClass" value='yellow'/>
  </c:if>

  <c:if test="${priority == '3'}">
   <c:set var="colorClass" value='green'/>
  </c:if>

  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${critical == 'S' || critical == 's'}">
   <c:set var="colorClass" value='pink'/>
  </c:if>

  <c:if test="${engineeringEvaluation == 'Y' || engineeringEvaluation == 'y'}">
   <c:set var="colorClass" value='purple'/>
  </c:if>

  <c:if test="${cancelStatus == 'rqcancel' || cancelStatus == 'canceled' || requestLineStatus == 'Cancelled' || requestLineStatus == 'Pending Cancellation' || buystatus == 'Closed' || buystatus == 'Cancel'}">
   <c:set var="colorClass" value='black'/>
   <c:set var="createPoPermission" value='No'/>
  </c:if>

  <%--<c:if test="${buystatus == 'Confirmed'}">
   <c:set var="createPoPermission" value='No'/>
  </c:if>--%>

  <tr class="${colorClass}" id="rowId${status.index}">

  <input type="hidden" name="associatePrViewBean[${dataCount}].prNumber" id="prNumber${dataCount}" value="${status.current.prNumber}">
  <input type="hidden" name="associatePrViewBean[${dataCount}].branchPlant" id="branchPlant${dataCount}" value="${status.current.branchPlant}">
  <input type="hidden" name="associatePrViewBean[${dataCount}].inventoryGroup" id="inventoryGroup${dataCount}" value="${status.current.inventoryGroup}">

  <c:choose>
  <c:when test="${createPoPermission == 'Yes'}">
  <td width="2%">
   <c:if test="${status.current.ok != null}" >
   <input type="checkbox" name="associatePrViewBean[${dataCount}].ok" id="ok${dataCount}" value="${dataCount}" checked onclick="checkBuyorder(${dataCount})" onchange="checkBuyorder(${dataCount})">
   </c:if>
   <c:if test="${status.current.ok == null}" >
   <input type="checkbox" name="associatePrViewBean[${dataCount}].ok" id="ok${dataCount}" value="${dataCount}" onclick="checkBuyorder(${dataCount})" onchange="checkBuyorder(${dataCount})" >
   </c:if>
  </td>
  <%--<TD width="5%"><A HREF="javascript:showPreviousPos(${dataCount})">${status.current.itemId}</A></TD>--%>
  <fmt:formatDate var="createdDate" value="${status.current.dateIssued}" pattern="MM/dd/yyyy hh:mm"/>
  <td width="5%">${createdDate}</td>
  <td width="5%">${status.current.prNumber}</td>
  <td width="5%">${status.current.itemId}</td>
  <td width="15%">${status.current.itemDesc}</td>
  <td width="2%">${status.current.orderQuantity}</td>
  <td width="5%">${status.current.sizeUnit}</td>
  <td width="2%">${status.current.inventoryGroup}</td>
  <td width="5%">
   <input type="text" name="associatePrViewBean[${dataCount}].raytheonPo" id="raytheonPo${dataCount}" value="${status.current.raytheonPo}" size="15" maxlength="60" class="inputBox">
  </td>
  <td width="5%">
   <input type="text" name="associatePrViewBean[${dataCount}].orderQuantity" id="orderQuantity${dataCount}" value="${status.current.orderQuantity}" size="4" maxlength="10" class="inputBox">
  </td>
  <%--<fmt:formatDate var="promisedDate" value="${status.current.needDate}" pattern="MM/dd/yyyy"/>
  <TD width="5%">
  <INPUT TYPE="text" name="associatePrViewBean[${dataCount}].promisedDate" ID="promisedDate${dataCount}" value="${promisedDate}" size="8" maxlength="10" Class="DETAILS"><FONT SIZE="4"><a href="javascript: void(0);" ID="linkpromisedDate${dataCount}" onClick="return getCalendar(document.buyOrderForm.promisedDate${dataCount});">&diams;</a></FONT>
  </TD>--%>
  </c:when>
  <c:otherwise>
   <td width="2%">&nbsp;</td>
   <td width="5%"><a href="javascript:showPreviousPos(${dataCount})">${status.current.itemId}</a></td>
   <td width="15%">${status.current.itemDesc}</td>
   <td width="5%">${status.current.orderQuantity}</td>
   <td width="5%">${status.current.sizeUnit}</td>
   <td width="2%">${status.current.inventoryGroup}</td>
   <td width="5%">${status.current.raytheonPo}</td>
   <td width="2%">&nbsp;</td>
  </c:otherwise>
  </c:choose>
 </tr>
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>


	 					<%-- * * * * * * END OF RESULTS FOR-EACH BLOCK * * * * * * --%>

   </table>
									<%-- * * * * * * WRAP-UP of SEARCH RESULTS SECTION * * * * * *  --%>
   <%-- If the collection is empty say no data found --%>
   <c:if test="${empty associatePrViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>

</c:if>								<%-- * * * * * * END OF SEARCH RESULTS SECTION * * * * * *  --%>

<%-- Hidden element start --%>
 <div id="hiddenElements" style="display: none;"></div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>

</div> <%-- close of interface --%>




</tcmis:form>
</DIV>
</c:when>
<c:otherwise>
 <fmt:message key="error.noaccesstopage"/>
</c:otherwise>
</c:choose>
</body>
</html:html>