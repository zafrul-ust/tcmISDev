<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
<script type="text/javascript" src="/js/hub/cabinetinventoryresults.js"></script>

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
<fmt:message key="label.cabinet"/> <fmt:message key="label.inventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("freightAdvice")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.showinventorydetail"/>;url=javascript:viewInventoryDetail();");
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
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

<c:set var="workArea">
 <fmt:message key="label.workarea"/>
</c:set>

<c:set var="leadTimeDays">
 <fmt:message key="label.leadtimeindays"/>
</c:set>

var config = [
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:6,
  sorting:"int",
  hiddenSortingColumn:"hitemId",
  align:"left"
},
{
  columnId:"cpigStatus",
  columnName:'<fmt:message key="label.status"/>',
  width:6
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.description"/>',
  width:16
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partnum"/>',
  tooltip:"Y"
},
{ columnId:"useApplication",
  columnName:'<tcmis:jsReplace value="${workArea}"/>',
  tooltip:"Y",
  width:9
},
{ columnId:"cabinetName",
  columnName:'<fmt:message key="label.cabinet"/>',
  width:9,
  tooltip:"Y"
},
{ columnId:"countType",
	  columnName:'<fmt:message key="label.counttype"/>',
	  width:6,
	  tooltip:"Y"
	},
{ columnId:"receiptId",
  columnName:'<fmt:message key="label.receiptid"/>',
  width:7
},
{ columnId:"mfgLot",
  columnName:'<fmt:message key="label.mfglot"/>'
},
{ columnId:"expireDate",
  columnName:'<fmt:message key="label.expirationdate"/>',
  sorting:"int",
  width:7,
  hiddenSortingColumn:"hexpireDate"
},
{ columnId:"hexpireDate",
  sorting:"int"
},
{ columnId:"qualityTrackingNumber",
	  columnName:'<fmt:message key="label.qualitynote"/>',
	  width:10,
	  tooltip:"Y"
},
{ columnId:"countDatetime",
  columnName:'<fmt:message key="label.lastcountdate"/>',
  sorting:"int",
  width:7,
  hiddenSortingColumn:"hcountDatetime"
},
{ columnId:"hcountDatetime",
  sorting:"int"
},
{ columnId:"countQuantity",
  columnName:'<fmt:message key="label.lastcountqty"/>',
  sorting:"int",
  width:5
},
{ columnId:"qtyIssuedAfterCount",
  columnName:'<fmt:message key="label.deliveredsincelastcount"/>',
  sorting:"int",
  width:7
},
{ columnId:"totalQuantity",
  columnName:'<fmt:message key="label.totalqty"/>',
  sorting:"int",
  width:5
},
{ columnId:"unitCost",
  columnName:'<fmt:message key="label.unitcost"/>',
  sorting:"int",
  hiddenSortingColumn:"hUnitCost"
},
{ columnId:"reorderPoint",
  columnName:'<fmt:message key="label.reorderpoint"/>',
  width:5,
  sorting:"int"
},
{ columnId:"stockingLevel",
  columnName:'<fmt:message key="label.stockinglevel"/>',
  width:6,
  sorting:"int"
},
{ columnId:"leadTimeDays",
  columnName:'<tcmis:jsReplace value="${leadTimeDays}"/>',
  width:5,
  sorting:"int"
},
{ columnId:"hub"
},
{ columnId:"hitemId",
  sorting:"int"
},
{ columnId:"hUnitCost",
  sorting:"int"
}
];

function setHlinkAndColName() {
 if( typeof( jsonMainData ) == 'undefined' ) return; 
 var rows =jsonMainData.rows;
 var setDPM = false;

/* for( var i = 0; i < rows.length; i++ ) 
 {
	if(rows[i].data[0]!='')
   {	
  	rows[i].data[0] ="<label class='pointer' style='color:blue;text-decoration:underline'>" + rows[i].data[0] + "</label>"; 
   }	
		  
 }*/
if(rows[0].data[18]=='2118')
{	
	config[2].columnName = '<fmt:message key="label.dpm"/>';
}

 	 
}
// -->
</script>
</head>
<body bgcolor="#ffffff" onload="setHlinkAndColName();resultOnLoad()">

