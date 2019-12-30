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
<script type="text/javascript" src="/js/common/operations/ediorderstatus.js"></script>

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
    <fmt:message key="ediorderstatus.label.title"/>
</title>

    <c:set var="editShipTo" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditShipTo" facilityId="${param.companyId}">
      <c:set var="editShipTo" value="${true}"/>
    </tcmis:facilityPermission>

    <c:set var="editQuantity" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditQuantity" facilityId="${param.companyId}">
      <c:set var="editQuantity" value="${true}"/>
    </tcmis:facilityPermission>

    <c:set var="editPrice" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditPrice" facilityId="${param.companyId}">
      <c:set var="editPrice" value="${true}"/>
    </tcmis:facilityPermission>

<c:set var="checkBoxPermission" value='N'/>
<c:set var="showCheckBox" value='N'/>
<c:if test="${showUpdateLink == 'Y'}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    
    //-->
</script>
</c:if>
<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
open:"<fmt:message key="label.open"/>",
cancelorder:"<fmt:message key="label.cancelorder"/>",
partlist:"<fmt:message key="label.partlist"/>",
vmistockout:"<fmt:message key="label.vmistockout"/>",
dpmsaddress:"<fmt:message key="dpmsaddress.title"/>",
ignore:"<fmt:message key="button.ignore"/>",
ignorequestion:"<fmt:message key="label.ignore.question"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
quantity:"<fmt:message key="label.quantity"/>",
unitprice:"<fmt:message key="label.unitprice"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ columnId:"permission"
},
  {
  	columnId:"orderedQtyPermission"
  },
  {
  	columnId:"orderedUomPermission"
  },
  {
  	columnId:"currencyIdPermission"
  },
  {
  	columnId:"unitPriceOnOrderPermission"
  },
  {
  	columnId:"shiptoPartyIdPermission"
  },
  {
  	columnId:"okDoReset",
  	columnName:'<fmt:message key="label.reset"/><br><input type="checkbox" value="" onClick="return checkAll(\'okDoReset\');" name="chkAllReset" id="chkAllReset">',
  	type:'hchstatus',
//  	sorting:"haasHch",
    align:'center',
    width:4
  },
{ columnId:"dateIssued",
  columnName:'<fmt:message key="label.orderreceived"/>',
  hiddenSortingColumn:'hiddenDateIssued',
  width:7,
  sorting:'int'
},
{ columnId:"hiddenDateIssued",
  sorting:'int'
},
{ columnId:"customerPoNoDisplay",
  columnName:'<fmt:message key="label.po"/>',
  width:10
},
<c:if test='${param.companyId=="USGOV"}'>
{ columnId:"transactionRefNum",
  columnName:'<fmt:message key="label.transactionrefnum"/>',
  width:10
},
</c:if>
{ columnId:"customerPoLineNoTrim",
  columnName:'<fmt:message key="label.line"/>',
  width:4,
  sorting:'int'
},
{ columnId:"catPartNoOnOrder",
  columnName:'<fmt:message key="label.partnum"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"manufacturerPartNum",
  columnName:'<fmt:message key="label.mfg.part.num"/>',
  width:10
},
<c:if test='${param.companyId=="USGOV"}'>
{ columnId:"facilityId",
  columnName:'<fmt:message key="label.facility"/>',
  tooltip:"Y",
  width:10
},
</c:if>
{ columnId:"orderedQty",
  columnName:'<fmt:message key="label.quantity"/>',
  type:'hed',
  width:6,
  sorting:'int'
},
{ columnId:"orderedUom",
  columnName:'<fmt:message key="label.uom"/>',
  type:'hcoro',
  tooltip:"Y",
  width:5
},
{ columnId:"currencyId",
  columnName:'<fmt:message key="label.currency"/>', 
  type:'hcoro',
  width:5
},
{ columnId:"unitPriceOnOrder",
  columnName:'<fmt:message key="label.unitprice"/>',
  type:'hed',
  sorting:"int",
  width:6
},
{ columnId:"shiptoPartyId",
  columnName:'<fmt:message key="label.shipto"/>',
  type:'hcoro',
  width:6 
},
<c:if test='${param.companyId=="USGOV"}'>
{ columnId:"addressChangeRequestId",
  columnName:'<fmt:message key="label.addrchangerequestid"/>',
  width:6
},
{ columnId:"addressChangeTypeDisplay",
  columnName:'<fmt:message key="label.addrchangetype"/>',
  width:6
},
</c:if>
{ columnId:"requestedDeliveryDate",
  columnName:'<fmt:message key="label.needdate"/>',
  hiddenSortingColumn:'hiddenRequestedDeliveryDate',
  sorting:"int",
  width:7
},
{ columnId:"hiddenRequestedDeliveryDate"
},
{ columnId:"buyerNameOnPo",
  columnName:'<fmt:message key="label.requestor"/>',
  tooltip:"Y",
  width:7  
},
{ columnId:"currentOrderStatus",
  columnName:'<fmt:message key="label.status"/>',
  width:5
},
{ columnId:"errorDetail",
  columnName:'<fmt:message key="label.errorss"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"catalogUos",
  columnName:'<fmt:message key="label.tcm.uos"/>',
  width:3
},
{ columnId:"uosPerPackaging",
  columnName:'<fmt:message key="label.uos.per.ea"/>',
  sorting:"int",
  width:4
},
{ columnId:"mrQty",
  columnName:'<fmt:message key="label.mr.qty"/>',
  sorting:"int",
//  align:'right',
  width:4
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.tcm.packaging"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"customerPoLineNote",
  columnName:'<fmt:message key="label.line.note"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"customerPoNote",
  columnName:'<fmt:message key="label.po.note"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"transactionTypeDisplay",
  columnName:'<fmt:message key="label.ordertype"/>',
  tooltip:"Y",
  width:4
},
{ columnId:"companyId",
  columnName:'<fmt:message key="label.company"/>',
  tooltip:"Y",
  width:6
},
<c:if test='${param.companyId=="USGOV"}'>
{ columnId:"shipToDodaac",
  columnName:'<fmt:message key="label.shiptododaac"/>',
  width:8
},
{ columnId:"shipToAddress",
  columnName:'<fmt:message key="label.shiptoaddress"/>',
  width:8
},
{ columnId:"markForDodaac",
  columnName:'<fmt:message key="label.markfordodaac"/>',
  width:8
},
{ columnId:"markForAddress",
  columnName:'<fmt:message key="label.markforaddress"/>',
  width:8
},
</c:if>
{ columnId:"customerPoNo"
},
{ columnId:"companyId"
},
{ columnId:"transactionType"
},
{ columnId:"lineSequence"
},
{ columnId:"changeOrderSequence"
},
{ columnId:"customerPoLineNo"	 
},
{ columnId:"loadId"
},
{ columnId:"loadLine"	 
},
{ columnId:"unitPriceOrig"	 
},
{ columnId:"multiplePart"	 
},
{ columnId:"catPartNoOrig"	 
},
{ columnId:"catalogId"	 
},
{ columnId:"addressChangeAllowed"	 
},
{ columnId:"addressChangeType"	 
},
{ columnId:"oldShiptoPartyId"	 
},
{ columnId:"oldOrderedQty"	 
},
{ columnId:"oldOrderedUom"	 
}
];

