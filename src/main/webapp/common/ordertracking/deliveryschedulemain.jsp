<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handles all the resizing of the page and frames -->
<script src="/js/common/iframeresizemain.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<!-- Add any other javascript you need for the page here -->
<script SRC="/js/common/ordertracking/deliveryschedulemain.js" LANGUAGE="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>

<title>
<fmt:message key="deliveryschedule.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
orderedqtyisdiffqtyscheduled:"<fmt:message key="label.orderedqtyisdiffqtyscheduled"/>",
orderedqtychange:"<fmt:message key="label.orderedqtychange"/>",
orderedqtymismatch:"<fmt:message key="label.orderedqtymismatch"/>",
missingdata:"<fmt:message key="label.missingdata"/>",
partialDeliveredQtyError:"<fmt:message key="label.partialDeliveredQtyError"/>",
increaseQtyError:"<fmt:message key="label.increaseQtyError"/>",
deleteNonDeliveredDate:"<fmt:message key="label.deleteNonDeliveredDate"/>",
undoDeleteData:"<fmt:message key="label.undoDeleteData"/>",
scheduledinformationchangedcontinue:"<fmt:message key="label.scheduledinformationchangedcontinue"/>",
scheduleedqtyequalorderedqty:"<fmt:message key="label.scheduleedqtyequalorderedqty"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var windowCloseOnEsc = true;
// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="resizeFrames()" onunload="try{closeAllchildren();}catch(ex){}" onbeforeunload="if($v('callReturnToMR') == 'N' 
	&& window.frames['resultFrame'].document.getElementById('userChangedData').value == 'y'){var flag = returnToMr('X'); if (flag == false){ return false;}}">

<div class="interface" id="mainPage" style="">
<%--

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
<fmt:message key="deliveryschedule.label.title"/>
</td>
<td width="30%" class="headingr">&nbsp;</td>
</tr>
</table>
</div>
--%>

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->

<c:set var="source" value="${source}"/>    
<iframe id="searchFrame" name="searchFrame" width="100%" height="120" frameborder="0" marginwidth="0" style="" src="deliveryschedulesearch.do?action=search&source=${source}&companyId=${companyId}&prNumber=${prNumber}&lineItem=${lineItem}&requestedQty=${requestedQty}"></iframe>
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
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
         <span id="updateResultLink" style="display: none">
            <a href="#" onclick="update()"><fmt:message key="label.update"/></a>
         </span>
         <span id="frequencyLink" style="display: none">
            | <a href="#" onclick="generateFrequency()"><fmt:message key="label.frequency"/></a>
         </span>
         <span id="deleteUndoLink" style="display: none">
            | <a id="deleteUndo" style="" href="javascript:deleteNonDeliveredDate()"><fmt:message key="label.deleteNonDeliveredDate"/></a>
         </span>         
         <span id="showCalendarLink" style="display: none">
            | <a href="#" onclick="showCalendar()"><fmt:message key="label.showcalendar"/></a>
         </span>
         <span id="showSummaryLink" style="display: none">
            | <a href="#" onclick="showSummary()"><fmt:message key="label.showsummary"/></a>
         </span>
         <span id="returnToMrLink" style="display: none">
            | <a href="#" onclick="returnToMr('');"><fmt:message key="label.returntomr"/></a>
         </span>

         &nbsp;      <%-- add this to avoid IE from removing the top of the line --%>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe id="resultFrame" name="resultFrame" width="100%" height="250" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div><!-- Result Frame Ends -->

<!-- Can not have footer message because of resizing issues with iframe -->
<%--
<!-- Footer message start -->
<div id="footer" class="messageBar">545</div>
<!-- Footer message end -->
--%>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
    <input name="selectedRowId" id="selectedRowId" type="hidden" value="${param.selectedRowId}"/>
    <input name="prStatus" id="prStatus" type="hidden" value="${param.prStatus}"/>
    <input name="action" id="action" type="hidden" value=""/>
    <input name="updateCount" id="updateCount" type="hidden" value="0"/>
    <input name="callReturnToMR" id="callReturnToMR" type="hidden" value="N"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">
  <html:errors/>
</div>
</div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);
//-->
</script>

</body>

</html:html>

