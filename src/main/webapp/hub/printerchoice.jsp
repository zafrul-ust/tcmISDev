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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%-- Add any other stylesheets you need for the page here --%>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/hub/receiving.js"></script>

<title>
<fmt:message key="labels.printerchoice.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/printerchoice.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
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

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="labels.printerchoice.title"/>
</td>
<td width="30%" class="headingr">
</td>
</tr>
</table>
</div>

<div class="contentArea">

<c:if test="${locationLabelPrinterCollection != null}" >
<!-- Search Option Begins -->
<table id="searchMaskTable" width="50%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

<c:if test="${!empty locationLabelPrinterCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>
 <c:forEach var="locationLabelPrinterBean" items="${locationLabelPrinterCollection}" varStatus="status">
  <c:choose>
   <c:when test="${status.index % 2 == 0}" >
    <c:set var="colorClass" value=''/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='alt'/>
   </c:otherwise>
  </c:choose>

 <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
  <td width="1%" class="alignRight"><input id="option<c:out value="${dataCount}"/>" type="radio" name="printerPath" value="<c:out value="${status.current.printerPath}"/>"></td>
  <td width="50%" class="alignLeft"><c:out value="${status.current.printerPath}"/></td>
 </tr>
 <c:set var="dataCount" value='${dataCount+1}'/>
 
</c:forEach>
</table>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr class=''>
  <td width="5%" class="optionTitleLeft">
   <html:submit property="continuePrinting" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="labels.generatelabels"/>
   </html:submit>
  </td>

  <td width="5%" class="optionTitleLeft">
  <html:button property="closewindow" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.close"/>
  </html:button>
  </td>
</tr>
</table>

</c:if>

<c:if test="${empty locationLabelPrinterCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="100%">
<fmt:message key="main.nodatafound"/>
</td>
</tr>
</table>
</c:if>

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
</c:if>

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
  <input type="hidden" name="totallines" value="<c:out value="${dataCount}"/>">
  <tcmis:saveRequestParameter/>
  <input type="hidden" name="labelReceipts" value="<c:out value="${labelReceipts}"/>">
  <input type="hidden" name="sourcePage" value="<c:out value="${sourcePage}"/>">
  <%--
  <input type="hidden" name="paperSize" value="<c:out value="${paperSize}"/>">
  <input type="hidden" name="skipKitLabels" value="<c:out value="${skipKitLabels}"/>">
  <input type="hidden" name="binLabels" value="<c:out value="${binLabels}"/>">
  <input type="hidden" name="hubNumber" value="<c:out value="${hubNumber}"/>">--%>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>