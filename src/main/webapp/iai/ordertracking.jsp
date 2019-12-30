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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss currentCss="clientpages.css"/>
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
<%--<script type="text/javascript" src="/js/menu/contextmenu.js"></script>--%>

<SCRIPT SRC="/js/ordertracking.js" LANGUAGE="JavaScript"></SCRIPT>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/rightclick.css"></LINK>
<SCRIPT SRC="/js/rightclick.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="ordertracking.label.title"/>
</title>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altFacilityId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
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
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
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

var altApplication = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplication["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
 <c:if test="${status1.index == 0}">
   "All","My Work Areas",
 </c:if>
 <c:set var="currentStatus"  value='${status1.current.status}'/>
 <c:if test="${currentStatus == 'A'}">
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
 </c:if>
 </c:forEach>
);
 </c:forEach>

<%--,"<c:out value="${status1.current.application}"/>"
  </c:forEach>
  );
 </c:forEach> --%>

var altApplicationDesc = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplicationDesc["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
 <c:if test="${status1.index == 0}">
   "All","My Work Areas",
 </c:if>
 <c:set var="currentStatus"  value='${status1.current.status}'/>
 <c:if test="${currentStatus == 'A'}">
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status1.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>
 </c:if>
 </c:forEach>
);
 </c:forEach>

<%--,"<c:out value="${status1.current.applicationDesc}"/>"
  </c:forEach>
  );
 </c:forEach>--%>
var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</SCRIPT>

</HEAD>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
  </c:otherwise>
</c:choose>
<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/orddertracking.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>
</div>
<%--
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="ordertracking.label.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<A HREF="/tcmIS/Hub/Home" STYLE="color:#FFFFFF"><fmt:message key="label.home"/></A>
</TD>
</TR>
</TABLE>
--%>

<DIV ID="TRANSITPAGE" STYLE="display: none;">

<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>

</DIV>

<DIV ID="MAINPAGE" STYLE="">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("MAINPAGE");
rowId.attachEvent('onmouseup',function () {
        makeRightClickNormal();});
// -->
</SCRIPT>

<tcmis:form action="/ordertracking.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">

<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.requestor"/>:</B>&nbsp;
</TD>

<TD WIDTH="10%">

<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="requestorId" value='${param.requestorId}'/>
<c:set var="requestorName" value='${param.requestorName}'/>

<%--<c:if test="${empty submitSearch && empty buttonCreateExcel}">
 <c:if test="${empty requestorId}">
  <c:set var="requestorId" value='${sessionScope.personnelBean.personnelId}'/>
 </c:if>
 <c:if test="${empty requestorName}">
  <c:set var="requestorName" value='${sessionScope.personnelBean.lastName}, ${sessionScope.personnelBean.firstName} ${sessionScope.personnelBean.midInitial}'/>
 </c:if>
</c:if>--%>

<INPUT CLASS="HEADER" TYPE="text" NAME="requestorName" value="<c:out value="${requestorName}"/>" onChange="invalidateRequestor()" size="20">
<BUTTON CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name="searchsupplierlike" value="..." OnClick="getPersonnel()"><IMG src="/images/search_small.gif" alt="Supplier"></BUTTON>
<INPUT TYPE="hidden" NAME="requestorId" value="<c:out value="${requestorId}"/>" >
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>&nbsp;
</TD>

<TD WIDTH="15%" CLASS="announce">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">
    <c:choose>
      <c:when test="${selectedFacilityId == 'All'}">
        <option value="All" selected>All</option>
        <c:if test="${empty selectedFacilityId}" >
          <c:set var="selectedFacilityId" value="All"/>
        </c:if>
      </c:when>
      <c:otherwise>
        <option value="All">All</option>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${selectedFacilityId == 'My Facilities'}">
        <option value="My Facilities" selected>My Facilities</option>
        <c:if test="${empty selectedFacilityId}" >
          <c:set var="selectedFacilityId" value="My Facilities"/>
        </c:if>
      </c:when>
      <c:otherwise>
        <option value="My Facilities">My Facilities</option>
      </c:otherwise>
    </c:choose>

  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacilityId}" >
        <c:set var="selectedFacilityId" value=""/>
        <%--<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>--%>
      </c:when>
      <c:when test="${currentFacilityId == selectedFacilityId}" >
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFacilityId == selectedFacilityId}">
        <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityId}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityId}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="10%" CLASS="announce">
<html:radio property="status" value=""/>
<B><fmt:message key="label.any"/></B>
<html:radio property="status" value="DRAFT"/>
<B><fmt:message key="label.pending"/></B>
<html:radio property="status" value="OPEN"/>
<B><fmt:message key="label.open"/></B>
</TD>

</TR>
<TR>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<html:checkbox property="needMyApproval" onchange="checkDisabled()" value="Y"/>
&nbsp;
</TD>

<TD  ALIGN="LEFT" WIDTH="10%">
<B><fmt:message key="ordertracking.label.needmyapproval"/></B>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.workarea"/></B>
</TD>

