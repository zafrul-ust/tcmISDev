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
<script type="text/javascript" src="/js/distribution/inventorylookup.js"></script>

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
    <fmt:message key="label.inventory"/>
</title>


<tcmis:inventoryGroupPermission indicator="true" userGroupId="releaseOrder">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission> 

<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryReconciliation">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="approveReconciliation">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="CSR">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</tcmis:inventoryGroupPermission>

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
returnreceipts:"<fmt:message key="label.returnreceipts"/>",
integer:"<fmt:message key="error.searchInput.integer"/>",
quantityshippednotgreaterthanquantity:"<fmt:message key="label.quantityshippednotgreaterthanquantity"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
<c:set var="expdate"><fmt:message key="label.expdate"/></c:set>
var config = [
  {
  	columnId:"permission"
  },
  {
   	columnId:"itemId",
    columnName:'<tcmis:jsReplace value="${itemid}"/>',
    width:5
  },
  {
   	columnId:"itemDesc",
    columnName:'<fmt:message key="label.description"/>',
    width:20
  },
  {
   	columnId:"inventoryGroupName",
    columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'
  },
  {
   	columnId:"receiptId",
    columnName:'<fmt:message key="label.receiptid"/>',
    width:6
  },
  {
   	columnId:"mfgLot",
    columnName:'<fmt:message key="label.lot"/>',
    width:4
  },
  {
   	columnId:"expireDateDisplay",
    columnName:'<tcmis:jsReplace value="${expdate}"/>',
    hiddenSortingColumn:'hiddenExpireDateTime',
    width:7
  },
  { 
    columnId:"hiddenExpireDateTime",
    sorting:'int'
  },
  {
   	columnId:"dateOfReceipt",
    columnName:'<fmt:message key="label.dor"/>',
    hiddenSortingColumn:'hiddenDateOfReceiptTime',
    sorting:'int',
    width:7
  },
  { 
    columnId:"hiddenDateOfReceiptTime",
    sorting:'int'
  },
  {
   	columnId:"bin",
    columnName:'<fmt:message key="label.bin"/>',
    width:4
  },
  {
   	columnId:"lotStatus",
    columnName:'<fmt:message key="label.status"/>',
    width:6
  },
  {
   	columnId:"quantity",
    columnName:'<fmt:message key="label.quantity"/>',
    align:'right',
    width:5
  },
  {
  	columnId:"ok",
  	columnName:'<fmt:message key="label.ok"/>',
  	type:'hchstatus',  
    align:'center',
    width:2
  },
  {
   	columnId:"qtyShipped",
    columnName:'<fmt:message key="label.quantityshipped"/>',
    type:'hed',
    size:3,
  	width:5
  },
  {
   	columnId:"expireDate"
  }
  ];

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/inventorylookupresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="logisticsViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${logisticsViewBeanCollection != null}">
<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty logisticsViewBeanCollection}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="logisticsViewBean" items="${logisticsViewBeanCollection}" varStatus="status">
<c:set var="showUpdateLink" value='N'/>

<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtExpireDateforServer" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>

<fmt:formatDate var="expireYear" value="${status.current.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>

<c:set var="hasPerm" value='N'/>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryReconciliation" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="releaseOrder" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="approveReconciliation" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="CSR" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPerm" value='Y'/>
</tcmis:inventoryGroupPermission>
                
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
/* Set the color to the rows according to their releaseStatus*/
	 data:['${hasPerm}',
	 '${status.current.itemId}','<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true" />',
	 '<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
	 '${status.current.receiptId}','${status.current.mfgLot}',
	 '${fmtExpireDate}','${status.current.expireDate.time}',
	 '${fmtDateOfReceipt}','${status.current.dateOfReceipt.time}',
	 '${status.current.bin}',
	 '${status.current.lotStatus}','${status.current.quantity}',false,'','${fmtExpireDateforServer}' ]}
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty logisticsViewBeanCollection}">
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
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
	<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
    <input type="hidden" name="hub" id="hub" value="${param.hub}"/>
    <input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}"/>
	<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
	<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
	<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
	<input type="hidden" name="searchField2" id="searchField2" value="${param.searchField2}"/>
	<input type="hidden" name="searchMode2" id="searchMode2" value="${param.searchMode2}"/>
	<input type="hidden" name="searchArgument2" id="searchArgument2" value="${param.searchArgument2}"/>
	<input type="hidden" name="showIssuedReceipts" id="showIssuedReceipts" value="${param.showIssuedReceipts}"/>
	<input type="hidden" name="searchField3" id="searchField3" value="${param.searchField3}"/>
	<input type="hidden" name="dorfrom" id="dorfrom" value="${param.dorfrom}"/>
	<input type="hidden" name="dorto" id="dorto" value="${param.dorto}"/>
	<input name="countId" id="countId" type="hidden" value="${param.countId}" />
	<input name="callAddReceipt" id="callAddReceipt" type="hidden" value="${param.callAddReceipt}" />
	<input name="successfullyAdded" id="successfullyAdded" type="hidden" value="${successfullyAdded}" />
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>