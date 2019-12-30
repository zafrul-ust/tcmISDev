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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/mrreleaseresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="mrrelease.title"/>
</title>

<c:set var="showUpdateLink" value='N'/>
<c:set var="checkBoxPermission" value='N'/>
<c:set var="showCheckBox" value='N'/>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
mrallocationreport:"<fmt:message key="mrallocationreport.label.title"/>",
cancelmrreason:"<fmt:message key="label.cancelmrreason"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
inputDate:"<fmt:message key="label.inputpromiseddate"/>",
open:"<fmt:message key="label.open"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function releaseMR() {
	
	var parNumber = null;
	var companyId = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	companyId = cellValue(beangrid.getSelectedRowId(),"companyId");	
	
	if( (null!=prNumber && ''!=prNumber) && (null!= companyId&& ''!=companyId))
	{	
		var flag = true;
/*		mymap = lineIdMap[rowId0];
		for(var j = 0;j < mymap.length; j++) {
			flag = validatDate(mymap[j]); 
			if(flag == false) break;
		} */
		
		if(flag) {
			document.mrrelease.target='';
			$('prNumber').value = prNumber;
			$('companyId').value = companyId;
			
			if (beangrid != null) 
	       		beangrid.parentFormOnSubmit();
	
			document.getElementById('action').value = 'releaseCreditHold';
//			   parent.showTransitWin();
			parent.showPleaseWait();
			var now = new Date();
			parent.document.getElementById("startSearchTime").value = now.getTime();
			document.mrrelease.submit();
		}
	}
}


function processReqChangeJSON(rowid,msg) 
{   
	//grid.deleteRow(rowid)  
	//haasDeleteRowFromRowSpan
	parent.closeTransitWin();//stopPleaseWait();
	if( ! msg )  
		beangrid.haasDeleteRowFromRowSpan(rowid);
	else {
		$('errorMessagesAreaBody').innerHTML = msg;
		showErrorMessages();
	}
}

// old ajax call
function releaseMR_RightClick() {
	var parNumber = null;
	var companyId = null;
	prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	companyId = cellValue(beangrid.getSelectedRowId(),"companyId");	
	
	if( (null!=prNumber && ''!=prNumber) && (null!= companyId&& ''!=companyId))
	{	
		var flag = true;
/*		mymap = lineIdMap[rowId0];
		for(var j = 0;j < mymap.length; j++) {
			flag = validatDate(mymap[j]); 
			if(flag == false) break;
		} */
		
		if(flag) {

/*
			document.mrrelease.target='';
			$('prNumber').value = prNumber;
			$('companyId').value = companyId;
			
			if (beangrid != null) 
	       		beangrid.parentFormOnSubmit();
	
			document.getElementById('action').value = 'releaseCreditHold';
			var now = new Date();
			parent.document.getElementById("startSearchTime").value = now.getTime();
			document.mrrelease.submit();
*/
//parent.showPleaseWait();

			   parent.showTransitWin();
			   url = "mrreleaseresults.do?uAction=releasecreditnew&prNumber="+prNumber+"&companyId="+companyId+"&rowid="+beangrid.getSelectedRowId()+"&callback=processReqChangeJSON";
			   callToServer(url);
		}
	}
}