<TD  ALIGN="LEFT" WIDTH="15%">
<c:set var="selectedApplicationId" value='${param.applicationId}'/>
<c:if test="${empty selectedApplicationId}" >
  <c:set var="selectedApplicationId" value="All"/>
</c:if>
<select name="applicationId">
 <c:choose>
    <c:when test="${selectedApplicationId == 'All'}">
      <option value="All" selected>All</option>
    </c:when>
    <c:otherwise>
      <option value="All">All</option>
    </c:otherwise>
  </c:choose>

  <c:choose>
    <c:when test="${selectedApplicationId == 'My Work Areas' && !selectedApplicationId == 'All'}">
      <option value="My Work Areas" selected>My Facilities</option>
    </c:when>
    <c:when test="${selectedApplicationId != 'My Work Areas' && selectedApplicationId == 'All'}">

     </c:when>
     <c:when test="${selectedApplicationId != 'My Work Areas'}">
      <option value="My Work Areas">My Work Areas</option>
     </c:when>
  </c:choose>

  <c:set var="applicationCount" value='${0}'/>
  <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
    <c:set var="currentApplicationId" value='${status.current.application}'/>
    <c:set var="currentStatus"  value='${status.current.status}'/>
    <c:if test="${currentStatus == 'A'}">
    <c:set var="applicationCount" value='${applicationCount+1}'/>
    <c:choose>
      <c:when test="${currentApplicationId == selectedApplicationId}">
        <option value="<c:out value="${currentApplicationId}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentApplicationId}"/>"><c:out value="${status.current.applicationDesc}"/></option>
      </c:otherwise>
    </c:choose>
    </c:if>
  </c:forEach>
</select>
</TD>

<TD WIDTH="15%" CLASS="announce">
<html:radio property="status" value="DELIVERED"/><B><fmt:message key="ordertracking.label.fullydelivered"/></B>
<INPUT CLASS="HEADER" TYPE="text" NAME="deliveredSinceDays" value="<c:out value="${param.deliveredSinceDays}"/>" size="5"><B><fmt:message key="label.days"/></B>
</TD>

</TR>
<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">

<B><fmt:message key="label.searchby"/>:</B>&nbsp;
</TD>

<TD  ALIGN="LEFT" WIDTH="10%">
<html:select property="searchWhat">
  <html:options collection="searchDropDownBeanCollection" name="SearchDropDownBean" labelProperty="displayName" property="databaseAction"/>
</html:select>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<html:select property="searchType">
  <html:options collection="searchtypeDropDownBeanCollection" name="SearchDropDownBean" labelProperty="displayName" property="databaseAction"/>
</html:select>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="searchText" value="<c:out value="${param.searchText}"/>" size="20">
</TD>
<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<html:radio property="status" value="CANCELED"/><B><fmt:message key="ordertracking.label.cancelled"/></B>
</TD>
</TR>
<TR>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.showlegend"/>:</B>&nbsp;
</TD>

<TD width="5%" COLSPAN="2"><FONT SIZE="5" COLOR=#ff0fff>&diams;</a></FONT>AOG
&nbsp;<FONT SIZE="5" COLOR=#FF9999>&diams;</a></FONT>Critical Orders
&nbsp;<FONT SIZE="5" COLOR=#ffff00>&diams;</a></FONT>Pending Cancellation
&nbsp;<FONT SIZE="5" COLOR=#OOOOOO>&diams;</a></FONT>Cancelled/Rejected
</TD>

<TD WIDTH="15%">
<html:checkbox property="critical" value="Y"/><B><fmt:message key="ordertracking.label.criticalonly"/></B>
</TD>

<TD WIDTH="15%" CLASS="announce">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.search"/>
</html:submit>
&nbsp;&nbsp;&nbsp;
<html:submit property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</TD>
</TR>

</TABLE>

<c:set var="showResults" value=''/>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>

 <c:if test="${empty submitSearch && empty buttonCreateExcel}">
  <I><fmt:message key="hub.proceed"/></I>
  <c:set var="showResults" value='showSearchResults'/>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>

</DIV>
<DIV ID="RESULTSPAGE" STYLE="">

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="pkgOrderTrackPrOrderTrackBean" items="${pkgOrderTrackPrOrderTrackBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<tr align="center">
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.status"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.customerpo"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.requestor"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.facility"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.workarea"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.partnum"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="label.partdesc"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.packaging"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.mrline"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.notes"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="ordertracking.label.released"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.needed"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.picked"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.delivered"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.lastdelivered"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="label.critical"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.item"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.approver"/></TH>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
   <c:set var="invisibleClass" value='INVISIBLEHEADBLUE'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
   <c:set var="invisibleClass" value='INVISIBLEHEADWHITE'/>
  </c:otherwise>
</c:choose>

<c:set var="critical" value='${status.current.critical}'/>
<c:set var="requestLineStatus" value='${status.current.requestLineStatus}'/>

