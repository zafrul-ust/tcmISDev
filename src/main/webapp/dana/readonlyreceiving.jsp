<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%--<tcmis:loggedIn indicator="true" forwardPage="/hub/Home.do"/>--%>
<html:html lang="true">
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico">
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css">
<SCRIPT SRC="/js/readonlyreceiving.js" LANGUAGE="JavaScript"></SCRIPT>
<title>
<fmt:message key="receiving.label.openpos"/>
</title>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altfacilityid = new Array(
<c:forEach var="customerFacilityIgViewBean" items="${customerFacilityIgViewBeanCollection}" varStatus="status">
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

var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="customerFacilityIgViewBean" items="${customerFacilityIgViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="inventoryGroupDefinitionBeanCollection" value='${status.current.inventoryGroupDefinitionBeanCollection}'/>

altinvid["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="inventoryGroupDefinitionBean" items="${inventoryGroupDefinitionBeanCollection}" varStatus="status1">
  <c:set var="issueGeneration" value='${status1.current.issueGeneration}'/>
  <c:if test="${issueGeneration == 'Item Counting'}">
  <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroup}"/>"
   </c:otherwise>
  </c:choose>
  </c:if>
  </c:forEach>
  );
altinvName["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="inventoryGroupDefinitionBean" items="${inventoryGroupDefinitionBeanCollection}" varStatus="status1">
 <c:set var="issueGeneration" value='${status1.current.issueGeneration}'/>
 <c:if test="${issueGeneration == 'Item Counting'}">
  <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:otherwise>
  </c:choose>
  </c:if>
  </c:forEach>
  );
 </c:forEach>

// -->
</SCRIPT>

</HEAD>

<c:set var="userAction" value='${param.userAction}'/>

<body BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">



<TABLE BORDER=0 WIDTH="100%" >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/openpos.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P>
<center>
 <fmt:message key="label.pleasewait"/>
</center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<html:form action="/receiving.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">
<TD WIDTH="5%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>&nbsp;
</TD>
<TD WIDTH="10%">
<c:set var="selectedFacility" value='${param.facilityId}'/>
<c:set var="invenGroupIn" value='${param.invenGroupIn}'/>

<select name="facilityId" onchange="facilityChanged()">
  <option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="customerFacilityIgViewBean" items="${customerFacilityIgViewBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>

  <c:choose>
   <%--
   <c:when test="${empty selectedFacility}" >
    <c:set var="selectedFacility" value='${status.current.facilityId}'/>
    <c:set var="inventoryGroupDefinitionBeanCollection" value='${status.current.inventoryGroupDefinitionBeanCollection}'/>
   </c:when>
   --%>
   <c:when test="${currentFacility == selectedFacility}" >
    <c:set var="inventoryGroupDefinitionBeanCollection" value='${status.current.inventoryGroupDefinitionBeanCollection}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentFacility == selectedFacility}">
    <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityId}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityId}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.search"/>:</B>&nbsp;
</TD>

<c:set var="searchWhat" value='${param.searchWhat}'/>
<TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
<SELECT CLASS="HEADER"  NAME="searchWhat" size="1">
<option value="itemDescription" <c:if test="${searchWhat == 'itemDescription'}" >selected</c:if>>Item Desc</option>
<option value="itemId" <c:if test="${searchWhat == 'itemId'}" >selected</c:if>>Item Id</option>
<option value="lastSupplier" <c:if test="${searchWhat == 'lastSupplier'}" >selected</c:if>>Supplier</option>
<option value="radianPo" <c:if test="${searchWhat == 'radianPo'}" >selected</c:if>>PO</option>
</SELECT>
</TD>

<c:set var="searchType" value='${param.searchType}'/>
<TD WIDTH="5%" ALIGN="LEFT" CLASS="announce">
<SELECT CLASS="HEADER"  NAME="searchType" size="1">
<option value="is" <c:if test="${searchType == 'is'}" >selected</c:if>>is</option>
<option value="contains" <c:if test="${searchType == 'contains'}" >selected</c:if>>contains</option>
</SELECT>
</TD>

<c:set var="searchText" value='${param.search}'/>
<TD WIDTH="5%" VALIGN="MIDDLE" ALIGN="LEFT" CLASS="announce">
<B>For</B>&nbsp;&nbsp;<INPUT CLASS="HEADER" TYPE="text" NAME="search" value="<c:out value="${searchText}"/>" size="20">
</TD>

</TR>

<TR VALIGN="MIDDLE">
<TD WIDTH="5%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.inventorygroup"/>:</B>&nbsp;
</TD>

