<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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


<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<%-- 
<script type="text/javascript" src="/js/hub/shipconfirm.js"></script>
--%>

<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noRowChecked:"<fmt:message key="error.norowselected"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",    
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
dateexpected:"<fmt:message key="label.dateexpected"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
mustshipemtid:"<fmt:message key="label.mustshipemtid"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
invalidDateFormat:"<fmt:message key="error.date.invalid"/>"};

with ( milonic=new menuname("showCustomer") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 <%--aI( "text=<fmt:message key="label.generateinvoice"/> ;url=javascript:generateInvoice();" );--%>
 aI( "text=<fmt:message key="label.headercharges"/> ;url=javascript:addHeaderCharges();" );
 aI( "text=<fmt:message key="label.linecharges"/> ;url=javascript:addLineCharges();" );
 aI("text=<fmt:message key="label.openmr"/> ;url=javascript:openMr();");
}

with ( milonic=new menuname("showLineCharge") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 <%--aI( "text=<fmt:message key="label.generateinvoice"/> ;url=javascript:generateInvoice();" );--%>
 aI( "text=<fmt:message key="label.linecharges"/> ;url=javascript:addLineCharges();" );
}

drawMenus();

var shipmentIdArray = [
    <c:forEach var="shipmentIdBean" items="${shipmentIdColl}" varStatus="status">
        <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${shipmentIdBean}',
          name: '${shipmentIdBean}'
        }
    </c:forEach>
];  
   
var prNumberArray = [
    <c:forEach var="prNumberBean" items="${prNumberColl}" varStatus="status">
    	<c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${prNumberBean}',
          name: '${prNumberBean}'
        }
    </c:forEach>
];  


var resizeGridWithWindow = true;
var selectedRowId = null;
var saveRowClass = null;

function myResultOnLoad()
{

 if ($v("totalLines") > 0) {
 	initializeGrid();
 	try {
 		buildIdFieldDropDown();
 	} catch(ex) {}
 }
 
 try{
	 if (!showCheckAllBox) /*Dont show any update links if the user does not have permissions*/
	 {
	   document.getElementById("chkAllSelected").style["display"] = "none";
	 }
 }
 catch(ex)
 {}

 /*This dislpays our standard footer message*/
 displayGridSearchDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();

}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('shipConfirmInputBean');

	initGridWithConfig(beangrid,config,false,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid._haas_bg_render_enabled = true;
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRowSelect",selectRow);
	beangrid.attachEvent("onRightClick",selectRow);
}

function pp(name) {
	var value = $v(name);
//	alert( value );
	return encodeURIComponent(value);
}

function gg(name) {
	var value = null;
	value = cellValue(selectedRowId,name);
	return encodeURIComponent(value);
}

function generateInvoice() {
	var loc = "shipconfirmmain.do?uAction=generateInvoice"+
	"&companyId="+gg('companyId')+
	"&shipmentId="+gg('shipmentId')+
	"&prNumber="+gg('prNumber');
		
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	openWinGeneric(loc,"generateInvoice","1024","600","yes");
}

<c:set var="shipconfirm"><fmt:message key="label.shipconfirm"/></c:set>
function getcurpath (){
	return encodeURIComponent('<tcmis:jsReplace value="${shipconfirm}"/>');
}

function addLineCharges() { 
	  parent.showTransitWin('<fmt:message key="label.linecharges"/>');
      var opsEntityId = document.getElementById("opsEntityId").value;
      loc = "addchargeline.do?orderType=MR"+"&status=&prNumber="+cellValue(selectedRowId,"prNumber")+
	  						"&companyId="+cellValue(selectedRowId,"companyId")+
	  						"&lineItem="+cellValue(selectedRowId,"lineItem")+
	  					    "&curpath="+getcurpath()+
                            "&opsEntityId="+opsEntityId+
                            "&currencyId="+gg("currencyId");
	  var winId= 'addHeaderCharge'+$v("prNumber");
	  
	  if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	  
	  parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}

function addHeaderCharges() { 
	  parent.showTransitWin('<fmt:message key="label.headercharges"/>');
      var opsEntityId = document.getElementById("opsEntityId").value;
      loc = "addchargeheader.do?orderType=MR"+"&status=&prNumber="+cellValue(selectedRowId,"prNumber")+
	  						"&companyId="+cellValue(selectedRowId,"companyId")+
	  					    "&curpath="+getcurpath()+
                            "&opsEntityId="+opsEntityId+
                            "&currencyId="+gg("currencyId");
	  var winId= 'addHeaderCharge'+$v("prNumber");
	  
	  if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	  
	  parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}

function openMr() {
	var prNumber = cellValue(selectedRowId,"prNumber");
	var loc = "scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent('scratchPad'+prNumber+'');
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	
	try	
		{
			parent.parent.openIFrame("scratchPad"+prNumber+"",loc,"MR "+prNumber+"","","N");
		}
		catch (ex)
		{
			openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");	
		}
}

