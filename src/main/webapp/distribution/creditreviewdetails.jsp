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

<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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
<script type="text/javascript" src="/js/distribution/creditreviewdetails.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
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
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="creditreview.title"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
total:"<fmt:message key="label.total"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>"};

var unPaidCashConfig = [
{ columnId:"opsEntityId",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"homeCurrencyAmount",
  columnName:'<fmt:message key="label.amount"/>'
}
];


var openInvoiceConfig = [
{ columnId:"opsEntityId",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"invoiceNumber",
  columnName:'<fmt:message key="label.invoicenumber"/>',
  align:'left',
  width:10,
  tooltip:"Y",
  sorting:"int"
},
{ columnId:"referenceNumber",
  columnName:'<fmt:message key="label.po"/>',
  width:8,
  tooltip:"Y"
},
{ columnId:"homeCurrencyAmount",
  columnName:'<fmt:message key="label.open"/>',
  hiddenSortingColumn:"hHomeCurrencyAmount",
  sorting:"int"
},
{ columnId:"hHomeCurrencyAmount",
  sorting:"int"
},

{ columnId:"invoiceDate",
  columnName:'<fmt:message key="label.invoicedate"/>',
  hiddenSortingColumn:"hinvoiceDate",
  sorting:"int"
},
{ columnId:"hinvoiceDate",
  sorting:"int"
},
{ columnId:"paymentDueDate",
  columnName:'<fmt:message key="label.duedate"/>',
  hiddenSortingColumn:"hPaymentDueDate",
  sorting:"int"
},
{ columnId:"hPaymentDueDate",  
  sorting:"int"
},
{ columnId:"daysLate",
  columnName:'<fmt:message key="label.daysLate"/>',
  sorting:"int"
}
];


var openOrdersConfig = [
{ columnId:"opsEntityId",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"paymentTerms",
  columnName:'<fmt:message key="label.creditterms"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"prNumber",
  columnName:'<fmt:message key="label.mrnumber"/>',
  tooltip:"Y"
},
{ columnId:"poNumber",
  columnName:'<fmt:message key="label.po"/>',
  width:8,
  tooltip:"Y"
},
{ columnId:"homeCurrencyAmount",
  columnName:'<fmt:message key="label.open"/>',
  hiddenSortingColumn:"hHomeCurrencyAmount",
  sorting:"int"
 },
{ columnId:"hHomeCurrencyAmount",
   sorting:"int"
},

{ columnId:"submittedDate",
  columnName:'<fmt:message key="label.orderdate"/>',
  hiddenSortingColumn:"hSubmittedDate",
  sorting:"int"
},
{ columnId:"hSubmittedDate",
  sorting:"int"
}


];
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();" >

<tcmis:form action="/creditreviewdetails.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>
<!-- Result Frame Begins -->


<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div id="resultGridDiv" style="display: none;">

<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showUnPaidCashDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="label.unappliedcash"/></td>
<td colspan="6">&nbsp; </td>

<td class="optionTitleBoldRight"  onclick="showUnPaidCashDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideUnPaidCashDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideUnPaidCashDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>

<div id="unPaidCashDiv"> <%-- style="display: none;"> --%>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"><fmt:message key="label.unappliedcash"/> - <a href="#" onclick="createExcel('unappliedCash');"><fmt:message key="label.createexcel"/></a></div>
    
    
<div id="CustomerUnappliedCashViewBean" style="width:100%;height:240px;" style="display: none;"></div>

<c:if test="${CustomerUnappliedCashViewBeanCol != null}">
<script type="text/javascript">
<!--
<c:set var="unpaidCashDataCount" value='${0}'/>

<c:set var="unpaidCurrentyId" value=''/>

<c:set var="unpaidAmountTotal" value='${0}'/>
<c:if test="${!empty CustomerUnappliedCashViewBeanCol}">

