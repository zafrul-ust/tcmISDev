<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic-el.tld" prefix="logic-el" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html-el:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss currentCss="global.css"/>
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
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<SCRIPT SRC="/js/supply/purchaseOrders.js" LANGUAGE="JavaScript"></SCRIPT>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<title>
<fmt:message key="purchaseorders.label.title"/>
</title>
<%--
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
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
</SCRIPT>
--%>
</HEAD>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup}" >
   <BODY onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <BODY>
  </c:otherwise>
</c:choose>

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitUpdate" value='${param.submitUpdate}'/>
<c:set var="submitCreatePo" value='${param.submitCreatePo}'/>
<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="purchaseorders.label.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<html-el:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html-el:link>
</TD>
</TR>
</TABLE>
</div>
<html-el:form action="/showpurchaseorders.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER="2" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">
 <TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
  <B><fmt:message key="label.buyer"/>:</B>
 </TD>
 <TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
  <select name="buyerId" CLASS="HEADER">
  <c:set var="selectedBuyerId" value='${param.buyerId}'/>
  <c:if test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty selectedBuyerId}">
   <c:set var="selectedBuyerId" value='${sessionScope.personnelBean.personnelId}'/>
  </c:if>

  <option value=""><fmt:message key="label.anybuyer"/></option>
  <%--<c:choose>
   <c:when test="${-1 == selectedBuyerId}">
    <option value="-1" selected><fmt:message key="purchaserequests.label.nobuyerassigned"/></option>
   </c:when>
   <c:otherwise>
    <option value="-1"><fmt:message key="purchaserequests.label.nobuyerassigned"/></option>
   </c:otherwise>
  </c:choose>--%>

  <c:forEach var="personnelNameUserGroupViewBean" items="${vvBuyerCollection}" varStatus="statusPersonnel">
  <c:set var="currentBuyerId" value='${statusPersonnel.current.personnelId}'/>
  <c:choose>
   <c:when test="${currentBuyerId == selectedBuyerId}">
    <option value="<c:out value="${currentBuyerId}"/>" selected><c:out value="${statusPersonnel.current.name}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentBuyerId}"/>"><c:out value="${statusPersonnel.current.name}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
  </select>
 </TD>

 <TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
   <B><fmt:message key="label.search"/>:</B>
 </TD>

 <TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
  <html-el:select property="searchWhat" styleClass="HEADER">
   <html-el:option value="RAYTHEON_PO" key="label.customerpo"/>
   <html-el:option value="ITEM_ID" key="label.itemid"/>
   <html-el:option value="MR_NUMBER" key="label.mr"/>
   <html-el:option value="MFG_ID" key="label.manufacturer"/>
   <html-el:option value="PR_NUMBER" key="label.prnumber"/>
  </html-el:select>
   <html-el:select property="searchType" styleClass="HEADER">
      <html-el:option value="is"/>
      <html-el:option value="contains"/>
    </html-el:select>
 </TD>
 <TD WIDTH="5%" ALIGN="LEFT" COLSPAN="2" CLASS="announce">
  <B>For<html-el:text property="searchText" size="20" styleClass="HEADER"/></B>
 </TD>

 <TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
  <html-el:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
   <fmt:message key="label.search"/>
  </html-el:submit>
 </TD>
