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

<!-- Add any other javascript you need for the page here -->
<script	type="text/javascript" src="/js/supplier/cylindermanagementresults.js"></script>

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

<title><fmt:message key="label.cylindermanagement" /></title>

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
        columnId:"dateReceived",
        columnName:'<fmt:message key="label.datereceived"/>',
        width:8,
        submit:false
     },
     {
    	columnId:"serialNumber",
    	columnName:'<fmt:message key="label.serialnumber"/>',
    	width:10
     },
     {
        columnId:"displayCylinderStatus",
        columnName:'<fmt:message key="label.cylinderstatus"/>',
        width:5
     },
     {
    	columnId:"dotRating",
    	columnName:'<fmt:message key="label.dotrating"/>',
    	width:10
     },
     {
    	columnId:"dimensions",
    	columnName:'<fmt:message key="label.dimensions"/>',
    	width:10
     },
     {
    	columnId:"gasType",
    	columnName:'<fmt:message key="label.gastype"/>',
    	width:10
     },
     {
    	columnId:"vendorPartNo",
    	columnName:'<fmt:message key="label.nsn8120"/>',
    	width:10
     },
     {
    	columnId:"correspondingNsn",
    	columnName:'<fmt:message key="label.nsn6830"/>',
    	width:10
     },
     {
        columnId:"conversionGroup",
        columnName:'<fmt:message key="label.conversiongroup"/>',
        width:7
     },
     {
        columnId:"cylinderConditionCode",
        columnName:'<fmt:message key="label.conditioncode"/>',
        width:7
     },
     {
        columnId:"capacity",
        columnName:'<fmt:message key="label.capacity"/>',
        width:7
     },
     {
        columnId:"manufacturerName",
        columnName:'<fmt:message key="label.manufacturer"/>',
        tooltip:"Y",
        width:15
     },
     {
        columnId:"latestHydroTestDate",
        columnName:'<fmt:message key="label.latesthydrotestdate"/>',
        width:7
     },
     {
        columnId:"returnFromLocation",
        columnName:'<fmt:message key="label.returnfromlocation"/>',
        width:7
     },
     {
        columnId:"lastShippedDodaac",
        columnName:'<fmt:message key="label.lastshippeddodaac"/>',
        width:7
     },
     {
        columnId:"dateSentOut",
        columnName:'<fmt:message key="label.datesentout"/>',
        width:7
     },
     {
        columnId:"dateDisposed",
        columnName:'<fmt:message key="label.datedisposed"/>',
        width:7
     },
     {
       	columnId:"locationDesc",
       	columnName:'<fmt:message key="label.location"/>',
       	tooltip:"Y",
      	width:15
     },
     {
        columnId:"receivedBy",
        columnName:'<fmt:message key="label.receivedby"/>',
        width:10
     },
     {
        columnId:"documentNumber",
        columnName:'<fmt:message key="label.documentnumber"/>',
        tooltip:"Y",
        width:10
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
        columnId:"notes",
        columnName:'<fmt:message key="label.notes"/>',
        tooltip:"Y",
        width:15
     },
     {
        columnId:"cylinderTrackingStatus"
     },
     {
        columnId:"cylinderStatus"
     },
     {
        columnId:"refurbCategory"
     },
     {
        columnId:"printBarcode",
        type:'hchstatus',
        align:"center",
        width:10
     },
     {
       columnId:"locationId"
     },
     {
       columnId:"rowWithUpdate"
     },
     {
       columnId:"supplier"
     },
     {
       columnId:"supplierName"
     },
     {
       columnId:"cylinderTrackingId"
     },
     {
        columnId:"manufacturerIdNo"
     },
     {
        columnId:"status"
     },
     {
        columnId:"companyId"
     }
  ];

