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
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

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
<script type="text/javascript" src="/js/hub/picklistpickingresults.js"></script>

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
<fmt:message key="picklistpicking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
with(milonic=new menuname("picklistPickingMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 disabledItemNotes ? "" : aI("text=<fmt:message key="itemnotes.title"/>;url=javascript:showItemNotes();");
 aI("text=<fmt:message key="label.openmr"/> ;url=javascript:openMr();");
 aI("text=<fmt:message key="label.shipinfo"/>;url=javascript:showDOT();");
}

with(milonic=new menuname("openMrMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.openmr"/> ;url=javascript:openMr();");
 aI("text=<fmt:message key="label.shipinfo"/>;url=javascript:showDOT();");
}

with(milonic=new menuname("itemNotesMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="itemnotes.title"/>;url=javascript:showItemNotes();");
 aI("text=<fmt:message key="label.shipinfo"/>;url=javascript:showDOT();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
pleaseSelect:"<fmt:message key="error.norowselected"/>",
shipinfo: "<fmt:message key="label.shipinfo"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var disabledItemNotes = false;
<tcmis:featureReleased feature="DisabledItemNotes" scope="ALL"  companyId="${personnelBean.companyId}">
	disabledItemNotes = true;
</tcmis:featureReleased>

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
{ columnId:"permission",
  columnName:'',
  submit:false
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/><br><input type="checkbox" value="" onClick="return checkAll(\'ok\');" name="chkAllOk" id="chkAllOk">',
  type:"hchstatus",
  align:"center",
  width:4,
  submit:true
},
{ columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  tooltip:"Y"
},
{ columnId:"inventoryGroup"  
},
{ columnId:"facilityName",
  columnName:'<fmt:message key="label.facility"/>',
  width:8,
  tooltip:"Y",
  submit:false
},
{ columnId:"shipToLocationId",
  columnName:'<fmt:message key="label.shipto"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"requestor",
  columnName:'<fmt:message key="label.requestor"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"prNumberLineItem",
  columnName:'<fmt:message key="label.mrline"/>',
  submit:false,
  width:7
},
{ columnId:"releaseDate",
  columnName:'<fmt:message key="label.releasedate"/>',
  hiddenSortingColumn:"hReleaseDate",
  sorting:"int",
  width:7
},
{ columnId:"hReleaseDate",
  sorting:"int"
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnumber"/>',  
  tooltip:"Y"
},
{ columnId:"stockingMethod",
  columnName:'<fmt:message key="label.type"/>',
  width:3
},
{ columnId:"pickQty",
  columnName:'<fmt:message key="label.quantity"/>',
  sorting:"int",
  width:5
},
{ columnId:"extendedPrice",
  columnName:'<fmt:message key="label.extendedprice"/> (<fmt:message key="label.currency"/>)',
  align:"right",
  width:8
},
{ columnId:"DisplayNeedDate",
  columnName:'<fmt:message key="label.needed"/>',
  hiddenSortingColumn:"hNeedDate",  
  submit:false,
  sorting:"int",  
  width:8
},
{ columnId:"hNeedDate",
  sorting:"int"
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.partdescription"/>',
  width:18,
  tooltip:"Y"
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  width:12,
  tooltip:"Y"
},
  	{
		columnId:"customerNote"<%--,
		columnName:'<fmt:message key="label.billtonote"/>',
		tooltip:"Y"--%>
	},
	{
		columnId:"shiptoNote"<%--,
		columnName:'<fmt:message key="label.shiptonote"/>',
		tooltip:"Y"--%>
	},
	{
		columnId:"prInternalNote"<%--,
		columnName:'<fmt:message key="label.orderinternalnote"/>',
		tooltip:"Y"--%>
	},
	{
		columnId:"prExternalNote"<%--,
		columnName:'<fmt:message key="label.orderexternalnote"/>',
		tooltip:"Y"--%>
	},
	{
		columnId:"lineInternalNote"<%--,
		columnName:'<fmt:message key="label.internallinenote"/>',
		tooltip:"Y"--%>
	},
{ columnId:"csr",
  columnName:'<fmt:message key="label.csr"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"customer",
  columnName:'<fmt:message key="label.customer"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"abcClassification",
  columnName:'<fmt:message key="label.abcclassification"/>',
  tooltip:"Y"
},
{ columnId:"mrNotes",
  columnName:'<fmt:message key="label.externallinenote"/>',
  tooltip:"Y"
},
{ columnId:"companyId",
  columnName:'',
  submit:true	
},
{ columnId:"prNumber",
  columnName:'',
  submit:true
},
{ columnId:"lineItem",
  columnName:'',
  submit:true
},
{ columnId:"hub",
  columnName:'',
  submit:true
},
{ columnId:"needDate",
  columnName:'',   
  submit:true    
},
{ columnId:"application",
  columnName:'<fmt:message key="label.workarea"/>',
  width:8,
  tooltip:"Y",
  submit:false
},
{ columnId:"deliveryPoint",
  columnName:'<fmt:message key="label.deliverypoint"/>',
  width:7,
  tooltip:"Y",
  submit:false
},
{
 columnId:"facilityId",
 columnName:'',
 submit:true  
},
{
 columnId:"inventoryGroup",
 columnName:'',
 submit:true  
},
{
 columnId:"itemId",
 columnName:'',
 submit:true  
},
{ columnId:"dot",
  columnName:'<fmt:message key="label.dot"/>',
  width:20,
  tooltip:"Y",
  submit:false
},
{ 
  columnId:"materialRequestOrigin"
},
{ 
  columnId:"distribution"
}
];

// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();">

<tcmis:form action="/picklistpickingresults.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesAreaBody" style="display:none;">
   <html:errors/>
    ${tcmISError}
    <c:forEach var="errItem" items="${tcmISErrors}" varStatus="status">
  	${errItem}<br/>
    </c:forEach>       
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError }">
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

<div id="picklistViewBean" style="width:100%;height:400px;" style="display: none;"></div>


<c:if test="${picklistColl != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:set var="showGeneratePickList" value='N'/>
<c:if test="${!empty picklistColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="picklistItem" items="${picklistColl}" varStatus="status">
<c:set var="hasPermission" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Picking" inventoryGroup="${picklistItem.inventoryGroup}">
<c:set var="hasPermission" value='Y'/>
</tcmis:inventoryGroupPermission>

<c:if test="${hasPermission == 'Y' && (picklistItem.hazmatIdMissing == 'MISSING' || picklistItem.dot == 'Not Defined'
              || param.isWmsHub == 'Y') }">
      <c:set var="hasPermission" value='N'/>
