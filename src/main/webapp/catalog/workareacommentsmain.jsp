<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
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

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>



<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
<title>
<fmt:message key="workareacomments.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};


function loadSearchFrame() {
 url	 = 
			'workareacommentssearch.do?catPartNo='+ encodeURIComponent( '${param.catPartNo}' ) +
            '&partGroupNo=${param.partGroupNo}' +
            '&applicationId=' + encodeURIComponent( '${param.applicationId}' ) +
   			'&facilityId=${param.facilityId}'+
            '&catalogId=${param.catalogId}';
            
            document.getElementById('searchFrame').src = url;
            
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="resizeFrames();" onload="loadSearchFrame()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them.
     If you need to set the widht of the search section you do it on the searchMaskTable in the search.jsp
 -->
 <iframe id="searchFrame" name="searchFrame" width="100%" height="120" frameborder="0" marginwidth="0" style="" src=""></iframe>
</div>
<!-- Search Frame Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="display: none;margin: 0px 4px 0px 4px;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50%.
     You also need to set a variable resultWidthResize to false in pagenameresults.jsp.
-->
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
          Please keep the <span></span> on the same line this will avoid extra virtical space. 
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
<%-- 
      <span id="showlegendLink"><a href="javascript:showreceivingpagelegend()"><fmt:message key="label.showlegend"/></a></span> |
--%>
      <span id="updateResultLink" style="display: none"> <a href="#" onclick="call('submitUpdate'); return false;"><fmt:message key="label.submitChanges"/></a></span>
     </div> <%-- mainUpdateLinks Ends --%>
<%-- OR --%>
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
		<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <div id="updateResultLink" style="display: none">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
<%-- END OF OR --%>
	 </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe id="resultFrame" name="resultFrame" width="100%" height="250" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>

   <!-- Footer message start -->
    <div id="footer" class="messageBar">545</div>
   <!-- Footer message end -->    
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">
</div>
</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display:none;z-index:5;">
<div id="showLegendAreaTitle" class="hd"><fmt:message key="label.showlegend"/></div>
<div id="showLegendAreaBody" class="bd">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="pink legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.aog"/></td></tr>
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalorders"/></td></tr>
    <tr><td width="100px" class="yellow legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.pendingcancellation"/></td></tr>
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="ordertracking.label.cancelled"/></td></tr>
  </table>
</div>
</div>

<%-- Notes Messages Begins --%>
<div id="showNotesArea" class="successMessages" style="display:none;z-index:5;">
<div id="showNotesAreaTitle" class="hd"><fmt:message key="label.notes"/></div>
<div id="showNotesAreaBody" class="bd">
</div>
</div>



<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);
//-->
</script>

</body>
</html>