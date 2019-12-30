<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	<%--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
	<tcmis:gridFontSizeCss  overflow="notHidden"/>
	<!-- Add any other stylesheets you need for the page here -->
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<%-- This handles which key press events are disabeled on this page --%>
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
	<script type="text/javascript" src="/js/common/cabinet/customermrtracking.js"></script>
		
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<title>
		<fmt:message key="label.mrallocation"/> 
	</title>

	<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			updateSuccess:"<fmt:message key="msg.updatesuccess"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			selectSomething:"<fmt:message key="error.norowselected"/>",
			errors:"<fmt:message key="label.errors"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>"
		};	       		
		// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnLoad();"  onresize="resizeFrames()">

	<div id="errorMessagesAreaBody" style="display:none;">
		${tcmISError}<br/>
		<c:forEach items="${tcmISErrors}" varStatus="status">
			${status.current}<br/>
		</c:forEach>
	</div>

	<script type="text/javascript">
		<c:choose>
			<c:when test="${empty tcmISErrors and empty tcmISError}">
				showErrorMessage = false;
			</c:when>
			<c:otherwise>
				showErrorMessage = true;
			</c:otherwise>
		</c:choose>
	</script>

	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
		<br><br><br><fmt:message key="label.pleasewait"/>
		<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>

	<div class="interface" id="mainPage">
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<div id="resultGridDiv" style="display:">
				<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					<tr id="showMrRow" style="display: none;">
						<td class="optionTitleBoldLeft">
							<fmt:message key="label.mrscreated"/>
						</td>
						<td colspan="6">&nbsp; </td>
						<td class="optionTitleBoldRight"  onclick="showMrDiv();">
							&nbsp;&nbsp;&nbsp;&nbsp;<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
						</td>
					</tr>
					<tr id="hideMrRow">
						<td colspan="7"> &nbsp;</td>
						<td class="optionTitleBoldRight"  onclick="hideMrDiv();">
							&nbsp;&nbsp;&nbsp;&nbsp;<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
						</td>
					</tr>
				</table>
				<div id="mrDiv">
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
												<fmt:message key="label.mrscreated"/> - <a href="#" onclick="doRefresh(); return false;"><fmt:message key="label.refresh"/></a>
											</div>
											<div class="dataContent">
												<c:choose>
													<c:when test="${empty mrResultsCollection}">
														<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
															<tr>
																<td width="100%">
																	<fmt:message key="main.nodatafound"/>
																</td>
															</tr>
														</table>								
													</c:when>
													<c:otherwise>
														<div id="mrResults" style="height:300px;"></div>
	
														<bean:size collection="${mrResultsCollection}" id="mrDataCount"/>
	
														<script type="text/javascript">
															<!--
															<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
															var mrConfig = [
																{columnId:"mrLine", columnName:'<fmt:message key="label.mrline"/>', width:8, align:"center"},
																{columnId:"facility", columnName:'<fmt:message key="label.facility"/>', width:10, tooltip:"Y"},
																{columnId:"workArea", columnName:'<fmt:message key="label.workarea"/>', width:10, tooltip:"Y"},
																{columnId:"partNum", columnName:'<fmt:message key="label.partnum"/>', width:8, tooltip:"Y", align:"center"},
																{columnId:"item", columnName:'<fmt:message key="label.item"/>', width:8, align:"center"},
																{columnId:"inventoryGroup", columnName:'<fmt:message key="label.inventorygroup"/>', width:10, tooltip:"Y"},
																{columnId:"itemDesc", columnName:'<fmt:message key="label.itemdescription"/>', width:20, tooltip:"Y"},
																{columnId:"packaging", columnName:'<fmt:message key="label.packaging"/>', width:20, tooltip:"Y"},
																{columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>', width:4, align:"center"},
																{columnId:"shipTo", columnName:'<fmt:message key="label.shipto"/>', width:10, tooltip:"Y"},
																{columnId:"status", columnName:'<fmt:message key="label.status"/>', width:8, align:"center"}
															];
															var mrGridConfig = {
																   divName:'mrResults', // the div id to contain the grid.
																   beanData:'jsonMainDataMr', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
																   beanGrid:'mrgrid', // the grid's name, as in beanGrid.attachEvent...
																   config:mrConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
																   rowSpan:false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
																   submitDefault:false // the fields in grid are defaulted to be submitted or not.
															};
	
															var jsonMainDataMr = {
															rows:[<c:forEach var="row" items="${mrResultsCollection}" varStatus="status">
																{id:${status.index + 1},
																 data:[ '${row.prNumber}-${row.lineItem}',
																	'<tcmis:jsReplace value="${row.facilityName}" processMultiLines="true"/>',
																	'<tcmis:jsReplace value="${row.applicationDesc}" processMultiLines="true"/>',
																	'${row.facPartNo}',
																	'${row.itemId}',
																	'${row.inventoryGroup}',
																	'<tcmis:jsReplace value="${row.itemDesc}" processMultiLines="true"/>',
																	'${row.packaging}',
																	'${row.quantity}',
																	'${row.shipToLocationId}',
																	'${row.requestLineStatus}'
																  ]}<c:if test="${!status.last}">,</c:if>
															  </c:forEach>]};														
															//-->
														</script>
													</c:otherwise>
												</c:choose>
											</div>
											<div id="hiddenElements" style="display: none;">
												<form action="dummy">
													<input name="totalLines" id="totalLines" value="${mrDataCount}" type="hidden"/>
													<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
													<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
													<input name="minHeight" id="minHeight" type="hidden" value="300"/>
												</form>
											</div>
											<div id="mrFooter" class="messageBar"></div>
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
				<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					<tr id="showAllocationRow" style="display: none;">
						<td class="optionTitleBoldLeft">
							<fmt:message key="label.allocationresults"/>
						</td>
						<td colspan="6">&nbsp; </td>
						<td class="optionTitleBoldRight"  onclick="showAllocationDiv();">
							&nbsp;&nbsp;&nbsp;&nbsp;<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
						</td>
					</tr>
					<tr id="hideAllocationRow">
						<td colspan="7"> &nbsp;</td>
						<td class="optionTitleBoldRight"  onclick="hideAllocationDiv();">
							&nbsp;&nbsp;&nbsp;&nbsp;<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
						</td>
					</tr>
				</table>
				<div id="allocationDiv">
					<table id="resultsMaskTable2" width="100%" border="0" cellpadding="0" cellspacing="0">
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
												<fmt:message key="label.allocationresults"/> - <a href="#" onclick="doRefresh(); return false;"><fmt:message key="label.refresh"/></a>
												<span style="margin-left: 10em;"><fmt:message key="label.subjecttochangemsg"/></span>
											</div>
											<div class="dataContent">
												<c:choose>
													<c:when test="${empty allocationResultsCollection}">
														<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
															<tr>
																<td width="100%">
																	<fmt:message key="main.nodatafound"/>
																</td>
															</tr>
														</table>								
													</c:when>
													<c:otherwise>
														<div id="allocationResults" style="height:300px;"></div>
	
														<bean:size collection="${allocationResultsCollection}" id="allocationDataCount"/>
	
														<script type="text/javascript">
															<!--
															<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
															var allocationConfig = [
																{columnId:"mrLine", columnName:'<fmt:message key="label.mrline"/>', width:8, align:"center"},
																{columnId:"item", columnName:'<fmt:message key="label.item"/>', width:8, align:"center", tooltip:"Y"},
																{columnId:"inventoryGroup", columnName:'<fmt:message key="label.inventorygroup"/>', width:10, tooltip:"Y"},
																{columnId:"quantity", columnName:'<fmt:message key="label.quantity"/>', width:4, align:"center"},
																{columnId:"allocationType", columnName:'<fmt:message key="label.allocationtype"/>', width:10, align:"center"},
																{columnId:"hub", columnName:'<fmt:message key="label.hub"/>', width:20, tooltip:"Y"}
															];
															var allocationGridConfig = {
																   divName:'allocationResults', // the div id to contain the grid.
																   beanData:'jsonMainDataAllocation', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
																   beanGrid:'allocationgrid', // the grid's name, as in beanGrid.attachEvent...
																   config:allocationConfig, // the column config var name, as in var config = { [ columnId:..,columnName...
																   rowSpan:false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
																   submitDefault:false // the fields in grid are defaulted to be submitted or not.
															};
	
															var jsonMainDataAllocation = {
															rows:[<c:forEach var="row" items="${allocationResultsCollection}" varStatus="status">
																{id:${status.index + 1},
																 data:[ '${row.prNumber}-${row.lineItem}',
																	'${row.itemId}',
																	'${row.inventoryGroup}',
																	'${row.quantity}',
																	'<tcmis:jsReplace value="${row.description}" processMultiLines="true"/>',
																	'<tcmis:jsReplace value="${row.hubName}" processMultiLines="true"/>'
																  ]}<c:if test="${!status.last}">,</c:if>
															  </c:forEach>]};														
															//-->
														</script>
													</c:otherwise>
												</c:choose>
											</div>
											<div id="hiddenElements2" style="display: none;">
												<form action="dummy">
													<input name="totalLines" id="totalLines2" value="${mrDataCount}" type="hidden"/>
													<input name="minHeight" id="minHeight2" type="hidden" value="300"/>
												</form>
											</div>
											<div id="allocationFooter" class="messageBar"></div>
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
		</div>
	</div>
</body>
</html:html>