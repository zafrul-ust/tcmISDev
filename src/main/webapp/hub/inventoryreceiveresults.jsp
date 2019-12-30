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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/inventoryreceive.js"></script>

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
    <fmt:message key="label.customerinventorytoreceive"/>
</title>


<script language="JavaScript" type="text/javascript"><!--

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
qty:"<fmt:message key="label.qtylessqtyreceived"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{
  columnId:"permission"
},
{
  columnId:"qtyPermission"
},
{
  columnId:"notesPermission"
},
{ columnId:"customerPoLine",
  columnName:'<fmt:message key="label.ponumberpoline"/>',
  width:10
},
{ columnId:"ownerCompanyId",
  columnName:'<fmt:message key="label.company"/>',
  width:8
},
{ columnId:"catalogId",
  columnName:'<fmt:message key="label.catalog"/>',
  width:8
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnum"/>',
  width:12
},
{ 
  columnId:"itemId",
  columnName:'<fmt:message key="label.itemid"/>',
  width:8
},
{ 
  columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdesc"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  tooltip:"Y",
  width:12
},
{ 
  columnId:"supplierName",
  columnName:'<fmt:message key="label.supplier"/>',
  tooltip:"Y",
  width:12
},
{ columnId:"inventoryGroup",
  columnName:'<fmt:message key="label.inventorygroup"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"expectedDeliveryDate",
  columnName:'<fmt:message key="label.expecteddeliverydate"/>',
  type:'hcal',
  hiddenSortingColumn:"hexpecteddeliverydate",
  sorting:"int",
  width:7
},
{ columnId:"hexpecteddeliverydate",
  sorting:"int"
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  width:4,
  align:'center',
  type:'hch'
}, 
{ columnId:"quantityToReceive",
  columnName:'<fmt:message key="label.quantity"/>',
  type:'hed',
  width:5
},
{ columnId:"ownerSegmentId",
  columnName:'<fmt:message key="label.ownersegmentid"/>',
  type:'hcoro',
  width:5
},
{ columnId:"accountNumber",
  columnName:'<fmt:message key="label.chargenumber"/>',
  type:'hed',
  width:5
},
{ columnId:"accountNumber2",
  columnName:'<fmt:message key="label.chargenumber2"/>',
  type:'hed',
  width:5
},
{ columnId:"accountNumber3",
  columnName:'<fmt:message key="label.chargenumber3"/>',
  type:'hed',
  width:5
},
{ columnId:"accountNumber4",
  columnName:'<fmt:message key="label.chargenumber4"/>',
  type:'hed',
  width:5
},
{ columnId:"customerReceiptId",
  columnName:'<fmt:message key="label.customerreceiptid"/>',
  type:'hed',
  width:5
},
{ columnId:"qualityTrackingNumber",
  columnName:'<fmt:message key="label.qualityTrackingNumber"/>',
  type:'hed',
  width:5
},
{ columnId:"totalQuantityReceived",
  columnName:'<fmt:message key="label.quantityreceived"/>',
  sorting:"int",
  width:5
},
{ 
  columnId:"unitOfSale",
  columnName:'<fmt:message key="label.unitofsale"/>',
  width:5
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>',
  type:"txt",
  tooltip:"Y",
  width:20
},
{ columnId:"enteredByName",
  columnName:'<fmt:message key="label.enteredby"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"dateInserted",
  columnName:'<fmt:message key="label.entereddate"/>',
  hiddenSortingColumn:"hentereddate",
  sorting:"int",
  width:7
},
{ columnId:"hentereddate",
  sorting:"int"
},
{ columnId:"updatedByName",
  columnName:'<fmt:message key="label.updatedby"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"dateLastUpdated",
  columnName:'<fmt:message key="label.updateddate"/>',
  hiddenSortingColumn:"hupdated",
  sorting:"int",
  width:7
},
{ columnId:"hupdated",
  sorting:"int"
},
{ 
  columnId:"poNumber"
},
{ 
  columnId:"docNum"
},
{ 
  columnId:"customerPoNo"
}
,
{ 
  columnId:"partGroupNo"
}
]; 		

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/inventoryreceiveresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="inventoryToReceiveBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="showUpdate" value=''/>
<c:if test="${CustomerInventoryToReceiveBeanColl != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:set var="showUpdate" value=''/>
<c:if test="${!empty CustomerInventoryToReceiveBeanColl}">
var ownerSegmentId = new Array();
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${CustomerInventoryToReceiveBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtExpectedDeliveryDate" value="${status.current.expectedDeliveryDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtEnterDate" value="${status.current.dateInserted}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtUpdateDate" value="${status.current.dateLastUpdated}" pattern="${dateFormatPattern}"/>
<c:set var="dateInsertedTime" value="${status.current.dateInserted.time}"/>
<c:set var="dateLastUpdatedTime" value="${status.current.dateLastUpdated.time}"/>
<c:set var="expectedDeliveryTime" value="${status.current.expectedDeliveryDate.time}"/>
<tcmis:jsReplace var="notes" value="${status.current.notes}"  processMultiLines="true"/>
<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}"  processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${status.current.packaging}"  processMultiLines="true"/>

<c:set var="readonly" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="custInvReceiving" inventoryGroup="${status.current.inventoryGroup}" companyId="${personnelBean.companyId}">
    <c:choose>
        <c:when test="${status.current.automatedPutaway == 'Y' && (status.current.dataTransferStatus == 'CLOSED' ||
                        status.current.dataTransferStatus == 'HISTORY' || status.current.dataTransferStatus == 'ARCHIVE')}">
            <c:set var="readonly" value='N'/>
        </c:when>
        <c:otherwise>
            <c:set var="readonly" value='Y'/>
        </c:otherwise>
    </c:choose>
    <c:set var="showUpdate" value='Y'/>
