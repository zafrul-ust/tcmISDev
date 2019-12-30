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
	
			<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>	
	
		<%-- Grid support --%>
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
		
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js"></script>
		<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
		<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
		
	
		<title>
			<fmt:message key="adhocusagereport.label.materialcategory"/>
		</title>
		
		<%-- NOTE: The only javascript here rather than in a page specific js file is javascript that contains values from JSP --%>
		<script language="JavaScript" type="text/javascript">
			<!-- <%-- Use the InventoryGroupPermission taglib to determine whether the user has permission to see/use the update links for THIS inventory group --%>
			// to perform the check all function in the header.
			var windowCloseOnEsc = true;
			function checkAll(checkboxname) {
				var checkall = $("selectAll");
				var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid
				var columnId = mygrid.getColIndexById(checkboxname);

				if (checkall.checked) {
					for ( var rowId = 1; rowId <= rowsNum; rowId++) {
							//if(cellValue(rowId, "permission") == 'Y') 
							{
							var curCheckBox = checkboxname + rowId;
							if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
								$(curCheckBox).checked = true;
								updateHchStatusA(curCheckBox);
							}
							else { // The HTML element hasn't been drawn yet, update the JSON data directly
								mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = true;
							}
						}
					}
				}
				else {
					for ( var rowId = 1; rowId <= rowsNum; rowId++) {
						//if(cellValue(rowId, "permission") == 'Y') 
							{
							var curCheckBox = checkboxname + rowId;
							if ($(curCheckBox)) {  // Make sure the row has been rendered and the element exists
								$(curCheckBox).checked = false;
								updateHchStatusA(curCheckBox);
							}
							else { // The HTML element hasn't been drawn yet, update the JSON data directly
								mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = false;
							}
						}
					}
				}
				return true;
			}

			function selectMatCat()
			{
				//mygrid.sortRows(4,"int","asc");
				var idString = '';
				var nameString = '';
				var rowsNum = mygrid.getRowsNum();
			  	for ( var i = 0; i < rowsNum; i++) {
			  		rowId = mygrid.getRowId(i);
					var curCheckBox = 'select' + rowId;
					if ($(curCheckBox) && $(curCheckBox).checked == true) { // Make sure the row has been rendered and the element exists
						if(idString == '')
						{
							idString += cellValue(rowId, "materialCategoryId");
							nameString += cellValue(rowId, "materialCategoryName");
						}
						else
						{
							idString += '|' + cellValue(rowId, "materialCategoryId");
							nameString += ';' + cellValue(rowId, "materialCategoryName");
						}	
					}

			  	}
				
			  	/*if(idString.length == 0) {
			  		alert(messagesData.norowselected);
			  		return false;
			  	}*/
				
			  	window.opener.setMatCat(idString,nameString);
			  	window.close();
			}

			function preSelect()
			{
				 var idArray=$v("selctedVals").split("|");
				 var rowsNum = mygrid.getRowsNum();
				 for ( var i=0; i < idArray.length; i++) {
				  	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
						if (idArray[i] == cellValue(rowId, "materialCategoryId")) { 
							var curCheckBox = 'select' + rowId;
							$(curCheckBox).checked = true;
							updateHchStatusA(curCheckBox);
							//mygrid.cells(rowId, mygrid.getColIndexById("order")).setValue(orderCount++);
							break;
						}
				  	}
				 }
			}
			
			
			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData = {
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				recordFound:"<fmt:message key="label.recordFound"/>",
				searchDuration:"<fmt:message key="label.searchDuration"/>",
				minutes:"<fmt:message key="label.minutes"/>",
				seconds:"<fmt:message key="label.seconds"/>",
				norowselected:"<fmt:message key="error.norowselected"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				errors:"<fmt:message key="label.errors"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


			<%-- Initialize the RCMenus --%>
			drawMenus();


			var columnConfig = [	
				{columnId:"permission"},	
			    {columnId:"select", columnName:'<fmt:message key="label.selectall"/><br><input type="checkbox" value="" onClick="return checkAll(\'select\');" name="selectAll" id="selectAll">', type:'hchstatus', align:'center', width:3 },
			    <c:if test="${showCatDesc == 'Y'}">
				{columnId:"catalogDesc",columnName:'<fmt:message key="label.catalog"/>',tooltip:"Y",width:18},
				</c:if> 
				{columnId:"materialCategoryName",columnName:'<fmt:message key="adhocusagereport.label.materialcategory"/>', attachHeader:'#text_filter', tooltip:"Y", width:23},
				{columnId:"materialCategoryId"}
			];

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'gridDiv',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				rowSpan: false, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				noSmartRender:true
				};

			<c:set var="resultsLength" value='${fn:length(searchResults)}'/>
			
			<%-- Check for passed error message that will require an error inline popup --%>

			<c:if test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
					messagesData.errors = "<fmt:message key="label.errors"/>";
					showErrorMessage = true;
			</c:if>   


			<%-- Check for passed error message that will require an error inline popup --%>
			<c:choose>
				<c:when test="${resultsLength > 0}">
						   var showUpdateLinks = true;
				 </c:when>
				 <c:otherwise>
				  		` var showUpdateLinks = false;
				  </c:otherwise>
			</c:choose>   
	
			//-->
		</script>
	</head>

	<body bgcolor="#ffffff" onload="popupOnLoadWithGrid(gridConfig);preSelect();" onresize="setTimeout('resizeFrames();',55)">
		<tcmis:form action="/materialcategorysearch.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors/>
    				${tcmISError}
    				<c:forEach var="tcmisError" items="${tcmISErrors}" >
  					${tcmisError}<br/>
    				</c:forEach>    
    				<c:if test="${param.maxData == fn:length(appColl)}">
					  <fmt:message key="label.maxdata"> 
					   <fmt:param value="${param.maxData}"/>
					  </fmt:message>
		 			</c:if>  
			</div>
			

			<div class="interface" id="resultsPage">
				<div class="backGroundContent"> <!-- start of backGroundContent -->
				<!-- Result Frame Begins -->
				<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<!-- Transit Page Begins -->
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				  <br><br><br><fmt:message key="label.pleasewait"/>
				  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
				</div>
				<!-- Transit Page Ends -->
				<div id="resultGridDiv" style="display: none;">

				<!-- results start -->
				<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td>
				<div class="roundcont contentContainer">
				<div class="roundright">
				  <div class="roundtop">
				    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				  </div>
				  <div class="roundContent">
				    <div class="boxhead"> <%-- boxhead Begins --%>
				     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
				          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
				          Please keep the <span></span> on the same line this will avoid extra virtical space.
				      --%>
				      <div id="updateResultLink" style="display: none"> <%-- mainUpdateLinks Begins --%>
				     	  <a href="#" onclick="selectMatCat(); return false;"><fmt:message key="label.returnselecteddata" /></a>&nbsp;|
				     	  <a href="#" onclick="window.close(); return false;"><fmt:message key="label.cancel" /></a>
				      </div>
				     </div> <%-- mainUpdateLinks Ends --%>
				     <%-- END OF OR --%>
					</div> <%-- boxhead Ends --%>
				    <div class="dataContent">
						<div id="gridDiv" style="width:100%;height:400px;display: none;"></div>
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
												 'Y',
												 false,
												 <c:if test="${showCatDesc == 'Y'}">
												 '<tcmis:jsReplace value="${row.catalogDesc}"/>',
												 </c:if>
												 '<tcmis:jsReplace value="${row.materialCategoryName}" processMultiLines="true" />',
												 '<tcmis:jsReplace value="${row.materialCategoryId}"/>'
	
												]}<c:if test="${!status.last}">,</c:if>
		 									</c:forEach>
										]};
									//-->   
								</script>
							</c:otherwise>
						</c:choose>
											   <%-- result count and time --%>
						 <div id="footer" class="messageBar"></div>
					</div>
					
					</div>
					</div>

					<div class="roundbottom">
					    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
					  </div>
					    
					  </td>
					</tr>
					</table>
					
					<div id="hiddenElements" style="display: none;">    			
						<input name="totalLines" id="totalLines" value="<c:out value="${resultsLength}"/>" type="hidden"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
						<!--This sets the end time for time elapesed from the action.-->
						<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
						<input name="selctedVals" id="selctedVals" type="hidden" value="${param.vals}">
					</div>
					   <%-- result count and time --%>
					   <div id="footer" class="messageBar"></div>
					   </div>
				</div>
			</div>
		</div>
		</tcmis:form>
	</body>
</html:html>