function setIdFieldOption(href, text, id, target, cssClassName) {
  var optionName = new Option(text, id, false, false)
  var optionO = parent.document.getElementById(target);
  optionO[href] = optionName;
  if( cssClassName ) optionO[href].className =cssClassName;
}

function buildIdFieldDropDown() {
	var obj = parent.$("idField");
    for (var i = obj.length; i >= 0;i--)
     	obj[i] = null;
     	
	if(parent.$v("documentType") == "shipmentId")
		arr = shipmentIdArray;
	else
		arr = prNumberArray;
	
	for ( var i=0; i < arr.length; i++) {
	    setIdFieldOption(i,arr[i].name,arr[i].id,"idField");
	}
  	obj.selectedIndex = 0;
	  
}

function consolidateBol() {
   	var totallines = $("totalLines").value;
   	if(totallines == 0) {
     		//alert("Please select a shipment id that you want to print.");
   	}
	else {
		var checked = countChecked();
		if (checked==0) {
			alert(messagesData.pleasemakeselection);
		}
   		else {
      			var tmp = "";
      			for ( var p = 1; p <= totallines; p++ ) {
      				if (haasGrid.haasRowIsRendered(p)) {
	         			var checkbox = document.getElementById("selected" + p);
	         			if (checkbox.checked == true) {
	          				var shipmentId = document.getElementById("shipmentId" + (p));
						if(tmp == "") {
							tmp = "?shipmentIds=" + "" + shipmentId.value;
						}
						else {
	           					tmp = tmp + "," + shipmentId.value;
						}
					}
      				}
			}
     			var loc = "printconsolidatedbol.do" + tmp;
     			
     			if ($v("personnelCompanyId") == 'Radian') 
     				  loc = "/tcmIS/hub/" + loc;
     			
     			openWinGeneric(loc,"printconsolidatebol","800","500","yes","80","80")
   		}
	}
}

function printBolShort() {
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   }
   else {
   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBolShortddd','800','600','yes',"80","80");
   document.genericForm.target='printBolShortddd';
   var action = document.getElementById("uAction");
   action.value = 'printBolShort';
   if(haasGrid) haasGrid.parentFormOnSubmit(); //prepare grid for data sending
    var a = window.setTimeout("document.genericForm.submit();",1000);
   }
}

function printPackingList() {
   var loc = 'showprintpackinglist.do?uAction=showprintPL&branchPlant='+$("branchPlant").value;
	   
   if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
   
   openWinGeneric(loc,'_PrintPL','650','500','yes');
    
}

function printConsolPL(){

   var documentType = parent.$v("documentType");
    var idField = parent.$v("idField");
    
    if (documentType != 'shipmentId')
  {  
   	  errorMessage = messagesData.mustshipemtid+"\n";
   	  alert(errorMessage);
   	  return false;
  }

     var reportLoc = "distconsolprintpl.do"+
		 				 "?shipmentId="+idField;
     
     if ($v("personnelCompanyId") == 'Radian') 
		  reportLoc = "/tcmIS/hub/" + reportLoc;
     
	openWinGeneric(reportLoc,"consolPL","800","550","yes","100","100");


}

function printBolLong() {
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   }
   else {
   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBolLongddd','800','600','yes',"80","80");
	 document.genericForm.target='printBolLongddd';
  	var action = document.getElementById("uAction");
 		action.value = 'printBolLong';
    if(haasGrid) haasGrid.parentFormOnSubmit(); //prepare grid for data sending 
    var a = window.setTimeout("document.genericForm.submit();",1000);
    }
}

function printBoxLabels() {
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   }
   else {
    openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBoxLabelsddd','800','600','yes',"80","80");
	document.genericForm.target='printBoxLabelsddd';
  	var action = document.getElementById("uAction");
 		action.value = 'printBoxLabels';
    if(haasGrid) haasGrid.parentFormOnSubmit(); //prepare grid for data sending
    var a = window.setTimeout("document.genericForm.submit();",1000);
    }
}

function countChecked() {
	var totallines = $("totalLines").value;
	var totalChecked = 0;
	for ( var p = 1; p <= totallines; p++ ) {
		if (haasGrid.haasRowIsRendered(p)) {
			var checkbox = document.getElementById("selected" + p);
			if (checkbox.checked == true) {
				totalChecked++;
			}
		}
	}
	return totalChecked;
}

function doOnBeforeSelect(rowId,oldRowId) {	
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;	
}

function selectRow()
{  
	
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   selectedRowId = rowId0;
// change to here for txt object.    
   if( colId0 == haasGrid.getColIndexById("remarks") && cellValue(rowId0,"remarksPermission") != 'Y' ){//&& cellValue("remarks")) {
	   haasGrid.lockRow(rowId0,"true");//return true;//alert('hh');
   }

   if( !rightClick ) return true;

	partno = cellValue(rowId0,"catPartNo");
	distribution = cellValue(rowId0,"distribution");
	var inventoryGroup = cellValue(rowId0,"inventoryGroup");

	if( distribution == 'Y')
		toggleContextMenu('showCustomer');
	else if('Dallas LM Co-Producer' == inventoryGroup || 'FTW LM Co-Producer' == inventoryGroup)
		toggleContextMenu('showLineCharge');
	else
		toggleContextMenu('contextMenu');
}

