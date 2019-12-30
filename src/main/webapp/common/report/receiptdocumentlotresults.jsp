<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/receiptdocumentlotresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
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
    <fmt:message key="receiptdocumentviewer.title"/> 
</title>
<script language="JavaScript" type="text/javascript">
<!--

var altAccountSysId = new Array();
<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status">
    altAccountSysId['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
        <c:forEach items="${status.current.accountSysList}" varStatus="status1">
        <c:if test="${status1.index > 0}">,</c:if>
        {
            value:'<tcmis:jsReplace value="${status1.current.accountSysId}"/>',
            text:'<tcmis:jsReplace value="${status1.current.accountSysDesc}"/>'
        }
        </c:forEach>
    );
</c:forEach>


var altDocuments = new Array();
<c:forEach var="receipts" items="${receiptDocumentOvBeanCollection}" varStatus="rstatus">
	altDocuments[${rstatus.count}] = {
        	coll: [ <c:forEach var="document" items="${receipts.documents}" varStatus="dpstatus">
				{
				documentId:'<tcmis:jsReplace value="${document.documentId}"/>',
				receiptId:'<tcmis:jsReplace value="${document.receiptId}"/>',
				documentUrl:'<tcmis:jsReplace value="${document.documentUrl}"/>',
				documentName:'<tcmis:jsReplace value="${document.documentName}"/> -  <tcmis:jsReplace value="${document.documentType}"/> - <fmt:formatDate value="${document.documentDate}" pattern="${dateFormatPattern}"/>'
	    	    }<c:if test="${!dpstatus.last}">,</c:if>
			</c:forEach>
		]
        };
</c:forEach>

with(milonic=new menuname("rightClickMenu")){
 top="offset=2"
 style = contextWideStyle;
 margin=3
 //aI("text=<fmt:message key="label.print"/>;url=javascript:window.print();");	
 aI("text=test;");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
movetoanotherworkarea:"<fmt:message key="label.movetoanotherworkarea"/>",
view:"<fmt:message key="label.view"/>",
marsrequests:"<fmt:message key="label.showmarsrequests"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
receiptDocuments:"<fmt:message key="label.viewreceiptdocument"/>",
pickingDocuments:"<fmt:message key="label.viewpickingdocuments"/>"};

var gridConfig = {
	divName:'receiptDocumentLotBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	selectChild: 1,
	//onBeforeSelect:beforeSelectRow,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightclick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
  };

<c:set var="showDeliveriesCostData" value="N"/>
<c:if test="${deliveriesCostData}">
    <c:set var="showDeliveriesCostData" value="Y"/>
</c:if>

