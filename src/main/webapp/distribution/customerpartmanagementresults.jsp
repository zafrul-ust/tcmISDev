<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/customerpartmanagement.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
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
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>

<title>
<fmt:message key="label.customerpartmanagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
// Added all column names to the messagesData array.
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
noRowSelected:"<fmt:message key="label.norowselected"/>",
missingCustomerPart:"<fmt:message key="label.missingcustomerpart"/>",	 
type:"<fmt:message key="label.type"/>"}; 

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:5
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.desc"/>',
  tooltip:true,
  width:24
},
{ columnId:"spec",
  columnName:'<fmt:message key="label.specs"/>',
  tooltip:true,
  width:20
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  type:'hchstatus',
  align:'center',
  width:4
},
{ columnId:"customerPartNo",
  columnName:'<fmt:message key="label.customerpart"/>',
  type: 'hed',
  size:27,	
  width:15
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  type: 'hcoro'	
},
{ columnId:"dateInserted",
  columnName:'<fmt:message key="label.date"/>',
  width:8
},
{ columnId:"catalogCompanyId",
  columnName:""
},
{ columnId:"catalogId",
  columnName:""
},
{ columnId:"catPartNo",
  columnName:""
},
{ columnId:"customerId",
  columnName:""
},
{ columnId:"companyId",
  columnName:""
},
{ columnId:"oldCustomerPartNo",
  columnName:""
},
{ columnId:"oldStatus",
  columnName:""
}
];

var status = new Array(
	{text:'<fmt:message key="label.active"/>',value:'A'},
	{text:'<fmt:message key="label.inactive"/>',value:'I'}
);

// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/customerpartmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>   
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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

<%--NEW - there is no results table anymore--%>
<div id="customerPartManagementDiv" style="width:100%;%;height:400px;" style="display: none;"></div>

<c:if test="${customerPartManagementCollection != null}" >
<script type="text/javascript">
<!--

	<c:set var="dataCount" value='${0}'/>
	<bean:size id="listSize" name="customerPartManagementCollection"/>
	<c:if test="${!empty customerPartManagementCollection}" >
		<c:set var="preItemId" value=''/>
		var map = null;
		var lineMap = new Array();
		//var lineIdMap = new Array();
		var lineMap3 = new Array();
		<c:set var="itemCount" value='${-1}'/>
		<c:forEach var="tmpBean" items="${customerPartManagementCollection}" varStatus="status">
			<c:set var="currentItemId" value='${tmpBean.itemId}'/>
			<c:if test="${ currentItemId != preItemId }">
				lineMap[${status.index}] = ${dataRowSpanHashMap[currentItemId]};
				map = new Array();
				<c:set var="itemCount" value='${itemCount+1}'/>
			</c:if>
			<c:set var="preItemId" value='${tmpBean.itemId}'/>
			lineMap3[${status.index}] = ${itemCount} % 2;
			map[map.length] =  ${status.index} ;
			//lineIdMap[${status.index}] = map;
		</c:forEach>

		var showUpdate = false;
		<c:set var="currentPermission" value='N'/>
		<tcmis:opsEntityPermission indicator="true" userGroupId="GenerateOrders">
  			<c:set var="currentPermission" value="Y"/>
			showUpdate = true;
		</tcmis:opsEntityPermission>
		var jsonMainData = new Array();
		var jsonMainData = {
			rows:[
				<c:forEach var="tmpBean" items="${customerPartManagementCollection}" varStatus="status">
					<tcmis:jsReplace var="partDesc" value='${tmpBean.partDescription}' processMultiLines="true" />
					<tcmis:jsReplace var="specList" value='${tmpBean.specList}' processMultiLines="false" />
					<tcmis:jsReplace var="custPartNo" value='${tmpBean.customerPartNo}' processMultiLines="false" />
					<tcmis:jsReplace var="catPartNo" value='${tmpBean.catPartNo}' processMultiLines="false" />
					<fmt:formatDate var="fmtDateInserted" value="${tmpBean.dateInserted}" pattern="${dateFormatPattern}"/>
				   <c:set var="tmpStatus" value="${tmpBean.status}"/>
					<c:if test="${currentPermission == 'N' && empty tmpStatus}">
						<c:set var="tmpStatus" value=" "/>
					</c:if>
					{ id:${status.index +1},
						data:[
							 '${currentPermission}',
							 '${tmpBean.itemId}',
							 '${partDesc}',
							 '${specList}',
							 '',
							 '${custPartNo}',
							 '${tmpStatus}',
							 '${fmtDateInserted}',
							 '${tmpBean.catalogCompanyId}',
							 '${tmpBean.catalogId}',
							 '${catPartNo}',
							 '${tmpBean.customerId}',
							 '${tmpBean.companyId}',
							 '${custPartNo}',
							 '${tmpBean.status}'
						]
					}
					<c:if test="${status.index+1 < listSize}">,</c:if>
					<c:set var="dataCount" value='${dataCount+1}'/>
				</c:forEach>
		]};
	</c:if>
// -->
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty customerPartManagementCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>	
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input name="customerId" id="customerId" value="${param.customerId}" type="hidden">
<input name="searchBy" id="searchBy" value="${param.searchBy}" type="hidden">
<input name="searchType" id="searchType" value="${param.searchType}" type="hidden">
<input name="searchText" id="searchText" value="${param.searchText}" type="hidden">
<input name="activeOnly" id="activeOnly" value="${param.activeOnly}" type="hidden">	
<input name="minHeight" id="minHeight" type="hidden" value="100">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>