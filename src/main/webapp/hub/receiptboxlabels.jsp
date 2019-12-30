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
<%-- based on template.jsp --%>
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

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%-- This handels which key press events are disabeled on this page --%>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handels the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script type="text/javascript" src="/js/hub/receiptboxlabels.js"></script>

<%-- For Calendar support --%>
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<%-- Add any other javascript you need for the page here --%>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>


<script language="JavaScript" type="text/javascript">

<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	quantityError: "<fmt:message key="message.printpastreceivedquantity"/>",
	quantityIntegerError: "<fmt:message key="errors.integer"><fmt:param><fmt:message key="label.nooflabels"/></fmt:param></fmt:message>",
	startingIdIntegerError: "<fmt:message key="errors.integer"><fmt:param><fmt:message key="label.startingid"/></fmt:param></fmt:message>"
	};
</script>

<title>
	<fmt:message key="largelabels.title"/>
</title>

</head>

<body bgcolor="#ffffff">

<tcmis:form action="/printreceiptboxlabels.do" onsubmit="return submitOnlyOnce();">

<input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">
<input type="hidden" name="printerPath" id="printerPath" value="<c:out value="${printerPath}"/>">
<input type="hidden" name="paperSize" id="paperSize" value="64">

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

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="largelabels.title"/>
</td>
<td width="30%" class="headingr">
<%--<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>--%>
</td>
</tr>
</table>
</div>

<div class="contentArea">


<div class="spacerY">&nbsp;</div>

<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<%-- Error Messages Ends --%>

<c:if test="${labelDataCollection != null}" >
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
    <div class="boxhead">
      <span id="genLabelLink" style="">
         <a href="#" onclick="submitSearchForm(); return false;"><fmt:message key="labels.generatelabels"/></a>
      </span> |
      <span id="closeWindowLink" style="">
         <a href="#" onclick="closeWindow(); return false;"><fmt:message key="label.close"/></a>
      </span>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

	<c:if test="${empty tucsonRay}">
		<c:forEach var="containerLabelMasterViewBean" items="${labelDataCollection}" varStatus="status">
			<c:if test="${status.current.inventoryGroupTucsonRay}">
				<c:set var="tucsonRay" value="yes"/>
			</c:if>
		</c:forEach>
	</c:if>

<c:forEach var="containerLabelMasterViewBean" items="${labelDataCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
   <th width="5%" ><fmt:message key="label.receiptid"/></th>
   <th width="3%" ><fmt:message key="label.item"/></th>
   <th width="20%"><fmt:message key="label.description"/></th>
   <th width="5%" ><fmt:message key="receiving.label.expdate"/></th>
   <c:if test="${status.current.hub == 2124}">
   	<th width="5%" ><fmt:message key="labels.label.qtyonlabel"/></th>
   </c:if>
   <th width="5%" ><fmt:message key="label.nooflabels"/></th>
   <c:if test="${not empty tucsonRay}">
   	<th width="5%" ><fmt:message key="label.startingid"/></th>
   </c:if>
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

  <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
  <input type="hidden" name="containerLabelMasterViewBean[<c:out value="${dataCount}"/>].receiptId" id="receiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.receiptId}"/>">
  <td width="5%"><c:out value="${status.current.receiptId}"/></td>
  <td width="3%"><c:out value="${status.current.itemId}"/></td>
  <td width="20%"><c:out value="${status.current.itemDesc}"/></td>
  <c:choose>
  	<c:when test="${empty status.current.expireDate}">
	  	<td width="5%" class="red">
  			<fmt:message key="label.missing"/>
		</td>
  	</c:when>
  	<c:otherwise>
	  	<td width="5%">
		  	<c:out value="${status.current.expireDate}"/>
		</td>
  	</c:otherwise>
  </c:choose>

   <c:choose>
   <c:when test="${status.current.hub == 2124}" >
    <td width="5%">
     <input type="text" name="containerLabelMasterViewBean[<c:out value="${dataCount}"/>].quantityOnLabel" id="quantityOnLabel<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.quantityOnLabel}"/>" onchange="checkInteger('quantityOnLabel<c:out value="${dataCount}"/>')" size="5" maxlength="8" class="inputBox">
    </td>
   </c:when>
   <c:otherwise>
    <input type="hidden" name="containerLabelMasterViewBean[<c:out value="${dataCount}"/>].quantityOnLabel" id="quantityOnLabel<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.quantityOnLabel}"/>" size="5" maxlength="8" class="inputBox">
   </c:otherwise>
   </c:choose>

  <td width="5%">
    <c:if test="${not empty status.current.expireDate}">
      	<input type="text" name="containerLabelMasterViewBean[<c:out value="${dataCount}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="" onchange="checkInteger('quantityReceived<c:out value="${dataCount}"/>')" size="5" maxlength="8" class="inputBox">
    </c:if>
  </td>
   <c:if test="${status.current.inventoryGroupTucsonRay}">
     <td width="5%">
    <c:if test="${not empty status.current.expireDate}">
       <input type="hidden" name="containerLabelMasterViewBean[<c:out value="${dataCount}"/>].startingPrintId" id="startingPrintId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lastIdPrinted + 1}"/>" size="5" maxlength="8" class="inputBox"><c:choose><c:when test="${not empty status.current.lastIdPrinted}">${status.current.lastIdPrinted + 1}</c:when><c:otherwise>1</c:otherwise></c:choose> 
    </c:if>
     </td>
   </c:if>
 </tr>


   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   
   </table>
   <%-- If the collection is empty say no data found --%>
   <c:if test="${empty labelDataCollection}" >

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
</td></tr>
</table>

</c:if>

<%-- Hidden element start --%>
 <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="receiptboxlabels" id="receiptboxlabels" value="">

 </div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>

</div> <%-- close of interface --%>

</tcmis:form>
</body>
</html:html>