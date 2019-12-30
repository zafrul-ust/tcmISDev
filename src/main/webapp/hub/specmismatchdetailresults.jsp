<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>Spec Mismatch Detail</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
pleaseselectarowforupdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
    	aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showRadianPo();");
    	aI("text=<fmt:message key="label.viewaddmrdocuments"/>;url=javascript:showMRDocuments();");
}

drawMenus();

var config = [
{ columnId:"issueSeverity",columnName:'Issue Severity', width:3},
{ columnId:"issueComment", columnName:'Issue Comment',tooltip:true,width:10},
{ columnId:"mismatchComment",columnName:'Mismatch Comment'},
{ columnId:"issueSeverity",columnName:'Issue Severity', width:3},
{ columnId:"issueComment", columnName:'Issue Comment',tooltip:true,width:10},
{ columnId:"dropShip", columnName:'DropShip', width:3},
{ columnId:"receiptPo", columnName:'Receipt PO', width:8},
{ columnId:"receiptPoLine", columnName:'Receipt PO Line', width:4},
{ columnId:"promiseDate", columnName:'Promise Date', hiddenSortingColumn:'hiddenPromiseDateTime', sorting:'int', width:8},
{ columnId:"hiddenPromiseDateTime", sorting:'int'},
{ columnId:"customerName", columnName:'Customer Name', width:15},
{ columnId:"inventoryGroup", columnName:'Inventory Group'},
{ columnId:"mrLine", columnName:'MR Line', width:8},
{ columnId:"facPartNo", columnName:'Fac Part No', width:8},
{ columnId:"partDescription", columnName:'Part Description', width:30},
{ columnId:"receiptId", columnName:'Receipt Id', width:8},
{ columnId:"quantity", columnName:'Quantity', width:4},
{ columnId:"boughtForThis", columnName:'Bought For This', width:3},
{ columnId:"partSpecRequirement", columnName:'Part Spec Requirement', width:10},
{ columnId:"specOnReceipt", columnName:'Spec On Receipt', width:10},
{ columnId:"shipToAddress", columnName:'Ship To Address', width:30},
{ columnId:"mismatch"}
];

var windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid = null;


function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	
	if (totalLines > 0)
	{		
		document.getElementById("specMismatchDetailBean").style["display"] = "";
		initializeGrid();
	}  
	else
	{
		document.getElementById("specMismatchDetailBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
 
}

function initializeGrid(){
   totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		beangrid = new dhtmlXGridObject('specMismatchDetailBean');
		initGridWithConfig(beangrid,config,false,true,true);
		if( typeof( jsonMainData ) != 'undefined' ) {
				beangrid.parse(jsonMainData,"json");
				
	     	
	 	beangrid.attachEvent("onRowSelect",selectRow);
	 	beangrid.attachEvent("onRightClick",selectRightclick);
	    
        }
	}
}

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd) {
	selectRow(rowId,cellInd);
	toggleContextMenu('rightClickMenu');
} 

function showProjectDocuments() {
		var receiptId = cellValue(selectedRowId,"receiptId");
		var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
		var loc = "/tcmIS/hub/receiptdocuments.do?receiptId=" + receiptId
				+ "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
		try {
			children[children.length] = openWinGeneric(loc,
					"showAllProjectDocuments", "450", "300", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no",
					"80", "80");
		}
	}
	
function showRadianPo() {
	  var radianPo = cellValue(selectedRowId,"receiptPo");
	  
	  loc = "/tcmIS/supply/purchaseorder.do?po=" + radianPo + "&Action=searchlike&subUserAction=po";
	  try {
	 	children[children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
	 } catch (ex){
	 	openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
	 }
	}
	
function showMRDocuments()
{ 
	var mrLine = cellValue(selectedRowId,"mrLine");
	var n=mrLine.indexOf("-"); 
	var prNumber = mrLine.substring(0,n);
	var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
	var loc = "/tcmIS/distribution/showmrdocuments.do?showDocuments=Yes&orderType=MR"+
	           "&prNumber="+prNumber+"&inventoryGroup="+inventoryGroup;
	 try {
	 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllMrDocuments","600","300","yes","80","80");
	 } catch (ex){
	 	openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
	 }
}

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/specmismatchdetailresults.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
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

<div id="specMismatchDetailBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${specMismatchDetailColl != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty specMismatchDetailColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${specMismatchDetailColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="readonly" value='N'/>

/* Get time for hidden date column. This is for sorting purpose. */
<c:set var="promiseDateTime" value="${bean.promiseDate.time}"/>

{id:${status.index +1},
 data:[
 '${bean.issueSeverity}',
  '<tcmis:jsReplace value="${bean.issueComment}" />',
  '${bean.mismatchComment}',
  '${bean.issueSeverity}',
  '<tcmis:jsReplace value="${bean.issueComment}" />',
  '${bean.dropship}',
  '${bean.receiptPo}',
  '${bean.receiptPoLine}',
  '<fmt:formatDate value="${bean.promiseDate}" pattern="${dateFormatPattern}"/>', 
  '${promiseDateTime}',
  '<tcmis:jsReplace value="${bean.customerName}" />',
  '${bean.inventoryGroup}',
  '${bean.mrLine}',
  '${bean.facPartNo}',
  '<tcmis:jsReplace value="${bean.partDescription}" processMultiLines="true"/>',
  '${bean.receiptId}',
  '${bean.quantity}',
  '${bean.boughtForThis}',
  '<tcmis:jsReplace value="${bean.partSpecRequirement}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.specOnReceipt}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.shipToAddress}" processMultiLines="true"/>',
  '${bean.mismatch}'
  ]}
  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->
<c:if test="${empty specMismatchDetailColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>



<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>