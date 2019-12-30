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

<link rel="stylesheet" type="text/css" href="/css/haasMenu.css"></link>
<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<%--<script src="/js/common/formchek.js" language="JavaScript"></script>--%>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/tabs.js" language="JavaScript"></script>

<script src="/js/common/applicationIframe.js" language="JavaScript"></script>

<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/appcontextmenu.js"></script>

<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<!-- Add any other javascript you need for the page here -->


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<c:set var="companyName" value='${sessionScope.personnelBean.companyName}'/>
<title>
<c:out value="${companyName}" /> <fmt:message key="label.tcmis"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="openStartingPages()" onresize="verifySize()">

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
<tcmis:menuTag />
// -->
</script>

<tcmis:form action="/application.do">
 <input name="action" id="action" type="hidden">
</tcmis:form>

<div id="interface" >

<!-- Header Begins -->
<div class="header">
  <div class="alignLeft"><img src="/images/logo_HASS.gif" width="44" height="43"><img src="/images/logo.gif" width="174" height="43"></div>
  <div class="alignRight">
   <c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
   <c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>

   <fmt:message key="label.loggedinas"/>: <span class="optionTitleBold"><c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" /></span>
   <fmt:message key="hub.home.forcompany"/>
   <span class="optionTitleBold">
    <c:out value="${companyName}" />
   </span>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="logout(); return false;"><fmt:message key="label.logout"/></a> | <a href="javascript:opentcmISHelp();"><fmt:message key="label.help"/></a>
  </div>
</div>
<!-- Header Ends -->

<!-- Menu Begins -->
<div class="menus">
  <div class="menuContainer">&nbsp;</div>
  <div class="tabMenu">
    <div class="title">&nbsp;</div>
    <script>
     drawMenus();
    </script>
  </div>
</div>
<!-- Menu Ends-->

<!-- CSS Tabs -->
<div id="appTabs">
 <ul id="mainTabList">
  <%--<li id="homeli"><a href="#" id="homeLink" class="selectedTab" onclick="togglePage('home'); return false;"><span id="homeLinkSpan" class="selectedSpanTab"><img src="/images/home.gif" class="iconImage">Home<br class="brNoHeight"><img src="/images/minwidth.gif" width="8" height="0"></span></a></li>--%>
 </ul>
</div>
<!-- CSS Tabs End -->

<!-- Iframe Tabs -->
<div id="maindivs">
</div>
<!-- Iframe Tabs end -->


<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
function startOnload()
{
//openIFrame('home','/tcmIS/index.html','<fmt:message key="label.home"/>','/images/home.gif');
<c:set var="dataCount" value='${0}'/>
<c:set var="selectedTabId" value=''/>

<c:forEach var="personnelStartPageViewBean" items="${startPageViewBeanCollection}" varStatus="status">
 <c:if test="${dataCount == 0}">
  <c:set var="selectedTabId" value="${status.current.pageId}"/>
 </c:if>
 openIFrame('<c:out value="${status.current.pageId}"/>','<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />','<fmt:message key="${status.current.pageId}"/>','<c:out value="${status.current.iconImage}"/>','<c:out value="${status.current.iframe}"/>');
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

<c:if test="${!empty selectedTabId}" >
 togglePage("<c:out value="${selectedTabId}"/>");
</c:if>
}
<%--
openIFrame('accountsPayable','/tcmIS/Invoice/AccountsPayable','Accounts Payable','');
openIFrame('dotShippingInfo','/tcmIS/Hub/dotshipname','DOT Shipping Info','');
openIFrame('msdsMaintenance','/tcmIS/Catalog/maintainmsds','MSDS Maintenance','');
openIFrame('receiving','/tcmIS/hub/receiving.do','Receiving','');
openIFrame('receivingQc','/tcmIS/hub/receivingqc.do','Receiving QC','');
openIFrame('minMaxLevels','/tcmIS/hub/minmaxchg','Min Max Levels','');
openIFrame('cabinetDefinitions','/tcmIS/hub/cabinetdefinition.do','Cabinet Definitions','');
openIFrame('cabinetScanning','/tcmIS/hub/showcabinetcountupload.do','Cabinet Scanning','');
openIFrame('cabinetManagement','/tcmIS/hub/showcabinetmanagement.do','Cabinet Management','');
---%>
// -->
</script>

 </div>
<!--close interface -->
</body>
</html:html>