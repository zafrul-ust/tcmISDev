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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogaddtrackingresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<title>
    <fmt:message key="catalogAddTracking.title"/>
</title>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

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
engineeringevaluation:"<fmt:message key="buyorders.legend.engineeringevaluation"/>",
viewEval:"<fmt:message key="label.vieweval"/>",
approvalDetail:"<fmt:message key="label.approvaldetail"/>",
viewApproveCatAdd:"<fmt:message key="label.viewapprovecatadd"/>",
cataddreq:"<fmt:message key="label.cataddreq"/>",
resubmit:"<fmt:message key="label.resubmit"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<c:set var="showMaterialNetWt" value="${tcmis:isFeatureReleased(personnelBean,'ShowMaterialNetWt','ALL')}"/>
var qualityIdLen = 10;
var catPartAttrLen = 10;
var qualityIdLabelColumnHeader = '${qualityIdLabelColumnHeader}';
var catPartAttrColumnHeader = '${catPartAttrColumnHeader}';

// set hidden later is more troublesome.
if( '--Hide--' ==	qualityIdLabelColumnHeader )	
	qualityIdLen = 0;
if( '--Hide--' ==	catPartAttrColumnHeader )	
	catPartAttrLen = 0;
	
var config = [
{ columnId:"displayRequest",
  columnName:'<fmt:message key="label.request"/>'
},
{ columnId:"requestStatusDescJsp",
  columnName:'<fmt:message key="label.status"/>',
  width:12
},
{ columnId:"name",
  columnName:'<fmt:message key="label.requestor"/>'
},
{ columnId:"submitDate",
  columnName:'<fmt:message key="label.submitted"/>',
  hiddenSortingColumn:"hsubmitDate",
  width:8,
  sorting:"int"
},
{ columnId:"hsubmitDate",
  sorting:"int"
},
{ columnId:"approvalRequestedJsp",
  columnName:'<fmt:message key="label.approvalrequested"/>'
},
{ columnId:"approvalRole",
  columnName:'<c:set var="approvalRole"><fmt:message key="label.approvalRole"/></c:set><tcmis:jsReplace value="${approvalRole}"/>'
},
{ columnId:"agingTime",
  columnName:'<fmt:message key="label.durationbydays"/>'
},        
{ columnId:"facilityName",
  columnName:'<fmt:message key="label.facility"/>'
},
{ columnId:"workArea",
  columnName:'<fmt:message key="label.workarea"/>'
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partno"/>',
  width:12
},
{ columnId:"itemId",
  columnName:'<c:set var="approvalRole"><fmt:message key="label.itemid"/></c:set><tcmis:jsReplace value="${approvalRole}"/>'
  ,width:5
},
{ columnId:"customerMixtureNumber",
  <c:if test="${param.isCompanyUsesCustomerMSDS == 'Y'}">
    columnName:'<fmt:message key="label.kitmsds"/>',
  </c:if>
  width:5
},
{ columnId:"materialDesc",
  columnName:'<fmt:message key="label.materialdesc"/>',
  width:15,
  tooltip:"Y"
},
{ columnId:"manufacturer",
  columnName:'<fmt:message key="label.manufacturer"/>',
  width:12	
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  width:12
},
{ columnId:"netAmount",
  <c:if test="${showMaterialNetWt}">
    columnName:'<fmt:message key="label.netamount"/>',
  </c:if>
  width: 7,
  submit:true
},
{ columnId:"mfgCatalogId",
  columnName:'<fmt:message key="label.mfgpartno"/>',
  width:12	
},
{ columnId:"customerRequest",
  columnName:'<fmt:message key="label.customerrequest"/>'
},
{ columnId:"requestStatus"
},
{ columnId:"engEval"
},
{ columnId:"startingView"
},
{ columnId:"requestId"
},
{ columnId:"companyId"
},
{ columnId:"customerMsdsNumber",
  <c:if test="${param.isCompanyUsesCustomerMSDS == 'Y'}">
    columnName:'<fmt:message key="label.msds"/>',
  </c:if>
  width:5
},
{
	columnId:"startingViewDesc"
},
{
	columnId:"approvalRequested"
},
{ columnId:"qualityId",
	  columnName:qualityIdLabelColumnHeader,
	  width:qualityIdLen
},
{ columnId:"catPartAttribute",
	  columnName:catPartAttrColumnHeader,
	  width:catPartAttrLen
}        	
];

with(milonic=new menuname("catAddTrackingMenu")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	  aI("text=<fmt:message key="catalogAddTracking.title"/>;url=javascript:doNothing();");
}

drawMenus();

