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
<script type="text/javascript" src="/js/report/openpicksresults.js"></script>

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
    <fmt:message key="openpicks.title"/>
</title>
<script language="JavaScript" type="text/javascript"><!--

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
{ columnId:"opsEntityId",	
  columnName:'<fmt:message key="label.operatingentity"/>'
},
{ columnId:"hub",
  columnName:'<fmt:message key="label.hub"/>'
},
{ columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'
},
{ columnId:"picklistId",
  columnName:'<fmt:message key="label.picklistid"/>'
},
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customer"/>'
},
{ columnId:"shipToLocationDesc",
  columnName:'<fmt:message key="label.shipto"/>',
  width:12,
  tooltip:"Y"	  
},
{ columnId:"customerPoLine",
  columnName:'<fmt:message key="label.customerpo"/>'
},
{ columnId:"salesOrder",
  columnName:'<fmt:message key="label.salesorder"/>'
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnum"/>'
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:"Y",
  width:12
},
{ columnId:"pickQty",
  columnName:'<fmt:message key="label.quantity"/>',
  sorting:"int",
  width:5
},
{ columnId:"unitPrice",
  columnName:'<fmt:message key="label.priceeach"/>',
  hiddenSortingColumn:"hunitPrice",
  sorting:"int",
  width:10
},
{ columnId:"picklistPrintDate",
  columnName:'<fmt:message key="label.dateprinted"/>',
  hiddenSortingColumn:"hpicklistPrintDate",
  sorting:"int",
  width:7
},
{ columnId:"hpicklistPrintDate",
  sorting:"int"
},
{ columnId:"value",
  columnName:'<fmt:message key="label.value"/>',
  hiddenSortingColumn:"hvalue",
  sorting:"int",
  width:10
},
{ columnId:"daysOld",
  columnName:'<fmt:message key="label.daysold"/>',
  sorting:"int",
  width:6
},
{ columnId:"supplierDateAccepted",
  columnName:'<fmt:message key="label.supplierdateaccepted"/>',
  hiddenSortingColumn:'hiddenSupplierDateAccepted',
  sorting:"int",
  width:6
},
{ columnId:"needDate",
  columnName:'<fmt:message key="label.needdate"/>',
  hiddenSortingColumn:'hiddenNeedDate',
  sorting:"int",
  width:6
},
{columnId:"hiddenSupplierDateAccepted", sorting:'int' },
{columnId:"hiddenNeedDate", sorting:'int' },
{ columnId:"hunitPrice",
  sorting:"int"
},
{ columnId:"hvalue",
  sorting:"int"
}

]; 		
/*
function setTotal() {
	  if( typeof( jsonMainData ) == 'undefined' ) return; 
	  var rows = jsonMainData.rows;
	  var extPriceTotal = 0 ;	 
	  var SSplit = 15;
	  var currencyId = "${openPicksColl[0].currencyId}";
	  for( var i = 0; i < rows.length; i++ ) {
		startsplit = SSplit;
		extPriceTotal = extPriceTotal*1 + rows[i].data[startsplit++]*1; 
		
	  }
	  startsplit = SSplit;
	  config[startsplit-1].columnName = currencyId+" "+Math.round(extPriceTotal*100)/100; 	 
	}  */
//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/openpicksresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="openPickList" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${openPicksColl != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty openPicksColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="openPicks" items="${openPicksColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtPicklistPrintDate" value="${status.current.picklistPrintDate}" pattern="${dateFormatPattern}"/>
<c:set var="picklistPrintDateSortable" value="${status.current.picklistPrintDate.time}"/>

<tcmis:jsReplace var="partDescription" value="${status.current.partDescription}"  processMultiLines="true"/>


<c:choose>
   <c:when test="${status.current.unitPrice eq null}">
     <c:set var="currencyId1" value='' />
   </c:when>
   <c:otherwise>
     <c:set var="currencyId1" value='${status.current.currencyId}' />
   </c:otherwise>
</c:choose>
<fmt:formatNumber var="unitPrice" value="${status.current.unitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>

<c:choose>
   <c:when test="${status.current.extPrice eq null}">
     <c:set var="currencyId2" value='' />
   </c:when>
   <c:otherwise>
     <c:set var="currencyId2" value='${status.current.currencyId}' />
   </c:otherwise>
</c:choose>
<fmt:formatNumber var="extPrice" value="${status.current.extPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

<c:choose>
<c:when test="${status.current.needDate eq null}">
  <c:set var="fmtNeedByPrefix" value='' />
</c:when>
<c:otherwise>
  <c:set var="fmtNeedByPrefix" value='${status.current.needByPrefix}' />
</c:otherwise>
</c:choose>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${status.current.operatingEntityName}','${status.current.hubName}','${status.current.inventoryGroupName}',
 '${status.current.picklistId}','<tcmis:jsReplace value="${status.current.customerName}"/>','<tcmis:jsReplace value="${status.current.shipToLocationDesc}"  processMultiLines="true"/>',
 '${status.current.customerPoLine}','${status.current.salesOrder}','${status.current.catPartNo}',
 '${partDescription}','${status.current.pickQty}','${currencyId1} ${unitPrice}',
 '${fmtPicklistPrintDate}','${picklistPrintDateSortable}','${currencyId2} ${extPrice}',
 '${status.current.daysOld}', 
 '<fmt:formatDate value="${status.current.supplierDateAccepted}" pattern="${dateFormatPattern}"/>',
 '${fmtNeedByPrefix} <fmt:formatDate value="${status.current.needDate}" pattern="${dateFormatPattern}"/>',
 '${status.current.supplierDateAccepted.time}',
 '${status.current.needDate.time}', 
 ' ${status.current.unitPrice}','${status.current.extPrice}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty openPicksColl}">
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
        <input name="action" id="action" value="" type="hidden">     
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