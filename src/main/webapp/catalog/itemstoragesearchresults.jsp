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
        <script	type="text/javascript" src="/js/catalog/itemstoragesearchresults.js"></script>

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

        <title><fmt:message key="itemStorageSearch" /></title>

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
                { columnId:"inventoryGroup",columnName:'<fmt:message key="label.inventorygroup"/>',width:15,tooltip:"Y"},
                { columnId:"itemId",columnName:'<fmt:message key="label.itemid"/>',width:10},
                { columnId:"partId"},
                { columnId:"materialId",columnName:'<fmt:message key="label.materialid"/>'},
                { columnId:"materialDesc",columnName:'<fmt:message key="label.materialdesc"/>',width:27,tooltip:"Y"},
                { columnId:"packaging",columnName:'<fmt:message key="label.packaging"/>',width:20,tooltip:"Y"}
            ];

            var gridConfig = {
                divName:'itemStorage', // the div id to contain the grid.
                beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
                beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
                config:'config',	     // the column config var name,
                rowSpan:true,			 // this page has rowSpan > 1 or not.
                submitDefault:true,    // the fields in grid are defaulted to be submitted or not.,
                noSmartRender: false,
                singleClickEdit:true,
                selectChild: 0,
                onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
                onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
            };

            var messagesData = new Array();
            messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
                validValues:"<fmt:message key="label.validvalues"/>",
                submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
                recordFound:"<fmt:message key="label.recordFound"/>",
                searchDuration:"<fmt:message key="label.searchDuration"/>",
                minutes:"<fmt:message key="label.minutes"/>",
                seconds:"<fmt:message key="label.seconds"/>",
                pleaseWait:"<fmt:message key="label.pleasewait"/>",
                editItemInfo:"<fmt:message key="label.edititeminfo"/>",
                shippingInfo:"<fmt:message key="label.shipinfo"/>",
                itemInteger:"<fmt:message key="error.item.integer"/>"};

            var rowSpanCols = [0,1];
            var lineMap = new Array();
            var lineMap3 = new Array();

            // -->
        </script>
    </head>

    <body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">

    <tcmis:form action="/itemstoragesearchresults.do" onsubmit="return submitFrameOnlyOnce();">

        <!-- Error Messages Begins -->
        <div id="errorMessagesAreaBody" style="display:none;">
                ${tcmISError}<br/>
            <c:forEach items="${tcmISErrors}" varStatus="status">
                ${status.current}<br/>
            </c:forEach>

            <c:if test="${param.maxData == fn:length(itemPartCollection)}">
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
            <c:when test="${param.maxData == fn:length(itemPartCollection)}">
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
                <c:if test="${empty itemPartCollection}" >
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                        <tr>
                            <td width="100%">
                                <fmt:message key="main.nodatafound"/>
                            </td>
                        </tr>
                    </table>
                </c:if>

                <div id="itemStorage" style="width: 100%; height: 400px;" style="display: none;"></div>
                <c:if test="${!empty itemPartCollection}" >
                    <c:set var="showUpdateLink" value='N'/>
                    <script type="text/javascript">
                        <!--
                        var jsonMainData = new Array();
                        var jsonMainData = {
                            rows:[
                                <c:forEach var="itemStorageBean" items="${itemPartCollection}" varStatus="status">
                                    <c:if test="${!status.first}">,</c:if>
                                    {id:${status.count},'class':"${colorClass}",
                                        data:[
                                            '<tcmis:jsReplace value="${itemStorageBean.inventoryGroup}" processMultiLines="true"/>',
                                            '${itemStorageBean.itemId}',
                                            '${itemStorageBean.partId}',
                                            '${itemStorageBean.materialId}',
                                            '<tcmis:jsReplace value="${itemStorageBean.materialDesc}" processMultiLines="true"/>',
                                            '<tcmis:jsReplace value="${itemStorageBean.packaging}" processMultiLines="true"/>'
                                        ]}
                                </c:forEach>
                            ]};

                            <%-- rowspan --%>
                            <c:set var="preKeyValue" value=''/>
                            <c:set var="dataCount" value='0'/>
                            <c:forEach var="itemStorageBean" items="${itemPartCollection}" varStatus="status">
                                <c:set var="currentKeyValue" value='${itemStorageBean.inventoryGroup}${itemStorageBean.itemId}'/>
                                <c:choose>
                                    <c:when test="${currentKeyValue != preKeyValue}">
                                        lineMap[${status.index}] = 1;
                                        <c:set var="preKeyValue" value='${currentKeyValue}'/>
                                        <c:set var="dataCount" value="${dataCount + 1}"/>
                                        <c:set var="parent" value="${status.index}"/>
                                    </c:when>
                                    <c:otherwise>
                                        lineMap[${parent}]++;
                                    </c:otherwise>
                                </c:choose>
                                lineMap3[${status.index}] = ${dataCount % 2};
                            </c:forEach>

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
                    <input name="totalLines" id="totalLines" value="${fn:length(itemPartCollection)}" type="hidden"/>
                    <input type="hidden" name="userAction" id="userAction" value=""/>
                </div>
                <!-- Hidden elements end -->
            </div>
        </div>
    </tcmis:form>
    </body>
</html:html>