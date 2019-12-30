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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
addlheaderchargespr:"<fmt:message key="label.addlheaderchargespr"/>",
format:"<fmt:message key="label.format"><fmt:param>###-##-####</fmt:param></fmt:message>"
};

<c:set var="InvoiceCorrectionPermission" value="N"/>
<c:if test="${ ! empty invoice[0].opsEntityId }">
	<c:set var="opsEntityId" value="${invoice[0].opsEntityId}"/>
</c:if>
<c:if test="${ empty invoice[0].opsEntityId }">
	<c:set var="opsEntityId" value="${param.opsEntityId}"/>
</c:if>

<c:if test="${ ! empty opsEntityId }">
<tcmis:opsEntityPermission indicator="true" userGroupId="InvoiceCorrection" opsEntityId="${opsEntityId}">
	<c:set var="InvoiceCorrectionPermission" value="Y"/>
</tcmis:opsEntityPermission>
</c:if>

function checkclose() {
if( '${param.uAction}' == 'deletedraft' ) {
	tabId = window.name.substr(0,window.name.length-5); 
	try { parent.parent.closeTabx(tabId);
	} catch(ex){}
	try { parent.closeTabx(tabId);
	} catch(ex){}
	window.close();
}

}

function submitInvoiceCorr(){
    $("uAction").value = "submit";
    submitOnlyOnce();
    invoiceGrid.parentFormOnSubmit(); //prepare grid for data sending
	chargeGrid.parentFormOnSubmit(); //prepare grid for data sending
    document.genericForm.submit();
}

function refresh() {
	$('uAction').value = 'search';
	document.genericForm.submit();
}

function print() {
	  loc = "/HaasReports/common/loadingfile.jsp";
	  openWinGeneric(loc,"printInvoice","800","600","yes","50","50","20","20","no");
	  var aa = document.genericForm.action;
	  var tt = document.genericForm.target;
	  document.genericForm.action = "/HaasReports/report/printinvoice.do?personnelId=${personnelBean.personnelId}&justprint=Y&InvoiceSearchBean[0].invoice=${invoice[0].correctedInvoice}&InvoiceSearchBean[0].selected=true&InvoiceSearchBean[0].customerId=${invoice[0].customerId}";
	  document.genericForm.target = "printInvoice";
      document.genericForm.submit();
	  document.genericForm.action = aa;
	  document.genericForm.target = tt;
      
}

function approve() {
	$('uAction').value = 'approve';
//	showTransitWin();
	$('invoice').value = '${invoice[0].correctedInvoice}';
	$('correctedInvoice').value = '${invoice[0].correctedInvoice}';
	document.genericForm.submit();
}

function save() {
    invoiceGrid.parentFormOnSubmit(); //prepare grid for data sending
	$('uAction').value = 'save';
//	showTransitWin();
	document.genericForm.submit();
}

function rollback() {
    invoiceGrid.parentFormOnSubmit(); //prepare grid for data sending
	$('uAction').value = 'rollback';
//	showTransitWin();
	document.genericForm.submit();
}

function closeifdone() {
	if( '${done}'  == 'Y') {
		window.opener.refreshme();
	}
	
	if( '${param.correctedInvoice}' ) {
		alert('Done');
		window.close();		
	}
	var err = '${err}';
	if( err ) {
		alert( err );
		window.close();
	}
}

function showmsg() {
var rollback= '${rollbackmsg}';
if( rollback ) {
	alert( rollback );
//	$('uAction').value = 'search';
//	$('correctedInvoice').value = '';
//	document.genericForm.submit();
	}
}
// -->
</script>

<title>
</title>

</head>

<body bgcolor="#ffffff" onload="closeifdone();loadinvoiceIds();loadchargeIds();initPopupGridWithGlobal(chargeConfig);initGridWithGlobal(invoiceConfig);showremovelines();showmsg()"><%-- has IE JS bug onresize="myResize()"> --%>

<tcmis:form action="/invoicecorrection.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">
<div class="contentArea">

<!-- Search Option Ends -->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<c:if test="${! empty tcmISErrors or !empty tcmISError}" >
<c:set var="hasError" value='hasError'/>
  <c:forEach var="err" items="${tcmISErrors}" varStatus="status">
    ${err} <br>
  </c:forEach>
  ${tcmISError}
</c:if>    
</div>
<!-- Error Messages Ends -->
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">