with(milonic=new menuname("ediOrderStatusMenu")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.cancelorder"/>;url=javascript:showPartList();");
}

drawMenus();

var orderedUom = new  Array(
   <c:forEach var="validUOS" items="${validUOSBean}" varStatus="status">
   	 <c:if test="${status.index > 0}">,</c:if>
     	{text:'<c:out value="${validUOS.unitOfSale}"/>',value:'<c:out value="${validUOS.unitOfSale}"/>'}
   </c:forEach>
);

var currencyId = new  Array(
<%--	{text:'${ediOrderErrorViewBean.currencyId}',value:'${ediOrderErrorViewBean.currencyId}'}, --%> 
    <tcmis:isCNServer indicator="false">{text:'<fmt:message key="label.USD"/>',value:'USD'});</tcmis:isCNServer>
    <tcmis:isCNServer>{text:'<fmt:message key="label.CNY"/>',value:'CNY'});</tcmis:isCNServer>


<c:if test='${param.companyId!="IAI"}'> 
var shiptoPartyId = new Array(
   <c:forEach var="stlBean" items="${ShipToListBean}" varStatus="status">
     <c:if test="${status.index > 0}">,</c:if>
    	{text:'<c:out value="${stlBean.shiptoPartyId}"/>',value:'<c:out value="${stlBean.shiptoPartyId}"/>'}
   </c:forEach>
);
</c:if>
<c:if test='${param.companyId=="IAI"}'> 
var shiptoPartyId = new Array(
<%--   {text:'<c:out value="${ediOrderErrorViewBean.shiptoPartyId}"/>',value:'<c:out value="${ediOrderErrorViewBean.shiptoPartyId}"/>'}  --%> 
   <c:forEach var="stlBean" items="${ShipToListBean}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
		{text:'<c:out value="${stlBean.shiptoPartyId}"/>',value:'<c:out value="${stlBean.shiptoPartyId}"/>'}
<%--  TODO: build a new business rule here
	<c:if test="${(ediOrderErrorViewBean.shiptoPartyId != stlBean.shiptoPartyId) &&
				  ((ediOrderErrorViewBean.shiptoFirstLetter==stlBean.shiptoFirstLetter ) ||
				  (ediOrderErrorViewBean.shiptoFirstLetter=='X' && stlBean.shiptoFirstLetter=='L') ||
                  (ediOrderErrorViewBean.shiptoFirstLetter=='C' && stlBean.shiptoFirstLetter=='M'))}">
		,{text:'<c:out value="${stlBean.shiptoPartyId}"/>',value:'<c:out value="${stlBean.shiptoPartyId}"/>'}
	</c:if>  --%> 
   </c:forEach>
);
</c:if>

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/ediorderstatusresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="ediOrderErrorViewBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:if test="${ediOrderErrorViewBeanColl != null}">
<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty ediOrderErrorViewBeanColl}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="ediOrderErrorViewBean" items="${ediOrderErrorViewBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtIssueDate" value="${ediOrderErrorViewBean.dateIssued}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtReqDate" value="${ediOrderErrorViewBean.requestedDeliveryDate}" pattern="${dateFormatPattern}"/>