<tcmis:form action="/cabinetinventoryresults.do" onsubmit="return submitFrameOnlyOnce();">

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

<div id="cabinetInventoryBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${cabinetInventoryBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty cabinetInventoryBeanCollection}">

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="dbBean" items="${cabinetInventoryBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	
	<c:set var="cpigStatus" value='' />
	<c:if test="${dbBean.cpigStatus ==  'A' }"><c:set var="cpigStatus"><fmt:message key="label.active"/></c:set></c:if>
	<c:if test="${dbBean.cpigStatus ==  'D' }"><c:set var="cpigStatus"><fmt:message key="label.drawdown"/></c:set></c:if>
	<c:if test="${dbBean.cpigStatus ==  'O' }"><c:set var="cpigStatus"><fmt:message key="label.obsolete"/></c:set></c:if>
	
	
	<fmt:formatDate var="fmtExpireDate" value="${dbBean.expireDate}" pattern="${dateFormatPattern}"/>
	<c:set var="expireDateSortable" value="${dbBean.expireDate.time}"/>
	
	<fmt:formatDate var="fmtCountDatetime" value="${dbBean.countDatetime}" pattern="${dateFormatPattern}"/>
	<c:set var="countDatetimeSortable" value="${dbBean.countDatetime.time}"/>
	
	<tcmis:jsReplace var="itemDesc" value="${dbBean.itemDesc}"  processMultiLines="true"/>
	 /*The row ID needs to start with 1 per their design.*/
	 
	<c:choose>
	   <c:when test="${dbBean.unitCost eq null}">
	     <c:set var="currencyId" value='' />
	   </c:when>
	   <c:otherwise>
	     <c:set var="currencyId" value='${dbBean.currencyId}' />
	   </c:otherwise>
	</c:choose>
	<fmt:formatNumber var="unitCost" value="${dbBean.unitCost}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
	
	 { id:${status.index +1},
	<c:if test="${status.current.cpigStatus eq 'O'}">'class':"grid_black",</c:if>
	 data:['${dbBean.itemId}','${cpigStatus}','${itemDesc}',
	 <c:if test="${dbBean.hub == '2118'}">
	 '${dbBean.qcDoc}'
	</c:if>
	<c:if test="${dbBean.hub != '2118'}">
	'${dbBean.catPartNo}'
	</c:if> ,
	 '${dbBean.useApplication}',
	 '${dbBean.cabinetName}',
	 '<c:choose><c:when test='${dbBean.countType == "ITEM_ID"}'><fmt:message key="label.itemid"/></c:when><c:when test='${dbBean.countType == "RECEIPT_ID"}'><fmt:message key="label.receiptid"/></c:when><c:otherwise></c:otherwise></c:choose>',
	 '${dbBean.receiptId}',
	 '${dbBean.mfgLot}',
	 '${fmtExpireDate}',
	 '${expireDateSortable}',
	 '${dbBean.qualityTrackingNumber}',
	 '${fmtCountDatetime}',
	 '${countDatetimeSortable}',
	 '${dbBean.countQuantity}',
	 '${dbBean.qtyIssuedAfterCount}',
	 '${dbBean.totalQuantity}',
	 '${currencyId} ${unitCost}',
	 '${dbBean.reorderPoint}',
	 '${dbBean.stockingLevel}',
	 '${dbBean.leadTimeDays}',
	 '${dbBean.hub}',
	 '${dbBean.itemId}',
	 '${dbBean.unitCost}']}
	 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty cabinetInventoryBeanCollection}">
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
         <input name="action" id="action" value="" type="hidden">	
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>