<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

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
<%@ include file="/common/locale.jsp"%> <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss /> <!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script	type="text/javascript" src="/js/common/commonutil.js"></script> <%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script> <!-- This handels the menu style and what happens to the right click on the whole page -->

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script	type="text/javascript" src="/js/hub/receivingdocumentreportresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script> <%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script> <%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script> <%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script> <%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rowspans > --%>
<%--
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
--%>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title><fmt:message key="label.receivingdocumentreport" /></title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

with(milonic=new menuname("receivingDocMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=;url=javascript:doNothing();image=");
}

drawMenus();

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
     {
    	columnId:"receiptId",
    	columnName:'<fmt:message key="label.receiptid"/>',
    	width:8
     },
     {
       	columnId:"mfgLot",
       	columnName:'<fmt:message key="label.mfglot"/>',
      	width:13
     },
     {
    	columnId:"dateQc",
    	columnName:'<fmt:message key="label.qceddate"/>',
    	hiddenSortingColumn:'hiddenQcDateTime',
    	width:12
     },
     {
        columnId:"hiddenQcDateTime",
        sorting:'int'
     },
     {
        columnId:"qcedBy",
        columnName:'<fmt:message key="label.qcedby"/>',
        width:10 
     },
     {
        columnId:"documentType",
        columnName:'<fmt:message key="label.type"/>',
        width:12
     },
     {
        columnId:"document",
        columnName:'<fmt:message key="label.document"/>',
        width:25
     },
     {
        columnId:"documentUrl"
     },
     {
        columnId:"documentId"
     },
     {
       columnId:"companyId"
     }
  ];

var gridConfig = {
	divName:'receivingDocumentReport', // the div id to contain the grid.
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

// -->

</script>

<script language="JavaScript" type="text/javascript">
<!--
    var messagesData = new Array();
    messagesData={alert:"<fmt:message key="label.alert"/>",
        and:"<fmt:message key="label.and"/>",
        validvalues:"<fmt:message key="label.validvalues"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        recordFound:"<fmt:message key="label.recordFound"/>",
        searchDuration:"<fmt:message key="label.searchDuration"/>",
        minutes:"<fmt:message key="label.minutes"/>",
        seconds:"<fmt:message key="label.seconds"/>",
        viewDoc:"<fmt:message key="label.viewreceiptdocument"/>",
        itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

<tcmis:form action="/receivingdocumentreportresults.do" onsubmit="return submitFrameOnlyOnce();">

	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;"><html:errors />
	</div>

	<script type="text/javascript">
    <!--
        /*YAHOO.namespace("example.aqua");
        YAHOO.util.Event.addListener(window, "load", init);*/

        /*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
        <c:choose>
           <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
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
	<div id="receivingDocumentReport" style="width: 100%; height: 400px;" style="display: none;"></div>

    <c:if test="${!empty receivingDocumentReportCollection}" >
        <script type="text/javascript">
        <!--
            var jsonMainData = new Array();
            var jsonMainData = {
            rows:[
                <c:forEach var="ReceivingDocumentReportBean" items="${receivingDocumentReportCollection}" varStatus="status">
                <c:if test="${!status.first}">,</c:if>
                    {id:${status.count},
                    data:[
                    '${ReceivingDocumentReportBean.receiptId}',
                    '${ReceivingDocumentReportBean.mfgLot}',
                    '<fmt:formatDate value="${ReceivingDocumentReportBean.qcDate}" pattern="${dateTimeFormatPattern}"/>',
                    '${ReceivingDocumentReportBean.qcDate}',
                    '${ReceivingDocumentReportBean.qcUserName}',
                    '${ReceivingDocumentReportBean.documentType}',
                    '${ReceivingDocumentReportBean.documentName}',
                    '${ReceivingDocumentReportBean.documentUrl}',
                    '${ReceivingDocumentReportBean.documentId}',
                    '${ReceivingDocumentReportBean.companyId}'
                    ]}
                </c:forEach>
          ]};
        //-->
        </script>
    </c:if>

    <c:if test="${empty receivingDocumentReportCollection}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			<tr>
				<td width="100%"><fmt:message key="main.nodatafound"/></td>
			</tr>
		</table>
	</c:if>

	<!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;">
	    <input name="totalLines" id="totalLines" value="${fn:length(receivingDocumentReportCollection)}" type="hidden">
	    <input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer', '',personnelBean.companyId)}'/>
	</div>
	<!-- Hidden elements end -->
	</div>
	</div>
</tcmis:form>
</body>
</html:html>