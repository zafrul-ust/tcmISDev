<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/customerrequest.js"></script>

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

<title>
<fmt:message key="label.logistics"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
customerrequestdetail:"<fmt:message key="customerrequestdetail.title"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

with ( milonic=new menuname("showCustomer") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=<fmt:message key="label.showdetails"/> ;url=javascript:linkViewCustomerRequestDetail();" );
}

drawMenus();
function selectRow()
{  
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
//
   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   haasGrid.selectRowById(rowId0,null,false,false);	
 //  selectRow(rowId,cellInd);

   if( !rightClick ) return;

	currentCustomerRequestId = cellValue(rowId0,"customerRequestId")
	toggleContextMenu('showCustomer');
//contextDisabled = true;
//closeAllMenus();
//popup('showCustomer',1);
}

var gridConfig = {
	divName:'CustomerAddRequestViewBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};
<%--
<a href="#" onclick="viewCustomerRequestDetail('${status.current.customerRequestId}');">${status.current.customerRequestId}</a>
--%>
var config = [
  {
  	columnId:"customerRequestId",
  	columnName:'<fmt:message key="label.requestid"/>',
  	width:5
  },
  {
  	columnId:"customerRequestType",
  	columnName:'<fmt:message key="label.requesttype"/>',
    width:10
  },
  {
  	columnId:"customerRequestStatus",
  	columnName:'<fmt:message key="label.status"/>',
    width:10
  },
  {
  	columnId:"requesterName",
  	columnName:'<fmt:message key="label.requestor"/>',
    width:10
  },
  {
  	columnId:"customerName",
  	columnName:'<fmt:message key="label.customername"/>',
    width:15,
    tooltip:true
  },
  {
  	columnId:"customerNotes",
  	columnName:'<fmt:message key="label.comments"/>',
    width:15,
    tooltip:true
  },
  {
  	columnId:"transactionDate",
  	columnName:'<fmt:message key="label.requestdate"/>',
	sorting:'int',
    align:"left",
    hiddenSortingColumn:"hdor1"
  },
  {
  	columnId:"paymentTerms",
  	columnName:'<fmt:message key="label.paymentterms"/>'
  },
<%--
  {
  	columnId:"paymentTermApproverName",
  	columnName:'<fmt:message key="label.paymenttermsapprovedby"/>'
  },
  {
  	columnId:"datePaymentTermsApproved",
  	columnName:'<fmt:message key="label.paymenttermsapprovedon"/>',
	sorting:'int',
	hiddenSortingColumn:"hdor2"
  },
--%>
  {
  	columnId:"approverName",
  	columnName:'<fmt:message key="label.approvedby"/>'
  },
  {
  	columnId:"dateApproved",
  	columnName:'<fmt:message key="label.approvedon"/>',
	sorting:'int',
    align:"left",
    hiddenSortingColumn:"hdor3"
  },
  {
  	columnId:"sapCustomerCode",
  	columnName:'<fmt:message key="label.sapcustomercode"/>'
  },
  {
  	columnId:"customerId",
  	columnName:'<fmt:message key="label.customerid"/>',
  	width:10
  },
  {
  	columnId:"hdor1"
  },
  {
  	columnId:"hdor2"
  },
  {
  	columnId:"hdor3"
  }
  ];

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">
<tcmis:form action="/customerrequestupdate.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty beanCollection}" >
<div id="CustomerAddRequestViewBean" style="width:100%;height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="transactionDate" value="${bean.transactionDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="datePaymentTermsApproved" value="${bean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="dateApproved" value="${bean.dateApproved}" pattern="${dateFormatPattern}"/>
{ id:${status.index +1},
 data:[
  '${status.current.customerRequestId}',
  '${bean.customerRequestType}',
  '${bean.customerRequestStatus}',
  '<tcmis:jsReplace value="${bean.requesterName}"/>',
  '<tcmis:jsReplace value="${bean.customerName}"/>',
  '<tcmis:jsReplace value="${bean.customerNotes}" processMultiLines="true"/>',         
  '${transactionDate}',
  '${bean.paymentTerms}',
  //'<tcmis:jsReplace value="${bean.paymentTermApproverName}"/>',
  //'${datePaymentTermsApproved}',
  '<tcmis:jsReplace value="${bean.approverName}"/>',
  '${dateApproved}',
  '${bean.sapCustomerCode}',
  '${bean.customerId}',
  '${bean.transactionDate.time}',
  '${bean.datePaymentTermsApproved.time}',
  '${bean.dateApproved.time}'
  ]
}

 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
//-->
</script>

</div> <!-- end of grid div. -->
</c:if>

<c:if test="${empty beanCollection}" >
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
<input name="customerRequestId" id="customerRequestId" value="" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
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

<!-- permission stuff here --!>
showUpdateLinks = true;
//-->
</script>

</body>
</html:html>