var config = [
{ columnId:"mfgLot",columnName:'<fmt:message key="label.lot"/>'},
{ columnId:"expireDate",columnName:'<fmt:message key="label.expiredate"/>'},
{ columnId:"itemId",columnName:'<fmt:message key="label.item"/>',width:7,align:"left"},
{ columnId:"itemDesc",columnName:'<fmt:message key="label.itemdesc"/>',width:17,tooltip:"Y"},
{ columnId:"receiptId",columnName:'<fmt:message key="label.receipt"/>',align:"left"},
{ columnId:"quantityIssued",columnName:'<fmt:message key="label.qty"/>',width:4},
{ columnId:"extendedPrice" <c:if test="${showDeliveriesCostData == 'Y'}">,columnName:'<fmt:message key="label.extendedprice"/>'</c:if>,align:"center",width:8},
{ columnId:"additionalCharges" <c:if test="${showDeliveriesCostData == 'Y'}">,columnName:'<fmt:message key="label.additional.charges"/>'</c:if>,align:"center",width:8},
{ columnId:"freightCharges" <c:if test="${showDeliveriesCostData == 'Y'}">,columnName:'<fmt:message key="label.freightcharge"/>'</c:if>,align:"center",width:8},
{ columnId:"applicationDesc",columnName:'<fmt:message key="label.workarea"/>',tooltip:"Y",align:"left",width:12},
{ columnId:"catalogDesc",columnName:'<fmt:message key="label.catalog"/>'},
{ columnId:"mrLine",columnName:'<fmt:message key="label.mrline"/>'},
{ columnId:"poLine" <c:if test="${param.poRequired == 'Y'}">,columnName:'<fmt:message key="label.podaskline"/>'</c:if>},
{ columnId:"dateDelivered",columnName:'<fmt:message key="label.delivered"/>'},
{ columnId:"mrLineQty",columnName:'<fmt:message key="label.mrquantity"/>',width:4},
{ columnId:"facPartNo",columnName:'<fmt:message key="label.partno"/>',width:9},
{ columnId:"partDescription",columnName:'<fmt:message key="label.partdesc"/>',width:17,tooltip:"Y"},
{ columnId:"shipmentId",columnName:'<fmt:message key="label.shipmentid"/>',width:8},
{ columnId:"confirmedBy",columnName:'<fmt:message key="label.confirmedby"/>',width:8},
{ columnId:"deliveryConfirmationDate",columnName:'<fmt:message key="label.dateconfirmed"/>'},
{ columnId:"deliveryComments",columnName:'<fmt:message key="label.comments"/>',width:15,tooltip:"Y"},
{ columnId:"facilityId"},
{ columnId:"application"},
{ columnId:"prNumber"},
{ columnId:"lineItem"},
{ columnId:"companyId"}
];	

var lineMap = new Array();
var lineMap3 = new Array();

<c:choose>
	<c:when test="${tcmis:userHasPagePermission(personnelBean,'marsRequestSearch')}">
		var marsMenuLine="text="+messagesData.marsrequests+";url=javascript:showMarsRequests();";
	</c:when>
	<c:otherwise>
		var marsMenuLine="text="+messagesData.marsrequests+";offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;";
	</c:otherwise>
</c:choose>
//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/receiptdocumentviewerresults.do" onsubmit="return submitFrameOnlyOnce();">
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
   <c:if test="${not empty MaxDataFilled || param.maxData == fn:length(receiptDocumentOvBeanCollection)}">
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
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
parent.messagesData.errors = "<fmt:message key="label.errors"/>";  
<c:if test="${not empty MaxDataFilled || param.maxData == fn:length(receiptDocumentOvBeanCollection)}">
	showErrorMessage = true;
	parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