</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
 <B><fmt:message key="label.inventorygroup"/>:</B>
 </TD>
 <TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>

 <select name="inventoryGroup" Class="HEADER">
  <%-- <option value=""><fmt:message key="label.all"/></option> --%>
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
        <option value="<c:out value="${currentIg}"/>" selected><c:out value="${status.current.inventoryGroup}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroup}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
 </TD>

 <TD WIDTH="5%" ALIGN="RIGHT" COLSPAN="4" CLASS="announce">
 &nbsp;
  <%--<html-el:checkbox property="showOnlyOpenBuys" value="Yes" styleClass="HEADER"/>
 </TD>
 <TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
   <B><fmt:message key="purchaserequests.label.showonlyopenbuys"/>:</B>
 </TD>

 <TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
   <B><fmt:message key="label.sort"/>:</B>
 </TD>
 <TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
  <html-el:select property="sort" styleClass="HEADER">
   <html-el:option value="dateIssued" key="purchaserequests.label.created"/>
   <html-el:option value="facility" key="label.facility"/>
   <html-el:option value="itemId" key="label.itemid"/>
   <html-el:option value="needDate" key="purchaserequests.label.needdate"/>
   <html-el:option value="status" key="label.status"/>
  </html-el:select>

 <%--<c:set var="selectedSortBy" value='${param.sort}'/>
 <select name="sort" CLASS="HEADER">
  <option value="All"><fmt:message key="main.all"/></option>
  <c:forEach var="vvBuypageSortBean" items="${vvBuypageSortBeanCollection}" varStatus="sortStatus">
  <c:set var="currentSortBy" value='${sortStatus.current.sortId}'/>

  <c:choose>
   <c:when test="${currentSortBy == selectedSortBy}">
    <option value="<c:out value="${currentSortBy}"/>" selected><c:out value="${sortStatus.current.sortDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentSortBy}"/>"><c:out value="${sortStatus.current.sortDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>--%>
 </TD>
<INPUT TYPE="hidden" NAME="sort" value="raytheonPo">

<c:set var="purchaserequestsPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing">
  <c:set var="purchaserequestsPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>

 <TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
   <c:if test="${purchaserequestsPermission == 'Yes'}">
    <html-el:submit property="submitUpdate" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
      <fmt:message key="label.update"/>
    </html-el:submit>
   </c:if>
 </TD>
</TR>
<%--
<TR VALIGN="MIDDLE">
<TD WIDTH="10%" COLSPAN="6" ALIGN="RIGHT" CLASS="announce">
 &nbsp;
 </TD>
 <TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
<c:if test="${purchaserequestsPermission == 'Yes'}">
 <html-el:submit property="submitCreatePo" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="purchaserequests.button.createPo"/>
 </html-el:submit>
</c:if>
 </TD>
</TR>
--%>
</TABLE>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html-el:errors/>
<BR>

<c:if test="${empty submitSearch && empty submitUpdate && empty submitCreatePo && empty submitCreateReport}">
  <I><fmt:message key="label.proceed"/></I>
</c:if>
</TD>
</TR>
</TABLE>

<INPUT TYPE="hidden" NAME="supplyPath" value="Customer">
<c:if test="${!empty errorMessage}">
 <c:out value="${errorMessage}"/>
</c:if>

<c:if test="${empty errorMessage && !empty submitUpdate}">
 <CENTER><fmt:message key="purchaserequests.label.successmessag"/></CENTER>
</c:if>

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%" CLASS="moveup">
 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>

 <c:forEach var="poHeaderViewBean" items="${poHeaderViewBeanCollection}" varStatus="status">
 <c:set var="dataCount" value='${dataCount+1}'/>
  <c:if test="${status.index % 10 == 0}">
   <TR align="center">
   <TH width="2%" height="38"><fmt:message key="label.po"/></TH>
   <TH width="2%" height="38"><fmt:message key="label.inventorygroup"/></TH>
   <TH width="2%" height="38"><fmt:message key="label.buyer"/></TH>
   <TH width="5%" height="38"><fmt:message key="label.itemid"/></TH>
   <TH width="15%" height="38"><fmt:message key="label.itemdesc"/></TH>
   <TH width="5%" height="38"><fmt:message key="label.qty"/></TH>
   <TH width="5%" height="38"><fmt:message key="label.pkg"/></TH>
   <TH width="2%" height="38"><fmt:message key="label.dateconfirmed"/></TH>
   </TR>
  </c:if>

  <c:choose>
   <c:when test="${status.index % 2 == 0}" >
    <c:set var="colorClass" value='white'/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='blue'/>
   </c:otherwise>
  </c:choose>

  <c:set var="createPoPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="customerPurchasing" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="createPoPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <c:set var="critical" value='${status.current.critical}'/>
  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${critical == 'S' || critical == 's'}">
   <c:set var="colorClass" value='pink'/>
  </c:if>

  <c:set var="poLineCollection"  value='${status.current.poLineCollection}'/>
  <bean-el:size id="poLineSize" name="poLineCollection"/>

  <c:choose>
  <c:when test="${poLineSize > 0 && !empty poLineCollection}">
  <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">

  <TD width="5%" rowspan="<c:out value="${poLineSize}"/>"><c:out value="${status.current.customerPo}"/></TD>
  <TD width="5%" rowspan="<c:out value="${poLineSize}"/>"><c:out value="${status.current.inventoryGroup}"/></TD>
  <TD width="5%" rowspan="<c:out value="${poLineSize}"/>"><c:out value="${status.current.buyerName}"/></TD>

  <c:forEach var="poLineDetailViewBean" items="${poLineCollection}" varStatus="lineStatus">
   <c:if test="${lineStatus.index > 0 && listSize > 1 }">
     <%--<c:set var="lineUpdateStatus" value='${lineStatus.current.updateStatus}'/>
     <c:if test="${lineUpdateStatus == 'NO' || lineUpdateStatus == 'Error'}">
      <c:set var="colorClass" value='error'/>
     </c:if>--%>
     <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${lineStatus.index}"/>">
   </c:if>

   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].radianPo" ID="radianPo<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.radianPo}"/>">
   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].poLine" ID="poLine<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.poLine}"/>">
   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].amendment" ID="amendment<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.amendment}"/>">
   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].itemId" ID="itemId<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.itemId}"/>">
   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].poLineStatus" ID="poLineStatus<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.poLineStatus}"/>">
   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].quantityReceived" ID="quantityReceived<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.quantityReceived}"/>">
   <INPUT TYPE="hidden" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].lineChangeStatus" ID="lineChangeStatus<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.lineChangeStatus}"/>">

   <TD width="5%"><c:out value="${lineStatus.current.itemId}"/></TD>
   <TD width="5%"><c:out value="${lineStatus.current.itemDesc}"/></TD>
   <TD width="5%">
   <c:choose>
   <c:when test="${createPoPermission == 'Yes'}">
    <INPUT TYPE="text" name="poLineDetailViewBean[<c:out value="${dataCount+listSize-1}"/>].quantity" ID="quantity<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${lineStatus.current.quantity}"/>" size="6" maxlength="30" Class="DETAILS" onChange="quantityChanged(<c:out value="${dataCount+listSize-1}"/>)">
   </c:when>
   <c:otherwise>
    <c:out value="${lineStatus.current.quantity}"/>
   </c:otherwise>
   </c:choose>
   </TD>
   <TD width="5%"><c:out value="${lineStatus.current.packaging}"/></TD>
   <fmt:formatDate var="formattedDateConfirmed" value="${lineStatus.current.dateConfirmed}" pattern="MM/dd/yyyy"/>
   <TD width="5%"><c:out value="${formattedDateConfirmed}"/></TD>
  </c:forEach>
  </TR>
  </c:if>
</c:forEach>
</TABLE>

<INPUT TYPE="hidden" name="totallines" value="<c:out value="${dataCount}"/>">
<c:if test="${empty poHeaderViewBeanCollection && !(empty submitSearch && empty submitUpdate)}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:when>
<c:otherwise>
 Here
</c:otherwise>
</c:choose>
</html-el:form>
</DIV>

</body>
</html-el:html>