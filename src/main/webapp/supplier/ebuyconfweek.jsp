<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
		

<html>
<head>
  <title>Haas TCM DBUY Confirmed Orders </title>
 <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
</head>

<body>

  <table width="100%">
    <tr>
       <%-- Banner --%>
       <td width="90%"><img src="/images/tcmtitlegif.gif"></td>
       <td class="pagetitle" align="right" colspan='2'>DBuy</td>
    </tr>
    <tr>
       <td class="heading"><b>E-Buy Confirmed Orders (last 2 weeks [default])</b></td>
     </tr>         
  </table>
  
  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
    <TR align="center">
      <td class="thheading">Path</td>
      <td class="thheading">Supplier</td>
      <td class="thheading">PO</td>
      <td class="thheading">Promise Date</td>
      <td class="thheading">Ship Date</td>
      <td class="thheading">Date First Confirmed</td>
      <td class="thheading">Buyer Logon ID</td>
    </TR>    
 
    <c:set var="colorClass" value='white'/>
    <c:forEach items="${EbuyConfirmedBeans}" var="ebuy" varStatus="status">
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value='white'/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='blue'/>
     </c:otherwise>
    </c:choose>

    <TR>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.supplyPath}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.supplier}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.radianPo}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24">
        <fmt:formatDate var="fmtPromiseDate" value="${ebuy.promisedDate}" pattern="MM/dd/yyyy"/>
        <c:out value="${fmtPromiseDate}"/>          
      </TD>        
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24">
        <fmt:formatDate var="fmtShipDate" value="${ebuy.shipDate}" pattern="MM/dd/yyyy"/>
        <c:out value="${fmtShipDate}"/>          
      </TD>        
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24">
        <fmt:formatDate var="fmtConfirmedDate" value="${ebuy.dateConfirmed}" pattern="MM/dd/yyyy HH:mm:ss"/>
        <c:out value="${fmtConfirmedDate}"/>          
      </TD>        
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="24"><c:out value="${ebuy.logonId}"/></TD>
    </TR>
    </c:forEach>       
  </TABLE>

</body>
</html>
