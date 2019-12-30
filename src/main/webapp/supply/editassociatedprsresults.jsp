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

<!-- For Calendar support 

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/editassociatedprsresult.js"></script>

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
<fmt:message key="label.purchaseordersupplier"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
disassociatePrsWithNonzeroQty:"<fmt:message key="msg.disassociateprswithnonzeroqty"/>",
selectDifferentPrs:"<fmt:message key="msg.selectdifferentprs"/>",
item:"<fmt:message key="label.item"/>",
customerPOForDropShip:"<fmt:message key="label.customerpofordropship"/>",
inventoryGroup:"<fmt:message key="label.inventorygroup"/>"
};

var gridConfig = {
    divName:'editAssociatedPrsViewBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
    beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
    beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
    config:'config',         // the column config var name, as in var config = { [ columnId:..,columnName...
    rowSpan:false,            // this page has rowSpan > 1 or not. Set this to -1 to disable rowSpan and smart rendering, but the sorting will still work
    submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click
    noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
};

/*This array defines the grid and properties about the columns in the grid.
* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
* */
var config = [  
    {
        columnId:"permission"
    },
    {
        columnId:"ok",
        columnName:'<fmt:message key="label.ok"/>', 
        type:'hchstatus', 
        onChange:checkUpdateDeleteFlag,
        align:'center',     
        width:3
    },   
    {
    	columnId:"prCreated",
        columnName:'<fmt:message key="label.prcreated"/>',
        tooltip:"Y",
        width:12
    },
    {
    	columnId:"status",
        columnName:'<fmt:message key="label.status"/>',
        tooltip:"Y",
        width:12
    },
	{ 
    	columnId:"shipTo",
        columnName:'<fmt:message key="label.shipto"/>',
        tooltip:"Y",
        width:12
	},
	{ 
		columnId:"facility",
	    columnName:'<fmt:message key="label.facility"/>',
	    tooltip:"Y"
	},
	{ 
		columnId:"buyer",
        columnName:'<fmt:message key="label.buyer"/>',
        tooltip:"Y",
        width:15
	},
	{ 
		columnId:"itemId",
        columnName:'<fmt:message key="label.item"/>',
        tooltip:"Y",
        width:15
	},
	{ 
		columnId:"itemDesc",
        columnName:'<fmt:message key="label.itemdesc"/>',
        tooltip:"Y",
        width:15
	},
	{ 
		columnId:"type",
        columnName:'<fmt:message key="label.type"/>',
        tooltip:"Y",
        width:15
	},
	{
		columnId:"buyQty",
        columnName:'<fmt:message key="label.buyqty"/>',
        tooltip:"Y",
        width:15
	},
	{
		columnId:"uom",
        columnName:'<fmt:message key="label.uom"/>',
        tooltip:"Y",
        width:15
	},
	{
		columnId:"needed",
        columnName:'<fmt:message key="label.needed"/>',
        tooltip:"Y",
        width:15
	},
	   {
        columnId:"mrLine",
        columnName:'<fmt:message key="label.mrline"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"mrQty",
        columnName:'<fmt:message key="label.mrqty"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"partNumber",
        columnName:'<fmt:message key="label.partnumber"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"specs",
        columnName:'<fmt:message key="label.specs"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"purchasingNote",
        columnName:'<fmt:message key="label.purchasingnotes"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"manufacturer",
        columnName:'<fmt:message key="label.manufacturer"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"lastSupplier",
        columnName:'<fmt:message key="label.lastsupplier"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"hub",
        columnName:'<fmt:message key="label.hub"/>',
        tooltip:"Y",
        width:15
    },    
    {
        columnId:"company",
        columnName:'<fmt:message key="label.company"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"clientPO",
        columnName:'<fmt:message key="label.clientpo"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"notes",
        columnName:'<fmt:message key="label.notes"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"mm",
        columnName:'<fmt:message key="label.mm"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"reorderPoint",
        columnName:'<fmt:message key="label.reorderpoint"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"inventory",
        columnName:'<fmt:message key="label.inventory"/>',
        tooltip:"Y",
        width:15
    },
    {
        columnId:"openPoQty",
        columnName:'<fmt:message key="label.openpoqty"/>',
        tooltip:"Y",
        width:15
    },
    {
    	columnId:"radianPo"
    },
    {
        columnId:"changed"
    },
    {
    	columnId:"prNumber"
    },
    {
        columnId:"buyerCompanyId"
    },
    {
    	columnId:"branchPlant"
    },
    {
    	columnId:"everConfirmed"
    }
];

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();">

