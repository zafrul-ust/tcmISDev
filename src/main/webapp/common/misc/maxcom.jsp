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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

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
<fmt:message key="label.loadingrequestedpage"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function openMaxcom()
{
//loc = "http://69.94.137.52/mcthc/index.jsp?tu=happy&tc=AERO_ECO";
//loc = "http://apps.tcmis.com/mcthc/index.jsp?tu=mbradford&tc=RAYTHEON";   
loc ="https://apps.tcmis.com/mcthc/index.jsp?tu=${logonId}&tc=${companyid}";
openWinGeneric(loc,'maxcom_application','800','600','yes');
}
// -->
</script>


<c:set var="companyid" value='${sessionScope.personnelBean.companyId}'/>
<c:set var="logonId" value='${sessionScope.personnelBean.logonId}'/>

<c:if test="${!empty logonId}">
<meta http-equiv="Refresh" content="0; url=http://apps.tcmis.com/mcthc/index.jsp?tu=${logonId}&tc=${companyid}">
<!--<meta http-equiv="Refresh" content="0; url=http://69.94.137.52/mcthc/index.jsp?tu=happy&tc=AERO_ECO">-->
<!--<meta http-equiv="Refresh" content="0; url=http://apps.tcmis.com/mcthc/index.jsp?tu=mbradford&tc=RAYTHEON">-->
</c:if>
</head>

<body bgcolor="#ffffff"  onload="">
  <div id="transitPage" class="optionTitleBoldCenter">
   <img src="/images/tcmintro.gif"  border=1 align="middle"><br><br><br><br>
   <fmt:message key="label.loadingrequestedpage"/><br><br><br><br>
   <fmt:message key="label.pleasewaitforredirection"/><br><br><br><br>
  </div>
</body>
</html:html>