<%-- beginning of setting actions --%>
<c:set var="correctedInvoice" value=""/>
<c:if test="${ !empty invoice[0].correctedInvoice && param.uAction eq 'submit' and InvoiceCorrectionPermission eq 'Y'}">
	<c:set var="correctedInvoice" value="Y"/>
</c:if>

	<c:if test="${empty correctedInvoice and InvoiceCorrectionPermission eq 'Y' }">
   		<a href="#" onclick="submitInvoiceCorr();"><fmt:message key="button.submit"/></a>
   	</c:if>
	<c:if test="${!empty correctedInvoice }">
		<a href="#" onclick="print();"><fmt:message key="label.print"/></a> 
		| <a href="#" onclick="rollback();"><fmt:message key="label.undo"/></a> 
		| <a href="#" onclick="approve();"><fmt:message key="label.approve"/></a>  
   	</c:if>

<%-- setting up readonly --%>
<c:set var="readonly" value="true"/>

    </div>
    <div class="dataContent">
    <table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
<td colspan="4">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
    <td>
		${invoice[0].opsEntityAddressLine1} ${invoice[0].opsEntityAddressLine2} ${invoice[0].opsEntityAddressLine3} ${invoice[0].opsEntityAddressLine4} ${invoice[0].opsEntityAddressLine5} 
		OFFice: ${invoice[0].opsEntityPhone} Fax: ${invoice[0].opsEntityFax}
    </td>
    </tr>
	</table>
</td>
</tr>
<tr>
<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.invoice"/></legend>

<table>
<tr>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.invoice"/>:</td>
<td width="15%">${invoice[0].customerInvoice}</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.salesordernumber"/>:</td>
<td width="15%">${invoice[0].prNumber}</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.ponumber"/>:</td>
<td width="15%">${invoice[0].poNumber}</td>
</tr>
<tr>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.invoicedate"/>:</td>
<td width="15%">
  <fmt:formatDate var="invoiceDate" value="${invoice[0].invoiceDate}" pattern="${dateFormatPattern}"/>
	${invoiceDate}
</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.invoicecurrency"/>:</td>
<td width="15%">${invoice[0].currencyId}</td>
<td/>
<td/>
</tr>
</table>

</fieldset>
</td>
</tr>

<tr>
<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.address"/> </legend>
<table>
<tr>
<td width="45%">
<table>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.billto"/>:</td>
	<td width="15%">${invoice[0].customerId}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.fulladdress"/>:<%--<span style='font-size:12.0pt;color:red'>*</span>--%></td>
	<td width="15%">${invoice[0].billToAddressLine1}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].billToAddressLine2}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].billToAddressLine3}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].billToAddressLine4}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].billToAddressLine5}</td>
</tr>
<tr class="alt">
<td/><td/>
</tr>
</table>
</td>
<td width="10%" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td width="50%" align="right">
<table>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.shipto"/>:</td>
	<td width="15%">${invoice[0].endUser}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight" title='<fmt:message key="label.addressToolTip"/>'><fmt:message key="label.fulladdress"/>:<%--<span style='font-size:12.0pt;color:red'>*</span>--%></td>
	<td width="15%" nowrap="nowrap">${invoice[0].shipToAddressLine1}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" nowrap="nowrap">${invoice[0].shipToAddressLine2}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" nowrap="nowrap">${invoice[0].shipToAddressLine3}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" nowrap="nowrap">${invoice[0].shipToAddressLine4}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%" nowrap="nowrap">${invoice[0].shipToAddressLine5}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.taxregistrationnum"/>:</td>
	<td width="15%" nowrap="nowrap">${invoice[0].taxRegistrationNumber}</td>
</tr>
</table>

</td>
</tr>
</table>

</fieldset>
</td>
</tr>
<%-- 
<tr>
<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.shipto"/> </legend>
<table>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight" title='<fmt:message key="label.addressToolTip"/>'><fmt:message key="label.fulladdress"/>:--%><%--<span style='font-size:12.0pt;color:red'>*</span>--%><%--</td>
	<td width="15%">${invoice[0].shipToAddressLine1}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].shipToAddressLine2}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].shipToAddressLine3}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].shipToAddressLine4}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"></td>
	<td width="15%">${invoice[0].shipToAddressLine5}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.attn"/>:</td>
	<td width="15%">${invoice[0].endUser}</td>
