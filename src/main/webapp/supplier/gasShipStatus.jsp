<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html locale="true">
<head>
  <title>Haas TCM DLA Ship Status </title>
 <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
</head>

<body>

  <table width="100%">
    <tr>
       <td width="90%"><img src="images/tcmtitlegif.gif"></td>
       <td class="pagetitle" align="right" colspan='2'>DLA</td>
    </tr>
    <tr>
       <td class="heading"><b>DLA Gas Order Shipments</b></td>
     </tr>         
  </table>

<c:if test="${!empty DLATenDayShipStatus}">

<c:forEach var="DailyShipStatus" items="${DLATenDayShipStatus}" varStatus="allStatus">  
  
  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
    <TR align="center">
      <td class="thheading">Need Date</td>
      <td class="thheading">Ship Date</td>
      <td class="thheading">Customer PO</td>
      <td class="thheading">Haas PO</td>
      <td class="thheading">Supplier</td>
    </TR>    
 
    <c:set var="colorClass" value='white'/>
    <c:forEach items="${DailyShipStatus}" var="ebuy" varStatus="status">
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value='white'/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='blue'/>
     </c:otherwise>
    </c:choose>

    <TR>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24">
        <fmt:formatDate var="fmtNeedDate" value="${ebuy.needDate}" pattern="MM/dd/yyyy HH:mm:ss"/>
        <c:out value="${fmtNeedDate}"/>          
      </TD>        
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24">
        <fmt:formatDate var="fmtShipDate" value="${ebuy.shipConfirmDate}" pattern="MM/dd/yyyy HH:mm:ss"/>
        <c:out value="${fmtShipDate}"/>          
      </TD>        
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.customerPo}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.radianPo}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.supplierName}"/></TD>
    </TR>
    </c:forEach>       
  </TABLE>

  <br>
</c:forEach>

</c:if>
  
</body>
</html:html>
