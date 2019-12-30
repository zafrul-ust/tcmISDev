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
<tcmis:fontSizeCss currentCss="global.css"/>
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<SCRIPT SRC="/js/cyclecountresults.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar.js" LANGUAGE="JavaScript"></SCRIPT>

<c:set var="uploadId" value='${param.uploadId}'/>
<title>
   <fmt:message key="cyclecountresults.title"/>
</title>
</HEAD>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${empty doexcelpopup || doexcelpopup == null}" >
    <BODY>
  </c:when>
  <c:otherwise>
   <BODY onLoad="doexcelpopup()">
  </c:otherwise>
</c:choose>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
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
<B><fmt:message key="cyclecountresults.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</TD>
</TR>
</TABLE>
</div>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="actualRowCount" value='${0}'/>

<c:forEach var="scannedReceiptReportViewBean" items="${scannedReceiptReportViewBeanCollection}" varStatus="status">

<c:if test="${status.index == 0}">
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">
<TD WIDTH="10%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="cyclecountresults.label.uploadid"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="15%" ALIGN="LEFT">
<c:out value='${uploadId}'/>
</TD>
<%--
<TD WIDTH="10%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="cyclecountresults.label.uploaddate"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="15%" ALIGN="LEFT">
<fmt:formatDate var="uploadDatetime" value="${status.current.uploadDatetime}" pattern="MM/dd/yyyy HH:mm"/>
<c:out value='${uploadDatetime}'/>
</TD>
--%>
<html:form action="/cyclecountresults.do" onsubmit="return SubmitOnlyOnce();">

<%--<TD COLSPAN="3" CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:submit property="buttonSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="cyclecountresults.button.search"/>
</html:submit>
</TD>
--%>
<TD CLASS="announce" HEIGHT="35" WIDTH="30%" ALIGN="LEFT">
<html:submit property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>

<INPUT TYPE="hidden" NAME="uploadId" value="<c:out value="${uploadId}"/>" >
</TD>
</html:form>
</TR>
</TABLE>
<BR><H4><B><fmt:message key="cyclecountresults.label.errorreport"/>:</B></H4>
<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
</c:if>

<c:if test="${(dataCount % 10 == 0 && actualRowCount ==0) || (status.index == 0)}">
<TR align="center">
<TH width="2%"><fmt:message key="label.receiptid"/></TH>
<TH width="2%"><fmt:message key="label.itemid"/></TH>
<TH width="2%"><fmt:message key="label.lotstatus"/></TH>
<TH width="5%"><fmt:message key="cyclecountresults.label.expectedbin"/></TH>
<TH width="5%"><fmt:message key="cyclecountresults.label.expectedqty"/></TH>
<TH width="5%"><fmt:message key="cyclecountresults.label.scannedbin"/></TH>
<TH width="5%"><fmt:message key="label.scannedqty"/></TH>
<TH width="5%"><fmt:message key="cyclecountresults.label.totalscannedqty"/></TH>
</TR>
</c:if>

<c:set var="scannedReceiptsCollection"  value='${status.current.scannedReceiptsCollection}'/>
<bean:size id="listSize" name="scannedReceiptsCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<c:choose>
  <c:when test="${dataCount % 2 == 0}" >
   <c:set var="colorClass" value='white'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='blue'/>
  </c:otherwise>
</c:choose>

 <c:set var="expectedBin" value='${status.current.expectedBin}' />
 <c:set var="expectedQtyForReceipt" value='${status.current.expectedQtyForReceipt}' />
 <c:set var="totalQtyCountedForReceipt" value='${status.current.totalQtyCountedForReceipt}' />
 <c:set var="actualRowCount" value='${actualRowCount+1}'/>

<%--<c:if test="${(expectedQtyForReceipt != totalQtyCountedForReceipt)}">--%>
 <c:set var="dataCount" value='${dataCount+1}'/>
 <c:set var="actualRowCount" value='${0}'/>
 <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">

 <TD width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><B><c:out value="${status.current.receiptId}"/></B></TD>
 <TD width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.itemId}"/></TD>
 <TD width="5%" ALIGN="LEFT" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lotStatus}"/></TD>
 <TD width="5%" ALIGN="LEFT" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.expectedBin}"/></TD>
 <TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.expectedQtyForReceipt}"/></TD>

 <c:forEach var="scannedReceiptsBean" items="${scannedReceiptsCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
  </c:if>

  <c:set var="scannedBin" value='${status1.current.scannedBin}' />
  <c:choose>
   <c:when test="${scannedBin != expectedBin}">
    <TD width="5%" ALIGN="LEFT" BGCOLOR="#FF9999"><c:out value="${status1.current.scannedBin}"/></TD>
    <TD width="5%"><c:out value="${status1.current.countedQuantity}"/></TD>
   </c:when>
   <c:otherwise>
    <TD ALIGN="LEFT" width="5%"><c:out value="${status1.current.scannedBin}"/></TD>
    <TD width="5%"><c:out value="${status1.current.countedQuantity}"/></TD>
   </c:otherwise>
  </c:choose>

 <c:if test="${status1.index == 0}">
  <TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.totalQtyCountedForReceipt}"/></TD>
 </c:if>
 </c:forEach>
<%--</c:if>--%>
</TR>
</c:forEach>
</TABLE>

<c:if test="${dataCount == 0}">
<TD width="100%" 'CLASS=white'>
<BR><BR><fmt:message key="main.nodatafound"/>
</TD>
</c:if>

<BR><H4><B><fmt:message key="cyclecountresults.label.receiptnotscanned"/>:</B></H4>
<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
<c:set var="dataCount" value='${0}'/>

<c:forEach var="scannedBinMissingReceiptBean" items="${scannedBinMissingReceiptBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>
<c:if test="${status.index % 10 == 0}">
<TR align="center">
<TH width="2%"><fmt:message key="label.receiptid"/></TH>
<TH width="8%"><fmt:message key="label.lotstatus"/></TH>
<TH width="5%"><fmt:message key="label.itemid"/></TH>
<TH width="15%"><fmt:message key="label.itemdesc"/></TH>
<TH width="5%"><fmt:message key="label.bin"/></TH>
<TH width="5%"><fmt:message key="cyclecountresults.label.expectedqty"/></TH>
</TR>
</c:if>

<c:choose>
  <c:when test="${dataCount % 2 == 0}" >
   <c:set var="colorClass" value='white'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='blue'/>
  </c:otherwise>
</c:choose>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
 <TD width="2%"><B><c:out value="${status.current.receiptId}"/></B></TD>
<TD width="8%"><c:out value="${status.current.lotStatus}"/></TD>
 <TD width="5%"><c:out value="${status.current.itemId}"/></TD>
 <TD width="15%" ALIGN="LEFT"><c:out value="${status.current.itemDesc}"/></TD>
 <TD width="5%" ALIGN="LEFT"><c:out value="${status.current.bin}"/></TD>
 <TD width="5%"><c:out value="${status.current.currentExpectedQty}"/></TD>
</TR>

</c:forEach>
</TABLE>

<c:if test="${dataCount == 0}">
<TD width="100%" 'CLASS=white'>
<BR><BR><fmt:message key="main.nodatafound"/>
</TD>
</c:if>

</DIV>
</BODY>
</html:html>