</tr>
<tr class="alt">
	<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.taxregistrationnum"/>:</td>
	<td width="15%">${invoice[0].taxRegistrationNumber}</td>
</tr>
</table>

</fieldset>
</td>
</tr>
--%>
<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.detail"/></legend>
<table>

<tr>
<td width="8%" class="optionTitleBoldRight">
</td>
<td width="15%">
</td>
<td width="8%" class="optionTitleBoldRight" nowrap="nowrap">
</td>
</tr>


<tr>
<td colspan="4" nowrap="nowrap">
<fmt:message key="label.invoiceline"/> 
[ <fmt:message key="label.total"/>: <span id="lineTotalSpan" style="color:red;cursor:pointer;">0</span> ]
<c:if test="${empty correctedInvoice and InvoiceCorrectionPermission eq 'Y'}">
<span id="addInvoiceLineSpan" style='color:blue;cursor:pointer'>
<a onclick="save()"><fmt:message key="label.save"/></a>	
&nbsp;
</span>
</c:if>
<%--
&#150;&#150;&#150;&#150;&#150;&#150; 
<span id="addInvoiceLineSpan" style='color:blue;cursor:pointer'>
<a onclick="addInvoiceLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="removeInvoiceLineSpan" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeInvoiceLine()"><fmt:message key="label.removeLine"/></a>
</span>
 --%>
<div id="InvoiceCorrPrintLineViewBean" style="height:150px;width:99%;display: none;" ></div>
<br/>

<fmt:message key="label.headercharges"/>
 [ <fmt:message key="label.total"/>: <span id="headerTotalSpan" style="color:red;cursor:pointer;">0</span> ]    
<span id="AddChargeLineSpan" style='color:blue;cursor:pointer'>
&nbsp;	
<c:if test="${empty correctedInvoice and InvoiceCorrectionPermission eq 'Y'}">
<a onclick="addHeaderCharge()"><fmt:message key="label.edit"/></a>
</c:if>	
&nbsp;
</span>
<span id="removeChargeLineSpan" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeHeaderCharge()"><fmt:message key="label.removeLine"/></a>
</span>
<div id="chargeDiv" style="height:150px;width:99%;display: none;" ></div>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
// common to all grid
var pageReadOnly = true;
<c:set var="gp" value="N"/>

function calNewPrice(rowId){
	gridCell(invoiceGrid,rowId,"extentedPrice").setValue(
		(gridCellValue(invoiceGrid,rowId,"orderedQuantity")*gridCellValue(invoiceGrid,rowId,"adjustedUnitPrice")).toFixed(2) );
	showremovelines();
}

function showremovelines() {
//	try 
	{
// use mark for delete		
//      using menu.		
//		if(	invoiceGrid.getRowsNum() != 0 )
//			$('removeInvoiceLineSpan').style["display"] = "";
//		if( chargeGrid.getRowsNum() != 0 ) 
//			$('removeChargeLineSpan').style["display"] = "";
		if (invoiceGrid)
		  {   var lineTotal = 0 ;
			  var rowsNum = invoiceGrid.getRowsNum()*1;
//			  alert( rowsNum );
			  for (var p = 1; p < (rowsNum+1) ; p ++)
			  {   //alert( gridCellValue(invoiceGrid,p,"deleteCharge") +":"+gridCellValue(invoiceGrid,p,"extentedPrice"));
				  if( gridCellValue(invoiceGrid,p,"deleteCharge").indexOf( 'true' ) == -1 )
				  	lineTotal = lineTotal + gridCellValue(invoiceGrid,p,"extentedPrice")*1;
			  }
			  $('lineTotalSpan').innerHTML = lineTotal.toFixed(2);
		  }
		if (chargeGrid)
		  {   var lineTotal = 0 ;
			  var rowsNum = chargeGrid.getRowsNum()*1;
			  for (var p = 1; p < (rowsNum+1) ; p ++)
			  {   //alert( (invoiceCount + p) +":"+gridCellValue(chargeGrid,invoiceCount + p,"deleteCharge") );
				  if( gridCellValue(chargeGrid,invoiceCount + p,"deleteCharge").indexOf( 'true' ) == -1 )
				  	lineTotal = lineTotal + gridCellValue(chargeGrid,invoiceCount + p,"catalogPrice")*1;
			  }
			  $('headerTotalSpan').innerHTML = lineTotal.toFixed(2);
		  }
	}
//    catch (ex2){}
}

