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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/hub/updateitemshelfliferesults.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<title><fmt:message key="shelfLifeManagement"/></title>

<script language="JavaScript" type="text/javascript"/>
<!--
var resizeGridWithWindow = true;

with(milonic=new menuname("updateshelflifeMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.editshelflife"/>;url=javascript:updateShelfLife();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:'<fmt:message key="label.recordFound"/>',
searchDuration:'<fmt:message key="label.searchDuration"/>',
minutes:'<fmt:message key="label.minutes"/>',
seconds:'<fmt:message key="label.seconds"/>'
};


var gridConfig = {
	divName:'updateItemShelfLifeBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'columnConfig',	     // the column config var name, 
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	singleClickEdit:true,
	selectChild: 1,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
  }; 



  
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${param.inventoryGroup}">
   <c:set var="receivingQcPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>

 
<c:if test="${!empty receivingQcViewRelationBeanCollection}">  
	<c:if test="${receivingQcPermission == 'Yes'}">
	    showUpdateLinks = true;
	 </c:if>
</c:if>


var columnConfig = [
{ columnId:"itemId",columnName:'<fmt:message key="label.item"/>',width:5},
{ columnId:"itemDesc",columnName:'<fmt:message key="label.description"/>',width:20,tooltip:"Y"},
{ columnId:"materialDesc",columnName:'<fmt:message key="label.materialdesc"/>',width:20,tooltip:"Y"},
{ columnId:"inventoryGroup"},
{ columnId:"inventoryGroupName",columnName:'<fmt:message key="label.inventorygroup"/>', width:10},
{ columnId:"partId",columnName:'<fmt:message key="label.part"/>',width:5},
{ columnId:"shelfLifeDays", columnName:'<fmt:message key="label.shelflife(days)"/>', width:5},
{ columnId:"shelfLifeBasis"},
{ columnId:"shelfLifeBasisDesc",columnName:'<fmt:message key="label.basis"/>',width:18},
{ columnId:"storageTemp",columnName:'<fmt:message key="label.storagetemp"/>',width:10},
{ columnId:"lastUpdatedBy"},
{ columnId:"lastUpdatedByName",columnName:'<fmt:message key="label.lastUpdatedBy"/>',width:7},
{ columnId:"lastUpdatedOn",columnName:'<fmt:message key="label.laastUpdatedDate"/>',width:7},
{ columnId:"labelColor",columnName:'<fmt:message key="label.labelcolor"/>',width:7},
{ columnId:"source",columnName:'<fmt:message key="label.source"/>',width:7},
{ columnId:"comments",columnName:'<fmt:message key="label.comments"/>',width:30,tooltip:"Y"},
{ columnId:"packaging"}
];

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1];
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [3,4];


function sizeFrame()
{
     parent.document.getElementById("resultFrame").height = "400";
}
//-->
</script>
</head>
<body onload="resultOnLoad();">
	<tcmis:form action="/updateitemshelfliferesults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
</div>
<script type="text/javascript">
	<c:choose>
		<c:when test="${empty tcmISErrors and empty tcmISError}">
			<c:choose>
				<c:when test="${param.maxData == fn:length(testBeanCollection)}">
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
		<div class="interface" id="resultsPage">
			<div class="backGroundContent">
				<div id="updateItemShelfLifeBean" style="width:100%;height:400px;" style="display:none;"></div>
				<c:if test="${updateShelfLifeBeanCollection != null}">
				<script type="text/javascript">
					<c:set var="dataCount" value="${0}"/>
						<c:if test="${!empty updateShelfLifeBeanCollection}" >
							var jsonMainData = new Array();
							jsonMainData = {
								rows:[
							<c:forEach var="bean" items="${updateShelfLifeBeanCollection}" varStatus="status">
							<fmt:formatDate var="lastUpdatedOn" value="${bean.lastUpdatedOn}" pattern="${dateFormatPattern}"/>
							
								<c:if test="${status.index > 0}">,</c:if>
								{ id:${status.index +1},<c:if test="${!empty colorClass}">"class":"${colorClass}",</c:if>
									data:[
									'${bean.itemId}',
									'<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${bean.materialDesc}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${bean.inventoryGroup}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.inventoryGroupName}" processMultiLines="false"/>',
									'${bean.partId}',
									'${bean.shelfLifeDays eq -1?"Indefinite":bean.shelfLifeDays}',  
									'<tcmis:jsReplace value="${bean.shelfLifeBasis}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.shelfLifeBasisDesc}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.storageTemp}" processMultiLines="false"/>',
									'${bean.lastUpdatedBy}',
									'<tcmis:jsReplace value="${bean.lastUpdatedByName}" processMultiLines="false"/>',
									'${lastUpdatedOn}',
									'<tcmis:jsReplace value="${bean.labelColor}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.source}" processMultiLines="false"/>',
									'<tcmis:jsReplace value="${bean.comments}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${bean.packaging}" processMultiLines="true"/>'
									]
								}
								<c:set var="dataCount" value="${dataCount+1}"/>
							</c:forEach>
								]
							};
						<c:forEach var="bean" items="${updateShelfLifeBeanCollection}" varStatus="status">
						<c:set var="currentIg" value="${bean.inventoryGroup}" />
						<c:set var="currentItem" value="${bean.itemId}" />
						<c:choose>
							<c:when test="${status.first}">
								<c:set var="rowSpanStart" value="0" />
								<c:set var="rowSpanCount" value="1" />
								<c:set var="rowSpanLvl2Start" value="0" />
								rowSpanMap[0] = 1;
								rowSpanLvl2Map[0] = 1;
								rowSpanClassMap[0] = 1;
							</c:when>
							<c:when test="${currentItem == previousItem}">
								rowSpanMap[${rowSpanStart}]++;
								rowSpanMap[${status.index}] = 0;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								<c:choose>
									<c:when test="${currentIg == previousIg}">
										rowSpanLvl2Map[${rowSpanLvl2Start}]++;
										rowSpanLvl2Map[${status.index}] = 0;
									</c:when>
									<c:otherwise>
										<c:set var="rowSpanLvl2Count" value="${rowSpanLvl2Count + 1}" />
										<c:set var="rowSpanLvl2Start" value="${status.index}" />
										rowSpanLvl2Map[${status.index}] = 1;
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:set var="rowSpanCount" value="${rowSpanCount + 1}" />
								<c:set var="rowSpanStart" value="${status.index}" />
								<c:set var="rowSpanLvl2Start" value="${status.index}" />
								rowSpanMap[${status.index}] = 1;
								rowSpanLvl2Map[${status.index}] = 1;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:otherwise>
						</c:choose>
						<c:set var="previousIg" value="${currentIg}" />
						<c:set var="previousItem" value="${currentItem}" />
						</c:forEach>
					</c:if>
				</script>
				<!-- If the collection is empty say no data found -->
				<c:if test="${empty updateShelfLifeBeanCollection}">
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
				<!-- Hidden Element start -->
				<div id="hiddenElements" style="display:none">
					<input type="hidden" name="uAction" id="uAction" value=""/>
					<input name="totalLines" id="totalLines" type="hidden" value="${dataCount}"/>
					<input name="itemId" id="itemId" type="hidden" value=""/>
					<input name="inventoryGroup" id="inventoryGroup" type="hidden" value=""/>
					<input name="hub" id="hub" type="hidden" value="${param.sourceHub}"/>
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
				</div>
			</div>
			<!-- close of backgroundContent -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>