</tcmis:inventoryGroupPermission>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${readonly}','','','${status.current.poNumber}','${status.current.ownerCompanyId}','${status.current.catalogId}',
 '${status.current.catPartNo}','${status.current.itemId}','${itemDesc}','${packaging}','${status.current.supplierName}','${status.current.inventoryGroup}',
 '${fmtExpectedDeliveryDate}','${expectedDeliveryTime}','','${status.current.quantityToReceive}',
 '${status.current.ownerSegmentId}','${status.current.accountNumber}','${status.current.accountNumber2}',
 '${status.current.accountNumber3}','${status.current.accountNumber4}','${status.current.customerReceiptId}','${status.current.qualityTrackingNumber}',
 '${status.current.totalQuantityReceived}','${status.current.unitOfSale}',
 '${notes}','${status.current.enteredByName}','${fmtEnterDate}','${dateInsertedTime}','${status.current.updatedByName}','${fmtUpdateDate}','${dateLastUpdatedTime}','${poNumber}',
 '${status.current.docNum}','${status.current.customerPoNo}','${status.current.partGroupNo}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

<%-- start of owner segment collection--%>
<c:forEach var="bean" items="${CustomerInventoryToReceiveBeanColl}" varStatus="status">
   ownerSegmentId['${status.index +1}']=[
    {
        text:'<fmt:message key="label.select"/>',
        value:''
    }
    <c:forEach var="ownerSegmentBean" items="${bean.ownerSegmentColl}" varStatus="status2">
       ,
       {
           text:'<tcmis:jsReplace value="${ownerSegmentBean.ownerSegmentDesc}"/>',
           value:'<tcmis:jsReplace value="${ownerSegmentBean.ownerSegmentId}"/>'
        }
    </c:forEach>
    ];
</c:forEach>
    <%-- end of owner segment collection --%>

</c:if>
//-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->

<c:if test="${empty CustomerInventoryToReceiveBeanColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
 <!-- Search results end --> 
 
<tcmis:inventoryGroupPermission indicator="true" userGroupId="custInvReceiving"  facilityId="${param.sourceHubName}" companyId="${personnelBean.companyId}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</tcmis:inventoryGroupPermission>


  <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="uAction" id="uAction" value="" type="hidden">     
        <input name="minHeight" id="minHeight" type="hidden" value="100"> 
        <input name="opsEntityId" id="opsEntityId" type="hidden" value='<tcmis:jsReplace value="${param.opsEntityId}"/>'/>
	    <input name="hub" id="hub" type="hidden" value='<tcmis:jsReplace value="${param.hub}"/>'/>
	    <input name="inventoryGroup" id="inventoryGroup" type="hidden" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
	    <input name="sourceHubName" id="sourceHubName" type="hidden" value='<tcmis:jsReplace value="${param.sourceHubName}"/>'/>
	    <input name="searchField" id="searchField" type="hidden" value='<tcmis:jsReplace value="${param.searchField}"/>'/>
	    <input name="searchMode" id="searchMode" type="hidden" value='<tcmis:jsReplace value="${param.searchMode}"/>'/>
	    <input name="searchArgument" id="searchArgument" type="hidden" value='<tcmis:jsReplace value="${param.searchArgument}"/>'/>
	    <input name="showAvailable" id="showAvailable" type="hidden" value='<tcmis:jsReplace value="${param.showAvailable}"/>'/>
	    <input name="expDeliveryFromDate" id="expDeliveryFromDate" type="hidden" value='<tcmis:jsReplace value="${param.expDeliveryFromDate}"/>'/>
	    <input name="expDeliveryToDate" id="expDeliveryToDate" type="hidden" value='<tcmis:jsReplace value="${param.expDeliveryToDate}"/>'/>
	    <input type="hidden" name="blockBefore_expectedDeliveryDate" id="blockBefore_expectedDeliveryDate" value=""/>
	    <input type="hidden" name="blockAfter_expectedDeliveryDate" id="blockAfter_expectedDeliveryDate" value=""/>
	    <input type="hidden" name="blockBeforeExclude_expectedDeliveryDate" id="blockBeforeExclude_expectedDeliveryDate" value=""/>
	    <input type="hidden" name="blockAfterExclude_expectedDeliveryDate" id="blockAfterExclude_expectedDeliveryDate" value=""/>
	   <input type="hidden" name="inDefinite_expectedDeliveryDate" id="inDefinite_expectedDeliveryDate" value=""/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>