var invoiceConfig = {
		divName:'InvoiceCorrPrintLineViewBean', // the div id to contain the grid of the data.
		beanData:'invoiceData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'invoiceGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'invoiceColumn',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:invoiceSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:invoiceSelectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};
//  chargeGrid.parentFormOnSubmit(); //prepare grid for data sending
//  invoiceGrid.parentFormOnSubmit(); //prepare grid for data sending
var invoiceRowId = null;
var invoiceIds = new Array();


function loadinvoiceIds() {
	var up = invoiceData.rows.length
	for(i=1;i<= up; i++)
		invoiceIds[""+i] = i;
}

function removeInvoiceLine() {
	invoiceGrid.deleteRow(invoiceRowId);
	delete( invoiceIds[invoiceRowId] ) ;
	if( invoiceGrid.getRowsNum() == 0 ) 
		$('removeInvoiceLineSpan').style["display"] = "none";
	return ;
}

function addInvoiceLine(rowKey,orderQuantityRule) {

// if( !$v('opsEntityId') ) {
//	  alert(messagesData.pleaseselect + " : " + messagesData.operatingentity );
//	  return ;
// }
 var rowid = (new Date()).valueOf();
 ind = invoiceGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
	var thisrow = invoiceGrid.addRow(rowid,"",ind);
//	  opsEntityId[ rowid ] = buildNewOpsValudset();
    alertthis = true;
    
      newStatus[rowid] = new Array( { value:'New',text:'New'} ) ;
	  invoiceGrid.cells(rowid, count++).setValue('Y');

	  invoiceIds[""+rowid] = rowid;
	  invoiceRowId = rowid;
	  invoiceGrid.selectRow(invoiceGrid.getRowIndex(rowid));
	  $('removeInvoiceLineSpan').style.display="";
//	  paymentCountryChanged(rowid,"columnName");
}

with ( milonic=new menuname("showLineChargeMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	// style = contextStyle;
	// margin=3;
	 aI( "text=<fmt:message key="label.linecharges"/> ;url=javascript:addLineCharges();" );
	}

drawMenus();

var dhxFreezeWins = null;
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

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
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}

var children= new Array();

function addLineCharges() {
	  showTransitWin('<fmt:message key="label.linecharges"/>');
//	  alert( invoiceRowId +":"+selectedRowId +cellValue(selectedRowId,"lineItem"));
	  loc = "/tcmIS/distribution/invoicecorrection.do?orderType=InvoiceCorr"+"&uAction=linecharge&prNumber="+$v("prNumber")+
	  						"&companyId="+cellValue(selectedRowId,"companyId")+
	  					    "&opsEntityId="+$v("opsEntityId")+
	  						"&lineItem="+cellValue(selectedRowId,"lineItem")+
	  						"&invoice="+$v("invoice")+
	  					    "&curpath="+'InvoiceCorrection'+
	  					    "&currencyId="+$v("currencyId");
	  var winId= 'addLineCharge'+$v("prNumber");
	  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}

var selectedRowId = null;

function invoiceSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 selectedRowId = oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 invoiceRowId = rowId0;

	if (!rightClick)
		return true;
//	if ( cellValue(selectedRowId, "lineItem") )
	haasGrid.selectRowById(rowId0, null, false, false);
	toggleContextMenu('showLineChargeMenu');
 
} //end of method
/*
function paymentOpsChanged(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(invoiceGrid,rowId,columnId);
	  var inv = $("inventoryGroup"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewIgValudset(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.all,"", "inventoryGroup"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "inventoryGroup"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}

var defaultops = '';

function setdefaultops() {

	for( i=0;i < opshubig.length; i++) { 
		defaultops = opshubig[i].id;
		break;
	}
//	for( i=0;i < opshubig.length; i++) { 
//		opsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
//		excOpsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
//	}
//	$('opsEntityId').value = defaultops;
//	$('excOpsEntityId').value = defaultops;
	
}
*/

<c:set var="invoiceCount" value='0'/>
<c:set var="lineCount" value='0'/>