</c:if>

<c:if test="${hasPermission == 'Y'}">
      <c:set var="showGeneratePickList" value='Y'/>
</c:if>
      
<c:choose>
<c:when test="${picklistItem.companyId == 'USGOV' && picklistItem.distribution != 'Y' && picklistItem.lineItem !='0'}">
</c:when>
<c:otherwise>

<c:if test="${dataCount> 0}">,</c:if>

<fmt:formatDate var="fmtReleaseDate" value="${picklistItem.releaseDate}" pattern="${dateFormatPattern}"/>
<c:set var="releaseDateSortable" value="${picklistItem.releaseDate.time}"/>

<fmt:formatDate var="fmtNeedDate" value="${picklistItem.needDate}" pattern="${dateFormatPattern}"/>
<c:set var="needDateSortable" value="${picklistItem.needDate.time}"/>

<tcmis:jsReplace var="partDescription" value="${picklistItem.partDescription}"  processMultiLines="true"/>
<tcmis:jsReplace var="packaging" value="${picklistItem.packaging}"  processMultiLines="true"/>
<tcmis:jsReplace var="mrNotes" value="${picklistItem.mrNotes}"  processMultiLines="true"/>
<tcmis:jsReplace var="customerName" value="${picklistItem.customerName}" processMultiLines="true" />
<tcmis:jsReplace var="facilityName" value="${picklistItem.facilityName}" processMultiLines="true" />
<tcmis:jsReplace var="requestorName" value="${picklistItem.requestor}"/>
<tcmis:jsReplace var="catPartNo" value="${picklistItem.catPartNo}" processMultiLines="false" />
<tcmis:jsReplace var="customerServiceRepName" value="${picklistItem.customerServiceRepName}" processMultiLines="false" />
<tcmis:jsReplace var="deliveryPoint" value="${picklistItem.deliveryPoint}" processMultiLines="false" />
<tcmis:jsReplace var="deliveryPointDesc" value="${picklistItem.deliveryPointDesc}" processMultiLines="false" />
<tcmis:jsReplace var="applicationDesc" value="${picklistItem.applicationDesc}" processMultiLines="false" />

