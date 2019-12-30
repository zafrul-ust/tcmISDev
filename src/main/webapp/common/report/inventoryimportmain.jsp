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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/clientchemlistmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="inventoryImport"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
function generateExcel() {

//	if (validateSearchForm()) 
	{
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',
				'Inventory_Import_Excel', '650', '600', 'yes');
		document.genericForm.target = 'Inventory_Import_Excel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.pleasewaitmenu+"...";
	$("transitLabel").innerHTML = waitingMsg;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";
//	alert('here');
	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		transitWin.setModal(true);  // freeze the window here
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function stopShowingWait() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

function uploadList() {
	openWinGeneric("uploadinventoryimport.do?uAction=showUploadList&facilityId="+$v('facilityId'),"uploadList","450","170","yes","80","80");
	showTransitWin(messagesData.pleasewait);
}


		var altFacilityId = new Array(
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			'<tcmis:jsReplace value="${facility.facilityId}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altFacilityName = new Array(
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   '<tcmis:jsReplace value="${facility.facilityName}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altWorkAreaGroupId = new Array();
		var altWorkAreaGroupDesc = new Array();
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   <c:set var="wagCount" value='0'/>
			altWorkAreaGroupId['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
					''
			<c:forEach var="wagBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:if test="${wagBean.reportingEntityStatus == 'A'}">
					,'<tcmis:jsReplace value="${wagBean.reportingEntityId}"/>'
					<c:set var="wagCount" value='${wagCount+1}'/>
				</c:if>
			</c:forEach>
			);
		   <c:set var="wagCount" value='0'/>
			altWorkAreaGroupDesc['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
					'All'
		 	<c:forEach var="wagBean"  items="${facility.workAreaGroupColl}" varStatus="status1">
		 	<c:if test="${wagBean.reportingEntityStatus == 'A'}">
					,'<tcmis:jsReplace value="${wagBean.reportingEntityDesc}"/>'
					<c:set var="wagCount" value='${wagCount+1}'/>
				</c:if>
			</c:forEach>
			);
		</c:forEach>
	
		var altWorkAreaArray = new Array();
		var altWorkAreaDescArray = new Array();
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-']=new Array('');
			altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-']=new Array('All');
		</c:forEach>
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-All'] = new Array(
					''
			<c:forEach var="workAreaBean" items="${facility.facilityWorkAreaColl}" varStatus="status1">
				<c:if test="${workAreaBean.status == 'A'}">
					,'<tcmis:jsReplace value="${workAreaBean.application}"/>'
					<c:set var="workAreaCount" value='${workAreaCount+1}'/>
				</c:if>
			</c:forEach>
			);
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-All'] = new Array(
			'All'
			<c:forEach var="workAreaBean" items="${facility.facilityWorkAreaColl}" varStatus="status1">
				<c:if test="${workAreaBean.status == 'A'}">
					,'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
					<c:set var="workAreaCount" value='${workAreaCount+1}'/>
				</c:if>
			</c:forEach>
		    	);
		</c:forEach>

		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:forEach var="workAreaGroupBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:set var="workAreaCount" value='${0}'/>
				altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				''
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						,'<tcmis:jsReplace value="${workAreaBean.application}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
				);
				<c:set var="workAreaCount" value='0'/>
				altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				'All'						
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						,'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
		      		);
			</c:forEach>
		</c:forEach>
		
