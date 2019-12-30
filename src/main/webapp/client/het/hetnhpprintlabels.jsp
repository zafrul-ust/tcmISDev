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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<!--<script type="text/javascript" src="/js/supply/pagename.js"></script>-->

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="printnonhaaspurchased"/>
</title>

<script language="JavaScript" type="text/javascript">
var hiddenPrintIframe = null;
var printed = false;
var printing = false;

function loadJson() {
		jsonMainData = window.opener.jsonSendData;
		$('totalLines').value = jsonMainData.length + 1;
}

function submitPrint() {
	document.genericForm.target = "hiddenPrintTxtFrame";
	if (beanGrid != null)
		beanGrid.parentFormOnSubmit(); 
	initPrinting();
	document.genericForm.submit();
}

function cancelPrint() {
	onCloseWin();
}

function onCloseWin() {
	printing = false;
	printed = false;
	window.opener.parent.closeTransitWin();
	window.close();
}

function finishPrinting() {
	if (printing && !printed) {
		printed = true;
		var x=document.getElementById("hiddenPrintTxtFrame");
		var y=(x.contentWindow || x.contentDocument);
		y.focus();
		y.print();
		onCloseWin();
	}
}

function checkHiddenIFrameState() {
	if (printing && !printed) {
		if (hiddenPrintIframe != null && hiddenPrintIframe.readyState && "complete" == hiddenPrintIframe.readyState) {
			finishPrinting();
		}
		else {
			setTimeout("checkHiddenIFrameState()", 500);
		}
	}
}

function initPrinting() {
	printing = true;
	hiddenPrintIframe = document.getElementById("hiddenPrintTxtFrame");
	setTimeout("checkHiddenIFrameState()", 500);
}

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	errors:"<fmt:message key="label.errors"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>"
};

var gridConfig = {
	divName:'nhpBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:'false',			 // this page has rowSpan > 1 or not.
	submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
    noSmartRender:false 
};

/*This array defines the grid and properties about the columns in the grid.
* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
* */
var config = [
	{ columnId:"permission"},
	{ columnId:"containerId", columnName:'<fmt:message key="label.containerid"/>',	align:'center', width:7, sort: 'na'},
	{ columnId:"catPartNo", columnName:'<fmt:message key="label.partnumber"/>',	align:'center', width:7, sort: 'na'},
	{ columnId:"tradeName", columnName:'<fmt:message key="label.tradename"/>', tooltip:"Y", width:20, sort: 'na'},
	{ columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>', tooltip:"Y", width:20 , sort: 'na'},
	{ columnId:"custMsdsNo", columnName:'<fmt:message key="label.customermsdsno"/>', width:7, sort: 'na'},
	{ columnId:"manufacturer", columnName:'<fmt:message key="label.manufacturer"/>', tooltip:"Y", width:20, sort: 'na'},
	{ columnId:"expirationDate", columnName:'<fmt:message key="label.expirationdate"/>', width:7, sort: 'na'},
	{ columnId:"hetUsageRecording"},
	{ columnId:"usageLoggingReq", columnName:'OTR', width:4, sort: 'na'}
];

</script>
</head>

<body bgcolor="#ffffff"  onload="loadJson();popupOnLoadWithGrid(gridConfig);" onresize="resizeFrames()" onunload="onCloseWin()">

<tcmis:form action="/hetnhpprintlabels.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
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
      <div id="updateResultLink" style="display: none">
         <a href="#" onclick="submitPrint(); return false;"><fmt:message key="label.printlabels"/></a>
         | <a href="#" onclick="cancelPrint(); return false;"><fmt:message key="label.cancel"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

<div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="nhpBean" style="width:100%;height:600px;" style="display: none;"></div>


<!-- Search results end -->
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input name="action" id="action" value="print" type="hidden"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
<script type="text/javascript">
    <!--
    showUpdateLinks = true;
    //-->
</script>

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<iframe id="hiddenPrintTxtFrame" name="hiddenPrintTxtFrame" width="0" height="0" frameborder="0" marginwidth="0" src="blank.html" onLoad="finishPrinting();"/>

</body>
</html:html>