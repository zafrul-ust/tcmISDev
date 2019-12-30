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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- For Calendar support for column type hcal-->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/listmanagement.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
<fmt:message key="label.listmanagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
manageList:"<fmt:message key="label.managelist"/>",
deleteList:"<fmt:message key="label.deletelist"/>",    
nothingchanged:"<fmt:message key="label.nothing"/>",
validValues:"<fmt:message key="label.validvalues"/>",
thresholdunit1:"<fmt:message key="label.thresholdunit1"/>",
thresholdunit2:"<fmt:message key="label.thresholdunit2"/>",
thresholdunit3:"<fmt:message key="label.thresholdunit3"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
listThresholdName:"<fmt:message key="label.listthresholdname"/>",
listThreshold:"<fmt:message key="label.listthresholdvalue"/>",
positivenumber:"<fmt:message key="label.positivenumber"/>",
listThresholdUnit:"<fmt:message key="label.listthresholdunit"/>"
};


with(milonic=new menuname("rightClickMenu")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=<fmt:message key="label.managelist"/>;url=javascript:openManageList();");
}
with(milonic=new menuname("rightClickMenuWithDelete")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=<fmt:message key="label.managelist"/>;url=javascript:openManageList();");
	aI("text=<fmt:message key="label.deleteline"/>;url=javascript:deleteList();");
}

drawMenus();

var config = [
{
  columnId:"permission"
}, 
{ 
  columnId:"listName",
  columnName:'<fmt:message key="label.name"/>',
  onChange:rowChanged,
  width:31,
  size:115,
  tooltip:"Y",
  type:"hed"
}, 
{ 
  columnId:"listDescription",
  columnName:'<fmt:message key="label.description"/>',
  onChange:rowChanged,
  width:31,
  size:115,
  tooltip:"Y",
  type:"hed"
},
{
  columnId:"listThresholdName",
  columnName:'<fmt:message key="label.listthresholdname"/>',
  onChange:rowChanged,
  width:15,
  size:50,
  type:"hed"
},
{
  columnId:"listThreshold",
  columnName:'<fmt:message key="label.listthresholdvalue"/>',
  width:10,
  size:50,  
  type:'hed',
  onChange:rowChanged
},
{
  columnId:"listThresholdUnit",
  columnName:'<fmt:message key="label.listthresholdunit"/>',
  width:10,	
  type:'hcoro',
  onChange:rowChanged
},
{
  columnId:"reference",
  columnName:'<fmt:message key="label.reference"/>',
  onChange:rowChanged,
  width:20,
  size:115,
  type:"hed"
},
{
  columnId:"author",
  columnName:'<fmt:message key="label.author"/>',
  width:10
},
{
  columnId:"insertDate",
  columnName:'<fmt:message key="label.date"/>',
  hiddenSortingColumn:'hiddenInsertDateTime', sorting:'int',
  width:10
},
{
	columnId:"hiddenInsertDateTime", sorting:'int' 
},
{ 
  columnId:"listId"
},
{
  columnId:"isAddLine"
},
{
  columnId:"updated"
},
{
	columnId:"owner"
},
{
	columnId:"insertBy"
},
{
	  columnId:"thresholdName",
	  columnName:'<fmt:message key="label.thresholdname1"/>',
	  width:15,
	  size:30,
	  type:"hed",
	  onChange:rowChanged
},
{
	  columnId:"thresholdUnit",
	  columnName:'<fmt:message key="label.thresholdunit1"/>',
	  width:10,	
	  type:'hcoro',
	  onChange:rowChanged
},
{
	  columnId:"thresholdName2",
	  columnName:'<fmt:message key="label.thresholdname2"/>',
	  width:15,
	  size:30,
	  type:"hed",
	  onChange:rowChanged
},
{
	  columnId:"thresholdUnit2",
	  columnName:'<fmt:message key="label.thresholdunit2"/>',
	  width:10,	
	  type:'hcoro',
	  onChange:rowChanged
},
{
	  columnId:"thresholdName3",
	  columnName:'<fmt:message key="label.thresholdname3"/>',
	  width:15,
	  size:30,
	  type:"hed",
	  onChange:rowChanged
},
{
	  columnId:"thresholdUnit3",
	  type:'hcoro',
	  columnName:'<fmt:message key="label.thresholdunit3"/>',
	  width:10,
	  onChange:rowChanged
	
}
];

<%-- Define the grid options--%>
var gridConfig = {
	divName: 'listManagementViewBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
	beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
	beanGrid: 'beangrid',		<%--  variable to put the grid object in for later use --%>
	config: config,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
	rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
	onRightClick: selectRow,	<%--  a javascript function to be called on right click with rowId & cellId as args --%>
	onRowSelect: selectRow,	<%--  a javascript function to be called on right click with rowId & cellId as args --%>
	noSmartRender:false,
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
    singleClickEdit:true
	};
