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
<script type="text/javascript" src="/js/common/standardmain.js" ></script>
<script type="text/javascript" src="/js/hub/receivingitemsearchmain.js"></script>
  
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<!--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>-->

<title>
<fmt:message key="label.receivingitemsearch"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

//var returnSelectedUserName=null;
var returnSelectedUserId=null;
var valueElementId=null;
var displayElementId=null;    
// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<!--<div class="topNavigation" id="topNavigation">

</div>-->

<!-- Search Frame Begins -->
<div id="searchFrameDiv" style="display:none">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->
<iframe id="searchFrame" name="searchFrame" width="100%" height="0" frameborder="0" marginwidth="0" style="" src="/blanksearch.html"></iframe>
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
      <div id="mainUpdateLinks" style="display: none"> <%--  --%>
        <span id="selectedItem">&nbsp;</span>
        <span id="selectedItem">
         <a href="#" onclick="submitNewChemRequest(); return false;"><fmt:message key="label.newchemrequest"/></a> |
        </span>
        <span id="closeWindowLink" style="">
         <a href="#" onclick="closeWindow(); return false;"><fmt:message key="label.close"/></a>
        </span>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe id="resultFrame" name="resultFrame" width="100%" height="250" frameborder="0" marginwidth="0" style="" src="receivingitemsearchresults.do?action=search&receipts=${param.receipts}&listItemId=${param.listItemId}&inventoryGroup=${param.inventoryGroup}&catPartNo=${param.catPartNo}&catalog=${param.catalog}&catalogCompanyId=${param.catalogCompanyId}"></iframe>
   </div>

   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>

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
  <input name="receipts" id="receipts" value="${param.receipts}" type="hidden"/>
  <input name="listItemId" id="listItemId" value="${param.listItemId}" type="hidden"/>

<tcmis:form action="/newitemform.do">
  <input name="listItemId" id="listItemId" type="hidden" value="${param.listItemId}">
  <input name="catPartNo" id="catPartNo" type="hidden" value="${param.catPartNo}">
  <input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${param.catalogCompanyId}">
  <input name="catalogId" id="catalogId" type="hidden" value="${param.catalog}">
  <input name="receiptId" id="receiptId" type="hidden" value="${param.receipts}">
  <input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">
</tcmis:form>

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