function showLegend(){
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}

function checkBox(rowNumber) {

	  var shipmentId = document.getElementById("shipmentId" + rowNumber);
	  var originalShipmentId = cellValue(rowNumber,"orishipmentId");
	  var check;
	  if(shipmentId.value == originalShipmentId) {
	    check = false;
	  }
	  else {
	    check = true;
	  }
	  var checkbox = document.getElementById("selected" + rowNumber);
	  checkbox.checked = check;
      updateHchStatusA("selected" + rowNumber);
}

function checkAll(checkboxname)
{
  var checkall = $("chkAllSelected");
  var rowsNum = haasGrid.getRowsNum();

  rowsNum = rowsNum*1;

  renderAllRows()
  
  if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
  }
  else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
  }
  return true;  
}

function  refreshme() {
	try {
	parent.submitSearchForm();
	}catch(ex){}
}

function printBagLabels() {
	var totallines = $("totalLines").value;
	var checked = countChecked();
	if (checked==0) {
		alert(messagesData.pleasemakeselection);
	}
	else {
		var prNumbers = "";
		var lineItems = "";
		var picklistIds = "";
		
  		for ( var rowId = 1; rowId <= totallines; rowId++ ) {
  			if (haasGrid.haasRowIsRendered(rowId)) {
      			var checkbox = document.getElementById("selected" + rowId);
      			if (checkbox.checked == true) {
       				var prNumber = cellValue(rowId, "prNumber");
       				var lineItem = cellValue(rowId, "lineItem");
       				var picklistId = cellValue(rowId, "picklistId");
       					
					if(prNumbers == "") {
						prNumbers = "?prNumbers=" + "" + prNumber;
						lineItems = "&lineItems=" + "" + lineItem;
						picklistIds = "&picklistIds=" + "" + picklistId;
					}
					else {
						prNumbers = prNumbers + "," + prNumber;
						lineItems = lineItems + "," + lineItem;
						picklistIds = picklistIds + "," + picklistId;
					}
				}
    		}
		}
   		var loc = "printbaglabels.do" + prNumbers + lineItems + picklistIds;
   		
   		if ($v("personnelCompanyId") == 'Radian') 
  		  loc = "/tcmIS/hub/" + loc;
   		
   		openWinGeneric(loc,"printbaglabels","800","500","yes","80","80")
 	}

}

function printProForma() {
	countForLink = $v("distributionCount");
	printInvoiceYCount = $v("printInvoiceYCount");
    if (printInvoiceYCount == 0)
    {
        countForLink = 0;
    }
    consignmentTransferCount = $v("consignmentTransferCount");
    
    if ($v("pageid") == 'shipConfirm') {
  	  openWinGeneric('/tcmIS/hub/confirmshipment.do?pageid='+$v("pageid")+'&uAction=showPrintProForma&branchPlant='+$("branchPlant").value+'&inventoryGroup='+$("inventoryGroup").value+'&distributionCount='+countForLink+'&consignmentTransferCount='+consignmentTransferCount,'_ConfirmShipment','900','600','yes');	  
    } else {
  	  openWinGeneric('confirmshipment.do?pageid='+$v("pageid")+'&uAction=showPrintProForma&branchPlant='+$("branchPlant").value+'&inventoryGroup='+$("inventoryGroup").value+'&distributionCount='+countForLink+'&consignmentTransferCount='+consignmentTransferCount+'&showProForma='+$("showProForma").value,'_ConfirmShipment','900','600','yes');	  
    }  
}

function printShippingLabels() {
	var totallines = $("totalLines").value;
	var checked = countChecked();
	if (checked==0) {
		alert(messagesData.pleasemakeselection);
	}
	else {
		var prNumbers = "";
		var lineItems = "";
		
  		for ( var rowId = 1; rowId <= totallines; rowId++ ) {
  			if (haasGrid.haasRowIsRendered(rowId)) {
      			var checkbox = document.getElementById("selected" + rowId);
      			if (checkbox.checked == true) {
       				var prNumber = cellValue(rowId, "prNumber");
       				var lineItem = cellValue(rowId, "lineItem");
       					
					if(prNumbers == "") {
						prNumbers = "?prNumbers=" + "" + prNumber;
						lineItems = "&lineItems=" + "" + lineItem;
					}
					else {
						prNumbers = prNumbers + "," + prNumber;
						lineItems = lineItems + "," + lineItem;
					}
				}
    		}
		}
		var loc = "printshippinglabels.do" + prNumbers + lineItems;
   		
   		if ($v("personnelCompanyId") == 'Radian') 
  		  loc = "/tcmIS/hub/" + loc;
   		
   		openWinGeneric(loc,"printshippinglabels","800","500","yes","80","80")
 	}

}
// -->
</script>
</head>
<%--TODO - Singl click open remarks.--%>
<body bgcolor="#ffffff" onload="myResultOnLoad();">
<tcmis:form action="/shipconfirmresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:set var="hasPermission" value='false'/>
<c:set var="isAutoShipConfirm" value='false'/>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:set var="distributionYCount" value='${0}'/>
<c:set var="printInvoiceYCount" value='${0}'/>
<c:set var="consignmentTransferCount" value='${0}'/>
<c:forEach var="bean" items="${beanColl}" varStatus="status">
	<c:if test="${ bean.distribution == 'Y' }">
		<c:set var="distributionYCount" value='${distributionYCount+1}'/>
	</c:if>
    <c:if test="${ bean.printInvoice == 'Y' }">
		<c:set var="printInvoiceYCount" value='${printInvoiceYCount+1}'/>
	</c:if>
	<c:if test="${ bean.consignmentTransfer == 'Y' }">
		<c:set var="consignmentTransferCount" value='${consignmentTransferCount+1}'/>
	</c:if>

