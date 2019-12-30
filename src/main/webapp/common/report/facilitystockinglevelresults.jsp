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

<!-- For Calendar support for column type hcal-->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


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

<title><fmt:message key="label.facilitystockinglevel"/></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
pleaseselectarowforupdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


var config = [
{ columnId:"companyId"},
{ columnId:"facilityId", columnName:'<fmt:message key="label.facility"/>', width:8},
{ columnId:"catPartNo",columnName:'<fmt:message key="label.partnumber"/>', width:8},
{ columnId:"partDesc",columnName:'<fmt:message key="label.partdesc"/>', tooltip:true, width:20},
{ columnId:"itemDesc",columnName:'<fmt:message key="label.itemdesc"/>', tooltip:true, width:20},
{ columnId:"purchasePackaging", columnName:'<fmt:message key="label.packaging"/>', tooltip:true, width:13},
{ columnId:"minStockingLevel", columnName:'<fmt:message key="label.minstockinglevel"/>', width:5, align:'center'},
{ columnId:"maxStockingLevel", columnName:'<fmt:message key="label.maxstockinglevel"/>', width:5, align:'center'},
{ columnId:"totalStockingLevel", columnName:'<fmt:message key="label.totalstockinglevel"/>', width:5, align:'center'},
{ columnId:"avgStockingLevel", columnName:'<fmt:message key="label.avgstockinglevel"/>', width:5, align:'center'},
{ columnId:"workAreaCount", columnName:'<fmt:message key="label.workareacount"/>', width:5, align:'center'}
];

var windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid = null;


function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	
	if (totalLines > 0)
	{		
		document.getElementById("facilityStockingLevelBean").style["display"] = "";
		initializeGrid();
	}  
	else
	{
		document.getElementById("facilityStockingLevelBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
 
}

function initializeGrid(){
   totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		beangrid = new dhtmlXGridObject('facilityStockingLevelBean');
		initGridWithConfig(beangrid,config,false,true,true);
		if( typeof( jsonMainData ) != 'undefined' ) {
				beangrid.parse(jsonMainData,"json");
				
	    	 		    
        }
	}
}



//-->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/facilitystockinglevelresults.do" onsubmit="return submitFrameOnlyOnce();">

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

<div id="facilityStockingLevelBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${facilityStockingLevelColl != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty facilityStockingLevelColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${facilityStockingLevelColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="readonly" value='N'/>

{id:${status.index +1},
 data:[
  '<tcmis:jsReplace value="${bean.companyId}" />',
  '<tcmis:jsReplace value="${bean.facilityName}" />',
  '<tcmis:jsReplace value="${bean.catPartNo}" />',
  '<tcmis:jsReplace value="${bean.partDescription}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.purchasePackaging}" />',
  '${bean.minStockingLevel}',
  '${bean.maxStockingLevel}',
  '${bean.totalStockingLevel}',
  '${bean.avgStockingLevel}',
  '${bean.workAreaCount}'
  ]}
  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->
<c:if test="${empty facilityStockingLevelColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>



<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>