var map = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
var lineIdMap = new Array();

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/catalogaddtrackingresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="newChemTrackingViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${newChemTrackingCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty newChemTrackingCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="newChemTracking" items="${newChemTrackingCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtSubmitDate" value="${status.current.submitDate}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="fmtApprovalRequestDate" value="${status.current.approvalGroupSeqStartTime}" pattern="${dateTimeFormatPattern}"/>
<c:set var="submitDateSortable" value="${status.current.submitDate.time}"/>

<tcmis:jsReplace var="requestorName" value="${status.current.name}" />
<tcmis:jsReplace var="materialDesc" value="${status.current.materialDesc}" processMultiLines="true" />
<tcmis:jsReplace var="packaging" value="${status.current.packaging}" processMultiLines="true" />
<tcmis:jsReplace var="catPartNo" value="${status.current.catPartNo}" />
<tcmis:jsReplace var="facilityName" value="${status.current.facilityName}" processMultiLines="true"/>
<tcmis:jsReplace var="workAreaList" value="${status.current.workAreaList}" processMultiLines="true"/>
<tcmis:jsReplace var="manufacturer" value="${status.current.manufacturer}" processMultiLines="true" />
<tcmis:jsReplace var="mfgCatalogId" value="${status.current.mfgCatalogId}" processMultiLines="true"/>
<c:set var="approvalList"><c:out value="${status.current.approvalList}"></c:out></c:set>
<c:set var="startingViewDescJsp" value=""/>
<c:if test="${fn:length(status.current.startingViewDescJsp) > 0}"><c:set var="startingViewDescJsp"><fmt:message key="${status.current.startingViewDescJsp}"/></c:set></c:if>
<c:set var="requestStatusDescJsp" value=""/>
<c:if test="${fn:length(status.current.requestStatusDescJsp) > 0}"><c:set var="requestStatusDescJsp"><fmt:message key="${status.current.requestStatusDescJsp}"/></c:set></c:if>

<c:set var="materialNetAmount" value=""/>
<c:if test="${!empty status.current.netwt && !empty status.current.netwtUnit }">
     <c:set var="materialNetAmount">${status.current.netwt} ${status.current.netwtUnit}</c:set>
</c:if>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[
 '${status.current.requestId} (<tcmis:jsReplace value="${startingViewDescJsp}" processMultiLines="false" />)',
 '<tcmis:jsReplace value="${requestStatusDescJsp}" processMultiLines="false" />',
 '${requestorName}','${fmtSubmitDate}','${submitDateSortable}',
 '${fmtApprovalRequestDate}','<tcmis:jsReplace value="${approvalList}"/>','${status.current.agingTime}',
 '${facilityName}','${workAreaList}',
 '${catPartNo}','${status.current.itemId}',
 '${status.current.customerMixtureNumber}',
 '${materialDesc}','${manufacturer}','${packaging}',
 '${materialNetAmount}',        
 '${mfgCatalogId}',
 '${status.current.customerRequestId}',
 '${status.current.requestStatus}','${status.current.engineeringEvaluation}',
 '${status.current.startingView}','${status.current.requestId}','${status.current.companyId}',
 '${status.current.customerMsdsNumber}',
 '${status.current.startingViewDesc}',
 '${status.current.requestStatusDesc}',
 '${status.current.qualityId}',
 '${status.current.catPartAttribute}'
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty newChemTrackingCollection}">
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
	<input name="creditHoldCount" id="creditHoldCount" value="<c:out value="${creditHoldCount}"/>" type="hidden">
	<input name="action" id="action" value="" type="hidden"/>   
	<input name="minHeight" id="minHeight" type="hidden" value="100"> 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

<c:set var="prePar" value=""/>
<c:set var="parCount" value="1"/>
<c:forEach var="newChemTracking" items="${newChemTrackingCollection}" varStatus="status">
	<c:set var="curPar">${status.current.requestId}</c:set>
	<c:set var="curItem" value="lineItem${status.current.lineItem}"/>

	<script language="JavaScript" type="text/javascript">
	<!--
		<c:if test="${!(curPar eq prePar)}">
			<c:set var="parCount" value="${parCount+1}"/>
			<c:set var="preItem" value=""/>
			lineMap[${status.index}] = ${rowCountRequest[curPar]} ; 
			map = new Array();
		</c:if>
		<c:if test="${ !( curItem eq preItem)}">
			<c:set var="componentSize" value='${rowCountLineItem[curPar][curItem]}' />
			lineMap2[${status.index}] = ${componentSize} ; 
		</c:if>
		lineMap3[${status.index}] = ${parCount}%2 ;
		map[map.length] =  ${status.index} ;
		lineIdMap[${status.index}] = map;
	// -->
	</script>

	<c:set var="prePar" value="${status.current.requestId}"/>
	<c:set var="preItem" value="lineItem${status.current.lineItem}"/>
</c:forEach>

</body>
</html:html>