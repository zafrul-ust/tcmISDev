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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>


<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<%-- <script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script> --%>

<title>
  <fmt:message key="label.picklistno"/>:<c:out value="${param.picklistId}"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

var windowCloseOnEsc = true;
useLayout = false;

function setStartTime() {
	var now = new Date();
	$("startSearchTime").value = now.getTime();
}
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setStartTime()" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<div id="searchFrameDiv" style="display:none">
<iframe id="searchFrame" name="searchFrame" width="100%" height="150" frameborder="0" marginwidth="0" style="" src="/blanksearch.html"></iframe>
</div>
<!-- Search Frame Ends -->
<div class="spacerY">&nbsp;</div>
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
        <fmt:message key="label.picklistno"/>:<c:out value="${param.picklistId}"/> |
      <span id="updateResultLink" style="display:none">
        <a href="#" onclick="call('update'); return false;"><fmt:message key="label.update"/></a> |
      </span>
		 <a href="#" onclick="parent.window.close()"><fmt:message key="label.close"/></a>
      &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe id="resultFrame" name="resultFrame" width="100%" height="250" frameborder="0" marginwidth="0" src="tabletpickableunitviewresults.do?action=${param.action}&hub=${param.hub}&inventoryGroup=${param.inventoryGroup}&picklistId=${param.picklistId}&sortBy=${param.sortBy}&opsEntityId=${param.opsEntityId}"></iframe>
   </div>

   <!-- Footer message start -->
   <div id="footer" class="messageBar"></div>
    <!-- Footer message end -->
   </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>

  </div>
</div>
</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="fromPickingPicklist" id="fromPickingPicklist" type="hidden" value="${param.fromPickingPicklist}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="hub" id="hub" type="hidden" value="${param.hub}"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<%-- Delivery comments Messages Begins --%>    
<div id="deliveryCommentsMessagesArea" class="successMessages" style="display:none;z-index:5;">
<div id="deliveryCommentsMessagesAreaTitle" class="hd"><fmt:message key="label.notes"/></div>
<div id="deliveryCommentsMessagesAreaBody" class="bd">
</div>
</div>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">

</div>
</div>

</body>
</html:html>