<tcmis:form action="/editassociatedprsresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false. -->
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder">
<c:set var="showUpdateLink" value='Y'/>
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:inventoryGroupPermission>


<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
	<html:errors/>${errorMessage}
	<html:errors/>${tcmISError}
	<c:forEach var="error" items="${tcmISErrors}">
		${error}<br/>
	</c:forEach>
</div>


<script type="text/javascript">
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
	<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty errorMessage && empty tcmISErrors }">
		showErrorMessage = false;
	</c:when>
	<c:otherwise>
		showErrorMessage = true;
	</c:otherwise>
</c:choose>
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty editAssociatedPrsViewBeanCollection}" >
<div id="editAssociatedPrsViewBean" style="width:100%;height:600px;" style="display: none;"></div>

<script type="text/javascript">
<!--

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${editAssociatedPrsViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<tcmis:jsReplace var="purchasingNote" value="${bean.purchasingNote}" processMultiLines="true" />  
<tcmis:jsReplace var="notes" value="${bean.notes}" processMultiLines="true" />  
<tcmis:jsReplace var="itemDesc" value="${bean.itemDesc}" processMultiLines="true" />  
<tcmis:jsReplace var="uom" value="${bean.uom}" processMultiLines="true" />  
<tcmis:jsReplace var="mfgId" value="${bean.mfgId}" processMultiLines="true" />  
<fmt:formatDate var="needed" value="${bean.needDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="created" value="${bean.datePoCreated}" pattern="${dateTimeFormatPattern}"/>

<c:set var="selectFlag" value="false"/>
<c:if test="${!empty bean.radianPo}">
   <c:set var="selectFlag" value="true"/>
</c:if>    

/*The row ID needs to start with 1 per their design.*/ 
{ id:${status.index +1},
 data:[
  'Y',
  ${selectFlag},
  '${bean.prNumber} (${created})', 
  '${bean.status}',
  '<tcmis:jsReplace value="${bean.shipToLocationId}" />',
  '<tcmis:jsReplace value="${bean.facility}" />',
  '<tcmis:jsReplace value="${bean.buyer}" />',
  '${bean.itemId}',
  '${itemDesc}',
  '${bean.itemType}',
  '${bean.orderQuantity}',
  '${uom}',
  '${needed}',
  '${bean.mrLineItem}',
  '${bean.mrQuantity}', 
  '${bean.partId}',
  '<tcmis:jsReplace value="${bean.specList}" />',
  '${purchasingNote}',
  '${mfgId}',
  '<tcmis:jsReplace value="${bean.lastSupplier}" />',
  '<tcmis:jsReplace value="${bean.branchPlant} (${bean.inventoryGroup})" />',
  '${bean.companyId}',
  '${bean.raytheonPo}',
  '${notes}',
  '${bean.stockingLevel}',
  '${bean.reorderPoint}',
  '${bean.availableQuantity}',
  '${bean.openPoQuantity}',
  '${bean.radianPo}',
  'N',
  '${bean.prNumber}',
  '${bean.buyerCompanyId}',
  '${bean.branchPlant}',
  '${bean.everConfirmed}'
  ]
}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};

//--> 
</script><!-- If the collection is empty say no data found -->
</c:if>

   <c:if test="${empty editAssociatedPrsViewBeanCollection}" >

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
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input name="uAction" id="uAction" type="hidden" value=""/>
<input name="updateSuccess" id="updateSuccess" value="<c:out value="${updateSuccess}"/>" type="hidden"/>
<input name="isFullyDisassociated" id="isFullyDisassociated" value="<c:out value="${isFullyDisassociated}"/>" type="hidden"/>
<tcmis:setHiddenFields beanName="searchInput"/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
         showUpdateLinks = true;        
        //-->
    </script>
</c:if>
</tcmis:form>
</body>
</html:html>