with(milonic=new menuname("menu3")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
//       aI("text=<fmt:message key="label.releasemr"/>;url=javascript:releaseMR();");
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

with(milonic=new menuname("menu31")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
       aI("text=<fmt:message key="label.releasemr"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

with(milonic=new menuname("menu7")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
       <%--aI("text=<fmt:message key="label.acceptmr"/>;url=javascript:acceptMR();");--%>
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

with(milonic=new menuname("menu6")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.releaseline"/>;url=javascript:releaseMRLine();");
       aI("text=<fmt:message key="label.createeditlinenote"/>;url=javascript:showMrLineNotes();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}


with(milonic=new menuname("menu4")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
       aI("text=<fmt:message key="label.releasemr"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

with(milonic=new menuname("menu5")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
       aI("text=<fmt:message key="label.releasemr"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.createeditlinenote"/>;url=javascript:showMrLineNotes();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

with(milonic=new menuname("menu2")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
       aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

with(milonic=new menuname("menu1")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
	    aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
	    aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

drawMenus();

useexternalevent = true;
var externalevent = null;

function selectRow(rowId,cellInd){
	rowId0 = arguments[0];
	colId0 = arguments[1];

// color style stuff
    ee     = arguments[2];

    rightClick = false; 
    popdown();
    externalevent = null;

    if( ee != null ) {
    		if( ee.button != null && ee.button == 2 ) rightClick = true;
    		else if( ee.which == 3  ) rightClick = true;
    		if( rightClick) externalevent = ee;
    }
    
    selectedRowId = rowId0;

//    return; // no menu
	if( !rightClick ) return;
	var line = cellValue(selectedRowId,"lineItem");
    if( cellInd > 18 && ! line ) {toggleContextMenu('');return true;}
    menuChoice = cellValue(selectedRowId,"menuChoice");
	if (cellInd > 18) {
	    var vvitems = new Array();
		vvitems[vvitems.length] = "text=<fmt:message key="label.openmr"/>;url=javascript:openMr();";

var releaseExpertReviewHold = "text=<fmt:message key="label.releaseexpertreviewhold"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";     
<tcmis:opsEntityPermission indicator="true" userGroupId="expertReviewer" opsEntityId="${param.opsEntityId}">
	releaseExpertReviewHold = "text=<fmt:message key="label.releaseexpertreviewhold"/> "+line+";url=javascript:releaseExpertReviewHold();";
</tcmis:opsEntityPermission>
        
        if(cellValue(selectedRowId,"menuChoice") == '3' && cellValue(selectedRowId,"status") == 'Force Hold')
			vvitems[vvitems.length] = "text=<fmt:message key="label.releaseforcehold"/> "+line+";url=javascript:releaseForceHold();";
		else if(cellValue(selectedRowId,"menuChoice") == '3' && (cellValue(selectedRowId,"status") == 'Pending Expert Review' || cellValue(selectedRowId,"status") == 'Expert Review Hold') && cellValue(selectedRowId,"expertReviewHold") == 'N' && cellValue(selectedRowId,"forceHold") == 'Y')
			vvitems[vvitems.length] = "text=<fmt:message key="label.releaseforcehold"/> "+line+";url=javascript:releaseForceHold();";
		else if(cellValue(selectedRowId,"menuChoice") == '3' && (cellValue(selectedRowId,"status") == 'Pending Expert Review' || cellValue(selectedRowId,"status") == 'Expert Review Hold'))
			vvitems[vvitems.length] = releaseExpertReviewHold;
		else if((cellValue(selectedRowId,"menuChoice") == '3' || cellValue(selectedRowId,"menuChoice") == '6' ) && cellValue(selectedRowId,"status") != 'Pending Acceptance')
			vvitems[vvitems.length] = "text=<fmt:message key="label.releaseline"/> "+line+";url=javascript:releaseMRLine();";
			
vvitems[vvitems.length] = "text=<fmt:message key="label.createeditlinenote"/> "+line+";url=javascript:showMrLineNotes();";
		if(cellValue(selectedRowId,"menuChoice") != '1' && cellValue(selectedRowId,"lineItem") == 0) {
			vvitems[vvitems.length] = "text=<fmt:message key="label.showallocatableig"/> "+line+";url=javascript:allocationPopup('IG');";
			vvitems[vvitems.length] = "text=<fmt:message key="label.showallocatableregion"/> "+line+";url=javascript:allocationPopup('REGION');";
			vvitems[vvitems.length] = "text=<fmt:message key="label.showallocatableglobal"/> "+line+";url=javascript:allocationPopup('GLOBAL');";
		}
// larry testing.
//		vvitems[vvitems.length] = "text=Larry Test "+line+";url=javascript:releaseCreditHold();";
		replaceMenu('menu5',vvitems);
	   	toggleContextMenu('menu5');
	   	return true;
/*			
	       aI("text=<fmt:message key="label.open"/> <fmt:message key="label.mr"/>;url=javascript:openMr();");
	       aI("text=<fmt:message key="label.printproforma"/>;url=javascript:printPerforma();");
	       aI("text=<fmt:message key="label.releasemr"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
	       aI("text=<fmt:message key="label.cancel"/>;url=javascript:cancelMR();");
	       aI("text=<fmt:message key="label.createeditlinenote"/>;url=javascript:showMrLineNotes();");
*/			
	}
	
	if(cellValue(selectedRowId,"menuChoice") == '3' && (cellValue(selectedRowId,"status") == 'Force Hold' || cellValue(selectedRowId,"status") == 'Pending Expert Review' || cellValue(selectedRowId,"status") == 'Expert Review Hold'))
		toggleContextMenu('menu4');
	else
    	toggleContextMenu('menu'+menuChoice);
}


<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>

var config = [
{
  columnId:"permission"
},
{ columnId:"inventoryGroup",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  tooltip:"Y",
  width:14
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  tooltip:"Y",
  width:12
},
{ columnId:"okPermission"
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  permission:true,
  type:'hchstatus',
  width:3
},
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customer"/>',
  tooltip:"Y",
  width:14
},
{ columnId:"materialRequestOrigin",
  columnName:'<fmt:message key="label.materialrequestorigin"/>',
  tooltip:"Y",
  width:9
},
{ columnId:"shipto",
  columnName:'<fmt:message key="label.shipto"/>',
  tooltip:"Y",
  width:16
},
{ columnId:"customerref",
  columnName:'<fmt:message key="label.customerreference"/>',
  tooltip:"Y",
  width:11
},
{ columnId:"prNumber",
  columnName:'<fmt:message key="label.mr"/>',
  width:6
},
{ columnId:"personnelName",
  columnName:'<fmt:message key="label.enteredby"/>',
  tooltip:"Y"
},
{ columnId:"holdDate",
  columnName:'<fmt:message key="label.holddate"/>'
},
{ columnId:"contact",
  columnName:'<fmt:message key="label.contact"/>'
},
{ columnId:"totalvalue",
  columnName:'<fmt:message key="label.totalvalue"/>',
  sorting:'int',
  align:"right",
  width:6
},
{ columnId:"creditStatus",
  columnName:'<fmt:message key="label.availablecredit"/>',
  sorting:'int',
  align:"right",
  width:8
},
{ columnId:"interms",
  columnName:'<fmt:message key="label.interms"/>',
  width:5
},
  	{
		columnId:"customerNote",
		columnName:'<fmt:message key="label.billtonote"/>',
		tooltip:"Y"
	},
	{
		columnId:"shiptoNote",
		columnName:'<fmt:message key="label.shiptonote"/>',
		tooltip:"Y"
	},
	{
		columnId:"internalNote",
		columnName:'<fmt:message key="label.orderinternalnote"/>',
		tooltip:"Y"
	},
	{
		columnId:"specialInstructions",
		columnName:'<fmt:message key="label.orderexternalnote"/>',
		tooltip:"Y"
	},
	{
		columnId:"holdComments",
		columnName:'<fmt:message key="label.holdcomments"/>',
		tooltip:"Y"
	},
{ columnId:"numberOfLines",
  columnName:''
},
{
  columnId:"companyId",
  columnName:''	
},
{
  columnId:"menuChoice",
  columnName:''
},
{
	columnId:"lineItem",
	columnName:'<fmt:message key="label.qualityholdinfo"/>',
  	attachHeader:'<fmt:message key="label.line"/>',
   align:"right",
   width:4
},
{
	columnId:"rcptQualityHoldSpec",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.spec"/>',
   align:"center",
   width:4
},
{
	columnId:"rcptQualityHoldShelfLife",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.sl"/>',
   align:"center",
   width:4
},
{
	columnId:"rcptQualityHoldReleaseDate",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.date"/>'
},
{
	columnId:"itemId",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.itemid"/>',
  	width:5
},
{
	columnId:"partDescription",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.partdesc"/>',
    width:20,
    tooltip:"Y"
},
{
	columnId:"rcptQualityHoldNote",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.note"/>',
  	tooltip:"Y",
    width:15
},
{
	columnId:"expertReviewDesc",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.expertreviewcomment"/>',
  	tooltip:"Y",
    width:15
},
{
	columnId:"forceHoldComment",
	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.forceholdcomment"/>',
  	tooltip:"Y",
    width:15
},
{
	columnId:"requiredDatetime"
//	columnName:'#cspan',
//  	attachHeader:'<fmt:message key="label.needdate"/>',
//  	width:8
},
{
  	columnId:"promiseDate"
//	columnName:'#cspan',
//  	attachHeader:'<fmt:message key="label.promiseddate"/>',
//  	type:'hcal',
//  	align:'center',
//    width:8
},
{
  columnId:"originalPromiseDate"
},
{
  columnId:"billToCompanyId"
},
{
  columnId:"opsEntityId"
},
{
  columnId:"needDate"
},
{
  columnId:"opsCompanyId"
},
{
  columnId:"orderQuantity"
},
{
  columnId:"partDescription"
},
{
  columnId:"itemId"
},
{
  columnId:"expertReviewHold"
},
{
  columnId:"forceHold"
}

];

var dhxNoteWins = null;
function showNotes(name,divName,readOnlyOrNot) {
	var noteArea = document.getElementById("noteArea"+divName);
	if(readOnlyOrNot != 'readOnly') 
		noteArea.innerHTML = noteArea.innerHTML.replace(/readOnly/gi,''); 
    noteArea.style.display="";
        
       if( dhxNoteWins == null) {
	       	dhxNoteWins = new dhtmlXWindows();
	       	dhxNoteWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
       }
       
       if (!dhxNoteWins.window(name)) {
       	   if (_isIE)
	       	 var popupWin = dhxNoteWins.createWindow(name,0, 0, 410, 245);
	       else
	         var popupWin = dhxNoteWins.createWindow(name,0, 0, 438, 260);
	       popupWin.setText(name);
	       popupWin.clearIcon();  // hide icon
	       popupWin.denyResize(); // deny resizing
	       popupWin.denyPark();   // deny parking
	       popupWin.attachObject("noteArea"+divName);
	       popupWin.attachEvent("onClose", function(popupWin){popupWin.hide();});
	       try{
	       	 $("cancelMRReasonAreaUSE").focus();
	       } catch(ex) {}
	       popupWin.center();
	       currentWindow = popupWin;
       }
       else {
		   dhxNoteWins.window(name).show();
       } 
}

function updateNote(rowid,note) {
	   cell(rowid,"rcptQualityHoldNote").setValue(note);
}

var notewinname = "";

function MrLineNoteOk() {

	   dhxNoteWins.window(notewinname).setModal(false);
	   dhxNoteWins.window(notewinname).hide();
	   
	    loc = "mrreleaseresults.do?action=updateNote&callback=updateNote"+
    	"&prNumber="+encodeURIComponent( cellValue(beangrid.getSelectedRowId(),"prNumber") )+
    	"&lineItem="+encodeURIComponent( cellValue(beangrid.getSelectedRowId(),"lineItem") )+
    	"&rcptQualityHoldNote="+encodeURIComponent( $v('LineNoteAreaUse') )+
    	"&rowid="+encodeURIComponent(selectedRowId);
//    	alert(loc);
		callToServer( loc );
}

function MrLineNoteClear() {
	   $("LineNoteAreaUse").value = '';
}

function showMrLineNotes() {
	var noteArea = document.getElementById("MrNoteArea");
//	name1 = "Notes_" + cellValue(selectedRowId,"prNumber")+"-"+cellValue(selectedRowId,"lineItem");
	notewinname = '<fmt:message key="label.notes"/>_' + cellValue(selectedRowId,"prNumber")+"-"+cellValue(selectedRowId,"lineItem");
	
	readOnlyOrNot = "";
	if(readOnlyOrNot != 'readOnly') 
		noteArea.innerHTML = noteArea.innerHTML.replace(/readOnly/gi,'');
	 
	$('LineNoteAreaUse').value = cellValue(selectedRowId,"rcptQualityHoldNote");
    noteArea.style.display="";
        
       if( dhxNoteWins == null) {
	       	dhxNoteWins = new dhtmlXWindows();
	       	dhxNoteWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
       }
       
//      if (!dhxNoteWins.window(name)) 
      {
       	   if (_isIE)
	       	 var popupWin = dhxNoteWins.createWindow(notewinname,0, 0, 410, 245);
	       else
	         var popupWin = dhxNoteWins.createWindow(notewinname,0, 0, 438, 260);
	       popupWin.setText(notewinname);
	       popupWin.clearIcon();  // hide icon
	       popupWin.denyResize(); // deny resizing
	       popupWin.denyPark();   // deny parking
	       popupWin.attachObject("MrNoteArea");
	       popupWin.attachEvent("onClose", function(popupWin){popupWin.hide();});  
	       popupWin.center();
	       document.getElementById("LineNoteAreaUse").focus();
	       currentWindow = popupWin;
       }
//       else {
//			dhxNoteWins.window(name).show();
//      } 
}



//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/mrreleaseresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${bean}<br/>
    </c:forEach>        
</div>

<div id="noteAreaCancelMRDiv" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
          	<TEXTAREA name="cancelMRReasonAreaUSE" id="cancelMRReasonAreaUSE" readOnly onKeyDown='limitText("cancelMRReasonAreaUSE", "<fmt:message key="label.cancelmrreason"/>", 1000);' onKeyUp='limitText("cancelMRReasonAreaUSE", "<fmt:message key="label.cancelmrreason"/>", 1000);' class="inputBox" COLS=70 ROWS=10></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input name="cancelMRReasonOk"  id="cancelMRReasonOk"  type="button" value="<fmt:message key="label.ok(done)"/>" onClick="cancelMRReasonOkClose();" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input name="cancelMRReasonClear"  id="cancelMRReasonClear"  type="button" value="<fmt:message key="label.clear"/>" onClick="cancelMRReasonClear();" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<div id="MrNoteArea" class="errorMessages" style="display:none;left:20%;top:20%;z-index:6;">
<table id="searchMaskTable" width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
  	  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
          <td class="optionTitleBoldLeft">
          	<TEXTAREA name="LineNoteAreaUse" id="LineNoteAreaUse" readOnly onKeyDown='limitText("LineNoteAreaUse", "Line Note", 1000);' onKeyUp='limitText("LineNoteAreaUse", "Line Note", 1000);' class="inputBox" COLS=70 ROWS=10></TEXTAREA></td>
        </tr> 
        <tr>
          <td align="center">
            <input id="lineNoteOk"  type="button" value="<fmt:message key="label.ok(done)"/>" onClick="MrLineNoteOk();" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
            <input id="lineNoteCancel"  type="button" value="<fmt:message key="label.clear"/>" onClick="MrLineNoteClear();" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" />
          </td>
        </tr> 
      </table>  
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="mrReleaseViewBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="colorClass" value=''/>
<c:if test="${mrReleaseViewBeanColl != null}">
<script type="text/javascript">

<!--
var lineMap = new Array();

<c:set var="parentDataCount" value='${0}'/>
<c:set var="dataCount" value='${0}'/>
<c:set var="creditHoldCount" value='${0}'/>
<c:set var="gridind" value="0"/>
<c:set var="colorIndex" value="0"/>

<c:if test="${!empty mrReleaseViewBeanColl}" >

<c:forEach var="bean" items="${mrReleaseViewBeanColl}" varStatus="status">
	<bean:size collection="${bean.line}" id="resultSize"/>
	lineMap[${gridind}] = ${resultSize};
	map = new Array();
	<c:forEach var="mrReleaseViewChild" items="${bean.line}" varStatus="status2">
		lineMap3[${gridind}] = ${colorIndex%2};
		<c:set var="gridind" value='${gridind+1}'/>
	</c:forEach>
	<c:set var="colorIndex" value='${colorIndex+1}'/>
   <c:set var="parentDataCount" value='${parentDataCount+1}'/>
</c:forEach>

<c:set var="okPermission" value="N"/>
<c:set var="mrFinanceApprover" value="false"/>
<tcmis:opsEntityPermission indicator="true" userGroupId="mrFinanceApprover" opsEntityId="${param.opsEntityId}">
	<c:set var="mrFinanceApprover" value="true"/>
	<c:set var="okPermission" value="Y"/>
</tcmis:opsEntityPermission>


<c:set var="colorIndex" value="0"/>
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${mrReleaseViewBeanColl}" varStatus="status">
	<c:set var="showUpdateLink" value='N'/>
	<c:set var="menuChoice" value="1"/>
	<c:set var="releaseOrder" value='false'/>

	<tcmis:inventoryGroupPermission indicator="true" userGroupId="releaseOrder" inventoryGroup="${bean.inventoryGroup}">
		<c:set var="releaseOrder" value='true'/>
		<c:set var="okPermission" value="Y"/>
	</tcmis:inventoryGroupPermission>

	<c:set var="generateOrders" value='false'/>
	<tcmis:inventoryGroupPermission indicator="true" userGroupId="generateOrders" inventoryGroup="${bean.inventoryGroup}">
		<c:set var="generateOrders" value='true'/>
		<%--<c:set var="okPermission" value="Y"/>--%>
	</tcmis:inventoryGroupPermission>

	<c:set var="releaseExpertReview" value="false"/>
	<tcmis:opsEntityPermission indicator="true" userGroupId="expertReviewer" opsEntityId="${bean.opsEntityId}">
	        <c:set var="releaseExpertReview" value='true'/>
	</tcmis:opsEntityPermission>

	<c:set var="acceptInterCompanyMr" value="N"/>
	<tcmis:opsEntityPermission indicator="true" userGroupId="acceptInterCompanyMr">
		<c:set var="acceptInterCompanyMr" value="Y"/>
	</tcmis:opsEntityPermission>

	<c:set var="numberOfChildColl" value='${bean.line}'/>
	<bean:size id="numberOfChildCount" name="numberOfChildColl"/>

	<c:choose>
		<c:when test="${bean.releaseStatus == 'Pending CC Auth' or bean.releaseStatus == 'Pending Cash' or bean.releaseStatus == 'Hold' or bean.releaseStatus == 'Stop' or bean.releaseStatus == 'Force Hold' or bean.releaseStatus == 'Pending Expert Review' or bean.releaseStatus == 'Expert Review Hold'}">
		    <c:choose>
				<c:when test="${bean.releaseStatus == 'Pending Expert Review' or bean.releaseStatus == 'Expert Review Hold'}">
					<c:choose>
						<c:when test="${releaseExpertReview}">
		         			<c:if test="${bean.displayStatus ne 'Quality Hold'}">
								<c:set var="menuChoice" value="3"/>
				 			</c:if>
		      			</c:when>
						<c:otherwise>
						 	<c:if test="${generateOrders}">
						  		<c:set var="menuChoice" value="2"/>
						 	</c:if>
						</c:otherwise>
				     </c:choose>
		    	</c:when>
				<c:when test="${ mrFinanceApprover }">
		        	<c:if test="${bean.displayStatus eq 'Quality Hold'}">
						<c:set var="menuChoice" value="2"/>
						<c:if test="${numberOfChildCount == 1}">
							<c:set var="menuChoice" value="6"/>
						</c:if>
					</c:if>
				</c:when>
		    	<c:otherwise>
		       		<c:if test="${generateOrders}">
		        		<c:set var="menuChoice" value="2"/>
		       		</c:if>
		      	</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${ bean.releaseStatus == 'Pending Acceptance'}">
		        <c:set var="menuChoice" value="7"/>
		</c:when>
		<c:when test="${ releaseOrder }">    
			<c:if test="${bean.displayStatus eq 'Quality Hold'}">
				<c:set var="menuChoice" value="2"/>
				<c:if test="${numberOfChildCount == 1}">
					<c:set var="menuChoice" value="6"/>
				</c:if>
			</c:if>
			<c:if test="${bean.displayStatus ne 'Quality Hold'}">
				<c:set var="menuChoice" value="3"/>
			</c:if>
		</c:when>
		<c:when test="${ generateOrders }">    
			<c:set var="menuChoice" value="2"/>
		</c:when>
	</c:choose>

	<tcmis:jsReplace var="shipToAddressLine1" value="${bean.shipToAddressLine1}" processMultiLines="true" />
	<tcmis:jsReplace var="shipToAddressLine2" value="${bean.shipToAddressLine2}" processMultiLines="true" />
	<tcmis:jsReplace var="shipToAddressLine3" value="${bean.shipToAddressLine3}" processMultiLines="true" />
	<tcmis:jsReplace var="shipToAddressLine4" value="${bean.shipToAddressLine4}" processMultiLines="true" />
	<tcmis:jsReplace var="shipToAddressLine5" value="${bean.shipToAddressLine5}" processMultiLines="true" />
	<tcmis:jsReplace var="poNumber" value="${bean.poNumber}" processMultiLines="true" />
	<tcmis:jsReplace var="customerName" value="${bean.customerName}" processMultiLines="true" />
	<tcmis:jsReplace var="requestorName" value="${bean.requestorName}"/>

	<c:forEach var="mrReleaseViewChild" items="${bean.line}" varStatus="status2">
		<fmt:formatDate var="rcptQualityHoldSetDate" value="${mrReleaseViewChild.rcptQualHoldSpecSetDate}" pattern="${dateFormatPattern}"/>
		<c:if test="${rcptQualityHoldSetDate == null}">
			<fmt:formatDate var="rcptQualityHoldSetDate" value="${mrReleaseViewChild.rcptQualHoldSlSetDate}" pattern="${dateFormatPattern}"/>	 
		</c:if>
		<tcmis:jsReplace var="rcptQualityHoldNote" value="${mrReleaseViewChild.rcptQualityHoldNote}" processMultiLines="true" />
		<c:set var="rcptQualityHoldSpec" value=""/>
		<c:if test="${mrReleaseViewChild.rcptQualityHoldSpec == 'Y' }">
			<c:set var="rcptQualityHoldSpec" value="X"/>
		</c:if>
		<c:set var="rcptQualityHoldShelfLife" value=""/>
		<c:if test="${mrReleaseViewChild.rcptQualityHoldShelfLife == 'Y' }">
			<c:set var="rcptQualityHoldShelfLife" value="X"/>
		</c:if>
		
		<c:set var="editPromisedDate" value="N"/>
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${bean.inventoryGroup}">
			<c:set var="editPromisedDate" value="Y"/> 	 
		</tcmis:inventoryGroupPermission>

		<c:if test="${empty mrReleaseViewChild.lineItem}">
			<c:set var="editPromisedDate" value="N"/>
		</c:if>

		<%--<fmt:formatDate var="fmtNeedDate" value="${bean.needDate}" pattern="${dateFormatPattern}"/>--%>
		<fmt:formatDate var="dateFirstConfirmed" value="${bean.dateFirstConfirmed}" pattern="${dateFormatPattern}"/>
		<fmt:formatDate var="requiredDatetime" value="${mrReleaseViewChild.requiredDatetime}" pattern="${dateFormatPattern}"/>
		<fmt:formatDate var="promiseDate" value="${mrReleaseViewChild.promiseDate}" pattern="${dateFormatPattern}"/>

		<c:set var="okPermissionNew" value="N"/>
		<c:choose>
			<c:when test="${ ( bean.displayStatus == 'Credit Hold') || (bean.displayStatus == 'Credit Hold, Quality Hold') || (bean.displayStatus == 'Credit Stop') }">
			    <c:if test="${okPermission eq 'Y' and mrFinanceApprover}">
			        <c:set var="creditHoldCount" value='${creditHoldCount+1}'/>
			    	<c:set var="okPermissionNew" value="Y"/>
				    <c:set var="showReleaseLink" value="Y"/>
			    </c:if>
		   	</c:when>
			<c:when test="${fn:contains(bean.displayStatus,'Pending CC Auth') or fn:contains(bean.displayStatus,'Pending Cash') or fn:contains(bean.displayStatus,'Pending Expert Review') }">
				<c:if test="${okPermission eq 'Y' }">
					<c:set var="creditHoldCount" value='${creditHoldCount+1}'/>
					<c:set var="okPermissionNew" value="Y"/>
					<c:set var="showReleaseLink" value="Y"/>
				</c:if>
			</c:when>
		</c:choose>
				
		<c:if test="${dataCount > 0}">,</c:if>
		/*The row ID needs to start with 1 per their design.*/
		{ id:${dataCount +1},
		 data:[
		 '${editPromisedDate}',
		 '${bean.inventoryGroupName}','${bean.displayStatus}',
		 '${okPermissionNew}',
		 false,	 
		 '${customerName}',
		 '${bean.materialRequestOrigin}',		 	
		 '${shipToAddressLine1} ${shipToAddressLine2} ${shipToAddressLine3} ${shipToAddressLine4} ${shipToAddressLine5}',
		 '${poNumber}',
		 '${bean.prNumber}',
		 '<tcmis:jsReplace value="${bean.submittedByName}" />',
		 '${dateFirstConfirmed}',
		 '${requestorName}',
		 '<fmt:formatNumber value="${bean.totalExtendedPrice}" pattern="${totalcurrencyformat}"></fmt:formatNumber>',
		 '${bean.availableCredit}',
		 '${bean.withinTerms}',
		 '<tcmis:jsReplace value="${bean.customerNote}" processMultiLines="true" />',
	  	 '<tcmis:jsReplace value="${bean.shiptoNote}" processMultiLines="true" />',
	  	 '<tcmis:jsReplace value="${bean.internalNote}" processMultiLines="true" />',
	  	 '<tcmis:jsReplace value="${bean.specialInstructions}" processMultiLines="true" />',
	  	 '<tcmis:jsReplace value="${bean.holdComments}" processMultiLines="true" />',
		 '',//{bean.numberOfLines}',
		 '${bean.companyId}',
		 '${menuChoice}',
		 '${mrReleaseViewChild.lineItem}',
		 '${rcptQualityHoldSpec}',
		 '${rcptQualityHoldShelfLife}',
		 '${rcptQualityHoldSetDate}',
		 '${mrReleaseViewChild.itemId}',
		 '<tcmis:jsReplace value="${mrReleaseViewChild.partDescription}" processMultiLines="true" />',
		 '${rcptQualityHoldNote}',
		 '<tcmis:jsReplace value="${mrReleaseViewChild.expertReviewDesc}" processMultiLines="true" />',
		 '<tcmis:jsReplace value="${mrReleaseViewChild.forceHoldComment}" processMultiLines="true" />',
		 '${requiredDatetime}',
		 '${promiseDate}',
		 '${promiseDate}',
		 '${bean.billToCompanyId}',
		 '${bean.opsEntityId}',
		 '${bean.billToCompanyId}',
		 '',
		 '${bean.opsCompanyId}', 
		 '${mrReleaseViewChild.orderQuantity}',
		 '<tcmis:jsReplace value="${mrReleaseViewChild.partDescription}" processMultiLines="true" />',
		 '${mrReleaseViewChild.itemId}',
		 '${mrReleaseViewChild.expertReviewHold}',
		 '${mrReleaseViewChild.forceHold}'	  
		  ]}
		 <c:set var="dataCount" value='${dataCount+1}'/>
	 </c:forEach>
</c:forEach>
]};

<c:if test="${showReleaseLink == 'Y'}">
  	 showUpdateLinks = true;
</c:if>

</c:if>
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty mrReleaseViewBeanColl}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
<!-- Search results end -->
</c:if>


<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="<c:out value="${parentDataCount}"/>" type="hidden"/>
	<input name="creditHoldCount" id="creditHoldCount" value="${creditHoldCount}" type="hidden"/>
	<input name="action" id="action" value="" type="hidden"/>   
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
	<input name="lineItem" id="lineItem" value="" type="hidden"/>  
	<input name="companyId" id="companyId" value="" type="hidden"/>  
	<input name="prNumber" id="prNumber" value="" type="hidden"/> 
	<input name="promiseDate" id="promiseDate" value="" type="hidden"/> 
	<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
	<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
	
	 <c:forEach var="oStatus" items="${paramValues.orderStatus}">			
			<input name="orderStatus" id="orderStatus"   type="hidden" value="${oStatus}"/>
	 </c:forEach>
	
	<input name="hub" id="hub" type="hidden" value="${param.hub}"/>
	<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
	<input name="confirmFromDate" id="confirmFromDate" type="hidden" value="${param.confirmFromDate}"/>
	<input name="confirmToDate" id="confirmToDate" type="hidden" value="${param.confirmToDate}"/>
	<input name="personnelId" id="personnelId" type="hidden" value="${param.personnelId}"/>
	<input name="searchField" id="searchField" type="hidden" value="${param.searchField}"/>
	<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}"/>
	<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}"/>
	<input type="hidden" name="cancelMRReason" id="cancelMRReason" value=""/>  
	
	<input type="hidden" name="blockBefore_promiseDate" id="blockBefore_promiseDate" value=""/>
	<input type="hidden" name="blockAfter_promiseDate" id="blockAfter_promiseDate" value=""/>
	<input type="hidden" name="blockBeforeExclude_promiseDate" id="blockBeforeExclude_promiseDate" value=""/>
	<input type="hidden" name="blockAfterExclude_promiseDate" id="blockAfterExclude_promiseDate" value=""/>
	<input type="hidden" name="inDefinite_promiseDate" id="inDefinite_promiseDate" value=""/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>