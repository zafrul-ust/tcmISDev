<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp"%>
<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script	type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
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
<script	type="text/javascript" src="/js/samsung/tmsordermanagementresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<title><fmt:message key="tmsordermanagement" /></title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

with(milonic=new menuname("rightClickMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=;url=javascript:doNothing();image=");
}

drawMenus();

var config = [
     {columnId: "permission"},
     {
    	columnId:"customerRequisistionNumber",
    	columnName:'<fmt:message key="label.reservationnumber"/>',
    	width:10
     },
     {
        columnId:"prNumber",
        columnName:'<fmt:message key="label.mrnumber"/>',
        width:5
     },
     {
       columnId:"rowWithUpdate",
       columnName:" ",
       type:'hchstatus',
       align:'center',
       width:3
     },
     {
       	columnId:"lineItem",
       	columnName:'<fmt:message key="label.line"/>',
      	width:3
     },
     {
        columnId:"status",
        columnName:'<fmt:message key="label.status"/>',
        width:7
     },
     {
        columnId:"catPartNo",
        columnName:'<fmt:message key="label.partnumber"/>',
        type: 'hed',
        width:10
     },
     {
        columnId:"packaging",
        columnName:'<fmt:message key="label.packaging"/>',
        tooltip:"Y",
        width:10
     },
     {
        columnId:"quantity",
        columnName:'<fmt:message key="label.quantity"/>',
        type: 'hed',
        width:5
     },
     {
        columnId:"requiredDatetime",
        columnName:'<fmt:message key="label.dateneeded"/>',
        type:'hcal',
        width:10
     },
     {
        columnId:"application",
        columnName:'<fmt:message key="label.deliverto"/>',
        type: 'hcoro',
        width:12
     },
     {
        columnId:"allocateByMfgLot",
        columnName:'<fmt:message key="label.lotnumber"/>',
        type: 'hed',
        width:10
     },
     {
        columnId:"internalNote",
        columnName:'<fmt:message key="label.notes"/>',
        tooltip:"Y",
        width:15
     },
     {
        columnId:"notes",
        columnName:'<fmt:message key="label.customernotes"/>',
        tooltip:"Y",
        width:15
     },
     {
        columnId:"requestDate",
        columnName:'<fmt:message key="label.dateordered"/>',
        width:7
     },
     {
        columnId:"lastUpdated",
        columnName:'<fmt:message key="label.lastupdated"/>',
        width:7
     },
     {
        columnId:"lastUpdateBy",
        columnName:'<fmt:message key="label.lastupdatedby"/>',
        width:7
     },
     {
       columnId:"companyId"
     },
     {
       columnId:"requestLineStatus"
     },
     {
       columnId:"allocateAfterPermission"
     },
     {
       columnId:"allocateAfter",
       columnName:'<fmt:message key="label.datetoallocate"/>',
       type:'hcal',
       width:10,
       permission:true
     }
  ];

