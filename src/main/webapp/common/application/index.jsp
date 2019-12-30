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
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/startapplication.js"></script>

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

<c:set var="module">
 <tcmis:module/>
</c:set>

<title>
        <c:if test="${module != 'boeing'}"><fmt:message key="${module}"/> </c:if><fmt:message key="label.tcmis"/>
</title>
	
<c:set var="moduleName"><fmt:message key="${module}"/></c:set>
<c:set var="moduleName"><tcmis:replace value="${moduleName}" from="," to=""/></c:set>
<c:set var="moduleName"><tcmis:replace value="${moduleName}" from="'" to=""/></c:set>
<c:set var="moduleName"><tcmis:replace value="${moduleName}" from="\"" to=""/></c:set>
<c:set var="moduleName"><tcmis:replace value="${moduleName}" from="#" to=""/></c:set>	

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

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="browserLocale" value="<%=request.getLocale()%>"/>

with(milonic=new menuname("Main Menu")){
alwaysvisible=1;
left=2.5;
itemheight=17;
orientation="horizontal";
style=menuStyle;
top=45.5;
<c:forEach var="pageBean" items="${pageBeanCollection}" varStatus="status">
<c:choose>
    <c:when test="${module == 'haas'}">
        <c:choose>
            <c:when test="${browserLocale ne 'en_GB' or status.current.pageId ne 'msds' or status.current.pageId ne 'newMsds'}">
                aI("text=<fmt:message key="${status.current.pageId}"/>;url=javascript:openIFrame('<c:out value="${status.current.pageId}"/>','<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />','<fmt:message key="${status.current.pageId}"/>','<c:out value="${status.current.iconImage}"/>','<c:out value="${status.current.iframe}"/>');image=");
            </c:when>
            <c:otherwise>
                
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        aI("text=<fmt:message key="${status.current.pageId}"/>;url=javascript:openIFrame('<c:out value="${status.current.pageId}"/>','<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />','<fmt:message key="${status.current.pageId}"/>','<c:out value="${status.current.iconImage}"/>','<c:out value="${status.current.iframe}"/>');image=");
    </c:otherwise>
</c:choose>
</c:forEach>
<c:if test="${module != 'chemtrec'}">
aI("text=<fmt:message key="label.starttcmis"/>;url=javascript:startTcmisApplication('${moduleName}');image=");
</c:if>
}

with(milonic=new menuname("appcontextMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.closetab"/>;url=javascript:closeTab();");
aI("type=header;text=<fmt:message key="label.addtabtostartup"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
aI("type=header;text=<fmt:message key="label.removepagefromstartup"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
}

// -->
</script>

<tcmis:form action="/application.do">
 <input name="action" id="action" type="hidden">
</tcmis:form>

<div id="interface" >

<!-- Header Begins -->
<div class="header">
  <div class="alignLeft"><%@ include file="/common/application/clientlogo.jsp" %></div>
  <div class="alignRight"></div>
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

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

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

<script language="JavaScript" type="text/javascript">
<!--
function startOnload()
{
	<c:set var="selectedTabId" value=''/>
	<c:set var="hasBulletin" value='N'/>
	<c:set var="hasHome" value='N'/>
	<c:set var="hasMsds" value=''/>
	
	<c:forEach var="pageBean" items="${pageBeanCollection}" varStatus="status">
	 <c:if test='${pageBean.pageId=="bulletin" && !empty connPoolMsgBeanCollection}'>
	  openIFrame('<c:out value="${status.current.pageId}"/>','<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />','<fmt:message key="${status.current.pageId}"/>','<c:out value="${status.current.iconImage}"/>','<c:out value="${status.current.iframe}"/>');
	  <c:set var="hasBulletin" value="Y"/>
	 </c:if>
    <c:choose>
      <c:when test='${module == "haas"}'>
        <c:choose>
          <c:when test='${((pageBean.pageId=="msds" || pageBean.pageId=="newMsds") and browserLocale ne "en_GB") || pageBean.pageId=="home"}'>
            openIFrame('<c:out value="${status.current.pageId}"/>','<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />','<fmt:message key="${status.current.pageId}"/>','<c:out value="${status.current.iconImage}"/>','<c:out value="${status.current.iframe}"/>');
            <c:if test='${pageBean.pageId=="home"}'>
                <c:set var="hasHome" value="Y"/>
              </c:if>
              <c:if test='${pageBean.pageId=="msds" || pageBean.pageId=="newMsds"}'>
                <c:set var="hasMsds" value="${pageBean.pageId}"/>
              </c:if>
          </c:when>
          <c:otherwise>
          </c:otherwise>
         </c:choose>
      </c:when>
      <c:otherwise>
        openIFrame('<c:out value="${status.current.pageId}"/>','<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />','<fmt:message key="${status.current.pageId}"/>','<c:out value="${status.current.iconImage}"/>','<c:out value="${status.current.iframe}"/>');
        <c:if test='${pageBean.pageId=="home"}'>
            <c:set var="hasHome" value="Y"/>
          </c:if>
          <c:if test='${pageBean.pageId=="msds" || pageBean.pageId=="newMsds"}'>
            <c:set var="hasMsds" value="${pageBean.pageId}"/>
          </c:if>
      </c:otherwise>
    </c:choose>          
	</c:forEach>

	<c:choose>
		<c:when test="${hasBulletin == 'Y'}">
			<c:set var="selectedTabId" value="bulletin"/>
		</c:when>
		<c:otherwise>
	   	<c:choose>
				<c:when test="${!empty hasMsds}">
					<c:set var="selectedTabId" value="${hasMsds}"/>
				</c:when>
				<c:otherwise>
					<c:if test="${hasHome == 'Y'}">
						<c:set var="selectedTabId" value="home"/>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<c:if test='${!empty selectedTabId}'>
 		togglePage("<c:out value="${selectedTabId}"/>");
	</c:if>
}
// -->
</script>

 </div>
<!--close interface -->
</body>
</html:html>