<fmt:formatNumber var="unitPriceOnOrder" value="${ediOrderErrorViewBean.unitPriceOnOrder}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<c:set var="addressChangeRequestId" value=''/>
<c:if test='${ediOrderErrorViewBean.addressChangeAllowed=="Y"}'>
	<c:set var="addressChangeRequestId" value='${ediOrderErrorViewBean.addressChangeRequestId}'/>
</c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['Y',
<c:choose>
  <c:when test="${empty ediOrderErrorViewBean.multiplePart && editQuantity}" >
    'Y',
  </c:when>
  <c:otherwise>
    'N',
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${empty ediOrderErrorViewBean.multiplePart && editQuantity}" >
    'Y',
  </c:when>
  <c:otherwise>
    'N',
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${editPrice && ediOrderErrorViewBean.currencyId!='USD'}" >
    'Y',
  </c:when>
  <c:otherwise>
    'N',
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${editPrice}" >
    'Y',
  </c:when>
  <c:otherwise>
    'N',
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${editShipTo && param.companyId!='USGOV'}" >
    'Y',
  </c:when>
  <c:otherwise>
    'N',
  </c:otherwise>
</c:choose>
  false,
  '${fmtIssueDate}','${ediOrderErrorViewBean.dateIssued.time}','${ediOrderErrorViewBean.customerPoNo}',
<c:choose>
  <c:when test='${param.companyId=="USGOV" && ediOrderErrorViewBean.facilityId=="DLA Gases" && ediOrderErrorViewBean.customerPoLineNoTrim=="1"}' >
    '${ediOrderErrorViewBean.transactionRefNum}',
  </c:when>
  <c:when test='${param.companyId=="USGOV"}' >
    '',
  </c:when>
  <c:otherwise>
  </c:otherwise>
</c:choose>
  '${ediOrderErrorViewBean.customerPoLineNoTrim}','<tcmis:jsReplace value="${ediOrderErrorViewBean.catPartNoOnOrder}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.manufacturerPartNum}" />',
<c:if test='${param.companyId=="USGOV"}'>
  '${ediOrderErrorViewBean.facilityId}',
</c:if>    
  '${ediOrderErrorViewBean.orderedQty}','${ediOrderErrorViewBean.orderedUom}',
  '${ediOrderErrorViewBean.currencyId}','${unitPriceOnOrder}','${ediOrderErrorViewBean.shiptoPartyId}',
<c:if test='${param.companyId=="USGOV"}'>
  '${addressChangeRequestId}',
<c:choose>
  <c:when test='${ediOrderErrorViewBean.addressChangeType=="dpms" && ediOrderErrorViewBean.sentToDpms=="N"}' >
    '<fmt:message key="label.verification"/>',
  </c:when>
  <c:otherwise>
    '${ediOrderErrorViewBean.addressChangeType}',
  </c:otherwise>
</c:choose>
</c:if>    
  '${fmtReqDate}','${ediOrderErrorViewBean.requestedDeliveryDate.time}',
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.buyerNameOnPo}" />','${ediOrderErrorViewBean.currentOrderStatus}',
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.errorDetail}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.catalogUos}" />','${ediOrderErrorViewBean.uosPerPackaging}',
  '${ediOrderErrorViewBean.mrQty}','<tcmis:jsReplace value="${ediOrderErrorViewBean.packaging}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.customerPoLineNote}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.customerPoNote}" processMultiLines="true"/>',
  '${ediOrderErrorViewBean.transactionTypeDisplay}','${ediOrderErrorViewBean.companyId}',
