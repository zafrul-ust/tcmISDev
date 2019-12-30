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
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="expires" content="-1" />
    <link rel="shortcut-icon"
        href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%-- For Internationalization, copies data from calendarval.js --%>
    <%@ include file="/common/locale.jsp"%>
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
    <%@ include file="/common/rightclickmenudata.jsp"%>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
    <script type="text/javascript"
        src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
    <script type="text/javascript"
        src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
    <script type="text/javascript"
        src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
        
    <title>Inventory Search Results</title>
    <script language="JavaScript" type="text/javascript" />
    <%-- Define the columns for the result grid --%> 
    <!--
    var resizeGridWithWindow = true;
    var messagesData = {};
   
        var columnConfig = [            
            {columnId:'company', columnName:'<fmt:message key="label.company"/>', width:16},
            {columnId:'ilsAlternatePartNumber', columnName:'<fmt:message key="label.alternatepartnumber"/>', width:15},
            {columnId:'ilsPartNumberDescription', columnName:'<fmt:message key="label.partdescription"/>', width:15},
            {columnId:'quantity', columnName:'<fmt:message key="label.quantity"/>'},
            {columnId:'conditionCode', columnName:'<fmt:message key="label.conditioncode"/>'},
            {columnId:'accreditedVendorLevel' , columnName:'<fmt:message key="label.accreditedvendorlevel"/>', width:15},
            {columnId:'supplierCage', columnName:'<fmt:message key="label.suppliercagecode"/>' },
            {columnId:'companyAddress', columnName:'<fmt:message key="label.companyaddress"/>', width:35},
            {columnId:'salesContactName', columnName:'<fmt:message key="label.salescontactname"/>', width:15 },
            {columnId:'salesContactTitle', columnName:'<fmt:message key="label.salescontacttitle"/>', width:10 },
            {columnId:'salesContactEmail', columnName:'<fmt:message key="label.salescontactemail"/>', width:20 },
            {columnId:'salesContactPhone', columnName:'<fmt:message key="label.salescontactphone"/>' },
            {columnId:'salesContactFax', columnName:'<fmt:message key="label.salescontactfax"/>' },
            {columnId:'rfqContactName', columnName:'<fmt:message key="label.rfqnontactname"/>', width:15 },
            {columnId:'rfqContactTitle', columnName:'<fmt:message key="label.rfqnontacttitle"/>', width:10 },
            {columnId:'rfqContactEmail', columnName:'<fmt:message key="label.rfqnontactemail"/>', width:20 },
            {columnId:'rfqContactPhone', columnName:'<fmt:message key="label.rfqnontactphone"/>' },
            {columnId:'rfqContactFax', columnName:'<fmt:message key="label.rfqnontactfax"/>' }                 
        ];
    
        <%-- Define the grid options--%>
        var gridConfig = {
            divName:    'displayOnlyGridDiv',   <%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
            beanData:   'jsonMainData',         <%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
            beanGrid:   'beanGrid',               <%--  variable to put the grid object in for later use --%>
            config:     'columnConfig',         <%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
            rowSpan:    false                   <%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>        
        };
        
    function resultOnLoad() {
        totalLines = document.getElementById("totalLines").value;
         if (totalLines > 0)
         {
            document.getElementById("displayOnlyGridDiv").style["display"] = "";
            initGridWithGlobal(gridConfig);
         }
         else
         {
           document.getElementById("displayOnlyGridDiv").style["display"] = "none";   
         }
         //displayGridSearchDuration(); 
         setResultFrameSize(); 
         
         parent.document.getElementById("partNum").innerHTML = document.getElementById("partNum").value;
         parent.document.getElementById("updateDate").innerHTML = document.getElementById("updateDate").value;
    }
    //-->
    </script>
</head>
<body onload="resultOnLoad();">
    <tcmis:form action="/partavailabilityresults.do" onsubmit="return submitFrameOnlyOnce();">
        <div id="errorMessagesAreaBody" style="display: none;">
            ${tcmISError}<br />
            <c:forEach var="tcmisError" items="${tcmISErrors}">
                                ${tcmisError}<br />
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
                
        </script>
        <div class="interface" id="resultsPage">
            <div class="backGroundContent">
                <div id="displayOnlyGridDiv"
                     style="width: 100%; height: 300px;"
                     style="display:none;"></div>
                    <c:if test="${partsAvailabilityList != null}">
                        <script type="text/javascript">
                            <c:set var="dataCount" value="${0}"/>
                            <c:if test="${!empty partsAvailabilityList}" >
                                var jsonMainData = new Array();
                                jsonMainData = { rows:[ <c:forEach var="row" items="${partsAvailabilityList}" varStatus="status">
                                                            { id:${status.count},                                 
                                                             data:[
                                                                    '<tcmis:jsReplace value="${row.company}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.ilsAlternatePartNumber}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.ilsPartNumberDescription}" processMultiLines="true" />', 
                                                                    '<tcmis:jsReplace value="${row.quantity}" processMultiLines="false" />',       
                                                                    '<tcmis:jsReplace value="${row.conditionCode}" processMultiLines="false" />',                                                                                                                                        
                                                                   <c:choose>
                                                                    <c:when test="${row.accreditedVendorLevel == 'AVP'}">
                                                                     '<fmt:message key="label.platinumaccreditedvendor"/>',
                                                                    </c:when>
                                                                     <c:when test="${row.accreditedVendorLevel == 'AVG'}">
                                                                     '<fmt:message key="label.goldaccreditedvendor"/>',
                                                                    </c:when>
                                                                     <c:when test="${row.accreditedVendorLevel == 'AVS'}">
                                                                     '<fmt:message key="label.silveraccreditedvendor"/>',
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                     '', 
                                                                    </c:otherwise>
                                                                   </c:choose>   
                                                                    '<tcmis:jsReplace value="${row.supplierCage}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.companyAddress}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.salesContactName}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.salesContactTitle}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.salesContactEmail}" processMultiLines="false" />',                                                                    
                                                                    '<tcmis:jsReplace value="${row.salesContactPhone}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.salesContactFax}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.rfqContactName}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.rfqContactTitle}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.rfqContactEmail}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.rfqContactPhone}" processMultiLines="false" />',
                                                                    '<tcmis:jsReplace value="${row.rfqContactFax}" processMultiLines="false" />'                                                                                                                                                                                                           
                                                                  ]}<c:if test="${!status.last}">,</c:if>
                                                                    <c:set var="dataCount" value="${dataCount + 1}"/>
                                                        </c:forEach>
                                                      ]};
                            </c:if>
                        </script>
                        <!-- If the collection is empty say no data found -->
                        <c:if test="${empty partsAvailabilityList}">
                            <table width="100%" border="0" cellpadding="0"
                                   cellspacing="0" class="tableNoData"
                                   id="resultsPageTable">
                                <tr>
                                    <td width="100%"><fmt:message key="main.nodatafound" /></td>
                                </tr>
                            </table>
                        </c:if>
                        <!-- Search results end -->
                    </c:if>
                    <!-- Hidden Element start -->
                    <div id="hiddenElements" style="display: none">
                        <input type="hidden" name="uAction" id="uAction" value="search" />
                        <input type="hidden" name="updateDate" id="updateDate" value="${updateDate}" />
                        <input type="hidden" name="partNum" id="partNum" value="${partNum}" />
                        <input name="totalLines" id="totalLines" type="hidden" value="${dataCount}" />
                        <input name="minHeight" id="minHeight" type="hidden" value="500" />
                    </div>
            </div>
            <!-- close of backgroundContent -->
        </div>
        <!-- close of interface -->
    </tcmis:form>
</body>
</html:html>