<c:if test="${critical == 'Y' || critical == 'y'}">
<c:set var="colorClass" value='red'/>
</c:if>

<c:if test="${critical == 'S' || critical == 's'}">
<c:set var="colorClass" value='pink'/>
</c:if>

<c:if test="${requestLineStatus == 'Pending Cancellation'}">
<c:set var="colorClass" value='yellow'/>
</c:if>

<c:if test="${requestLineStatus == 'Cancelled' || requestLineStatus == 'Rejected'}">
<c:set var="colorClass" value='black'/>
</c:if>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>

<c:set var="prNumber" value='${status.current.prNumber}'/>
<c:set var="lineItem" value='${status.current.lineItem}'/>
<c:set var="orderType" value='${status.current.orderType}'/>

<TD width="5%"><c:out value="${status.current.requestLineStatus}"/></TD>
<TD width="5%"><c:out value="${status.current.poNumber}"/></TD>
<TD width="5%"><c:out value="${status.current.requestorName}"/></TD>
<TD width="5%"><c:out value="${status.current.facilityId}"/></TD>
<TD width="5%"><c:out value="${status.current.applicationDesc}"/></TD>
<TD width="5%"><c:out value="${status.current.catalogId}"/></TD>
<TD width="5%"><c:out value="${status.current.facPartNo}"/></TD>
<TD width="15%"><c:out value="${status.current.partDescription}"/></TD>
<TD width="5%"><c:out value="${status.current.packaging}"/></TD>
<TD width="5%"><c:out value="${orderType}"/></TD>
<TD width="5%">
<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="prNumber<c:out value="${status.index}"/>" value="<c:out value="${prNumber}"/>" >
<INPUT TYPE="hidden" NAME="lineItem<c:out value="${status.index}"/>" value="<c:out value="${lineItem}"/>" >
<INPUT TYPE="hidden" NAME="orderType<c:out value="${status.index}"/>" value="<c:out value="${orderType}"/>" >

<c:choose>
   <c:when test="${empty prNumber || empty lineItem || prNumber == '0' }" >
     <%--<c:out value="${prNumber} - ${lineItem}"/>--%>
   </c:when>
   <c:otherwise>
     <c:out value="${prNumber} - ${lineItem}"/></A>
   </c:otherwise>
</c:choose>
</TD>

<c:set var="notes" value='${status.current.notes}'/>
 <c:choose>
   <c:when test="${empty notes || notes == ' '}" >
     <TD width="10%" ID="lineNotesTd<c:out value="${status.index}"/>">
   </c:when>
   <c:otherwise>
     <TD width="10%" ID="lineNotesTd<c:out value="${status.index}"/>" onMouseOver=style.cursor="hand">
     <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
     <!--
     var rowId  =  document.getElementById("lineNotesTd<c:out value="${status.index}"/>");
     rowId.attachEvent('onclick',function () {
        showLineNotes('<c:out value="${status.index}"/>');});
     // -->
     </SCRIPT>
     <P ID="lineNoteslink<c:out value="${status.index}"/>">+</P>
     <DIV ID="lineNotes<c:out value="${status.index}"/>" CLASS="displaynone" onMouseOver=style.cursor="hand">
     <c:out value="${status.current.notes}"/>
     </DIV>
     <INPUT TYPE="hidden" NAME="notesVisible<c:out value="${status.index}"/>" value="No" >
   </c:otherwise>
 </c:choose>
</TD>
<TD width="5%"><c:out value="${status.current.releaseDate}"/></TD>
<TD width="5%"><c:out value="${status.current.requiredDatetime}"/></TD>
<TD width="5%"><c:out value="${status.current.totalPicked} of ${status.current.quantity}"/></TD>
<TD width="5%"><c:out value="${status.current.totalShipped}"/></TD>
<TD width="5%"><c:out value="${status.current.lastShipped}"/></TD>
<%--<TD width="5%"><c:out value="${status.current.critical}"/></TD>
<TD width="5%"><c:out value="${status.current.itemId}"/></TD>--%>
<TD width="5%"><c:out value="${status.current.approverName}"/></TD>
</TR>
</c:forEach>
</table>

<%--<c:if test="${resultCount != 0}">
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">

<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#000066">&nbsp;

</TD></tr>

</table>
</c:if>
--%>

<c:if test="${empty pkgOrderTrackPrOrderTrackBeanCollection}" >
<c:if test="${pkgOrderTrackPrOrderTrackBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD></tr>

</c:if>
</c:if>
</TABLE>

</tcmis:form>
</DIV>

<DIV id="ie5menu" class="skin0" onMouseover="highlightie5()" onMouseout="lowlightie5()" onClick="jumptoie5();">
<DIV class="menuitems" url="javascript:showMrAllocationReport();">MR Allocation</DIV>
<DIV class="menuitems" url="javascript:showMrLineAllocationReport();">MR Line Allocation</DIV>
<HR>
<DIV class="menuitems" url="javascript:showDeliverySchedule();">MR Line Schedulde</DIV>
</DIV>

</body>
</html:html>