var unPaidCashJsonMainData = new Array();
var unPaidCashJsonMainData = {
rows:[
<c:forEach var="CustomerUnappliedCashViewBeanCol" items="${CustomerUnappliedCashViewBeanCol}" varStatus="status">

<fmt:formatNumber var="amountFmt" value="${status.current.homeCurrencyAmount}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

<c:if test="${status.index > 0}">,</c:if>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['<tcmis:jsReplace value="${status.current.paidToEntityName}" />',

 <c:choose>
 <c:when test="${status.current.homeCurrencyAmount==null}"> 
 '',
 </c:when>
 <c:otherwise>
 '${status.current.homeCurrencyId} ${amountFmt}'
 </c:otherwise>
</c:choose> 
  ]}
 <c:set var="unpaidCashDataCount" value='${unpaidCashDataCount+1}'/>
 <c:set var="unpaidAmountTotal" value='${unpaidAmountTotal+status.current.homeCurrencyAmount}'/>
 <c:set var="unpaidCurrentyId" value='${status.current.homeCurrencyId}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty CustomerUnappliedCashViewBeanCol}">
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


  </div>
   <div id="unpaidCashFooter"  style="padding: 4px 0px 0px 0px;"></div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div> 
</div>

<div id="openInvoiceFrameDiv" style="margin: 4px 4px 0px 4px;">

<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showOpenInvoicesDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="label.openinvoices"/></td>
<td colspan="6">&nbsp; </td>

<td class="optionTitleBoldRight"  onclick="showOpenInvoicesDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideOpenInvoicesDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideOpenInvoicesDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>

<div id="openInvoicesDiv" > <%-- style="display: none;"> --%>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"><fmt:message key="label.openinvoices"/> - <a href="#" onclick="createExcel('openInvoices');"><fmt:message key="label.createexcel"/></a></div> 
	<div id="paymentTermIgExceptionViewBean"></div>

<div id="CustomerOpenInvoiceViewBean" style="width:100%;height:240px;" style="display: none;"></div>

<c:if test="${CustomerOpenInvoiceViewBeanCol != null}">
<script type="text/javascript">
<!--
<c:set var="openInvoicesDataCount" value='${0}'/>
<c:set var="openInvoicesTotal" value='${0}'/>
<c:set var="openInvoicesCurrentyId" value=''/>
<c:if test="${!empty CustomerOpenInvoiceViewBeanCol}">

var openInvoiceJsonMainData = new Array();
var openInvoiceJsonMainData = {
rows:[
<c:forEach var="CustomerOpenInvoiceViewBeanCol" items="${CustomerOpenInvoiceViewBeanCol}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatNumber var="amount2Fmt" value="${status.current.homeCurrencyAmount}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

<fmt:formatDate var="fmtInvoiceDate" value="${status.current.invoiceDate}" pattern="${dateFormatPattern}"/>
<c:set var="invoiceDateSortable" value="${status.current.invoiceDate.time}"/>

<fmt:formatDate var="fmtPaymentDueDate" value="${status.current.paymentDueDate}" pattern="${dateFormatPattern}"/>
<c:set var="paymentDueDateSortable" value="${status.current.paymentDueDate.time}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['<tcmis:jsReplace value="${status.current.paidToEntityName}" />','${status.current.invoiceNumber}',
 '${status.current.referenceNumber}',

 <c:choose>
 <c:when test="${status.current.homeCurrencyAmount==null}"> 
 '',
 </c:when>
 <c:otherwise>
 '${status.current.homeCurrencyId} ${amount2Fmt}',
 </c:otherwise>
</c:choose> 
 

 
 '${status.current.homeCurrencyAmount}' ,
 '${fmtInvoiceDate}',
 '${invoiceDateSortable}','${fmtPaymentDueDate}','${paymentDueDateSortable}',
 '${status.current.daysLate}']}
 <c:set var="openInvoicesDataCount" value='${openInvoicesDataCount+1}'/>
 <c:set var="openInvoicesTotal" value='${openInvoicesTotal+status.current.homeCurrencyAmount}'/>
 <c:set var="openInvoicesCurrentyId" value='${status.current.homeCurrencyId}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty CustomerOpenInvoiceViewBeanCol}">
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


  </div>
   <div id="openInvoiceFooter" style="padding: 4px 0px 0px 0px;"></div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div> 
 </div>
 
 <div id="openOrdersFrameDiv" style="margin: 4px 4px 0px 4px;">

<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showOpenOrdersDivRow" style="display: none;">

<td class="optionTitleBoldLeft"><fmt:message key="label.openorders"/></td>
<td colspan="6">&nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showOpenOrdersDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideOpenOrdersDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideOpenOrdersDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>

<div id="openOrdersDiv" > <%-- style="display: none;"> --%>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"><fmt:message key="label.openorders"/> - <a href="#" onclick="createExcel('openOrders');"><fmt:message key="label.createexcel"/></a></div>

<div id="CustomerOpenOrdersViewBean" style="width:100%;height:240px;" style="display: none;"></div>

<c:if test="${CustomerOpenOrdersViewBeanCol != null}">
<script type="text/javascript">
<!--
<c:set var="openOrdersDataCount" value='${0}'/>
<c:set var="openOrdersTotal" value='${0}'/>
<c:set var="openOrdersCurrentyId" value=''/>
<c:if test="${!empty CustomerOpenOrdersViewBeanCol}">
var openOrdersJsonMainData = new Array();
var openOrdersJsonMainData = {
rows:[
<c:forEach var="CustomerOpenOrdersViewBeanCol" items="${CustomerOpenOrdersViewBeanCol}" varStatus="status">
<fmt:formatNumber var="amount3Fmt" value="${status.current.homeCurrencyAmount}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtSubmittedDate" value="${status.current.submittedDate}" pattern="${dateFormatPattern}"/>
<c:set var="submittedDateSortable" value="${status.current.submittedDate.time}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${status.current.orderedFromEntityName}','${status.current.paymentTerms}','${status.current.prNumber}',
 '${status.current.poNumber}',
 <c:choose>
 <c:when test="${status.current.homeCurrencyAmount==null}"> 
 '',
 </c:when>
 <c:otherwise>
 '${status.current.homeCurrencyId} ${amount3Fmt}',
 </c:otherwise>
</c:choose> 
 '${status.current.homeCurrencyAmount}',
 '${fmtSubmittedDate}','${submittedDateSortable}']}
 <c:set var="openOrdersDataCount" value='${openOrdersDataCount+1}'/>
 <c:set var="openOrdersTotal" value='${openOrdersTotal+status.current.homeCurrencyAmount}'/>
 <c:set var="openOrdersCurrentyId" value='${status.current.homeCurrencyId}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty CustomerOpenOrdersViewBeanCol}">
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


  </div>
   <div id="openOrdersFooter"  style="padding: 4px 0px 0px 0px;"></div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div> 
 </div>
 
 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

</div>
</div>
</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="customerId" id="customerId" value="${param.customerId}" type="hidden"/>
<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="minHeight" id="minHeight" type="hidden" value="100"/>


<input name="unpaidCashTotalLines" id="unpaidCashTotalLines" value="${unpaidCashDataCount}" type="hidden"/>  
<input name="openInvoicesTotalLines" id="openInvoicesTotalLines" value="${openInvoicesDataCount}" type="hidden"/>
<input name="openOrdersTotalLines" id="openOrdersTotalLines" value="${openOrdersDataCount}" type="hidden"/>

<fmt:formatNumber var="unpaidAmountTotalFmt" value="${unpaidAmountTotal}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="openInvoicesTotalFmt" value="${openInvoicesTotal}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="openOrdersTotalFmt" value="${openOrdersTotal}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
 
 <input name="unPaidAmountTotal" id="unPaidAmountTotal" value="${openOrdersTotal}" type="hidden"/>
 <input name="openInvoicesTotal" id="openInvoicesTotal" value="${openInvoicesTotalFmt}" type="hidden"/>
 <input name="openOrderTotal" id="openOrderTotal" value="${openOrdersTotalFmt}" type="hidden"/>
 
 
  <input name="unPaidCurrencyId" id="unPaidCurrencyId" value="${unpaidCurrentyId}" type="hidden"/>
 <input name="openInvoicesCurrencyId" id="openInvoicesCurrencyId" value="${openInvoicesCurrentyId}" type="hidden"/>
 <input name="openOrderCurrencyId" id="openOrderCurrencyId" value="${openOrdersCurrentyId}" type="hidden"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>