var gridConfig = {
	divName:'cylinderManagement', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name,
	rowSpan:false,			 // this page has rowSpan > 1 or not.
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
        updateRefurbSubcategory:"<fmt:message key="label.updaterefurbactivity"/>",
        viewHistory:"<fmt:message key="label.viewcylinderdetail"/>",
        editCylinder:"<fmt:message key="label.editcylinder"/>",
        pleasewait:"<fmt:message key="label.pleasewait"/>",
        noRowWasSelected:"<fmt:message key="label.norowwasselected"/>",
        manageDocumentsImages:"<fmt:message key="label.managedocumentsimages"/>",
        itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/cylindermanagementresults.do" onsubmit="return submitFrameOnlyOnce();">

	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
        ${tcmISError}<br/>
        <c:forEach items="${tcmISErrors}" varStatus="status">
            ${status.current}<br/>
        </c:forEach>

         <c:if test="${param.maxData == fn:length(cylinderManagementCollection)}">
             <fmt:message key="label.maxdata">
               <fmt:param value="${param.maxData}"/>
             </fmt:message>
             &nbsp;<fmt:message key="label.clickcreateexcelforcompleteresult"/>
        </c:if>
    </div>

	<script type="text/javascript">
    <!--

        showUpdateLinks = false;

        /*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
        <c:choose>
            <c:when test="${empty tcmISErrors and empty tcmISError}">
                <c:choose>
                    <c:when test="${param.maxData == fn:length(cylinderManagementCollection)}">
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
            <c:if test="${empty cylinderManagementCollection}" >
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                    <tr>
                        <td width="100%">
                            <fmt:message key="main.nodatafound"/>
                        </td>
                    </tr>
                </table>
            </c:if>

            <div id="cylinderManagement" style="width: 100%; height: 400px;" style="display: none;"></div>
            <c:if test="${!empty cylinderManagementCollection}" >
                <script type="text/javascript">
                <!--
                    var jsonMainData = new Array();
                    var jsonMainData = {
                    rows:[
                        <c:forEach var="cylinderManagementBean" items="${cylinderManagementCollection}" varStatus="status">
                            <c:set var="tmpStatus"><fmt:message key="label.active"/></c:set>
                            <c:if test="${cylinderManagementBean.status == 'I'}">
                                <c:set var="tmpStatus"><fmt:message key="label.inactive"/></c:set>
                            </c:if>

                            <c:if test="${!status.first}">,</c:if>
                            {id:${status.count},
                            data:[
                            'N',
                            '<fmt:formatDate value="${cylinderManagementBean.dateReceived}" pattern="${dateTimeFormatPattern}"/>',
                            '<tcmis:jsReplace value="${cylinderManagementBean.serialNumber}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.cylinderStatus}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.dotRating}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.dimensions}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.gasType}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.vendorPartNo}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.correspondingNsn}" processMultiLines="true" />',
                            '${cylinderManagementBean.conversionGroup}',
                            '${cylinderManagementBean.cylinderConditionCode}',
                            '<tcmis:jsReplace value="${cylinderManagementBean.capacity}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.manufacturerName}" processMultiLines="true" />',
                            '<fmt:formatDate value="${cylinderManagementBean.latestHydroTestDate}" pattern="${dateFormatPattern}"/>',
                            '<tcmis:jsReplace value="${cylinderManagementBean.returnFromLocation}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.lastShippedDodaac}" processMultiLines="true" />',
                            '<fmt:formatDate value="${cylinderManagementBean.dateSentOut}" pattern="${dateTimeFormatPattern}"/>',
                            '<fmt:formatDate value="${cylinderManagementBean.dateDisposed}" pattern="${dateTimeFormatPattern}"/>',
                            '<tcmis:jsReplace value="${cylinderManagementBean.locationDesc}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.receivedByName}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.documentNumber}" processMultiLines="true" />',
                            '<fmt:formatDate value="${cylinderManagementBean.lastUpdated}" pattern="${dateTimeFormatPattern}"/>',
                            '<tcmis:jsReplace value="${cylinderManagementBean.lastUpdatedByName}" processMultiLines="true" />',
                            '<tcmis:jsReplace value="${cylinderManagementBean.notes}" processMultiLines="true" />',
                            '${tmpStatus}',
                            '${cylinderManagementBean.cylinderStatus}',
                            '${cylinderManagementBean.refurbCategory}',
                            "false",
                            '${cylinderManagementBean.locationId}',
                            '',
                            '${cylinderManagementBean.supplier}',
                            '<tcmis:jsReplace value="${cylinderManagementBean.supplierName}" processMultiLines="true" />',
                            '${cylinderManagementBean.cylinderTrackingId}',
                            '${cylinderManagementBean.manufacturerIdNo}',
                            '${cylinderManagementBean.status}',
                            '${cylinderManagementBean.companyId}'
                            ]}
                        </c:forEach>
                  ]};

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
                <input name="totalLines" id="totalLines" value="${fn:length(cylinderManagementCollection)}" type="hidden">
                <input type="hidden" name="userAction" id="userAction" value="">
                <input type="hidden" name="facilityId" id="facilityId" value='<tcmis:jsReplace value="${param.facilityId}"/>'>
                <input type="hidden" name="searchField" id="searchField" value='<tcmis:jsReplace value="${param.searchField}"/>'>
                <input type="hidden" name="searchMode" id="searchMode" value='<tcmis:jsReplace value="${param.searchMode}"/>'>
                <input type="hidden" name="searchArgument" id="searchArgument" value='<tcmis:jsReplace value="${param.searchArgument}"/>'>
            </div>
            <!-- Hidden elements end -->
	    </div>
	</div>
</tcmis:form>
</body>
</html:html>