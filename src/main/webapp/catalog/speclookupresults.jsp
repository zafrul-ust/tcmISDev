<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
        <script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
        <%@ include file="/common/rightclickmenudata.jsp" %>

        <%-- Form popup Calendar support --%>
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
		<%-- Grid support --%>
		<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<%-- Page specific javascript --%>
	
		<title>
			<fmt:message key="label.speclookup"/>
		</title>
		
		<%-- NOTE: The only javascript here rather than in a page specific js file is javascript that contains values from JSP --%>
		<script language="JavaScript" type="text/javascript">

			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData = {
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				recordFound:"<fmt:message key="label.recordFound"/>",
				searchDuration:"<fmt:message key="label.searchDuration"/>",
				minutes:"<fmt:message key="label.minutes"/>",
				seconds:"<fmt:message key="label.seconds"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
                viewSpecification:"<fmt:message key="label.viewspecification"/>",
                addNewSpec:"<fmt:message key="label.addnewspec"/>",
                returnSelected:"<fmt:message key="label.returnselected"/>",
                submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
	
			

			<%-- Define the columns for the result grid --%>
			var columnConfig = [
				{columnId:"specId", columnName:'<fmt:message key="label.specid"/>', tooltip:"Y", width:5},
 				{columnId:"specName", columnName:'<fmt:message key="label.specname"/>', tooltip:"Y", width:8 },
                {columnId:"specTitle", columnName:'<fmt:message key="label.spectitle"/>', tooltip:"Y", width:8 },
                {columnId:"specVersion", columnName:'<fmt:message key="label.specversion"/>', align:'center', width:3},
                {columnId:"specAmendment", columnName:'<fmt:message key="label.specamendment"/>', align:'center', width:3},
                {columnId:"onLine", columnName:'<fmt:message key="label.viewonline"/>', align:'center', width:3},
                {columnId:"itar", columnName:'<fmt:message key="label.itar"/>', align:'center', width:3},
                {columnId:"specLibraryDesc", columnName:'<fmt:message key="label.speclibrary"/>', tooltip:"Y", width:8 },
                {columnId:"specLibrary"}
            ];

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'specLookupGridDiv',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
                onRightClick:rightClickRow, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
                onRowSelect:selectedRow,
                rowSpan: false 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
			};
					

			<%-- Check for passed error message that will require an error inline popup --%>
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>

            function rightClickRow(rowId,cellInd) {
                var aitems = new Array();
                if (cellValue(rowId,"onLine") == 'Y') {
                    aitems[aitems.length] = "text="+messagesData.viewSpecification+";url=javascript:viewSpec();";
                }else {
                    aitems[aitems.length] = "text=";    
                }
                replaceMenu('viewSpecMenu',aitems);
	            toggleContextMenu('viewSpecMenu');
            }

            function viewSpec() {
                var tmpUrl = '';
                if ($v("secureDocViewer") == 'true')
                    tmpUrl = '/DocViewer/client/';
                openWinGeneric(tmpUrl + 'docViewer.do?uAction=viewSpec' +'&spec=' + encodeURIComponent(cellValue(mygrid.getSelectedRowId(),"specId"))+"&companyId="+$v("companyId"),"ViewSpec","800","800",'yes' );
            }

            function replaceMenu(menuname,menus){
                var i = mm_returnMenuItemCount(menuname);
                for(;i> 1; i-- )
                    mm_deleteItem(menuname,i);

                for( i = 0; i < menus.length; ){
                    var str = menus[i];
                    i++;
                    mm_insertItem(menuname,i,str);
                    // delete original first item.
                    if( i == 1 ) mm_deleteItem(menuname,1);
               }
            }

            function selectedRow(rowId,cellInd) {
                if (parent.$v("calledFrom") == 'catalogAddSpecQc') {
                    var tmpSpecId = cellValue(mygrid.getSelectedRowId(),"specId");
                    //these two value is if user want to add selected spec to request
                    parent.$("specId").value = tmpSpecId;
                    parent.$("specLibrary").value = cellValue(mygrid.getSelectedRowId(),"specLibrary");
                    parent.$("mainUpdateLinks").innerHTML = '<a href="#" onclick="addNewSpec(); return false;">'+messagesData.addNewSpec+'</a>'+
                                                            ' | <a href="#" onclick="addSelectedSpec(); return false;">'+messagesData.returnSelected+': '+tmpSpecId+'</a>'
                    parent.$("mainUpdateLinks").style["display"] = "";
                }else {
                    parent.$("mainUpdateLinks").style["display"] = "none";
                }
            }

            function editOnLoad() {
                if (parent.$v("calledFrom") == 'catalogAddSpecQc') {
                    parent.$("mainUpdateLinks").innerHTML = '<a href="#" onclick="addNewSpec(); return false;">'+messagesData.addNewSpec+'</a>';
                    parent.$("mainUpdateLinks").style["display"] = "";
                }else {
                    parent.$("mainUpdateLinks").style["display"] = "none";
                }
            }

            with ( milonic=new menuname("viewSpecMenu") ) {
             top="offset=2";
             style=contextStyle;
             margin=3
             aI("text=<fmt:message key="label.viewspecification"/>;url=javascript:viewSpec();");
            }

            drawMenus();

            //-->
		</script>
	</head>

	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig); editOnLoad();">
		<tcmis:form action="/speclookupresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
    				${tcmISError}
    				<c:forEach var="tcmisError" items="${tcmISErrors}" >
  					${tcmisError}<br/>
    				</c:forEach>        
			</div>

			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<div id="specLookupGridDiv" style="width:100%;height:400px;" style="display: none;"></div>
					<c:choose>
						<c:when test="${empty searchResults}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
								<tr>
									<td width="100%">
										<fmt:message key="main.nodatafound"/>
									</td>
								</tr>
							</table>
						</c:when>				
						<c:otherwise>
							<script type="text/javascript">
								<%-- Loop through the results and output each row, formatting the results as necessary 
								     use tcmis:jsReplace to escape special characters for ANY user entered text
								     use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
								     use mt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
								<!--
								var jsonMainData = new Array();
								jsonMainData = {
									rows:[ <c:forEach var="row" items="${searchResults}" varStatus="status">
										{ id:${status.count}, <%-- use status.count rather than status.index because the grid requires a 1 based index rather than 0 based --%>
										<%--  Set the color to the rows according to their releaseStatus --%>
										 data:[
											 '<tcmis:jsReplace value="${row.specId}" />',
											 '<tcmis:jsReplace value="${row.specName}" processMultiLines="true" />',
                                             '<tcmis:jsReplace value="${row.specTitle}" processMultiLines="true" />',
                                             '${row.specVersion}',
                                             '${row.specAmendment}',
                                             '${row.onLine}',
                                             '${row.itar}',    
                                             '<tcmis:jsReplace value="${row.specLibraryDesc}" />',    
                                             '<tcmis:jsReplace value="${row.specLibrary}" />'
                                            ]}
                                        <c:if test="${!status.last}">,</c:if>
	 									</c:forEach>
									]};
								//-->   
							</script>
						</c:otherwise>
					</c:choose>

					<div id="hiddenElements" style="display: none;">
						<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(searchResults)}"/>" type="hidden"/>
						<input name="minHeight" id="minHeight" type="hidden" value="100"/>
                        <input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','', personnelBean.companyId)}'/>
                        <input type="hidden" name="companyId" id="companyId" value='${personnelBean.companyId}'/>
                    </div>
				</div>
			</div>
		</tcmis:form>
	</body>
</html:html>