var invoiceData = {
        rows:[
<c:forEach var="bean" items="${invoiceColl}" varStatus="status">
<c:set var="lineCount" value='${lineCount+1}'/>
<c:if test="${status.index > 0}">,</c:if>
<%--<fmt:formatDate var="approvedOn" value="" pattern="${dateFormatPattern}"/>--%>
<c:set var="gp" value="N"/>
<c:if test="${empty correctedInvoice and InvoiceCorrectionPermission eq 'Y'}">
	<c:set var="gp" value="Y"/>
</c:if>
<tcmis:jsReplace var="partDescription" value="${bean.partDescription}" processMultiLines="true" />
<c:set var="deleteCharge" value="false"/>
<c:if test="${bean.deleteCharge eq 'Y'}">
	<c:set var="deleteCharge" value="true"/>
</c:if>
        /*The row ID needs to start with 1 per their design.*/
        { id:${lineCount},
         data:[
 '${gp}',
 '${bean.lineItem}','${bean.itemId}','${partDescription}','${bean.orderedQuantity}',
 '${bean.unitOfSalePrice}','${bean.adjustedUnitPrice}',('${bean.orderedQuantity}'*'${bean.adjustedUnitPrice}').toFixed(2),
 'N',${deleteCharge},
 '${bean.lineItem}','${bean.companyId}','${bean.unitOfSaleQuantityPerEach}'
 ] }
    	<c:forEach var="lcbean" items="${bean.charges}" varStatus="status">
        <c:set var="lineCount" value='${lineCount+1}'/>
    	<c:set var="deleteCharge" value="false"/>
    	<c:if test="${lcbean.deleteCharge eq 'Y'}">
    		<c:set var="deleteCharge" value="true"/>
    	</c:if>
    	<tcmis:jsReplace var="partDescription" value="${lcbean.chargeDescription}" processMultiLines="true" />    	
    	,
    	{ id:${lineCount},
            data:[
    'N',
    '','${lcbean.itemId}','${partDescription}','',
    '${lcbean.price}','${lcbean.adjustedPrice}',(1*'${lcbean.price}').toFixed(2),
    'N',${deleteCharge},
    '${bean.lineItem}','${bean.companyId}'
        ] }
//    	private BigDecimal invoice;
//    	private BigDecimal prNumber;
//    	private String lineItem;
//    	private String chargeDescription;
//    	private BigDecimal price;
//    	private BigDecimal taxRate;
    	</c:forEach>
        
    <c:set var="invoiceCount" value='${invoiceCount+1}'/>
     </c:forEach>
    ]};

var invoiceColumn = [
  {
  	columnId:"permission"
  },
  {
	columnName:"<fmt:message key="label.lineitem"/>",
  	columnId:"lineItemHidden"
  },
  {
  	columnId:"itemId"
  },
{
  	columnId:"partDescription",
	columnName:"<fmt:message key="label.itemdesc"/>",
	width:40
//    onChange:paymentOpsChanged,
//  	type:"hcoro"
},
{
  	columnId:"orderedQuantity",
	columnName:"<fmt:message key="label.orderedqty"/>"
},
/*{
  	columnId:"unitOfSaleQuantity",
	columnName:"--<fmt:message key="label.qtyshipped"/>--",
	width:10
},
{
  	columnId:"remainingQuantity",
	columnName:"<fmt:message key="label.qtyremaining"/>",
	width:6
},
*/
{
  	columnId:"unitOfSalePrice",
	columnName:"<fmt:message key="label.unitprice"/>"
},
{
  	columnId:"adjustedUnitPrice",
	columnName:"<fmt:message key="label.adjustedunitprice"/>",
  	type:"hed",
  	onChange:calNewPrice
},
{
  	columnId:"extentedPrice",
	columnName:"<fmt:message key="label.extendedprice"/>"
},
{
  	columnId:"deleteChargePermission"
},
{
  	columnId:"deleteCharge",
	columnName:"<fmt:message key="catalogspec.label.markedfordeletion"/>",
	type:'hchstatus',
	permission:true
},
{
  	columnId:"lineItem"
},
{
  	columnId:"companyId"
},
{
  	columnId:"unitOfSaleQuantityPerEach"
}
];

function chargeSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 igExcRowId = rowId0;
 
} //end of method


var chargeConfig = {
		divName:'chargeDiv', // the div id to contain the grid of the data.
		beanData:'chargeData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'chargeGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'chargeColumn',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:chargeSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};

//  chargeGrid.parentFormOnSubmit(); //prepare grid for data sending
var chargeRowId = null;
var chargeIds = new Array();

function loadchargeIds() {
	var up = chargeData.rows.length
	for(i=1;i<= up; i++)
		chargeIds[""+i] = i;
}

