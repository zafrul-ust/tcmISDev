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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->

<!-- Popup window support -->
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<title>
<fmt:message key="label.contactlog"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={
	waitingFor:"<fmt:message key="label.waitingforinputfrom"/>",
	document:"<fmt:message key="label.document"/>",
	revDateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.revisiondate"/></fmt:param></fmt:message>",
	material:"<fmt:message key="label.materialsearch"/>",
	msdsMaintenance:"<fmt:message key="msdsMaintenance"/>",
	editmsds:"<fmt:message key="label.editmdsdmaintenance"/>",
	deletemsds:"<fmt:message key="label.deletemsds"/>",
	contactPurpose:"<fmt:message key="label.purpose"/>",
	contactStatus:"<fmt:message key="label.status"/>",
	contactLogType:"<fmt:message key="label.method"/>",
	contactName:"<fmt:message key="label.contactname"/>",
	fieldsRequired:"<fmt:message key="label.atleastonerequired"/>"
};

var windowCloseOnEsc = true;
var children = new Array();
var dhxWins = null;

with ( milonic=new menuname("editMsdsMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=Edit MSDS Data ;url=javascript:openMsdsMaintenance();" );
 aI( "text=Delete MSDS ;url=javascript:deleteMsds();" );
}

drawMenus();

var materialConfig = [
	{columnId:"permission", submit:false},
	{columnId:"materialId", columnName:'<fmt:message key="label.material"/>', width:30, type:'hed'},
	{columnId:"revisionDate", columnName:'<fmt:message key="label.msdsrevisiondate"/>', width:20, type:'hed'}
];


function submit(action) {
	if (action === undefined) {
		action = "submitLog";
	}
	var msg = "";
	if ($v("contactPurpose") == "") {
		msg += "\n"+messagesData.contactPurpose;
	}
	if ($v("contactStatus") == "") {
		msg += "\n"+messagesData.contactStatus;
	}
    /*
    if ($v("contactName") == "") {
		msg += "\n"+messagesData.contactName;
	}
	*/
	if ($v("contactLogType") == "") {
		msg += "\n"+messageData.contactType;
	}
	
	if (msg != "") {
		alert(messagesData.fieldsRequired+"\n"+msg);
	}
	else {
		materialGrid.parentFormOnSubmit();
		document.getElementById("uAction").value = action;
		document.genericForm.submit();
	}
}

function cancel() {
	window.close();
}

windowCloseOnEsc = true;
var selectedMaterialRowId = -1;
var preSelect = true;

function onMaterialSelected(rowId, cellInd) {
	var index = materialGrid.getRowsNum();
	if(index == 0) {
		alert("Error");
		return;
	}
	else {
		selectedMaterialRowId = rowId;
		var id = gridCellValue(materialGrid,rowId, "materialId");
		var revDate = gridCellValue(materialGrid,rowId, "revisionDate");
		// if the selected row matches the previously selected row
		if (id == $v("currentMaterialId") && revDate == $v("currentRevisionDate")) {
			return;
		}
		else {
			document.getElementById("currentMaterialId").value = id;
			document.getElementById("currentRevisionDate").value = revDate;
			updateMsdsMenu();
			j$.ajax({
					url:"contactlog.do",
					cache:false,
					data:{uAction: "logHistory", currentMaterialId:id, currentRevisionDate:revDate},
					success: function(data) {
						if (data != null && data != "") {
							logHistoryJsonData = data;
							initGridWithGlobal(logHistoryGridConfig);
						}
					}
			});
		}
	}
	return false;
}

var materialGridConfig = {
	divName:'materialDataDiv',
	beanData:'materialJsonData',
	beanGrid:'materialGrid',
	config:'materialConfig',
	rowSpan:false,
	onRowSelect: onMaterialSelected,
	onRightClick:showEditMsdsMenu,
	submitDefault:true
};

function showEditMsdsMenu(rowId, colId) {
	onMaterialSelected(rowId, colId);
	toggleContextMenu('editMsdsMenu');
}

function showViewDocumentMenu(rowId, colId) {
	updateDocumentMenu();
	toggleContextMenu('viewDocumentMenu');
}

function myOnLoad() {
	initGridWithGlobal(materialGridConfig);
	initGridWithGlobal(logHistoryGridConfig);
}

function setStatusToDraft() {
	document.getElementById("contactStatus").value = $v("draftStatus");
}

function updateMsdsMenu() {
	var menus = new Array();
	menus[menus.length] = "text="+messagesData.editmsds+" ;url=javascript:openMsdsMaintenance();";
	
	var curMatlId = $v("currentMaterialId");
	var matlId = $v("materialId");
	var curRevDate = $v("currentRevisionDate");
	var revDate = $v("revisionDate");
	
	if (matlId == curMatlId &&
			curRevDate == revDate) {
		// do nothing
	}
	else {
		menus[menus.length] = "text="+messagesData.deletemsds+" ;url=javascript:deleteMsds();";
	}
	replaceMenu('editMsdsMenu',menus);
}


