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
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/hub/receiving.js"></script>

<title>
<fmt:message key="hubbin.add.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pickNewBin:"<fmt:message key="hubbin.add.title"/>"
}

windowCloseOnEsc = true;
var dhxWins = null;
var addOrOk = 'N';

function setUAction()
{
	if(opener.document.title == "Reconciliation")
	{
		    try{
		  	var bg = opener.window.beangrid;
		  	var bin = document.getElementById("vvHubBin").value;
		  	document.getElementById("uAction").value = "upReconcil";
		  	document.getElementById("receiptId").value = bg.cellById(bg.getSelectedRowId(),bg.getColIndexById("receiptId")).getValue();
		  	document.getElementById("bin").value = bin;
		    }catch(e){}
	}
	else {
		try {
			bin = document.getElementById("vvHubBin").value;
			opener.window.getBin(bin,bin,'${param.callbackparam}'); //value, name
			window.close();
			return false;
			
	 	}
	 	catch(e){}		
	}
}

function showVvHubBins() {
	var rowNumber = document.getElementById("rowNumber").value;
	var itemId = document.getElementById("itemId").value;
	var branchPlant =  document.getElementById("branchPlant").value;
	
	var loc = "/tcmIS/hub/showhubbin.do?addRooms=Y&branchPlant=" + branchPlant
			+ "&userAction=showBins&rowNumber=" + rowNumber + "&itemId=";
	loc = loc + itemId;
	try {
		children[children.length] = openWinGeneric(loc, "showVvHubBins2", "300",
				"150", "no", "80", "80");
	} catch (ex) {
		children[children.length] = openWinGeneric(loc, "showVvHubBins2", "300", "150", "no", "80", "80");
	}
	showTransitWin(messagesData.pickNewBin);
}

function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	var transitLabel  = document.getElementById("transitLabel");
	var transitDailogWin = document.getElementById("transitDailogWin");

	transitLabel.innerHTML =waitingMsg.replace(/[{]0[}]/g,messageType);
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 300, 150);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function addOptionItem(binN,binV)
{
   obj = document.getElementById("vvHubBin")
   var index = obj.length;
   obj.options[index]=new Option(binN,binV);
   obj.options[index].selected = true; 
}

function pageCheck()
{
	var addBin = document.getElementById("addBin");
	if(addBin != null)
	{
		if(opener.document.title == "Reconciliation")
		{
			addBin.style.visibility = 'visible';
			//button();
			addOrOk = 'Y';
		}
		else
			addBin.style.visibility = 'hidden';
	}
}

function closeAllchildren()
{
// if (document.getElementById("uAction").value != 'new') {
 try {
  for(var n=0;n<children.length;n++) {
   try {
    children[n].closeAllchildren();
    } catch(ex){}
   children[n].close();
   }
  } catch(ex){}
  children = new Array();
// }
}

var rooms = new Array();
<c:forEach var="rooms" items="${roomCollection}" varStatus="status">
	rooms[${status.index}]  = '${rooms.room}';
</c:forEach>

// -->
</script>
</head>

<c:choose>
<c:when test="${!empty resultAddBin}" >
 <body bgcolor="#ffffff" onLoad="addBintoMainPage();pageCheck();" onunload="try{opener.closeTransitWin();}catch(ex){} try{opener.parent.closeTransitWin();}catch(ex){} closeAllchildren();">
</c:when>
<c:otherwise>
 <body bgcolor="#ffffff" onLoad="pageCheck();" onunload="try{opener.closeTransitWin();}catch(ex){} try{opener.parent.closeTransitWin();}catch(ex){} closeAllchildren();">
</c:otherwise>
</c:choose>

<tcmis:form action="/showhubbin.do" onsubmit="return submitOnlyOnce();">

 <div class="interface" id="mainPage">


<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
<c:choose>
<c:when test="${!empty resultAddBin}" >
<input type="hidden" name="vvHubBin" id="vvHubBin" value="<c:out value="${vvHubBin}"/>" readonly/>
<br/><br/><fmt:message key="hubbin.add.successmessage"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr class=''>
<td width="5%">
 <html:button property="addBinToItemBinCollection" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "addBintoMainPage()">
   <fmt:message key="label.ok(done)"/>
 </html:button>
</td>
</tr>
</table>
</c:when>
<c:otherwise>

<c:choose>
   <c:when  test="${empty vvHubBinsBeanBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:when>
<c:otherwise>

<br/><br/>
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin" id ="addBin" value="+" OnClick="showVvHubBins(<c:out value="${dataCount}"/>)" >
<select class="selectBox" name="vvHubBin" id="vvHubBin" size="1">
<option value="NONE"><fmt:message key="hubbin.label.selectionfromoption"/></option>
 <c:forEach var="vvHubBinsBean" items="${vvHubBinsBeanBeanCollection}" varStatus="status">
  <option value="<c:out value="${status.current.bin}"/>"><c:out value="${status.current.bin}"/></option>
 </c:forEach>
</select>

<br/><br/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    

<tr class=''>
<td width="5%">
<html:submit property="addBinToItemBinCollection" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="setUAction()"> 
   <c:choose>
   		<c:when test="${param.pageId eq 'recon'}" >
      		<fmt:message key="label.ok(done)"/>
        </c:when>
		<c:otherwise>
   			<fmt:message key="label.add"/>
   		</c:otherwise>
   </c:choose>
</html:submit>
</td>

<td width="5%"class="optionTitleLeft">
 <html:button property="cancelButton" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.cancel"/>
 </html:button>
</td>
</tr>
</table>
</c:otherwise>

</c:choose>
</c:otherwise>
</c:choose>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<!--  Transit Modal Win -->
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="itemId" id="itemId" value="<c:out value="${itemId}"/>" readonly>
   <input type="hidden" name="rowNumber" id="rowNumber" value="<c:out value="${rowNumber}"/>" readonly>
   <input type="hidden" name="branchPlant" id="branchPlant" value="<c:out value="${branchPlant}"/>" readonly>
   <input type="hidden" name="uAction" id="uAction" value="">
   <input type="hidden" name="receiptId" id="receiptId" value="">
   <input type="hidden" name="bin" id="bin" value="">
   <input type="hidden" name="room" id="room" value="">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>