function removeHeaderCharge() {
	chargeGrid.deleteRow(igExcRowId);
	delete( chargeIds[igExcRowId] ) ;
	if( chargeGrid.getRowsNum() == 0 ) 
		$('newIgExcRemoveLine').style["display"] = "none";
	return ;
}

function addHeaderCharge() {
		  showTransitWin(messagesData.addlheaderchargespr);
//  	  loc = "addchargeheader.do?orderType="+$v("orderType")+"&status="+$v("status")+"&prNumber="+$v("prNumber")+"&companyId="+$v("companyId")+"&totalLineCharge="+lineTotal+"&currencyId="+$v("currencyId")+
//"&inventoryGroup="+$v("inventoryGroup")+"&creditStatus="+$v("creditStatus")+"&orderStatus="+$v("orderStatus")+"&noAddChargePermission="+noAddChargePermission+"&opsEntityId="+$v("opsEntityId");
		  loc = "invoicecorrection.do?orderType=InvoiceCorr"+"&uAction=headercharge&prNumber="+$v("prNumber")+"&totalLineCharge="+$("headerTotalSpan").innerHTML+
			"&companyId="+$v("companyId")+
			"&invoice="+$v("invoice")+
			"&opsEntityId="+$v("opsEntityId")+
// header charge no line  "&lineItem="+cellValue(selectedRowId,"lineItemHidden")+
		    "&curpath="+'InvoiceCorrection'+
		    "&currencyId="+$v("currencyId");

		  var winId= 'addHeaderCharge'+$v("prNumber");
		  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}
			


<c:set var="chargeCount" value='0'/>

var chargeData = {
        rows:[
<c:forEach var="bean" items="${chargeColl}" varStatus="status">

 <c:set var="gp" value="N"/>
		  <c:if test="${status.index > 0}">,</c:if>
<%--<fmt:formatDate var="approvedOn" value="${bean.approvedOn}" pattern="${dateFormatPattern}"/>--%>
<tcmis:jsReplace var="chargeDescription" value="${bean.chargeDescription}" processMultiLines="true" />
<c:set var="deleteCharge" value="false"/>
<c:if test="${bean.deleteCharge eq 'Y'}">
	<c:set var="deleteCharge" value="true"/>
</c:if>
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index + lineCount+ 1},
         data:[
 '${gp}',
 '${bean.catalogPrice}',
 '${chargeDescription}',
  ${deleteCharge},
 '${bean.prNumber}'
 ]}
    <c:set var="igExcCount" value='${igExcCount+1}'/>
     </c:forEach>
    ]};

var chargeColumn = [
  {
  	columnId:"permission"
  },
  {
	  	columnId:"catalogPrice",
		columnName:"<fmt:message key="label.linecharge"/>"
  },
  {
  	columnId:"chargeDescription",
	columnName:"<fmt:message key="label.description"/>",
	width:20
  },
  {
	  	columnId:"deleteCharge",
		columnName:"<fmt:message key="catalogspec.label.markedfordeletion"/>",
		type:'hchstatus'
  },
  {
	  	columnId:"prNumber"
  }
];


// -->
</script>

</td>
</tr>



</table>

</fieldset>
</td>
</tr>


<tr>
<td><%-- no use now --%></td>
</tr>
   </table>
   <!-- If the collection is empty say no data found -->
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
<%--
</c:if>
--%>
<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
	<input name="invoice" id="invoice" value="<c:out value="${param.invoice}"/>">
	<input name="customerInvoice" id="customerInvoice" value="<c:out value="${param.customerInvoice}"/>">
	<input name="prNumber" id="prNumber" type="hidden" value="<c:out value="${invoice[0].prNumber}"/>">
	<input name="currencyId" id="currencyId" type="hidden" value="<c:out value="${invoice[0].currencyId}"/>">
	<input name="opsEntityId" id="opsEntityId" type="hidden" value="${opsEntityId}"/>
	<input name="companyId" id="companyId" type="hidden" value="<c:out value="${invoice[0].companyId}"/>">
	<input name="correctedInvoice" id="correctedInvoice" type="hidden" value="${param.correctedInvoice}"/>
   <input type="hidden" name="uAction" id="uAction">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->


</div> <!-- close of interface -->

<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>


	 <%-- freeze --%>
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

</tcmis:form>

<script language="JavaScript" type="text/javascript">
<!--
var invoiceCount = ${lineCount};
//-->
</script>

</body>
</html:html>

