<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->

	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<title>
		<fmt:message key="label.selectcontainer"/>
	</title>

	<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			returnselected:"<fmt:message key="label.returnselected"/>",
			ok:"<fmt:message key="label.ok"/>"
			};             

		var config = [
			{columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:8, align:"center"},
			{columnId:"itemId", columnName:'<fmt:message key="label.item"/>', width:6, align:"center"},
			{columnId:"custMsdsNo", columnName:'<fmt:message key="label.msds"/>',width:6, align:"center" }, 
			{columnId:"containerId", columnName:'<fmt:message key="label.containerid"/>', width:8, align:"center"},
			{columnId:"materialDesc" , columnName:'<fmt:message key="label.materialdesc"/>', tooltip:true , width:22 },
			{columnId:"containerSize" , columnName:'<fmt:message key="label.containersize"/>', tooltip:true , width:22 },
			{columnId:"amountRemaining" , columnName:'<fmt:message key="label.amount"/>', width:5, align: 'right' },
			{columnId:"unitOfMeasure" , columnName:'<fmt:message key="label.unit"/>', width:5, align: 'left' },
			{columnId:"containerType" }
		];

		var selectedRowId = 1;
		
		function resultOnLoad() {

			var totalLines = $v("totalLines");
			if (totalLines > 0) {
				// make result building visible if data exist
				$("SearchResults").style["display"] = "";
				// build the grid for display
				doInitGrid();
			}
			else {
				$("SearchResults").style["display"] = "none";
			}

			/* This displays our standard footer message */
			 displayNoSearchSecDuration();
			 setResultSize();

			 if(totalLines > 0) {
				 mygrid.selectRow(0);
				 selectRow(1);
			 }
		}

		function doInitGrid() {
			mygrid = new dhtmlXGridObject('SearchResults');
			initGridWithConfig(mygrid, config, -1, true, true);
			if (typeof (jsonMainData) != 'undefined') {
				mygrid.parse(jsonMainData, "json");
			}
			mygrid.attachEvent("onRowSelect",selectRow);
		}

		
		function selectRow(rowId, cellId) {
			selectedRowId = rowId;
			var container = gridCellValue(mygrid, rowId, "containerId");
			$("selectedRow").innerHTML = messagesData.returnselected + " : " + container;
		}

		function returnSelectedRow () {
			var containerId = gridCellValue(mygrid, selectedRowId, "containerId");
			var partNo = gridCellValue(mygrid, selectedRowId, "catPartNo");
			var itemId = gridCellValue(mygrid, selectedRowId, "itemId");
			var msds = gridCellValue(mygrid, selectedRowId, "custMsdsNo");
			var desc = gridCellValue(mygrid, selectedRowId, "materialDesc");
			var size = gridCellValue(mygrid, selectedRowId, "containerSize");
			var amount = gridCellValue(mygrid, selectedRowId, "amountRemaining");
			var uom = gridCellValue(mygrid, selectedRowId, "unitOfMeasure");
			var containerType = gridCellValue(mygrid, selectedRowId, "containerType");
					
			<c:choose>
				<c:when test="${param.uAction == 'solvent' || param.uAction == 'adHocMixture' || param.uAction == 'addContainerMixture'}">
					opener.addContainer(containerId, partNo, itemId, msds, desc, size, amount, uom, containerType, ${mixtureCount},'${param.uAction}');
				</c:when>
				<c:otherwise>
					opener.changeContainer(containerId, amount, uom);
				</c:otherwise>
			</c:choose>
			window.close();
		}
		// -->
	</script>
</head>


<body bgcolor="#ffffff" onload="resultOnLoad()" onresize="resizeFrames()" onunload="opener.parent.closeTransitWin();">
	<div class="interface" id="mainPage">
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br>
				<br>
				<br>
				<fmt:message key="label.pleasewait" />
				<br>
				<br>
				<br>
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
	
			<div id="resultGridDiv" style="display: none;">
				<%-- Search results start --%> 
				<%-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. --%>
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="roundcont contentContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright">
											<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
									<div class="roundContent">
										<div class="boxhead">
											<div id="mainUpdateLinks"> 
												<a href="#" id ="selectedRow" onclick="returnSelectedRow();"></a>
											</div>
										</div>
										<div class="dataContent">
											<tcmis:form action="/buildingsearch.do">
												<div id="errorMessagesAreaBody" style="display:none;">
													<html:errors/>
													${tcmISError}
													<c:forEach items="${tcmISErrors}" varStatus="status">
													${status.current}<br/>
													</c:forEach>        
												</div>

												<script type="text/javascript">
												   <c:choose>
													<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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
														<div id="SearchResults" style="width:100%;height:400px;" style="display: none;"></div>

														<script type="text/javascript">
														<!--
														<c:if test="${!empty containers}" >  
														var jsonMainData =  {
															rows:[<c:forEach var="row" items="${containers}" varStatus="status">
															{id: ${status.count},
															 	 data:[ '${row.catPartNo}',
																	'${row.itemId}',
																	'<tcmis:jsReplace value="${row.custMsdsNo}"/>',
																	'${row.containerId}',
																	'<tcmis:jsReplace value="${row.materialDesc}"/>',
																	'<tcmis:jsReplace value="${row.containerSize}"/>',
																	'${row.amountRemaining}',
																	'${row.unitOfMeasure}',
																	'${row.containerType}'
															 	 ]}<c:if test="${!status.last}">,</c:if>
															 </c:forEach>
														]};
														</c:if>
														//-->  
														</script>

														<%-- If the collection is empty say no data found --%>
														<c:if test="${empty containers}">
														  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
															<tr>
															   <td width="100%">
																  <fmt:message key="main.nodatafound"/>
															   </td>
															</tr>
														  </table>
														</c:if>

														<%-- Hidden element start --%> 
														<div id="hiddenElements" style="display: none;">    		
															<input name="minHeight" id="minHeight" type="hidden" value="100">
															<input name="totalLines" id="totalLines" value="${fn:length(containers)}" type="hidden"/>
															<input name="uAction" id="uAction" value="" type="hidden"/>	
															<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
															<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>														</div>
													</div>
												</div>
											</tcmis:form>
										</div>
								
										<%-- result count and time --%>
										<div id="footer" class="messageBar"></div>
								
									</div>
									<div class="roundbottom">
										<div class="roundbottomright">
											<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div> <!-- close of interface -->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
	
	</div>

</body>
</html:html>