<c:set var="extendedPrice" value=''/>
 <c:if test="${!empty picklistItem.extendedPrice}">
    <c:set var="extendedPrice"><fmt:formatNumber value='${picklistItem.extendedPrice}' type="NUMBER"  maxFractionDigits="2"/> (${picklistItem.currencyId})</c:set>
 </c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${dataCount+1},
	<c:if test="${picklistItem.critical eq 'Y' || picklistItem.critical eq 'y'}">'class':"grid_red",</c:if>
	<c:if test="${picklistItem.critical eq 'S'}">'class':"grid_pink",</c:if>
	<c:if test="${picklistItem.critical eq 'L'}">'class':"grid_pink",</c:if>
	<c:if test="${picklistItem.hazmatIdMissing eq 'MISSING' || picklistItem.dot == 'Not Defined'}">'class':"grid_orange",</c:if>	
	<c:if test="${picklistItem.ackSent !='Yes'}">'class':"grid_lightgray",</c:if>

 data:['${hasPermission}',
  '','${picklistItem.inventoryGroupName}', '${picklistItem.inventoryGroup}',
  '${facilityName}',
  <c:choose>
	<c:when test="${picklistItem.distribution == 'Y'}">
		'<tcmis:jsReplace value='${picklistItem.addressLine1Display}' processMultiLines="true"/> <tcmis:jsReplace value='${picklistItem.addressLine2Display}' processMultiLines="true"/> <tcmis:jsReplace value='${picklistItem.addressLine3Display}' processMultiLines="true"/> <tcmis:jsReplace value='${picklistItem.addressLine4Display}' processMultiLines="true"/> <tcmis:jsReplace value='${picklistItem.addressLine5Display}' processMultiLines="true"/>',
	</c:when>
    <c:when test="${picklistItem.lineItem == '0'}">
		'${picklistItem.destInventoryGroupName} - ${picklistItem.application}',
	</c:when>
    <c:otherwise>
		'${applicationDesc}',
	</c:otherwise>
  </c:choose>
 '${requestorName}',
 '${picklistItem.prNumber} - ${picklistItem.lineItem}','${fmtReleaseDate}','${releaseDateSortable}',
 '${catPartNo}','${picklistItem.stockingMethod}', '${picklistItem.pickQty}','${extendedPrice}',
 ' ${picklistItem.needDatePrefix}  ${fmtNeedDate}', '${needDateSortable}',
 '${partDescription}','${packaging}',
  '<tcmis:jsReplace value="${picklistItem.customerNote}" processMultiLines="true" />',
  '',
  '<tcmis:jsReplace value="${picklistItem.prInternalNote}" processMultiLines="true" />',
  '<tcmis:jsReplace value="" processMultiLines="true" />',
  '<tcmis:jsReplace value="${picklistItem.lineInternalNote}" processMultiLines="true" />',
 '${customerServiceRepName}','${customerName}','${picklistItem.abcClassification}','${mrNotes}','${picklistItem.companyId}','${picklistItem.prNumber}',
 '${picklistItem.lineItem}','${picklistItem.hub}','${fmtNeedDate}',
 <c:choose>
   <c:when test="${picklistItem.lineItem == '0'}">
		'${picklistItem.application}',
	</c:when>
    <c:otherwise>
		'${applicationDesc}',
	</c:otherwise>
  </c:choose>
  '${deliveryPointDesc}','${picklistItem.facilityId}',
 '${picklistItem.inventoryGroup}','${picklistItem.itemId}',
 '<tcmis:jsReplace value="${picklistItem.dot}" processMultiLines="true" />','${picklistItem.materialRequestOrigin}','${picklistItem.distribution}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:otherwise>
 </c:choose>       
 </c:forEach>
]};
</c:if>
//-->
</script>
<!-- If the collection is empty say no data found -->

<c:if test="${empty picklistColl}">
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
        <input name="action" id="action" value="" type="hidden"/>     
        <input name="minHeight" id="minHeight" type="hidden" value="100"/>	
        <%--<input name="packingGroupId" id="packingGroupId" type="hidden" value="${packingGroupId}"/>--%>        
        <input name="fromPickingPicklist" id="fromPickingPicklist" type="hidden" value="Y"/>
        <input name="picklistId" id="picklistId" type="hidden" value="${picklistId}"/>
        <input type="hidden" name="hub" id="hub" value='<tcmis:jsReplace value="${param.hub}"/>'/>
		<input type="hidden" name="prNumber" id="prNumber" value='<tcmis:jsReplace value="${param.prNumber}"/>'/>
		<input type="hidden" name="inventoryGroup" id="inventoryGroup" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
		<input type="hidden" name="opsEntityId" id="opsEntityId" value='<tcmis:jsReplace value="${param.opsEntityId}"/>'/>
		<input type="hidden" name="facilityId" id="facilityId" value='<tcmis:jsReplace value="${param.facilityId}"/>'/>
		<input type="hidden" name="expireDays" id="expireDays" value='<tcmis:jsReplace value="${param.expireDays}"/>'/>
		<input name="searchMode" id="searchMode" type="hidden" value='<tcmis:jsReplace value="${param.searchMode}"/>'/>
		<input name="searchField" id="searchField" type="hidden" value='<tcmis:jsReplace value="${param.searchField}"/>'/>
		<input name="searchArgument" id="searchArgument" type="hidden" value='<tcmis:jsReplace value="${param.searchArgument}"/>'/>
		<input name="customerServiceRepId" id="customerServiceRepId" type="hidden" value='<tcmis:jsReplace value="${param.customerServiceRepId}"/>'/>
		<input name="customerId" id="customerId" type="hidden" value='<tcmis:jsReplace value="${param.customerId}"/>'/>
        <input name="sortBy" id="sortBy" type="hidden" value='<tcmis:jsReplace value="${param.sortBy}"/>'/>
        <input name="pageid" id="pageid" type="hidden" value='<tcmis:jsReplace value="${param.pageid}"/>'/>
		<input name="companyId" id="companyId" type="hidden" value='<tcmis:jsReplace value="${sessionScope.personnelBean.companyId}"/>'/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<c:if test="${showGeneratePickList == 'Y'}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</c:if>

</tcmis:form>
</body>
</html:html>