function openMsdsMaintenance() {
	var materialId  =  gridCellValue(materialGrid,materialGrid.getSelectedRowId(),"materialId"); 
	var revisionDate  =  gridCellValue(materialGrid,materialGrid.getSelectedRowId(),"revisionDate");
	var loc = "/tcmIS/catalog/msdsindexingmain.do?uAction=getMsds&materialId="+materialId+"&revisionDate="+revisionDate;

    try {
    	parent.opener.parent.openIFrame("msdsIndexing",loc,""+messagesData.msdsMaintenance,"","N");
    }
    catch (ex) {
    	var winId= "msdsIndexing "+materialId;
        openWinGeneric(loc,winId,"900","600","yes","80","80","yes");
    }       
}

function doNothing() {
}

function addMsds(materialId, revisionDate) {
	if (materialId == null || revisionDate == null) {
		var loc = "contactlog.do?uAction=msdsRevisionSelect";
		showWait(formatMessage(messagesData.waitingFor, messagesData.material));
		children[children.length] = openWinGeneric(loc,"msdsRevisionSelect","700","250","yes","100","100","20","20","no");
	}
	else {
		var ind = materialGrid.getRowsNum();
		var rowId = 0;
		
		var displayMsds = true;
		for (rowId = 1; rowId <= ind;rowId++) {
			var id = gridCellValue(materialGrid,rowId, "materialId");
			var revDate = gridCellValue(materialGrid,rowId, "revisionDate");
			if (id == materialId && revisionDate == revDate) {
				displayMsds = false;
			}
		}
		
		if (displayMsds) {
			var rowData = ['N', materialId, revisionDate];
			materialGrid.addRow(rowId, rowData, ind);
		}
	}
}

function deleteMsds() {
	if ($v("materialId") == $v("currentMaterialId") &&
			$v("revisionDate") == $v("currentRevisionDate")) {
		// THIS SHOULD NEVER HAPPEN; the menu item should not appear
		alert("Error: You cannot delete the record that you opened this contact log from.");
	}
	else {
		j$.ajax({
			url:"contactlog.do",
			cache:false,
			data:{uAction: "deleteMaterial", contactLogId:$v("contactLogId"), currentMaterialId:$v("currentMaterialId"), currentRevisionDate:$v("currentRevisionDate") },
			success: function(data) {
				materialGrid.deleteRow(selectedMaterialRowId);
			}
		});
	}
}

function uploadDocuments() {
	var contactLogId = $v("contactLogId");
	
	if (contactLogId == null || contactLogId == "") {
		submit("viewDocuments");
	}
	else {
		var loc = "contactlogdocumentview.do?uAction=view&contactLogId="+contactLogId;
		showWait(formatMessage(messagesData.waitingFor, messagesData.document));
		children[children.length] = openWinGeneric(loc,"contactLogDocumentViewer","700","320","yes","100","100","20","20","no");
	}
}

function doCallback() {
	var contactLogId = $v("contactLogId");
	if (contactLogId != "") {
		var loc = "contactlogdocumentview.do?uAction=view&contactLogId="+contactLogId;
		showWait(formatMessage(messagesData.waitingFor, messagesData.document));
		children[children.length] = openWinGeneric(loc,"contactLogDocumentViewer","700","320","yes","100","100","20","20","no");
	}
}

function select(){
	// If there was a previously selected area, re select it
	if (selectedMaterialRowId.length > 0) {
		
		setTimeout("materialGrid.selectRowById(selectedMaterialRowId ,false,true,true)",50);
	}
	else {
		if(materialGrid.getRowsNum() > 0) {
			setTimeout("materialGrid.selectRowById(1 ,false,true,true)",50);
		}
		else {
			preSelect = false;
		}
	}
}

function showWait(message){
	$("transitLabel").innerHTML = message;
	
	var transitDailogWin = $("transitDailogWin");
	transitDailogWin.style.display="";

	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}

	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(200, 150);
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

function stopShowingWait() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

// -->
</script>

<%@ include file="/catalog/contactloghistory.jsp" %>
</head>
<body bgcolor="#ffffff" onload="myOnLoad();select();<c:if test="${not empty viewDocuments }">doCallback();</c:if>" 
	onunload="myUnload();" onmouseup="toggleContextMenuToNormal();">

<div id="transitDailogWin" class="optionTitleBoldCenter" style="display: none;overflow: auto;position: absolute;">
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

