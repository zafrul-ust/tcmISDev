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
<script type="text/javascript" src="/js/accountspayable/supplierinvoicereportresults.js"></script>

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


<title>
	<fmt:message key="supplierinvoicereport.title" />
</title>

<script language="JavaScript" type="text/javascript">
<!--

var showUpdateLinks = false;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noRowChecked:"<fmt:message key="error.norowselected"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",  
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
invalidDateFormat:"<fmt:message key="error.date.invalid"/>"};

with(milonic=new menuname("poMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.openpo"/>;url=javascript:viewPO();");
       aI("text=<fmt:message key="label.openpoforinvoice"/>;url=javascript:viewInvoice();");
}

drawMenus();

function viewPO() {
	var radianPo = cellValue(mygrid.getSelectedId(),"radianPo"); 
	var loc = '/tcmIS/Invoice/AccountsPayable?po='+radianPo+'&Action=searchlike&subUserAction=po';	
  
	children[children.length] = openWinGeneric(loc,"radianPo"+radianPo,"800","820","yes","80","80","yes");	
}


function viewInvoice() {
	var radianPo = cellValue(mygrid.getSelectedId(),"radianPo"); 
	var supplierInvoiceId = cellValue(mygrid.getSelectedId(),"supplierInvoiceId");
	var loc = '/tcmIS/Invoice/AccountsPayable?po='+radianPo+'&selectedInvoiceId='+supplierInvoiceId+'&Action=searchlike&subUserAction=po';

	children[children.length] = openWinGeneric(loc,"voucherId","800","820","yes","80","80","yes");
}

var gridConfig = {
		divName:'voucherReportBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:false,     // this will open the txt cell type to enter notes by single click
	    onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRightclick,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	    onBeforeSorting:doOnBeforeSelect, // the onBeforeSorting event function to be attached, as in beanGrid.attachEvent("onBeforeSorting",selectRow));
	    noSmartRender:false// set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
};  

var config = [
{ columnId:"radianPo",
  columnName:'<fmt:message key="label.po"/>',
  width:5
},
{ columnId:"buyerName",
  columnName:'<fmt:message key="label.buyer"/>',
  tooltip:true,
  width:10
},
{ columnId:"buyerEmail",
  columnName:'<fmt:message key="label.email"/>',
  tooltip:true,
  width:15
},
{
   columnId:"buyerPhone",
   columnName:'<fmt:message key="label.phone"/>',
   width:7
},
{
    columnId:"supplierName",
    columnName:'<fmt:message key="label.supplier"/>',
    tooltip:true,
    width:18
},
{ columnId:"supplierInvoiceId",
  columnName:'<fmt:message key="label.invoice"/>',
  width:7
},
{ columnId:"invoiceDate",
  columnName:'<fmt:message key="label.invoicedate"/>',
  width:6
},
{  columnId:"poTerms",
   columnName:'<fmt:message key="label.ourterms"/>',
   width:6
},
{  columnId:"supplierTerms",
   columnName:'<fmt:message key="label.supplierterms"/>',
   width:6
},
{  columnId:"netInvoiceAmount",
   columnName:'<fmt:message key="label.amount"/>',
   align:'right',
   width:6
},
{  columnId:"status",
   columnName:'<fmt:message key="label.status"/>',
   width:8
},
{  columnId:"apApproverName",
   columnName:'<fmt:message key="label.approvedby"/>',
   width:10
},
{  columnId:"approvedDate",
   columnName:'<fmt:message key="label.approvedon"/>',
   width:6
},
{  columnId:"apQcUserName",
   columnName:'<fmt:message key="label.qcedby"/>',
   width:10
},
{  columnId:"qcDate",
   columnName:'<fmt:message key="label.qceddate"/>',
   width:6
},
{  columnId:"availableReceipts",
   columnName:'<fmt:message key="label.availablereceipts"/>',
   width:5
},
{  columnId:"apUserName",
   columnName:'<fmt:message key="label.apuser"/>',
   width:10
},
{  columnId:"apNote",
   columnName:'<fmt:message key="label.comment"/>',
   tooltip:true,
   width:15
}
];

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);" onunload="closeAllchildren();">
<tcmis:form action="/supplierinvoicereportresults.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:set var="hasPermission" value='false'/>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty voucherReportColl}" >
<div id="voucherReportBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*Storing the data to be displayed in a JSON object array.*/

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="reportBean" items="${voucherReportColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

{ id:${status.count}, 
  data:[
	      '${reportBean.radianPo}','<tcmis:jsReplace value="${reportBean.buyerName}" processMultiLines="false" />',
	      '${reportBean.buyerEmail}',
	      '${reportBean.buyerPhone}',
	      '<tcmis:jsReplace value="${reportBean.supplierName}" processMultiLines="false" />',
	      '${reportBean.supplierInvoiceId}','<fmt:formatDate value="${reportBean.invoiceDate}" pattern="${dateFormatPattern}"/>',
	      '${reportBean.poTerms}','${reportBean.supplierTerms}','${reportBean.netInvoiceAmount}',
	      '${reportBean.status}',
	      '<tcmis:jsReplace value="${reportBean.apApproverName}" processMultiLines="false" />',
	      '<fmt:formatDate value="${reportBean.approvedDate}" pattern="${dateFormatPattern}"/>',
	      '<tcmis:jsReplace value="${reportBean.apQcUserName}" processMultiLines="false" />',
	      '<fmt:formatDate value="${reportBean.qcDate}" pattern="${dateFormatPattern}"/>',
	      '${reportBean.availableReceipts}','<tcmis:jsReplace value="${reportBean.apUserName}" processMultiLines="false" />',
	      '<tcmis:jsReplace value="${reportBean.apNote}" processMultiLines="true" />']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};


//-->
</script>

</c:if>

<c:if test="${empty voucherReportColl}" >
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
<input name="userAction" id="userAction" value="" type="hidden"/>
<input name="maxData" id="maxData" type="hidden" value="${param.maxData}"/>
</div>

</div>
</div> <!-- close of interface -->

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
  <c:if test="${param.maxData == fn:length(beanColl)}">
	 <fmt:message key="label.maxdata">
	  <fmt:param value="${param.maxData}"/>
	 </fmt:message>
  </c:if>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
 <c:choose>
 <c:when test="${empty tcmISErrors and empty tcmISError}">
  <c:choose>
   <c:when test="${param.maxData == fn:length(beanColl)}">
     showErrorMessage = true;
     parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
   </c:when>
   <c:otherwise>
     showErrorMessage = false;
   </c:otherwise>
  </c:choose>
 </c:when>
 <c:otherwise>
   parent.messagesData.errors = "<fmt:message key="label.errors"/>";
   showErrorMessage = true;
   </c:otherwise>
 </c:choose>

//-->
</script>

</body>
</html>