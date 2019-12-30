<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic-el" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html-el:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
  <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
  <SCRIPT SRC="/js/edisupport.js" LANGUAGE="JavaScript"></SCRIPT>

  <SCRIPT SRC="/js/util.js" LANGUAGE="JavaScript"></SCRIPT>
  <title>
    <fmt:message key="dbuystatus.title"/>
  </title>
</head>

<body bgcolor="#ffffff">
<c:if test="org.apache.struts.action.MESSAGE == null">
  <font color="red">
    ERROR:  Application resources not loaded -- Contact tcmIS Hotline at 512-801-3109.
  </font>
</c:if>

<div class="width">
 <div class="minwidth">
 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html-el:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="dbuystatus.title"/>
</td>
<td width="30%" class="headingr">
</td>
</tr>
</table>
</div>

<c:if test="${DbuyStatusBeans != null}" >
<!-- Search results start -->
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    

    <tr align="center">
    <th width="10%"><fmt:message key="label.po"/></th>
    <th width="10%"><fmt:message key="label.mrnumber"/></th>
    <th width="10%"><fmt:message key="dbuystatus.datesent"/></th>
    <th width="10%"><fmt:message key="dbuystatus.dateacknowledged"/></th>
    <th width="5%"><fmt:message key="dbuystatus.dateconfirmed"/></th>
    <th width="10%"><fmt:message key="label.supplier"/></th>
    <th width="15%"><fmt:message key="label.suppliername"/></th>
    </tr>

    <c:forEach var="dbuy" items="${DbuyStatusBeans}" varStatus="status">

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr align="center" class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${status.index}"/>'>
     <td width="10%"><c:out value="${dbuy.radianPo}"/></td>
     <td width="10%"><c:out value="${dbuy.prNumber}"/></td>
     <td width="10%">
         <fmt:formatDate var="fmtDateSent" value="${dbuy.dateSent}" pattern="MM/dd/yyyy hh:mm a"/>
         <c:out value="${fmtDateSent}"/>         
     </td>
     <td width="10%">
         <fmt:formatDate var="fmtDateAcknowledged" value="${dbuy.dateAcknowledgement}" pattern="MM/dd/yyyy hh:mm a"/>
         <c:out value="${fmtDateAcknowledged}"/>         
     </td>
     <td width="5%">
         <fmt:formatDate var="fmtDateConfirmed" value="${dbuy.dateConfirmed}" pattern="MM/dd/yyyy hh:mm a"/>
         <c:out value="${fmtDateConfirmed}"/>         
     </td>
     <td width="10%"><c:out value="${dbuy.supplier}"/></td>
     <td width="15%"><c:out value="${dbuy.supplierName}"/></td>
   </tr>
   
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty DbuyStatusBeans}" >

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
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of width -->
</div> <!-- close of minwidth -->
</div> <!-- close of interface -->

</body>
</html-el:html>