var permissionArr = new Array();
<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
    <tcmis:facilityPermission indicator="true" userGroupId="ExternalDataImport" facilityId="${status.current.facilityId}">
		permissionArr['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = 'Y';
	</tcmis:facilityPermission> 
</c:forEach>

var purchasingMethodArr = new Array(
<c:forEach var="pur" items="${purchasingMethod}" varStatus="status">
   <c:if test="${status.index > 0}">,</c:if>
   {
		facilityId:'<tcmis:jsReplace value="${pur.facilityId}"/>',
		purchasingMethodId:'<tcmis:jsReplace value="${pur.purchasingMethodId}"/>',
		purchasingMethodName:'<tcmis:jsReplace value="${pur.purchasingMethodName}"/>'
	}
</c:forEach>
);

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
all:"<fmt:message key="label.all"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="loadLayoutWin('','inventoryImport');myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>


<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/inventoryimportresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <!-- Search Option Table Start -->
<table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
        <td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.facility"/>:</td>
        <td width="25%" class="optionTitleBoldLeft">
            <select class="selectBox"  name="facilityId" id="facilityId" size="1" onchange="facilityChanged()"></select>
        </td>
        <td width="10%" class="optionTitleBoldRight">
            <fmt:message key="label.entrytype" />:&nbsp;
        </td>
        <td width="50%" class="optionTitleLeft">
            <select name="type" class="selectBox" id="type">
                <option value=""><fmt:message key="label.all" /></option>
                <option value="M"><fmt:message key="label.manual" /></option>
                <option value="I"><fmt:message key="label.import" /></option>
            </select>
        </td>
    </tr>
    <tr>
        <td  class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.workareagroup"/>:</td>
        <td class="optionTitleBoldLeft">
            <select class="selectBox"  name="workAreaGroup" id="workAreaGroup" size="1"  onchange="workAreaGroupChanged()"></select>
        </td>
        <td width="10%" class="optionTitleBoldRight">
            <fmt:message key="label.source" />:&nbsp;
        </td>
        <td width="50%" class="optionTitleLeft">
            <select name="source" class="selectBox" id="source">
                    
            </select>
        </td>
    </tr>
    <tr>
        <td  class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.workarea"/>:</td>
        <td  class="optionTitleBoldLeft">
            <select name="workArea" id="workArea" class="selectBox" size="1" onchange="workAreaChanged(this.selectedIndex)"></select>
        </td>
	<td  class="optionTitleBoldRight" nowrap="true"><fmt:message key="cyclecountresults.label.uploadid"/>:</td>
	<td width="25%" class="optionTitleBoldLeft">
		<input class="inputBox" type="text" name="uploadId" id="uploadId" value='' size="25" />
	</td>
</tr>
<tr>
	<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.partnum"/>:</td>
	<td width="25%" class="optionTitleBoldLeft">
		<input class="inputBox" type="text" name="partNo" id="partNo" value='' size="25" />
	</td>
	<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.entrydate"/>:</td>
	<td width="25%" class="optionTitleBoldLeft" nowrap="nowrap">
            <input type="text" readonly name="entryStartDate" id="entryStartDate" onclick="return getCalendar(document.genericForm.entryStartDate,null,document.genericForm.entryEndDate,null,null,'N');" class="inputBox pointer" value='' maxlength="10" size="8"/>
            &nbsp;<fmt:message key="label.to"/>&nbsp;
            <input type="text" readonly name="entryEndDate" id="entryEndDate" onclick="return getCalendar(document.genericForm.entryEndDate,document.genericForm.entryStartDate,null,null,null,'N');" class="inputBox pointer" value='' maxlength="10" size="8"/>
	</td>
</tr>												
<tr>
	<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.msdsnumber"/>:</td>
	<td width="25%" class="optionTitleBoldLeft">
		<input class="inputBox" type="text" name="msdsNo" id="msdsNo" value='' size="25" />
	</td>
	<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.storagedate"/>:</td>
	<td width="25%" class="optionTitleBoldLeft" nowrap="nowrap">
            <input type="text" readonly name="usageStartDate" id="usageStartDate" onclick="return getCalendar(document.genericForm.usageStartDate,null,document.genericForm.usageEndDate,null,null,'N');" class="inputBox pointer" value='' maxlength="10" size="8"/>
            &nbsp;<fmt:message key="label.to"/>&nbsp;
            <input type="text" readonly name="usageEndDate" id="usageEndDate" onclick="return getCalendar(document.genericForm.usageEndDate,document.genericForm.usageStartDate,null,null,null,'N');" class="inputBox pointer" value='' maxlength="10" size="8"/>
	</td>
</tr>												
<tr>
	<td colspan=4 class="optionTitleBoldLeft">
		 <input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key='label.search'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return search()"/>

		 <input name="buttonCreateExcel" id="buttonCreateExcel" type="button"
                                    class="inputBtns" value="<fmt:message key="label.createexcel"/>"
                                    onmouseover="this.className='inputBtns inputBtnsOver'"
                                    onmouseout="this.className='inputBtns'"
                                    onclick="generateExcel(); return false;">
		 <input name="uploadB" id="uploadB" type="button"
	                                    class="inputBtns" value="<fmt:message key="label.uploadinventorydata"/>"
	                                    onmouseover="this.className='inputBtns inputBtnsOver'"
	                                    onmouseout="this.className='inputBtns'"
	                                    onclick="uploadList(); return false;">
		</td>		
	</tr>
</table>
    <!-- Search Option Table end -->
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
<input name="uAction" id="uAction" type="hidden" value="search">
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="108">
<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}"/>
<input name="myDefaultFacilityId" id="myDefaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}"/>
<input name="workAreaName" id="workAreaName" type="hidden" value=""/>
</div>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

<!-- Search Frame Ends -->



<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
      --%>        
    <div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
         <div id="updateResultLink" style="display:">
        	  	<a href="#" onclick="call('addInventory');return false;"><fmt:message key="label.add"/></a>&nbsp;|&nbsp;
        	  	<a href="#" onclick="call('submitUpdate');return false;"><fmt:message key="label.delete"/></a>
    	</div>
    	
     </div> <%-- mainUpdateLinks Ends --%>
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
</td></tr>
</table>
</div>  
</div><!-- Result Frame Ends --> 

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>


</body>
</html:html>