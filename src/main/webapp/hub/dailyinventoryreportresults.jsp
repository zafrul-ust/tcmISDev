<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/hub/dailyinventoryreport.js"></script>

<title>
<fmt:message key="dailyInventoryreport.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
    all:"<fmt:message key="label.all"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    minutes:"<fmt:message key="label.minutes"/>",
    seconds:"<fmt:message key="label.seconds"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>"};
    
    
var config = [
    { 
      columnId:"catPartNo",
	  columnName:'<fmt:message key="label.partnumber"/>',
	  tooltip:true,
	  width:8
	},  
    { 
      columnId:"itemId",
	  columnName:'<fmt:message key="label.itemid"/>',
	  align:'right',
	  width:5
	},
    {
      columnId:"itemDesc",
	  columnName:'<fmt:message key="label.description"/>',
	  sorting:"haasStr",
	  tooltip:true,
	  width:20
	},
	{
	  columnId:"quantity",
	  columnName:'<fmt:message key="label.qty"/>',
	  align:'right',
	  width:4
	},
	{	
	  columnId:"packaging",
	  columnName:'<fmt:message key="label.pkg"/>',
	  tooltip:true,
	  sorting:'na',
	  width:15
	},
	{	
	  columnId:"currencyId",
	  columnName:'<fmt:message key="label.currencyid"/>',
	  sorting:'na',
	  width:5
	},
	{	
	  columnId:"mvhaas",
	  columnName:'<fmt:message key="dailyInventoryreport.label.materialvalue"/>',
	  attachHeader:'<fmt:message key="label.haas"/>',
	  sorting:'na',
	  align:'right',
	  width:7
		
	},
	{
  	  columnId:"mvconsigned",
  	  columnName:'#cspan',
  	  attachHeader:'<fmt:message key="label.consigned"/>',
  	  sorting:'na',
  	  align:'right',
  	  width:6
	},
	{
	  columnId:"mvcustomer",
	  columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.customer"/>',
	  sorting:'na',
	  align:'right',
	  width:5
	},
	{
	  columnId:"mvtotal",
	  columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.total"/>',
	  sorting:'na',
	  align:'right',
	  width:8
	},
	{	
	  columnId:"fvhaas",
	  columnName:'<fmt:message key="dailyInventoryreport.label.fullvalue"/>',
	  attachHeader:'<fmt:message key="label.haas"/>',
	  sorting:'na',
	  align:'right',
	  width:7
		
	},
	{
  	  columnId:"fvconsigned",
  	  columnName:'#cspan',
  	  attachHeader:'<fmt:message key="label.consigned"/>',
  	  sorting:'na',
  	  align:'right',
  	  width:6
	},
	{
	  columnId:"fvcustomer",
	  columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.customer"/>',
	  sorting:'na',
	  align:'right',
	  width:5
	},
	{
	  columnId:"fvtotal",
	  columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.total"/>',
	  sorting:'na',
	  align:'right',
	  width:8
	},
	{
	  columnId:"inventoryGroup"
	},
	{
	  columnId:"dailyDate"
	}
    ];
        
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/dailyinventoryreportresults.do" onsubmit="return submitFrameOnlyOnce();">

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

<div id="dailyInventoryReportBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${dailyInventoryItemViewBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty dailyInventoryItemViewBeanCollection}">

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="dbBean" items="${dailyInventoryItemViewBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	
	<fmt:formatNumber var="haasMaterialValue" value="${dbBean.haasMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="consMaterialValue" value="${dbBean.consMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="custMaterialValue" value="${dbBean.custMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="sumMaterialValue" value="${dbBean.sumMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="haasFullValue" value="${dbBean.haasFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="consignedFullValue" value="${dbBean.consignedFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="customerFullValue" value="${dbBean.customerFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="sumFullValue" value="${dbBean.sumFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	
	<fmt:formatNumber var="totalBeanhaasMaterialValue" value="${totalBean.haasMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeanconsMaterialValue" value="${totalBean.consMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeancustMaterialValue" value="${totalBean.custMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeansumMaterialValue" value="${totalBean.sumMaterialValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeanhaasFullValue" value="${totalBean.haasFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeanconsignedFullValue" value="${totalBean.consignedFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeancustomerFullValue" value="${totalBean.customerFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	<fmt:formatNumber var="totalBeansumFullValue" value="${totalBean.sumFullValue}" pattern="${totalcurrencyformat}"></fmt:formatNumber>
	
	<tcmis:jsReplace var="itemDesc" value="${dbBean.itemDesc}"  processMultiLines="true"/>
	<tcmis:jsReplace var="packaging" value="${dbBean.packaging}"  processMultiLines="true"/>
	 /*The row ID needs to start with 1 per their design.*/
		
	 { id:${status.index +1},
	 data:['${dbBean.catPartNo}',
	 '${dbBean.itemId}','${itemDesc}',
	 '${dbBean.quantity}',
	 '${packaging}',
	 '${dbBean.currencyId}',
	 '${haasMaterialValue}',
	 '${consMaterialValue}',
	 '${custMaterialValue}',
	 '${sumMaterialValue}',
	 '${haasFullValue}',
	 '${consignedFullValue}',
	 '${customerFullValue}',
	 '${sumFullValue}',
	 '${dbBean.inventoryGroup}',
	 '${dbBean.dailyDate}'
	  ]}
	 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
  ,{ id:${dataCount+1},
	 data:['',
	 '','',
	 '',
	 '<center><b><fmt:message key="label.total"/>:</b></center>',
	 '${totalBean.currencyId}',
	 '${totalBeanhaasMaterialValue}',
	 '${totalBeanconsMaterialValue}',
	 '${totalBeancustMaterialValue}',
	 '${totalBeansumMaterialValue}',
	 '${totalBeanhaasFullValue}',
	 '${totalBeanconsignedFullValue}',
	 '${totalBeancustomerFullValue}',
	 '${totalBeansumFullValue}',
	 '',
	 ''
	  ]}
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty dailyInventoryItemViewBeanCollection}">
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
        <input name="userAction" id="userAction" value="" type="hidden">              
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden">
        <input name="hub" id="hub" value="${param.hub}" type="hidden">
        <input name="dailyDate" id="dailyDate" value="${param.dailyDate}" type="hidden">
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html>

