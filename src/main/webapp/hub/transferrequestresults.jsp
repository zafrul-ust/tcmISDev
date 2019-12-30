
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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


<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/transferrequestresults.js"></script>

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
   <fmt:message key="transferrequest.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            transferQuantityInteger:"<fmt:message key="error.quantity.integer"/>",
			noTransfer:"<fmt:message key="error.transfer.none"/>", itemIdInteger:"<fmt:message key="error.item.integer"/>",
			Quantitytosendnotgreaterthanavailable:"<fmt:message key="label.Quantitytosendnotgreaterthanavailable"/>",
			maximum4000:"<fmt:message key="label.maximum4000"/>",
            ok:"<fmt:message key="label.ok"/>"};


var searchConfig = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"transferDatePermission"
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>',
    sorting:'int',
    width:5
  },  
  {
  	columnId:"itemDesc",
  	columnName:'<fmt:message key="label.description"/>',
  	tooltip:"Y",
    width:10
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.packaging"/>',
  	tooltip:"Y",
    width:20
  },
  {
  	columnId:"customerpartnumber",
  	columnName:'<fmt:message key="label.customerpartnumber"/>',
  	tooltip:"Y",
    width:12
  },
  {
  	columnId:"fromInventoryGroup",
  	columnName:'<fmt:message key="label.frominventorygroup"/>',
  	tooltip:"Y",
    width:8
  },
  {
  	columnId:"ok",
  	columnName:'<fmt:message key="label.ok"/>',
  	type:'hchstatus',  // checkbox:The value is string "true" if checked
    align:'center',
    width:3
  },
  {
  	columnId:"quantity",
  	columnName:'<fmt:message key="label.quantityavailable"/>',
  	align:'right',
  	width:5
  },
  {
    columnId:"allocatable",
    columnName:'<fmt:message key="label.allocatable"/>',
  	align:'right',
  	width:6
  },
  {
  	columnId:"transferQuantity",
  	columnName:'<fmt:message key="label.quantitytosend"/>',
  	type:'hed',    // input field
  	align:'right',
  	sorting:'int',
  	width:5, // the width of this cell
  	size:4,  // the size of the input field
    	maxlength:5
  },
  {
  	columnId:"transferDate",
  	columnName:'<fmt:message key="label.datesent"/>',
  	type:'hcal',  // checkbox:The value is string "true" if checked
    align:'center',
    width:9
  },
  {
	columnId:"shippingReference",
	columnName:'<fmt:message key="label.shippingreferenece"/>',
	type:'hed',
  	width:10,
  	size:16
  },
  {
  	columnId:"comments",
  	columnName:'<fmt:message key="label.comments"/>',
  	type:"txt",     // single click on this field will show a textarea
  	tooltip:"Y",
  	width:20
  },
  {
    columnId:"inventoryGroup"
  },
  {
  	columnId:"destInventoryGroup"
  },
  {
  	columnId:"opsEntityId"
  },
  {
    columnId:"companyId"
  },
  {
  	columnId:"catalogCompanyId"
  },
  {
  	columnId:"catalogId"
  },
  {
  	columnId:"catPartNo"
  },
  {
  	columnId:"partGroupNo"
  },
  {
  	columnId:"destInvGroupName"
  }
];


            
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/transferrequestresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <c:if test="${! empty errorMsgCollection }" >
	  <c:forEach var="err" items="${errorMsgCollection}" varStatus="status">
		<c:set var="tcmISError" value='hasError'/>
	    ${err}<br>
	  </c:forEach><br>
	</c:if>
	
	<c:if test="${! empty errorCollection }" >
	  <c:forEach var="transferRequestInputBean" items="${errorCollection}" varStatus="status">
		<c:set var="tcmISError" value='hasError'/><br>
	    <fmt:message key="error.transferrequest.procerror"/>: <c:out value="${status.current.itemId}"/>/<c:out value="${status.current.inventoryGroup}"/>/<c:out value="${status.current.destInventoryGroup}"/><br>
	  </c:forEach><br>
	</c:if>  
	<c:if test="${!empty sucessfulTransferColl && doneTransfer == 'Y'}" >
	   <fmt:message key="transferrequest.successmessage"/>
	   <div id="successMessageArea" class="successMessages" style="width:700;height:250px;">
	   </div>
	</c:if>
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && doneTransfer != 'Y'}">
     showResults = false;
    </c:when>
    <c:otherwise>
     showResults = true;
    </c:otherwise>
   </c:choose>  
   <c:if test="${doneTransfer == 'Y' && !empty sucessfulTransferColl}" >
	   showGrid = true;
   </c:if> 
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="transferRequestInputBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty transferableInventoryViewBeanCollection}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="transferRequestInputBean" items="${transferableInventoryViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

/* Please use this tag, tcmis:jsReplace, to escape special characters */
<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}" processMultiLines="true" />
<tcmis:jsReplace var="packaging" value="${status.current.packaging}" processMultiLines="true" />
<tcmis:jsReplace var="inventoryGroupName" value="${status.current.inventoryGroupName}" processMultiLines="false" />
<tcmis:jsReplace var="destInvGroupName" value="${status.current.destInvGroupName}" processMultiLines="false" />
<tcmis:jsReplace var="distCustomerPartList" value="${status.current.distCustomerPartList}" processMultiLines="true" /> 
/* set page permission here */
<c:set var="hasPermission" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer" inventoryGroup="${status.current.inventoryGroup}">
	<c:set var="hasPermission" value='Y'/>