//This is the array for type:'hcoro'. 
var listThresholdUnit = new  Array({text:'<fmt:message key="label.selectOne"/>',value:''},
        {text:'<fmt:message key="label.lb"/>',value:'lb'},
        {text:'<fmt:message key="label.kg"/>',value:'kg'},
        {text:'<fmt:message key="label.percentage"/>',value:'%'}
);
//This is the array for type:'hcoro'. 
var thresholdUnit2 = new  Array({text:'<fmt:message key="label.selectOne"/>',value:''},
        {text:'<fmt:message key="label.lb"/>',value:'lb'},
        {text:'<fmt:message key="label.kg"/>',value:'kg'},
        {text:'<fmt:message key="label.percentage"/>',value:'%'}
);
//This is the array for type:'hcoro'. 
var thresholdUnit = new  Array({text:'<fmt:message key="label.selectOne"/>',value:''},
        {text:'<fmt:message key="label.lb"/>',value:'lb'},
        {text:'<fmt:message key="label.kg"/>',value:'kg'},
        {text:'<fmt:message key="label.percentage"/>',value:'%'}
);
//This is the array for type:'hcoro'. 
var thresholdUnit3 = new  Array({text:'<fmt:message key="label.selectOne"/>',value:''},
        {text:'<fmt:message key="label.lb"/>',value:'lb'},
        {text:'<fmt:message key="label.kg"/>',value:'kg'},
        {text:'<fmt:message key="label.percentage"/>',value:'%'}
);

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/listmanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

<tcmis:permission indicator="true"  userGroupId="editCustomerList">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:permission>
   
<!~-~- Error Messages Begins ~-~->
<div id="errorMessagesAreaBody" style="display:none;">
 ${tcmISError}<br/>
<c:forEach var="tcmisError" items="${tcmISErrors}">
  ${tcmisError}<br/>
</c:forEach>
<c:if test="${param.maxData == fn:length(listChemicalColl)}">
 <fmt:message key="label.maxdata">
  <fmt:param value="${param.maxData}"/>
 </fmt:message>
</c:if>
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
<c:when test="${empty tcmISErrors and empty tcmISError}">
 <c:choose>
  <c:when test="${param.maxData == fn:length(listChemicalColl)}">
    showErrorMessage = true;
    parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
  </c:when>
  <c:otherwise>
    showErrorMessage = false;
  </c:otherwise>
 </c:choose>
</c:when>
<c:otherwise>
  parent.messagesData.errors = "<fmt:message key="label.errors"/>";
  showErrorMessage = true;
  </c:otherwise>
</c:choose>


//-->
</script>
<!-- Error Messages Ends -->  

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:set var="readonly" value='N'/>
<tcmis:permission indicator="true" userGroupId="editCustomerList">
 <c:set var="readonly" value="Y"/>
</tcmis:permission>

<div id="listManagementViewBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="completePermission" value="false"/>

<c:if test="${listChemicalColl != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty listChemicalColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${listChemicalColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
{id:${status.count},
 data:[
 <c:choose>
 	<c:when test="${readonly == 'Y' && personnelBean.companyId == bean.source && personnelBean.personnelId == bean.insertBy}">
		 'Y',
		 <c:set var="completePermission" value="true"/>
	</c:when>
	<c:otherwise>
	 	 'N',
	</c:otherwise>
 </c:choose>
 <c:set var="custAppend" value=""/>
 <c:if test="${bean.source != 'GLOBAL' && !completePermission}"><c:set var="custAppend" value="(CUST)"/></c:if>
  '<tcmis:jsReplace value="${bean.listName} ${custAppend}" processMultiLines="false"/>',
  '<tcmis:jsReplace value="${bean.listDescription}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.listThresholdName}" processMultiLines="true"/>',
  '${bean.listThreshold}',
  '${bean.listThresholdUnit}',
  '<tcmis:jsReplace value="${bean.reference}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.insertName}" processMultiLines="false"/>',
  '<fmt:formatDate value="${bean.insertDate}" pattern="${dateFormatPattern}"/>',
  '${bean.insertDate.time}',
  '<tcmis:jsReplace value="${bean.listId}" processMultiLines="true"/>',
  false,
  false,
  '${bean.source}',
  '<tcmis:jsReplace value="${bean.insertBy}" processMultiLines="true"/>',
  '<tcmis:jsReplace value="${bean.thresholdName}" processMultiLines="true"/>',
  '${bean.thresholdUnit}',
  '<tcmis:jsReplace value="${bean.thresholdName2}" processMultiLines="true"/>',
  '${bean.thresholdUnit2}',
  '<tcmis:jsReplace value="${bean.thresholdName3}" processMultiLines="true"/>',
  '${bean.thresholdUnit3}'
 ]}
  
 </c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->
<%--<c:if test="${empty listChemicalColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>--%>	

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(listChemicalColl)}"/>" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="listId" id="listId" type="hidden" value="${param.listId}"/>
<input name="listName" id="listName" type="hidden" value="${param.listName}"/>
<input name="listDescription" id="listDescription" type="hidden" value="${param.listDescription}"/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
<input type="hidden" name="personnelId" id="personnelId" value="${personnelBean.personnelId}"/>
<input name="maxData" id="maxData" type="hidden" value="${param.maxData}">
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>