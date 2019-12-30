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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

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

var mygrid;
var resizeGridWithWindow = true;

function resultOnLoad()
{	
	 totalLines = document.getElementById("totalLines").value;
	 if (totalLines > 0)
	 {
	  	document.getElementById("customerMsdsBean").style["display"] = "";
	  	initializeGrid();  
	 }  
	 else
	 {
	   document.getElementById("customerMsdsBean").style["display"] = "none";   
	 }
	 displayGridSearchDuration(); 
	 setResultFrameSize(); 
}


function initializeGrid(){
	initGridWithGlobal(gridConfig);
}  


function selectRow(rowId,cellInd)
{
	selectedRowId = rowId;
}  

function selectRightclick(rowId,cellInd) {
	selectRow(rowId, cellInd);
	if(cellInd > 5)
		toggleContextMenu('rightClickMenu');
	else
		toggleContextMenu('contextMenu');
}

function assignUser() {
	document.genericForm.target='';
	document.getElementById('uAction').value = 'assignUser';

	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (mygrid != null) {
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function onChangeAssignedTo() {
	var cid = "assigneeUpdated" + mygrid.getSelectedRowId();
	if ($(cid)) {
		$(cid).checked = true;
		updateHchStatusA(cid);
	}
}

function openMsdsMaintenance() {
	var materialId  =  cellValue(mygrid.getSelectedRowId(),"materialId"); 
	var revisionDate  =  cellValue(mygrid.getSelectedRowId(),"revisionDateTime");
	var companyId  =  cellValue(mygrid.getSelectedRowId(),"companyId");
	var loc = "/tcmIS/catalog/msdsindexingmain.do?uAction=getMsds&RMC=Y&materialId="+materialId+"&companyId="+companyId+"&revisionDate="+revisionDate; 

    try
    {
        parent.parent.openIFrame("msdsMaintenance",loc,""+messagesData.msdsMaintenance,"","N");
    }
    catch (ex)
    {
    	var winId= "msdsMaintenance"+materialId+revisionDate;
        openWinGeneric(loc,winId,"900","600","yes","80","80","yes");
    }       
}

function updateAssignedTo() {
	document.genericForm.target='';
	document.getElementById('uAction').value = 'assignUser';

	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (mygrid != null) {
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}


with(milonic=new menuname("rightClickMenu")){
 top="offset=2"
 style = contextWideStyle;
 margin=3
 aI("text=<fmt:message key="label.editmdsdmaintenance"/>;url=javascript:openMsdsMaintenance();");	
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
msdsMaintenance:"<fmt:message key="msdsMaintenance"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var rowSpanCols = [0,1,2,3,4,5];

var gridConfig = {
	divName:'customerMsdsBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	selectChild: 1,
	//onBeforeSelect:beforeSelectRow,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightclick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
  }; 
  

var config = [
{
	columnId:"permission"
},
{
    columnId:"companyName",
    <c:if test="${param.status == 'customerIndexing' || param.status == 'customerQc'}" >
        columnName:'<fmt:message key="label.company"/>',
    </c:if>
    tooltip:"Y"
},
{
    columnId:"materialId",
    columnName:'<fmt:message key="label.material"/>',
    width:5,
    submit:true
},
{
    columnId:"materialDesc",
    columnName:'<fmt:message key="label.description"/>',
    tooltip:"Y",
    align:"left",
    width:15
},
{
    columnId:"tradeName",
    columnName:'<fmt:message key="label.tradename"/>',
    tooltip:"Y",
    width:15},
{
    columnId:"mfg_desc",
    columnName:'<fmt:message key="label.manufacturer"/>',
    tooltip:"Y",
    align:"left",
    width:15
},
{
    columnId:"currentCustRevisionDate"
    <c:if test="${param.status == 'customerIndexing' || param.status == 'customerQc'}" >
       , columnName:'<fmt:message key="label.companyrevisiondate"/>'
    </c:if>
},
{
    columnId:"revisionDateDisplay",
    columnName:'<fmt:message key="label.revisiondate"/>'
},
{
	columnId:"revisionDate",
    submit:true
},
{
    columnId:"globalDec",
    <c:if test="${param.status == 'customerIndexing' || param.status == 'customerQc'}" >
        columnName:'<fmt:message key="label.globaldataentrycompelete"/>',
    </c:if>
    align:"center"
},
{
	columnId:"assigneeUpdated",
	<c:if test="${param.status eq 'globalQc' || param.status eq 'customerQc'}">
	columnName:'<fmt:message key="label.ok"/>',
	type:"hchstatus",
	submit: true,
	</c:if>
	align:'center'
},
{
	columnId:"assignedTo",
	<c:if test="${param.status eq 'globalQc' || param.status eq 'customerQc'}">
	columnName:'<fmt:message key="label.assignedto"/>',
	type:"hcoro",
	onChange:onChangeAssignedTo,
	submit: true,
	width:15,
	</c:if>
	align:'center'
},
{
    columnId:"comments",
    columnName:'<fmt:message key="label.comments"/>',
    tooltip:"Y",
    width:15
},
{ columnId:"mfgId"},
{ columnId:"companyId"},
{ columnId:"revisionDateTime"},
{
    columnId:"submittedBy",
    <c:if test="${param.status == 'globalQc' || param.status == 'customerQc'}" >
        columnName:'<fmt:message key="label.submittedby"/>',
    </c:if>
    tooltip:"Y"
}
];	
  

var lineMap = new Array();
var lineMap3 = new Array();

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/customermsdsqueueresults.do" onsubmit="return submitFrameOnlyOnce();">
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
   <c:if test="${not empty MaxDataFilled || param.maxData == fn:length(customerIndexingMsdsQViewBeanColl)}">
    	<fmt:message key="label.maxdata"> 
    		<fmt:param value="${param.maxData}"/>
    	</fmt:message>
    </c:if>
</div>

<script type="text/javascript">
<!--
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
parent.messagesData.errors = "<fmt:message key="label.errors"/>";  
<c:if test="${not empty MaxDataFilled || param.maxData == fn:length(customerIndexingMsdsQViewBeanColl)}">
	showErrorMessage = true;
	parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
</c:if>
//-->
</script>
<!-- Error Messages Ends -->  

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="customerMsdsBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:choose>
	<c:when test="${empty customerIndexingMsdsQViewBeanColl}">
		<%-- If the collection is empty say no data found --%> 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			<tr>
				<td width="100%"><fmt:message key="main.nodatafound" /></td>
			</tr>
		</table>
	</c:when> 
	<c:otherwise>
	<script type="text/javascript">
	<!--
	  <c:set var="dataCount" value='${0}'/>
	  <bean:size id="listSize" name="customerIndexingMsdsQViewBeanColl"/>
	  <c:set var="preCompanyId" value=''/>
	  <c:set var="preMaterialId" value=''/>
		<c:set var="msdsCount" value='0'/>
		<c:forEach var="tmpBean" items="${customerIndexingMsdsQViewBeanColl}" varStatus="status">
		<c:choose>
			<c:when test="${tmpBean.companyId != preCompanyId || tmpBean.materialId != preMaterialId}">
				lineMap[${status.index}] = 1;
				<c:set var="preCompanyId" value='${tmpBean.companyId}'/>
				<c:set var="preMaterialId" value='${tmpBean.materialId}'/>
				<c:set var="msdsCount" value="${msdsCount + 1}"/>
				<c:set var="parent" value="${status.index}"/>
			</c:when>
			<c:otherwise>
				lineMap[${parent}]++;
			</c:otherwise>
		</c:choose>
			lineMap3[${status.index}] = ${msdsCount % 2};
		</c:forEach>
		
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
	<c:forEach var="msdsBean" items="${customerIndexingMsdsQViewBeanColl}" varStatus="status">
	   
			/*The row ID needs to start with 1 per their design.*/
			{ id:${status.count},
			 data:[
			 'Y',
			 '<tcmis:jsReplace value="${msdsBean.companyName}" />',
			 '${msdsBean.materialId}',
			 '<tcmis:jsReplace value="${msdsBean.materialDesc}" processMultiLines="true" />',
			 '<tcmis:jsReplace value="${msdsBean.tradeName}" processMultiLines="true" />',
			 '<tcmis:jsReplace value="${msdsBean.mfgDesc}" processMultiLines="true" />',
			 '<fmt:formatDate value="${msdsBean.currentCustRevisionDate}" pattern="${dateFormatPattern}"/>',
             '<fmt:formatDate value="${msdsBean.revisionDate}" pattern="${dateFormatPattern}"/>',
             '<fmt:formatDate value="${msdsBean.revisionDate}" pattern="${dateTimeFormatPattern}"/>',
			 '${msdsBean.globalDec}',
			 'N',
			 '${msdsBean.assignedTo}',
			 '<tcmis:jsReplace value="${msdsBean.comments}" processMultiLines="true" />',
			 '${msdsBean.mfgId}',
			 '${msdsBean.companyId}',
			 '<fmt:formatDate value="${msdsBean.revisionDate}" pattern="${dateTimeFormatPattern}"/>}',
             '<tcmis:jsReplace value="${msdsBean.submittedBy}" />'        
             ]}
		          <c:if test="${status.count < listSize}">,</c:if>
					<c:set var="dataCount" value='${dataCount+1}'/>
				</c:forEach>
		]};
		
		var assignedTo = [];
		<%-- start of assignee collection--%>
		<c:forEach var="msdsBean" items="${customerIndexingMsdsQViewBeanColl}" varStatus="status">
		       assignedTo['${status.count}']=[
		        {
		            text:'Unassigned',
		            value:''
		        }
		        <c:forEach var="user" items="${catalogUsers}" varStatus="status2">
		        <c:if test="${(empty msdsBean.companyId && user.companyId eq 'Radian') || user.companyId eq msdsBean.companyId}">
		           ,
		           {
		               text:'<tcmis:jsReplace value="${user.personnelName}"/>',
		               value:'${user.personnelId}'
		            }
		        </c:if>
		        </c:forEach>
		        ];
		</c:forEach>
		    <%-- end of assignee collection --%>

	//-->
	</script>
  </c:otherwise>				
</c:choose>
 <!-- Search results end --> 

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
        <input name="uAction" id="uAction" value="" type="hidden"/> 
        <input name="companyId" id="companyId" value="<tcmis:jsReplace value="${param.companyId}"/>" type="hidden"/>
        <input name="minHeight" id="minHeight" type="hidden" value="100"/> 
        <input name="maxData" id="maxData" type="hidden" value="<tcmis:jsReplace value="${param.maxData}"/>"/>
        <input name="status" id="status" type="hidden" value="<tcmis:jsReplace value="${param.status}"/>"/>
        <input name="assignedTo" id="assignedTo" type="hidden" value="<tcmis:jsReplace value="${param.assignedTo}"/>"/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>