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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>
<%@ include file="/common/opshubig.jsp"%>
<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
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
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/customerreturns.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title><fmt:message key="raytheonInactiveChargeNo"/></title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages

function createXSL(){
	  {   
		if( !validateForm() ) return;
//		var dropdowns= new Array('opsEntityId','hub','inventoryGroup','facilityId','sortBy');
//		setDropDownNames(dropdowns);
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','RaytheonChargeNoReport','650','600','yes');
	    resulttarget = document.genericForm.target;
	    document.genericForm.target='RaytheonChargeNoReport';
	    var a = window.setTimeout("document.genericForm.submit();document.genericForm.target=resulttarget",50);
	  }
}  

function validateForm() 
{  
/*  if(!isInteger(document.genericForm.itemId.value.trim(), true)) {
    alert(messagesData.itemIdInteger);
    return false;
  }
  if(!isInteger(document.genericForm.receiptId.value.trim(), true)) {
    alert(messagesData.receiptIdInteger);
    return false;
  }
  if(!isFloat(document.genericForm.mrNumber.value.trim(), true)) {
    alert(messagesData.prNumberInteger + "\n" + '<fmt:message key="label.mr"/>');
    return false;
  }
  if(!$v('hub') ) {
	alert(messagesData.pleaseselectahub);
	return false;
  }
  if(!$v('mrNumber')&&!$v('receiptId')) {
	    alert(messagesData.atleastonerequired + "\n" + '<fmt:message key="label.receiptid"/>' +"\n" + '<fmt:message key="label.mr"/>');
	    return false;
  }
  try {
		var hub = document.getElementById("hub");
		document.getElementById("hubName").value = hub[hub.selectedIndex].text;
  }catch(ex){}
  */
  return true;
}
/*
function doReprint(){
			var loc = "/tcmIS/Hub/ShowUnconfirmedReceipts?session=Active&HubNo="+$v('hub')+"&customownd=yes&genLabels=1";
			var winname = null;
			try {
				winname = openWinGeneric(loc, "unConfirmedReceipts", "300", "150", "no", "80", "80");
				children[children.length] = winname;
				} catch (ex) {
//				openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
			}
//	https://demo/tcmIS/Hub/ShowUnconfirmedReceipts?session=Active&HubNo=2125&customownd=yes&genLabels=1
}
		var altFacilityId = new Array();
		var altFacilityName = new Array();
		<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.branchPlant}'/>
		  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
		  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
		    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
		    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

		      altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
		      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
		        ,"<c:out value="${status2.current.facilityId}"/>"
		      </c:forEach>
		      );

		      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
		      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
		        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
		      </c:forEach>
		      );

		  </c:forEach>
		</c:forEach>		
		
defaults.fac = {id:'',name:'<fmt:message key="label.all"/>'};
		

function showFacilityIdOptions(selectedInventoryGroup) {

var facilityIdArray = new Array();
facilityIdArray = altFacilityId[selectedInventoryGroup];
var facilityNameArray = new Array();
facilityNameArray = altFacilityName[selectedInventoryGroup];


if(facilityIdArray == null || facilityIdArray.length == 0) {
 setOption(0,messagesData.all,"", "facilityId")
}
else {
 for (var i=0; i < facilityIdArray.length; i++) {
   setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId");
 }
}
}

function inventoryGroupChanged() {
var inventoryGroup0 = document.getElementById("inventoryGroup");
var facilityId0 = document.getElementById("facilityId");
var selectedInventoryGroup = inventoryGroup0.value;
if(facilityId0 != null) {
 for (var i = facilityId0.length; i > 0; i--) {
   facilityId0[i] = null;
 }
}
showFacilityIdOptions(selectedInventoryGroup);
// facilityId0.selectedIndex = 0;
}

defaults.hub.callback = inventoryGroupChanged; 
//	var 	defaultOpsEntityId = '${param.hub}';
//	var 	defaultHub = '${param.inventoryGroup}';
//	var 	preferredInventoryGroup = '${param.facilityId}';
*/
var _submitcount = 0;
var preReturnCredit = false; // default customer owned.
function checksearch() {
	var checkboxchanged = false;
	if( $('returnForCredit2').checked ) {
		if( preReturnCredit == false )
			checkboxchanged = true;
		preReturnCredit = true;
	}
	else {
		if( preReturnCredit == true )
			checkboxchanged = true;
		preReturnCredit = false;
	}
	if( checkboxchanged && _submitcount > 0 )
		submitSearchForm();
}

function submitSearchForm() {
	var ee = window.event;
	if( ee == null ) ee = arguments[0];
	var now = new Date();
	document.getElementById('startSearchTime').value = now.getTime(); 
	if( validateForm() && submitSearchOnlyOnce() ) { 
		  var now = new Date();
		  document.getElementById("startSearchTime").value = now.getTime();
		  $('uAction').value = 'search';
		  showPleaseWait();
		  _submitcount++;
		  document.genericForm.submit();
		return false;
	}
	return false;//1174146
}

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemIdInteger:"<fmt:message key="error.item.integer"/>",
receiptIdInteger:"<fmt:message key="error.receiptid.integer"/>",
prNumberInteger:"<fmt:message key="msg.selectNumber"/>",
pleaseselectahub:"<fmt:message key="label.pleaseselectahub"/>",
atleastonerequired:"<fmt:message key="label.atleastonerequired"/>",
validvalues:"<fmt:message key="label.validvalues"/>"};
// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
If you dont want to use the layout set javascript variable useLayout=false;
It is important to send the pageId if this page is going to open in a tab in the application.
You can also call any functions you need to do your search initialization for drop downs.
-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','rayinactivechargeno');" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search div Begins -->
<div id="searchFrameDiv">
<!-- start of contentArea -->
<div class="contentArea">
<tcmis:form action="/rayinactivechargenoresult.do" onsubmit="return submitSearchForm();" target="resultFrame">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td nowrap width="5%" class="optionTitleBoldRight"><fmt:message key="label.searchby"/>:</td>
        <td width="30%" class="optionTitleBoldLeft" nowrap="nowrap">
<input type="radio" class="radioBtns" name="inactiveChargeNumber" value="Y" checked="checked" id="returnForCredit1" onclick="checksearch()"/>
<fmt:message key="label.inactiveChargeNumber"/>
<input type="radio" class="radioBtns" name="inactiveChargeNumber" value="N" id="returnForCredit2" onclick="checksearch()"/>
<fmt:message key="label.invalidDirectedCharge"/>
        </td>
      </tr>
      <tr>
        <td width="100%" class="optionTitleBoldLeft" colspan="4" nowarp="nowarp">
          <input onclick="return submitSearchForm()" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="search" id="search"/>
          <input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createXSL()"/>
        </td>
      </tr>
    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value=""/>
<input name="hubName" id="hubName" type="hidden" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="133"/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search div Ends -->

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
	  <div id="mainUpdateLinks" style="display: "> <%-- mainUpdateLinks Begins --%>
      <%--<div id="updateResultLink" style="display: "> take a look of this --%>
      <span id="updateResultLink" style="display: ">
<%--          <a href="#" onclick="call('receiveReturns');" ><fmt:message key="label.receive"/></a> --%> 
      </span>
      &nbsp;
      </div>
     </div> <%-- mainUpdateLinks Ends | 
         <a href="#" onclick="doReprint(); return false;" ><fmt:message key="label.reprint"/></a>--%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

</div> <!-- close of interface -->

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>