var gridConfig = {
	divName:'orderManagement', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name,
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	singleClickEdit:true,
	selectChild: 1,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
  };

  var messagesData = new Array();
  messagesData={alert:"<fmt:message key="label.alert"/>",
        and:"<fmt:message key="label.and"/>",
        validvalues:"<fmt:message key="label.validvalues"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        recordFound:"<fmt:message key="label.recordFound"/>",
        searchDuration:"<fmt:message key="label.searchDuration"/>",
        minutes:"<fmt:message key="label.minutes"/>",
        seconds:"<fmt:message key="label.seconds"/>",
        forVal:"<fmt:message key="label.for"/>",
        partNumber:"<fmt:message key="label.partnumber"/>",
        quantity:"<fmt:message key="label.quantity"/>",
        requiredDatetime:"<fmt:message key="label.dateneeded"/>",
        application:"<fmt:message key="label.deliverto"/>",
        allocateByMfgLot:"<fmt:message key="label.lotnumber"/>",
        pleasewait:"<fmt:message key="label.pleasewait"/>",
        cancelReservation:"<fmt:message key="label.cancelreservation"/>",
        cancelLineReservation:"<fmt:message key="label.cancelreservationline"/>",
        pleasewait:"<fmt:message key="label.pleasewait"/>",
        itemInteger:"<fmt:message key="error.item.integer"/>"};

    var rowSpanCols = [1,2];
    var lineMap = new Array();
    var lineMap3 = new Array();

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/tmsordermanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
        ${tcmISError}<br/>
        <c:forEach items="${tcmISErrors}" varStatus="status">
            ${status.current}<br/>
        </c:forEach>

         <c:if test="${param.maxData == fn:length(orderManagementCollection)}">
             <fmt:message key="label.maxdata">
               <fmt:param value="${param.maxData}"/>
             </fmt:message>
             &nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
        </c:if>
    </div>

	<script type="text/javascript">
    <!--

        /*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
        <c:choose>
            <c:when test="${empty tcmISErrors and empty tcmISError}">
                <c:choose>
                    <c:when test="${param.maxData == fn:length(orderManagementCollection)}">
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
        <c:choose>
            <c:when test="${updateSuccess == 'Y'}">
                updateSuccess = true;
            </c:when>
            <c:otherwise>
                updateSuccess = false;
            </c:otherwise>
        </c:choose>

    //-->
    </script>

	<div class="interface" id="resultsPage">
        <div class="backGroundContent">
            <%-- If the collection is empty say no data found --%>
            <c:if test="${empty orderManagementCollection}" >
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                    <tr>
                        <td width="100%">
                            <fmt:message key="main.nodatafound"/>
                        </td>
                    </tr>
                </table>
            </c:if>

            <div id="orderManagement" style="width: 100%; height: 400px;" style="display: none;"></div>
            <c:if test="${!empty orderManagementCollection}" >
                <c:set var="showUpdateLink" value='N'/>
                <script type="text/javascript">
                <!--
                    var jsonMainData = new Array();
                    var jsonMainData = {
                    rows:[
                        <c:forEach var="orderManagementBean" items="${orderManagementCollection}" varStatus="status">
                            <c:set var="tmpStatus" value='${orderManagementBean.requestLineStatus}'/>
                            <c:set var="colorClass" value=''/>
                            <c:set var="editRow" value='N'/>
                            <c:if test="${tmpStatus == 'Pending Use Approval' || (tmpStatus == 'In Progress' && !empty orderManagementBean.customerRequisitionNumber)}">
                                <c:if test="${tmpStatus == 'Pending Use Approval'}">
                                    <c:set var="tmpStatus"><fmt:message key="label.error"/></c:set>
                                </c:if>
                                <c:set var="editRow" value='Y'/>
                                <c:set var="showUpdateLink" value='Y'/>
                            </c:if>
                            <c:set var="editAllocateDate" value='N'/>
                            <c:if test="${editRow == 'Y' && !empty orderManagementBean.allocateAfter}">
                                <c:set var="editAllocateDate" value='Y'/>
                            </c:if>

                            <c:if test="${!status.first}">,</c:if>
                            {id:${status.count},'class':"${colorClass}",
                                data:[
                                    '${editRow}',
                                    '<tcmis:jsReplace value="${orderManagementBean.customerRequisitionNumber}" processMultiLines="true" />',
                                    '${orderManagementBean.prNumber}',
                                    false,
                                    '${orderManagementBean.lineItem}',
                                    '${tmpStatus}',
                                    '${orderManagementBean.catPartNo}',
                                    '<tcmis:jsReplace value="${orderManagementBean.packaging}" processMultiLines="true" />',
                                    '${orderManagementBean.quantity}',
                                    '<fmt:formatDate value="${orderManagementBean.requiredDatetime}" pattern="${dateFormatPattern}"/>',
                                    '<tcmis:jsReplace value="${orderManagementBean.application}" processMultiLines="true" />',
                                    '<tcmis:jsReplace value="${orderManagementBean.allocateByMfgLot}" processMultiLines="true" />',
                                    '<tcmis:jsReplace value="${orderManagementBean.internalNote}" processMultiLines="true" />',
                                    '<tcmis:jsReplace value="${orderManagementBean.notes}" processMultiLines="true" />',
                                    '<fmt:formatDate value="${orderManagementBean.requestDate}" pattern="${dateTimeFormatPattern}"/>',
                                    '<fmt:formatDate value="${orderManagementBean.lastUpdated}" pattern="${dateTimeFormatPattern}"/>',
                                    '<tcmis:jsReplace value="${orderManagementBean.lastUpdatedByName}" processMultiLines="true" />',
                                    '${orderManagementBean.companyId}',
                                    '${orderManagementBean.requestLineStatus}',
                                    '${editAllocateDate}',
                                    '<fmt:formatDate value="${orderManagementBean.allocateAfter}" pattern="${dateFormatPattern}"/>'
                            ]}
                        </c:forEach>
                  ]};

                    <c:set var="preCustReqNumber" value=''/>
                    <c:set var="dataCount" value='0'/>
                    <c:forEach var="orderManagementBean" items="${orderManagementCollection}" varStatus="status">
                        <c:set var="custReqNumber" value='${orderManagementBean.customerRequisitionNumber}${orderManagementBean.prNumber}'/>
                        <c:choose>
                            <c:when test="${custReqNumber != preCustReqNumber}">
                                lineMap[${status.index}] = 1;
                                <c:set var="preCustReqNumber" value='${orderManagementBean.customerRequisitionNumber}${orderManagementBean.prNumber}'/>
                                <c:set var="dataCount" value="${dataCount + 1}"/>
                                <c:set var="parent" value="${status.index}"/>
                            </c:when>
                            <c:otherwise>
                                lineMap[${parent}]++;
                            </c:otherwise>
                        </c:choose>
                            lineMap3[${status.index}] = ${dataCount % 2};
                    </c:forEach>

                    <c:set var="tmpColl" value='${applicationCollection}'/>
                    <bean:size id="tmpSize" name="tmpColl"/>
                    var application = new Array(
                        <c:if test="${tmpSize > 1}">
                        {
                            text:'<fmt:message key="label.select"/>',
                            value:''
                        },
                        </c:if>
                        <c:forEach var="tmpBean" items="${applicationCollection}" varStatus="status2">
                           <c:if test="${status2.index > 0}">,</c:if>
                           {
                                text:'<tcmis:jsReplace value="${tmpBean.applicationDesc}"/>',
                                value:'<tcmis:jsReplace value="${tmpBean.application}"/>'
                            }
                        </c:forEach>
                    );

                    <c:if test="${showUpdateLink == 'Y'}">
                         showUpdateLinks = true;
                    </c:if>

                //-->
                </script>
            </c:if>

            <div id="transitDialogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
            </div>

            <div id="transitDialogWinBody" class="errorMessages" style="display: none;">
            <table width="100%" border="0" cellpadding="2" cellspacing="1">
             <tr><td>&nbsp;</td></tr>
             <tr><td>&nbsp;</td></tr>
             <tr><td>&nbsp;</td></tr>
             <tr>
              <td align="center" id="transitLabel">
              </td>
             </tr>
             <tr>
              <td align="center">
               <img src="/images/rel_interstitial_loading.gif" align="middle">
              </td>
             </tr>
            </table>
            </div>

            <!-- Hidden element start -->
            <div id="hiddenElements" style="display: none;">
                <input name="totalLines" id="totalLines" value="${fn:length(orderManagementCollection)}" type="hidden"/>
                <input type="hidden" name="userAction" id="userAction" value=""/>
                <input type="hidden" name="prNumber" id="prNumber" value=""/>
                <input type="hidden" name="lineItem" id="lineItem" value=""/>
                <input type="hidden" name="companyId" id="companyId" value='<tcmis:jsReplace value="${param.companyId}"/>'/>
                <input type="hidden" name="facilityId" id="facilityId" value='<tcmis:jsReplace value="${param.facilityId}"/>'/>
                <input type="hidden" name="searchField" id="searchField" value='<tcmis:jsReplace value="${param.searchField}"/>'/>
                <input type="hidden" name="searchMode" id="searchMode" value='<tcmis:jsReplace value="${param.searchMode}"/>'/>
                <input type="hidden" name="searchArgument" id="searchArgument" value='<tcmis:jsReplace value="${param.searchArgument}"/>'/>
                <input type="hidden" name="orderFromDate" id="orderFromDate" value='<tcmis:jsReplace value="${param.orderFromDate}"/>'/>
                <input type="hidden" name="orderToDate" id="orderToDate" value='<tcmis:jsReplace value="${param.orderToDate}"/>'/>
                <!-- Popup Calendar input options -->
                <input type="hidden" name="blockBefore_requiredDatetime" id="blockBefore_requiredDatetime" value='<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>'/>
                <input type="hidden" name="blockAfter_requiredDatetime" id="blockAfter_requiredDatetime" value=""/>
                <input type="hidden" name="blockBeforeExclude_requiredDatetime" id="blockBeforeExclude_requiredDatetime" value=""/>
                <input type="hidden" name="blockAfterExclude_requiredDatetime" id="blockAfterExclude_requiredDatetime" value=""/>
                <input type="hidden" name="inDefinite_requiredDatetime" id="inDefinite_requiredDatetime" value=""/>
                <input type="hidden" name="blockBefore_allocateAfter" id="blockBefore_allocateAfter" value='<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>'/>
                <input type="hidden" name="blockAfter_allocateAfter" id="blockAfter_allocateAfter" value=""/>
                <input type="hidden" name="blockBeforeExclude_allocateAfter" id="blockBeforeExclude_allocateAfter" value=""/>
                <input type="hidden" name="blockAfterExclude_allocateAfter" id="blockAfterExclude_allocateAfter" value=""/>
                <input type="hidden" name="inDefinite_allocateAfter" id="inDefinite_allocateAfter" value=""/>
            </div>
            <!-- Hidden elements end -->
	    </div>
	</div>
</tcmis:form>
</body>
</html:html>