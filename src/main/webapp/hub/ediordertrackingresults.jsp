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
<script	type="text/javascript" src="/js/hub/ediordertrackingresults.js"></script>

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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<title><fmt:message key="label.ediordertracking" /></title>

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
    	columnId:"customerPoNo",
    	columnName:'<fmt:message key="label.ordernumber"/>',
    	width:8
     },
     {
       	columnId:"customerPoLineNo",
       	columnName:'<fmt:message key="label.orderline"/>',
      	width:5
     },
     {
        columnId:"catPartNo",
        columnName:'<fmt:message key="label.partnumber"/>',
        width:10
     },
     {
        columnId:"partShortDesc",
        columnName:'<fmt:message key="label.description"/>',
        tooltip:"Y",
        width:18
     },
     {
        columnId:"quantity",
        columnName:'<fmt:message key="label.quantity"/>',
        width:5
     },
     {
        columnId:"orderPartUom",
        columnName:'<fmt:message key="label.uom"/>',
        width:3
     },
     {
        columnId:"companyName",
        columnName:'<fmt:message key="label.company"/>',
        width:5
     },
     {
        columnId:"facilityName",
        columnName:'<fmt:message key="label.facility"/>',
        width:8
     },
     {
        columnId:"shiptoCity",
        columnName:'<fmt:message key="label.shiptocity"/>',
        width:8
     },
     {
        columnId:"status",
        columnName:'<fmt:message key="label.status"/>',
        width:5
     },
     {
        columnId:"statusDetail",
        columnName:'<fmt:message key="label.statusdetail"/>',
        tooltip:"Y",
        width:13
     },
     {
        columnId:"catalogAddRequestId",
        columnName:'<fmt:message key="label.catalogaddrequestid"/>',
        width:8
     },
     {
       columnId:"catalogCompanyId"
     },
     {
       columnId:"catalogId"
     },
     {
       columnId:"companyId"
     }
     ,
     {
       columnId:"facilityId"
     }
  ];

var gridConfig = {
	divName:'ediOrderTracking', // the div id to contain the grid.
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
        editUpdateEdiError:"<fmt:message key="label.editupdateedierror"/>",
        pleasewait:"<fmt:message key="label.pleasewait"/>",
        itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/ediordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();">

	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
        ${tcmISError}<br/>
        <c:forEach items="${tcmISErrors}" varStatus="status">
            ${status.current}<br/>
        </c:forEach>

         <c:if test="${param.maxData == fn:length(ediOrderTrackingCollection)}">
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
                    <c:when test="${param.maxData == fn:length(ediOrderTrackingCollection)}">
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
            <div id="ediOrderTracking" style="width: 100%; height: 400px;" style="display: none;"></div>
            <c:if test="${!empty ediOrderTrackingCollection}" >
                <script type="text/javascript">
                <!--
                    var jsonMainData = new Array();
                    var jsonMainData = {
                    rows:[
                        <c:forEach var="EdiOrderTrackingBean" items="${ediOrderTrackingCollection}" varStatus="status">
                        <c:if test="${!status.first}">,</c:if>
                            {id:${status.count},
                            data:[
                            'Y',
                            '${EdiOrderTrackingBean.customerPoNo}',
                            '${EdiOrderTrackingBean.customerPoLineNo}',
                            '${EdiOrderTrackingBean.catPartNo}',
                            '<tcmis:jsReplace value="${EdiOrderTrackingBean.partShortDesc}" processMultiLines="true" />',
                            '${EdiOrderTrackingBean.quantity}',
                            '${EdiOrderTrackingBean.orderPartUom}',
                            '${EdiOrderTrackingBean.companyName}',
                            '${EdiOrderTrackingBean.facilityName}',
                            '<tcmis:jsReplace value="${EdiOrderTrackingBean.shiptoCity}" processMultiLines="true" />',
                            '${EdiOrderTrackingBean.status}',
                            '<tcmis:jsReplace value="${EdiOrderTrackingBean.errorDetail}" processMultiLines="true" />',
                            '${EdiOrderTrackingBean.catalogAddRequestId}',
                            '${EdiOrderTrackingBean.catalogCompanyId}',
                            '${EdiOrderTrackingBean.catalogId}',
                            '${EdiOrderTrackingBean.companyId}',
                            '${EdiOrderTrackingBean.facilityId}'
                            ]}
                        </c:forEach>
                  ]};
                //-->
                </script>
            </c:if>

            <%-- If the collection is empty say no data found --%>
            <c:if test="${empty ediOrderTrackingCollection}" >
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                    <tr>
                        <td width="100%">
                            <fmt:message key="main.nodatafound"/>
                        </td>
                    </tr>
                </table>
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
                <input name="totalLines" id="totalLines" value="${fn:length(ediOrderTrackingCollection)}" type="hidden">
                <input type="hidden" name="userAction" id="userAction" value="">
                <input type="hidden" name="companyId" id="companyId" value="${param.companyId}">
                <input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}">
                <input type="hidden" name="searchField" id="searchField" value="${param.searchField}">
                <input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}">
                <input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}">
                <input type="hidden" name="onlyDisplayCatalogAdd" id="onlyDisplayCatalogAdd" value="${param.onlyDisplayCatalogAdd}">
                <input type="hidden" name="onlyDisplayEdiError" id="onlyDisplayEdiError" value="${param.onlyDisplayEdiError}">
            </div>
            <!-- Hidden elements end -->
	    </div>
	</div>
</tcmis:form>
</body>
</html:html>