<tcmis:form action="/contactlog.do" target="_self">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="" cellspacing="">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <div class="boxhead"> <%-- boxhead Begins --%>
		<a href="javascript:submit('submitLog')"><fmt:message key="button.submit"/></a>&nbsp;|
		<a href="javascript:addMsds(null,null)"><fmt:message key="label.addmsds"/></a>&nbsp;|
		<a href="javascript:uploadDocuments()"><fmt:message key="label.viewuploaddocuments"/></a>&nbsp;|
		<a href="javascript:cancel()"><fmt:message key="label.cancel"/></a>&nbsp;
		<br/>
		<c:choose>
		<c:when test="${contactLogBean.contactLogId == null}">
			<span>New contact log</span>
		</c:when>
		<c:otherwise>
			<span>Editing log # <c:out value="${contactLogBean.contactLogId}"/></span>
		</c:otherwise>
		</c:choose>
	</div> <%-- boxhead Ends --%>
    
    <table width="75%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      	<tr>
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.purpose"/>:</td>
        	<td width="10%" >
        	<select name="contactPurpose" id="contactPurpose"  class="selectBox" >
        		<c:forEach var="purpose" items="${contactLogBean.contactPurposeColl}" varStatus="pStatus">
        			<option <c:if test="${pStatus.current == contactLogBean.contactPurpose}">selected</c:if> value="${pStatus.current}">${pStatus.current}</option>
        		</c:forEach>
        	</select></td>
      	
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.method"/>:</td>
        	<td width="10%" >
        	<select name="contactLogType" id="contactLogType"  class="selectBox" >
        		<c:forEach var="logType" items="${contactLogBean.contactTypeColl}" varStatus="tStatus">
        			<option <c:if test="${tStatus.current == contactLogBean.contactLogType}">selected</c:if> value="${tStatus.current}">${tStatus.current}</option>
        		</c:forEach>
        	</select></td>
        </tr>
        <tr>
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.status"/>:</td>
        	<td width="10%" >
        	<select name="contactStatus" id="contactStatus"  class="selectBox" >
                <c:set var="selectedStatus" value="${contactLogBean.contactStatus}"/>
                <c:if test="${empty selectedStatus}">
                    <c:set var="selectedStatus" value="${contactLogBean.draftStatus}"/>    
                </c:if>

                <c:forEach var="logStatus" items="${contactLogBean.contactStatusColl}" varStatus="sStatus">
        			<option <c:if test="${sStatus.current == selectedStatus}">selected</c:if> value="${sStatus.current}">${sStatus.current}</option>
        		</c:forEach>
        	</select></td>
        	
        	<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.contactname"/>:</td>
        	<td width="10%" ><input name="contactName" id="contactName" type="text" class="inputBox" value="${contactLogBean.contactName}"/></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.externalnote"/>:</td>
        	<td colspan="3" ><input name="contactReference" id="contactReference" type="text" class="inputBox" size="75" value="${contactLogBean.contactReference}"/></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.internalnote"/>:</td>
        	<td colspan="3" ><textarea id="notes" name="notes" cols="50" class="inputBox">${contactLogBean.notes}</textarea></td>
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
<tr>
<td>
<div id="section1" class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   
   <div id="materialDataDiv" style="width:770px;height:230px;" style="display: none;"></div>
	  <c:if test="${contactLogBean.materialDataColl != null}" >
		<script type="text/javascript">
		<!--
			<%@ include file="/catalog/contactlogmaterials.jsp" %>
		// -->
		</script>
	  </c:if>
   </div>

   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td>
</tr>

<tr>
<td>
<div id="section1" class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   <div class="boxhead"> <%-- boxhead Begins --%>
   		Contact Log History
   </div>
   <div id="logHistoryDataDiv" style="width:770px;height:230px;" style="display: none;"></div>
   </div>
   
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td>
</tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	 <fmt:formatDate var="fmtRevisionDate" value="${contactLogBean.revisionDate}" pattern="${dateFormatPattern}"/>
	 <input name="uAction" id="uAction" type="hidden" value=""/>
	 <input name="materialId" id="materialId" type="hidden" value="${contactLogBean.materialId}"/>
	 <input name="revisionDate" id="revisionDate" type="hidden" value="${fmtRevisionDate}"/>
	 <input name="currentMaterialId" id="currentMaterialId" type="hidden" value=""/>
	 <input name="currentRevisionDate" id="currentRevisionDate" type="hidden" value=""/>
	 <input name="contactLogId" id="contactLogId" type="hidden" value="${contactLogBean.contactLogId}"/>
	 <input name="draftStatus" id="draftStatus" type="hidden" value="${contactLogBean.draftStatus}"/>
</div>
 
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>

<div id="hiddenFrame" style="display: none;">
	<iframe scrolling="no" id="logHistoryDataFrame" name="logHistoryDataFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
</div>
</body>
</html:html>