</c:forEach>

<c:if test="${!empty beanColl}" >
<div id="shipConfirmInputBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
showCheckAllBox = false;
var shipmentId = new Array();
/*Storing the data to be displayed in a JSON object array.*/

<c:forEach var="bean" items="${beanColl}" varStatus="status">
  <c:if test="${status.current.autoShipConfirm == 'Y'}">
    <c:set var="isAutoShipConfirm" value='true'/>
    shipmentId[${status.index+1}] = new Array(
    		  {value:"${status.current.shipmentId}",text:'${status.current.shipmentId}'}
	);
  </c:if>
  <c:if test="${status.current.autoShipConfirm == 'N'}">
  		shipmentId[${status.index+1}] = new Array(
  	  		  {value:"",text:''}
  		<c:forEach var="gBean" items="${bean.shipmentBeanCollection}" varStatus="status1">
  		  ,{value:"${status1.current.shipmentId}",text:'${status1.current.shipmentId}'}
  		</c:forEach>
  		);
  </c:if>
</c:forEach>

<c:set var="p" value="N"/>
<c:set var="showChkAllBox" value='N'/>
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtDatePicked" value="${status.current.datePicked}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="deliveredDate" value="${status.current.deliveredDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="issueQcDate" value="${status.current.issueQcDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="lastProFormaPrintDate" value="${status.current.lastProFormaPrintDate}" pattern="${dateFormatPattern}"/>

  <tcmis:jsReplace var="shipToAddressLine1" value='${status.current.shipToAddressLine1}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine2" value='${status.current.shipToAddressLine2}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine3" value='${status.current.shipToAddressLine3}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine4" value='${status.current.shipToAddressLine4}' processMultiLines="true"/>
  <tcmis:jsReplace var="shipToAddressLine5" value='${status.current.shipToAddressLine5}' processMultiLines="true"/>

<c:set var="p" value="N"/>
<c:if test="${param.isWmsHub != 'Y'}">
    <tcmis:inventoryGroupPermission indicator="true" userGroupId="shipConfirm" inventoryGroup="${status.current.inventoryGroup}">
        <c:set var="hasPermission" value='true'/>
        <c:set var="p" value="Y"/>
    </tcmis:inventoryGroupPermission>
</c:if>

<c:if test="${status.current.pickQcComplete == 'N'}">
<c:set var="p" value="N"/>
</c:if>

<c:set var="showdropdown" value="N"/>
<c:if test="${status.current.autoShipConfirm == 'Y'}">
<c:set var="isAutoShipConfirm" value='true'/>
</c:if>
<c:if test="${status.current.autoShipConfirm != 'Y'}">
	<c:if test="${ p == 'Y' }">
		<c:set var="showdropdown" value="Y"/>
	</c:if>
</c:if>

<c:set var="shippingReferencePermission" value='N'/>
<c:if test="${ p == 'Y' }">
	<c:set var="showChkAllBox" value='Y'/>
	<c:if test="${ status.current.distribution == 'Y' }">
		<c:set var="shippingReferencePermission" value='Y'/>
	</c:if>
</c:if>

{ id:${status.index +1},
 <c:if test="${status.current.overShipped  == 'Y'}">'class':"grid_orange",</c:if>
 data:[
  '${p}',
  false,
  <c:if test='${distributionYCount != 0 || isAutoShipConfirm == "false"}' >
	  '${showdropdown}',
	  '${bean.shipmentId}',
	  '${shippingReferencePermission}',
	  '<tcmis:jsReplace value="${bean.shippingReference}"/>',
  </c:if>
  '<tcmis:jsReplace value="${bean.inventoryGroup}"/>',  
  <c:if test="${ distributionYCount != 0 }">
  '<tcmis:jsReplace value="${bean.customerName}"/>',
  </c:if>
  <c:if test="${ distributionYCount == 0 }">
  '<tcmis:jsReplace value="${bean.companyName}"/>',
  </c:if>

  <c:if test="${ distributionYCount != 0 }">
  	    '${bean.materialRequestOrigin}',
		'${shipToAddressLine1} ${shipToAddressLine2} ${shipToAddressLine3} ${shipToAddressLine4} ${shipToAddressLine5}','${bean.paymentTerms}',
  </c:if>
  <c:if test="${ distributionYCount == 0 }">
  	    '${bean.materialRequestOrigin}',
		'<tcmis:jsReplace value="${bean.facilityName}"/>',
  		'<tcmis:jsReplace value="${bean.applicationDesc}"/>',
  		'${bean.shipToLocationId}',
  </c:if>
  '${bean.hazardous}',
  '${bean.hazardCategory}',
  '${status.current.prNumber}-${status.current.lineItem}',
  '<tcmis:jsReplace value="${bean.csrName}"/>',
  '${bean.batch}',         
  '<tcmis:jsReplace value="${bean.catPartNo}"/>',
  '${bean.quantity}',
  '${fmtDatePicked}',
  '<tcmis:jsReplace value="${bean.issuerName}"/>',
  '${bean.lineItem}',
  '${bean.prNumber}',
  '${bean.companyId}',
  '${bean.facilityId}',
  '${bean.carrierCode}',
  '${bean.trackingNumber}',
  '${deliveredDate}',
  '${bean.customerId}',
  '${bean.customerServiceRepId}',
  '${issueQcDate}',
  '<tcmis:jsReplace value="${bean.issueQcUserName}"/>',
  '<tcmis:jsReplace value="${bean.customerNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${bean.shiptoNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${bean.prInternalNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="" processMultiLines="true" />',
  '<tcmis:jsReplace value="${bean.lineInternalNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="" processMultiLines="true" />',
  '${bean.shipmentId}',
  '${bean.overShipped}',
  '${bean.currencyId}',
  '${bean.companyId}',
  '${bean.materialRequestOrigin}',
  '${bean.distribution}',
  '${bean.pickingUnitId}',
  '${bean.boxCount}'
  	<c:if test="${showProForma == 'Y'}">
	,
	'${lastProFormaPrintDate}' 
	</c:if>
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="shipmentid"><fmt:message key="label.shipmentid"/></c:set>  
var config = [
{ columnId:"permission"
},
{
	columnId:"selected",	
  	columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'selected\');" name="chkAllSelected" id="chkAllSelected">',
  	type:'hchstatus',
  	width:2
},
<c:if test='${distributionYCount != 0 || isAutoShipConfirm == "false"}' >
{
	columnId:"shipmentIdPermission"	
},
  {
  	columnId:"shipmentId",
  	columnName:'<tcmis:jsReplace value="${shipmentid}"/>',
  	type:'hcoro',
  	permission:true,
  	onChange:checkBox ,
	width:6 	
  },
  {
		columnId:"shippingReferencePermission"	
  },
  {
	columnId:"shippingReference",
	columnName:'<fmt:message key="label.customerreference"/>',
	type:'hed',
	permission:true
  },
</c:if>
  {
	columnId:"inventoryGroup",
	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
	tooltip:"Y",
	width:8
  },
  <c:if test="${ distributionYCount != 0 }">
  {
  	columnId:"companyName",
	columnName:'<fmt:message key="label.customer"/>',
	tooltip:"Y",
	width:10
  },
  </c:if>
  <c:if test="${ distributionYCount == 0 }">
  {
  	columnId:"companyName",
	columnName:'<fmt:message key="label.company"/>',
	tooltip:"Y",
	width:10
  },
  </c:if>
  <c:if test="${ distributionYCount != 0 }">
   { 
	  columnId:"materialRequestOrigin",
	  columnName:'<fmt:message key="label.materialrequestorigin"/>',
	  tooltip:"Y",
	  width:9
  },
  {
  	columnId:"shipTo",
  	columnName:'<fmt:message key="label.shipto"/>',
  	tooltip:"Y",
	width:15
  },
  {
  	columnId:"paymentTerms",
  	columnName:'<fmt:message key="label.paymentterms"/>',
  	tooltip:"Y",
	width:8
  },
  </c:if>
  <c:if test="${ distributionYCount == 0 }">
  {
	  columnId:"materialRequestOrigin"
  },
  {
  	columnId:"facilityName",
  	columnName:'<fmt:message key="label.facility"/>',
  	tooltip:"Y",
	width:6
  },
  {
	columnId:"applicationDesc",
	columnName:'<fmt:message key="label.workarea"/>',
    tooltip:"Y",
	width:10
  },
  {
	columnId:"shipToLocationId",
	columnName:'<fmt:message key="label.dock"/>',
	tooltip:"Y",
	width:6
  },
  </c:if>
  {
  	columnId:"hazardous",
  	columnName:'<fmt:message key="label.haz"/>',
  	align:'center',
    width:3
  },
  {
	columnId:"hazardCategory",
	columnName:'<fmt:message key="label.hazardcategory"/>',
	width:6
  },
  {
	columnId:"mrline",
	columnName:'<fmt:message key="label.mrline"/>',
	width:6
  },
	{
		columnId:"csrName",
		columnName:'<fmt:message key="label.csr"/>',
		tooltip:"Y",
		width:6
  	},
	{
		columnId:"picklistId",
		columnName:'<fmt:message key="label.picklistid"/>',
		width:6
  	},
  {
	columnId:"catPartNo",
	columnName:'<fmt:message key="label.part"/>',
	width:8
  },
  {
	columnId:"quantity",
	columnName:'<fmt:message key="label.quantity"/>',
    sorting:'int',
	width:4 
  },
  {
	columnId:"datePicked",
	columnName:'<fmt:message key="label.datepicked"/>',
	width:11
  },
  {
	columnId:"issuerName",
	columnName:'<fmt:message key="label.picker"/>'
  },
  {
	columnId:"lineItem"
  },
  {
	columnId:"prNumber"
  },
  {
	columnId:"companyId"
  },
  {
	columnId:"facilityId"
  },
  {
	columnId:"carrierCode"
  },
  {
	columnId:"trackingNumber"
  },
  {
	columnId:"deliveredDate"
  },
  {
	columnId:"customerId"
  },
	{
		columnId:"customerServiceRepId"
	},
	{
		columnId:"issueQcDate",
		columnName:'<fmt:message key="label.qcdate"/>'
	},
	{
		columnId:"issueQcUserName",
		columnName:'<fmt:message key="label.qcedby"/>'
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
		columnId:"prInternalNote",
		columnName:'<fmt:message key="label.orderinternalnote"/>',
		tooltip:"Y"
	},
	{
		columnId:"prExternalNote",
		columnName:'<fmt:message key="label.orderexternalnote"/>',
		tooltip:"Y"
	},
	{
		columnId:"lineInternalNote",
		columnName:'<fmt:message key="label.internallinenote"/>',
		tooltip:"Y"
	},
	{
		columnId:"lineExternalNote",
		columnName:'<fmt:message key="label.externallinenote"/>',
		tooltip:"Y"
	},
  	{
		columnId:"orishipmentId"
	},
  	{
		columnId:"overShipped"
	},
  	{
		columnId:"currencyId"
	},
	{
		columnId:"companyId"
	},
	{   
		columnId:"materialRequestOrigin"
	},
	{   
		columnId:"distribution"
	},
	{
		columnId:"pickingUnitId"
	},
	{
		columnId:"boxCount"
	}
	<c:if test="${showProForma == 'Y'}">
	,
	{
		columnId:"lastProFormaPrintDate",
		columnName:'<fmt:message key="label.lastproformaprintdate"/>',
		width:11
	}
	</c:if>		
  ];

function _simpleUpdate(act,val) { 
//	  if( window['validateForm'] && !validateForm() ) return false;
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'update';
  $(act).value = val;
  document.genericForm.target='';
  parent.showPleaseWait();
  if(haasGrid) haasGrid.parentFormOnSubmit(); //prepare grid for data sending
  document.genericForm.submit();
  return false;
}

var isAutoShipConfirm = '${isAutoShipConfirm}';
function confirmShipment() {
	  if(isAutoShipConfirm == 'true') {
	  try{
		  document.getElementById('deliveredDate').value = parent.document.getElementById('deliveredDate').value;
		  }
		  catch (ex)
        {
        }
         var flag = validateForm();
        
	    if(flag) {
	      var action = document.getElementById("uAction");
	        action.value = 'confirmShipment';
	        document.genericForm.target='';
		    parent.showPleaseWait();
		    if(haasGrid) haasGrid.parentFormOnSubmit();
		    document.genericForm.submit();
	      }
	    }
	    
	  else {
	      var action = document.getElementById("uAction");
          countForLink = $v("distributionCount");
          printInvoiceYCount = $v("printInvoiceYCount");
          if (printInvoiceYCount == 0)
          {
              countForLink = 0;
          }
          consignmentTransferCount = $v("consignmentTransferCount");
          
          if ($v("pageid") == 'shipConfirm') {
        	  openWinGeneric('/tcmIS/hub/confirmshipment.do?pageid='+$v("pageid")+'&uAction=showconfirmshipment&branchPlant='+$("branchPlant").value+'&inventoryGroup='+$("inventoryGroup").value+'&distributionCount='+countForLink+'&consignmentTransferCount='+consignmentTransferCount,'_ConfirmShipment','900','600','yes');	  
          } else {
        	  openWinGeneric('confirmshipment.do?pageid='+$v("pageid")+'&uAction=showconfirmshipment&branchPlant='+$("branchPlant").value+'&inventoryGroup='+$("inventoryGroup").value+'&distributionCount='+countForLink+'&consignmentTransferCount='+consignmentTransferCount,'_ConfirmShipment','900','600','yes');	  
          }
	  }
	   
}
	
function validateForm() {
  var deliveredDate = $v("deliveredDate");
  try{
  if(deliveredDate.length == 0 ){
      alert(messagesData.dateexpected);
      return false;
  }
  }
    catch (ex)
        {
        }

  if(!isAnyRowChecked()) {
    alert(messagesData.noRowChecked);
    return false;
  }
  return true;
}

function isAnyRowChecked() {
	var rowsNum = beangrid.getRowsNum();
		for(var p = 1 ; p <= rowsNum; p ++) {
	          try {
				var cid = "selected"+p;
				if($(cid).checked) 
					return true;
			  } catch(ex){}
		}
	
	return false;
}
	
function printDeliveryDocument() {
  var documentType = parent.$v("documentType");
  var idField = parent.$v("idField");
  
  if (!(isFloat(idField,false)))
  {  
   	  errorMessage = messagesData.mustbeanumberinthisfield+"\n";
   	  alert(errorMessage);
   	  return false;
  }
  
  //loc = "/tcmIS/distribution/printdeliverydocument.do?documentType="+documentType+"&idField="+idField;
  loc = "/HaasReports/report/printdeliverydocument.do?documentType="+documentType+"&idField="+idField;
//use your report server setting.
//  loc = "/HaasReports/report/printdeliverydocument.do?documentType="+documentType+"&idField="+idField;
  openWinGeneric(loc,"printDeliveryDocument","800","600","yes","50","50","20","20","no");
}

//-->
</script>

<div id="hiddenSpan" style="display: none;">
<a href="#" onclick="resultFrame.showLegend(); return false;"><fmt:message key="label.showlegend"/></a>
<c:if test="${hasPermission == 'true'}">
<c:choose>
  <c:when test='${distributionYCount != 0}' >
    | <a href="#" onclick="resultFrame._simpleUpdate(null,'createShipment'); return false;"><fmt:message key="label.createshipment"/></a>
    | <a href="#" onclick="resultFrame._simpleUpdate(null,'updateShipment'); return false;"><fmt:message key="label.updateshipment"/></a>
  	| <a href="#" onclick="resultFrame.confirmShipment(); return false;"><fmt:message key="label.confirmshipment"/></a>
  	| <a href="#" onclick="resultFrame.printBoxLabels(); return false;"><fmt:message key="label.deliverylabels"/></a>
  	| <a href="#" onclick="resultFrame.printBagLabels(); return false;"><fmt:message key="label.baglabels"/></a>
  	| <a href="#" onclick="resultFrame.printShippingLabels(); return false;"><fmt:message key="label.shippinglabels"/></a>
  	| <fmt:message key="label.documentsfor"/>&nbsp;
    <select name="documentType" id="documentType" onchange="resultFrame.buildIdFieldDropDown();" class="selectBox">
  			<option value="shipmentId" selected><fmt:message key="label.shipment"/></option>
  			<option value="mr"><fmt:message key="label.mr"/></option>
    </select>
    <select name="idField" id="idField" class="selectBox">
    </select>
<%--    <input class="inputBox" type="text" name="idField" id="idField" value="" size="4" maxlength="10">   --%>
    <a href="#" onclick="resultFrame.printDeliveryDocument(); return false;"><fmt:message key="label.print"/></a>
     | <a href="#" onclick="resultFrame.printConsolPL(); return false;"><fmt:message key="label.printconsolPL"/></a>
    | <a href="#" onclick="resultFrame.printBolLong(); return false;"><fmt:message key="label.printbollong"/></a>
    | <a href="#" onclick="resultFrame.printBolShort(); return false;"><fmt:message key="label.printbolshort"/></a>
    | <a href="#" onclick="resultFrame.consolidateBol(); return false;"><fmt:message key="label.consolidatedbol"/></a> 
    <c:if test="${showProForma == 'Y'}">
    	| <a href="#" onclick="resultFrame.printProForma(); return false;"><fmt:message key="label.printproforma"/></a>
    </c:if>
  </c:when>
  <c:when test='${distributionYCount == 0 && isAutoShipConfirm == "false"}' >
    | <a href="#" onclick="resultFrame._simpleUpdate(null,'createShipment'); return false;"><fmt:message key="label.createshipment"/></a>
    | <a href="#" onclick="resultFrame._simpleUpdate(null,'updateShipment'); return false;"><fmt:message key="label.updateshipment"/></a>
  	| <a href="#" onclick="resultFrame.confirmShipment(); return false;"><fmt:message key="label.confirmshipment"/></a>
  	| <a href="#" onclick="resultFrame.printBoxLabels(); return false;"><fmt:message key="label.deliverylabels"/></a>
  	| <a href="#" onclick="resultFrame.printBagLabels(); return false;"><fmt:message key="label.baglabels"/></a>
  	| <a href="#" onclick="resultFrame.printShippingLabels(); return false;"><fmt:message key="label.shippinglabels"/></a>
  	| <a href="#" onclick="resultFrame.printPackingList(); return false;"><fmt:message key="label.printpackinglist"/></a>
    | <a href="#" onclick="resultFrame.printBolLong(); return false;"><fmt:message key="label.printbollong"/></a>
    | <a href="#" onclick="resultFrame.printBolShort(); return false;"><fmt:message key="label.printbolshort"/></a>
    | <a href="#" onclick="resultFrame.consolidateBol(); return false;"><fmt:message key="label.consolidatedbol"/></a> 
    <c:if test="${showProForma == 'Y'}">
    	| <a href="#" onclick="resultFrame.printProForma(); return false;"><fmt:message key="label.printproforma"/></a>
    </c:if>
  </c:when>
  <c:when test='${distributionYCount == 0 && isAutoShipConfirm == "true"}' >
    | <a href="#" onclick="resultFrame.confirmShipment(); return false;"><fmt:message key="label.confirmshipment"/></a>
	<c:if test="${isAutoShipConfirm == 'true'}">
		<c:set var="deliveredDate"><tcmis:jsReplace value="${param.deliveredDate}"/></c:set>
		<c:if test="${empty deliveredDate}">
		  <c:set var="deliveredDate">
		    <tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>
		  </c:set>
		</c:if>
 		| <fmt:message key="label.datedelivered"/>:
		<input class="inputBox pointer" name="deliveredDate" id="deliveredDate" type="text" value="${deliveredDate}" size="9" readonly="readonly" 
		onclick="return getCalendar(document.getElementById('deliveredDate'), null, null, document.genericForm.date60, document.genericForm.date7);"/>
	</c:if>
	| <a href="#" onclick="resultFrame.printBolLong(); return false;"><fmt:message key="label.printbollong"/></a>
	| <a href="#" onclick="resultFrame.printBolShort(); return false;"><fmt:message key="label.printbolshort"/></a>
	| <a href="#" onclick="resultFrame.printBoxLabels(); return false;"><fmt:message key="label.deliverylabels"/></a>
	| <a href="#" onclick="resultFrame.printBagLabels(); return false;"><fmt:message key="label.baglabels"/></a>
	| <a href="#" onclick="resultFrame.printShippingLabels(); return false;"><fmt:message key="label.shippinglabels"/></a>
	<c:if test="${showProForma == 'Y'}">
    	| <a href="#" onclick="resultFrame.printProForma(); return false;"><fmt:message key="label.printproforma"/></a>
    </c:if>
  </c:when> 
  <c:otherwise>
    &nbsp;
<%--    <a href="#" onclick="resultFrame.printDelDocuments(); return false;"><fmt:message key="label.printdeliverydoc"/></a> --%>
  </c:otherwise>
</c:choose>   
</c:if>
</div>

</c:if>

<c:if test="${empty beanColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="hub" id="hub" type="hidden"  value='<tcmis:jsReplace value="${param.hub}"/>' />
<input name="branchPlant" id="branchPlant" type="hidden"  value='<tcmis:jsReplace value="${param.hub}"/>'/>
<input name="inventoryGroup" id="inventoryGroup" type="hidden"  value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
<input name="sortBy" id="sortBy" type="hidden"  value='<tcmis:jsReplace value="${param.sortBy}"/>'/>
<input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}"/>
<input name="opsEntityId" id="opsEntityId" type="hidden"  value='<tcmis:jsReplace value="${param.opsEntityId}"/>'/>
<input name="customerName" id="customerName" type="hidden"  value='<tcmis:jsReplace value="${param.customerName}"/>'/>
<input name="customerId" id="customerId" type="hidden"  value='<tcmis:jsReplace value="${param.customerId}"/>'/>
<input name="customerServiceRepId" id="customerServiceRepId" type="hidden"  value='<tcmis:jsReplace value="${param.customerServiceRepId}"/>'/>
<input name="searchField" id="searchField" type="hidden"  value='<tcmis:jsReplace value="${param.searchField}"/>'/>
<input name="searchMode" id="searchMode" type="hidden"  value='<tcmis:jsReplace value="${param.searchMode}"/>'/>
<input name="searchArgument" id="searchArgument" type="hidden"  value='<tcmis:jsReplace value="${param.searchArgument}"/>'/>
<input name="distributionCount" id="distributionCount" type="text" value="${distributionYCount}"/>
<input name="printInvoiceYCount" id="printInvoiceYCount" type="text" value="${printInvoiceYCount}"/> 
<input name="consignmentTransferCount" id="consignmentTransferCount" type="text" value="${consignmentTransferCount}"/>
<input name="pageid" id="pageid" type="hidden"  value='<tcmis:jsReplace value="${param.pageid}"/>'/>  
<input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>   
<input type="hidden" name="showProForma"  id="showProForma" value="${showProForma}"/> 
</div>

</div>
</div> <!-- close of interface -->

<c:if test="${showChkAllBox == 'Y'}">
<script type="text/javascript">
    <!--
   	 showCheckAllBox = true;
    //-->
</script>
</c:if>

</tcmis:form>

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

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.shipconfirmlegendmsg"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>


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

<c:if test="${! empty beanColl }" >
parent.document.getElementById('mainUpdateLinks').innerHTML = 
	   document.getElementById('hiddenSpan').innerHTML;
	   
</c:if>
<c:if test="${empty beanColl }" >
parent.document.getElementById('mainUpdateLinks').innerHTML = '&nbsp;';
parent.document.getElementById('mainUpdateLinks').style.display = 'none';
</c:if>

function closeTransitWin() {
	parent.closeTransitWin();
}
//-->
</script>

</body>
</html>