</tcmis:inventoryGroupPermission>

<c:set var="transferDate" value=''/>
<c:set var="showTransferDate" value='N'/>
<c:if test="${status.current.xferSourceOriginate == 'Y'}">
	<c:set var="showTransferDate" value='Y'/>
</c:if>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${hasPermission}','${showTransferDate}',
  '${status.current.itemId}',
  '${itemDesc}', '${packaging}','${distCustomerPartList}','${inventoryGroupName}',
  false,'${status.current.onHand}','${status.current.allocatable}','${status.current.transferQuantity}',
  '<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>','','',
  '${status.current.inventoryGroup}','${status.current.destInventoryGroup}','${status.current.opsEntityId}',
  '${status.current.companyId}','${status.current.catalogCompanyId}',
  '${status.current.catalogId}',
  '${status.current.catPartNo}',
  '${status.current.partGroupNo}','${destInvGroupName}'
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>


var tjsonMainData = new Array();
var tjsonMainData = {
rows:[
<c:forEach var="bean" items="${sucessfulTransferColl}" varStatus="status1">
<c:if test="${status1.index > 0}">,</c:if>

{ id:${status1.index +1},
 data:['${bean.transferRequestId}','${bean.itemId}','<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true" />',
  '<tcmis:jsReplace value="${bean.inventoryGroup}"/>','<tcmis:jsReplace value="${bean.destInvGroupName}"/>','${bean.transferQuantity}',
  '${bean.inventoryGroup}','${bean.opsEntityId}','${bean.companyId}'
   ]}  
 </c:forEach>
]};

<c:if test="${!empty sucessfulTransferColl}" >  

var tConfig = [
  {
  	columnId:"transferRequestId",
  	columnName:'<fmt:message key="label.transferrequestid"/>',
	width:6
  },
  {
  	columnId:"itemId",
  	columnName:'<fmt:message key="label.item"/>',
	width:4
  },
  {
  	columnId:"itemDesc",
  	columnName:'<fmt:message key="label.description"/>',
  	tooltip:"Y",
	width:10
  },
  {
  	columnId:"fromInventoryGroup",
  	columnName:'<fmt:message key="label.sourceig"/>',
  	tooltip:"Y",
	width:14
  },
  {
  	columnId:"destInvGroupName",
  	columnName:'<fmt:message key="label.destinationig"/>',
  	tooltip:"Y",
	width:14
  },
  {
  	columnId:"transferQuantity",
  	columnName:'<fmt:message key="label.qty"/>',
  	align:'right',
	width:5
  },
  {
  	columnId:"inventoryGroup"
  },
  {
  	columnId:"opsEntityId"
  },
  {
  	columnId:"companyId"
  }
];
</c:if>

//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty transferableInventoryViewBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
       <td width="100%">
          <fmt:message key="main.nodatafound"/>
       </td>
    </tr>
  </table>
</c:if>
        
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
    <input name="uAction" id="uAction" value="" type="hidden">     
    <input name="minHeight" id="minHeight" type="hidden" value="100">
      
  <!-- Put all the Search Criteria here for re-search after update-->
    <input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}">
    <input name="hub" id="hub" type="hidden" value="${param.hub}">
    <input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">
    <input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
    <input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
    <input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
    <input name="destHub" id="destHub" type="hidden" value="${param.destHub}">
	<input name="destInventoryGroup" id="destInventoryGroup" type="hidden" value="${param.destInventoryGroup}">									
	<input name="includeNoInventory" id="includeNoInventory" type="hidden" value="${param.includeNoInventory}">
	<input name="sourceInventoryGroup" id="sourceInventoryGroup" type="hidden" value="${sourceInventoryGroup}">
    <!-- Popup Calendar input options for revisedPromisedDate -->
	<input type="hidden" name="blockBefore_transferDate" id="blockBefore_transferDate" value=""/>
	<input type="hidden" name="blockAfter_transferDate" id="blockAfter_transferDate" value=""/>
	<input type="hidden" name="blockBeforeExclude_transferDate" id="blockBeforeExclude_transferDate" value=""/>
	<input type="hidden" name="blockAfterExclude_transferDate" id="blockAfterExclude_transferDate" value=""/>
	<input type="hidden" name="inDefinite_transferDate" id="inDefinite_transferDate" value=""/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:inventoryGroupPermission>

</tcmis:form>
</body>
</html:html>