<c:if test='${param.companyId=="USGOV"}'>
  '${ediOrderErrorViewBean.shipToDodaac}','<tcmis:jsReplace value="${ediOrderErrorViewBean.shipToAddress}" processMultiLines="true"/>',
  '${ediOrderErrorViewBean.markForDodaac}','<tcmis:jsReplace value="${ediOrderErrorViewBean.markForAddress}" processMultiLines="true"/>',
</c:if>  
  '<tcmis:jsReplace value="${ediOrderErrorViewBean.customerPoNo}" />','${ediOrderErrorViewBean.companyId}',
  '${ediOrderErrorViewBean.transactionType}','${ediOrderErrorViewBean.lineSequence}',
  '${ediOrderErrorViewBean.changeOrderSequence}','<tcmis:jsReplace value="${ediOrderErrorViewBean.customerPoLineNo}" />',
  '${ediOrderErrorViewBean.loadId}','${ediOrderErrorViewBean.loadLine}','${ediOrderErrorViewBean.unitPriceOrig}',
  '${ediOrderErrorViewBean.multiplePart}','<tcmis:jsReplace value="${ediOrderErrorViewBean.catPartNoOrig}" />','${ediOrderErrorViewBean.catalogId}',
  '${ediOrderErrorViewBean.addressChangeAllowed}','${ediOrderErrorViewBean.addressChangeType}',
  '${ediOrderErrorViewBean.shiptoPartyId}','${ediOrderErrorViewBean.orderedQty}','${ediOrderErrorViewBean.orderedUom}']}   
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty ediOrderErrorViewBeanColl}">
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
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
	<input name="uAction" id="uAction" value="" type="hidden"/>   
	<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"> 
	<input name="searchField" id="searchField" type="hidden" value="${param.searchField}"> 
	<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}"> 
	<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}"> 
	<input name="selectedCustomerPoNo" id="selectedCustomerPoNo" type="hidden" value=""> 
	<input name="selectedCustomerPoLineNo" id="selectedCustomerPoLineNo" type="hidden" value=""> 
	<input name="selectedLoadId" id="selectedLoadId" type="hidden" value=""> 
	<input name="selectedLoadLine" id="selectedLoadLine" type="hidden" value=""> 
	<input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}">
	<input name="editShipTo" id="editShipTo" type="hidden" value="${editShipTo}">
	<input name="editPrice" id="editPrice" type="hidden" value="${editPrice}">
	<input name="minHeight" id="minHeight" type="hidden" value="100">
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>