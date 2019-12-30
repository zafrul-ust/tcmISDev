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
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<!-- Add any other stylesheets you need for the page here -->
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
	<script type="text/javascript" src="/js/common/cabinet/clientcabinetbinlabel.js"></script>
		
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

	<%--This has the custom cells we built, hcal - the internationalized calendar which we use
	    hlink- this is for any links you want tp provide in the grid, the URL/function to call
	    can be attached based on a even (rowselect etc)
	--%>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<title><fmt:message key="label.cabinetid"/> : <fmt:formatNumber type="number" groupingUsed="false" minIntegerDigits="8" value="${param.cabinetId}" /></title>

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

<body bgcolor="#ffffff" onload="if(window['gridConfig']){popupOnLoadWithGrid(gridConfig);}"  onresize="resizeFrames()">

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

	<div class="interface" id="mainPage">
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br><br><br><fmt:message key="label.pleasewait"/>
				<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
			<div id="resultGridDiv" style="display:">
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
												<a href="#" onclick="generateBinLabels(); return false;"><fmt:message key="cabinetbinlabel.label.generatebinlabel"/></a>
											</div>
										</div>
										<div class="dataContent">
											<c:choose>
												<c:when test="${empty searchResultBeanCollection}">
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
														<tr>
															<td width="100%">
																<fmt:message key="main.nodatafound"/>
															</td>
														</tr>
													</table>								
												</c:when>
												<c:otherwise>
													<div id="clientCabinetLabelBinResults" style="height:400px;"></div>

													<bean:size collection="${searchResultBeanCollection}" id="dataCount"/>

													<script type="text/javascript">
														<!--
														var gridConfig = {
															   divName:'clientCabinetLabelBinResults', // the div id to contain the grid.
															   beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
															   beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
															   config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
															   rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
															   backgroundRender: true,
															   submitDefault:false // the fields in grid are defaulted to be submitted or not.
														};
														<%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
														var config = [ 
															{columnId: "permission"},
															{columnId: "print", columnName:'<fmt:message key="label.print"/><br><input type="checkbox" value="" onClick="return checkAll(\'print\');" name="checkAllPrint" id="checkAllPrint">', type:"hchstatus", width:6, align:"center", sorting:"na"},
															{columnId: "binIdDisplay", columnName:'<fmt:message key="label.binid"/>', align:"center"},
															{columnId: "binName", columnName:'<fmt:message key="label.binname"/>', tooltip:true, width:20},
															{columnId: "itemId", columnName:'<fmt:message key="label.itemid"/>', align:"center"},
															{columnId: "partNo", columnName:'<fmt:message key="label.partno"/>', align:"center"},
															{columnId: "partDesc", columnName:'<fmt:message key="label.partdescription"/>', tooltip:true, width:30},
															{columnId: "binId"}
														];

														var rowSpanMap = new Array();
														var rowSpanClassMap = new Array();
														var rowSpanCols = [0,1,2,3];

														<c:forEach var="row" items="${searchResultBeanCollection}" varStatus="status">
															<c:choose>
																<c:when test="${lineStatus.first}">
																	<c:set var="rowSpanStart" value='0' />
																	<c:set var="rowSpanCount" value='1' />
																	rowSpanMap[0] = 1;
																	rowSpanClassMap[0] = 1 % 2;
																</c:when>
																<c:when test="${row.binId == prevBinId}">
																	rowSpanMap[${rowSpanStart}]++;
																	rowSpanMap[${status.index}] = 0;
																	rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
																</c:when>
																<c:otherwise>
																	<c:set var="rowSpanStart" value='${status.index}' />
																	<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
																	rowSpanMap[${status.index}] = 1;
																	rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
																</c:otherwise>
															</c:choose>
															<c:set var="prevBinId" value='${row.binId}' />
														</c:forEach>
																												
														var jsonMainData = new Array();
														var jsonMainData = {
														rows:[<c:forEach var="cabinetBinLabelViewBean" items="${searchResultBeanCollection}" varStatus="status">
															{id:${status.index +1},
															 data:['Y',
																'',
																'<fmt:formatNumber type="number" groupingUsed="false" minIntegerDigits="8" value="${cabinetBinLabelViewBean.binId}" />',
																'<tcmis:jsReplace value="${cabinetBinLabelViewBean.binName}" processMultiLines="true"/>',
																'${cabinetBinLabelViewBean.itemId}',
																'${cabinetBinLabelViewBean.catPartNo}',
																'<tcmis:jsReplace value="${cabinetBinLabelViewBean.partDescription}" processMultiLines="true"/>',
																'${cabinetBinLabelViewBean.binId}',
															  ]}<c:if test="${!status.last}">,</c:if>
														  </c:forEach>]};

														  
														//-->
													</script>
												</c:otherwise>
											</c:choose>
										</div>
										<div id="hiddenElements" style="display: none;">
											<form action="dummy">
												<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
												<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
												<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
												<input name="minHeight" id="minHeight" type="hidden" value="400"/>
												<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
											</form>
										</div>
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
	</div>
	<iframe id="LabelPrintFrame" src="/blank.html" name="LabelPrintFrame" style="border:0px; width:0px; height:0px;"></iframe>
	<form name="LabelPrintForm" id="LabelPrintForm" method="post" action="/tcmIS/Hub/Cabinetbin" target="LabelPrintFrame">
	</form>	
	
	<div id="generatingWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="center" id="messageText">
					<fmt:message key="label.generatinglabels"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<img src="/images/rel_interstitial_loading.gif" align="middle">
				</td>
			</tr>
		</table>
	</div>	
</body>
</html:html>