<TD WIDTH="10%">
<c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
<c:set var="invenGroupCount" value='${0}'/>
<select name="inventoryGroup">
 <c:choose>
  <c:when test="${empty selectedFacility}">
   <option value="All"><fmt:message key="label.pleaseselect"/></option>
  </c:when>
  <c:otherwise>
   <c:forEach var="inventoryGroupDefinitionBean" items="${inventoryGroupDefinitionBeanCollection}" varStatus="status">
   <c:set var="issueGeneration" value='${status.current.issueGeneration}'/>
   <c:if test="${issueGeneration == 'Item Counting'}">
    <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
    <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
    <c:choose>
     <c:when test="${selectedInventoryGroup == currentInventoryGroup}">
      <option value="<c:out value="${currentInventoryGroup}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
     </c:when>
     <c:otherwise>
      <option value="<c:out value="${currentInventoryGroup}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
     </c:otherwise>
    </c:choose>
  </c:if>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value="All"><fmt:message key="label.pleaseselect"/></option>
  </c:if>
 </c:otherwise>
</c:choose>
</select>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.sortby"/>:</B>&nbsp;
</TD>

<c:set var="sort" value='${param.sort}'/>
<TD WIDTH="10%" ALIGN="LEFT" COLSPAN="2">
<SELECT CLASS="HEADER"  NAME="sort" size="1">
<option  selected value="PO/POLine" <c:if test="${sort == 'PO/POLine'}" >selected</c:if>>PO/POLine</option>
<option  value="Supplier/Date Exptd" <c:if test="${sort == 'Supplier/Date Exptd'}" >selected</c:if>>Supplier/Date Exptd</option>
<option  value="Date Exptd/Supplier" <c:if test="${sort == 'Date Exptd/Supplier'}" >selected</c:if>>Date Exptd/Supplier</option>
<option  value="Item Id/Date Exptd" <c:if test="${sort == 'Item Id/Date Exptd'}" >selected</c:if>>Item Id/Date Exptd</option>
</SELECT>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="5%" ALIGN="LEFT">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "return readonlysearch()">
     <fmt:message key="label.search"/>
</html:submit>
</TD>

</TR>
</TABLE>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>
 <c:if test="${empty userAction}">
  <fmt:message key="hub.proceed"/>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>
<html:hidden property="userAction" value=""/>
<INPUT TYPE="hidden" NAME="invenGroupIn" value="<c:out value="${invenGroupIn}"/>">

<c:if test="${userAction == 'search' || userAction == 'createxlsreport'}" >
<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>

<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">

<c:forEach var="receivingViewBean" items="${receivingViewBeanCollection}" varStatus="status">
<c:if test="${status.index % 10 == 0}">

<c:set var="rowCount" value='${rowCount+1}'/>

<TR align="center">
<TH CLASS="results" width="5%">PO</TH>
<TH CLASS="results" width="5%">PO Line</TH>
<TH CLASS="results" width="5%">Date Exptd</TH>
<TH CLASS="results" width="5%">Supplier</TH>
<TH CLASS="results" width="5%">Open Amt</TH>
<TH CLASS="results" width="5%">Item<BR>(Inven Grp)</TH>
<TH CLASS="results" width="5%">Packaging</TH>
<TH CLASS="results" width="25%">Description</TH>
<%--<TH CLASS="results" width="5%">OK &nbsp;</TH>
<TH CLASS="results" width="5%">Bin</TH>
<TH CLASS="results" width="5%">Lot</TH>
<TH CLASS="results" width="5%">Receipt ID</TH>
<TH CLASS="results" width="5%">Act Supp Ship Date mm/dd/yyyy</TH>
<TH CLASS="results" width="5%">DOR mm/dd/yyyy</TH>
<TH CLASS="results" width="5%">DOM mm/dd/yyyy</TH>
<TH CLASS="results" width="5%">Manufacturer DOS mm/dd/yyyy</TH>
<TH CLASS="results" width="5%">Exp Date mm/dd/yyyy</TH>
<TH CLASS="results" width="5%">Qty Rec'd</TH>
<TH CLASS="results" width="5%">Duplicate</TH>--%>
</TR>

</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center">

<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.radianPo}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.lineItem}"/></TD>
<TD <c:out value="${colorClass}"/>
<fmt:formatDate var="formattedDate" value="${status.current.expected}" pattern="MM/dd/yyyy"/>
<c:out value="${formattedDate}"/>
</TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.lastSupplier}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.qtyOpen}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.itemId}"/><BR>(<c:out value="${status.current.inventoryGroup}"/>)</TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.packaging}"/></TD>
<TD <c:out value="${colorClass}"/> width="25%"><c:out value="${status.current.itemDescription}"/></TD>

</TR>
</c:forEach>

<c:if test="${rowCount == 0}">
<TD width="100%" 'CLASS=white'>
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>

</TABLE>
</c:if>

</html:form>
</DIV>
</BODY>
</html:html>


