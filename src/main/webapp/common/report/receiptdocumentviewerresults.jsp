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
<script type="text/javascript" src="/js/common/report/receiptdocumentviewerresults.js"></script>

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
    <fmt:message key="receiptdocumentviewer.title"/> 
</title>
<script language="JavaScript" type="text/javascript">
<!--
var altAccountSysId = new Array(
<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		id:'<tcmis:jsReplace value="${status2.current.accountSysId}"/>',
		label:'<tcmis:jsReplace value="${status2.current.accountSysLabel}"/>'
	}
</c:forEach>
);



with(milonic=new menuname("allMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.movetoanotherworkarea"/>;url=javascript:moveToAnotherWorkArea();");	
       aI("text=<fmt:message key="label.viewreceiptdocument"/>;url=javascript:showReceiptDocument();");
}

with(milonic=new menuname("moveMaterialMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.movetoanotherworkarea"/>;url=javascript:moveToAnotherWorkArea();");
}

with(milonic=new menuname("showDocMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.viewreceiptdocument"/>;url=javascript:showReceiptDocument();");
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
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ columnId:"receiptIdDisplay",
  columnName:'<fmt:message key="label.receipt"/>',  
  sorting:"int",
  align:"left"
},
{ columnId:"applicationDesc",
  columnName:'<fmt:message key="label.workarea"/>', 
  tooltip:"Y", 
  align:"left",
  width:12
},
{ columnId:"mfgLot",
  columnName:'<fmt:message key="label.lot"/>'
},
{ columnId:"quantityIssued",
  columnName:'<fmt:message key="label.qty"/>',
  sorting:"int",
  width:4
},
{ columnId:"dateDelivered",
  columnName:'<fmt:message key="label.delivered"/>',
  hiddenSortingColumn:"hdateDelivered",
  sorting:"int"
},
{ columnId:"hdateDelivered",
  sorting:"int"
},
{ columnId:"expireDate",
  columnName:'<fmt:message key="label.expiredate"/>',
  hiddenSortingColumn:"hExpireDate",
  sorting:"int"
},
{ columnId:"hExpireDate",
  sorting:"int"
},
{ columnId:"mrLine",
  columnName:'<fmt:message key="label.mrline"/>'
},
{ columnId:"poLine",
  columnName:'<fmt:message key="label.podaskline"/>'
},
{ columnId:"catalogDesc",
  columnName:'<fmt:message key="label.catalog"/>'
},
{ columnId:"facPartNo",
  columnName:'<fmt:message key="label.partno"/>',
  width:9
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.partdesc"/>',
  width:17,
  tooltip:"Y"
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:7,
  align:"left",
  sorting:"int"
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdesc"/>',
  width:17,
  tooltip:"Y"
},
{ columnId:"hdocumentUrl"
},
{ columnId:"facilityId"
},
{ columnId:"application"
},
{ columnId:"receiptId"
},
{ columnId:"prNumber"
},
{ columnId:"lineItem"
}
];	

/*
with(milonic=new menuname("receiptDocumentMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.openmr"/>;url=javascript:openMr();");
}

drawMenus();	

function setHlink() {
	  if( typeof( jsonMainData ) == 'undefined' ) return; 
	 
	  var rows =jsonMainData.rows;
	  for( var i = 0; i < rows.length; i++ ) 
	 {
		if(rows[i].data[12]!='')
	   {	
	  	rows[i].data[0] ="<label class='pointer' style='color:blue;text-decoration:underline'>" + rows[i].data[0] + "</label>"; 
	   }					  
	 }	  
}  */
//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/receiptdocumentviewerresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="receiptDocumentBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${receiptDocumentViewerBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty receiptDocumentViewerBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="receiptDocumentViewerBeanCollection" items="${receiptDocumentViewerBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtDateDelivered" value="${status.current.dateDelivered}" pattern="${dateFormatPattern}"/>
<c:set var="dateDeliveredSortable" value="${status.current.dateDelivered.time}"/>
<fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${status.current.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="fmtExpireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>

<c:set var="receiptId">${status.current.receiptId} <img src="/images/buttons/document.gif"/></c:set>
<c:if test="${empty status.current.documentUrl}"><c:set var="receiptId" value="${status.current.receiptId}"/></c:if>

<c:set var="poLine" value="${status.current.poLine}"/>
<c:if test="${status.current.poLine eq '-'}"><c:set var="poLine" value=""/></c:if>

<c:set var="expireDateSortable" value="${status.current.expireDate.time}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${receiptId}','${status.current.applicationDesc}','${status.current.mfgLot}','${status.current.quantityIssued}',
 '${fmtDateDelivered}','${dateDeliveredSortable}','${fmtExpireDate}','${expireDateSortable}','${status.current.mrLine}',
 '${poLine}','<tcmis:jsReplace value="${status.current.catalogDesc}" processMultiLines="true" />',
 '<tcmis:jsReplace value="${status.current.facPartNo}" />',
 '<tcmis:jsReplace value="${status.current.partDescription}" processMultiLines="true" />','${status.current.itemId}',
 '<tcmis:jsReplace value="${status.current.itemDesc}" processMultiLines="true" />','${status.current.documentUrl}',
 '${status.current.facilityId}','${status.current.application}','${status.current.receiptId}',
 '${status.current.prNumber}','${status.current.lineItem}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty receiptDocumentViewerBeanCollection}">
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
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden">  
        <input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden">    
        <input name="deliveryFromDate" id="deliveryFromDate" value="${param.deliveryFromDate}" type="hidden">    
        <input name="deliveryToDate" id="deliveryToDate" value="${param.deliveryToDate}" type="hidden">    
        <input name="searchBy" id="searchBy" value="${param.searchBy}" type="hidden"> 
        <input name="searchType" id="searchType" value="${param.searchType}" type="hidden">      
        <input name="searchText" id="searchText" value="${param.searchText}" type="hidden">           
        <input name="accountSysId" id="accountSysId" value="" type="hidden"> 
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