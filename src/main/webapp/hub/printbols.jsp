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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
--%>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.downloadingfile"/>
</title>

<%--
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
--%>

<c:set var="action" value='${action}'/>
<c:set var="source" value='${source}'/>   
<c:choose>
 <c:when test="${action == 'printBolShort' && source == 'pickingQC'}">
  <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols&boldetails=No">
</c:when>
 <c:when test="${action == 'printBolLong' && source == 'pickingQC'}">
  <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols&boldetails=Yes">
 </c:when>
  <c:when test="${action == 'printExitLabels' && source == 'pickingQC'}">
  <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/reprintlabels?session=Active&generate_bols=yes&boldetails=No&labelType=exitLabels&materilReqOriginCount=${materilReqOriginCount}">
  </c:when>
    <c:when test="${action == 'rePrintLabels' && source == 'pickingQC'}">
  <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/reprintlabels?session=Active&generate_bols=yes&boldetails=No&materilReqOriginCount=${materilReqOriginCount}">
  </c:when>
    <c:when test="${action == 'generatedelvtkt' && source == 'pickingQC'}">
  <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/PickingQC?session=Active&UserAction=generatedelvtkt&boldetails=No">
  </c:when>
  
 <c:when test="${action == 'printBolShort' && source == 'picking'}">
 <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/searchpicklist?session=Active&generate_bols=yes&UserAction=generatebols&boldetails=No">
 </c:when>
 <c:when test="${action == 'printBolLong' && source == 'picking'}">
<meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/searchpicklist?session=Active&generate_bols=yes&UserAction=generatebols&boldetails=Yes">
 </c:when>
 <c:when test="${action == 'printConsolidatedBol' && source == 'picking'}">
<meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/searchpicklist?session=Active&generate_picklist=yes&UserAction=genconsolidatedpicks">
 </c:when>
 <c:when test="${action == 'printBolShort' && source == 'shipconfirm'}">
 <meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/searchpicklist?session=Active&generate_bols=yes&UserAction=generatebols&boldetails=No">
 </c:when>
 <c:when test="${action == 'printBolLong' && source == 'shipconfirm'}">
<meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/searchpicklist?session=Active&generate_bols=yes&UserAction=generatebols&boldetails=Yes">
 </c:when>
 <c:when test="${action == 'printConsolidatedBol' && source == 'shipconfirm'}">
<meta http-equiv="Refresh" content="0; url=/tcmIS/Hub/ShipConfirm?session=Active&generate_labels=yes&UserAction=generateConsolidatedBol&shipmentIds=">
 </c:when>
 <c:when test="${action == 'printBoxLabels'}">
<meta http-equiv="Refresh" content="0; url=/tcmIS/hub/reprintboxlbls?HubNoforlabel=${param.branchPlant}">
 </c:when>        
</c:choose>
</head>

<body bgcolor="#ffffff">
  <div id="transitPage" class="optionTitleBoldCenter">
  <img src="/images/tcmintro.gif"  border=1 align="middle"><br><br><br><br>
    <fmt:message key="label.downloadingfile"/><br><br><br><br>
    <fmt:message key="label.pleasewaitforfile"/><br><br><br><br>
    <fmt:message key="label.closeafterdownload"/><br><br><br><br>
  </div>
</body>
</html:html>