</c:if>
//-->
</script>
<!-- Error Messages Ends -->  

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="receiptDocumentLotBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:choose>
	<c:when test="${empty receiptDocumentOvBeanCollection}">
		<%-- If the collection is empty say no data found --%> 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			<tr>
				<td width="100%"><fmt:message key="main.nodatafound" /></td>
			</tr>
		</table>
	</c:when> 
	<c:otherwise>
		<script type="text/javascript">
		<!--
		 <c:set var="dataCount" value='${0}'/>
		  <bean:size id="listSize" name="receiptDocumentOvBeanCollection"/>
		  <c:if test="${!empty receiptDocumentOvBeanCollection}" >
		  <c:set var="preMfgLot" value=''/>
			<c:set var="lotCount" value='0'/>
			<c:forEach var="tmpBean" items="${receiptDocumentOvBeanCollection}" varStatus="status">
			<c:choose>
				<c:when test="${tmpBean.mfgLot != preMfgLot}">
					lineMap[${status.index}] = 1;
					<c:set var="preMfgLot" value='${tmpBean.mfgLot}'/>
					<c:set var="lotCount" value="${lotCount + 1}"/>
					<c:set var="parent" value="${status.index}"/>
				</c:when>
				<c:otherwise>
					lineMap[${parent}]++;
				</c:otherwise>
			</c:choose>
				lineMap3[${status.index}] = ${lotCount % 2};
			</c:forEach>
		
		var jsonMainData = new Array();
		var jsonMainData = {
		rows:[
		<c:forEach var="lotColl" items="${receiptDocumentOvBeanCollection}" varStatus="status">
		
		<fmt:formatDate var="fmtDateDelivered" value="${lotColl.dateDelivered}" pattern="${dateFormatPattern}"/>
		<fmt:formatDate var="fmtExpireDate" value="${lotColl.expireDate}" pattern="${dateFormatPattern}"/>
		<fmt:formatDate var="expireYear" value="${lotColl.expireDate}" pattern="yyyy"/>
		<c:if test="${expireYear == '3000'}">
			<c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
		</c:if>
		<fmt:formatDate var="fmtDateConfirmed" value="${lotColl.deliveryConfirmationDate}" pattern="${dateFormatPattern}"/>
		<c:if test="${param.poRequired == 'Y'}">
			<c:set var="poLine" value="${status.current.poLine}"/>
			<c:if test="${lotColl.poLine eq '-'}"><c:set var="poLine" value=""/></c:if>
		</c:if>
		
	    <c:set var="extendedPriceDisplay" value="" />
       	<c:if test="${!empty lotColl.extendedPrice}">
        <fmt:formatNumber var="extendedPriceDisplay" value="${lotColl.extendedPrice}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
         <c:set var="extendedPriceDisplay" value="${extendedPriceDisplay} ${lotColl.currencyId}" />
        </c:if>
        <c:set var="addChargesDisplay" value="" />
       	<c:if test="${!empty lotColl.additionalCharges}">
        <fmt:formatNumber var="addChargesDisplay" value="${lotColl.additionalCharges}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
         <c:set var="addChargesDisplay" value="${addChargesDisplay} ${lotColl.currencyId}" />
        </c:if>
        <c:set var="freightChargesDisplay" value="" />
       	<c:if test="${!empty lotColl.freightCharges}">
        <fmt:formatNumber var="freightChargesDisplay" value="${lotColl.freightCharges}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
         <c:set var="freightChargesDisplay" value="${freightChargesDisplay} ${lotColl.currencyId}" />
        </c:if>
		
		 /*The row ID needs to start with 1 per their design.*/
		{ id:${status.index +1},
		 data:['${lotColl.mfgLot}',
		 '${fmtExpireDate}',
		 '${lotColl.itemId}',
		 '<tcmis:jsReplace value="${lotColl.itemDesc}" processMultiLines="true" />',
		 '${lotColl.receiptId}',
		 '${lotColl.quantityIssued}',
		  '${extendedPriceDisplay}',
		  '${addChargesDisplay}',
		  '${freightChargesDisplay}',
		 '<tcmis:jsReplace value="${lotColl.applicationDesc}" processMultiLines="true" />',
		 '<tcmis:jsReplace value="${lotColl.catalogDesc}" processMultiLines="true" />',
		 '${lotColl.mrLine}',
		 '${poLine}',
		 '${fmtDateDelivered}','${lotColl.mrLineQty}',
		 '<tcmis:jsReplace value="${lotColl.facPartNo}" />',
		 '<tcmis:jsReplace value="${lotColl.partDescription}" processMultiLines="true" />',
		 '${lotColl.shipmentId}',
		 '${lotColl.confirmedBy}',
		 '${fmtDateConfirmed}',
		 '<tcmis:jsReplace value="${lotColl.deliveryComments}" />',
		 '${lotColl.facilityId}',
		 '${lotColl.application}',
		 '${lotColl.prNumber}',
		 '${lotColl.lineItem}',
		 '${lotColl.companyId}'
		 ]}
		  <c:if test="${status.index+1 < listSize}">,</c:if>
					<c:set var="dataCount" value='${dataCount+1}'/>
				</c:forEach>
		]};
	</c:if>
	
	//-->
	</script>
  </c:otherwise>				
</c:choose>
  <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="accountSysId" id="accountSysId" value="" type="hidden">
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <input name="deliveriesCostData" id="deliveriesCostData" value="${showDeliveriesCostData}" type="hidden">
        <input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','',personnelBean.companyId)}'/>
        <tcmis:setHiddenFields beanName="inputBean"/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>